---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
sidebar_class_name: updated-content
_i18n_hash: 564d1d0c46ef2395fb2ad2785ba18d53
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

El `RadioButtonGroup` gestiona una colección de [`RadioButton`](/docs/components/radiobutton) componentes. Solo un `RadioButton` puede estar seleccionado en un `RadioButtonGroup`. Cuando un usuario selecciona un nuevo botón de opción, el previamente seleccionado en el grupo se deselecciona automáticamente.

<!-- INTRO_END -->

## Creando un `RadioButtonGroup` {#creating-a-radiobuttongroup}

:::important Renderizado del `RadioButtonGroup`
El componente `RadioButtonGroup` no renderiza un elemento HTML. Solo proporciona la lógica para hacer que los componentes `RadioButton` se comporten como un grupo en lugar de individualmente.
:::

Crea componentes `RadioButton` individuales y pásalos al constructor de `RadioButtonGroup`. Solo un botón en el grupo puede estar seleccionado a la vez.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>


## Agregando y eliminando componentes `RadioButton` {#adding-and-removing-radiobuttons}

Puedes incluir componentes `RadioButton` en el constructor de `RadioButtonGroup` para crear un grupo a partir de los componentes proporcionados.
Para agregar o eliminar un `RadioButton` de un `RadioButtonGroup` existente, utiliza los métodos `add()` o `remove()`.

:::tip Obteniendo el Grupo de un `RadioButton`
El componente `RadioButton` tiene el método `getButtonGroup()`, que devuelve el `RadioButtonGroup` al que pertenece, o `null` si no tiene un grupo.
:::

## Anidando <DocChip chip='since' label='25.11' /> {#nesting}

Al igual que otros componentes, puedes anidar un `RadioButtonGroup` dentro de un contenedor, por lo que no tienes que agregar directamente cada `RadioButton` individualmente.

```java
RadioButton agree = new RadioButton("Aceptar");
RadioButton neutral = new RadioButton("Neutral");
RadioButton disagree = new RadioButton("Rechazar");

RadioButtonGroup group = new RadioButtonGroup("opciones", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Opciones");
fieldset.add(group);
```

## Usando `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Cada `RadioButton` puede tener su propio listener de eventos para detectar cuando un usuario lo activa. Sin embargo, una ventaja de usar un `RadioButtonGroup` es que puedes usar un solo listener de eventos que responda a todos los botones de radio en el grupo con el [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html).

**Agregando listeners de eventos a cada `RadioButton`**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Agregando un solo listener de eventos al `RadioButtonGroup`**

```java
RadioButtonGroup group = new RadioButtonGroup("opciones", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

El siguiente ejemplo de [Ubicación del Drawer](/docs/components/drawer#placement) utiliza el `RadioButtonGroupChangeEvent` para cambiar automáticamente la ubicación del componente `Drawer`:

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Nombrando {#naming}

El atributo `name` en un `RadioButtonGroup` agrupa juntos los RadioButtons relacionados, permitiendo a los usuarios hacer una única elección entre las opciones proporcionadas y haciendo cumplir la exclusividad entre los RadioButtons. El nombre de un grupo no se refleja en el DOM, sin embargo, y es una utilidad de conveniencia para el desarrollador Java.
