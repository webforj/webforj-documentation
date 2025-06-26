# Third-Party Component Event Annotation Patterns

When integrating third-party web components, proper event annotation is crucial for capturing event data reliably. Here are comprehensive patterns for common scenarios:

## Pattern 1: Standard HTML Input Events

```java
// For third-party input components that follow HTML standards
@EventName("input")
@EventOptions(data = {
    @EventOptions.EventData(key = "value", exp = "event.target.value"),
    @EventOptions.EventData(key = "oldValue", exp = "event.target.getAttribute('data-old-value') || ''"),
    @EventOptions.EventData(key = "inputType", exp = "event.inputType || 'unknown'"),
    @EventOptions.EventData(key = "isComposing", exp = "event.isComposing || false")
})
public static class ThirdPartyInputEvent extends ComponentEvent<MyThirdPartyInput> {
    public ThirdPartyInputEvent(MyThirdPartyInput component, Map<String, Object> eventData) {
        super(component, eventData);
    }

    public String getValue() {
        return (String) getData().get("value");
    }

    public String getOldValue() {
        return (String) getData().get("oldValue");
    }

    public String getInputType() {
        return (String) getData().get("inputType");
    }

    public Boolean isComposing() {
        return (Boolean) getData().get("isComposing");
    }
}
```

## Pattern 2: Custom Detail Events

```java
// For components that use event.detail for custom data
@EventName("value-changed")
@EventOptions(data = {
    @EventOptions.EventData(key = "value", exp = "event.detail.value"),
    @EventOptions.EventData(key = "oldValue", exp = "event.detail.oldValue"),
    @EventOptions.EventData(key = "source", exp = "event.detail.source || 'user'"),
    @EventOptions.EventData(key = "metadata", exp = "JSON.stringify(event.detail.metadata || {})")
})
public static class DetailBasedEvent extends ComponentEvent<MyCustomComponent> {
    public DetailBasedEvent(MyCustomComponent component, Map<String, Object> eventData) {
        super(component, eventData);
    }

    public Object getValue() {
        return getData().get("value");
    }

    public Object getOldValue() {
        return getData().get("oldValue");
    }

    public String getSource() {
        return (String) getData().get("source");
    }

    public String getMetadataJson() {
        return (String) getData().get("metadata");
    }
}
```

## Pattern 3: Selection and Choice Events

```java
// For dropdown, listbox, or selection components
@EventName("selection-changed")
@EventOptions(data = {
    @EventOptions.EventData(key = "selectedValue", exp = "event.detail.value"),
    @EventOptions.EventData(key = "selectedIndex", exp = "event.detail.index || -1"),
    @EventOptions.EventData(key = "selectedLabel", exp = "event.detail.label || ''"),
    @EventOptions.EventData(key = "selectedItems", exp = "JSON.stringify(event.detail.items || [])"),
    @EventOptions.EventData(key = "selectionType", exp = "event.detail.type || 'single'")
})
public static class SelectionEvent extends ComponentEvent<MySelectionComponent> {
    public SelectionEvent(MySelectionComponent component, Map<String, Object> eventData) {
        super(component, eventData);
    }

    public Object getSelectedValue() {
        return getData().get("selectedValue");
    }

    public Integer getSelectedIndex() {
        return ((Number) getData().get("selectedIndex")).intValue();
    }

    public String getSelectedLabel() {
        return (String) getData().get("selectedLabel");
    }

    public List<Object> getSelectedItems() {
        String json = (String) getData().get("selectedItems");
        // Parse JSON or return empty list
        return parseJsonArray(json);
    }

    public String getSelectionType() {
        return (String) getData().get("selectionType");
    }
}
```

## Pattern 4: Mouse and Touch Interaction Events

```java
// For components with rich mouse/touch interaction
@EventName("item-click")
@EventOptions(data = {
    @EventOptions.EventData(key = "itemId", exp = "event.detail.itemId"),
    @EventOptions.EventData(key = "itemData", exp = "JSON.stringify(event.detail.item || {})"),
    @EventOptions.EventData(key = "clickX", exp = "event.detail.clientX || event.clientX"),
    @EventOptions.EventData(key = "clickY", exp = "event.detail.clientY || event.clientY"),
    @EventOptions.EventData(key = "button", exp = "event.button || 0"),
    @EventOptions.EventData(key = "ctrlKey", exp = "event.ctrlKey || false"),
    @EventOptions.EventData(key = "shiftKey", exp = "event.shiftKey || false"),
    @EventOptions.EventData(key = "altKey", exp = "event.altKey || false")
})
public static class ItemClickEvent extends ComponentEvent<MyInteractiveComponent> {
    public ItemClickEvent(MyInteractiveComponent component, Map<String, Object> eventData) {
        super(component, eventData);
    }

    public String getItemId() {
        return (String) getData().get("itemId");
    }

    public String getItemDataJson() {
        return (String) getData().get("itemData");
    }

    public Integer getClickX() {
        return ((Number) getData().get("clickX")).intValue();
    }

    public Integer getClickY() {
        return ((Number) getData().get("clickY")).intValue();
    }

    public Integer getButton() {
        return ((Number) getData().get("button")).intValue();
    }

    public Boolean isCtrlPressed() {
        return (Boolean) getData().get("ctrlKey");
    }

    public Boolean isShiftPressed() {
        return (Boolean) getData().get("shiftKey");
    }

    public Boolean isAltPressed() {
        return (Boolean) getData().get("altKey");
    }
}
```

## Pattern 5: Validation and State Change Events

```java
// For form components with validation states
@EventName("validation-changed")
@EventOptions(data = {
    @EventOptions.EventData(key = "isValid", exp = "event.detail.valid"),
    @EventOptions.EventData(key = "errors", exp = "JSON.stringify(event.detail.errors || [])"),
    @EventOptions.EventData(key = "warnings", exp = "JSON.stringify(event.detail.warnings || [])"),
    @EventOptions.EventData(key = "fieldName", exp = "event.target.name || event.target.id"),
    @EventOptions.EventData(key = "value", exp = "event.target.value"),
    @EventOptions.EventData(key = "validationTrigger", exp = "event.detail.trigger || 'unknown'")
})
public static class ValidationEvent extends ComponentEvent<MyValidatableComponent> {
    public ValidationEvent(MyValidatableComponent component, Map<String, Object> eventData) {
        super(component, eventData);
    }

    public Boolean isValid() {
        return (Boolean) getData().get("isValid");
    }

    public List<String> getErrors() {
        String json = (String) getData().get("errors");
        return parseJsonStringArray(json);
    }

    public List<String> getWarnings() {
        String json = (String) getData().get("warnings");
        return parseJsonStringArray(json);
    }

    public String getFieldName() {
        return (String) getData().get("fieldName");
    }

    public String getValue() {
        return (String) getData().get("value");
    }

    public String getValidationTrigger() {
        return (String) getData().get("validationTrigger");
    }
}
```

## Pattern 6: Drag and Drop Events

```java
// For components with drag and drop functionality
@EventName("item-dropped")
@EventOptions(data = {
    @EventOptions.EventData(key = "draggedItemId", exp = "event.detail.draggedItem.id"),
    @EventOptions.EventData(key = "draggedItemData", exp = "JSON.stringify(event.detail.draggedItem)"),
    @EventOptions.EventData(key = "targetItemId", exp = "event.detail.target.id"),
    @EventOptions.EventData(key = "targetItemData", exp = "JSON.stringify(event.detail.target)"),
    @EventOptions.EventData(key = "dropPosition", exp = "event.detail.position || 'on'"),
    @EventOptions.EventData(key = "dropIndex", exp = "event.detail.index || -1"),
    @EventOptions.EventData(key = "operation", exp = "event.detail.operation || 'move'")
})
public static class ItemDropEvent extends ComponentEvent<MyDragDropComponent> {
    public ItemDropEvent(MyDragDropComponent component, Map<String, Object> eventData) {
        super(component, eventData);
    }

    public String getDraggedItemId() {
        return (String) getData().get("draggedItemId");
    }

    public String getDraggedItemDataJson() {
        return (String) getData().get("draggedItemData");
    }

    public String getTargetItemId() {
        return (String) getData().get("targetItemId");
    }

    public String getTargetItemDataJson() {
        return (String) getData().get("targetItemData");
    }

    public String getDropPosition() {
        return (String) getData().get("dropPosition");
    }

    public Integer getDropIndex() {
        return ((Number) getData().get("dropIndex")).intValue();
    }

    public String getOperation() {
        return (String) getData().get("operation");
    }
}
```

## Pattern 7: Loading and Async State Events

```java
// For components with loading states and async operations
@EventName("loading-state-changed")
@EventOptions(data = {
    @EventOptions.EventData(key = "isLoading", exp = "event.detail.loading"),
    @EventOptions.EventData(key = "operation", exp = "event.detail.operation || 'unknown'"),
    @EventOptions.EventData(key = "progress", exp = "event.detail.progress || 0"),
    @EventOptions.EventData(key = "total", exp = "event.detail.total || 100"),
    @EventOptions.EventData(key = "message", exp = "event.detail.message || ''"),
    @EventOptions.EventData(key = "startTime", exp = "event.detail.startTime || Date.now()"),
    @EventOptions.EventData(key = "error", exp = "event.detail.error ? event.detail.error.message : null")
})
public static class LoadingStateEvent extends ComponentEvent<MyAsyncComponent> {
    public LoadingStateEvent(MyAsyncComponent component, Map<String, Object> eventData) {
        super(component, eventData);
    }

    public Boolean isLoading() {
        return (Boolean) getData().get("isLoading");
    }

    public String getOperation() {
        return (String) getData().get("operation");
    }

    public Integer getProgress() {
        return ((Number) getData().get("progress")).intValue();
    }

    public Integer getTotal() {
        return ((Number) getData().get("total")).intValue();
    }

    public String getMessage() {
        return (String) getData().get("message");
    }

    public Long getStartTime() {
        return ((Number) getData().get("startTime")).longValue();
    }

    public String getError() {
        return (String) getData().get("error");
    }

    public double getProgressPercentage() {
        return (double) getProgress() / getTotal() * 100;
    }
}
```

## Pattern 8: Rich Text Editor Events

```java
// Complete example for rich text editor integration
@EventName("content-changed")
@EventOptions(data = {
    @EventOptions.EventData(key = "content", exp = "event.detail.content"),
    @EventOptions.EventData(key = "text", exp = "event.detail.text"),
    @EventOptions.EventData(key = "length", exp = "event.detail.length"),
    @EventOptions.EventData(key = "delta", exp = "JSON.stringify(event.detail.delta || {})"),
    @EventOptions.EventData(key = "source", exp = "event.detail.source || 'user'"),
    @EventOptions.EventData(key = "timestamp", exp = "Date.now()")
})
public static class ContentChangedEvent extends ComponentEvent<RichTextEditor> {
    public ContentChangedEvent(RichTextEditor component, Map<String, Object> eventData) {
        super(component, eventData);
    }

    public String getContent() {
        return (String) getData().get("content");
    }

    public String getText() {
        return (String) getData().get("text");
    }

    public Integer getLength() {
        return ((Number) getData().get("length")).intValue();
    }

    public String getDeltaJson() {
        return (String) getData().get("delta");
    }

    public String getSource() {
        return (String) getData().get("source");
    }

    public Long getTimestamp() {
        return ((Number) getData().get("timestamp")).longValue();
    }

    public boolean isUserGenerated() {
        return "user".equals(getSource());
    }
}
```

## Essential Helper Methods for Event Classes

```java
// Add these helper methods to your event classes for JSON parsing
public abstract class BaseThirdPartyEvent extends ComponentEvent<ElementComposite> {
    
    public BaseThirdPartyEvent(ElementComposite component, Map<String, Object> eventData) {
        super(component, eventData);
    }

    protected List<String> parseJsonStringArray(String json) {
        if (json == null || json.trim().isEmpty() || "null".equals(json)) {
            return new ArrayList<>();
        }
        try {
            // Use your preferred JSON library (Gson, Jackson, etc.)
            return gson.fromJson(json, new TypeToken<List<String>>(){}.getType());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    protected List<Object> parseJsonArray(String json) {
        if (json == null || json.trim().isEmpty() || "null".equals(json)) {
            return new ArrayList<>();
        }
        try {
            return gson.fromJson(json, new TypeToken<List<Object>>(){}.getType());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    protected Map<String, Object> parseJsonObject(String json) {
        if (json == null || json.trim().isEmpty() || "null".equals(json)) {
            return new HashMap<>();
        }
        try {
            return gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    protected <T> T parseJsonAs(String json, Class<T> clazz) {
        if (json == null || json.trim().isEmpty() || "null".equals(json)) {
            return null;
        }
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }
}
```

## Best Practices for Third-Party Event Annotations

1. **Always provide fallback values** using `||` operator:
   ```java
   @EventOptions.EventData(key = "value", exp = "event.detail.value || event.target.value || ''")
   ```

2. **Use JSON.stringify for complex objects**:
   ```java
   @EventOptions.EventData(key = "itemData", exp = "JSON.stringify(event.detail.item || {})")
   ```

3. **Check for nested properties safely**:
   ```java
   @EventOptions.EventData(key = "nestedValue", exp = "event.detail.data && event.detail.data.value || null")
   ```

4. **Capture element context when needed**:
   ```java
   @EventOptions.EventData(key = "elementId", exp = "event.target.id")
   @EventOptions.EventData(key = "elementClass", exp = "event.target.className")
   ```

5. **Handle boolean values explicitly**:
   ```java
   @EventOptions.EventData(key = "isChecked", exp = "Boolean(event.target.checked)")
   ```

6. **Include coordinates for mouse events**:
   ```java
   @EventOptions.EventData(key = "clientX", exp = "event.clientX || 0")
   @EventOptions.EventData(key = "clientY", exp = "event.clientY || 0")
   ```

---

## Key Takeaways

1. **Understand the component's event structure** - Check documentation for event.detail vs event.target
2. **Always use fallbacks** - Third-party components may have different event structures
3. **JSON.stringify complex data** - Ensures proper serialization
4. **Test thoroughly** - Third-party events can vary between versions
5. **Document assumptions** - Note expected event structure in comments

## Navigation

- [← Previous: Event Handling](06-event-handling.md)
- [Next: Styling & CSS →](08-styling-css.md)
- [Back to Index](00-index.md)