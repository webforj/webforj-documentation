---
title: 'Webswing con webforJ: moderniza una aplicación Swing paso a paso'
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Ejecuta una aplicación Swing, JavaFX o SWT en el navegador con Webswing,
  intégrala en una aplicación webforJ y reemplázala por pantallas web, una a
  una.
_i18n_title_hash: 698c1a9e98e2ef808c204b0bc4be4669
_i18n_description_hash: 3bd3bdba7583bc1699e8d6153577eb56
_i18n_hash: 53117d208e8a0b0a2e46705f9edd1cdc
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing ejecuta una aplicación Swing, JavaFX o SWT en el navegador sin modificar el código fuente. Con webforJ, puedes insertar esa aplicación en una aplicación web y reemplazarla pantalla por pantalla. Para que ambas intercambien mensajes, debes añadir la API de Webswing a la aplicación Swing.

Webswing es una tecnología de servidor web. Renderiza la aplicación de escritorio en el servidor y transmite la interfaz al navegador mediante un lienzo HTML5, gestionando de forma transparente todas las interacciones del usuario.

Para ver un resumen de todo el proceso, desde ejecutar la aplicación de escritorio en el navegador hasta retirarla, consulta [Modernización de Swing](/modernization).

## Qué resuelve Webswing {#what-webswing-solves}

Muchas organizaciones han invertido considerablemente en aplicaciones de escritorio Java que contienen lógica empresarial crítica, desarrollada durante años o décadas. A menudo, estas aplicaciones no se pueden reescribir fácilmente debido a lo siguiente:

- Lógica de dominio compleja cuya recreación sería arriesgada
- Integración con bibliotecas o hardware específicos para equipos de escritorio
- Limitaciones de tiempo y costo para una reescritura completa
- Necesidad de mantener la paridad de funciones con la aplicación existente

Webswing permite que estas aplicaciones estén disponibles en la web sin modificarlas. Conservan sus funciones y apariencia originales.

## Integración con webforJ {#integration-with-webforj}

La integración de Webswing con webforJ proporciona el componente `WebswingConnector`, que permite insertar aplicaciones alojadas en Webswing directamente en tu aplicación webforJ. Esto abre posibilidades para:

### Modernización progresiva {#progressive-modernization}

En lugar de reescribir todo de una sola vez, puedes:

1. Empezar insertando tu aplicación Swing existente mediante `WebswingConnector`
2. Desarrollar nuevas funciones en webforJ alrededor de la aplicación insertada
3. Reemplazar gradualmente los componentes Swing por equivalentes de webforJ
4. Finalmente, retirar por completo la aplicación heredada

### Aplicaciones híbridas {#hybrid-applications}

Combina una interfaz de usuario web moderna creada con webforJ con funciones especializadas de escritorio:

- Usa webforJ para interfaces orientadas al usuario, paneles y reportes
- Conserva Swing para visualizaciones complejas o editores especializados
- Mantén una experiencia de aplicación integrada única

:::tip Reemplazar equivalentes de Swing
Consulta [Transición desde Swing](/docs/introduction/coming-from-swing) para comparar los componentes Swing con sus equivalentes de webforJ. Las comparaciones incluyen referencias pertinentes que te ayudarán a usar las funciones de cada equivalente de webforJ.
:::

## Cómo funciona {#how-it-works}

La integración funciona a través de tres capas:

1. **Servidor Webswing**: ejecuta tu aplicación de escritorio Java, captura su salida visual y procesa la entrada del usuario
2. **Componente WebswingConnector**: un componente webforJ que inserta el cliente de Webswing y gestiona la conexión y la comunicación con el servidor
3. **Protocolo de comunicación**: mensajería bidireccional que permite a tu aplicación webforJ enviar comandos a la aplicación Swing y recibir eventos

Cuando un usuario accede a tu aplicación webforJ, `WebswingConnector` establece una conexión con el servidor Webswing. El servidor crea una instancia de la aplicación o se vuelve a conectar a una, y comienza a transmitir su estado visual al navegador. Las interacciones del usuario (ratón y teclado) se capturan y envían al servidor, donde se reproducen en la aplicación Swing real.

## Temas {#topics}

<DocCardList className="topics-section" />
