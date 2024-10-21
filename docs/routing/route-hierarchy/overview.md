<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# Route hierarchy

Routes are organized into a hierarchical tree structure that enables developers to define layouts, manage views, and dynamically render components across various parts of the app. 

The key concepts you’ll encounter when building a webforJ routable app include:

- **Route Hierarchy**: Organizes routes into parent-child structures for modular UI development.
- **Route Types**: Routes are categorized as either **View Routes** or **Layout Routes**, each serving a different purpose.
- **Nested Routes**: Routes can be nested within each other, allowing parent components to render child components in designated outlets.
- **Outlets**: Components where child views are dynamically injected into parent layouts.
- **Layouts**: Special routes that wrap child components without contributing to the URL, providing shared UI elements such as headers, footers, or sidebars.

## Topics

<DocCardList className="topics-section" />