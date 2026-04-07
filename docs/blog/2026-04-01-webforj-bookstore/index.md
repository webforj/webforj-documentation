---
title: Building a Bookstore App with webforJ and Spring Boot
description: A walkthrough of the webforJ Bookstore, a full-stack inventory manager with live filtering, data binding, custom table renderers, and Spring Security
slug: webforj-bookstore
date: 2026-04-01
authors: Eric Handtke
tags: [spring, spring security, web development, tutorial, full-stack]
image: "https://cdn.webforj.com/webforj-documentation/blogs/2026-04-01-webforj-bookstore/cover.png"
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-04-01-webforj-bookstore/cover.png)

My colleague Matthew recently wrote about [his first foray into full-stack development](https://docs.webforj.com/blog/Full-stack%20development%20with%20webforJ) with webforJ and Spring Boot. Reading it, I kept wondering what a more fully featured version of that same stack would look like, with real filtering, a richer UI, and users who actually have to log in.

That turned into the [webforJ Bookstore](https://github.com/webforj/built-with-webforj/tree/main/webforj-bookstore), a book inventory manager I built to push webforJ and Spring Boot a bit further. It has a live-filtering table, genre management with colored chips, a data-bound edit drawer, and a Spring Security layer with role-based access. There's a lot going on, so this post walks through the parts I found most interesting.

<!-- truncate -->

![Screenshot of Bookstore app](https://cdn.webforj.com/webforj-documentation/blogs/2026-04-01-webforj-bookstore/app-preview.png)

## The bookstore at a glance

Here's what the app does:

- Browse, search, and sort a book inventory in a live `Table`
- Add and edit books using a slide-out `Drawer` with automatic data binding and validation
- Create and manage genres with custom colors, displayed as colored chips in each row
- Log in as a regular user or an admin, with Spring Security protecting views based on role

Two accounts ship with the app for testing:

| Username | Password | Role |
|----------|----------|------|
| `user` | `password` | User |
| `admin` | `admin` | User + Admin |

## Connecting the table to Spring Data

The backbone of the inventory is a `Table<Book>` fed by a `SpringDataRepository`. webforJ's `SpringDataRepository` wraps a standard Spring Data `JpaRepository` and gives the `Table` component a way to page, sort, and filter rows without loading everything into memory at once.

Setting it up in `BookService` is one method:

```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

  private final BookRepository bookRepository;

  public SpringDataRepository<Book, String> getFilterableRepository() {
    return new SpringDataRepository<>(bookRepository);
  }
}
```

And in `InventoryView`, plugging it into the table takes two lines:

```java
repository = bookService.getFilterableRepository();
bookTable.setRepository(repository);
```

From that point on, the table manages its own data lifecycle. Sorting by any column, scrolling through hundreds of rows, and updating after a save all go through the repository without any extra plumbing.

## Live filtering with JPA Specifications

The search bar and genre filter both update the table in real time. Each time the user types or picks a genre, the view builds a JPA `Specification` and hands it to the repository:

```java
Specification<Book> searchSpec = (root, query, cb) -> {
  query.distinct(true);
  Predicate predicate = cb.conjunction();

  if (!term.isEmpty()) {
    String escaped = escapeLikePattern(term);
    predicate = cb.and(predicate, cb.or(
        cb.like(cb.lower(root.get("title")), "%" + escaped + "%", '\\'),
        cb.like(cb.lower(root.get("author")), "%" + escaped + "%", '\\')));
  }

  if (genre != null) {
    predicate = cb.and(predicate, cb.isMember(genre, root.get("genres")));
  }

  return predicate;
};
repository.setFilter(searchSpec);
repository.commit();
```

`repository.commit()` is the trigger that tells the `Table` to re-fetch its data. The JPA `Specification` runs as a proper SQL query, so filtering against a large dataset stays fast.

One small detail worth noting: `escapeLikePattern` sanitizes the search term before it goes into the `LIKE` clause to handle input that contains `%`, `_`, or `\`. It's a short helper, but skipping it would open up accidental wildcard matches.

## Custom genre chip renderer

Genres are stored as a list of strings on each `Book`, but they display as colored pill badges in the table. That's handled by `GenreChipRenderer`, a custom `Renderer` that outputs inline HTML for each row:

```java
public class GenreChipRenderer<T> extends Renderer<T> {

  @Override
  public String build() {
    return """
        <div style='display:flex;gap:var(--dwc-space-xs);flex-wrap:wrap;'>
          <% var genres = JSON.parse(cell.value || '[]'); %>
          <% genres.forEach(function(g) { %>
            <span style='display:inline-flex;align-items:center;...'>
              <span style='width:8px;height:8px;border-radius:50%;background:<%- g.color %>;'></span>
              <span><%- g.name %></span>
            </span>
          <% }); %>
        </div>
        """;
  }
}
```

The column passes a JSON string (a serialized list of `{name, color}` objects), and the renderer parses and renders it as pills. Each genre gets its own dot in whatever color was assigned when the genre was created.

Wiring it to the column in `InventoryView` is straightforward:

```java
bookTable.addColumn("Genres", book -> GSON.toJson(
    book.getGenres().stream()
        .map(name -> Map.of("name", name, "color", genreColorCache.getOrDefault(name, DEFAULT_GENRE_COLOR)))
        .collect(Collectors.toList())))
    .setRenderer(new GenreChipRenderer<>());
```

I've worked with table renderers before in the [dashboard project](https://docs.webforj.com/dashboard/), and this pattern never gets old. Being able to drop arbitrary HTML into a cell while keeping all the data handling in Java is a genuinely useful combination.

## Data binding in the edit drawer

Clicking any row opens an `InventoryDrawer`, a `Drawer` component that lets users add or edit a book. The form fields map directly to a `Book` instance through webforJ's `BindingContext`:

```java
bindingContext = BindingContext.of(this, Book.class, true);
```

That single line wires every form field in the drawer to the corresponding property on `Book`. Fields with `@UseProperty` annotations handle cases where the field name doesn't match the model property:

```java
@UseProperty("author")
private ChoiceBox authorBox;

@UseProperty("publisher")
private ChoiceBox publisherBox;
```

When the user hits Save, `bindingContext.write()` pushes the form values back to the model and runs Jakarta validation in the same pass:

```java
private void saveBook() {
  ValidationResult result = bindingContext.write(this.currentBook);
  if (result.isValid()) {
    if (onSave != null) {
      onSave.accept(this.currentBook);
    }
    self.close();
  }
}
```

If validation fails, the binding context surfaces errors on the individual fields automatically. If it passes, the book goes to the service layer and the table refreshes. No manual getter/setter calls, no separate validation logic to maintain.

<!-- vale Google.Headings = NO -->
## Adding Spring Security
<!-- vale Google.Headings = YES -->

The app uses Spring Security for authentication and role-based access. Connecting it to webforJ's routing is done through `WebforjSecurityConfigurer`:

```java
@Bean
SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  return http
      .with(WebforjSecurityConfigurer.webforj(), configurer -> configurer
          .loginPage(LoginView.class)
          .accessDeniedPage(AccessDenyView.class)
          .logout())
      .build();
}
```

Rather than hardcoding URL strings, you pass your actual view classes. webforJ resolves the paths at runtime. Three things get set up in this one call: the login page, a custom access-denied page, and logout support.

### Public routes and role restrictions

The login view is marked `@AnonymousAccess` so unauthenticated users can reach it:

```java
@Route("/signin")
@AnonymousAccess
public class LoginView extends Composite<Login> {
  // ...
}
```

The admin-only management section uses a standard Jakarta annotation to declare its access requirement:

```java
@Route(value = "/management", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class ManagementView extends Composite<FlexLayout> {
  // ...
}
```

That's it for access control at the route level. Spring Security handles the rest: if a user without the `ADMIN` role navigates to `/management`, they're redirected to the access-denied page.

### Role-aware navigation

The navigation drawer also adapts based on role. In `MainLayout`, `SecurityContextHolder` gives access to the current user's authorities:

```java
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
if (auth != null && auth.getAuthorities().stream()
    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
  appNav.addItem(new AppNavItem("Management", ManagementView.class, TablerIcon.create("users")));
}
```

Regular users never see the Management tab. Admins do. The same `auth` object provides the username shown in the toolbar avatar, so there's a single source of truth for the current session state.

Logout is a one-liner using `SpringSecurityFormSubmitter`:

```java
logoutBtn.addClickListener(e ->
    SpringSecurityFormSubmitter.logout("/logout").submit()
);
```

## Wrapping up

Building the Bookstore gave me a good look at what a more complete webforJ and Spring Boot app looks like. The parts that stood out most:

- `SpringDataRepository` makes the gap between a JPA repository and a live `Table` almost disappear
- JPA `Specification` filtering composes well and stays fast
- `BindingContext` handles form-to-model wiring and validation in one step
- Custom `Renderer` implementations are a clean way to go beyond plain text in table cells
- Spring Security integrates without fighting the rest of the stack

None of these pieces required anything unusual. Each one fits into the same Java-first, annotation-driven style that makes Spring Boot and webforJ work well together in the first place.

## Get the source code

The full source code is available on GitHub:

🔗 **[View the source code on GitHub](https://github.com/webforj/built-with-webforj/tree/main/webforj-bookstore)**

Clone it, run it with `mvn spring-boot:run`, and log in with both accounts to see the full app.
