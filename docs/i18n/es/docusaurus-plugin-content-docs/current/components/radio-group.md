---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
description: >-
  Coordinate mutually exclusive RadioButton selections with RadioButtonGroup,
  including nested containers and dynamic membership.
_i18n_hash: a616c60faaf0d58f9d9a1e778318880a
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

El `RadioButtonGroup` gestiona una colección de componentes [`RadioButton`](/docs/components/radiobutton). Solo se puede seleccionar un `RadioButton` en un `RadioButtonGroup`. Cuando un usuario selecciona un nuevo botón de opción, el previamente seleccionado en el grupo se deselecciona automáticamente.

<!-- INTRO_END -->

## Creando un `RadioButtonGroup` {#creating-a-radiobuttongroup}

:::important Renderización de `RadioButtonGroup`
El componente `RadioButtonGroup` no renderiza un elemento HTML. Solo proporciona lógica para hacer que los componentes `RadioButton` se comporten como un grupo en lugar de individualmente.
:::

Crea componentes `RadioButton` individuales y pásalos al constructor de `RadioButtonGroup`. Solo se puede seleccionar un botón en el grupo a la vez.

<ComponentDemo
path='/webforj/radiobuttongroup'
files={['src/main/java/com.webforj/samples/views/radiobutton/RadioButtonGroupView.java']}
height='200px'
/>


## Agregando y removiendo componentes `RadioButton` {#adding-and-removing-radiobuttons}

Puedes incluir componentes `RadioButton` en el constructor de `RadioButtonGroup` para crear un grupo a partir de los componentes proporcionados.
Para agregar o eliminar un `RadioButton` de un `RadioButtonGroup` existente, utiliza los métodos `add()` o `remove()`.

:::tip Obteniendo el Grupo de un `RadioButton`
El componente `RadioButton` tiene el método `getButtonGroup()`, que devuelve el `RadioButtonGroup` al que pertenece, o `null` si no tiene un grupo.
:::

## Anidando <DocChip chip='since' label='25.11' /> {#nesting}

Al igual que otros componentes, puedes anidar un `RadioButtonGroup` dentro de un contenedor, así que no tienes que agregar directamente cada `RadioButton` individual.

```java
RadioButton agree = new RadioButton("Agree");
RadioButton neutral = new RadioButton("Neutral");
RadioButton disagree = new RadioButton("Disagree");

RadioButtonGroup group = new RadioButtonGroup("choices", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Options");
fieldset.add(group);
```

## Usando `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Cada `RadioButton` puede tener su propio listener de eventos para detectar cuando un usuario lo activa. Sin embargo, una ventaja de usar un `RadioButtonGroup` es que puedes usar un solo listener de eventos que responda a todos los botones de opción en el grupo con el [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html).

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

El siguiente ejemplo de [Colocación del Drawer](/docs/components/drawer#placement) utiliza el `RadioButtonGroupChangeEvent` para cambiar automáticamente la colocación del componente `Drawer`:

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Nomenclatura {#naming}

El atributo `name` en un `RadioButtonGroup` agrupa los RadioButtons relacionados, permitiendo a los usuarios hacer una sola elección entre las opciones proporcionadas y aplicando exclusividad entre los RadioButtons. El nombre de un grupo no se refleja en el DOM, sin embargo, y es una utilidad de conveniencia para el desarrollador en Java.
