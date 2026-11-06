---
title: "Is webforJ production ready? What's behind the name"
description: "Is webforJ production ready? The name is newer than the foundation under it: BASIS International, BBj and the DWC. Here are the facts behind the doubt."
slug: is-webforj-production-ready
date: 2026-10-30
authors: webforJ
tags: [community, web development, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-30-is-webforj-production-ready/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-30-is-webforj-production-ready/cover.jpg)

You are about to pick a UI framework for an app that has to run for ten years, and the name webforJ is one you only heard recently. Asking "is webforJ production ready" is the right question. The short answer is yes, and the reason is that webforJ, the full-stack Java framework, is a newer name on a foundation that is decades old. It is a Java API built by BASIS International, on top of BBj and the Dynamic Web Client (DWC), shipped through Maven Central with regular numbered releases and Spring Boot integration.

<!-- truncate -->

This post takes the doubts one at a time, myth by myth.

## Myth: webforJ is brand new {#myth-new}

**Fact:** the name is recent, the technology is not. webforJ is a Java API around the DWC, the browser client that BBj has shipped to developers. BBj is the foundation: as the [architecture documentation](/docs/architecture/controls-components) explains, every webforJ component shipped with the product maps one to one to an underlying BBj control. You build on that proven foundation entirely in Java.

That mapping is the point. When you place a `Button` or a `Table` on a page, you are not trusting a UI toolkit that was written last year. You are driving components that sit on a client and a server runtime that BASIS has been building and supporting for years. The webforJ layer is the part that is new, and it is the part you can read, test and upgrade like any other Java library.

## Who is behind webforJ {#who-is-behind}

webforJ is built by BASIS International. The company page says BASIS has "almost 40 years" of experience helping organizations modernize mission-critical systems, and "20+ years of delivering technology built on Java." The BASIS site says its mission "since 1985" is to blaze a trail, and that in 1985 it released the first generation of Business BASIC.

BASIS is privately owned and operated. It runs its own sites in North America and Europe, with the US base in Albuquerque, New Mexico, and the European base in Saarbrucken, Germany. Support is staffed in both regions, with separate email addresses and phone numbers published on the [company page](https://webforj.com/company/). It also works with partners in more than 30 countries.

For a procurement conversation, this is the answer to "is there a company behind it." There is a named, privately owned company with offices on two continents, and its mission page dates back to 1985.

## Myth: a new framework has no lineage {#myth-lineage}

**Fact:** the lineage runs in a straight line. BBx was the first generation of Business BASIC in 1985. BBj is described by BASIS as the Java-based sixth generation, "Business BASIC on Java". The DWC arrived on top of BBj as a browser client, announced in May 2021 with BBx and Barista 21.0. webforJ is the Java API around that client.

BASIS also publishes how its Java baseline moves forward. BBj 20.10 ended support for Java 8. BBj 20.30 was the first release built entirely with Java 11. BBj 24.00 is built with Java 17 and runs with either Java 17 or Java 21. webforJ 26.00 requires Java 21 or later, and Java 25 is also supported. The stack has moved to newer Java versions over time, which is not what a prototype does.

## Is webforJ production ready? {#production-ready}

Production readiness is about what happens after the first release, so look at the cadence. These are the webforJ versions published from April 2024 onward.

| Year | Versions |
|---|---|
| 2024 | 24.00, 24.01, 24.02, 24.10, 24.11, 24.12, 24.20 |
| 2025 | 24.21, 24.22, 25.00, 25.01, 25.02, 25.03, 25.10 |
| 2026 | 25.11, 25.12, 26.00, 26.01, 26.02 |

Version 24.00 went live on April 8, 2024, and 26.02 followed on August 31, 2026. The major number moves about once a year, with feature releases in between. The version numbers are shared with BBj, which keeps the whole stack on one release train.

The upgrade path is also tooling, not a rewrite. Version 26.00 introduced `webforj-rewrite`, a set of OpenRewrite recipes that move a project to the new major. Spring Boot support moved with it, to 4.0.4 from 3.5.7. You can read the details in the [Spring Boot integration guide](/docs/integrations/spring/spring-boot).

Distribution is ordinary Java distribution. The artifacts are on Maven Central, so the dependency lands in your build the same way every other library does.

## Myth: nobody runs it in production {#myth-nobody}

**Fact:** there are named customers. The [success stories](https://webforj.com/success-stories/) include Prodin and b+p Personal. The company page lists myneva, Midata, Prodin and Bisco Industries as businesses running on BASIS.

Prodin is a good one to read. It is described as a Dutch ERP software provider with over 45 years of experience. Within four months it delivered its first web-based modules to customers, with a six-member Java development team, and it reused Java-compatible business logic that had been written over two decades earlier. That is the pattern webforJ is designed for: a team that already has valuable code and needs a web front end, not a rewrite.

The same pages carry testimonials about support. One says "The quality of the technical support is stellar."

## Myth: it will not fit our Java stack {#myth-stack}

**Fact:** webforJ is a Java framework, and it works with the tools a Java team already uses. You build with Maven, you run on Java 21 or later, and you can add [Spring Boot](/docs/integrations/spring/spring-boot) so that services and repositories are injected into your UI classes through the constructor. Your build, your CI and your code review process stay the same.

The browser side is covered by the DWC, so for standard components there is no second codebase in JavaScript to staff and maintain. Fewer moving parts means fewer things to keep current.

## The enterprise checklist {#enterprise-choice}

Teams looking for an established Java web framework for enterprise use usually check the same things. This is how webforJ answers them with published facts.

| Question | Answer |
|---|---|
| Who builds it | BASIS International, privately owned, mission since 1985 |
| Where are they | Albuquerque (North America) and Saarbrucken (Europe) |
| Java experience | "20+ years of delivering technology built on Java" |
| How is it released | Numbered releases shared with BBj, from 24.00 in April 2024 to 26.02 in August 2026 |
| How is it distributed | Maven Central |
| Does it fit Spring | Yes, with [Spring Boot integration](/docs/integrations/spring/spring-boot) |
| Who uses it | Prodin, b+p Personal, myneva, Midata, Bisco Industries |
| How do you get help | Direct support in North America and Europe, partners in more than 30 countries |

That is a full-stack Java framework backed by a company, with the history to match. For a business app, the combination of a Java API, a client with its own track record and a vendor that has worked with enterprise customers since 1985 is a strong base to build on.

## Try it on your own code {#next-step}

The quickest way to settle the question is to generate a project and judge it. [startforJ](https://docs.webforj.com/startforj) builds a Maven project in a minute, and the [getting started guide](/docs/introduction/getting-started) walks through the first run. Pick one screen from your current app and build it. Production readiness shows up fast when it is your own data and your own build.

## Frequently asked questions {#faq}

### Is webforJ production ready? {#faq-production-ready}

Yes. webforJ has been released on a regular schedule since 24.00 in April 2024, and 26.02 shipped in August 2026. It sits on BBj and the DWC from BASIS International, and named customers such as Prodin use it for business apps.

### Who is behind webforJ? {#faq-who-behind}

BASIS International, a privately owned company with sites in North America and Europe and partners in more than 30 countries. Its company page describes almost 40 years of helping organizations modernize mission-critical systems and 20+ years of delivering technology built on Java.

### Does webforJ keep up with new Java versions? {#faq-long-term}

webforJ belongs to a product line with a long history and a visible release record. Version 26.00 requires Java 21 or later and also supports Java 25. Upgrades between majors come with OpenRewrite recipes.

### Does webforJ work with Spring Boot? {#faq-spring}

Yes. You can generate a Spring Boot webforJ project with startforJ or Maven archetypes and inject Spring-managed beans into your UI components. The [Spring Boot guide](/docs/integrations/spring/spring-boot) shows the setup.
