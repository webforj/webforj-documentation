---
title: "webforJ: AI-assisted, human-owned"
description: webforJ's stance on AI-generated code in open source frameworks
slug: webforj-human-owned
date: 2026-02-12
authors: webforJ
tags: [webforj, ai]
hide_table_of_contents: false
---

As AI coding tools, assistants, and agents become increasingly powerful, professional engineers and casual vibe coders alike can go from concept to compilation faster than ever before.
On top of that, meta-prompting systems like [get-shit-done](https://github.com/glittercowboy/get-shit-done) and [Auto-Claude](https://github.com/AndyMik90/Auto-Claude) automate entire development workflows, so that the AI doesn't just write the code, but verifies it as well. 

AI tools certainly accelerate output, and are very impressive at first glance.
But what impact are they having on code quality?
Can the open source ecosystem withstand the flood of AI-generated PRs?
How can developers use AI without sacrificing understanding and quality?

Research into these questions is still emerging, but the current findings would suggest that for code that require security, maintainability, and performance, it's best not to put too much trust in AI-written code.
This is why we've made a strategic choice at webforJ: **AI-assisted development, but human-owned code.**

<!-- truncate -->

## The quantity

As you might expect, there is plenty of data tracking the impact of AI on the sheer volume of code being produced.
For instance, GitHub's annual [Octoverse Report](https://github.blog/news-insights/octoverse/octoverse-a-new-developer-joins-github-every-second-as-ai-leads-typescript-to-1/) analyzes developer trends, providing insight into the use of AI on the platform.
Their findings over the last couple years show massive growth in developer output, along with increased usage of AI as a development tool:

In 2025, every metric of developer productivity on GitHub broke new records:
- 25% increase in code pushes
- 20% increase in pull requests
- 11% increase in new issues

This increase in output comes alongside increased usage of AI. GitHub's analysis found:
- AI-related projects have almost doubled in less than two years
- Six of the 10 fastest-growing repos were related to AI infrastructure
- Almost 80% of new developers started using GitHub Copilot within their first week

These numbers represent growth compared 2024, compounding on the trends from that year.
In 2024, [GitHub's report](https://github.blog/news-insights/octoverse/octoverse-2024/) also showed significant growth in AI development:
- 98% increase in generative AI projects on GitHub 
- 59% increase in contributions to these projects

According to over 49,000 responses to [StackOverflow's 2025 Developer Survey](https://survey.stackoverflow.co/2025/), 78.5% of developers currently use AI tools, with another 5.3% planning to use them soon.

Taken together, these trends reveal that usage of AI tools has become the norm, and is creating tangible impact on code volume.

## The quality

So what about the quality of all this code? 
Does the flood of commits stand up to scrutiny? 
These questions have been top of mind for many developers, and emerging research is starting to answer them.

StackOverflow's 2025 Developer survey found that positive sentiment toward AI tools dropped in 2025, falling to only **60% positive sentiment**.
Additionally, they found that trust for the output of AI tools is at about 33%, with trust generally decreasing relative to developer experience.

CodeRabbit's [State of AI vs. Human Code Generation Report](https://www.coderabbit.ai/blog/state-of-ai-vs-human-code-generation-report), which analyzed 320 AI-co-authored PRs and 150 human-authored PRs, found that AI-written code generally had more issues:
- 1.7x more issues overall
- More high-issue outliers
- 1.4x more critical issues
- 1.7x more major issues

Specifically, they found that AI code often contains mistakes in logic and correctness, with increased occurrences of logic errors, misconfigurations, and poor error or exception handling.

StackOverflow's survey found that 66% of developers using AI tools had the problem of "AI solutions that are almost right, but not quite," and 45% agreed that "Debugging AI-generated code is more time-consuming."
Only 4% responded that they haven't encountered any problems when using AI tools.

In addition, the METR study [*Measuring the Impact of Early-2025 AI on Experienced Open-Source Developer Productivity*](https://metr.org/blog/2025-07-10-early-2025-ai-experienced-os-dev-study/) found that experienced developers in large open source codebases took **19% longer** to complete tasks with the help of AI tools than without.

### Maintainability

GitClear analyzed over 200 million lines of code written between 2020 and 2024 in their [AI Copilot Code Quality](https://www.gitclear.com/ai_assistant_code_quality_2025_research) study.
They found that in 2024, copy/pasted lines of code exceeded moved lines of code for the first time, indicating **less refactoring** and **worse code reuse**, making code bases more difficult to maintain, and increasing the risk of future errors. 
From 2020 to 2024, they found that code reuse, as measured in moved lines, has decreased from 25% of all changes to less than 10% of all changes.
In just one year from 2023 to 2024, the percentage of commits that contained duplicated blocks of code rose dramatically, from just 1.8% to 6.6%.
Duplicated code blocks create technical debt and place undue burden on future developers, who have to find duplicated sections and determine whether they should all be updated, or if they serve different functions.

CodeRabbit's study found that AI-produced code often creates more technical debt, with issues of readability, formatting errors, inconsistent or unclear naming, and unused or redundant code all increased compared to human-written PRs.
They found a 2.6x increase in formatting problems, and almost twice as many naming inconsistencies.

These issues make codebases more difficult to maintain, and increase the risk of future errors.

### Security 

CodeRabbit's study found a **2.74x increase in security issues** in AI-written PRs.
The most common issues were improper password handling, insecure object references, cross-site scripting issues (XSS), and insecure deserialization.
These aren't AI-specific vulnerabilities, but the use of AI seems to increase their frequency.

#### Slopsquatting

In addition to increased prevalence of traditional vulnerabilities, AI also opens up new potential attack vectors that exploit the possibility of developers shipping un-reviewed code.
Large Language Models sometimes "hallucinate" information, including dependencies and package names.
Malicious actors can take advantage of common AI hallucinations by creating malicious packages under those names, tricking inattentive software developers into installing them without verifying their legitimacy.
This practice is called [slopsquatting](https://en.wikipedia.org/wiki/Slopsquatting), a combination of the words "AI Slop" and "[typosquatting](https://en.wikipedia.org/wiki/Typosquatting)," an older technique of registering misspelled domain names.

The research paper [*We Have a Package for You! A Comprehensive Analysis of Package Hallucinations by Code Generating LLMs*](https://arxiv.org/abs/2406.10279) found that almost **20% of recommended packages** across more than half a million code samples **didn't exist**.
Many of the package names appeared every time the same prompt was given, making it easy for attackers to identify common hallucinations.

### Performance

CodeRabbit's study found that AI-written PRs were more likely to create performance issues, with **7x more excessive I/O operations** in the form of unnecessary file reads, repeated network calls, or unbatched operations.
They suggest that this may be due to AI's preference for clarity and simple patterns at the cost of larger-scale efficiency.

## AI upside

This isn't all to say that AI shouldn't be used as a coding tool.
In fact, given the pace of improvement and adoption, it's crucial to learn how to use it safely and effectively.
AI *can* improve both output and the developer's experience, provided that care is taken to avoid these common pitfalls.

## webforJ's AI policy

AI coding assistants are dramatically changing the way that people create software, and the engineers at webforJ are no exception.
We are excited to make use of these new tools and capabilities, but we're strongly committed to our policy of **AI-assisted development, and human-owned code**.
We use AI in many parts of our development cycle, including generating boilerplate, writing tests, updating dependencies, drafting documentation, creating sample programs, and exploring architectural alternatives.
But regardless of what the development process looks like, we only ship code that passes human review and quality assurance checks.

### Where is AI most useful?

Research into AI's impact on software development gives some hints as to where AI can be most effective, and where it's important to exercise caution.
An initial commit might be ready in a matter of minutes, but we have to make sure it isn't imposing higher costs in review and maintenance.

In core code, we prioritize security, quality, and maintainability—areas where AI-generated code often falls short.
On top of that, the complexity of the webforJ core puts AI tools at a disadvantage when compared to engineers with a deep knowledge of the system.
When using AI, we have found that it's important to **set a high bar** and **iterate repeatedly**.
The first attempt is rarely acceptable, and even the best outputs usually require modification.
This means that we can only allow AI to play a limited part in development, and engineers always [own the merge button](https://github.blog/ai-and-ml/generative-ai/code-review-in-the-age-of-ai-why-developers-will-always-own-the-merge-button/) and ensure that our standards are upheld.

Outside of the core, the benefits of AI really shine in **exploration and experimentation**, **demos and samples**, **testing**, **documentation**, and **internal communication**.

#### Exploration and experimentation

Exploration is one of our top uses of AI.
Without AI tools, spending time and resources exploring new paths requires a high level of certainty that they will result in useful outcomes.
Now, engineers can use AI agents to collect information and identify promising ideas quickly, lessening the risk of devoting time to something that doesn't pan out.
Once a promising path is identified, engineers can do their usual work of creating secure, maintainable, and high quality code.

Because these experiments aren't connected to our core code, they have **low risk and high reward**, letting us focus on speed and turnaround time without worrying about the impact on our codebase.

#### Demos and samples

Demos and samples are another one of the best areas to use AI, because they're **low-risk** and **self-contained**.
AI tools struggle the most in high-complexity environments where they need to integrate with the logic and style of a larger system.
Even relatively large sample programs are much lower complexity than the core code, so AI can thrive in this use case.

In demos and samples, it's **easier for the AI** to maintain the full project in its context, and it's **easier for engineers** to verify code style and accuracy.

Not only is this a low-risk area to make use of AI, but doing so also provides us with valuable insight into the experience of our users.
Since AI usage has become the norm, we expect that webforJ users are also using AI in the development of their Java-based web applications.
By putting ourselves in their shoes, we can uncover problems and ensure that webforJ works with AI as smoothly as possible.

#### Testing

Testing is another area where AI has improved our workflow.
Unit tests are crucial for verifying software stability, but writing them can be boring and repetitive, because many tests follow very similar patterns.
However, AI is particularly good at **repetitive** and **predictable** tasks, because it doesn't have to innovate.
We can simply teach the AI how we write our unit tests, and it will create new tests following the same patterns.
Engineers review the output for accuracy, and don't have to re-write the same boilerplate code over and over.

#### Documentation

Documentation includes both Javadocs and our [online documentation](https://docs.webforj.com/docs/introduction/getting-started), and AI can assist with both to varying degrees.
Generating Javadoc comments with AI is especially useful, because it's **time-consuming** work for humans, **simple for AI**, and **easy to verify**.
Engineers can focus on the API implementation, offload Javadocs to the AI, and edit them as needed.

We aren't able to rely as heavily on AI in the rest of our documentation, but it can still assist with the boilerplate code for many pages and provide initial rough drafts.
This is an area where strict style guides and review processes ensure that, regardless of the workflow used to create a page, the final product meets our standards.

## How webforJ supports your use of AI

In addition to our own use of AI, we understand that AI development tools are shaping the future of software development, and we are always looking for ways to improve the experience of developers using webforJ with these tools.
One of the best ways to start using AI in your webforJ development is with the dedicated [webforJ MCP server](/docs/introduction/mcp).
The [Model Context Protocol](https://en.wikipedia.org/wiki/Model_Context_Protocol) (MCP) is an open standard for integrating LLMs with external data sources.
The webforJ MCP Server gives an AI direct access to the webforJ documentation and verified code samples, making its responses more relevant and accurate. 
By integrating it into your workflow, you can avoid many of the pitfalls of AI-generated code and accelerate the development of your own app.

Additionally, components like the `MarkdownViewer` make it easier to integrate AI output into your own app, by displaying an stream of text as it's generated.

Using AI is no longer a cutting-edge trend; it's become the norm.
As the tools evolve and gain traction, webforJ is developing alongside them as an AI-friendly toolkit, making sure that you can integrate AI into your workflow and get the best results possible.
However you're using AI in your own workflow, you can do it all in Java with webforJ!