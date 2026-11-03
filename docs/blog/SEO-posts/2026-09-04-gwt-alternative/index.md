---
title: "GWT alternative: keep your UI in Java with webforJ"
description: "Looking for a GWT alternative? webforJ keeps the whole UI in Java without compiling it to JavaScript, and replaces RPC with direct service calls."
slug: gwt-alternative
date: 2026-09-04
authors: webforJ
tags: [community, ai, modernization, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-04-gwt-alternative/cover.jpg
hide_table_of_contents: false
---

<!-- vale Google.Acronyms = NO -->
<!-- vale Google.Headings = NO -->

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-04-gwt-alternative/cover.jpg)

webforJ is a GWT alternative for teams that want to keep writing their UI in Java but no longer want a Java-to-JavaScript compile step in the middle. You build screens from Java components, they run on the server inside your Spring Boot app, and the browser renders them. There is no client-side code to compile and no RPC layer between the UI and your services.

This post compares the two models, maps GWT concepts to their webforJ counterparts, and shows how a team can move an existing GWT app one screen at a time.

<!-- truncate -->

## What GWT does and where it stands {#what-gwt-does}

The GWT project describes itself as [a development toolkit for browser-based applications](https://www.gwtproject.org/overview.html). Its compiler turns your Java source into JavaScript: "GWT compiles your Java source code into optimized, stand-alone JavaScript files." You write client-side applications in Java and deploy them as JavaScript.

The project is still releasing. The [GWT versions page](https://www.gwtproject.org/versions.html) lists 2.13.0 from February 2026 and 2.13.1 from June 2026. If your GWT app works today, nothing forces a move. The reason teams look at a GWT alternative is usually the shape of the architecture, and that's where webforJ differs.

## Client-side Java versus server-side Java {#client-vs-server}

In GWT, UI code runs in the browser after translation to JavaScript. Server code is ordinary Java and, in the words of the GWT RPC guide, it ["doesn't need to be translatable, so you're free to use any Java library you find useful"](https://www.gwtproject.org/doc/latest/DevGuideServerCommunication.html). The client is a separate program with its own rules about which Java it can use.

In webforJ, UI code is ordinary Java running on the server, in the same process as your services. The browser renders the components and sends events back. The whole app, UI included, uses the full JDK and any library on the classpath.

| Question | GWT | webforJ |
| --- | --- | --- |
| Where UI code runs | In the browser, as compiled JavaScript | On the server, as Java |
| Build step for the UI | Java-to-JavaScript compilation | Normal Java compilation |
| UI to service calls | RPC with async interfaces and callbacks | Direct method calls |
| Types crossing the boundary | Must meet GWT serialization rules | None, they never cross |
| Libraries in UI code | Those the client side can translate | Anything on the classpath |
| Layout templates | UiBinder XML or panels in Java | Java composition |

## What happens to RPC? {#rpc}

A GWT RPC call needs several pieces. According to the [GWT documentation](https://www.gwtproject.org/doc/latest/DevGuideServerCommunication.html), you define a service interface that extends `RemoteService`, a matching `Async` interface with an `AsyncCallback` parameter on each method, and a servlet that extends `RemoteServiceServlet`. The client creates a proxy with `GWT.create()` and handles the result in a callback. Parameters and return values must follow GWT's serialization rules.

In webforJ that layer has no reason to exist, because the view and the service live in the same JVM. A view receives the service through its constructor and calls it like any other Java object. There is no `Async` twin of the interface, no callback and no data transfer class created only to cross the wire. The [Spring Boot integration guide](/docs/integrations/spring/spring-boot) shows constructor injection into views.

Server-side rendering does mean the UI depends on a live connection to the server. For internal business applications, that's usually an acceptable trade for the simpler architecture.

## How GWT concepts map to webforJ {#concept-mapping}

Most of what you know carries over. The names change and the number of moving parts shrinks.

| GWT | webforJ |
| --- | --- |
| `Button`, `TextBox` | [`Button`](/docs/components/button) and `TextField` |
| `FlowPanel`, `VerticalPanel`, `HorizontalPanel` | [`FlexLayout`](/docs/components/flex-layout) with a direction and spacing |
| `DockLayoutPanel` | [`AppLayout`](/docs/components/app-layout) with header, drawer and footer |
| `CellTable` | [`Table`](/docs/components/table/overview) bound to a repository or a list |
| `DialogBox` | [`Dialog`](/docs/components/dialog) with header, content and footer sections |
| `ClickHandler` and other handlers | `onClick(...)` and the other `on` methods |
| UiBinder template and owner class | A Java class that composes components |
| RPC service | An injected Spring bean |

For the GWT side, see the [panels guide](https://www.gwtproject.org/doc/latest/DevGuideUiPanels.html), the [cell table guide](https://www.gwtproject.org/doc/latest/DevGuideUiCellTable.html), and the [DialogBox reference](https://www.gwtproject.org/javadoc/latest/com/google/gwt/user/client/ui/DialogBox.html). The panels guide itself notes that `VerticalPanel` can usually be replaced by a simple `FlowPanel`, which is the same instinct as reaching for a `FlexLayout` in column direction.

## What happens to UiBinder templates? {#uibinder}

[UiBinder](https://www.gwtproject.org/doc/latest/DevGuideUiBinder.html) pairs a `ui.xml` template with an owner Java class. Elements marked with `ui:field` become `@UiField` members, and `@UiHandler` methods take the place of listeners.

In webforJ there is no second file. The layout is Java, so the template, the fields and the handlers collapse into one class. A field you declared as `@UiField` becomes a plain variable, and a `@UiHandler` becomes a lambda passed to `onClick`. Because layout is code, loops and conditionals are available where a template had none. A view built from components is a `Composite`, and the [composing components guide](/docs/building-ui/composing-components) covers the pattern.

## What does a typical screen look like in webforJ? {#typical-screen}

Here is a customer list with a search field and an add dialog. The service is a Spring bean injected through the constructor.

```java
@Route(value = "customers", outlet = Shell.class)
public class CustomersView extends Composite<FlexLayout> {
  private final CustomerService service;
  private final Table<Customer> table = new Table<>();

  public CustomersView(CustomerService service) {
    this.service = service;

    TextField search = new TextField("Search");
    search.onValueChange(e -> table.setItems(service.find(e.getValue())));

    table.addColumn("Name", Customer::name);
    table.addColumn("City", Customer::city);
    table.setItems(service.find(""));

    Button add = new Button("Add customer", ButtonTheme.PRIMARY);
    add.onClick(e -> openEditor());

    getBoundComponent().setDirection(FlexDirection.COLUMN).setSpacing("1em");
    getBoundComponent().add(search, add, table);
  }

  private void openEditor() {
    Dialog dialog = new Dialog();
    TextField name = new TextField("Name");
    TextField city = new TextField("City");
    Button save = new Button("Save", ButtonTheme.PRIMARY);
    save.onClick(e -> {
      service.save(new Customer(name.getValue(), city.getValue()));
      table.setItems(service.find(""));
      dialog.close();
    });
    dialog.addToContent(name, city);
    dialog.addToFooter(save);
    dialog.open();
  }
}
```

`service.find(...)` is a direct call that returns a list. There is no callback and no serialization step. `@Route` gives the screen its own URL, and `outlet` places it inside a shared layout, which is the role a GWT app usually gives to a history token and a root panel.

The shell is another small class with an `AppLayout` and an `AppNav` for the menu:

```java
public class Shell extends Composite<AppLayout> {
  public Shell() {
    AppNav nav = new AppNav();
    nav.addItem(new AppNavItem("Customers", CustomersView.class, TablerIcon.create("users")));
    getBoundComponent().addToDrawer(nav);
  }
}
```

## How do you migrate from GWT to webforJ? {#migration}

Treat the move as a screen-by-screen rewrite of the UI layer, while the service layer stays as it is.

1. **Create the webforJ app beside the GWT one.** Generate a Spring Boot project with [startforJ](https://docs.webforj.com/startforj). Point it at the same database and reuse the same service classes, or move them into a shared module.
2. **Pick one screen.** A read-only list or a small form is a good first target. Rebuild it with a `Table` or a `FlexLayout`, and call the service directly.
3. **Delete the RPC plumbing for that screen.** The `Async` interface, the servlet registration and the transfer classes used only by that screen can go.
4. **Fold the template into the class.** Convert the `ui.xml` structure into component construction, and the `@UiHandler` methods into listeners.
5. **Move users over.** Run both apps during the transition and link between them by URL until the last screen is done.

Services that already sit behind RPC are the easy part. Their implementation classes are plain Java, and in webforJ you inject them instead of exposing them through a servlet.

:::tip Let an AI assistant port the screens
Install the [webforJ AI plugin](/docs/ai-tooling) in Claude Code, GitHub Copilot, Cursor, Gemini CLI or Codex. The [MCP server](/docs/ai-tooling/mcp) gives the assistant the current webforJ documentation and API, so it writes against the real framework instead of guessing, and the [Agent Skills](/docs/ai-tooling/agent-skills) guide it through tasks such as building forms and creating components. Hand it a `ui.xml` file with its view class, ask for the webforJ screen that calls the same services, and review the result before you merge it.
:::

## Is webforJ right for your team? {#choosing}

Choose webforJ if your team knows Java, wants to stay in Java for the UI, and runs business applications where a Spring Boot backend is the center of gravity. You get one language and one build, and the code that used to cross a network boundary becomes a method call. If you also want an installable app on the desktop or home screen, the [installable apps post](/blog/installable-apps) shows how it works.

To try it, generate a project with [startforJ](https://docs.webforj.com/startforj) or follow the [getting started guide](/docs/introduction/getting-started). Then rebuild one list screen from your GWT app and compare the two.

## Frequently asked questions {#faq}

### Is GWT still maintained? {#faq-gwt-maintained}

The GWT project site lists release 2.13.0 from February 2026 and 2.13.1 from June 2026. An existing GWT app doesn't have to move for that reason alone.

### Does webforJ compile Java to JavaScript? {#faq-compile}

No. webforJ components are Java classes that run on the server. The browser renders them with web components and sends events back to the server.

### What replaces GWT RPC in webforJ? {#faq-rpc}

Direct method calls. A view gets a Spring service through its constructor and calls it in the same JVM, so there is no async interface, no callback, and no serialization contract to maintain.

### Do I have to rewrite everything at once? {#faq-rewrite}

No. Services stay as plain Java, and you can rebuild the UI one screen at a time while both apps run. Each migrated screen lets you delete its RPC code.
