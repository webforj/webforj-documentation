---
title: Terminal
sidebar_position: 126
description: >-
  Embed an interactive terminal emulator with the Terminal component for shells,
  dashboards, debug consoles, and remote access tools.
_i18n_hash: 231986360b04eb43ad3b6fecc9f02816
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

Le composant `Terminal` est un émulateur de terminal interactif qui se comporte comme une console système traditionnelle. Il gère les sorties texte, les entrées utilisateur, les séquences de contrôle et les tampons d'écran, ce qui le rend adapté à la construction d'outils d'accès à distance, de tableaux de bord textuels, de shells de commande intégrés ou de consoles de débogage.

<!-- INTRO_END -->

## Création d'un terminal {#creating-a-terminal}

:::info Importation du Terminal
Pour utiliser le composant `Terminal` dans votre application, assurez-vous d'inclure la dépendance suivante dans votre pom.xml.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

L'exemple suivant construit un shell de commande interactif avec des commandes tapées, une navigation dans l'historique et une sortie personnalisée.

<ComponentDemo
path='/webforj/terminal'
files={[
  'src/main/java/com/webforj/samples/views/terminal/TerminalView.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/TerminalCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/ClearCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/DateCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/HelpCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/MsgCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/ConfirmCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/TimeCommand.java',
  'src/main/frontend/css/terminal/terminal-view.css',
]}
height='400px'
/>

## Comment ça fonctionne {#how-it-works}

Le terminal gère une grille de cellules textuelles, traite les flux de caractères entrants et réagit aux actions de l'utilisateur comme la frappe ou la sélection de texte. Il interprète automatiquement les caractères de contrôle et les séquences d'échappement pour le mouvement du curseur, les changements de couleur et le nettoyage de l'écran.

Les comportements principaux incluent :

- **Entrée de données** : L'écriture de données dans le terminal met à jour l'écran, gérant à la fois le texte et les séquences de contrôle.
- **Sortie de données** : Capture les frappes de l'utilisateur et les émet sous forme d'événements structurés.
- **Gestion de l'écran** : Maintient un tampon d'historique déroulant et l'état actuel de l'écran.
- **Gestion du curseur** : Suit la position du curseur pour l'entrée de texte et les réponses aux séquences de contrôle.

Le terminal est avec état, ce qui signifie qu'il reconstruit correctement les caractères multioctets et maintient la continuité à travers des entrées fragmentées.

## Envoi de données au terminal {#sending-data-to-the-terminal}

Les données sont envoyées au terminal à l'aide des méthodes `write` et `writeln` :

- `write(Object data)`: Envoie des données dans le flux du terminal.
- `writeln(Object data)`: Envoie des données suivies d'une nouvelle ligne.

Le terminal traite toutes les données entrantes sous forme de chaînes **UTF-16**. Il gère automatiquement les caractères multioctets, même lorsque l'entrée arrive en morceaux fragmentés.

### Exemple {#example}
```java
terminal.write("echo Bonjour le monde\n");
terminal.writeln("Prêt.");
```

Vous pouvez également attacher un rappel qui s'exécute une fois que le morceau de données a été traité :

```java
terminal.write("Sortie de commande longue", e -> {
  System.out.println("Données traitées.");
});
```

## Réception de l'entrée utilisateur {#receiving-user-input}

Le terminal capture l'entrée générée par l'utilisateur à travers deux événements :

- **Événement de données (`onData`)** : Se déclenche lors de l'entrée de texte, envoyant des caractères Unicode.
- **Événement de clé (`onKey`)** : Se déclenche pour chaque pression de touche, y compris des informations sur les codes de touche et les modificateurs comme <kbd>Ctrl</kbd> ou <kbd>Alt</kbd>.

Ces événements peuvent être utilisés pour relayer l'entrée utilisateur à un backend, mettre à jour des éléments d'interface utilisateur ou déclencher des actions personnalisées.

### Exemple {#example-1}
```java
terminal.onData(event -> {
  String userInput = event.getValue();
  backend.send(userInput);
});

terminal.onKey(event -> {
  if (event.isControlKey() && "C".equals(event.getKey())) {
      backend.send("SIGINT");
  }
});
```

Toutes les entrées utilisateur capturées par le terminal (comme celles provenant des événements `onData`) sont émises sous forme de chaînes UTF-16.
Si votre backend attend un encodage différent (comme des octets UTF-8), vous devez transcoder manuellement les données.

:::info Encodages légacy
Le terminal **ne prend pas en charge les encodages légacy** tels que `ISO-8859`.
Si vous avez besoin de compatibilité avec des systèmes non-UTF-8, utilisez un transcoder externe (par exemple, [`luit`](https://linux.die.net/man/1/luit) ou [`iconv`](https://en.wikipedia.org/wiki/Iconv)) pour convertir les données avant de les écrire dans ou de les lire depuis le terminal.
:::

## Gestion de grands flux de données {#handling-large-data-streams}

Comme le terminal ne peut pas rendre instantanément des entrées illimitées, il maintient un tampon d'entrée interne. Si ce tampon devient trop grand (environ `50 Mo` par défaut), de nouvelles données entrantes peuvent être perdues pour protéger les performances du système.

Pour gérer correctement des sources de données rapides, vous devez implémenter un **contrôle de flux**.

### Exemple de contrôle de flux de base {#basic-flow-control-example}

Pauze votre backend jusqu'à ce que le terminal termine le traitement d'un morceau :

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### Exemple de contrôle de flux de watermark {#watermark-flow-control-example}

Pour un contrôle plus efficace, utilisez des points de repère haut/bas :

```java
int HIGH_WATERMARK = 100_000;
int LOW_WATERMARK = 10_000;

int bufferedBytes = 0;

pty.onData(chunk -> {
  bufferedBytes += chunk.length;

  terminal.write(chunk, e -> {
    bufferedBytes -= chunk.length;
    if (bufferedBytes < LOW_WATERMARK) {
        pty.resume();
    }
  });

  if (bufferedBytes > HIGH_WATERMARK) {
    pty.pause();
  }
});
```

<ComponentDemo
path='/webforj/serverlogs'
files={['src/main/java/com/webforj/samples/views/terminal/ServerLogsView.java']}
height='400px'
/>

## Personnalisation {#customization}

### Options du terminal {#terminal-options}

La classe `TerminalOptions` vous permet de configurer le comportement :

- Clignotement du curseur.
- Paramètres de police (famille, taille, poids).
- Taille du tampon de défilement.
- Hauteur de ligne et espacement des lettres.
- Paramètres d'accessibilité (mode lecteur d'écran).

Exemple :
```java
TerminalOptions options = new TerminalOptions()
  .setCursorBlink(true)
  .setFontFamily("Courier New, monospace")
  .setFontSize(13)
  .setScrollback(5000);

terminal.setOptions(options);
```

### Thème du terminal {#terminal-theme}

Vous pouvez styliser le terminal à l'aide de `TerminalTheme`, qui définit :

- Couleurs de fond et de premier plan.
- Palette de couleurs `ANSI` standard.
- Couleurs de fond du curseur et de la sélection.

Exemple :
```java
TerminalTheme theme = new TerminalTheme();
theme.setBackground("#1e1e1e");
theme.setForeground("#cccccc");
terminal.setTheme(theme);
```
<ComponentDemo
path='/webforj/terminalthemepicker'
files={['src/main/java/com/webforj/samples/views/terminal/TerminalThemePickerView.java']}
height='500px'
/>

## Séquences prises en charge {#supported-sequences}

Le terminal prend en charge une large gamme de séquences de contrôle standard utilisées pour le mouvement du curseur, les mises à jour de l'écran et le formatage du texte.

Groupes reconnus :

- **Codes de contrôle `C0`** (commandes 7 bits à un octet, `\x00`, `\x1F`, comme retour arrière et retour à la ligne)
- **Codes de contrôle `C1`** (commandes à un octet, `\x80`, `\x9F`)
- **Séquences `ESC`** (commençant par `ESC` (`\x1B`), comme sauvegarde/restauration du curseur, alignement de l'écran)
- **Séquences `CSI`** (Introducer de Séquence de Contrôle, `ESC [` ou `CSI (\x9B)`, pour des opérations comme le défilement, l'effacement et le style)
- **Séquences `DCS`** (Chaînes de Contrôle d'Appareil, `ESC P` ou `DCS (\x90)`)
- **Séquences `OSC`** (Commandes du Système d'Exploitation, `ESC ]` ou `OSC (\x9D)`, pour définir le titre de la fenêtre, des hyperliens et des couleurs)

:::info Gestion des séquences exotiques et personnalisées
Certaines séquences exotiques comme `APC`, `PM`, et `SOS` sont reconnues mais silencieusement ignorées.
Des séquences personnalisées peuvent être prises en charge par le biais d'intégrations si nécessaire.
:::

## Stylisation {#styling}

<TableBuilder name="Terminal" />
