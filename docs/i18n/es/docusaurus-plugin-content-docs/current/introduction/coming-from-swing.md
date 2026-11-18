---
title: Si vienes de Swing
sidebar_position: 5
description: >-
  Relaciona conceptos de Swing como JFrame, administradores de diseño,
  listeners, hilo de eventos, modelos de tabla, JOptionPane y CardLayout con sus
  equivalentes en webforJ.
_i18n_title_hash: 4334c9ef532372ad4ec7040646346991
_i18n_description_hash: bb61f39a493a0bd89b7e48af68c639d2
_i18n_hash: a55f88307cad0553b509d1cbb9c3a3f3
---
Esta página relaciona los conceptos de una aplicación de escritorio Java Swing con sus equivalentes en webforJ. En ambos casos, la interfaz de usuario es Java puro: los componentes son objetos Java, los eventos se gestionan mediante listeners y los diseños se componen en código. webforJ se ejecuta en el servidor y el framework mantiene el navegador sincronizado con el estado de los componentes. Consulta [Arquitectura de componentes](/docs/architecture/controls-components) para saber cómo funciona.

La API de webforJ es análoga a Swing, no compatible con ella. Cada pantalla se reescribe con la API de webforJ, mientras que las clases que no dependen de `javax.swing` ni de `java.awt`, como los servicios, las entidades y las reglas de validación, se usan sin cambios desde las nuevas vistas.

## Ventanas y frames {#windows-and-frames}

Una aplicación Swing crea un `JFrame`, agrega contenido y lo muestra. En webforJ, la página del navegador es la ventana. La clase de la aplicación extiende `App`, y el framework proporciona un `Frame` que contiene el contenido visible. Cuando se habilita el enrutamiento mediante `@Routify`, cada pantalla es una clase anotada con `@Route`, y el router la renderiza en el frame.

| Swing | webforJ | Referencia |
| --- | --- | --- |
| `JFrame` | `App` y `Frame`, con pantallas como clases `@Route` | [Aplicaciones enrutables](/docs/routing/routable-apps), [Enrutamiento](/docs/routing/overview) |
| `JFrame.setTitle` | `@FrameTitle` o `HasFrameTitle` | [Títulos de frames de navegación](/docs/routing/navigational-frame-titles) |
| Método principal que construye la interfaz de usuario | Subclase de `App` con `@Routify` | [Conceptos básicos de App](/docs/introduction/basics) |

## Paneles y gestores de diseño {#panels-and-layout-managers}

Un `JPanel` con un gestor de diseño se convierte en un `Composite` vinculado a un componente de diseño. `FlexLayout` organiza los elementos secundarios en una fila o columna, con espaciado, alineación y ajuste de línea, y cubre la mayoría de las pantallas creadas con `BoxLayout` o `FlowLayout`. Un contenedor de ventana `BorderLayout` se corresponde con `AppLayout`, y los demás paneles `BorderLayout` se corresponden con contenedores `FlexLayout` anidados.

| Swing | webforJ | Referencia |
| --- | --- | --- |
| Subclase de `JPanel` | `Composite<T>` vinculado a un contenedor como `FlexLayout` o `Div` | [Composición de componentes](/docs/building-ui/composing-components) |
| `BoxLayout`, `FlowLayout` | `FlexLayout` | [FlexLayout](/docs/components/flex-layout) |
| `BorderLayout` | `AppLayout` para un contenedor de ventana, contenedores `FlexLayout` anidados para los demás paneles | [AppLayout](/docs/components/app-layout), [FlexLayout](/docs/components/flex-layout) |
| Formularios `GridLayout`, `GridBagLayout` | `ColumnsLayout` | [ColumnsLayout](/docs/components/columns-layout) |
| `JSplitPane` | `Splitter` | [Splitter](/docs/components/splitter) |
| `JTabbedPane` | `TabbedPane` | [TabbedPane](/docs/components/tabbedpane) |
| `JToolBar` | `Toolbar` | [Toolbar](/docs/components/toolbar) |
| Ventana principal con encabezado, menú lateral y área de contenido | `AppLayout` | [AppLayout](/docs/components/app-layout) |

Un panel Swing con un campo de texto y un botón que responde a los clics se convierte en la siguiente vista:

```java
@Route("customers/new")
@FrameTitle("New customer")
public class NewCustomerView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField name = new TextField("Name");
  private final Button save = new Button("Save", ButtonTheme.PRIMARY);

  public NewCustomerView() {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    save.onClick(e -> OptionDialog.showMessageDialog("Saved " + name.getValue(), "Customer"));
    self.add(name, save);
  }
}
```

## Componentes {#components}

La mayoría de los componentes Swing tienen un equivalente directo. Los componentes de texto usan `setText()` para su título, y los componentes de entrada usan `getValue()` y `setValue()` para su contenido, tal como se describe en [Uso de componentes](/docs/building-ui/using-components).

| Swing | webforJ | Referencia |
| --- | --- | --- |
| `JButton` | `Button` | [Button](/docs/components/button) |
| `JLabel` | `Label` | [Uso de componentes](/docs/building-ui/using-components) |
| `JTextField` | `TextField` | [TextField](/docs/components/fields/textfield) |
| `JPasswordField` | `PasswordField` | [PasswordField](/docs/components/fields/passwordfield) |
| `JFormattedTextField` | `MaskedTextField` y los demás campos enmascarados | [MaskedTextField](/docs/components/fields/masked/textfield) |
| `JTextArea` | `TextArea` | [TextArea](/docs/components/textarea) |
| `JCheckBox` | `CheckBox` | [CheckBox](/docs/components/checkbox) |
| `JRadioButton` y `ButtonGroup` | `RadioButton` y `RadioButtonGroup` | [RadioButton](/docs/components/radiobutton), [RadioButtonGroup](/docs/components/radiobuttongroup) |
| `JComboBox` | `ChoiceBox` para un conjunto fijo, `ComboBox` cuando los usuarios pueden escribir un valor | [ChoiceBox](/docs/components/lists/choicebox), [ComboBox](/docs/components/lists/combobox) |
| `JList` | `ListBox` | [ListBox](/docs/components/lists/listbox) |
| `JTree` | `Tree` | [Tree](/docs/components/tree) |
| `JProgressBar` | `ProgressBar` | [ProgressBar](/docs/components/progressbar) |
| `JSlider` | `Slider` | [Slider](/docs/components/slider) |

## Listeners y eventos {#listeners-and-events}

Swing registra listeners mediante métodos `addXxxListener`. Los componentes de webforJ exponen el mismo patrón: un método `addXxxListener` y, en la mayoría de los casos, un alias más corto `on`. Cada registro devuelve un `ListenerRegistration`, y al llamar a `remove()` en él se reemplaza el método `removeXxxListener` de Swing. Consulta [Eventos](/docs/building-ui/events).

| Swing | webforJ | Referencia |
| --- | --- | --- |
| `ActionListener` en un botón | `onClick` o `addClickListener` | [Button](/docs/components/button) |
| `DocumentListener` en un campo de texto | `onModify` | [Eventos](/docs/building-ui/events) |
| `ItemListener` en un `JCheckBox` | `onToggle` | [CheckBox](/docs/components/checkbox) |
| `ChangeListener` y listeners de cambios de propiedades en valores | `onValueChange` | [Eventos](/docs/building-ui/events) |
| `FocusListener` | `onFocus` y `onBlur` | [Eventos](/docs/building-ui/events) |
| `KeyListener` en un campo | `onKeypress` | [Eventos](/docs/building-ui/events) |
| `removeXxxListener` | `ListenerRegistration.remove()` | [Eliminar un evento](/docs/building-ui/events#removing-an-event) |

## El hilo de despacho de eventos y las actualizaciones de la interfaz de usuario {#event-dispatch-thread-and-ui-updates}

Swing requiere que todo el trabajo de interfaz de usuario se realice en el hilo de despacho de eventos. webforJ sigue la misma regla: cada sesión de usuario tiene un hilo `Environment`, y todas las actualizaciones de la interfaz de usuario deben realizarse en él. Los constructores, los controladores de eventos y los métodos del ciclo de vida ya se ejecutan en ese hilo. Un hilo en segundo plano debe devolverle los cambios de la interfaz mediante `Environment.runLater(Environment, Runnable)`.

Obtén el destino con `Environment.getCurrent()` en el hilo `Environment`, antes de iniciar el trabajo en segundo plano. En un hilo en segundo plano, `getCurrent()` devuelve `null`. Consulta [Actualizaciones asíncronas](/docs/advanced/asynchronous-updates) para conocer el modelo de hilos y la cancelación de tareas mediante `PendingResult`.

| Swing | webforJ | Referencia |
| --- | --- | --- |
| Hilo de despacho de eventos | Hilo `Environment` de la sesión | [Actualizaciones asíncronas](/docs/advanced/asynchronous-updates#understanding-the-thread-model) |
| `SwingUtilities.invokeLater` | `Environment.runLater(Environment, Runnable)` | [Actualizaciones asíncronas](/docs/advanced/asynchronous-updates#passing-the-target) |
| `SwingWorker` | Trabajo en segundo plano, por ejemplo con `CompletableFuture`, seguido de `Environment.runLater` | [Actualizaciones asíncronas](/docs/advanced/asynchronous-updates) |
| `javax.swing.Timer` | `Interval` | [Interval](/docs/advanced/interval) |
| Reiniciar un temporizador con cada pulsación de tecla | `Debouncer` | [Debouncing](/docs/advanced/debouncing) |

El cuerpo de `doInBackground` de un `SwingWorker` se convierte en la tarea en segundo plano, y el método `done` se convierte en los bloques `runLater`: uno para el resultado y otro para un error:

```java
public class ReportView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final CustomerService service = new CustomerService();
  private final Button export = new Button("Export");
  private final Label status = new Label();

  public ReportView() {
    self.add(export, status);
    export.onClick(e -> {
      Environment env = Environment.getCurrent();
      CompletableFuture.supplyAsync(service::exportReport)
          .thenAccept(path -> Environment.runLater(env, () -> { status.setText("Saved to " + path); }))
          .exceptionally(error -> {
            Environment.runLater(env, () -> { status.setText("Export failed"); });
            return null;
          });
    });
  }
}
```

## Modelos y vinculación de datos {#models-and-data-binding}

Swing separa los datos de las vistas mediante interfaces de modelo como `TableModel` y `ListModel`, y código que copia manualmente los valores entre los campos y los objetos. webforJ usa un `Repository` como fuente de datos para componentes como `Table`, y un `BindingContext` para conectar los campos de un formulario con las propiedades de un bean en ambas direcciones.

| Swing | webforJ | Referencia |
| --- | --- | --- |
| `TableModel` | `Repository`, como `CollectionRepository` | [Repository](/docs/advanced/repository/overview) |
| `ListModel` | Entradas `ListItem` agregadas a un `ListBox`, `ChoiceBox` o `ComboBox` | [Listas](/docs/components/lists/overview) |
| `fireTableDataChanged`, `fireTableRowsUpdated` | `commit()` y `commit(entity)` en el repositorio | [Sincronización de datos](/docs/advanced/repository/overview#data-synchronization) |
| Copiar valores de campos a un objeto y viceversa | `BindingContext` con `read(bean)` y `write(bean)` | [Vinculación de datos](/docs/data-binding/overview), [Vinculación automática](/docs/data-binding/automatic-binding) |
| `InputVerifier` | Validadores en las vinculaciones o Jakarta Validation | [Validación](/docs/data-binding/validation/overview), [Jakarta Validation](/docs/data-binding/validation/jakarta-validation) |

`BindingContext.of` vincula los campos de los componentes con las propiedades del bean que tienen el mismo nombre:

```java
public class CustomerForm extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField name = new TextField("Name");
  private final TextField email = new TextField("Email");
  private final BindingContext<Customer> context;

  public CustomerForm(Customer customer) {
    self.setDirection(FlexDirection.COLUMN).add(name, email);
    context = BindingContext.of(this, Customer.class);
    context.read(customer);
  }

  public boolean save(Customer customer) {
    return context.write(customer).isValid();
  }
}
```

## Diálogos {#dialogs}

Los diálogos de opciones son modales y bloquean la ejecución hasta que el usuario responde, como `JOptionPane`. El código que sigue a la llamada lee directamente el resultado, sin necesidad de un callback. Consulta [Diálogos de opciones](/docs/components/option-dialogs/overview).

| Swing | webforJ | Referencia |
| --- | --- | --- |
| `JOptionPane.showMessageDialog` | `OptionDialog.showMessageDialog` | [Diálogo de mensaje](/docs/components/option-dialogs/message) |
| `JOptionPane.showConfirmDialog` | `OptionDialog.showConfirmDialog`, que devuelve `ConfirmDialog.Result` | [Diálogo de confirmación](/docs/components/option-dialogs/confirm) |
| `JOptionPane.showInputDialog` | `OptionDialog.showInputDialog` | [Diálogo de entrada](/docs/components/option-dialogs/input) |
| `JFileChooser` para abrir un archivo en el equipo del usuario | `OptionDialog.showFileUploadDialog` | [Diálogo de carga de archivos](/docs/components/option-dialogs/file-upload) |
| `JFileChooser` para explorar los archivos del servidor | `OptionDialog.showFileChooserDialog` | [Diálogo del selector de archivos](/docs/components/option-dialogs/file-chooser) |
| `JDialog` con contenido personalizado | `Dialog`, que se abre con `open()` y se cierra con `close()` | [Dialog](/docs/components/dialog) |

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "Delete this customer?", "Delete customer",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);

if (result == ConfirmDialog.Result.YES) {
  service.delete(customer);
}
```

## Tablas {#tables}

Un `JTable` lee los datos de una subclase de `TableModel`. Un `Table<T>` de webforJ contiene una lista de entidades y vincula cada columna a un getter, por lo que no hace falta escribir una clase de modelo. `setItems` envuelve una colección en un repositorio, y `setRepository` acepta uno respaldado por la capa de datos. Consulta [Table](/docs/components/table/overview).

| Swing | webforJ | Referencia |
| --- | --- | --- |
| `JTable` con un `TableModel` | `Table<T>` con `setItems` o `setRepository` | [Table](/docs/components/table/overview) |
| `TableColumn` y `getValueAt` | `addColumn(id, getter)` | [Columnas](/docs/components/table/columns) |
| `ListSelectionModel` | `setSelectionMode`, `getSelectedItems`, `onItemSelect` | [Selección](/docs/components/table/selection) |
| `MouseListener` que detecta un doble clic | `onItemDoubleClick` | <JavadocLink type="table" location="com/webforj/component/table/Table" code="true">Table</JavadocLink> |
| `TableRowSorter` | `Column.setSortable(true)` | [Ordenación](/docs/components/table/sorting) |
| `RowFilter` | `setBaseFilter(Predicate)` en un `CollectionRepository`, aplicado al llamar a `commit()` | [Filtrado](/docs/components/table/filtering) |
| `TableCellRenderer` | `Renderer` configurado con `Column.setRenderer` | [Representación](/docs/components/table/rendering) |

```java
Table<Customer> table = new Table<>();
table.addColumn("Name", Customer::getName);
table.addColumn("Email", Customer::getEmail);
table.setSelectionMode(Table.SelectionMode.SINGLE);
table.setItems(service.findAll());
table.onItemDoubleClick(e -> confirmDelete(e.getItem()));
```

## Navegación {#navigation}

Las aplicaciones Swing cambian de pantalla al intercambiar paneles, a menudo mediante `CardLayout`, o al abrir ventanas nuevas. En webforJ, cada pantalla es una ruta con su propia URL, por lo que funcionan el historial del navegador, los marcadores y los enlaces profundos. Consulta [Enrutamiento](/docs/routing/overview).

| Swing | webforJ | Referencia |
| --- | --- | --- |
| `CardLayout.show` o reemplazar el panel de contenido | Vistas `@Route` y `Router.getCurrent().navigate(View.class)` | [Definición de rutas](/docs/routing/defining-routes), [Navegación entre rutas](/docs/routing/route-navigation) |
| `JMenu` o una lista lateral de pantallas | `AppNav` con entradas `AppNavItem` vinculadas a rutas | [AppNav](/docs/components/appnav) |
| Abrir una pantalla con parámetros | Parámetros de ruta y parámetros de consulta | [Patrones de rutas](/docs/routing/route-patterns), [Parámetros de consulta](/docs/routing/query-parameters) |

```java
Router.getCurrent().navigate(NewCustomerView.class);
```

## Migrar pantalla por pantalla {#moving-screen-by-screen}

No es necesario reescribir toda la aplicación Swing de una vez. Webswing ejecuta una aplicación Swing, JavaFX o SWT existente en el navegador sin modificar su código fuente, y el componente `WebswingConnector` la integra en una vista de webforJ. Solo hace falta agregar un pequeño componente a la aplicación Swing para permitir la comunicación en ambas direcciones, como se describe a continuación. Así, las pantallas antiguas y las nuevas conviven en una sola aplicación mientras se migran una por una.

El conector permite la comunicación en ambas direcciones. `performAction` envía acciones de webforJ a la aplicación Swing, y `onAction` recibe las acciones que la aplicación Swing envía mediante la API de Java de Webswing. Para enviar acciones de vuelta, hay que agregar esa API a la aplicación Swing.

En el lado de Swing, la dependencia `webswing-api` del repositorio de Webswing proporciona `WebswingUtil.getWebswingApi()`. Este método devuelve `null` cuando la aplicación se ejecuta como una aplicación de escritorio normal, por lo que el mismo código sigue funcionando en el escritorio. En este ejemplo, al hacer doble clic en una fila de la tabla, se envía el cliente seleccionado a webforJ en vez de abrir un diálogo Swing:

```java
WebswingApi api = WebswingUtil.getWebswingApi();

if (api != null) {
  api.sendActionEvent("select-customer", gson.toJson(customer), null);
} else {
  showEditDialog(customer);
}
```

- [Webswing](/docs/integrations/webswing/overview) explica la arquitectura y la modernización progresiva.
- [Configuración](/docs/integrations/webswing/setup) describe el servidor Webswing y CORS.
- [Comunicación](/docs/integrations/webswing/communication) explica `performAction` y `onAction`.
- [Tutorial de modernización](/docs/integrations/webswing/tutorial) reemplaza un diálogo de edición Swing por un formulario de webforJ mientras se conserva la tabla Swing.
