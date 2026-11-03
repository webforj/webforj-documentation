---
sidebar_position: 3
title: Interaction utilisateur
description: >-
  Act on the components of a TestBed test through the same gestures a user
  makes, and get the same events a user causes.
_i18n_hash: 1abb4d1f79dc5eb1556e58df714dfac7
---
Votre code peut appeler `setValue` ou `setEnabled` sur n’importe quel composant, mais pas un utilisateur. Un utilisateur saisit du texte dans un champ visible et clique sur un bouton activé. Un test TestBed agit comme le ferait l’utilisateur, par l’intermédiaire de `UserInteraction`. Chacun de ses verbes vérifie que l’utilisateur pourrait effectuer le geste, l’effectue et permet au composant de le signaler à ses écouteurs, comme il le ferait si l’utilisateur agissait dans le navigateur.

Chaque verbe se termine avant l’exécution de la ligne suivante du test, qui vérifie donc le résultat juste après le geste.

<!-- INTRO_END -->

## Agir comme un utilisateur {#acting-like-a-user}

Le formulaire suivant permet de s’abonner à une newsletter avec une adresse e-mail. Son bouton **Subscribe** reste désactivé tant que le champ ne contient pas une adresse :

```java title="NewsletterForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class NewsletterForm extends Composite<Div> {

  public NewsletterForm() {
    TextField email = new TextField("Email");
    Paragraph status = new Paragraph();
    Button subscribe =
        new Button(
            "Subscribe", e -> status.setText("Subscribed " + email.getValue()));
    subscribe.setEnabled(false);
    email.onModify(e -> subscribe.setEnabled(!email.getValue().isBlank()));

    getBoundComponent().add(email, subscribe, status);
  }
}
```

Le test saisit une adresse et clique sur **Subscribe**, les deux gestes qu’effectuerait un utilisateur :

```java title="NewsletterFormTest.java" showLineNumbers
@Test
void shouldSubscribeTheEmailTheUserTyped(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
  var subscribe = ComponentLocator.of(Button.class)
      .byText("Subscribe")
      .locate();

  UserInteraction.type(email, "ada@example.com");
  UserInteraction.click(subscribe);

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Subscribed ada@example.com")
          .isPresent());
}
```

La saisie signale la modification à l’écouteur de modification du champ, qui active le bouton. Le clic qui suit atteint l’écouteur du bouton, qui affiche la confirmation.

## Verbes {#verbs}

`UserInteraction` propose un verbe pour chacun des gestes partagés par de nombreux composants :

- `click` et `doubleClick` cliquent sur un composant ou sur un élément qu’il contient, comme une ligne de tableau ou un nœud d’arbre.
- `type` remplace le texte d’un champ, `keypress` y appuie sur une touche et `focus` place le focus sur un composant.
- `check` et `uncheck` activent ou désactivent une case à cocher, un bouton radio ou un interrupteur.
- `select`, `selectIndex`, `selectKey` et `deselect` modifient la sélection des listes, tableaux, arbres et panneaux à onglets.
- `open`, `close` et `cancel` ouvrent et ferment des panneaux, boîtes de dialogue, tiroirs et menus déroulants, et annulent une connexion ou un téléversement.

Un verbe signale les événements comme le ferait le geste d’un utilisateur, dans le même ordre. Lors de la sélection d’un onglet, le désélectionnement de l’onglet précédent est signalé avant la sélection du nouvel onglet. Le bouton de la souris, sa position et les touches de modification d’un geste sont définis dans `MouseOptions` et `KeyOptions`, les derniers arguments de `click`, `doubleClick` et `keypress`.

La référence <JavadocLink type="automation" location="com/webforj/automation/act/UserInteraction" code="true">UserInteraction</JavadocLink> répertorie les composants qui répondent à chaque verbe.

## Les clics atteignent les composants environnants {#clicks-reach-the-components-around}

Dans le navigateur, un clic atteint le composant cliqué, puis tous les composants qui l’entourent. Un verbe se comporte de la même manière. Dans la carte suivante, seule la carte écoute les clics, pas l’avatar qu’elle contient :

```java title="MemberCard.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class MemberCard extends Composite<Div> {

  public MemberCard() {
    Paragraph profile = new Paragraph();
    Card card = new Card(new Avatar("Ada Lovelace"));
    card.onClick(e -> profile.setText("Profile of Ada Lovelace"));

    getBoundComponent().add(card, profile);
  }
}
```

Le test clique sur l’avatar, comme le ferait un utilisateur :

```java title="MemberCardTest.java" showLineNumbers
@Test
void shouldOpenTheProfileWhenTheUserClicksTheAvatar(WebforjTestbed context) {
  context.add(new MemberCard());

  UserInteraction.click(ComponentLocator.of(Avatar.class).locate());

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Profile of Ada Lovelace")
          .isPresent());
}
```

Le clic atteint l’écouteur de la carte qui entoure l’avatar, lequel affiche le profil.

## Ce qu’un utilisateur ne peut pas faire {#what-a-user-cant-do}

Un verbe ne s’exécute que si l’utilisateur pourrait effectuer le geste. Tant qu’aucune adresse n’est saisie, le bouton **Subscribe** du [formulaire d’inscription](#acting-like-a-user) est désactivé. Le test ne peut donc pas non plus cliquer dessus :

```java title="NewsletterFormTest.java" showLineNumbers
@Test
void shouldRejectTheClickWhileNoEmailIsTyped(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var subscribe = ComponentLocator.of(Button.class)
      .byText("Subscribe")
      .locate();

  var failure =
      assertThrows(
          InteractionRejectedException.class,
          () -> UserInteraction.click(subscribe));

  assertEquals(
      """
      The component Button is disabled and a user cannot interact with it.
      - Frame:
        - NewsletterForm [composite=Div] [node=div]:
          - TextField [label=Email]
          - Button "Subscribe" [disabled]
          - Paragraph [composite=Element] [node=p]
      """,
      failure.getMessage());
  assertFalse(
      ComponentLocator.of(Paragraph.class)
          .byTextContaining("Subscribed")
          .isPresent());
}
```

Le clic lève une `InteractionRejectedException`, et l’écouteur du bouton ne s’exécute jamais. Son message, vérifié par le test, indique le composant et la raison du rejet, puis affiche l’arbre des composants, ce qui permet de voir le contenu de la page en cas d’échec du test. Un geste est rejeté si le composant, ou l’un de ses composants environnants, est désactivé ou masqué, ou se trouve dans une boîte de dialogue ou un tiroir fermé. La saisie dans un champ en lecture seule est rejetée, tout comme un geste que le composant n’autorise pas, par exemple la fermeture d’une alerte sans bouton de fermeture.

:::tip Composants non attachés
Un composant non attaché échoue à chaque verbe en levant une `IllegalStateException` qui vous demande de l’ajouter d’abord au contexte.
:::

## Gestes propres à un composant {#gestures-of-one-component}

Certains gestes sont propres à un composant, comme déplacer un curseur, trier un tableau ou développer un nœud d’arbre. Chaque composant webforJ qui prend en charge de tels gestes possède une classe d’interaction. `UserInteraction.perform` exécute l’un de ces gestes après les mêmes vérifications que les verbes. Le contrôle suivant affiche le volume défini par son curseur :

```java title="VolumeControl.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.slider.Slider;

public class VolumeControl extends Composite<Div> {

  public VolumeControl() {
    Paragraph volume = new Paragraph("Volume 50");
    Slider slider = new Slider(50, 0, 100);
    slider.onSlide(e -> volume.setText("Volume " + e.getValue()));

    getBoundComponent().add(slider, volume);
  }
}
```

Le test règle le curseur sur 80, puis essaie de le régler sur 150, une valeur en dehors de sa plage :

```java title="VolumeControlTest.java" showLineNumbers
@Test
void shouldShowTheVolumeTheUserSlidTo(WebforjTestbed context) {
  context.add(new VolumeControl());

  var slider = ComponentLocator.of(Slider.class).locate();

  UserInteraction.perform(slider, SliderInteraction.slide(80));

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("Volume 80").isPresent());

  var failure =
      assertThrows(
          InteractionRejectedException.class,
          () -> UserInteraction.perform(slider, SliderInteraction.slide(150)));

  assertEquals(
      """
      The component Slider ranges from 0 to 100 and a user cannot slide it to 150.
      - Frame:
        - VolumeControl [composite=Div] [node=div]:
          - Slider [min=0] [max=100]
          - Paragraph [composite=Element] [node=p]
      """,
      failure.getMessage());
}
```

Le réglage sur 80 atteint l’écouteur de déplacement, qui affiche le nouveau volume. Le réglage sur 150 est rejeté comme le serait un verbe, car un utilisateur ne peut pas faire glisser le curseur au-delà de sa limite. Le message indique la plage du curseur et la valeur demandée par le test.

Chaque composant webforJ doté de ses propres gestes possède une classe d’interaction nommée d’après le composant, par exemple `SliderInteraction` pour `Slider`. Chaque méthode statique de cette classe correspond à un geste. Pour connaître les gestes proposés par un composant, consultez le Javadoc de sa classe d’interaction : par exemple <JavadocLink type="automation" location="com/webforj/automation/act/component/slider/SliderInteraction" code="true">SliderInteraction</JavadocLink> avec `slide`, <JavadocLink type="automation" location="com/webforj/automation/act/component/table/TableInteraction" code="true">TableInteraction</JavadocLink> avec `sort`, `moveColumn` et `resizeColumn`, ou <JavadocLink type="automation" location="com/webforj/automation/act/component/tree/TreeInteraction" code="true">TreeInteraction</JavadocLink> avec `expand` et `collapse`. La <JavadocLink type="automation" location="index">référence webforj-automation</JavadocLink> les répertorie tous, dans un package par composant sous `com.webforj.automation.act.component`.

## Téléversements et téléchargements {#uploads-and-downloads}

Comme un utilisateur téléverse des fichiers depuis sa propre machine, le test doit d’abord les transmettre au contexte. Le téléversement suivant affiche les noms des fichiers reçus :

```java title="PhotoUpload.java" showLineNumbers
import com.webforj.UploadedFile;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.upload.Upload;
import java.util.stream.Collectors;

public class PhotoUpload extends Composite<Div> {

  public PhotoUpload() {
    Paragraph received = new Paragraph();
    Upload upload = new Upload();
    upload.onUpload(
        e ->
            received.setText(
                e.getFiles().stream()
                    .map(UploadedFile::getClientName)
                    .collect(Collectors.joining(", "))));

    getBoundComponent().add(upload, received);
  }
}
```

Le test choisit deux fichiers avec `pickFile`, puis les téléverse en indiquant leurs noms :

```java title="PhotoUploadTest.java" showLineNumbers
@Test
void shouldReceiveThePhotosTheUserUploaded(WebforjTestbed context) {
  context.add(new PhotoUpload());

  var upload = ComponentLocator.of(Upload.class).locate();

  context.pickFile("beach.jpg", "beach".getBytes(StandardCharsets.UTF_8));
  context.pickFile("sunset.png", "sunset".getBytes(StandardCharsets.UTF_8));
  UserInteraction.perform(
      upload, UploadInteraction.upload("beach.jpg", "sunset.png"));

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("beach.jpg, sunset.png")
          .isPresent());
}
```

Un fichier qui dépasse le nombre maximal de fichiers ou la taille maximale, ou dont le type n’est pas accepté par le téléversement, est signalé aux écouteurs de rejet et n’est pas téléversé.

Les fichiers que votre application envoie à l’utilisateur sont conservés par le contexte. Le bouton suivant envoie un rapport :

```java title="ReportDownload.java" showLineNumbers
import com.webforj.Page;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import java.nio.charset.StandardCharsets;

public class ReportDownload extends Composite<Div> {

  public ReportDownload() {
    Button download =
        new Button(
            "Download report",
            e ->
                Page.getCurrent()
                    .download(
                        "Orders: 42".getBytes(StandardCharsets.UTF_8),
                        "report.txt"));

    getBoundComponent().add(download);
  }
}
```

Le test clique sur le bouton, puis lit le fichier reçu par l’utilisateur avec `getDownloads()` :

```java title="ReportDownloadTest.java" showLineNumbers
@Test
void shouldSendTheReportWhenTheUserClicksDownload(WebforjTestbed context) {
  context.add(new ReportDownload());

  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Download report").locate());

  var report = context.getDownloads().get(0);
  assertEquals("report.txt", report.getName());
  assertEquals(
      "Orders: 42", new String(report.getContents(), StandardCharsets.UTF_8));
}
```

## Événements sans geste {#events-without-a-gesture}

Certains événements atteignent un composant à la suite d’un geste, mais proviennent du navigateur plutôt que de l’utilisateur. C’est le cas, par exemple, du signal indiquant qu’un tiroir a fini de se fermer. La vue suivante indique si son tiroir d’aide est ouvert :

```java title="HelpDrawer.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class HelpDrawer extends Composite<Div> {

  public HelpDrawer() {
    Paragraph status = new Paragraph("Help is open");
    Drawer drawer = new Drawer("Help");
    drawer.onClose(e -> status.setText("Help is closed"));
    drawer.open();

    getBoundComponent().add(drawer, status);
  }
}
```

`close` ferme le tiroir, puis le test déclenche l’événement de fermeture signalé par le navigateur avec `UserInteraction.fireEvent` :

```java title="HelpDrawerTest.java" showLineNumbers
@Test
void shouldReportTheClosedHelp(WebforjTestbed context) {
  context.add(new HelpDrawer());

  var drawer = ComponentLocator.of(Drawer.class).locate();

  UserInteraction.close(drawer);
  UserInteraction.fireEvent(new DrawerCloseEvent(drawer, Map.of()));

  assertFalse(drawer.isOpened());
  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Help is closed")
          .isPresent());
}
```

`fireEvent` transmet l’événement aux écouteurs du composant et vérifie uniquement que le composant est attaché, puisque l’événement ne provient pas de l’utilisateur. Les événements d’ouverture et de fermeture des boîtes de dialogue, des tiroirs et du tiroir d’une disposition d’application, ainsi que les événements de progression d’un téléversement, sont de ce type. Un `Element` écoute les événements par leur nom, et `UserInteraction.fireEvent(element, eventName, data)` transmet un événement aux écouteurs correspondant à ce nom.

## Gestes personnalisés {#gestures-of-your-own}

`perform` accepte n’importe quel `Consumer` d’un composant, ce qui permet à un test de définir son propre geste. Le test suivant s’abonne avec le [formulaire d’inscription](#acting-like-a-user) en utilisant un geste qui saisit du texte et clique dans le formulaire reçu :

```java title="NewsletterFormTest.java" showLineNumbers
@Test
void shouldSubscribeThroughTheGestureOfTheTest(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var form = ComponentLocator.of(NewsletterForm.class).locate();

  UserInteraction.perform(form, subscribe("ada@example.com"));

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Subscribed ada@example.com")
          .isPresent());
}

private static Consumer<NewsletterForm> subscribe(String address) {
  return form -> {
    UserInteraction.type(
        ComponentLocator.of(TextField.class).within(form).locate(), address);
    UserInteraction.click(
        ComponentLocator.of(Button.class)
            .within(form)
            .byText("Subscribe")
            .locate());
  };
}
```

Le test décrit le geste d’un utilisateur, et d’autres tests peuvent réutiliser ce même geste.

## Vos propres composants {#your-own-components}

Un composant de votre application répond aux verbes correspondant aux [interfaces de fonctionnalité](/docs/building-ui/component-fundamentals#concern-interfaces) qu’il implémente, sans code supplémentaire. Un composant dont la valeur est un `Boolean`, via `HasValue`, répond à `check`, `uncheck` et `click` comme le ferait une case à cocher. L’interrupteur suivant indique si un élément est un favori :

```java title="FavoriteToggle.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Span;
import com.webforj.concern.HasValue;
import com.webforj.data.event.ValueChangeEvent;
import com.webforj.dispatcher.EventDispatcher;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

public class FavoriteToggle
    extends Composite<Span> implements HasValue<FavoriteToggle, Boolean> {
  private final EventDispatcher dispatcher = new EventDispatcher();
  private boolean favorite = false;

  public FavoriteToggle() {
    getBoundComponent().setText("Add to favorites");
  }

  @Override
  public Boolean getValue() {
    return favorite;
  }

  @Override
  public FavoriteToggle setValue(Boolean value) {
    favorite = value;
    getBoundComponent()
        .setText(favorite ? "Remove from favorites" : "Add to favorites");
    dispatcher.dispatchEvent(new ValueChangeEvent<>(this, value));

    return this;
  }

  @Override
  public ListenerRegistration<ValueChangeEvent<Boolean>> addValueChangeListener(
      EventListener<ValueChangeEvent<Boolean>> listener) {
    return dispatcher.addListener(ValueChangeEvent.class, listener);
  }
}
```

Le test active l’interrupteur, puis clique dessus :

```java title="FavoriteToggleTest.java" showLineNumbers
@Test
void shouldToggleTheFavoriteLikeACheckbox(WebforjTestbed context) {
  context.add(new FavoriteToggle());

  var toggle = ComponentLocator.of(FavoriteToggle.class).locate();
  var label = ComponentLocator.of(Span.class).locate();

  UserInteraction.check(toggle);
  assertEquals("Remove from favorites", label.getText());

  UserInteraction.click(toggle);
  assertEquals("Add to favorites", label.getText());
}
```

De même, un composant qui implémente `HasReadOnly` avec `HasValue` ou `HasText` répond à `type`, et un composant qui implémente les interfaces de sélection de webforJ répond à `select`.

### Apprendre les verbes à un composant {#teaching-a-component-the-verbs}

Lorsqu’aucune interface de fonctionnalité ne convient, une classe d’interaction définit le comportement d’un verbe pour votre composant. Le composant d’extension suivant affiche ses détails une fois développé :

```java title="Expander.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class Expander extends Composite<Div> {
  private final Paragraph details = new Paragraph("Ships in 2 days");

  public Expander() {
    details.setVisible(false);
    getBoundComponent().add(new Paragraph("Shipping"), details);
  }

  public void expand() {
    details.setVisible(true);
  }

  public void collapse() {
    details.setVisible(false);
  }

  public boolean isExpanded() {
    return details.isVisible();
  }
}
```

La classe d’interaction indique la classe du composant dans son constructeur et implémente une interface pour chaque verbe pris en charge — ici `OpenInteraction` et `CloseInteraction`. `reject` crée l’exception associée à un geste que le composant n’autorise pas :

```java title="ExpanderInteraction.java" showLineNumbers
import com.webforj.automation.act.AbstractComponentInteraction;
import com.webforj.automation.act.CloseInteraction;
import com.webforj.automation.act.OpenInteraction;
import com.webforj.samples.views.testbed.Expander;

public final class ExpanderInteraction
    extends AbstractComponentInteraction<Expander>
    implements OpenInteraction<Expander>, CloseInteraction<Expander> {

  public ExpanderInteraction() {
    super(Expander.class);
  }

  @Override
  public void open(Expander expander) {
    expander.expand();
  }

  @Override
  public void close(Expander expander) {
    if (!expander.isExpanded()) {
      throw reject(expander, "is already collapsed");
    }

    expander.collapse();
  }
}
```

Le nom pleinement qualifié de la classe est inscrit dans un fichier des ressources de test :

```text title="src/test/resources/META-INF/services/com.webforj.automation.act.ComponentInteraction"
com.webforj.samples.unit.views.testbed.ExpanderInteraction
```

Le test ouvre et ferme ensuite le composant avec les verbes. La deuxième tentative de fermeture est rejetée avec la raison indiquée par la classe :

```java title="ExpanderTest.java" showLineNumbers
@Test
void shouldOpenAndCloseTheDetailsThroughTheVerbs(WebforjTestbed context) {
  context.add(new Expander());

  var expander = ComponentLocator.of(Expander.class).locate();
  var details = ComponentLocator.of(Paragraph.class)
      .byText("Ships in 2 days")
      .locate();

  UserInteraction.open(expander);
  verifyThat(details).isVisible();

  UserInteraction.close(expander);
  verifyThat(details).isHidden();

  var failure =
      assertThrows(
          InteractionRejectedException.class,
          () -> UserInteraction.close(expander));

  assertEquals(
      """
      The component Expander is already collapsed.
      - Frame:
        - Expander [composite=Div] [node=div]:
          - Paragraph [composite=Element] [node=p]
      """,
      failure.getMessage());
}
```

Une interaction de votre application répond à un verbe avant celle fournie par webforJ pour le même composant. Si deux interactions de votre application répondent au même verbe pour le même composant, le test échoue avec un message qui nomme les deux interactions. Pour ne répondre que pour certaines instances d’une classe, redéfinissez `isSupported(component)`.
