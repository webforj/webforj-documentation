---
sidebar_position: 35
title: Option Dialogs
hide_giscus_comments: true
_i18n_hash: 4d818d70f6238be10dc8913d19ed47b7
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

# Diálogos de Opción
<!-- vale on -->

Los diálogos de opción proporcionan una forma para que la aplicación se comunique con los usuarios y recopile su información. Estos diálogos son modales, lo que significa que bloquean la ejecución de la aplicación hasta que el usuario interactúe con ellos, asegurando que los mensajes importantes se aborden antes de continuar.

Los diálogos de opción en webforJ son similares al `JOptionPane` en Swing, resolviendo un problema fundamental de manejo de diálogos bloqueantes en aplicaciones web.

:::tip Modalidad
Al utilizar diálogos de opción para crear diálogos modales en webforJ, el diálogo bloquea la entrada del usuario a otras partes de la aplicación y procesa eventos únicamente para el diálogo modal. Esto asegura que el diálogo permanezca receptivo mientras se evitan interacciones con otras partes, mejorando la experiencia del usuario y manteniendo el flujo de la aplicación. El servidor deja de procesar cualquier solicitud adicional hasta que el diálogo sea cerrado o se devuelva un valor de este.
:::

## Temas {#topics}

<DocCardList className="topics-section" />
