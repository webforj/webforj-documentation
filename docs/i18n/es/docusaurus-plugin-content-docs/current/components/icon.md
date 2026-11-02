---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: f1572fcb6d1396a20fac32bfa282c091
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

El componente `Icon` muestra íconos que escalan a cualquier tamaño sin perder calidad. Puedes elegir entre tres grupos de íconos integrados o crear los tuyos personalizados. Los íconos sirven como señales visuales para la navegación y las acciones, reduciendo la necesidad de etiquetas de texto en tu interfaz.

Cada `Icon` se renderiza como una imagen de Gráficos Vectoriales Escalables (SVG), cargada bajo demanda desde una red de entrega de contenido (CDN) para mantener baja la latencia. Para crear uno, elige un grupo de íconos y un nombre de ícono. Algunos íconos también ofrecen la opción de una versión contorneada o rellena a través de [variaciones](#variations).

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip ¿Sabías que?
Algunos componentes, como `PasswordField` y `TimeField`, tienen íconos integrados para ayudar a transmitir significado a los usuarios finales.
:::

## Grupos {#pools}

Un grupo de íconos es una colección de íconos comúnmente utilizados que permite un fácil acceso y reutilización. Al usar íconos de un grupo de íconos, puedes asegurarte de que los íconos en tu aplicación sean reconocibles y compartan un estilo consistente. Usar webforJ te permite elegir entre tres grupos o implementar un grupo personalizado. Cada grupo tiene una extensa colección de íconos de código abierto que son gratuitos para usar. Usar webforJ te brinda la flexibilidad de elegir entre tres grupos y usarlos como clases únicas, sin la molestia de descargar ninguno de los íconos directamente.

| Grupo de Íconos                                     | Clase webforJ |
| --------------------------------------------------- | ------------- |
| [Tabler](https://tabler-icons.io/)                  | `TablerIcon` y `DwcIcon`.<br/>`DwcIcon` es un subconjunto de los íconos de Tabler.|
| [Feather](https://feathericons.com/)                | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)      | `FontAwesomeIcon`   |

:::tip

Si estás interesado en crear tu propio grupo de íconos, consulta [Creando grupos personalizados](#creating-custom-pools).

:::

Una vez que hayas seleccionado el grupo o grupos para incluir en tu aplicación, el siguiente paso es especificar el nombre del ícono que deseas usar.

## Nombres {#names}

Para incluir un ícono en tu aplicación, todo lo que necesitas es el grupo de íconos y el nombre del ícono. Navega por el sitio web del grupo de íconos para encontrar el ícono que deseas usar, y utiliza el nombre del ícono como parámetro del método `create()`. Adicionalmente, puedes crear los íconos a través de enums para las clases `FeatherIcon` y `DwcIcon`, lo que les permite aparecer en la autocompletar de código.

```java
// Crear un ícono a partir de un nombre de String
Icon image = TablerIcon.create("image");
// Crear un ícono a partir de un enum
Icon image = FeatherIcon.IMAGE.create();
```

## Variaciones {#variations}

Puedes personalizar los íconos aún más utilizando variaciones. Ciertos íconos te permiten elegir entre una versión contorneada o rellena, permitiéndote enfatizar un ícono específico según tu preferencia. Los íconos de `FontAwesomeIcon` y `Tabler` ofrecen variaciones.

### Variaciones de `FontAwesomeIcon` {#fontawesomeicon-variations}

1. `REGULAR`: La variación contorneada de los íconos. Esta es la predeterminada.
2. `SOLID`: La variación rellena de los íconos.
3. `BRAND`: La variación para cuando estás usando íconos de marcas.

### Variaciones de `TablerIcon` {#tablericon-variations}

1. `OUTLINE`: La variación contorneada de los íconos. Esta es la predeterminada.
2. `FILLED`: La variación rellena de los íconos.

```java
// Una variación rellena de un ícono de Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

El siguiente demo ilustra cómo usar íconos de diferentes grupos, aplicar variaciones e integrarlos sin problemas en componentes.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Agregar íconos a componentes {#adding-icons-to-components}

Integra íconos en tus componentes utilizando slots. Los slots proporcionan opciones flexibles para hacer que los componentes sean más útiles. Es beneficioso agregar un `Icon` a un componente para aclarar aún más el significado previsto a los usuarios. Los componentes que implementan la interfaz `HasPrefixAndSuffix` pueden incluir un `Icon` u otros componentes válidos. Los componentes añadidos pueden colocarse en los slots `prefix` y `suffix` y pueden mejorar tanto el diseño general como la experiencia del usuario.

Usando los slots `prefix` y `suffix`, puedes determinar si deseas el ícono antes o después del texto utilizando los métodos `setPrefixComponent()` y `setSuffixComponent()`.

Decidir si colocar un ícono antes o después del texto en un componente depende en gran medida del propósito y el contexto del diseño.

### Colocación de íconos: antes VS después {#icon-placement-before-vs-after}

Los íconos posicionados antes del texto del componente ayudan a los usuarios a comprender rápidamente la acción principal o el propósito del componente, especialmente para íconos universalmente reconocidos como el ícono de guardar. Los íconos antes del texto de un componente ofrecen un orden lógico de procesamiento, guiando a los usuarios de manera natural a través de la acción destinada, lo cual es beneficioso para los botones cuya función principal es una acción inmediata.

Por el contrario, colocar íconos después del texto del componente es efectivo para acciones que brindan contexto adicional u opciones, mejorando la claridad y las señales para la navegación. Los íconos después del texto de un componente son ideales para componentes que ofrecen información suplementaria o guían a los usuarios en un flujo direccional.

En última instancia, la consistencia es clave. Una vez que elijas un estilo, manténlo a lo largo de tu sitio para un diseño cohesivo y fácil de usar.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Creando grupos personalizados {#creating-custom-pools}

Más allá de utilizar colecciones de íconos existentes, tienes la opción de crear un grupo personalizado que puede ser usado para logotipos o avatares personalizados. Un grupo personalizado de íconos se puede almacenar en un directorio centralizado o en la carpeta de recursos (contexto), simplificando el proceso de gestión de íconos. Tener un grupo personalizado hace que la creación de aplicaciones sea más consistente y reduce el mantenimiento a través de diferentes componentes y módulos.

Los grupos personalizados se pueden crear a partir de una carpeta que contenga imágenes SVG y utilizando la clase `IconPoolBuilder`. A partir de ahí, puedes elegir el nombre de tu grupo personalizado y usarlo con los nombres de archivo SVG para crear componentes de íconos personalizados.

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

También puedes crear una clase de fábrica para un grupo personalizado en webforJ, al igual que `FeatherIcon`. Esto te permite crear y gestionar recursos de íconos dentro de un grupo especificado y permitir la autocompletar de código. Cada ícono puede ser instanciado a través del método `create()`, que devuelve un `Icon`. La clase de fábrica debe proporcionar metadatos específicos del grupo, como el nombre del grupo y el identificador del ícono, formateados al nombre del archivo de la imagen. Este diseño permite un fácil acceso estandarizado a los activos de íconos desde el grupo personalizado utilizando constantes de enum, apoyando la escalabilidad y el mantenimiento en la gestión de íconos.

```java
/// Creando una fábrica de grupos personalizada para app-pool
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

El siguiente fragmento muestra las dos maneras diferentes de usar un grupo personalizado.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Crear un Icon utilizando los nombres del grupo personalizado y el archivo de imagen
Icon customLogo = new Icon("logo", "app-pool");

// Crear un Icon utilizando la fábrica de grupos personalizada del fragmento anterior
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Botones de íconos <DocChip chip="name" label="dwc-icon-button" /> {#icon-buttons}
Un componente `Icon` no es seleccionable, pero para acciones que se representan mejor con solo un ícono, como notificaciones o alertas, puedes usar el `IconButton`.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("¡Tienes un nuevo mensaje!", "¡Ding Dong!")
});
```

## Mejores prácticas

- **Accesibilidad:** Utiliza un tooltip o una etiqueta en los íconos para hacer que tu aplicación sea accesible para usuarios con discapacidades visuales que dependen de lectores de pantalla.
- **Evitar ambigüedades:** Evita usar íconos si el significado no está claro o ampliamente comprendido. Si los usuarios tienen que adivinar lo que representa el ícono, se pierde el propósito.
- **Usar íconos con moderación:** Demasiados íconos pueden abrumar a los usuarios, así que usa íconos solo cuando agreguen claridad o reduzcan la complejidad.

## Estilizando
Un ícono hereda el tema de su componente padre directo, pero puedes sobrescribir esto aplicando un tema a un `Icon` directamente.

### Temas
Los componentes de íconos vienen con siete temas discretos integrados para un estilo rápido sin la necesidad de CSS. Estos temas son estilos predefinidos que se pueden aplicar a los íconos para cambiar su apariencia y presentación visual. Ofrecen una manera rápida y consistente de personalizar la apariencia de los íconos en toda una aplicación.

Si bien hay muchos casos de uso para cada uno de los diversos temas, algunos ejemplos de uso son:

- `DANGER`: Mejor para acciones con consecuencias severas, como borrar información completada o eliminar permanentemente una cuenta/datos.
- `DEFAULT`: Apropiado para acciones en toda una aplicación que no requieren atención especial y son genéricas, como alternar un ajuste.
- `PRIMARY`: Apropiado como una "llamada a la acción" principal en una página, como registrarse, guardar cambios o continuar a otra página.
- `SUCCESS`: Excelente para visualizar la finalización exitosa de un elemento en una aplicación, como la presentación de un formulario o la finalización de un proceso de registro. El tema de éxito puede aplicarse programáticamente una vez que se haya completado una acción exitosa.
- `WARNING`: Útil para indicar que un usuario está a punto de realizar una acción potencialmente arriesgada, como navegar lejos de una página con cambios no guardados. Estas acciones suelen ser menos impactantes que las que utilizarían el tema de Peligro.
- `GRAY`: Bueno para acciones sutiles, como configuraciones menores o acciones que son más suplementarias a una página y no parte de la funcionalidad principal.
- `INFO`: Bueno para proporcionar información adicional y aclaratoria a un usuario.

<TableBuilder name={['Icon', 'IconButton']} />
