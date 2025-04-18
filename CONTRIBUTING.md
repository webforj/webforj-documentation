# Contributing to webforJ docs

Thank you for considering contributing to the webforJ documentation. Clear, accurate, and useful documentation is vital for helping others get started and stay productive with webforJ.

This guide will help you contribute effectively, whether you’re fixing a typo, suggesting a new section, or opening a larger pull request.

Following this guide helps ensure contributions are consistent, well-scoped, and easy to review. It also shows respect for the time of the community and maintainers.

---

## What contributions are welcome?

We welcome:

- Fixes for typos, grammar, and clarity  
- Improvements to tutorials, guides, and reference material  
- New documentation pages for undocumented features  
- Bug reports or inconsistencies in existing content  
- Suggestions to improve developer onboarding  
- External resources (e.g., tutorials or examples) that help the community  

> You don't have to write code to contribute! Even improving or reorganizing content helps others.

---

## What contributions aren't ideal?
  
- Feature requests unrelated to documentation - open these in the [main repo](https://github.com/webforj/webforj/issues)  
- Unverifiable examples or tutorials not based on current functionality  

---

## Ground rules

- Be respectful, concise, and constructive  
- Focus on accuracy and style. Follow the conventions already present in the docs  
- Link to [official Javadoc](https://javadoc.io/doc/com.webforj) or [source code](https://github.com/webforj/webforj/tree/main) where appropriate  
- Always open a pull request from a branch - never `main`  

---

## Small fixes

You can [open an issue](https://github.com/webforj/webforj-documentation/issues) and link a PR directly for minor fixes like:

- Spelling and grammar  
- Formatting issues  
- Broken internal links  
- Typos in code comments or examples  

These will be reviewed and merged quickly.

---

## Bug reports and suggestions

To report a problem with the documentation, open a [bug report](https://github.com/webforj/webforj-documentation/issues/new?template=bug_report.yml) in the documentation repo and include:

- The page or section you're referring to  
- What you saw that's behaving incorrectly
- What you expected to see instead
- How severe/critical this issue is
- The browser you observed the behavior on (you may choose multiple)
- Additional observations  

> **Security issues?**  
> Please **do not** open an issue. Instead, please use [this link](https://github.com/webforj/webforj-documentation/security/advisories) and create a new security advisory there.

---

## Proposing new docs or features

If you want to contribute new guides or content, please use this [link to create a request for a new doc or feature](https://github.com/webforj/webforj-documentation/issues/new?template=bug_report.yml), and include the following:

- A suggested title(if the enhancement is a new documentation article), or the current title if the request applies to an existing article
- Explain why it's useful and what it covers  
- Link related articles, docs, or materials
- Label the enhancement according to its type
- Additional notes

This helps us keep the documentation well-organized and easy to maintain.

---

## How to contribute

We welcome contributions to improve our documentation, demos, and framework. Whether you’re fixing typos, expanding a guide, or submitting new demo apps, every contribution helps make webforJ better for everyone.

> To contribute to webforJ itself, please see [this guide](https://github.com/webforj/webforj/blob/main/CONTRIBUTING.md).

### Contribution workflow

1) **Open an Issue** - If your contribution adds new content, changes existing behavior, or fixes a bug, please first open an issue using the appropriate template outlined above.

2) **Create a branch** - Create a branch that closes the opened issue based off main.

3) **Submit a Pull Request** - Once your changes are ready, open a Pull Request (PR) that links to your issue (use `Closes #<issue-number>` in your PR description). All PRs should clearly state what was changed and why.

### Contributing to the documentation articles

All documentation content lives in Markdown/MDX format inside the docs/ folder and is powered by Docusaurus. To contribute to any page, you’ll need to set up the project locally.

To see the required to get docusaurus up and running, as well as how to use the development server, and other development need-to-knows, start with [this article](https://docusaurus.io/docs/installation).

### Contributing to the demos

We use self-contained demo applications to illustrate how to use webforJ. If you’d like to contribute new examples or improve existing ones, you’ll need to set up a local webforJ development environment.

See [this article](https://docs.webforj.com/docs/introduction/prerequisites) for a more in-depth guide to setting up a project. You will need:

- Java 17 or higher
- Maven
- Jetty plugin or servlet container

**All demos should:**

- Follow the standard Maven structure
- Compile and run via mvn jetty:run
- Contain relevant comments or Javadoc when appropriate

---

## Code review and acceptance

Pull requests are reviewed by the documentation team. Please:

- Follow the current writing style  
- Break large changes into smaller PRs when possible  
- Respond to review comments promptly  

To help with these goals, we use various tools and plugins to keep writing style cohesive, code clean, and PRs manageable.

## Tools we're using

We use the following tools when creating code for demos, or writing documentation articles to keep a consistent tone and keep documentation as high quality as possible.

### SonarLint
SonarLint is a static analysis tool that helps catch common bugs, anti-patterns, and style issues in real-time. It integrates with your IDE and provides immediate feedback while you're writing Java example code.

- VS Code: [SonarLint Extension](https://marketplace.visualstudio.com/items?itemName=SonarSource.sonarlint-vscode)
- IntelliJ IDEA: [SonarLint Plugin](https://plugins.jetbrains.com/plugin/7973-sonarqube-for-ide)

### Code Formatting (Google Java Style)
We follow the Google Java Style Guide when formatting Java code in webforJ documentation and examples. This helps keep the codebase uniform and easy to read for all contributors.

You can download the formatter configuration file [here](https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml)

### Checkstyle
Checkstyle is another tool we use to enforce code style and formatting. It checks your Java code against defined rules and ensures it aligns with project standards (which follow Google’s rules).

- VS Code: [Checkstyle for Java](https://marketplace.visualstudio.com/items?itemName=shengchen.vscode-checkstyle)
- IntelliJ IDEA: [CheckStyle-IDEA Plugin](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea)

Example code in webforJ documentation should pass Checkstyle validation before being submitted.

### Vale for prose linting
Vale is a syntax-aware prose linter used to enforce grammar, style, and consistency across all Markdown and MDX documentation in the webforJ project.

We use a custom Vale configuration (.vale.ini) with rules that match our internal style guide (e.g., consistent casing, word choice, tone). This helps ensure that contributions remain clear and aligned with the rest of the site.

### Conventional commits

We use [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/) (`fix:`, `docs:`, `refactor:`, `chore:`, etc.) where appropriate.