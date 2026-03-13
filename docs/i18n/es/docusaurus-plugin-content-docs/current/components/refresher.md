---
title: Refresher
sidebar_position: 101
_i18n_hash: 763037d616f2274feb7a7ed24b9c91f0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Pull-to-refresh es un patrón común en interfaces móviles y amigables al tacto, y el componente `Refresher` lo lleva a contenedores desplazables en webforJ. A medida que los usuarios deslizan hacia abajo más allá de un umbral configurable, transiciona entre estados visuales: `pull`, `release` y `refreshing`, cada uno con un ícono personalizable y texto localizado. Se complementa bien con [`InfiniteScroll`](../components/infinitescroll) para recargar o restablecer contenido mediante entrada basada en gestos.

<!-- INTRO_END -->

## Instalación e internacionalización {#instantiation-and-internationalization}

Agrega un `Refresher` instanciándolo y registrando un listener de actualización. Cuando las operaciones de actualización se completan, llama a `finish()` para restablecer el componente a su estado inactivo.

:::info Cómo activar el `Refresher`
Para activar el `Refresher`, **haz clic y arrastra hacia abajo** desde la parte superior del área desplazable. Aunque este gesto es familiar en móviles, es menos común en escritorio; asegúrate de mantener y tirar con tu mouse.
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

Este enfoque se usa comúnmente para actualizar listas paginadas o reiniciar la carga de desplazamiento infinito.

### Internacionalización {#internationalization}

Cada etiqueta de estado también se puede localizar utilizando el objeto `RefresherI18n`. Los tres estados son:

- Pull: Texto del gesto inicial (por ejemplo, "Desliza hacia abajo para actualizar")
- Release: Umbral de activación alcanzado (por ejemplo, "Suelta para actualizar")
- Refresh: Estado de carga (por ejemplo, "Actualizando")

Esto permite soporte multilingüe y ajustes de marca según sea necesario.

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Personalización de íconos {#icon-customization}

Puedes cambiar los [`Icons`](../components/icon) utilizados para los estados de `pull`/`release` y `refreshing` usando un [`Icon`](../components/icon) predefinido o una [URL de ícono](../managing-resources/assets-protocols). Estos son útiles cuando deseas aplicar la marca o una animación personalizada.

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Configuración del comportamiento de tirón {#pull-behavior-configuration}

### Umbral {#threshold}

Establece qué tan lejos debe tirar el usuario hacia abajo (en píxeles) antes de activar la actualización:

```java
refresher.setThreshold(80); // predeterminado: 80px
```

### Máximo umbral {#threshold-maximum}

Para definir la distancia máxima de tirón permitida, usa el método `setThresholdMax()`:

```java
refresher.setThresholdMax(160);
```

Estos umbrales controlan la sensibilidad del gesto y la curva de resistencia.

## Gestión de estados {#state-management}

El componente `Refresher` mantiene su propio estado interno y comunica los cambios de estado a través de eventos. Cuando un usuario tira hacia abajo más allá del umbral definido, el `Refresher` emite un evento de actualización al que puedes responder registrando un listener `onRefresh()`.

Dentro de este listener, se espera que realices la operación necesaria, como obtener nuevos datos o restablecer una lista, y luego llames explícitamente:

```java
refresher.finish();
```
:::warning Falta `finish()`
Si olvidas llamar a `finish()`, el refresher permanecerá en el estado de carga indefinidamente.
:::

También puedes desactivar programáticamente el `Refresher` en cualquier momento para evitar que el usuario active el comportamiento de actualización:

```java
refresher.setEnabled(false);
```

Esto es útil cuando las actualizaciones deberían estar temporalmente desactivadas; por ejemplo, durante una pantalla de carga o mientras otro proceso crítico se está ejecutando.

## Estilo {#styling}

### Temas {#themes}

El componente `Refresher` admite múltiples temas para distinguir visualmente diferentes estados o para coincidir con la apariencia de tu aplicación. Los temas se pueden aplicar utilizando el método `setTheme()`.

El siguiente ejemplo cicla a través de todos los temas disponibles cada vez que tiras para actualizar, brindándote una vista previa en vivo de cómo se ve el `Refresher` a través de diferentes temas:

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
