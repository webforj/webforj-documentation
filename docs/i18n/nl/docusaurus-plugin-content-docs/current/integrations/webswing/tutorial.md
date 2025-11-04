---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: d4f256ba28ac621f2280bbd31575f6f1
---
Deze tutorial leidt je door het moderniseren van een bestaande Java Swing-app door deze te integreren met webforJ met behulp van de `WebswingConnector`. Je leert hoe je een traditionele desktop-app web-toegankelijk maakt en geleidelijk moderne webfuncties toevoegt, zoals web-gebaseerde dialoogvensters en interactieve formulieren met webforJ-componenten.

:::tip Broncodering
De volledige broncode voor deze tutorial is beschikbaar op GitHub: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/webswing/modernization-tutorial.mp4#t=5" type="video/mp4"/>
  </video>
</div>

## Het scenario

Stel je voor dat je een klantenbeheertoepassing hebt gebouwd met Swing die al jaren in productie is. Het werkt goed, maar gebruikers verwachten nu webtoegang en een moderne interface. In plaats van alles opnieuw te schrijven, gebruik je Webswing om het direct web-toegankelijk te maken en voeg je geleidelijk moderne webfuncties toe, zoals web-gebaseerde dialoogvensters en formulieren met webforJ-componenten.

## Startpunt: de Swing-app

De voorbeeld Swing-app is een klanten tabel met typische CRUD-operaties. Zoals veel enterprise Swing-apps volgt het standaard patronen:

```java
public class Application {
  private List<Customer> customers;
  private DefaultTableModel model;
  private JTable table;

  private void createTable() {
    String[] columnNames = { "Naam", "Bedrijf", "E-mail" };
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
          // Verwerk dubbelklik om te bewerken
        }
      }
    });
  }

  private void showEditDialog(Customer customer) {
    JTextField nameField = new JTextField(customer.getName());
    JTextField companyField = new JTextField(customer.getCompany());
    JTextField emailField = new JTextField(customer.getEmail());

    Object[] fields = {
        "Naam:", nameField,
        "Bedrijf:", companyField,
        "E-mail:", emailField
    };

    int result = JOptionPane.showConfirmDialog(null, fields, "Bewerk Klant",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
  }
}
```

Deze app werkt perfect als desktopapp, maar mist webtoegang. Gebruikers moeten Java installeren en het JAR-bestand lokaal uitvoeren.

## Stap 1: maak het Webswing-compatibel

De eerste stap is om de Swing-app te laten detecteren of deze draait onder Webswing. Dit stelt het in staat om zijn gedrag aan te passen zonder desktopcompatibiliteit te breken.

### Het Webswing-omgeving detecteren

Voeg de Webswing API-afhankelijkheid toe aan je Swing-project:

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Pas vervolgens je app aan om de Webswing-runtime te detecteren:

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

De belangrijke inzicht hier is dat `WebswingUtil.getWebswingApi()` `null` retourneert wanneer deze draait als een reguliere desktop-app, waardoor je dubbele compatibiliteit kunt behouden.

### Gedrag aanpassen voor webimplementatie

Met detectie op zijn plaats, kun je nu het gedrag van de app aanpassen. De belangrijkste wijziging betreft hoe gebruikersinteracties worden afgehandeld:

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

Door het gedrag te splitsen op basis van de waarde van `isWebswing`, kan de codebase beide omgevingen aan.

## Stap 2: maak de webforJ-wrapper

Nu de Swing-app kan communiceren via evenementen, maak je een webforJ-app die de Swing-app inbedt en moderne webfuncties toevoegt, zoals web-gebaseerde dialoogvensters en formulieren.

### De connector instellen

De `WebswingConnector`-component integreert je Webswing-gehoste app binnen een webforJ-weergave:

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

De connector maakt verbinding met je Webswing-server en stelt een bidirectioneel communicatiokanaal in.

### Evenementen van Swing verwerken

Wanneer de Swing-app evenementen verzendt (zoals wanneer een gebruiker dubbelklikt op een rij), ontvangt de connector deze:

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

Nu zien gebruikers in plaats van het Swing-dialoogvenster een modern webformulier dat is gebouwd met webforJ-componenten.

## Stap 3: bidirectionele communicatie

De integratie wordt krachtiger wanneer de communicatie in beide richtingen plaatsvindt. De webforJ-app kan updates terugsturen naar de Swing-app, waardoor beide UI's gesynchroniseerd blijven.

### Updates naar Swing verzenden

Nadat de gebruiker een klant in het webforJ-dialoogvenster heeft bewerkt:

```java
dialog.onSave(() -> {
  // Stuur bijgewerkte klant terug naar Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Updates in Swing verwerken

De Swing-app luistert naar deze updates en ververst zijn weergave:

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

## Architectuurvoordelen

Deze aanpak biedt verschillende voordelen ten opzichte van een volledige herschrijving:

### Directe webimplementatie

Je Swing-app wordt onmiddellijk web-toegankelijk zonder codewijzigingen. Gebruikers kunnen deze via een browser openen terwijl je werkt aan verbeteringen.

### Progressieve verbetering

Begin met alleen het bewerkingsdialoogvenster te vervangen en vervang geleidelijk meer componenten:

1. **Fase 1**: Integreer de volledige Swing-app, vervang alleen het bewerkingsdialoogvenster
2. **Fase 2**: Voeg webforJ-navigatie en menu's toe rond de ingebedde app
3. **Fase 3**: Vervang de tabel door een webforJ-tabel, houd Swing voor onvervangbare functies
4. **Fase 4**: Vervang uiteindelijk alle Swing-componenten

### Risicobeperking

Aangezien de oorspronkelijke Swing-app functioneel blijft, kun je:

- Terugvallen op desktopimplementatie indien nodig
- Nieuwe functies naast bestaande testen
- Gebruikers geleidelijk migreren
- Dezelfde bedrijfslogica behouden
