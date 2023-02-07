<!-- ---
sidebar_position: 1
--- -->


# Has Mouse Wheel Condition

A control implements the HasMouseWheelCondition interface if the control has specific behavioral interactions with the mouse wheel. Possible values are as follows:

|Alignments|
|----------|
|<ul><li>[`MouseWheelCondition.DEFAULT`](#)</li><li>[`MouseWheelCondition.NEVER`](#)</li><li>[`MouseWheelCondition.FOCUS`](#)</li><li>[`MouseWheelCondition.MOUSE_OVER`](#)</li><li>[`MouseWheelCondition.FOCUS_AND_MOUSE_OVER`](#)</li><li>[`MouseWheelCondition.MOUSE_THEN_FOCUS`](#)</li><li>[`MouseWheelCondition.FOCUS_THEN_MOUSE`](#)</li></ul>|

### At a Glance

| Methods |
|------------|
| <ul><li>[`MouseWheelCondition getScrollWheelBehavior()`](#)</li><li>[`HasMouseWheelCondition setScrollWheelBehavior(MouseWheelCondition condition)`](#)</li></ul>|