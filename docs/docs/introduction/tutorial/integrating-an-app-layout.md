---
title: Integrating an App Layout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
sidebar_class_name: new-content
---

In this step, you'll pull all the parts of your app into a cohesive app layout. By the end of this step, your app’s structure will closely resemble the [SideMenu archetype](https://docs.webforj.com/docs/building-ui/archetypes/sidemenu), and you’ll have a better understanding of how the following components and concepts work:

- [`FlexLayout`](/docs/components/flex-layout)
- [Route Outlets](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## Running the app {#running-the-app}

As you develop your app, you can use [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) as a comparison. To see the app in action:

1. Navigate to the top-level directory containing the `pom.xml` file, this is `6-integrating-an-app-layout` if you're following along with the version on GitHub.

2. Use the following Maven command to run the Spring Boot app locally:
    ```bash
    mvn
    ```

Running the app automatically opens a new browser at `http://localhost:8080`.

## Creating a reusable component {#creating-a-reusable-component}

In a previous step, [Routing and Composites](/docs/introduction/tutorial/routing-and-composites), you created two composite components that contained the content of the customer table and the customer form.
As part of this step, you’ll create a smaller, reusable composite component to display the app’s name inside the side menu and an about page. If you decide to change the app’s name in the future, you’d only need to update it in this component.

In `src/main/java/com/webforj/tutorial/components`, create a class called `AppTitle`. The bound component for `AppTitle` will be a `FlexLayout`, a container component that’s used throughout this step to show you how to make more complex layouts.
For this `FlexLayout`, you’ll arrange the items’ direction and the spacing between items. That’s done by using the `setDirection()` and `setSpacing()` methods respectively.

```java title='AppTitle.java'
// Make the bound component a FlexLayout
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  
  public AppTitle() {

    // Arrange the items vertically
    self.setDirection(FlexDirection.COLUMN);

    // Set the gap between items
    self.setSpacing("0px");
  }
}
```

Then use standard HTML elements to create the title and subtitle. Setting the bottom margin of a header element to `0px` brings the elements closer, and you can style the subtitle using [DWC CSS variables](/docs/styling/css-variables).

```java title='AppTitle.java' {3-4,7-9,13}
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Customer Manager");
  private Paragraph subTitle = new Paragraph("A Simple Record System");

  public AppTitle() {
    title.setStyle("margin-bottom", "0px");
    subTitle.setStyle("color", "var(--dwc-color-gray-50)");
    subTitle.setStyle("font-size", "var(--dwc-font-size-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0px")
        .add(title, subTitle);
  }
}
```

### Optional rendering {#optional-rendering}

Even though `AppTitle` is simple, adding a boolean argument to the constructor method allows you to control when to render certain parts of the component, like the subtitle.

```java title='AppTitle.java'
// Add a boolean argument
public AppTitle(boolean showSubTitle) {

  self.setDirection(FlexDirection.COLUMN)
      .setSpacing("0px")

      // Add the title by default
      .add(title);
  
  // Optionally display the subtitle
  if (showSubTitle) {
    self.add(subTitle);
  }
}
```

### Completed `AppTitle` {#completed-app-title}

All together, the reusable component should look like the following:

```java title='AppTitle.java'
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Customer Manager");
  private Paragraph subTitle = new Paragraph("A Simple Record System");

  public AppTitle(boolean showSubTitle) {
    title.setStyle("margin-bottom", "0");
    subTitle.setStyle("color", "var(--dwc-color-gray-50)");
    subTitle.setStyle("font-size", "var(--dwc-font-size-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0px")
        .add(title);
        
    if (showSubTitle) {
      self.add(subTitle);
    }
  }
}
```

## Creating an about page {#creating-an-about-page}

The first place to add the newly created `AppTitle` component will be an about page. This page includes an image and the `AppTitle` component, centered on the page by using another `FlexLayout`.

### Centering content using a `FlexLayout` {#centering-content-using-a-flexlayout}

The goal is center the content of the about page using the `FlexLayout`. The `FlexLayout` component follows the [CSS flexbox layout model](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Methods for the `FlexLayout`, like the ones used earlier to orient the items into a column, are different ways to arrange the items.

The methods for arranging items in a `FlexLayout` use a relative directional system. Instead of thinking about the horizontal and vertical axes, it’s better to think about the axis parallel to the items as the main axis, and the axis perpendicular to the items as the cross axis.

Setting both the `FlexJustifyContent` and the `FlexAlignment` properties to `CENTER` will center the items along both the main and cross axes in the `FlexLayout`, and making the `FlexLayout` take up the entirety of its parent container makes it centered on the page. 

```java
private final FlexLayout layout = new FlexLayout();

// Fill the entire space of the parent element
layout.setSize("100%", "100%");

// Make the main axis vertical
layout.setDirection(FlexDirection.COLUMN);

// Center the items along the cross axis
layout.setAlignment(FlexAlignment.CENTER);

// Center the items along the main axis
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

To help visualize how the different methods work, take a look at the blog post [FlexWrap your mind around webforJ's FlexLayout](/blog/2025/08/26/flexlayout-container).

### Adding resources {#adding-resources}

One of the items that’ll go inside the centered `FlexLayout` is an image. For this tutorial, you can view and download the [about page's image](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg) on GitHub.
Once downloaded, add it to your project’s static folder in `src/main/resources/static/images` and name it `Files.svg`.

Putting this image in the static folder allows you to 
reference it using the Webserver protocol, like you did when referencing the CSS file in the first step, [Creating a Basic App](/docs/introduction/tutorial/creating-a-basic-app). Then, you can use it inside your app as an HTML element, like so:

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### Creating `AboutView` {#creating-about-view}

Like the two existing app pages, the about page will be a routable view. In `src/main/java/com/webforj/tutorial/views`, add a class named `AboutView`. Use a `FlexLayout` for the bound component, as you did for `AppTitle`.

Since you’ve named the class `AboutView`, there’s no need to give a custom value for the URL mapping; this page renders at `http://localhost:8080/about` by default.

Here’s what it looks like when you use the concepts from the previous steps with the newly created components to create a new view with centered content: 

```java title='AboutView.java'
@Route()
@FrameTitle("About")
public class AboutView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private Img fileImg = new Img("ws://images/Files.svg");

  public AboutView() {
    fileImg.setWidth(250);
    self.setSize("100%", "100%")
        .setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .add(fileImg, new AppTitle(false));
  }
}
```

## Creating the `Layout` route {#creating-the-layout-route}

It’s briefly mentioned in the [Routing and Composites](/docs/introduction/tutorial/routing-and-composites) step, but there are two [route types](/docs/routing/route-hierarchy/route-types). `MainView`, `FormView`, and `AboutView` are all `View` routes, while the route type you’ll use to create the side menu of the app is a `Layout` route.

Layout routes wrap child views and allow certain UI parts to persist across views, like a side menu. In `src/main/java/com/webforj/tutorial/layouts`, create a class called `MainLayout`.

### Route outlets {#route-outlets}

Like the view routes, `MainLayout` needs a `@Route` annotation. However, because it has `Layout` as a suffix and layout routes don’t contribute to the URL, this annotation doesn’t need any arguments.

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {
}
```

The app knows which views to render inside `MainLayout` by declaring the layout class as the [route outlet](/docs/routing/route-hierarchy/route-outlets) in each view. The previous steps only have a `value` property set in the `@Route` annotations, so now you’ll need to explicitly state what the `value` and `outlet` properties are for the view classes.

<!-- vale Google.Quotes = NO -->
<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java
  @Route(value = "/", outlet = MainLayout.class)
  ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```
  </TabItem>
  <TabItem value="AboutView" label="AboutView">
  ```java
  @Route(outlet = MainLayout.class)
  ```
  </TabItem>
</Tabs>
<!-- vale Google.Quotes = YES -->

:::note Final touches
This is the last modification required for `FormView` and `AboutView` in this step, so remember to update the `@Route` annotation for those views before you run your app.
:::

## Using the `AppLayout` component {#using-the-app-layout-component}

Now that your app renders the views inside `MainLayout`, you can choose where those components render. Choosing the `AppLayout` as the bound component for `MainLayout` allows you to store the views in a main content area by default, while also giving you different areas to add items for the header and side menu.

### Slots {#slots}

For many webforJ containers, using the `add()` methods adds UI components to the main content area. In the `AppLayout` component, there are multiple areas for adding UI components, each in a separate slot.
By marking `MainLayout` as a layout route and setting its bound component as a `AppLayout`, views automatically render in the main content slot.

In this step, you’ll use the `drawer-title` and `drawer` slots to create a side menu, and the `header` slot to display which page the user’s on and a toggle for the side menu.

### Making a side menu {#making-a-side-menu}

When there’s enough screen room on the device, the `AppLayout` component displays a drawer. This is where you’ll add the `AppTitle` again and items that’ll allow users to navigate the app.

By default, `AppLayout` doesn’t show a drawer header, but using the `setDrawerHeaderVisible()` method allows you to show items that are inside the `drawer-title` slot, which will be the `AppTitle` with its subtitle shown.

```java
private AppLayout appLayout = new AppLayout();

// Show the Drawer Header
appLayout.setDrawerHeaderVisible(true);

// Add the AppTitle to the Drawer Header with its subtitle
appLayout.addToDrawerTitle(new AppTitle(true));
```

The `drawer` slot should then contain the components that allow users to navigate in the app. Using the [`AppNav`](/docs/components/appnav) component makes it easy to create new navigation options. For each link, you just need to create an `AppNavItem`.
The `AppNavItem` components in this tutorial use three parameters:

- The label for the link
- The target view
- An optional [`Icon`](/docs/components/icon) component, using images from [Tabler](https://tabler.io/icons)

Grouping all the drawer settings in `MainLayout` looks like the following:

```java title="MainLayout"
@Route
public class MainLayout extends Composite<AppLayout> {
  private AppLayout self = getBoundComponent();
  private AppNav appNav = new AppNav();

  public MainLayout() {
    setDrawer();
  }

  private void setDrawer() {
    self.setDrawerHeaderVisible(true)
        .addToDrawerTitle(new AppTitle(true));

    appNav.addItem(new AppNavItem("Dashboard", MainView.class,
        TablerIcon.create("archive")));
    appNav.addItem(new AppNavItem("About", AboutView.class,
        TablerIcon.create("info-circle")));
    self.addToDrawer(appNav);
  }
```

### Making a header {#making-a-header}

The `header` slot should include two items: a toggle to show or hide the side menu and a way to display the frame title. Both of these items will be inside a [Toolbar](/docs/components/toolbar) component, another way to organize components.

You can include the toggle for the `AppLayout` drawer with the `AppDrawerToggle` component. This component is already styled with a commonly used icon for hidden menu options, and targets the drawer to open and close it. 

```java
// Create the container components
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// Add the Toolbar to the AppLayout header
appLayout.self.addToHeader(toolbar);

// Add the AppDrawerToggle to the toolbar
toolbar.addToStart(new AppDrawerToggle());
```

The header can also display the frame title by using the navigation event to retrieve details about the incoming component, while having an event listener to remove the registration to prevent memory leaks.

```java
// Create the H1 element and the navigation registration
private H1 title = new H1("");
private ListenerRegistration<NavigateEvent> navigateRegistration;

// Register the event when navigating
navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);

// Remove listeners before MainLayout is destroyed
@Override
protected void onDidDestroy() {
  if (navigateRegistration != null) {
    navigateRegistration.remove();
  }
}

// Retrieve the frame title from the incoming view class
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## Completed `MainLayout`

Here's `MainLayout` with created content for the drawer and the header inside an `AppLayout`:

<!-- vale off -->
<ExpandableCode title="MainLayout.java" language="java">
{`@Route
  public class MainLayout extends Composite<AppLayout> {
    private AppLayout self = getBoundComponent();
    private H1 title = new H1("");
    private ListenerRegistration<NavigateEvent> navigateRegistration;
    private Toolbar toolbar = new Toolbar();
    private AppNav appNav = new AppNav();

    public MainLayout() {
      setHeader();
      setDrawer();
      navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);
    }

    private void setHeader() {
      self.addToHeader(toolbar);

      toolbar.addToStart(new AppDrawerToggle());
      toolbar.addToTitle(title);
    }

    private void setDrawer() {
      self.setDrawerHeaderVisible(true)
          .addToDrawerTitle(new AppTitle(true));

      appNav.addItem(new AppNavItem("Dashboard", MainView.class,
          TablerIcon.create("archive")));
      appNav.addItem(new AppNavItem("About", AboutView.class,
          TablerIcon.create("info-circle")));
      self.addToDrawer(appNav);
    }

    @Override
    protected void onDidDestroy() {
      if (navigateRegistration != null) {
        navigateRegistration.remove();
      }
    }

    private void onNavigate(NavigateEvent ev) {
      Component component = ev.getContext().getComponent();
      if (component != null) {
        FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
        title.setText(frameTitle != null ? frameTitle.value() : "");
      }
    }

  }
`}
</ExpandableCode>
<!-- vale on -->

## Updating `FormView` {#updating-form-view}

As mentioned previously, the only change to `FormView` was to the `@Route` annotation.

  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```

## Updating `MainView` {#updating--view}

For `MainView`, you’ll change the bound component from a `Div` to a `FlexLayout`. This allows you to center the table, while also moving specific components inside the layout. Using the `setItemAlignment()` method lets you pick a component in the layout and move it, so you can keep the table centered while anchoring the add customer button to the top right of the layout.

```java
// Change the bound component to a FlexLayout
private FlexLayout self = getBoundComponent();

// Align the button to the end of the cross axis
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

Another improvement you can make here is the table's size. Using methods from the `Table` component, you can set it to always show all rows without scrolling, unless it’s larger than the FlexLayout it’s in.

```java 
double headerHeight = table.getHeaderHeight();
double rowHeight = table.getRowHeight();
double tableHeight = rowHeight * customerService.getTotalCustomersCount() + headerHeight;
table.setWidth("100%");
table.setHeight((float) tableHeight);
table.setMaxHeight("100%");
```

Putting these together and making another method to get the `FlexLayout` centered like the previous ones makes `MainView` with the highlighted changes:

<!-- vale off -->
<ExpandableCode title="MainLayout.java" language="java">
{`@Route(value = "/", outlet = MainLayout.class)
  @FrameTitle("Customer Table")
  // highlight-next-line
  public class MainView extends Composite<FlexLayout> {
    private final CustomerService customerService;
    // highlight-next-line
    private FlexLayout self = getBoundComponent();
    private Table<Customer> table = new Table<>();
    private Button addCustomer = new Button("Add Customer", ButtonTheme.PRIMARY,
        e -> Router.getCurrent().navigate(FormView.class));

    public MainView(CustomerService customerService) {
      this.customerService = customerService;
      addCustomer.setWidth(200);
      buildTable();
      // highlight-next-line
      setFlexLayout();
      // highlight-next-line
      self.add(addCustomer, table);
      // highlight-next-line
      self.setItemAlignment(FlexAlignment.END, addCustomer);
    }

    private void buildTable() {
      // highlight-next-line
      double headerHeight = table.getHeaderHeight();
      // highlight-next-line
      double rowHeight = table.getRowHeight();
      // highlight-next-line
      double tableHeight = rowHeight * customerService.getTotalCustomersCount() + headerHeight;
      // highlight-next-line
      table.setWidth("100%");
      // highlight-next-line
      table.setHeight((float) tableHeight);
      // highlight-next-line
      table.setMaxHeight("100%");
      table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
      table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
      table.addColumn("company", Customer::getCompany).setLabel("Company");
      table.addColumn("country", Customer::getCountry).setLabel("Country");
      table.setColumnsToAutoFit();
      table.setColumnsToResizable(false);
      table.getColumns().forEach(column -> column.setSortable(true));
      table.setRepository(customerService.getRepositoryAdapter());
      table.setKeyProvider(Customer::getId);
      table.addItemClickListener(this::editCustomer);
    }

    // highlight-next-line
    private void setFlexLayout() {
      // highlight-next-line
      self.setSize("100%", "100%")
          // highlight-next-line
          .setMargin("auto")
          // highlight-next-line
          .setMaxWidth(2000)
          // highlight-next-line
          .setDirection(FlexDirection.COLUMN)
          // highlight-next-line
          .setAlignment(FlexAlignment.CENTER);
          // highlight-next-line
    }

    private void editCustomer(TableItemClickEvent<Customer> e) {
      Router.getCurrent().navigate(FormView.class,
          ParametersBag.of("id=" + e.getItemKey()));
    }
  }
`}
</ExpandableCode>
<!-- vale on -->