package com.webforj.samples.pages.checkbox;

import com.microsoft.playwright.Page;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.views.checkbox.CheckboxHorizontalTextView;

import java.util.List;

public class CheckboxHorizontalTextPage extends AbstractPage {
    
    // Index mapping for checkboxes in CheckboxHorizontalTextView:
    // Index 0: Daily (first column, checked, no bbj-reverse-order)
    // Index 1: Weekly (first column)
    // Index 2: Bi-Weekly (first column)
    // Index 3: Monthly (first column)
    // Index 4: Annually (first column)
    // Index 5: Daily (second column, checked, has bbj-reverse-order)
    // Index 6: Weekly (second column, has bbj-reverse-order)
    // Index 7: Bi-Weekly (second column, has bbj-reverse-order)
    // Index 8: Monthly (second column, has bbj-reverse-order)
    // Index 9: Annually (second column, has bbj-reverse-order)
    
    public CheckboxHorizontalTextPage(Page page) {
      super(page, CheckboxHorizontalTextView.class);
    }

    /**
     * Gets checkboxes by their position in the view.
     * Uses index-based access since text-based filtering finds inner elements.
     *
     * @param name the label text of the checkbox
     * @param isLeftColumn true for first column (indices 0-4), false for second column (indices 5-9)
     * @return a locator for the checkbox element
     */
    private WebforjLocator getCheckboxByNameAndColumn(String name, boolean isLeftColumn) {
        // Map checkbox names to their indices in each column
        int indexOffset = isLeftColumn ? 0 : 5;
        int index = switch (name) {
            case "Daily" -> 0;
            case "Weekly" -> 1;
            case "Bi-Weekly" -> 2;
            case "Monthly" -> 3;
            case "Annually" -> 4;
            default -> throw new IllegalArgumentException("Unknown checkbox name: " + name);
        };
        return getByClass(CheckBox.class).nth(indexOffset + index);
    }
    
    /**
     * Gets the checkbox from the first column (left-aligned, no bbj-reverse-order).
     */
    public WebforjLocator getFirstColumnCheckbox(String name) {
        return getCheckboxByNameAndColumn(name, true);
    }
    
    /**
     * Gets the checkbox from the second column (right-aligned, has bbj-reverse-order).
     */
    public WebforjLocator getSecondColumnCheckbox(String name) {
        return getCheckboxByNameAndColumn(name, false);
    }

    public List<String> getNames() {
      return List.of(
        "Weekly",
        "Bi-Weekly",
        "Monthly",
        "Annually"
      );
    }
}
