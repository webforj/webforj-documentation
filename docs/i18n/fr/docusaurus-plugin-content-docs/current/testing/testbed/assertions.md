---
sidebar_position: 4
title: Assertions
description: >-
  Check what the user would see on a component with verifyThat, and read
  failures that name the component and both values.
_i18n_hash: 6414e2fab20d3bf7828413494af79b74
---
Après une action du test, celui-ci vérifie ce que l’utilisateur verrait : le texte d’un bouton, si un champ peut être modifié, quel élément d’une liste est sélectionné. `verifyThat(component)` énonce ces attentes avec les mêmes termes, par exemple `isReadOnly()` ou `hasText("Save")`. Chaque vérification lit l’état du composant, le même état que celui affiché par le navigateur, et une vérification échouée indique le composant et les deux valeurs.

<!-- INTRO_END -->

## Vérifier un composant {#checking-a-component}

L’éditeur suivant affiche un nom qui ne peut pas être modifié tant que l’utilisateur n’a pas cliqué sur **Edit**. Le clic rend le champ modifiable, lui donne le focus et transforme le bouton en **Save** :

```java title="ProfileEditor.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;

public class ProfileEditor extends Composite<Div> {

  public ProfileEditor() {
    TextField name = new TextField("Name", "Ada");
    name.setReadOnly(true);
    Button edit = new Button("Edit");
    edit.onClick(
        e -> {
          name.setReadOnly(false).focus();
          edit.setText("Save");
        });

    getBoundComponent().add(name, edit);
  }
}
```

Le test vérifie le champ avant et après le clic :

```java title="ProfileEditorTest.java" showLineNumbers
@Test
void shouldMakeTheNameEditableWhenTheUserClicksEdit(WebforjTestbed context) {
  context.add(new ProfileEditor());

  var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();
  var edit = ComponentLocator.of(Button.class).byText("Edit").locate();

  verifyThat(name).hasValue("Ada").isReadOnly();

  UserInteraction.click(edit);

  verifyThat(name).isEditable().isFocused();
  verifyThat(edit).hasText("Save");
}
```

`verifyThat` provient de `com.webforj.testbed.assertion.WebforjAssertions` et est importé de façon statique. Chaque vérification renvoie l’assertion, ce qui permet d’enchaîner celles qui portent sur un même composant dans une seule instruction. Elles sont exécutées dans l’ordre et la première qui échoue met fin au test.

Chaque état a une vérification pour chacun de ses aspects, par exemple `isReadOnly()` et `isEditable()`, ou `isVisible()` et `isHidden()`. Le test énonce donc directement l’état attendu.

## Ce que lit une vérification {#what-a-check-reads}

Une vérification lit le composant par l’intermédiaire d’une [interface de concern](/docs/building-ui/component-fundamentals#concern-interfaces). Elle fonctionne donc sur tous les composants qui implémentent cette interface, y compris ceux de votre application. `hasLabel` fonctionne sur tous les composants ayant un libellé, et `isReadOnly` sur tous les composants qui peuvent être en lecture seule. Les vérifications couvrent :

- Ce que l’utilisateur lit : texte, contenu HTML, libellé, texte indicatif, texte d’aide et info-bulle.
- L’état du composant : visibilité, activation, lecture seule, caractère obligatoire, validité et focus.
- La valeur d’un champ, ainsi que les éléments et la sélection des listes, tableaux et arbres.
- Ce que l’application a défini : nom, attributs, propriétés, style, thème et étendue.
- Les composants contenus dans un conteneur, et le fait qu’un composant soit attaché ou détruit.

La référence <JavadocLink type="testbed-foundation" location="com/webforj/testbed/assertion/AbstractComponentAssert" code="true">AbstractComponentAssert</JavadocLink> répertorie chaque vérification et l’interface qu’elle lit.

## Valeurs {#values}

Le champ suivant calcule un total à partir de la quantité saisie par l’utilisateur :

```java title="OrderQuantity.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.field.NumberField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class OrderQuantity extends Composite<Div> {

  public OrderQuantity() {
    NumberField quantity = new NumberField("Quantity");
    Paragraph total = new Paragraph("Total: 0");
    quantity.onValueChange(
        e -> total.setText("Total: " + (int) (e.getValue() * 5)));

    getBoundComponent().add(quantity, total);
  }
}
```

Le test saisit une quantité, puis vérifie la valeur du champ et le total :

```java title="OrderQuantityTest.java" showLineNumbers
@Test
void shouldHoldTheTypedQuantityAsANumber(WebforjTestbed context) {
  context.add(new OrderQuantity());

  var quantity = ComponentLocator.of(NumberField.class)
      .byLabel("Quantity")
      .locate();

  UserInteraction.type(quantity, "3");

  verifyThat(quantity).hasValue(3.0);
  verifyThat(ComponentLocator.of(Paragraph.class).locate())
      .hasText("Total: 15");
}
```

`hasValue` compare les valeurs avec `equals` ; la valeur attendue doit donc être du même type que celle stockée par le champ. Un `NumberField` stocke un `Double` : le test attend donc `3.0`, et non `3`.

:::info Types de valeurs
Un `DateField` stocke un `LocalDate`, et un `TimeField`, un `LocalTime`. Attendez-vous à une valeur de ce type.
:::

## Composants enfants {#child-components}

`hasComponentCount` vérifie le nombre de composants contenus dans un conteneur, et `hasComponent` vérifie qu’il en contient un donné. L’éditeur suivant ajoute à une liste chaque étiquette saisie par l’utilisateur :

```java title="TagEditor.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Span;

public class TagEditor extends Composite<Div> {

  public TagEditor() {
    TextField tag = new TextField("Tag");
    Div tags = new Div();
    tags.setName("tags");
    Button add = new Button("Add", e -> tags.add(new Span(tag.getValue())));

    getBoundComponent().add(tag, add, tags);
  }
}
```

Le test ajoute deux étiquettes et vérifie la liste :

```java title="TagEditorTest.java" showLineNumbers
@Test
void shouldAddEveryTagTheUserEnters(WebforjTestbed context) {
  context.add(new TagEditor());

  var tag = ComponentLocator.of(TextField.class).byLabel("Tag").locate();
  var add = ComponentLocator.of(Button.class).byText("Add").locate();
  var tags = ComponentLocator.of(Div.class).byName("tags").locate();

  UserInteraction.type(tag, "java");
  UserInteraction.click(add);
  UserInteraction.type(tag, "kotlin");
  UserInteraction.click(add);

  verifyThat(tags)
      .hasComponentCount(2)
      .hasComponent(ComponentLocator.of(Span.class).byText("kotlin").locate());
}
```

## Sélection {#selection}

Les vérifications de sélection lisent l’élément qu’une liste, un tableau ou un arbre affiche comme sélectionné, en fonction de l’élément, de son index ou de sa clé. La liste suivante propose trois fruits :

```java title="FruitPicker.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ListBox;

public class FruitPicker extends Composite<Div> {

  public FruitPicker() {
    ListBox fruits = new ListBox("Fruit");
    fruits.add("apple", "Apple");
    fruits.add("pear", "Pear");
    fruits.add("plum", "Plum");

    getBoundComponent().add(fruits);
  }
}
```

Le test vérifie qu’aucun élément n’est sélectionné, choisit un fruit, puis vérifie la sélection par clé et par index :

```java title="FruitPickerTest.java" showLineNumbers
@Test
void shouldSelectTheFruitTheUserPicked(WebforjTestbed context) {
  context.add(new FruitPicker());

  var fruits = ComponentLocator.of(ListBox.class).byLabel("Fruit").locate();

  verifyThat(fruits).hasNoSelection();

  UserInteraction.selectKey(fruits, "pear");

  verifyThat(fruits).hasSelectedKey("pear").hasSelectedIndex(1);
}
```

Pour un composant qui autorise la sélection de plusieurs éléments, `hasSelected`, `hasSelectedIndex` et `hasSelectedKey` réussissent lorsque l’élément attendu en fait partie. `hasItemCount` vérifie le nombre d’éléments d’un composant doté d’un référentiel, comme un `Table`.

## Cycle de vie {#lifecycle}

`isAttached()` vérifie qu’un composant se trouve sur la page, et `isDestroyed()` que l’application l’a détruit. La bannière suivante se détruit lorsque l’utilisateur la ferme :

```java title="WelcomeBanner.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class WelcomeBanner extends Composite<Div> {

  public WelcomeBanner() {
    getBoundComponent()
        .add(
            new Paragraph("Welcome back"),
            new Button("Dismiss", e -> destroy()));
  }
}
```

Le test ferme la bannière et la vérifie :

```java title="WelcomeBannerTest.java" showLineNumbers
@Test
void shouldRemoveTheBannerWhenTheUserDismissesIt(WebforjTestbed context) {
  context.add(new WelcomeBanner());

  var banner = ComponentLocator.of(WelcomeBanner.class).locate();

  verifyThat(banner).isAttached();

  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Dismiss").locate());

  verifyThat(banner).isDestroyed();
}
```

## Messages d’échec {#failure-messages}

Une vérification échouée lève une `AssertionError` qui indique le type du composant, la valeur attendue et la valeur réelle. Le test suivant fait volontairement échouer une vérification et lit le message :

```java title="ProfileEditorTest.java" showLineNumbers
@Test
void shouldNameTheComponentAndBothValuesOnFailure(WebforjTestbed context) {
  context.add(new ProfileEditor());

  var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();

  var failure = assertThrows(
      AssertionError.class, () -> verifyThat(name).hasValue("Grace"));

  assertEquals(
      "Expected TextField to have value <Grace> but it has <Ada>.",
      failure.getMessage());
}
```

Une vérification portant sur un composant qui n’expose pas l’état échoue avec un message indiquant l’interface manquante. Un `Div` ne stocke aucune valeur ; `hasValue` échoue donc sur un `Div` et indique `ValueAware`.

## Vérifications pour vos propres composants {#checks-for-your-own-components}

Un composant de votre application qui implémente des interfaces de concern bénéficie de leurs vérifications sans code supplémentaire. Pour les états connus uniquement de votre composant, écrivez une classe d’assertion. Le composant d’évaluation suivant compte les étoiles sur lesquelles l’utilisateur clique et les renvoie avec `getStars()` :

```java title="StarRating.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;

public class StarRating extends Composite<Div> {
  private int stars = 0;

  public StarRating() {
    for (int i = 1; i <= 5; i++) {
      int value = i;
      getBoundComponent()
          .add(new Button(String.valueOf(i), e -> stars = value));
    }
  }

  public int getStars() {
    return stars;
  }
}
```

La classe d’assertion étend `AbstractComponentAssert` et ajoute une vérification du nombre d’étoiles. Une méthode statique `verifyThat` crée cette classe, ce qui permet au test de l’appeler comme les vérifications intégrées :

```java title="StarRatingAssert.java" showLineNumbers
import com.webforj.samples.views.testbed.StarRating;
import com.webforj.testbed.assertion.AbstractComponentAssert;

public final class StarRatingAssert
    extends AbstractComponentAssert<StarRatingAssert, StarRating> {

  private StarRatingAssert(StarRating actual) {
    super(actual, StarRatingAssert.class);
  }

  public static StarRatingAssert verifyThat(StarRating actual) {
    return new StarRatingAssert(actual);
  }

  public StarRatingAssert hasStars(int stars) {
    isNotNull();
    if (actual.getStars() != stars) {
      failWithMessage(
          "Expected %s to have %s stars but it has %s.",
          componentName(),
          stars,
          actual.getStars());
    }

    return myself;
  }
}
```

Dans une vérification, `actual` désigne le composant, `failWithMessage` déclenche un échec avec un message formaté, `componentName()` indique le nom du composant et le renvoi de `myself` permet d’enchaîner les vérifications. La nouvelle classe ajoute des vérifications, elle n’en remplace aucune. Un test importe de façon statique les deux méthodes `verifyThat`, et Java choisit celle dont le paramètre correspond à l’argument : un `StarRating` est transmis à `StarRatingAssert`, et tout autre composant à `WebforjAssertions`. Les vérifications intégrées restent également disponibles sur le composant d’évaluation, comme `isAttached()` :

```java title="StarRatingTest.java" showLineNumbers
import static com.webforj.samples.unit.views.testbed.StarRatingAssert.verifyThat;
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.samples.views.testbed.StarRating;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class StarRatingTest {
  @Test
  void shouldHoldTheStarsTheUserClicked(WebforjTestbed context) {
    context.add(new StarRating());

    var rating = ComponentLocator.of(StarRating.class).locate();

    UserInteraction.click(
        ComponentLocator.of(Button.class).byText("4").locate());

    verifyThat(rating).hasStars(4).isAttached();
  }

  @Test
  void shouldCheckTheRatingAndTheButtonInOneTest(WebforjTestbed context) {
    context.add(new StarRating());

    var rating = ComponentLocator.of(StarRating.class).locate();
    var four = ComponentLocator.of(Button.class).byText("4").locate();

    UserInteraction.click(four);

    verifyThat(rating).hasStars(4);
    verifyThat(four).hasText("4").isEnabled();
  }
}
```
