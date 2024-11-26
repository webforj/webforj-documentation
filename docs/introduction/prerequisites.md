---
title: Prerequisites
sidebar_position: 1
---

Before you start building apps with webforJ, make sure your development environment is set up with the following essential tools. This section overviews each of the requirements and includes links to installation guides for macOS, Windows, and Linux.

<!-- vale off -->
## Java Development Kit (JDK) 17 or higher

<!-- vale on -->


webforJ requires `Java 17` or a newer version to ensure compatibility and access to the latest features of the Java ecosystem. The JDK provides the tools needed to compile, run, and manage Java apps. You can choose between Oracle JDK and the open source OpenJDK.

**Installation links:**
The official Oracle versions of the JDK can be found at [this link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html). Alternatively, open source versions of the various JDK versions can be found at [here](https://adoptium.net/temurin/releases/).

:::tip Java versions
Newer versions of the JDK can also be found and can be used with webforJ as well
:::

### How to verify your JDK installation
After installing the JDK, verify the installation by running the following command in your terminal or command prompt:

```bash
java -version
```
If installed correctly, you should see a response with version details indicating `Java 17` or higher.

<!-- vale off -->
## Installing Maven

<!-- vale on -->

Apache Maven is a build automation and dependency management tool that simplifies the process of including external libraries (like webforJ) in your project. Maven helps you manage project dependencies, compile code, run tests, and package applications.

To install Maven, follow this link to the [download site](https://maven.apache.org/download.cgi). For detailed instructions on installation across various OSs, follow [this link to a guide](https://www.baeldung.com/install-maven-on-windows-linux-mac) detailing the required steps. 

<!-- vale off -->
### Verify your Maven installation

<!-- vale on -->

After installing Maven, verify the installation by running:

```bash
mvn -v
```

The output should show the Maven version, Java version, and operating system information if Maven is installed correctly.

## Java IDE

A Java IDE provides a comprehensive environment for writing, testing, and debugging your code. Some popular choices for Java development include:

- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Known for its powerful Java support and rich plugin ecosystem.
- **[Visual Studio Code](https://code.visualstudio.com/Download)**: A lightweight, extensible code editor with Java support through plugins.
- **[NetBeans](https://netbeans.apache.org/download/index.html)**: A free, open source IDE for Java and other languages, known for its ease of use and built-in project templates.
