package com.webforj.samples.views.table.renderers;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Column;
import com.webforj.component.table.Column.SortDirection;
import com.webforj.component.table.Table;
import com.webforj.component.table.renderer.BadgeRenderer;
import com.webforj.component.table.renderer.BooleanRenderer;
import com.webforj.component.table.renderer.CurrencyRenderer;
import com.webforj.component.table.renderer.PercentageRenderer;
import com.webforj.component.table.renderer.ProgressBarRenderer;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.Locale;

@Route
@FrameTitle("Product Catalog")
public class ProductCatalogView extends Composite<Div> {

  private final Div self = getBoundComponent();

  public ProductCatalogView() {
    Table<Product> table = new Table<>();
    table.setWidth("100vw");
    table.setHeight("100vh");
    table.setStriped(true);

    table.addColumn("name", Product::getName).setFlex(1.7f).setLabel("Product");

    table
        .addColumn("category", Product::getCategory)
        .setFlex(1.0f)
        .setLabel("Category")
        .setRenderer(new BadgeRenderer<>(BadgeTheme.PRIMARY));

    table
        .addColumn("price", Product::getPrice)
        .setFlex(0.7f)
        .setLabel("Price")
        .setSortDirection(SortDirection.DESC)
        .setRenderer(new CurrencyRenderer<>(Locale.US));

    table
        .addColumn("discount", Product::getDiscount)
        .setFlex(1.0f)
        .setLabel("Discount")
        .setRenderer(new PercentageRenderer<>(Theme.SUCCESS));

    table
        .addColumn("rating", Product::getRating)
        .setFlex(1.5f)
        .setLabel("Rating")
        .setRenderer(
            new ProgressBarRenderer<Product>()
                .setMax(5)
                .setTheme(Theme.INFO)
                .setTextVisible(true)
                .setText("<%= cell.value %>/5"));

    table
        .addColumn("inStock", Product::isInStock)
        .setFlex(1f)
        .setLabel("In Stock")
        .setAlignment(Column.Alignment.CENTER)
        .setRenderer(new BooleanRenderer<>());

    table
        .getColumns()
        .forEach(
            col -> {
              col.setSortable(true);
              col.setResizable(true);
            });

    table.setRepository(ProductService.getProducts());
    self.add(table);
  }
}
