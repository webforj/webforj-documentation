---
sidebar_position: 4
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


1. BBj download and installation
2. Using the BBj Plugin Manager to create your app
3. Launching your app


:::tip Prerequisites
Before you begin, make sure you have reviewed the necessary [prerequisites](../../introduction/prerequisites) for setting up and using webforJ. This ensures you have all the required tools and configurations in place before starting your project.
:::


## 1. BBj download and installation {#1-bbj-download-and-installation}

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

## 2. Install and configure the webforJ plugin

Once BBj has been installed, the Plugin Manager can be accessed to install tools needed to configure webforJ. To start, type "Plugin Manager" into the start menu or Finder. 

<!-- ![Plugin manager start location](/img/bbj-installation/users/local/i1.png#rounded-border) -->

After the plugin manager has been opened, navigate to the "Available Plugins" tab towards the top.

![Plugin manager configuration](/img/bbj-installation/local/Step_1l.png#rounded-border)

Once in this section, select the "Show versions under development" checkbox

![Plugin manager configuration](/img/bbj-installation/local/Step_2l.png#rounded-border)

The DWCJ entry should now be visible in the list of available plugins for download. Click on this entry in the list to select it.

![Plugin manager configuration](/img/bbj-installation/local/Step_3l.png#rounded-border)

With the DWCJ entry selected, click the "Install" button

![Plugin manager configuration](/img/bbj-installation/local/Step_4l.png#rounded-border)

Once the plugin has finished installing, click the "Installed Plugins" tab at the top.

![Plugin manager configuration](/img/bbj-installation/local/Step_5l.png#rounded-border)

This tab displays installed plugins, which should now include the DWCJ entry. Click on the entry within the list.

![Plugin manager configuration](/img/bbj-installation/local/Step_6l.png#rounded-border)

With the DWCJ entry selected, click the "Configure" button

![Plugin manager configuration](/img/bbj-installation/local/Step_7l.png#rounded-border)

On the window that opens, click the "Enable Maven Remote Install" button at the bottom left of the window.


![Plugin manager configuration](/img/bbj-installation/local/Step_8l.png#rounded-border)

:::tip 

Alternatively, navigate to the `bin` directory within your `bbx` folder and run the following command:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

A dialog should display that remote installation has been enabled. Click "OK" to close this dialog.

![Plugin manager configuration](/img/bbj-installation/local/Step_9l.png#rounded-border)
<!-- ![Plugin manager start location](/img/bbj-installation/users/local/i2.png#rounded-border)

On this tab, select the "Show versions under development" checkbox near the top left of the window.

![Plugin manager start location](/img/bbj-installation/users/local/i2.5.png#rounded-border)

On this page, select the DWCJ entry, and click "Install".

![Plugin manager start location](/img/bbj-installation/users/local/i3.png#rounded-border)

Once this has been done, you should be able to switch back to the "Installed Plugins" tab, and see the DWCJ entry listed there.

Finally, click on the "Configure" button, which will open a new window. In this window, click the "Enable Maven Remote Install" button.

![Enabling Remote Installation](/img/bbj-installation/users/local/i6.png#rounded-border) -->


## 3. Using the starter project
Once BBj and the required webforJ plugin are installed and configured, you can create a new, scaffolded project from the command line. This project comes with the necessary tools to run your first webforJ program.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. Launching the app

Once this has been done, run a `mvn install` in your project directory. This will run the webforJ install plugin, and allow
you to access your app. To see the app, you'll want to go to the following URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Replace `YourHostPort` with the Host port you configured with Docker, and `YourPublishName` is replaced by the text inside the `<publishname>` tag of the POM. 
If done correctly, you should see your app render.