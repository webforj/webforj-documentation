---
title: Icon
sidebar_position: 55
_i18n_hash: 2da7d4e8288df67fc46f2a3ba84e12ee
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

El componente `Icon` de webforJ te permite incluir íconos sin esfuerzo en tu interfaz de usuario. 
Los íconos son una parte fundamental para mejorar el diseño de la interfaz de usuario, haciendo más rápido que los usuarios escaneen la pantalla en busca de elementos accionables. 
Usar íconos en tu aplicación crea señales visuales para la navegación y acciones, lo que puede reducir la cantidad de texto necesario y simplificar la interfaz de usuario. Puedes elegir entre tres grupos de íconos existentes y webforJ también te ofrece la opción de crear nuevos desde cero.

:::tip ¿Sabías que?

Algunos componentes, como `PasswordField` y `TimeField`, tienen íconos incorporados para ayudar a transmitir significado a los usuarios finales.

:::

## Básicos {#basics}

Cada `Icon` está diseñado como una imagen de gráficos vectoriales escalables (SVG), lo que significa que puede escalar fácilmente a cualquier tamaño sin perder claridad o calidad. 
Además, los componentes `Icon` se cargan a demanda desde una red de entrega de contenido (CDN), lo que ayuda a reducir la latencia y mejorar el rendimiento general.

Al crear un `Icon`, necesitarás identificar un grupo específico y el nombre del ícono. 
Algunos íconos también ofrecen la elección entre una versión contorneada o una llena a través de [variaciones](#variations).

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

### Grupos {#pools}

Un grupo de íconos es una colección de íconos comúnmente usados que permite un fácil acceso y reutilización. Al usar íconos de un grupo, puedes asegurarte de que los íconos en tu aplicación sean reconocibles y compartan un estilo consistente. 
Usar webforJ te permite elegir entre tres grupos, o implementar un grupo personalizado. 
Cada grupo tiene una extensa colección de íconos de código abierto que son gratuitos para usar. 
Usar webforJ te otorga la flexibilidad de elegir entre tres grupos y usarlos como clases únicas, sin la molestia de descargar directamente ninguno de los íconos.

| Grupo de Íconos                                         | Clase de webforJ |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` y `DwcIcon`.<br/>`DwcIcon` es un subconjunto de los íconos de Tabler.|    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Si estás interesado en crear tu propio grupo de íconos, consulta [Creando grupos personalizados](#creating-custom-pools).

:::

Una vez que hayas seleccionado el grupo o grupos para incluir en tu aplicación, el siguiente paso es especificar el nombre del ícono que deseas usar.

### Nombres {#names}

Para incluir un ícono en tu aplicación, todo lo que necesitas es el grupo de íconos y el nombre del ícono. Navega por el sitio web del grupo de íconos para encontrar el ícono que deseas usar y usa el nombre del ícono como el parámetro del método `create()`. 
Además, puedes crear los íconos a través de enums para las clases `FeatherIcon` y `DwcIcon`, lo que permite que aparezcan en la autocompletación del código.

```java
// Crear un ícono a partir de un nombre de cadena
Icon image = TablerIcon.create("image");
// Crear un ícono a partir de un enum
Icon image = FeatherIcon.IMAGE.create();
```

### Variaciones {#variations}

Puedes personalizar aún más los íconos utilizando variaciones. 
Ciertos íconos te permiten elegir entre una versión contorneada o una llena, permitiéndote enfatizar un ícono específico según tu preferencia. Los íconos `FontAwesomeIcon` y `Tabler` ofrecen variaciones.

#### Variaciones de `FontAwesomeIcon` {#fontawesomeicon-variations}

1. `REGULAR`: La variación contorneada de los íconos. Esta es la predeterminada.
2. `SOLID`: La variación llena de los íconos.
3. `BRAND`: La variación para cuando usas los íconos de las marcas.

#### Variaciones de `TablerIcon` {#tablericon-variations}

1. `OUTLINE`: La variación contorneada de los íconos. Esta es la predeterminada.
2. `FILLED`: La variación llena de los íconos.

```java
// Una variación llena de un ícono de Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

La siguiente demostración ilustra cómo usar íconos de diferentes grupos, aplicar variaciones y, de manera fluida, integrarlos en componentes.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Agregar íconos a componentes {#adding-icons-to-components}

Integra íconos en tus componentes utilizando slots. Los slots ofrecen opciones flexibles para hacer que los componentes sean más útiles. Es beneficioso agregar un `Icon` a un componente para aclarar aún más el significado destinado a los usuarios. 
Los componentes que implementan la interfaz `HasPrefixAndSuffix` pueden incluir un `Icon` u otros componentes válidos. Los componentes añadidos pueden colocarse en los slots `prefix` y `suffix` y pueden mejorar tanto el diseño general como la experiencia del usuario.

Usando los slots `prefix` y `suffix`, puedes determinar si deseas que el ícono esté antes o después del texto utilizando los métodos `setPrefixComponent()` y `setSuffixComponent()`.

Decidir si colocar un ícono antes o después del texto en un componente depende en gran medida del propósito y el contexto del diseño.

### Colocación de íconos: antes VS después {#icon-placement-before-vs-after}

Los íconos posicionados antes del texto del componente ayudan a los usuarios a comprender rápidamente la acción o propósito principal del componente, especialmente para íconos universalmente reconocibles como el ícono de guardar. 
Íconos antes del texto de un componente ofrecen un orden lógico de procesamiento, guiando a los usuarios de manera natural a través de la acción prevista, lo cual es beneficioso para botones cuya función principal es una acción inmediata.

Por otro lado, colocar íconos después del texto del componente es efectivo para acciones que proporcionan contexto o opciones adicionales, mejorando la claridad y las señales para la navegación. 
Íconos después del texto de un componente son ideales para componentes que ofrecen información suplementaria o guían a los usuarios en un flujo direccional.

En última instancia, la consistencia es clave. Una vez que elijas un estilo, mantenlo en toda tu página para un diseño cohesivo y amigable para el usuario.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Crear grupos personalizados {#creating-custom-pools}

Más allá de utilizar colecciones de íconos existentes, tienes la opción de crear un grupo personalizado que se puede usar para logotipos o avatares personalizados. 
Un grupo personalizado de íconos puede almacenarse en un directorio centralizado o en la carpeta de recursos (contexto), simplificando el proceso de gestión de íconos. 
Tener un grupo personalizado hace que la creación de la aplicación sea más consistente y reduce el mantenimiento entre diferentes componentes y módulos.

Los grupos personalizados se pueden crear a partir de una carpeta que contenga imágenes SVG y utilizando la clase `IconPoolBuilder`. Desde allí, puedes elegir el nombre de tu grupo personalizado y usarlo con los nombres de archivos SVG para crear componentes de íconos personalizados.

```java
// Creando un grupo personalizado llamado "app-pool" que tiene imágenes para un logotipo y un avatar.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Asegúrate de diseñar íconos con ancho y alto iguales, ya que los componentes `Icon` están diseñados para ocupar un espacio cuadrado.
:::

### Fábrica de grupos personalizados {#custom-pool-factory}

También puedes crear una clase de fábrica para un grupo personalizado en webforJ, al igual que `FeatherIcon`. Esto te permite crear y gestionar recursos de íconos dentro de un grupo específico y permitir la autocompletación del código. 
Cada ícono se puede instanciar a través del método `create()`, que devuelve un `Icon`. La clase de fábrica debe proporcionar metadatos específicos del grupo, como el nombre del grupo y el identificador del ícono, formateados de acuerdo con el nombre del archivo de la imagen. 
Este diseño permite un acceso fácil y estandarizado a los activos de íconos desde el grupo personalizado utilizando constantes de enum, apoyando la escalabilidad y el mantenimiento en la gestión de íconos.

```java
// Creando una fábrica de grupo personalizada para app-pool
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

El siguiente fragmento muestra las dos diferentes maneras de usar un grupo personalizado.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Crear un Icon usando los nombres del grupo personalizado y el archivo de imagen
Icon customLogo = new Icon("logo", "app-pool");

// Crear un Icon usando la fábrica del grupo personalizada del fragmento anterior
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Botones de íconos {#icon-buttons}
Un componente `Icon` no es seleccionable, pero para acciones que se representan mejor solo con un ícono, como notificaciones o alertas, puedes usar el `IconButton`.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("¡Tienes un nuevo mensaje!", "¡Ding Dong!")
  });
```

## Mejores prácticas

- **Accesibilidad:** Usa un tooltip o una etiqueta en los íconos para hacer tu aplicación accesible a usuarios con discapacidades visuales que dependen de lectores de pantalla.
- **Evitar ambigüedades:** Evita usar íconos si el significado no es claro o ampliamente entendido. Si los usuarios tienen que adivinar qué representa el ícono, se pierde el propósito.
- **Usa íconos con moderación:** Demasiados íconos pueden abrumar a los usuarios, así que usa íconos solo cuando aporten claridad o reduzcan la complejidad.

## Estilo
Un ícono hereda el tema de su componente padre directo, pero puedes anular esto aplicando un tema a un `Icon` directamente.

### Temas
Los componentes de íconos vienen con siete temas discretos incorporados para un rápido estilo sin el uso de CSS. Estos temas son estilos predefinidos que se pueden aplicar a los íconos para cambiar su apariencia y presentación visual. Ofrecen una forma rápida y consistente de personalizar la apariencia de los íconos en toda una aplicación.

Aunque hay muchos casos de uso para cada uno de los diversos temas, algunos ejemplos son:

- `DANGER`: Mejor para acciones con consecuencias severas, como borrar información completada o eliminar permanentemente una cuenta/datos.
- `DEFAULT`: Apropiado para acciones a lo largo de una aplicación que no requieren atención especial y son genéricas, como alternar una configuración.
- `PRIMARY`: Apropiado como una "llamada a la acción" principal en una página, como registrarse, guardar cambios o continuar a otra página.
- `SUCCESS`: Excelente para visualizar la finalización exitosa de un elemento en una aplicación, como la presentación de un formulario o la finalización de un proceso de registro. El tema de éxito puede aplicarse programáticamente una vez que se ha completado una acción exitosa.
- `WARNING`: Útil para indicar que un usuario está a punto de realizar una acción potencialmente arriesgada, como navegar fuera de una página con cambios no guardados. Estas acciones son a menudo menos impactantes que aquellas que usarían el tema de Peligro.
- `GRAY`: Bueno para acciones sutiles, como configuraciones menores o acciones que son más suplementarias a una página, y no parte de la funcionalidad principal.
- `INFO`: Bueno para proporcionar información clarificadora adicional a un usuario.

<TableBuilder name="Icon" />
