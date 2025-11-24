package com.webforj.samples.blogs.renderers;


import java.util.ArrayList;
import java.util.List;

public class Cryptocurrency {
  private String symbol;
  private String name;
  private double currentPrice;
  private double previousPrice;
  private double priceChange24h;
  private double priceChangePercentage24h;
  private double marketCap;
  private double volume24h;
  private double high24h;
  private double low24h;
  private long totalSupply;
  private long circulatingSupply;
  private int rank;
  private List<Double> priceHistory;
  private long lastUpdated;

  public Cryptocurrency(String symbol, String name, double currentPrice, double marketCap,
      double volume24h, int rank) {
    this.symbol = symbol;
    this.name = name;
    this.currentPrice = currentPrice;
    this.previousPrice = currentPrice;
    this.marketCap = marketCap;
    this.volume24h = volume24h;
    this.rank = rank;
    this.priceHistory = new ArrayList<>();
    this.lastUpdated = System.currentTimeMillis();
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getCurrentPrice() {
    return currentPrice;
  }

  public void setCurrentPrice(double currentPrice) {
    this.previousPrice = this.currentPrice;
    this.currentPrice = currentPrice;
    updatePriceChange();
    addToPriceHistory(currentPrice);
    this.lastUpdated = System.currentTimeMillis();
  }

  public double getPreviousPrice() {
    return previousPrice;
  }

  public void setPreviousPrice(double previousPrice) {
    this.previousPrice = previousPrice;
  }

  public double getPriceChange24h() {
    return priceChange24h;
  }

  public void setPriceChange24h(double priceChange24h) {
    this.priceChange24h = priceChange24h;
  }

  public double getPriceChangePercentage24h() {
    return priceChangePercentage24h;
  }

  public void setPriceChangePercentage24h(double priceChangePercentage24h) {
    this.priceChangePercentage24h = priceChangePercentage24h;
  }

  public double getMarketCap() {
    return marketCap;
  }

  public void setMarketCap(double marketCap) {
    this.marketCap = marketCap;
  }

  public double getVolume24h() {
    return volume24h;
  }

  public void setVolume24h(double volume24h) {
    this.volume24h = volume24h;
  }

  public double getHigh24h() {
    return high24h;
  }

  public void setHigh24h(double high24h) {
    this.high24h = high24h;
  }

  public double getLow24h() {
    return low24h;
  }

  public void setLow24h(double low24h) {
    this.low24h = low24h;
  }

  public long getTotalSupply() {
    return totalSupply;
  }

  public void setTotalSupply(long totalSupply) {
    this.totalSupply = totalSupply;
  }

  public long getCirculatingSupply() {
    return circulatingSupply;
  }

  public void setCirculatingSupply(long circulatingSupply) {
    this.circulatingSupply = circulatingSupply;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public List<Double> getPriceHistory() {
    return priceHistory;
  }

  public String getPriceHistoryJson() {
    StringBuilder json = new StringBuilder("[");
    for (int i = 0; i < priceHistory.size(); i++) {
      json.append(priceHistory.get(i));
      if (i < priceHistory.size() - 1) {
        json.append(",");
      }
    }
    json.append("]");
    return json.toString();
  }

  public void setPriceHistory(List<Double> priceHistory) {
    this.priceHistory = priceHistory;
  }

  public long getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(long lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  private void updatePriceChange() {
    this.priceChange24h = currentPrice - previousPrice;
    if (previousPrice != 0) {
      this.priceChangePercentage24h = (priceChange24h / previousPrice) * 100;
    }
  }

  private void addToPriceHistory(double price) {
    priceHistory.add(price);
    if (priceHistory.size() > 50) {
      priceHistory.remove(0);
    }
  }
}