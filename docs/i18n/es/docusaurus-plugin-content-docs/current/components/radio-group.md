---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 8e58efd7b052a00eaf8cfce276cda92e
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

La clase `RadioButtonGroup` se utiliza para agrupar botones de radio relacionados, lo que ayuda a establecer la exclusividad mutua entre las opciones dentro de ese grupo. Los usuarios solo pueden seleccionar un botón de radio dentro de un grupo de radio dado. Cuando un usuario selecciona un botón de radio dentro de un grupo, cualquier botón de radio previamente seleccionado en el mismo grupo se deselecciona automáticamente. Esto asegura que solo se puede elegir una opción a la vez.

:::tip
Un componente `RadioButton` almacena el grupo al que pertenece, que se puede acceder a través del método `getButtonGroup()`.
:::

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important
El componente `RadioButtonGroup` no renderiza un elemento HTML en la página. Más bien, es solo
una lógica que garantiza que un grupo de RadioButtons se comporte como un grupo en lugar de individualmente.
:::

## Usos {#usages}

El `RadioButtonGroup` se utiliza mejor en escenarios donde los usuarios necesitan hacer una única selección de un conjunto predefinido de opciones presentadas como botones de radio. Aquí hay algunos ejemplos de cuándo usar el `RadioButtonGroup`:

1. **Encuestas o Cuestionarios**: Los componentes `RadioButtonGroup` se utilizan comúnmente en encuestas o cuestionarios donde los usuarios necesitan seleccionar una única respuesta de una lista de opciones.

2. **Configuraciones de Preferencias**: Las aplicaciones que involucran paneles de preferencias o configuraciones a menudo utilizan el componente RadioButtonGroup para permitir a los usuarios elegir una única opción de un conjunto de elecciones mutuamente exclusivas.

3. **Filtrado o Clasificación**: Un `RadioButton` se puede usar en aplicaciones que requieren que los usuarios seleccionen un único filtro o opción de clasificación, como ordenar una lista de elementos por diferentes criterios.

<!-- vale off -->
## Agregar y eliminar RadioButtons {#adding-and-removing-radiobuttons}
<!-- vale on -->

Es posible agregar y eliminar objetos `RadioButton` singulares o múltiples a un grupo, asegurando que exhiban un comportamiento de selección mutuamente exclusivo y estén asociados con cualquier nombre que pueda pertenecer al grupo.

## Nomenclatura {#naming}

El atributo de nombre en un `RadioButtonGroup` agrupa los RadioButtons relacionados, permitiendo a los usuarios hacer una única elección de las opciones proporcionadas y reforzando la exclusividad entre los RadioButtons. El nombre de un grupo no se refleja en el DOM, sin embargo, y es una utilidad conveniente para el desarrollador de Java.

## Mejores prácticas {#best-practices}

Para garantizar una experiencia de usuario óptima al utilizar el componente RadioButton, considere las siguientes mejores prácticas:

1. **Etiquetas Claras para las Opciones**: Proporcione etiquetas claras y concisas para cada opción de `RadioButton` para describir con precisión la elección. Las etiquetas deben ser fáciles de entender y distinguir entre sí.

2. **Proporcionar Selección Predeterminada**: Si es aplicable, considere proporcionar una selección predeterminada para los botones de radio para guiar a los usuarios cuando se encuentran por primera vez con las opciones. La selección predeterminada debe alinearse con la opción más común o preferida.
