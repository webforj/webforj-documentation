---
sidebar_position: 1
title: Docker
---

# Docker installation

This section of the documentation will cover the steps required for users who wish to develop using Docker. Changes to your code
will be made on your development machine, and the resulting app will be run in Docker. 

## 1. Downloading Docker

The installation process for Docker will differ slightly between Windows, Mac, and Linux users. See the section below that corresponds to your operating system.


### Windows

:::info
It is recommended to download the latest version of Windows Subsystem for Linux. More information can be found [at this link](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Download Docker Desktop:**
>- Visit the Docker Desktop for Windows download page: [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)
>- Click on the "Get Docker Desktop for Windows" button to download the installer.

**2. Install Docker Desktop:**
>- Run the installer you downloaded.
>- Follow the installation wizard, and make sure to enable Hyper-V (if prompted) as Docker for Windows uses Hyper-V for virtualization.
>- Once installation is complete, Docker Desktop will start automatically.

**3. Verify Installation:**
>- Open a terminal and run the command `docker --version` to verify that Docker is installed and working correctly.

### Mac

**1. Download Docker Desktop:**
>- Visit the Docker Desktop for Mac download page: [Docker Desktop for Mac](https://www.docker.com/products/docker-desktop/)

**2. Install Docker Desktop:**
>- Run the installer you downloaded.
>- Once installation is complete, Docker Desktop will start automatically.

**3. Verify Installation:**
>- Open a terminal and run the command `docker --version` to verify that Docker is installed and working correctly.

<!-- ### Linux

**1. Install Docker Engine**
>- Visit the Docker Desktop for Mac download page: [Docker for Linux](https://docs.docker.com/engine/install/)

**2. Verify Installation:**
>- Open a terminal and run the command `docker --version` to verify that Docker is installed and working correctly. -->

## 2. Configuration

Once Docker Desktop has been downloaded, search for the latest webforJ image, which is currently under the name `webforj/sandbox`.

![DWCJ Image Search](./_images/docker/Step_1l.png#rounded-border)

Click on the list of tags to see the available options

![DWCJ Image Search](./_images/docker/Step_2l.png#rounded-border)

For the most recent build, select "rc"

![DWCJ Image Search](./_images/docker/Step_3l.png#rounded-border)

Pull the image to start your container

![DWCJ Image Search](./_images/docker/Step_4l.png#rounded-border)

Once the download is complete, click the run button, which will open configuration settings

![DWCJ Image Search](./_images/docker/Step_5l.png#rounded-border)

Open the "Optional settings" menu

![DWCJ Image Search](./_images/docker/Step_6l.png#rounded-border)

Select a desired host port where you can see your app running within Docker

![DWCJ Image Search](./_images/docker/Step_7l.png#rounded-border)

Click "Run" to start the container

![DWCJ Image Search](./_images/docker/Step_8l.png#rounded-border)

<!-- Click the `Run` button, which will pop up a configuration window. These settings are optional, but it is highly recommended to
supply the `Host port` configuration setting, as this will be necessary later when running your app.

![Configuration](./_images/docker/2.png)

Once this is finished, click the `Run` button at the bottom of the window, which will create a new container with your specified settings. -->

:::success Important
Make sure to take note of the custom Host port number you provide, as this will be needed later.
:::

## 3. Running your app

Once the container has been created, webforJ applications can be run within the container instead of locally. First, it is necessary to configure
the POM file of your project correctly. Once this is done, going to a specific URL in the browser will show the app.

### Configuring your POM file

Running a webforJ project in the Docker container will require the use of the webforJ Install Plugin, which can be configured using your POM file:


Create a new `<plugin>` entry in `<plugins>` section of POM. The following code shows a starting entry that can be used and tweaked as 
needed for your project:

:::important
If your POM file does not have a `<plugins>` section, create one.
:::

```xml
<plugin>
<groupId>com.webforj</groupId>
<artifactId>webforj-install-maven-plugin</artifactId>
<version>${webforj.version}</version>
<executions>
    <execution>
    <goals>
        <goal>install</goal>
    </goals>
    </execution>
</executions>
<configuration>
    <deployurl>http://localhost:8888/webforj-install</deployurl>
    <classname>samples.HelloWorldApp</classname>
    <publishname>hello-world</publishname>
    <debug>true</debug>
</configuration>
</plugin>
```

Once an entry similar to the one above has been created, customize the following information:

- Change the `<deployurl>` entry to use the port number that you match the **Host port** that you configured for your container
in the previous step.

- Ensure that the `<classname>` entry matches the name of the app you want to run.

- If your `<username>` and `<password>` credentials are different for your installation of BBj, change these.


### Using the starter project
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

### Launching the app

Once this has been done, run a `mvn install` in your project directory. This will run the webforJ install plugin, and allow
you to access your app. To see the app, you'll want to go to the following URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Replace `YourHostPort` with the Host port you configured with Docker, and `YourPublishName` is replaced by the text inside the `<publishname>` tag of the POM. 
If done correctly, you should see your app render.

<!-- <UnderConstruction /> -->
