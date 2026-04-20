---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: c50931f8465e3be081ecfee03a3ef559
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

El componente `MaskedTextField` proporciona una entrada de texto configurable que impone reglas de formato y validación. Es adecuado para aplicaciones que requieren entradas estructuradas, como sistemas financieros, de comercio electrónico y de salud.

<!-- INTRO_END -->

## Básicos {#basics}

El `MaskedTextField` se puede instanciar con o sin parámetros. Puedes definir un valor inicial, una etiqueta, un texto de marcador de posición y un oyente en caso de que el valor cambie.

```java
MaskedTextField field = new MaskedTextField("ID de cuenta");
field.setMask("ZZZZ-0000")
  .setHelperText("Máscara: ZZZZ-0000 - por ejemplo: SAVE-2025")
```

## Reglas de máscara {#mask-rules}

El `MaskedTextField` formatea la entrada de texto usando una máscara, que es una cadena que define qué caracteres se permiten en cada posición. Esto asegura una entrada consistente y estructurada para cosas como números de teléfono, códigos postales y formatos de identificación.

:::tip Aplicando máscaras de manera programática
Para formatear cadenas con la misma sintaxis de máscara fuera de un campo, por ejemplo, al renderizar datos en una [`Table`](/docs/components/table/overview), utiliza la clase utilitaria [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Caracteres de máscara soportados {#supported-mask-characters}

| Carácter | Descripción                                                                                 |
|----------|---------------------------------------------------------------------------------------------|
| `X`      | Cualquier carácter imprimible                                                                |
| `a`      | Cualquier carácter alfabético (mayúscula o minúscula)                                       |
| `A`      | Cualquier carácter alfabético; las letras minúsculas se convierten a mayúsculas             |
| `0`      | Cualquier dígito (0–9)                                                                       |
| `z`      | Cualquier dígito o letra (mayúscula o minúscula)                                            |
| `Z`      | Cualquier dígito o letra; las letras minúsculas se convierten a mayúsculas                  |

Todos los demás caracteres en la máscara se tratan como literales y deben ser escritos exactamente. 
Por ejemplo, una máscara como `XX@XX` requiere que el usuario ingrese un `@` en el medio.

- **Los caracteres no válidos** son ignorados silenciosamente.
- **La entrada corta** se complemente con espacios.
- **La entrada larga** se trunca para ajustarse a la máscara.

### Ejemplos {#examples}

```java
field.setMask("(000) 000-0000");     // Ejemplo: (123) 456-7890
field.setMask("A00 000");            // Ejemplo: A1B 2C3 (código postal canadiense)
field.setMask("ZZZZ-0000");          // Ejemplo: ABCD-1234
field.setMask("0000-0000-0000-0000");// Ejemplo: 1234-5678-9012-3456
```

:::tip Entrada completa permitida
Si la máscara solo contiene `X`, el campo se comporta como un [`TextField`](../textfield) estándar, permitiendo cualquier entrada imprimible.
Esto es útil cuando quieres reservar la capacidad de formatear sin aplicar reglas estrictas sobre los caracteres.
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## Patrones de validación {#validation-patterns}

Mientras que las máscaras definen la estructura de la entrada, puedes combinarlas con patrones de validación para imponer reglas de entrada más específicas. Esto añade una capa adicional de validación del lado del cliente utilizando expresiones regulares.

Utiliza el método `setPattern()` para aplicar una expresión regular personalizada:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Impone un código alfanumérico de 10 caracteres
```

Esto asegura que la entrada no solo coincida con la máscara, sino que también se ajuste a una estructura definida, como la longitud o los caracteres permitidos.

Esto es especialmente útil cuando:

- La máscara permite demasiada flexibilidad
- Quieres imponer una longitud exacta o un formato específico (p. ej. hex, Base64, UUID)

:::tip Formato de expresión regular
El patrón debe ser una expresión regular válida de [JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), como la que utiliza el tipo `RegExp`. Puedes encontrar más detalles en la [documentación del atributo pattern de HTML](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Restaurando el valor {#restoring-the-value}

El `MaskedTextField` incluye una función de restauración que restablece el valor del campo a un estado predefinido u original. 
Esto puede ser útil para deshacer cambios del usuario o volver a una entrada predeterminada.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Formas de restaurar el valor {#ways-to-restore-the-value}

- **De manera programática**, llamando a `restoreValue()`
- **A través del teclado**, presionando <kbd>ESC</kbd> (esta es la tecla de restauración predeterminada a menos que se anule mediante un oyente de eventos)

Puedes establecer el valor a restaurar con `setRestoreValue()`. Si no se establece ningún valor de restauración, el campo volverá al valor inicial en el momento en que se renderizó.

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

El `MaskedTextFieldSpinner` extiende el [`MaskedTextField`](#basics) añadiendo controles de spinner que permiten a los usuarios ciclar a través de una lista de valores predefinidos. 
Esto mejora la experiencia del usuario en situaciones donde la entrada debe estar limitada a un conjunto fijo de opciones válidas.

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### Características clave {#key-features}

- **Soporte para listas de opciones**  
  Poblab el spinner con una lista de valores de cadena válidos utilizando `setOptions()`:

  ```java
  spinner.setOptions(List.of("Opción A", "Opción B", "Opción C"));
  ```

- **Rotación programática**  
  Utiliza `spinUp()` y `spinDown()` para moverte entre opciones:

  ```java
  spinner.spinUp();   // Selecciona la siguiente opción
  spinner.spinDown(); // Selecciona la opción anterior
  ```

- **Control de índice**  
  Establece o recupera el índice de selección actual con:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Compatibilidad con máscaras**  
  Hereda completamente todas las reglas de formateo, reglas de máscara y validación de patrones de `MaskedTextField`.

## Estilizando {#styling}

<TableBuilder name="MaskedTextField" />
