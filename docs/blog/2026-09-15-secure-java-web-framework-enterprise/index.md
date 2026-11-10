---
title: "Secure Java web framework for enterprise teams"
description: "A secure Java web framework for enterprise keeps rules on the server. See how webforJ shrinks the attack surface and which duties stay with your own team."
slug: secure-java-web-framework-enterprise
date: 2026-09-15
authors: webforJ
tags: [insights, security, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-15-secure-java-web-framework-enterprise/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-15-secure-java-web-framework-enterprise/cover.jpg)

When a security review lands on your desk, the first question is usually how much of the app an outsider can reach. A JavaScript front end plus a public REST API gives them a lot to work with. A secure Java web framework for enterprise use starts from the opposite end: keep state, business rules and access decisions on the server, and let the browser only render the interface.

webforJ, the full-stack Java framework, is built that way. Screens, routing, validation and access control are written in Java and run on the server, so you write no separate front-end codebase and no REST endpoints just to feed the UI.

<!-- truncate -->

## What the attack surface looks like {#attack-surface}

Most security work on a web app is the work of shrinking what an attacker can reach. A typical split architecture has two exposed layers:

- A JavaScript app that ships logic, routing rules and often hints about the data model to every visitor.
- A REST API that must be public enough for the browser to call it, so every endpoint needs its own authentication, authorization and input checks.

Each endpoint is a door. Each duplicated rule, say a validation that exists in the browser and again on the server, is a chance for the two to drift apart. Reviewers then audit two stacks, two dependency trees, and two sets of tooling.

To reduce the attack surface of a web app in Java, you remove doors instead of adding locks to every one of them. That's the design choice behind a server-side UI.

## How a server-side UI changes it {#server-side-ui}

In webforJ the app runs in Java on the server. It sends data to the browser, and the browser sends events back. The rules that decide what's allowed run in your Java code on the server. The [client and server interaction](/docs/architecture/client-server) page describes the model in detail.

For a risk owner, this has four practical effects:

1. **No UI-only endpoints to write.** Your views call your Java services directly, so you don't need REST controllers whose only job is to feed a screen.
2. **Rules are enforced on the server.** A manipulated client can send anything, so the server is the only place a rule can hold. In webforJ the server is also where the rule is written.
3. **Text is shown as text.** When you set a component's text, the value is displayed literally. Markup is rendered only when you opt in on purpose, which helps prevent a common class of cross-site scripting by default.
4. **One codebase to review.** The UI is Java, so it goes through the same pull requests, static analysis and dependency scanning your backend already does. There is no parallel front-end toolchain to bring under policy.

This is secure by design Java web UI in a literal sense: the safe path is the default path, and the dangerous paths are explicit calls a reviewer can search for.

## Route security and secure by default {#route-security}

Authorization in webforJ is declared on the route and checked before the view is created. Four annotations cover the cases: `@AnonymousAccess` for public pages, `@PermitAll` for any signed-in user, `@RolesAllowed` for specific roles, and `@DenyAll` to close a route completely. The [security annotations](/docs/security/annotations) page lists them with examples.

The important setting for an enterprise rollout is secure-by-default. With `webforj.security.secure-by-default=true`, a route that carries no annotation requires authentication, and only routes you explicitly mark as anonymous are public. A developer who forgets an annotation gets a locked door instead of an open one. For a security review, that turns a review question ("did anyone forget to protect a page?") into a configuration fact.

When Spring Boot is on the classpath, the [Spring Security integration](/docs/security/getting-started) plugs in through `WebforjSecurityConfigurer`. Users, password encoding, sessions and login come from Spring Security, which your security team likely knows already. If you run without Spring, the [security architecture](/docs/security/architecture/overview) guide covers a custom implementation. The [security overview](/docs/security/overview) is the right starting page for both.

## What your tools already cover {#existing-tools}

Because the app is Java, the controls you already pay for apply directly. Code review, static analysis, dependency scanning and your build pipeline all apply to a Maven project the way they do to any Java service. Your security team can review a webforJ screen with the Java skills it already has.

This matters for staffing as well. The people who maintain the service layer can maintain the screens, and the review checklist is the one you already have.

## Decision table {#decision-table}

| Question for the review | JavaScript front end plus public REST API | Server-side Java UI with webforJ |
| --- | --- | --- |
| Where does UI state live? | In the browser, mirrored from API calls | In the Java app on the server |
| Where do business rules run? | Server, often duplicated in the browser | On the server, in Java |
| Endpoints written just for the UI | One or more per screen | None needed, views call Java services |
| Access control for a screen | Per endpoint, plus client-side routing | Per route, checked before the view exists |
| Unannotated page | Depends on each framework and team | Requires sign-in with secure-by-default on |
| Languages and toolchains to scan | Java plus JavaScript and its package tree | Java |
| Authentication | Chosen and wired per project | Spring Security integration or a custom implementation |

The table describes the architecture, not a promise. Both columns can be run badly.

## What stays your responsibility {#your-responsibility}

No framework makes an app secure by itself, and we do not claim that webforJ does. Some duties stay with your team:

- **Validate input on the server.** Bound values, check types and use parameterized queries. webforJ's [data binding and validation](/docs/data-binding/validation/overview) runs in Java on the server, but it is an integrity layer, not a defense against injection.
- **Encrypt every connection.** Handle TLS at your container, proxy or load balancer and redirect plain HTTP.
- **Deploy carefully.** Keep secrets out of the source tree, leave development tooling off in production, keep dependencies patched, and cap uploads and request sizes.
- **Treat disabled and hidden as cues, not controls.** A disabled button is a hint to honest users. The rule belongs in the server-side handler.
- **Sanitize markup you opt into.** If a fragment of HTML comes from a person or a stored record, clean it with a maintained sanitizer before it reaches a component.

Each of these is covered in our [app security](/docs/security/application-security/overview), including [common threats](/docs/security/application-security/common-threats), and [production hardening](/docs/security/application-security/production-hardening). Read them next to your own standards. They are written to show where the framework helps and where it stops.

## Who stands behind it {#low-risk}

Security reviews also ask who stands behind the software. webforJ comes from BASIS International. [BASIS](https://webforj.com/company) software and software tools have been used to develop business solutions for both large international corporations and SMEs for almost 40 years, with 20+ years of delivering technology built on Java. Operating its own sites in North America and Europe and working with partners in more than 30 countries, BASIS is well positioned on the international stage.

For the wider evaluation, our posts on the [Java full-stack framework](/blog/java-full-stack-framework) and on [enterprise web frameworks in Java](/blog/java-enterprise-web-framework) cover the full stack and the enterprise checklist.

## Hand one route to your reviewers {#review-one-route}

Pick one internal screen that today needs a dedicated REST endpoint, and build it as a webforJ route behind `@RolesAllowed` with secure-by-default on. Then hand the result to your security reviewers with the [security overview](/docs/security/overview) and the [app security](/docs/security/application-security/overview) pages. A single screen is enough to compare the review effort against your current approach. You can generate a starter project with [startforJ](https://docs.webforj.com/startforj).

## Frequently asked questions {#faq}

### Is webforJ certified or promised to be secure? {#faq-certified}

No. We make no certification or compliance claims, and no framework can promise that an app can't be attacked. webforJ removes common exposure by design, such as UI-only endpoints and client-side rules. Your team still owns input validation, transport security, and secure deployment.

### How does a server-side UI reduce the attack surface of a web app in Java? {#faq-attack-surface}

State and business rules stay on the server, so there are fewer public endpoints and less client-side logic to tamper with. Access is checked per route before a view is created. The result is fewer places where a missed check can expose data.

### Does webforJ work with Spring Security? {#faq-spring-security}

Yes. When Spring Boot is on the classpath, `WebforjSecurityConfigurer` connects Spring Security to webforJ routing. Spring handles users, password encoding and sessions, and webforJ enforces the route annotations. A custom implementation is available if you do not use Spring.

### Can we scan webforJ code with our existing security tools? {#faq-scanning}

The UI is plain Java in a Maven project, so code review, static analysis and dependency scanning apply as they do to any Java service. There is no separate front-end language to cover for the screens you write in webforJ.
