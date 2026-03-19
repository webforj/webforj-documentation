---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: b938ea9adf24f0f2624f22a1a012d0cd
---
Tämä opas käy läpi olemassa olevan Java Swing -sovelluksen modernisoinnin integroimalla se webforJ:hin käyttäen `WebswingConnector`-komponenttia. Opit, kuinka perinteisestä työpöytäsovelluksesta tehdään verkko- käyttöliittymä ja lisätään vähitellen nykyaikaisia verkkotoimintoja, kuten verkkopohjaisia dialogeja ja interaktiivisia lomakkeita webforJ-komponenttien avulla.

:::note Esivaatimukset
Ennen tämän oppaan aloittamista, suorita [Asennus ja konfiguraatio](./setup) -vaiheet Webswing-palvelimen ja CORS-asetusten konfiguroimiseksi.
:::

:::tip Lähdekoodi
Koko tämän oppaan lähdekoodi on saatavilla GitHubissa: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/webswing/modernization-tutorial.mp4#t=5" type="video/mp4"/>
  </video>
</div>

## Tilanne {#the-scenario}

Kuvittele, että sinulla on asiakashallintasovellus, joka on rakennettu Swingillä ja ollut tuotannossa vuosia. Se toimii hyvin, mutta käyttäjät odottavat nyt verkkoyhteyttä ja nykyaikaista käyttöliittymää. Sen sijaan, että kirjoittaisit kaiken alusta alkaen, käytät Webswingia tehdäkseen sen heti verkkoyhteensopivaksi, ja lisäät vähitellen nykyaikaisia verkkotoimintoja, kuten verkkopohjaisia dialogeja ja lomakkeita webforJ-komponetteja käyttäen.

## Lähtökohta: Swing-sovellus {#starting-point-the-swing-app}

Esimerkkiswing-sovellus on asiakastietojen taulukko, jossa on tyypilliset CRUD-toiminnot. Kuten monissa yrityksissä käytettävissä Swing-sovelluksissa, se noudattaa tavallisia malleja:

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
          // Käsittele kaksoisnapsautus muokataksesi
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

Tämä sovellus toimii täydellisesti työpöytäsovelluksena, mutta siinä ei ole verkkoyhteyttä. Käyttäjien on asennettava Java ja suoritettava JAR-tiedosto paikallisesti.

## Vaihe 1: tee siitä Webswing-tietoista {#step-1-making-it-webswing-aware}

Ensimmäinen vaihe on tehdä Swing-sovelluksesta tietoinen siitä, onko se käynnissä Webswingin alla. Tämä sallii sen mukauttaa käyttäytymistään rikkomatta työpöytäsovelluksen yhteensopivuutta.

### Webswing-ympäristön havaitseminen {#detecting-the-webswing-environment}

Lisää Webswingin API-riippuvuus Swing-projektiisi:

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Muokkaa sitten sovellustasi havaitsemaan Webswing-aika:

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

Keskeinen oivallus tässä on, että `WebswingUtil.getWebswingApi()` palauttaa `null`, kun se on käynnissä tavallisena työpöytäsovelluksena, mikä mahdollistaa kaksitaitoisen yhteensopivuuden säilyttämisen.

### Käyttäytymisen mukauttaminen verkkototeutusta varten {#adapting-behavior-for-web-deployment}

Havainnon jälkeen voit nyt mukauttaa sovelluksen käyttäytymistä. Tärkein muutos on, kuinka käyttäjäinteraktiot käsitellään:

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

Jakamalla käyttäytymistä `isWebswing`-arvon mukaan, koodiperusta voi käsitellä molempia ympäristöjä.

## Vaihe 2: luo webforJ-kääntäjä {#step-2-creating-the-webforj-wrapper}

Nyt kun Swing-sovellus voi kommunikoida tapahtumien kautta, luo webforJ-sovellus, joka upottaa Swing-sovelluksen ja lisää nykyaikaisia verkkotoimintoja, kuten verkkopohjaisia dialogeja ja lomakkeita.

### Yhdistäjän määrittäminen {#setting-up-the-connector}

`WebswingConnector`-komponentti upottaa Webswingiin isännöidyn sovelluksesi webforJ-näkymään:

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

Yhdistäjä yhdistää Webswing-palvelimeesi, luoden kaksisuuntaisen viestintäkanavan.

### Tapahtumien käsittely Swingistä {#handling-events-from-swing}

Kun Swing-sovellus lähettää tapahtumia (kuten kun käyttäjä kaksoisnapsauttaa riviä), yhdistäjä vastaanottaa ne:

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

Nyt käyttäjät näkevät nykyaikaisen verkkolomakkeen, joka on rakennettu webforJ-komponenteilla, eikä Swing-dialogia.

## Vaihe 3: kaksisuuntainen kommunikaatio {#step-3-bidirectional-communication}

Integraatiosta tulee tehokasta, kun viestintä virtaa molempiin suuntiin. WebforJ-sovellus voi lähettää päivityksiä takaisin Swing-sovellukseen, pitäen molemmat käyttöliittymät synkronoituna.

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

Tämä lähestymistapa tarjoaa useita etuja verrattuna täydelliseen uudelleenkirjoittamiseen:

### Välitön verkkodeployointi {#immediate-web-deployment}

Swing-sovelluksestasi tulee verkkoyhteensopiva heti ilman koodimuutoksia. Käyttäjät voivat käyttää sitä selaimen kautta samalla kun työskentelet parannusten parissa.

### Progressiivinen parannus {#progressive-enhancement}

Aloita vain muokkausdialogin korvaamisesta, ja vaihda vähitellen lisää komponentteja:

1. **Vaihe 1**: Upota koko Swing-sovellus, vaihda vain muokkausdialogi
2. **Vaihe 2**: Lisää webforJ-navigaatio ja valikot upotetun sovelluksen ympärille
3. **Vaihe 3**: Korvaa taulukko webforJ-taulukolla, pidä Swing välttämättömissä ominaisuuksissa
4. **Vaihe 4**: Lopulta korvaa kaikki Swing-komponentit

### Riskin vähentäminen {#risk-mitigation}

Koska alkuperäinen Swing-sovellus pysyy toiminnallisena, voit:

- Palata työpöytädeployointiin tarvittaessa
- Testata uusia ominaisuuksia olemassa olevien rinnalla
- Siirtää käyttäviä vähitellen
- Säilyttää saman liiketoimintalogiikan
