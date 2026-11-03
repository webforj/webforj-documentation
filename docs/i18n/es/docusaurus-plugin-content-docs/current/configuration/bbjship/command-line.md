---
title: Línea de comandos
sidebar_position: 7
description: >-
  The bbjship commands a BBj administrator runs on the BBj machine to manage the
  deployment endpoint, deployed apps, development runs and BBjServices.
_i18n_hash: a80e91a6626a5c018604f40e37a5ff00
---
# Línea de comandos <DocChip chip='since' label='26.03' /> {#command-line}

La línea de comandos `bbjship` administra BBjShip en la máquina BBj. Se encuentra en `<BBj home>/plugins/webforj-bbjship`, como `bbjship.cmd` en Windows. Todos los comandos se ejecutan dentro de BBjServices, por lo que BBjServices debe estar en ejecución para ejecutar cualquier comando.

```text
bbjship <group> <command> [options]
```

## Comandos {#commands}

| Comando | Descripción |
|---|---|
| [`remote enable [--port PORT] [--ssl-port PORT] [--allow-unencrypted]`](#remote-enable) | Habilita o actualiza el endpoint de implementación e imprime sus direcciones. |
| [`remote disable`](#remote-disable) | Elimina el endpoint de implementación. Las aplicaciones implementadas siguen en ejecución. |
| [`remote status`](#remote-status) | Imprime las direcciones del endpoint, indica si las ejecuciones de desarrollo están habilitadas y si se permiten solicitudes sin cifrar desde otra máquina. |
| [`apps deploy ARCHIVE`](#apps-deploy) | Implementa un archivo de implementación. |
| [`apps list [--long]`](#apps-list) | Muestra las aplicaciones que BBjShip implementó. |
| [`apps undeploy NAME`, `apps undeploy --all`](#apps-undeploy) | Elimina una o todas las aplicaciones que BBjShip implementó. |
| [`development enable`, `development disable`](#development) | Activa o desactiva las [ejecuciones de desarrollo](/docs/configuration/deploy-reload/bbjservices) en esta máquina. |
| [`reset [--yes] [--properties FILE]`](#reset) | Elimina todas las aplicaciones y todo lo que BBjShip haya configurado. |

## Opciones compartidas {#options}

| Configuración | Valor predeterminado | Descripción |
|---|---|---|
| `--username USER` | Sin configurar | Nombre de usuario administrador de BBj. |
| `--password PASSWORD` | Sin configurar | Contraseña del administrador de BBj. |
| `--password-stdin` | Desactivado | Lee la contraseña de la primera línea de la entrada estándar. No está disponible en Windows. |
| `--help`, `-h` | | Imprime el uso de la línea de comandos o de un comando. |
| `--version`, `-V` | | Imprime la versión de BBjShip. |

Una contraseña proporcionada con `--password` es visible para otros usuarios de la máquina en la lista de procesos mientras se ejecuta el comando. En macOS y Linux, un script pasa la contraseña por la entrada estándar:

```bash
printf '%s\n' "$PASSWORD" | bbjship remote enable --username admin --password-stdin
```

En todos los sistemas, las variables mantienen la contraseña fuera de la línea de comandos:

```bash
WEBFORJ_BBJ_USERNAME=admin WEBFORJ_BBJ_PASSWORD="$PASSWORD" bbjship remote enable
```

En Windows, la entrada estándar no está disponible, por lo que `--password-stdin` termina con el código de salida `2`. Proporciona la contraseña como valor o mediante las variables:

```text
bbjship.cmd remote enable --username admin --password <value>
```

Si se proporcionan tanto `--password` como `--password-stdin`, un nombre de usuario sin contraseña o una contraseña sin nombre de usuario, el comando termina con el código de salida `2` y un mensaje que indica qué se debe especificar.

Si no se proporcionan credenciales, el comando usa la cuenta de fábrica `admin` con la contraseña `admin123` e imprime una advertencia.

## Variables de entorno {#environment-variables}

| Variable | Valor predeterminado | Descripción |
|---|---|---|
| `WEBFORJ_BBJ_USERNAME` | Sin configurar | Nombre de usuario cuando no se proporciona `--username`. |
| `WEBFORJ_BBJ_PASSWORD` | Sin configurar | Contraseña cuando no se proporciona `--password` ni `--password-stdin`. |
| `NO_COLOR` | Sin configurar | Desactiva la salida en color. |

## Acceso remoto {#remote}

Los comandos `remote` administran el endpoint de implementación.

### `remote enable` {#remote-enable}

```text
bbjship remote enable [--port PORT] [--ssl-port PORT] [--allow-unencrypted]
```

Habilita o actualiza el endpoint de implementación e imprime sus direcciones.

| Configuración | Valor predeterminado | Descripción |
|---|---|---|
| `--port PORT` | `8888` | Puerto HTTP del servidor web de BBjServices para el endpoint. |
| `--ssl-port PORT` | Ninguno | Puerto SSL del servidor web de BBjServices para el endpoint. Sin este puerto, el endpoint no tiene dirección HTTPS. |
| `--allow-unencrypted` | Desactivado | Acepta las credenciales de administración por HTTP desde otra máquina. Si no se especifica, las solicitudes desde otra máquina deben usar el puerto SSL y el endpoint responde con HTTP 403. Las solicitudes desde la propia máquina BBj usan HTTP en cualquier caso. |

Los puertos deben ser puertos en los que el servidor web de BBjServices ya esté escuchando. De lo contrario, `remote enable` termina con el código de salida `1`, indica el puerto que no responde y conserva el endpoint anterior.

Cada ejecución de `remote enable` vuelve a registrar los puertos y el valor de `--allow-unencrypted`. Una ejecución sin la opción desactiva esta configuración e imprime `Refused unencrypted requests from another machine`. Una ejecución con la opción la activa e imprime `Allowed unencrypted requests from another machine`. La compilación que implementa por HTTP desde otra máquina también establece [`allowUnencrypted`](/docs/configuration/bbjship/deploying#deploying-from-another-machine).

### `remote disable` {#remote-disable}

```text
bbjship remote disable
```

Elimina el endpoint de implementación. Las aplicaciones implementadas siguen en ejecución.

<!-- vale Google.WordListCase = NO -->
:::warning Las compilaciones no pueden implementar
`remote disable` elimina el endpoint de implementación. Las compilaciones y los trabajos de CI que implementan en él fallan hasta que vuelvas a ejecutar `remote enable`. Anota los puertos y la dirección del endpoint antes de desactivarlo. Las aplicaciones implementadas siguen en ejecución.
:::
<!-- vale Google.WordListCase = YES -->

### `remote status` {#remote-status}

```text
bbjship remote status
```

Imprime las direcciones del endpoint, indica si las ejecuciones de desarrollo están habilitadas y si se permiten solicitudes sin cifrar desde otra máquina.

```text
$ bbjship remote status
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
Development runs  disabled
Unencrypted requests  refused
```

## Aplicaciones {#apps}

Los comandos `apps` muestran y eliminan únicamente las aplicaciones que BBjShip implementó. No afectan a las aplicaciones registradas en Enterprise Manager.

### `apps deploy` {#apps-deploy}

```text
bbjship apps deploy ARCHIVE
```

Implementa un archivo de implementación. `apps deploy` instala un archivo creado por [`bbj-package`](/docs/configuration/bbjship/deploying#package-without-deploying), sin usar el endpoint de implementación. El nombre del archivo sin `.zip` es el [nombre de publicación](/docs/configuration/bbjship/deploying#publish-names). BBjServices lee el archivo, por lo que el usuario del sistema operativo que ejecuta BBjServices necesita tener acceso de lectura.

```text
$ bbjship apps deploy inventory.zip
Deployed inventory /webapp/inventory
```

:::warning Reemplaza la aplicación
Implementar un archivo con el nombre de una aplicación ya implementada reemplaza esa aplicación. Se elimina la carpeta de implementación anterior y finalizan las sesiones en ejecución de la aplicación. Conserva el archivo anterior si es posible que necesites volver a él.
:::

### `apps list` {#apps-list}

```text
bbjship apps list [--long]
```

Muestra las aplicaciones que BBjShip implementó.

| Configuración | Valor predeterminado | Descripción |
|---|---|---|
| `--long`, `-l` | Desactivado | Añade la carpeta de implementación y las carpetas de clases de cada aplicación. |

```text
$ bbjship apps list
NAME       URL
inventory  /webapp/inventory
```

### `apps undeploy` {#apps-undeploy}

```text
bbjship apps undeploy NAME
bbjship apps undeploy --all
```

Elimina una o todas las aplicaciones que BBjShip implementó.

| Configuración | Valor predeterminado | Descripción |
|---|---|---|
| `--all` | Desactivado | Anula la implementación de todas las aplicaciones que BBjShip implementó. |

:::warning No se puede deshacer
`apps undeploy` elimina la aplicación de Enterprise Manager, finaliza sus sesiones en ejecución y borra su carpeta de implementación. `apps undeploy --all` hace lo mismo con todas las aplicaciones que BBjShip implementó. Esta acción no se puede deshacer. Conserva el archivo de implementación de cada aplicación, por ejemplo `target/bbj/<publish name>.zip`, para poder volver a implementarla.
:::

## Desarrollo {#development}

Las ejecuciones de desarrollo están desactivadas en una instalación nueva, y la primera ejecución de `webforj:watch` en la misma máquina las activa. `bbjship development disable` las desactiva hasta que la siguiente ejecución de `webforj:watch` en esta máquina las vuelva a activar. Se rechaza una ejecución de desarrollo desde otra máquina, ya que la configuración que ejecuta primero solo funciona en la máquina BBj.

### `development enable` {#development-enable}

```text
bbjship development enable
```

Activa las [ejecuciones de desarrollo](/docs/configuration/deploy-reload/bbjservices) en esta máquina.

### `development disable` {#development-disable}

```text
bbjship development disable
```

Desactiva las [ejecuciones de desarrollo](/docs/configuration/deploy-reload/bbjservices) en esta máquina.

## Restablecimiento {#reset}

```text
bbjship reset [--yes] [--properties FILE]
```

`reset` anula la implementación de todas las aplicaciones que BBjShip implementó, elimina el endpoint de implementación, el interruptor de desarrollo y la configuración `--allow-unencrypted`, restaura el archivo de propiedades de BBjServices que modificó una ejecución de desarrollo y elimina los archivos escritos por BBjShip. El comando nunca detiene BBjServices. Cuando restaura el archivo de propiedades, reinicia BBjServices para aplicar los cambios. La carpeta del complemento se conserva.

| Configuración | Valor predeterminado | Descripción |
|---|---|---|
| `--yes` | Desactivado | Omite la confirmación. |
| `--properties FILE` | `<BBj home>/cfg/BBj.properties` | Archivo de propiedades de BBjServices que restaura `reset`. |

El comando imprime lo que hará y pide confirmación, a menos que se proporcione `--yes`. En Windows, `reset` sin `--yes` termina con el código de salida `2` antes de iniciar sesión.

:::warning Haz una copia de seguridad primero
`reset` no se puede deshacer. Elimina la carpeta de todas las aplicaciones que BBjShip implementó y finaliza sus sesiones en ejecución. Antes de ejecutarlo, conserva el archivo de implementación de cada aplicación, por ejemplo `target/bbj/<publish name>.zip` de la compilación, y haz una copia de seguridad de `<BBj home>/cfg/BBj.properties`. Si se conservó una copia del archivo en la carpeta de ejecución de BBjShip, `reset` la restaura sobre `cfg/BBj.properties`, por lo que se perderán los cambios que hayas hecho en el archivo después de la primera ejecución de desarrollo. Después, reinicia BBjServices para aplicar la configuración Java restaurada.
:::

## Códigos de salida {#exit-codes}

| Código | Significado |
|---|---|
| `0` | El comando se ejecutó correctamente. |
| `1` | El comando falló e imprimió la causa, o se canceló `reset`. |
| `2` | Los argumentos no son válidos o el comando necesita entrada estándar en Windows. El mensaje indica qué se debe especificar. |

[Solución de problemas](/docs/configuration/bbjship/troubleshooting#command-line) enumera los mensajes y qué hacer en cada caso.
