package com.webforj.samples.views.splitter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.options.BoundingBox;

import com.webforj.samples.pages.splitter.SplitterPositionPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SplitterPositionViewIT extends BaseTest {

  private SplitterPositionPage splitterPage;

  public void setupSplitterPosition(SupportedLanguage language) {
    navigateToRoute(SplitterPositionPage.getRoute(language));
    splitterPage = new SplitterPositionPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSplitterPosition(SupportedLanguage language) {
    setupSplitterPosition(language);
    BoundingBox masterBox = splitterPage.getPositionedMasterPanel().boundingBox();
    BoundingBox detailBox = splitterPage.getPositionedDetailPanel().boundingBox();

    int masterWidth = (int) masterBox.width;
    int detailWidth = (int) detailBox.width;

    double ratio = (double) masterWidth / detailWidth;

    assertTrue(ratio > 2.5 && ratio < 3.5);
  }
}
