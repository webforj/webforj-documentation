package com.webforj.samples.blogs.renderers;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CryptocurrencyService {

  private final String[][] CRYPTO_DATA = {
      { "BTC", "Bitcoin", "107356.60", "2140000000000", "13260000000" },
      { "ETH", "Ethereum", "2638.47", "318750000000", "9350000000" },
      { "USDT", "Tether", "1.00", "153010000000", "31500000000" },
      { "XRP", "XRP", "2.26", "132940000000", "926830000" },
      { "BNB", "BNB", "684.31", "98550000000", "461830000" },
      { "SOL", "Solana", "170.49", "88770000000", "2220000000" },
      { "USDC", "USDC", "1.00", "61370000000", "4980000000" },
      { "DOGE", "Dogecoin", "0.22", "32900000000", "859790000" },
      { "ADA", "Cardano", "0.74", "26380000000", "304620000" },
      { "STETH", "Lido Staked ETH", "2634.67", "23730000000", "8930000" },
      { "WBTC", "Wrapped Bitcoin", "107369.39", "13850000000", "96940000" },
      { "HYPE", "Hyperliquid", "34.49", "11570000000", "239100000" },
      { "LINK", "Chainlink", "15.48", "10180000000", "175310000" },
      { "AVAX", "Avalanche", "23.04", "9710000000", "194480000" },
      { "WETH", "Wrapped ETH", "2638.47", "8910000000", "547080000" },
      { "XLM", "Stellar", "0.28", "8810000000", "91290000" },
      { "TON", "Toncoin", "3.35", "8380000000", "569210000" },
      { "SHIB", "Shiba Inu", "0.000014", "8320000000", "83840000" },
      { "HBAR", "Hedera", "0.18", "7760000000", "52380000" },
      { "BTCB", "Bitcoin BEP2", "107281.09", "7010000000", "20830000" },
      { "DOT", "Polkadot", "4.51", "6870000000", "110420000" },
      { "PEPE", "Pepe", "0.000013", "5750000000", "825540000" },
      { "DAI", "Dai", "0.99", "5370000000", "57180000" },
      { "USDE", "Ethena USDe", "1.00", "5220000000", "30260000" },
      { "AAVE", "Aave", "264.11", "4000000000", "176990000" },
      { "NEAR", "Near Protocol", "2.80", "3410000000", "89760000" },
      { "OKB", "OKB", "51.95", "3120000000", "1910000" },
      { "ONDO", "Ondo", "0.92", "2930000000", "109190000" },
      { "CRO", "Cronos", "0.096", "2570000000", "12380000" },
      { "GT", "GateToken", "20.46", "2520000000", "10560000" },
      { "MNT", "Mantle", "0.72", "2440000000", "109680000" },
      { "VET", "VeChain", "0.026", "2320000000", "26540000" },
      { "RENDER", "Render", "4.37", "2260000000", "160830000" },
      { "FIL", "Filecoin", "2.82", "1900000000", "84930000" },
      { "ALGO", "Algorand", "0.21", "1860000000", "32050000" },
      { "ATOM", "Cosmos", "4.72", "1850000000", "69970000" },
      { "JUP", "Jupiter", "0.59", "1730000000", "45070000" },
      { "FDUSD", "First Digital USD", "0.99", "1620000000", "3950000000" },
      { "BONK", "Bonk", "0.000019", "1540000000", "113550000" }
  };

  private Random random = new Random();

  public List<Cryptocurrency> generateCryptocurrencies() {
    List<Cryptocurrency> cryptocurrencies = new ArrayList<>();

    for (int i = 0; i < CRYPTO_DATA.length; i++) {
      String[] data = CRYPTO_DATA[i];
      double basePrice = Double.parseDouble(data[2]);
      double marketCap = Double.parseDouble(data[3]);
      double volume = Double.parseDouble(data[4]);

      // Add some initial price variation
      double initialVariation = 0.95 + (random.nextDouble() * 0.1); // -5% to +5%
      double currentPrice = basePrice * initialVariation;

      Cryptocurrency crypto = new Cryptocurrency(
          data[0], // symbol
          data[1], // name
          currentPrice,
          marketCap,
          volume,
          i + 1 // rank
      );

      // Set additional fields
      crypto.setHigh24h(currentPrice * (1 + random.nextDouble() * 0.05));
      crypto.setLow24h(currentPrice * (1 - random.nextDouble() * 0.05));
      crypto.setCirculatingSupply((long) (marketCap / currentPrice));
      crypto.setTotalSupply((long) (crypto.getCirculatingSupply() * (1.1 + random.nextLong() * 0.3)));

      // Generate initial price history
      List<Double> history = new ArrayList<>();
      double historyPrice = currentPrice * 0.95;
      for (int j = 0; j < 20; j++) {
        historyPrice = historyPrice * (0.98 + random.nextDouble() * 0.04);
        history.add(historyPrice);
      }
      crypto.setPriceHistory(history);

      // Set initial 24h change
      double change24h = (currentPrice - basePrice) / basePrice * 100;
      crypto.setPriceChangePercentage24h(change24h);
      crypto.setPriceChange24h(currentPrice - basePrice);

      cryptocurrencies.add(crypto);
    }

    return cryptocurrencies;
  }

  public void updatePrices(List<Cryptocurrency> cryptocurrencies) {
    for (Cryptocurrency crypto : cryptocurrencies) {
      // Simulate price movements (-2% to +2% per update)
      double changePercent = -0.02 + (random.nextDouble() * 0.04);
      double currentPrice = crypto.getCurrentPrice();
      double newPrice = currentPrice * (1 + changePercent);

      // Ensure price doesn't go negative
      newPrice = Math.max(newPrice, 0.0001);

      crypto.setCurrentPrice(newPrice);

      // Update market cap based on new price
      double newMarketCap = newPrice * crypto.getCirculatingSupply();
      crypto.setMarketCap(newMarketCap);

      // Simulate volume changes
      double volumeChange = 0.95 + (random.nextDouble() * 0.1);
      crypto.setVolume24h(crypto.getVolume24h() * volumeChange);

      // Update high/low if necessary
      if (newPrice > crypto.getHigh24h()) {
        crypto.setHigh24h(newPrice);
      }
      if (newPrice < crypto.getLow24h()) {
        crypto.setLow24h(newPrice);
      }
    }
  }
}