---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 853a4bb057c1a3499c26d4714120170f
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing es una tecnología de servidor web que permite que aplicaciones de escritorio Java (Swing, JavaFX, SWT) se ejecuten en un navegador sin modificaciones al código fuente original. Renderiza la aplicación de escritorio en el servidor y transmite la interfaz al navegador utilizando HTML5 canvas, manejando todas las interacciones con el usuario de manera transparente.

## Qué resuelve Webswing {#what-webswing-solves}

Muchas organizaciones tienen inversiones sustanciales en aplicaciones de escritorio Java que contienen lógica empresarial crítica desarrollada durante años o décadas. Estas aplicaciones a menudo no pueden reescribirse fácilmente debido a:

- Lógica de dominio compleja que sería arriesgado recrear
- Integración con bibliotecas o hardware específicos de escritorio
- Restricciones de tiempo y costo de una reescritura completa
- Necesidad de mantener la paridad de funciones con la funcionalidad existente

Webswing permite que estas aplicaciones sean accesibles por la web sin modificaciones, preservando su funcionalidad y apariencia originales.

## Integración con webforJ {#integration-with-webforj}

La integración de webforJ con Webswing proporciona el componente `WebswingConnector`, que permite incrustar aplicaciones alojadas en Webswing directamente dentro de tu aplicación webforJ. Esto crea oportunidades para:

### Modernización progresiva {#progressive-modernization}

En lugar de una reescritura total, puedes:

1. Comenzar incrustando tu aplicación Swing existente a través de `WebswingConnector`
2. Construir nuevas características en webforJ alrededor de la aplicación incrustada
3. Reemplazar gradualmente los componentes de Swing con equivalentes de webforJ
4. Finalmente, eliminar completamente la aplicación heredada

### Aplicaciones híbridas {#hybrid-applications}

Combina una interfaz web moderna construida con webforJ con funcionalidad de escritorio especializada:

- Utiliza webforJ para interfaces orientadas al usuario, paneles y reportes
- Aprovecha Swing para visualizaciones complejas o editores especializados
- Mantén una única experiencia de aplicación integrada

## Cómo funciona {#how-it-works}

La integración opera a través de tres capas:

1. **Servidor Webswing**: ejecuta tu aplicación de escritorio Java, capturando su salida visual y procesando la entrada del usuario
2. **Componente WebswingConnector**: un componente de webforJ que incrusta el cliente de Webswing, gestionando la conexión y comunicación con el servidor
3. **Protocolo de comunicación**: mensajería bidireccional que permite a tu aplicación webforJ enviar comandos a la aplicación Swing y recibir eventos de vuelta

Cuando un usuario accede a tu aplicación webforJ, el `WebswingConnector` establece una conexión con el servidor Webswing. El servidor crea o reconecta una instancia de la aplicación y comienza a transmitir el estado visual al navegador. Las interacciones del usuario (ratón, teclado) se capturan y envían al servidor, donde se reproducen en la aplicación Swing real.

## Temas {#topics}

<DocCardList className="topics-section" />
