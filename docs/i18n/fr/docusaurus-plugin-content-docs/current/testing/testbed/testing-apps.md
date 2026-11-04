---
sidebar_position: 6
title: Tester des applications
description: >-
  Start a TestBed test with the routes or the whole app, navigate, test secured
  routes, answer dialogs, control timers, and answer for the browser.
_i18n_hash: 4194d4d355b0e26f86b8c3c53ea353c9
---
Une vue existe rarement de façon isolée. Une route y mène, la sécurité des routes peut la protéger, elle sollicite l’utilisateur au moyen de boîtes de dialogue, se met à jour à intervalles réguliers et demande des informations au navigateur. Dans une application en cours d’exécution, le serveur, l’utilisateur, l’horloge et le navigateur jouent chacun leur rôle. Dans un test TestBed, c’est le test qui joue tous ces rôles : il décide quelle partie de l’application s’exécute, navigue comme le ferait la barre d’adresse, répond aux boîtes de dialogue à la place de l’utilisateur, décide quand le temps passe et répond à la place du navigateur.

<!-- INTRO_END -->

## Choisir le point de départ d’un test {#choosing-what-a-test-starts-with}

Les attributs de `@WebforjTest` déterminent le point de départ de chaque test :

- **Aucun attribut.** Une page vide. Le test ajoute les composants qu’il vérifie à l’aide de `context.add()`, comme dans les tests des autres pages.
- **`routeClasses`.** Les vues `@Route` indiquées, chacune à la route définie par son annotation, et rien d’autre de l’application.
- **`routePackages`.** Toutes les vues `@Route` des packages indiqués, trouvées de la même manière que `@Routify` les trouve.
- **`app`.** La classe de l’application, démarrée comme lors d’un déploiement. Sa méthode `run()` s’exécute, son annotation `@Routify` enregistre ses routes, et ses écouteurs de cycle de vie ainsi que la sécurité des routes sont actifs. `routeClasses` et `routePackages` n’ont alors aucun effet.

Commencez avec le minimum nécessaire au test. Un test d’une seule vue enregistre cette vue, tandis qu’un test du démarrage de l’application ou des décisions de sa sécurité des routes lance l’application. `context.getApp()` renvoie l’application en cours d’exécution et `context.getFrame()` la première frame qu’elle a créée.

Un test qui configure lui-même le contexte appelle `WebforjTestbed.setup()` avec un `WebforjTestbedOptions` qui contient les mêmes choix, puis appelle `WebforjTestbed.tearDown()` à la fin. Une telle classe ne porte pas d’annotation `@WebforjTest`. Les options acceptent également une fonction qui reçoit le `RouteRegistry`, pour les enregistrements que l’annotation ne permet pas d’exprimer, par exemple une vue associée à une route différente de celle de son annotation.

## Naviguer entre les routes {#navigating-routes}

Un test accède à une vue comme le ferait un utilisateur : en passant par sa route ou en cliquant sur un élément qui déclenche une navigation. La liste suivante mène à deux produits, et la vue du produit affiche celui que sa route désigne :

```java title="ProductListView.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.router.Router;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.ParametersBag;
import java.util.List;

@Route("testbed/products")
public class ProductListView extends Composite<Div> {

  public ProductListView() {
    for (String id : List.of("41", "42")) {
      getBoundComponent()
          .add(
              new Button(
                  "Product " + id,
                  e ->
                      Router.getCurrent()
                          .navigate(
                              ProductView.class,
                              ParametersBag.of("id=" + id))));
    }
  }
}
```

```java title="ProductView.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.Route;
import com.webforj.router.event.DidEnterEvent;
import com.webforj.router.history.ParametersBag;
import com.webforj.router.observer.DidEnterObserver;

@Route("testbed/products/:id")
public class ProductView extends Composite<Div> implements DidEnterObserver {
  private final Paragraph title = new Paragraph();

  public ProductView() {
    getBoundComponent().add(title);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    title.setText("Product " + parameters.get("id").orElse(""));
  }
}
```

Le test enregistre les deux vues. Le premier test clique sur un produit de la liste, et le second navigue directement vers un produit :

```java title="ProductListViewTest.java" showLineNumbers
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.history.ParametersBag;
import com.webforj.samples.views.testbed.ProductListView;
import com.webforj.samples.views.testbed.ProductView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest(routeClasses = {ProductListView.class, ProductView.class})
class ProductListViewTest {
  @Test
  void shouldOpenTheProductTheUserClicks(WebforjTestbed context) {
    context.navigate(ProductListView.class);

    UserInteraction.click(
        ComponentLocator.of(Button.class).byText("Product 42").locate());

    assertTrue(
        ComponentLocator.of(Paragraph.class).byText("Product 42").isPresent());
  }

  @Test
  void shouldShowTheProductOfTheRoute(WebforjTestbed context) {
    var product = context.navigate(
        ProductView.class, ParametersBag.of("id=42"));

    assertTrue(
        ComponentLocator.of(Paragraph.class)
            .within(product)
            .byText("Product 42")
            .isPresent());
    assertEquals(
        "/testbed/products/42",
        context.getRouter()
            .orElseThrow()
            .getHistory()
            .getLocation()
            .orElseThrow()
            .getFullURI());
  }
}
```

`context.navigate(view)` navigue vers la route d’une vue et renvoie la vue qui a été rendue. `context.navigate(view, parameters)` renseigne les paramètres de la route, de sorte que `id=42` affiche le produit à `/testbed/products/42`. `context.navigate(location)` prend un chemin, tel que `"testbed/products/42"`, et renvoie le composant qui y a été rendu.

La navigation exécute les mêmes [observateurs du cycle de vie des routes](/docs/routing/navigation-lifecycle/observers) que dans une application en cours d’exécution ; ainsi, `onDidEnter` de la vue du produit est exécutée avant le retour de `navigate`. Pour toute autre fonctionnalité du routeur, comme une navigation qui remplace l’entrée de l’historique, appelez le routeur du test, renvoyé par `context.getRouter()`. Son historique contient également l’emplacement actuel.

## Tester les routes sécurisées {#testing-secured-routes}

Pour tester la sécurité des routes, lancez l’application, configurez l’utilisateur comme le ferait l’application, puis naviguez. Dans une application dotée d’une [implémentation de sécurité personnalisée](/docs/security/architecture/custom-implementation), le test se connecte au moyen de la méthode de connexion du gestionnaire de sécurité propre à l’application.

:::info La sécurité des routes nécessite l’application
La sécurité des routes est active lorsque le test lance l’application avec `@WebforjTest(app = ...)`. Avec `routeClasses` ou `routePackages`, les vues sont rendues sans la sécurité des routes ; un test peut donc vérifier une vue sécurisée de manière isolée, sans se connecter.
:::

Chaque test démarre avec une nouvelle session. Par conséquent, un test qui ne connecte aucun utilisateur navigue comme un visiteur anonyme.

:::tip Applications Spring Boot
Dans une application Spring Boot qui utilise Spring Security, un test configure l’utilisateur avec `@WithMockUser`, comme décrit dans [Tests Spring](/docs/integrations/spring/testing#testing-secured-routes).
:::

### Vérifier les redirections {#checking-redirects}

Lorsque la sécurité des routes refuse l’accès, elle redirige vers l’emplacement d’authentification ou de refus configuré par l’application. `navigate(String)` renvoie le composant qui y a été rendu ; le test vérifie donc la redirection en fonction du type de ce composant.

`navigate(Class)` attend la vue demandée. Si la sécurité des routes rend une autre vue à la place, la méthode échoue avec une `IllegalStateException` qui nomme les deux vues et indique d’utiliser `navigate(String)`.

Si l’accès est refusé et que l’application n’a configuré aucun emplacement d’authentification ou de refus, rien n’est rendu. Les deux méthodes `navigate` échouent alors avec un message qui nomme la route et indique que la navigation a été bloquée. Le même échec signale une navigation bloquée par un observateur du cycle de vie des routes.

## Répondre aux boîtes de dialogue {#answering-dialogs}

Une [boîte de dialogue d’options](/docs/components/option-dialogs/overview) interrompt le code qui l’a affichée jusqu’à ce que l’utilisateur réponde. Comme il n’y a pas d’utilisateur dans un test, celui-ci fournit la réponse avant que le code n’affiche la boîte de dialogue. La vue suivante demande à l’utilisateur de confirmer la suppression d’un rapport :

```java title="DeleteReport.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.optiondialog.ConfirmDialog;
import com.webforj.component.optiondialog.OptionDialog;

public class DeleteReport extends Composite<Div> {

  public DeleteReport() {
    Paragraph status = new Paragraph("report.txt");
    Button delete =
        new Button(
            "Delete",
            e -> {
              ConfirmDialog.Result answer =
                  OptionDialog.showConfirmDialog(
                      "Delete report.txt?",
                      "Delete",
                      ConfirmDialog.OptionType.OK_CANCEL);
              if (answer == ConfirmDialog.Result.OK) {
                status.setText("Deleted");
              }
            });

    getBoundComponent().add(status, delete);
  }
}
```

Le premier test répond **OK** avant de cliquer sur **Delete**, et le second répond **Cancel** :

```java title="DeleteReportTest.java" showLineNumbers
@Test
void shouldDeleteTheReportOnceTheUserConfirms(WebforjTestbed context) {
  context.add(new DeleteReport());

  context.answerConfirmDialog(ConfirmDialog.Result.OK);
  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Delete").locate());

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("Deleted").isPresent());

  var dialog = context.getShownOptionDialogs().get(0);
  assertEquals("Delete", dialog.getTitle());
  assertEquals("Delete report.txt?", dialog.getMessage());
}

@Test
void shouldKeepTheReportWhenTheUserCancels(WebforjTestbed context) {
  context.add(new DeleteReport());

  context.answerConfirmDialog(ConfirmDialog.Result.CANCEL);
  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Delete").locate());

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("report.txt").isPresent());
}
```

`context.getShownOptionDialogs()` renvoie, dans l’ordre, toutes les boîtes de dialogue affichées par l’application pendant le test, avec leur titre et leur message. Le premier test vérifie donc également le contenu de la boîte de dialogue.

Chaque type de boîte de dialogue possède sa propre méthode de réponse. La vue suivante demande un nouveau nom au moyen d’une boîte de dialogue de saisie :

```java title="RenameReport.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.optiondialog.OptionDialog;

public class RenameReport extends Composite<Div> {

  public RenameReport() {
    Paragraph name = new Paragraph("report.txt");
    Button rename =
        new Button(
            "Rename",
            e -> {
              String answer = OptionDialog.showInputDialog(
                  "New name", "Rename", name.getText());
              if (answer != null) {
                name.setText(answer);
              }
            });

    getBoundComponent().add(name, rename);
  }
}
```

Le test fournit le texte que l’utilisateur saisirait :

```java title="RenameReportTest.java" showLineNumbers
@Test
void shouldRenameTheReportToTheNameTheUserEnters(WebforjTestbed context) {
  context.add(new RenameReport());

  context.answerInputDialog("summary.txt");
  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Rename").locate());

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("summary.txt").isPresent());
}
```

`answerConfirmDialog` prend le bouton sur lequel l’utilisateur appuie, et `answerInputDialog` le texte qu’il saisit. Les boîtes de dialogue de fichiers prennent le chemin ou le nom de fichier choisi par l’utilisateur, via `answerFileChooserDialog`, `answerFileSaveDialog` et `answerFileUploadDialog`. Passer `null` à une boîte de dialogue de saisie ou de fichier revient à y répondre comme avec **Cancel**. Une boîte de dialogue de message n’a pas besoin de réponse, car l’utilisateur peut uniquement la fermer.

Les réponses sont affectées aux boîtes de dialogue de leur type dans l’ordre où le test les a fournies. Ainsi, une vue qui pose deux questions reçoit la deuxième réponse lors de la deuxième boîte de dialogue. Si une boîte de dialogue est affichée sans réponse correspondante, le test échoue avec une `IllegalStateException` qui nomme la boîte de dialogue et la méthode de réponse à appeler.

## Minuteurs {#timers}

Un test n’attend pas que le temps passe. Il décide quand le temps passe avec `context.runIntervals()`. La vue suivante s’actualise toutes les cinq secondes :

```java title="RefreshCounter.java" showLineNumbers
import com.webforj.Interval;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class RefreshCounter extends Composite<Div> {
  private int refreshes = 0;

  public RefreshCounter() {
    Paragraph status = new Paragraph("Refreshed 0 times");
    Interval interval =
        new Interval(
            5f, e -> status.setText("Refreshed " + (++refreshes) + " times"));
    interval.start();

    getBoundComponent().add(status);
  }
}
```

Chaque appel à `runIntervals()` simule l’écoulement une fois du délai de chaque [intervalle](/docs/advanced/interval) actif et renvoie le nombre d’intervalles déclenchés :

```java title="RefreshCounterTest.java" showLineNumbers
@Test
void shouldRefreshEveryTimeTheIntervalElapses(WebforjTestbed context) {
  context.add(new RefreshCounter());

  assertEquals(1, context.runIntervals());
  assertEquals(1, context.runIntervals());

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Refreshed 2 times")
          .isPresent());
}
```

Un [debouncer](/docs/advanced/debouncing) attend de la même manière. La zone de recherche suivante n’effectue la recherche que lorsque l’utilisateur cesse de saisir du texte :

```java title="SearchBox.java" showLineNumbers
import com.webforj.Debouncer;
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class SearchBox extends Composite<Div> {

  public SearchBox() {
    TextField search = new TextField("Search");
    Paragraph results = new Paragraph();
    Debouncer debouncer = new Debouncer(0.3f);
    search.onModify(
        e -> debouncer.run(
            () -> results.setText("Results for " + search.getValue())));

    getBoundComponent().add(search, results);
  }
}
```

Le test saisit du texte deux fois. Rien n’est recherché avant l’appel à `runIntervals()`. Le debouncer s’exécute alors une seule fois, avec la dernière saisie :

```java title="SearchBoxTest.java" showLineNumbers
@Test
void shouldSearchOnlyForTheLastInput(WebforjTestbed context) {
  context.add(new SearchBox());

  var search = ComponentLocator.of(TextField.class).byLabel("Search").locate();
  var results = ComponentLocator.of(Paragraph.class).locate();

  UserInteraction.type(search, "jav");
  UserInteraction.type(search, "java");

  assertEquals("", results.getText());

  context.runIntervals();

  assertEquals("Results for java", results.getText());
}
```

[`Environment.runLater`](/docs/advanced/asynchronous-updates) ne nécessite aucun appel particulier. Appelée depuis le test ou depuis le code qu’il exécute, cette méthode lance la tâche avant de renvoyer le résultat, et son `PendingResult` est déjà terminé.

## Répondre au navigateur {#answering-the-browser}

Certains codes demandent une information au navigateur, par exemple la largeur de la fenêtre. Comme il n’y a pas de navigateur dans un test, celui-ci répond à sa place. La vue suivante affiche la largeur renvoyée par le navigateur :

```java title="WindowWidth.java" showLineNumbers
import com.webforj.Page;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class WindowWidth extends Composite<Div> {

  public WindowWidth() {
    Paragraph width = new Paragraph();
    Button measure =
        new Button(
            "Measure",
            e -> width.setText(
                "Width: " + Page.getCurrent().executeJs("window.innerWidth")));

    getBoundComponent().add(measure, width);
  }
}
```

`answerExecuteJs` fournit le résultat du prochain appel à `executeJs`. Le test le fournit donc avant le clic :

```java title="WindowWidthTest.java" showLineNumbers
@Test
void shouldShowTheWidthTheBrowserReports(WebforjTestbed context) {
  context.add(new WindowWidth());

  context.answerExecuteJs(1280);
  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Measure").locate());

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("Width: 1280").isPresent());
}
```

`executeJs` renvoie `null` si le test n’a fourni aucun résultat. Le code qui n’attend pas le navigateur utilise `executeJsAsync`, qui se termine plus tard. Le bouton suivant copie le lien de la page et confirme l’opération une fois le navigateur terminé :

```java title="CopyLink.java" showLineNumbers
import com.webforj.Page;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class CopyLink extends Composite<Div> {

  public CopyLink() {
    Paragraph status = new Paragraph();
    Button copy =
        new Button(
            "Copy link",
            e ->
                Page.getCurrent()
                    .executeJsAsync(
                        "navigator.clipboard.writeText(location.href)")
                    .thenAccept(result -> status.setText("Link copied")));

    getBoundComponent().add(copy, status);
  }
}
```

Le script démarre au clic. Le test fournit donc sa réponse après le clic avec `answerExecuteJsAsync`, qui termine le script en attente le plus ancien :

```java title="CopyLinkTest.java" showLineNumbers
@Test
void shouldConfirmOnceTheBrowserCopiedTheLink(WebforjTestbed context) {
  context.add(new CopyLink());

  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Copy link").locate());
  context.answerExecuteJsAsync(null);

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("Link copied").isPresent());
}
```

`answerExecuteJsAsync` échoue avec une `IllegalStateException` si aucun script n’attend de résultat. Un `Element` attend que le navigateur le définisse. Ainsi, `whenDefined()` et les appels qu’il met en file d’attente, tels que `callJsFunctionAsync`, attendent que le test déclenche un `ElementDefinedEvent` pour l’élément avec `UserInteraction.fireEvent`.

La référence de <JavadocLink type="testbed-foundation" location="com/webforj/testbed/WebforjTestbed" code="true">WebforjTestbed</JavadocLink> répertorie toutes les méthodes du contexte de test.
