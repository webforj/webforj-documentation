---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 5716356b99e40dc53cfdf82a87fd9b3c
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

El `RadioButtonGroup` gestiona una colección de componentes [`RadioButton`](/docs/components/radiobutton). Solo se puede seleccionar un `RadioButton` en un `RadioButtonGroup`. Cuando un usuario selecciona un nuevo botón de radio, el que estaba seleccionado previamente en el grupo se deselecciona automáticamente.

<!-- INTRO_END -->

## Creando un `RadioButtonGroup` {#creating-a-radiobuttongroup}

:::important Renderización de `RadioButtonGroup`
El componente `RadioButtonGroup` no renderiza un elemento HTML. Solo proporciona la lógica para que los componentes `RadioButton` se comporten como un grupo en lugar de individualmente.
:::

Crea componentes `RadioButton` individuales y pásalos al constructor de `RadioButtonGroup`. Solo se puede seleccionar un botón en el grupo a la vez.

<ComponentDemo
path='/webforj/radiobuttongroup'
files={['src/main/java/com.webforj/samples/views/radiobutton/RadioButtonGroupView.java']}
height='200px'
/>


## Añadiendo y eliminando componentes `RadioButton` {#adding-and-removing-radiobuttons}

Puedes incluir componentes `RadioButton` en el constructor de `RadioButtonGroup` para crear un grupo a partir de los componentes proporcionados. Para añadir o eliminar un `RadioButton` de un `RadioButtonGroup` existente, utiliza los métodos `add()` o `remove()`.

:::tip Obtener el Grupo de un `RadioButton`
El componente `RadioButton` tiene el método `getButtonGroup()`, que devuelve el `RadioButtonGroup` al que pertenece, o `null` si no tiene un grupo.
:::

## Anidación <DocChip chip='since' label='25.11' /> {#nesting}

Al igual que otros componentes, puedes anidar un `RadioButtonGroup` dentro de un contenedor, por lo que no tienes que añadir directamente cada `RadioButton` individual.

```java
RadioButton agree = new RadioButton("De acuerdo");
RadioButton neutral = new RadioButton("Neutral");
RadioButton disagree = new RadioButton("En desacuerdo");

RadioButtonGroup group = new RadioButtonGroup("opciones", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Opciones");
fieldset.add(group);
```

## Usando `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Cada `RadioButton` puede tener su propio escuchador de eventos para detectar cuando un usuario lo alterna. Sin embargo, una ventaja de usar un `RadioButtonGroup` es que puedes usar un solo escuchador de eventos que responda a todos los botones de radio en el grupo con el [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html).

**Añadiendo escuchadores de eventos a cada `RadioButton`**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Añadiendo un solo escuchador de eventos al `RadioButtonGroup`**

```java
RadioButtonGroup group = new RadioButtonGroup("opciones", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

El siguiente ejemplo de [Ubicación del Drawer](/docs/components/drawer#placement) utiliza el `RadioButtonGroupChangeEvent` para cambiar automáticamente la ubicación del componente `Drawer`:

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Nomenclatura {#naming}

El atributo `name` en un `RadioButtonGroup` agrupa los RadioButtons relacionados, permitiendo a los usuarios hacer una sola elección entre las opciones proporcionadas y forzando la exclusividad entre los RadioButtons. El nombre de un grupo no se refleja en el DOM, sin embargo, es una utilidad de conveniencia para el desarrollador de Java.
