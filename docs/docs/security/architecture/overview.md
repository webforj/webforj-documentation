---
sidebar_position: 1
title: Security Architecture
hide_table_of_contents: true
hide_giscus_comments: true
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->

import DocCardList from '@theme/DocCardList';

<!-- vale on -->

The webforJ security system is built on a foundation of interfaces and design patterns that enable flexible, extensible route protection. This section explains how the foundation security framework works and how to build custom security implementations by implementing these interfaces.

:::tip[You probably don't need to implement this yourself]
Most applications should use the [Spring Security integration](/docs/security/getting-started), which auto-configures all of this for you. Only implement custom security if you have specific requirements or you're not using Spring Boot. The Spring integration is built on this same foundation architecture.
:::

You'll learn about the core interfaces, the evaluator chain pattern, how navigation is intercepted and evaluated, and different approaches for storing authentication state.

:::info[Focus on architecture and extension points]
These guides explain the foundation architecture and extension points—the interfaces you implement and how they work together. Code examples show **one possible approach**, not prescriptive requirements. Your implementation can use different storage mechanisms (JWT, database, LDAP), different wiring patterns, or different authentication flows based on your needs.
:::

## What you'll learn {#what-youll-learn}

- **Foundation architecture**: The core interfaces that define security behavior and how they work together
- **Navigation interception**: How the security system intercepts navigation requests and evaluates access rules
- **Evaluator chain pattern**: How security rules are evaluated in priority order using the chain of responsibility pattern
- **Authentication storage**: Different approaches for storing user authentication state (sessions, JWT, database, etc.)
- **Complete implementation**: A working example showing all components wired together

## Who this is for {#who-this-is-for}

These guides are for developers who want to:

- Build custom security implementations for non-Spring applications
- Understand the foundation architecture to troubleshoot issues
- Implement custom authentication flows or authorization logic
- Create security evaluators with domain-specific logic
- Integrate with existing authentication systems (LDAP, OAuth, custom backends)

## Prerequisites {#prerequisites}

Before diving into these guides, you should:

- Complete the [Getting Started guide](/docs/security/getting-started) to understand security concepts
- Understand security annotations from the [Annotations guide](/docs/security/annotations)
- Be familiar with the chain of responsibility design pattern
- Have experience with Java interfaces and inheritance

## Topics {#topics}

<DocCardList className="topics-section" />
