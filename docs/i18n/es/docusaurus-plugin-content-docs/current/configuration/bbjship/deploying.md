---
title: Despliegue
sidebar_position: 5
description: >-
  Package, deploy and undeploy webforJ apps in BBjServices with the bbj-package,
  bbj-deploy and bbj-undeploy goals of the webforJ Maven plugin.
_i18n_hash: 77099210b317740da114b7881490bd09
---
# Implementación <DocChip chip='since' label='26.03' /> {#deploying}

El complemento Maven de webforJ implementa una aplicación en [BBjShip](/docs/configuration/bbjship/overview) con tres objetivos:

| Objetivo | Se ejecuta | Qué hace |
|---|---|---|
| `bbj-package` | con la fase `package` | Escribe el archivo de implementación `target/bbj/<publish name>.zip` |
| `bbj-deploy` | con la fase `install` | Escribe el archivo e implementa la aplicación en BBjShip |
| `bbj-undeploy` | manualmente | Elimina la aplicación de BBjServices |

Las fases se aplican cuando el complemento se declara con `<extensions>true</extensions>` y un bloque `<bbj>`. Consulta [Configurar un proyecto](/docs/configuration/bbjship/existing-projects).

## Implementar {#deploy}

```bash
mvn install
```

Sin la extensión, ejecuta `mvn package webforj:bbj-deploy`. Cuando finaliza la implementación, el objetivo muestra la dirección de la aplicación:

```text
[INFO] BBj deployment of inventory completed at http://localhost:8888/webapp/inventory
```

Si la implementación falla, la versión anterior sigue ejecutándose.

:::warning Reemplaza la aplicación
Una implementación reemplaza la aplicación que ya tiene el mismo nombre de publicación. Se elimina la carpeta de la implementación anterior y se cierran las sesiones activas de esa aplicación. Conserva el archivo de la versión anterior por si necesitas volver a ella.
:::

[Solución de problemas](/docs/configuration/bbjship/troubleshooting#deploy-and-undeploy) enumera los mensajes de una implementación fallida y qué hacer en cada caso.

## Empaquetar sin implementar {#package-without-deploying}

`bbj-package` escribe el mismo archivo sin una URL de implementación ni credenciales. Un trabajo de CI puede adjuntar `target/bbj/<publish name>.zip` a la compilación, y un administrador de BBj puede implementarlo en la máquina BBj con [`bbjship apps deploy`](/docs/configuration/bbjship/command-line#apps).

## Retirar la implementación {#undeploy}

```bash
mvn webforj:bbj-undeploy
```

La aplicación se detiene y se elimina de Enterprise Manager, junto con sus archivos.

:::warning No se puede deshacer
`bbj-undeploy` cierra las sesiones activas de la aplicación y elimina su carpeta de implementación. Esta acción no se puede deshacer. Conserva el archivo de `target/bbj/<publish name>.zip` por si quieres volver a implementar la aplicación.
:::

## Nombres de publicación {#publish-names}

De forma predeterminada, el nombre de publicación es el nombre final de la compilación y aparece en la dirección de la aplicación. Empieza con una letra o un dígito, solo contiene letras, dígitos, `_`, `.` y `-`, y no termina en punto. Los nombres reservados, como las rutas de BBjServices `webapp` y `files`, o el nombre de dispositivo de Windows `con`, se rechazan antes de escribir el archivo.

Cada clase de entrada pertenece a una sola aplicación implementada. Si se intenta implementarla con un segundo nombre, se produce un error:

```text
BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Implementar desde otra máquina {#deploying-from-another-machine}

Para implementar desde otra máquina, como un servidor de CI, se usa un token de autorización a través de HTTPS:

1. En la máquina BBj, [habilita el punto de conexión](/docs/configuration/bbjship/installing#enabling-the-deployment-endpoint) con un puerto SSL.
2. En Enterprise Manager, crea un token en Security, Authentication Tokens. También puedes renovarlo y revocarlo allí.
3. Pasa el token a la compilación:

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=https://bbj.example.com:8443/webforj-bbjship \
  -Dwebforj.bbj.admin.token="$BBJ_ADMIN_TOKEN"
```

El certificado del servidor web de BBjServices debe coincidir con el nombre de host de la URL o estar en el almacén de confianza de Java de la compilación. Java no confía en el certificado predeterminado de BBj, que está autofirmado.

Se rechaza una URL `http://` a otra máquina en ambos extremos. El objetivo se detiene antes de enviar nada, y BBjShip responde con HTTP 403 a las solicitudes que recibe por HTTP desde otra máquina. Una URL que identifica la propia máquina BBj —`localhost` o una de sus direcciones— funciona mediante HTTP.

Para implementar mediante HTTP en otra máquina de todos modos, por ejemplo, en una máquina virtual de tu propio equipo, habilítalo en ambos extremos. En la máquina BBj, ejecuta `bbjship remote enable --allow-unencrypted` con los puertos del punto de conexión. En la compilación, establece `<deployment><allowUnencrypted>true</allowUnencrypted>` o pasa la propiedad:

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=http://bbj.example.com:8888/webforj-bbjship \
  -Dwebforj.bbj.deployment.allowUnencrypted=true
```

En ese caso, el objetivo muestra una advertencia y realiza la implementación. Las credenciales se transmiten en texto legible por la red a través de esa conexión.

## Configuración de la aplicación {#app-settings}

BBjShip lee el archivo `webforj.conf` de la aplicación en cada implementación y aplica estas propiedades a la aplicación en Enterprise Manager. Una nueva implementación recoge los valores modificados. Las propiedades que no están establecidas conservan los valores predeterminados de BBjServices.

```hocon title="webforj.conf"
webforj.assetsSource = packaged
webforj.disallowConsole = true
webforj.programArguments = ["mode=demo", "region eu"]
```

| Propiedad | Valor predeterminado | Descripción |
|---|---|---|
| `webforj.assetsSource` | Sin establecer | `packaged` sirve los archivos estáticos `ws://` y los iconos `icons://` desde la aplicación, en `/webforj/<publish name>/`. Sin esta propiedad, una aplicación que use `icons://` no se inicia. |
| `webforj.showClientConfirmationDialog` | `false` | Indica si el navegador pide confirmación antes de cerrar la página. |
| `webforj.disallowConsole` | `true` | Indica si la consola está deshabilitada en la aplicación. |
| `webforj.secure` | Valor predeterminado de BBjServices | Indica si la aplicación se ejecuta mediante una conexión segura. |
| `webforj.configFile` | Valor predeterminado de BBjServices | Ruta del archivo de configuración de BBj con el que se ejecuta la aplicación, tal como lo lee BBjServices en la máquina BBj. La ruta se pasa tal cual, por lo que puede indicar un archivo que solo exista en el servidor. |
| `webforj.programArguments` | Ninguno | Argumentos del programa de la aplicación, como una lista de cadenas o como una sola cadena. |

Si `webforj.conf` no es válido, la implementación falla con HTTP 400 y el motivo correspondiente.

## Configuración {#settings}

La configuración se encuentra en el bloque `<bbj>` de la configuración de `webforj-maven-plugin`, como se muestra en [Complemento de compilación](/docs/configuration/bbjship/existing-projects#the-build-plugin). Cada propiedad sustituye el elemento correspondiente en la línea de comandos.

| Elemento Maven | Propiedad Maven | Valor predeterminado | Descripción |
|---|---|---|---|
| `<home>` | `webforj.bbj.home` | Detectado | Carpeta de instalación de BBj, utilizada por `bbj-setup` y `watch`. |
| `<publishName>` | `webforj.bbj.publishName` | Nombre final de la compilación | Nombre con el que se publica la aplicación; consulta [Nombres de publicación](#publish-names). |
| `<deployment><url>` | `webforj.bbj.deployment.url` | Ninguno, obligatorio | Dirección de BBjShip: `http://<host>:<HTTP port>/webforj-bbjship` o `https://<host>:<SSL port>/webforj-bbjship`. |
| `<deployment><allowUnencrypted>` | `webforj.bbj.deployment.allowUnencrypted` | `false` | Permite que `bbj-deploy` y `bbj-undeploy` envíen las credenciales mediante una URL `http://` a otra máquina; consulta [Implementar desde otra máquina](#deploying-from-another-machine). |
| `<admin><username>` | `webforj.bbj.admin.username` | `admin` cuando no se especifican ni el nombre de usuario ni la contraseña | Nombre de usuario del administrador de BBj. |
| `<admin><password>` | `webforj.bbj.admin.password` | `admin123` cuando no se especifican ni el nombre de usuario ni la contraseña | Contraseña del administrador de BBj. |
| `<admin><token>` | `webforj.bbj.admin.token` | Vacío | Token de autorización para `bbj-deploy` y `bbj-undeploy`. Tiene prioridad sobre el nombre de usuario y la contraseña. |
| `<properties>` | `webforj.bbj.properties` | `<home>/cfg/BBj.properties` | Archivo de propiedades de BBjServices en el que una [ejecución de desarrollo](/docs/configuration/deploy-reload/bbjservices) escribe la configuración de intercambio en caliente. |
| `<development><classFolder>` | — | Directorio de salida de la compilación | Carpeta de clases de una ejecución de desarrollo, tal como la lee BBjServices. |
| — | `webforj.bbj.force` | `false` | Permite que `bbj-setup` sustituya una instalación de BBjShip de otra versión. Solo es una propiedad. |

Si se especifica un nombre de usuario sin contraseña o una contraseña sin nombre de usuario, el objetivo se detiene.
