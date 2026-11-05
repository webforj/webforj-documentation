---
title: Intercambio en caliente
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 97e2fbe0bd74ada89fb19fae84fc4102
---
# Hotswap <DocChip chip='since' label='26.02' />

Una herramienta de hotswap aplica los cambios de las clases compiladas a la aplicación en ejecución sin reiniciarla. La aplicación conserva su estado entre actualizaciones. La herramienta se especifica en la configuración del [complemento de compilación de webforJ](/docs/configuration/build-plugin) y se conecta cuando la compilación inicia la aplicación. El comando de ejecución no cambia y el proyecto no declara ninguna dependencia para la herramienta.

Se admiten dos herramientas:

- **HotswapAgent** es de código abierto. El complemento de compilación descarga el agente en la primera ejecución y lo guarda en la caché.
- **JRebel** es un producto comercial. Requiere una instalación y una licencia propias.

Configure exactamente una. Si la compilación especifica ambas, falla con un error que menciona las dos.

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

Sin esta capacidad, las modificaciones del cuerpo de los métodos siguen aplicándose, pero los cambios en la estructura de una clase no llegan a la aplicación en ejecución hasta que se reinicia. El registro de compilación muestra una advertencia con el requisito, y el navegador muestra un aviso una vez.

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) es un producto comercial cuya licencia concede su proveedor. webforJ no lo incluye ni lo descarga, y no interviene en su licencia. La compilación lee la ruta configurada, comprueba que el archivo exista y lo conecta sin modificarlo.

Indique en la configuración la ruta al agente de su instalación de JRebel, ya sea una biblioteca nativa o un archivo jar:

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

La ruta es obligatoria. Si una compilación selecciona JRebel sin especificarla, falla con un error que indica el ajuste que falta.

Con JRebel, todos los cambios en las clases, incluidos los cambios en su estructura, se aplican en cualquier entorno de ejecución de Java.

## Selección desde la línea de comandos {#command-line-selection}

La propiedad `webforj.hotswap` prevalece sobre el archivo de compilación para una sola ejecución. Los valores aceptados son `hotswapAgent`, `jrebel` y `off`. Cualquier otro valor hace que la compilación falle con un error que enumera los valores válidos. Si se selecciona `jrebel`, sigue siendo necesario configurar la ruta al agente.

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

Compile un cambio y este llegará a la aplicación en ejecución. Guarde el archivo en un IDE que compile al guardar, o ejecute una compilación en una segunda terminal.

Cuando todas las clases modificadas pertenezcan a lo que muestra la página actual, la parte afectada se reconstruye en el mismo lugar y la aplicación conserva su estado. En los demás casos, se vuelve a cargar la página completa: si la aplicación no tiene enrutamiento, si una clase queda fuera de las rutas mostradas o si no se puede realizar la reconstrucción. Cada cambio compilado genera una actualización en el navegador.

## Acelerar las recargas en IntelliJ IDEA {#speeding-up-reloads-in-intellij-idea}

IntelliJ IDEA solo compila al guardar si las compilaciones automáticas están activadas, y espera antes de cada paso. Reduzca las esperas para que los cambios lleguen antes a la aplicación en ejecución.

### Activar las compilaciones automáticas {#enable-automatic-builds}

Abra la configuración y active estas opciones:

1. En **Build, Execution, Deployment** > **Compiler**, active **Build project automatically**.
<!-- vale off -->
2. En **Advanced Settings** > **Compiler**, active **Allow auto-make to start even if developed application is currently running**.
<!-- vale on -->
3. En **Appearance & Behavior** > **System Settings**, en **Autosave**, active **Save files if the IDE is idle for** y establezca el valor en `1` segundo.

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

Pulse <kbd>Ctrl+ALt+S</kbd> o seleccione **File** > **Settings**.

</TabItem>
<TabItem value="macos" label="macOS">

Pulse <kbd>Cmd+,</kbd> o seleccione **IntelliJ IDEA** > **Settings**.

</TabItem>
</Tabs>

### Reducir las demoras de compilación {#shorten-the-build-delays}

Establezca estas claves del Registro en `0`:

| Clave | Descripción |
|-----|-------------|
| `compiler.automake.trigger.delay` | El tiempo de espera entre un cambio en un archivo y el inicio de una compilación. |
| `compiler.automake.build.while.idle.timeout` | El tiempo de inactividad antes de que se inicie una compilación. |
| `compiler.automake.postpone.when.idle.less.than` | El tiempo de inactividad por debajo del cual IntelliJ IDEA pospone una compilación. |

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

1. Pulse `Ctrl+Shift+A` para abrir **Find Action**.
2. Escriba `Registry...` y selecciónelo.

</TabItem>
<TabItem value="macos" label="macOS">

1. Pulse `Cmd+Shift+A` para abrir **Find Action**.
2. Escriba `Registry...` y selecciónelo.

</TabItem>
</Tabs>

También puede pulsar dos veces `Shift` para abrir **Search Everywhere**, cambiar a la pestaña **Actions** y escribir `Registry`.

En el cuadro de diálogo **Registry**, escriba el nombre de una clave para filtrar la lista y establezca su valor en `0`.

:::warning
Las entradas del Registro son ajustes avanzados de IntelliJ IDEA que no cuentan con soporte. Seleccione **Restore Defaults** en el cuadro de diálogo **Registry** para restablecerlos.
:::
