package com.webforj.samples.views.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.table.TableColumnComparatorPage;
import com.webforj.samples.views.BaseTest;

public class TableColumnComparatorViewIT extends BaseTest {

    private TableColumnComparatorPage tableColumnComparator;

    @BeforeEach
    public void setupTableColumnComparator() {
        navigateToRoute(TableColumnComparatorPage.getRoute());
        tableColumnComparator = new TableColumnComparatorPage(page);
    }

    @Test
    public void testComparatorFunctionalityTableColumnComparator() {
        tableColumnComparator.getNumberColumnHeader().click();

        List<String> ascNumbersText = tableColumnComparator.getNumberCells().allTextContents();
        List<Integer> ascNumbers = ascNumbersText.stream()
                .limit(3)
                .map(num -> Integer.parseInt(num.trim()))
                .collect(Collectors.toList());

        List<Integer> ascNumbersSorted = new ArrayList<>(ascNumbers);
        Collections.sort(ascNumbersSorted);
        assertEquals(ascNumbersSorted, ascNumbers, "Numbers are not in ascending order!");
    }
}
