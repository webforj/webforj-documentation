---
title: "Spring Security role-based UI access: why protecting routes beats hiding elements"
description: "Template conditionals like sec:authorize decide what HTML to emit — route-level annotations decide whether to render at all. They're not the same thing."
slug: spring-security-route-access-control
date: 2026-10-02
authors: webforJ
tags: [spring, security, web development]
image: ./cover.png
hide_table_of_contents: false

# --- Internal tracking (stripped at publish) ---
---

![cover](./cover.png)

A developer asks: "How do I hide the admin panel from non-admin users?" The search results all point in the same direction — `sec:authorize` in Thymeleaf templates, or `th:if` wrapped around a role expression from Spring Security's authorization context. It's practical advice that works for what it does. But what it does is different from what the question was asking.

Hiding an element and restricting access to a view are different operations. Conflating them is how unintended access surfaces in production.

<!-- truncate -->

## Two problems that look the same

The developer's intent is usually to prevent unauthorized users from reaching a view. What template conditionals address is whether to include a particular element in the HTML the server generates.

Those operations run at different points in the request cycle. Template conditionals — `sec:authorize`, `th:if` with a role expression — evaluate at render time. By then the request has already been routed to the view, the framework has committed to generating a response, and rendering is underway. The conditional shapes what goes into that response, not whether the response happens at all.

Route-level enforcement intercepts before any of that. An annotation on the view class itself is evaluated by the security system before the first component is constructed. If the check fails, rendering never starts — the request is redirected before a response is generated.

The surface difference is subtle: both approaches result in the user not seeing the restricted content. The structural difference is significant: one determines the output of a render; the other determines whether the render occurs.

## What template conditionals do

`sec:authorize` in a Thymeleaf template is a render-time decision about the HTML output. If the user lacks the required role, the element is not emitted. The link doesn't appear. The section is absent from the response.

What the template conditional doesn't do is stop the user from navigating to the target route directly. The conditional is on the element pointing to the destination, not on the destination. A user who knows the URL — from a bookmark, a shared link, browser history, or a network scan — can reach the unprotected route regardless of what any template chooses to render.

Template conditionals are a presentation layer. They control what the interface shows. They don't control who can reach a view.

## What route-level enforcement does instead

Route-level security shifts the enforcement point. When `@RolesAllowed` or `@RouteAccess` appears on a Java view class, the security system evaluates it before instantiating the view. The webforJ [Security Annotations](/docs/security/annotations) documentation describes this directly: "the security system automatically enforces these rules before any component is rendered."

If the check fails, the view class is never touched. The components in that view are never constructed. There is no rendered output to shape — the user is redirected at the navigation interceptor.

This changes the security posture in a concrete way: there is no path to the view that skips the check. Direct URL navigation, history manipulation, and any client-side mechanism that triggers a route change all arrive at the same enforcement point. The annotation is on the thing being protected, not on a pointer to it.

It also keeps the access policy co-located with the view it governs. The developer reading the view class can see its security requirements in the annotation — they don't need to scan templates to determine who can reach this route.

## The production hardening rule

webforJ's production hardening documentation addresses the visibility/security distinction directly. Under "Disabled and hidden aren't security," the [Production Hardening guide](/docs/security/application-security/production-hardening) states:

> `setEnabled(false)` and `setVisible(false)` are interface cues, not access controls. webforJ mirrors a control's disabled state to the client, but it doesn't stop a manipulated client from re-enabling that control and triggering its action.

The instruction that follows: put the real rule in the server-side handler. A disabled button is a UX signal. A server-side permission check is what stops a manipulated client.

Template conditionals operate on the same principle. `sec:authorize` guides users by not showing them paths they can't follow. A `@RolesAllowed` annotation on the route class stops any client from rendering the view. The former is a courtesy to the user; the latter is a constraint on the system.

## When template conditionals are the right tool

None of this argues against template conditionals as a UX mechanism. The case for using them holds in the right context.

Hiding navigation items that point to routes a user can't reach makes the interface less cluttered, and conditionally rendering UI sections based on role keeps the experience consistent with what each role is expected to do. These are presentation decisions — they reduce noise and shape the UX for a given set of permissions.

The template conditional is the right tool for those cases. The access control for the views those elements point to belongs at the route level.

Route-level annotations enforce access; template conditionals reflect access. Both are useful. The mistake is using one to do the job of the other.

## Where each belongs

A common pattern that creates exposure: the view exists, the route is unannotated, and `sec:authorize` hides the link to it. From the user's perspective, the link is gone. From an attacker's perspective, the route is reachable via direct URL.

The robust version: `@RolesAllowed` on the view class, then optionally a matching `sec:authorize` or `th:if` in the template to remove the corresponding link. The annotation is the enforcement fact; the hidden link is the UX reflection of that fact. If the annotation changes — a new role is added, a permission is revised — the route policy updates regardless of whether the template conditional was updated to match. If someone removes the template conditional, the route still enforces the correct access.

One layer enforces; the other reflects. For views that carry sensitive data or privileged actions, the enforcement layer is non-negotiable. The reflection layer is a courtesy.

webforJ covers both patterns in the [Security Annotations](/docs/security/annotations) reference — `@RolesAllowed`, `@PermitAll`, `@AnonymousAccess`, and `@DenyAll` for the common cases, and [`@RouteAccess` with SpEL expressions](/docs/security/spel-expressions) for more complex role and authority combinations.
