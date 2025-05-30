# AI Antipatterns & Common Mistakes

This document tracks common mistakes AI assistants make when generating webforJ code, along with corrections and training examples to improve AI understanding.

## Purpose

AI models often apply patterns from other frameworks (React, Vue, JavaFX) to webforJ, leading to incorrect code generation. This document helps:
1. Identify recurring AI mistakes
2. Document correct webforJ patterns
3. Create training data to prevent these mistakes
4. Guide AI model improvements

## Antipattern Categories

### 🔄 Lifecycle Management

#### ❌ ANTIPATTERN: Event-based lifecycle hooks
**What AI generates:**
```java
public class MyView extends App {
    @Override
    public void run() {
        self.onAttach(e -> {
            // initialization code
        });
        
        self.onDetach(e -> {
            // cleanup code
        });
    }
}
```

**Why it's wrong:** webforJ uses interface-based lifecycle management, not event listeners

**✅ CORRECT PATTERN:**
```java
public class MyView extends App implements DidEnterObserver, DidLeaveObserver {
    @Override
    public void run() {
        // Component setup
    }
    
    @Override
    public void onDidEnter(DidEnterEvent event) {
        // Initialization code when view becomes active
    }
    
    @Override
    public void onDidLeave(DidLeaveEvent event) {
        // Cleanup code when view becomes inactive
    }
}
```

**Training notes:**
- webforJ lifecycle interfaces: `DidEnterObserver`, `DidLeaveObserver`, `HasEnterObserver`, `HasLeaveObserver`
- No `onAttach`, `onDetach`, `mounted`, `unmounted` methods
- Lifecycle methods are interface contracts, not event listeners

---

### 🎨 Component Creation

#### ❌ ANTIPATTERN: Constructor-based configuration
**What AI generates:**
```java
Button button = new Button({
    text: "Click me",
    theme: ButtonTheme.PRIMARY,
    enabled: false
});
```

**Why it's wrong:** webforJ doesn't use object literal configuration

**✅ CORRECT PATTERN:**
```java
Button button = new Button("Click me");
button.setTheme(ButtonTheme.PRIMARY);
button.setEnabled(false);

// Or use fluent API
Button button = new Button("Click me")
    .setTheme(ButtonTheme.PRIMARY)
    .setEnabled(false);
```

---

### 🎯 Event Handling

#### ❌ ANTIPATTERN: Generic addEventListener
**What AI generates:**
```java
button.addEventListener("click", e -> {
    // handle click
});
```

**Why it's wrong:** webforJ uses specific event methods

**✅ CORRECT PATTERN:**
```java
button.onClick(e -> {
    // handle click
});

// Or with method reference
button.onClick(this::handleButtonClick);
```

---

### 📦 State Management

#### ❌ ANTIPATTERN: React-style state hooks
**What AI generates:**
```java
private State<String> userName = useState("");

public void updateUser(String name) {
    userName.setState(name);
}
```

**Why it's wrong:** webforJ uses standard Java fields for state

**✅ CORRECT PATTERN:**
```java
private String userName = "";

public void updateUser(String name) {
    this.userName = name;
    // Update UI components directly
    userLabel.setText(name);
}
```

---

### 🔗 Routing

#### ❌ ANTIPATTERN: Imperative navigation
**What AI generates:**
```java
router.navigate("/users/123");
// or
this.navigateTo("/users/123");
```

**Why it's wrong:** webforJ uses annotation-based routing

**✅ CORRECT PATTERN:**
```java
@Route("users/:id")
public class UserView extends App implements ParameterizedRoute {
    @Override
    public void setParameter(BeforeEnterEvent event, @Param("id") String id) {
        // Handle route parameter
    }
}

// Navigation via UI components
Link userLink = new Link("View User", UserView.class);
userLink.setRouteParameter("id", "123");
```

---

### 🏗️ Layout Management

#### ❌ ANTIPATTERN: CSS-in-JS or inline styles
**What AI generates:**
```java
Div container = new Div();
container.setStyles({
    display: "flex",
    flexDirection: "column",
    gap: "10px"
});
```

**Why it's wrong:** webforJ doesn't support object-based style setting

**✅ CORRECT PATTERN:**
```java
// Use layout components
FlexLayout container = new FlexLayout();
container.setDirection(FlexDirection.COLUMN);
container.setGap("10px");

// Or use individual style methods
Div container = new Div();
container.setStyle("display", "flex");
container.setStyle("flex-direction", "column");
container.setStyle("gap", "10px");

// Or use CSS classes
container.addClassName("flex-container");
```

---

### 🔄 Async Operations

#### ❌ ANTIPATTERN: Promise/async-await style
**What AI generates:**
```java
async loadData() {
    const data = await fetchUsers();
    this.setState({ users: data });
}
```

**Why it's wrong:** Java doesn't have async/await syntax

**✅ CORRECT PATTERN:**
```java
private void loadData() {
    Loading loading = new Loading();
    add(loading);
    
    // Using CompletableFuture
    CompletableFuture.supplyAsync(() -> {
        return userService.fetchUsers();
    }).thenAccept(users -> {
        // Update UI on EDT
        App.consoleLog("Loaded " + users.size() + " users");
        remove(loading);
        displayUsers(users);
    }).exceptionally(error -> {
        remove(loading);
        showError("Failed to load users: " + error.getMessage());
        return null;
    });
}
```

---

### 🎨 Styling

#### ❌ ANTIPATTERN: Styled-components approach
**What AI generates:**
```java
StyledButton = styled(Button)`
    background: blue;
    color: white;
    &:hover {
        background: darkblue;
    }
`;
```

**Why it's wrong:** webforJ doesn't have styled-components

**✅ CORRECT PATTERN:**
```java
// Use theme variants
button.setTheme(ButtonTheme.PRIMARY);

// Or use CSS classes with separate CSS file
button.addClassName("primary-button");

// CSS file: /src/main/resources/css/buttons.css
/*
.primary-button {
    background: blue;
    color: white;
}
.primary-button:hover {
    background: darkblue;
}
*/
```

---

## Training Data Structure

### Directory Layout
```
mcp-server/training-data/antipatterns/
├── lifecycle/
│   ├── incorrect/
│   │   ├── event-based-lifecycle.java
│   │   └── react-hooks-lifecycle.java
│   └── correct/
│       ├── interface-based-lifecycle.java
│       └── lifecycle-with-observers.java
├── events/
│   ├── incorrect/
│   │   └── generic-event-listeners.java
│   └── correct/
│       └── specific-event-methods.java
└── README.md
```

### Example Training File

**incorrect/event-based-lifecycle.java:**
```java
// INCORRECT: Do not use this pattern
// AI often generates this based on other frameworks
public class IncorrectView extends App {
    @Override
    public void run() {
        // WRONG: No onAttach method exists
        this.onAttach(e -> {
            System.out.println("Component attached");
        });
        
        // WRONG: No addEventListener for lifecycle
        this.addEventListener("mount", e -> {
            loadData();
        });
    }
}
```

**correct/interface-based-lifecycle.java:**
```java
// CORRECT: This is the proper webforJ pattern
// Use interfaces for lifecycle management
public class CorrectView extends App implements DidEnterObserver {
    @Override
    public void run() {
        // Setup UI components
        add(new Label("Hello WebforJ"));
    }
    
    @Override
    public void onDidEnter(DidEnterEvent event) {
        // This runs when the view becomes active
        System.out.println("View entered");
        loadData();
    }
    
    private void loadData() {
        // Load data when view is active
    }
}
```

---

## Common Framework Confusion

### React → webforJ
| React Pattern | webforJ Equivalent |
|--------------|-------------------|
| `useState()` | Instance fields |
| `useEffect()` | `DidEnterObserver` interface |
| `props` | Constructor parameters or setters |
| `<Component />` | `new Component()` |
| `className` | `addClassName()` |

### Vue → webforJ
| Vue Pattern | webforJ Equivalent |
|------------|-------------------|
| `mounted()` | `onDidEnter()` |
| `data()` | Instance fields |
| `v-model` | Direct binding with listeners |
| `@click` | `onClick()` |

### JavaFX → webforJ
| JavaFX Pattern | webforJ Equivalent |
|---------------|-------------------|
| `Stage/Scene` | `App` class |
| `FXML` | Java code or HTML templates |
| `@FXML` | Direct field references |
| `initialize()` | `run()` method |

---

## How to Contribute

1. **Identify Pattern**: When AI generates incorrect code
2. **Document**: Add to appropriate section above
3. **Create Examples**: Add training files showing incorrect vs correct
4. **Test**: Verify the correct pattern works
5. **Train**: Include in next training dataset

## Priority Antipatterns to Address

1. **Lifecycle management** - Most common mistake
2. **Event handling** - Often uses generic patterns
3. **State management** - Assumes React-like state
4. **Async patterns** - Uses JS-style promises
5. **Routing** - Assumes imperative navigation

## Success Metrics

Track improvement over time:
- Reduction in lifecycle pattern errors
- Correct event handler usage
- Proper component initialization
- Accurate routing implementation