---
title: Instalación de BBjShip
sidebar_position: 4
description: >-
  Install BBjShip on your own machine with the webforJ Maven plugin, or on a
  shared BBj server with the BBj Plugin Manager.
_i18n_hash: b5b4d6d8d79017066eecfcda86557633
---
# Instalación de BBjShip <DocChip chip='since' label='26.03' />

BBjShip se ejecuta dentro de BBjServices, por lo que siempre está instalado en la máquina que ejecuta BBjServices. Una vez instalado y habilitado su punto de conexión de implementación, las compilaciones se implementan en él mediante la dirección `/webforj-bbjship` del servidor web de BBjServices, ya sea desde esa misma máquina o desde otra.

La forma de instalar BBjShip depende de dónde se ejecute BBjServices:

| Dónde se ejecuta BBjServices | Quién instala BBjShip | Cómo |
|---|---|---|
| Tu propia máquina | El complemento de compilación de webforJ | Automáticamente con `mvn` o una vez con `mvn webforj:bbj-setup` |
| Un servidor compartido o de producción | Un administrador de ese servidor | BBj Plugin Manager y, después, `bbjship remote enable` |

En ambos casos, instala la versión de BBjShip que coincida con la versión de webforJ de tus aplicaciones. Las implementaciones fallan si las versiones no coinciden.

## En tu propia máquina {#on-your-own-machine}

Cuando BBj se ejecuta en la misma máquina que tu compilación, no tienes que instalar BBjShip por tu cuenta. La primera [ejecución de desarrollo](/docs/configuration/deploy-reload/bbjservices) de un proyecto configurado para BBjShip lo instala y habilita su punto de conexión de implementación:

```bash
mvn
```

### Mediante el complemento de Maven {#through-the-maven-plugin}

Para implementar con `mvn install` antes de iniciar cualquier ejecución de desarrollo, instala BBjShip una vez desde la carpeta de tu proyecto:

```bash
mvn webforj:bbj-setup
```

El objetivo instala la versión de BBjShip que coincide con `webforj-maven-plugin` y habilita el punto de conexión de implementación en el puerto de la URL de implementación. Necesita:

- BBjServices en ejecución y respondiendo en el puerto de la URL de implementación
- Una URL de implementación que indique el nombre de tu máquina, por ejemplo, `http://localhost:8888/webforj-bbjship`
- El nombre de usuario y la contraseña del administrador de BBj, de `<admin>` o de las propiedades `webforj.bbj.admin.*`. Si no se proporcionan, el objetivo utiliza la cuenta de fábrica `admin` con la contraseña `admin123` y muestra una advertencia.
- La carpeta de instalación de BBj, de `<home>`, `webforj.bbj.home` o `BBJ_HOME`. Si no se proporciona, el objetivo la detecta.

Después de actualizar webforJ, reemplaza la versión instalada de BBjShip por la nueva:

```bash
mvn webforj:bbj-setup -Dwebforj.bbj.force=true
```

Las aplicaciones implementadas seguirán implementadas cuando vuelvas a ejecutar el objetivo.

:::tip Archivos en uso en Windows
En Windows, el reemplazo puede fallar mientras BBjServices mantiene abiertos los archivos de BBjShip instalado. Reinicia BBjServices y vuelve a ejecutar el objetivo; consulta [Solución de problemas](/docs/configuration/bbjship/troubleshooting#windows).
:::

## En un servidor {#on-a-server}

En un servidor BBj compartido o de producción, un administrador instala BBjShip una vez en ese servidor. Después, los desarrolladores y las canalizaciones de CI implementan en él desde sus propias máquinas a través de HTTPS.

1. Instala BBjShip con [BBj Plugin Manager](#through-the-bbj-plugin-manager).
2. [Habilita el punto de conexión de implementación](#enabling-the-deployment-endpoint) con el puerto HTTPS del servidor web de BBjServices.
3. Crea un token de autorización en Enterprise Manager para las compilaciones que implementan; consulta [Implementar desde otra máquina](/docs/configuration/bbjship/deploying#deploying-from-another-machine).

### Mediante BBj Plugin Manager {#through-the-bbj-plugin-manager}

En [BBj Plugin Manager](https://documentation.basis.cloud/BASISHelp/WebHelp/bbutil/BBj_Plugin_Manager.htm), instala BBjShip desde el repositorio `webforj-bbjship` de la organización `BBj-Plugins`. Una etiqueta instala una versión de webforJ y `master` instala la instantánea de desarrollo. Más adelante, actualízalo desde la pestaña Installed.

### Habilitar el punto de conexión de implementación {#enabling-the-deployment-endpoint}

El punto de conexión de implementación es la dirección a la que se implementan las compilaciones. Habilítalo en el servidor con tu cuenta de administrador de BBj y los puertos en los que escucha el servidor web de BBjServices. `--password` recibe la contraseña como valor, que otros usuarios de la máquina pueden ver en la lista de procesos mientras se ejecuta el comando. En macOS y Linux, `--password-stdin` lee la contraseña desde la entrada estándar, y `WEBFORJ_BBJ_PASSWORD` la mantiene fuera de la línea de comandos en todos los sistemas:

```text
$ <BBj home>/plugins/webforj-bbjship/bbjship remote enable --username admin --password <value> --port 8888 --ssl-port 8443
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
```

Si no se especifica `--port`, el punto de conexión usa el puerto 8888. Si no se especifica `--ssl-port`, no tendrá una dirección HTTPS. Las compilaciones de otras máquinas se implementan en la dirección HTTPS; el punto de conexión responde con HTTP 403 a sus solicitudes HTTP, a menos que se especifique `--allow-unencrypted`. BBjServices debe estar en ejecución. Consulta [Línea de comandos](/docs/configuration/bbjship/command-line) para ver todos los comandos.

:::tip Contraseña en Windows
En Windows, ejecuta `bbjship.cmd` y pasa la contraseña con `--password` o `WEBFORJ_BBJ_PASSWORD`. `--password-stdin` no está disponible allí y termina con el código de salida `2`.
:::

## Desinstalar {#uninstalling}

Primero, ejecuta `bbjship reset`. Este comando anula la implementación de todas las aplicaciones y elimina todo lo que BBjShip haya configurado en Enterprise Manager. Después, desinstala BBjShip en BBj Plugin Manager.

:::warning Haz una copia de seguridad primero
No se puede deshacer `bbjship reset`. Antes, haz una copia de seguridad de `<BBj home>/cfg/BBj.properties` y conserva el archivo de implementación de cada aplicación; consulta [Restablecer](/docs/configuration/bbjship/command-line#reset).
:::
