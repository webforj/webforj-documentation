---
sidebar_position: 140
---

# Text Area

### At a Glance

|Parent Class| Interfaces |
|------------|------------|
|[AbstractDwcControl](#)| <ul><li>[HasMouseWheelCondition](#)</li><li>[HasFocus](#)</li><li>[HasTabTraversal](#)</li></ul>|

| Methods |
|------------|
| <ul><li>[`TextArea addParagraph(Integer index, String paragraph)`](#)</li><li>[`TextArea appendToParagraph(Integer parNum, String text)`](#)</li><li>[`List<String> getAllParagraphs()`](#)</li><li>[`Integer getCurrentParagraphIndex()`](#)</li><li>[`Expanse getExpanse()`](#)</li><li>[`Integer getLineCountLimit()`](#)</li><li>[`Integer getMaxParagraphSize()`](#)</li><li>[`Integer getMaxLength()`](#)</li><li>[`Integer getNumberOfParagraphs()`](#)</li><li>[`String getParagraph(Integer parNum)`](#)</li><li>[`List<String> getSelection()`](#)</li><li>[`Integer getTabSize()`](#)</li><li>[`Theme getTheme()`](#)</li><li>[`void highlight(Integer parIndex1, Integer off1, Integer parIndex2, Integer off2)`](#)</li><li>[`Boolean isHorizontalScrollable()`](#)</li><li>[`Boolean isIgnoreEnters()`](#)</li><li>[`Boolean isIgnoreTabs()`](#)</li><li>[`Boolean isLimitToOneParagraph()`](#)</li><li>[`Boolean isLimitToOneParagraph()`](#)</li><li>[`Boolean isLineWrap()`](#)</li><li>[`Boolean isOvertypeMode()`](#)</li><li>[`Boolean isVerticalScrollable()`](#)</li><li>[`Boolean isWrapStyleWord()`](#)</li><li>[`void removeAll()`](#)</li><li>[`TextArea removeParagraph(Integer parIndex)`](#)</li><li>[`TextArea setExpanse(Expanse expanse)`](#)</li><li>[`TextArea setHorizontalScrollable(Boolean scroll)`](#)</li><li>[`TextArea setIgnoreEnters(Boolean ignore)`](#)</li><li>[`TextArea setIgnoreTabs(Boolean ignore)`](#)</li><li>[`TextArea setLimitToOneParagraph(Boolean limit)`](#)</li><li>[`TextArea setLineCountLimit(Integer limit)`](#)</li><li>[`TextArea setLineWrap(Boolean wrap)`](#)</li><li>[`TextArea setMaxParagraphSize(Integer size)`](#)</li><li>[`TextArea setMaxLength(Integer length)`](#)</li><li>[`TextArea setOvertypeMode(Boolean overtype)`](#)</li><li>[`TextArea setTabSize(Integer size)`](#)</li><li>[`TextArea setTheme(Theme theme)`](#)</li><li>[`TextArea setVerticalScrollable(Boolean scroll)`](#)</li><li>[`TextArea setWrapStyleWord(Boolean word)`](#)</li></ul>|


| Events |
|------------|
| <ul><li>[`public TextArea onEditModify(Consumer<TextAreaOnEditModifyEvent> callback)`](#)</li></ul> |