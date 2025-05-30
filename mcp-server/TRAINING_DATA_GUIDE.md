# MCP Training Data Guide

This guide explains how training data is ingested and used by the webforJ MCP server to improve AI code generation.

## Overview

The MCP server uses training data in two ways:

1. **Real-time Retrieval** - Training data is indexed and made available during conversations
2. **Pattern Detection** - The server can check code for antipatterns and suggest corrections

## How Training Data is Ingested

### 1. Directory Scanning

When the MCP server starts, the `TrainingDataScanner` scans the `training-data/` directory:

```
mcp-server/
└── training-data/
    ├── antipatterns/       # Incorrect patterns to avoid
    │   ├── lifecycle/
    │   │   ├── incorrect/  # Examples of wrong patterns
    │   │   └── correct/    # Proper webforJ implementations
    │   ├── events/
    │   ├── state/
    │   └── ...
    └── examples/          # Good code examples
```

### 2. Index Building

The scanner:
- Reads all `.java` files in the training directories
- Extracts metadata (explanations, frameworks, tags)
- Pairs incorrect patterns with their correct equivalents
- Stores everything in the MCP index

### 3. Availability via Tools

Once indexed, the training data is available through MCP tools:

#### `check_antipatterns`
Checks provided code for known antipatterns:
```json
{
  "code": "self.onAttach(e -> { ... })"
}
```

Returns detected issues and corrections.

#### `get_antipattern`
Retrieves antipatterns by category or framework:
```json
{
  "category": "lifecycle"
}
```

#### `get_correct_pattern`
Gets the correct webforJ pattern for a concept:
```json
{
  "concept": "lifecycle management"
}
```

### 4. Real-time Usage

When you're chatting with Claude:
1. The MCP server is running in the background
2. Claude can query the training data using the tools
3. If Claude generates incorrect code, it can check against antipatterns
4. Claude receives the correct patterns and explanations

## Adding New Training Data

### Step 1: Identify the Pattern

When you notice AI making a mistake:
```java
// AI generates this (WRONG):
button.addEventListener("click", e -> {
    handleClick();
});
```

### Step 2: Create Training Files

Create paired files showing incorrect and correct patterns:

**antipatterns/events/incorrect/generic-event-listener.java:**
```java
// INCORRECT - DO NOT USE
// AI often uses generic addEventListener from DOM API
button.addEventListener("click", e -> {
    handleClick();
});
```

**antipatterns/events/correct/specific-event-methods.java:**
```java
// CORRECT - Use specific event methods
button.onClick(e -> {
    handleClick();
});
```

### Step 3: Rebuild Index

After adding new training data:
```bash
cd mcp-server
npm run build
# Restart Claude Desktop to reload
```

## How AI Uses This Data

### During Code Generation

1. **Pattern Matching**: AI can check if a pattern exists in antipatterns
2. **Framework Translation**: Maps React/Vue patterns to webforJ equivalents
3. **Validation**: Verifies generated code against known issues

### Example Flow

1. User asks: "Add a lifecycle hook when component mounts"
2. AI might initially think: `onMount()` or `componentDidMount()`
3. MCP provides correction: Use `DidEnterObserver` interface
4. AI generates correct code:
   ```java
   public class MyView extends App implements DidEnterObserver {
       @Override
       public void onDidEnter(DidEnterEvent event) {
           // initialization code
       }
   }
   ```

## Training Data Categories

### Lifecycle Management
- Component initialization
- Cleanup and disposal
- View activation/deactivation

### Event Handling
- Click, focus, blur events
- Custom event dispatching
- Event listener patterns

### State Management
- Component state
- Shared state
- State updates and UI sync

### Routing
- Navigation patterns
- Route parameters
- Guards and redirects

### Styling
- CSS application
- Theme usage
- Dynamic styling

### Async Operations
- Data fetching
- Background tasks
- UI updates from async code

## Best Practices

1. **Be Specific**: Include exact error patterns AI generates
2. **Provide Context**: Add comments explaining why it's wrong
3. **Show Alternatives**: Include multiple correct approaches when applicable
4. **Test Examples**: Ensure correct examples actually compile and work
5. **Document Frameworks**: Note which framework the AI is confusing (React, Vue, etc.)

## Monitoring Effectiveness

Track improvements by:
1. Checking if AI stops making the same mistakes
2. Reviewing MCP observation logs
3. Testing with specific prompts that previously failed

## Future Enhancements

Potential improvements to training data system:
- Automated testing of patterns
- Metrics on pattern detection frequency
- Integration with AI fine-tuning pipelines
- Community-contributed patterns