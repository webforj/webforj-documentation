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

You are about to pick a UI framework for an app that has to run for ten years, and the name webforJ is one you only heard recently. Asking "is webforJ production ready" is the right question. The short answer is yes, and the reason is that webforJ, the full-stack Java framework, is a newer name on top of BBj and the Dynamic Web Client. It is a Java API built by BASIS International, on top of BBj and the Dynamic Web Client (DWC), shipped through Maven Central with regular numbered releases and Spring Boot integration.

<!-- truncate -->

This post takes the doubts one at a time, myth by myth.

## Myth: webforJ is brand new {#myth-new}

**Fact:** the name is recent, the technology is not. webforJ is a Java API around the DWC, the browser client that BBj has shipped to developers. BBj is the foundation: every webforJ component we ship maps one to one to an underlying BBj control, as our [architecture documentation](/docs/architecture/controls-components) shows. You build on that foundation entirely in Java.

That mapping is the point. When you place a `Button` or a `Table` on a page, you are not trusting a UI toolkit that was written last year. You are driving components that sit on a client and a server runtime from BASIS. The webforJ layer is the part that is new, and it is the part you can read, test and upgrade like any other Java library.

## Who is behind webforJ {#who-is-behind}

webforJ is built by BASIS International. [BASIS](https://webforj.com/company) software and software tools have been used to develop business solutions for both large international corporations and SMEs for almost 40 years, with 20+ years of delivering technology built on Java.

Operating its own sites in North America and Europe and working with partners in more than 30 countries, BASIS is well positioned on the international stage.

For a procurement conversation, these sentences answer "is there a company behind it."

## Myth: a new framework has no lineage {#myth-lineage}

**Fact:** the lineage runs in a straight line. [BASIS](https://basis.cloud) created the newest version of BBx®, the sixth generation, using Java technology. The DWC is a browser client on top of BBj. webforJ is the Java API around that client.

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

**Fact:** customers have published their results. Our [success stories](https://webforj.com/success-stories/) page lists Prodin, Audev's CarIT and more.

Prodin is a good one to read, and the [Prodin success story](https://webforj.com/wp-content/uploads/2024/10/BASIS_SuccessStory_Prodin.pdf) is a short PDF. Within four months, Prodin delivered their first web-based modules to customers. Prodin assembled a six-member Java development team, including junior and mid-level developers, to assist in the migration.

## Myth: it will not fit our Java stack {#myth-stack}

**Fact:** webforJ is a Java framework, and it works with the tools a Java team already uses. You build with Maven, you run on Java 21 or later, and you can add [Spring Boot](/docs/integrations/spring/spring-boot) so that services and repositories are injected into your UI classes through the constructor. Your build, your CI and your code review process stay the same.

The browser side is covered by the DWC, so for standard components there is no second codebase in JavaScript to staff and maintain. Fewer moving parts means fewer things to keep current.

## The enterprise checklist {#enterprise-choice}

Teams looking for a Java web framework for enterprise use usually check the same things. This is how webforJ answers them with published facts.

| Question | Answer |
|---|---|
| Who builds it | BASIS International |
| Where are they | Offices in Albuquerque (US) and Saarbrücken (Germany) |
| Java experience | "20+ years of delivering technology built on Java" |
| How is it released | Numbered releases shared with BBj, from 24.00 in April 2024 to 26.02 in August 2026 |
| How is it distributed | Maven Central |
| Does it fit Spring | Yes, with [Spring Boot integration](/docs/integrations/spring/spring-boot) |

That is a full-stack Java framework from BASIS International, built on BBj and the DWC.

## Try it on your own code {#next-step}

The quickest way to settle the question is to generate a project and judge it. [startforJ](https://docs.webforj.com/startforj) builds a Maven project in a minute, and the [getting started guide](/docs/introduction/getting-started) walks through the first run. Pick one screen from your current app and build it. Production readiness shows up fast when it is your own data and your own build.

## Frequently asked questions {#faq}

### Is webforJ production ready? {#faq-production-ready}

Yes. webforJ has been released on a regular schedule since 24.00 in April 2024, and 26.02 shipped in August 2026. It sits on BBj and the DWC from BASIS International, and our success stories page lists Prodin, Audev's CarIT and more.

### Who is behind webforJ? {#faq-who-behind}

BASIS International. BASIS software and software tools have been used to develop business solutions for both large international corporations and SMEs for almost 40 years, with 20+ years of delivering technology built on Java. Operating its own sites in North America and Europe and working with partners in more than 30 countries, BASIS is well positioned on the international stage.

### Does webforJ keep up with new Java versions? {#faq-long-term}

webforJ belongs to a product line with a long history and a visible release record. Version 26.00 requires Java 21 or later and also supports Java 25. Upgrades between majors come with OpenRewrite recipes.

### Does webforJ work with Spring Boot? {#faq-spring}

Yes. You can generate a Spring Boot webforJ project with startforJ or Maven archetypes and inject Spring-managed beans into your UI components. The [Spring Boot guide](/docs/integrations/spring/spring-boot) shows the setup.
