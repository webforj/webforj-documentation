---
title: JBang
sidebar_position: 15
description: >-
  Run webforJ apps as single-file JBang scripts with no Maven, Gradle, or
  project structure for rapid prototyping and demos.
_i18n_hash: a7af803fa3e58cb86e0c18289f8fbb40
---
# JBang <DocChip chip='since' label='25.11' />

[JBang](https://www.jbang.dev/) es una herramienta que permite ejecutar código Java como scripts, sin archivos de compilación, configuración de proyectos ni compilación manual. La integración de JBang con webforJ permite crear aplicaciones webforJ rápidamente, ideales para la creación rápida de prototipos, el aprendizaje y las demostraciones, sin necesitar las dependencias ni la infraestructura tradicionales de un programa Java completo.

## Por qué usar JBang con webforJ {#why-use-jbang}

Los proyectos webforJ tradicionales usan Maven o Gradle, con varios archivos de configuración y una estructura de proyecto estándar. Esta configuración es habitual en aplicaciones de producción, pero puede resultar pesada para experimentos o demostraciones sencillas.

Con JBang, puedes:

- **Empezar al instante**: Escribir un único archivo `.java` y ejecutarlo inmediatamente
- **Omitir la configuración del proyecto**: Sin `pom.xml`, sin `build.gradle` ni estructura de directorios
- **Compartir fácilmente**: Enviar a alguien un único archivo que pueda ejecutar con un comando
- **Aprender más rápido**: Centrarse en los conceptos de webforJ sin la complejidad de las herramientas de compilación

La integración incluye el cierre automático del servidor al cerrar la pestaña del navegador, lo que permite mantener limpio el flujo de trabajo de desarrollo.

## Requisitos previos {#prerequisites}

### Instalar JBang {#install-jbang}

Elige el método de instalación que prefieras:

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

:::info[Versión predeterminada de Java]
Cuando ejecutas JBang por primera vez sin tener un JDK instalado, JBang descarga uno automáticamente. Puedes establecer la versión y el proveedor del JDK antes de ejecutar JBang:

```bash
export JBANG_DEFAULT_JAVA_VERSION=21
export JBANG_JDK_VENDOR=temurin
```
:::

:::tip[Más información sobre JBang]
Para consultar la documentación completa de JBang, visita:
- [Primeros pasos con JBang](https://www.jbang.dev/documentation/jbang/latest/index.html) - Instalación y conceptos básicos
- [Referencia de directivas de script](https://www.jbang.dev/documentation/jbang/latest/script-directives.html) - Todas las directivas disponibles
- [Dependencias](https://www.jbang.dev/documentation/jbang/latest/dependencies.html) - Administración avanzada de dependencias
:::

## Crear un script de webforJ {#creating-a-script}

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

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("What is your name?");
  private Button btn = new Button("Say Hello");

  public MainView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(FeatherIcon.BELL.create())
        .setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> Toast.show("Welcome to webforJ JBang Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}

```

### Comprender la estructura del script {#script-structure}

| Línea | Propósito |
|------|---------|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | Línea shebang que permite ejecutar el script directamente en sistemas Unix |
| `//JAVA 21` | Especifica la versión mínima de Java necesaria; JBang la descarga automáticamente si es necesario |
| `//DEPS com.webforj:webforj-jbang-starter:25.11` | Declara el starter de JBang de webforJ como dependencia mediante coordenadas de Maven |
| `@SpringBootApplication` | Habilita la configuración automática de Spring Boot |
| `extends App` | Convierte esta clase en una aplicación webforJ |

La dependencia `webforj-jbang-starter` incluye todo lo necesario para ejecutar una aplicación webforJ: el starter de Spring Boot, herramientas de desarrollo y la apertura automática del navegador.

:::note[Versión]
Reemplaza `25.11` por la versión más reciente de webforJ. Consulta [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj-jbang-starter) para ver la versión más reciente.
:::
### Añadir dependencias {#adding-dependencies}

Puedes añadir dependencias adicionales de Maven mediante varias líneas `//DEPS`:

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21
//DEPS com.webforj:webforj-jbang-starter:25.11
//DEPS com.google.code.gson:gson:2.11.0
//DEPS org.apache.commons:commons-lang3:3.14.0
```

Las dependencias usan las coordenadas estándar de Maven (`groupId:artifactId:version`). JBang las descarga automáticamente de Maven Central la primera vez que se ejecuta el script.

## Ejecutar el script {#running-your-script}

Ejecuta el script con JBang:

```bash
jbang HelloWorld.java
```

JBang hará lo siguiente:

1. Descargar las dependencias (solo la primera vez)
2. Compilar el script
3. Iniciar el servidor integrado en un puerto aleatorio disponible
4. Abrir la aplicación en el navegador predeterminado

### Hacer que el script sea ejecutable {#executable-script}

En sistemas Unix, puedes hacer que el script sea directamente ejecutable:

```bash
chmod +x HelloWorld.java
./HelloWorld.java
```

Esto funciona gracias a la línea shebang al principio del archivo.

## Compatibilidad con IDE {#ide-support}

JBang se integra con IDE de Java populares, como VS Code, IntelliJ IDEA, Eclipse y otros. Estas integraciones ofrecen funciones como autocompletado de directivas, resolución automática de dependencias y la posibilidad de ejecutar y depurar scripts directamente desde el IDE.

Consulta la [documentación de integración de JBang con IDE](https://www.jbang.dev/documentation/jbang/latest/editing.html) para obtener instrucciones de configuración e información sobre los editores compatibles.

## Configuración {#configuration}

El starter de JBang para webforJ incluye valores predeterminados adecuados para crear scripts. Puedes personalizar el comportamiento mediante propiedades del sistema.

### Cierre automático {#auto-shutdown}

De forma predeterminada, el servidor se cierra automáticamente cuando se cierran todas las pestañas del navegador conectadas a la aplicación. Así se mantiene limpio el flujo de trabajo de desarrollo y se evita que queden servidores huérfanos en ejecución.

| Propiedad | Valor predeterminado | Descripción |
|----------|---------|-------------|
| `webforj.jbang.auto-shutdown` | `true` | Activa o desactiva el cierre automático |
| `webforj.jbang.idle-timeout` | `5` | Segundos que se esperan después de la desconexión del último navegador antes de cerrar el servidor |

Para desactivar el cierre automático:

```bash
jbang -Dwebforj.jbang.auto-shutdown=false HelloWorld.java
```

Para cambiar el tiempo de espera de inactividad:

```bash
jbang -Dwebforj.jbang.idle-timeout=30 HelloWorld.java
```

### Configuración predeterminada {#default-settings}

El starter de JBang configura los siguientes valores predeterminados:

| Configuración | Valor | Descripción |
|---------|-------|-------------|
| `server.port` | `0` | Asignación de un puerto aleatorio para evitar conflictos al ejecutar varios scripts |
| `server.shutdown` | `immediate` | Cierre rápido para finalizar scripts sin demora |
| `spring.main.banner-mode` | `off` | Oculta el banner de Spring Boot para que la salida sea más limpia |
| `logging.level.root` | `ERROR` | Registro mínimo para mantener limpia la salida de la consola |
| `logging.level.com.webforj` | `WARN` | Muestra solo advertencias y errores de webforJ |
| `webforj.devtools.browser.open` | `true` | Abre el navegador automáticamente al iniciar la aplicación |

### Redespliegue y recarga en vivo {#development-workflow}

Los scripts de JBang no admiten la recarga en vivo. Para ver los cambios:

1. Detén el script en ejecución (cierra la pestaña del navegador o pulsa <kbd>Ctrl</kbd>+<kbd>C</kbd>)
2. Edita el código
3. Vuelve a ejecutar `jbang HelloWorld.java`

Para realizar un redespliegue automático durante el desarrollo, considera usar un [proyecto Maven completo con Spring DevTools](/docs/integrations/spring/spring-boot). Consulta la [documentación de recarga en vivo](/docs/configuration/deploy-reload/overview) para obtener más detalles.

## Pasar a un proyecto completo {#transitioning}

Cuando el prototipo deje de caber en un único archivo, crea un proyecto formal con [startforJ](https://docs.webforj.com/startforj) o el [arquetipo de Maven](./spring/spring-boot#option-2-using-the-command-line). Puedes copiar directamente la lógica del script en la estructura del proyecto generado.
