---
sidebar_position: 1
title: ColorField
slug: colorfield
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/field/ColorField" top='true'/>

:::success **Important**
The `ColorField` class is a Field component, and as such shares all of the commonalities belonging to a Field. Please refer to the **[Field documentation page](/docs/components/fields)** for an overview of Field properties, events, and other important information.
:::

The color field is a user interface component that allows users to select colors. It provides an intuitive and visually appealing way to choose colors for various purposes.

### Usages

The `ColorField` is best used in scenarios where color selection is a crucial part of the user interface or application functionality. This section outlines the best practices and scenarios for using the color field component effectively.

1. **Graphic Design and Image Editing Tools**: Color fields are essential in applications that involve graphic design, image editing, or any task that requires selecting or modifying colors.

2. **Theme Customization**: If your application allows users to customize themes, using a color field enables them to choose colors for different UI elements, such as backgrounds, text, buttons, etc.

3. **Data Visualization**: Color fields are valuable in applications that involve data visualization, as they allow users to select colors for charts, graphs, heatmaps, and other visual representations.

4. **Form Inputs**: When designing forms that require color inputs, using a color field simplifies the color selection process for users.

### Constructors

The `ColorField` class has four constructors:

1. `ColorField(String label, Color color)`: Creates a `ColorField` with a given label and color.
2. `ColorField(String label)`: Creates a `ColorField` with a given label but with no pre-populated color.
3. `ColorField(Color color)`: Creates a `ColorField` with a given color, but without a label.
4. `ColorField()`: Creates a `ColorField` without any provided information.

### Color Code Format

Currently, the `ColorField` supports `#RRGGBB` hexadecimal format. Only simple colors (without alpha channel) are allowed in the picker interface. Additionally, the `ColorField` comes along with a various methods that interact with the `java.awt.Color` class to add flexibility.

:::info
The component's presentation may vary substantially from one browser and/or platform to another. It might be a simple textual field that automatically validates to ensure that the color information is entered in the proper format, a platform-standard color picker, or some kind of custom color picker window.
:::

### Static Utilities 

The `ColorField` class also provides the following static utility methods:

- `Color fromHex(String hex)`: Convert a color string in hex format to a `Color` object which can then be utilized with this class, or elsewhere.

- `String toHex(Color color)`: Convert the given value to the corresponding hex representation.

- `boolean isValidHexColor(String hex)`: Check if the given value is a valid 7 character hex color.

### Best Practices

To ensure an optimal user experience when using the `ColorField` component, consider the following best practices:

 **Consider Contextual Assistance**: Provide contextual assistance, such as tooltips or inline help, to guide users in understanding the purpose and usage of the color field component.