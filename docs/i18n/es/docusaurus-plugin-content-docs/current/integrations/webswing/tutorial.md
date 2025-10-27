---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: 32805132a2cf7b320864275fbbae7889
---
Este tutorial muestra cómo modernizar una aplicación Java Swing existente integrándola con webforJ usando el `WebswingConnector`. Aprenderás a hacer que una aplicación de escritorio tradicional sea accesible a través de la web y a añadir características modernas de la web, como diálogos basados en la web y formularios interactivos, utilizando componentes de webforJ.

:::tip Código fuente
El código fuente completo de este tutorial está disponible en GitHub: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

## El escenario

Imagina que tienes una aplicación de gestión de clientes construida con Swing que ha estado en producción durante años. Funciona bien, pero los usuarios ahora esperan acceso a la web y una interfaz moderna. En lugar de reescribir desde cero, utilizarás Webswing para hacerla accesible a la web de inmediato y luego irás añadiendo características modernas de la web, como diálogos y formularios, utilizando componentes de webforJ.

## Punto de partida: la aplicación Swing

La aplicación Swing de ejemplo es una tabla de clientes con operaciones CRUD típicas. Al igual que muchas aplicaciones empresariales de Swing, sigue patrones estándar:

```java
public class Application {
  private List<Customer> customers;
  private DefaultTableModel model;
  private JTable table;

  private void createTable() {
    String[] columnNames = { "Nombre", "Empresa", "Correo electrónico" };
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
        "Correo electrónico:", emailField
    };

    int result = JOptionPane.showConfirmDialog(null, fields, "Editar Cliente",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
  }
}
```

Esta aplicación funciona perfectamente como una aplicación de escritorio, pero carece de accesibilidad web. Los usuarios deben instalar Java y ejecutar el archivo JAR localmente.

## Paso 1: haciéndola consciente de Webswing

El primer paso es hacer que la aplicación Swing detecte si se está ejecutando bajo Webswing. Esto le permite adaptar su comportamiento sin romper la compatibilidad de escritorio.

### Detección del entorno Webswing

Agrega la dependencia de la API de Webswing a tu proyecto Swing:

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Luego modifica tu aplicación para detectar el entorno de ejecución de Webswing:

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

La clave aquí es que `WebswingUtil.getWebswingApi()` devuelve `null` al ejecutarse como una aplicación de escritorio regular, lo que te permite mantener la compatibilidad en modo dual.

### Adaptando el comportamiento para la implementación web

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

Al ramificar el comportamiento de acuerdo al valor de `isWebswing`, el código puede manejar ambos entornos.

## Paso 2: creando el envoltorio de webforJ

Ahora que la aplicación Swing puede comunicarse a través de eventos, crea una aplicación de webforJ que incruste la aplicación Swing y añada características modernas de la web, como diálogos y formularios basados en la web.

### Configurando el conector

El componente `WebswingConnector` incrusta tu aplicación alojada en Webswing dentro de una vista de webforJ:

```java
@Route("/")
public class CustomerTableView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public CustomerTableView(@Value("${webswing.connector.url}") String webswingUrl) {
    WebswingConnector connector = new WebswingConnector(webswingUrl);
    connector.setSize("100vw", "100vh");

    self.add(connector);
  }
}
```

El conector se conecta a tu servidor Webswing, estableciendo un canal de comunicación bidireccional.

### Manejando eventos desde Swing

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

## Paso 3: comunicación bidireccional

La integración se vuelve poderosa cuando la comunicación fluye en ambas direcciones. La aplicación de webforJ puede enviar actualizaciones de vuelta a la aplicación Swing, manteniendo ambas interfaces de usuario sincronizadas.

### Enviando actualizaciones a Swing

Después de que el usuario edita un cliente en el diálogo de webforJ:

```java
dialog.onSave(() -> {
  // Enviar cliente actualizado de vuelta a Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Procesando actualizaciones en Swing

La aplicación Swing escucha estas actualizaciones y refresca su visualización:

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

## Beneficios de arquitectura

Este enfoque ofrece varias ventajas sobre una reescritura completa:

### Implementación web inmediata

Tu aplicación Swing se vuelve accesible a la web de inmediato sin cambios en el código. Los usuarios pueden acceder a ella a través de un navegador mientras trabajas en mejoras.

### Mejora progresiva

Comienza reemplazando solo el diálogo de edición y luego reemplaza gradualmente más componentes:

1. **Fase 1**: Incrustar toda la aplicación Swing, reemplazar solo el diálogo de edición
2. **Fase 2**: Agregar navegación y menús de webforJ alrededor de la aplicación incrustada
3. **Fase 3**: Reemplazar la tabla con una tabla de webforJ, manteniendo Swing para características irremplazables
4. **Fase 4**: Finalmente reemplazar todos los componentes de Swing

### Mitigación de riesgos

Dado que la aplicación Swing original sigue siendo funcional, puedes:

- Volver a la implementación de escritorio si es necesario
- Probar nuevas características junto con las existentes
- Migrar usuarios gradualmente
- Mantener la misma lógica de negocio
