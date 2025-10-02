---
title: "Building a Todo App with MVC Pattern in webforJ"
description: "Learning webforJ's styling system from a developer's perspective: using `@StyleSheet` and `@InlineStyleSheet` annotations, working with DWC design tokens, handling responsive layouts, and building complex demos that scale from simple components to full dashboards."
slug: webforj-mvc
date: 2025-10-02
authors: Matthew Hawkins
tags: [webforJ, MVC, Spring, Spring Boot, Front End, Back End]
image: "https://cdn.webforj.com/webforj-documentation/blogs/buildingbettercss/cover.png"
hide_table_of_contents: true
---

# Building a Todo App with MVC Pattern in webforJ

Remember learning about Model-View-Controller (MVC) in university? For most, at least from what I hear when speaking to others, they had to actually create an app following this design paradigm, which greatly helps any future use of this pattern in the "real world." 

For me, that class happened right in the middle of COVID. Our instructor was a nice enough guy, but between the Zoom fatigue and lack of experience trying to teach people (I'm fairly sure it was his first or second term teaching), not only did we not end up actually building anything, but we spent time doing a theoretical exploration of the various design patterns out there, with MVC only receiving a few days of review. 

All this today that when I started working with webforJ, I saw it as the perfect opportunity to finally get hands-on with MVC – not just to understand the pattern properly this time, but also to learn how webforJ fits into this paradigm. Building a stereotypical todo app seemed like the ideal way to explore both.

## The Mission: Clean Architecture, Real Application

The goal was to build something simple, but that could clearly demonstrate the MVC design pattern. As many seasoned developers know, a simple "Todo List" application is a nearly ubiquitously accepted method to show how to do this in various technologies, and webforJ proved to be no exception. The goal was to implement a proper MVC (Model-View-Controller) pattern with Spring Boot integration, all while keeping the code clean and maintainable.

## What is MVC Anyway?

Before diving into code, let's demystify MVC in case any of you reading this also took your design patterns class during COVID. It's basically organizing your code into three distinct layers:

- **Model**: Your data and business logic (the brain)
- **View**: What users see and interact with (the face)
- **Controller**: The middleman coordinating between them (the nervous system)

There are all sorts of metaphors and analogies relating real-life paradigms to MVC - I'll spare you having to read through more of them here. 

Of course, working with webforJ meant that the view in particular would be of interest - would it not only be straightforward to create a modern, responsive UI, but would working with the tools the framework provides also make wiring in the other two pieces quick and painless? 

I'll walk through each of the layers, what I had to do to get them implemented, and leave you with the answer to this question.

## The Backend

Let's start with our Model layer. In webforJ with Spring Boot, this means **entities**, **repositories**, and **services**:

```java
@Entity
@Table(name = "todos")
public class Todo {
    @Id
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean completed;

    public Todo(String title) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.completed = false;
    }

    public void toggle() {
        this.completed = !this.completed;
    }
}
```

That's our todo entity – clean and simple. The repository? Even simpler:

```java
public interface TodoRepository extends JpaRepository<Todo, String> {
    // Spring Data JPA handles everything!
}
```

The real orchestration happens in the service layer, which also contains some input logic:

```java
@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> list() {
        return todoRepository.findAll();
    }

    public Todo add(String title) {
        if (title == null || title.trim().isEmpty()) {
            return null;
        }
        Todo todo = new Todo(title.trim());
        return todoRepository.save(todo);
    }

    public Todo toggle(String id) {
        Optional<Todo> todoOpt = todoRepository.findById(id);
        if (todoOpt.isPresent()) {
            Todo todo = todoOpt.get();
            todo.toggle();
            return todoRepository.save(todo);
        }
        return null;
    }

    public void delete(String id) {
        todoRepository.deleteById(id);
    }
}
```

So we've got our data layer all set up with Spring Boot doing its thing. But data sitting in a database doesn't help anyone – we need to show it to users and let them interact with it. This is where I got to see how to use webforJ optimally to create a modern UI.

## The Frontend

Here's where things got interesting. In my webforJ program, my front end was comprised entirely of components, which helped me keep things simple and effective. 

I extended `Composite<T>`, and started building UIs that just made sense. Both my view, and the components within that view, were built from this foundational building block. No wrestling with templating languages or complex state management libraries. 

The first class is the `TodoView`, which lives in the `views` directory, and is therefore automatically scanned for routing - in this case, as the home route. 

```java
@Route("/")
public class TodoView extends Composite<Div> {
    /**
     * Constructs a new TodoView with the specified controller.
     * Following proper MVC pattern with controller injection.
     */
    public TodoView(TodoController todoController) {
        // Create and add the TodoList component with the injected controller
        TodoList todoList = new TodoList(todoController);
        getBoundComponent().add(todoList);
    }
}
```

Notice how `TodoView` is basically just a container? That's intentional. It gets picked up by webforJ's routing (thanks to that `@Route` annotation) and serves as our entry point. The real magic happens in `TodoList`, which contains the various components and their assigned events:

```java
public class TodoList extends Composite<Div> {
    private final TodoController todoController;
    private TextField text = new TextField();
    private FlexLayout todoItemsContainer;
    private TodoFooter todoFooter;

    public TodoList(TodoController todoController) {
        this.todoController = todoController;

        text.setPlaceholder("Add Todo item. Press Enter to save.");

        // Setup event handlers
        text.onKeypress(e -> {
            if (e.getKeyCode().equals(KeypressEvent.Key.ENTER) && !text.getText().isBlank()) {
                Todo todo = todoController.addNewTodo(text.getText());
                if (todo != null) {
                    text.setText("");
                    refreshTodoDisplay();
                }
            }
        });

        // Load existing todos
        refreshTodoDisplay();
    }

    private void refreshTodoDisplay() {
        todoItemsContainer.removeAll();
        List<Todo> todos = todoController.getFilteredTodos(currentFilter);
        for (Todo todo : todos) {
            TodoItem item = createTodoItem(todo);
            todoItemsContainer.add(item);
        }
    }
}
```

The nice thing here is how easy handling these events. That `onKeypress` method works exactly as you'd expect. No convoluted event bubbling or synthetic events to worry about.

And then each todo item becomes its own little self-contained world:

```java
public class TodoItem extends Composite<FlexLayout> {
    private RadioButton radioButton = RadioButton.Switch();
    private Div text = new Div();
    private Div deleteButton = new Div();

    public TodoItem(Todo todo, Consumer<Todo> onToggle, Consumer<Todo> onDelete) {
        this.text.setText(todo.getTitle());

        radioButton.setChecked(todo.isCompleted());
        if (todo.isCompleted()) {
            text.setStyle("text-decoration", "line-through");
        }

        deleteButton.setText("✕");
        deleteButton.addClassName("todo-delete-btn");

        getBoundComponent()
            .add(radioButton, text, deleteButton);

        radioButton.onToggle(e -> {
            if (e.isToggled()) {
                text.setStyle("text-decoration", "line-through");
            } else {
                text.setStyle("text-decoration", "unset");
            }
            if (onToggle != null) {
                onToggle.accept(todo);
            }
        });
    }
}
```

The beauty of making `TodoItem` its own component is that it manages its own state and appearance. When you toggle that radio button, the component handles updating its own strikethrough styling. It's component composition at its finest – and those callbacks to the parent? That's just good old-fashioned function passing, no magic required.

## The Controller

Tying these two pieces of together, the `TodoController` acts as the coordinator between the View and Model layers, managing business logic and state:

```java
@Component
public class TodoController {
    @Autowired
    private TodoService todoService;

    public List<Todo> getFilteredTodos(FilterType filterType) {
        List<Todo> allTodos = todoService.list();
        switch (filterType) {
            case ACTIVE:
                return allTodos.stream()
                    .filter(todo -> !todo.isCompleted())
                    .collect(Collectors.toList());
            case COMPLETED:
                return allTodos.stream()
                    .filter(Todo::isCompleted)
                    .collect(Collectors.toList());
            default:
                return allTodos;
        }
    }

    public Todo addNewTodo(String title) {
        return todoService.add(title);
    }

    public Todo toggleTodo(String id) {
        return todoService.toggle(id);
    }
}
```

Notice how the controller isn't trying to do everything? It's not a REST endpoint (though we'll be exploring that in the near future!), and it's not managing database connections. It's just coordinating – taking requests from the view, applying business logic like filtering, and delegating the heavy lifting to the service layer.

## How this all works

As I was building this, I never really ended up getting terribly stuck, or even moderately stuck. One would hope that a todo app wouldn't present such issues, but it became apparently that webforJ has been created in a way that naturally facilitates MVC without forcing it down your throat. The framework gives you these building blocks that naturally guide you toward good architecture.

Take the `Composite` pattern, for instance. When you extend `Composite<Div>`, you're not just creating a component – you're creating something that knows how to manage its lifecycle, handle events, and compose with other components. It's like Lego blocks for UIs, but with type safety and IDE support.

The Spring Boot integration continues to make app building pleasant. Your `@Service` classes get autowired into your views, your `@Route` annotations get picked up automatically, and there's no weird workarounds or shortcuts that need to be taken.

## Watching It All Come Together

Here's what happens when you add a new todo:

1. You type "Buy coffee" and hit Enter
2. `TodoList` captures the input event and calls `todoController.addNewTodo()`
3. `TodoController` delegates to `TodoService`
4. `TodoService` validates and creates the `Todo` entity
5. `Repository` saves it to the database
6. `TodoList` refreshes the display with the new todo
7. The UI updates instantly with the new `TodoItem` component

To recap here, each piece has a clear job. The controller doesn't try to be a service, the service doesn't try to be a repository, and the view components don't try to be controllers. It's MVC in action, and it feels natural.

## Small Sample Demonstrates Large Promise

Building this small sample gave me a little practical experience I never got while studying, and exemplified why this pattern is so widely used. It's not about following some rigid pattern from a textbook – it's about organizing your code in a way that makes sense both now and six months from now when you need to add features.

What webforJ brings to the table is a way to keep all of your code in Java, including the presentation layer. It gives you a clean way to build and route UIs without the complexity of modern JavaScript frameworks, and ihe Spring Boot integration means you get all the backend power you need without configuration hell.

The event handling deserves a special mention too. Being able to write `text.onKeypress(e -> {...})` and have it just work is refreshing.

## Looking Back (and Forward)

This project started as a simple way to finally understand MVC properly after my COVID-interrupted education. What I discovered was that webforJ doesn't just support MVC – it makes it feel like the natural way to build applications. The framework gives you the tools (components, routing, dependency injection) and then gets out of your way.

The todo app might be simple, but it's architecturally sound. The separation between the Model (entities, repositories, services), View (components extending Composite), and Controller (coordinating logic) isn't forced – it emerges naturally from using webforJ's features.

And that's perhaps the best lesson from this whole experience: good frameworks don't force patterns on you; they make the right patterns feel obvious.

---

*Want to see the full code? Check out the [webforJ Todo App repository](https://github.com/webforj/built-with-webforj/tree/main/webforj-todo) and try it yourself.*