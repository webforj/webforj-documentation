---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
description: >-
  Share common field properties across webforJ input components, including
  labels, helper text, required state, spellcheck, and prefix or suffix slots.
_i18n_hash: 56462cf4d659ce15f72a624acae60f0f
---
<JavadocLink type="foundation" location="com/webforj/component/field/DwcField"/>

El marco webforJ soporta once tipos de componentes de campo, incluyendo cuatro campos enmascarados, cada uno con diferentes comportamientos e implementaciones que se adaptan a diversas necesidades de entrada. Aunque cada uno de estos componentes tiene variaciones en sus implementaciones, este artículo describe las propiedades compartidas entre todas las clases de campos.

:::info
Esta sección describe características comunes de varios componentes de campo en webforJ, y no es en sí misma una clase que pueda ser instanciada y utilizada.
:::

## Propiedades compartidas de los campos {#shared-field-properties}

### Etiqueta {#label}

Una etiqueta de campo es un texto o título descriptivo que está asociado con el campo y que puede ser definido en el constructor o utilizando el método `setLabel()`. Las etiquetas proporcionan una breve explicación o indicación para ayudar a los usuarios a entender el propósito o la entrada esperada para ese campo en particular. Las etiquetas de campo son importantes para la usabilidad y juegan un papel crucial en la accesibilidad, ya que permiten que los lectores de pantalla y las tecnologías asistivas brinden información precisa y faciliten la navegación por teclado.

### Texto de ayuda {#helper-text}

Cada campo puede mostrar texto de ayuda debajo de la entrada utilizando el método `setHelperText()`. Este texto de ayuda ofrece contexto adicional o explicaciones sobre las entradas disponibles, asegurando que los usuarios tengan la información necesaria para realizar selecciones informadas.

### Requerido {#required}

Puedes llamar al método `setRequired(true)` para exigir que los usuarios proporcionen un valor antes de enviar un formulario. Esta propiedad funciona en conjunto con la etiqueta del campo, proporcionando una indicación visual de que un campo es necesario. Esta señal visual ayuda a las personas a completar formularios de manera precisa.

:::info
Los componentes de campo contienen validación visual incorporada para notificar a los usuarios cuando un campo requerido está vacío o si un usuario ha eliminado un valor.
:::

### Comprobación ortográfica {#spellcheck}

Al usar `setSpellCheck(true)`, puedes permitir que el navegador o el agente de usuario verifiquen la ortografía del texto ingresado por el usuario e identifiquen errores.

### Prefijo y sufijo {#prefix-and-suffix}

Los slots proporcionan opciones flexibles para mejorar la capacidad de los componentes de campo. Puedes tener íconos, etiquetas, spinners de carga, capacidad de limpiar/restablecer, fotos de avatar/perfil y otros componentes beneficiosos anidados dentro de un campo para aclarar aún más el significado previsto para los usuarios. Los campos tienen dos slots: el slot `prefijo` y el slot `sufijo`. Utiliza los métodos `setPrefixComponent()` y `setSuffixComponent()` para insertar varios componentes antes y después de la opción mostrada dentro de un campo. Aquí hay un ejemplo usando el campo `TextField`:

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## Estilo {#styling}

:::info
Debido a que todos los componentes de campo están construidos a partir de un componente web singular, todos comparten los siguientes Shadow Parts y valores de propiedades CSS
:::

<TableBuilder name="Field" />

## Temas {#topics}

<DocCardList className="topics-section" />
