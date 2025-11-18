---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: d4f256ba28ac621f2280bbd31575f6f1
---
Tämä opas käy läpi olemassa olevan Java Swing -sovelluksen modernisointia integroimalla se webforJ:iin käyttäen `WebswingConnector` -komponenttia. Opit tekemään perinteisestä työpöytäsovelluksesta verkkosovelluksen ja lisäämään vähitellen moderneja verkkotoimintoja, kuten verkkopohjaisia dialogeja ja interaktiivisia lomakkeita webforJ-komponenttien avulla.

:::tip Lähdekoodi
Tämän oppaan koko lähdekoodi on saatavilla GitHubissa: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/webswing/modernization-tutorial.mp4#t=5" type="video/mp4"/>
  </video>
</div>

## Tilannekuva

Kuvittele, että sinulla on asiakashallintasovellus, joka on rakennettu Swingillä ja ollut tuotannossa vuosia. Se toimii hyvin, mutta käyttäjät odottavat nyt verkkopääsyä ja modernia käyttöliittymää. Sen sijaan, että kirjoitat sovelluksen alusta alkaen uudelleen, käytät Webswingia tehdäksesi siitä heti verkkosovelluksen, ja lisäät vähitellen moderneja verkkotoimintoja, kuten verkkopohjaisia dialogeja ja lomakkeita webforJ-komponenttien avulla.

## Aloituskohta: Swing-sovellus

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
          // Käsittele kaksoisnapsautus muokkaamista varten
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

Tämä sovellus toimii täydellisesti työpöytäsovelluksena, mutta siitä puuttuu verkkopääsy. Käyttäjien on asennettava Java ja suoritettava JAR-tiedosto paikallisesti.

## Vaihe 1: tehdä siitä Webswing-tietoinen

Ensimmäinen askel on tehdä Swing-sovelluksesta tietoinen, onko se käynnissä Webswingin alla. Tämä mahdollistaa sen käyttäytymisen mukauttamisen ilman työpöytäsovelluksen yhteensopivuuden rikkoutumista.

### Webswing-ympäristön tunnistaminen

Lisää Webswing API -riippuvuus Swing-projektiisi:

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Muokkaa sitten sovellustasi tunnistamaan Webswing-suoritusympäristö:

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

Tässä on tärkeä oivallus: `WebswingUtil.getWebswingApi()` palauttaa `null`, kun se suoritetaan tavallisena työpöytäsovelluksena, mikä mahdollistaa kaksimuotisen yhteensopivuuden säilyttämisen.

### Käyttäytymisen mukauttaminen verkkosovellusta varten

Kun tunnistus on paikoillaan, voit nyt mukauttaa sovelluksen käyttäytymistä. Tärkein muutos on, miten käyttäjävuorovaikutuksia käsitellään:

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

Jakamalla käyttäytymistä `isWebswing`-arvon mukaan koodipohja voi käsitellä molempia ympäristöjä.

## Vaihe 2: luo webforJ-kääntö

Nyt kun Swing-sovellus voi kommunikoida tapahtumien kautta, luo webforJ-sovellus, joka upottaa Swing-sovelluksen ja lisää moderneja verkkotoimintoja, kuten verkkopohjaisia dialogeja ja lomakkeita.

### Liittimen määrittäminen

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

Liitin yhdistää Webswing-palvelimeesi, luoden kaksisuuntaisen viestintäkanavan.

### Tapahtumien käsittely Swingissä

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

Nyt käyttäjät näkevät nykyaikaisen web-lomakkeen, joka on rakennettu webforJ-komponenteilla sen sijaan, että käyttäisivät Swing-dialogia.

## Vaihe 3: kaksisuuntainen viestintä

Integraatio muuttuu tehokkaaksi, kun viestintä virtaa molempiin suuntiin. WebforJ-sovellus voi lähettää päivityksiä takaisin Swing-sovellukseen, pitäen molemmat käyttöliittymät synkronoituna.

### Päivitysten lähettäminen Swingille

Kun käyttäjä muokkaa asiakasta webforJ-dialogissa:

```java
dialog.onSave(() -> {
  // Lähetä päivitetty asiakas takaisin Swingiin
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Päivitysten käsittely Swingissä

Swing-sovellus kuuntelee näitä päivityksiä ja päivittää näyttöään:

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

## Arkkitehtuurin hyödyt

Tämä lähestymistapa tarjoaa useita etuja täydelliseen uudelleenkirjoitukseen verrattuna:

### Välitön verkkosovellus

Swing-sovelluksesi on heti verkkopääsyinen ilman koodimuutoksia. Käyttäjät voivat käyttää sitä selaimen kautta samalla, kun työskentelet parannusten parissa.

### Progressiivinen parannus

Aloita vaihtamalla vain muokkausdialogi, ja korvaa vähitellen lisää komponentteja:

1. **Vaihe 1**: Upota koko Swing-sovellus, vaihda vain muokkausdialogi
2. **Vaihe 2**: Lisää webforJ-navigointi ja valikot upotetun sovelluksen ympärille
3. **Vaihe 3**: Korvaa taulukko webforJ-taulukolla, pitäen Swingin korvaamattomille ominaisuuksille
4. **Vaihe 4**: Korvaa lopulta kaikki Swing-komponentit

### Riskien vähentäminen

Koska alkuperäinen Swing-sovellus pysyy toiminnassa, voit:

- Palata työpöytätoteutukseen, jos tarvitaan
- Testata uusia ominaisuuksia rinnakkain olemassa olevien kanssa
- Siirtää käyttäjiä vähitellen
- Säilyttää samat liiketoimintalogiikat
