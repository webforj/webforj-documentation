---
title: "Parallel run migration: prove each new Java screen with a test"
description: "In a parallel run migration the old and new versions run side by side. Keep the old Java app running, ship new webforJ screens, and pin each with a TestBed test."
slug: test-migrated-java-screen
date: 2026-11-14
authors: webforJ
tags: [tutorial, modernization, webswing]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-11-14-test-migrated-java-screen/cover-v3.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-11-14-test-migrated-java-screen/cover-v3.jpg)

You have moved the first screen of an old Java app to the web, and it looks right. The next question is harder. Does it still behave the way the old screen did, and will it still behave that way after the next ten screens land? Nobody wants a user to be the one who finds out.

In a parallel run migration the old and the new version run side by side, and the old one is the reference you compare against. For a Java desktop app, Webswing keeps the existing app running in the browser while webforJ builds the replacement screens next to it in the same tab, so the old behavior is still there to check each new screen against. This guide gives every new webforJ screen a TestBed test, a plain JUnit test with no browser, that pins the behavior your users rely on. Each screen passes its tests before the next one starts.

<!-- truncate -->

## Why a ported screen needs a test {#why-a-test}

During a parallel run the old screen is still there to compare against, so the first check is easy. Open both, try the same steps, and see whether they match. The trouble starts later. Screen six changes a shared service or a common layout, and screen one breaks without anyone opening it again.

A test turns what you checked by hand into something the build checks on every change. The webforJ [TestBed](/docs/testing/testbed/overview), available since 26.03, runs a view inside a plain JUnit test. No browser is opened and no server is started. The test finds components the way a user does, acts on them, and checks what the page shows, and it runs in the same `mvn test` as your unit tests.

Be clear about what it tests. The TestBed tests the new webforJ screens. The Swing screens still running inside [Webswing](/docs/integrations/webswing/overview) are rendered on the server and streamed to the browser as an HTML5 canvas, so a TestBed test never reaches them. The old screen is your reference. The new screen is what the test pins.

For users, both sides can still look like one app. The [Swing look and feel](/docs/integrations/webswing/look-and-feel) gives the Swing screens the colors, fonts and buttons of webforJ with one JAR and one call at startup, so moving between an old screen and a new one doesn't feel like switching apps.

## Step 1: write down what users rely on {#step-1-list-behavior}

Before you port a screen, open the old one and list what a user reads and does on it, as plain sentences. Take a small subscribe form as the example:

1. The address field carries the label `Email`.
2. **Subscribe** stays disabled until the field holds an address.
3. After a click, the screen confirms the address it subscribed.

Each sentence becomes one test. Keep the sentences about what the user reads and does, not about how the old code was built. A TestBed test works through what the page shows, so it keeps passing when the view is built differently and fails when the user would see something else. That's exactly the property a port needs.

## Step 2: add the TestBed dependency {#step-2-dependency}

The TestBed is a separate module. Add it with test scope:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-testbed-junit</artifactId>
  <scope>test</scope>
</dependency>
```

The version comes from the webforJ Bill of Materials, so the dependency declares none. Spring Boot apps use the `webforj-spring-testbed` module instead, covered in [Spring testing](/docs/integrations/spring/testing).

## Step 3: build the new screen in webforJ {#step-3-build-screen}

The webforJ version of the form is one Java class. The button starts disabled, and a listener on the field turns it on once an address is typed:

```java title="NewsletterForm.java"
public class NewsletterForm extends Composite<Div> {

  public NewsletterForm() {
    TextField email = new TextField("Email");
    Paragraph status = new Paragraph();
    Button subscribe =
        new Button(
            "Subscribe", e -> status.setText("Subscribed " + email.getValue()));
    subscribe.setEnabled(false);
    email.onModify(e -> subscribe.setEnabled(!email.getValue().isBlank()));

    getBoundComponent().add(email, subscribe, status);
  }
}
```

## Step 4: pin each behavior in a test {#step-4-pin-behavior}

Sentences 1 and 3 from the list fit in one test. It finds the field by its label and the button by its text, types an address, clicks, and checks the confirmation. The test class carries `@WebforjTest`, which gives each test method its own `WebforjTestbed`:

```java title="NewsletterFormTest.java"
@Test
void shouldSubscribeTheEmailTheUserTyped(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
  var subscribe = ComponentLocator.of(Button.class)
      .byText("Subscribe")
      .locate();

  UserInteraction.type(email, "ada@example.com");
  UserInteraction.click(subscribe);

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Subscribed ada@example.com")
          .isPresent());
}
```

If someone changes that label to `E-mail address` during the port, `byLabel("Email")` finds nothing and the test fails. Users who learned the old screen notice that kind of change, so the test should too.

## Step 5: pin what a user can't do {#step-5-what-users-cant-do}

Sentence 2 is a rule, and rules are where ported screens drift. `UserInteraction` makes only the gestures a user could make, so clicking a disabled button throws an `InteractionRejectedException` and the listener never runs:

```java title="NewsletterFormTest.java"
@Test
void shouldRejectTheClickWhileNoEmailIsTyped(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var subscribe = ComponentLocator.of(Button.class)
      .byText("Subscribe")
      .locate();

  assertThrows(
      InteractionRejectedException.class,
      () -> UserInteraction.click(subscribe));

  assertFalse(
      ComponentLocator.of(Paragraph.class)
          .byTextContaining("Subscribed")
          .isPresent());
}
```

If a later change drops the `setEnabled(false)` line, the click goes through, nothing is thrown, and this test goes red.

## Step 6: pin a whole screen with a snapshot {#step-6-snapshot}

Some screens are mostly read: an order summary, a detail view, a confirmation page. Checking each label one by one takes a long test. A snapshot writes down everything a user would see in the view, one line per component, and stores that description next to the test. Here `OrderSummary` is a view with an order number, a note field, a hidden discount line, a **Pay** button and a disabled **Cancel** button. Its full source is on the [snapshots page](/docs/testing/testbed/snapshots):

```java title="OrderSummaryTest.java"
@Test
void shouldMatchTheSnapshot(WebforjTestbed context) {
  var summary = new OrderSummary();
  context.add(summary);

  verifyThat(summary).matchesSnapshot();
}
```

The first run writes the file and passes:

```snapshot title="OrderSummaryTest.shouldMatchTheSnapshot.yml"
# webforJ testbed snapshot v1
- OrderSummary [composite=Div] [node=div]:
  - Paragraph "Order 1042" [composite=Element] [node=p]
  - TextField [label=Note] [placeholder="Delivery instructions"]
  - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
  - Button "Pay" [theme=primary]:
    - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
  - Button "Cancel" [disabled]
```

Read that file next to the old screen. If it matches what users saw before, commit it. Every later run describes the view again and compares. When the **Pay** button turns into **Pay now**, `mvn test` fails with a plain diff:

```diff title="Terminal"
-  - Button "Pay" [theme=primary]:
+  - Button "Pay now" [theme=primary]:
```

When a change is intended, run `mvn test -Dwebforj.testbed.updateSnapshots=true` once and review the changed files like code. On a CI server the TestBed never writes snapshot files, so a missing snapshot fails the build instead of being written, and an update run fails too. An outdated snapshot fails on every machine until you review and update it. The [snapshots page](/docs/testing/testbed/snapshots) covers the format and the options.

## Step 7: go green before the next screen {#step-7-go-green}

TestBed tests are plain JUnit tests. `mvn test` runs them with the rest of the suite, and your CI runs them on every push. Make that the gate of the migration. A screen counts as moved when its tests pass, and the next screen starts after that.

This keeps the [strangler fig approach](/blog/strangler-fig-pattern-java-swing) honest. Users switch to the new screen while the old one stays available in Webswing, and every screen you already moved stays pinned while you work on the next. If an AI assistant writes the port, the same tests give it a pass or a fail before you review, which is the loop in [the AI verification loop for Java](/blog/ai-verification-loop-java).

## What a TestBed test doesn't cover {#limits}

A TestBed test checks what your views do when a user works with them. It leaves out two things:

- **The Swing screens.** They run inside Webswing and reach the browser as a canvas. The TestBed finds webforJ components only.
- **What only a browser shows.** Styling, layout and client behavior belong in a few browser tests. [Java web app testing strategy](/blog/java-web-app-testing-strategy) shows how to split the layers so the browser suite stays small.

For a full tour of locating, acting and asserting, read [Java UI testing without a browser](/blog/java-ui-testing-without-browser).

## Checklist for every ported screen {#checklist}

- [ ] The old screen's behavior is written down as plain sentences.
- [ ] Each sentence has a TestBed test on the new webforJ screen.
- [ ] Rules, such as a disabled button, have a test that tries the forbidden gesture.
- [ ] Mostly read screens have a snapshot that was checked against the old screen.
- [ ] Snapshot files are committed next to the tests.
- [ ] `mvn test` passes locally and in CI.
- [ ] Only then does the next screen start.

## Plan your migration with us {#plan-your-migration}

When you are ready to pick the first screens and plan the parallel run, [see how the path works and talk to us](/modernization).
