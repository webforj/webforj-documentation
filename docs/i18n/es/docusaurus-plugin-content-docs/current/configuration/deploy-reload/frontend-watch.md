---
title: Frontend watch
sidebar_position: 20
sidebar_class_name: new-content
description: >-
  Rebuild the sources under src/main/frontend while a webforJ app runs, applying
  stylesheet and image output in place and reloading the view for script output.
_i18n_hash: 8307e05aa7a4c55b75fe8667be1f6b27
---
El proceso de observación de frontend reconstruye las fuentes en `src/main/frontend` mientras la aplicación se está ejecutando y envía la salida al navegador. Es el lado de desarrollo del [bundler de frontend](/docs/managing-resources/bundler/overview) y requiere que `webforj.devtools.livereload.enabled` esté activado, consulta la [configuración](/docs/configuration/deploy-reload/overview#settings).

## Ejecutando la vigilancia {#running-the-watch}

Ejecuta el objetivo `watch` antes del objetivo que inicia la aplicación. Un proyecto arquetipo establece esto como su objetivo predeterminado, por lo que `mvn` sin argumentos ejecuta ambos:

```bash
mvn compile webforj:watch spring-boot:run
```

```bash
mvn compile webforj:watch jetty:run
```

Para ejecutar la vigilancia como un paso de construcción independiente, consulta [Construcción y pruebas](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## Cómo se aplica la salida {#how-the-output-applies}

La acción del navegador depende de la salida producida, no del archivo editado:

| Salida | Acción del navegador |
|---|---|
| Hoja de estilos, de una fuente `.css`, `.scss`, `.sass` o `.less` | Aplicada en su lugar. Sin recarga, los datos del formulario y la posición de desplazamiento permanecen. |
| Imagen | Intercambiada en su lugar. Sin recarga. |
| Cualquier otra salida, como `.ts`, `.tsx` o `.js` compilados | La vista se recarga. |

Cuando una reconstrucción produce varios archivos, el navegador los aplica en su lugar solo si cada archivo califica. De lo contrario, se recarga una vez, por lo que un cambio nunca se aplica parcialmente.

## Durante un reinicio del servidor {#during-a-server-restart}

Un cambio en Java sin una [herramienta de hotswap](/docs/configuration/deploy-reload/hotswap) reinicia el servidor. A través del reinicio:

- Los estilos aplicados permanecen en la página.
- Un indicador muestra mientras el servidor está inactivo. Aparece solo para un reinicio, no para una recarga manual.
- La página se recarga cuando la aplicación está lista, no antes.

Una adición o eliminación de `@BundleEntry` tiene efecto cuando ese reinicio se completa.
