package com.webforj.samples.views.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.table.TableColumnComparatorPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TableColumnComparatorViewIT extends BaseTest {

    private TableColumnComparatorPage tableColumnComparator;

    public void setupTableColumnComparator(SupportedLanguage language) {
        navigateToRoute(TableColumnComparatorPage.getRoute(language));
        tableColumnComparator = new TableColumnComparatorPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testComparatorFunctionalityTableColumnComparator(SupportedLanguage language) {
        setupTableColumnComparator(language);
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
