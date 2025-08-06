---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: 2553d37a63c097b7520f2989849f016b
---
Cette documentation sert de guide pour mettre à niveau les applications webforJ de la version 24.00 à 25.00. Voici les changements nécessaires pour que les applications existantes continuent de fonctionner correctement. Comme toujours, consultez le [aperçu des publications GitHub](https://github.com/webforj/webforj/releases) pour une liste plus complète des changements entre les versions.

## Serveurs web Jetty 12 {#jetty-12-web-servers}

webforJ 25.00 et versions supérieures utilisent Jetty 12, en utilisant l'architecture de servlet Jakarta EE10. Si vous utilisez le plugin Maven Jetty pour le développement, migrez de Jakarta EE8 à Jakarta EE10. Cette mise à niveau nécessitera également de remplacer tout ce qui dépendait du package `javax.servlet` par le package `Jakarta.servlet`.

### Changements du fichier POM {#pom-file-changes}

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

## Changements de l'API pour la classe `App` {#api-changes-for-the-app-class}

Plusieurs méthodes obsolètes de la classe `App` ont été supprimées dans 25.00. Les sections suivantes décrivent quelles méthodes ont été remplacées et les remplacements recommandés.

### Journalisation de la console {#console-logging}

La classe utilitaire [`BrowserConsole`](../advanced/browser-console.md), dédiée à la création de journaux stylés dans la console du navigateur, remplace les méthodes `consoleLog()` et `consoleError()`. Obtenez le `BrowserConsole` en utilisant la méthode `console()` :

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

Pour les versions antérieures à webforJ 25.00, la classe `App` possède les méthodes `getLocalStorage()`, `getSessionStorage()` et `getCookieStorage()` pour obtenir des instances des classes `LocalStorage`, `SessionStorage` et `CookieStorage` respectivement. À l'avenir, chaque classe a une méthode `getCurrent()`.

Consultez [Web Storage](../advanced/web-storage.md) pour plus d'informations.

### Classe `Request` {#request-class}

La classe `Request` est désormais responsable de l'obtention de l'URL, du port, de l'hôte et du protocole d'une application. Donc, au lieu d'utiliser `App.getUrl()`, utilisez `App.getCurrent().getUrl()`. La méthode `getCurrent()` remplace également la méthode `getRequest()` pour obtenir une instance de la classe `Request`.

:::info
La classe `Request` a également des méthodes supprimées, rendez-vous sur [`Request`](#request-changes) pour les voir.
:::

### Classe `Page` {#page-class}

La méthode `getPage()` est remplacée par `Page.getCurrent()` pour obtenir l'instance de la page actuelle.

### Dialogues d'options {#option-dialogs}

Au lieu d'utiliser la méthode `msgbox()`, utilisez [`OptionDialog.showMessageDialog()`](../components/option-dialogs/message) pour créer des dialogues de message.

### Terminaison de l'application {#app-termination}

La méthode `cleanup()` a été supprimée. Il y a maintenant deux méthodes pour la terminaison : `onWillTerminate()` et `onDidTerminate()`.

Consultez [Hooks for termination](../advanced/terminate-and-error-actions.md#hooks-for-termination) pour plus d'informations.

## Tri de tableau {#table-sorting}

Pour webforJ 25.00 et versions supérieures, les tableaux utilisent le tri sur une seule colonne par défaut. Les colonnes ne seront triées que par l'en-tête de colonne récemment sélectionné. Pour faire utiliser le tri multi-colonnes à un tableau, invoquez la méthode [`setMultiSorting()`](../components/table/sorting#multi-sorting) :

```java
table.setMultiSorting(true);
```

## Corps `TabbedPane` masqué {#hidden-tabbedpane-body}

La méthode `hideBody()` est remplacée par `setBodyHidden()` pour maintenir une convention de nommage cohérente pour les méthodes.

## Rendu HTML dans les composants {#rendering-html-inside-components}

Dans webforJ 25.00 et versions supérieures, il existe une méthode `setHtml()` pour aider à distinguer entre le texte littéral et le texte HTML à l'intérieur d'un composant. Il est toujours possible de définir du HTML en utilisant la méthode `setText()`, mais cela nécessite désormais de l'encapsuler explicitement avec des balises `<html>`.

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

Le package `com.webforj.component.htmlcontainer` n'est plus présent dans webforJ. Utilisez le package plus riche en fonctionnalités `com.webforj.component.element` à la place. Pour une liste des classes webforJ pour les éléments HTML standard, consultez [HTML Element Components](../building-ui/web-components/html-elements.md).

## Changements de `Request` {#request-changes}

- Tout comme la suppression de la méthode `getCookieStorage()` pour la classe `App`, la classe `Request` n'a plus la méthode `getCookie()`. Cela renforce l'utilisation de `CookieStorage.getCurrent()` pour obtenir une instance de la classe `CookieStorage`.

- La méthode `getQueryParam()` est désormais `getQueryParameter()`.

## Changements de `WebforjBBjBridge` {#webforjbbjbridge-changes}

### Obtention d'une instance de `WebforjBBjBridge` {#getting-an-instance-of-webforjbbjbridge}

La classe `Environment` n'a plus la méthode `getWebforjHelper()`, utilisez donc `getBridge()` à la place.

### Utilisation du composant `ConfirmDialog` pour la méthode `msgbox()` {#using-the-confirmdialog-component-for-the-msgbox-method}

Les versions précédentes de webforJ utilisaient directement des chaînes et des entiers pour la méthode `msgbox()` de `WebforjBBjBridge`. Cependant, les messages pour `WebforjBBjBridge` dans webforJ 25.00 et versions supérieures utilisent le composant [`ConfirmDialog`](../components/option-dialogs/confirm.md). Cela donne plus de contrôle sur les boutons affichés et le type de message.

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

## Correction de faute de frappe `PasswordMediation` {#passwordmediation-typo-correction}

La classe énumérée `PasswordMediation`, utilisée pour indiquer si un utilisateur doit se connecter à chaque visite d'une application avec un composant `Login`, contient une faute de frappe dans les versions précédentes de webforJ. `SILENT` remplace la faute de frappe `SILIENT` pour webforJ 25.00 et versions supérieures.

## Méthodes de mise au point automatique {#auto-focusing-methods}

Pour garder webforJ cohérent, des méthodes telles que `setAutofocus()` et `isAutofocus()` ont désormais une capitalisation uniforme comme l'interface HasAutoFocus. Donc, des composants comme `Dialog` et `Drawer` utilisent `setAutoFocus()` et `isAutoFocus()` pour 25.00 et versions supérieures.

## `BBjWindowAdapter` et `Panel` marqués comme `final` {#bbjwindowadapter-and-panel-marked-as-final}

Les classes `BBjWindowAdapter` et `Panel` sont désormais déclarées comme `final`, ce qui signifie qu'elles ne peuvent plus être sous-classées. Ce changement améliore la stabilité et impose des modèles d'utilisation cohérents.
