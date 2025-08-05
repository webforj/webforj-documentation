---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: b04acdedbd800790417edfe940160bf2
---
<JavadocLink type="foundation" location="com/webforj/component/field/AbstractField"/>

El marco webforJ admite siete tipos diferentes de componentes de campo, cada uno con comportamientos e implementaciones diferentes que se adaptan a diversas necesidades de entrada. Aunque cada uno de estos componentes tiene variaciones en sus implementaciones, este artículo describe las propiedades compartidas entre todas las clases de campo.

:::info
Esta sección describe características comunes de varios componentes de campo en webforJ, y no es en sí misma una clase que se pueda instanciar y usar.
:::

## Propiedades compartidas del campo {#shared-field-properties}

### Etiqueta {#label}

Una etiqueta de campo es un texto descriptivo o título asociado con el campo que se puede definir en el constructor o utilizando el método `setLabel()`. Las etiquetas proporcionan una breve explicación o indicación para ayudar a los usuarios a comprender el propósito o la entrada esperada para ese campo en particular. Las etiquetas de campo son importantes para la usabilidad y juegan un papel crucial en la accesibilidad, ya que permiten a los lectores de pantalla y a las tecnologías de asistencia proporcionar información precisa y facilitar la navegación por teclado.

### Texto de ayuda {#helper-text}

Cada campo puede mostrar texto de ayuda debajo de la entrada utilizando el método `setHelperText()`. Este texto de ayuda ofrece contexto adicional o explicaciones sobre las entradas disponibles, asegurando que los usuarios tengan la información necesaria para hacer selecciones informadas.

### Requerido {#required}

Puedes llamar al método `setRequired(true)` para requerir que los usuarios proporcionen un valor antes de enviar un formulario. Esta propiedad funciona en conjunto con la etiqueta del campo, proporcionando una indicación visual de que un campo es necesario. Esta señal visual ayuda a las personas a completar formularios de manera precisa.

:::info
Los componentes de campo contienen validación visual integrada para notificar a los usuarios cuando un campo requerido está vacío o si un usuario ha eliminado un valor.
:::

### Corrector ortográfico {#spellcheck}

Al utilizar `setSpellCheck(true)`, puedes permitir que el navegador o agente de usuario verifique la ortografía del texto ingresado por el usuario e identifique cualquier error.

### Prefijo y sufijo {#prefix-and-suffix}

Los slots ofrecen opciones flexibles para mejorar la capacidad de los componentes de campo. Puedes tener íconos, etiquetas, indicadores de carga, capacidad de borrar/restablecer, fotos de avatar/perfil y otros componentes beneficiosos anidados dentro de un campo para aclarar aún más el significado intencionado para los usuarios. Los campos tienen dos slots: el slot `prefix` y el slot `suffix`. Usa los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de la opción mostrada dentro de un campo. Aquí hay un ejemplo utilizando el campo `TextField`:

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## Estilo {#styling}

:::info
Debido a que todos los componentes de campo están construidos a partir de un único componente web, todos comparten los seguintes Shadow Parts y valores de propiedades CSS
:::

<TableBuilder name="Field" />

## Temas {#topics}

<DocCardList className="topics-section" />
