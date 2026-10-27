---
title: Instalando BBjShip
sidebar_position: 4
description: >-
  Install BBjShip on your own machine with the webforJ Maven plugin, or on a
  shared BBj server with the BBj Plugin Manager.
_i18n_hash: 1f408734cddab0f6acd5a9446a2d51e9
---
# Instalación de BBjShip <DocChip chip='since' label='26.03' />

BBjShip se ejecuta dentro de BBjServices, por lo que siempre se instala en la máquina que ejecuta BBjServices. Una vez instalado y habilitado su punto de despliegue, tus compilaciones se despliegan a través de la dirección `/webforj-bbjship` en el servidor web de BBjServices, ya sea desde esa misma máquina o desde otra.

La forma en que instalas BBjShip depende de dónde se ejecute BBjServices:

| Dónde se ejecuta BBjServices | Quién instala BBjShip | Cómo |
|---|---|---|
| Tu propia máquina | El plugin de construcción webforJ | Automáticamente con `mvn`, o una vez con `mvn webforj:bbj-setup` |
| Un servidor compartido o de producción | Un administrador de ese servidor | El BBj Plugin Manager, luego `bbjship remote enable` |

En ambos casos, instala la versión de BBjShip que coincida con la versión de webforJ de tus aplicaciones. Los despliegues fallan cuando las dos difieren.

## En tu propia máquina {#on-your-own-machine}

Cuando BBj se ejecuta en la misma máquina que tu construcción, no instalas BBjShip tú mismo. La primera [ejecución de desarrollo](/docs/configuration/deploy-reload/bbjservices) de un proyecto configurado para BBjShip lo instala y habilita su punto de despliegue:

```bash
mvn
```

### A través del plugin de Maven {#through-the-maven-plugin}

Para desplegar con `mvn install` antes de comenzar cualquier ejecución de desarrollo, instala BBjShip una vez desde la carpeta de tu proyecto:

```bash
mvn webforj:bbj-setup
```

El objetivo instala la versión de BBjShip que coincide con el `webforj-maven-plugin` y habilita el punto de despliegue en el puerto de tu URL de despliegue. Necesita:

- BBjServices en ejecución y respondiendo en el puerto de la URL de despliegue
- Una URL de despliegue que nombre tu máquina, por ejemplo `http://localhost:8888/webforj-bbjship`
- El nombre de usuario y la contraseña del administrador de BBj, de `<admin>` o las propiedades `webforj.bbj.admin.*`. Sin ellos, el objetivo utiliza la cuenta por defecto `admin` con `admin123` y muestra una advertencia.
- La carpeta de instalación de BBj, de `<home>`, `webforj.bbj.home` o `BBJ_HOME`. Sin ellos, el objetivo la detecta.

Después de una actualización de webforJ, reemplaza el BBjShip instalado con la nueva versión:

```bash
mvn webforj:bbj-setup -Dwebforj.bbj.force=true
```

Tus aplicaciones desplegadas permanecen desplegadas cuando vuelves a ejecutar el objetivo.

:::tip Archivos en uso en Windows
En Windows, un reemplazo puede fallar mientras BBjServices mantiene los archivos del BBjShip instalado abiertos. Reinicia BBjServices y ejecuta el objetivo nuevamente, consulta [Solución de problemas](/docs/configuration/bbjship/troubleshooting#windows).
:::

## En un servidor {#on-a-server}

En un servidor BBj compartido o de producción, un administrador instala BBjShip una vez en ese servidor. Los desarrolladores y las tuberías de CI luego se despliegan a él desde sus propias máquinas a través de HTTPS.

1. Instala BBjShip con el [BBj Plugin Manager](#through-the-bbj-plugin-manager).
2. [Habilita el punto de despliegue](#enabling-the-deployment-endpoint) con el puerto HTTPS del servidor web de BBjServices.
3. Crea un token de autorización en Enterprise Manager para las compilaciones que despliegan, consulta [Despliegue desde otra máquina](/docs/configuration/bbjship/deploying#deploying-from-another-machine).

### A través del BBj Plugin Manager {#through-the-bbj-plugin-manager}

En el [BBj Plugin Manager](https://documentation.basis.cloud/BASISHelp/WebHelp/bbutil/BBj_Plugin_Manager.htm), instala BBjShip desde el repositorio `webforj-bbjship` de la organización `BBj-Plugins`. Una etiqueta instala una versión de webforJ, y `master` instala el snapshot de desarrollo. Actualízalo más tarde en la pestaña Instalados.

### Habilitando el punto de despliegue {#enabling-the-deployment-endpoint}

El punto de despliegue es la dirección a la que se despliegan las compilaciones. Habilítalo en el servidor con tu cuenta de administrador de BBj y los puertos que el servidor web de BBjServices escucha. `--password` toma la contraseña como un valor, que otros usuarios de la máquina pueden ver en la lista de procesos mientras se ejecuta el comando. En macOS y Linux, `--password-stdin` la lee desde la entrada estándar, y `WEBFORJ_BBJ_PASSWORD` la mantiene fuera de la línea de comandos en todos los sistemas:

```text
$ <BBj home>/plugins/webforj-bbjship/bbjship remote enable --username admin --password <value> --port 8888 --ssl-port 8443
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
```

Sin `--port`, el endpoint usa el puerto 8888. Sin `--ssl-port`, no tiene dirección HTTPS. BBjServices debe estar en ejecución. Consulta [Línea de comandos](/docs/configuration/bbjship/command-line) para cada comando.

:::tip Contraseña en Windows
En Windows, ejecuta `bbjship.cmd` y pasa la contraseña con `--password` o `WEBFORJ_BBJ_PASSWORD`. `--password-stdin` no está disponible allí y termina con el código de salida `2`.
:::

## Desinstalación {#uninstalling}

Ejecuta `bbjship reset` primero. Esto desmantela cada aplicación y elimina todo lo que BBjShip configuró en Enterprise Manager. Luego desinstala BBjShip en el BBj Plugin Manager.

:::warning Haz una copia de seguridad primero
`bbjship reset` no se puede deshacer. Haz una copia de seguridad de `<BBj home>/cfg/BBj.properties` y conserva el archivo de despliegue de cada aplicación primero, consulta [Reiniciar](/docs/configuration/bbjship/command-line#reset).
:::
