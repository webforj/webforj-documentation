---
title: Getting Started
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 81825a3ba8656a8aee4820dee71da732
---
<DocChip chip='since' label='26.02' />

craftforJ se envía con webforJ, por lo que no hay nada que descargar por separado. Esta página cubre lo que tu aplicación necesita antes de que aparezca craftforJ y cómo abrirlo.

:::tip Ya habilitado en proyectos generados
Los proyectos creados con [startforJ](https://docs.webforj.com/startforj) o a partir de un [arquetipo](/docs/building-ui/archetypes/overview) de webforJ vienen con craftforJ habilitado. Si comenzaste desde uno, ejecuta tu aplicación y salta a [Abrir craftforJ](#opening-craftforj).
:::

## Requisitos {#requirements}

craftforJ se adjunta a una aplicación solo cuando todas las siguientes condiciones son verdaderas. Si una de ellas no se cumple, nada aparece en la página.

### Agregar la dependencia {#add-the-dependency}

Agrega `webforj-devtools` a tu proyecto si aún no está allí:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Modo de depuración y la bandera craftforJ {#debug-mode-and-the-craftforj-flag}

Agrega las siguientes propiedades a tu proyecto. Si tienes una aplicación webforJ estándar, establece las propiedades en `webforj.conf`. Para un proyecto webforJ que utiliza [Spring](/docs/integrations/spring/overview), establece las propiedades en `application.properties`.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ solo funciona cuando ambas propiedades están habilitadas; por lo tanto, una aplicación que va a producción con el modo de depuración activado no expone tu árbol de fuente.

### Un navegador local y una licencia de desarrollador {#a-local-browser-and-a-developer-license}

Abre la aplicación desde la máquina que la ejecuta y asegúrate de tener una licencia de desarrollador válida. Para acceder a craftforJ desde otra máquina, agrega su dirección a [`hosts-allowed`](/docs/craftforj/configuration#access).

Una vez que estos elementos estén en su lugar, reinicia la aplicación y recarga la página.

## Abrir craftforJ {#opening-craftforj}

Cuando craftforJ está activo, un botón de activación aparece sobre tu aplicación. Haz clic en él para abrir craftforJ, o presiona <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> desde cualquier parte de la aplicación. El mismo atajo cierra craftforJ, y puedes arrastrar el botón a cualquier esquina que te convenga.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4" type="video/mp4" />
  </video>
</div>

Sus pestañas cubren el [árbol de componentes](/docs/craftforj/inspector), [rutas](/docs/craftforj/routes), el [tema](/docs/craftforj/theme) y el [asistente](/docs/craftforj/ai). La configuración y la información de la aplicación se encuentran a su lado.

- **El botón de activación** es el botón que abre y cierra craftforJ. Se mantiene fuera del camino mientras craftforJ está cerrado.
- **La franja de pestañas** corre a lo largo del borde más cercano a la aplicación y cambia entre lo que te muestra craftforJ.
- **El menú de la ventana** contiene todo lo relacionado con la ubicación de craftforJ, cubierto en [Dónde se encuentra craftforJ](#where-craftforj-sits).

:::info Atajos en macOS
craftforJ escribe cada atajo utilizando los modificadores de la plataforma en la que te encuentras, por lo que <kbd>Alt</kbd> aparece como <kbd>⌥</kbd> y <kbd>Ctrl</kbd> como <kbd>⌘</kbd>. Presiona <kbd>Shift</kbd> + <kbd>?</kbd> en craftforJ para ver la lista actual.
:::

## Dónde se encuentra craftforJ {#where-craftforj-sits}

craftforJ flota sobre tu aplicación por defecto. Arrástralo a cualquier lugar en la página, redimensiona desde cualquier borde y minimízalo nuevamente a su botón de activación cuando quieras que la aplicación sea solo tuya. Arrastrarlo hacia un borde de la página lo ancla allí, a toda altura o toda anchura, y cada borde mantiene el tamaño que le diste. Arrastrarlo lejos del borde lo vuelve a flotar.

:::info Anclando cubre la aplicación, no la reorganiza
craftforJ se dibuja sobre la página. Tu aplicación no se redimensiona y nada en ella se mueve fuera del camino, por lo que lo que esté debajo de craftforJ está oculto mientras esté allí. Para ver lo que hay debajo, mueve craftforJ a otro borde o sácalo de la página.
:::

![craftforJ anclado a la derecha de una página de aplicación, cubriendo ese borde de la aplicación](/img/craftforj/getting-started/docking.png#rounded-border)

Para dejar de cubrir la aplicación por completo, mueve craftforJ fuera de la página y a una ventana o pestaña del navegador propia, lo que será adecuado para un segundo monitor. Aún inspecciona tu aplicación a través de la página que lo abrió, así que deja esa página abierta. Navega o ciérrala y craftforJ no tendrá nada más que inspeccionar hasta que vuelvas a abrir la aplicación.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4" type="video/mp4" />
  </video>
</div>

Elige una pestaña en lugar de una ventana si usas la vista dividida de Chrome, que coloca tu aplicación y craftforJ uno al lado del otro y solo acepta pestañas reales. Haz clic derecho en la pestaña de tu aplicación, agrégala a una nueva vista dividida y luego selecciona la pestaña de craftforJ.

:::info La vista dividida es una función de Chrome
Chrome proporciona la disposición uno al lado del otro, no craftforJ. Otros navegadores no tienen equivalente, por lo que craftforJ en otros navegadores se abre en una pestaña normal a la que cambias. craftforJ funciona igual en ambos casos.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4" type="video/mp4" />
  </video>
</div>

:::tip Mover mientras el asistente está escribiendo
Mover craftforJ a otra ventana finaliza una respuesta que todavía está transmitiéndose. craftforJ pregunta primero, y todo lo escrito hasta ese punto permanece en el chat.
:::

## Haciendo un primer cambio {#making-a-first-change}

1. Presiona <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> para comenzar a seleccionar un componente.
2. Pasa el mouse sobre algo en tu aplicación y haz clic en ello.
3. El árbol selecciona ese componente y la barra lateral se llena con sus propiedades.
4. Cambia una propiedad. La aplicación en ejecución se actualiza inmediatamente.

El cambio afecta solo a la aplicación que tienes delante. Tus archivos permanecen intactos hasta que revises el cambio y lo apliques, lo que se cubre en [Escribir cambios en la fuente](/docs/craftforj/source-changes).

![craftforJ abierto junto a una aplicación en ejecución con un componente seleccionado](/img/craftforj/getting-started/first-open.png#rounded-border)

Si nada aparece en absoluto, consulta [Resolución de problemas](/docs/craftforj/troubleshooting).
