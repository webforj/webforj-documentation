---
title: Hotswap
sidebar_position: 10
sidebar_class_name: new-content
description: Apply compiled class changes to a running webforJ app without a restart, through HotswapAgent or JRebel configured in the webforJ build plugin.
---

# Hotswap <DocChip chip='since' label='26.02' />

A hotswap tool applies compiled class changes to the running app without a restart. The app keeps its state between updates. The tool is named in the [webforJ build plugin](/docs/configuration/build-plugin) configuration and attaches when the build starts the app. The run command stays the same, and the project declares no dependency for it.

Two tools are supported:

- **HotswapAgent** is open source. The build plugin downloads the agent on the first run and caches it.
- **JRebel** is a commercial product. It requires your own installation and license.

Configure exactly one. A build naming both fails with an error naming both.

<!-- vale off -->
## HotswapAgent {#hotswapagent}
<!-- vale on -->

An empty element is a complete configuration:

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

Two options refine the attachment:

| Option | Description |
|--------|-------------|
| `version` | A specific agent version instead of the one the plugin selects. |
| `path` | An agent jar on disk, used directly with no download. For machines without network access or for a custom agent build. |

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

### Class structure changes {#class-structure-changes}

Method body edits apply on any Java virtual machine. Changes to the structure of a class, such as a new field or a new method, require a virtual machine that accepts the `-XX:+AllowEnhancedClassRedefinition` option, which the [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) provides. The build detects the capability and turns it on. See [Prerequisites](/docs/introduction/prerequisites#java-development-kit-jdk-21) for installing a JetBrains Runtime.

Without the capability, method body edits still apply, and a class structure change doesn't reach the running app until a restart. The build log prints a warning naming the requirement, and the browser shows a notice once.

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) is a commercial product, licensed by its vendor. webforJ doesn't ship it, doesn't download it, and takes no part in its licensing. The build reads the configured path, checks that the file exists, and attaches it unchanged.

Point the configuration at the agent from your JRebel installation, a native library or a jar:

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

The path is required. A build selecting JRebel without it fails with an error naming the missing setting.

With JRebel, all class changes, including structure changes, apply on any Java runtime.

## Command line selection {#command-line-selection}

The `webforj.hotswap` property overrides the build file for a single run. Accepted values are `hotswapAgent`, `jrebel`, and `off`. Any other value fails the build with an error listing the valid ones. Selecting `jrebel` still requires the agent path in the configuration.

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

## Applying a change {#applying-a-change}

Compile a change and it reaches the running app. Save in an IDE that compiles on save, or run a compile in a second terminal.

When every changed class belongs to what the current page renders, the affected part rebuilds in place and the app state stays. Otherwise the page reloads in full: for an app without routing, for a class outside the rendered routes, or when the rebuild can't be carried out. One compiled change produces one update in the browser.
