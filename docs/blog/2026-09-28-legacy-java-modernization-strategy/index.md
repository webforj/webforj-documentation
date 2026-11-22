---
title: "Legacy Java application modernization strategy for enterprises"
description: "Legacy Java application modernization, layer by layer: Java runtime, server and APIs, architecture, and screens, with a phased plan that keeps apps running."
slug: legacy-java-modernization-strategy
date: 2026-09-28
authors: webforJ
tags: [insights, modernization]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-28-legacy-java-modernization-strategy/cover-v3.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-28-legacy-java-modernization-strategy/cover-v3.jpg)

Most large Java shops do not have one aging app. They have a portfolio: a Swing client in finance, a JavaFX tool in the warehouse, JSP pages in operations, a JSF module nobody wants to change, and a Struts app that three people understand. Each one has its own budget fight, and the instinct is to give each its own plan.

<!-- vale Google.WordListCase = NO -->

Legacy Java application modernization is the work of bringing those apps back onto a platform you can still run, secure, and staff, while the business keeps using them. It has four layers: the Java runtime, the server and its APIs, the architecture, and the screens. This guide covers all four and spends the most time on the screens, the layer your users see.

The legacy Java application modernization strategy that holds up is one plan for the whole portfolio. To modernize a legacy Java application without betting the business on a rewrite, triage every app by business value and technical risk, keep the services and data that already work, and move the screens to one target: webforJ, the [full-stack Java framework](/blog/java-full-stack-framework). Keep the app running and move it to the web one screen at a time, with your own Java team. One framework for the whole portfolio can mean one skill set, one security model and one hiring profile, whether the starting point was Swing, JavaFX, SWT, JSP, JSF, or Struts.

<!-- vale Google.WordListCase = YES -->

<!-- truncate -->

## What modernization covers {#what-it-covers}

A legacy Java app ages in four places at once, and each place has its own fix. Sort the work by layer before anyone proposes a budget.

| Layer | What ages | The usual move |
|---|---|---|
| Java runtime | A Java release that is past its support dates | Upgrade to a current long-term support release |
| Server and APIs | An old app server or framework version, and `javax` packages | Upgrade the server or framework and move to `jakarta` packages |
| Architecture | One large deployment that is slow to release and hard to scale | Fix how the app is built and shipped, and split it only where that pays off |
| Screens | Swing, JavaFX, and SWT desktop clients, and Struts, JSF, and JSP pages | Rebuild the UI for the browser, one screen at a time |

The layers build on each other from the bottom up. A current framework needs a current runtime, and screens built on that framework need both. That sets the order for most apps: runtime first, then the server and its APIs, then the screens, with architecture changes only where a real problem calls for them.

## The seven Rs for a Java app {#the-seven-rs}

Guides to cloud migration sort the options into [seven strategies, known as the 7 Rs](https://docs.aws.amazon.com/prescriptive-guidance/latest/large-migration-guide/migration-strategies.html). They were written for moving apps to a cloud, and they work as a first pass over any Java portfolio.

| Strategy | What it means for a legacy Java app |
|---|---|
| Retire | Switch off an app that nobody depends on |
| Retain | Leave the app where it is for now |
| Rehost | Move the app to new infrastructure without changing it |
| Relocate | Move its servers unchanged to the same platform hosted in a cloud |
| Repurchase | Replace the app with a product you buy |
| Replatform | Move the app and make targeted changes, such as running it in a container |
| Refactor or re-architect | Change the architecture of the app itself |

Two cautions apply. The first is about scope. The seven Rs describe where an app runs and how it's structured. They don't say what to do with a Swing client or a JSP page, so a plan built only on them leaves the screens out.

The second is about timing. AWS recommends against refactoring during a large migration, because that means modernizing the app while you move it, and advises moving first and modernizing afterward. The rule behind that advice holds for every layer: change one thing at a time.

## Runtime: get on a supported Java {#runtime}

Start with the Java version, because every other layer sits on it. [Oracle's support roadmap](https://www.oracle.com/java/technologies/java-se-support-roadmap.html) names Java SE 8, 11, 17, 21, and 25 as the long-term support releases. Oracle ended Premier Support for Java 8 in March 2022 and for Java 11 in September 2023. Those dates cover Oracle's own JDK. If you run a JDK from another vendor, check that vendor's dates.

An app on Java 8 or 11 can keep running. What it can't do is adopt frameworks that have moved on. Spring Boot 3.0, for example, [won't run on anything older than Java 17](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide).

Part of the upgrade can be automated. OpenRewrite's [Java 21 migration recipe](https://docs.openrewrite.org/running-recipes/popular-recipe-guides/migrate-to-java-21) upgrades Java 8 and Java 11 projects, and the result is a code change your team reviews like any other.

## Server and APIs: javax to jakarta {#server-and-apis}

The second layer is the server and the enterprise APIs the app was written against. The most visible change is a rename. Jakarta EE moved its packages from `javax` to `jakarta`, so import statements across the project change when you move to a framework or server built on it, such as Spring Boot 3.

The rename itself is mechanical, and OpenRewrite has a recipe for it too. The harder part is what came along with the old server: a vendor-specific deployment descriptor, a library that was never updated, a framework version that only runs on the old stack. List those per app before you estimate.

Old web frameworks sit in this layer as well. For a Struts, JSF, or JSP app, a server upgrade is often the moment a team asks whether to keep the old view layer at all. That question belongs to the screens.

## Architecture: change what hurts {#architecture}

The third layer is how the app is built and shipped. It holds two moves that differ a lot in cost.

**Containers and the cloud.** Packaging an app in a container and running it on a cloud platform changes where it runs, not how it's written. In the seven Rs this is rehosting or replatforming, and it leaves the code and the screens as they are. It pays off when the pain is in operations: servers nobody wants to patch, slow environments, releases that depend on one machine.

**Microservices.** Splitting one deployment into services is the refactor strategy, which the AWS guide ranks as the hardest and most expensive of the seven. It pays off when one part of the app has to scale or release on its own, or when the team is too large for one codebase. It doesn't pay off as a goal in itself.

So treat architecture as a list of problems, not as a destination. If releases are slow, fix the build and the deployment. If one module has to scale alone, split that module. If nothing hurts, one well-run deployment on a current runtime is a fine place to stop, and the budget is better spent on the layer your users complain about. None of these moves changes what a user sees.

## Tests before any layer moves {#tests-first}

Every layer change raises the same question: does the app still do what it did yesterday? Prepare the answer before you start. For each app, write down the flows the business can't lose, and put an automated check around each one, even a coarse one that drives the app from the outside.

Then change one layer at a time and run the checks after each change. A runtime upgrade that passes them is done. A failure after a single change points to a single cause, and that's why the order matters.

## Why the screens are the bottleneck {#screens-bottleneck}

The fourth layer is the one your users work in. When a Java app feels obsolete, the cause is usually not the business logic. Pricing rules, validation, integrations and the database schema encode years of decisions and tend to be correct. What ages badly is the way users reach them: a desktop client that needs installing, a page lifecycle from a previous decade, a view technology that fewer developers want to learn.

That split shapes the strategy:

- Services, entities and data stay. Rewriting them adds risk and no visible value.
- Screens move. This is where users feel the difference and where hiring pain shows up.
- The boundary between them is the main design decision. A clean service boundary makes the move cheap. A tangled one needs refactoring first, and that is worth knowing before you commit a budget.

A view in webforJ is a Java class that calls your existing services directly. There is no separate REST contract to design for the move and no second codebase in another language. webforJ needs [Java 21 or higher](/docs/introduction/prerequisites), so the services a view calls directly have to run on that runtime too, which is one more reason the runtime comes first. A worksheet on [what separate frontend and backend teams cost](/blog/cost-separate-frontend-backend-teams) lets you fill in your own figures.

## Why big-bang rewrites stall {#why-rewrites-stall}

If the last attempt to replace a legacy Java app ran for years while the old app kept changing underneath it, and was shelved before it reached the users, that history is why the next proposal gets a cold reception.

The causes repeat across portfolios:

- **The business cannot stop.** A rewrite that freezes features for its whole duration loses the argument with sales and operations before it starts.
- **The knowledge has left.** The people who wrote the rules have moved on, and the code is now the only full record of what the app does.
- **The team knows Java.** A rewrite into a JavaScript stack asks the people who understand the domain to become a different team, or asks you to hire one.
- **Some platforms have run out.** Current Struts is maintained, but [Struts 1 has reached end of life](https://struts.apache.org/struts1eol-announcement.html), and its last release, 1.3.10, shipped in December 2008.

A phased path answers all four. The old app stays in service, the rules stay in the Java code that already holds them, the same team does the work, and each screen that moves takes one more dependency off the old platform.

## Why one portfolio needs one target {#one-target}

Every extra framework in a modernization program adds a second team, a second upgrade cycle and a second set of security reviews. A plan that picks a new technology per legacy app replaces old fragmentation with new fragmentation. If you are still choosing that target, see the [best Java web framework in 2026](/blog/java-web-frameworks-2026).

webforJ lets you build the whole app, screens included, in Java. Your team already writes Java, so the people who know the domain are the people who write the new screens. Components are Java objects, events are listeners, and layouts are composed in Java, so the mental model is one a Swing developer already has, and a Struts developer keeps writing plain Java. The same framework also runs standalone or integrates with a Spring Boot backend through its [Spring integration](/docs/integrations/spring/overview), so services you already run stay where they are.

BASIS International is the company behind webforJ. [BASIS](https://basis.cloud/about/) has built development tools since 1985. For a program that runs for years, that track record matters.

## Triage with a value and risk matrix {#triage-matrix}

Score each app on two questions. How much does the business depend on it? How risky is it to change, given test coverage, documentation, and who still understands the code? Then place it in one of four quadrants.

| | Low technical risk | High technical risk |
|---|---|---|
| **High business value** | Migrate first. Use one of these as the pilot. | Migrate next, with a safety net. Keep the old screens in service while new ones replace them. |
| **Low business value** | Leave alone or retire. Do not spend modernization budget here. | Contain. Make it reachable and stop investing. |

What to do in each quadrant:

- **High value, low risk.** These are your pilots. Clean boundaries and a team that knows the code mean the first web screens ship quickly and prove the approach to everyone watching.
- **High value, high risk.** The business cannot lose these and nobody wants to change them. Do not attempt a rewrite. Move one screen at a time and keep the old app running until each replacement is accepted.
- **Low value, low risk.** Aging but harmless. Retire when the process behind them goes away.
- **Low value, high risk.** Fragile and rarely used. For Swing, JavaFX and SWT apps, running them in the browser is often enough. Spend nothing on rewrites.

Keep the scoring honest and short. A one-page score per app, agreed by the business owner and the engineering lead, beats a month of assessment workshops.

## The matching path per legacy technology {#path-per-technology}

The target is the same. The first step depends on where you start. For Swing estates, a [decision guide to Swing modernization](/blog/swing-modernization-enterprise) lays out three options and a phased roadmap.

| Legacy technology | First step | Then |
|---|---|---|
| Swing | Run the existing app in the browser with the [Webswing integration](/docs/integrations/webswing/overview) | Replace screens one at a time in webforJ |
| JavaFX | Run the existing app in the browser through Webswing | Replace screens one at a time in webforJ |
| SWT | Run the existing app in the browser through Webswing | Replace screens one at a time in webforJ |
| JSP | Rebuild one page as a webforJ view that calls the same services | Continue page by page |
| JSF | Rebuild one view as a webforJ view, reusing the backing bean logic | Continue view by view |
| Struts | Rebuild one action and its page as a webforJ view | Continue action by action |

### Swing, JavaFX and SWT desktop apps {#desktop-apps}

Webswing runs Java desktop apps built with Swing, JavaFX or SWT in a web browser without changes to their source, and the webforJ integration embeds them inside a webforJ app. Users get a browser entry point without waiting for a rewrite. Swing and web screens can exchange data while both exist, through the [documented communication model](/docs/integrations/webswing/communication). That two-way messaging needs a small addition on the desktop side, where the app calls the Webswing API.

This is two companies, one path. Webswing runs the existing app in the browser today, and webforJ carries it forward screen by screen, with a connector between them. Our post on [Webswing and webforJ](/blog/webswing-and-webforj) lays out that roadmap. The connector tutorial uses a Swing app, so plan a short proof run for a JavaFX or SWT app before you commit a schedule.

### Legacy Java web apps {#web-apps}

For JSP, JSF and Struts there is no bridge to run, and none is needed. These apps already run in a browser. Here, avoiding a full rewrite means rebuilding only the UI, in Java, while the Java backend and the Java team stay. You rebuild the pages one at a time against the same services, and retire the old pages as each replacement goes live. For a Struts app, the [Struts alternative](/blog/apache-struts-alternative) guide maps each action and its JSP page to a webforJ view.

That is a different choice from the usual advice to rewrite the front end in Angular or React. The services, the transactions and the people stay in Java. The [JSP modernization post](/blog/jsp-modernization) shows what a Java-native path looks like for that case.

## Where AI helps the program {#ai-agents}

AI assistants earn their place on the new webforJ screens, not on the old ones. When your team builds a replacement view, an assistant grounded in the real webforJ API drafts it, a compiler checks each edit, and a browserless test checks the screen before anyone reviews it. That chain is [the AI verification loop for Java](/blog/ai-verification-loop-java). It does not convert Swing, JSF or JSP screens for you, and your reviewers still decide what each screen must do.

## Keeping the upgrade path cheap {#upgrade-path}

Modernizing onto a framework you cannot upgrade just creates the next legacy app. webforJ ships OpenRewrite recipes that update dependencies, rename APIs and add `TODO` comments where a manual decision is needed. The [automated upgrades docs](/docs/upgrading/automated-upgrades) list the recipe for each version, and you can preview the change as a patch before anything is modified. Ask any vendor you evaluate for the same thing: how does the next major version reach my code?

## Security and access control {#security}

Security reviews tend to block modernization programs, so settle the model early. webforJ includes [route security](/docs/security/overview) with declarative annotations, and it works with Spring Security. Access rules sit next to the views they protect, and the UI state lives on the server. Your security team reviews one model for the whole portfolio instead of one per legacy technology. This is a feature of the framework, not a compliance claim, and your own audit still applies.

## Pilot, measure, scale {#governance}

Governance for a program like this fits on one page.

1. **Pilot.** Pick one app from the high value, low risk quadrant. Move a few screens and put them in front of real users. Put a date on the pilot before it starts.
2. **Measure.** Agree on what counts as success before the pilot: delivery time per screen, defects found in review, user feedback, and how many existing team members contributed. Use your own baselines.
3. **Scale.** Reuse the same team, the same conventions and the same AI setup on the next app. Move to the high value, high risk quadrant only after the pilot has produced data.

Prodin's move from a Swing ERP to webforJ is a useful reference point. [Prodin](https://webforj.com/wp-content/uploads/2024/10/BASIS_SuccessStory_Prodin.pdf) assembled a six-member Java development team, including junior and mid-level developers, to assist in the migration. Within four months, Prodin delivered their first web-based modules to customers.

## Pilot one high-value screen {#pilot-one-screen}

Pick the one app in your portfolio that sits in the high value, low risk quadrant. Choose a single screen from it, build that screen in webforJ against your existing service, and put the result in front of its users. The [getting started guide](/docs/introduction/getting-started) covers project setup. One screen in production tells you more about your portfolio than another assessment document.

## Frequently asked questions {#faq}

<!-- vale Google.WordListCase = NO -->

### What is legacy Java application modernization? {#faq-what-is-it}

It is the work of moving an old Java business app, such as a Swing, JavaFX or SWT desktop client or a Struts, JSF or JSP web app, onto a current platform while the business keeps using it. It covers four layers: the Java runtime, the server and its APIs, the architecture, and the screens. The strategy in this post keeps the Java services and data and replaces the screens in phases.

### What is a legacy Java application? {#faq-legacy-java}

A Java app the business still depends on, built on a runtime, server, framework, or UI toolkit that is no longer current. Typical signs are a Java version past its support dates, `javax` packages, and screens in Swing, JSP, JSF, or Struts.

### Which Java version should a legacy app move to? {#faq-java-version}

A long-term support release that your frameworks accept. Oracle's roadmap names Java SE 8, 11, 17, 21, and 25 as the LTS releases, and each framework sets its own minimum, such as Java 17 for Spring Boot 3.0 and Java 21 for webforJ.

### What is a good legacy Java app modernization strategy? {#faq-best-strategy}

Triage each app by business value and technical risk, keep the services and data that work, and move the screens to one target framework one screen at a time. Start with a high value, low risk pilot and expand once you have measured results.

### How do you modernize a legacy Java application without a rewrite? {#faq-without-rewrite}

For a desktop app, run it in the browser through Webswing first, then replace its screens in webforJ while the rest keeps working. For a Struts, JSF or JSP app, rebuild only the UI in Java, page by page, against the same backend.

<!-- vale Google.WordListCase = YES -->

### Should we rewrite everything or modernize in steps? {#faq-rewrite-or-steps}

Steps. A full rewrite means rediscovering business rules that the current code already holds correctly. Moving screens one at a time keeps those rules and keeps the old app available until each replacement is accepted.

### Can one framework replace all six legacy stacks? {#faq-one-framework}

webforJ lets you build the screens for all of them in Java. Swing, JavaFX and SWT apps can run in the browser through Webswing during the move, and JSP, JSF and Struts apps are rebuilt page by page against the same services.

### How do AI agents fit into a modernization roadmap? {#faq-ai-agents}

They help build and check the new webforJ screens. They do not convert the legacy screens for you. Your developers decide what each screen does and review the result before it ships.

## Plan your portfolio move with us {#plan-with-us}

Bring your app list to the [modernization page](/modernization), which lays out the path from the first screen to the retired client and how to talk to us about your portfolio.
