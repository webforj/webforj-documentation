---
title: "Rewrite or migrate a legacy Java application: how to decide"
description: "Should you rewrite or migrate a legacy Java application? Why rewrites fail, when one is still right, and the incremental path that keeps your app running."
slug: rewrite-or-migrate-legacy-java-app
date: 2026-11-16
authors: webforJ
tags: [insights, modernization]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-11-16-rewrite-or-migrate-legacy-java-app/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-11-16-rewrite-or-migrate-legacy-java-app/cover.jpg)

Somewhere in your company there is a Java app that runs the business. It works, the rules inside it took years to get right, and the people who wrote most of it have moved on. Now it needs to reach the browser, and the meeting keeps circling back to one question. Do we start over, or do we move what we have?

<!-- vale Google.WordListCase = NO -->
This page is for the architect who has to recommend an answer and the budget holder who has to sign it. Whether to rewrite or migrate a legacy Java application is mostly a question of risk, and a rewrite carries the most of it. Our case is simple: keep your Java app running, and move it to the web one screen at a time, with your own Java team.
<!-- vale Google.WordListCase = YES -->

<!-- truncate -->

## Why rewrites fail so often {#why-rewrites-fail}

If your last rewrite failed, you are in good company. Large rewrites fail often, and they tend to fail the same way.

The plan starts with a clean slate and a date. Then the team finds out how much the old app really does. Every screen hides rules nobody wrote down, so the estimate grows while the old system keeps getting changes that the new one has to catch up with. Years in, the budget has been overstepped more than once, a part of the features is finished, and users still work in the old app every day. At that point the business either pays again or throws the project away.

None of this means your team is weak. It means a rewrite stacks several hard problems into one long project with no value delivered until the end.

## What a rewrite really asks of you {#what-a-rewrite-asks}

A rewrite looks like a coding project. It is mostly a discovery project with coding attached.

- **Rediscover every rule.** Validation quirks, rounding, the special case for one customer: the old code holds them and the documentation usually does not. For each odd behavior the rewrite team has to work out whether it is a bug, a feature, or a habit of the original author.
- **Freeze or fork the product.** Either new features stop while the rewrite catches up, or every change is built twice, once in the old system and once in the new one. Sales and customers notice both.
- **Depend on people who left.** The knowledge you need to rewrite safely is the knowledge that walked out the door, and a rewrite that runs for years loses more of it along the way.
- **Bet the budget on the last month.** Users see nothing until cutover. If funding stops halfway, the company owns a half-finished system and the old one it was meant to replace.

The risk comes down to one thing. You have to understand the system before you can rewrite it, or you end up with a different mess.

## Rewrite or refactor: the real question {#rewrite-or-refactor}

"Rewrite or refactor" sounds like a choice about code. For a business app it is really a choice about which layer is broken.

In most aging Java apps the business logic is not the problem. Pricing rules, validation, integrations and the database schema encode years of decisions and are usually correct. What ages badly is how people reach that logic: a desktop client that needs installing, a page technology fewer developers want to learn, a UI that cannot run in a browser.

When the logic is sound and the screens are the problem, rewriting everything spends most of the budget on the part that already works. Moving the screens, and keeping the logic behind them, puts the money where users feel the change. Our [legacy Java modernization strategy](/blog/legacy-java-modernization-strategy) covers how to triage a whole portfolio on that basis.

## When a rewrite is the right call {#when-to-rewrite}

A rewrite is not always wrong, and pretending otherwise would not help you decide. Starting over can make sense when:

- The business logic itself is wrong or no longer matches how the business works, so there is little worth keeping.
- The app is small enough that a team can rebuild it in one release cycle while the old one keeps running.
- The domain changed so much that the new system is a different product, not a new version of the old one.
- Nobody can run, build or deploy the existing code anymore, so nothing can be kept in place while you move.

Even in those cases a rewrite needs one thing first: a clear picture of what the app has to do. If you cannot write that picture down, a rewrite inherits the same gaps as the original.

## Five questions to settle first {#five-questions}

Put these to the team and the business owner before anyone estimates anything.

1. **Is the business logic correct?** If yes, the case for a full rewrite is weak from the start.
2. **Can the business stop shipping features?** If not, any plan that needs a long freeze is a plan on paper.
3. **Who still understands the app?** If the answer is one or two people, a rewrite depends on them for its whole length. An incremental move uses their time one screen at a time.
4. **What does your team know?** A Java team asked to rebuild in a new language and a new stack is learning while it rewrites. Two kinds of risk at once.
5. **Is a deadline forcing the move?** [Struts 1 reached end of life](https://struts.apache.org/struts1eol-announcement.html) with its last release in 2008, while current Struts 6 and 7 are maintained. Java Web Start left the JDK with Java 11, and JEP 504 [removes the Applet API](https://openjdk.org/jeps/504) after JDK 25. A deadline forces you to move. It does not force you to rewrite.

If most answers point to "the logic is fine, the business cannot stop, and the team knows Java", you already have your answer.

## Rewrite vs migrate, side by side {#rewrite-vs-migrate}

| | Full rewrite | Incremental migration |
|---|---|---|
| **First thing users see** | The new system, at cutover | A working app in the browser, then new screens one at a time |
| **If funding stops halfway** | A half-built system next to the old one | Every screen already moved stays in use |
| **Business rules** | Rediscovered and reimplemented | Kept and called from the new screens |
| **Feature work during the move** | Frozen, or built twice | Continues, in the old app or in the new screens |
| **Who does the work** | Often a new team or a new stack | Your existing Java team |
| **Where the risk sits** | One large bet at the end | Small steps you can review and stop |

Incremental is not free. You run old and new side by side for a while, and each screen still needs building and testing. But each step pays for itself, and you can stop after any of them. How to sequence the cutover itself is a separate decision from whether to rewrite at all.

## The incremental path, in Java {#incremental-path}

webforJ is a full-stack Java framework for business web apps. It gives the incremental path a concrete shape.

**For Swing, JavaFX and SWT desktop apps,** Webswing runs Java desktop apps in a web browser [without any modifications to the original source code](/docs/integrations/webswing/overview). webforJ embeds that running app in a webforJ view with the `WebswingConnector`, available since webforJ 25.10. Users open a URL instead of installing a client, and the old app keeps working the way they know it. Webswing and webforJ are partners: Webswing runs the app today, and webforJ carries it forward.

New screens are built in webforJ next to the embedded app, in the same browser tab. The two sides can exchange data both ways. That part needs a small addition on the Swing side using the Webswing Java API, which the [communication docs](/docs/integrations/webswing/communication) cover. Screen by screen, the webforJ side grows and the Swing side shrinks until it can be retired.

**For Struts, JSF and JSP web apps,** there is no bridge to run, because these apps already live in a browser. Here, avoiding a full rewrite means rebuilding the UI in Java, page by page, against the services you already have. You rewrite the screens, not the system.

In both cases the new screens are written the way a Java developer already thinks. Components are Java objects, events are listeners, and layouts are composed in Java. The business logic stays where it is, and if it already runs on Spring, the beans stay too. Existing Spring Data repositories, for example, [work with webforJ components without modification](/docs/integrations/spring/spring-data-jpa).

If your estate is Swing and you want the paths compared in detail, including a JavaScript rewrite, the [Java Swing to web migration guide](/blog/migrate-swing-app-to-web) lays them out with a decision table.

## Making the case to the budget holder {#budget-case}

A rewrite asks for one large approval with nothing to show until the end. The incremental path turns that into a series of small approvals, each with something users can open.

- **Step one buys reach.** The existing app becomes available in the browser. For desktop apps that means less installing and patching on every machine.
- **Each screen buys a visible improvement.** You pick the screens that matter most, and each one ships when it is ready.
- **Stopping is allowed.** If priorities change, what has moved stays moved. Nothing is written off.

Prodin, an ERP vendor, kept its Java business logic when it moved to the web with webforJ, some of it written over two decades earlier. By reusing its existing Java code, [Prodin avoided the expense of a complete rewrite](https://webforj.com/wp-content/uploads/2024/10/BASIS_SuccessStory_Prodin.pdf), and its first web-based modules shipped to customers within four months.

AI can do much of the porting, and each step stays easy to review. The [webforJ AI plugin](/docs/ai-tooling) connects your assistant to the webforJ documentation and API through the MCP server and Agent Skills, so it looks up methods instead of guessing them.

Ask the agent for a [TestBed](/docs/testing/testbed/overview) test with every screen it ports. The TestBed runs the view in a plain JUnit test, with no browser and no server, so the agent gets a pass or a fail on each change before a developer looks at it. That is the [AI verification loop](/blog/ai-verification-loop-java): write against the real API, then prove the behavior with a test. A developer still reviews every change. These tools work on the new webforJ screens, not on the Swing screens running inside Webswing.

Behind the framework is BASIS International, which has built tools for business software since 1985. For a budget holder, that means a vendor with a long record in exactly this kind of app.

## Frequently asked questions {#faq}

### Should we rewrite our Java app? {#faq-should-we-rewrite}

Usually not, if the business logic is correct and the business cannot stop shipping features. In that case, keep the app running and move it to the web one screen at a time. A rewrite makes more sense when the logic itself is wrong or the app is small enough to rebuild in one release cycle.

### Is it better to rewrite or refactor a legacy app? {#faq-rewrite-or-refactor}

For most business applications, keeping and refactoring the logic while replacing the screens costs less and carries less risk than starting over. A rewrite has to rediscover every rule the old code already holds, often without the people who wrote it.

### What is the difference between a rewrite and a migration? {#faq-rewrite-vs-migrate}

A rewrite replaces the whole system and delivers value at cutover. An incremental migration keeps the existing app working and replaces it in steps, so users get improvements along the way and the business rules carry over.

### Can the old app keep running while we migrate? {#faq-keep-running}

Yes. For Swing, JavaFX and SWT apps, Webswing runs the existing app in the browser, and webforJ embeds it next to the new screens in the same tab. Struts, JSF and JSP apps keep serving their old pages while new webforJ views replace them one at a time.

## Plan your first move with us {#plan-first-move}

See how the screen-by-screen path works for your app and talk to us about it on the [webforJ modernization page](/modernization).
