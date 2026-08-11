---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
sidebar_position: 30
_i18n_hash: 6fdaf15e67e0015f7319572200ccc353
---
Cette documentation sert de guide pour mettre à niveau les applications webforJ de la version 24.00 à 25.00. Voici les changements nécessaires pour que les applications existantes continuent de fonctionner sans problème. Comme toujours, consultez [l'aperçu des versions sur GitHub](https://github.com/webforj/webforj/releases) pour une liste plus complète des changements entre les versions.

## Serveurs web Jetty 12 {#jetty-12-web-servers}

webforJ 25.00 et supérieur utilise Jetty 12, utilisant l'architecture de servlet Jakarta EE10. Si vous utilisez le plugin Maven Jetty pour le développement, migrez de Jakarta EE8 à Jakarta EE10. Cette mise à niveau nécessitera également de remplacer tout ce qui dépendait du paquet `javax.servlet` par le paquet `Jakarta.servlet`.

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

Plusieurs méthodes obsolètes de la classe `App` ont été supprimées dans la version 25.00. Les sections suivantes décrivent quelles méthodes ont été remplacées et les remplacements recommandés.

### Journalisation dans la console {#console-logging}

La classe utilitaire [`BrowserConsole`](/docs/advanced/browser-console), dédiée à la création de journaux stylisés dans la console du navigateur, remplace les méthodes `consoleLog()` et `consoleError()`. Obtenez le `BrowserConsole` en utilisant la méthode `console()` :

```java
public class Application extends App{

  @Override
  public void run() throws WebforjException {
    console().log("Message de journal");
    console().error("Message d'erreur");
  }
}
```

### Stockage Web {#web-storage}

Pour les versions antérieures à webforJ 25.00, la classe `App` disposait des méthodes `getLocalStorage()`, `getSessionStorage()` et `getCookieStorage()` pour obtenir des instances des classes `LocalStorage`, `SessionStorage` et `CookieStorage` respectivement. À partir de maintenant, chaque classe dispose d'une méthode `getCurrent()`.

Consultez [Web Storage](/docs/advanced/web-storage) pour plus d'informations.

### Classe `Request` {#request-class}

La classe `Request` est désormais responsable de l'obtention de l'URL, du port, de l'hôte et du protocole de l'application. Donc, au lieu d'utiliser `App.getUrl()`, utilisez `App.getCurrent().getUrl()`. La méthode `getCurrent()` remplace également la méthode `getRequest()` pour obtenir une instance de la classe `Request`.

:::info
La classe `Request` a également supprimé des méthodes, allez à [`Request`](#request-changes) pour les voir.
:::

### Classe `Page` {#page-class}

La méthode `getPage()` est remplacée par `Page.getCurrent()` pour obtenir l'instance de la page actuelle.

### Dialogues d'options {#option-dialogs}

Au lieu d'utiliser la méthode `msgbox()`, utilisez [`OptionDialog.showMessageDialog()`](/docs/components/option-dialogs/message) pour créer des dialogues de message.

### Terminaison de l'application {#app-termination}

La méthode `cleanup()` a été supprimée. Il y a maintenant deux méthodes pour la terminaison, `onWillTerminate()` et `onDidTerminate()`.

Consultez [Hooks for termination](/docs/advanced/terminate-and-error-actions#hooks-for-termination) pour plus d'informations.

## Tri des tableaux {#table-sorting}

Pour webforJ 25.00 et supérieur, les tableaux utilisent un tri par colonne unique par défaut. Les colonnes ne seront triées que par l'en-tête de colonne récemment sélectionné. Pour faire en sorte qu'un tableau utilise le tri par plusieurs colonnes, invoquez la méthode [`setMultiSorting()`](/docs/components/table/sorting#multi-sorting) :

```java
table.setMultiSorting(true);
```

## Corps de `TabbedPane` caché {#hidden-tabbedpane-body}

La méthode `hideBody()` est remplacée par `setBodyHidden()` pour maintenir une convention de nommage constante pour les méthodes.

## Rendu HTML à l'intérieur des composants {#rendering-html-inside-components}

Dans webforJ 25.00 et supérieur, il existe une méthode `setHtml()` pour aider à faire la distinction entre le texte littéral et le texte HTML à l'intérieur d'un composant. Il est toujours possible de définir du HTML en utilisant la méthode `setText()`, mais cela nécessite désormais de l'encapsuler explicitement avec des balises `<html>`.

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

Le paquet `com.webforj.component.htmlcontainer` n'est plus dans webforJ. Utilisez plutôt le paquet `com.webforj.component.element`, qui offre plus de fonctionnalités. Pour une liste des classes webforJ pour les éléments HTML standard, allez à [HTML Element Components](/docs/components/html-elements).

## Changements dans `Request` {#request-changes}

- Tout comme la suppression de la méthode `getCookieStorage()` pour la classe `App`, `Request` n'a plus la méthode `getCookie()`. Cela renforce l'utilisation de `CookieStorgage.getCurrent()` pour obtenir une instance de la classe `CookieStorage`.

- La méthode `getQueryParam()` est désormais `getQueryParameter()`.

## Changements dans `WebforjBBjBridge` {#webforjbbjbridge-changes}

### Obtention d'une instance de `WebforjBBjBridge` {#getting-an-instance-of-webforjbbjbridge}

La classe `Environment` n'a plus la méthode `getWebforjHelper()`, utilisez donc `getBridge()` à la place.

### Utilisation du composant `ConfirmDialog` pour la méthode `msgbox()` {#using-the-confirmdialog-component-for-the-msgbox-method}

Les versions précédentes de webforJ utilisaient des chaînes de caractères et des entiers directement pour la méthode `msgbox()` de `WebforjBBjBridge`. Cependant, les messages pour `WebforjBBjBridge` dans webforJ 25.00 et supérieur utilisent le composant [`ConfirmDialog`](/docs/components/option-dialogs/confirm). Cela offre plus de contrôle sur les boutons affichés et le type de message.

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

## Correction de faute de frappe dans `PasswordMediation` {#passwordmediation-typo-correction}

La classe énumérée `PasswordMediation`, utilisée pour indiquer si un utilisateur doit se connecter à chaque visite d'une application avec un composant `Login`, contient une faute de frappe dans les versions précédentes de webforJ. `SILENT` remplace la faute de frappe `SILIENT` pour webforJ 25.00 et supérieur.

## Méthodes de mise au point automatique {#auto-focusing-methods}

Pour garder webforJ cohérent, des méthodes comme `setAutofocus()` et `isAutofocus()` ont désormais une capitalisation uniforme comme l'interface HasAutoFocus. Ainsi, des composants comme `Dialog` et `Drawer` utilisent `setAutoFocus()` et `isAutoFocus()` pour 25.00 et supérieur.

## `BBjWindowAdapter` et `Panel` marqués comme `final` {#bbjwindowadapter-and-panel-marked-as-final}

Les classes `BBjWindowAdapter` et `Panel` sont désormais déclarées comme `final`, ce qui signifie qu'elles ne peuvent plus être sous-classées. Ce changement améliore la stabilité et impose des modèles d'utilisation cohérents.
