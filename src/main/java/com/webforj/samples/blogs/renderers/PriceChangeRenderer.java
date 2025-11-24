package com.webforj.samples.blogs.renderers;


import com.webforj.component.table.renderer.Renderer;

public class PriceChangeRenderer extends Renderer<Cryptocurrency> {
  @Override
  public String build() {
    return /* html */"""
          <%
            const priceChange = parseFloat(cell.row.getValue('PriceChange24h'));
            const percentageChange = parseFloat(cell.row.getValue('PriceChangePercentage24h'));
            
            // Format price change
            const sign = priceChange >= 0 ? "+" : "-";
            let formattedPrice;
            if (Math.abs(priceChange) >= 1) {
              formattedPrice = sign + "$" + Math.abs(priceChange).toFixed(2);
            } else {
              formattedPrice = sign + "$" + Math.abs(priceChange).toFixed(4);
            }
            
            // Format percentage
            const formattedPercentage = sign + Math.abs(percentageChange).toFixed(2) + "%";
            
            // Determine color class
            let colorClass = "neutral";
            if (priceChange > 0) {
              colorClass = "gain";
            } else if (priceChange < 0) {
              colorClass = "loss";
            }
          %>
          <div part="price-change-container">
            <span part="price-change-<%= colorClass %>"><%= formattedPrice %></span>
            <span part="percentage-badge-<%= colorClass %>"><%= formattedPercentage %></span>
          </div>
          """;
  }
}