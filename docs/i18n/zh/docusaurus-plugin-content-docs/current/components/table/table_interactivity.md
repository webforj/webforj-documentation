---
sidebar_position: 40
title: 交互性
slug: Interactivity
description: >-
  Reference the keyboard shortcuts that move focus, select rows, and navigate
  cells in the Table component.
_i18n_hash: 49377400524e9e1c3a25dd35cfb7750c
---
`Table` 组件会响应用户的各种键盘输入，以支持更精细的交互。以下列表说明了按下指定按键时的行为：


## 键盘交互 {#keyboard-interactions}

|按键|行为|
|:-:|-|
|<kbd>Space</kbd>|选中当前行。|
|<kbd>Enter</kbd>|聚焦单元格；如果单元格有渲染器，则聚焦可聚焦元素。|
|<kbd>Home</kbd>|聚焦第一行第一列的单元格。|
|<kbd>End</kbd>|聚焦最后一行最后一列的单元格。|
|<kbd>Shift</kbd> + <kbd>Tab</kbd>|如果单元格位于表格主体中，按下 Shift + Tab 后会聚焦表头中第一个可聚焦的单元格。|
|<kbd>&#8594;</kbd>|如果按下 Ctrl 键，则聚焦当前行的最后一个单元格；否则，水平聚焦下一个单元格。|
|<kbd>&#8592;</kbd>|如果按下 Ctrl 键，则聚焦当前行的第一个单元格；否则，水平聚焦上一个单元格。|
|<kbd>&#8593;</kbd>|如果按下 Ctrl 键，则聚焦当前列的第一个单元格；否则，垂直聚焦上一个单元格。|
|<kbd>&#8595;</kbd>|如果按下 Ctrl 键，则聚焦当前列的最后一个单元格；否则，垂直聚焦下一个单元格。|
