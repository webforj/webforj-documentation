---
title: TextArea
sidebar_position: 130
_i18n_hash: e8956f1a5bf39eab9a42244ff8d5ff21
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

El componente `TextArea` proporciona un campo de entrada de texto multilínea donde los usuarios pueden escribir y editar bloques de texto más largos. Soporta límites máximos de caracteres, estructura de párrafos, ajuste de línea y reglas de validación para controlar cómo se maneja la entrada.

<!-- INTRO_END -->

## Creando un `TextArea` {#creating-a-textarea}

Crea un `TextArea` pasando una etiqueta a su constructor. Propiedades como texto de marcador de posición, límites de caracteres y comportamiento de ajuste se pueden configurar a través de métodos setters.

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Manejo de párrafos {#managing-paragraphs}

El componente `TextArea` proporciona características para manejar párrafos de texto, lo que lo hace ideal para aplicaciones que requieren edición de documentos o entrada de texto estructurada.

Aquí hay un ejemplo rápido de cómo construir y manipular contenido de párrafos:

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
- `setParagraphLengthLimit(int maxCharsPerLine)` limita el número de caracteres por párrafo (o línea), ayudando a hacer cumplir normas de legibilidad o formato.

**Las restricciones de contenido**, por otro lado, se ocupan de la cantidad total de texto ingresado, independientemente de cómo se distribuya:
- `setMaxLength(int maxChars)` limita el número total de caracteres permitidos en todos los párrafos.
- `setMinLength(int minChars)` impone una longitud mínima, asegurando que se proporcione suficiente contenido.

La siguiente demostración permite a los usuarios ajustar los límites de validación—como el número máximo de caracteres, la longitud del párrafo y el conteo de líneas—en tiempo real y ver cómo responde el `TextArea`.

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Ajuste de palabras y ajuste de línea {#word-wrap-and-line-wrapping}

Puedes controlar si el texto se ajusta o se desplaza horizontalmente usando `setLineWrap()`. Cuando el ajuste está deshabilitado, las líneas continúan horizontalmente más allá del área visible, requiriendo desplazamiento. Cuando está habilitado, el texto se ajusta automáticamente a la siguiente línea cuando alcanza el borde del componente.

Para refinar aún más cómo se comporta el ajuste, `setWrapStyle()` te permite elegir entre dos estilos:
- `WORD_BOUNDARIES` ajusta el texto en palabras completas, preservando el flujo de lectura natural.
- `CHARACTER_BOUNDARIES` ajusta a caracteres individuales, permitiendo un control más preciso sobre el diseño, especialmente en contenedores estrechos o de ancho fijo.

Estas opciones de ajuste funcionan mano a mano con restricciones estructurales como límites de conteo de líneas y longitud de párrafo. Mientras que el ajuste determina *cómo* fluye el texto dentro del espacio disponible, los límites estructurales definen *cuánto* espacio se permite ocupar al texto. Juntos, ayudan a mantener tanto la estructura visual como los límites de entrada del usuario.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Texto predictivo {#predicted-text}

El componente `TextArea` admite sugerencias inteligentes de texto para ayudar a los usuarios a escribir más rápido y con menos errores. A medida que los usuarios ingresan texto, aparecen sugerencias predictivas basadas en la entrada actual, lo que les permite completar frases comunes o esperadas.

Las predicciones se pueden aceptar presionando la tecla `Tab` o `Flecha Derecha`, insertando el texto sugerido en la entrada sin problemas. Si no hay una predicción adecuada disponible en un momento dado, la entrada permanece sin cambios y el usuario puede seguir escribiendo sin interrupción, asegurando que la función nunca interfiera.

Este comportamiento predictivo mejora tanto la velocidad como la precisión, especialmente en escenarios de entrada repetitiva o aplicaciones donde la consistencia de la frase es importante.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
cssURL='/css/textarea/text-area-predicted-text-view.css'
height = '400px'
/>

:::info
Esta demostración utiliza la [API Datamuse](https://datamuse.com/) para proporcionar sugerencias de palabras basadas en la entrada del usuario. La calidad y relevancia de las predicciones dependen completamente del conjunto de datos y del mecanismo de puntuación de la API. No utiliza modelos de IA o modelos de lenguaje grande (LLMs); las sugerencias son generadas por un motor ligero, basado en reglas, enfocado en la similitud léxica.
:::

## Estado de solo lectura y deshabilitado {#read-only-and-disabled-state}

El componente `TextArea` se puede configurar como solo lectura o deshabilitado para controlar la interacción del usuario.

Un área de texto **solo lectura** permite a los usuarios ver y seleccionar el contenido, pero no editarlo. Esto es útil para mostrar información dinámica o prellenada que debe permanecer sin cambios.

Un área de texto **deshabilitada**, por otro lado, bloquea toda interacción—incluyendo enfoque y selección de texto—y se estiliza típicamente como inactiva o descolorida.

Utiliza el modo solo lectura cuando el contenido es relevante pero inmutable, y el modo deshabilitado cuando la entrada no es actualmente aplicable o debería estar temporalmente inactiva.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Estilo {#styling}

<TableBuilder name="TextArea" />
