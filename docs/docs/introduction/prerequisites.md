---
title: Prerequisites
sidebar_position: 1
---

Getting started with webforJ is simple, because there are only a couple of prerequisites. Use this guide to set up your development environment with the essential tools you will need to get up and running with webforJ. 

<!-- vale off -->
## Java Development Kit (JDK) 17.0.3 or higher

<!-- vale on -->

A Java Development Kit (JDK) is the most important requirement for developing with webforJ, providing the necessary tools to compile, run, and manage Java apps.
Java **17.0.3** or higher is required to ensure compatibility with webforJ and access to the latest features and security updates of the Java ecosystem. The webforJ framework is compatible with official Oracle JDKs and the open source Eclipse Temurin JDKs.
<!-- vale off -->
### JDK installation links:
<!-- vale on -->
:::tip
If you are using a UNIX based operating system we recommend the usage of [sdkman](https://sdkman.io/) for managing your Java Environment.
They allow you to freely choose from a variety of vendors without any extra navigation necessary.
:::
- Official Oracle JDKs can be found on Oracle's [Java Downloads](https://www.oracle.com/java/technologies/downloads/) page. 
  - Select Java version **17.0.3** or higher.
  - Click the tab for Linux, macOS, or Windows.
  - Click the link that corresponds to your computer's architecture. 
  - See Oracle's [JDK Installation Guide](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html) for complete information on installing an Oracle JDK.
- Open source JDKs can be found on Adoptium's [Eclipse Temurin™ Latest Releases](https://adoptium.net/temurin/releases/) page. 
  - Use the dropdown menus to select the operating system, architecture, package type, and JDK version **17.0.3** or higher. 
  - Click the link in the table for the archive type you wish to download.
  - See Adoptium's [Installation Guide](https://adoptium.net/installation/) for complete information on installing an Eclipse Temurin JDK.

<!-- vale off -->
### Verify your JDK installation
<!-- vale on -->
After installing the JDK, verify the installation by running the following command in your terminal or command prompt:

```bash
java -version
```

If your JDK is installed correctly, you will see output with your JDK version details, indicating version **17.0.3** or higher.
<!-- vale off -->
## Apache Maven
<!-- vale on -->

[Apache Maven](https://maven.apache.org/index.html) is a build automation and dependency management tool that simplifies the process of including external libraries such as webforJ in your project. 
In addition to helping with dependency management, Maven can automate tasks like compiling code, running tests, and packaging applications.

### Maven installation links
- To install the latest version of Maven, go to the [Apache Maven Download Page](https://maven.apache.org/download.cgi). 
  - Maven's [Installing Apache Maven](https://maven.apache.org/install.html) page has an overview of the installation process. 
  - Baeldung's [How to Install Maven on Windows, Linux, and Mac](https://www.baeldung.com/install-maven-on-windows-linux-mac) is a more in-depth installation guide for each operating system.

<!-- vale off -->
### Verify your Maven installation

<!-- vale on -->

After installing Maven, verify the installation by running the following command in your terminal or command prompt:

```bash
mvn -v
```

If Maven is installed correctly, the output should show the Maven version, Java version, and operating system information.

## Java IDE

A Java IDE provides a comprehensive environment for writing, testing, and debugging your code. There are many IDEs to choose from, so you can choose whichever one fits your workflow. Some popular choices for Java development include:

- **[Visual Studio Code](https://code.visualstudio.com/Download)**: A lightweight, extensible code editor with Java support through plugins.
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Known for its powerful Java support and rich plugin ecosystem.
- **[NetBeans](https://netbeans.apache.org/download/index.html)**: A free, open source IDE for Java and other languages, known for its ease of use and built-in project templates.
