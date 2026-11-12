---
title: "Cursor Spring Boot UI: Let the Assistant Write Your Screens in Java"
description: "Build a Spring Boot UI with Cursor and webforJ. Install the AI plugin, prompt for a view that injects a Spring bean, and check it with a browserless test."
slug: cursor-spring-boot-ui
date: 2026-11-09
authors: Eric Handtke
tags: [ai, spring, mcp]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-11-09-cursor-spring-boot-ui/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-11-09-cursor-spring-boot-ui/cover.jpg)

You have a Spring Boot backend with services that work, and now it needs screens. Asking an AI assistant to write them is tempting, but a plain prompt in Cursor leaves the assistant guessing at your front end. For a Cursor Spring Boot UI that holds up, give the assistant the real webforJ API and let it write the views in Java. The webforJ AI plugin does that, and a short test confirms the result.

<!-- truncate -->

## What Cursor needs for a Spring Boot UI {#what-cursor-needs}

An assistant that guesses at a framework invents method names. Two pieces from the [webforJ AI plugin](/docs/ai-tooling) remove the guessing:

- The [MCP server](/docs/ai-tooling/mcp) lets the assistant look things up in the webforJ docs, scaffold projects, and validate design tokens instead of recalling them from memory.
- The [Agent Skills](/docs/ai-tooling/agent-skills) tell the assistant when to use those tools and in what order. There are skills for building forms, adding servlets, styling apps, and creating components.

Your screens are plain Java classes in the same project as your services. There is no second codebase to hand the assistant. If you use Claude Code instead, the same plugin lets you [build a Java web app with Claude Code](/blog/claude-code-java-web-app) end to end.

## Cursor AI and Spring Boot, step by step {#cursor-ai-spring-boot}

Follow these steps.

1. **Create a Spring Boot project.** Use [startforJ](https://docs.webforj.com/startforj) and choose **Spring Boot** under **Runs on**. Or use the command line with the `webforj-spring` flavor:

   ```bash
   mvn -B archetype:generate \
     -DarchetypeGroupId=com.webforj \
     -DarchetypeArtifactId=webforj-archetype-hello-world \
     -DarchetypeVersion=LATEST \
     -DgroupId=org.example \
     -DartifactId=my-app \
     -Dversion=1.0-SNAPSHOT \
     -Dflavor=webforj-spring
   ```

2. **Connect Cursor to webforJ.** Cursor uses manual configuration rather than a marketplace command. The docs point to the [per-client install guide](https://github.com/webforj/webforj-ai#clients) for the exact steps, so follow that guide. Cursor is an MCP-over-HTTP client, so the server address is `https://mcp.webforj.com/mcp`.

3. **Name webforJ in the prompt.** That's the cue the assistant uses to reach for the plugin instead of general Java knowledge. Ask for verification too:

   ```text
   Add a webforJ view at /orders. It takes the existing OrderService
   through the constructor and shows its summary for the customer name
   typed into a text field. Verify the API against the webforJ docs.
   ```

4. **Review the view it writes.** Look for a `@Route` class that receives the bean through its constructor. The next section shows the shape.

5. **Run the app.** The archetype sets a default Maven goal, so this compiles the app and starts it on port 8080:

   ```bash
   mvn
   ```

## Build a Spring Boot UI with Cursor {#build-a-spring-boot-ui}

This is the kind of view the assistant should hand back. A Spring service holds the logic:

```java title="OrderService.java"
@Service
public class OrderService {

  public String summary(String customer) {
    return "Open orders for " + customer + ": 3";
  }
}
```

The view is a `@Route` class. When Spring is on the classpath, webforJ creates the route through Spring, so the constructor receives the bean:

```java title="OrdersView.java"
@Route("/orders")
public class OrdersView extends Composite<Div> {

  public OrdersView(OrderService orders) {
    TextField customer = new TextField("Customer");
    Paragraph summary = new Paragraph();
    customer.onModify(e -> summary.setText(orders.summary(customer.getValue())));

    getBoundComponent().add(customer, summary);
  }
}
```

The route class needs no Spring annotation. The `@Route` annotation alone enables both route discovery and constructor injection. See [Spring routing](/docs/integrations/spring/routing) for how webforJ creates a fresh instance on each navigation.

## Check the result with a test {#check-with-a-test}

A generated view that compiles can still do the wrong thing. TestBed runs views in a plain JUnit test with no browser. In a Spring Boot app, `@SpringBootTest` starts the context and `@WebforjTest` registers the view. TestBed needs webforJ 26.03, and the Spring pieces are in the [Spring testing guide](/docs/integrations/spring/testing).

```java title="OrdersViewTest.java"
@SpringBootTest
@WebforjTest(routeClasses = OrdersView.class)
class OrdersViewTest {
  @Autowired
  private OrderService orders;

  @Test
  void shouldShowTheSummaryFromTheService(WebforjTestbed context) {
    context.navigate(OrdersView.class);

    UserInteraction.type(
        ComponentLocator.of(TextField.class).byLabel("Customer").locate(), "Ada");

    verifyThat(ComponentLocator.of(Paragraph.class).locate())
        .hasText(orders.summary("Ada"));
  }
}
```

Ask the assistant to write this test with the view, then run `mvn test`. If it fails, paste the failure back into the chat. This write, test, fix loop is the one described in [the AI verification loop](/blog/ai-verification-loop-java). For more on tests without a browser, read [Java UI testing without a browser](/blog/java-ui-testing-without-browser).

## Prompts that work in Cursor {#prompts-that-work}

The [MCP server docs](/docs/ai-tooling/mcp) give a few habits worth keeping:

- Mention **webforJ** in every prompt.
- Be specific. A named project and archetype beat a vague request like make an app.
- Ask for verification, such as verify against webforJ docs.
- If the assistant answers without consulting the server, say use the webforJ MCP server to answer.

## Frequently asked questions {#faq}

### Does the webforJ AI plugin work with Cursor? {#faq-cursor}

Yes. Cursor is one of the clients that support the plugin and the Agent Skills. Cursor uses manual configuration, and the exact steps are in the [per-client install guide](https://github.com/webforj/webforj-ai#clients).

### Can Cursor build a Java web app without JavaScript? {#faq-no-javascript}

Yes. The views are Java classes, so the assistant writes them in the same language as your services and you review them the same way.

### Can I use my existing Spring services? {#faq-spring-services}

Yes. Any bean in the Spring context can be a constructor parameter of a `@Route` class, including services and Spring Data repositories.

## Ship one view with a passing test {#ship-one-view}

- Project created with the `webforj-spring` flavor or startforJ with **Spring Boot**.
- Cursor configured with the steps from the per-client install guide.
- "webforJ" named in the prompt, with a request to verify against the docs.
- View is a `@Route` class and takes Spring beans in its constructor.
- Test added with `@SpringBootTest` and `@WebforjTest`, and `mvn test` passes.
- Generated code read by a person before it ships.
