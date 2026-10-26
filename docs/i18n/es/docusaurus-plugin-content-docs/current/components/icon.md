---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: c526ee2878756d5dd13fa2972dfef56e
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

El componente `Icon` muestra íconos que se escalan a cualquier tamaño sin perder calidad. Puedes elegir entre tres grupos de íconos integrados o crear los tuyos propios. Los íconos sirven como indicativos visuales para la navegación y las acciones, reduciendo la necesidad de etiquetas de texto en tu interfaz.

Cada `Icon` se renderiza como una imagen de Gráficos Vectoriales Escalables (SVG), cargada a demanda desde una red de entrega de contenido (CDN) para mantener baja la latencia. Para crear uno, elige un grupo de íconos y el nombre de un ícono. Algunos íconos también ofrecen la opción entre una versión contorneada o una versión llena a través de [variaciones](#variations).

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip ¿Sabías que?
Al algunos componentes, como `PasswordField` y `TimeField`, tienen íconos integrados para ayudar a transmitir significado a los usuarios finales.
:::

## Grupos {#pools}

Un grupo de íconos es una colección de íconos comúnmente utilizados que permite un acceso y reutilización fáciles. Al usar íconos de un grupo, puedes asegurarte de que los íconos en tu aplicación sean reconocibles y compartan un estilo consistente. 
Usar webforJ te permite elegir entre tres grupos o implementar un grupo personalizado. 
Cada grupo tiene una extensa colección de íconos de código abierto que son gratuitos para usar. 
Usar webforJ te brinda la flexibilidad de elegir entre tres grupos y usarlos como clases únicas, sin la molestia de descargar ninguno de los íconos directamente.

| Grupo de íconos                                      | Clase de webforJ  |
| --------------------------------------------------- | ----------------- |
| [Tabler](https://tabler-icons.io/)                   | `TablerIcon` y `DwcIcon`.<br/>`DwcIcon` es un subconjunto de los íconos de Tabler. |
| [Feather](https://feathericons.com/)                 | `FeatherIcon`     |
| [Font Awesome](https://fontawesome.com/search)       | `FontAwesomeIcon` |

:::tip

Si estás interesado en crear tu propio grupo de íconos, consulta [Creando grupos personalizados](#creating-custom-pools).

:::

Una vez que hayas seleccionado el grupo o grupos para incluir en tu aplicación, el siguiente paso es especificar el nombre del ícono que deseas usar.

## Nombres {#names}

Para incluir un ícono en tu aplicación, solo necesitas el grupo de íconos y el nombre del ícono. Explora el sitio web del grupo de íconos para el ícono que deseas usar y utiliza el nombre del ícono como parámetro del método `create()`. 
Además, puedes crear los íconos a través de enums para las clases `FeatherIcon` y `DwcIcon`, permitiendo que aparezcan en la finalización de código.

```java
// Crear un ícono desde un nombre de String
Icon image = TablerIcon.create("image");
// Crear un ícono desde un enum
Icon image = FeatherIcon.IMAGE.create();
```

## Variaciones {#variations}

Puedes personalizar aún más los íconos utilizando variaciones. 
Ciertos íconos te permiten elegir entre una versión contorneada o una versión llena, permitiéndote enfatizar un ícono específico según tu preferencia. Los íconos `FontAwesomeIcon` y `Tabler` ofrecen variaciones.

### Variaciones de `FontAwesomeIcon` {#fontawesomeicon-variations}

1. `REGULAR`: La variación contorneada de los íconos. Esta es la predeterminada.
2. `SOLID`: La variación llena de los íconos.
3. `BRAND`: La variación para cuando estás usando los íconos de marcas.

### Variaciones de `TablerIcon` {#tablericon-variations}

1. `OUTLINE`: La variación contorneada de los íconos. Esta es la predeterminada.
2. `FILLED`: La variación llena de los íconos.

```java
// Una variación llena de un ícono de Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

La siguiente demostración ilustra cómo usar íconos de diferentes grupos, aplicar variaciones e integrarlos sin problemas en componentes.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Agregar íconos a componentes {#adding-icons-to-components}

Integra íconos en tus componentes usando slots. Los slots brindan opciones flexibles para hacer que los componentes sean más útiles. Es beneficioso agregar un `Icon` a un componente para aclarar aún más el significado destinado a los usuarios. 
Los componentes que implementan la interfaz `HasPrefixAndSuffix` pueden incluir un `Icon` u otros componentes válidos. Los componentes añadidos pueden ubicarse en los slots `prefix` y `suffix` y pueden mejorar tanto el diseño general como la experiencia del usuario.

Usando los slots `prefix` y `suffix`, puedes determinar si deseas que el ícono esté antes o después del texto utilizando los métodos `setPrefixComponent()` y `setSuffixComponent()`.

Decidir si colocar un ícono antes o después del texto en un componente depende en gran medida del propósito y el contexto de diseño.

### Colocación del ícono: antes VS después {#icon-placement-before-vs-after}

Los íconos colocados antes del texto del componente ayudan a los usuarios a comprender rápidamente la acción principal o el propósito del componente, especialmente para íconos universalmente reconocidos como el ícono de guardar. 
Los íconos antes del texto de un componente ofrecen un orden lógico de procesamiento, guiando a los usuarios de manera natural a través de la acción prevista, lo cual es beneficioso para los botones cuya función principal es una acción inmediata.

Por otro lado, colocar íconos después del texto del componente es efectivo para acciones que brindan contexto o opciones adicionales, mejorando la claridad y las indicaciones para la navegación. 
Los íconos después del texto de un componente son ideales para componentes que ofrecen información suplementaria o guían a los usuarios en un flujo direccional.

En última instancia, la consistencia es clave. Una vez que elijas un estilo, mantenlo en todo tu sitio para un diseño cohesivo y amigable para el usuario.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Creando grupos personalizados {#creating-custom-pools}

Además de utilizar colecciones de íconos existentes, tienes la opción de crear un grupo personalizado que puede ser utilizado para logotipos o avatares personalizados. 
Un grupo personalizado de íconos puede almacenarse en un directorio centralizado o en la carpeta de recursos (contexto), simplificando el proceso de gestión de íconos. 
Tener un grupo personalizado hace que la creación de aplicaciones sea más consistente y reduce el mantenimiento en diferentes componentes y módulos.

Los grupos personalizados se pueden crear a partir de una carpeta que contenga imágenes SVG y utilizando la clase `IconPoolBuilder`. Desde allí, puedes elegir el nombre de tu grupo personalizado y usar eso con los nombres de archivos SVG para crear componentes de íconos personalizados.

```java
// Creando un grupo personalizado llamado "app-pool" que tiene imágenes para un logotipo y un avatar.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Asegúrate de diseñar íconos con igual ancho y alto, ya que los componentes `Icon` están diseñados para ocupar un espacio cuadrado.
:::

### Fábrica de grupos personalizados {#custom-pool-factory}

También puedes crear una clase de fábrica para un grupo personalizado en webforJ, al igual que `FeatherIcon`. Esto te permite crear y gestionar recursos de íconos dentro de un grupo específico y permitir la finalización de código. 
Cada ícono puede ser instanciado a través del método `create()`, que devuelve un `Icon`. La clase de fábrica debe proporcionar metadatos específicos del grupo, como el nombre del grupo y el identificador del ícono, formateado al nombre del archivo de imagen. 
Este diseño permite un acceso fácil y estandarizado a los activos de íconos desde el grupo personalizado utilizando constantes de enum, apoyando la escalabilidad y mantenibilidad en la gestión de íconos.

```java
/// Creando una fábrica de grupos personalizados para app-pool
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return el nombre del grupo para los íconos
   */
  @Override
  public String getPool() {
    return "app-pool";
  }

  /**
   * @return el nombre del ícono
   */
  @Override
  public String toString() {
    return this.name().toLowerCase(Locale.ENGLISH).replace('_', '-');
  }
}
```

El siguiente fragmento muestra las dos formas diferentes de usar un grupo personalizado.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Crear un Icon usando los nombres del grupo personalizado y el archivo de imagen
Icon customLogo = new Icon("logo", "app-pool");

// Crear un Icon usando la fábrica de grupos personalizados del fragmento anterior
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Botones de ícono {#icon-buttons}
Un componente `Icon` no es seleccionable, pero para acciones que se representan mejor con solo un ícono, como notificaciones o alertas, puedes usar el `IconButton`.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("¡Tienes un nuevo mensaje!", "¡Ding Dong!")
});
```

## Mejores prácticas

- **Accesibilidad:** Utiliza un tooltip o una etiqueta en los íconos para hacer tu aplicación accesible a los usuarios con discapacidades visuales que dependen de lectores de pantalla.
- **Evitar ambigüedad:** Evita usar íconos si el significado no es claro o ampliamente comprendido. Si los usuarios tienen que adivinar lo que representa el ícono, se pierde el propósito.
- **Usar íconos con moderación:** Demasiados íconos pueden abrumar a los usuarios, así que solo usa íconos cuando añadan claridad o reduzcan la complejidad.

## Estilo
Un ícono hereda el tema de su componente padre directo, pero puedes sobrescribir esto aplicando un tema a un `Icon` directamente.

### Temas
Los componentes de íconos vienen con siete temas discretos integrados para un estilo rápido sin la necesidad de CSS. Estos temas son estilos predefinidos que se pueden aplicar a íconos para cambiar su apariencia y presentación visual. Ofrecen una forma rápida y consistente de personalizar la apariencia de los íconos en toda una aplicación.

Si bien hay muchos casos de uso para cada uno de los diversos temas, algunos ejemplos de uso son:

- `DANGER`: Mejor para acciones con consecuencias severas, como borrar información completada o eliminar permanentemente una cuenta/datos.
- `DEFAULT`: Apropiado para acciones en toda una aplicación que no requieren atención especial y son generales, como alternar una configuración.
- `PRIMARY`: Apropiado como una "llamada a la acción" principal en una página, como registrarse, guardar cambios o continuar a otra página.
- `SUCCESS`: Excelente para visualizar la finalización exitosa de un elemento en una aplicación, como la presentación de un formulario o la finalización de un proceso de registro. El tema de éxito puede aplicarse programáticamente una vez que se haya completado una acción exitosa.
- `WARNING`: Útil para indicar que un usuario está a punto de realizar una acción potencialmente arriesgada, como navegar fuera de una página con cambios no guardados. Estas acciones suelen ser menos impactantes que aquellas que utilizarían el tema de peligro.
- `GRAY`: Bueno para acciones sutiles, como configuraciones menores o acciones que son más suplementarias a una página, y no forman parte de la funcionalidad principal.
- `INFO`: Bueno para proporcionar información aclaratoria adicional a un usuario.

<TableBuilder name={['Icon', 'IconButton']} />
