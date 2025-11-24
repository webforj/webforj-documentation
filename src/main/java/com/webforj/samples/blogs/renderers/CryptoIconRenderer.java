package com.webforj.samples.blogs.renderers;


import com.webforj.component.table.renderer.Renderer;

public class CryptoIconRenderer extends Renderer<Cryptocurrency> {
  @Override
  public String build() {
    return /* html */"""
          <%
            const cryptoName = cell.row.getValue('Name');
            const cryptoSymbol = cell.row.getValue('Symbol');
            const symbolLower = cryptoSymbol.toLowerCase();
            // Add "2" only if the ticker is 3 characters or less
            const iconUrl = symbolLower.length <= 3 
              ? `https://assets.coincap.io/assets/icons/${symbolLower}2@2x.png`
              : `https://assets.coincap.io/assets/icons/${symbolLower}@2x.png`;
          %>
          <div part='crypto-info'>
            <img part='crypto-icon' src="<%= iconUrl %>" />
            <div>
            <div part='crypto-name' class='crypto-name-desktop'><%= cryptoName%></div>
            <div part='crypto-symbol'><%= cryptoSymbol%></div>
            </div>
            </div>
            """;
  }
}