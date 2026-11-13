---
title: Comenzando
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 6cb150ca0da866a142787024af9e2bd1
---
<!-- vale off -->
# Primeros pasos <DocChip chip='since' label='26.02' />
<!-- vale on -->

craftforJ se incluye con webforJ, así que no hace falta descargar nada por separado. En esta página se explica qué necesita tu aplicación para que aparezca craftforJ y cómo abrirlo.

:::tip Ya está habilitado en los proyectos generados
Los proyectos creados con [startforJ](https://docs.webforj.com/startforj) o a partir de un [arquetipo](/docs/building-ui/archetypes/overview) de webforJ incluyen craftforJ habilitado. Si empezaste con uno de ellos, ejecuta tu aplicación y ve directamente a [Abrir craftforJ](#opening-craftforj).
:::

## Requisitos {#requirements}

craftforJ se conecta a una aplicación solo cuando se cumplen todas las condiciones siguientes. Si alguna no se cumple, no aparecerá nada en la página.

### Añadir la dependencia {#add-the-dependency}

Añade `webforj-devtools` a tu proyecto si aún no está incluido:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Modo de depuración y la marca de craftforJ {#debug-mode-and-the-craftforj-flag}

Añade las siguientes propiedades a tu proyecto. Si tienes una aplicación webforJ estándar, define las propiedades en `webforj.conf`. Para un proyecto webforJ que use [Spring](/docs/integrations/spring/overview), define las propiedades en `application.properties`.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ solo funciona cuando ambas propiedades están habilitadas; por lo tanto, aunque una aplicación llegue a producción con el modo de depuración activado, no expondrá el árbol de código fuente.

### Un navegador local y una licencia de desarrollador {#a-local-browser-and-a-developer-license}

Abre la aplicación desde la máquina donde se ejecuta y asegúrate de tener una licencia de desarrollador válida. Para acceder a craftforJ desde otra máquina, añade su dirección a [`hosts-allowed`](/docs/craftforj/configuration#access).

Una vez que hayas configurado estos elementos, reinicia la aplicación y vuelve a cargar la página.

## Abrir craftforJ {#opening-craftforj}

Cuando craftforJ está activo, aparece un botón de activación sobre tu aplicación. Haz clic en él para abrir craftforJ o pulsa <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> desde cualquier lugar de la aplicación. El mismo atajo vuelve a cerrar craftforJ, y puedes arrastrar el botón de activación a la esquina que prefieras.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Sus pestañas incluyen el [árbol de componentes](/docs/craftforj/inspector), las [rutas](/docs/craftforj/routes), el [tema](/docs/craftforj/theme) y el [asistente](/docs/craftforj/ai). Junto a ellas están los ajustes y la información de la aplicación.


- **El botón de activación** abre y cierra craftforJ. No estorba cuando craftforJ está cerrado.
- **La barra de pestañas** se encuentra en el borde más cercano a la aplicación y permite cambiar lo que craftforJ muestra.
- **El menú de la ventana** contiene todas las opciones de ubicación de craftforJ, que se describen en [Dónde se ubica craftforJ](#where-craftforj-sits).

:::info Atajos en macOS
craftforJ muestra cada atajo con las teclas modificadoras de la plataforma que estás usando, por lo que <kbd>Alt</kbd> aparece como <kbd>⌥</kbd> y <kbd>Ctrl</kbd> como <kbd>⌘</kbd>. Pulsa <kbd>Shift</kbd> + <kbd>?</kbd> en craftforJ para ver la lista actual.
:::

## Dónde se ubica craftforJ {#where-craftforj-sits}

De forma predeterminada, craftforJ flota sobre tu aplicación. Arrástralo a cualquier parte de la página, cambia su tamaño desde cualquiera de los bordes y minimízalo para volver al botón de activación cuando quieras usar la aplicación sin él. Si lo arrastras hasta un borde de la página, se acopla allí a toda la altura o a todo el ancho, y cada borde conserva el tamaño que le hayas dado. Al alejarlo del borde, vuelve a flotar.

:::info El acoplamiento cubre la aplicación, no modifica su distribución
craftforJ se muestra sobre la página. La aplicación no cambia de tamaño ni sus elementos se apartan, así que todo lo que quede debajo de craftforJ estará oculto mientras esté allí. Para ver lo que hay debajo, mueve craftforJ a otro borde o sácalo de la página.
:::

![craftforJ acoplado a la derecha de la página de una aplicación, cubriendo ese borde](/img/craftforj/getting-started/docking.png#rounded-border)

Para dejar de cubrir la aplicación por completo, saca craftforJ de la página y ábrelo en una ventana o pestaña independiente del navegador, lo que resulta práctico si tienes un segundo monitor. Seguirá inspeccionando tu aplicación a través de la página que lo abrió, así que déjala abierta. Si navegas a otra página o la cierras, craftforJ ya no tendrá nada que inspeccionar hasta que vuelvas a abrir la aplicación.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Elige una pestaña en vez de una ventana si usas la vista dividida de Chrome, que coloca la aplicación y craftforJ una al lado de la otra y solo acepta pestañas reales. Haz clic con el botón derecho en la pestaña de tu aplicación, añádela a una nueva vista dividida y, después, selecciona la pestaña de craftforJ.

:::info La vista dividida es una función de Chrome
Chrome ofrece la disposición en paralelo, no craftforJ. Otros navegadores no tienen una función equivalente, así que craftforJ se abre en una pestaña normal que tendrás que seleccionar. craftforJ funciona igual en ambos casos.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::tip Mover craftforJ mientras el asistente está escribiendo
Al mover craftforJ a otra ventana, se interrumpe cualquier respuesta que se esté transmitiendo. craftforJ te lo pregunta primero, y todo lo escrito hasta ese momento permanece en el chat.
:::

## Hacer un primer cambio {#making-a-first-change}

1. Pulsa <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> para empezar a seleccionar un componente.
2. Pasa el cursor sobre un elemento de la aplicación y haz clic en él.
3. El árbol selecciona ese componente y la barra lateral muestra sus propiedades.
4. Cambia una propiedad. La aplicación en ejecución se actualiza de inmediato.

El cambio solo afecta a la aplicación que tienes delante. Los archivos no se modifican hasta que revises y apliques el cambio, como se explica en [Escribir cambios en el código fuente](/docs/craftforj/source-changes).

![craftforJ abierto junto a una aplicación en ejecución con un componente seleccionado](/img/craftforj/getting-started/first-open.png#rounded-border)

Si no aparece nada, sigue los pasos de [Solución de problemas](/docs/craftforj/troubleshooting).
