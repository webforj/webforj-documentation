---
title: Icon
sidebar_position: 55
_i18n_hash: 5c32d2def53818005b15e22290fb3d52
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

El componente `Icon` muestra iconos que se escalan a cualquier tamaño sin perder calidad. Puedes elegir entre tres conjuntos de iconos incorporados o crear los personalizados. Los iconos sirven como pistas visuales para la navegación y acciones, reduciendo la necesidad de etiquetas de texto en tu interfaz.

<!-- INTRO_END -->

## Básicos {#basics}

Cada `Icon` está diseñado como una imagen de Gráficos Vectoriales Escalables (SVG), lo que significa que puede escalar fácilmente a cualquier tamaño sin perder claridad o calidad. Además, los componentes `Icon` se cargan bajo demanda desde una red de entrega de contenido (CDN), lo que ayuda a reducir la latencia y mejorar el rendimiento general.

Al crear un `Icon`, deberás identificar un conjunto específico y el nombre del icono en sí. Algunos iconos también ofrecen la opción entre una versión contornada o llena a través de [variaciones](#variations).

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

:::tip ¿Sabías que?
Algunos componentes, como `PasswordField` y `TimeField`, tienen iconos integrados para ayudar a transmitir significado a los usuarios finales.
:::

### Conjuntos {#pools}

Un conjunto de iconos es una colección de iconos comúnmente utilizados que permite un acceso y reutilización fáciles. Al utilizar iconos de un conjunto de iconos, puedes asegurar que los iconos en tu aplicación son reconocibles y comparten un estilo consistente. Usar webforJ te permite elegir entre tres conjuntos, o implementar un conjunto personalizado. Cada conjunto tiene una extensa colección de iconos de código abierto que son gratuitos para usar. Usar webforJ te da la flexibilidad de elegir entre tres conjuntos y utilizarlos como clases únicas, sin la molestia de descargar ninguno de los iconos directamente.

| Conjunto de Iconos                                | Clase webforJ  |
| -------------------                               | --------       |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` y `DwcIcon`.<br/>`DwcIcon` es un subconjunto de los iconos de Tabler.|    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Si te interesa crear tu propio conjunto de iconos, consulta [Creando conjuntos personalizados](#creating-custom-pools).

:::

Una vez que hayas seleccionado el conjunto o los conjuntos para incluir en tu aplicación, el siguiente paso es especificar el nombre del icono que deseas usar.

### Nombres {#names}

Para incluir un icono en tu aplicación, todo lo que necesitas es el conjunto de iconos y el nombre del icono. Navega por el sitio web del conjunto de iconos para el icono que deseas usar, y utiliza el nombre del icono como el parámetro del método `create()`. Además, puedes crear los iconos a través de enums para las clases `FeatherIcon` y `DwcIcon`, permitiendo que aparezcan en la completación de código.

```java
// Crear un icono a partir de un nombre String
Icon image = TablerIcon.create("image");
// Crear un icono a partir de un enum
Icon image = FeatherIcon.IMAGE.create();
```

### Variaciones {#variations}

Puedes personalizar aún más los iconos utilizando variaciones. Ciertos iconos te permiten elegir entre una versión contornada o llena, permitiéndote enfatizar un icono específico según tu preferencia. Los iconos `FontAwesomeIcon` y `Tabler` ofrecen variaciones.

#### Variaciones de `FontAwesomeIcon` {#fontawesomeicon-variations}

1. `REGULAR`: La variación contorneada de los iconos. Esta es la predeterminada.
2. `SOLID`: La variación llena de los iconos.
3. `BRAND`: La variación para cuando estás utilizando iconos de marcas.

#### Variaciones de `TablerIcon` {#tablericon-variations}

1. `OUTLINE`: La variación contorneada de los iconos. Esta es la predeterminada.
2. `FILLED`: La variación llena de los iconos.

```java
// Una variación llena de un icono de Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

La siguiente demostración ilustra cómo usar iconos de diferentes conjuntos, aplicar variaciones y integrarlos sin problemas en componentes.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Agregando iconos a componentes {#adding-icons-to-components}

Integra iconos en tus componentes utilizando espacios reservados. Los espacios reservados brindan opciones flexibles para hacer que los componentes sean más útiles. Es beneficioso agregar un `Icon` a un componente para aclarar aún más el significado previsto para los usuarios. Los componentes que implementan la interfaz `HasPrefixAndSuffix` pueden incluir un `Icon` u otros componentes válidos. Los componentes agregados pueden colocarse en los espacios `prefix` y `suffix` y pueden mejorar tanto el diseño general como la experiencia del usuario.

Utilizando los espacios `prefix` y `suffix`, puedes determinar si deseas que el icono esté antes o después del texto utilizando los métodos `setPrefixComponent()` y `setSuffixComponent()`.

Decidir si colocar un icono antes o después del texto en un componente depende en gran medida del propósito y del contexto del diseño.

### Colocación del icono: antes VS después {#icon-placement-before-vs-after}

Los iconos posicionados antes del texto del componente ayudan a los usuarios a comprender rápidamente la acción o propósito principal del componente, especialmente para iconos universalmente reconocidos como el icono de guardar. Los iconos antes del texto de un componente ofrecen un orden de procesamiento lógico, guiando a los usuarios de manera natural hacia la acción prevista, lo cual es beneficioso para botones cuya función principal es una acción inmediata.

Por otro lado, colocar iconos después del texto del componente es efectivo para acciones que brindan contexto o opciones adicionales, mejorando la claridad y las pistas para la navegación. Los iconos después del texto de un componente son ideales para componentes que ofrecen información complementaria o guían a los usuarios en un flujo direccional.

En última instancia, la consistencia es clave. Una vez que elijas un estilo, mantenlo en todo tu sitio para un diseño cohesivo y fácil de usar.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Creando conjuntos personalizados {#creating-custom-pools}

Más allá de utilizar colecciones de iconos existentes, tienes la opción de crear un conjunto personalizado que se puede usar para logotipos o avatares personalizados. Un conjunto personalizado de iconos puede almacenarse en un directorio centralizado o en la carpeta de recursos (contexto), simplificando el proceso de gestión de iconos. Tener un conjunto personalizado hace que la creación de aplicaciones sea más consistente y reduce el mantenimiento entre diferentes componentes y módulos.

Los conjuntos personalizados se pueden crear a partir de una carpeta que contenga imágenes SVG y utilizando la clase `IconPoolBuilder`. Desde allí, puedes elegir el nombre de tu conjunto personalizado y usar eso con los nombres de archivo SVG para crear componentes de icono personalizados.

```java
// Creando un conjunto personalizado llamado "app-pool" que tiene imágenes para un logotipo y un avatar.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Asegúrate de diseñar iconos con igual ancho y altura, ya que los componentes `Icon` están diseñados para ocupar un espacio cuadrado.
:::

### Fábrica de conjuntos personalizados {#custom-pool-factory}

También puedes crear una clase de fábrica para un conjunto personalizado en webforJ, al igual que `FeatherIcon`. Esto te permite crear y gestionar recursos de iconos dentro de un conjunto específico y permitir la completación de código. Cada icono puede ser instanciado a través del método `create()`, que devuelve un `Icon`. La clase de fábrica debe proporcionar metadatos específicos del conjunto, como el nombre del conjunto y el identificador del icono, formateado según el nombre del archivo de la imagen. Este diseño permite un acceso fácil y estandarizado a los activos de iconos desde el conjunto personalizado utilizando constantes de enum, apoyando la escalabilidad y la mantenibilidad en la gestión de iconos.

```java
/// Creando una fábrica de conjuntos personalizados para app-pool
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return el nombre del conjunto para los iconos
   */
  @Override
  public String getPool() {
    return "app-pool";
  }

  /**
   * @return el nombre del icono
   */
  @Override
  public String toString() {
    return this.name().toLowerCase(Locale.ENGLISH).replace('_', '-');
  }
}
```

El siguiente fragmento muestra las dos formas diferentes de usar un conjunto personalizado.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Crear un Icono usando los nombres del conjunto personalizado y el archivo de imagen
Icon customLogo = new Icon("logo", "app-pool");

// Crear un Icono usando la fábrica de conjuntos personalizada del fragmento anterior
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Botones de icono {#icon-buttons}
Un componente `Icon` no es seleccionable, pero para acciones que se representan mejor con solo un icono, como notificaciones o alertas, puedes usar `IconButton`.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("¡Tienes un nuevo mensaje!", "¡Ding Dong!")
});
```

## Mejores prácticas

- **Accesibilidad:** Usa un tooltip o una etiqueta en los iconos para hacer tu aplicación accesible a usuarios con discapacidades visuales que dependen de lectores de pantalla.
- **Evita la ambigüedad:** Evita usar iconos si el significado no es claro o ampliamente entendido. Si los usuarios tienen que adivinar lo que representa el icono, se pierde el propósito.
- **Usa iconos con moderación:** Demasiados iconos pueden abrumar a los usuarios, así que usa iconos solo cuando añadan claridad o reduzcan la complejidad.

## Estilizando
Un Icono hereda el tema de su componente padre directo, pero puedes anular esto aplicando un tema a un `Icon` directamente.

### Temas
Los componentes de icono vienen con siete temas discretos incorporados para un estilizado rápido sin la necesidad de CSS. Estos temas son estilos predefinidos que pueden aplicarse a los iconos para cambiar su apariencia y presentación visual. Ofrecen una forma rápida y consistente de personalizar el aspecto de los iconos a lo largo de una aplicación.

Si bien existen muchos casos de uso para cada uno de los distintos temas, algunos ejemplos de uso son:

- `DANGER`: Mejor para acciones con severas consecuencias, como borrar información rellenada o eliminar permanentemente una cuenta/datos.
- `DEFAULT`: Apropiado para acciones a lo largo de una aplicación que no requieren atención especial y son genéricas, como alternar un ajuste.
- `PRIMARY`: Apropiado como “llamada a la acción” principal en una página, como registrarse, guardar cambios o continuar a otra página.
- `SUCCESS`: Excelente para visualizar la finalización exitosa de un elemento en una aplicación, como la presentación de un formulario o la finalización de un proceso de registro. El tema de éxito puede aplicarse programáticamente una vez que se haya completado una acción exitosa.
- `WARNING`: Útil para indicar que un usuario está a punto de realizar una acción potencialmente arriesgada, como navegar fuera de una página con cambios no guardados. Estas acciones a menudo son menos impactantes que las que utilizarían el tema de peligro.
- `GRAY`: Bueno para acciones sutiles, como configuraciones menores o acciones que son más suplementarias a una página, y no forman parte de la funcionalidad principal.
- `INFO`: Bueno para proporcionar información aclaratoria adicional a un usuario.

<TableBuilder name={['Icon', 'IconButton']} />
