# AI Antipatterns Training Data

This directory contains examples of common mistakes AI makes when generating webforJ code, along with the correct patterns.

## Purpose

Help AI models understand:
1. What NOT to generate (incorrect patterns from other frameworks)
2. What TO generate (correct webforJ patterns)
3. Why certain patterns are wrong
4. How to map concepts from other frameworks correctly

## Structure

```
antipatterns/
├── lifecycle/          # Lifecycle management patterns
│   ├── incorrect/     # What AI shouldn't generate
│   └── correct/       # What AI should generate
├── events/            # Event handling patterns
├── state/             # State management patterns
├── routing/           # Navigation and routing patterns
├── styling/           # CSS and styling patterns
└── async/             # Asynchronous operations
```

## How to Use

1. **For Training**: Include both incorrect and correct examples in training datasets
2. **For Validation**: Test AI outputs against these patterns
3. **For Documentation**: Reference in AI guidance documents

## Key Principles

### 1. webforJ is NOT React/Vue
- No hooks (`useState`, `useEffect`)
- No virtual DOM
- No JSX or templates in Java

### 2. webforJ is Interface-Based
- Lifecycle through interfaces (`DidEnterObserver`, etc.)
- Event handling through specific methods (`onClick`, not `addEventListener`)
- Strong typing throughout

### 3. webforJ is Java-First
- Standard Java patterns apply
- CompletableFuture for async, not promises
- Inheritance and interfaces, not mixins

## Common Mistakes by Category

### Lifecycle
- ❌ `onAttach()`, `onDetach()`, `mounted()`
- ✅ `DidEnterObserver`, `DidLeaveObserver` interfaces

### Events
- ❌ `addEventListener("click", ...)`
- ✅ `onClick()`, `onBlur()`, etc.

### State
- ❌ `useState()`, `setState()`
- ✅ Instance fields with direct updates

### Routing
- ❌ `router.push()`, `navigate()`
- ✅ `@Route` annotation, `Link` component

### Styling
- ❌ CSS-in-JS, styled-components
- ✅ CSS files, `addClassName()`, themes

## Contributing

When you notice AI making a mistake:
1. Document the incorrect pattern
2. Provide the correct webforJ equivalent
3. Add examples to the appropriate category
4. Include comments explaining why it's wrong