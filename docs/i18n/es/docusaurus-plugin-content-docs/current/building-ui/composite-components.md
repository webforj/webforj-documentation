---
sidebar_position: 2
title: Composite Components
draft: false
_i18n_hash: c133bfa392bbd2705acdc71cea3fdd68
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Los desarrolladores a menudo querrán crear componentes que contengan componentes constitutivos para su uso a nivel de aplicación. El componente `Composite` proporciona a los desarrolladores las herramientas que necesitan para crear sus propios componentes mientras mantienen el control sobre lo que eligen exponer a los usuarios.

Permite a los desarrolladores gestionar un tipo específico de instancia de `Component`, proporcionando una forma de encapsular su comportamiento. Requiere que cualquier subclase que se extienda especifique el tipo de `Component` que pretende gestionar, asegurando que una subclase de `Composite` esté intrínsecamente vinculada a su `Component` subyacente.

:::tip
Se recomienda encarecidamente crear componentes personalizados utilizando el componente `Composite`, en lugar de extender el componente base `Component`.
:::

Para utilizar el componente `Composite`, comience creando una nueva clase de Java que extienda el componente `Composite`. Especifique el tipo de Component que desea gestionar como parámetro de tipo genérico.

```java
public class ApplicationComponent extends Composite<Div> {
	//Implementación
}
```

## Vinculación de componentes {#component-binding}

La clase `Composite` requiere que los desarrolladores especifiquen el tipo de `Component` que gestiona. Esta fuerte asociación asegura que un componente `Composite` esté intrínsecamente vinculado a su `Component` subyacente. Esto también proporciona beneficios sobre la herencia tradicional, ya que permite al desarrollador decidir exactamente qué funcionalidad exponer a la API pública.

Por defecto, el componente `Composite` utiliza el parámetro de tipo genérico de su subclase para identificar e instanciar el tipo de componente vinculado. Esto se basa en la suposición de que la clase de componente tiene un constructor sin parámetros. Los desarrolladores pueden personalizar el proceso de inicialización del componente al anular el método `initBoundComponent()`. Esto permite una mayor flexibilidad en la creación y gestión del componente vinculado, incluyendo la invocación de constructores parametrizados.

El siguiente fragmento anula el método initBoundComponent para usar un constructor parametrizado para la clase [FlexLayout](../components/flex-layout.md):

```java
public class OverrideComposite extends Composite<FlexLayout> {
	
	TextField nameField;
	Button submit;

	@Override
	protected FlexLayout initBoundComponent() {
		nameField = new TextField();
		submit = new Button("Enviar");
		return new FlexLayout(nameField, submit);
	}
}
```

## Gestión del ciclo de vida {#lifecycle-management}

A diferencia del `Component`, los desarrolladores no necesitan implementar los métodos `onCreate()` y `onDestroy()` al trabajar con el componente `Composite`. El componente `Composite` se encarga de estos aspectos por usted.

Si necesita acceder a los componentes vinculados en las diversas etapas de su ciclo de vida, los ganchos `onDidCreate()` y `onDidDestroy()` permiten a los desarrolladores acceder a estas etapas del ciclo de vida para realizar funcionalidad adicional. La utilización de estos ganchos es opcional.

El método `onDidCreate()` se llama inmediatamente después de que se crea y se agrega el componente vinculado a una ventana. Utilice este método para configurar su componente, modificar cualquier configuración necesaria y agregar componentes secundarios si corresponde. Mientras que el método `onCreate()` de la clase `Component` toma una instancia de [Window](#), el método `onDidCreate()` en su lugar toma el componente vinculado, eliminando la necesidad de llamar al método `getBoundComponent()` directamente. Por ejemplo:

```java
public class ApplicationComponent extends Composite<Div> {
	@Override
	protected void onDidCreate(Div container) {
		// Agregar componentes secundarios al contenedor
		container.add(new CheckBox());
		container.add(new Paragraph());
		// ...
	}
}
```

:::tip
Esta lógica también se puede implementar en el constructor, llamando a `getBoundComponent()`.
:::

De manera similar, el método `onDidDestroy()` se activa una vez que el componente vinculado ha sido destruido, y permite que se active un comportamiento adicional en la destrucción si se desea.

### Ejemplo de componente `Composite` {#example-composite-component}

En el siguiente ejemplo, se ha creado una simple aplicación de ToDo, donde cada elemento agregado a la lista es un componente `Composite`, que consiste en un [`RadioButton`](../components/radio-button.md) estilizado como un interruptor, y un [`Div`](#) con texto.

La lógica para este componente está configurada en el constructor, que establece el estilo y agrega componentes constitutivos al componente vinculado utilizando el método `getBoundComponent`, y agrega lógica de eventos.

:::tip
Esto también podría implementarse en el método `onDidCreate()`, lo que daría acceso directo al componente vinculado [`FlexLayout`](../components/flex-layout.md).
:::

Este componente se instancia y se utiliza en una Aplicación, y permite su uso en diversas ubicaciones, lo que lo convierte en una herramienta poderosa en la creación de componentes personalizados.

<ComponentDemo 
path='/webforj/composite?' 
cssURL='/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/CompositeView.java'
height='550px'
/>
