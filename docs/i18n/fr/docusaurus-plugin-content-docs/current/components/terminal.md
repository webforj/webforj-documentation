---
title: Terminal
sidebar_position: 126
_i18n_hash: 8bc6b488ddba7e4660319f00c497321c
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

Le composant `Terminal` fournit un émulateur de terminal interactif qui fonctionne de manière similaire à une console système traditionnelle. Il permet aux applications d'afficher et de manipuler une interface basée sur du texte, en gérant la sortie texte, en recevant des entrées utilisateur, en interprétant les séquences de contrôle et en maintenant des tampons d'écran.

Ce terminal est conçu pour offrir un comportement fiable dans un large éventail de cas d'utilisation, tel que la construction d'outils d'accès à distance, de tableaux de bord textuels, de shells de commande embarqués ou de consoles de débogage interactives.

:::info Importation de Terminal
Pour utiliser le composant `Terminal` dans votre application, assurez-vous d'inclure la dépendance suivante dans votre pom.xml.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

<ComponentDemo 
path='/webforj/terminal?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/TerminalView.java'
urls={[
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/TerminalCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/ClearCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/DateCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/HelpCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/MsgCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/PromptCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/java/com/webforj/samples/views/terminal/commands/TimeCommand.java'
]}
height='400px'
/>

## Comment ça fonctionne {#how-it-works}

Le terminal gère une grille de cellules de texte, traite les flux de caractères entrants et réagit aux actions de l'utilisateur comme la saisie ou la sélection de texte. Il interprète automatiquement les caractères de contrôle et les séquences d'échappement pour le mouvement du curseur, les changements de couleur et l'effacement de l'écran.

Les comportements principaux incluent :

- **Entrée de données** : Écrire des données dans le terminal met à jour l'écran, gérant à la fois le texte et les séquences de contrôle.
- **Sortie de données** : Capture les frappes de l'utilisateur et les émet sous forme d'événements structurés.
- **Gestion de l'écran** : Maintient un tampon d'historique défilable et l'état actuel de l'écran.
- **Gestion du curseur** : Suit la position du curseur pour les entrées de texte et les réponses aux séquences de contrôle.

Le terminal est étatful, ce qui signifie qu'il reconstruit correctement les caractères multioctets et maintient la continuité à travers les entrées fragmentées.

## Envoi de données au terminal {#sending-data-to-the-terminal}

Les données sont envoyées au terminal à l'aide des méthodes `write` et `writeln` :

- `write(Object data)`: Envoie des données dans le flux du terminal.
- `writeln(Object data)`: Envoie des données suivies d'une nouvelle ligne.

Le terminal traite toutes les données entrantes en tant que chaînes **UTF-16**. Il gère automatiquement les caractères multioctets, même lorsque l'entrée arrive par morceaux fragmentés.

### Exemple {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Prêt.");
```

Vous pouvez également attacher un rappel qui s'exécute une fois le morceau de données traité :

```java
terminal.write("Long output de commande", e -> {
    System.out.println("Données traitées.");
});
```

## Réception des entrées utilisateur {#receiving-user-input}

Le terminal capture les entrées générées par l'utilisateur à travers deux événements :

- **Événement de données (`onData`)** : Se déclenche lorsque du texte est saisi, envoyant des caractères Unicode.
- **Événement de clé (`onKey`)** : Se déclenche pour chaque pression de touche, y compris les informations sur les codes de touche et les modificateurs comme <kbd>Ctrl</kbd> ou <kbd>Alt</kbd>.

Ces événements peuvent être utilisés pour relayer les entrées utilisateur à un backend, mettre à jour des éléments d'interface utilisateur ou déclencher des actions personnalisées.

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

Toutes les entrées utilisateur capturées par le terminal (telles que provenant d'événements `onData`) sont émises sous forme de chaînes UTF-16.  
Si votre backend attend un encodage différent (tel que des octets UTF-8), vous devez convertir manuellement les données.

:::info Encodages hérités
Le terminal **ne prend pas en charge les encodages hérités** tels que `ISO-8859`.  
Si vous avez besoin de compatibilité avec des systèmes non-UTF-8, utilisez un transcoder externe (par exemple, [`luit`](https://linux.die.net/man/1/luit) ou [`iconv`](https://en.wikipedia.org/wiki/Iconv)) pour convertir les données avant de les écrire ou de les lire à partir du terminal.
:::

## Gestion des grands flux de données {#handling-large-data-streams}

Parce que le terminal ne peut pas instantanément rendre un nombre illimité d'entrées, il maintient un tampon d'entrée interne. Si ce tampon devient trop grand (environ `50 Mo` par défaut), de nouvelles données entrantes peuvent être supprimées pour protéger les performances du système.

Pour gérer correctement des sources de données rapides, vous devez implémenter un **contrôle de flux**.

### Exemple de contrôle de flux basique {#basic-flow-control-example}

Mettez votre backend en pause jusqu'à ce que le terminal ait fini de traiter un morceau :

```java
pty.onData(chunk -> {
    pty.pause();
    terminal.write(chunk, result -> {
        pty.resume();
    });
});
```

### Exemple de contrôle de flux de seuil {#watermark-flow-control-example}

Pour un contrôle plus efficace, utilisez des seuils élevés/bas :

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
path='/webforj/serverlogs?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/ServerLogsView.java'
height='400px'
/>

## Personnalisation {#customization}

### Options du terminal {#terminal-options}

La classe `TerminalOptions` vous permet de configurer le comportement :

- Clignotement du curseur.
- Paramètres de police (famille, taille, poids).
- Taille du tampon de retour en arrière.
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

Vous pouvez styliser le terminal en utilisant `TerminalTheme`, qui définit :

- Couleurs d'arrière-plan et de premier plan.
- Palette de couleurs `ANSI` standard.
- Couleurs d'arrière-plan du curseur et de sélection.

Exemple :
```java
TerminalTheme theme = new TerminalTheme();
theme.setBackground("#1e1e1e");
theme.setForeground("#cccccc");
terminal.setTheme(theme);
```
<ComponentDemo 
path='/webforj/terminalthemepicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/TerminalThemePickerView.java'
height='500px'
/>

## Séquences prises en charge {#supported-sequences}

Le terminal prend en charge un large éventail de séquences de contrôle standard utilisées pour le mouvement du curseur, les mises à jour de l'écran et le formatage du texte.

Groupes reconnus :

- **Codes de commande `C0`** (commandes à un octet 7 bits, `\x00`, `\x1F`, comme retour arrière et saut de ligne)
- **Codes de commande `C1`** (commandes à un octet 8 bits, `\x80`, `\x9F`)
- **Séquences `ESC`** (commençant par `ESC` (`\x1B`), comme sauvegarder/restaurer le curseur, alignement de l'écran)
- **Séquences `CSI`** (Introducer de Séquence de Contrôle, `ESC [` ou `CSI (\x9B)`, pour des opérations telles que le défilement, l'effacement et le stylage)
- **Séquences `DCS`** (Chaînes de Contrôle d'Appareil, `ESC P` ou `DCS (\x90)`)
- **Séquences `OSC`** (Commandes du Système d'Exploitation, `ESC ]` ou `OSC (\x9D)`, pour définir le titre de la fenêtre, des hyperliens et des couleurs)

:::info Gestion des séquences exotiques et personnalisées
Certaines types de séquences exotiques comme `APC`,`PM`, et `SOS` sont reconnues mais ignorées silencieusement.  
Les séquences personnalisées peuvent être prises en charge via des intégrations si nécessaire.
:::

## Stylisation {#styling}

<TableBuilder name="Terminal" />
