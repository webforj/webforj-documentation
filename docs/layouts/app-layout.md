---
sidebar_position: 0
title: App Layout
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import AppLayoutViewer from '@site/src/components/DocsTools/AppLayoutViewer';

<div style={{width: "100%" , display: "flex", justifyContent: "flex-end", marginBottom: "-50px"}}>
<p style={{color: "gray"}} >API:&nbsp;</p>
<b><a href="https://javadoc.io/doc/org.dwcj/dwcj-engine/latest/org/dwcj/controls/applayout/AppLayout.html" style={{justifySelf: "flex-end"}}> Java </a></b>
</div>


### Features

The DWCJ App Layout is a component which allows for building common application layouts.

<ul>
    <li>Easy to use and customize</li>
    <li>Responsive design</li>
    <li>Multiple layout options</li>
    <li>Works with DWCJ Dark Mode</li>
</ul>

It provides a header, footer , drawer, and content section all built into a responsive component which can be easily customized to quickly build common application layouts such as a dashboard. The header and footer are fixed, the drawer slides in and out of the viewport, and the content is scrollable.

Each part of the layout is a Div, which can contain any valid DWCJ control. For best results, the application should include a viewport meta tag which contains viewport-fit=cover. The meta tag causes the viewport to be scaled to fill the device display.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

### Overview

The following code sample will result in an application with a collapsible sidebar that contains a logo and tabs for various content options and a header. The demo uses the bbj-icon-button web component to create a drawer toggle button. The button has the data-drawer-toggle attribute which instructs the BBjAppLayout to listen to click events coming from that component to toggle the drawer state.

<AppLayoutViewer url='/demos/app-layout-demos/basis-layout.html' mobile='false' />


<details>
    <summary>Show Code</summary> 


<Tabs>
<TabItem value='Java' label='Java' default>

```java showLineNumbers
public class AppLayoutDemo extends App{

    Label contentLabel; 
    
    @Override
    public void run() throws DwcAppInitializeException {
        App.getPage().addInlineStyleSheet("context://css/apptemplatestyles.css");
        
        AppPanel panel = new AppPanel();
        AppLayout demo = new AppLayout();
        panel.add(demo);

        //Header 
        demo.getHeader().addClassName("layout__header")
            .add(new Label("<html><bbj-icon-button name='menu-2' data-drawer-toggle><bbj-icon-button></html>"),
            new Label("DWCJ Application")
                .addClassName("layout__header")
        );

        //Drawer
        Div drawer = demo.getDrawer();
        drawer.addClassName("app-layout-drawer");

        //Drawer's logo container and logo
        drawer.add(new Div().addClassName("drawer__logo").add(
            new Label("<html><img src='" + "https://i.ibb.co/1n4n1Nh/logo.png" + "'</img></html>")
        ));

        //Drawer's Menu
        TabControl drawerMenu = new TabControl();
        drawer.add(drawerMenu);
        
        //Setting drawer menu's attributes
        drawerMenu.setAttribute("nobody","true");
        drawerMenu.setAttribute("borderless","true");
        drawerMenu.setAttribute("placement","left");

        //Adding tabs to drawer menu
        drawerMenu.add("<bbj-icon name='dashboard'></bbj-icon>      Dashboard")
            .add("<bbj-icon name='shopping-cart'></bbj-icon>  Orders"   )
            .add("<bbj-icon name='users'></bbj-icon>          Customers")
            .add("<bbj-icon name='box'></bbj-icon>            Products" )
            .add("<bbj-icon name='files'></bbj-icon>          Documents")
            .add("<bbj-icon name='checklist'></bbj-icon>      Tasks"    )
            .add("<bbj-icon name='chart-dots-2'></bbj-icon>   Analytics");
        
        drawerMenu.onSelect(this::onTabChange);

        //Content
        this.contentLabel = new Label();
        demo.getContent().add(
            new Label("<html><h1>Application Title</h1></html>"),
            this.contentLabel
        );
    }
    
    private void onTabChange(TabSelectEvent ev){
        String value = ev.getTitle().replaceAll("<[^>]*>","").trim();
        contentLabel.setText("<html><p>Content for " + value + " goes here</p></html>");
    }
}
```
</TabItem>
    
<TabItem value='CSS' label='CSS'>

```css
body,html {overflow: hidden}

.drawer__logo{
    display: flex;
    align-items: center;
    justify-content: center;
    padding: var(--bbj-space-m) 0;
    margin-bottom: var(--bbj-space-m);
    border-bottom: thin solid var(--bbj-color-default) !important;
}

.drawer__logo img {
    max-width: 100px;
    border-radius: 10px;
}

.layout__header {
    display: flex;
    align-items: center;
    gap: var(--bbj-space-m);
    padding: 0 var(--bbj-space-m);
}


.layout__header--title{
    display: block;
    font-size: 1.25em;
    margin-top: 0.83em;
    margin-bottom: 0.83em;
    margin-left: 0;
    margin-right: 0;
    font-weight: bold;
}

```
</TabItem>
</Tabs>

</details>

### Full-Width Navbars

By default, the AppLayout renders the header and the footer in the off-screen mode. The off-screen mode means that the header and the footer position will be shifted to fit beside the opened drawer. Disabling this mode will cause the header and footer to take the full available space and shift the drawer top and bottom position to fit with the header and the footer.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<AppLayoutViewer url='/demos/app-layout-demos/full-header.html' mobile='false'/>


### Multiple Toolbars

The navbar has no limit to the number of toolbars you can add. A toolbar is only a Div. To add an additional toolbar, simply add a new Div to the existing header div of the AppLayout.

The following demo shows how to use two toolbars, The first one houses the drawer's toggle button and the application's title. The second toolbar houses a secondary navigation menu.

<AppLayoutViewer url='/demos/app-layout-demos/multi-toolbars.html' mobile='false'/>

<details>
    <summary>Show Code</summary> 


<Tabs>
<TabItem value='Java' label='Java' default>

```java showLineNumbers
public class AppLayoutDemo extends App{
    
    Label contentLabel; 
    
    @Override
    public void run() throws DwcAppInitializeException {
        App.getPage().addInlineStyleSheet("context://css/apptemplatestyles.css");
        
        AppPanel panel = new AppPanel();
        AppLayout demo = new AppLayout();
        panel.add(demo);

        //Header 
        demo.getHeader().addClassName("app-layout-header").add(
            new Label("<html><bbj-icon-button name='menu-2' data-drawer-toggle></bbj-icon-button></html>"),
            new Label("DWCJ Application")
        );
        
        
        //Drawer
        Div drawer = demo.getDrawer();
        drawer.addClassName("app-layout-drawer");

        //Drawer's logo container and logo
        drawer.add(new Div().addClassName("drawer-logo-div").add(
            new Label("<html><img src='" + "https://i.ibb.co/1n4n1Nh/logo.png" + "'</img></html>").addClassName("drawer-logo")
        ));
        
        //Drawer's Menu
        TabControl drawerMenu = new TabControl();
        drawer.add(drawerMenu);
        
        //Setting drawer menu's attributes
        drawerMenu.setAttribute("nobody","true");
        drawerMenu.setAttribute("borderless","true");
        drawerMenu.setAttribute("placement","left");
        
        //Adding tabs to drawer menu
        drawerMenu.add("<bbj-icon name='dashboard'></bbj-icon>      Dashboard")
        .add("<bbj-icon name='shopping-cart'></bbj-icon>  Orders"   )
            .add("<bbj-icon name='users'></bbj-icon>          Customers")
            .add("<bbj-icon name='box'></bbj-icon>            Products" )
            .add("<bbj-icon name='files'></bbj-icon>          Documents")
            .add("<bbj-icon name='checklist'></bbj-icon>      Tasks"    )
            .add("<bbj-icon name='chart-dots-2'></bbj-icon>   Analytics");
        
            drawerMenu.onSelect(this::onTabChange);
            
            //Content
            this.contentLabel = new Label();
            demo.getContent().add(
                new Label("<html><h1>Application Title</h1></html>"),
            this.contentLabel
            );

            // Adding the additional toolbar with menu items
            Div secondToolbar = new Div();
            demo.getHeader().add(secondToolbar);
            TabControl secondMenu = new TabControl();
            secondToolbar.add(secondMenu);
            secondMenu.setAttribute("nobody", "true")
                .setAttribute("borderless", "true")
                .add("<bbj-icon name='report-money'></bbj-icon> Sales")
                .add("<bbj-icon name='building'></bbj-icon> Enterprise")
                .add("<bbj-icon name='credit-card'></bbj-icon> Payments")
                .add("<bbj-icon name='history'></bbj-icon> History");
    }

    private void onTabChange(TabSelectEvent ev){
        String value = ev.getTitle().replaceAll("<[^>]*>","").trim();
        contentLabel.setText("<html><p>Content for " + value + " goes here</p></html>");
    }
}
```
</TabItem>
    
<TabItem value='CSS' label='CSS'>

```css
body,html {overflow: hidden}

.drawer__logo{
    display: flex;
    align-items: center;
    justify-content: center;
    padding: var(--bbj-space-m) 0;
    margin-bottom: var(--bbj-space-m);
    border-bottom: thin solid var(--bbj-color-default) !important;
}

.drawer__logo img {
    max-width: 100px;
    border-radius: 10px;
}

.layout__header {
    display: flex;
    align-items: center;
    gap: var(--bbj-space-m);
    padding: 0 var(--bbj-space-m);
}


.layout__header--title{
    display: block;
    font-size: 1.25em;
    margin-top: 0.83em;
    margin-bottom: 0.83em;
    margin-left: 0;
    margin-right: 0;
    font-weight: bold;
}

```
</TabItem>
</Tabs>

</details>

### Sticky Toolbars

A sticky toolbar is a toolbar that remains visible at the top of the page when the user scrolls down but the navbar height is collapsed to make more space available for the page's content. Usually this kind of toolbar contains a fixed navigation menu which is relevant to the current page.

It is possible to create a sticky toolbars using the CSS custom property `--bbj-app-layout-header-collapse-height` and the `AppLayout.setHeaderReveal()` option.

When `AppLayout.setHeaderReveal(true)` is set called, the header will be visible on first render, and then hidden when the user starts scrolling down. Once the user starts scrolling up again the header will be revealed.

With the help of the CSS custom property `--bbj-app-layout-header-collapse-height` it is possible to control how much of the header navbar will be hidden.

<AppLayoutViewer url='/demos/app-layout-demos/sticky-toolbar.html' mobile='false' />

<details>
    <summary>Show Code</summary> 


<Tabs>
<TabItem value='Java' label='Java' default>

```java showLineNumbers
import org.dwcj.App;
import org.dwcj.util.Assets;
import org.dwcj.exceptions.DwcAppInitializeException;
import org.dwcj.controls.applayout.AppLayout;
import org.dwcj.controls.label.Label;
import org.dwcj.controls.panels.AppPanel;
import org.dwcj.controls.panels.Div;
import org.dwcj.controls.tabcontrol.TabControl;
import org.dwcj.controls.tabcontrol.events.TabSelectEvent;

public class AppLayoutDemo extends App{
    
    Label contentLabel; 
    
    @Override
    public void run() throws DwcAppInitializeException {
        App.getPage().addInlineStyleSheet("context://css/apptemplatestyles.css");
        
        AppPanel panel = new AppPanel();
        AppLayout demo = new AppLayout();
        panel.add(demo);

        //Header 
        demo.getHeader().addClassName("header__toolbar").add(
        demo.setHeaderReveal(true);

        );
        Div titleBar = new Div();
        titleBar.addClassName("header__content");
        titleBar.add(new Label("<html><bbj-icon-button name='menu-2' data-drawer-toggle></bbj-icon-button></html>"), 
                     new Label("DWCJ Application").addClassName("header__title")
        );
        
        
        demo.setDrawerPlacement(DrawerPlacement.HIDDEN);
        
        //Drawer's Menu
        TabControl menu = new TabControl();
        
        //Setting menu's attributes
        menu.setAttribute("nobody","true");
        menu.setAttribute("borderless","true");
        
        //Adding tabs to drawer menu
        menu.add("<bbj-icon name='dashboard'></bbj-icon>      Dashboard")
        .add("<bbj-icon name='shopping-cart'></bbj-icon>  Orders"   )
            .add("<bbj-icon name='users'></bbj-icon>          Customers")
            .add("<bbj-icon name='box'></bbj-icon>            Products" )
            .add("<bbj-icon name='files'></bbj-icon>          Documents")
            .add("<bbj-icon name='checklist'></bbj-icon>      Tasks"    )
            .add("<bbj-icon name='chart-dots-2'></bbj-icon>   Analytics");
        
        demo.getHeader().add(menu);

        menu.onSelect((ev) -> {
            int idx = ev.getIndex();
            if(displayList.get(idx).getValue().equals(Boolean.FALSE)){
                contentDisplay.addPage(String.valueOf(idx), displayList.get(idx).getKey());
                displayList.get(idx).setValue(Boolean.TRUE);
            }
            contentDisplay.displayPage(idx);
        });

            //Content
            this.contentLabel = new Label();
            demo.getContent().add(
                new Label("<html><h1>Application Title</h1></html>"),
            this.contentLabel
            );
    }

    private void onChange(TabSelectEvent ev){
        String value = ev.getTitle().replaceAll("<[^>]*>","").trim();
        contentLabel.setText("<html><p>Content for " + value + " goes here</p></html>");
    }
}
```
</TabItem>
    
<TabItem value='CSS' label='CSS'>

```css
body,html {overflow: hidden}

:root {   
  --bbj-app-layout-header-collapse-height: 45px;
}

.header__toolbar {
    display: flex;
    align-items: center;
    gap: var(--bbj-space-m);
    padding: 0 var(--bbj-space-m);
}

.header__title{
    display: block;
    font-size: 1.25em;
    margin-top: 0.83em;
    margin-bottom: 0.83em;
    margin-left: 0;
    margin-right: 0;
    font-weight: bold;
}

.header__content{
    display: flex;
    align-items: center;
    gap: var(--bbj-space-m);
    padding: 0 var(--bbj-space-m);
}

.header__content img{
    height: 24px;
}

```
</TabItem>
</Tabs>

</details>


### Mobile Navigation Layout

The bottom navbar can be used to provide a different version of the navigation at the bottom of application. This type of navigation is specifically popular in mobile apps.

Notice how the drawer is hidden in the following demo. The AppLayout widget supports three drawer positions: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT`, and `DrawerPlacement.HIDDEN`.

Same as `AppLayout.setHeaderReveal()`, `AppLayout.setFooterReveal()` is supported. When `AppLayout.setFooterReveal(true)` is called, the footer will be visible at first render then hidden when the user starts scrolling up. Once the user starts scrolling down again the footer will be revealed.

Be default, when the screen width is 800px or less , the drawer will be switched to popover mode. This is called the breakpoint. The popover mode means that the drawer will pop over the content area with an overlay. It is possible to configure the breakpoint by using the BBjAppLayout:setDrawerBreakpoint method and the breakpoint must be a valid [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

<AppLayoutViewer url='/demos/app-layout-demos/footer-reveal.html' mobile='true'/>

### Drawer Breakpoint

Be default, when the screen width is 800px or less , the drawer will be switched to popover mode. This is called the breakpoint. Popover mode means that the drawer will pop over the content area with an overlay. It is possible to configure the breakpoint by using the BBjAppLayout:setDrawerBreakpoint method and the breakpoint must be a valid media query.

For instance, in the following sample. we configure the drawer breakpoint to be 500px or less.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

<AppLayoutViewer url='/demos/app-layout-demos/drawer-breakpoint.html' mobile='true'/>

### Events

The `AppLayout` class supports two events:

<ol>
    <li>onDrawerOpen Fired when the drawer is opened</li>
    <li>onDrawerClose Fired when the drawer is closed</li>
</ol>