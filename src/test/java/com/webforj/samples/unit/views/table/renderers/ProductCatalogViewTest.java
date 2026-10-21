package com.webforj.samples.unit.views.table.renderers;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Table;
import com.webforj.component.table.renderer.BadgeRenderer;
import com.webforj.component.table.renderer.BooleanRenderer;
import com.webforj.component.table.renderer.CurrencyRenderer;
import com.webforj.component.table.renderer.PercentageRenderer;
import com.webforj.component.table.renderer.ProgressBarRenderer;
import com.webforj.samples.views.table.renderers.Product;
import com.webforj.samples.views.table.renderers.ProductCatalogView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ProductCatalogViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ProductCatalogView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldShowCategoryPriceDiscountRatingAndStockThroughBuiltInRenderers(
      WebforjTestbed context) {
    context.add(new ProductCatalogView());

    Table<Product> table = ComponentLocator.of(Table.class).locate();

    assertInstanceOf(BadgeRenderer.class, table.getColumnById("category").getRenderer());
    assertInstanceOf(CurrencyRenderer.class, table.getColumnById("price").getRenderer());
    assertInstanceOf(PercentageRenderer.class, table.getColumnById("discount").getRenderer());
    assertInstanceOf(ProgressBarRenderer.class, table.getColumnById("rating").getRenderer());
    assertInstanceOf(BooleanRenderer.class, table.getColumnById("inStock").getRenderer());
  }
}
