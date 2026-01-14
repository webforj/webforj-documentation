---
title: Avatar
sidebar_position: 7
sidebar_class_name: new-content
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

The `Avatar` component provides a visual representation of a user or entity. It can display an image, auto-computed or custom initials, or an icon. Avatars are commonly used in user interfaces to identify users in comment sections, navigation menus, chat applications, or anywhere a visual user identifier enhances the experience.

To create an `Avatar`, pass a label that serves as the accessible name. The component automatically computes initials by extracting the first letter of each word in the label.

```java
// Creates an avatar displaying "JD" from the label
Avatar avatar = new Avatar("John Doe");
```

You can also provide explicit initials if you prefer more control over what's displayed:

```java
// Creates an avatar with custom initials
Avatar avatar = new Avatar("John Doe", "J");
```

:::info Auto-computed Initials
When you create an `Avatar` with just a label, initials are automatically computed by taking the first character of each word. For example, "John Doe" becomes "JD".
:::

<ComponentDemo 
path='/webforj/avatarbasics?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarBasicsView.java'
cssURL='/css/avatar/avatar.css'
height = '350px'
/>

## Displaying images {#displaying-images}

The `Avatar` component can display an image instead of initials by slotting an `Img` component as a child. When an image is provided, it takes precedence over initials.

```java
import com.webforj.component.html.elements.Img;

// Avatar with an image
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Image Sizing
The image automatically scales to fit within the avatar's dimensions based on the current expanse setting.
:::

## Displaying icons {#displaying-icons}

You can display an icon inside the `Avatar` by adding an `Icon` component as a child:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar with an icon
Avatar avatar = new Avatar("Guest User", TablerIcon.create("user"));
```

## Label and initials {#label-and-initials}

The `Avatar` component uses the label for accessibility and tooltip generation. The `setLabel()` and `setText()` methods are aliases—both set the accessible label for the `Avatar`.

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Sets label and auto-generates tooltip
avatar.setInitials("JS");       // Override auto-computed initials
```

:::tip Automatic Tooltip
The component automatically generates a tooltip from the label, making it easy to see the full name on hover. This behavior is disabled when using the default label `"Avatar"`.
:::

## Click events {#click-events}

The `Avatar` component implements `HasElementClickListener`, allowing you to respond to user clicks. This is useful for triggering actions like opening a user profile or displaying a menu.

```java
avatar.onClick(event -> {
  // Handle avatar click - e.g., open user profile
  System.out.println("Avatar clicked!");
});
```

## Themes {#themes}

Themes convey meaning or status—use them to indicate availability, highlight important users, or match your application's design.

The following themes are available:

- `DEFAULT`: Standard appearance
- `GRAY`: Neutral, subdued appearance
- `PRIMARY`: Emphasizes primary actions or users
- `SUCCESS`: Indicates positive status (e.g., online)
- `WARNING`: Indicates caution (e.g., away)
- `DANGER`: Indicates error or busy status
- `INFO`: Provides informational context

Each theme also has an outlined variant for a lighter visual treatment:

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='100px'
/>

## Expanses {#expanses}

Control the avatar size using the `setExpanse()` method. The component supports nine size options ranging from `XXXSMALL` to `XXXLARGE`.

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>

## Shapes {#shapes}

Avatars can be displayed as circles or squares. The default shape is `CIRCLE`, which is standard for user avatars. Use `SQUARE` for entities like teams, companies, or applications.

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## Team Panel Example {#team-panel-example}

The example below showcases avatars in a team panel context. Each `Avatar` displays either a profile image or auto-generated initials based on the user's name. Clicking an `Avatar` opens a dialog with an enlarged view.

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '450px'
/>

## Styling {#styling}

<TableBuilder name="Avatar" />

