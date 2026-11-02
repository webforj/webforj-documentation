---
title: Command Line
sidebar_position: 7
description: >-
  The bbjship commands a BBj administrator runs on the BBj machine to manage the
  deployment endpoint, deployed apps, development runs and BBjServices.
_i18n_hash: 976400b6e439ac682858351e8f9ade17
---
# Línea de comandos <DocChip chip='since' label='26.03' /> {#command-line}

La línea de comandos `bbjship` gestiona BBjShip en la máquina BBj. Se encuentra en `<BBj home>/plugins/webforj-bbjship`, como `bbjship.cmd` en Windows. Cada comando se ejecuta dentro de BBjServices, por lo que BBjServices debe estar en ejecución para cada comando.

```text
bbjship <grupo> <comando> [opciones]
```

## Comandos {#commands}

| Comando | Descripción |
|---|---|
| [`remote enable [--port PORT] [--ssl-port PORT]`](#remote-enable) | Habilita o actualiza el punto de enlace de implementación e imprime sus direcciones. |
| [`remote disable`](#remote-disable) | Elimina el punto de enlace de implementación. Las aplicaciones implementadas siguen funcionando. |
| [`remote status`](#remote-status) | Imprime las direcciones del punto de enlace y si las ejecuciones de desarrollo están habilitadas. |
| [`apps deploy ARCHIVE`](#apps-deploy) | Implementa un archivo de implementación. |
| [`apps list [--long]`](#apps-list) | Lista las aplicaciones que BBjShip ha implementado. |
| [`apps undeploy NAME`, `apps undeploy --all`](#apps-undeploy) | Elimina una o todas las aplicaciones que BBjShip ha implementado. |
| [`development enable`, `development disable`](#development) | Activa o desactiva las [ejecuciones de desarrollo](/docs/configuration/deploy-reload/bbjservices) en esta máquina. |
| [`reset [--yes] [--properties FILE]`](#reset) | Elimina cada aplicación y todo lo que BBjShip configuró. |

## Opciones compartidas {#options}

| Configuración | Predeterminado | Descripción |
|---|---|---|
| `--username USER` | No establecido | Nombre de usuario del administrador de BBj. |
| `--password PASSWORD` | No establecido | Contraseña del administrador de BBj. |
| `--password-stdin` | Apagado | Lee la contraseña de la primera línea de la entrada estándar. No disponible en Windows. |
| `--help`, `-h` | | Imprime el uso de la línea de comandos o de un comando. |
| `--version`, `-V` | | Imprime la versión de BBjShip. |

Una contraseña dada con `--password` es visible para otros usuarios de la máquina en la lista de procesos mientras se ejecuta el comando. En macOS y Linux, un script pasa la contraseña a la entrada estándar en su lugar:

```bash
printf '%s\n' "$PASSWORD" | bbjship remote enable --username admin --password-stdin
```

En cada sistema, las variables mantienen la contraseña fuera de la línea de comandos:

```bash
WEBFORJ_BBJ_USERNAME=admin WEBFORJ_BBJ_PASSWORD="$PASSWORD" bbjship remote enable
```

En Windows, la entrada estándar no está disponible, por lo que `--password-stdin` termina con el código de salida `2`. Pasa la contraseña como un valor o a través de las variables:

```text
bbjship.cmd remote enable --username admin --password <valor>
```

Dar tanto `--password` como `--password-stdin`, un nombre de usuario sin contraseña, o una contraseña sin nombre de usuario termina con el código de salida `2` y un mensaje que indica qué especificar.

Sin credenciales, el comando utiliza la cuenta de fábrica `admin` con `admin123` e imprime una advertencia.

## Variables de entorno {#environment-variables}

| Variable | Predeterminado | Descripción |
|---|---|---|
| `WEBFORJ_BBJ_USERNAME` | No establecido | Nombre de usuario cuando `--username` está ausente. |
| `WEBFORJ_BBJ_PASSWORD` | No establecido | Contraseña cuando no se proporciona ni `--password` ni `--password-stdin`. |
| `NO_COLOR` | No establecido | Desactiva la salida en color. |

## Remoto {#remote}

Los comandos `remote` gestionan el punto de enlace de implementación.

### `remote enable` {#remote-enable}

```text
bbjship remote enable [--port PORT] [--ssl-port PORT]
```

Habilita o actualiza el punto de enlace de implementación e imprime sus direcciones.

| Configuración | Predeterminado | Descripción |
|---|---|---|
| `--port PORT` | `8888` | Puerto HTTP del servidor web de BBjServices para el punto de enlace. |
| `--ssl-port PORT` | Ninguno | Puerto SSL del servidor web de BBjServices para el punto de enlace. Sin él, el punto de enlace no tiene dirección HTTPS. |

Los puertos deben ser puertos a los que el servidor web de BBjServices ya escucha. De lo contrario, `remote enable` termina con el código de salida `1`, nombra el puerto que no responde y mantiene el punto de enlace anterior.

### `remote disable` {#remote-disable}

```text
bbjship remote disable
```

Elimina el punto de enlace de implementación. Las aplicaciones implementadas siguen funcionando.

<!-- vale Google.WordListCase = NO -->
:::warning No se pueden implementar construcciones
`remote disable` elimina el punto de enlace de implementación. Las construcciones y trabajos de CI que implementan en él fallan hasta que vuelva a ejecutar `remote enable`. Tenga en cuenta los puertos y la dirección del punto de enlace antes de deshabilitarlo. Las aplicaciones implementadas siguen funcionando.
:::
<!-- vale Google.WordListCase = YES -->

### `remote status` {#remote-status}

```text
bbjship remote status
```

Imprime las direcciones del punto de enlace y si las ejecuciones de desarrollo están habilitadas.

```text
$ bbjship remote status
Habilitado http://localhost:8888/webforj-bbjship
Habilitado https://localhost:8443/webforj-bbjship
Ejecuciones de desarrollo deshabilitadas
```

## Aplicaciones {#apps}

Los comandos `apps` solo listan y eliminan aplicaciones que BBjShip ha implementado. Las aplicaciones registradas en Enterprise Manager no se ven afectadas.

### `apps deploy` {#apps-deploy}

```text
bbjship apps deploy ARCHIVE
```

Implementa un archivo de implementación. `apps deploy` instala un archivo escrito por [`bbj-package`](/docs/configuration/bbjship/deploying#package-without-deploying), sin el punto de enlace de implementación. El nombre del archivo sin `.zip` es el [nombre de publicación](/docs/configuration/bbjship/deploying#publish-names). BBjServices lee el archivo, por lo que el usuario del sistema operativo de BBjServices necesita acceso de lectura al mismo.

```text
$ bbjship apps deploy inventory.zip
Implementado inventory /webapp/inventory
```

:::warning Reemplaza la aplicación
Implementar un archivo bajo el nombre de una aplicación que ya está implementada reemplaza esa aplicación. La carpeta de implementación anterior se elimina y las sesiones en ejecución de la aplicación finalizan. Mantenga el archivo anterior si puede necesitar volver atrás.
:::

### `apps list` {#apps-list}

```text
bbjship apps list [--long]
```

Lista las aplicaciones que BBjShip ha implementado.

| Configuración | Predeterminado | Descripción |
|---|---|---|
| `--long`, `-l` | Apagado | Agrega la carpeta de implementación y las carpetas de clase de cada aplicación. |

```text
$ bbjship apps list
NOMBRE       URL
inventory    /webapp/inventory
```

### `apps undeploy` {#apps-undeploy}

```text
bbjship apps undeploy NAME
bbjship apps undeploy --all
```

Elimina una o todas las aplicaciones que BBjShip ha implementado.

| Configuración | Predeterminado | Descripción |
|---|---|---|
| `--all` | Apagado | Desimplementa todas las aplicaciones que BBjShip ha implementado. |

:::warning No se puede deshacer
`apps undeploy` elimina la aplicación de Enterprise Manager, finaliza sus sesiones en ejecución y elimina su carpeta de implementación. `apps undeploy --all` hace esto para cada aplicación que BBjShip ha implementado. Esto no se puede deshacer. Mantenga el archivo de implementación de cada aplicación, por ejemplo `target/bbj/<nombre de publicación>.zip`, para que pueda volver a implementarla.
:::

## Desarrollo {#development}

Las ejecuciones de desarrollo están desactivadas en una nueva instalación, y la primera `webforj:watch` en la misma máquina las activa. `bbjship development disable` las desactiva hasta que la próxima `webforj:watch` en esta máquina las active nuevamente. Una ejecución de desarrollo desde otra máquina es rechazada, porque la configuración que ejecuta primero funciona solo en la máquina BBj.

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

## Restablecer {#reset}

```text
bbjship reset [--yes] [--properties FILE]
```

`reset` desimplementa cada aplicación que BBjShip ha implementado, elimina el punto de enlace de implementación y el interruptor de desarrollo, restaura el archivo de propiedades de BBjServices que una ejecución de desarrollo cambió, y elimina los archivos que BBjShip escribió. El comando nunca detiene BBjServices. Cuando restaura el archivo de propiedades, reinicie BBjServices para aplicarlo. La carpeta del plugin se mantiene.

| Configuración | Predeterminado | Descripción |
|---|---|---|
| `--yes` | Apagado | Omite la confirmación. |
| `--properties FILE` | `<BBj home>/cfg/BBj.properties` | Archivo de propiedades de BBjServices que `reset` restaura. |

El comando imprime lo que hace y solicita confirmación a menos que se dé `--yes`. En Windows, `reset` sin `--yes` termina con el código de salida `2` antes de iniciar sesión.

:::warning Haz una copia de seguridad primero
`reset` no se puede deshacer. Elimina la carpeta de cada aplicación que BBjShip ha implementado y finaliza sus sesiones en ejecución. Antes de ejecutarlo, mantenga el archivo de implementación de cada aplicación, por ejemplo, el `target/bbj/<nombre de publicación>.zip` de la construcción, y haga una copia de seguridad de `<BBj home>/cfg/BBj.properties`. Cuando se haya mantenido una copia del archivo en la carpeta de tiempo de ejecución de BBjShip, `reset` coloca esa copia sobre `cfg/BBj.properties`, por lo que cualquier cambio que haya realizado en el archivo después de la primera ejecución de desarrollo se pierde. Reinicie BBjServices después para aplicar los ajustes de Java restaurados.
:::

## Códigos de salida {#exit-codes}

| Código | Significado |
|---|---|
| `0` | El comando tuvo éxito. |
| `1` | El comando falló e imprimió la causa, o `reset` fue cancelado. |
| `2` | Los argumentos son inválidos, o el comando necesita entrada estándar en Windows. El mensaje muestra qué especificar. |

[Solución de problemas](/docs/configuration/bbjship/troubleshooting#command-line) enumera los mensajes y qué hacer en cada caso.
