---
title: Solución de problemas
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: c517c84b00b3b7eb6bb9d485c528c4f7
---
# Solución de problemas <DocChip chip='since' label='26.03' /> {#troubleshooting}

Esta página enumera los mensajes que pueden aparecer al configurar BBjShip, implementar una aplicación, iniciar una ejecución de desarrollo o usar la línea de comandos `bbjship`. Para cada mensaje, indica la causa y qué hacer.

Un objetivo de Maven que falla detiene la compilación con un mensaje breve que identifica el objetivo, seguido del motivo. El motivo es la parte que se enumera en esta página:

```text
BBj deployment failed. Check the application package, dependencies and deployment settings: BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Credenciales y conexión {#credentials-and-connection}

Estos mensajes pueden aparecer en cualquier objetivo que se comunique con BBjShip: `bbj-setup`, `bbj-deploy`, `bbj-undeploy` y `watch`.

| Mensaje | Causa | Qué hacer |
|---|---|---|
| `Warning: the BBj factory administrator account admin is in use with its factory password...` | No se configuró ni un nombre de usuario ni una contraseña, por lo que el objetivo inicia sesión como `admin` con `admin123`. | Cambia la contraseña en Enterprise Manager y configura las nuevas credenciales; consulta [Configuración](/docs/configuration/bbjship/deploying#settings). |
| `The BBj administration user name is missing`, `The BBj administration password is missing` | Solo se configuró el nombre de usuario o la contraseña. | Configura ambos o ninguno para usar la cuenta predeterminada. `bbj-deploy` y `bbj-undeploy` también aceptan un token. |
| `BBj deployment requires bbj.deployment.url...` | No se configuró una URL de implementación. | Establece `<bbj><deployment><url>` o pasa `-Dwebforj.bbj.deployment.url=<URL>`. |
| `BBj deployment URL must be an HTTP or HTTPS URL without credentials, query or fragment` | La URL usa otro esquema, o incluye un nombre de usuario, una consulta o un fragmento. | Usa `http://<host>:<port>/webforj-bbjship` o `https://<host>:<port>/webforj-bbjship`, y coloca las credenciales en `<admin>`. |
| `Connect to ... failed: Connection refused` | No hay respuesta en el host y el puerto de la URL de implementación. | Inicia BBjServices y comprueba que el host y el puerto de la URL coincidan con el servidor web de BBjServices. |
| `could not read the BBjShip version at <URL>, HTTP 404...` | La URL no conduce a BBjShip, o BBjShip no está instalado o su endpoint no está habilitado en ese puerto. | Comprueba la ruta `/webforj-bbjship` y el puerto. En la máquina de BBj, ejecuta `mvn webforj:bbj-setup` o, en un servidor, `bbjship remote enable`. |
| `failed with HTTP 401. Check the BBj administration credentials or token` | BBjServices rechazó el nombre de usuario y la contraseña, o el token. | Comprueba las credenciales. Renueva en Enterprise Manager los tokens caducados o revocados. |
| `BBjShip <version> at <URL> does not match webforJ <version>...` | Las versiones instaladas de BBjShip y `webforj-maven-plugin` son distintas. | En tu máquina, ejecuta `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. En un servidor, instala la versión de BBjShip correspondiente con BBj Plugin Manager. |
| `could not establish a trusted HTTPS connection to <URL>...` | Java no confía en el certificado del servidor web de BBjServices. | Instala en la máquina de BBj un certificado para el nombre de host de la URL, o añade su certificado al almacén de confianza de Java de la compilación. |
| `BBj deployment URL <URL> sends the administration credentials over an unencrypted connection to another machine...` | La URL de implementación usa `http://` y apunta a otra máquina. El objetivo se detiene antes de enviar nada. Con `allowUnencrypted`, el mismo texto aparece como advertencia y el objetivo continúa. | Usa una URL `https://` con el puerto SSL o permite HTTP en ambos extremos; consulta [Implementar desde otra máquina](/docs/configuration/bbjship/deploying#deploying-from-another-machine). |

## Configuración {#setup}

Estos mensajes aparecen al ejecutar `mvn webforj:bbj-setup` y durante la primera [ejecución de desarrollo](/docs/configuration/deploy-reload/bbjservices) de un proyecto, que configura BBjShip de la misma manera.

| Mensaje | Causa | Qué hacer |
|---|---|---|
| `BBj setup failed. Check the BBj plugin artifact and setup settings` | El objetivo falló. A continuación aparece el motivo. Si el motivo se refiere a la resolución de artefactos, Maven no pudo descargar `webforj-bbjship-plugin` en la versión de `webforj-maven-plugin`. | Lee el motivo. Si se refiere a la resolución de artefactos, comprueba los repositorios y el acceso a la red de la compilación. |
| `BBj setup is local only. Run setup on the BBj host` | La URL de implementación apunta a otra máquina. | Ejecuta la configuración en la máquina de BBj o instala BBjShip allí con [BBj Plugin Manager](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager). |
| `BBj setup cannot resolve the deployment URL host <host>...` | No se reconoce el nombre de host de la URL de implementación. | Usa `localhost` o el nombre de esta máquina. |
| `BBj setup requires the administration password. Configure bbj.admin.password instead of a token` | Hay un token configurado para una ejecución de desarrollo. Las ejecuciones de desarrollo solo inician sesión con un nombre de usuario y una contraseña. | Configura `<admin><username>` y `<admin><password>`. |
| `Cannot detect a BBj installation. Configure bbj.home or BBJ_HOME` | El objetivo no encontró una instalación de BBj en esta máquina. | Establece `<bbj><home>`, `-Dwebforj.bbj.home` o la variable `BBJ_HOME` en la carpeta de instalación de BBj. |
| `Invalid BBj home. Configure a local BBj installation`, `BBj executable is missing or not executable. Check bbj.home` | La carpeta configurada no contiene una instalación completa de BBj. | Configura el ajuste para que apunte a la carpeta que contiene `bin` y `cfg` de BBj. |
| `Installed BBjShip version <version> does not match <version>...` | Hay instalada otra versión de BBjShip, por ejemplo, tras actualizar webforJ. | Ejecuta `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. Las aplicaciones implementadas seguirán implementadas. |
| `Installed BBjShip version is missing...` | La carpeta de BBjShip instalada está incompleta. | Ejecuta `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. |
| `The plugin directory is a symbolic link...`, `BBj plugin destination must not contain symbolic links` | La carpeta `plugins` de BBj o la carpeta de BBjShip que contiene es un enlace simbólico o contiene uno. | Instala en una carpeta normal. |
| `BBj setup command failed. Check credentials and local BBjServices.` seguido de la salida del comando | Falló el comando que habilita el endpoint de implementación. La salida indica el motivo, por ejemplo, un inicio de sesión rechazado o un puerto en el que el servidor web no está escuchando. | Comprueba las credenciales, que BBjServices esté en ejecución y que el puerto de la URL de implementación corresponda a un puerto del servidor web de BBjServices. |
| `BBj setup command timed out. Check local BBjServices` | El comando no terminó en 120 segundos. | Comprueba que BBjServices esté en ejecución y responda; luego vuelve a ejecutar el objetivo. |
| `Previous BBjShip files are preserved at <folder>` | Falló el reemplazo y los archivos de la versión anterior se conservaron en esa carpeta. | Corrige la causa del fallo y vuelve a ejecutar el objetivo. |

## Implementar y retirar la implementación {#deploy-and-undeploy}

### Antes de la carga {#before-the-upload}

Estos mensajes aparecen en `bbj-package`, `bbj-deploy` y `bbj-undeploy` antes de que nada llegue a BBjShip.

| Mensaje | Causa | Qué hacer |
|---|---|---|
| `BBj deployment requires jar packaging, this project is packaged as <packaging>` | El proyecto no está empaquetado como un archivo JAR. | Establece `<packaging>jar</packaging>`. |
| `BBj deployment requires the packaged application jar...` | El objetivo se ejecutó sin la fase `package` en la misma compilación. | Ejecuta `mvn install` o `mvn package webforj:bbj-deploy`. |
| `BBj deployment must not carry webforj-engine, BBj is the engine...` | `webforj-engine` está en la ruta de clases de ejecución de la aplicación. | Exclúyelo de la dependencia `webforj`; consulta [Sin el complemento de instalación](/docs/configuration/bbjship/existing-projects#without-the-install-plugin). |
| `BBj deployment requires webforj-foundation on the runtime classpath` | La aplicación no tiene una dependencia de webforJ en tiempo de ejecución. | Añade la dependencia `webforj` con el alcance `compile`. |
| `BBj deployment dependency is unresolved...` | No hay ningún archivo para una dependencia de la aplicación. | Comprueba que Maven resuelva todas las dependencias del proyecto. |
| `Publish name <name> is invalid...` | El nombre de publicación infringe las reglas de [Nombres de publicación](/docs/configuration/bbjship/deploying#publish-names). | Elige otro `<publishName>`. |
| `The publish name <name> is reserved by BBjServices for its own web paths...` | BBjServices usa ese nombre para sus propias direcciones. | Elige otro `<publishName>`. |
| `BBj deployment archive could not be written...` | `bbj-package` no pudo escribir `target/bbj/<publish name>.zip`. A continuación aparece el motivo. | Lee el motivo. |

### Respuestas de BBjShip {#answers-of-bbjship}

Cuando BBjShip rechaza una implementación o su retirada, el mensaje empieza con `BBj deployment failed with HTTP <status>` o `BBj undeployment failed with HTTP <status>`, seguido del motivo.

| Estado | Motivo | Causa | Qué hacer |
|---|---|---|---|
| 400 | `Publish name <name> is invalid...` o `...is reserved by BBjServices...` | Se rechazó el nombre de publicación. | Elige otro nombre de publicación. |
| 400 | `BBj deployment webforj.conf is invalid. <detail>` | No se puede leer el archivo `webforj.conf` de la aplicación, o un [ajuste de la aplicación](/docs/configuration/bbjship/deploying#app-settings) tiene un tipo incorrecto. | Corrige el archivo según lo que indique el detalle. |
| 400 | `The uploaded body is not a BBj deployment archive...`, `Invalid BBj deployment archive entry: <entry>` u otro motivo relacionado con el archivo | El complemento de compilación no creó el archivo o este se modificó después. | Implementa el archivo creado por la compilación con la misma versión de webforJ que BBjShip. |
| 401 | `Check the BBj administration credentials or token` | Se rechazó el inicio de sesión. | Comprueba las credenciales o renueva el token. |
| 403 | `BBjShip refuses the administration credentials over an unencrypted connection from another machine. Use the SSL port of the BBjServices web server` | La solicitud llegó por HTTP desde otra máquina. | Usa la dirección HTTPS del endpoint o ejecuta `bbjship remote enable --allow-unencrypted` en la máquina de BBj. |
| 403 | `Development runs are not enabled on this BBj machine. Run bbjship development enable` | Una ejecución de desarrollo llegó a una máquina en la que las ejecuciones de desarrollo están desactivadas. | Ejecuta `bbjship development enable` en la máquina de BBj. |
| 404 | `No BBj application named <name> was deployed by BBjShip. Check the publish name` | Se intentó retirar la implementación de una aplicación que BBjShip no implementó. | Comprueba el nombre de publicación con `bbjship apps list`. |
| 409 | `Entry class <class> is already published as <name>. Undeploy <name> first` | La clase de entrada de la aplicación ya se ejecuta con otro nombre de publicación. | Retira la implementación de la otra aplicación o implementa con su nombre. |
| 409 | `Published BBj application <name> is not owned by BBjShip...` | Una aplicación registrada en Enterprise Manager ya usa ese nombre. | Elige otro nombre de publicación o elimina esa aplicación en Enterprise Manager. |
| 409 | `Published BBj application <other> differs from <name> only by letter case...` | Otra aplicación usa el mismo nombre, pero con mayúsculas o minúsculas distintas. | Elige otro nombre de publicación. |
| 409 | `<name> is a development run. Stop the watch first` | Hay una ejecución de desarrollo activa para la aplicación. | Detén el modo de observación con `Ctrl+C` y luego implementa la aplicación. |
| 409 | `<name> is deployed from an archive. Undeploy it first` | Una ejecución de desarrollo usa el nombre de una aplicación implementada. | Retira la implementación de la aplicación o establece otro `<publishName>` para la ejecución de desarrollo. |
| 413 | `BBj deployment archive exceeds the allowed size of 1 GiB` | El archivo supera el tamaño que BBjShip acepta. | Elimina las dependencias que la aplicación no necesita en tiempo de ejecución. |
| 500 | `BBj deployment failed. Check the BBjServices log` | BBjServices falló al instalar o quitar la aplicación. | Lee los registros de BBjServices. |

Una implementación que termina con `did not confirm the requested application. Check the BBjShip version and BBjServices log` llegó a BBjShip, pero no recibió confirmación de la aplicación. Comprueba que BBjShip y `webforj-maven-plugin` tengan la misma versión y luego lee el registro de BBjServices.

### La aplicación en ejecución {#the-running-app}

| Mensaje | Causa | Qué hacer |
|---|---|---|
| `Icons protocol requires webforj.assetsSource = packaged when running with BBj Services` | La aplicación usa `icons://` sin recursos empaquetados. | Añade `webforj.assetsSource = packaged` a `webforj.conf` y vuelve a implementar la aplicación. |

## Ejecuciones de desarrollo {#development-runs}

Estos mensajes aparecen al ejecutar `mvn` o `mvn compile webforj:watch` en un proyecto configurado para BBjShip. Una ejecución de desarrollo fallida empieza con `BBj development run failed. Check the deployment settings and that development runs are enabled on the BBj machine`, seguido del motivo indicado en [Configuración](#setup), [Credenciales y conexión](#credentials-and-connection) o [Respuestas de BBjShip](#answers-of-bbjship).

| Mensaje | Causa | Qué hacer |
|---|---|---|
| `The class folder <folder> does not exist. Run compile before webforj:watch` | El modo de observación se inició antes de compilar el proyecto. | Ejecuta `mvn compile webforj:watch` o `mvn` en un proyecto de arquetipo. |
| `BBj development class folder must be an absolute directory on the BBj machine` | `<development><classFolder>` indica una carpeta que BBjServices no encuentra. | Establece la ruta absoluta de la carpeta de clases en la máquina de BBj. |
| `BBjServices properties file not found at <file>. Configure bbj.properties` | El archivo de propiedades de BBjServices no está en la ubicación donde la ejecución lo busca. | Establece `<bbj><properties>` o `-Dwebforj.bbj.properties` para indicar el archivo. |
| `BBjServices did not stop within 180 seconds` | La ejecución pidió a BBjServices que se detuviera para aplicar los ajustes de hotswap, pero siguió en ejecución. | Detén BBjServices manualmente, vuelve a iniciarlo y ejecuta de nuevo el modo de observación. |
| `BBjServices start program is missing: <program>` | La instalación de BBj no incluye un programa para iniciar BBjServices. | Inicia BBjServices manualmente y vuelve a ejecutar el modo de observación. |
| `BBjServices did not start. The program ended with exit code <code>`, `BBjServices did not start within 120 seconds` | BBjServices no volvió a iniciarse tras el reinicio. | Inicia BBjServices manualmente y vuelve a ejecutar el modo de observación. |
| `BBjShip did not answer within 120 seconds` | BBjServices se inició, pero el endpoint de implementación no respondió. | Comprueba el registro de BBjServices y vuelve a ejecutar el modo de observación. |
| `BBjServices restarted without the hotswap settings. Check <file>` | BBjServices se inició con argumentos de Java distintos de los escritos en el archivo de propiedades. | Comprueba los ajustes de Java de BBjServices en Enterprise Manager y en ese archivo. |
| `webforJ watch: development run of <name> was not stopped. <reason>` | No se pudo quitar la aplicación cuando se detuvo la compilación. | Quítala con `bbjship apps undeploy <name>` o vuelve a iniciar el modo de observación, que la reemplazará. |

La primera ejecución de desarrollo que añade los ajustes de hotswap reinicia BBjServices. En macOS y Linux, inicia BBjServices con `bin/bbjservices`. En Windows, inicia el servicio de Windows `BBjServices` si está registrado; de lo contrario, ejecuta `bin\BBjServices.exe -noservice`. Si se inicia con `bin/bbjservices` o con `-noservice`, BBjServices se ejecuta fuera del administrador de servicios del sistema operativo hasta que se reinicie la máquina.

## Línea de comandos {#command-line}

Todos los comandos `bbjship` se ejecutan dentro de BBjServices, por lo que BBjServices debe estar en ejecución. Si no lo está, el comando no puede iniciarse.

Los argumentos no válidos terminan con el código de salida `2` y un mensaje que indica qué se debe especificar:

| Mensaje | Qué hacer |
|---|---|
| `Specify a command. Use --help for available commands`, `Unknown command <command>...` | Indica un comando; consulta [Línea de comandos](/docs/configuration/bbjship/command-line). |
| `Unknown option <option>...`, `Option <option> is not available for <command>...` | Usa una opción válida para ese comando, tal como se indica en `--help`. |
| `Specify a user name after --username`, `Specify a password after --password`, `Specify a port after --port` | Asigna un valor a la opción. |
| `Specify --port from 1 to 65535`, `Specify --ssl-port from 1 to 65535` | Indica un número de puerto. |
| `Specify <option> only once` | Elimina la opción repetida. |
| `Specify only one of --password and --password-stdin` | Conserva solo una de las dos opciones. |
| `The BBj administration password is missing. Specify --username and --password <value> or --password-stdin, or set WEBFORJ_BBJ_USERNAME and WEBFORJ_BBJ_PASSWORD` | Indica el nombre de usuario y la contraseña juntos. El mensaje indica cuál falta. |
| `Specify an application name or --all`, `Specify one application name` | Proporciona un nombre de publicación a `apps undeploy` o usa `--all`. |
| `Specify the path of a deployment archive ending in .zip`, `Specify one deployment archive` | Proporciona un archivo `.zip` a `apps deploy`. |
| `Deployment archive <path> does not exist`, `Deployment archive <path> cannot be read` | Comprueba la ruta. BBjServices lee el archivo, por lo que el usuario del sistema operativo que ejecuta BBjServices necesita permiso de lectura. |
| `Development archives are deployed by the watch, not by apps deploy` | Implementa el archivo de `bbj-package`, no el de una ejecución de desarrollo. |
| `Publish name <name> is invalid...`, `The publish name <name> is reserved...` | Cambia el nombre del archivo. Su nombre sin `.zip` es el nombre de publicación. |

Un comando que falla termina con el código de salida `1` y el mensaje `Command failed.`, seguido del motivo:

| Motivo | Causa | Qué hacer |
|---|---|---|
| Un error de inicio de sesión de BBjServices | El nombre de usuario o la contraseña son incorrectos. | Comprueba las credenciales. |
| `The BBjServices web server does not answer on port <port>. Use the HTTP port of the BBjServices web server` | `--port` o `--ssl-port` no corresponde a un puerto en el que escuche el servidor web de BBjServices. El endpoint anterior permanece. | Usa los puertos configurados para el servidor web en Enterprise Manager. |
| `Application <name> is not deployed by BBjShip` | `apps undeploy` indica una aplicación que BBjShip no implementó. | Comprueba el nombre con `bbjship apps list`. |
| `Another deployment is in progress` | Otra implementación o retirada de implementación ocupó la máquina durante 30 segundos. | Espera a que termine y vuelve a ejecutar el comando. |
| `The BBjShip context belongs to another application`, `The assets context belongs to another application` | Otra aplicación de Enterprise Manager usa el contexto web `webforj-bbjship` o `webforj-bbjship-assets`. | Cambia el nombre de ese contexto o elimínalo en Enterprise Manager. |
| `The BBjShip command classpath belongs to another application`, `The BBjShip base classpath contains an unowned library` | Se modificaron las rutas de clases `_webforj_bbjship_cli` o `_webforj_bbjship` en Enterprise Manager. | Elimina ambas rutas de clases en Enterprise Manager y vuelve a ejecutar el comando para que las cree de nuevo. |
| `The BBjShip servlet is missing. Check the plugin installation`, `The BBjShip library is missing. Check the plugin installation` | La carpeta de BBjShip está incompleta. | Vuelve a instalar BBjShip. |
| `BBjServices does not manage web contexts. Update BBj` | La versión de BBj es demasiado antigua para BBjShip. | Instala la versión de BBj que corresponda a webforJ. |

`reset` termina con el código de salida `1` y `Reset cancelled` si la respuesta a `Continue? [y/N]` no es `y` ni `Y`. Si restaura los ajustes de Java de BBjServices, termina con `Restart BBjServices now to apply the restored Java settings`. Reinicia BBjServices para ejecutarlo sin los ajustes de hotswap.

## Windows {#windows}

| Situación | Qué hacer |
|---|---|
| Al ejecutar `bbj.exe` con el programa de BBjShip no se muestra nada. | Ejecuta `bbjship.cmd` en `<BBj home>\plugins\webforj-bbjship`, que muestra la salida del comando. |
| `--password-stdin` termina con el código de salida `2` y `Standard input is not available on Windows. Use --password <value> or WEBFORJ_BBJ_PASSWORD`. | Pasa la contraseña con `--password` o establece `WEBFORJ_BBJ_USERNAME` y `WEBFORJ_BBJ_PASSWORD`. |
| `reset` termina con el código de salida `2` y `Standard input is not available on Windows. Run reset --yes to confirm`. | Ejecuta `bbjship.cmd reset --yes`. |
| `apps undeploy` o `reset` muestra `Not removed: <folder>. <reason>` y termina con el código de salida `0`. | BBjServices aún tiene abiertos archivos de la aplicación. La aplicación se elimina de Enterprise Manager. Reinicia BBjServices. La carpeta se eliminará con la siguiente implementación de ese nombre; también puedes eliminarla después del reinicio. |
| La instalación de BBjShip sobre una versión instalada falla porque sus archivos están en uso. | BBjServices aún tiene abiertos los archivos de BBjShip instalado. Reinicia BBjServices y vuelve a instalarlo. |
| BBjServices no se ejecuta como servicio de Windows después de que lo reinicie una ejecución de desarrollo. | La ejecución de desarrollo lo inició con `BBjServices.exe -noservice` porque no hay ningún servicio `BBjServices` registrado. Se ejecuta fuera del administrador de servicios de Windows hasta que se reinicie la máquina. |
| No se puede iniciar un comando `bbjship.cmd`. | Inicia BBjServices. Todos los comandos `bbjship` necesitan que esté en ejecución. |
