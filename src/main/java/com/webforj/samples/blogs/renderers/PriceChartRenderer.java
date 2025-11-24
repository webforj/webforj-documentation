package com.webforj.samples.blogs.renderers;


import com.webforj.component.table.renderer.Renderer;

public class PriceChartRenderer extends Renderer<Cryptocurrency> {
    @Override
    public String build() {
      return /* html */"""
            <%
              const history = JSON.parse(cell.row.getValue('PriceHistory'));
              const values = history.slice(-20); // Last 20 data points
              const min = Math.min(...values);
              const max = Math.max(...values);
              const range = max - min || 1;
              const svgWidth = 200; // Viewbox width for calculations
              const isMobile = window.innerWidth <= 480;
              const svgHeight = isMobile ? 30 : 35;
              const strokeWidth = isMobile ? 1.5 : 2;
              const padding = isMobile ? 2 : 3; // Add padding for mobile

              // Generate sparkline path with padding for mobile
              const points = values.map((v, i) => {
                const x = (i / (values.length - 1)) * svgWidth;
                const y = padding + (svgHeight - 2 * padding) - ((v - min) / range) * (svgHeight - 2 * padding);
                return `${x},${y}`;
              }).join(' ');

              // Determine color based on price change
              const priceChange = cell.row.getValue('PriceChangePercentage24h');
              const color = priceChange >= 0 ? 'var(--dwc-color-success)' : 'var(--dwc-color-danger)';
            %>
            <div part="sparkline-cell" class="sparkline-container" style="width: 100%; display: flex; align-items: center;">
              <svg width="100%" height="<%= svgHeight %>" viewBox="0 0 <%= svgWidth %> <%= svgHeight %>" preserveAspectRatio="none" style="width: 100%;">
                <polyline
                  points="<%= points %>"
                  fill="none"
                  stroke="<%= color %>"
                  stroke-width="<%= strokeWidth %>"
                  stroke-linejoin="round"
                  stroke-linecap="round"
                  vector-effect="non-scaling-stroke"
                />
              </svg>
            </div>
          """;
    }
  }