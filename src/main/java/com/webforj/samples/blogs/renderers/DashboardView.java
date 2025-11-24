package com.webforj.samples.blogs.renderers;

import java.util.List;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.router.annotation.Route;

@Route("blogs/cryptocurrency-dashboard")
@StyleSheet("ws://blogs/renderers/dashboard-view.css")
public class DashboardView extends Composite<Div> {

  private final CryptocurrencyService cryptoService = new CryptocurrencyService();

  public DashboardView() {
    Div container = getBoundComponent();
    container.setHeight("100%"); 
    container.setWidth("100%");
    CryptocurrencyTable cryptoTable = new CryptocurrencyTable();
    container.add(cryptoTable);
    List<Cryptocurrency> cryptocurrencies = cryptoService.generateCryptocurrencies();
    cryptoTable.setData(cryptocurrencies);

  }
  
}
