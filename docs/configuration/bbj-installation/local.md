---
sidebar_position: 3
---

# Local Installation

This section of the documentation will cover the steps required only for users who wish to use webforJ for web and/or app development with a local BBj instance on their machine. This installation will not allow users to contribute to the webforJ foundation code itself.
<br/>

:::info
This walkthrough will cover installation on a Windows system - installation
steps may vary for Mac/Linux OS devices.
:::
<br/>

Installation will be broken down into the following steps:


1. Java and Maven download and configuration
2. BBj download and installation
3. Using the BBj Plugin Manager to create your app
4. Launching your app


## 1. Java and Maven download and configuration

In order to use webforJ, you must first have Java and Maven installed and properly configured. If you already
have Java and Maven downloaded, please skip to [**Step 2**](#section2). If you also have 
BBj installed on your system, please skip to [**Step 3**](#section3).

### Java

Java **OpenJDK17** can be found [by following this link](https://adoptium.net/temurin/releases/). It is recommended 
to allow the installation to handle setting the JAVA_HOME variable during installation, where applicable.

### Maven

Maven should also be downloaded, and can be found [at this link](https://maven.apache.org/download.cgi). It is 
recommended to configure your system environment variables with Maven - a guide for installation and configuration 
for Windows users can be found [here](https://phoenixnap.com/kb/install-maven-windows).


<a name='section2'></a>

## 2. BBj download and installation

<b>While following this step, be sure that you install the BBj version that corresponds to the same webforJ version. </b><br/><br/>

[This video](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) can help with the installation of BBj if you need assistance with setup. The installation section of the BASIS website can be found [at this link](https://basis.cloud/download-product)

:::tip
It's recommended to use the latest stable revision build of BBj, and to select "BBj" from the list of options, without Barista or Addon.
:::

<!-- Once BBj has been installed, it is also necessary to install the needed dependencies from the BBj library. This is done by navigating to the `lib` directory inside your bbx folder, and
running the following commands: -->

<!-- ```bash
mvn install:install-file -Dfile=BBjStartup.jar -DgroupId=com.basis.lib -DartifactId=BBjStartup -Dversion=23.01 -Dpackaging=jar
mvn install:install-file -Dfile=BBj.jar -DgroupId=com.basis.lib -DartifactId=BBj -Dversion=23.01 -Dpackaging=jar
mvn install:install-file -Dfile=BBjUtil.jar -DgroupId=com.basis.lib -DartifactId=BBjUtil -Dversion=23.01 -Dpackaging=jar
``` -->

<a name='section3'></a>

## 3. Install and configure the webforJ plugin

Once BBj has been installed, the Plugin Manager can be accessed to install tools needed to configure webforJ. To start, type "Plugin Manager" into the start menu or Finder. 

<!-- ![Plugin manager start location](./_images/users/local/i1.png#rounded-border) -->

After the plugin manager has been opened, navigate to the "Available Plugins" tab towards the top.

![Plugin manager configuration](./_images/local/Step_1l.png#rounded-border)

Once in this section, select the "Show versions under development" checkbox

![Plugin manager configuration](./_images/local/Step_2l.png#rounded-border)

The DWCJ entry should now be visible in the list of available plugins for download. Click on this entry in the list to select it.

![Plugin manager configuration](./_images/local/Step_3l.png#rounded-border)

With the DWCJ entry selected, click the "Install" button

![Plugin manager configuration](./_images/local/Step_4l.png#rounded-border)

Once the plugin has finished installing, click the "Installed Plugins" tab at the top.

![Plugin manager configuration](./_images/local/Step_5l.png#rounded-border)

This tab displays installed plugins, which should now include the DWCJ entry. Click on the entry within the list.

![Plugin manager configuration](./_images/local/Step_6l.png#rounded-border)

With the DWCJ entry selected, click the "Configure" button

![Plugin manager configuration](./_images/local/Step_7l.png#rounded-border)

On the window that opens, click the "Enable Maven Remote Install" button at the bottom left of the window.


![Plugin manager configuration](./_images/local/Step_8l.png#rounded-border)

:::tip 

Alternatively, navigate to the `bin` directory within your `bbx` folder and run the following command:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

A dialog should display that remote installation has been enabled. Click "OK" to close this dialog.

![Plugin manager configuration](./_images/local/Step_9l.png#rounded-border)
<!-- ![Plugin manager start location](./_images/users/local/i2.png#rounded-border)

On this tab, select the "Show versions under development" checkbox near the top left of the window.

![Plugin manager start location](./_images/users/local/i2.5.png#rounded-border)

On this page, select the DWCJ entry, and click "Install".

![Plugin manager start location](./_images/users/local/i3.png#rounded-border)

Once this has been done, you should be able to switch back to the "Installed Plugins" tab, and see the DWCJ entry listed there.

Finally, click on the "Configure" button, which will open a new window. In this window, click the "Enable Maven Remote Install" button.

![Enabling Remote Installation](./_images/users/local/i6.png#rounded-border) -->


4. ### Using the starter project
Once BBj and the required webforJ plugin are installed and configured, you can create a new, scaffolded project from the command line. This project comes with the necessary tools to run your first webforJ program.

To create and scaffold a new project, follow these steps:

1) **Navigate to the proper directory**:
Open a terminal and move to the folder where you want to create your new project.

2) **Run the archetype command**:
Use the Maven command below, and customize the `groupId`, `artifactId`, and `version` as needed for your project. To proceed with the webforJ starter project, use the following command:

```bash
mvn -B archetype:generate \
-DarchetypeGroupId=com.webforj \
-DarchetypeArtifactId=webforj-archetype-bbj-hello-world \
-DgroupId=org.example \
-DartifactId=my-hello-world-app \
-Dversion=1.0-SNAPSHOT
```

After running the command, Maven will generate the project files necessary to run the starter project.

### 5. Launching the app

Once this has been done, run a `mvn install` in your project directory. This will run the webforJ install plugin, and allow
you to access your app. To see the app, you'll want to go to the following URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Replace `YourHostPort` with the Host port you configured with Docker, and `YourPublishName` is replaced by the text inside the `<publishname>` tag of the POM. 
If done correctly, you should see your app render.