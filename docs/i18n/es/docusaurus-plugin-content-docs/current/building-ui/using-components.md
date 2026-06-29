---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: fb67c93e2165a651245a703c772d3bcb
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Los componentes son los bloques de construcción de las aplicaciones webforJ. Ya sea que estés utilizando componentes integrados como `Button` y `TextField`, o trabajando con componentes personalizados proporcionados por tu equipo, la forma en que interactúas con ellos sigue el mismo modelo consistente: configuras propiedades, gestionas el estado y compones componentes en diseños.

Esta guía se centra en esas operaciones del día a día: no en los entresijos de cómo funcionan los componentes, sino en cómo hacer las cosas con ellos en la práctica.

## Propiedades del componente {#component-properties}

Cada componente expone propiedades que controlan su contenido, apariencia y comportamiento. La mayoría de estas tienen métodos Java tipados dedicados (`setText()`, `setTheme()`, `setExpanse()`, etc.), que es la forma principal en la que configurarás componentes en webforJ. Las secciones a continuación cubren las propiedades y métodos que se aplican de manera amplia a través de los tipos de componentes.

### Contenido de texto {#text-content}

El método `setText()` establece el texto visible de un componente como caracteres literales, como el título en un `Button` o el contenido de una `Label`. Para componentes de entrada como `TextField`, usa `setValue()` en su lugar para establecer el valor actual del campo.

```java
Button button = new Button();
button.setText("Haga clic en mí");

Label label = new Label();
label.setText("Estado: listo");

TextField field = new TextField();
field.setValue("Valor inicial");
```

El marcado escrito con `setText()` aparece como esos caracteres y nunca se ejecuta, lo que evita que el texto que proviene de la entrada del usuario o datos externos se interprete como marcado en vivo.

```java
// Se muestra como los caracteres literales "<b>Estado: listo</b>"
component.setText("<b>Estado: listo</b>");
```

:::note Uso de la etiqueta `<html>`
Las versiones anteriores de webforJ trataban un valor envuelto en `<html>` y pasado a `setText()` como HTML. Este comportamiento está en desuso y se eliminará en webforJ 27.00.

La primera vez que un valor envuelto en `<html>` alcanza `setText()`, se registra una advertencia que nombra el componente y el sitio de llamada, para que la llamada pueda ser movida a `setHtml()`.

Para adoptar el valor predeterminado de webforJ 27.00 con anticipación, establece `webforj.legacyHtmlInText` en `false`. En una aplicación Spring, el mismo valor se establece a través de `webforj.legacy-html-in-text`.

```java
// webforj.legacyHtmlInText = true (predeterminado)
component.setText("<html><b>Estado: listo</b></html>"); // renderiza en negrita

// webforj.legacyHtmlInText = false
component.setText("<html><b>Estado: listo</b></html>"); // muestra los caracteres <b>Estado: listo</b>
```
:::

### Renderizando HTML {#rendering-html}

Algunos componentes también soportan `setHtml()` para casos en los que necesitas renderizar marcado HTML en línea en el contenido:

```java
Div container = new Div();
container.setHtml("<strong>Texto en negrita</strong> y <em>texto en cursiva</em>");
```

:::danger Secuencias de comandos entre sitios (XSS)
Como precaución contra [ataques de secuencias de comandos entre sitios (XSS)](/docs/security/application-security/common-threats#cross-site-scripting-xss), usa `setHtml()` solo con contenido que controles directamente.
:::

### Atributos HTML {#html-attributes}

La mayor parte de la configuración en webforJ se realiza a través de métodos Java tipados en lugar de atributos HTML en crudo. Sin embargo, `setAttribute()` es útil para pasar atributos de accesibilidad que no tienen una API dedicada:

```java
Button button = new Button("Enviar");
button.setAttribute("aria-label", "Enviar el formulario");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Verificar el soporte del componente
No todos los componentes soportan atributos arbitrarios. Esto depende de la implementación del componente subyacente.
:::

### IDs del componente {#component-ids}

Puedes asignar un ID al elemento HTML de un componente usando `setAttribute()`:

```java
Button submitButton = new Button("Enviar");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("Correo electrónico");
emailField.setAttribute("id", "email-input");
```

Los IDs del DOM se utilizan comúnmente para selectores de prueba y dirigirse a CSS en tus hojas de estilo.

:::tip Preferir clases para dirigirse a múltiples componentes
A diferencia de las clases CSS, los IDs deben ser únicos dentro de tu aplicación. Si necesitas dirigirte a múltiples componentes, usa `addClassName()` en su lugar.
:::

:::info IDs gestionados por el marco
webforJ también asigna identificadores automáticos a los componentes internamente. El ID del lado del servidor (accedido a través de `getComponentId()`) se utiliza para el seguimiento del marco, mientras que el ID del lado del cliente (accedido a través de `getClientComponentId()`) se utiliza para la comunicación entre el cliente y el servidor. Estos son diferentes del atributo `id` del DOM que estableces con `setAttribute()`.
:::

### Estilización {#styling}

Tres métodos cubren la mayoría de las necesidades de estilización: `setStyle()` para valores individuales de propiedades CSS, y `addClassName()` y `removeClassName()` para aplicar o quitar clases CSS definidas en tus hojas de estilo. 
Usa `setStyle()` para ajustes de estilo menores o puntuales, y usa clases CSS para aplicar estilos más grandes o reutilizables.

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

Más allá del contenido y la apariencia, los componentes tienen propiedades de estado que determinan si son visibles y si responden a la interacción del usuario. Las dos más comúnmente utilizadas son `setVisible()` y `setEnabled()`.

`setVisible()` controla si el componente se renderiza en la interfaz de usuario en absoluto. `setEnabled()` controla si acepta entrada o interacción mientras sigue siendo visible. En la mayoría de los casos, deshabilitar es preferible a ocultar: un botón deshabilitado aún comunica que existe una acción, pero no está disponible aún, lo cual es menos desorientador que hacer que aparezca y desaparezca.

```java
// Revelar un campo adicional cuando se selecciona una casilla de verificación
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

:::warning Deshabilitado y oculto no son seguridad
`setVisible(false)` y `setEnabled(false)` afectan solo la interfaz de usuario. No impiden que un usuario determinado invoque la acción subyacente a través del navegador o una solicitud manipulada, así que nunca confíes en ellos para proteger operaciones sensibles. Siempre aplica control de acceso en el servidor. Consulta [Deshabilitado y oculto no son seguridad](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security) para más detalles.
:::

El siguiente formulario de inicio de sesión demuestra `setEnabled()` en práctica. El botón de inicio de sesión permanece deshabilitado hasta que ambos campos tienen contenido, haciendo claro al usuario que se requiere entrada antes de proceder:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='450px'
/>

## Trabajando con contenedores {#working-with-containers}

En webforJ, el diseño se maneja mediante contenedores, que son componentes que albergan otros componentes y controlan cómo se disponen. No posicionas los componentes secundarios manualmente; en su lugar, los agregas a un contenedor y configuras las propiedades de diseño de ese contenedor.

### Agregando componentes {#adding-components}

Todos los contenedores proporcionan un método `add()`. Puedes pasar componentes uno a la vez o todos de una vez:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Haga clic en mí"));

TextField nameField = new TextField("Nombre");
TextField emailField = new TextField("Correo electrónico");
Button submitButton = new Button("Enviar");

container.add(nameField, emailField, submitButton);
```

### Opciones de diseño {#layout-options}

`FlexLayout` es el contenedor de diseño principal en webforJ y cubre la mayoría de los casos de uso: filas, columnas, alineación, espaciado y envoltura. Para arreglos más complejos como CSS Grid o posicionamiento personalizado, puedes aplicar CSS directamente mediante `setStyle()` o `addClassName()` en cualquier componente contenedor. Consulta la documentación de [FlexLayout](/docs/components/flex-layout) para obtener la gama completa de opciones de diseño.

### Mostrando y ocultando secciones {#showing-hiding-sections}

Un uso común de `setVisible()` en contenedores es revelar UI adicional solo cuando es relevante. Esto mantiene la interfaz enfocada y reduce el desorden visual. En lugar de navegar a una nueva vista, puedes mostrar una sección del diseño actual en respuesta directa a la entrada del usuario.

El siguiente panel de configuración demuestra esto: las preferencias de notificación básicas siempre son visibles, y una sección de opciones avanzadas solo aparece cuando el usuario la solicita. El botón de guardado se activa tan pronto como se cambia cualquier configuración:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/resources/static/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### Gestión del contenedor {#container-management}

Utiliza `remove()` y `removeAll()` para sacar componentes de un contenedor en tiempo de ejecución:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Temporal");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Esto es útil cuando necesitas reemplazar contenido por completo, como intercambiar un indicador de carga por los datos cargados.

## Validación de formularios {#form-validation}

Coordinar múltiples componentes para bloquear una acción de envío es un patrón común en las interfaces de webforJ. La idea básica es que cada campo de entrada registra un oyente, y cada vez que un valor cambia, el formulario re-evalúa si se cumplen todos los criterios y actualiza el botón de envío en consecuencia.
 
El ejemplo a continuación lo configura manualmente para que puedas ver cómo el estado del componente y los oyentes de eventos trabajan juntos. No es el enfoque recomendado para formularios reales: la lógica del oyente manual se vuelve difícil de mantener a medida que los formularios crecen, y no conecta tus componentes a un modelo de datos subyacente.
 
:::tip Usa enlace de datos para la validación de formularios
Para formularios en producción, usa [enlace de datos](/docs/data-binding/overview). Cubre la validación, la sincronización bidireccional entre componentes y tu modelo, y la transformación de valores a través de `BindingContext`. El patrón manual mostrado aquí es solo para ilustración.
:::
 
En este formulario de contacto, el campo de nombre no debe estar vacío, el correo electrónico debe contener un símbolo `@`, y el mensaje debe tener al menos 10 caracteres de longitud:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/resources/static/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## Actualizaciones de contenido dinámico {#dynamic-content-updates}

Los componentes no tienen que permanecer en un estado fijo después de ser creados. Puedes actualizar texto, intercambiar clases CSS y alternar el estado habilitado en cualquier momento en respuesta a eventos de la aplicación. Un ejemplo común es proporcionar retroalimentación durante una tarea de larga duración:

```java
Label statusLabel = new Label("Listo");
Button startButton = new Button("Iniciar proceso");

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

Deshabilitar el botón mientras se ejecuta la tarea evita envíos duplicados, y actualizar la etiqueta mantiene al usuario informado sobre lo que está sucediendo.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

La interfaz `ComponentLifecycleObserver` te permite observar eventos del ciclo de vida del componente desde fuera del componente mismo. Esto es útil cuando necesitas reaccionar ante un componente que se crea o destruye sin modificar su implementación. Por ejemplo, podrías usarlo para mantener un registro de componentes activos o liberar recursos externos cuando se elimina un componente.

### Uso básico {#basic-usage}

Llama a `addLifecycleObserver()` en cualquier componente para registrar un callback. El callback recibe el componente y el evento del ciclo de vida:

```java
Button button = new Button("Mírame");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("El botón fue creado");
            break;
        case DESTROY:
            System.out.println("El botón fue destruido");
            break;
    }
});
```

### Patrón: Registro de recursos {#pattern-resource-registry}

El evento DESTROY es particularmente útil para mantener un registro automáticamente sincronizado. En lugar de eliminar manualmente los componentes cuando ya no se necesitan, dejas que el componente notifique al registro por sí solo:

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

Usa `ComponentLifecycleObserver` para:
- Construir registros de componentes
- Implementar registro o monitoreo
- Coordinar múltiples componentes
- Limpiar recursos externos

Para ejecutar código después de que un componente se adjunta al DOM, consulta `whenAttached()` en la guía de [Componiendo Componentes](/docs/building-ui/composing-components).

## Datos del usuario {#user-data}

Los componentes pueden llevar datos arbitrarios del lado del servidor a través de `setUserData()` y `getUserData()`. Ambos métodos toman una clave para identificar los datos. Esto es útil cuando necesitas asociar objetos de dominio o contexto con un componente sin gestionar una estructura de búsqueda por separado.

```java
Button button = new Button("Procesar");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Dado que los datos del usuario nunca se envían al cliente, puedes almacenar información sensible u objetos grandes sin afectar el tráfico de red.
