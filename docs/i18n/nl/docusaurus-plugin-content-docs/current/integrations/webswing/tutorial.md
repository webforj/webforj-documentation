---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: 32805132a2cf7b320864275fbbae7889
---
Deze tutorial doorloopt het moderniseren van een bestaande Java Swing-app door deze te integreren met webforJ met behulp van de `WebswingConnector`. Je leert hoe je een traditionele desktopapp webtoegankelijk kunt maken en geleidelijk moderne webfuncties kunt toevoegen, zoals webgebaseerde dialogen en interactieve formulieren met behulp van webforJ-componenten.

:::tip Bronnencode
De volledige bronnencode voor deze tutorial is beschikbaar op GitHub: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

## Het scenario

Stel je voor dat je een klantbeheer-app hebt die met Swing is gebouwd en die al jaren in productie is. Het werkt goed, maar gebruikers verwachten nu webtoegang en een moderne interface. In plaats van het helemaal opnieuw te schrijven, ga je Webswing gebruiken om het onmiddellijk webtoegankelijk te maken en vervolgens geleidelijk moderne webfuncties toe te voegen, zoals webgebaseerde dialogen en formulieren met behulp van webforJ-componenten.

## Beginpunt: de Swing-app

De voorbeeld Swing-app is een klantentabel met typische CRUD-operaties. Net als veel enterprise Swing-apps volgt het standaardpatronen:

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

    int result = JOptionPane.showConfirmDialog(null, fields, "Klant bewerken",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
  }
}
```

Deze app werkt perfect als desktopapp, maar heeft geen webtoegang. Gebruikers moeten Java installeren en het JAR-bestand lokaal uitvoeren.

## Stap 1: het Webswing-bewust maken

De eerste stap is om de Swing-app te laten detecteren of deze onder Webswing draait. Dit stelt het in staat om zijn gedrag aan te passen zonder de desktopcompatibiliteit te breken.

### Detecteren van de Webswing-omgeving

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

De belangrijkste inzicht hier is dat `WebswingUtil.getWebswingApi()` `null` retourneert wanneer deze draait als een reguliere desktop-app, waardoor je dubbele modus compatibiliteit kunt behouden.

### Gedrag aanpassen voor webimplementatie

Met detectie op zijn plaats kun je nu het gedrag van de app aanpassen. De belangrijkste wijziging is hoe gebruikersinteracties worden afgehandeld:

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

## Stap 2: de webforJ-wrapper maken

Nu de Swing-app via evenementen kan communiceren, maak je een webforJ-app die de Swing-app embed en moderne webfuncties toevoegt, zoals webgebaseerde dialogen en formulieren.

### De connector instellen

De `WebswingConnector`-component embed je Webswing-gehoste app binnen een webforJ-weergave:

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

De connector maakt verbinding met je Webswing-server en richt een bidirectioneel communicatiekanaal in.

### Evenementen van Swing afhandelen

Wanneer de Swing-app evenementen verzendt (zoals wanneer een gebruiker op een rij dubbelklikt), ontvangt de connector deze:

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

In plaats van de Swing-dialoog zien gebruikers nu een modern webformulier dat is opgebouwd met webforJ-componenten.

## Stap 3: bidirectionele communicatie

De integratie wordt krachtig wanneer de communicatie in beide richtingen vloeit. De webforJ-app kan updates terugsturen naar de Swing-app, waardoor beide UIs gesynchroniseerd blijven.

### Updates naar Swing sturen

Nadat de gebruiker een klant in de webforJ-dialoog heeft bewerkt:

```java
dialog.onSave(() -> {
  // Verzend de bijgewerkte klant terug naar Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Updates verwerken in Swing

De Swing-app luistert naar deze updates en verfrist zijn weergave:

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

Je Swing-app wordt onmiddellijk webtoegankelijk zonder codewijzigingen. Gebruikers kunnen er via een browser toegang toe krijgen terwijl jij aan verbeteringen werkt.

### Progressieve verbetering

Begin met alleen de bewerkingsdialoog te vervangen en vervang geleidelijk meer componenten:

1. **Fase 1**: Embed de hele Swing-app, vervang alleen de bewerkingsdialoog
2. **Fase 2**: Voeg webforJ-navigatie en menu's toe rondom de ingebedde app
3. **Fase 3**: Vervang de tabel door een webforJ-tabel, houd Swing voor niet-vervangbare functies
4. **Fase 4**: Vervang uiteindelijk alle Swing-componenten

### Risicobeperking

Omdat de oorspronkelijke Swing-app functioneel blijft, kun je:

- Terugvallen op desktopimplementatie indien nodig
- Nieuwe functies naast bestaande testen
- Gebruikers geleidelijk migreren
- Dezelfde bedrijfslogica behouden
