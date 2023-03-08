---
sidebar_position: 5
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';


# Button

The button control object provides methods for creating and manipulating a button in applications
or webpages. The button's behavior can be modified with the methods and events listed below, and is customizable with various themes, styles and expanses.

### Text

Buttons come with a parameterized constructor, taking a String argument, which will set the initial text of the button. The ```setText(String foo)``` method can also be called to set the text of the button.  

<br />

### Adding Icons to Buttons

In addition to, or instead of having text on a button, it is possible to add an icon to a button as well. Out of the box, the following icon pools can be used:

<ol>
    <li><a href='https://tabler-icons.io/'> Tabler </a></li>
    <li><a href='https://feathericons.com/'> Feather </a></li>
    <li><a href='https://fontawesome.com/'> Font Awesome Free </a></li>
</ol>

Below are examples of buttons with text to the left and right, as well as a button with only an icon:

<ComponentDemo 
path='/demos/button-demos/icon-demo.html' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/buttondemos/ButtonIcon.java'
showCSS='true' 
javaHighlight='{19-24}'
/>


<!-- ![Adding icons to buttons](./_images/button/button_icons.jpg) d -->
<br />

To add these icons, set the button's text to have an `<html>` tag, with a `<bbj-icon>` tag inside with the name attribute set accordingly. In addition to an icon, include text to the left or right of the `<bbj-icon>` tag to include a label as well:

<!-- ```java
 Button b1 = new Button ("<html><bbj-icon name=\"alien\"></bbj-icon> Icon Left</html>");
 Button b2 = new Button ("<html>Icon Right <bbj-icon name=\"alien\"></bbj-icon></html>");
 Button b3 = new Button ("<html><bbj-icon name=\"alien\"></bbj-icon></html>");
``` -->


<br />

### Disabling a Button

Button controls can be disabled to convey to a user that a certain action is not yet or is no longer available. A disabled button will increase the gray scale of the button, and is available for 
all button themes and expanses. <br/><br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.buttondemos.ButtonDisable' 
style={{"width": "100%"}}></iframe> -->

<ComponentDemo 
path='/demos/button-demos/button-disabled.html' 
showCSS='true'
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/buttondemos/ButtonDisable.java'
javaHighlight='{47-50}'
/>

<!-- ![Disabling a button](./_images/button/button_disable.jpg) -->

<br />

Disabling a button can be done at any time in the code by using the ```setEnabled(boolean foo)``` function. For added convenience, a button can also be disabled when clicked using the built-in ```setDisabledOnClick(boolean foo)``` function.

<br />

### Themes

DWCJ button components come with 14 themes built in for quick styling without the use of CSS.
Shown below are example buttons with each of the supported Themes applied: <br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.buttondemos.ButtonDemo' 
style={{"width": "100%"}}></iframe> -->

![Button themes](./_images/button/button_themes.jpg)

<br/>

Listed below are the current supported theme options for the button control:<br/>

|Button Themes|
|-|
|<ul><li>```Button.Theme.DEFAULT```</li><li>```Button.Theme.DANGER```</li><li>```Button.Theme.GRAY```</li><li>```Button.Theme.INFO```</li><li>```Button.Theme.PRIMARY```</li><li>```Button.Theme.SUCCESS```</li><li>```Button.Theme.WARNING```</li><li>```Button.Theme.OUTLINED_DEFAULT```</li><li>```Button.Theme.OUTLINED_DANGER```</li><li>```Button.Theme.OUTLINED_GRAY```</li><li>```Button.Theme.OUTLINED_INFO```</li><li>```Button.Theme.OUTLINED_PRIMARY```</li><li>```Button.Theme.OUTLINED_SUCCESS```</li><li>```Button.Theme.OUTLINED_WARNING```</li></ul>|

<br/>Theming is supported by use of a built-in enum class. To apply a theme, execute code similar to the following: <br/><br />

```java
import org.dwcj.controls.button.Button;

Button exampleButton = new Button("Example");      //Creates a new button with the text "example"
exampleButton.setTheme(Button.Theme.DEFAULT);      //Sets the button's theme to be the default theme.
```

<br />

### Expanses
There are five button expanses that are supported in the DWCJ which allow for quick styling without using CSS.
Below are the various expanses supported for the button control: <br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.buttondemos.ButtonExpanses' 
style={{"width": "100%", "height" : "100px"}}></iframe><br/>
Listed below are the current supported expanse options for the button control:<br/><br/> -->

![Button expanses](./_images/button/button_expanses.jpg)

|Button Expanses|
|-|
|<ul><li>```Button.Expanse.XSMALL```</li><li>```Button.Expanse.SMALL```</li><li>```Button.Expanse.MEDIUM```</li><li>```Button.Expanse.LARGE```</li><li>```Button.Expanse.XLARGE```</li></ul>|

<br/>Expanses are supported by use of a built-in enum class. To apply an expanse, execute code similar to the following: <br/><br />

```java
import org.dwcj.controls.button.Button;

Button exampleButton = new Button("Example");      //Creates a new button with the text "example"
exampleButton.setExpanse(Button.Expanse.MEDIUM);     //Sets the button's expanse to the medium size.
```


<br />

### Vertical Text Alignments

The DWCJ supports alignment of text within a button without having to use CSS to accomplish this.
Shown below are the three options for vertical alignment of text within a button : <br/>

<!-- <iframe 
loading="lazy"
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.buttondemos.ButtonAlignment' 
style={{"width": "100%", "height" : "120px"}}></iframe> -->

![Button vertical alignments](./_images/button/button_alignment.jpg)

Listed below are the current supported vertical text alignments for the button control:

|Button Vertical Alignments|
|-|
|<ul><li>```Button.Expanse.TOP```</li><li>```Button.Expanse.CENTER```</li><li>```Button.Expanse.BOTTOM```</li></ul>|


<br/>The various alignments are supported by use of a built-in enum class. To apply an alignment, execute code similar to the following: <br/><br/>

```java
import org.dwcj.controls.button.Button;                       

Button exampleButton = new Button("Example");                             //Creates a new button with the text "example"
exampleButton.setVerticalAlignment(Button.TextVerticalAlignment.CENTER);  //Sets the button's theme to be the default theme.
```

<!--  




### Sample Program


This code snippet is a small demonstration program that will create two buttons, each of which has an event attached. The first button will display a modal window with the first and last name that have
been entered into the text boxes, and the second button will clear any text within these boxes.

<iframe
loading="lazy" 
src='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.buttondemos.ButtonHelloWorld' 
style={{"width": "450px","height" : "350px"}}></iframe>



<details>
    <summary>Show Code</summary> 


<Tabs>
<TabItem value='Java' label='Java' default>

```java
    import org.dwcj.App;
    import org.dwcj.controls.panels.AppPanel;
    import org.dwcj.controls.label.Label;
    import org.dwcj.controls.textbox.TextBox;
    import org.dwcj.controls.button.Button;
    import org.dwcj.controls.button.events.ButtonClickEvent;

    import org.dwcj.exceptions.DwcAppInitializeException;


    public class HelloWorldJava extends App {
        
        private TextBox edFirstname;
        private TextBox edLastname;

        @Override
        public void run() throws DwcAppInitializeException {


            AppPanel panel = new AppPanel();

            //Initial styling for the application site panel
            panel.setStyle("display", "inline-grid");
            panel.setStyle("grid-template-columns", "1fr 2fr");
            panel.setStyle("gap", "20px");
            panel.setStyle("left", "20px");
            panel.setStyle("top", "20px");
            panel.setStyle("border", "1px dotted");
            panel.setStyle("padding", "10px");
            // Adding some labels and TextBox controls to use in the demonstration
            panel.add(new Label("Firstname:"));
            edFirstname = new TextBox();
            panel.add(edFirstname);
            panel.add(new Label("Lastname:"));
            edLastname = new TextBox("");
            panel.add(edLastname);
    
            //Creating the button and using the parameterized constructor for initial text
            Button helloBtn = new Button("Display Name");

            //adding the button to our application panel
            panel.add(helloBtn);

            //Setting the theme, expanse and text alignment using the Button's enum values
            helloBtn.setTheme(Button.Theme.PRIMARY);
            helloBtn.setExpanse(Button.Expanse.LARGE);
            helloBtn.setVerticalAlignment(Button.TextVerticalAlignment.CENTER);

            //Setting a click event for the first button
            helloBtn.onClick(this::onHelloButtonPush);
        
            //Repeating the above functionality for the second button
            Button deleteBtn = new Button("Clear Text");
            panel.add(deleteBtn);
            deleteBtn.setTheme(Button.Theme.DANGER);
            deleteBtn.setExpanse(Button.Expanse.LARGE);
            deleteBtn.setVerticalAlignment(Button.TextVerticalAlignment.CENTER);
            deleteBtn.onClick(this::onDeleteButtonPush);
            //Styling for both buttons
            helloBtn.setStyle("grid-column", "1 / span 2");
            helloBtn.setStyle("width", "100%");
            deleteBtn.setStyle("grid-column", "1 / span 2");
            deleteBtn.setStyle("width", "100%");
            
        }
            
        //Implementing behavior for the first button
        private void onHelloButtonPush(ButtonClickEvent ev) {
            String text = edFirstname.getText() + " " + edLastname.getText();
            App.msgbox(text, 0, "Hello World");
        } 
        
        //Implementing behavior for the second button
        private void onDeleteButtonPush(ButtonClickEvent ev) {
            edFirstname.setText("");
            edLastname.setText("");
        } 
    }
```
</TabItem>
    
<TabItem value='CSS' label='CSS'>
</TabItem>
</Tabs>

</details>



-->