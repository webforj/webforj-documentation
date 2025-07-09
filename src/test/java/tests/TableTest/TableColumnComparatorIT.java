package tests.TableTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import tests.BaseTest;
import utils.annotations.BrowserTest;
import pages.TablePages.TableColumnComparatorViewPage;

public class TableColumnComparatorIT extends BaseTest {

    private TableColumnComparatorViewPage tableColumnComparator;

    @BeforeEach
    public void setupTableColumnComparator() {
        navigateToRoute(TableColumnComparatorViewPage.getRoute());
        tableColumnComparator = new TableColumnComparatorViewPage(page);
    }

    @BrowserTest
    public void testComparatorFunctionality() {
        tableColumnComparator.getNumberColumnHeader().click();

        List<String> ascNumbersText = tableColumnComparator.getNumberCells().allTextContents();
        List<Integer> ascNumbers = ascNumbersText.stream()
                .limit(3)
                .map(num -> Integer.parseInt(num.trim()))
                .collect(Collectors.toList());

        List<Integer> ascNumbersSorted = new ArrayList<>(ascNumbers);
        Collections.sort(ascNumbersSorted);
        Assertions.assertEquals(ascNumbersSorted, ascNumbers, "Numbers are not in ascending order!");

        tableColumnComparator.getNumberColumnHeader().click();
        page.waitForTimeout(500);

        List<String> descNumbersText = tableColumnComparator.getNumberCells().allTextContents();
        List<Integer> descNumbers = descNumbersText.stream()
                .limit(3)
                .map(num -> Integer.parseInt(num.trim()))
                .collect(Collectors.toList());

        List<Integer> descNumbersSorted = new ArrayList<>(descNumbers);
        descNumbersSorted.sort(Collections.reverseOrder());
        Assertions.assertEquals(descNumbersSorted, descNumbers, "Numbers are not in descending order!");
    }
}