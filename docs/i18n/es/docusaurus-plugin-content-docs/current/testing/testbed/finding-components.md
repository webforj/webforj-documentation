---
sidebar_position: 2
title: Encontrar componentes
description: >-
  Describe the components a TestBed test acts on the way a user picks them out
  on the page, by type, text, label and what they contain.
_i18n_hash: 3e31c9bc7b51ebd2ebaf1a5e44c6e94f
---
Antes de hacer clic en un botón, el usuario lo encuentra en la página: el botón **Save** o el campo etiquetado para la dirección de correo electrónico. Un test de TestBed encuentra los componentes de la misma manera, con `ComponentLocator`. Un locator describe un componente por su tipo y por lo que el usuario lee en él, y busca en la página cuando el test solicita el resultado.

Un test nunca conserva referencias a los campos de una vista para acceder a sus componentes. Los encuentra como lo hace un usuario, por lo que el test sigue funcionando aunque la vista se construya de otra manera, siempre que la página muestre lo mismo.

<!-- INTRO_END -->

## Localizar un componente {#locating-a-component}

`ComponentLocator.of(type)` inicia una descripción con el tipo del componente. Los filtros, como `byText`, la acotan, y un método final, como `locate()`, busca en la página y devuelve el resultado.

El siguiente componente compuesto cuenta los clics en su botón **Add**:

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

El test encuentra el botón por su texto, hace clic en él dos veces y luego encuentra el párrafo que muestra el recuento:

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

Un locator busca en la página cuando se ejecuta su método final, por lo que la búsqueda posterior a los clics encuentra el texto nuevo.

## Describir el componente {#describing-the-component}

Una descripción parte del tipo y añade lo que distingue al componente, en el orden en que lo haría un usuario: primero, lo que el usuario lee en él; después, cómo está ubicado en la página. Cada filtro conserva solo los componentes que coinciden con él, y los filtros se encadenan, por lo que un componente debe coincidir con todos.

- `byText`, `byTextContaining`, `byLabel` y `byPlaceholder` coinciden con lo que el usuario lee en el componente.
- `byValue`, `byTheme`, `byExpanse`, `byClassName`, `byStyle` y `byAttribute` coinciden con lo que la aplicación establece en el componente.
- `byName` coincide con el nombre que la aplicación asigna a un componente que no muestra texto, como un botón que solo tiene un icono.
- `has` encuentra un componente según otro componente que contiene.
- `filter` encuentra componentes que cumplen cualquier condición definida por el test.

Un filtro omite los componentes que no tienen lo que intenta leer, por lo que `byLabel` pasa por alto un `Div` en lugar de hacer que falle la búsqueda.

### Lo que lee el usuario {#what-the-user-reads}

El siguiente formulario de inicio de sesión tiene un campo de correo electrónico con una etiqueta, un campo de contraseña con un marcador de posición y un botón de ayuda que solo muestra un icono:

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

El test encuentra los tres componentes por etiqueta, marcador de posición y nombre, completa los campos y solicita ayuda:

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

### Lo que contiene un componente {#what-a-component-contains}

Algunos componentes solo se distinguen por lo que contienen, como las tarjetas que muestran nombres diferentes. `has` conserva un componente cuando otro locator encuentra una coincidencia en su interior. El siguiente componente compuesto muestra una tarjeta por cada contacto, con el nombre como título, el correo electrónico como subtítulo y un botón **Delete**:

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

El test encuentra la tarjeta de Linus por el nombre que contiene y luego comprueba que el correo electrónico de esa tarjeta sea el suyo:

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

Solo cuentan los componentes dentro de una tarjeta, nunca la tarjeta en sí.

### Cualquier otra condición {#any-other-condition}

`filter` acepta una condición sobre el tipo que busca el locator, por lo que puede leer cualquier propiedad que ofrezca ese tipo. El siguiente formulario tiene tres campos, dos de los cuales son obligatorios:

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

El test encuentra los campos obligatorios con `filter` y lee sus etiquetas:

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

La referencia de <JavadocLink type="automation" location="com/webforj/automation/find/ComponentLocator" code="true">ComponentLocator</JavadocLink> enumera todos los filtros y los componentes que leen.

## Buscar dentro de un componente {#searching-inside-a-component}

Cuando un usuario encuentra dos veces el mismo botón, observa la parte de la página que rodea al que quiere. `within(component)` hace lo mismo y limita la búsqueda a ese componente y a los componentes que contiene.

Ambas [tarjetas de contacto](#what-a-component-contains) tienen un botón **Delete**, por lo que `byText("Delete")` por sí solo coincide con dos botones. El siguiente test busca solo en la primera tarjeta:

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

Solo se elimina la primera tarjeta, y la de Linus permanece.

## Leer el resultado {#reading-the-result}

El método final determina qué devuelve el test. `locate()` devuelve el único componente que coincide. `all()` devuelve todas las coincidencias en el orden de la página; `first()` y `last()`, la primera y la última coincidencia; `count()`, el número de coincidencias; e `isPresent()`, si hay alguna coincidencia.

La siguiente lista muestra una casilla de verificación por elemento:

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

El test lee todos los elementos con `all()`, el primero y el último con `first()` y `last()`, y la cantidad de elementos con `count()`:

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

:::tip Componentes destruidos
Un componente que la aplicación ha destruido ya no está en la página, por lo que ningún locator lo encuentra, sea cual sea el método que finalice la búsqueda.
:::

## Cuando la descripción es incorrecta {#when-the-description-is-wrong}

`locate()` espera exactamente una coincidencia, por lo que una descripción que no coincide con ningún componente o que coincide con varios falla, en lugar de elegir uno al azar. Ambas [tarjetas de contacto](#what-a-component-contains) tienen un botón **Delete**. El siguiente test describe un botón solo por su texto y lee el error:

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

El mensaje indica el tipo y el número de coincidencias, seguido del árbol de componentes que buscó el locator. El árbol muestra los dos botones **Delete**, uno en cada tarjeta, por lo que la solución es buscar [dentro](#searching-inside-a-component) de una de ellas.

## Componentes compuestos {#composites}

Un locator encuentra un `Composite` por su propio tipo y también por el tipo del componente al que está vinculado. El siguiente componente compuesto es un campo de correo electrónico. Vincula un `TextField`, le asigna una etiqueta y un marcador de posición, y devuelve la dirección introducida con `getAddress()`:

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

El test encuentra el componente compuesto por su propio tipo y el campo al que está vinculado por su tipo `TextField` y su etiqueta. Introduce texto en el campo y lee la dirección a través del componente compuesto:

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

Cuando un componente compuesto se vincula a otro componente compuesto, se puede encontrar mediante cada tipo de esa cadena.
