package tests.TableTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TableColumnComparatorIT extends BaseTest {

    @BeforeEach
    public void setupTableColumnComparator() {
        page.navigate("https://docs.webforj.com/tablecolumncomparator?");
    }

    @BrowserTest
    public void testComparatorFunctionality() {
        Locator numberColumnHeader = page.locator("th[data-column='Number']");
        Locator numberCells = page.locator("td[data-cell*='-Number'] div[part='cell-label']");

        numberColumnHeader.click();

        List<String> ascNumbersText = numberCells.allTextContents();
        List<Integer> ascNumbers = ascNumbersText.stream()
                .limit(3)
                .map(num -> Integer.parseInt(num.trim()))
                .collect(Collectors.toList());

        List<Integer> ascNumbersSorted = new ArrayList<>(ascNumbers);
        Collections.sort(ascNumbersSorted);
        Assertions.assertEquals(ascNumbersSorted, ascNumbers, "Numbers are not in ascending order!");

        numberColumnHeader.click();
        page.waitForTimeout(500);

        List<String> descNumbersText = numberCells.allTextContents();
        List<Integer> descNumbers = descNumbersText.stream()
                .limit(3)
                .map(num -> Integer.parseInt(num.trim()))
                .collect(Collectors.toList());

        List<Integer> descNumbersSorted = new ArrayList<>(descNumbers);
        descNumbersSorted.sort(Collections.reverseOrder());
        Assertions.assertEquals(descNumbersSorted, descNumbers, "Numbers are not in descending order!");
    }
} 