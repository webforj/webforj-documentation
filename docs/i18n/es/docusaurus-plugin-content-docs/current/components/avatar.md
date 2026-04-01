---
title: Avatar
sidebar_position: 7
sidebar_class_name: new-content
_i18n_hash: 928db2bff36515d2d9a41aeca9a233e0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

El componente `Avatar` proporciona una representación visual de un usuario o entidad. Puede mostrar una imagen, iniciales auto-computadas, iniciales personalizadas o un ícono. Los avatares se utilizan comúnmente para identificar a los usuarios en secciones de comentarios, menús de navegación, aplicaciones de chat y listas de contactos.

<!-- INTRO_END -->

## Creando avatares {#creating-avatars}

Para crear un `Avatar`, pasa una etiqueta que sirva como el nombre accesible. El componente calcula automáticamente las iniciales extrayendo la primera letra de cada palabra en la etiqueta.

```java
// Crea un avatar mostrando "JD" a partir de la etiqueta
Avatar avatar = new Avatar("John Doe");
```

También puedes proporcionar iniciales explícitas si prefieres tener más control sobre lo que se muestra:

```java
// Crea un avatar con iniciales personalizadas
Avatar avatar = new Avatar("John Doe", "J");
```

El ejemplo a continuación muestra avatares en un contexto de panel de equipo. Cada `Avatar` muestra una imagen de perfil o iniciales auto-generadas basadas en el nombre del usuario. Hacer clic en un `Avatar` abre un diálogo con una vista ampliada.

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '450px'
/>

## Mostrando imágenes {#displaying-images}

El componente `Avatar` puede mostrar una imagen en lugar de iniciales insertando un componente `Img` como hijo. Cuando se proporciona una imagen, tiene prioridad sobre las iniciales.

```java
import com.webforj.component.html.elements.Img;

// Avatar con una imagen
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Redimensionamiento de la imagen
La imagen se escala automáticamente para ajustarse a las dimensiones del avatar según la configuración de expansión actual.
:::

## Mostrando íconos {#displaying-icons}

Puedes mostrar un ícono dentro del `Avatar` añadiendo un componente `Icon` como hijo:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar con un ícono
Avatar avatar = new Avatar("Guest User", TablerIcon.create("user"));
```

## Etiqueta e iniciales {#label-and-initials}

El componente `Avatar` utiliza la etiqueta para accesibilidad y generación de tooltips. Los métodos `setLabel()` y `setText()` son alias que establecen la etiqueta accesible para el `Avatar`.

:::info Iniciales auto-computadas
Cuando creas un `Avatar` con solo una etiqueta, las iniciales se computan automáticamente tomando el primer carácter de cada palabra. Por ejemplo, un `Avatar` con la etiqueta "John Doe" muestra automáticamente "JD" en la interfaz de usuario.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Establece la etiqueta y genera automáticamente el tooltip
avatar.setInitials("JS");       // Sobrescribe las iniciales auto-computadas
```

:::tip Tooltip automático
El componente genera automáticamente un tooltip a partir de la etiqueta, facilitando ver el nombre completo al pasar el ratón. Este comportamiento se desactiva al usar la etiqueta predeterminada `"Avatar"`.
:::

## Eventos de clic {#click-events}

El componente `Avatar` implementa `HasElementClickListener`, lo que te permite responder a los clics del usuario. Esto es útil para activar acciones como abrir el perfil de un usuario o mostrar un menú.

```java
avatar.onClick(event -> {
  // Manejar clic en el avatar - por ejemplo, abrir el perfil del usuario
  System.out.println("¡Avatar clicado!");
});
```

## Formas {#shapes}

Los avatares pueden mostrarse como círculos o cuadrados. La forma predeterminada es `CIRCLE`, que es estándar para avatares de usuario. Usa `SQUARE` para entidades como equipos, empresas o aplicaciones.

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## Temas {#themes}

Los temas transmiten significado o estado; puedes usarlos para indicar disponibilidad, resaltar usuarios importantes o coincidir con el diseño de tu aplicación.

Los siguientes temas están disponibles:

- `DEFAULT`: Apariencia estándar
- `GRAY`: Apariencia neutral y apagada
- `PRIMARY`: Enfatiza acciones o usuarios principales
- `SUCCESS`: Indica un estado positivo (por ejemplo, en línea)
- `WARNING`: Indica precaución (por ejemplo, ausente)
- `DANGER`: Indica un estado de error o ocupado
- `INFO`: Proporciona contexto informativo

Cada tema también tiene una variante contorneada para un tratamiento visual más ligero:

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## Expansiones {#expanses}

Controla el tamaño del avatar utilizando el método `setExpanse()`. El componente soporta nueve opciones de tamaño que van desde `XXXSMALL` hasta `XXXLARGE`.

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>


## Estilo {#styling}

<TableBuilder name="Avatar" />
