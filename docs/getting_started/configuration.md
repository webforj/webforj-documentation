---
sidebar_position: 3
displayed_sidebar: documentationSidebar
---

# Configuration

Options exist to configure the DWCJ in order to load a default class or enable debug mode.

### Default Class

It is possible to configure the DWCJ to automatically load an application from the list of available applications that extend the `App` class. 

#### Changing the URL

The first option to do this is to modify the URL in the browser to include the classname of the application you want to run and replace <b>your.class.name.here</b> with the full classname as it appears on the list of classes:

`http://localhost:8888/webapp/yourAppName?class=your.class.name.here`

In the above URL,  

#### Editing the BBj config file
The second option is to open your config.bbx file, and set the classname within the file itself. This file is found in the cfg directory of your BBj installation, `C:\bbx\cfg\config.bbx` for example. To do so, add the following line and replace <b>your.class.name.here</b> with the full classname as it appears on the list of classes:

`SET DWCJCLASSNAME=your.class.name.here`

#### Using the Enterprise Manager

Finally, you can set the default class within the Enterprise Manager by adding the following line as a program argument within your Application:

`class=your.class.name.here`

Replace <b>your.class.name.here</b> with the full classname as it appears on the list of classes.

Once any of these options have been completed, the specified class will always load instead of displaying a list of available classes.

<br />

### Debug Mode

It's also possible to run your application in debug mode, which will allow comprehensive error messages to be printed to the console. 

The first option is to change the config.bbx file, found in the cfg directory of your BBj installation, `C:\bbx\cfg\config.bbx` for example. Add the line `SET DEBUG=1` to the file and save your changes.

Additionally, in the Enterprise Manager, you can add the following as a program argument:

`DEBUG`

Completing either of these will allow error messages to be printed to the browser console.