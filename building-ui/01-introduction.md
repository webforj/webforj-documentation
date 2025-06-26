# Introduction

webforJ is a modern Java framework that bridges the gap between server-side Java and client-side web technologies. This guide focuses on one of webforJ's most powerful features: the ability to create custom components and integrate any web component library seamlessly.

## Why This Guide Matters

- **Universal Integration**: Learn patterns that work with ANY web component library (Shoelace, Material Design, custom components)
- **Type Safety**: Maintain Java's type safety while working with dynamic web technologies
- **Performance**: Understand client-server optimization strategies
- **Best Practices**: Avoid common pitfalls and anti-patterns

## What You'll Learn

- **Composition Patterns**: How to build components using Composite and ElementComposite
- **ElementComposite Integration**: Seamless web component integration with type safety
- **Property Management**: Type-safe property handling with PropertyDescriptor
- **Event Handling**: Advanced event patterns and performance optimization
- **CSS Integration**: Professional styling and theming strategies
- **Testing**: Comprehensive testing approaches for composed components
- **Best Practices**: Proven patterns for maintainable component architecture

## Critical Rule: Always Use Composition

**NEVER extend Component or DwcComponent directly.** This guide focuses exclusively on composition patterns:

- **Use `Composite<T>`** for business logic components
- **Use `ElementComposite`** for web component integration  
- **Use `ElementCompositeContainer`** for slotted components
- **Never extend `Component`** - it's framework internal
- **Never extend `DwcComponent`** - all built-in components are final

---

## Next Steps

Ready to dive in? Continue to [Component Foundation](02-component-foundation.md) to understand the core concepts.

## Navigation

- [← Back to Index](00-index.md)
- [Next: Component Foundation →](02-component-foundation.md)