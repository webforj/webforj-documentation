---
title: Supervisión del frontend
sidebar_position: 20
description: >-
  Reconstruye src/main/frontend mientras se ejecuta una aplicación webforJ;
  aplica los estilos e imágenes en el acto y recarga la vista al generar
  scripts.
_i18n_title_hash: 87d66a6e5b9e657405313f309833cd07
_i18n_description_hash: d5b96b8ea22c6930a214e543b2de01da
_i18n_hash: 065e4f5ad8868a6758e682f38e2dd3b9
---
El frontend watch reconstruye las fuentes bajo `src/main/frontend` mientras la aplicación se ejecuta y envía la salida al navegador. Es el lado de desarrollo del [agrupador de frontend](/docs/managing-resources/bundler/overview) y requiere que `webforj.devtools.livereload.enabled` esté activado, consulta la [configuración](/docs/configuration/deploy-reload/overview#settings).

## Ejecutando el watch {#running-the-watch}

Ejecuta el objetivo `watch` antes del objetivo que inicia la aplicación. Un proyecto de arquetipo establece esto como su objetivo predeterminado, por lo que `mvn` sin argumentos ejecuta ambos:

```bash
mvn compile webforj:watch spring-boot:run
```

```bash
mvn compile webforj:watch jetty:run
```

En BBjServices, el watch inicia la [ejecución de desarrollo](/docs/configuration/deploy-reload/bbjservices) por sí mismo:

```bash
mvn compile webforj:watch
```

Para ejecutar el watch como un paso de construcción independiente, consulta [Construcción y pruebas](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## Cómo se aplica la salida {#how-the-output-applies}

La acción del navegador depende de la salida producida, no del archivo editado:

| Salida | Acción del navegador |
|---|---|
| Hoja de estilos, desde un archivo `.css`, `.scss`, `.sass`, o `.less` | Aplicada en su lugar. Sin recarga, los datos del formulario y la posición de desplazamiento se mantienen. |
| Imagen | Sustituida en su lugar. Sin recarga. |
| Cualquier otra salida, como `.ts` compilado, `.tsx`, o `.js` | La vista se recarga. |

Cuando una reconstrucción produce varios archivos, el navegador los aplica en su lugar solo si cada archivo califica. De lo contrario, se recarga una vez, por lo que un cambio nunca se aplica parcialmente.

## Durante un reinicio del servidor {#during-a-server-restart}

Un cambio en Java sin una [herramienta de hotswap](/docs/configuration/deploy-reload/hotswap) reinicia el servidor. A través del reinicio:

- Los estilos aplicados permanecen en la página.
- Un indicador se muestra mientras el servidor está inactivo. Aparece solo para un reinicio, no para una recarga manual.
- La página se recarga cuando la aplicación está lista, no antes.

Una adición o eliminación de `@BundleEntry` surtirá efecto cuando finalice ese reinicio.
