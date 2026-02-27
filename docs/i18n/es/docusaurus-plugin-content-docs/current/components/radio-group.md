---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
sidebar_class_name: updated-content
_i18n_hash: da7906128f0d003b9ed8c48c99c3aefc
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

El `RadioButtonGroup` gestiona una colección de [`RadioButton`](/docs/components/radiobutton) componentes. Solo se puede seleccionar un `RadioButton` en un `RadioButtonGroup`. Cuando un usuario selecciona un nuevo botón de radio, el que estaba seleccionado anteriormente en el grupo se deselecciona automáticamente.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important Renderizado de `RadioButtonGroup`
El componente `RadioButtonGroup` no renderiza un elemento HTML. Solo proporciona lógica para hacer que los componentes `RadioButton` se comporten como un grupo en lugar de individualmente.
:::

## Agregar y eliminar componentes `RadioButton` {#adding-and-removing-radiobuttons}

Puedes incluir componentes `RadioButton` en el constructor de `RadioButtonGroup` para crear un grupo a partir de los componentes proporcionados. Para agregar o eliminar un `RadioButton` de un `RadioButtonGroup` existente, utiliza los métodos `add()` o `remove()`.

:::tip Obtener el Grupo de un `RadioButton`
El componente `RadioButton` tiene el método `getButtonGroup()`, que devuelve el `RadioButtonGroup` al que pertenece, o `null` si no tiene un grupo.
:::

## Anidamiento <DocChip chip='since' label='25.11' /> {#nesting}

Como otros componentes, puedes anidar un `RadioButtonGroup` dentro de un contenedor, por lo que no necesitas agregar directamente cada `RadioButton` individualmente.

```java
RadioButton agree = new RadioButton("Agree");
RadioButton neutral = new RadioButton("Neutral");
RadioButton disagree = new RadioButton("Disagree");

RadioButtonGroup group = new RadioButtonGroup("choices", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Options");
fieldset.add(group);
```

## Usando `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Cada `RadioButton` puede tener su propio listener de eventos para detectar cuándo un usuario lo alterna. Sin embargo, una ventaja de usar un `RadioButtonGroup` es que puedes utilizar un único listener de eventos que responda a todos los botones de radio en el grupo con el [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html).

**Agregando listeners de eventos a cada `RadioButton`**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Agregando un solo listener de eventos al `RadioButtonGroup`**

```java
RadioButtonGroup group = new RadioButtonGroup("choices", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

El siguiente ejemplo de [Posición del Drawer](/docs/components/drawer#placement) utiliza el `RadioButtonGroupChangeEvent` para cambiar automáticamente la posición del componente `Drawer`:

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Nomenclatura {#naming}

El atributo `name` en un `RadioButtonGroup` agrupa los `RadioButtons` relacionados, permitiendo a los usuarios hacer una única elección entre las opciones proporcionadas y reforzando la exclusividad entre los `RadioButtons`. Sin embargo, el nombre de un grupo no se refleja en el DOM y es una utilidad de conveniencia para el desarrollador de Java.
