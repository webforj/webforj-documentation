---
sidebar_position: 3
title: Using Components
sidebar_class_name: new-content
_i18n_hash: 3ffe2e3b31ea278e434f7319f8019637
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Los componentes son los bloques de construcción de las aplicaciones webforJ. Ya sea que estés utilizando componentes incorporados como `Button` y `TextField`, o trabajando con componentes personalizados proporcionados por tu equipo, la forma en que interactúas con ellos sigue el mismo modelo consistente: configuras propiedades, gestionas el estado y compones componentes en diseños.

Esta guía se centra en esas operaciones diarias: no en los aspectos internos de cómo funcionan los componentes, sino en cómo hacer las cosas con ellos en la práctica.

## Propiedades del componente {#component-properties}

Cada componente expone propiedades que controlan su contenido, apariencia y comportamiento. La mayoría de estas tienen métodos de Java tipados dedicados (`setText()`, `setTheme()`, `setExpanse()`, y así sucesivamente), que es la forma principal en que configurarás los componentes en webforJ. Las secciones a continuación cubren las propiedades y métodos que se aplican ampliamente a través de los tipos de componentes.

### Contenido de texto {#text-content}

El método `setText()` establece el texto visible de un componente, como el título en un `Button` o el contenido de un `Label`. Para componentes de entrada como `TextField`, usa `setValue()` en su lugar para establecer el valor actual del campo.

```java
Button button = new Button();
button.setText("Haz clic en mí");

Label label = new Label();
label.setText("Estado: listo");

TextField field = new TextField();
field.setValue("Valor inicial");
```

Algunos componentes también soportan `setHtml()` para casos donde necesitas marcado HTML en línea en el contenido:

```java
Div container = new Div();
container.setHtml("<strong>Texto en negrita</strong> y <em>texto en cursiva</em>");
```

### Atributos HTML {#html-attributes}

La mayor parte de la configuración en webforJ se realiza a través de métodos de Java tipados en lugar de atributos HTML en bruto. Sin embargo, `setAttribute()` es útil para pasar atributos de accesibilidad que no tienen una API dedicada:

```java
Button button = new Button("Enviar");
button.setAttribute("aria-label", "Enviar el formulario");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Verifica el soporte de componentes
No todos los componentes soportan atributos arbitrarios. Esto depende de la implementación subyacente del componente.
:::

### IDs de componentes {#component-ids}

Puedes asignar un ID al elemento HTML de un componente usando `setAttribute()`:

```java
Button submitButton = new Button("Enviar");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("Correo electrónico");
emailField.setAttribute("id", "email-input");
```

Los IDs del DOM se usan comúnmente para selectores de prueba y direccionamiento CSS en tus hojas de estilo.

:::tip Prefiere clases para dirigir múltiples componentes
A diferencia de las clases CSS, los IDs deben ser únicos dentro de tu aplicación. Si necesitas dirigir múltiples componentes, usa `addClassName()` en su lugar.
:::

:::info IDs gestionados por el marco
webforJ también asigna identificadores automáticos a los componentes internamente. El ID del lado del servidor (accedido a través de `getComponentId()`) se utiliza para el seguimiento del marco, mientras que el ID del lado del cliente (accedido a través de `getClientComponentId()`) se utiliza para la comunicación cliente-servidor. Estos son distintos del atributo `id` del DOM que configuras con `setAttribute()`.
:::

### Estilo {#styling}

Tres métodos cubren la mayoría de las necesidades de estilo: `setStyle()` para valores de propiedades CSS individuales, y `addClassName()` y `removeClassName()` para aplicar o eliminar clases CSS definidas en tus hojas de estilo. Usa `setStyle()` para ajustes de estilo menores o únicos, y usa clases CSS para aplicar estilos más grandes o reutilizables.

```java
Div container = new Div();
container.setStyle("padding", "20px");

if (isHighPriority) {
    container.setStyle("border-left", "4px solid red");
}

Button button = new Button("Alternar");
button.addClassName("primario", "grande");

if (isLoading) {
    button.addClassName("cargando");
}
```

:::note Enfoque legado
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) es un enfoque legado y generalmente no se recomienda para nuevos proyectos. En la mayoría de los casos, mantiene tus estilos en archivos CSS separados.
:::

## Estado del componente {#component-state}

Más allá del contenido y la apariencia, los componentes tienen propiedades de estado que determinan si son visibles y si responden a la interacción del usuario. Las dos más comúnmente usadas son `setVisible()` y `setEnabled()`.

`setVisible()` controla si el componente se renderiza en la UI en absoluto. `setEnabled()` controla si acepta entrada o interacción mientras permanece visible. En la mayoría de los casos, deshabilitar es preferible a ocultar: un botón deshabilitado aún comunica que existe una acción, pero no está disponible aún, lo cual es menos desorientador que hacer que aparezca y desaparezca.

```java
// Revelar un campo adicional cuando se marca una casilla
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

El siguiente formulario de inicio de sesión demuestra `setEnabled()` en la práctica. El botón de inicio de sesión permanece deshabilitado hasta que ambos campos tienen contenido, dejando claro al usuario que se requiere entrada antes de proceder:

<ComponentDemo 
path='/webforj/conditionalstate?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/conditionalstate.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java'
height='400px'
/>

## Trabajando con contenedores {#working-with-containers}

En webforJ, el diseño es manejado por contenedores, que son componentes que contienen otros componentes y controlan cómo se arreglan. No posicionas los componentes secundarios manualmente; en su lugar, los agregas a un contenedor y configuras las propiedades de diseño de ese contenedor.

### Agregando componentes {#adding-components}

Todos los contenedores proporcionan un método `add()`. Puedes pasar componentes uno a la vez o todos de una vez:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Haz clic en mí"));

TextField nameField = new TextField("Nombre");
TextField emailField = new TextField("Correo electrónico");
Button submitButton = new Button("Enviar");

container.add(nameField, emailField, submitButton);
```

### Opciones de diseño {#layout-options}

`FlexLayout` es el contenedor de diseño principal en webforJ y cubre la mayoría de los casos de uso: filas, columnas, alineación, espaciado y envoltura. Para arreglos más complejos como CSS Grid o posicionamiento personalizado, puedes aplicar CSS directamente a través de `setStyle()` o `addClassName()` en cualquier componente contenedor. Consulta la documentación de [FlexLayout](/docs/components/flex-layout) para un rango completo de opciones de diseño.

### Mostrar y ocultar secciones {#showing-hiding-sections}

Un uso común de `setVisible()` en contenedores es revelar UI adicional solo cuando es relevante. Esto mantiene la interfaz enfocada y reduce el desorden visual. En lugar de navegar a una nueva vista, puedes mostrar una sección del diseño actual en respuesta directa a la entrada del usuario.

El siguiente panel de configuraciones demuestra esto: preferencias básicas de notificación son siempre visibles, y una sección de opciones avanzadas solo aparece cuando el usuario lo solicita. El botón de guardar se activa tan pronto como se cambia cualquier ajuste:

<ComponentDemo 
path='/webforj/progressivedisclosure?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/progressivedisclosure.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java'
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

Esto es útil cuando necesitas reemplazar contenido completamente, como intercambiar un indicador de carga por los datos cargados.

## Validación de formularios {#form-validation}

Coordinar múltiples componentes para bloquear una acción de envío es uno de los patrones más comunes en las interfaces webforJ. La idea central es simple: cada campo de entrada registra un oyente, y cada vez que cambia algún valor, el formulario re-evalúa si se cumplen todos los criterios y actualiza el botón de envío en consecuencia.

Esto es preferible a mostrar errores de validación solo después de que el usuario haga clic en enviar, porque proporciona retroalimentación continua y previene envíos innecesarios. El botón de envío sirve como el indicador: deshabilitado significa que el formulario no está listo, habilitado significa que sí lo está.

En este formulario de contacto, el campo de nombre no debe estar vacío, el correo electrónico debe contener un símbolo `@`, y el mensaje debe tener al menos 10 caracteres de largo:

<ComponentDemo 
path='/webforj/formvalidation?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/formvalidation.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java'
height='500px'
/>

## Actualizaciones de contenido dinámicas {#dynamic-content-updates}

Los componentes no tienen que permanecer en un estado fijo después de ser creados. Puedes actualizar texto, intercambiar clases CSS y alternar el estado habilitado en cualquier momento en respuesta a eventos de la aplicación. Un ejemplo común es proporcionar retroalimentación durante una tarea de larga duración:

```java
Label statusLabel = new Label("Listo");
Button startButton = new Button("Iniciar proceso");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("Procesando...");
    statusLabel.addClassName("procesando");
    
    performTask(() -> {
        statusLabel.setText("Completo");
        statusLabel.removeClassName("procesando");
        statusLabel.addClassName("exito");
        startButton.setEnabled(true);
    });
});
```

Deshabilitar el botón mientras se ejecuta la tarea previene envíos duplicados, y actualizar la etiqueta mantiene informado al usuario sobre qué está sucediendo.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

La interfaz `ComponentLifecycleObserver` te permite observar eventos del ciclo de vida del componente desde fuera del componente en sí. Esto es útil cuando necesitas reaccionar a la creación o destrucción de un componente sin modificar su implementación. Por ejemplo, podrías usarlo para mantener un registro de componentes activos o liberar recursos externos cuando se elimina un componente.

### Uso básico {#basic-usage}

Llama a `addLifecycleObserver()` en cualquier componente para registrar una devolución de llamada. La devolución de llamada recibe el componente y el evento del ciclo de vida:

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

El evento DESTROY es particularmente útil para mantener un registro en sincronización automática. En lugar de eliminar manualmente los componentes cuando ya no son necesarios, dejas que el componente notifique al registro:

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
- Implementar registros o monitoreo
- Coordinar múltiples componentes
- Limpiar recursos externos

Para ejecutar código después de que un componente esté adjunto al DOM, consulta [`whenAttached()`](/docs/building-ui/composite-components) en la guía de Componentes Compuestos.

## Datos del usuario {#user-data}

Los componentes pueden llevar datos arbitrarios del lado del servidor a través de `setUserData()` y `getUserData()`. Ambos métodos toman una clave para identificar los datos. Esto es útil cuando necesitas asociar objetos de dominio o contexto con un componente sin gestionar una estructura de búsqueda separada.

```java
Button button = new Button("Procesar");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Dado que los datos del usuario nunca se envían al cliente, puedes almacenar de manera segura información sensible u objetos grandes sin afectar el tráfico de la red.
