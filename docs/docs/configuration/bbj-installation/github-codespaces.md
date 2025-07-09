---
sidebar_position: 2
title: Github Codespaces
---

import UnderConstruction from '@site/src/components/PageTools/UnderConstruction';

[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) has been configured to run in Github Codespaces. Codespaces is a cloud-based development environment, and allows you to develop and run webforJ applications directly within your browser. To start developing with this tool, follow the steps below:

## 1. Navigate to the HelloWorldJava repository

To start, you'll need to go to the HelloWorldJava repository, which can be found [at this link](https://github.com/webforj/webforj-hello-world). Once there, click the green **"Use this template"** button, and then the **"Open in a codespace"** option.

![Codespace buttons](./_images/github/1.png#rounded-border)

## 2. Running your program

After waiting for the codespace to load, you should see a browser version of VS Studio Code open with the "HelloWorldJava" sample program loaded. From here, you can run the sample program, or start developing.

To compile a program, open the terminal in VS Code and run the `mvn install` command.

![Maven Install](./_images/github/2.png#rounded-border)

If everything completes successfully, you should see the `BUILD SUCCESS` message.

:::warning WARNING 
Make sure to use the `mvn install` command instead of VS Code's built-in Maven interface for installing your program.
:::

Once this has been done, you'll need to navigate to a specific web address to see your program. To do this, first click the **"Ports"** tab at the bottom of VS Code. Here, you will see two ports, 8888, and one other, listed.

![Forwarded Ports](./_images/github/3.png#rounded-border)

Click on the small **"Open in Browser"** button, resembling a globe, in the **"Local Address"** section of the **Ports** tab, which will open a new tab in your browser.

![Browser Button](./_images/github/4.png#rounded-border)

When the browser tab is open, you'll want to add to the end of the URL to ensure that your app is run. First, add a `/webapp` to the end of the web address, which will end in `github.dev`. After that, add the correct app and class name (if applicable) to show the desired app. To see how to properly configure the URL, [follow this guide](./configuration).

:::success Tip
If you want to run the default "Hello World" program, simple add `/hworld` after the `/webapp` in the URL:
<br />

![Modified URL](./_images/github/5.png#rounded-border)
:::


Once this is done, you should see your app running in the browser, and can modify it in the VS Code instance running within codespaces.