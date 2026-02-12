---
title: JBang
sidebar_position: 15
sidebar_class_name: new-content
_i18n_hash: a8ffb21c2834adc74528dc39cb6d0497
---
# JBang <DocChip chip='since' label='25.11' />

[JBang](https://www.jbang.dev/) es una herramienta que te permite ejecutar código Java como scripts, sin archivos de construcción, configuración de proyectos o compilación manual. La integración de webforJ con JBang te permite crear aplicaciones webforJ rápidamente, siendo más adecuada para prototipos rápidos, aprendizaje y demostraciones rápidas, sin necesidad de las dependencias e infraestructura tradicionales de un programa Java completo.

## ¿Por qué usar JBang con webforJ? {#why-use-jbang}

Los proyectos tradicionales de webforJ utilizan Maven o Gradle con múltiples archivos de configuración y una estructura de proyecto estándar. Esta configuración es común para aplicaciones de producción, pero puede parecer pesada para experimentos o demostraciones simples.

Con JBang, puedes:

- **Comenzar instantáneamente**: Escribir un único archivo `.java` y ejecutarlo de inmediato
- **Saltar la configuración del proyecto**: Sin `pom.xml`, sin `build.gradle`, sin estructura de directorios
- **Compartir fácilmente**: Enviar a alguien un solo archivo que puede ejecutar con un comando
- **Aprender más rápido**: Concentrarte en los conceptos de webforJ sin la complejidad de herramientas de construcción

La integración incluye el cierre automático del servidor cuando cierras la pestaña del navegador, manteniendo limpio tu flujo de trabajo de desarrollo.

## Requisitos previos {#prerequisites}

### Instalar JBang {#install-jbang}

Elige tu método de instalación preferido:

```bash
# Universal (Linux/macOS/Windows con bash)
curl -Ls https://sh.jbang.dev | bash -s - app setup

# SDKMan
sdk install jbang

# Homebrew (macOS)
brew install jbangdev/tap/jbang

# Chocolatey (Windows)
choco install jbang

# Scoop (Windows)
scoop install jbang
```

Verifica la instalación:

```bash
jbang --version
```

:::info[Versión de Java predeterminada]
Cuando ejecutas JBang por primera vez sin un JDK instalado, JBang lo descarga automáticamente. Puedes establecer la versión y el proveedor del JDK antes de ejecutar JBang:

```bash
export JBANG_DEFAULT_JAVA_VERSION=21
export JBANG_JDK_VENDOR=temurin
```
:::

:::tip[Aprender más sobre JBang]
Para una documentación completa de JBang, consulta:
- [JBang Introducción](https://www.jbang.dev/documentation/jbang/latest/index.html) - Instalación y conceptos básicos
- [Referencia de Directivas de Script](https://www.jbang.dev/documentation/jbang/latest/script-directives.html) - Todas las directivas disponibles
- [Dependencias](https://www.jbang.dev/documentation/jbang/latest/dependencies.html) - Gestión avanzada de dependencias
:::

## Creando un script de webforJ {#creating-a-script}

Crea un archivo llamado `HelloWorld.java` con el siguiente contenido:

```java title="HelloWorld.java"
///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.webforj:webforj-jbang-starter:25.11
//JAVA 21

package bang;

import com.webforj.App;
import com.webforj.annotation.Routify;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.Route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Routify
public class HelloWorld extends App {
  public static void main(String[] args) {
    SpringApplication.run(HelloWorld.class, args);
  }
}

@Route("/")
class MainView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("¿Cuál es tu nombre?");
  private Button btn = new Button("Di Hola");

  public MainView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(FeatherIcon.BELL.create())
        .setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> Toast.show("¡Bienvenido a webforJ JBang Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}

```

### Entendiendo la estructura del script {#script-structure}

| Línea | Propósito |
|------|---------|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | Línea shebang que permite la ejecución directa del script en sistemas Unix |
| `//JAVA 21` | Especifica la versión mínima de Java requerida; JBang la descarga automáticamente si es necesario |
| `//DEPS com.webforj:webforj-jbang-starter:25.11` | Declara el iniciador de webforJ JBang como una dependencia usando coordenadas de Maven |
| `@SpringBootApplication` | Habilita la auto-configuración de Spring Boot |
| `extends App` | Hace de esta clase una aplicación webforJ |

La dependencia `webforj-jbang-starter` incluye todo lo necesario para ejecutar una aplicación webforJ: el iniciador de Spring Boot, herramientas de desarrollo y apertura automática del navegador.

:::note[Versión]
Reemplaza `25.11` con la última versión de webforJ. Consulta [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj-jbang-starter) para la versión más reciente.
:::
### Agregando dependencias {#adding-dependencies}

Puedes agregar dependencias de Maven adicionales usando múltiples líneas `//DEPS`:

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21
//DEPS com.webforj:webforj-jbang-starter:25.11
//DEPS com.google.code.gson:gson:2.11.0
//DEPS org.apache.commons:commons-lang3:3.14.0
```

Las dependencias utilizan coordenadas estándar de Maven (`groupId:artifactId:version`). JBang las obtiene automáticamente de Maven Central en la primera ejecución.

## Ejecutando tu script {#running-your-script}

Ejecuta el script con JBang:

```bash
jbang HelloWorld.java
```

JBang hará:

1. Descargar dependencias (solo en la primera ejecución)
2. Compilar el script
3. Iniciar el servidor embebido en un puerto aleatorio disponible
4. Abrir tu navegador predeterminado a la aplicación

### Haciendo el script ejecutable {#executable-script}

En sistemas Unix, puedes hacer que el script sea directamente ejecutable:

```bash
chmod +x HelloWorld.java
./HelloWorld.java
```

Esto funciona gracias a la línea shebang en la parte superior del archivo.

## Soporte de IDE {#ide-support}

JBang se integra con IDEs de Java populares, incluyendo VS Code, IntelliJ IDEA, Eclipse y otros. Estas integraciones proporcionan características como autocompletado de directivas, resolución automática de dependencias y la capacidad de ejecutar y depurar scripts directamente desde el IDE.

Consulta la [documentación de integración de IDE de JBang](https://www.jbang.dev/documentation/jbang/latest/editing.html) para obtener instrucciones de configuración y editores compatibles.

## Configuración {#configuration}

El iniciador de webforJ JBang incluye configuraciones predeterminadas sensatas optimizadas para scripting. Puedes personalizar el comportamiento utilizando propiedades del sistema.

### Cierre automático {#auto-shutdown}

Por defecto, el servidor se cierra automáticamente cuando se cierran todas las pestañas del navegador conectadas a la aplicación. Esto mantiene limpio tu flujo de trabajo de desarrollo al no dejar servidores huérfanos en ejecución.

| Propiedad | Predeterminado | Descripción |
|----------|---------|-------------|
| `webforj.jbang.auto-shutdown` | `true` | Activar o desactivar el cierre automático |
| `webforj.jbang.idle-timeout` | `5` | Segundos a esperar después de que la última desconexión del navegador ocurra antes de apagarse |

Para desactivar el cierre automático:

```bash
jbang -Dwebforj.jbang.auto-shutdown=false HelloWorld.java
```

Para cambiar el tiempo de espera inactivo:

```bash
jbang -Dwebforj.jbang.idle-timeout=30 HelloWorld.java
```

### Configuraciones predeterminadas {#default-settings}

El iniciador de JBang configura los siguientes valores predeterminados:

| Configuración | Valor | Descripción |
|---------|-------|-------------|
| `server.port` | `0` | Asignación de puerto aleatorio para evitar conflictos al ejecutar múltiples scripts |
| `server.shutdown` | `immediate` | Cierre rápido para una rápida terminación del script |
| `spring.main.banner-mode` | `off` | Oculta el banner de Spring Boot para una salida más limpia |
| `logging.level.root` | `ERROR` | Registro mínimo para mantener la salida de la consola limpia |
| `logging.level.com.webforj` | `WARN` | Muestra solo advertencias y errores de webforJ |
| `webforj.devtools.browser.open` | `true` | Abre automáticamente el navegador cuando la aplicación se inicia |

### Redeployment y recarga en vivo {#development-workflow}

Los scripts de JBang no admiten recarga en vivo. Para ver los cambios:

1. Detén el script en ejecución (cierra la pestaña del navegador o presiona `Ctrl+C`)
2. Edita tu código
3. Ejecuta `jbang HelloWorld.java` nuevamente

Para la redeployment automática durante el desarrollo, considera usar un [proyecto completo de Maven con Spring DevTools](/docs/integrations/spring/spring-boot). Consulta la [documentación de recarga en vivo](/docs/configuration/deploy-reload/overview) para más detalles.

## Transición a un proyecto completo {#transitioning}

Cuando tu prototipo crezca más allá de un solo archivo, crea un proyecto adecuado usando [startforJ](https://docs.webforj.com/startforj) o el [arquetipo de Maven](./spring/spring-boot#option-2-using-the-command-line). Puedes copiar la lógica de tu script directamente en la estructura de proyecto generada.
