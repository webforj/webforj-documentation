---
title: Comenzando
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 5d30f5dd80df008a05be5d4617c40369
---
<!-- vale off -->
# Primeros pasos <DocChip chip='since' label='26.02' />
<!-- vale on -->

craftforJ se incluye con webforJ, así que no tienes que descargar nada por separado. En esta página se explica qué necesita tu app para que aparezca craftforJ y cómo abrirlo.

:::tip Ya está habilitado en los proyectos generados
Los proyectos creados con [startforJ](https://docs.webforj.com/startforj) o a partir de un [arquetipo](/docs/building-ui/archetypes/overview) de webforJ ya incluyen craftforJ habilitado. Si empezaste con uno de ellos, ejecuta tu app y ve directamente a [Abrir craftforJ](#opening-craftforj).
:::

## Requisitos {#requirements}

craftforJ se conecta a una app solo cuando se cumplen todas las condiciones siguientes. Si no se cumple alguna, no aparecerá nada en la página.

### Añadir la dependencia {#add-the-dependency}

Añade `webforj-devtools` a tu proyecto, si aún no está:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Modo de depuración y la marca de craftforJ {#debug-mode-and-the-craftforj-flag}

Añade las propiedades siguientes al proyecto. Si tienes una app webforJ estándar, configura las propiedades en `webforj.conf`. Para un proyecto webforJ que use [Spring](/docs/integrations/spring/overview), configúralas en `application.properties`.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ solo funciona si ambas propiedades están habilitadas; por lo tanto, una app que llegue a producción con el modo de depuración activado no expondrá el árbol de código fuente.

### Un navegador local y una licencia de desarrollador {#a-local-browser-and-a-developer-license}

Abre la app desde la máquina donde se ejecuta y asegúrate de tener una licencia de desarrollador válida. Para acceder a craftforJ desde otra máquina, añade su dirección a [`hosts-allowed`](/docs/craftforj/configuration#access).

Una vez cumplidos estos requisitos, reinicia la app y vuelve a cargar la página.

## Abrir craftforJ {#opening-craftforj}

Cuando craftforJ está activo, aparece un botón de activación sobre la app. Haz clic en él para abrir craftforJ o pulsa <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> desde cualquier lugar de la app. El mismo atajo vuelve a cerrar craftforJ y puedes arrastrar el botón de activación a la esquina que prefieras.

<VideoPlayer video="craftforj/getting-started" width={1920} height={984} title="Getting started" />

Sus pestañas incluyen el [árbol de componentes](/docs/craftforj/inspector), las [rutas](/docs/craftforj/routes), el [tema](/docs/craftforj/theme) y el [asistente](/docs/craftforj/ai). También encontrarás la configuración y la información de la app.


- **El botón de activación** abre y cierra craftforJ. No estorba cuando craftforJ está cerrado.
- **La barra de pestañas** se extiende por el borde más cercano a la app y permite cambiar lo que muestra craftforJ.
- **El menú de la ventana** contiene todas las opciones sobre la posición de craftforJ, descritas en [Dónde se ubica craftforJ](#where-craftforj-sits).

:::info Atajos en macOS
craftforJ muestra los atajos con los modificadores de la plataforma que estés usando, así que <kbd>Alt</kbd> aparece como <kbd>⌥</kbd> y <kbd>Ctrl</kbd> como <kbd>⌘</kbd>. Pulsa <kbd>Shift</kbd> + <kbd>?</kbd> en craftforJ para ver la lista actual.
:::

## Dónde se ubica craftforJ {#where-craftforj-sits}

De forma predeterminada, craftforJ flota sobre la app. Arrástralo a cualquier lugar de la página, cambia su tamaño desde cualquiera de los bordes y minimízalo en su botón de activación cuando quieras usar la app sin él. Al arrastrarlo hasta un borde de la página, se acopla allí y ocupa todo el alto o el ancho; cada borde conserva el tamaño que le hayas dado. Si lo alejas del borde, volverá a flotar.

:::info El acoplamiento cubre la app, no reajusta su diseño
craftforJ se dibuja sobre la página. La app no cambia de tamaño ni mueve nada para dejarle espacio, así que craftforJ oculta lo que quede debajo. Para ver lo que hay detrás, mueve craftforJ a otro borde o sácalo de la página.
:::

![craftforJ acoplado a la derecha de una página de app, cubriendo ese borde](/img/craftforj/getting-started/docking.png#rounded-border)

Para dejar de cubrir la app, mueve craftforJ fuera de la página y ábrelo en una ventana o pestaña del navegador independiente, una opción práctica para usarlo en un segundo monitor. Seguirá inspeccionando la app a través de la página que lo abrió, así que déjala abierta. Si navegas a otra página o la cierras, craftforJ ya no tendrá nada que inspeccionar hasta que vuelvas a abrir la app.

<VideoPlayer video="craftforj/undock-window" width={1920} height={1048} title="Undock window" />

Elige una pestaña en lugar de una ventana si usas la vista dividida de Chrome, que coloca la app y craftforJ una al lado de la otra y solo admite pestañas reales. Haz clic con el botón derecho en la pestaña de la app, añádela a una nueva vista dividida y, a continuación, selecciona la pestaña de craftforJ.

:::info La vista dividida es una función de Chrome
Chrome proporciona la disposición en paralelo, no craftforJ. Otros navegadores no tienen una función equivalente, así que en ellos craftforJ se abre en una pestaña normal a la que tendrás que cambiar. craftforJ funciona igual en ambos casos.
:::

<VideoPlayer video="craftforj/split-view" width={1920} height={1080} title="Split view" />

:::tip Mover craftforJ mientras el asistente está escribiendo
Mover craftforJ a otra ventana interrumpe una respuesta que aún se está generando. craftforJ te lo pregunta antes y todo lo que se haya escrito hasta ese momento permanece en el chat.
:::

## Hacer un primer cambio {#making-a-first-change}

1. Pulsa <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> para empezar a seleccionar un componente.
2. Pasa el cursor sobre un elemento de la app y haz clic en él.
3. El árbol selecciona ese componente y la barra lateral muestra sus propiedades.
4. Cambia una propiedad. La app en ejecución se actualiza de inmediato.

El cambio solo afecta a la app que tienes delante. Los archivos permanecen intactos hasta que revises y apliques el cambio, como se explica en [Escribir cambios en el código fuente](/docs/craftforj/source-changes).

![craftforJ abierto junto a una app en ejecución con un componente seleccionado](/img/craftforj/getting-started/first-open.png#rounded-border)

Si no aparece nada, sigue los pasos de [Solución de problemas](/docs/craftforj/troubleshooting).
