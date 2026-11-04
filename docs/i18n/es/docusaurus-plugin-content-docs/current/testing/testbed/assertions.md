---
sidebar_position: 4
title: Aserciones
description: >-
  Check what the user would see on a component with verifyThat, and read
  failures that name the component and both values.
_i18n_hash: 6414e2fab20d3bf7828413494af79b74
---
Después de que una prueba realiza una acción, comprueba lo que vería el usuario: el texto de un botón, si se puede editar un campo o qué elemento de una lista aparece seleccionado. `verifyThat(component)` expresa estas expectativas con las mismas palabras, como `isReadOnly()` o `hasText("Save")`. Cada comprobación lee el estado del componente —el mismo estado que muestra el navegador— y, si falla, indica el componente y ambos valores.

<!-- INTRO_END -->

## Comprobar un componente {#checking-a-component}

El siguiente editor muestra un nombre que no se puede cambiar hasta que el usuario hace clic en **Edit**. El clic hace que el campo sea editable, mueve el foco al campo y cambia el botón a **Save**:

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

La prueba comprueba el campo antes y después del clic:

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

`verifyThat` proviene de `com.webforj.testbed.assertion.WebforjAssertions` y se importa de forma estática. Cada comprobación devuelve la aserción, por lo que las comprobaciones de un mismo componente se encadenan en una sola instrucción. Se ejecutan en orden y la primera que falla termina la prueba.

Cada estado tiene una comprobación para cada una de sus opciones, como `isReadOnly()` e `isEditable()`, o `isVisible()` e `isHidden()`, de modo que la prueba expresa directamente el estado esperado.

## Qué lee una comprobación {#what-a-check-reads}

Una comprobación lee el componente a través de una [interfaz de concern](/docs/building-ui/component-fundamentals#concern-interfaces), por lo que funciona con todos los componentes que implementan esa interfaz, incluidos los componentes de tu aplicación. `hasLabel` funciona con todos los componentes que tienen una etiqueta, y `isReadOnly`, con todos los componentes que pueden ser de solo lectura. Las comprobaciones abarcan:

- Lo que lee el usuario: texto, contenido HTML, etiqueta, marcador de posición, texto de ayuda e información sobre herramientas.
- El estado del componente: visible, habilitado, de solo lectura, obligatorio, válido y enfocado.
- El valor de un campo, así como los elementos y la selección de listas, tablas y árboles.
- Lo que establece la aplicación: nombre, atributos, propiedades, estilo, tema y extensión.
- Los componentes que contiene un contenedor, y si un componente está adjunto o se ha destruido.

La referencia <JavadocLink type="testbed-foundation" location="com/webforj/testbed/assertion/AbstractComponentAssert" code="true">AbstractComponentAssert</JavadocLink> enumera todas las comprobaciones junto con la interfaz que leen.

## Valores {#values}

El siguiente campo calcula un total a partir de la cantidad que escribe el usuario:

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

La prueba escribe una cantidad y luego comprueba el valor del campo y el total:

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

`hasValue` compara mediante `equals`, por lo que el valor esperado debe tener el mismo tipo que el valor del campo. Un `NumberField` contiene un `Double`, así que la prueba espera `3.0`, no `3`.

:::info Tipos de valor
Un `DateField` contiene un `LocalDate`, y un `TimeField`, un `LocalTime`. El valor esperado debe ser de ese tipo.
:::

## Componentes secundarios {#child-components}

`hasComponentCount` comprueba cuántos componentes contiene un contenedor, y `hasComponent` comprueba que contiene uno determinado. El siguiente editor añade a una lista cada etiqueta que escribe el usuario:

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

La prueba añade dos etiquetas y comprueba la lista:

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

## Selección {#selection}

Las comprobaciones de selección leen qué elemento de una lista, tabla o árbol aparece seleccionado, ya sea por el elemento, su índice o su clave. La siguiente lista ofrece tres frutas:

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

La prueba comprueba que no haya nada seleccionado, elige una fruta y comprueba la selección por clave y por índice:

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

En un componente que permite seleccionar varios elementos, `hasSelected`, `hasSelectedIndex` y `hasSelectedKey` pasan si el elemento esperado está entre los seleccionados. `hasItemCount` comprueba el número de elementos de un componente con un repositorio, como una `Table`.

## Ciclo de vida {#lifecycle}

`isAttached()` comprueba que un componente esté en la página, e `isDestroyed()`, que la aplicación lo haya destruido. El siguiente banner se destruye cuando el usuario lo descarta:

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

La prueba descarta el banner y lo comprueba:

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

## Mensajes de error {#failure-messages}

Una comprobación fallida lanza un `AssertionError` que indica el tipo de componente, el valor esperado y el valor real. La siguiente prueba provoca un fallo intencionadamente y lee el mensaje:

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

Una comprobación de un componente que no expone ese estado falla con un mensaje que indica la interfaz que falta. Un `Div` no contiene ningún valor, así que `hasValue` en un `Div` falla e indica `ValueAware`.

## Comprobaciones para tus propios componentes {#checks-for-your-own-components}

Un componente de tu aplicación que implementa interfaces de concern obtiene sus comprobaciones automáticamente, sin código adicional. Para estados que solo conoce tu componente, escribe una clase de aserción. La siguiente clasificación cuenta las estrellas en las que hace clic el usuario y las devuelve mediante `getStars()`:

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

La clase de aserción extiende `AbstractComponentAssert` y añade una comprobación de las estrellas. Un método estático `verifyThat` la instancia, de modo que una prueba lo llama igual que a las comprobaciones integradas:

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

Dentro de una comprobación, `actual` es el componente, `failWithMessage` provoca un fallo con un mensaje formateado, `componentName()` indica el nombre del componente y devolver `myself` permite encadenar comprobaciones. La nueva clase añade comprobaciones, no sustituye ninguna. Una prueba importa estáticamente ambos métodos `verifyThat`, y Java elige el que corresponde al argumento: un `StarRating` se pasa a `StarRatingAssert`, y cualquier otro componente, a `WebforjAssertions`. Las comprobaciones integradas también siguen disponibles para la clasificación, como `isAttached()`:

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
