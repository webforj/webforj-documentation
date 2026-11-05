---
title: "AI actions that update a Java UI: open and change webforJ views"
description: "Let an AI open your Java view with typed input, then update it with actions and an update tool. A deep dive into webforJ MCP Apps and the open view."
slug: mcp-tool-update-ui
date: 2026-10-12
authors: Hyyan Abo Fakher
tags: [ai, mcp, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-12-mcp-tool-update-ui/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-12-mcp-tool-update-ui/cover.jpg)

You ask an AI assistant to open a screen, and it does. Then you ask for a change, and a second copy of the screen appears under the first, because the assistant only knows how to open things. The view you're looking at never moves.

AI actions that update a Java UI fix that. With webforJ MCP Apps, the AI opens your routed view with typed input, then changes the same live view through actions and an update tool, while your Java code keeps running on the server. This post walks through each piece with code from a working playground app built to exercise all of them.

<!-- truncate -->

The playground view has three ways to be driven by a model: an opening call, an update call, and custom actions. Along the way I'll point out the choices that are easy to get wrong.

## The problem with chat-only answers {#the-problem}

Ask an assistant a question and most backends return text or JSON. The model then writes a paragraph about it. The user can't sort it, filter it, or click into anything.

What you want is the model to open your actual screen, already pointed at the right data, and to change that screen on a follow-up instead of pasting a second one under it. That is what MCP Apps in webforJ does. The AI client, called a host, renders your routed view inside the conversation, and your Java code keeps running on the server with the same components, services and state it uses in a browser.

If you haven't set up the server side yet, the [Spring Boot setup](/docs/integrations/mcp-apps/spring) covers the dependencies and endpoint, and [testing](/docs/integrations/mcp-apps/testing) shows how to connect a client. Here I only look at the interaction between the model and the open view.

## Define the opening input {#define-the-input}

The model needs to know what it can pass when it opens the view. One record describes it, and Jackson annotations feed the generated schema. In the playground, the opening input is a name and an optional note:

```java
public record OpeningInput(
    @JsonPropertyDescription("Name displayed by the playground") String name,
    @JsonPropertyDescription("Optional note displayed by the playground") String note) {
}
```

I treat the description text as part of the API. The model reads it to decide what to send, so a vague description gets you vague arguments.

## Open the view with input {#open-the-view}

Add `@McpApp` to a routed view and one `@McpAppInput` method. webforJ renders the route first, then calls your method on that same view instance:

```java
@Route("/")
@McpApp(name = "playground", description = "Exercises webforJ MCP app inputs, updates, actions, and host APIs.")
public class McpApiPlaygroundView extends Composite<FlexLayout> implements McpAppUpdateObserver {
```

```java
@McpAppInput
void applyOpeningInput(OpeningInput input) {
  openingName.setValue(valueOrEmpty(input.name()));
  openingNote.setValue(valueOrEmpty(input.note()));
  append("Opening input received.");
}
```

`openingName` and `openingNote` are read-only text fields in the view, and `append` writes a line to an activity log, so you can watch every call arrive. A few details matter:

- Every `@McpApp` needs a nonblank description. The model reads it to decide when the tool is relevant, so write it for the model, not for yourself.
- Set `name` explicitly, as the playground does. Without it webforJ derives the tool name from the route, and the root route becomes `app`. A renamed route would silently rename the tool.
- The generated opening tool navigates without route parameters. A route like `/orders/:id` can't be exposed this way. Use a parameter-free route and put the value in the opening input.

Pick exactly one way to declare the input. `@McpAppInput`, `input = OpeningInput.class` on `@McpApp`, or an `inputSchema` document all define it, and combining them is rejected when the app is discovered. I use `@McpAppInput` whenever the view must apply the values after it renders, which is nearly always.

## Update the open view {#update-the-open-view}

Now the follow-up. The user asks the model to show a different name. The model should not open a second copy. It should change the one on screen.

Implement `McpAppUpdateObserver`, as the class declaration shown earlier does, and webforJ publishes one extra tool, `playground_update`, with the same input schema as the opening tool:

```java
@Override
public CallToolResult onMcpAppUpdate(McpAppUpdateEvent event) {
  OpeningInput input = new OpeningInput(event.getArguments().path("name").asString(null),
      event.getArguments().path("note").asString(null));
  applyOpeningInput(input);
  append("Update input received.");
  return CallToolResult.builder().addTextContent("Playground updated.").build();
}
```

Two things to understand here. First, the update tool has no UI resource attached. Calling it never opens the route or renders another view. It is routed to the view already open in the same MCP session. If nothing is open, the call returns an error that directs the client to call `playground` first.

Second, webforJ does not apply the arguments to your components for you. The callback receives them and you decide what they mean. That is the right default. An update is rarely a blind copy of fields, and you often want to validate the code, clamp a range or reject a value the user's role can't see.

The returned `CallToolResult` is what the model reads back, so it is part of your API just like the input schema. A short sentence such as "Playground updated." lets the model confirm the change in plain words. Keep it short and factual.

## Add actions for distinct operations {#add-actions}

The update tool mirrors the opening input. Operations with their own input belong in actions. Annotate a view method with `@McpAppAction` and webforJ publishes it as another tool. The playground has two, each with its own input record:

```java
public record EchoInput(
    @JsonProperty(required = true) @JsonPropertyDescription("Text returned unchanged") String text) {
}

public record AdditionInput(
    @JsonProperty(required = true) @JsonPropertyDescription("First integer") Integer left,
    @JsonProperty(required = true) @JsonPropertyDescription("Second integer") Integer right) {
}
```

```java
@McpAppAction(description = "Adds two integers and records the action in the playground.")
Map<String, Object> addNumbers(AdditionInput input) {
  Map<String, Object> result = new LinkedHashMap<>();
  result.put("left", input.left());
  result.put("right", input.right());
  result.put("sum", input.left() + input.right());
  append("Action playground_add_numbers completed.");
  return result;
}
```

For an app named `playground`, the method `addNumbers` becomes the tool `playground_add_numbers`, and `echo` becomes `playground_echo`. Set `name` on the annotation to choose that suffix yourself. An action takes either no parameter or one object parameter, and the object's properties become the tool's input schema. The return type decides the result:

- `CallToolResult` is returned as is.
- Any other non-`void` value becomes structured content, like the map with `left`, `right` and `sum`.
- A `void` method returns a completion message.

Actions can also live in a separate class listed in `@McpApp(actions = ...)`. In that case each action must accept the running view as a parameter. I do this once a view has more than three or four actions, because the view class otherwise turns into a list of tool entry points.

## Action or update: which one? {#action-or-update}

This is the choice I see people get backwards.

| Situation | Use |
| --- | --- |
| Same fields as the opening call, just new values | `McpAppUpdateObserver` |
| A separate operation with its own input | `@McpAppAction` |
| Needs different validation or permission handling | `@McpAppAction` |

A view can use both, as the preceding examples do. Keep the update observer for pointing the view somewhere else, and keep actions for verbs like add, export, or approve. When you put a verb into the update observer you end up parsing a mode flag out of arguments, and the schema stops telling the model what is possible.

## Test the whole sequence {#test-the-sequence}

A tool that works once proves little. The order of calls is what the design depends on, so I test it as a script and not as separate calls.

1. Call `playground` with a name and confirm the view shows it in the opening-input fields.
2. Call `playground_update` with a second name and confirm the same view changes, with no second copy appearing.
3. Call `playground_add_numbers` and read the structured result with the sum.
4. Close the view, call `playground_update` again, and confirm the error that points back to `playground`.

The playground's activity log makes each step visible as it happens. Step four is the one teams skip. It tells you whether your tool descriptions are clear enough for a model to recover. The [testing guide](/docs/integrations/mcp-apps/testing) explains how to connect an MCP Apps capable host, including MCPJam on localhost for quick checks.

Also check what each tool returns when the input is wrong. If `onMcpAppUpdate` receives a value your app can't use, return a clear text result that names the problem, because the model will repeat that text to the person.

## Know when the view isn't open {#when-the-view-isnt-open}

Actions and the update tool appear in the tool list even when the app is closed. Calling them succeeds only while the matching view is rendered in the same MCP session. That is by design, since the tools act on a live instance.

Two consequences:

- Don't assume call order in your own tests. Open the view, then call the action, and also test the closed case to confirm the client receives the error that points it back to `playground`.
- The tools act on a rendered instance, so a call only works while the matching view is rendered in that session.

If your own background code needs to change the view's components, push the change through `Environment.runLater(...)`, as described in [asynchronous updates](/docs/advanced/asynchronous-updates).

## Let the view talk back {#let-the-view-talk-back}

Opening and updating is one direction. The other direction, where choices the person makes in the UI reach the model, is covered in [host interaction](/docs/integrations/mcp-apps/host-interaction). Together they make the view a shared surface. The model sets the context and the person works with the real components.

I'd also keep the browser route working. The playground still loads at `/` in a regular browser, and the opening tool always returns text content, so hosts with stricter rendering policies still get a useful answer. Host support for MCP Apps varies as the MCP specification evolves, so test each host you target.

## Next step {#next-step}

Pick one screen your users already ask questions about, give it a parameter-free route and an input record, and add `@McpApp`. Then add the update observer. The full reference is in [open a view with input](/docs/integrations/mcp-apps/opening-apps) and [add tools for an open view](/docs/integrations/mcp-apps/actions-updates), and the [MCP Apps overview](/docs/integrations/mcp-apps/overview) shows the whole picture.
