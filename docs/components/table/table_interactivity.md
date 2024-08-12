---
sidebar_position: 30
title: Interactivity
slug: Interactivity
---

The `Table` component will respond to various keyboard inputs from users to allow for more fine-grained interaction. The following list outlines the various behaviors when the designated key is pressed:


## Keyboard Interations

|Key|Behavior|
|:-:|-|
|<kbd>Space</kbd>|Selects the current row.|
|<kbd>Enter</kbd>|Focuses the cell, or the focusable element if the cell has a renderer.|
|<kbd>Home</kbd>|Focuses the first cell of the first column and row.|
|<kbd>End</kbd>|Focuses the last cell of the last column and row.|
|<kbd>Shift</kbd> + <kbd>Tab</kbd>|If the cell is in the body of the table and Shift + Tab is pressed, it focuses the first focusable cell in the header.|
|<kbd>&#8594;</kbd>|If the Ctrl key is pressed, it focuses the last cell of the current row. Otherwise, it focuses the next cell horizontally.|
|<kbd>&#8592;</kbd>|If the Ctrl key is pressed, it focuses the first cell of the current row. Otherwise, it focuses the previous cell horizontally.|
|<kbd>&#8593;</kbd>|If the Ctrl key is pressed, it focuses the first cell of the current column. Otherwise, it focuses the previous cell vertically.|
|<kbd>&#8595;</kbd>|If the Ctrl key is pressed, it focuses the last cell of the current column. Otherwise, it focuses the next cell vertically.|