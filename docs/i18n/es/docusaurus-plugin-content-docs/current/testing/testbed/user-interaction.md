---
sidebar_position: 3
title: Interacción del usuario
description: >-
  Act on the components of a TestBed test through the same gestures a user
  makes, and get the same events a user causes.
_i18n_hash: 1abb4d1f79dc5eb1556e58df714dfac7
---
Tu código puede llamar a `setValue` o `setEnabled` en cualquier componente, pero un usuario no puede hacerlo. Un usuario escribe en un campo que puede ver y hace clic en un botón habilitado. Una prueba de TestBed actúa como lo hace el usuario, mediante `UserInteraction`. Cada una de sus acciones comprueba que el usuario podría realizar el gesto, lo realiza y permite que el componente lo notifique a sus listeners, tal como lo hace cuando un usuario actúa en el navegador.

Cada acción termina antes de que se ejecute la siguiente línea de la prueba, por lo que esta comprueba el resultado justo después del gesto.

<!-- INTRO_END -->

## Actuar como un usuario {#acting-like-a-user}

El siguiente formulario permite suscribirse con una dirección de correo electrónico. Su botón **Subscribe** permanece deshabilitado hasta que el campo contiene una dirección:

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

La prueba escribe una dirección y hace clic en **Subscribe**, los dos gestos que realiza un usuario:

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

Al escribir, se notifica el cambio al listener de modificación del campo, que habilita el botón. El clic que viene a continuación llega al listener del botón, que muestra la confirmación.

## Acciones {#verbs}

`UserInteraction` tiene una acción para cada gesto que comparten muchos componentes:

- `click` y `doubleClick` pulsan un componente o un elemento que contiene, como una fila de una tabla o un nodo de un árbol.
- `type` reemplaza el texto de un campo; `keypress` pulsa una tecla en él, y `focus` mueve el foco a un componente.
- `check` y `uncheck` marcan y desmarcan una casilla, un botón de opción o un interruptor.
- `select`, `selectIndex`, `selectKey` y `deselect` cambian la selección de listas, tablas, árboles y paneles con pestañas.
- `open`, `close` y `cancel` abren y cierran paneles, diálogos, cajones y menús desplegables, y cancelan un inicio de sesión o una carga.

Una acción notifica lo mismo que notificaría el gesto de un usuario, en el mismo orden. Al seleccionar una pestaña, primero se notifica que la pestaña anterior perdió la selección y luego se notifica la nueva. El botón del ratón, la posición y las teclas modificadoras de un gesto se especifican en `MouseOptions` y `KeyOptions`, el último argumento de `click`, `doubleClick` y `keypress`.

La referencia de <JavadocLink type="automation" location="com/webforj/automation/act/UserInteraction" code="true">UserInteraction</JavadocLink> enumera los componentes que responden a cada acción.

## Los clics llegan a los componentes que lo rodean {#clicks-reach-the-components-around}

En el navegador, un clic llega al componente en el que se hizo clic y luego a todos los componentes que lo rodean; una acción hace lo mismo. En la siguiente tarjeta, solo la tarjeta escucha los clics; el avatar que contiene no lo hace:

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

La prueba hace clic en el avatar, que es donde haría clic un usuario:

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

El clic llega al listener de la tarjeta que rodea el avatar, que muestra el perfil.

## Lo que un usuario no puede hacer {#what-a-user-cant-do}

Una acción solo se ejecuta si un usuario pudiera realizar el gesto. Antes de escribir una dirección, **Subscribe** está deshabilitado en el [formulario de suscripción](#acting-like-a-user), así que la prueba tampoco puede hacer clic en él:

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

El clic lanza una `InteractionRejectedException` y el listener del botón nunca se ejecuta. Su mensaje, que la prueba comprueba, indica el componente y el motivo, seguido del árbol de componentes, para que una prueba fallida muestre qué contenía la página. Se rechaza un gesto cuando el componente o uno de los componentes que lo rodean está deshabilitado u oculto, o se encuentra dentro de un diálogo o cajón cerrado. También se rechaza escribir en un campo de solo lectura, así como cualquier gesto que el componente no permita, como cerrar una alerta que no tiene botón de cierre.

:::tip Componentes que no están adjuntos
Un componente que no está adjunto falla en todas las acciones con una `IllegalStateException` que te pide añadirlo primero al contexto.
:::

## Gestos de un componente {#gestures-of-one-component}

Algunos gestos corresponden a un único componente, como deslizar un control deslizante, ordenar una tabla o expandir un nodo de un árbol. Cada componente de webforJ que admite este tipo de gestos tiene una clase de interacción, y `UserInteraction.perform` ejecuta uno de sus gestos después de realizar la misma comprobación que las acciones. El siguiente control muestra el volumen al que está ajustado su control deslizante:

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

La prueba desliza el control hasta 80 y luego intenta establecerlo en 150, un valor fuera de su rango:

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

Al deslizarlo hasta 80, se llega al listener de deslizamiento, que muestra el nuevo volumen. El intento de deslizarlo hasta 150 se rechaza como una acción, porque ningún usuario puede arrastrar el control más allá del final de su recorrido. El mensaje indica el rango del control deslizante y el valor que solicitó la prueba.

Cada componente de webforJ que admite sus propios gestos tiene una clase de interacción cuyo nombre deriva del del componente, como `SliderInteraction` para `Slider`. Cada método estático de esa clase representa un gesto. Para consultar los gestos que admite un componente, abre el Javadoc de su clase de interacción; por ejemplo, <JavadocLink type="automation" location="com/webforj/automation/act/component/slider/SliderInteraction" code="true">SliderInteraction</JavadocLink> con `slide`, <JavadocLink type="automation" location="com/webforj/automation/act/component/table/TableInteraction" code="true">TableInteraction</JavadocLink> con `sort`, `moveColumn` y `resizeColumn`, o <JavadocLink type="automation" location="com/webforj/automation/act/component/tree/TreeInteraction" code="true">TreeInteraction</JavadocLink> con `expand` y `collapse`. La <JavadocLink type="automation" location="index">referencia de webforj-automation</JavadocLink> los enumera todos, con un paquete por componente dentro de `com.webforj.automation.act.component`.

## Cargas y descargas {#uploads-and-downloads}

Un usuario carga archivos desde su propio equipo, así que la prueba primero los entrega al contexto. La siguiente carga muestra los nombres de los archivos que recibe:

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

La prueba elige dos archivos con `pickFile` y luego los carga por nombre:

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

Los archivos que superen el número máximo permitido, excedan el tamaño máximo o sean de un tipo que la carga no acepte se notifican a los listeners de rechazo y no se cargan.

El contexto conserva los archivos que tu aplicación envía al usuario. El siguiente botón envía un informe:

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

La prueba hace clic en el botón y lee el archivo que recibe el usuario mediante `getDownloads()`:

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

## Eventos sin un gesto {#events-without-a-gesture}

Algunos eventos llegan a un componente después de un gesto, desde el navegador y no desde el usuario, como el aviso de que un cajón terminó de cerrarse. La siguiente vista muestra si su cajón de ayuda está abierto:

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

`close` cierra el cajón y, a continuación, la prueba dispara el evento de cierre que notifica el navegador con `UserInteraction.fireEvent`:

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

`fireEvent` entrega el evento a los listeners del componente y solo comprueba que el componente esté adjunto, ya que el evento no procede del usuario. Los eventos de apertura y cierre de los diálogos, los cajones y el cajón de un diseño de aplicación, así como los eventos de progreso de una carga, son de este tipo. Un `Element` escucha eventos por nombre, y `UserInteraction.fireEvent(element, eventName, data)` entrega un evento a sus listeners con ese nombre.

## Tus propios gestos {#gestures-of-your-own}

`perform` acepta cualquier `Consumer` de un componente, así que una prueba puede definir un gesto propio. La siguiente prueba se suscribe mediante el [formulario de suscripción](#acting-like-a-user) con un gesto que escribe y hace clic dentro del formulario que recibe:

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

La prueba se lee como los pasos que realiza un usuario, y otras pruebas pueden reutilizar el mismo gesto.

## Tus propios componentes {#your-own-components}

Un componente de tu aplicación responde a las acciones de las [interfaces de comportamiento](/docs/building-ui/component-fundamentals#concern-interfaces) que implementa, sin necesidad de código adicional. Un componente cuyo valor es un `Boolean` y que implementa `HasValue` responde a `check`, `uncheck` y `click` como lo hace una casilla. El siguiente interruptor indica si un elemento es favorito:

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

La prueba marca el interruptor y luego hace clic en él:

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

Del mismo modo, un componente que implementa `HasReadOnly` junto con `HasValue` o `HasText` responde a `type`, y uno que implementa las interfaces de selección de webforJ responde a `select`.

### Enseñar las acciones a un componente {#teaching-a-component-the-verbs}

Cuando no hay una interfaz de comportamiento adecuada, una clase de interacción define cómo funciona una acción en tu componente. El siguiente expandidor muestra sus detalles cuando está expandido:

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

La clase de interacción especifica la clase del componente en su constructor e implementa una interfaz por cada acción a la que responde; en este caso, `OpenInteraction` y `CloseInteraction`. `reject` crea la excepción para un gesto que el componente no permite:

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

La clase se registra con su nombre completo en un archivo dentro de los recursos de prueba:

```text title="src/test/resources/META-INF/services/com.webforj.automation.act.ComponentInteraction"
com.webforj.samples.unit.views.testbed.ExpanderInteraction
```

La prueba abre y cierra el expandidor con las acciones. Si vuelve a cerrarlo, se rechaza la acción con el motivo proporcionado por la clase:

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

Una interacción de tu aplicación responde a una acción antes que la que proporciona webforJ para el mismo componente. Si dos interacciones de tu aplicación responden a la misma acción para el mismo componente, la prueba falla con un mensaje que identifica a ambas. Para responder solo a algunas instancias de una clase, sobrescribe `isSupported(component)`.
