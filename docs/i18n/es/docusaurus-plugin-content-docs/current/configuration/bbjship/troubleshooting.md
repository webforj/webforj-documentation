---
title: Solución de problemas
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: d9235c64dc2d56c5acc41e60b2a2f7df
---
# Solución de problemas <DocChip chip='since' label='26.03' /> {#troubleshooting}

Esta página enumera los mensajes que puedes encontrar al configurar BBjShip, desplegar una aplicación, iniciar una ejecución de desarrollo o usar la línea de comandos `bbjship`. Para cada mensaje, proporciona la causa y qué hacer.

Un objetivo de Maven fallido finaliza la construcción con un mensaje breve del objetivo, seguido de la razón. La razón es la parte que esta página enumera:

```text
El despliegue de BBj falló. Verifica el paquete de la aplicación, las dependencias y los ajustes de despliegue: El despliegue de BBj falló con HTTP 409. La clase de entrada com.example.Application ya está publicada como inventario. Despliega el inventario primero.
```

## Credenciales y conexión {#credentials-and-connection}

Estos mensajes provienen de cada objetivo que interactúa con BBjShip: `bbj-setup`, `bbj-deploy`, `bbj-undeploy` y `watch`.

| Mensaje | Causa | Qué hacer |
|---|---|---|
| `Advertencia: la cuenta de administrador de fábrica de BBj admin está en uso con su contraseña de fábrica...` | Ni un nombre de usuario ni una contraseña están configurados, por lo que el objetivo inicia sesión como `admin` con `admin123`. | Cambia la contraseña en el Enterprise Manager y configura las nuevas credenciales, consulta [Configuraciones](/docs/configuration/bbjship/deploying#settings). |
| `Falta el nombre de usuario de administración de BBj`, `Falta la contraseña de administración de BBj` | Solo uno del nombre de usuario y la contraseña está configurado. | Configura ambos, o ninguno para usar la cuenta de fábrica. `bbj-deploy` y `bbj-undeploy` también aceptan un token. |
| `El despliegue de BBj requiere bbj.deployment.url...` | No se ha configurado ninguna URL de despliegue. | Establece `<bbj><deployment><url>` o pasa `-Dwebforj.bbj.deployment.url=<URL>`. |
| `La URL de despliegue de BBj debe ser una URL HTTP o HTTPS sin credenciales, consulta o fragmento` | La URL tiene otro esquema, o lleva un nombre de usuario, una consulta o un fragmento. | Usa `http://<host>:<port>/webforj-bbjship` o `https://<host>:<port>/webforj-bbjship`, y coloca las credenciales en `<admin>`. |
| `Conexión a ... fallida: Conexión rechazada` | Nada responde en el host y el puerto de la URL de despliegue. | Inicia BBjServices y verifica el host y el puerto de la URL contra el servidor web de BBjServices. |
| `no se pudo leer la versión de BBjShip en <URL>, HTTP 404...` | La URL no lleva a BBjShip, o BBjShip no está instalado o su punto final no está habilitado en ese puerto. | Verifica la ruta `/webforj-bbjship` y el puerto. En la máquina BBj, ejecuta `mvn webforj:bbj-setup`, o `bbjship remote enable` en un servidor. |
| `falló con HTTP 401. Verifica las credenciales de administración de BBj o el token` | BBjServices rechazó el nombre de usuario y la contraseña, o el token. | Verifica las credenciales. Renueva un token que expiró o fue revocado en el Enterprise Manager. |
| `La versión de BBjShip <version> en <URL> no coincide con la versión de webforJ <version>...` | Las versiones instaladas de BBjShip y `webforj-maven-plugin` son diferentes. | En tu propia máquina, ejecuta `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. En un servidor, instala la versión correspondiente de BBjShip con el Administrador de Plugins BBj. |
| `no se pudo establecer una conexión HTTPS de confianza a <URL>...` | Java no confía en el certificado del servidor web de BBjServices. | Instala un certificado para el nombre de host de la URL en la máquina BBj, o agrega su certificado al almacén de confianza de Java de la construcción. |
| `La URL de despliegue de BBj <URL> envía las credenciales de administración legibles a través de la red...` | Una advertencia para una URL `http://` a otra máquina. | Utiliza una URL `https://` con el puerto SSL, consulta [Desplegando desde otra máquina](/docs/configuration/bbjship/deploying#deploying-from-another-machine). |

## Configuración {#setup}

Estos mensajes provienen de `mvn webforj:bbj-setup` y de la primera [ejecución de desarrollo](/docs/configuration/deploy-reload/bbjservices) de un proyecto, que configura BBjShip de la misma manera.

| Mensaje | Causa | Qué hacer |
|---|---|---|
| `La configuración de BBj falló. Verifica el artefacto del plugin de BBj y los ajustes de configuración` | El objetivo falló. La razón sigue a este. Una razón sobre la resolución de artefactos significa que Maven no pudo descargar `webforj-bbjship-plugin` en la versión del `webforj-maven-plugin`. | Lee la razón. Para la resolución de artefactos, verifica los repositorios y el acceso a la red de la construcción. |
| `La configuración de BBj es solo local. Ejecuta la configuración en el host de BBj` | La URL de despliegue nombra otra máquina. | Ejecuta la configuración en la máquina BBj, o instala BBjShip allí con el [Administrador de Plugins BBj](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager). |
| `La configuración de BBj no puede resolver el host de la URL de despliegue <host>...` | El nombre de host de la URL de despliegue es desconocido. | Usa `localhost` o un nombre de esta máquina. |
| `La configuración de BBj requiere la contraseña de administración. Configura bbj.admin.password en lugar de un token` | Una ejecución de desarrollo tiene un token configurado. Las ejecuciones de desarrollo inician sesión solo con un nombre de usuario y contraseña. | Configura `<admin><username>` y `<admin><password>`. |
| `No se puede detectar una instalación de BBj. Configura bbj.home o BBJ_HOME` | El objetivo no encontró ninguna instalación de BBj en esta máquina. | Establece `<bbj><home>`, `-Dwebforj.bbj.home` o la variable `BBJ_HOME` en la carpeta de instalación de BBj. |
| `Dirección de BBj no válida. Configura una instalación local de BBj`, `El ejecutable de BBj está ausente o no es ejecutable. Verifica bbj.home` | La carpeta configurada no es una instalación completa de BBj. | Apunta la configuración a la carpeta que contiene `bin` y `cfg` de BBj. |
| `La versión instalada de BBjShip <version> no coincide con <version>...` | Se instaló otra versión de BBjShip, por ejemplo después de una actualización de webforJ. | Ejecuta `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. Las aplicaciones desplegadas permanecen desplegadas. |
| `La versión instalada de BBjShip está ausente...` | La carpeta instalada de BBjShip está incompleta. | Ejecuta `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. |
| `El directorio del plugin es un enlace simbólico...`, `El destino del plugin de BBj no debe contener enlaces simbólicos` | La carpeta `plugins` de BBj, o la carpeta de BBjShip en ella, es un enlace simbólico o contiene uno. | Instala en una carpeta regular. |
| `El comando de configuración de BBj falló. Verifica las credenciales y los BBjServices locales.` seguido de la salida del comando | El comando que habilita el punto final de despliegue falló. La salida nombra la razón, por ejemplo, un inicio de sesión rechazado o un puerto en el que el servidor web no escucha. | Verifica las credenciales, que BBjServices se ejecute y que el puerto de la URL de despliegue sea un puerto del servidor web de BBjServices. |
| `El comando de configuración de BBj excedió el tiempo de espera. Verifica los BBjServices locales` | El comando no terminó dentro de los 120 segundos. | Verifica que BBjServices se ejecute y responda, luego ejecuta el objetivo nuevamente. |
| `Los archivos anteriores de BBjShip se mantienen en <folder>` | Un reemplazo falló y los archivos de la versión anterior se conservaron en esa carpeta. | Soluciona la razón de la falla y ejecuta el objetivo nuevamente. |

## Desplegar y desimplantar {#deploy-and-undeploy}

### Antes de la subida {#before-the-upload}

Estos mensajes provienen de `bbj-package`, `bbj-deploy` y `bbj-undeploy` antes de que algo llegue a BBjShip.

| Mensaje | Causa | Qué hacer |
|---|---|---|
| `El despliegue de BBj requiere empaquetado en jar, este proyecto está empaquetado como <packaging>` | El proyecto no está empaquetado como un jar. | Establece `<packaging>jar</packaging>`. |
| `El despliegue de BBj requiere el jar de la aplicación empaquetada...` | El objetivo se ejecutó sin la fase `package` en la misma construcción. | Ejecuta `mvn install`, o `mvn package webforj:bbj-deploy`. |
| `El despliegue de BBj no debe llevar webforj-engine, BBj es el motor...` | `webforj-engine` está en el classpath de tiempo de ejecución de la aplicación. | Exclúyelo de la dependencia `webforj`, consulta [Sin el plugin de instalación](/docs/configuration/bbjship/existing-projects#without-the-install-plugin). |
| `El despliegue de BBj requiere webforj-foundation en el classpath de tiempo de ejecución` | La aplicación no tiene ninguna dependencia de webforJ en tiempo de ejecución. | Agrega la dependencia `webforj` en el ámbito `compile`. |
| `La dependencia del despliegue de BBj no se pudo resolver...` | Una dependencia de la aplicación no tiene archivo. | Verifica que Maven resuelva todas las dependencias del proyecto. |
| `El nombre de publicación <name> no es válido...` | El nombre de publicación rompe las reglas en [Nombres de publicación](/docs/configuration/bbjship/deploying#publish-names). | Elige otro `<publishName>`. |
| `El nombre de publicación <name> está reservado por BBjServices para sus propias rutas web...` | BBjServices usa ese nombre para sus propias direcciones. | Elige otro `<publishName>`. |
| `No se pudo escribir el archivo de despliegue de BBj...` | `bbj-package` no pudo escribir `target/bbj/<nombre de publicación>.zip`. La razón sigue. | Lee la razón. |

### Respuestas de BBjShip {#answers-of-bbjship}

Cuando BBjShip rechaza un despliegue o una desimplantación, el mensaje comienza con `El despliegue de BBj falló con HTTP <status>` o `La desimplantación de BBj falló con HTTP <status>`, seguido de la razón.

| Estado | Razón | Causa | Qué hacer |
|---|---|---|---|
| 400 | `El nombre de publicación <name> no es válido...` o `...está reservado por BBjServices...` | Se rechazó el nombre de publicación. | Elige otro nombre de publicación. |
| 400 | `El webforj.conf de despliegue de BBj es inválido. <detalle>` | No se puede leer el `webforj.conf` de la aplicación, o una [configuración de la aplicación](/docs/configuration/bbjship/deploying#app-settings) tiene el tipo incorrecto. | Corrige el archivo según lo que dice el detalle. |
| 400 | `El cuerpo subido no es un archivo de despliegue de BBj...`, `Entrada de archivo de despliegue de BBj inválida: <entry>` o otra razón acerca del archivo | El archivo no fue escrito por el plugin de construcción o fue modificado posteriormente. | Despliega el archivo que la construcción escribe, con la misma versión de webforJ que BBjShip. |
| 401 | `Verifica las credenciales de administración de BBj o el token` | Se rechazó el inicio de sesión. | Verifica las credenciales o renueva el token. |
| 403 | `Las ejecuciones de desarrollo no están habilitadas en esta máquina de BBj. Ejecuta bbjship development enable` | Una ejecución de desarrollo llegó a una máquina donde las ejecuciones de desarrollo están desactivadas. | Ejecuta `bbjship development enable` en la máquina BBj. |
| 404 | `No se encontró ninguna aplicación BBj llamada <name> desplegada por BBjShip. Verifica el nombre de publicación` | Una desimplantación nombró una aplicación que BBjShip no desplegó. | Verifica el nombre de publicación con `bbjship apps list`. |
| 409 | `La clase de entrada <class> ya se ha publicado como <name>. Despliega <name> primero` | La clase de entrada de la aplicación ya se está ejecutando bajo otro nombre de publicación. | Despliega la otra aplicación, o despliega bajo su nombre. |
| 409 | `La aplicación BBj publicada <name> no es propiedad de BBjShip...` | Una aplicación registrada en el Enterprise Manager ya usa el nombre. | Elige otro nombre de publicación, o elimina esa aplicación en el Enterprise Manager. |
| 409 | `La aplicación BBj publicada <other> difiere de <name> solo por el uso de mayúsculas...` | Otra aplicación usa el mismo nombre en otro caso. | Elige otro nombre de publicación. |
| 409 | `<name> es una ejecución de desarrollo. Detén la vigilancia primero` | Una ejecución de desarrollo de la aplicación está activa. | Detén la vigilancia con `Ctrl+C`, luego despliega. |
| 409 | `<name> se ha desplegado desde un archivo. Despliega primero` | Una ejecución de desarrollo usa el nombre de una aplicación desplegada. | Despliega la aplicación, o establece otro `<publishName>` para la ejecución de desarrollo. |
| 413 | `El archivo de despliegue de BBj excede el tamaño permitido de 1 GiB` | El archivo es más grande de lo que BBjShip acepta. | Elimina las dependencias que la aplicación no necesita en tiempo de ejecución. |
| 500 | `El despliegue de BBj falló. Verifica el registro de BBjServices` | BBjServices falló al instalar o eliminar la aplicación. | Lee los registros de BBjServices. |

Un despliegue que termina con `no confirmó la aplicación solicitada. Verifica la versión de BBjShip y el registro de BBjServices` llegó a BBjShip pero no recibió confirmación para la aplicación. Verifica que BBjShip y el `webforj-maven-plugin` tengan la misma versión, luego lee el registro de BBjServices.

### La aplicación en ejecución {#the-running-app}

| Mensaje | Causa | Qué hacer |
|---|---|---|
| `El protocolo de íconos requiere webforj.assetsSource = packaged cuando se ejecuta con BBj Services` | La aplicación usa `icons://` sin activos empaquetados. | Agrega `webforj.assetsSource = packaged` al `webforj.conf` y despliega nuevamente. |

## Ejecuciones de desarrollo {#development-runs}

Estos mensajes provienen de `mvn` o `mvn compile webforj:watch` en un proyecto configurado para BBjShip. Una ejecución de desarrollo fallida comienza con `La ejecución de desarrollo de BBj falló. Verifica los ajustes de despliegue y que las ejecuciones de desarrollo estén habilitadas en la máquina BBj`, seguido de la razón de [Configuración](#setup), [Credenciales y conexión](#credentials-and-connection), o [Respuestas de BBjShip](#answers-of-bbjship).

| Mensaje | Causa | Qué hacer |
|---|---|---|
| `La carpeta de clases <folder> no existe. Ejecuta compile antes de webforj:watch` | La vigilancia comenzó antes de compilar el proyecto. | Ejecuta `mvn compile webforj:watch`, o `mvn` en un proyecto arquetipo. |
| `La carpeta de clases de desarrollo de BBj debe ser un directorio absoluto en la máquina BBj` | `<development><classFolder>` nombra una carpeta que BBjServices no puede encontrar. | Establece la ruta absoluta de la carpeta de clases en la máquina BBj. |
| `No se encontró el archivo de propiedades de BBjServices en <file>. Configura bbj.properties` | El archivo de propiedades de BBjServices no está donde la ejecución lo busca. | Establece `<bbj><properties>` o `-Dwebforj.bbj.properties` al archivo. |
| `BBjServices no se detuvo dentro de los 180 segundos` | La ejecución pidió a BBjServices detenerse para aplicar los ajustes de intercambio en caliente, y continuó ejecutándose. | Detén BBjServices tú mismo, reinícialo y ejecuta la vigilancia nuevamente. |
| `El programa de inicio de BBjServices está ausente: <program>` | La instalación de BBj no tiene un programa para iniciar BBjServices. | Inicia BBjServices tú mismo y ejecuta la vigilancia nuevamente. |
| `BBjServices no se inició. El programa terminó con el código de salida <code>`, `BBjServices no se inició dentro de los 120 segundos` | BBjServices no regresó después del reinicio. | Inicia BBjServices tú mismo, luego ejecuta la vigilancia nuevamente. |
| `BBjShip no respondió dentro de los 120 segundos` | BBjServices se inició, pero el punto final de despliegue no respondió. | Verifica el registro de BBjServices, luego ejecuta la vigilancia nuevamente. |
| `BBjServices se reinició sin los ajustes de intercambio en caliente. Verifica <file>` | BBjServices se inició con argumentos de Java diferentes a los escritos en el archivo de propiedades. | Verifica los ajustes de Java de BBjServices en el Enterprise Manager y en ese archivo. |
| `webforJ watch: la ejecución de desarrollo de <name> no fue detenida. <razón>` | La aplicación no pudo ser eliminada cuando la construcción se detuvo. | Elimínala con `bbjship apps undeploy <name>`, o inicia la vigilancia nuevamente, lo que la reemplaza. |

La primera ejecución de desarrollo que agrega los ajustes de intercambio en caliente reinicia BBjServices. Comienza BBjServices con `bin/bbjservices` en macOS y Linux. En Windows, inicia el servicio de Windows `BBjServices` cuando uno está registrado y ejecuta `bin\BBjServices.exe -noservice` de lo contrario. Un BBjServices iniciado por `bin/bbjservices` o `-noservice` se ejecuta fuera del administrador de servicios del sistema operativo hasta que la máquina se reinicia.

## Línea de comandos {#command-line}

Cada comando `bbjship` se ejecuta dentro de BBjServices, por lo que BBjServices debe estar en ejecución. Sin él, el comando no puede iniciarse.

Los argumentos inválidos terminan con el código de salida `2` y un mensaje que indica qué especificar:

| Mensaje | Qué hacer |
|---|---|
| `Especifica un comando. Usa --help para los comandos disponibles`, `Comando desconocido <command>...` | Nombra un comando, consulta [Línea de comandos](/docs/configuration/bbjship/command-line). |
| `Opción desconocida <option>...`, `La opción <option> no está disponible para <command>...` | Usa una opción de ese comando, como se indica en `--help`. |
| `Especifica un nombre de usuario después de --username`, `Especifica una contraseña después de --password`, `Especifica un puerto después de --port` | Proporciona un valor para la opción. |
| `Especifica --port de 1 a 65535`, `Especifica --ssl-port de 1 a 65535` | Proporciona un número de puerto. |
| `Especifica <option> solo una vez` | Elimina la opción repetida. |
| `Especifica solo uno de --password y --password-stdin` | Mantén uno de los dos. |
| `La contraseña de administración de BBj está ausente. Especifica --username y --password <value> o --password-stdin, o establece WEBFORJ_BBJ_USERNAME y WEBFORJ_BBJ_PASSWORD` | Proporciona el nombre de usuario y la contraseña juntos. El mensaje nombra el que falta. |
| `Especifica un nombre de aplicación o --all`, `Especifica un nombre de aplicación` | Proporciona a `apps undeploy` un nombre de publicación, o `--all`. |
| `Especifica la ruta de un archivo de despliegue que termine en .zip`, `Especifica un archivo de despliegue` | Proporciona a `apps deploy` un archivo `.zip`. |
| `El archivo de despliegue <path> no existe`, `El archivo de despliegue <path> no puede ser leído` | Verifica la ruta. BBjServices lee el archivo, por lo que el usuario del sistema operativo de BBjServices necesita acceso de lectura a él. |
| `Los archivos de desarrollo se despliegan mediante la vigilancia, no mediante apps deploy` | Despliega el archivo de `bbj-package`, no el de una ejecución de desarrollo. |
| `El nombre de publicación <name> no es válido...`, `El nombre de publicación <name> está reservado...` | Cambia el nombre del archivo. Su nombre de archivo sin `.zip` es el nombre de publicación. |

Un comando que falla termina con el código de salida `1` y `Comando fallido.` seguido de la razón:

| Razón | Causa | Qué hacer |
|---|---|---|
| Un fallo de inicio de sesión de BBjServices | El nombre de usuario o la contraseña son incorrectos. | Verifica las credenciales. |
| `El servidor web de BBjServices no responde en el puerto <port>. Usa el puerto HTTP del servidor web de BBjServices` | `--port` o `--ssl-port` no es un puerto en el que el servidor web de BBjServices escucha. El punto final anterior permanece. | Usa los puertos configurados para el servidor web en el Enterprise Manager. |
| `La aplicación <name> no está desplegada por BBjShip` | `apps undeploy` nombró una aplicación que BBjShip no desplegó. | Verifica el nombre con `bbjship apps list`. |
| `Otra operación de despliegue está en progreso` | Otro despliegue o desimplantación bloqueó la máquina durante 30 segundos. | Espera a que termine, luego ejecuta el comando nuevamente. |
| `El contexto de BBjShip pertenece a otra aplicación`, `El contexto de activos pertenece a otra aplicación` | Otra aplicación en el Enterprise Manager usa el contexto web `webforj-bbjship` o `webforj-bbjship-assets`. | Cambia el nombre o elimina ese contexto en el Enterprise Manager. |
| `El classpath de comando de BBjShip pertenece a otra aplicación`, `El classpath base de BBjShip contiene una biblioteca no poseída` | Los classpaths `_webforj_bbjship_cli` o `_webforj_bbjship` en el Enterprise Manager fueron cambiados. | Elimina ambos classpaths en el Enterprise Manager y ejecuta el comando nuevamente, lo que los recrea. |
| `El servlet de BBjShip está ausente. Verifica la instalación del plugin`, `La biblioteca de BBjShip está ausente. Verifica la instalación del plugin` | La carpeta de BBjShip está incompleta. | Instala BBjShip nuevamente. |
| `BBjServices no gestiona contextos web. Actualiza BBj` | La versión de BBj es demasiado antigua para BBjShip. | Instala la versión de BBj que coincida con webforJ. |

`reset` termina con el código de salida `1` y `Reset cancelado` cuando la respuesta a `¿Continuar? [y/N]` no es `y` o `Y`. Cuando restaura los ajustes de Java de BBjServices, termina con `Reinicia BBjServices ahora para aplicar los ajustes de Java restaurados`. Reinicia BBjServices para ejecutarlo sin los ajustes de intercambio en caliente.

## Windows {#windows}

| Situación | Qué hacer |
|---|---|
| Ejecutar `bbj.exe` con el programa BBjShip no imprime nada. | Ejecuta `bbjship.cmd` en `<BBj home>\plugins\webforj-bbjship`, lo que muestra la salida del comando. |
| `--password-stdin` termina con código de salida `2` y `La entrada estándar no está disponible en Windows. Usa --password <value> o WEBFORJ_BBJ_PASSWORD`. | Proporciona la contraseña con `--password`, o establece `WEBFORJ_BBJ_USERNAME` y `WEBFORJ_BBJ_PASSWORD`. |
| `reset` termina con código de salida `2` y `La entrada estándar no está disponible en Windows. Ejecuta reset --yes para confirmar`. | Ejecuta `bbjship.cmd reset --yes`. |
| `apps undeploy` o `reset` imprimen `No eliminado: <folder>. <razón>` y terminan con código de salida `0`. | BBjServices aún mantiene archivos de la aplicación abiertos. La aplicación se elimina del Enterprise Manager. Reinicia BBjServices. La carpeta se eliminará con el siguiente despliegue de ese nombre, o elimínala después del reinicio. |
| Instalar BBjShip sobre una versión instalada falla porque sus archivos están en uso. | BBjServices aún mantiene abiertos los archivos de BBjShip instalado. Reinicia BBjServices, luego instala nuevamente. |
| BBjServices no se ejecuta como un servicio de Windows después de que una ejecución de desarrollo lo reinició. | La ejecución de desarrollo lo inició con `BBjServices.exe -noservice` porque no hay ningún servicio `BBjServices` registrado. Se ejecuta fuera del administrador de servicios de Windows hasta que la máquina se reinicia. |
| Un comando `bbjship.cmd` no puede iniciarse. | Inicia BBjServices. Cada comando `bbjship` necesita que esté en ejecución. |
