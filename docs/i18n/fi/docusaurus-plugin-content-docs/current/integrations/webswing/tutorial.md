---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: 32805132a2cf7b320864275fbbae7889
---
Tämä opas käy läpi olemassa olevan Java Swing -sovelluksen modernisoimisen integroimalla se webforJ:hin käyttäen `WebswingConnector`-komponenttia. Opit, kuinka voit tehdä perinteisestä työpöytäsovelluksesta verkkopääsyn mahdollistavan ja lisätä vähitellen moderneja verkkotoimintoja, kuten verkkopohjaisia dialogeja ja interaktiivisia lomakkeita webforJ-komponenttien avulla.

:::tip Lähdekoodi
Täydellinen lähdekoodi tälle oppaalle on saatavilla GitHubissa: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

## Sknaario

Kuvittele, että sinulla on asiakashallintasovellus, joka on rakennettu Swingillä ja joka on ollut tuotannossa vuosia. Se toimii hyvin, mutta käyttäjät odottavat nyt verkkopääsyä ja modernia käyttöliittymää. Sen sijaan, että kirjoittaisit sen alusta alkaen uudelleen, käytät Webswingia tehdäksesi sen verkkopääsyiseksi välittömästi, ja lisäät vähitellen moderneja verkkotoimintoja, kuten verkkopohjaisia dialogeja ja lomakkeita webforJ-komponenttien avulla.

## Aloituskohta: Swing-sovellus

Esimerkki Swing-sovellus on asiakastaulukko, jossa on tyypilliset CRUD-toiminnot. Kuten monissa yrityksen Swing-sovelluksissa, se noudattaa vakiomalleja:

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
          // Käsittele kaksoisnapsautus muokkausta varten
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

Tämä sovellus toimii täydellisesti työpöytäsovelluksena, mutta siinä ei ole verkkopääsyä. Käyttäjien on asennettava Java ja suoritettava JAR-tiedosto paikallisesti.

## Vaihe 1: tehdä siitä Webswing-tietoista

Ensimmäinen askel on tehdä Swing-sovelluksesta tietoinen siitä, onko se käynnissä Webswingin alla. Tämä mahdollistaa sovelluksen käyttäytymisen mukauttamisen ilman työpöytäyhteensopivuuden rikkomista.

### Webswing-ympäristön havaitseminen

Lisää Webswing API -riippuvuus Swing-projektiisi:

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Muuta sitten sovellustasi havaitsemaan Webswing-suoritusaika:

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

Keskeinen oivallus tässä on se, että `WebswingUtil.getWebswingApi()` palauttaa `null`, kun sovellus käynnistetään tavallisena työpöytäsovelluksena, mikä mahdollistaa kaksimuotoisen yhteensopivuuden ylläpitämisen.

### Käyttäytymisen mukauttaminen verkkotoimitusta varten

Havaitsemisen ollessa paikoillaan voit nyt mukauttaa sovelluksen käyttäytymistä. Tärkein muutos on se, kuinka käyttäjävuorovaikutuksia käsitellään:

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

Käyttäytymistä haaroittamalla `isWebswing`-arvon mukaan koodipohja voi käsitellä molempia ympäristöjä.

## Vaihe 2: luoda webforJ-käärin

Nyt kun Swing-sovellus voi kommunikoida tapahtumien kautta, luo webforJ-sovellus, joka upottaa Swing-sovelluksen ja lisää moderneja verkkotoimintoja, kuten verkkopohjaisia dialogeja ja lomakkeita.

### Yhteyden asentaminen

`WebswingConnector`-komponentti upottaa Webswingä -hostatun sovelluksesi webforJ-näkymään:

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

Yhteys yhdistää Webswing-palvelimeesi ja luo kaksisuuntaisen viestintäkanavan.

### Tapahtumien käsittely Swingistä

Kun Swing-sovellus lähettää tapahtumia (kuten kun käyttäjä kaksoisnapsauttaa riviä), yhdistin vastaanottaa ne:

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

Nyt käyttäjät näkevät moderneja verkkolomakkeita webforJ-komponenttien avulla sen sijaan, että käyttäisivät Swing-dialogia.

## Vaihe 3: kaksisuuntainen viestintä

Integraatio muuttuu tehokkaaksi, kun viestintä virtaa molempiin suuntiin. WebforJ-sovellus voi lähettää päivityksiä takaisin Swing-sovellukseen, pitäen molemmat käyttöliittymät synkronoituna.

### Päivitysten lähettäminen Swingille

Kun käyttäjä muokkaa asiakasta webforJ-dialogissa:

```java
dialog.onSave(() -> {
  // Lähetä päivitetty asiakas takaisin Swingille
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Päivitysten käsittely Swingissä

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

## Arkkitehtuurin edut

Tämä lähestymistapa tarjoaa useita etuja verrattuna täydelliseen uudelleenkirjoitukseen:

### Välitön verkkotoimitus

Swing-sovelluksesi tulee verkkopääsyiseksi heti ilman koodimuutoksia. Käyttäjät voivat käyttää sitä selaimen kautta samalla, kun työskentelet parannusten parissa.

### Progressiivinen parannus

Aloita vain muokkausdiyalgin korvaaminen, ja vaihda sitten vähitellen enemmän komponentteja:

1. **Vaihe 1**: Upota koko Swing-sovellus, vaihda vain muokkausdiyalgi
2. **Vaihe 2**: Lisää webforJ-navigointi ja valikot upotetun sovelluksen ympärille
3. **Vaihe 3**: Korvata taulukko webforJ-taulukolla, pitäen Swing erottamattomista ominaisuuksista
4. **Vaihe 4**: Korvata lopulta kaikki Swing-komponentit

### Riskien vähentäminen

Koska alkuperäinen Swing-sovellus pysyy toiminnallisena, voit:

- Palautua työpöytätoteutukseen tarvittaessa
- Testata uusia ominaisuuksia rinnakkain olemassa olevien kanssa
- Siirtää käyttäjiä vähitellen
- Säilyttää saman liiketoimintalogiikan
