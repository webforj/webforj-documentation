---
title: JRebel
description: >-
  Use JRebel with webforJ to hot-swap modified classes into a running Jetty
  server and skip full restarts during development.
_i18n_hash: 639c97ac6892efd7261824c13b7162da
---
JRebel es una herramienta de desarrollo de Java que se integra con la JVM para detectar cambios en el código y reemplazar clases modificadas directamente en memoria, permitiendo a los desarrolladores ver los cambios en el código de inmediato sin reiniciar el servidor.

Cuando se realiza un cambio en una clase, método o campo, JRebel compila e inyecta el bytecode actualizado sobre la marcha, eliminando la necesidad de un reinicio completo del servidor. Al aplicar cambios directamente a la aplicación en ejecución, JRebel optimiza el flujo de trabajo de desarrollo, ahorrando tiempo y preservando el estado de la aplicación, incluidas las sesiones de usuario.

:::tip Cambios en el frontend
Los cambios bajo `src/main/frontend` son manejados por el [frontend watch](/docs/configuration/deploy-reload/frontend-watch), que los vuelve a compilar y actualiza el navegador junto con el servidor.
:::

## Instalación {#installation}

El sitio oficial de JRebel proporciona [instrucciones de inicio rápido](https://www.jrebel.com/products/jrebel/learn) para poner el producto en funcionamiento en varias IDEs populares. Sigue estas instrucciones para integrar JRebel en tu entorno de desarrollo.

Una vez completada la configuración, abre un proyecto webforJ y asegúrate de que la propiedad `scan` de jetty en el archivo `pom.xml` esté configurada en `0` para deshabilitar el reinicio automático del servidor. Una vez hecho esto, utiliza el siguiente comando:

```bash
mvn jetty:run
```

Si se realiza correctamente, JRebel mostrará información de registro en la terminal, y los cambios realizados en tu programa deberían reflejarse a demanda.

:::info Ver tus cambios
Si se realiza un cambio en una vista o componente que ya se está mostrando, JRebel no forzará una recarga de la página, ya que el servidor no se reinicia.
:::
