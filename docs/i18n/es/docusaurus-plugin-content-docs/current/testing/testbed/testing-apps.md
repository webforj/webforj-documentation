---
sidebar_position: 6
title: Pruebas de aplicaciones
description: >-
  Start a TestBed test with the routes or the whole app, navigate, test secured
  routes, answer dialogs, control timers, and answer for the browser.
_i18n_hash: 4194d4d355b0e26f86b8c3c53ea353c9
---
Una vista rara vez está aislada. Una ruta lleva a ella, la seguridad de rutas puede protegerla, solicita información al usuario mediante diálogos, se actualiza con un temporizador y pide respuestas al navegador. En una aplicación en ejecución, el servidor, el usuario, el reloj y el navegador desempeñan cada uno su función. En una prueba de TestBed, la prueba desempeña todas esas funciones: decide qué parte de la aplicación se ejecuta, navega como lo haría la barra de direcciones, responde a los diálogos en nombre del usuario, decide cuándo pasa el tiempo y responde en nombre del navegador.

<!-- INTRO_END -->

## Elegir con qué empieza una prueba {#choosing-what-a-test-starts-with}

Los atributos de `@WebforjTest` determinan con qué empieza cada prueba:

- **Sin atributos.** Una página vacía. La prueba añade los componentes que comprueba con `context.add()`, como hacen las pruebas de las demás páginas.
- **`routeClasses`.** Las vistas `@Route` indicadas, cada una en la ruta de su anotación, y nada más de la aplicación.
- **`routePackages`.** Todas las vistas `@Route` de los paquetes indicados, detectadas del mismo modo que `@Routify`.
- **`app`.** La clase de la aplicación, iniciada como en un despliegue. Se ejecuta su método `run()`, su anotación `@Routify` registra sus rutas y se ejecutan sus listeners del ciclo de vida y la seguridad de rutas. En ese caso, `routeClasses` y `routePackages` no tienen ningún efecto.

Empieza con lo mínimo que necesite la prueba. Una prueba de una vista registra esa vista, y una prueba del inicio de la aplicación o de cómo decide la seguridad de rutas inicia la aplicación. `context.getApp()` devuelve la aplicación en ejecución y `context.getFrame()`, el primer frame que creó.

Una prueba que configura el contexto por su cuenta llama a `WebforjTestbed.setup()` con un `WebforjTestbedOptions` que contiene las mismas opciones, y a `WebforjTestbed.tearDown()` al final. Esa clase no lleva `@WebforjTest`. Las opciones también aceptan una función que recibe el `RouteRegistry`, para los registros que no se pueden expresar con la anotación, como una vista en una ruta distinta a la de su anotación.

## Navegar por las rutas {#navigating-routes}

Una prueba llega a una vista como lo hace un usuario: mediante su ruta o haciendo clic en un elemento que navega. La siguiente lista enlaza con dos productos, y la vista del producto muestra el producto que indica su ruta:

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

La prueba registra ambas vistas. La primera prueba hace clic en un producto de la lista, y la segunda navega directamente a un producto:

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

`context.navigate(view)` navega a la ruta de una vista y devuelve la vista que se renderizó. `context.navigate(view, parameters)` completa los parámetros de la ruta, de modo que `id=42` renderiza el producto en `/testbed/products/42`. `context.navigate(location)` recibe una ruta, como `"testbed/products/42"`, y devuelve lo que se haya renderizado en ella.

La navegación ejecuta los mismos [observadores del ciclo de vida de las rutas](/docs/routing/navigation-lifecycle/observers) que una aplicación en ejecución, por lo que se ejecuta `onDidEnter` de la vista del producto antes de que `navigate` devuelva el control. Para cualquier otra función que ofrezca el router, como la navegación que reemplaza la entrada del historial, llama al router de la prueba, que se obtiene con `context.getRouter()`. Su historial también contiene la ubicación actual.

## Probar rutas protegidas {#testing-secured-routes}

Una prueba de seguridad de rutas inicia la aplicación, establece el usuario tal como lo lee la aplicación y navega. En una aplicación con una [implementación de seguridad personalizada](/docs/security/architecture/custom-implementation), la prueba inicia sesión mediante el método de inicio de sesión del propio gestor de seguridad de la aplicación.

:::info La seguridad de rutas requiere la aplicación
La seguridad de rutas se ejecuta cuando una prueba inicia la aplicación con `@WebforjTest(app = ...)`. Con `routeClasses` o `routePackages`, las vistas se renderizan sin seguridad de rutas, de modo que una prueba puede comprobar una vista protegida por separado, sin iniciar sesión.
:::

Cada prueba empieza con una sesión nueva, así que una prueba que no inicia sesión navega como visitante anónimo.

:::tip Aplicaciones Spring Boot
En una aplicación Spring Boot con Spring Security, una prueba establece el usuario con `@WithMockUser`, como se describe en [Pruebas de Spring](/docs/integrations/spring/testing#testing-secured-routes).
:::

### Comprobar las redirecciones {#checking-redirects}

Cuando la seguridad de rutas deniega el acceso, redirige a la ubicación de autenticación o denegación configurada por la aplicación. `navigate(String)` devuelve el componente que se renderizó allí, así que una prueba comprueba la redirección según el tipo de ese componente.

`navigate(Class)` espera la vista solicitada. Si la seguridad de rutas renderiza otra vista, falla con una `IllegalStateException` que indica ambas vistas y remite a `navigate(String)`.

Si se deniega el acceso y la aplicación no configura ninguna ubicación de autenticación o denegación, no se renderiza nada. En ese caso, ambos métodos `navigate` fallan con un mensaje que indica la ruta y que la navegación fue vetada. El mismo error informa de una navegación vetada por un observador del ciclo de vida de las rutas.

## Responder a los diálogos {#answering-dialogs}

Un [diálogo de opciones](/docs/components/option-dialogs/overview) detiene el código que lo mostró hasta que el usuario responde. En una prueba no hay ningún usuario que responda, así que la prueba proporciona la respuesta antes de que el código muestre el diálogo. La siguiente vista pide al usuario que confirme antes de eliminar un informe:

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

La primera prueba responde **OK** antes de hacer clic en **Delete**, y la segunda responde **Cancel**:

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

`context.getShownOptionDialogs()` devuelve todos los diálogos que mostró la aplicación durante la prueba, en orden y con su título y mensaje, por lo que la primera prueba también comprueba qué preguntó el diálogo.

Cada tipo de diálogo tiene su propio método para responder. La siguiente vista pide un nombre nuevo mediante un diálogo de entrada:

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

La prueba responde con el texto que escribiría el usuario:

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

`answerConfirmDialog` recibe el botón que pulsa el usuario, y `answerInputDialog`, el texto que introduce. Los diálogos de archivos reciben la ruta o el nombre de archivo que elige el usuario, mediante `answerFileChooserDialog`, `answerFileSaveDialog` y `answerFileUploadDialog`. Pasar `null` a un diálogo de entrada o de archivo equivale a responder **Cancel**. Un diálogo de mensaje no necesita respuesta, porque el usuario solo puede cerrarlo.

Las respuestas se asignan a los diálogos de su tipo en el orden en que las proporciona la prueba, así que una vista que solicita dos respuestas recibe la segunda respuesta en el segundo diálogo. Si se muestra un diálogo sin una respuesta disponible, la prueba falla con una `IllegalStateException` que indica el diálogo y el método de respuesta que se debe llamar.

## Temporizadores {#timers}

Una prueba no espera a que pase el tiempo. Decide cuándo pasa el tiempo con `context.runIntervals()`. La siguiente vista se actualiza cada cinco segundos:

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

Cada llamada a `runIntervals()` actúa como si hubiera transcurrido una vez el intervalo de cada [temporizador](/docs/advanced/interval) activo y devuelve cuántos se activaron:

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

Un [debouncer](/docs/advanced/debouncing) espera de la misma manera. El siguiente cuadro de búsqueda busca solo cuando el usuario deja de escribir:

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

La prueba escribe dos veces. No se realiza ninguna búsqueda hasta que llama a `runIntervals()`, momento en que el debouncer se ejecuta una sola vez con la última entrada:

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

[`Environment.runLater`](/docs/advanced/asynchronous-updates) no requiere ninguna llamada. Tanto si se llama desde la prueba como desde el código que esta ejecuta, la tarea se ejecuta antes de que el método devuelva el control y su `PendingResult` ya está completado.

## Responder al navegador {#answering-the-browser}

Algunos códigos solicitan un resultado al navegador, como el ancho de la ventana. En una prueba no hay navegador, así que la prueba responde en su nombre. La siguiente vista muestra el ancho que informa el navegador:

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

`answerExecuteJs` proporciona el resultado de la siguiente llamada a `executeJs`, así que la prueba lo proporciona antes de hacer clic:

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

`executeJs` devuelve `null` cuando la prueba no proporcionó ningún resultado. El código que no espera al navegador usa `executeJsAsync`, que se completa más tarde. El siguiente botón copia el enlace de la página y lo confirma cuando el navegador termina:

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

El script se inicia al hacer clic, así que la prueba responde después del clic con `answerExecuteJsAsync`, que completa el script más antiguo que aún espera:

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

`answerExecuteJsAsync` falla con una `IllegalStateException` cuando ningún script está esperando un resultado. Un `Element` espera a que el navegador lo defina, por lo que `whenDefined()` y las llamadas que este encola, como `callJsFunctionAsync`, esperan hasta que la prueba envía un `ElementDefinedEvent` para el elemento mediante `UserInteraction.fireEvent`.

La referencia de <JavadocLink type="testbed-foundation" location="com/webforj/testbed/WebforjTestbed" code="true">WebforjTestbed</JavadocLink> enumera todos los métodos del contexto de prueba.
