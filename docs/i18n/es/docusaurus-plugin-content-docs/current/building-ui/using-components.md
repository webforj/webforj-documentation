---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: 046749107d0e78ccfaab4017d4e374d1
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Los componentes son los bloques de construcción de las aplicaciones webforJ. Ya sea que esté utilizando componentes integrados como `Button` y `TextField`, o trabajando con componentes personalizados proporcionados por su equipo, la forma en que interactúa con ellos sigue el mismo modelo consistente: configura propiedades, gestiona el estado y compone componentes en diseños.

Esta guía se centra en esas operaciones cotidianas: no en los detalles internos de cómo funcionan los componentes, sino en cómo hacer cosas con ellos en la práctica.

## Propiedades del componente {#component-properties}

Cada componente expone propiedades que controlan su contenido, apariencia y comportamiento. La mayoría de estas tienen métodos de Java tipados y dedicados (`setText()`, `setTheme()`, `setExpanse()`, y así sucesivamente), que es la forma principal en que configurará componentes en webforJ. Las secciones a continuación cubren las propiedades y métodos que se aplican de manera amplia a través de los tipos de componentes.

### Contenido de texto {#text-content}

El método `setText()` establece el texto visible de un componente como caracteres literales, como el título en un `Button` o el contenido de un `Label`. Para componentes de entrada como `TextField`, utilice `setValue()` en su lugar para establecer el valor actual del campo.

```java
Button button = new Button();
button.setText("Haga clic en mí");

Label label = new Label();
label.setText("Estado: listo");

TextField field = new TextField();
field.setValue("Valor inicial");
```

El marcado escrito con `setText()` aparece como esos caracteres y nunca se ejecuta, lo que evita que el texto que proviene de la entrada del usuario o de datos externos se interprete como marcado en vivo.

```java
// Mostrado como los caracteres literales "<b>Estado: listo</b>"
component.setText("<b>Estado: listo</b>");
```

:::note Uso de la etiqueta `<html>`
Las versiones anteriores de webforJ trataban un valor envuelto en `<html>` y pasado a `setText()` como HTML. Este comportamiento está en desuso y se eliminará en webforJ 27.00.

La primera vez que un valor envuelto en `<html>` llega a `setText()`, se registra una advertencia que nombra el componente y el sitio de llamada, para que la llamada pueda trasladarse a `setHtml()`.

Para adoptar la configuración predeterminada de webforJ 27.00 por adelantado, establezca `webforj.legacyHtmlInText` en `false`. En una aplicación de Spring, el mismo valor se establece a través de `webforj.legacy-html-in-text`.

```java
// webforj.legacyHtmlInText = true (predeterminado)
component.setText("<html><b>Estado: listo</b></html>"); // renderiza en negrita

// webforj.legacyHtmlInText = false
component.setText("<html><b>Estado: listo</b></html>"); // muestra los caracteres <b>Estado: listo</b>
```
:::

### Renderizando HTML {#rendering-html}

Algunos componentes también admiten `setHtml()` para casos en los que necesita renderizar marcado HTML en línea en el contenido:

```java
Div container = new Div();
container.setHtml("<strong>Texto en negrita</strong> y <em>texto en cursiva</em>");
```

:::danger Ataques de scripting entre sitios (XSS)
Como precaución contra [ataques de scripting entre sitios (XSS)](/docs/security/application-security/common-threats#cross-site-scripting-xss), utilice `setHtml()` solo con contenido que controle directamente.
:::

### Atributos HTML {#html-attributes}

La mayoría de la configuración en webforJ se realiza a través de métodos de Java tipados en lugar de atributos HTML en bruto. Sin embargo, `setAttribute()` es útil para pasar atributos de accesibilidad que no tienen una API dedicada:

```java
Button button = new Button("Enviar");
button.setAttribute("aria-label", "Enviar el formulario");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Verificar el soporte del componente
No todos los componentes admiten atributos arbitrarios. Esto depende de la implementación subyacente del componente.
:::

### IDs del componente {#component-ids}

Puede asignar un ID al elemento HTML de un componente usando `setAttribute()`:

```java
Button submitButton = new Button("Enviar");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("Email");
emailField.setAttribute("id", "email-input");
```

Los IDs del DOM se utilizan comúnmente para selectores de prueba y objetivo CSS en sus hojas de estilo.

:::tip Preferir clases para la selección de múltiples componentes
A diferencia de las clases CSS, los IDs deben ser únicos dentro de su aplicación. Si necesita seleccionar múltiples componentes, utilice `addClassName()` en su lugar.
:::

:::info IDs gestionados por el framework
webforJ también asigna identificadores automáticos a los componentes internamente. El ID del lado del servidor (accedido a través de `getComponentId()`) se usa para el seguimiento del framework, mientras que el ID del lado del cliente (accedido a través de `getClientComponentId()`) se usa para la comunicación cliente-servidor. Estos son diferentes del atributo `id` del DOM que establece con `setAttribute()`.
:::

### Estilizando {#styling}

Tres métodos cubren la mayoría de las necesidades de estilo: `setStyle()` para valores de propiedades CSS individuales, y `addClassName()` y `removeClassName()` para aplicar o eliminar clases CSS definidas en sus hojas de estilo. Utilice `setStyle()` para ajustes de estilo menores o únicos, y utilice clases CSS para aplicar un estilo más amplio o reutilizable.

```java
Div container = new Div();
container.setStyle("padding", "20px");

if (isHighPriority) {
    container.setStyle("border-left", "4px solid red");
}

Button button = new Button("Alternar");
button.addClassName("primary", "large");

if (isLoading) {
    button.addClassName("loading");
}
```

## Estado del componente {#component-state}

Más allá del contenido y la apariencia, los componentes tienen propiedades de estado que determinan si son visibles y si responden a la interacción del usuario. Las dos más utilizadas son `setVisible()` y `setEnabled()`.

`setVisible()` controla si el componente se renderiza en la interfaz de usuario. `setEnabled()` controla si acepta entrada o interacción manteniéndose visible. En la mayoría de los casos, desactivar es preferible a ocultar: un botón desactivado aún comunica que existe una acción, pero no está disponible todavía, lo que es menos desorientador que hacerlo aparecer y desaparecer.

```java
// Revelar un campo adicional cuando se marca una casilla de verificación
TextField advancedField = new TextField("Configuración avanzada");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Mostrar configuraciones avanzadas");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Habilitar un botón solo cuando el campo requerido tiene un valor
Button submitButton = new Button("Enviar");
submitButton.setEnabled(false);

TextField nameField = new TextField("Nombre");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

:::warning Desactivado y oculto no son seguridad
`setVisible(false)` y `setEnabled(false)` afectan solo a la interfaz de usuario. No detienen a un usuario determinado de invocar la acción subyacente a través del navegador o una solicitud manipulada, por lo que nunca confíe en ellos para proteger operaciones sensibles. Siempre aplique control de acceso en el servidor. Vea [Desactivado y oculto no son seguridad](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security) para más detalles.
:::

El siguiente formulario de inicio de sesión demuestra `setEnabled()` en práctica. El botón de inicio de sesión permanece deshabilitado hasta que ambos campos tengan contenido, dejando claro al usuario que se requiere entrada antes de proceder:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/frontend/usingcomponents/conditionalstate.css',
]}
height='450px'
/>

## Trabajando con contenedores {#working-with-containers}

En webforJ, el diseño es manejado por contenedores, que son componentes que contienen otros componentes y controlan cómo se organizan. No posiciona manualmente los componentes secundarios; en su lugar, los agrega a un contenedor y configura las propiedades de diseño de ese contenedor.

### Agregando componentes {#adding-components}

Todos los contenedores proporcionan un método `add()`. Puede pasar componentes uno a la vez o todos a la vez:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Haga clic en mí"));

TextField nameField = new TextField("Nombre");
TextField emailField = new TextField("Email");
Button submitButton = new Button("Enviar");

container.add(nameField, emailField, submitButton);
```

### Opciones de diseño {#layout-options}

`FlexLayout` es el contenedor de diseño principal en webforJ y cubre la mayoría de los casos de uso: filas, columnas, alineación, espaciado y envoltura. Para arreglos más complejos, como CSS Grid o posicionamiento personalizado, puede aplicar CSS directamente a través de `setStyle()` o `addClassName()` en cualquier componente contenedor. Consulte la documentación de [FlexLayout](/docs/components/flex-layout) para conocer la gama completa de opciones de diseño.

### Mostrando y ocultando secciones {#showing-hiding-sections}

Un uso común de `setVisible()` en contenedores es revelar UI adicional solo cuando es relevante. Esto mantiene la interfaz enfocada y reduce el desorden visual. En lugar de navegar a una nueva vista, puede mostrar una sección del diseño actual en respuesta directa a la entrada del usuario.

El siguiente panel de configuración demuestra esto: las preferencias de notificación básicas siempre son visibles, y una sección de opciones avanzadas solo aparece cuando el usuario lo solicita. El botón de guardar se activa tan pronto como se cambia cualquier configuración:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/frontend/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### Gestión de contenedores {#container-management}

Utilice `remove()` y `removeAll()` para retirar componentes de un contenedor en tiempo de ejecución:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Temporal");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Esto es útil cuando necesita reemplazar completamente el contenido, como intercambiar un indicador de carga por los datos cargados.

## Validación de formularios {#form-validation}

Coordinar múltiples componentes para restringir una acción de envío es un patrón común en las interfaces de usuario de webforJ. La idea básica es que cada campo de entrada registra un oyente, y cada vez que cambia un valor, el formulario vuelve a evaluar si se cumplen todos los criterios y actualiza el botón de envío en consecuencia.

El ejemplo a continuación conecta esto manualmente para que pueda ver cómo el estado del componente y los oyentes de eventos trabajan juntos. No es el enfoque recomendado para formularios reales: la lógica del oyente manual se vuelve difícil de mantener a medida que los formularios crecen, y no conecta sus componentes con un modelo de datos subyacente.

:::tip Utilice enlace de datos para la validación de formularios
Para formularios de producción, use [enlace de datos](/docs/data-binding/overview). Cubre la validación, la sincronización bidireccional entre componentes y su modelo, y la transformación de valores a través de `BindingContext`. El patrón manual que se muestra aquí es solo para ilustración.
:::

En este formulario de contacto, el campo de nombre no debe estar vacío, el email debe contener un símbolo `@`, y el mensaje debe tener al menos 10 caracteres de longitud:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/frontend/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## Actualizaciones de contenido dinámico {#dynamic-content-updates}

Los componentes no tienen que permanecer en un estado fijo después de ser creados. Puede actualizar texto, intercambiar clases CSS y alternar el estado habilitado en cualquier momento en respuesta a eventos de la aplicación. Un ejemplo común es proporcionar retroalimentación durante una tarea que consume mucho tiempo:

```java
Label statusLabel = new Label("Listo");
Button startButton = new Button("Iniciar Proceso");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("Procesando...");
    statusLabel.addClassName("processing");

    performTask(() -> {
        statusLabel.setText("Completo");
        statusLabel.removeClassName("processing");
        statusLabel.addClassName("success");
        startButton.setEnabled(true);
    });
});
```

Deshabilitar el botón mientras se ejecuta la tarea evita envíos duplicados, y actualizar la etiqueta mantiene informado al usuario sobre lo que está pasando.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

La interfaz `ComponentLifecycleObserver` le permite observar eventos del ciclo de vida del componente desde fuera del propio componente. Esto es útil cuando necesita reaccionar a la creación o destrucción de un componente sin modificar su implementación. Por ejemplo, podría usarlo para mantener un registro de los componentes activos o liberar recursos externos cuando se elimina un componente.

### Uso básico {#basic-usage}

Llame a `addLifecycleObserver()` en cualquier componente para registrar una devolución de llamada. La devolución de llamada recibe el componente y el evento del ciclo de vida:

```java
Button button = new Button("Obsérvame");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("Botón creado");
            break;
        case DESTROY:
            System.out.println("Botón destruido");
            break;
    }
});
```

### Patrón: Registro de recursos {#pattern-resource-registry}

El evento DESTROY es particularmente útil para mantener un registro automáticamente sincronizado. En lugar de eliminar manualmente los componentes cuando ya no son necesarios, deja que el componente notifique al registro:

```java
public class ResourceRegistry {
    private final Map<String, Component> activeComponents = new ConcurrentHashMap<>();

    public void track(Component component, String name) {
        activeComponents.put(name, component);

        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                activeComponents.remove(name);
            }
        });
    }
}
```

### Patrón: Coordinación de componentes {#pattern-component-coordination}

Una clase coordinadora que gestiona un conjunto de componentes relacionados puede usar el mismo enfoque para mantener su lista interna precisa:

```java
public class FormCoordinator {
    private final List<DwcComponent<?>> managedComponents = new ArrayList<>();

    public void manage(DwcComponent<?> component) {
        managedComponents.add(component);

        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                managedComponents.remove(comp);
            }
        });
    }

    public void disableAll() {
        managedComponents.forEach(c -> c.setEnabled(false));
    }
}
```

### Cuándo usar {#when-to-use}

Utilice `ComponentLifecycleObserver` para:
- Construir registros de componentes
- Implementar registro o monitoreo
- Coordinar múltiples componentes
- Limpiar recursos externos

Para ejecutar código después de que un componente esté adjunto al DOM, consulte `whenAttached()` en la guía de [Composición de Componentes](/docs/building-ui/composing-components).

## Datos del usuario {#user-data}

Los componentes pueden llevar datos arbitrarios del lado del servidor a través de `setUserData()` y `getUserData()`. Ambos métodos toman una clave para identificar los datos. Esto es útil cuando necesita asociar objetos de dominio o contexto con un componente sin gestionar una estructura de búsqueda separada.

```java
Button button = new Button("Procesar");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Dado que los datos del usuario nunca se envían al cliente, puede almacenar de forma segura información sensible u objetos grandes sin afectar el tráfico de red.
