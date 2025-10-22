---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 8dcd8fdee2734f6b4b243b0ea82fa1c2
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing es una tecnología de servidor web que permite que aplicaciones de escritorio Java (Swing, JavaFX, SWT) se ejecuten en un navegador web sin ninguna modificación al código fuente original. Renderiza la aplicación de escritorio en el servidor y transmite la interfaz al navegador utilizando canvas HTML5, manejando todas las interacciones del usuario de forma transparente.

## Lo que Webswing soluciona

Muchas organizaciones tienen inversiones sustanciales en aplicaciones de escritorio Java que contienen lógica empresarial crítica desarrollada durante años o décadas. Estas aplicaciones a menudo no se pueden reescribir fácilmente debido a:

- Lógica de dominio compleja que sería arriesgado recrear
- Integración con bibliotecas o hardware específicos de escritorio
- Limitaciones de tiempo y costo de una reescritura completa
- Necesidad de mantener paridad de funciones con la funcionalidad existente

Webswing permite que estas aplicaciones sean accesibles a través de la web sin modificación, preservando su funcionalidad y apariencia originales.

## Integración con webforJ

La integración de webforJ con Webswing proporciona el componente `WebswingConnector`, que permite incrustar aplicaciones hospedadas en Webswing directamente dentro de tu aplicación webforJ. Esto crea oportunidades para:

### Modernización progresiva

En lugar de una reescritura total, puedes:

1. Comenzar incrustando tu aplicación Swing existente a través de `WebswingConnector`
2. Construir nuevas características en webforJ alrededor de la aplicación incrustada
3. Reemplazar gradualmente los componentes de Swing por equivalentes de webforJ
4. Eventualmente eliminar la aplicación heredada por completo

### Aplicaciones híbridas

Combina una interfaz web moderna construida con webforJ con funcionalidad de escritorio especializada:

- Usa webforJ para interfaces de usuario, tableros e informes
- Aprovecha Swing para visualizaciones complejas o editores especializados
- Mantén una experiencia de aplicación integrada única

## Cómo funciona

La integración opera a través de tres capas:

1. **Servidor Webswing**: ejecuta tu aplicación de escritorio Java, capturando su salida visual y procesando la entrada del usuario
2. **Componente WebswingConnector**: un componente de webforJ que incrusta el cliente Webswing, gestionando la conexión y la comunicación con el servidor
3. **Protocolo de comunicación**: mensajería bidireccional que permite a tu aplicación webforJ enviar comandos a la aplicación Swing y recibir eventos de vuelta

Cuando un usuario accede a tu aplicación webforJ, el `WebswingConnector` establece una conexión con el servidor Webswing. El servidor crea o reconecta a una instancia de la aplicación, y comienza a transmitir el estado visual al navegador. Las interacciones del usuario (ratón, teclado) se capturan y se envían al servidor, donde se reproducen en la aplicación Swing real.

## Topics {#topics}

<DocCardList className="topics-section" />
