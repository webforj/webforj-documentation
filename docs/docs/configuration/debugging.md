---
title: "Java Debug Setup"
---

Debugging is an essential part of Java development, helping developers identify and fix issues efficiently. This guide explains how to configure debugging in webforJ for Visual Studio Code, IntelliJ IDEA, and Eclipse.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

### Debugging in Visual Studio Code

1. Open your webforJ project in VS Code.
2. Press <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (or <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> on Mac) to open the Run and Debug panel.
3. Click "create a launch.json file"
4. Select Java as the environment.
5. Modify `launch.json` to match the following:

```json title="launch.json"
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Attach to Jetty",
      "request": "attach",
      "hostName": "localhost",
      "port": 8000
    }
  ]
}
```

6. Save the file and click Start Debugging.

</TabItem>
<TabItem value="intellij" label="IntelliJ IDEA">

### Debugging in IntelliJ IDEA

1. Open your project in IntelliJ IDEA.
2. Navigate to Run → Edit Configurations.
3. Click the <kbd>+</kbd> button and select Remote JVM Debug.
4. Set the host to `localhost` and port to `8000`.
5. Save the configuration and click Debug to attach to the running app.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

### Debugging in eclipse

1. Open your project in Eclipse.
2. Go to Run → Edit Configurations.
3. Select Remote Java Application.
4. Click New Configuration and set:
   - Host: `localhost`
   - Port: `8000`
5. Save and start the debugger.

</TabItem>
</Tabs>

## Running the debugger

Once you’ve configured your IDE:

1. Start your webforJ app using `mvnDebug jetty:run`.
2. Run the debug configuration in your IDE.
3. Set breakpoints and begin debugging

:::tip Debugging Tips
1. Ensure port 8000 is available and not blocked by any firewall.
2. If you are using any of the webforJ archetypes and have changed the port number in the pom.xml file, make sure the port used for debugging matches the updated value.
:::