---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 91d753e882e3d6d59deef5044ee7bc4c
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

La clase `RadioButtonGroup` se utiliza para agrupar botones de radio relacionados, lo que ayuda a establecer la exclusividad mutua entre las opciones dentro de ese grupo. Los usuarios solo pueden seleccionar un botón de radio dentro de un grupo de radio dado. Cuando un usuario selecciona un botón de radio dentro de un grupo, cualquier botón de radio previamente seleccionado en el mismo grupo automáticamente queda deseleccionado. Esto garantiza que solo se pueda elegir una opción a la vez.

:::tip
Un componente `RadioButton` almacena el grupo al que pertenece, el cual se puede acceder a través del método `getButtonGroup()`.
:::

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important
El componente `RadioButtonGroup` no renderiza un elemento HTML en la página. Más bien, es solo
lógica que asegura que un grupo de RadioButtons se comporte como un grupo en lugar de individualmente.
:::

## Usos {#usages}

El `RadioButtonGroup` es mejor utilizado en situaciones donde los usuarios necesitan hacer una sola selección de un conjunto predefinido de opciones presentadas como botones de radio. Aquí hay algunos ejemplos de cuándo usar el `RadioButtonGroup`:

1. **Encuestas o Cuestionarios**: Los componentes `RadioButtonGroup` son comúnmente utilizados en encuestas o cuestionarios donde los usuarios necesitan seleccionar una única respuesta de una lista de opciones.

2. **Configuraciones de Preferencias**: Las aplicaciones que involucran paneles de preferencias o configuraciones a menudo utilizan el componente RadioButtonGroup para permitir a los usuarios elegir una sola opción de un conjunto de opciones mutuamente excluyentes.

3. **Filtrando o Ordenando**: Un `RadioButton` puede ser utilizado en aplicaciones que requieren que los usuarios seleccionen un único filtro u opción de ordenamiento, como ordenar una lista de elementos por diferentes criterios.

<!-- vale off -->
## Agregar y quitar RadioButtons {#adding-and-removing-radiobuttons}
<!-- vale on -->

Es posible agregar y quitar objetos `RadioButton` singulares o múltiples a un grupo, asegurando que exhiban un comportamiento de verificación mutuamente exclusivo, y estén asociados con cualquier nombre que pueda pertenecer al grupo.

## Nomenclatura {#naming}

El atributo de nombre en un `RadioButtonGroup` agrupa los RadioButtons relacionados, permitiendo a los usuarios hacer una sola elección de las opciones proporcionadas y forzando la exclusividad entre los RadioButtons. El nombre de un grupo no se refleja en el DOM, sin embargo, es una utilidad de conveniencia para el desarrollador de Java.

## Mejores prácticas {#best-practices}

Para garantizar una experiencia óptima del usuario al utilizar el componente RadioButton, considere las siguientes mejores prácticas:

1. **Etiquetar Claramente las Opciones**: Proporcione etiquetas claras y concisas para cada opción de `RadioButton` para describir con precisión la elección. Las etiquetas deben ser fáciles de entender y distinguir entre sí.

2. **Proporcionar Selección Predeterminada**: Si es aplicable, considere proporcionar una selección predeterminada para los botones de radio para guiar a los usuarios cuando se encuentren por primera vez con las opciones. La selección predeterminada debe estar alineada con la opción más común o preferida.
