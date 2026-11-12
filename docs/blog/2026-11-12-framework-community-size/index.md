---
title: "From 207,000 to 937: community size no longer measures a framework"
description: "Stack Overflow questions fell from 207,000 a month to 937. Java, Spring Boot, React and every Java UI framework lost 95% or more since 2022. What that means for choosing a framework now."
slug: framework-community-size-ai-era
date: 2026-11-12
authors: Hyyan Abo Fakher
tags: [insights, ai]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-11-12-framework-community-size/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-11-12-framework-community-size/cover.jpg)

Every team that evaluates a framework asks some version of the same question: if we get stuck, will we find an answer? For twenty years the honest shortcut was to look at the crowd. A big community meant many forum threads and many people who had already hit your problem. A small one meant risk.

That shortcut is still how most comparisons work, and it's still how AI assistants rank frameworks when you ask them. The numbers say it stopped working. Community size was only ever a proxy for one thing, knowledge reaching you when you need it, and the engine behind that proxy has stopped running for large and small frameworks alike.

A different question takes its place: do the people who build the framework publish what's true for the version you run, in a form your AI assistant can read and test?

<!-- truncate -->

## The crowd stopped asking {#forum-decline}

For most of the last fifteen years, Stack Overflow was where that knowledge lived. These are the questions asked there in each month that were still on the site on November 12, 2026, the day I wrote this. Each number links to Stack Overflow's own search, so the live count can differ a little as old questions are deleted:

| Month | All questions | Tagged `java` |
|---|---|---|
| March 2014 | [207,169](https://stackoverflow.com/search?q=is%3Aquestion+created%3A2014-03-01..2014-03-31) | [21,693](https://stackoverflow.com/search?q=%5Bjava%5D+is%3Aquestion+created%3A2014-03-01..2014-03-31) |
| October 2022 | [105,743](https://stackoverflow.com/search?q=is%3Aquestion+created%3A2022-10-01..2022-10-31) | [5,212](https://stackoverflow.com/search?q=%5Bjava%5D+is%3Aquestion+created%3A2022-10-01..2022-10-31) |
| October 2024 | [23,161](https://stackoverflow.com/search?q=is%3Aquestion+created%3A2024-10-01..2024-10-31) | [1,229](https://stackoverflow.com/search?q=%5Bjava%5D+is%3Aquestion+created%3A2024-10-01..2024-10-31) |
| October 2026 | [937](https://stackoverflow.com/search?q=is%3Aquestion+created%3A2026-10-01..2026-10-31) | [40](https://stackoverflow.com/search?q=%5Bjava%5D+is%3Aquestion+created%3A2026-10-01..2026-10-31) |

October 2022 is the month before [ChatGPT launched](https://openai.com/index/chatgpt/). Four years later the whole site gets fewer than a thousand new questions a month, and the `java` tag gets about 40. Developers didn't stop having questions. They ask an AI assistant. In the [2025 Stack Overflow Developer Survey](https://survey.stackoverflow.co/2025/ai#1-ai-tools-in-the-development-process), 84% of respondents use or plan to use AI tools in their development process.

## Big or small, every crowd went quiet {#every-framework}

If this were a story about small frameworks losing ground, the largest ones would have held up. They didn't. These are the questions asked from January to October of each year, by tag:

| Tag | 2018 | 2022 | 2026 | Change since 2022 |
|---|---|---|---|---|
| Java | [122,293](https://stackoverflow.com/search?q=%5Bjava%5D+is%3Aquestion+created%3A2018-01-01..2018-10-31) | [57,742](https://stackoverflow.com/search?q=%5Bjava%5D+is%3Aquestion+created%3A2022-01-01..2022-10-31) | [1,057](https://stackoverflow.com/search?q=%5Bjava%5D+is%3Aquestion+created%3A2026-01-01..2026-10-31) | down 98% |
| Spring Boot | [15,949](https://stackoverflow.com/search?q=%5Bspring-boot%5D+is%3Aquestion+created%3A2018-01-01..2018-10-31) | [14,647](https://stackoverflow.com/search?q=%5Bspring-boot%5D+is%3Aquestion+created%3A2022-01-01..2022-10-31) | [374](https://stackoverflow.com/search?q=%5Bspring-boot%5D+is%3Aquestion+created%3A2026-01-01..2026-10-31) | down 97% |
| Swing | [2,588](https://stackoverflow.com/search?q=%5Bswing%5D+is%3Aquestion+created%3A2018-01-01..2018-10-31) | [1,254](https://stackoverflow.com/search?q=%5Bswing%5D+is%3Aquestion+created%3A2022-01-01..2022-10-31) | [55](https://stackoverflow.com/search?q=%5Bswing%5D+is%3Aquestion+created%3A2026-01-01..2026-10-31) | down 96% |
| JavaFX | [3,937](https://stackoverflow.com/search?q=%5Bjavafx%5D+is%3Aquestion+created%3A2018-01-01..2018-10-31) | [1,180](https://stackoverflow.com/search?q=%5Bjavafx%5D+is%3Aquestion+created%3A2022-01-01..2022-10-31) | [32](https://stackoverflow.com/search?q=%5Bjavafx%5D+is%3Aquestion+created%3A2026-01-01..2026-10-31) | down 97% |
| Thymeleaf | [1,115](https://stackoverflow.com/search?q=%5Bthymeleaf%5D+is%3Aquestion+created%3A2018-01-01..2018-10-31) | [586](https://stackoverflow.com/search?q=%5Bthymeleaf%5D+is%3Aquestion+created%3A2022-01-01..2022-10-31) | [5](https://stackoverflow.com/search?q=%5Bthymeleaf%5D+is%3Aquestion+created%3A2026-01-01..2026-10-31) | down 99% |
| Vaadin | [433](https://stackoverflow.com/search?q=%5Bvaadin%5D+is%3Aquestion+created%3A2018-01-01..2018-10-31) | [520](https://stackoverflow.com/search?q=%5Bvaadin%5D+is%3Aquestion+created%3A2022-01-01..2022-10-31) | [2](https://stackoverflow.com/search?q=%5Bvaadin%5D+is%3Aquestion+created%3A2026-01-01..2026-10-31) | down 100% |
| JavaServer Faces | [1,073](https://stackoverflow.com/search?q=%5Bjsf%5D+is%3Aquestion+created%3A2018-01-01..2018-10-31) | [274](https://stackoverflow.com/search?q=%5Bjsf%5D+is%3Aquestion+created%3A2022-01-01..2022-10-31) | [12](https://stackoverflow.com/search?q=%5Bjsf%5D+is%3Aquestion+created%3A2026-01-01..2026-10-31) | down 96% |
| Google Web Toolkit | [247](https://stackoverflow.com/search?q=%5Bgwt%5D+is%3Aquestion+created%3A2018-01-01..2018-10-31) | [48](https://stackoverflow.com/search?q=%5Bgwt%5D+is%3Aquestion+created%3A2022-01-01..2022-10-31) | [0](https://stackoverflow.com/search?q=%5Bgwt%5D+is%3Aquestion+created%3A2026-01-01..2026-10-31) | down 100% |
| React | [38,817](https://stackoverflow.com/search?q=%5Breactjs%5D+is%3Aquestion+created%3A2018-01-01..2018-10-31) | [67,175](https://stackoverflow.com/search?q=%5Breactjs%5D+is%3Aquestion+created%3A2022-01-01..2022-10-31) | [204](https://stackoverflow.com/search?q=%5Breactjs%5D+is%3Aquestion+created%3A2026-01-01..2026-10-31) | down 100% |
| Angular | [47,585](https://stackoverflow.com/search?q=%5Bangular%5D+is%3Aquestion+created%3A2018-01-01..2018-10-31) | [19,234](https://stackoverflow.com/search?q=%5Bangular%5D+is%3Aquestion+created%3A2022-01-01..2022-10-31) | [254](https://stackoverflow.com/search?q=%5Bangular%5D+is%3Aquestion+created%3A2026-01-01..2026-10-31) | down 99% |

React, the largest front-end crowd of them all, went from 67,175 questions to 204. Spring Boot went from 14,647 to 374. The Java UI frameworks, old and new, all landed in the same place: a few dozen questions a year, or none.

Version tags don't change the picture. For Java and Spring Boot, adding tags such as `java-17` or `spring-boot-3` moves the totals by less than one percent. For Vaadin, counting all its version tags raises 2022 to 605 and leaves 2026 at 2. Deleted questions aren't counted either, and recent months have had the least time for cleanup, so if anything these numbers flatter 2026.

A big community no longer produces a steady stream of fresh answers, and neither does a small one.

## What's left is frozen {#frozen-knowledge}

The crowd's knowledge didn't vanish. It froze, and frozen knowledge about software ages fast.

Java ships a feature release [every six months](https://openjdk.org/projects/jdk/), and 16 of them have shipped since Java 11 came out in September 2018.

Yet [1,413,912](https://stackoverflow.com/search?q=%5Bjava%5D+is%3Aquestion+created%3A..2018-09-24) of the 1,914,180 Java questions on Stack Overflow, 74%, were asked before Java 11 existed. 92% were asked before Java 17.

Spring Boot shows the same shape. [Spring Boot 3.0](https://spring.io/blog/2022/11/24/spring-boot-3-0-goes-ga) moved the baseline to Java 17 and Jakarta EE 9 in November 2022.

[125,312](https://stackoverflow.com/search?q=%5Bspring-boot%5D+is%3Aquestion+created%3A..2022-11-23) of its 150,223 questions, 83%, were asked before that release.

So the bigger a framework's archive, the more of it describes versions you no longer run. A large forum footprint now mostly measures the past.

## Assistants learned from the frozen archive {#assistants}

This matters because the archive is what AI assistants learned from. An assistant that answers from years of threads blends every version those threads discussed, and developers feel it. In the same [2025 survey](https://survey.stackoverflow.co/2025/ai#2-accuracy-of-ai-tools), more developers distrust the accuracy of AI tools (46%) than trust it (33%). The top frustration, named by 66%, is "AI solutions that are almost right, but not quite."

"Almost right" is what a blend of old versions produces: code that looks familiar, compiles against an API that moved, or follows a pattern the framework replaced two releases ago. More frozen threads don't fix that. They make it more likely.

## Knowledge now has to come from the makers {#makers}

If the crowd no longer writes the answers, the team that builds the framework has to. And the reader is no longer only a developer. It's the developer's AI assistant. That changes what good framework knowledge looks like:

- **Versioned.** It answers for the release in your build file, not the average of every release ever discussed.
- **Machine-readable.** It's published in a form an assistant can load whole, such as the [llms.txt format](https://llmstxt.org/), or queried live through an open protocol like the [Model Context Protocol](https://modelcontextprotocol.io/).
- **Checkable.** It comes with a fast way for the assistant to test what it wrote, so "almost right" fails before a person reviews it.

This changes who can compete. Under the old test, a newer framework could never catch up, because a crowd takes a decade to grow. Under the new one, size gives no head start.

## What the old archive is still good for {#archive-use}

The archive keeps its value for anything that doesn't change between versions. How collections behave, how threads work, how a transaction commits: an answer from 2014 on those is as right today as the day it was written.

Answers tied to a release are different. Configuration, annotations, build setup, and framework APIs move with every version, and that's where an old answer misleads. For those, the source has to be the people who ship the release.

## A better test than counting threads {#test}

If thread counts no longer tell you anything, what does? The numbers point to a test you can run yourself in an afternoon, and it measures what a community was supposed to provide.

Pick one screen your app needs, such as a form with validation or a table with paging. Ask your AI assistant to build it on the current release of each framework you're considering. Then look at three things:

1. **Does the code match the current version?** Removed APIs, old package names, and patterns the framework has replaced show that the assistant is answering from the frozen archive.
2. **Where did the answer come from?** If the makers publish versioned knowledge for assistants, the assistant can look it up and say so. If they don't, it's working from memory.
3. **Can the assistant check itself?** If it can run a test on what it wrote, an answer that's almost right fails before it reaches you.

What you see in that afternoon is what your team will live with for years: the quality of the answers you get for the version you run. No community metric measures that anymore.

## The archive isn't being refilled {#takeaway}

The drop in questions is easy to read as a story about one website. The larger point is what it does to everything built on that website. For fifteen years, developers wrote down what each new release broke and how to fix it, and that record is what search engines indexed and AI assistants learned from. At 937 questions a month, the record has almost stopped growing. Every release from now on widens the gap between what the public archive knows and what's true.

Only the people who make the software can close that gap. Where they do, an assistant answers from current knowledge. Where they don't, it answers from an archive that ages a little more with every release, however large the community once was.

webforJ shows what that looks like. It's the full-stack Java framework for business web apps, where you build the whole app, screens included, in Java. Its 24.00 release came out in April 2024, after the forums had already gone quiet, so it was designed for this from the start. Its knowledge ships with the framework: an [AI plugin](/docs/ai-tooling) answers for the exact version in your project, and a [TestBed](/docs/testing/testbed/overview) lets the assistant test what it wrote.

So the question to ask of any framework has changed. It used to be how many people are talking about it. Now it's whether its makers are writing down what's true for the version you'll run next year.
