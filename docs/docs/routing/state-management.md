---
sidebar_position: 7
title: State Management
---

Creating seamless, dynamic user experiences often requires that the state of your web app be reflected in the URL and retained across browser navigation events. You can achieve this without reloading the page by leveraging URL parameter updates and browser history state management. This ensures that users can share, bookmark, or return to specific views with the app fully aware of their prior interactions.

## Updating the URL

When the state of a web page changes, like filtering a product list or navigating through different views, you often need the URL to reflect those changes. You can use the `replaceState` or `pushState` methods provided by the `BrowserHistory` class to manipulate the URL without reloading the page:

- **`pushState`**: Adds a new entry to the browser’s history stack without reloading the page. This is useful for navigating between different views or dynamic content.
- **`replaceState`**: Updates the current entry in the browser’s history without adding a new entry. This is ideal for updating state within the same view.

### Example: Updating the URL with query parameters

In this example, when the "Update URL" button is clicked, the UI is updated to show the selected category and sorting, and the URL is updated with new query parameters for `category` and `sort`:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> {
  Paragraph paragraph = new Paragraph();
  Random random = new Random();

  public ProductView() {
    Button update = new Button("Update URL", ButtonTheme.PRIMARY);
    update.onClick(ev -> {
      filter("electronics", String.valueOf(random.nextInt(3) - 1));
    });

    Div div = getBoundComponent();
    div.add(update);
    div.add(paragraph);
  }

  public void filter(String category, String sort) {
    // update the UI
    updateUI(category, sort);

    // update the URL
    updateUrl(category, sort);
  }

  private void updateUI(String category, String sort) {
    paragraph.setText("Viewing category: " + category + " and sorting by: " + sort);
  }

  private void updateUrl(String category, String sort) {
    ParametersBag queryParameters = new ParametersBag();
    queryParameters.put("category", category);
    queryParameters.put("sort", sort);

    Location newLocation = new Location("/products?" + queryParameters.getQueryString());
    Router.getCurrent().getHistory()
        // Update the URL without reloading the page
        .replaceState(null, newLocation);
  }
}
```

### Explanation:

- **`filter` Method**: The method handles updating both the UI and the URL based on the selected `category` and `sort`.
- **`updateUrl` Method**: This method creates a new `ParametersBag` for query parameters, constructs a new URL, and then uses `replaceState` to update the browser's URL without reloading the page.
- **`replaceState`**: This method changes the URL to the new location while maintaining the current state, without causing a page reload.

## Saving and restoring state in browser history

In addition to updating the URL, it's possible to save arbitrary state objects in the browser's history. This means you can stash additional data related to the current view (for instance: form inputs, filters, etc.) without embedding them directly into the URL.

### Example: Saving selection state

In the following example, a `ProfileView` consists of several tabs (Profile, Orders and Settings). When the user switches between tabs, the selected tab’s state is saved in the browser’s history using `replaceState`. This allows the app to remember the last active tab if the user navigates back to this view or refreshes the page.

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {
  TabbedPane sections = new TabbedPane();
  int currentSection = 0;

  public ProfileView() {
    sections.addTab("Profile");
    sections.addTab("Orders");
    sections.addTab("Settings");

    sections.onSelect(ev -> {
      currentSection = ev.getTabIndex();
       // Save the state using replaceState
      updateState(currentSection);
    });

    getBoundComponent().add(sections);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    // Try to retrieve the last saved section from the browser history state
    Optional<Integer> lastSavedSection = event.getState(Integer.class);

    // If a section was saved, restore the tab selection
    lastSavedSection.ifPresent(section -> sections.select(section));
  }

  private void updateState(int section) {
    Router router = Router.getCurrent();
    Location currentLocation = router.getHistory().getLocation().get();

    // Update the current state with the selected section
    Router.getCurrent().getHistory()
        .replaceState(section, currentLocation);
  }
}
```

### Explanation:

1. **TabbedPane Component**: The view consists of a `TabbedPane` component, which has three tabs: Profile, Orders, and Settings.
2. **State Saving on Tab Change**: Each time a tab is selected, the current section index is saved in the browser’s history using the `replaceState` method.
3. **Restoring State on Navigation**: When the user navigates back to the `ProfileView`, the app retrieves the saved section from the history using `event.getState()` and restores the correct tab selection.
