---
sidebar_position: 2
title: Trouver des composants
description: >-
  Describe the components a TestBed test acts on the way a user picks them out
  on the page, by type, text, label and what they contain.
_i18n_hash: 3e31c9bc7b51ebd2ebaf1a5e44c6e94f
---
Avant qu’un utilisateur clique sur un bouton, il le repère sur la page : le bouton **Save** ou le champ associé à l’adresse e-mail. Un test TestBed repère les composants de la même façon, avec `ComponentLocator`. Un localisateur décrit un composant par son type et par ce que l’utilisateur y lit, puis recherche le résultat sur la page lorsque le test le demande.

Un test ne conserve jamais les champs d’une vue pour accéder à ses composants. Il les repère comme le ferait un utilisateur. Le test continue donc de fonctionner même si la vue est construite différemment, tant que la page affiche les mêmes éléments.

<!-- INTRO_END -->

## Localiser un composant {#locating-a-component}

`ComponentLocator.of(type)` commence une description par le type du composant. Des filtres comme `byText` la précisent, puis une méthode finale comme `locate()` recherche le résultat sur la page et le renvoie.

Le composite suivant compte les clics sur son bouton **Add** :

```java title="ClickCounter.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class ClickCounter extends Composite<Div> {
  private int clicks = 0;

  public ClickCounter() {
    Paragraph count = new Paragraph("Clicked 0 times");
    Button add = new Button(
        "Add", e -> count.setText("Clicked " + (++clicks) + " times"));

    getBoundComponent().add(add, count);
  }
}
```

Le test repère le bouton par son texte, clique dessus deux fois, puis recherche le paragraphe qui affiche le nombre de clics :

```java title="ClickCounterTest.java" showLineNumbers
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.samples.views.testbed.ClickCounter;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ClickCounterTest {
  @Test
  void shouldCountEveryClick(WebforjTestbed context) {
    context.add(new ClickCounter());

    var button = ComponentLocator.of(Button.class).byText("Add").locate();

    UserInteraction.click(button);
    UserInteraction.click(button);

    assertTrue(
        ComponentLocator.of(Paragraph.class)
            .byText("Clicked 2 times")
            .isPresent());
  }
}
```

Un localisateur recherche sur la page lorsque sa méthode finale est appelée. La recherche effectuée après les clics trouve donc le nouveau texte.

## Décrire le composant {#describing-the-component}

Une description part du type et ajoute ce qui distingue le composant, dans l’ordre où un utilisateur le ferait : d’abord ce qu’il lit sur le composant, puis son emplacement sur la page. Chaque filtre ne conserve que les composants qui lui correspondent, et les filtres peuvent être chaînés : un composant doit donc correspondre à chacun d’eux.

- `byText`, `byTextContaining`, `byLabel` et `byPlaceholder` correspondent à ce que l’utilisateur lit sur le composant.
- `byValue`, `byTheme`, `byExpanse`, `byClassName`, `byStyle` et `byAttribute` correspondent à ce que l’application définit sur le composant.
- `byName` correspond au nom que l’application attribue à un composant sans texte visible, comme un bouton avec une icône seule.
- `has` recherche un composant en fonction d’un autre composant qu’il contient.
- `filter` vérifie toute condition définie par le test.

Un filtre ignore les composants qui ne possèdent pas l’élément qu’il examine. Ainsi, `byLabel` passe outre un `Div` au lieu de faire échouer la recherche.

### Ce que l’utilisateur lit {#what-the-user-reads}

Le formulaire de connexion suivant comporte un champ e-mail avec un libellé, un champ de mot de passe avec un texte indicatif et un bouton d’aide qui n’affiche qu’une icône :

```java title="SignInForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.PasswordField;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.TablerIcon;

public class SignInForm extends Composite<Div> {

  public SignInForm() {
    TextField email = new TextField("Email");
    PasswordField password = new PasswordField().setPlaceholder("Password");
    Paragraph hint = new Paragraph();
    Button help = new Button(
        TablerIcon.create("help"), e -> hint.setText("Use your work email"));
    help.setName("help");

    getBoundComponent().add(email, password, help, hint);
  }
}
```

Le test repère les trois composants à l’aide de leur libellé, de leur texte indicatif et de leur nom, remplit les champs, puis demande de l’aide :

```java title="SignInFormTest.java" showLineNumbers
@Test
void shouldShowTheHintWhenTheUserAsksForHelp(WebforjTestbed context) {
  context.add(new SignInForm());

  var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
  var password = ComponentLocator.of(PasswordField.class)
      .byPlaceholder("Password")
      .locate();
  var help = ComponentLocator.of(Button.class).byName("help").locate();

  UserInteraction.type(email, "ada@example.com");
  UserInteraction.type(password, "secret");
  UserInteraction.click(help);

  assertEquals("ada@example.com", email.getValue());
  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Use your work email")
          .isPresent());
}
```

### Le contenu d’un composant {#what-a-component-contains}

Certains composants se distinguent uniquement par leur contenu, comme des cartes dont le nom affiché diffère. `has` conserve un composant lorsqu’un autre localisateur trouve une correspondance à l’intérieur. Le composite suivant affiche une carte par contact, avec son nom comme titre, son adresse e-mail comme légende et un bouton **Delete** :

```java title="ContactCards.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Span;

public class ContactCards extends Composite<Div> {

  public ContactCards() {
    getBoundComponent()
        .add(
            card("Ada", "ada@example.com"), card("Linus", "linus@example.com"));
  }

  private Card card(String name, String email) {
    Card card = new Card();
    card.addToTitle(new Span(name))
        .addToCaption(new Span(email))
        .addToFooter(new Button("Delete", e -> card.destroy()));

    return card;
  }
}
```

Le test repère la carte de Linus grâce au nom qu’elle contient, puis vérifie que l’adresse e-mail affichée sur cette carte est la sienne :

```java title="ContactCardsTest.java" showLineNumbers
@Test
void shouldShowTheEmailOnTheCardOfTheContact(WebforjTestbed context) {
  context.add(new ContactCards());

  var linus =
      ComponentLocator.of(Card.class)
          .has(ComponentLocator.of(Span.class).byText("Linus"))
          .locate();

  assertTrue(
      ComponentLocator.of(Span.class)
          .within(linus)
          .byText("linus@example.com")
          .isPresent());
}
```

Seuls les composants contenus dans une carte sont pris en compte, jamais la carte elle-même.

### Toute autre condition {#any-other-condition}

`filter` prend une condition portant sur le type recherché par le localisateur. Il peut donc examiner tout ce que ce type expose. Le formulaire suivant comporte trois champs, dont deux sont obligatoires :

```java title="ShippingForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;

public class ShippingForm extends Composite<Div> {

  public ShippingForm() {
    getBoundComponent()
        .add(
            new TextField("Name").setRequired(true),
            new TextField("Company"),
            new TextField("City").setRequired(true));
  }
}
```

Le test repère les champs obligatoires avec `filter` et lit leurs libellés :

```java title="ShippingFormTest.java" showLineNumbers
@Test
void shouldMarkTheNameAndTheCityAsRequired(WebforjTestbed context) {
  context.add(new ShippingForm());

  var required = ComponentLocator.of(TextField.class)
      .filter(TextField::isRequired)
      .all();

  assertEquals(
      List.of("Name", "City"),
      required.stream().map(TextField::getLabel).toList());
}
```

La référence <JavadocLink type="automation" location="com/webforj/automation/find/ComponentLocator" code="true">ComponentLocator</JavadocLink> répertorie tous les filtres et les composants qu’ils examinent.

## Rechercher dans un composant {#searching-inside-a-component}

Lorsqu’un utilisateur repère deux fois le même bouton, il regarde la partie de la page qui entoure celui qu’il veut. `within(component)` fait de même et limite la recherche à ce composant et à ceux qu’il contient.

Les deux [cartes de contact](#what-a-component-contains) comportent un bouton **Delete**. `byText("Delete")` seul correspond donc à deux boutons. Le test suivant limite la recherche à la première carte :

```java title="ContactCardsTest.java" showLineNumbers
@Test
void shouldRemoveOnlyTheCardWhoseDeleteButtonTheUserClicks(
    WebforjTestbed context) {
  context.add(new ContactCards());

  var ada = ComponentLocator.of(Card.class).all().get(0);

  UserInteraction.click(
      ComponentLocator.of(Button.class).within(ada).byText("Delete").locate());

  assertEquals(1, ComponentLocator.of(Card.class).count());
  assertTrue(ComponentLocator.of(Span.class).byText("Linus").isPresent());
}
```

Seule la première carte est supprimée ; celle de Linus reste affichée.

## Lire le résultat {#reading-the-result}

La méthode finale détermine ce que le test reçoit. `locate()` renvoie l’unique composant correspondant. `all()` renvoie toutes les correspondances dans l’ordre de la page, `first()` et `last()` renvoient respectivement la première et la dernière, `count()` renvoie le nombre de correspondances, et `isPresent()` indique si au moins une correspondance existe.

La liste suivante affiche une case à cocher par article :

```java title="GroceryList.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optioninput.CheckBox;
import java.util.List;

public class GroceryList extends Composite<Div> {

  public GroceryList() {
    for (String item : List.of("Milk", "Bread", "Eggs")) {
      getBoundComponent().add(new CheckBox(item));
    }
  }
}
```

Le test récupère tous les articles avec `all()`, le premier et le dernier avec `first()` et `last()`, ainsi que leur nombre avec `count()` :

```java title="GroceryListTest.java" showLineNumbers
@Test
void shouldListEveryItemInOrder(WebforjTestbed context) {
  context.add(new GroceryList());

  var items = ComponentLocator.of(CheckBox.class).all();

  assertEquals(
      List.of("Milk", "Bread", "Eggs"),
      items.stream().map(CheckBox::getText).toList());
  assertEquals(
      "Milk",
      ComponentLocator.of(CheckBox.class).first().orElseThrow().getText());
  assertEquals(
      "Eggs",
      ComponentLocator.of(CheckBox.class).last().orElseThrow().getText());
  assertEquals(3, ComponentLocator.of(CheckBox.class).count());
}
```

:::tip Composants détruits
Un composant détruit par l’application n’est plus sur la page. Aucun localisateur ne le trouve, quelle que soit la méthode utilisée pour terminer la recherche.
:::

## Lorsque la description est incorrecte {#when-the-description-is-wrong}

`locate()` attend exactement une correspondance. Si la description ne correspond à aucun composant ou en correspond à plusieurs, la recherche échoue au lieu d’en choisir un au hasard. Les deux [cartes de contact](#what-a-component-contains) comportent un bouton **Delete**. Le test suivant décrit un bouton uniquement par son texte et examine l’échec :

```java title="ContactCardsTest.java" showLineNumbers
@Test
void shouldNameEveryMatchWhenTheDescriptionFitsTwoButtons(
    WebforjTestbed context) {
  context.add(new ContactCards());

  var delete = ComponentLocator.of(Button.class).byText("Delete");

  var failure = assertThrows(IllegalStateException.class, delete::locate);

  assertEquals(
      """
      Expected exactly one Button but found 2 in the tree:
      - Frame:
        - ContactCards [composite=Div] [node=div]:
          - Card [composite=Element] [node=dwc-card]:
            - Span [slot=title] [composite=Element] [node=span]
            - Span [slot=caption] [composite=Element] [node=span]
            - Button "Delete" [slot=footer]
          - Card [composite=Element] [node=dwc-card]:
            - Span [slot=title] [composite=Element] [node=span]
            - Span [slot=caption] [composite=Element] [node=span]
            - Button "Delete" [slot=footer]
      """,
      failure.getMessage());
}
```

Le message indique le type et le nombre de correspondances, puis l’arbre des composants parcouru par le localisateur. L’arbre montre les deux boutons **Delete**, un dans chaque carte. Pour corriger le problème, il faut limiter la recherche [à l’intérieur](#searching-inside-a-component) d’une seule carte.

## Composites {#composites}

Un localisateur trouve un `Composite` par son propre type, mais aussi par le type du composant auquel il est lié. Le composite suivant est un champ d’adresse e-mail. Il lie un `TextField`, lui attribue un libellé et un texte indicatif, puis renvoie l’adresse saisie avec `getAddress()` :

```java title="EmailField.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;

public class EmailField extends Composite<TextField> {

  public EmailField() {
    getBoundComponent().setLabel("Email").setPlaceholder("name@example.com");
  }

  public String getAddress() {
    return getBoundComponent().getValue();
  }
}
```

Le test trouve le composite par son propre type, puis le champ auquel il est lié par son type `TextField` et son libellé. Il saisit une adresse dans le champ et la récupère à travers le composite :

```java title="EmailFieldTest.java" showLineNumbers
@Test
void shouldReadTheAddressTheUserTypedIntoTheBoundField(WebforjTestbed context) {
  context.add(new EmailField());

  var email = ComponentLocator.of(EmailField.class).locate();
  var field = ComponentLocator.of(TextField.class).byLabel("Email").locate();

  UserInteraction.type(field, "ada@example.com");

  assertEquals("ada@example.com", email.getAddress());
}
```

Lorsqu’un composite est lié à un autre composite, le localisateur peut le trouver par chacun des types de cette chaîne.
