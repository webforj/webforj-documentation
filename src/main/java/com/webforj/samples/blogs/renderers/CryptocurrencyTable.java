package com.webforj.samples.blogs.renderers;


import com.webforj.Page;
import com.webforj.component.Composite;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.component.table.Column.PinDirection;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.event.page.PageEventOptions;

import java.util.List;

public class CryptocurrencyTable extends Composite<Table> {

  @SuppressWarnings("unchecked")
  private Table<Cryptocurrency> table = getBoundComponent();
  private Column<Cryptocurrency, ?> cryptoColumn;
  private Column<Cryptocurrency, ?> marketCapColumn;
  private Column<Cryptocurrency, ?> volumeColumn;

  public CryptocurrencyTable() {
    initializeTable();
    setupResizeListener();
  }

  private void initializeTable() {
    // Add columns for cryptocurrency data
    table.addColumn("Symbol", Cryptocurrency::getSymbol).setHidden(true);
    table.addColumn("Name", Cryptocurrency::getName).setHidden(true);
    cryptoColumn = table.addColumn("Crypto", Cryptocurrency::getSymbol)
        .setRenderer(new CryptoIconRenderer()).setMinWidth(250.0f);
    table.addColumn("Price", c -> FormatUtils.formatPrice(c.getCurrentPrice()))
        .setSortable(true);
    table.addColumn("24h Change", Cryptocurrency::getPriceChange24h)
        .setRenderer(new PriceChangeRenderer())
        .setSortable(true)
        .setMinWidth(180.0f);
    table.addColumn("PriceChange24h", Cryptocurrency::getPriceChange24h).setHidden(true);
    table.addColumn("PriceChangePercentage24h", Cryptocurrency::getPriceChangePercentage24h).setHidden(true);
    marketCapColumn = table.addColumn("Market Cap", c -> FormatUtils.formatLargeNumber(c.getMarketCap()))
        .setSortable(true);
    volumeColumn = table.addColumn("Volume (24h)", c -> FormatUtils.formatLargeNumber(c.getVolume24h()))
        .setSortable(true);
    table.addColumn("Price Chart", Cryptocurrency::getCurrentPrice)
        .setRenderer(new PriceChartRenderer());
    table.addColumn("PriceHistory", Cryptocurrency::getPriceHistoryJson)
        .setHidden(true);

    // Configure table properties
    table.setMultiSorting(true);
    table.setRowHeight(65);
    table.setHeight("100%");
    table.setColumnsToAutoSize();
  }

  public void setData(List<Cryptocurrency> cryptocurrencies) {
    table.setRepository(new CollectionRepository<>(cryptocurrencies));
  }

  public CollectionRepository<Cryptocurrency> getRepository() {
    return (CollectionRepository<Cryptocurrency>) table.getRepository();
  }

  private void applyMobileSettings() {
    if (cryptoColumn != null) {
      cryptoColumn.setPinDirection(PinDirection.LEFT);
      cryptoColumn.setMinWidth(80.0f);
    }
    if (marketCapColumn != null) {
      marketCapColumn.setHidden(true);
    }
    if (volumeColumn != null) {
      volumeColumn.setHidden(true);
    }

    if (!table.isDestroyed()) {
      table.setRowHeight(30);
      table.getColumnById("Price").setMinWidth(125.0f);
    }
  }

  private void applyDesktopSettings() {
    if (cryptoColumn != null) {
      cryptoColumn.setPinDirection(PinDirection.AUTO);
      cryptoColumn.setMinWidth(250.0f);
    }
    if (marketCapColumn != null) {
      marketCapColumn.setHidden(false);
    }
    if (volumeColumn != null) {
      volumeColumn.setHidden(false);
    }

    if (!table.isDestroyed()) {
      table.setRowHeight(65);
    }
  }

  private void applyResponsiveSettings() {
    try {
      Object result = Page.getCurrent().executeJs("window.innerWidth");
      if (result != null) {
        double width = 0;
        if (result instanceof Number) {
          width = ((Number) result).doubleValue();
        } else {
          width = Double.parseDouble(result.toString());
        }

        if (width <= 480) {
          applyMobileSettings();
        } else {
          applyDesktopSettings();
        }
      } else {
        applyDesktopSettings();
      }
    } catch (Exception e) {
      applyDesktopSettings();
    }
  }

  private void setupResizeListener() {
    applyResponsiveSettings();
    PageEventOptions options = new PageEventOptions();
    options.setDebounce(500);
    Page.getCurrent().addEventListener("resize", e -> {
      applyResponsiveSettings();
    }, options);
  }
}
