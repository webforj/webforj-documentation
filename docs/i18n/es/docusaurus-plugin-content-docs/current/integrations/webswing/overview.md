---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Embed legacy Swing, JavaFX, or SWT desktop apps inside a webforJ UI through
  Webswing for progressive modernization and hybrid experiences.
_i18n_hash: 300c45aa8cc9f51831219f75d74d31b6
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing es una tecnología de servidor web que permite ejecutar aplicaciones de escritorio Java (Swing, JavaFX, SWT) en un navegador web sin modificar el código fuente original. Renderiza la aplicación de escritorio en el servidor y transmite la interfaz al navegador mediante un canvas HTML5, gestionando de forma transparente todas las interacciones del usuario.

## Qué resuelve Webswing {#what-webswing-solves}

Muchas organizaciones han invertido considerablemente en aplicaciones de escritorio Java que contienen lógica empresarial crítica desarrollada a lo largo de años o décadas. A menudo, estas aplicaciones no se pueden reescribir fácilmente debido a lo siguiente:

- Lógica de dominio compleja que sería arriesgado recrear
- Integración con bibliotecas o hardware específicos para escritorio
- Limitaciones de tiempo y costo de una reescritura completa
- Necesidad de mantener la paridad de funcionalidades con las características existentes

Webswing permite que se pueda acceder a estas aplicaciones desde la web sin modificarlas y conserva su funcionalidad y apariencia originales.

## Integración con webforJ {#integration-with-webforj}

La integración de Webswing con webforJ proporciona el componente `WebswingConnector`, que permite insertar aplicaciones alojadas en Webswing directamente en la aplicación webforJ. Esto ofrece oportunidades para:

### Modernización progresiva {#progressive-modernization}

En lugar de realizar una reescritura total, puedes:

1. Empezar insertando tu aplicación Swing existente mediante `WebswingConnector`
2. Desarrollar nuevas funcionalidades en webforJ alrededor de la aplicación insertada
3. Reemplazar gradualmente los componentes Swing por sus equivalentes de webforJ
4. Finalmente, retirar por completo la aplicación heredada

### Aplicaciones híbridas {#hybrid-applications}

Combina una interfaz de usuario web moderna creada con webforJ con funcionalidades de escritorio especializadas:

- Usa webforJ para las interfaces de usuario, los paneles y los informes
- Aprovecha Swing para visualizaciones complejas o editores especializados
- Mantén una experiencia integrada en una sola aplicación

:::tip Sustitución de equivalentes de Swing
Consulta [Coming from Swing](/docs/introduction/coming-from-swing) para ver comparaciones entre los componentes Swing y sus equivalentes en webforJ. Las comparaciones incluyen referencias pertinentes para ayudarte a usar cada funcionalidad equivalente de webforJ.
:::

## Cómo funciona {#how-it-works}

La integración funciona mediante tres capas:

1. **Servidor Webswing**: ejecuta tu aplicación de escritorio Java, captura su salida visual y procesa las entradas del usuario
2. **Componente WebswingConnector**: un componente de webforJ que inserta el cliente de Webswing y gestiona la conexión y la comunicación con el servidor
3. **Protocolo de comunicación**: mensajería bidireccional que permite a tu aplicación webforJ enviar comandos a la aplicación Swing y recibir eventos de vuelta

Cuando un usuario accede a tu aplicación webforJ, `WebswingConnector` establece una conexión con el servidor Webswing. El servidor crea una instancia de la aplicación o se vuelve a conectar a una existente y comienza a transmitir su estado visual al navegador. Las interacciones del usuario (ratón y teclado) se capturan y se envían al servidor, donde se reproducen en la aplicación Swing real.

## Temas {#topics}

<DocCardList className="topics-section" />
