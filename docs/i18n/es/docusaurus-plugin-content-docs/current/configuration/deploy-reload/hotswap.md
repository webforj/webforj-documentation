---
title: Intercambio en caliente
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 5c17c1ef76de9c15b7177aee4b2b8419
---
# Hotswap <DocChip chip='since' label='26.02' />

Una herramienta de hotswap aplica los cambios de clases compiladas a la aplicación en ejecución sin reiniciarla. La aplicación conserva su estado entre actualizaciones. La herramienta se especifica en la configuración del [complemento de compilación de webforJ](/docs/configuration/build-plugin) y se conecta cuando la compilación inicia la aplicación. El comando de ejecución no cambia y el proyecto no declara ninguna dependencia para la herramienta.

Se admiten dos herramientas:

- **HotswapAgent** es de código abierto. El complemento de compilación descarga el agente en la primera ejecución y lo guarda en caché.
- **JRebel** es un producto comercial. Requiere una instalación y una licencia propias.

Configura exactamente una. Si una compilación especifica ambas, falla con un error que las menciona.

<!-- vale off -->
## HotswapAgent {#hotswapagent}
<!-- vale on -->

Un elemento vacío es una configuración completa:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <hotswap>
      <hotswapAgent/>
    </hotswap>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    hotswapAgent {}
  }
}
```

</TabItem>
</Tabs>

Dos opciones permiten ajustar la conexión:

| Opción | Descripción |
|--------|-------------|
| `version` | Una versión específica del agente, en lugar de la que selecciona el complemento. |
| `path` | Un archivo jar del agente en el disco, que se usa directamente sin descargarlo. Para máquinas sin acceso a la red o para una compilación personalizada del agente. |

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<hotswap>
  <hotswapAgent>
    <path>/path/to/hotswap-agent.jar</path>
  </hotswapAgent>
</hotswap>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    hotswapAgent {
      path = file('/path/to/hotswap-agent.jar')
    }
  }
}
```

</TabItem>
</Tabs>

### Cambios en la estructura de las clases {#class-structure-changes}

Las modificaciones del cuerpo de un método se aplican en cualquier máquina virtual Java. Los cambios en la estructura de una clase, como añadir un campo o un método, requieren una máquina virtual que acepte la opción `-XX:+AllowEnhancedClassRedefinition`, disponible en [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases). La compilación detecta esta capacidad y la activa. Consulta [Requisitos previos](/docs/introduction/prerequisites#java-development-kit-jdk-21) para instalar JetBrains Runtime.

Sin esta capacidad, las modificaciones del cuerpo de los métodos siguen aplicándose, pero los cambios en la estructura de una clase no llegan a la aplicación en ejecución hasta que se reinicia. El registro de compilación muestra una advertencia que indica el requisito, y el navegador muestra un aviso una vez.

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) es un producto comercial cuya licencia concede su proveedor. webforJ no lo incluye ni lo descarga, y no interviene en la concesión de licencias. La compilación lee la ruta configurada, comprueba que el archivo exista y lo conecta sin modificarlo.

Configura la ruta del agente de tu instalación de JRebel, ya sea una biblioteca nativa o un archivo jar:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<hotswap>
  <jrebel>
    <path>/path/to/libjrebel64.dylib</path>
  </jrebel>
</hotswap>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    jrebel {
      path = file('/path/to/libjrebel64.dylib')
    }
  }
}
```

</TabItem>
</Tabs>

La ruta es obligatoria. Si una compilación selecciona JRebel sin especificarla, falla con un error que indica cuál es el ajuste que falta.

Con JRebel, todos los cambios de clases, incluidos los cambios en su estructura, se aplican en cualquier entorno de ejecución de Java.

## Selección desde la línea de comandos {#command-line-selection}

La propiedad `webforj.hotswap` prevalece sobre el archivo de compilación durante una sola ejecución. Los valores aceptados son `hotswapAgent`, `jrebel` y `off`. Cualquier otro valor hace que falle la compilación con un error que enumera los valores válidos. Si se selecciona `jrebel`, sigue siendo necesario especificar la ruta del agente en la configuración.

<Tabs>
<TabItem value="maven" label="Maven">

```bash
mvn -Dwebforj.hotswap=off
mvn -Dwebforj.hotswap=hotswapAgent
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```bash
./gradlew bootRun -Pwebforj.hotswap=off
./gradlew bootRun -Pwebforj.hotswap=hotswapAgent
```

</TabItem>
</Tabs>

## Aplicar un cambio {#applying-a-change}

Al compilar un cambio, este llega a la aplicación en ejecución. Guarda los cambios en un IDE que compile al guardar o ejecuta una compilación en una segunda terminal.

Si todas las clases modificadas forman parte de lo que muestra la página actual, se vuelve a crear la parte afectada sin cambiar de lugar y se conserva el estado de la aplicación. De lo contrario, la página se recarga por completo: si la aplicación no tiene enrutamiento, si una clase está fuera de las rutas mostradas o si no se puede llevar a cabo la reconstrucción. Cada cambio compilado produce una actualización en el navegador.

## Acelerar las recargas en IntelliJ IDEA {#speeding-up-reloads-in-intellij-idea}

IntelliJ IDEA solo compila al guardar si están habilitadas las compilaciones automáticas, y espera antes de cada paso. Reduce las esperas para que los cambios lleguen antes a la aplicación en ejecución.

### Habilitar las compilaciones automáticas {#enable-automatic-builds}

Abre los ajustes y activa estas opciones:

1. En **Build, Execution, Deployment** > **Compiler**, activa **Build project automatically**.
<!-- vale off -->
2. En **Advanced Settings** > **Compiler**, activa **Allow auto-make to start even if developed application is currently running**.
<!-- vale on -->
3. En **Appearance & Behavior** > **System Settings**, en **Autosave**, activa **Save files if the IDE is idle for** y establece el valor en `1` segundo.

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

Pulsa <kbd>Ctrl</kbd>+<kbd>ALt</kbd>+<kbd>S</kbd> o selecciona **File** > **Settings**.

</TabItem>
<TabItem value="macos" label="macOS">

Pulsa <kbd>Cmd</kbd>+<kbd>+</kbd> o selecciona **IntelliJ IDEA** > **Settings**.

</TabItem>
</Tabs>

### Reducir las demoras de compilación {#shorten-the-build-delays}

Establece estas claves del Registro en `0`:

| Clave | Descripción |
|-----|-------------|
| `compiler.automake.trigger.delay` | La demora entre un cambio en un archivo y el inicio de una compilación. |
| `compiler.automake.build.while.idle.timeout` | El tiempo de inactividad que debe transcurrir antes de que se inicie una compilación. |
| `compiler.automake.postpone.when.idle.less.than` | El tiempo de inactividad por debajo del cual IntelliJ IDEA pospone una compilación. |

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

1. Pulsa <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>A</kbd> para abrir **Find Action**.
2. Escribe `Registry...` y selecciónalo.

</TabItem>
<TabItem value="macos" label="macOS">

1. Pulsa <kbd>Cmd</kbd>+<kbd>Shift</kbd>+<kbd>A</kbd> para abrir **Find Action**.
2. Escribe `Registry...` y selecciónalo.

</TabItem>
</Tabs>

También puedes pulsar <kbd>Shift</kbd> dos veces para abrir **Search Everywhere**, cambiar a la pestaña **Actions** y escribir `Registry`.

En el cuadro de diálogo **Registry**, escribe el nombre de una clave para filtrar la lista y, a continuación, establece su valor en `0`.

:::warning
Las entradas del Registro son ajustes avanzados de IntelliJ IDEA que no cuentan con soporte. Selecciona **Restore Defaults** en el cuadro de diálogo **Registry** para restablecerlas.
:::
