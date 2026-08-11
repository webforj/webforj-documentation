---
title: Hotswap
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 0943bf726abb55f753a0149ca3744ad7
---
# Hotswap <DocChip chip='since' label='26.02' />

Una herramienta de hotswap aplica cambios en clases compiladas a la aplicación en ejecución sin necesidad de reiniciar. La aplicación mantiene su estado entre actualizaciones. La herramienta se nombra en la configuración del [plugin de construcción webforJ](/docs/configuration/build-plugin) y se adjunta cuando la construcción inicia la aplicación. El comando de ejecución permanece igual, y el proyecto no declara ninguna dependencia para ello.

Se admiten dos herramientas:

- **HotswapAgent** es de código abierto. El plugin de construcción descarga el agente en la primera ejecución y lo almacena en caché.
- **JRebel** es un producto comercial. Requiere tu propia instalación y licencia.

Configura exactamente uno. Una construcción que nombre ambos fallará con un error que menciona ambos.

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

Dos opciones refinan la conexión:

| Opción | Descripción |
|--------|-------------|
| `version` | Una versión específica del agente en lugar de la que selecciona el plugin. |
| `path` | Un jar de agente en disco, utilizado directamente sin descarga. Para máquinas sin acceso a la red o para una construcción de agente personalizada. |

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

### Cambios en la estructura de clases {#class-structure-changes}

Las ediciones en el cuerpo de un método se aplican en cualquier máquina virtual de Java. Los cambios en la estructura de una clase, como un nuevo campo o un nuevo método, requieren una máquina virtual que acepte la opción `-XX:+AllowEnhancedClassRedefinition`, que proporciona el [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases). La construcción detecta la capacidad y la activa. Consulta [Requisitos previos](/docs/introduction/prerequisites#java-development-kit-jdk-21) para instalar un JetBrains Runtime.

Sin la capacidad, las ediciones en el cuerpo del método aún se aplican, y un cambio en la estructura de la clase no llega a la aplicación en ejecución hasta que se reinicia. El registro de construcción imprime una advertencia mencionando el requisito, y el navegador muestra un aviso una vez.

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) es un producto comercial, licenciado por su proveedor. webforJ no lo envía, no lo descarga, y no participa en su licencia. La construcción lee la ruta configurada, verifica que el archivo exista y lo adjunta sin cambios.

Apunta la configuración al agente de tu instalación de JRebel, una biblioteca nativa o un jar:

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

La ruta es obligatoria. Una construcción que seleccione JRebel sin ella fallará con un error que menciona la configuración faltante.

Con JRebel, todos los cambios en las clases, incluidos los cambios en la estructura, se aplican en cualquier tiempo de ejecución de Java.

## Selección desde la línea de comandos {#command-line-selection}

La propiedad `webforj.hotswap` sobrescribe el archivo de construcción para una única ejecución. Los valores aceptados son `hotswapAgent`, `jrebel`, y `off`. Cualquier otro valor falla la construcción con un error que lista los válidos. La selección de `jrebel` aún requiere la ruta del agente en la configuración.

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

## Aplicando un cambio {#applying-a-change}

Compila un cambio y llega a la aplicación en ejecución. Guarda en un IDE que compile al guardar, o ejecuta una compilación en una segunda terminal.

Cuando cada clase cambiada pertenece a lo que la página actual renderiza, la parte afectada se reconstruye en su lugar y el estado de la aplicación se mantiene. De lo contrario, la página se recarga en su totalidad: para una aplicación sin enrutamiento, para una clase fuera de las rutas renderizadas, o cuando la reconstrucción no se puede llevar a cabo. Un cambio compilado produce una actualización en el navegador.
