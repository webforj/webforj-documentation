---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: b938ea9adf24f0f2624f22a1a012d0cd
---
Deze tutorial doorloopt het moderniseren van een bestaande Java Swing-app door deze te integreren met webforJ met behulp van de `WebswingConnector`. Je leert hoe je een traditionele desktop-app webtoegankelijk maakt en geleidelijk moderne webfunctionaliteiten toevoegt, zoals webgebaseerde dialogen en interactieve formulieren met webforJ-componenten.

:::note Vereisten
Voordat je aan deze tutorial begint, voltooi je de stappen voor [Setup en Configuratie](./setup) om je Webswing-server en CORS-instellingen te configureren.
:::

:::tip Bronnen
De volledige broncode voor deze tutorial is beschikbaar op GitHub: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/webswing/modernization-tutorial.mp4#t=5" type="video/mp4"/>
  </video>
</div>

## Het scenario {#the-scenario}

Stel je voor dat je een klantbeheertoepassing hebt gebouwd met Swing die al jaren in productie is. Het werkt goed, maar gebruikers verwachten nu webtoegang en een moderne interface. In plaats van helemaal opnieuw te schrijven, gebruik je Webswing om het onmiddellijk webtoegankelijk te maken en voeg je geleidelijk moderne webfunctionaliteiten toe, zoals webgebaseerde dialogen en formulieren met webforJ-componenten.

## Startpunt: de Swing-app {#starting-point-the-swing-app}

De voorbeeld Swing-app is een klantenlijst met typische CRUD-bewerkingen. Zoals veel enterprise Swing-apps volgt deze standaard patronen:

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
          // Behandel dubbelklik om te bewerken
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

    int result = JOptionPane.showConfirmDialog(null, fields, "Klant Bewerken",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
  }
}
```

Deze app werkt perfect als desktop-app, maar mist webtoegang. Gebruikers moeten Java installeren en het JAR-bestand lokaal uitvoeren.

## Stap 1: het webforJ-bewust maken {#step-1-making-it-webswing-aware}

De eerste stap is ervoor zorgen dat de Swing-app detecteert of deze draait onder Webswing. Dit stelt het in staat om zijn gedrag aan te passen zonder de desktopcompatibiliteit te beschadigen.

### Detecteren van de Webswing-omgeving {#detecting-the-webswing-environment}

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

De belangrijkste inzichten hier is dat `WebswingUtil.getWebswingApi()` `null` retourneert wanneer het draait als een gewone desktop-app, waardoor je dubbele moduscompatibiliteit kunt behouden.

### Gedrag aanpassen voor webimplementatie {#adapting-behavior-for-web-deployment}

Met de detectie op zijn plaats, kun je nu het gedrag van de app aanpassen. De belangrijkste verandering is hoe gebruikersinteracties worden afgehandeld:

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

Door het gedrag te vertakken op basis van de waarde van `isWebswing` kan de codebase beide omgevingen aan.

## Stap 2: de webforJ-wrapper maken {#step-2-creating-the-webforj-wrapper}

Nu de Swing-app via evenementen kan communiceren, maak je een webforJ-app die de Swing-app inbedt en moderne webfunctionaliteiten toevoegt, zoals webgebaseerde dialogen en formulieren.

### De connector instellen {#setting-up-the-connector}

De `WebswingConnector`-component embed jouw Webswing-gehoste app binnen een webforJ-weergave:

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

De connector maakt verbinding met jouw Webswing-server en stelt een bidirectioneel communicatiekanaal tot stand.

### Evenementen van Swing afhandelen {#handling-events-from-swing}

Wanneer de Swing-app evenementen verstuurt (zoals wanneer een gebruiker op een rij dubbelklikt), ontvangt de connector ze:

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

Nu zien gebruikers in plaats van de Swing-dialoog een moderne webformulier gebouwd met webforJ-componenten.

## Stap 3: bidirectionele communicatie {#step-3-bidirectional-communication}

De integratie wordt krachtig wanneer communicatie in beide richtingen stroomt. De webforJ-app kan updates terugsturen naar de Swing-app, waardoor beide UI's gesynchroniseerd blijven.

### Updates naar Swing verzenden {#sending-updates-to-swing}

Nadat de gebruiker een klant in de webforJ-dialoog heeft bewerkt:

```java
dialog.onSave(() -> {
  // Stuur de bijgewerkte klant terug naar Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Updates verwerken in Swing {#processing-updates-in-swing}

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

## Architectuurvoordelen {#architecture-benefits}

Deze aanpak biedt verschillende voordelen ten opzichte van een volledige herschrijving:

### Onmiddellijke webimplementatie {#immediate-web-deployment}

Jouw Swing-app wordt onmiddellijk webtoegankelijk zonder codewijzigingen. Gebruikers kunnen er via een browser toegang toe krijgen terwijl je werkt aan verbeteringen.

### Progressieve verbetering {#progressive-enhancement}

Begin met het vervangen van alleen de bewerkingsdialoog, vervang vervolgens geleidelijk meer componenten:

1. **Fase 1**: Embed de entire Swing-app, vervang alleen de bewerkingsdialoog
2. **Fase 2**: Voeg webforJ-navigatie en menu's rondom de ingebedde app toe
3. **Fase 3**: Vervang de tabel door een webforJ-tabel, behoud Swing voor onvervangbare functies
4. **Fase 4**: Vervang uiteindelijk alle Swing-componenten

### Risicobeperking {#risk-mitigation}

Aangezien de oorspronkelijke Swing-app functioneel blijft, kun je:

- Terugvallen op desktopimplementatie indien nodig
- Nieuwe functies testen naast bestaande
- Gebruikers geleidelijk migreren
- Dezelfde bedrijfslogica behouden
