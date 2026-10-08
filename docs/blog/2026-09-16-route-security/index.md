---
title: "Securing webforJ Routes Before the View Exists"
description: "Route annotations stop people reaching a view. What they don't do is follow you inside it, and that distinction decides where your access checks belong."
slug: route-security
date: 2026-09-23
authors: Lauren Alamo
tags: [security, routing, web development, tutorial]
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-16-route-security/blog-cover.png)

<!-- vale Google.FirstPerson = NO -->

Broken access control has sat at the top of the [OWASP Top 10](https://owasp.org/Top10/2025/A01_2025-Broken_Access_Control/) since 2021, and the 2025 list keeps it there. One of OWASP's own example scenarios is an app that keeps all its access control in the front end, where the attacker can't click through to the admin page because the JavaScript won't let them, so they skip the browser and request the URL with `curl` instead.

Here's a version of that you've probably written:

```java
if (isAdmin) {
    Button delete = new Button("Delete user");
    delete.onClick(e -> userService.delete(selected));
    self.add(delete);
}
```

Ordinary users don't see the button. That feels like enough, and in a server-driven framework it feels like more than enough, because the business logic is all sitting safely in Java. This post is about what actually protects that handler, what webforJ gives you for free, and where the line between the two falls.

<!-- truncate -->

## The route half {#the-route-half}

webforJ added route security in `25.10`, and the part it handles is getting people to the view in the first place. One annotation on the route class:

```java
@Route(value = "/admin/users", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class UserAdminView extends Composite<FlexLayout> {
    // view implementation
}
```

No check at the top of the constructor, no guard clause to copy into the next admin view you write. Four annotations cover the common cases:

| Annotation | Who gets in |
| --- | --- |
| `@AnonymousAccess` | Everyone, logged in or not |
| `@PermitAll` | Any authenticated user |
| `@RolesAllowed("ADMIN")` | Users with at least one of the listed roles |
| `@DenyAll` | Nobody |

Three of those are the standard Jakarta annotations rather than webforJ inventions. `@PermitAll`, `@RolesAllowed`, and `@DenyAll` are read straight from `jakarta.annotation.security`, with the same meanings they carry in a Jakarta EE app, so the vocabulary transfers if you've secured a Java app before. Only `@AnonymousAccess` is specific to webforJ, since unauthenticated access to a route isn't something the Jakarta set covers.

The annotations are only names, though. What webforJ supplies is everything that gives them effect: a security manager that hooks the router, an evaluator per annotation, the chain those evaluators run in, the decision and redirect handling, and the session storage behind it. Reusing the Jakarta names means none of that arrives as vocabulary you have to learn on top of the behavior.

## Why the timing matters {#why-the-timing-matters}

The check happens on `BEFORE_CREATE`, at the point where the router has resolved which class it's about to render but hasn't constructed it yet. That's the detail I'd point to if someone asked what's good about this feature.

Most views do real work in their constructor, loading records through a service, running a repository query, sometimes doing things that are slow or that write to a log. A permission check written inside the view runs after all of that has already happened, so a user who shouldn't be there has still triggered the queries by the time you turn them away. Because webforJ evaluates on `BEFORE_CREATE`, a denied user doesn't run any of it and the class is never instantiated. It's early enough in the lifecycle that your own navigation observers don't fire either.

So the route is genuinely handled, and handled earlier than a check you'd write yourself could manage. The same design decision is what sets the boundary: evaluation is tied to navigation, so it runs when someone asks the router for a route, and not at any other time.

## Back to the button {#back-to-the-button}

The [accessing user](/docs/security/accessing-user) documentation has an example that sits right on that boundary:

```java
boolean isAdmin = auth.getAuthorities().stream()
    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

if (isAdmin) {
    Button adminPanel = new Button("Admin Panel");
    adminPanel.onClick(e -> Router.getCurrent().navigate(AdminView.class));
    self.add(adminPanel);
}
```

This one is fine, and it's worth being precise about why. The button navigates to `AdminView`, which carries its own annotation and gets checked on arrival. The `if (isAdmin)` is presentation, keeping an unusable control off the screen, while the enforcement happens at the destination.

Now compare it to the snippet at the top of this post. Structurally they're the same, but `userService.delete(selected)` isn't a navigation. The click runs a handler on a view that's already open, so there's no route for the router to evaluate and no annotation involved. That leaves the `if (isAdmin)` as the only thing standing between an ordinary user and the delete call, and it's living in the browser's copy of the interface, which is exactly where OWASP's scenario said it can't hold.

The fix is a check in the handler itself:

```java
delete.onClick(e -> {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    boolean allowed = auth.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    if (!allowed) {
        return;
    }

    userService.delete(selected);
});
```

Keep the `if (isAdmin)` around the button too, since hiding a control nobody can use is still good design. It just isn't what stops anyone.

None of this is particular to webforJ. Route guards guard routes, and the division that's worked for me is that the annotation decides who gets into the room and the handler decides what they can do once they're in it. What webforJ does is make the first half disappear as a thing you think about, which is most of the value, because that's the half you'd otherwise be hand-writing in every view and eventually forgetting in one.

## Worth knowing {#worth-knowing}

A few things sit around the edges of this that are covered properly in the docs but worth flagging.

`webforj.security.secure-by-default=true` decides what an unannotated route does. With it on, a route with no annotation requires authentication and `@AnonymousAccess` is how you open something to the public. It's the setting I'd want on, because forgetting an annotation should fail in a direction somebody notices: an authenticated user reaching a page meant for a narrower group gets reported, while an admin page on the open internet can go unnoticed indefinitely.

Behind the annotations is a [chain of evaluators](/docs/security/architecture/evaluator-chain) running in priority order, and you can register your own when the four annotations don't express your rule. The chain lives in `webforj-foundation`, so custom evaluators work whether or not you're on Spring.

On Spring specifically, [`@RouteAccess`](/docs/security/spel-expressions) takes a SpEL expression for rules that need more than a role, like `hasRole('MANAGER') and hasAuthority('REPORTS:READ')`. Past a certain complexity I'd write an evaluator instead so the logic stays in Java where the compiler can check it.

There's also a nice touch in the denial path. Before redirecting, webforJ stores the location the user originally requested, and on Spring the `WebforjAuthenticationSuccessHandler` picks it back up after login, so someone who followed a deep link into a protected page finishes on that page rather than a generic dashboard.

One caveat on all of it: route security is still marked public preview. The documentation describes it as ready for production and notes the API may be refined during the preview, with release notes and migration guidance for any changes.

## Wrapping up {#wrapping-up}

Declarative access control in server-side code is what OWASP recommends, and one annotation on a route class, enforced before the view is constructed, is a clean version of it.

The thing to hold onto is what that annotation is actually promising. It gets the right people to the view. Once they're there, anything that isn't a navigation is still yours to check, and the `if` statement that hides the button was never the part doing the work.

The [security documentation](/docs/security/overview) covers the whole feature, including custom evaluators for rules the annotations don't reach.