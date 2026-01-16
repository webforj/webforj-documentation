---
title: JBang
sidebar_position: 15
---

# JBang <DocChip chip='since' label='25.11' />

JBang is a tool that allows you to run Java code as scripts, without build files, project setup, or manual compilation. The webforJ JBang integration enables you to create webforJ apps quickly, best suited for rapid prototyping, learning, and quick demos, without needing the traditional dependencies and infrastructure of a fully fledged Java program.

## Why use JBang with webforJ {#why-use-jbang}

Traditional webforJ projects use Maven or Gradle with multiple configuration files and a standard project structure. This setup is standard for production apps but can feel heavy for simple experiments or demos.

With JBang, you can:

- **Start instantly**: Write a single `.java` file and run it immediately
- **Skip project setup**: No `pom.xml`, no `build.gradle`, no directory structure
- **Share easily**: Send someone a single file they can run with one command
- **Learn faster**: Focus on webforJ concepts without build tool complexity

The integration includes automatic server shutdown when you close the browser tab, keeping your development workflow clean.

## Prerequisites {#prerequisites}

### Install JBang {#install-jbang}

Choose your preferred installation method:

```bash
# Linux/macOS (curl)
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

Verify the installation:

```bash
jbang --version
```

:::info Default Java version
When you run JBang for the first time, and no JDK is found, JBang will try to install a JDK for you. You can control the default JDK version and vendor by setting the `JBANG_DEFAULT_JAVA_VERSION` and `JBANG_JDK_VENDOR` environment variables. It is recommended to use an OpenJDK vendor version of Java to ensure execution without a webforJ watermark. Run these commands prior to installing Jbang:

```bash 
export JBANG_DEFAULT_JAVA_VERSION=21 
export JBANG_JDK_VENDOR=openjdk
```
:::

:::tip[Learn more about JBang]
For comprehensive JBang documentation, see:
- [JBang Getting Started](https://www.jbang.dev/documentation/jbang/latest/index.html) - Installation and basics
- [Script Directives Reference](https://www.jbang.dev/documentation/jbang/latest/script-directives.html) - All available directives
- [Dependencies](https://www.jbang.dev/documentation/jbang/latest/dependencies.html) - Advanced dependency management
:::

## Creating a webforJ script {#creating-a-script}

Create a file named `HelloWorld.java` with the following content:

```java title="HelloWorld.java"
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21
//DEPS com.webforj:webforj-jbang-starter:25.11

import com.webforj.App;
import com.webforj.component.button.Button;
import com.webforj.component.field.NumberField;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.window.Frame;
import com.webforj.exceptions.WebforjException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloWorld extends App {

  int count = 0;

  @Override
  public void run() throws WebforjException {
    Frame frame = new Frame();
    NumberField counter = new NumberField();
    counter.setReadOnly(true);
    counter.setValue(0d);

    Button button = new Button("Click me!", e -> {
      count++;
      counter.setValue((double) count);
    });

    FlexLayout layout = FlexLayout.create(button, counter)
        .vertical()
        .justify().center()
        .contentAlign().center()
        .build();
    layout.setHeight("100%");
    layout.setSpacing("var(--dwc-space-m)");

    frame.add(layout);
  }
}
```

### Understanding the script structure {#script-structure}

| Line | Purpose |
|------|---------|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | Shebang line allowing the script to be executed directly on Unix systems |
| `//JAVA 21` | Specifies the minimum Java version required; JBang downloads it automatically if needed |
| `//DEPS com.webforj:webforj-jbang-starter:25.11` | Declares the webforJ JBang starter as a dependency using Maven coordinates |
| `@SpringBootApplication` | Enables Spring Boot auto-configuration |
| `extends App` | Makes this class a webforJ app |

The `webforj-jbang-starter` dependency includes everything needed to run a webforJ app: the Spring Boot starter, development tools, and automatic browser opening.

:::note[Version]
Replace `25.11` with the latest webforJ version. Check [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj-jbang-starter) for the most recent release.
:::

### Using multiple files {#using-multiple-files}

JBang supports multiple source files using the `//SOURCES` directive:

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//SOURCES Helper.java
//SOURCES utils/Formatter.java
```

Files are relative to the main script, and only the main script can declare `//DEPS` and other directives. For complex multi-file projects, consider using a [Maven or Gradle project](./spring/spring-boot) instead.

### Adding dependencies {#adding-dependencies}

You can add additional Maven dependencies using multiple `//DEPS` lines:

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21
//DEPS com.webforj:webforj-jbang-starter:25.11
//DEPS com.google.code.gson:gson:2.11.0
//DEPS org.apache.commons:commons-lang3:3.14.0
```

Dependencies use standard Maven coordinates (`groupId:artifactId:version`). JBang fetches them automatically from Maven Central on first run.

## Running your script {#running-your-script}

Run the script with JBang:

```bash
jbang HelloWorld.java
```

JBang will:

1. Download dependencies (first run only)
2. Compile the script
3. Start the embedded server on a random available port
4. Open your default browser to the app

You should see a button and counter field. Click the button to increment the counter.

### Making the script executable {#executable-script}

On Unix systems, you can make the script directly executable:

```bash
chmod +x HelloWorld.java
./HelloWorld.java
```

This works because of the shebang line at the top of the file.

## IDE support {#ide-support}

JBang integrates with popular Java IDEs for a better development experience:

| IDE | Plugin | Features |
|-----|--------|----------|
| VS Code | [JBang extension](https://marketplace.visualstudio.com/items?itemName=jbangdev.jbang-vscode) | Directive autocomplete, dependency resolution, debugging |
| IntelliJ IDEA | [JBang plugin](https://plugins.jetbrains.com/plugin/18257-jbang) | Directive completion, dependency sync, JDK management, script templates |

These plugins provide features like autocomplete for `//DEPS` and other directives, automatic dependency resolution, and the ability to run and debug scripts directly from the IDE.

## Configuration {#configuration}

The webforJ JBang starter includes sensible defaults optimized for scripting. You can customize behavior using system properties.

### Auto-shutdown {#auto-shutdown}

By default, the server automatically shuts down when all browser tabs connected to the app are closed. This keeps your development workflow clean by not leaving orphaned servers running.

| Property | Default | Description |
|----------|---------|-------------|
| `webforj.jbang.autoShutdown` | `true` | Turn auto-shutdown on or off |
| `webforj.jbang.idleTimeout` | `5` | Seconds to wait after last browser disconnects before shutting down |

To turn off auto-shutdown:

```bash
jbang -Dwebforj.jbang.autoShutdown=false HelloWorld.java
```

To change the idle timeout:

```bash
jbang -Dwebforj.jbang.idleTimeout=30 HelloWorld.java
```

### Default settings {#default-settings}

The JBang starter configures the following defaults:

| Setting | Value | Description |
|---------|-------|-------------|
| `server.port` | `0` | Random port assignment to avoid conflicts when running multiple scripts |
| `server.shutdown` | `immediate` | Fast shutdown for quick script termination |
| `spring.main.banner-mode` | `off` | Hides Spring Boot banner for cleaner output |
| `logging.level.root` | `ERROR` | Minimal logging to keep console output clean |
| `logging.level.com.webforj` | `WARN` | Shows only warnings and errors from webforJ |
| `webforj.devtools.browser.open` | `true` | Automatically opens browser when the app starts |

### Development workflow {#development-workflow}

JBang scripts don't support hot reload. To see changes:

1. Stop the running script (close the browser tab or press `Ctrl+C`)
2. Edit your code
3. Run `jbang HelloWorld.java` again

For rapid iteration with hot reload, consider using a [full Maven project with Spring DevTools](./spring/spring-boot).

## Transitioning to a full project {#transitioning}

When your prototype grows beyond a single file, create a proper project using [startforJ](https://docs.webforj.com/startforj) or the [Maven archetype](./spring/spring-boot#option-2-using-the-command-line). You can copy your script logic directly into the generated project structure.
