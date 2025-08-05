---
title: JRebel
_i18n_hash: 9e2b7ce994eb40e656cf61dc4967ec7e
---
JRebel es una herramienta de desarrollo de Java que se integra con la JVM para detectar cambios en el código y reemplazar clases modificadas directamente en la memoria, permitiendo a los desarrolladores ver los cambios en el código de inmediato sin reiniciar el servidor.

Cuando se realiza un cambio en una clase, método o campo, JRebel compila e inyecta el bytecode actualizado sobre la marcha, eliminando la necesidad de un reinicio completo del servidor. Al aplicar cambios directamente en la aplicación en ejecución, JRebel optimiza el flujo de trabajo de desarrollo, ahorrando tiempo y preservando el estado de la aplicación, incluidas las sesiones de usuario.

## Instalación {#installation}

El sitio oficial de JRebel proporciona [instrucciones de inicio rápido](https://www.jrebel.com/products/jrebel/learn) para poner en marcha el producto en varios IDE populares. Sigue estas instrucciones para integrar JRebel en tu entorno de desarrollo.

Una vez completada la configuración, abre un proyecto de webforJ y asegúrate de que la propiedad `scan` de jetty en el archivo `pom.xml` esté configurada en `0` para desactivar el reinicio automático del servidor. Una vez hecho esto, utiliza el siguiente comando:

```bash
mvn jetty:run
```

Si se hace correctamente, JRebel mostrará información de registro en la terminal, y los cambios realizados en tu programa deberían reflejarse a demanda.

:::info Ver tus cambios
Si se realiza un cambio en una vista o componente que ya se está mostrando, JRebel no forzará una recarga de la página, ya que el servidor no se reinicia.
:::
