---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 288d408cb058dbaa417fea651698123a
---
<JavadocLink type="foundation" location="com/webforj/component/field/AbstractField"/>

El marco webforJ soporta siete tipos diferentes de componentes de campo, cada uno con comportamientos e implementaciones variados que se adaptan a diversas necesidades para entradas. Aunque cada uno de estos componentes tiene variaciones en sus implementaciones, este artículo describe las propiedades compartidas entre todas las clases de campo.

:::info
Esta sección describe características comunes de varios componentes de campo en webforJ, y no es en sí misma una clase que se puede instanciar y usar.
:::

## Propiedades de campo compartidas {#shared-field-properties}

### Etiqueta {#label}

Una etiqueta de campo es un texto descriptivo o título que está asociado con el campo y que se puede definir en el constructor o usando el método `setLabel()`. Las etiquetas proporcionan una breve explicación o indicación para ayudar a los usuarios a entender el propósito o la entrada esperada para ese campo en particular. Las etiquetas de campo son importantes para la usabilidad y juegan un papel crucial en la accesibilidad, ya que permiten que los lectores de pantalla y las tecnologías de asistencia proporcionen información precisa y faciliten la navegación por teclado.

### Texto de ayuda {#helper-text}

Cada campo puede mostrar texto de ayuda debajo de la entrada usando el método `setHelperText()`. Este texto de ayuda ofrece contexto o explicaciones adicionales sobre las entradas disponibles, asegurando que los usuarios tengan la información necesaria para hacer selecciones informadas.

### Requerido {#required}

Puedes llamar al método `setRequired(true)` para requerir que los usuarios proporcionen un valor antes de enviar un formulario. Esta propiedad funciona en conjunto con la etiqueta del campo, proporcionando una indicación visual de que un campo es necesario. Esta señal visual ayuda a las personas a completar formularios de manera precisa.

:::info
Los componentes de campo contienen validación visual incorporada para notificar a los usuarios cuando un campo requerido está vacío o si un usuario eliminó un valor.
:::

### Corrección ortográfica {#spellcheck}

Al usar `setSpellCheck(true)`, puedes permitir que el navegador o agente de usuario verifique la ortografía del texto ingresado por el usuario e identifique cualquier error.

### Prefijo y sufijo {#prefix-and-suffix}

Los slots proporcionan opciones flexibles para mejorar la capacidad de los componentes de campo. Puedes tener íconos, etiquetas, indicadores de carga, capacidad de borrar/restablecer, imágenes de avatar/perfil y otros componentes beneficiosos anidados dentro de un campo para aclarar aún más el significado intencionado para los usuarios. Los campos tienen dos slots: el slot `prefix` y el slot `suffix`. Usa los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de la opción mostrada dentro de un campo. Aquí hay un ejemplo usando el campo `TextField`:

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## Estilizando {#styling}

:::info
Debido a que todos los componentes de campo están construidos a partir de un único componente web, todos comparten las
siguientes partes de sombra y valores de propiedades CSS
:::

<TableBuilder name="Field" />

## Temas {#topics}

<DocCardList className="topics-section" />
