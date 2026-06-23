---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: 97722c8e3bf6c3129c078d8ae23cf2a4
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Los componentes son los bloques de construcción de las aplicaciones webforJ. Ya sea que estés usando componentes integrados como `Button` y `TextField`, o trabajando con componentes personalizados proporcionados por tu equipo, la forma en que interactúas con ellos sigue el mismo modelo consistente: configuras propiedades, gestionas el estado y compones componentes en diseños.

Esta guía se centra en esas operaciones cotidianas: no en los detalles de cómo funcionan los componentes, sino en cómo hacer las cosas con ellos en la práctica.

## Propiedades del componente {#component-properties}

Cada componente expone propiedades que controlan su contenido, apariencia y comportamiento. La mayoría de estas tienen métodos de Java tipados y dedicados (`setText()`, `setTheme()`, `setExpanse()`, y así sucesivamente), que es la forma principal en que configurarás componentes en webforJ. Las secciones a continuación cubren las propiedades y métodos que se aplican de forma amplia a los tipos de componentes.

### Contenido de texto {#text-content}

El método `setText()` establece el texto visible de un componente como caracteres literales, como el texto en un `Button` o el contenido de un `Label`. Para componentes de entrada como `TextField`, usa `setValue()` en su lugar para establecer el valor actual del campo.

```java
Button button = new Button();
button.setText("Haz clic en mí");

Label label = new Label();
label.setText("Estado: listo");

TextField field = new TextField();
field.setValue("Valor inicial");
```

El marcado escrito con `setText()` aparece como esos caracteres y nunca se ejecuta, lo que evita que el texto que proviene de la entrada del usuario o datos externos sea interpretado como marcado en vivo.

```java
// Se muestra como los caracteres literales "<b>Estado: listo</b>"
component.setText("<b>Estado: listo</b>");
```

:::note Uso de la etiqueta `<html>`
Las versiones anteriores de webforJ trataban un valor envuelto en `<html>` y pasado a `setText()` como HTML. Este comportamiento está en desuso y será eliminado en webforJ 27.00.

La primera vez que un valor envuelto en `<html>` llega a `setText()`, se registra una advertencia que nombra el componente y el sitio de llamada, para que la llamada pueda ser movida a `setHtml()`.

Para adoptar el valor predeterminado de webforJ 27.00 con anticipación, establece `webforj.legacyHtmlInText` en `false`. En una aplicación Spring, el mismo valor se establece a través de `webforj.legacy-html-in-text`.

```java
// webforj.legacyHtmlInText = true (predeterminado)
component.setText("<html><b>Estado: listo</b></html>"); // renderiza en negrita

// webforj.legacyHtmlInText = false
component.setText("<html><b>Estado: listo</b></html>"); // muestra los caracteres <b>Estado: listo</b>
```
:::

### Renderizando HTML {#rendering-html}

Algunos componentes también admiten `setHtml()` para casos en los que necesitas renderizar marcado HTML en línea en el contenido:

```java
Div container = new Div();
container.setHtml("<strong>Texto en negrita</strong> y <em>texto en cursiva</em>");
```

:::danger Scripting entre sitios (XSS)
Como precaución contra [ataques de scripting entre sitios (XSS)](/docs/security/application-security/common-threats#cross-site-scripting-xss), utiliza `setHtml()` solo con contenido que controles directamente.
:::

### Atributos HTML {#html-attributes}

La mayor parte de la configuración en webforJ se realiza a través de métodos Java tipados en lugar de atributos HTML en bruto. Sin embargo, `setAttribute()` es útil para pasar atributos de accesibilidad que no tienen una API dedicada:

```java
Button button = new Button("Enviar");
button.setAttribute("aria-label", "Enviar el formulario");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Verificación de soporte de componentes
No todos los componentes admiten atributos arbitrarios. Esto depende de la implementación subyacente del componente.
:::

### IDs de componentes {#component-ids}

Puedes asignar un ID al elemento HTML de un componente utilizando `setAttribute()`:

```java
Button submitButton = new Button("Enviar");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("Email");
emailField.setAttribute("id", "email-input");
```

Los IDs del DOM se utilizan comúnmente para selectores de prueba y targeting CSS en tus hojas de estilo.

:::tip Preferir clases para targeting de múltiples componentes
A diferencia de las clases CSS, los IDs deben ser únicos dentro de tu aplicación. Si necesitas apuntar a múltiples componentes, utiliza `addClassName()` en su lugar.
:::

:::info IDs gestionados por el framework
webforJ también asigna identificadores automáticos a los componentes internamente. El ID del lado del servidor (accedido a través de `getComponentId()`) se usa para el seguimiento del framework, mientras que el ID del lado del cliente (accedido a través de `getClientComponentId()`) se utiliza para la comunicación cliente-servidor. Estos son diferentes del atributo `id` del DOM que estableces con `setAttribute()`.
:::

### Estilo {#styling}

Tres métodos cubren la mayoría de las necesidades de diseño: `setStyle()` para valores de propiedad CSS individuales, y `addClassName()` y `removeClassName()` para aplicar o eliminar clases CSS definidas en tus hojas de estilo. Usa `setStyle()` para ajustes menores o únicos, y usa clases CSS para aplicar un estilo más amplio o reutilizable.

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

:::note Enfoque heredado
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) es un enfoque heredado y generalmente no se recomienda para nuevos proyectos. En la mayoría de los casos, mantén tus estilos en archivos CSS separados.
:::

## Estado del componente {#component-state}

Más allá del contenido y la apariencia, los componentes tienen propiedades de estado que determinan si son visibles y si responden a la interacción del usuario. Las dos más utilizadas son `setVisible()` y `setEnabled()`.

`setVisible()` controla si el componente se renderiza en la UI en absoluto. `setEnabled()` controla si acepta entradas o interacción mientras permanece visible. En la mayoría de los casos, deshabilitar es preferible a ocultar: un botón deshabilitado aún comunica que existe una acción, pero que no está disponible aún, lo que es menos desorientador que hacer que aparezca y desaparezca.

```java
// Revelar un campo adicional cuando se marca una caja de verificación
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

El siguiente formulario de inicio de sesión demuestra `setEnabled()` en práctica. El botón de inicio de sesión permanece deshabilitado hasta que ambos campos tienen contenido, haciendo que quede claro para el usuario que se requiere entrada antes de proceder:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='400px'
/>

## Trabajando con contenedores {#working-with-containers}

En webforJ, el diseño es manejado por contenedores, que son componentes que contienen otros componentes y controlan cómo están dispuestos. No posicionas componentes secundarios manualmente; en su lugar, los agregas a un contenedor y configuras las propiedades de diseño de ese contenedor.

### Agregando componentes {#adding-components}

Todos los contenedores proporcionan un método `add()`. Puedes pasar componentes uno a la vez o todos a la vez:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Haz clic en mí"));

TextField nameField = new TextField("Nombre");
TextField emailField = new TextField("Email");
Button submitButton = new Button("Enviar");

container.add(nameField, emailField, submitButton);
```

### Opciones de diseño {#layout-options}

`FlexLayout` es el contenedor de diseño principal en webforJ y cubre la mayoría de los casos de uso: filas, columnas, alineación, espaciado y envoltura. Para arreglos más complejos, como CSS Grid o posicionamiento personalizado, puedes aplicar CSS directamente a través de `setStyle()` o `addClassName()` en cualquier componente contenedor. Consulta la documentación de [FlexLayout](/docs/components/flex-layout) para obtener la gama completa de opciones de diseño.

### Mostrando y ocultando secciones {#showing-hiding-sections}

Un uso común de `setVisible()` en contenedores es revelar UI adicional solo cuando es relevante. Esto mantiene la interfaz centrada y reduce el desorden visual. En lugar de navegar a una nueva vista, puedes mostrar una sección del diseño actual en respuesta directa a la entrada del usuario.

El siguiente panel de ajustes demuestra esto: las preferencias de notificación básicas son siempre visibles, y una sección de opciones avanzadas solo aparece cuando el usuario lo solicita. El botón de guardar se activa tan pronto como se cambia cualquier ajuste:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/resources/static/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### Gestión de contenedores {#container-management}

Usa `remove()` y `removeAll()` para sacar componentes de un contenedor en tiempo de ejecución:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Temporal");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Esto es útil cuando necesitas reemplazar contenido por completo, como cambiar un indicador de carga por los datos cargados.

## Validación de formularios {#form-validation}

Coordinar múltiples componentes para controlar una acción de envío es uno de los patrones más comunes en las interfaces de usuario de webforJ. La idea central es simple: cada campo de entrada registra un oyente, y cada vez que cambia algún valor, el formulario reevalúa si se cumplen todos los criterios y actualiza el botón de envío en consecuencia.

Esto es preferible a mostrar errores de validación solo después de que el usuario hace clic en enviar, porque proporciona retroalimentación continua y previene envíos innecesarios. El botón de envío sirve como el indicador: deshabilitado significa que el formulario no está listo, habilitado significa que sí.

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

Los componentes no tienen que permanecer en un estado fijo después de ser creados. Puedes actualizar texto, cambiar clases CSS y alternar el estado habilitado en cualquier momento en respuesta a eventos de la aplicación. Un ejemplo común es proporcionar retroalimentación durante una tarea de larga duración:

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

Deshabilitar el botón mientras se ejecuta la tarea previene envíos duplicados, y actualizar la etiqueta mantiene al usuario informado sobre lo que está sucediendo.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

La interfaz `ComponentLifecycleObserver` te permite observar eventos del ciclo de vida del componente desde fuera del propio componente. Esto es útil cuando necesitas reaccionar a la creación o destrucción de un componente sin modificar su implementación. Por ejemplo, podrías usarlo para mantener un registro de componentes activos o liberar recursos externos cuando un componente es eliminado.

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

El evento DESTROY es particularmente útil para mantener un registro en sincronía automáticamente. En lugar de eliminar manualmente los componentes cuando ya no son necesarios, dejas que el componente notifique al registro por sí mismo:

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

Para ejecutar código después de que un componente esté adjunto al DOM, consulta [`whenAttached()`](/docs/building-ui/composing-components) en la guía de Componentes Compuestos.

## Datos de usuario {#user-data}

Los componentes pueden llevar datos arbitrarios del lado del servidor a través de `setUserData()` y `getUserData()`. Ambos métodos reciben una clave para identificar los datos. Esto es útil cuando necesitas asociar objetos de dominio o contexto con un componente sin gestionar una estructura de búsqueda separada.

```java
Button button = new Button("Procesar");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Dado que los datos de usuario nunca se envían al cliente, puedes almacenar de forma segura información sensible u objetos grandes sin afectar el tráfico de red.
