---
title: Tutorial de modernización
sidebar_position: 4
description: >-
  Modernize a Swing customer management app step by step by embedding it with
  WebswingConnector and layering in webforJ dialogs and forms.
_i18n_hash: ded3a5a3abe3905086532c58861652f8
---
Este tutorial explica cómo modernizar una aplicación Java Swing existente integrándola con webforJ mediante `WebswingConnector`. Aprenderás a hacer que una aplicación de escritorio tradicional sea accesible desde la web y a añadir gradualmente funciones web modernas, como cuadros de diálogo web y formularios interactivos que utilizan componentes de webforJ.

:::note Requisitos previos
Antes de empezar este tutorial, completa los pasos de [Configuración](./setup) para configurar el servidor Webswing y los ajustes de CORS.
:::

:::tip Código fuente
El código fuente completo de este tutorial está disponible en GitHub: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<VideoPlayer video="webswing/modernization-tutorial" width={1920} height={1080} title="Modernization tutorial" />

## El escenario {#the-scenario}

Imagina que tienes una aplicación de gestión de clientes creada con Swing que lleva años en producción. Funciona bien, pero ahora los usuarios esperan poder acceder a ella desde la web y contar con una interfaz moderna. En lugar de reescribirla desde cero, usarás Webswing para hacerla accesible desde la web de inmediato y, luego, añadirás gradualmente funciones web modernas, como cuadros de diálogo y formularios web que utilizan componentes de webforJ.

## Punto de partida: la aplicación Swing {#starting-point-the-swing-app}

La aplicación Swing de ejemplo es una tabla de clientes con las operaciones CRUD habituales. Como muchas aplicaciones Swing empresariales, sigue patrones estándar:

```java
public class Application {
  private List<Customer> customers;
  private DefaultTableModel model;
  private JTable table;

  private void createTable() {
    String[] columnNames = { "Name", "Company", "Email" };
    model = new DefaultTableModel(columnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    table = new JTable(model);
    table.setRowHeight(30);
    table.setRowSelectionAllowed(true);

    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          // Handle double-click to edit
        }
      }
    });
  }

  private void showEditDialog(Customer customer) {
    JTextField nameField = new JTextField(customer.getName());
    JTextField companyField = new JTextField(customer.getCompany());
    JTextField emailField = new JTextField(customer.getEmail());

    Object[] fields = {
        "Name:", nameField,
        "Company:", companyField,
        "Email:", emailField
    };

    int result = JOptionPane.showConfirmDialog(null, fields, "Edit Customer",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
  }
}
```

Esta aplicación funciona perfectamente como aplicación de escritorio, pero no es accesible desde la web. Los usuarios deben instalar Java y ejecutar el archivo JAR localmente.

## Paso 1: hacer que sea compatible con Webswing {#step-1-making-it-webswing-aware}

El primer paso es hacer que la aplicación Swing detecte si se está ejecutando en Webswing. Esto le permite adaptar su comportamiento sin perder la compatibilidad con el escritorio.

### Detectar el entorno de Webswing {#detecting-the-webswing-environment}

Añade la dependencia de la API de Webswing a tu proyecto Swing:

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Luego, modifica la aplicación para detectar el entorno de ejecución de Webswing:

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

La clave es que `WebswingUtil.getWebswingApi()` devuelve `null` cuando la aplicación se ejecuta como una aplicación de escritorio normal, lo que permite mantener la compatibilidad con ambos modos.

### Adaptar el comportamiento para la implementación web {#adapting-behavior-for-web-deployment}

Con la detección lista, ya puedes adaptar el comportamiento de la aplicación. El cambio más importante afecta a cómo se gestionan las interacciones del usuario:

```java
private void handleDoubleClick(MouseEvent e) {
  int row = table.rowAtPoint(e.getPoint());
  if (row >= 0 && row < customers.size()) {
    Customer customer = customers.get(row);

    if (isWebswing) {
      api.sendActionEvent("select-customer", gson.toJson(customer), null);
    } else {
      showEditDialog(customer);
    }
  }
}
```

Al ramificar el comportamiento según el valor de `isWebswing`, la base de código puede funcionar en ambos entornos.

## Paso 2: crear el contenedor de webforJ {#step-2-creating-the-webforj-wrapper}

Ahora que la aplicación Swing puede comunicarse mediante eventos, crea una aplicación webforJ que incorpore la aplicación Swing y añada funciones web modernas, como cuadros de diálogo y formularios web.

### Configurar el conector {#setting-up-the-connector}

El componente `WebswingConnector` incorpora la aplicación alojada en Webswing en una vista de webforJ:

```java
@Route("/")
public class CustomerTableView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public CustomerTableView(@Value("${webswing.connector.url}") String webswingUrl) {
    WebswingConnector connector = new WebswingConnector(webswingUrl);
    connector.setSize("100vw", "100vh");

    self.add(connector);
  }
}
```

El conector se conecta al servidor Webswing y establece un canal de comunicación bidireccional.

### Gestionar eventos de Swing {#handling-events-from-swing}

Cuando la aplicación Swing envía eventos, por ejemplo, cuando un usuario hace doble clic en una fila, el conector los recibe:

```java
connector.onAction(event -> {
  switch (event.getActionName()) {
    case "select-customer":
      event.getActionData().ifPresent(data -> {
        JsonObject customer = JsonParser.parseString(data).getAsJsonObject();
        CustomerForm dialog = new CustomerForm(customer);
        self.add(dialog);
        dialog.onSave(() -> {
          Gson gson = new Gson();
          connector.performAction("update-customer", gson.toJson(customer));
        });
      });
      break;
  }
});
```

Ahora, en lugar del cuadro de diálogo de Swing, los usuarios verán un formulario web moderno creado con componentes de webforJ.

## Paso 3: comunicación bidireccional {#step-3-bidirectional-communication}

La integración cobra todo su potencial cuando la comunicación fluye en ambas direcciones. La aplicación webforJ puede enviar actualizaciones a la aplicación Swing para mantener sincronizadas ambas interfaces.

### Enviar actualizaciones a Swing {#sending-updates-to-swing}

Después de que el usuario edite un cliente en el cuadro de diálogo de webforJ:

```java
dialog.onSave(() -> {
  // Send updated customer back to Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Procesar actualizaciones en Swing {#processing-updates-in-swing}

La aplicación Swing escucha estas actualizaciones y refresca su vista:

```java
private void setupWebswingListeners() {
  api.addBrowserActionListener(event -> {
    if ("update-customer".equals(event.getActionName())) {
      Customer updated = gson.fromJson(event.getData(), Customer.class);
      updateCustomer(updated);
    }
  });
}
```

## Ventajas de la arquitectura {#architecture-benefits}

Este enfoque ofrece varias ventajas frente a una reescritura completa:

### Implementación web inmediata {#immediate-web-deployment}

Tu aplicación Swing estará disponible en la web de inmediato, sin cambios en el código. Los usuarios podrán acceder a ella desde un navegador mientras trabajas en las mejoras.

### Mejora progresiva {#progressive-enhancement}

Empieza por reemplazar solo el cuadro de diálogo de edición y, luego, sustituye gradualmente más componentes:

1. **Fase 1**: incorpora toda la aplicación Swing y reemplaza solo el cuadro de diálogo de edición
2. **Fase 2**: añade navegación y menús de webforJ alrededor de la aplicación incorporada
3. **Fase 3**: reemplaza la tabla con una tabla de webforJ y conserva Swing para las funciones irremplazables
4. **Fase 4**: reemplaza finalmente todos los componentes de Swing

### Mitigación de riesgos {#risk-mitigation}

Como la aplicación Swing original sigue funcionando, puedes:

- Volver a la implementación de escritorio si es necesario
- Probar las nuevas funciones junto con las existentes
- Migrar a los usuarios gradualmente
- Mantener la misma lógica de negocio
