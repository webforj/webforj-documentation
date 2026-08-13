---
title: Troubleshooting
sidebar_position: 11
description: >-
  Fix the common cases where craftforJ doesn't appear, a feature is unavailable,
  or the assistant doesn't answer.
_i18n_hash: fcc5f7188c92523c0fb500bfc7b0ce58
---
### Nada aparece en la página {#nothing-appears-on-the-page}

craftforJ se adjunta solo cuando se cumplen todos los requisitos en [Comenzando](/docs/craftforj/getting-started#requirements), y no muestra nada en absoluto cuando falta uno. Revísalos en orden: la dependencia `webforj-devtools` en el classpath, el modo de depuración, la propiedad craftforJ, un navegador en la máquina que ejecuta la aplicación y una licencia de desarrollador válida. Un archivo de configuración en la ubicación incorrecta, o un perfil que sobrescribe una de las propiedades, produce exactamente el mismo resultado que la propiedad estando apagada.

### Una función no está disponible {#a-feature-is-unavailable}

craftforJ muestra una función deshabilitada en lugar de ocultarla, por lo que un control que está presente pero marcado como no soportado se apagó deliberadamente. O bien fue deshabilitado con un [flag de característica](/docs/craftforj/configuration#feature-flags) en la configuración de la aplicación, o la versión de `webforj-devtools` en tu classpath es anterior a eso.

Escribir en la fuente también necesita un raíz de proyecto que craftforJ pueda encontrar. Verifica el que detectó en [Información de la app](/docs/craftforj/app-info), y establece [`project-root`](/docs/craftforj/configuration#project-root) si está mal.

### La validación de Java es más débil de lo esperado {#java-validation-is-weaker-than-expected}

La [validación de compilación](/docs/craftforj/ai#it-writes-java) del asistente necesita un JDK. Verifica la versión de Java en [Información de la app](/docs/craftforj/app-info), y ejecuta la app en un JDK en lugar de un JRE.

### craftforJ parece desactualizado después de una actualización {#craftforj-looks-out-of-date-after-an-update}

Tu navegador almacenó en caché la versión anterior. Realiza una recarga forzada de la página, o abre la app en una ventana privada. Si el problema persiste, confirma qué versión de `webforj-devtools` está realmente en el classpath en [Información de la app](/docs/craftforj/app-info), ya que un jar antiguo en tu repositorio local de Maven se ve igual desde el navegador.

### El asistente no responde {#the-assistant-doesnt-answer}

El asistente necesita un proveedor configurado y un modelo que pueda llamar a herramientas. Un modelo sin soporte para herramientas puede mantener una conversación pero no puede inspeccionar ni cambiar nada. Un modelo local que sigue perdiendo el hilo de la conversación generalmente está funcionando con una ventana de contexto demasiado pequeña.

Si un modelo local está configurado y accesible pero cada solicitud es rechazada, el servidor del modelo está rechazando el origen de la página. Para Ollama, permite el origen y reinícialo:

```bash
launchctl setenv OLLAMA_ORIGINS "*"
pkill ollama && ollama serve
```

En Linux, establece `OLLAMA_ORIGINS` en el entorno desde donde Ollama se inicia y reinícialo.

### craftforJ dice que la app se está reiniciando {#craftforj-says-the-app-is-restarting}

Tu app desaparece regularmente en desarrollo, cada vez que se reconstruye. craftforJ informa lo que está sucediendo en lugar de congelarse, por lo que muestra cuando la app se está reiniciando o la página se está recargando, y sus controles permanecen inertes hasta que la app vuelva. Se reconecta por sí mismo con tu selección y tu trabajo pendiente intacto, por lo que no hay nada que hacer más que esperar. Si informa que no puede alcanzar la app en absoluto, confirma que la app sigue ejecutándose y recarga la página.

### La app sigue reiniciándose {#the-app-keeps-restarting}

Aplicar un cambio a la fuente reinicia la app, como se describe en [Después de aplicar](/docs/craftforj/source-changes#after-you-apply). Los reinicios que ocurren sin un cambio aplicado provienen del observador de archivos de tu construcción en lugar de craftforJ.

### Recopilando registros {#collecting-logs}

Antes de informar un problema, activa la logging detallada en la configuración de craftforJ, borra el registro, reproduce el problema, y luego descarga el registro. Adjunta esto junto con el contenido de [Información de la app](/docs/craftforj/app-info).
