---
sidebar_position: 5
title: Instantáneas
description: >-
  Compare everything a view shows with a stored text description of it, read
  that description, and add the state of your own components to it.
_i18n_hash: 2f709890e8d1fd70a61debc1de3ae63f
---
Comprobar una vista hecho por hecho requiere una aserción por cada etiqueta, botón y campo. Una instantánea lo comprueba todo a la vez. Registra todo lo que vería un usuario en la vista, una línea por componente, y guarda esa descripción junto a la prueba. En cada ejecución posterior, vuelve a describir la vista y la compara, por lo que cualquier cambio en lo que muestra la página hace que falle la prueba y aparece como una diferencia de texto sin formato en la revisión del código.

<!-- INTRO_END -->

## Comparar con una instantánea {#matching-a-snapshot}

El siguiente resumen muestra un número de pedido, un campo de notas, una línea de descuento oculta, un botón **Pay** con un icono y un botón **Cancel** deshabilitado:

```java title="OrderSummary.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.TablerIcon;

public class OrderSummary extends Composite<Div> {

  public OrderSummary() {
    Paragraph order = new Paragraph("Order 1042");
    TextField note = new TextField("Note")
        .setPlaceholder("Delivery instructions");
    Paragraph discount = new Paragraph("Discount applied");
    discount.setVisible(false);
    Button pay = new Button("Pay", ButtonTheme.PRIMARY);
    pay.setPrefixComponent(TablerIcon.create("credit-card"));
    Button cancel = new Button("Cancel");
    cancel.setEnabled(false);

    getBoundComponent().add(order, note, discount, pay, cancel);
  }
}
```

La prueba agrega el resumen y lo comprueba con `matchesSnapshot()`:

```java title="OrderSummaryTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.samples.views.testbed.OrderSummary;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class OrderSummaryTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var summary = new OrderSummary();
    context.add(summary);

    verifyThat(summary).matchesSnapshot();
  }
}
```

La primera ejecución escribe la descripción en un archivo y pasa. Todas las ejecuciones posteriores fallan si la vista ya no coincide con el archivo guardado:

```snapshot title="OrderSummaryTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- OrderSummary [composite=Div] [node=div]:
  - Paragraph "Order 1042" [composite=Element] [node=p]
  - TextField [label=Note] [placeholder="Delivery instructions"]
  - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
  - Button "Pay" [theme=primary]:
    - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
  - Button "Cancel" [disabled]
```

`matchesSnapshot` es una comprobación de `verifyThat`, como las que aparecen en la página de [Aserciones](./assertions), por lo que funciona con cualquier componente, no solo con una vista.

:::tip Cuándo usar instantáneas
Una instantánea fija una vista completa. Úsala para las vistas cuyo contenido no debería cambiar y comprueba hechos concretos, como el texto de un botón después de hacer clic, con [aserciones](./assertions).
:::

## Leer una instantánea {#reading-a-snapshot}

Cada línea de la [instantánea guardada](#matching-a-snapshot) describe un componente. Los componentes que contiene aparecen debajo, con una sangría de dos espacios, y las líneas que contienen otros componentes terminan en dos puntos. Una línea se lee en el siguiente orden:

1. El tipo de componente, como `Button`.
2. El texto entre comillas dobles, cuando el componente muestra uno, como `"Pay"`.
3. Los atributos como `[key=value]`, por ejemplo, `[theme=primary]` o `[label=Note]`.
4. Los indicadores como `[key]`, por ejemplo, `[disabled]` o `[hidden]`.

Una línea solo muestra lo que difiere del valor predeterminado del componente, para que el resumen se entienda de un vistazo. **Cancel** lleva `[disabled]`, mientras que el botón **Pay**, que está habilitado, no muestra nada sobre su estado. **Pay** lleva `[theme=primary]`, mientras que un botón con el tema predeterminado no muestra ningún tema. Los tamaños, estilos y nombres de clase nunca se muestran.

Algunos atributos indican dónde se encuentra un componente:

- `slot` indica la parte de su elemento principal que ocupa un componente. El icono de **Pay** aparece antes del texto, por lo que muestra `[slot=prefix]`.
- `composite` indica el componente al que enlaza un `Composite`, y `node` el elemento que representa. El resumen enlaza un `Div`, por lo que muestra `[composite=Div] [node=div]`.
- `route` indica la ruta bajo la que se registra una vista.
- `hidden` marca un componente oculto, como la línea de descuento. Ocultar o mostrar un componente cambia la instantánea.

El texto siempre aparece entre comillas. El valor de un atributo aparece entre comillas si contiene algo que no sean letras, dígitos, `.` , `_`, `+`, `/` o `-`, como en `[placeholder="Delivery instructions"]`.

## Listas largas {#long-lists}

Una lista larga ocultaría el resto de la vista, por lo que los elementos de una lista, las filas de una tabla y los nodos de un árbol muestran hasta diez líneas debajo de su elemento principal. La siguiente lista contiene doce países:

```java title="CountryList.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ListBox;
import java.util.List;

public class CountryList extends Composite<Div> {

  public CountryList() {
    ListBox countries = new ListBox("Country");
    for (String country :
        List.of(
            "Austria", "Belgium", "Canada", "Denmark", "Egypt", "France",
            "Germany", "Hungary",
            "India", "Japan", "Kenya", "Mexico", "Hyyan")) {
      countries.add(country);
    }

    getBoundComponent().add(countries);
  }
}
```

```java title="CountryListTest.java" showLineNumbers
@Test
void shouldMatchTheSnapshot(WebforjTestbed context) {
  var list = new CountryList();
  context.add(list);

  verifyThat(list).matchesSnapshot();
}
```

La instantánea guardada muestra los primeros diez países y una línea para los dos siguientes:

```snapshot title="CountryListTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- CountryList [composite=Div] [node=div]:
  - ListBox [label=Country]:
    - ListItem "Austria" [key=Austria]
    - ListItem "Belgium" [key=Belgium]
    - ListItem "Canada" [key=Canada]
    - ListItem "Denmark" [key=Denmark]
    - ListItem "Egypt" [key=Egypt]
    - ListItem "France" [key=France]
    - ListItem "Germany" [key=Germany]
    - ListItem "Hungary" [key=Hungary]
    - ListItem "India" [key=India]
    - ListItem "Japan" [key=Japan]
    - … 3 more
```

Para guardar más elementos, pasa el límite en las [opciones de instantánea](#snapshot-options) con `setMaxWidth`. `matchesSnapshot(name, options)` representa la vista usando exactamente las opciones que recibe, así que la prueba también activa las otras tres opciones de la instantánea guardada y le asigna un nombre:

```java title="CountryListTest.java" showLineNumbers
@Test
void shouldMatchTheSnapshotOfEveryCountry(WebforjTestbed context) {
  var list = new CountryList();
  context.add(list);

  verifyThat(list)
      .matchesSnapshot(
          "every-country",
          new SnapshotOptions()
              .setMaxWidth(20)
              .setBrowserState(true)
              .setHiddenComponents(true)
              .setComposites(true));
}
```

La instantánea guardada contiene los doce países, en un archivo con el nombre de la instantánea:

```snapshot title="CountryListTest.every-country.yml" showLineNumbers
# webforJ testbed snapshot v1
- CountryList [composite=Div] [node=div]:
  - ListBox [label=Country]:
    - ListItem "Austria" [key=Austria]
    - ListItem "Belgium" [key=Belgium]
    - ListItem "Canada" [key=Canada]
    - ListItem "Denmark" [key=Denmark]
    - ListItem "Egypt" [key=Egypt]
    - ListItem "France" [key=France]
    - ListItem "Germany" [key=Germany]
    - ListItem "Hungary" [key=Hungary]
    - ListItem "India" [key=India]
    - ListItem "Japan" [key=Japan]
    - ListItem "Kenya" [key=Kenya]
    - ListItem "Mexico" [key=Mexico]
    - ListItem "Hyyan" [key=Hyyan]
```

:::tip Tablas y árboles
Una tabla no cuenta los elementos de su repositorio, por lo que termina con un `- …` sin indicar ninguna cantidad. El límite se aplica por separado a cada elemento principal, así que un árbol trunca cada nivel después de diez nodos.
:::

## Dónde se guardan las instantáneas {#where-snapshots-are-stored}

Los archivos de instantánea se guardan en `src/test/resources/webforj-snapshots`, dentro de las carpetas del paquete de la clase de prueba. Cada archivo lleva el nombre de la clase de prueba, seguido del nombre de la instantánea y `.yml`:

```text title="src/test/resources"
src/test/resources/webforj-snapshots/
  com/webforj/samples/unit/views/testbed/
    CountryListTest.shouldMatchTheSnapshot.yml
    OrderSummaryTest.shouldMatchTheSnapshot.yml
```

Los archivos se mantienen en el control de versiones junto a las pruebas. La primera línea de cada archivo indica la versión del formato. Un archivo escrito con otra versión falla y muestra un mensaje que indica ambas versiones, en lugar de una diferencia.

## Asignar nombres a las instantáneas {#naming-snapshots}

`matchesSnapshot()` asigna a la instantánea el nombre del método de prueba en ejecución, por lo que la instantánea de `shouldMatchTheSnapshot` en `OrderSummaryTest` se guarda en `OrderSummaryTest.shouldMatchTheSnapshot.yml`. Una prueba que comprueba más de una instantánea pasa un nombre a cada una con `matchesSnapshot(name)`, y el nombre sustituye al método en el nombre del archivo. El nombre puede contener letras, dígitos, espacios, puntos, guiones y el carácter `_`; cualquier otro carácter se convierte en un guion. Así, `save/form:v2` se guarda como `save-form-v2`.

`matchesSnapshot(name, options)` también recibe [`SnapshotOptions`](#snapshot-options) para generar la representación, por ejemplo, para guardar más filas de una tabla que las que permite el valor predeterminado.

## Cuando una instantánea no coincide {#when-a-snapshot-doesnt-match}

Cuando la vista ya no coincide con el archivo guardado, la comprobación falla e indica los cambios. Si el texto del botón **Pay** del [resumen del pedido](#matching-a-snapshot) cambia a **Pay now**, `mvn test` informa lo siguiente:

```diff title="Terminal"
java.lang.AssertionError:
Snapshot <shouldMatchTheSnapshot> does not match src/test/resources/webforj-snapshots/…/OrderSummaryTest.shouldMatchTheSnapshot.yml.
First difference at line 5.
--- stored
+++ actual
@@ -3,5 +3,5 @@
   - TextField [label=Note] [placeholder="Delivery instructions"]
   - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
-  - Button "Pay" [theme=primary]:
+  - Button "Pay now" [theme=primary]:
     - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
   - Button "Cancel" [disabled]
Actual:
- OrderSummary [composite=Div] [node=div]:
  - Paragraph "Order 1042" [composite=Element] [node=p]
  - TextField [label=Note] [placeholder="Delivery instructions"]
  - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
  - Button "Pay now" [theme=primary]:
    - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
  - Button "Cancel" [disabled]
Run with -Dwebforj.testbed.updateSnapshots=true to update the snapshot, or verify more than one snapshot in the same test with explicit names.
```

## Actualizar instantáneas {#updating-snapshots}

Cuando un cambio en una vista es intencional, ejecuta las pruebas una vez con la propiedad del sistema `webforj.testbed.updateSnapshots` establecida en `true`. Se vuelve a escribir cada instantánea que comprueban las pruebas ejecutadas y se eliminan los archivos de instantánea que ya no comprueba ninguna prueba. Revisa las diferencias de los archivos antes de confirmarlos.

```bash title="Terminal"
mvn test -Dwebforj.testbed.updateSnapshots=true
```

## Instantáneas obsoletas {#obsolete-snapshots}

Después de que se ejecuten todas las pruebas de una clase, TestBed busca archivos de instantánea de esa clase que ninguna de sus pruebas haya comprobado, por ejemplo, el archivo de una prueba cuyo nombre haya cambiado. Si encuentra alguno, la clase de prueba falla y muestra un mensaje con la lista de esos archivos, y te pide que los elimines o ejecutes las pruebas con `-Dwebforj.testbed.updateSnapshots=true` para que se eliminen.

:::info Métodos de prueba individuales
La comprobación solo se ejecuta cuando se han ejecutado todas las pruebas de la clase. Si se inicia un único método de prueba desde el IDE, se omite.
:::

## Integración continua {#continuous-integration}

Las instantáneas se escriben localmente y se confirman. Cuando la variable de entorno `CI` está establecida en un valor distinto de `false` o `0`, como ocurre en la mayoría de los servicios de integración continua, TestBed nunca escribe un archivo de instantánea:

- Si falta una instantánea, la prueba falla e indica el archivo esperado, en lugar de escribirlo.
- Si la ejecución se inicia con `-Dwebforj.testbed.updateSnapshots=true`, falla y te pide que actualices las instantáneas localmente.

## Imprimir un componente {#printing-a-component}

`Snapshot.render(component)` devuelve la descripción de un componente como una cadena, sin crear un archivo. Imprímela mientras escribes una prueba para ver qué contiene la página. Omite el estado que mantiene el navegador, como el valor que escribió un usuario, y los componentes ocultos, a menos que las opciones que recibe los incluyan.

## Opciones de instantánea {#snapshot-options}

`SnapshotOptions` determina cuánto incluye una descripción. `setMaxWidth` establece cuántos elementos se muestran debajo de un elemento principal; el valor predeterminado es diez. `setBrowserState` añade el estado que mantiene el navegador, como el valor que escribió un usuario y el foco. `setHiddenComponents` añade los componentes ocultos con el indicador `hidden`, y `setComposites` añade `composite` y `node`.

Una instantánea guardada incluye todo: el estado del navegador, los componentes ocultos y los elementos compuestos. `Snapshot.render` y `new SnapshotOptions()` no incluyen ninguno de ellos. `matchesSnapshot(name, options)` genera la representación usando exactamente las opciones que recibe, así que, para guardar más elementos, establece un `maxWidth` mayor y activa las otras tres opciones. La referencia de <JavadocLink type="automation" location="com/webforj/automation/see/SnapshotOptions" code="true">SnapshotOptions</JavadocLink> describe cada opción.

## Instantáneas de tus propios componentes {#snapshots-for-your-own-components}

Un componente de tu aplicación muestra su tipo, su texto y el estado de cada [interfaz de funcionalidad](/docs/building-ui/component-fundamentals#concern-interfaces) que implementa, como una etiqueta o un tema, sin necesidad de código adicional. El estado que solo conoce tu componente requiere un `SnapshotSerializer`. La siguiente clasificación cuenta las estrellas en las que hace clic el usuario y las devuelve desde `getStars()`:

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

El serializador indica en `getContract()` la clase del componente que describe y añade las estrellas a la línea del componente en `describe`:

```java title="StarRatingSerializer.java" showLineNumbers
import com.webforj.automation.see.SnapshotNode;
import com.webforj.automation.see.SnapshotSerializer;
import com.webforj.samples.views.testbed.StarRating;

public final class StarRatingSerializer
    implements SnapshotSerializer<StarRating> {

  @Override
  public Class<StarRating> getContract() {
    return StarRating.class;
  }

  @Override
  public void describe(StarRating rating, SnapshotNode node) {
    node.setAttribute("stars", rating.getStars());
  }
}
```

El serializador se especifica con su nombre completo en un archivo dentro de los recursos de prueba:

```text title="src/test/resources/META-INF/services/com.webforj.automation.see.SnapshotSerializer"
com.webforj.samples.unit.views.testbed.StarRatingSerializer
```

A partir de entonces, todas las instantáneas de la ejecución de prueba lo utilizan. La siguiente prueba hace clic en cuatro estrellas y comprueba la instantánea:

```java title="StarRatingSnapshotTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.samples.views.testbed.StarRating;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class StarRatingSnapshotTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var rating = new StarRating();
    context.add(rating);

    UserInteraction.click(
        ComponentLocator.of(Button.class).byText("4").locate());

    verifyThat(rating).matchesSnapshot();
  }
}
```

La línea de la clasificación muestra las estrellas:

```snapshot title="StarRatingSnapshotTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- StarRating [composite=Div] [node=div] [stars=4]:
  - Button "1"
  - Button "2"
  - Button "3"
  - Button "4"
  - Button "5"
```

Además de `setAttribute`, el `SnapshotNode` que recibe un serializador permite establecer el texto de la línea y sus indicadores, y añadir líneas debajo de ella para las partes del componente, como las columnas y filas de una tabla. Un serializador solo lee el estado que mantiene el navegador cuando `isBrowserState()` devuelve `true`. La referencia de <JavadocLink type="automation" location="com/webforj/automation/see/SnapshotNode" code="true">SnapshotNode</JavadocLink> enumera todos los métodos.

Los serializadores de tu aplicación se ejecutan después de los de webforJ, por lo que cualquier atributo que establezcan reemplaza el valor que webforJ haya establecido para la misma clave. Si hay dos serializadores para la misma clase de componente, o dos que escriben el mismo atributo en la misma línea, la instantánea falla y muestra un mensaje que indica ambas clases.
