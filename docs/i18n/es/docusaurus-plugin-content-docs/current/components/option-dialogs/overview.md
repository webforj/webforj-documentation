---
sidebar_position: 35
title: Option Dialogs
hide_giscus_comments: true
_i18n_hash: 7ff00c0abd564956da84fbd20761413e
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

# Diálogos de Opción
<!-- vale on -->

Los diálogos de opción proporcionan una forma para que la aplicación se comunique con los usuarios y recopile su entrada. Estos diálogos son modales, lo que significa que bloquean la ejecución de la aplicación hasta que el usuario interactúa con ellos, asegurando que los mensajes importantes sean atendidos antes de continuar.

Los diálogos de opción en webforJ son similares al `JOptionPane` en Swing, resolviendo un problema fundamental de manejo de diálogos bloqueantes en aplicaciones web.

:::tip Modalidad
Al usar diálogos de opción para crear diálogos modales en webforJ, el diálogo bloquea la entrada del usuario a otras partes de la aplicación y procesa eventos únicamente para el diálogo modal. Esto asegura que el diálogo permanezca receptivo mientras se previenen interacciones con otras partes, mejorando la experiencia del usuario y manteniendo el flujo de la aplicación. El servidor deja de procesar cualquier otra solicitud hasta que el diálogo se cierre o se devuelva un valor desde él.
:::

## Temas {#topics}

<DocCardList className="topics-section" />
