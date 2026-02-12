---
title: "webforJ: AI-assisted, human-owned"
description: webforJ's stance on AI-generated code in open source frameworks
slug: webforj-human-owned
date: 2026-02-12
authors: webforJ
tags: [webforj, ai]
hide_table_of_contents: false
---

I recently came across [get-shit-done](https://github.com/glittercowboy/get-shit-done) and [Auto-Claude](https://github.com/AndyMik90/Auto-Claude), which are meta-prompting systems that promise to automate entire development workflows. 
At first glance, they're impressive. But they also made me wonder: what's happening to the quality of the open source ecosystem as AI-generated code floods platforms like npm, PyPI, and GitHub?

The answer, backed by recent research, is sobering. And it supports why we've made a strategic choice at webforJ: **AI-assisted development, but human-owned code.**

<!-- truncate -->

## The quantity

GitHub's annual [Octoverse Report](https://github.blog/news-insights/octoverse/octoverse-a-new-developer-joins-github-every-second-as-ai-leads-typescript-to-1/) analyzes developer trends, providing insight into the use of AI on the platform.
Their findings over the last couple years show massive growth in developer output, along with increased usage of AI as a development tool:

In 2025, every metric of developer productivity on GitHub broke new records:
- 25% increase in code pushes
- 20% increase in pull requests
- 11% increase in new issues

This increase in output comes alongside increased usage of AI. GitHub's analysis found:
- AI-related projects have almost doubled in less than two years
- Six of the 10 fastest-growing repos were related to AI infrastructure
- Almost 80% of new developers started using GitHub Copilot within their first week

These trends build on the prior year. In 2024, [GitHub's findings](https://github.blog/news-insights/octoverse/octoverse-2024/) show significant growth in AI development:
- 98% increase in generative AI projects on GitHub 
- 59% increase in contributions to these projects

According to [StackOverflow's 2025 Developer Survey](https://survey.stackoverflow.co/2025/), which received over 49,000 responses, 78.5% of developers currently use AI tools, with another 5.3% planning to use them soon.


## The quality

So what about the quality of all this code? 
Does the flood of commits stand up to scrutiny? 
These questions have been top of mind for many developers, and emerging research is starting to answer them.

StackOverflow's 2025 Developer survey found that positive sentiment toward AI tools dropped in 2025, at only 60%.
Additionally, they found that trust for the output of AI tools is at about 33%, with trust generally decreasing relative to developer experience.

CodeRabbit's [State of AI vs. Human Code Generation Report](https://www.coderabbit.ai/blog/state-of-ai-vs-human-code-generation-report) which analyzed 320 AI-co-authored PRs and 150 human-authored PRs, found the following about AI-written PRs:
- 1.7x more issues overall
- More high-issue outliers
- 1.4x more critical issues
- 1.7x more major issues

AI code often contains mistakes in logic and correctness, with increased occurrences of logic errors, misconfigurations, and poor error or exception handling.

StackOverflow's survey found that 66% of developers using AI tools had the problem of "AI solutions that are almost right, but not quite," and 45% agreed that "Debugging AI-generated code is more time-consuming."
Only 4% responded that they have not encountered any problems when using AI tools.

GitClear analyzed over 200 million lines of code, written between 2020 and 2024 in their [AI Copilot Code Quality](https://www.gitclear.com/ai_assistant_code_quality_2025_research) research.
They found that in 2024, copy/pasted lines of code exceeded moved lines of code for the first time, indicating less refactoring and worse code reuse, and generally indicating trouble for code quality.
From 2020 to 2024, they found that code reuse, as measured in moved lines, has decreased from 25% of all changes to less than 10% of all changes.
From 2023 to 2024, the percentage of commits that contained duplicated blocks of code rose dramatically from just 1.8% to 6.6%.


### Maintanability

CodeRabbit found that AI-produced code often creates more technical debt, with issues of readability, formatting errors, unclear naming, and unused or redundant code all increased compared to human-written PRs.
They found a 2.6x increase in formatting problems, and almost twice as many naming inconsistencies.

These issues can cause codebases to become more difficult to maintain for human developers

### Security 

CodeRabbit's study found a 2.74x increase in security issues in AI-written PRs.
The most common issues were improper password handling, insecure object references, cross-site scripting issues (XSS), and insecure deserialization.
These aren't AI-specific vulnerabilities, but the use of AI seems to increase their frequency.

#### Slopsquatting

In addition to increased prevalence of traditional vulnerabilities, AI also opens up new potential attack vectors that exploit the possibility of developers shipping un-reviewed code.
Large Language Models sometimes "hallucinate" information, including dependencies and package names.
Malicious actors can take advantage of common AI hallucinations by creating malicious packages under those names, tricking inattentive software developers into installing them without verifying their legitimacy.
This practice is called [slopsquatting](https://en.wikipedia.org/wiki/Slopsquatting), a combination of the words "AI Slop" and "[typosquatting](https://en.wikipedia.org/wiki/Typosquatting)," an older technique of registering misspelled domain names.
The research paper [We Have a Package for You! A Comprehensive Analysis of Package Hallucinations by Code Generating LLMs](https://arxiv.org/abs/2406.10279) found that almost 20% of recommended packages across more than half a million code samples did not exist.
Many of the package names appeared every time the same prompt was given, making it easy for attackers to identify common hallucinations.

### Performance

CodeRabbit found that AI PRs exhibited 7x more excessive I/O operations.
They suggest that this may be due to AI's preference for clarity and simple patterns at the cost of larger-scale efficiency.

## AI upside

This isn't all to say that AI shouldn't be used as a coding tool.
AI *can* improve both output and the developer's experience, provided that care is taken to avoid these common pitfalls.
<!-- research on what AI does well? -->
<!-- Recommendations on how to offset these effects? -->
GitClear concludes their research by recommending that developers focus on consolidating and simplifying code to offset the duplication and churn created by AI.

## webforJ's AI policy

AI coding assistants are dramatically changing the way that people create software, and the engineers working at webforJ are no exception.
While we are excited to leverage these new tools and capabilities, we are committed to our policy of **AI-assisted development, and human-owned code**.
We use AI in many parts of our development cycle, including generating boilerplate, writing tests, updating dependencies, drafting documentation, creating sample programs, and exploring architectural alternatives.
But regardless of what the development process looks like, we only ship code that passes human review and quality assurance checks.

### Where is AI most useful?

The research into the impact of AI on software development gives some hints into where AI can be most effective, and where it's important to exercise caution.
An initial commit might be ready in a matter of minutes, but we have to make sure it isn't imposing a higher cost in review and maintenance.
In core code, we prioritize security, quality, and maintainability.
We allow AI to play a part in development, but engineers always [own the merge button](https://github.blog/ai-and-ml/generative-ai/code-review-in-the-age-of-ai-why-developers-will-always-own-the-merge-button/) and ensure that our standards are upheld.

Outside of the core, though, there are several areas where the benefits of AI really shine:

#### Demos and samples

Demos and samples are one of the best areas to make use of AI, because they are **low-risk** and **self-contained**.
AI tools struggle the most in high-complexity environments where they need to integrate with the logic and style of a larger system.

In even a relatively large sample program, that problem is mitigated by the fact that it's both easier for the AI to maintain the full project in its context, and for engineers to verify code style and accuracy.

Not only is this a low-risk area to make use of AI, but doing so also provides us with valuable insight into the experience of our users.
Since AI usage has become the norm, we expect that webforJ users are also using AI in the development of their Java-based web applications.
By putting ourselves in their shoes, we can uncover problems and ensure that webforJ works with AI as smoothly as possible.

#### Exploration and experimentation

AI excels at creating low-effort first drafts of new ideas.
This can improve our ability to test the viability of new features before investing more time into the quality and accuracy of the code.

This is another area where the low risk allows us to prioritize speed and turnaround time, giving us the opportunity to experiment freely and quickly in order to find promising areas to invest development time in.

#### Drafting documentation

Much of our documentation follows similar patterns, especially the webforJ [components](/docs/components/overview) pages.
Even though AI tools rarely provide a finished product, they can accelerate our documentation process by essentially providing the boilerplate code of a documentation page.
This is an area where strict style guides and review processes ensure that, regardless of the workflow used to create a page, the final product meets our standards.

#### Internal communication

Good development depends on good communication, and AI tools can help here as well.
Communication can sometimes break down across language barriers and experience levels, but using LLMs to assist with issue descriptions, bug reports, and feature requests helps us reduce misunderstandings, allowing PRs to be merged faster.

#### Code review

Just as it's important for humans to review AI output, AI can be extremely valuable in reviewing human output.
Using AI as a code reviewer can help flag up issues that a human may miss, and ensures that a PR meets basic standards before another developer looks at it, saving them the cognitive load of addressing minor issues or oversights.

## How webforJ supports your use of AI

In addition to our own use of AI, we understand that AI development tools are shaping the future of software development, and we are always looking for ways to improve the experience of developers using webforJ with these tools.
One of the best ways to start using AI in your webforJ development is with the dedicated [webforJ MCP server](/docs/introduction/mcp).
The [Model Context Protocol](https://en.wikipedia.org/wiki/Model_Context_Protocol) (MCP) is an open standard for integrating LLMs with external data sources.
The webforJ MCP Server gives an AI direct access to the webforJ documentation and verified code samples, making its responses more relevant and accurate. 
By integrating it into your workflow, you can avoid many of the pitfalls of AI-generated code and accelerate the development of your own app.
<!-- AI integration in your application -->