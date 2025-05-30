# MCP Server Curation Guide

This guide explains how to properly curate the webforJ MCP server with demos, code snippets, and training data to enable effective AI-assisted development.

## Overview

The MCP server needs structured data in specific locations to provide comprehensive assistance. Currently, the server can find components but lacks practical examples and implementation details.

## Directory Structure Required

```
webforj-documentation/
├── src/main/java/com/webforj/samples/views/
│   ├── button/
│   │   ├── ButtonView.java          # Basic button demo
│   │   ├── ButtonThemesView.java    # Theme variations
│   │   ├── ButtonEventsView.java    # Event handling
│   │   └── ButtonIconsView.java     # With icons
│   └── [component]/
│       └── [Component]View.java
├── src/main/resources/
│   ├── css/
│   │   ├── button/
│   │   │   ├── button.css
│   │   │   └── buttonThemes.css
│   │   └── [component]/
│   └── data/
│       └── [component]-examples.json
└── docs/docs/components/
    ├── button.md
    └── [component].md
```

## 1. Java Demo Files

### Location
`src/main/java/com/webforj/samples/views/[component]/`

### Required Annotations
Every demo MUST have:
- `@Route` annotation with the demo route
- `@FrameTitle` annotation (optional but recommended)
- Class-level Javadoc

### Example: ButtonView.java
```java
package com.webforj.samples.views.button;

import com.webforj.App;
import com.webforj.annotation.FrameTitle;
import com.webforj.annotation.Route;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

/**
 * Basic button demonstration showing click handling and text updates.
 * This demo illustrates the fundamental button usage pattern in webforJ.
 */
@Route("button")
@FrameTitle("Button - Basic Demo")
public class ButtonView extends App {
    
    private int clickCount = 0;
    private Paragraph statusText;
    
    @Override
    public void run() {
        // Create container
        Div container = new Div();
        container.setStyle("padding", "20px");
        
        // Create button
        Button button = new Button("Click Me!");
        button.setTheme(ButtonTheme.PRIMARY);
        button.onClick(e -> handleClick());
        
        // Create status text
        statusText = new Paragraph("Button has not been clicked yet.");
        
        // Add components
        container.add(button, statusText);
        add(container);
    }
    
    private void handleClick() {
        clickCount++;
        statusText.setText("Button clicked " + clickCount + " times!");
    }
}
```

### Naming Conventions
- Main demo: `[Component]View.java` (e.g., `ButtonView.java`)
- Theme demo: `[Component]ThemesView.java`
- Events demo: `[Component]EventsView.java`
- Advanced features: `[Component][Feature]View.java`

## 2. Documentation Files

### Location
`docs/docs/components/[component].md`

### Required Structure
```markdown
---
title: Button
sidebar_label: Button
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import ComponentHeader from '@site/src/components/DocsTools/ComponentHeader';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<ComponentHeader 
  title="Button"
  javadocUrl="com/webforj/component/button/Button"
/>

## Overview

The Button component is used for triggering actions in your application...

## Basic Usage

<ComponentDemo path="/webforj/button" />

```java
Button button = new Button("Click Me");
button.onClick(e -> {
    // Handle click
});
```

## Properties

| Property | Type | Description | Default |
|----------|------|-------------|---------|
| text | String | The button label | "" |
| enabled | boolean | Whether the button is enabled | true |
| theme | ButtonTheme | Visual theme | DEFAULT |

## Methods

| Method | Parameters | Returns | Description |
|--------|------------|---------|-------------|
| setText | String text | void | Sets the button text |
| onClick | ClickEventListener | Button | Adds click listener |
| setTheme | ButtonTheme theme | void | Sets visual theme |

## Events

| Event | Payload | Description |
|-------|---------|-------------|
| onClick | ClickEvent | Fired when button is clicked |
| onFocus | FocusEvent | Fired when button gains focus |
| onBlur | BlurEvent | Fired when button loses focus |

## Examples

### Theme Variations
<ComponentDemo path="/webforj/button-themes" />

### With Icons
<ComponentDemo path="/webforj/button-icons" />

## Common Patterns

### Loading State
```java
Button submitButton = new Button("Submit");
submitButton.onClick(e -> {
    submitButton.setEnabled(false);
    submitButton.setText("Loading...");
    
    // Perform async operation
    performOperation().thenAccept(result -> {
        submitButton.setEnabled(true);
        submitButton.setText("Submit");
    });
});
```
```

## 3. Code Snippets Database

### Location
`src/main/resources/data/[component]-snippets.json`

### Structure
```json
{
  "component": "button",
  "imports": [
    "com.webforj.component.button.Button",
    "com.webforj.component.button.ButtonTheme",
    "com.webforj.component.button.event.ButtonClickEvent"
  ],
  "snippets": [
    {
      "name": "basic",
      "description": "Basic button with click handler",
      "code": "Button button = new Button(\"Click Me\");\nbutton.onClick(e -> System.out.println(\"Clicked!\"));"
    },
    {
      "name": "themed",
      "description": "Button with theme",
      "code": "Button button = new Button(\"Primary Action\");\nbutton.setTheme(ButtonTheme.PRIMARY);"
    },
    {
      "name": "disabled",
      "description": "Disabled button",
      "code": "Button button = new Button(\"Disabled\");\nbutton.setEnabled(false);"
    },
    {
      "name": "with_icon",
      "description": "Button with icon",
      "code": "Button button = new Button(\"Save\");\nbutton.setIcon(\"save\");\nbutton.setIconPosition(Button.IconPosition.LEFT);"
    }
  ],
  "patterns": [
    {
      "name": "confirm_action",
      "description": "Button with confirmation dialog",
      "code": "Button deleteButton = new Button(\"Delete\");\ndeleteButton.setTheme(ButtonTheme.DANGER);\ndeleteButton.onClick(e -> {\n    ConfirmDialog.show(\"Confirm Delete\", \"Are you sure?\", confirmed -> {\n        if (confirmed) {\n            // Perform delete\n        }\n    });\n});"
    }
  ]
}
```

## 4. CSS Resources

### Location
`src/main/resources/css/[component]/`

### Naming Convention
- Main styles: `[component].css`
- Demo-specific: `[component][Feature].css`

### Example: button.css
```css
/* Custom button demo styles */
.demo-button-container {
    display: flex;
    gap: 10px;
    flex-wrap: wrap;
    padding: 20px;
}

.demo-button-group {
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    padding: 15px;
    margin-bottom: 20px;
}

.demo-button-label {
    font-size: 14px;
    color: #666;
    margin-bottom: 10px;
}
```

## 5. Component Metadata

### Location
`docs/docs/components/_metadata/[component].json`

### Structure
```json
{
  "name": "button",
  "displayName": "Button",
  "package": "com.webforj.component.button",
  "extends": "com.webforj.component.Component",
  "implements": ["Clickable", "Focusable"],
  "constructors": [
    {
      "signature": "Button()",
      "description": "Creates a button with no text"
    },
    {
      "signature": "Button(String text)",
      "description": "Creates a button with the specified text"
    },
    {
      "signature": "Button(String text, ButtonTheme theme)",
      "description": "Creates a button with text and theme"
    }
  ],
  "commonUseCases": [
    "Form submission",
    "Dialog actions",
    "Navigation triggers",
    "Action confirmations"
  ],
  "relatedComponents": [
    "IconButton",
    "ButtonGroup",
    "ToggleButton"
  ]
}
```

## 6. Training Data Format

### Location
`mcp-server/training-data/[component]/`

### Structure
Create multiple example files showing different use cases:

#### basic-form.java
```java
// Training example: Button in a form
public class FormExample extends App {
    @Override
    public void run() {
        Form form = new Form();
        
        TextField nameField = new TextField();
        nameField.setLabel("Name");
        
        Button submitButton = new Button("Submit");
        submitButton.onClick(e -> {
            String name = nameField.getValue();
            showToast("Hello, " + name + "!");
        });
        
        form.add(nameField, submitButton);
        add(form);
    }
}
```

#### responsive-layout.java
```java
// Training example: Responsive button layout
public class ResponsiveButtons extends App {
    @Override
    public void run() {
        FlexLayout layout = new FlexLayout();
        layout.setGap("10px");
        layout.setWrap(true);
        
        Button primaryBtn = new Button("Primary Action");
        primaryBtn.setTheme(ButtonTheme.PRIMARY);
        
        Button secondaryBtn = new Button("Secondary");
        secondaryBtn.setTheme(ButtonTheme.SECONDARY);
        
        layout.add(primaryBtn, secondaryBtn);
        add(layout);
    }
}
```

## 7. Integration Checklist

For each component, ensure:

- [ ] **Java Demos**
  - [ ] Basic demo (`[Component]View.java`)
  - [ ] Theme/style variations
  - [ ] Event handling examples
  - [ ] Advanced features
  - [ ] All demos have `@Route` annotation
  - [ ] All demos have descriptive Javadoc

- [ ] **Documentation**
  - [ ] Overview with use cases
  - [ ] Properties table with types
  - [ ] Methods table with signatures
  - [ ] Events table with payloads
  - [ ] Code examples for each feature
  - [ ] Common patterns section

- [ ] **Code Snippets**
  - [ ] Basic usage snippet
  - [ ] All constructor variations
  - [ ] Common configurations
  - [ ] Integration patterns
  - [ ] Import statements

- [ ] **CSS Resources**
  - [ ] Demo-specific styles
  - [ ] Responsive examples
  - [ ] Theme variations

- [ ] **Metadata**
  - [ ] Package information
  - [ ] Constructor signatures
  - [ ] Related components
  - [ ] Common use cases

## 8. Testing Your Curation

After adding content, verify:

1. **Build the MCP index**
   ```bash
   cd mcp-server
   npm run build
   npm start
   ```

2. **Test component search**
   ```javascript
   // Should return your component with all metadata
   search_components({ query: "button" })
   ```

3. **Test code retrieval**
   ```javascript
   // Should return full source code
   get_component_code({ route: "button" })
   ```

4. **Verify snippets**
   ```javascript
   // Should include your snippets in results
   search_advanced({ 
     query: "button", 
     searchIn: ["code"] 
   })
   ```

## 9. Priority Components

Focus on these high-usage components first:

1. **Core Components**
   - Button
   - TextField
   - Label
   - Div/Span
   - Paragraph

2. **Layout Components**
   - FlexLayout
   - ColumnsLayout
   - AppLayout

3. **Form Components**
   - Form
   - Checkbox
   - RadioButton
   - ComboBox
   - DateField

4. **Feedback Components**
   - Dialog
   - Toast
   - Loading
   - ProgressBar

5. **Navigation**
   - Router
   - Link
   - Menu

## Example: Complete Button Curation

Here's what a fully curated Button component looks like:

### File Structure
```
├── src/main/java/com/webforj/samples/views/button/
│   ├── ButtonView.java
│   ├── ButtonThemesView.java
│   ├── ButtonEventsView.java
│   ├── ButtonIconsView.java
│   └── ButtonStatesView.java
├── src/main/resources/
│   ├── css/button/
│   │   ├── button.css
│   │   └── buttonThemes.css
│   └── data/
│       └── button-snippets.json
├── docs/docs/components/
│   ├── button.md
│   └── _metadata/
│       └── button.json
└── mcp-server/training-data/button/
    ├── basic-usage.java
    ├── form-integration.java
    ├── async-operations.java
    └── responsive-layout.java
```

This structure provides the MCP server with:
- Live demos it can reference
- Complete source code to analyze
- Structured documentation to parse
- Code snippets for quick examples
- Training data for pattern recognition

With this level of curation, the MCP server can provide comprehensive assistance for webforJ development.