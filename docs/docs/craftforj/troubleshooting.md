---
title: Troubleshooting
sidebar_position: 11
description: Fix the common cases where craftforJ doesn't appear, a feature is unavailable, or the assistant doesn't answer.
---

### Nothing appears on the page {#nothing-appears-on-the-page}

craftforJ attaches only when every requirement in [Getting started](/docs/craftforj/getting-started.#requirements) is met, and it shows nothing at all when one is missing. Check them in order: the `webforj-devtools` dependency on the classpath, debug mode, the craftforJ property, a browser on the machine running the app, and a valid developer license. A configuration file in the wrong location, or a profile that overrides one of the properties, produces exactly the same result as the property being off.

### A feature is unavailable {#a-feature-is-unavailable}

craftforJ shows a disabled feature rather than hiding it, so a control that's present but marked as unsupported was switched off deliberately. Either it was disabled with a [feature flag](/docs/craftforj/configuration#feature-flags) in the app's configuration, or the `webforj-devtools` version on your classpath predates it.

Writing to source also needs a project root that craftforJ can find. Check the one it detected in [App info](/docs/craftforj/app-info), and set [`project-root`](/docs/craftforj/configuration#project-root) if it's wrong.

### Java validation is weaker than expected {#java-validation-is-weaker-than-expected}

The assistant's [compile validation](/docs/craftforj/ai#it-writes-java) needs a JDK. Check the Java version in [App info](/docs/craftforj/app-info), and run the app on a JDK rather than a JRE.

### craftforJ looks out of date after an update {#craftforj-looks-out-of-date-after-an-update}

Your browser cached the previous version. Hard-reload the page, or open the app in a private window. If the problem persists, confirm which `webforj-devtools` version is actually on the classpath in [App info](/docs/craftforj/app-info), since an old jar in your local Maven repository looks the same from the browser.

### The assistant doesn't answer {#the-assistant-doesnt-answer}

The assistant needs a configured provider and a model that can call tools. A model without tool support can hold a conversation but can't inspect or change anything. A local model that keeps losing track of the conversation is usually running with too small a context window.

If a local model is configured and reachable but every request is refused, the model server is rejecting the page's origin. For Ollama, allow the origin and restart it:

```bash
launchctl setenv OLLAMA_ORIGINS "*"
pkill ollama && ollama serve
```

On Linux, set `OLLAMA_ORIGINS` in the environment Ollama starts from and restart it.

### craftforJ says the app is restarting {#craftforj-says-the-app-is-restarting}

Your app goes away regularly in development, every time it rebuilds. craftforJ reports what's happening rather than freezing, so it shows when the app is restarting or the page is reloading, and its controls stay inert until the app is back. It reconnects on its own with your selection and your pending work intact, so there's nothing to do but wait. If it reports that it can't reach the app at all, confirm the app is still running and reload the page.

### The app keeps restarting {#the-app-keeps-restarting}

Applying a change to source restarts the app, as described in [After you apply](/docs/craftforj/source-changes#after-you-apply). Restarts that happen without an applied change come from your build's file watcher rather than from craftforJ.

### Collecting logs {#collecting-logs}

Before reporting a problem, turn on verbose logging in craftforJ settings, clear the log, reproduce the problem, then download the log. Attach it together with the contents of [App info](/docs/craftforj/app-info).
