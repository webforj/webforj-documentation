---
title: "ZK framework alternative: a Java-only path with webforJ"
description: "Looking for a ZK framework alternative? Move ZUL screens and view models to webforJ, a server side full-stack Java framework with no XML markup, one screen at a time."
slug: zk-framework-alternative
date: 2026-10-05
authors: Matthew Hawkins
tags: [insights, modernization, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-05-zk-framework-alternative/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-05-zk-framework-alternative/cover.jpg)

Your team has a ZK app that works. Screens are written in ZUL, the logic sits in composers or view models, and a change to one form means opening more than one file. If you are searching for a ZK framework alternative that keeps the server side model but drops the markup layer, webforJ, the [full-stack Java framework](/blog/java-full-stack-framework), is built for that. Every screen is a Java class, with no ZUL and no XML, and you can move over one screen at a time.

Moving from ZK to a Spring Boot app this way is not a rewrite of the whole system. You rebuild the UI in Java, keep the Java backend and the Java team, and let the ZK app serve every screen you have not ported yet.

<!-- truncate -->

## Why look at a ZK alternative? {#why-look}

ZK is a server side Java framework. You describe a page in ZUL files, which are XML markup, and connect them to Java through MVC composers or MVVM view models. A screen therefore lives in two places, and a field rename touches the markup and the Java.

Reasons to consider a move include:

- One language for the whole UI, so refactoring and code search cover everything.
- Standard Java build tooling, with Spring Boot integration when you want it.
- Components you can brand through a theming system.
- Screens an AI coding assistant can read and write without a markup dialect in the way.

## A Java-only ZK alternative {#java-only}

In webforJ a screen is a component class. You create fields, buttons and layouts as Java objects, add them to a container, and attach handlers. The class is the template, so rename, find usages and compile errors work across the whole screen.

Event handling follows the same shape you know. A button gets a click listener, the handler runs on the server, and the component updates are sent to the browser. The runtime model is familiar to ZK teams. For the browser and server split, see [how a server-side UI framework works in webforJ](/blog/server-side-ui-framework-java). What changes is how you declare the UI.

## Where the MVVM habit goes {#mvvm}

If your ZK code follows MVVM, you keep the pattern and lose the markup. A plain bean is the model, and `BindingContext` binds a form to it. `read` fills the form from the bean, `write` pushes it back, and the result says whether validation passed. The post on [keeping a screen in sync without reactive streams](/blog/java-reactive-ui-framework) covers this in more depth.

```java
BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
Hero bean = new Hero("Superman", "Fly");

// reflect the bean data in the form
context.read(bean);

submit.onClick(e -> {
  // write the form data back to the bean
  ValidationResult results = context.write(bean);

  if (results.isValid()) {
    // do something with the bean
  }
});
```

The [data binding docs](/docs/data-binding/overview) cover automatic binding, validation, transformers, and nested properties. For a library of view models, the work is mostly moving property names across, because the bean side stays the same.

## Routing replaces page mapping {#routing}

Each view declares its own URL with one annotation:

```java
@Route(value = "profile")
@RouteAlias("user/me")
public class UserProfileView extends Composite<Div> {
  // Component logic here
}
```

Patterns like `user/:id` cover path parameters, and the [routing overview](/docs/routing/overview) lists the rest. Because the URL lives on the class, you can list every screen with a search for one annotation.

## Spring Boot and theming {#spring-and-theming}

With Spring Boot, webforJ runs in the same app, so you inject services and other Spring beans into views through constructors. If your ZK app already talks to Spring beans, your service layer carries over, and only the UI classes change. See the [Spring Boot docs](/docs/integrations/spring/spring-boot).

Components are web components styled through CSS variables. You apply a built-in light or dark theme and adjust tokens to match your brand. The [themes docs](/docs/styling/themes) cover the details.

## Migrate from ZK: model first {#migration}

Run both apps against the same services and database, and port in this order:

1. Create a webforJ Spring Boot project next to the existing app.
2. Pick a leaf screen, such as a settings page or a read only report.
3. Port the model first. Keep your beans and validation rules, and bind them with `BindingContext`.
4. Rebuild the layout in Java. Each ZUL container becomes a layout component and each widget the matching component.
5. Add the route and link to it from the old navigation.
6. Move the shared shell, menu and login last.

Each ported screen can ship on its own while users move between old and new. Starting with the model pays off because validation rules are the part teams most want to keep identical. Once a bean binds and validates the same way in both apps, the layout work that remains is mechanical.

## Keeping AI drafts honest {#ai-port}

A ZUL file and its view model make a clear input if your team asks an AI coding assistant for a first draft of the Java view. Treat that draft as unproven until it passes three checks: class and method names confirmed against the webforJ docs, a compile on every edit, and a test that binds the bean and submits the form. [The AI verification loop for Java](/blog/ai-verification-loop-java) sets those checks up. One screen per session keeps each review small.

## Is webforJ the right fit? {#choosing}

Choose webforJ when you want to stay on the server and in Java, remove the markup layer, and move to Spring Boot conventions. If your team is comfortable with MVVM binding, the move is closer to a translation than a redesign.

If part of your interface is custom client side scripting with no Java counterpart, wrap the library as a web component and call it from Java, or leave that screen where it is for now.

## Port one small ZK screen {#next-step}

Generate a project with [startforJ](https://docs.webforj.com/startforj) or follow [getting started](/docs/introduction/getting-started), then port one small screen.

## Frequently asked questions {#faq}

### Is there a drop-in replacement that reads ZUL files? {#faq-zul}

No. webforJ does not read ZUL files. You rebuild each screen as a Java class, one screen at a time, and your bean and service code stays as it is. If an AI coding assistant helps with the rebuild, the [webforJ AI tooling](/docs/ai-tooling) gives it the real webforJ API to work against, and your team still reviews each screen.

### Can I keep my view models? {#faq-viewmodels}

Mostly. The bean, its validation rules, and your service calls carry over. You replace binder expressions with `BindingContext`, which reads a bean into a form and writes it back with validation results.

### Do I have to migrate everything at once? {#faq-rewrite}

No. Run the webforJ app beside the existing one, share the back end, and port screens in any order.

## Plan your ZK migration with us {#plan-your-migration}

For the screens beyond the first one, [see how webforJ moves Java apps to the web](/modernization) and talk to us about your ZK app.
