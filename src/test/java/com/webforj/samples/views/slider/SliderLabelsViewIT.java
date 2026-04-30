package com.webforj.samples.views.slider;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.slider.SliderLabelsPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SliderLabelsViewIT extends BaseTest {

  private SliderLabelsPage sliderPage;

  @BeforeEach
  public void setupSliderLabels() {
    navigateToRoute(SliderLabelsPage.getRoute());
    sliderPage = new SliderLabelsPage(page);
  }

  @Test
  public void testLabelsThemesSlider() {
    sliderPage.getTenDegreeOption().click();
    assertThat(sliderPage.getSliderLabel()).hasAttribute("theme", "primary");

    sliderPage.getFortyDegreeOption().click();
    assertThat(sliderPage.getSliderLabel()).hasAttribute("theme", "success");
  }
}
