package tests.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import pages.table.TableColumnComparatorPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TableColumnComparatorIT extends BaseTest {

    private TableColumnComparatorPage tableColumnComparator;

    @BeforeEach
    public void setupTableColumnComparator() {
        navigateToRoute(TableColumnComparatorPage.getRoute());
        tableColumnComparator = new TableColumnComparatorPage(page);
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