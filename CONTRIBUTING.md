# Contributing to webforJ docs

The complete [Contributing guide](docs/docs/contributing/overview.md) covers framework and documentation contributions, including local setup, forks, pull requests, tests, documentation structure, and demo requirements.

Thank you for considering contributing to the webforJ documentation. Clear, accurate, and useful documentation is vital for helping others get started and stay productive with webforJ.

This guide will help you contribute effectively, whether you’re fixing a typo, suggesting a new section, or opening a larger pull request.

Following this guide keeps contributions consistent, well-scoped, and easy to review. It also shows respect for the time of the community and maintainers.

---

## What contributions are welcome?

Accepted contributions include:

- Fixes for typos, grammar, and clarity  
- Improvements to tutorials, guides, and reference material  
- New documentation pages for undocumented features  
- Bug reports or inconsistencies in existing content  
- Suggestions to improve developer onboarding  
- External resources (e.g., tutorials or examples) that help the community  

> You don't have to write code to contribute. Improving or reorganizing content also helps readers.

---

## What contributions aren't ideal?
  
- Feature requests unrelated to documentation - open these in the [main repo](https://github.com/webforj/webforj/issues)  
- Unverifiable examples or tutorials not based on current behavior

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
> Please **don't** open an issue. Instead, use [this link](https://github.com/webforj/webforj-documentation/security/advisories) to create a security advisory.

---

## Proposing new docs or features

If you want to contribute new guides or content, please use this [link to create a request for a new doc or feature](https://github.com/webforj/webforj-documentation/issues/new?template=feature_request.yml), and include the following:

- A suggested title(if the enhancement is a new documentation article), or the current title if the request applies to an existing article
- Explain why it's useful and what it covers  
- Link related articles, docs, or materials
- Label the enhancement according to its type
- Additional notes

This keeps the documentation well-organized and easy to maintain.

---

## How to contribute

Contributions can improve documentation, demos, and the framework. This repository accepts documentation and sample changes. Framework code changes are submitted to the `webforj/webforj` repository.

> To contribute to webforJ itself, follow the [Framework Contributions guide](docs/docs/contributing/framework.md). It expands on the framework repository's contribution policy with its current setup, module, formatting, and testing workflow.

### Contribution workflow

1) **Open an Issue** - If your contribution adds new content, changes existing behavior, or fixes a bug, please first open an issue using the appropriate template outlined above.

2) **Create a branch** - Create a branch that closes the opened issue based off main.

3) **Submit a Pull Request** - Once your changes are ready, open a Pull Request (PR) that links to your issue (use `Closes #<issue-number>` in your PR description). All PRs should clearly state what was changed and why.

### Contributing to the documentation articles

All documentation content uses Markdown or Markdown with JSX inside the `docs` folder and is powered by Docusaurus. To contribute to a page, set up the project locally.

To see the required to get docusaurus up and running, as well as how to use the development server, and other development need-to-knows, start with [this article](https://docusaurus.io/docs/installation).

### Contributing to the demos

Self-contained demo apps illustrate how to use webforJ. To contribute new examples or improve existing ones, set up a local webforJ development environment.

See [this article](https://docs.webforj.com/docs/introduction/prerequisites) for a more in-depth guide to setting up a project. You will need:

- Java 21 or higher
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

The following tools keep writing cohesive, code clean, and pull requests manageable.

## Tools

Use these tools when creating demo code or writing documentation articles.

### Static analysis
SonarLint is a static analysis tool that helps catch common bugs, anti-patterns, and style issues in real-time. It integrates with your IDE and provides immediate feedback while you're writing Java example code.

- VS Code: [SonarLint Extension](https://marketplace.visualstudio.com/items?itemName=SonarSource.sonarlint-vscode)
- IntelliJ IDEA: [SonarLint Plugin](https://plugins.jetbrains.com/plugin/7973-sonarqube-for-ide)

### Code formatting
Java code in webforJ documentation and examples follows the Google Java Style Guide. This keeps the codebase uniform and readable.

You can download the formatter configuration file [here](https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml)

### Spotless
The Maven build uses Spotless with Google Java Format to validate Java files in `src/main/java` and `src/test/java`.

Run `mvn spotless:check` before submitting Java changes. Use `mvn spotless:apply` to format files that fail validation.

### Vale for prose linting
Vale is a syntax-aware prose linter used to enforce grammar, style, and consistency across all Markdown and Markdown with JSX documentation in the webforJ project.

Pull request CI runs Vale with the repository configuration in `.vale.ini`. Resolve all alerts in changed prose files before submitting.

### Conventional commits

Use [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/) (`fix:`, `docs:`, `refactor:`, `chore:`, etc.) where appropriate.
