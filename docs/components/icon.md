---
title: Icon
---


<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<JavadocLink type="foundation" location="com/webforj/component/icons/Icon" top='true'/>

The weborJ `Icon` component allows you to effortlessly include icons in your user interface.
Icons are a fundamental part of enhancing the design of the user interface, making it faster for users to scan the screen for actionable items.
Using icons in your app creates visual cues for navigation and actions, which can reduce the amount of text needed and simplify the user interface. You can choose from three existing icon pools and webforJ also gives you the option to create new ones from scratch.

:::tip Did you know?

Some components, like `PasswordField` and `TimeField`, have built-in icons to help convey meaning for end users.

:::

## Basics

Every `Icon` is designed as a Scalable Vector Graphics (SVG) image, which means it can easily scale to any size without losing clarity or quality.
Additionally, `Icon` components are loaded on demand from a content delivery network (CDN), which helps reduce latency and improve overall performance.

When creating an `Icon`, you'll need to identify a specific pool and the name of the icon itself.
Some icons also offer the choice between an outlined or a filled version via [variations](#variations).

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/iconmenu?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconMenuView.java'
height='200px'
/>

### Pools

An icon pool is a collection of commonly used icons that enables easy access and reuse. By using icons from an icon pool, you can ensure that the icons in your app are recognizable and share a consistent style.
Using webforJ allows you to choose from three pools, or implement a custom pool.
Each pool has an extensive collection of open source icons that are free to use.
Using webforJ gives you the flexibility to choose from three pools and use them as unique classes, without the hassle of downloading any of the icons directly.


| Icon Pool                                         | webforJ Class |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` and `DwcIcon`.<br/>`DwcIcon` is a subset of the Tabler icons.|    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

If you're interested in creating your own icon pool, see [Creating custom pools](#creating-custom-pools).

:::

Once you have selected the pool or pools to include in your app, the next step is to specify the name of the icon you want to use.

### Names

To include an icon in your app, all you need is the icon pool and the icon name. Browse the icon pool website for the icon you wish to use, and use the icon name as the parameter of the `create()` method.
Additionally, you have the option to create the icons through enums for the `FeatherIcon` and `DwcIcon` classes, allowing them to appear in code completion.

```java
// Create an icon from a String name
Icon image = TablerIcon.create("image");
// Create an icon from an enum
Icon image = FeatherIcon.IMAGE.create();
```

### Variations

You can personalize icons even more by utilizing variations.
Certain icons allow you to choose between an outlined or a filled version, allowing you to put emphasis on a specific icon based on your preference. `FontAwesomeIcon` and `Tabler` icons offer variations.

#### `FontAwesomeIcon` variations

1. `REGULAR`: The outlined variate of icons. This is the default.
2. `SOLID`: The filled variate of icons.
3. `BRAND`: The variate for when you're using the icons of brands.

#### `TablerIcon` variations

1. `OUTLINE`: The outlined variate of icons. This is the default.
2. `FILLED`: The filled variate of icons.

```java
// A filled variation of an icon from Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

The following demo illustrates how to use icons from different pools, apply variations, and seamlessly integrate them into components.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/iconactionbuttons?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconActionButtonsView.java'
height='200px'
/>

## Adding icons to components

Integrate icons into your components using slots. Slots provide flexible options for improving the functionality of a component. It's beneficial to add an `Icon` to a component to further clarify intended meaning to users.
Components that implement the `HasPrefixAndSuffix` interface can include an `Icon` or other valid components. The added components can be placed in the `prefix` and `suffix` slots and can enhance both overall design and user experience.


Using `prefix` and `suffix` slots, you can determine if you want the icon before or after the text using the `setPrefixComponent()` and `setSuffixComponent()` methods.
Deciding whether to place an icon before or after the text on a component largely depends on the purpose and design context.

### Icons before component text

    Placing icons before a component allows users to quickly understand the component's purpose, especially for more universally understood icons like the save icon.
    Seeing the icon first helps users process the information in a logical flow.
    If the button's primary function is an action, putting the icon first signals the action more clearly, especially when users are scanning quickly.

   **Examples**
   - 💾 Save
   - ✏️ Edit
   - 🗑️ Delete

### Icons after component text

   Icons after components is effective place for buttons that provide contextual cues or extra information.
   Placing icons after text is most suitable for actions that provide extra information or options, as well as for components that guide directional flow.

   **Examples**
   - Next ➡️
   - Options ☰
   - Download ⬇️

Ultimately, consistency is key. Once you choose a style, maintain it across your site for a cohesive and user-friendly design.

## Creating custom pools

Beyond utilizing existing icon collections, you have the option to create a custom pool that can be used for custom logos or avatars.
A custom pool of icons can be stored in a centralized directory or in the resources folder (context), simplifying the icon management process.
Having a custom pool makes app creation more consistent and reduces maintenance across different components and modules.

Custom pools can be created from a folder containing SVG images and by using the `IconPoolBuilder` class. From there, you can choose the name of your custom pool and use that with the SVG file names to create custom icon components.

:::tip
Make sure to design icons with equal width and height, as `Icon` components are designed to occupy a square space.
:::

## Icon buttons
An `Icon` component is nonselectable, but for actions that are best represented with just an icon, such as notifications or alerts, you can use the `IconButton`.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> showMessageDialog("You have a new message!", "Ding Dong!"));
```

## Best practices

- **Accessibility:** Use a tool tip or a label on icons to make your app accessible to visually impaired users relying on screen readers.
- **Avoid ambiguity:** Avoid using icons if the meaning isn’t clear or widely understood. If users have to guess what the icon represents, it defeats the purpose.
- **Use icons sparingly:** Too many icons can overwhelm users, so only use icons when they add clarity or reduce complexity.

## Styling
An Icon inherits the theme of its direct parent component, but you can override this by applying a theme to an `Icon` directly.

### Themes
Icon components come with 7 discrete themes built in for quick styling without the use of CSS. These themes are pre-defined styles that can be applied to icons to change their appearance and visual presentation. They offer a quick and consistent way to customize the look of icons throughout an application.

While there are many use cases for each of the various themes, some examples uses are:

- `DANGER`: Best for actions with severe consequences, such as clearing filled-out information or permanently deleting an account/data.
- `DEFAULT`: Appropriate for actions throughout an application that don't require special attention and are generic, such as toggling a setting.
- `PRIMARY`: Appropriate as a main "call-to-action" on a page, such as signing up, saving changes, or continuing to another page.
- `SUCCESS`: Excellent for visualizing successful completion of an element in an application, such as the submission of a form or completion of a sign-up process. The success theme can by programmatically applied once a successful action has been completed.
- `WARNING`: Useful to indicate that a user is about to perform a potentially risky action, such as navigating away from a page with unsaved changes. These actions are often less impactful than those that would use the Danger theme.
- `GRAY`: Good for subtle actions, such as minor settings or actions that are more supplementary to a page, and not part of the main functionality.
- `INFO`: Good for providing additional clarifying information to a user.


### Shadow parts

These are the various parts of the shadow DOM for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Icon} table="parts"/>

### CSS properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Icon} table="properties"/>

### Reflected attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Icon} table="reflects"/>