package com.webforj.samples.views.viewtransitions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.viewtransitions.ViewTransitionShufflePage;
import com.webforj.samples.views.BaseTest;

class ViewTransitionShuffleViewIT extends BaseTest {

    private ViewTransitionShufflePage shufflePage;

    @BeforeEach
    void setupShuffleDemo() {
        navigateToRoute(ViewTransitionShufflePage.getRoute());
        shufflePage = new ViewTransitionShufflePage(page);
    }

    @Test
    void shouldShuffle() {
        List<String> initialOrder = getCardTexts();
        assertThat(shufflePage.getCards()).hasCount(4);

        shufflePage.getShuffleBtn().click();

        assertThat(shufflePage.getCards()).hasCount(4);

        List<String> shuffledOrder = getCardTexts();
        assertTrue(shuffledOrder.containsAll(initialOrder));
    }

    private List<String> getCardTexts() {
        List<String> texts = new ArrayList<>();
        int count = shufflePage.getCards().count();
        for (int i = 0; i < count; i++) {
            texts.add(shufflePage.getCards().nth(i).innerText());
        }
        return texts;
    }
}
