---
title: Terminate and Error Actions
sidebar_position: 40
_i18n_hash: 1a250a51020b32c8b3471ae75ea8f750
---
<!-- vale off -->
# Terminer et actions d'erreur <DocChip chip='since' label='23.06' />
<!-- vale on -->

Lors du développement d'applications avec webforJ, il est essentiel de définir le comportement de votre application lorsqu'elle se termine ou rencontre une erreur. Le framework fournit des mécanismes pour personnaliser ces comportements via des actions `terminate` et `error`.

## Aperçu {#overview}

La classe `App` vous permet de définir des actions qui s'exécutent lorsque l'application se termine normalement ou lorsqu'elle rencontre une erreur. Ces actions sont des instances de l'interface `AppCloseAction` et peuvent être définies à l'aide de :

- `setTerminateAction(AppCloseAction action)`: Définit l'action à exécuter lors de la terminaison normale.
- `setErrorAction(AppCloseAction action)`: Définit l'action à exécuter lorsqu'une erreur se produit.

Les implémentations disponibles de `AppCloseAction` incluent :

- `DefaultAction`: Efface le navigateur et affiche un message localisé invitant l'utilisateur à recharger l'application.
- `NoneAction`: N'effectue aucune action, réinitialisant effectivement toute action précédemment définie.
- `MessageAction`: Affiche un message de lien personnalisé.
- `RedirectAction`: Redirige l'utilisateur vers une URL spécifiée.

:::info Distinction entre les actions de terminaison et d'erreur dans webforJ
webforJ ne considère pas la terminaison due à une exception levée ou non gérée comme une action d'erreur, mais plutôt comme une action de terminaison, car l'application se ferme normalement. Une action d'erreur se produit lorsque l'application reçoit un signal de terminaison en raison d'une erreur externe, comme lorsque le navigateur ne peut pas se connecter au serveur exécutant l'application.
:::

## Comportement par défaut {#default-behavior}

Dans la version `24.11` de webforJ et les versions antérieures, l'application utilise par défaut `DefaultAction` pour les événements de terminaison et d'erreur. Cela signifie que lorsque l'application se termine ou rencontre une erreur, le navigateur affiche un message invitant l'utilisateur à recharger l'application.

À partir de la version `24.12`, webforJ utilise par défaut `NoneAction` pour les événements de terminaison et d'erreur. Ce changement signifie qu'aucune action n'est effectuée lorsque l'application se termine ou qu'une erreur se produit, permettant à webforJ de déléguer la gestion des erreurs à un `ErrorHandler` approprié s'il est configuré, ou de se replier sur ses mécanismes de gestion des erreurs génériques. En utilisant `NoneAction`, l'application évite de perturber le flux de gestion des erreurs par défaut, vous permettant de définir des gestionnaires d'erreurs personnalisés ou de vous fier à la gestion des erreurs intégrée de webforJ.

## Personnalisation des actions {#customizing-actions}

Pour changer le comportement par défaut, utilisez les méthodes `setTerminateAction()` et `setErrorAction()` dans votre sous-classe `App`.

### Définition d'une action de message personnalisée {#setting-a-custom-message-action}

Si vous souhaitez afficher un message personnalisé lors de la terminaison normale :

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Définir une action de message personnalisée
    setTerminateAction(new MessageAction(
        "Merci d'avoir utilisé notre application ! Cliquez pour recharger"
    ));
  }
}
```

### Définir une action de redirection {#setting-a-redirect-action}

Pour rediriger l'utilisateur vers une URL spécifique lors de la terminaison normale :

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Définir une action de redirection en cas d'erreur
    setTerminateAction(new RedirectAction(
        "https://example.com/error"
    ));
  }
}
```

## Terminer l'application {#terminating-the-app}

Vous pouvez terminer programmément votre application en appelant la méthode `terminate()` :

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Terminer l'application sous certaines conditions
    if (someCondition) {
      terminate();
    }
  }
}
```

Lors de l'appel de `terminate()`, l'action définie par `setTerminateAction()` s'exécute.

## Hooks pour la terminaison {#hooks-for-termination}

La classe `App` fournit des méthodes d'accroche pour effectuer des actions avant et après la terminaison :

- `onWillTerminate()`: Appelée avant la terminaison.
- `onDidTerminate()`: Appelée après la terminaison.

```java
public class MyApp extends App {

  @Override
  protected void onWillTerminate() {
    // Effectuer des tâches de nettoyage
  }

  @Override
  protected void onDidTerminate() {
    // Actions après la terminaison
  }
}
```

:::tip Écouteurs de cycle de vie externes
Pour une gestion du cycle de vie plus avancée, envisagez d'utiliser `AppLifecycleListener` pour gérer les événements de terminaison provenant de composants externes sans modifier la classe `App`. Cela est particulièrement utile pour les architectures de plugins ou lorsque plusieurs composants doivent répondre à la terminaison de l'application. En savoir plus sur [les écouteurs de cycle de vie](lifecycle-listeners.md).
:::

### Page de terminaison personnalisée {#custom-termination-page}

Dans certains cas, vous souhaiterez peut-être afficher une page de terminaison personnalisée lorsque votre application se termine, fournissant aux utilisateurs un message personnalisé ou des ressources supplémentaires. Cela peut être réalisé en surchargeant la méthode `onDidTerminate()` dans votre sous-classe `App` et en injectant du HTML personnalisé dans la page.

Voici un exemple de création d'une page de terminaison personnalisée :

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    setTerminateAction(new NoneAction());
    terminate();
  }

  @Override
  protected void onDidTerminate() {
    String html = """
    <div style="display: flex; justify-content: center; align-items: center; height: 100vh; flex-direction: column;">
        <h1>Merci d'avoir utilisé webforJ</h1>
        <p>Pour plus d'informations, visitez <a href="https://webforj.com">webforj.com</a></p>
    </div>
    """;

    Page.getCurrent().executeJsVoidAsync(
        String.format("document.body.innerHTML = `%s`", html)
    );
  }
}
```
