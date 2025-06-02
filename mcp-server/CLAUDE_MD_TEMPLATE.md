# webforJ Anti-Patterns Template for CLAUDE.md

This template provides a section that webforJ users can copy-paste into their project's CLAUDE.md file to help AI assistants avoid common mistakes when generating webforJ code.

## Copy-Paste Section for CLAUDE.md

Add the following section to your project's CLAUDE.md file:

---

## webforJ Framework Patterns & Anti-Patterns

This project uses the webforJ framework. Please follow these specific patterns and avoid common mistakes:

### ✅ Correct webforJ Patterns

#### Lifecycle Management
- Use **interface-based lifecycle**: `implements DidEnterObserver, DidLeaveObserver`
- **NOT** event-based: No `onAttach()`, `onDetach()`, `addEventListener("mount")`
- **Correct**: `onDidEnter(DidEnterEvent event)` and `onDidLeave(DidLeaveEvent event)`

#### Async Operations
- Use **CompletableFuture** for non-blocking operations
- **NOT** JavaScript patterns: No `async/await`, `Promise.then()`
- **Correct**: `CompletableFuture.supplyAsync(() -> {...}).thenAccept(result -> {...})`

#### Event Handling
- Use **specific event methods**: `button.onClick(e -> {...})`
- **NOT** generic listeners: No `addEventListener("click", ...)`
- **Correct**: `onClick()`, `onDoubleClick()`, `onChange()`

#### Component Creation
- Use **constructor + setters**: `new Button("Text").setTheme(ButtonTheme.PRIMARY)`
- **NOT** object literals: No `new Button({text: "Text", theme: PRIMARY})`
- **Correct**: Fluent API with method chaining

#### State Management
- Use **Java instance fields** for component state
- **NOT** React-style hooks: No `useState()`, `useEffect()`
- **Correct**: `private String userName = "";` with direct field updates

#### Styling
- Use **CSS classes** and **theme variants**: `addClassName("my-style")`, `setTheme(ButtonTheme.PRIMARY)`
- **NOT** CSS-in-JS: No `setStyles({display: "flex"})` or styled-components
- **Correct**: `setStyle("property", "value")` or CSS files in `/src/main/resources/css/`

#### Application Structure
- **App class**: Extends `App` for application entry point with `run()` method
- **View classes**: Extend `Composite` for individual views and components
- **Routing**: Use `@Route` annotations (NOT imperative `router.navigate()`)
- **Focus management**: Use component's `focus()` method (NOT JavaScript `document.getElementById().focus()`)

### ❌ Common Anti-Patterns to Avoid

1. **Event-based lifecycle**: `onAttach()`, `onDetach()`, `mounted()`, `unmounted()`
2. **JavaScript async**: `async/await`, `Promise.then()`, `.catch()`
3. **Generic events**: `addEventListener()`, `removeEventListener()`
4. **React patterns**: `useState()`, `useEffect()`, `props`, JSX syntax
5. **CSS-in-JS**: Object-based styling, styled-components syntax
6. **Component caching**: Storing webforJ components in static collections (causes memory leaks)
7. **JavaScript DOM**: `document.getElementById()`, `element.style`, `element.classList`

### Framework Context
- **Language**: Java (NOT JavaScript/TypeScript)
- **UI Paradigm**: Server-side component-based (NOT client-side reactive)
- **Class hierarchy**: `App` for entry point, `Composite` for views/components
- **Styling**: CSS classes + theme system (NOT inline styles or CSS-in-JS)
- **State**: Direct field manipulation (NOT reactive state management)

---

## Usage Instructions

1. Copy the section above (from "## webforJ Framework Patterns" to the end)
2. Paste it into your project's CLAUDE.md file
3. AI assistants will automatically read this context and follow webforJ patterns
4. Customize the patterns based on your specific project needs

## Benefits

- **Immediate context**: AI gets webforJ-specific guidance from the start
- **Prevents common mistakes**: Blocks patterns from React, Vue, JavaFX confusion  
- **Project-specific**: Each webforJ project can have this guidance
- **Easy to maintain**: Update the template as new anti-patterns are discovered

## Maintenance

Keep this template updated by monitoring:
- New anti-patterns discovered in webforJ projects
- Framework updates that change recommended patterns
- User feedback on missed or incorrect guidance

This approach leverages CLAUDE.md's automatic loading to provide immediate framework context without requiring MCP server changes or complex RAG systems.