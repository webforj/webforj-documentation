---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: 97df9e800c5792a1ff22fb6e0e9a33e9
---
Tämä opas käy läpi olemassa olevan Java Swing -sovelluksen modernisoinnin integroimalla se webforJ:hen `WebswingConnector`in avulla. Opit, miten perinteisestä työpöytäsovelluksesta tehdään web-pohjainen, ja lisätään vähitellen moderneja verkkotoimintoja, kuten web-pohjaisia dialogeja ja interaktiivisia lomakkeita webforJ-komponenttien avulla.

:::note Ehdot
Ennen tämän oppaan aloittamista, suorita [Asetus ja konfigurointi](./setup) -vaiheet Webswing-palvelimen ja CORS-asetusten konfiguroimiseksi.
:::

:::tip Lähdekoodi
Tämän oppaan täydellinen lähdekoodi on saatavilla GitHubissa: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/webswing/modernization-tutorial.mp4#t=5" type="video/mp4"/>
  </video>
</div>

## Tilannekuva {#the-scenario}

Kuvittele, että sinulla on asiakkuudenhallintasovellus, joka on rakennettu Swingillä ja ollut tuotannossa vuosia. Se toimii hyvin, mutta käyttäjät odottavat nyt verkkopääsyä ja modernia käyttöliittymää. Sen sijaan, että kirjoittaisit sovelluksen kokonaan uusiksi, hyödynnät Webswingiä tehdäksesi siitä heti verkkopohjaisen ja lisäät vähitellen moderneja web-toimintoja, kuten web-pohjaisia dialogeja ja lomakkeita webforJ-komponenttien avulla.

## Lähtöpiste: Swing-sovellus {#starting-point-the-swing-app}

Esimerkkiswing-sovellus on asiakastaulukko, jossa on tyypilliset CRUD-toiminnot. Kuten monet yritysten Swing-sovellukset, se noudattaa vakiomalleja:

```java
public class Application {
  private List<Customer> customers;
  private DefaultTableModel model;
  private JTable table;

  private void createTable() {
    String[] columnNames = { "Nimi", "Yritys", "Sähköposti" };
    model = new DefaultTableModel(columnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    table = new JTable(model);
    table.setRowHeight(30);
    table.setRowSelectionAllowed(true);

    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          // Käsittele kaksoisnapsautus muokkaamiseen
        }
      }
    });
  }

  private void showEditDialog(Customer customer) {
    JTextField nameField = new JTextField(customer.getName());
    JTextField companyField = new JTextField(customer.getCompany());
    JTextField emailField = new JTextField(customer.getEmail());

    Object[] fields = {
        "Nimi:", nameField,
        "Yritys:", companyField,
        "Sähköposti:", emailField
    };

    int result = JOptionPane.showConfirmDialog(null, fields, "Muokkaa asiakasta",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
  }
}
```

Tämä sovellus toimii erinomaisesti työpöytäsovelluksena, mutta sillä ei ole verkkopääsyä. Käyttäjien on asennettava Java ja suoritettava JAR-tiedosto paikallisesti.

## Vaihe 1: Webswing-tietoisuuden lisääminen {#step-1-making-it-webswing-aware}

Ensimmäinen askel on tehdä Swing-sovelluksesta tietoinen siitä, onko se käynnissä Webswingissä. Tämä mahdollistaa sen käyttäytymisen mukauttamisen ilman työpöytäyhteensopivuuden rikkomista.

### Webswing-ympäristön tunnistaminen {#detecting-the-webswing-environment}

Lisää Webswing API -riippuvuus Swing-projektiisi:

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Muokkaa sitten sovellustasi tunnistamaan Webswing-aika:

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

Keskeinen oivallus on, että `WebswingUtil.getWebswingApi()` palauttaa `null`, kun sovellus toimii tavallisena työpöytäsovelluksena, mikä mahdollistaa kaksitaitoisen yhteensopivuuden ylläpitämisen.

### Käyttäytymisen mukauttaminen verkkodeploytausta varten {#adapting-behavior-for-web-deployment}

Kun tunnistus on paikallaan, voit nyt mukauttaa sovelluksen käyttäytymistä. Tärkein muutos on se, miten käyttäjäinteraktiot käsitellään:

```java
private void handleDoubleClick(MouseEvent e) {
  int row = table.rowAtPoint(e.getPoint());
  if (row >= 0 && row < customers.size()) {
    Customer customer = customers.get(row);

    if (isWebswing) {
      api.sendActionEvent("select-customer", gson.toJson(customer), null);
    } else {
      showEditDialog(customer);
    }
  }
}
```

Jakamalla käyttäytymistä `isWebswing`-arvon mukaan, koodipohja voi käsitellä molempia ympäristöjä.

## Vaihe 2: webforJ-kääreen luominen {#step-2-creating-the-webforj-wrapper}

Nyt kun Swing-sovellus voi kommunikoida tapahtumien kautta, luodaan webforJ-sovellus, joka upottaa Swing-sovelluksen ja lisää moderneja web-toimintoja, kuten web-pohjaisia dialogeja ja lomakkeita.

### Yhteyden muodostamisen asetukset {#setting-up-the-connector}

`WebswingConnector`-komponentti upottaa Webswingissä isännöidyn sovelluksesi webforJ-näkymään:

```java
@Route("/")
public class CustomerTableView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public CustomerTableView(@Value("${webswing.connector.url}") String webswingUrl) {
    WebswingConnector connector = new WebswingConnector(webswingUrl);
    connector.setSize("100vw", "100vh");

    self.add(connector);
  }
}
```

Liitin yhdistää Webswing-palvelimeesi ja luo kaksisuuntaisen viestintäkanavan.

### Tapahtumien käsittely Swingistä {#handling-events-from-swing}

Kun Swing-sovellus lähettää tapahtumia (kuten kun käyttäjä kaksoisnapsauttaa riviä), liitin vastaanottaa ne:

```java
connector.onAction(event -> {
  switch (event.getActionName()) {
    case "select-customer":
      event.getActionData().ifPresent(data -> {
        JsonObject customer = JsonParser.parseString(data).getAsJsonObject();
        CustomerForm dialog = new CustomerForm(customer);
        self.add(dialog);
        dialog.onSave(() -> {
          Gson gson = new Gson();
          connector.performAction("update-customer", gson.toJson(customer));
        });
      });
      break;
  }
});
```

Nyt käyttäjät näkevät modernin web-lomakkeen, joka on rakennettu webforJ-komponenteilla, sen sijaan että he näkisivät Swing-dioja.

## Vaihe 3: kaksisuuntainen viestintä {#step-3-bidirectional-communication}

Integraatiosta tulee voimakas, kun viestintä tapahtuu molempiin suuntiin. WebforJ-sovellus voi lähettää päivityksiä takaisin Swing-sovellukseen, pitäen molemmat käyttöliittymät synkronoituna.

### Päivitysten lähettäminen Swingiin {#sending-updates-to-swing}

Kun käyttäjä muokkaa asiakasta webforJ-dialogissa:

```java
dialog.onSave(() -> {
  // Lähetä päivitetty asiakas takaisin Swingiin
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Päivitysten käsittely Swingissä {#processing-updates-in-swing}

Swing-sovellus kuuntelee näitä päivityksiä ja päivittää näyttönsä:

```java
private void setupWebswingListeners() {
  api.addBrowserActionListener(event -> {
    if ("update-customer".equals(event.getActionName())) {
      Customer updated = gson.fromJson(event.getData(), Customer.class);
      updateCustomer(updated);
    }
  });
}
```

## Arkkitehtuurin hyödyt {#architecture-benefits}

Tämä lähestymistapa tarjoaa useita etuja verrattuna täysin uusiksi kirjoittamiseen:

### Välitön verkkodeploy {#immediate-web-deployment}

Swing-sovelluksesi on heti verkkopohjainen ilman koodimuutoksia. Käyttäjät voivat käyttää sitä selaimen kautta, kun työskentelet parannusten parissa.

### Vähittäinen parantaminen {#progressive-enhancement}

Aloita vain muokkausdialogin korvaamisesta, ja vaihda sitten vähitellen lisää komponentteja:

1. **Vaihe 1**: Upota koko Swing-sovellus, korvaten vain muokkausdialogi
2. **Vaihe 2**: Lisää webforJ-navigointi ja valikot upotetun sovelluksen ympärille
3. **Vaihe 3**: Korvaa taulukko webforJ-taulukolla, pitäen Swingin korvattavissa ominaisuuksissa
4. **Vaihe 4**: Lopulta korvata kaikki Swing-komponentit

### Riskien vähentäminen {#risk-mitigation}

Koska alkuperäinen Swing-sovellus pysyy toiminnassa, voit:

- Palata työpöytädeployantoon tarvittaessa
- Testata uusia ominaisuuksia olemassa olevien rinnalla
- Siirtää käyttäjiä vähitellen
- Säilyttää saman liiketoimintalogiikan
