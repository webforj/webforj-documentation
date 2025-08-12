---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: 71f184a02c2552f5af34bfc3ec47c385
---
Cette documentation sert de guide pour mettre à niveau les applications webforJ de la version 24.00 à 25.00. Voici les changements nécessaires pour que les applications existantes continuent à fonctionner sans heurts. Comme toujours, consultez la [vue d'ensemble des versions sur GitHub](https://github.com/webforj/webforj/releases) pour une liste plus complète des changements entre les versions.

## Serveurs web Jetty 12 {#jetty-12-web-servers}

webforJ 25.00 et versions supérieures utilisent Jetty 12, en se basant sur l'architecture des servlets Jakarta EE10. Si vous utilisez le plugin Maven Jetty pour le développement, migrez de Jakarta EE8 à Jakarta EE10. Cette mise à niveau nécessitera également de remplacer tout ce qui dépendait du package `javax.servlet` par le package `Jakarta.servlet`.

### Changements dans le fichier POM {#pom-file-changes}

**Avant**

```xml {2-4}
<plugin>
  <groupId>org.eclipse.jetty.ee8</groupId>
  <artifactId>jetty-ee8-maven-plugin</artifactId>
  <version>10.x.xx</version>
```
**Après**

```xml {2-4}
<plugin>
  <groupId>org.eclipse.jetty.ee10</groupId>
  <artifactId>jetty-ee10-maven-plugin</artifactId>
  <version>12.x.xx</version>
```

## Changements d'API pour la classe `App` {#api-changes-for-the-app-class}

Plusieurs méthodes `App` obsolètes sont supprimées dans la version 25.00. Les sections suivantes décrivent quelles méthodes ont été remplacées et les remplacements recommandés.

### Journalisation dans la console {#console-logging}

La classe utilitaire [`BrowserConsole`](../advanced/browser-console.md), dédiée à la création de journaux stylisés dans la console du navigateur, remplace les méthodes `consoleLog()` et `consoleError()`. Obtenez le `BrowserConsole` en utilisant la méthode `console()` :

```java
public class Application extends App{
  
  @Override
  public void run() throws WebforjException {
    console().log("Message de journal");
    console().error("Message d'erreur");
  }
}
```

### Stockage web {#web-storage}

Pour les versions antérieures à webforJ 25.00, la classe `App` dispose des méthodes `getLocalStorage()`, `getSessionStorage()`, et `getCookieStorage()` pour obtenir des instances des classes `LocalStorage`, `SessionStorage` et `CookieStorage` respectivement. À l'avenir, chaque classe dispose d'une méthode `getCurrent()`.

Consultez [Web Storage](../advanced/web-storage.md) pour plus d'informations.

### Classe `Request` {#request-class}

La classe `Request` est désormais responsable de l'obtention de l'URL, du port, de l'hôte et du protocole de l'application. Donc, au lieu d'utiliser `App.getUrl()`, utilisez `App.getCurrent().getUrl()`. La méthode `getCurrent()` remplace également la méthode `getRequest()` pour obtenir une instance de la classe `Request`.

:::info
La classe `Request` a également supprimé des méthodes, consultez [`Request`](#request-changes) pour les voir.
:::

### Classe `Page` {#page-class}

La méthode `getPage()` est remplacée par `Page.getCurrent()` pour obtenir l'instance de la page actuelle.

### Dialogues d'options {#option-dialogs}

Au lieu d'utiliser la méthode `msgbox()`, utilisez [`OptionDialog.showMessageDialog()`](../components/option-dialogs/message) pour créer des dialogues de message.

### Terminaison de l'application {#app-termination}

La méthode `cleanup()` a été supprimée. Il existe maintenant deux méthodes pour la terminaison, `onWillTerminate()` et `onDidTerminate()`.

Consultez [Hooks pour la terminaison](../advanced/terminate-and-error-actions.md#hooks-for-termination) pour plus d'informations.

## Tri des tableaux {#table-sorting}

Pour webforJ 25.00 et versions supérieures, les tableaux utilisent le tri par colonne unique par défaut. Les colonnes ne seront triées que par l'en-tête de colonne le plus récemment sélectionné. Pour faire en sorte qu’un tableau utilise le tri par plusieurs colonnes, invoquez la méthode [`setMultiSorting()`](../components/table/sorting#multi-sorting) :

```java
table.setMultiSorting(true);
```

## Corps `TabbedPane` masqué {#hidden-tabbedpane-body}

La méthode `hideBody()` est remplacée par `setBodyHidden()` pour maintenir une convention de nommage cohérente pour les méthodes.

## Rendu HTML à l'intérieur des composants {#rendering-html-inside-components}

Dans webforJ 25.00 et versions supérieures, il existe une méthode `setHtml()` pour aider à faire la distinction entre le texte littéral et le texte HTML à l'intérieur d'un composant. Il est toujours possible de définir du HTML à l'aide de la méthode `setText()`, mais cela nécessite maintenant de l'envelopper explicitement dans des balises `<html>`.

```java
// Utilisations valides de setText() et setHtml()
Button home = new Button();

home.setText("""
  <html>
    <h1>Accueil</h1>
  </html>
""");

home.setHtml("<h1>Accueil</h1>");

home.setText("Accueil");
```

```java
// Utilisations invalides de setText() et setHtml()
Button home = new Button();
home.setText("<h1>Accueil</h1>");
```

## Conteneurs HTML {#html-containers}

Le package `com.webforj.component.htmlcontainer` n'est plus présent dans webforJ. Utilisez plutôt le package `com.webforj.component.element`, qui dispose de plus de fonctionnalités. Pour une liste des classes webforJ pour les éléments HTML standard, consultez [Composants d'éléments HTML](../building-ui/web-components/html-elements.md).

## Changements dans `Request` {#request-changes}

- Tout comme la suppression de la méthode `getCookieStorage()` pour la classe `App`, `Request` n'a plus la méthode `getCookie()`. Cela renforce l'utilisation de `CookieStorgage.getCurrent()` pour obtenir une instance de la classe `CookieStorage`.

- La méthode `getQueryParam()` est désormais `getQueryParameter()`.

## Changements dans `WebforjBBjBridge` {#webforjbbjbridge-changes}

### Obtenir une instance de `WebforjBBjBridge` {#getting-an-instance-of-webforjbbjbridge}

La classe `Environment` n'a plus la méthode `getWebforjHelper()`, utilisez donc `getBridge()` à la place.

### Utiliser le composant `ConfirmDialog` pour la méthode `msgbox()` {#using-the-confirmdialog-component-for-the-msgbox-method}

Les versions précédentes de webforJ utilisent des chaînes de caractères et des entiers directement pour la méthode `msgbox()` de `WebforjBBjBridge`. Cependant, les messages pour `WebforjBBjBridge` dans webforJ 25.00 et supérieures utilisent le composant [`ConfirmDialog`](../components/option-dialogs/confirm.md). Cela offre un meilleur contrôle sur les boutons affichés et le type de message.

**Avant**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getWebforjHelper();

int msgboxResult = bridge.msgbox("Êtes-vous sûr de vouloir supprimer ce fichier ?", 1, "Suppression");
```

**Après**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getBridge();

ConfirmDialog dialog = new ConfirmDialog(
      "Êtes-vous sûr de vouloir supprimer ce fichier ?", "Suppression",
      ConfirmDialog.OptionType.OK_CANCEL, ConfirmDialog.MessageType.QUESTION);

int msgboxResult = bridge.msgbox(dialog);
```

<!-- ## Environment.logError removed -->

## Correction de typographie dans `PasswordMediation` {#passwordmediation-typo-correction}

La classe énumérée `PasswordMediation`, utilisée pour indiquer si un utilisateur doit se connecter à chaque visite d'une application avec un composant `Login`, a une faute de frappe dans les versions précédentes de webforJ. `SILENT` remplace l'erreur `SILIENT` pour webforJ 25.00 et versions supérieures.

## Méthodes d'auto-focalisation {#auto-focusing-methods}

Pour maintenir la cohérence dans webforJ, des méthodes comme `setAutofocus()` et `isAutofocus()` ont désormais une capitalisation uniforme, comme dans l'interface HasAutoFocus. Ainsi, les composants comme `Dialog` et `Drawer` utilisent `setAutoFocus()` et `isAutoFocus()` dans la version 25.00 et supérieure.

## `BBjWindowAdapter` et `Panel` marqués comme `final` {#bbjwindowadapter-and-panel-marked-as-final}

Les classes `BBjWindowAdapter` et `Panel` sont désormais déclarées comme `final`, ce qui signifie qu'elles ne peuvent plus être sous-classées. Ce changement améliore la stabilité et impose des modèles d'utilisation cohérents.
