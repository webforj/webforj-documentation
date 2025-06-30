package pages.CompositePage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class CompositeDemoPage extends BasePage {

    private static final String ROUTE = "composite";

    private final Locator todosTitle;
    private final Locator inputField;
    private final Locator todoItemsContainer;
    private final Locator todoItemToggleBaseSelector;
    private final Locator todoItemsSelector;
    private final Locator strikethroughItemSelector;

    public CompositeDemoPage(Page page) {
        super(page);

        this.todosTitle = page.locator("h1[dwc-id=\"11\"]");
        this.inputField = page.locator("#field-1");
        this.todoItemsContainer = page.locator("div[dwc-id=\"12\"]");
        this.todoItemToggleBaseSelector = page.locator("dwc-radio[dwc-id=\"15\"] >> div > input");
        this.todoItemsSelector = page.locator("div[dwc-id=\"12\"] > div");
        this.strikethroughItemSelector = page.locator("div[dwc-id=\"16\"]");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public boolean isTodosTitleDisplayed() {
        return todosTitle.innerText().equals("Todos");
    }

    public void addTodoItem(String itemText) {
        inputField.fill(itemText);
        page.keyboard().press("Enter");
    }

    public String getTodoItemText(int index) {
        Locator todoItem = todoItemsSelector.nth(index);
        return todoItem.innerText();
    }

    public void clickToggle() {
        todoItemToggleBaseSelector.click();
    }

    public boolean isTodoItemCompleted() {
        return todoItemToggleBaseSelector.isChecked();
    }

    public boolean hasStrikethroughStyle() {
        String style = strikethroughItemSelector.getAttribute("style");
        return style != null && style.contains("text-decoration") && style.contains("line-through");
    }

    public boolean hasNoStrikethroughStyle() {
        String style = strikethroughItemSelector.getAttribute("style");
        return style == null || !style.contains("line-through");
    }

    public void waitForStrikethrough() {
        page.waitForCondition(() -> {
            String style = strikethroughItemSelector.getAttribute("style");
            return style != null && style.contains("text-decoration") && style.contains("line-through");
        }, new Page.WaitForConditionOptions().setTimeout(5000));
    }

    public void waitForNoStrikethrough() {
        page.waitForCondition(() -> {
            String style = strikethroughItemSelector.getAttribute("style");
            return style == null || !style.contains("line-through");
        }, new Page.WaitForConditionOptions().setTimeout(5000));
    }

    public Locator getTodoItemsContainer() {
        return todoItemsContainer;
    }

    public Locator getTodoItemToggleBaseSelector() {
        return todoItemToggleBaseSelector;
    }

    public Locator getInputField() {
        return inputField;
    }

    public Locator getTodoItemsSelector() {
        return todoItemsSelector;
    }
} 