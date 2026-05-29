---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: b938ea9adf24f0f2624f22a1a012d0cd
---
Este tutorial te guiará a modernizar una aplicación Java Swing existente integrándola con webforJ utilizando el `WebswingConnector`. Aprenderás cómo hacer que una aplicación de escritorio tradicional sea accesible por la web y agregar de manera incremental características modernas de la web, como diálogos basados en la web y formularios interactivos utilizando componentes de webforJ.

:::note Prerrequisitos
Antes de comenzar este tutorial, completa los pasos de [Configuración y Preparación](./setup) para configurar tu servidor Webswing y los ajustes de CORS.
:::

:::tip Código fuente
El código fuente completo de este tutorial está disponible en GitHub: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/webswing/modernization-tutorial.mp4#t=5" type="video/mp4"/>
  </video>
</div>

## El escenario {#the-scenario}

Imagina que tienes una aplicación de gestión de clientes construida con Swing que ha estado en producción durante años. Funciona bien, pero ahora los usuarios esperan acceso web y una interfaz moderna. En lugar de reescribir desde cero, usarás Webswing para hacerla accesible por la web de inmediato y luego agregar de manera incremental características modernas de la web, como diálogos y formularios basados en la web utilizando componentes de webforJ.

## Punto de partida: la aplicación Swing {#starting-point-the-swing-app}

La aplicación Swing de ejemplo es una tabla de clientes con operaciones CRUD típicas. Como muchas aplicaciones empresariales de Swing, sigue patrones estándar:

```java
public class Application {
  private List<Customer> customers;
  private DefaultTableModel model;
  private JTable table;

  private void createTable() {
    String[] columnNames = { "Nombre", "Empresa", "Correo" };
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
          // Manejar doble clic para editar
        }
      }
    });
  }

  private void showEditDialog(Customer customer) {
    JTextField nameField = new JTextField(customer.getName());
    JTextField companyField = new JTextField(customer.getCompany());
    JTextField emailField = new JTextField(customer.getEmail());

    Object[] fields = {
        "Nombre:", nameField,
        "Empresa:", companyField,
        "Correo:", emailField
    };

    int result = JOptionPane.showConfirmDialog(null, fields, "Editar Cliente",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
  }
}
```

Esta aplicación funciona perfectamente como una aplicación de escritorio, pero carece de accesibilidad web. Los usuarios deben instalar Java y ejecutar el archivo JAR localmente.

## Paso 1: hacerlo consciente de Webswing {#step-1-making-it-webswing-aware}

El primer paso es hacer que la aplicación Swing detecte si está en ejecución bajo Webswing. Esto le permite adaptar su comportamiento sin romper la compatibilidad de escritorio.

### Detectar el entorno Webswing {#detecting-the-webswing-environment}

Agrega la dependencia de la API de Webswing a tu proyecto Swing:

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Luego modifica tu aplicación para detectar el tiempo de ejecución de Webswing:

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

La clave aquí es que `WebswingUtil.getWebswingApi()` devuelve `null` cuando se ejecuta como una aplicación de escritorio normal, lo que permite mantener la compatibilidad dual.

### Adaptar el comportamiento para la implementación web {#adapting-behavior-for-web-deployment}

Con la detección en su lugar, ahora puedes adaptar el comportamiento de la aplicación. El cambio más importante es cómo se manejan las interacciones del usuario:

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

Al ramificar el comportamiento de acuerdo con el valor de `isWebswing`, la base de código puede manejar ambos entornos.

## Paso 2: crear el envoltorio de webforJ {#step-2-creating-the-webforj-wrapper}

Ahora que la aplicación Swing puede comunicarse a través de eventos, crea una aplicación webforJ que embebe la aplicación Swing y agrega características modernas de la web, como diálogos y formularios basados en la web.

### Configurando el conector {#setting-up-the-connector}

El componente `WebswingConnector` embebe tu aplicación alojada en Webswing dentro de una vista de webforJ:

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

El conector se conecta a tu servidor Webswing, estableciendo un canal de comunicación bidireccional.

### Manejo de eventos desde Swing {#handling-events-from-swing}

Cuando la aplicación Swing envía eventos (como cuando un usuario hace doble clic en una fila), el conector los recibe:

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

Ahora, en lugar del diálogo de Swing, los usuarios ven un formulario web moderno construido con componentes de webforJ.

## Paso 3: comunicación bidireccional {#step-3-bidirectional-communication}

La integración se vuelve poderosa cuando la comunicación fluye en ambas direcciones. La aplicación webforJ puede enviar actualizaciones de vuelta a la aplicación Swing, manteniendo sincronizadas ambas interfaces.

### Enviando actualizaciones a Swing {#sending-updates-to-swing}

Después de que el usuario edita un cliente en el diálogo de webforJ:

```java
dialog.onSave(() -> {
  // Enviar el cliente actualizado de vuelta a Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Procesando actualizaciones en Swing {#processing-updates-in-swing}

La aplicación Swing escucha estas actualizaciones y refresca su display:

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

## Beneficios de la arquitectura {#architecture-benefits}

Este enfoque ofrece varias ventajas sobre una reescritura completa:

### Implementación web inmediata {#immediate-web-deployment}

Tu aplicación Swing se vuelve accesible por la web de inmediato sin cambios en el código. Los usuarios pueden acceder a ella a través de un navegador mientras trabajas en mejoras.

### Mejora progresiva {#progressive-enhancement}

Comienza reemplazando solo el diálogo de edición, luego reemplaza gradualmente más componentes:

1. **Fase 1**: Ember la aplicación Swing completa, reemplaza solo el diálogo de edición
2. **Fase 2**: Agregar navegación y menús de webforJ alrededor de la aplicación embebida
3. **Fase 3**: Reemplazar la tabla con una tabla de webforJ, manteniendo Swing para características insustituibles
4. **Fase 4**: Finalmente reemplazar todos los componentes Swing

### Mitigación de riesgos {#risk-mitigation}

Dado que la aplicación Swing original sigue siendo funcional, puedes:

- Retroceder a la implementación de escritorio si es necesario
- Probar nuevas características junto a las existentes
- Migrar a los usuarios gradualmente
- Mantener la misma lógica de negocio
