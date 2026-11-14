---
title: Modernisointitutoriaali
sidebar_position: 4
description: >-
  Modernize a Swing customer management app step by step by embedding it with
  WebswingConnector and layering in webforJ dialogs and forms.
_i18n_hash: ded3a5a3abe3905086532c58861652f8
---
Tässä opetusohjelmassa käydään läpi olemassa olevan Java Swing -sovelluksen modernisointi integroimalla se webforJ:hin `WebswingConnector`-komponentin avulla. Opit tekemään perinteisestä työpöytäsovelluksesta verkkokäyttöisen ja lisäämään siihen vähitellen moderneja verkkotoimintoja, kuten verkkopohjaisia valintaikkunoita ja vuorovaikutteisia lomakkeita webforJ-komponenttien avulla.

:::note Edellytykset
Ennen tämän opetusohjelman aloittamista suorita [Asennus ja määritys](./setup) -vaiheet Webswing-palvelimen ja CORS-asetusten määrittämiseksi.
:::

:::tip Lähdekoodi
Tämän opetusohjelman koko lähdekoodi on saatavilla GitHubissa: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<VideoPlayer video="webswing/modernization-tutorial" width={1920} height={1080} title="Modernization tutorial" />

## Skenaario {#the-scenario}

Kuvittele, että sinulla on Swingillä rakennettu asiakashallintasovellus, joka on ollut tuotantokäytössä jo vuosia. Se toimii hyvin, mutta käyttäjät odottavat nyt verkkokäyttöä ja modernia käyttöliittymää. Sen sijaan, että kirjoittaisit kaiken alusta alkaen, voit tehdä sovelluksesta heti verkkokäyttöisen Webswingin avulla ja lisätä siihen sitten vähitellen moderneja verkkotoimintoja, kuten verkkopohjaisia valintaikkunoita ja lomakkeita webforJ-komponenttien avulla.

## Lähtötilanne: Swing-sovellus {#starting-point-the-swing-app}

Esimerkin Swing-sovellus on asiakastaulukko, jossa on tavanomaiset CRUD-toiminnot. Kuten monet yrityskäyttöön tarkoitetut Swing-sovellukset, se noudattaa vakiintuneita käytäntöjä:

```java
public class Application {
  private List<Customer> customers;
  private DefaultTableModel model;
  private JTable table;

  private void createTable() {
    String[] columnNames = { "Name", "Company", "Email" };
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
          // Handle double-click to edit
        }
      }
    });
  }

  private void showEditDialog(Customer customer) {
    JTextField nameField = new JTextField(customer.getName());
    JTextField companyField = new JTextField(customer.getCompany());
    JTextField emailField = new JTextField(customer.getEmail());

    Object[] fields = {
        "Name:", nameField,
        "Company:", companyField,
        "Email:", emailField
    };

    int result = JOptionPane.showConfirmDialog(null, fields, "Edit Customer",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
  }
}
```

Sovellus toimii työpöytäsovelluksena moitteettomasti, mutta siitä puuttuu verkkokäyttömahdollisuus. Käyttäjien on asennettava Java ja suoritettava JAR-tiedosto paikallisesti.

## Vaihe 1: Webswing-tuen lisääminen {#step-1-making-it-webswing-aware}

Ensimmäiseksi Swing-sovellus saadaan tunnistamaan, suoritetaanko sitä Webswingin alla. Näin se voi mukauttaa toimintaansa vaarantamatta yhteensopivuutta työpöytäympäristön kanssa.

### Webswing-ympäristön tunnistaminen {#detecting-the-webswing-environment}

Lisää Webswing API -riippuvuus Swing-projektiisi:

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Muokkaa sitten sovellusta tunnistamaan Webswing-ajonaikainen ympäristö:

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

Tärkeää on, että `WebswingUtil.getWebswingApi()` palauttaa arvon `null`, kun sovellusta suoritetaan tavallisena työpöytäsovelluksena. Näin sovellus voi toimia molemmissa ympäristöissä.

### Toiminnan mukauttaminen verkkokäyttöön {#adapting-behavior-for-web-deployment}

Kun ympäristön tunnistus on käytössä, voit mukauttaa sovelluksen toimintaa. Tärkein muutos koskee käyttäjän vuorovaikutuksen käsittelyä:

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

Kun toiminta valitaan `isWebswing`-arvon perusteella, koodipohja toimii molemmissa ympäristöissä.

## Vaihe 2: webforJ-kääreen luominen {#step-2-creating-the-webforj-wrapper}

Nyt kun Swing-sovellus pystyy viestimään tapahtumien kautta, luodaan webforJ-sovellus, joka upottaa Swing-sovelluksen ja lisää siihen moderneja verkkotoimintoja, kuten verkkopohjaisia valintaikkunoita ja lomakkeita.

### Liittimen määrittäminen {#setting-up-the-connector}

`WebswingConnector`-komponentti upottaa Webswing-palvelimella isännöidyn sovelluksen webforJ-näkymään:

```java
@Route("/")
public class CustomerTableView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public CustomerTableView(@Value("${webswing.connector.url}") String webswingUrl) {
    WebswingConnector connector = new WebswingConnector(webswingUrl);
    connector.setSize("100vw", "100vh");

    self.add(connector);
  }
}
```

Liitin muodostaa yhteyden Webswing-palvelimeen ja luo kaksisuuntaisen viestintäkanavan.

### Swing-sovelluksen tapahtumien käsittely {#handling-events-from-swing}

Kun Swing-sovellus lähettää tapahtumia, esimerkiksi kun käyttäjä kaksoisnapsauttaa riviä, liitin vastaanottaa ne:

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

Swing-valintaikkunan sijaan käyttäjät näkevät nyt webforJ-komponenteilla toteutetun modernin verkkolomakkeen.

## Vaihe 3: kaksisuuntainen viestintä {#step-3-bidirectional-communication}

Integroinnista tulee tehokas, kun viestintä toimii molempiin suuntiin. webforJ-sovellus voi lähettää päivityksiä takaisin Swing-sovellukseen, jolloin molempien käyttöliittymien tiedot pysyvät synkronoituina.

### Päivitysten lähettäminen Swing-sovellukseen {#sending-updates-to-swing}

Kun käyttäjä on muokannut asiakastietoja webforJ-valintaikkunassa:

```java
dialog.onSave(() -> {
  // Send updated customer back to Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Päivitysten käsittely Swing-sovelluksessa {#processing-updates-in-swing}

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

## Arkkitehtuurin edut {#architecture-benefits}

Tällä lähestymistavalla on useita etuja täydelliseen uudelleenkirjoitukseen verrattuna:

### Verkkokäyttöönotto heti {#immediate-web-deployment}

Swing-sovelluksestasi tulee heti verkkokäyttöinen ilman koodimuutoksia. Käyttäjät voivat käyttää sitä selaimella samalla, kun kehität siihen parannuksia.

### Vaiheittainen parantaminen {#progressive-enhancement}

Aloita korvaamalla vain muokkausvalintaikkuna ja korvaa sitten vähitellen lisää komponentteja:

1. **Vaihe 1**: Upota koko Swing-sovellus ja korvaa vain muokkausvalintaikkuna
2. **Vaihe 2**: Lisää webforJ-navigointi ja -valikot upotetun sovelluksen ympärille
3. **Vaihe 3**: Korvaa taulukko webforJ-taulukolla ja säilytä Swing ominaisuuksille, joita ei voi korvata
4. **Vaihe 4**: Korvaa lopulta kaikki Swing-komponentit

### Riskien vähentäminen {#risk-mitigation}

Koska alkuperäinen Swing-sovellus toimii edelleen, voit:

- Palata tarvittaessa työpöytäkäyttöönottoon
- Testata uusia ominaisuuksia vanhojen rinnalla
- Siirtää käyttäjät vaiheittain
- Säilyttää saman liiketoimintalogiikan
