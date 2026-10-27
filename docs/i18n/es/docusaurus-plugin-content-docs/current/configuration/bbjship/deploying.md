---
title: Implementación
sidebar_position: 5
description: >-
  Package, deploy and undeploy webforJ apps in BBjServices with the bbj-package,
  bbj-deploy and bbj-undeploy goals of the webforJ Maven plugin.
_i18n_hash: 19fc6feafc7d9e17580d710ced7b962b
---
# Despliegue de <DocChip chip='since' label='26.03' /> {#deploying}

El plugin Maven webforJ despliega una aplicación en [BBjShip](/docs/configuration/bbjship/overview) con tres objetivos:

| Objetivo | Se ejecuta | Qué hace |
|---|---|---|
| `bbj-package` | durante la fase `package` | Genera el archivo de despliegue `target/bbj/<nombre de publicación>.zip` |
| `bbj-deploy` | durante la fase `install` | Genera el archivo y lo despliega en BBjShip |
| `bbj-undeploy` | manualmente | Elimina la aplicación de BBjServices |

Las fases se aplican cuando el plugin se declara con `<extensions>true</extensions>` y un bloque `<bbj>`, ver [Configurando un proyecto](/docs/configuration/bbjship/existing-projects).

## Desplegar {#deploy}

```bash
mvn install
```

Sin la extensión, ejecuta `mvn package webforj:bbj-deploy`. El objetivo imprime la dirección de la aplicación cuando el despliegue se completa:

```text
[INFO] BBj deployment of inventory completed at http://localhost:8888/webapp/inventory
```

Cuando un despliegue falla, la versión anterior sigue funcionando.

:::warning Reemplaza la aplicación
Un despliegue reemplaza la aplicación que ya tiene el mismo nombre de publicación. Se elimina la carpeta de despliegue anterior y las sesiones en ejecución de esa aplicación terminan. Mantén el archivo de la versión anterior si necesitas volver a ella.
:::

[Resolución de problemas](/docs/configuration/bbjship/troubleshooting#deploy-and-undeploy) enumera los mensajes de un despliegue fallido y qué hacer con cada uno.

## Paquetear sin desplegar {#package-without-deploying}

`bbj-package` genera el mismo archivo sin una URL de despliegue ni credenciales. Un trabajo de CI puede adjuntar `target/bbj/<nombre de publicación>.zip` a la construcción, y un administrador de BBj lo despliega en la máquina BBj usando [`bbjship apps deploy`](/docs/configuration/bbjship/command-line#apps).

## Desplegar {#undeploy}

```bash
mvn webforj:bbj-undeploy
```

La aplicación se detiene y se elimina del Enterprise Manager, junto con sus archivos.

:::warning No se puede deshacer
`bbj-undeploy` termina las sesiones en ejecución de la aplicación y elimina su carpeta de despliegue. Esto no se puede deshacer. Mantén el archivo de `target/bbj/<nombre de publicación>.zip` si planeas desplegar la aplicación nuevamente.
:::

## Nombres de publicación {#publish-names}

El nombre de publicación se establece de forma predeterminada como el nombre final de la construcción y aparece en la dirección de la aplicación. Comienza con una letra o un dígito, solo contiene letras, dígitos, `_`, `.` y `-`, y no termina con un punto. Nombres reservados, como las rutas de BBjServices `webapp` y `files` o el nombre del dispositivo de Windows `con`, son rechazados antes de que se genere el archivo.

Cada clase de entrada pertenece a una aplicación desplegada. Desplegarla bajo un segundo nombre falla con:

```text
BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Despliegue desde otra máquina {#deploying-from-another-machine}

Un despliegue desde otra máquina, como un servidor CI, utiliza un token de autorización a través de HTTPS:

1. En la máquina BBj, [habilita el endpoint](/docs/configuration/bbjship/installing#enabling-the-deployment-endpoint) con un puerto SSL.
2. En Enterprise Manager, crea un token bajo Seguridad, Tokens de Autenticación. Renueva y revoca allí también.
3. Pasa el token a la construcción:

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=https://bbj.example.com:8443/webforj-bbjship \
  -Dwebforj.bbj.admin.token="$BBJ_ADMIN_TOKEN"
```

El certificado del servidor web de BBjServices debe coincidir con el nombre de host de la URL, o estar en el almacén de confianza de Java de la construcción. Java no confía en el certificado predeterminado de BBj, que es autofirmado. Una URL `http://` a otra máquina envía las credenciales legibles a través de la red, y los objetivos imprimen una advertencia al respecto.

## Configuración de la aplicación {#app-settings}

BBjShip lee el `webforj.conf` de la aplicación en cada despliegue y aplica estas propiedades a la aplicación en Enterprise Manager. Un redepliegue recoge un valor cambiado. Una propiedad que no está establecida mantiene el valor predeterminado de BBjServices.

```hocon title="webforj.conf"
webforj.assetsSource = packaged
webforj.disallowConsole = true
webforj.programArguments = ["mode=demo", "region eu"]
```

| Propiedad | Predeterminado | Descripción |
|---|---|---|
| `webforj.assetsSource` | No establecido | `packaged` sirve los archivos estáticos `ws://` y los iconos `icons://` de la aplicación, en `/webforj/<nombre de publicación>/`. Sin él, una aplicación que utiliza `icons://` no puede iniciar. |
| `webforj.showClientConfirmationDialog` | `false` | Si el navegador solicita confirmación antes de cerrar la página. |
| `webforj.disallowConsole` | `true` | Si se prohíbe la consola en la aplicación. |
| `webforj.secure` | Predeterminado de BBjServices | Si la aplicación se ejecuta a través de una conexión segura. |
| `webforj.configFile` | Predeterminado de BBjServices | Ruta del archivo de configuración de BBj que la aplicación utiliza, como lo lee BBjServices en la máquina BBj. La ruta se pasa tal cual, por lo que puede nombrar un archivo que solo tiene el servidor. |
| `webforj.programArguments` | Ninguno | Argumentos del programa de la aplicación, como una lista de cadenas o como una sola cadena. |

Un `webforj.conf` inválido falla el despliegue con HTTP 400 y el motivo.

## Configuraciones {#settings}

Las configuraciones residen en el bloque `<bbj>` de la configuración del `webforj-maven-plugin`, como se muestra en [Construir plugin](/docs/configuration/bbjship/existing-projects#the-build-plugin). Cada propiedad anula su elemento en la línea de comandos.

| Elemento de Maven | Propiedad de Maven | Predeterminado | Descripción |
|---|---|---|---|
| `<home>` | `webforj.bbj.home` | Detectado | Carpeta de instalación de BBj, utilizada por `bbj-setup` y `watch`. |
| `<publishName>` | `webforj.bbj.publishName` | Nombre final de la construcción | Nombre bajo el cual se publica la aplicación, ver [Nombres de publicación](#publish-names). |
| `<deployment><url>` | `webforj.bbj.deployment.url` | Ninguno, requerido | Dirección de BBjShip, `http://<host>:<HTTP port>/webforj-bbjship` o `https://<host>:<SSL port>/webforj-bbjship`. |
| `<admin><username>` | `webforj.bbj.admin.username` | `admin` cuando tanto el nombre de usuario como la contraseña están ausentes | Nombre de usuario del administrador de BBj. |
| `<admin><password>` | `webforj.bbj.admin.password` | `admin123` cuando tanto el nombre de usuario como la contraseña están ausentes | Contraseña del administrador de BBj. |
| `<admin><token>` | `webforj.bbj.admin.token` | Vacío | Token de autorización para `bbj-deploy` y `bbj-undeploy`. Tiene prioridad sobre el nombre de usuario y la contraseña. |
| `<properties>` | `webforj.bbj.properties` | `<home>/cfg/BBj.properties` | Archivo de propiedades de BBjServices donde un [ejecución en desarrollo](/docs/configuration/deploy-reload/bbjservices) escribe la configuración de hotswap. |
| `<development><classFolder>` | — | Directorio de salida de la construcción | Carpeta de clases de una ejecución en desarrollo como lo lee BBjServices. |
| — | `webforj.bbj.force` | `false` | Permite a `bbj-setup` reemplazar una BBjShip instalada de otra versión. Solo propiedad. |

Un nombre de usuario sin contraseña, o una contraseña sin nombre de usuario, detiene el objetivo.
