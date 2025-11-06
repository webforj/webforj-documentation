---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: e8f61966c5b7d0745f65f23172dd114a
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing es una tecnología de servidor web que permite que las aplicaciones de escritorio Java (Swing, JavaFX, SWT) se ejecuten en un navegador web sin ninguna modificación en el código fuente original. Renderiza la aplicación de escritorio en el servidor y transmite la interfaz al navegador utilizando HTML5 canvas, manejando todas las interacciones del usuario de manera transparente.

## Qué resuelve Webswing

Muchas organizaciones han realizado inversiones sustanciales en aplicaciones de escritorio Java que contienen lógica empresarial crítica desarrollada a lo largo de los años o décadas. Estas aplicaciones a menudo no pueden reescribirse fácilmente debido a:

- Lógica de dominio compleja que sería arriesgado recrear
- Integración con bibliotecas o hardware específicos de escritorio
- Restricciones de tiempo y costo para una reescritura completa
- Necesidad de mantener la paridad de funciones con la funcionalidad existente

Webswing permite que estas aplicaciones sean accesibles por la web sin modificación, preservando su funcionalidad y apariencia originales.

## Integración con webforJ

La integración de Webswing con webforJ proporciona el componente `WebswingConnector`, que te permite incrustar aplicaciones hospedadas en Webswing directamente dentro de tu aplicación webforJ. Esto crea oportunidades para:

### Modernización progresiva

En lugar de una reescritura total, puedes:

1. Comenzar incrustando tu aplicación Swing existente a través de `WebswingConnector`
2. Construir nuevas funciones en webforJ alrededor de la aplicación incrustada
3. Reemplazar gradualmente los componentes de Swing con equivalentes de webforJ
4. Eventualmente, eliminar por completo la aplicación heredada

### Aplicaciones híbridas

Combina una interfaz web moderna construida con webforJ con funcionalidad de escritorio especializada:

- Usa webforJ para interfaces de usuario, paneles y reportes
- Aprovecha Swing para visualizaciones complejas o editores especializados
- Mantén una experiencia de aplicación integrada

## Cómo funciona

La integración opera a través de tres capas:

1. **Servidor Webswing**: ejecuta tu aplicación de escritorio Java, capturando su salida visual y procesando la entrada del usuario
2. **Componente WebswingConnector**: un componente de webforJ que incrusta el cliente Webswing, gestionando la conexión y comunicación con el servidor
3. **Protocolo de Comunicación**: mensajería bidireccional que permite que tu aplicación webforJ envíe comandos a la aplicación Swing y reciba eventos de vuelta

Cuando un usuario accede a tu aplicación webforJ, el `WebswingConnector` establece una conexión con el servidor Webswing. El servidor crea o reconecta a una instancia de la aplicación y comienza a transmitir el estado visual al navegador. Las interacciones del usuario (ratón, teclado) se capturan y se envían al servidor, donde se reproducen en la aplicación Swing real.

## Topics {#topics}

<DocCardList className="topics-section" />
