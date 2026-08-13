---
title: TextArea
sidebar_position: 130
description: >-
  Capture multi-line input with the TextArea component, including paragraph
  management, character limits, wrapping, and validation.
_i18n_hash: f9863352a124e1af3575a849204b97ed
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

El componente `TextArea` proporciona un campo de entrada de texto de varias líneas donde los usuarios pueden escribir y editar bloques más largos de texto. Soporta límites máximos de caracteres, estructura de párrafos, ajuste de líneas y reglas de validación para controlar cómo se maneja la entrada.

<!-- INTRO_END -->

## Creando un `TextArea` {#creating-a-textarea}

Crea un `TextArea` pasando una etiqueta a su constructor. Propiedades como texto de marcador de posición, límites de caracteres y comportamiento de ajuste pueden configurarse a través de métodos de establecimiento.

<ComponentDemo
path='/webforj/textarea'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaView.java']}
height='300px'
/>

## Gestión de párrafos {#managing-paragraphs}

El componente `TextArea` proporciona características para manejar párrafos de texto, lo que lo hace ideal para aplicaciones que requieren edición de documentos o entrada de texto estructurado.

Aquí hay un ejemplo rápido de cómo construir y manipular el contenido de los párrafos:

```java
TextArea textArea = new TextArea();

// Insertar un párrafo al principio
textArea.addParagraph(0, "Este es el primer párrafo.");

// Agregar otro párrafo al final
textArea.addParagraph("Aquí hay un segundo párrafo.");

// Agregar contenido adicional al primer párrafo
textArea.appendToParagraph(0, " Esta oración continúa la primera.");

// Eliminar el segundo párrafo
textArea.removeParagraph(1);

// Recuperar e imprimir todos los párrafos actuales
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
  System.out.println("Párrafo " + i + ": " + paragraphs.get(i));
}
```

## Validación {#validation}

El componente `TextArea` admite dos tipos complementarios de validación: restricciones estructurales y restricciones de contenido.

**Las restricciones estructurales** se centran en cómo se organiza y presenta visualmente el texto. Por ejemplo:
- `setLineCountLimit(int maxLines)` restringe el número de líneas permitidas en el área de texto.
- `setParagraphLengthLimit(int maxCharsPerLine)` limita el número de caracteres por párrafo (o línea), ayudando a hacer cumplir estándares de legibilidad o formato.

**Las restricciones de contenido**, por otro lado, se ocupan de la cantidad total de texto ingresado, independientemente de cómo esté distribuido:
- `setMaxLength(int maxChars)` establece un límite máximo al número total de caracteres permitidos en todos los párrafos.
- `setMinLength(int minChars)` impone una longitud mínima, asegurando que se proporcione suficiente contenido.

La siguiente demostración permite a los usuarios ajustar los límites de validación, como el conteo máximo de caracteres, la longitud de los párrafos y el conteo de líneas, en tiempo real y ver cómo responde el `TextArea`.

<ComponentDemo
path='/webforj/textareavalidation'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java']}
height='550px'
/>

## Ajuste de palabras y ajuste de líneas {#word-wrap-and-line-wrapping}

Puedes controlar si el texto se ajusta o se desplaza horizontalmente utilizando `setLineWrap()`. Cuando el ajuste está desactivado, las líneas continúan horizontalmente más allá del área visible, requiriendo desplazamiento. Cuando está habilitado, el texto se ajusta automáticamente a la siguiente línea cuando llega al borde del componente.

Para refinar aún más cómo se comporta el ajuste, `setWrapStyle()` te permite elegir entre dos estilos:
- `WORD_BOUNDARIES` ajusta el texto en palabras completas, preservando el flujo natural de lectura.
- `CHARACTER_BOUNDARIES` ajusta en caracteres individuales, permitiendo un control más estricto sobre la disposición, especialmente en contenedores estrechos o de ancho fijo.

Estas opciones de ajuste funcionan de la mano con restricciones estructurales como los límites de conteo de líneas y longitud de párrafos. Mientras que el ajuste determina *cómo* fluye el texto dentro del espacio disponible, los límites estructurales definen *cuánto* espacio se permite ocupar al texto. Juntos, ayudan a mantener tanto la estructura visual como los límites de entrada del usuario.

<ComponentDemo
path='/webforj/textareawrap'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java']}
height='400px'
/>

## Texto predecido {#predicted-text}

El componente `TextArea` admite sugerencias inteligentes de texto para ayudar a los usuarios a escribir más rápido y con menos errores. A medida que los usuarios ingresan texto, aparecen sugerencias predictivas basadas en la entrada actual, lo que les permite completar frases comunes o esperadas.

Las predicciones pueden aceptarse presionando la tecla `Tab` o `ArrowRight`, insertando el texto sugerido en la entrada de forma fluida. Si no hay ninguna predicción adecuada disponible en un momento dado, la entrada permanece sin cambios, y el usuario puede continuar escribiendo sin interrupciones, asegurando que la función nunca se interponga.

Este comportamiento predictivo mejora tanto la velocidad como la precisión, especialmente en escenarios de entrada repetitiva o aplicaciones donde la consistencia de la frase es importante.

<ComponentDemo
path='/webforj/textareapredictedtext'
files={[
  'src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java',
  'src/main/frontend/css/textarea/text-area-predicted-text-view.css',
]}
height='400px'
/>

:::info
Esta demostración utiliza la [API Datamuse](https://datamuse.com/) para proporcionar sugerencias de palabras basadas en la entrada del usuario. La calidad y relevancia de las predicciones dependen completamente del conjunto de datos y el mecanismo de puntuación de la API. No utiliza modelos de IA o modelos de lenguaje grandes (LLMs); las sugerencias se generan a partir de un motor ligero basado en reglas centrado en la similitud léxica.
:::

## Estado de solo lectura y desactivado {#read-only-and-disabled-state}

El componente `TextArea` puede configurarse en modo solo lectura o desactivado para controlar la interacción del usuario.

Un área de texto **solo lectura** permite a los usuarios ver y seleccionar el contenido, pero no editarlo. Esto es útil para mostrar información dinámica o prellenada que debe permanecer sin cambios.

Por otro lado, un área de texto **desactivada** bloquea toda interacción, incluyendo el enfoque y la selección de texto, y típicamente se presenta como inactiva o atenuada.

Utiliza el modo solo lectura cuando el contenido es relevante pero inmutable, y el modo desactivado cuando la entrada no es actualmente aplicable o debería estar temporalmente inactiva.

<ComponentDemo
path='/webforj/textareastates'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java']}
height='300px'
/>

## Estilo {#styling}

<TableBuilder name="TextArea" />
