---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: 97df9e800c5792a1ff22fb6e0e9a33e9
---
Deze tutorial loopt door het moderniseren van een bestaande Java Swing-app door deze te integreren met webforJ met behulp van de `WebswingConnector`. Je leert hoe je een traditionele desktopapp web-toegankelijk maakt en geleidelijk moderne webfuncties toevoegt, zoals web-gebaseerde dialogen en interactieve formulieren met behulp van webforJ-componenten.

:::note Vereisten
Voordat je met deze tutorial begint, voltooi de stappen in de [Setup en Configuratie](./setup) om je Webswing-server en CORS-instellingen te configureren.
:::

:::tip Broncode
De volledige broncode voor deze tutorial is beschikbaar op GitHub: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/webswing/modernization-tutorial.mp4#t=5" type="video/mp4"/>
  </video>
</div>

## Het scenario {#the-scenario}

Stel je voor dat je een klantbeheer-app hebt gebouwd met Swing die al jaren in productie is. Het werkt goed, maar gebruikers verwachten nu webtoegang en een moderne interface. In plaats van vanaf nul opnieuw te schrijven, gebruik je Webswing om deze onmiddellijk web-toegankelijk te maken en vervolgens geleidelijk moderne webfuncties toe te voegen, zoals web-gebaseerde dialogen en formulieren met behulp van webforJ-componenten.

## Startpunt: de Swing-app {#starting-point-the-swing-app}

De voorbeeld Swing-app is een klantentabel met typische CRUD-operaties. Zoals veel enterprise Swing-apps volgt het standaard patronen:

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
          // Behandel dubbelklikken om te bewerken
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

Deze app werkt perfect als een desktop-app, maar mist webtoegang. Gebruikers moeten Java installeren en het JAR-bestand lokaal uitvoeren.

## Stap 1: het webforJ-bewust maken {#step-1-making-it-webswing-aware}

De eerste stap is het maken van de Swing-app die detecteert of deze onder Webswing draait. Dit stelt het in staat om zijn gedrag aan te passen zonder de compatibiliteit met desktop te verbreken.

### Detecteren van de Webswing-omgeving {#detecting-the-webswing-environment}

Voeg de Webswing API-afhankelijkheid toe aan je Swing-project:

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Wijzig vervolgens je app om het Webswing-runtime te detecteren:

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

De belangrijkste inzicht hierbij is dat `WebswingUtil.getWebswingApi()` `null` retourneert wanneer deze draait als een reguliere desktop-app, waardoor je dubbele moduscompatibiliteit kunt behouden.

### Gedrag aanpassen voor webimplementatie {#adapting-behavior-for-web-deployment}

Met de detectie op zijn plaats, kun je nu het gedrag van de app aanpassen. De belangrijkste wijziging is hoe gebruikersinteracties worden behandeld:

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

Door het gedrag te vertakken op basis van de waarde van `isWebswing`, kan de codebasis beide omgevingen verwerken.

## Stap 2: de webforJ-wrapper maken {#step-2-creating-the-webforj-wrapper}

Nu de Swing-app kan communiceren via evenementen, maak een webforJ-app die de Swing-app insluit en moderne webfuncties toevoegt, zoals web-gebaseerde dialogen en formulieren.

### De connector instellen {#setting-up-the-connector}

De `WebswingConnector` component voegt je Webswing-gehoste app in binnen een webforJ-weergave:

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

De connector verbindt met je Webswing-server en stelt een bidirectioneel communicatiekanaal in.

### Evenementen vanuit Swing verwerken {#handling-events-from-swing}

Wanneer de Swing-app evenementen verzendt (zoals wanneer een gebruiker dubbelklikt op een rij), ontvangt de connector ze:

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

Nu zien gebruikers in plaats van de Swing-dialoog een modern webformulier dat is opgebouwd met webforJ-componenten.

## Stap 3: bidirectionele communicatie {#step-3-bidirectional-communication}

De integratie wordt krachtig wanneer de communicatie in beide richtingen stroomt. De webforJ-app kan updates terugsturen naar de Swing-app, waardoor beide UI's gesynchroniseerd blijven.

### Updates naar Swing verzenden {#sending-updates-to-swing}

Nadat de gebruiker een klant in de webforJ-dialoog bewerkt heeft:

```java
dialog.onSave(() -> {
  // Stuur de bijgewerkte klant terug naar Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Updates in Swing verwerken {#processing-updates-in-swing}

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

Je Swing-app wordt onmiddellijk web-toegankelijk zonder codewijzigingen. Gebruikers kunnen er via een browser toegang toe krijgen terwijl je aan verbeteringen werkt.

### Progressieve verbetering {#progressive-enhancement}

Begin met het vervangen van alleen de bewerk-dialoog, vervang vervolgens geleidelijk meer componenten:

1. **Fase 1**: Verbind de hele Swing-app, vervang alleen de bewerk-dialoog
2. **Fase 2**: Voeg webforJ-navigatie en menu's toe rond de ingesloten app
3. **Fase 3**: Vervang de tabel door een webforJ-tabel, behoud Swing voor onvervangbare functies
4. **Fase 4**: Vervang uiteindelijk alle Swing-componenten

### Risicobeperking {#risk-mitigation}

Aangezien de originele Swing-app functioneel blijft, kun je:

- Terugvallen op desktopimplementatie indien nodig
- Nieuwe functies naast bestaande testen
- Gebruikers geleidelijk migreren
- Dezelfde bedrijfslogica handhaven
