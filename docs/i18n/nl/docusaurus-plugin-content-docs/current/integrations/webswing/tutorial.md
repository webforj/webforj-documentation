---
title: Handleiding voor modernisering
sidebar_position: 4
description: >-
  Modernize a Swing customer management app step by step by embedding it with
  WebswingConnector and layering in webforJ dialogs and forms.
_i18n_hash: ded3a5a3abe3905086532c58861652f8
---
Deze tutorial laat zien hoe je een bestaande Java Swing-app moderniseert door deze te integreren met webforJ via de `WebswingConnector`. Je leert hoe je een traditionele desktopapp toegankelijk maakt via het web en stapsgewijs moderne webfuncties toevoegt, zoals webgebaseerde dialogen en interactieve formulieren met webforJ-componenten.

:::note Vereisten
Voltooi voordat je aan deze tutorial begint de stappen in [Setup en configuratie](./setup) om je Webswing-server en CORS-instellingen te configureren.
:::

:::tip Broncode
De volledige broncode voor deze tutorial is beschikbaar op GitHub: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<VideoPlayer video="webswing/modernization-tutorial" width={1920} height={1080} title="Modernization tutorial" />

## Het scenario {#the-scenario}

Stel je voor dat je een klantbeheerapp hebt die met Swing is gebouwd en al jaren in productie is. De app werkt goed, maar gebruikers verwachten nu webtoegang en een moderne interface. In plaats van de app helemaal opnieuw te schrijven, gebruik je Webswing om deze meteen toegankelijk te maken via het web. Vervolgens voeg je stapsgewijs moderne webfuncties toe, zoals webgebaseerde dialogen en formulieren met webforJ-componenten.

## Uitgangspunt: de Swing-app {#starting-point-the-swing-app}

De voorbeeldapp in Swing is een klantentabel met de gebruikelijke CRUD-bewerkingen. Net als veel Swing-apps voor bedrijven volgt deze standaardpatronen:

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

Deze app werkt perfect als desktopapp, maar is niet toegankelijk via het web. Gebruikers moeten Java installeren en het JAR-bestand lokaal uitvoeren.

## Stap 1: de app geschikt maken voor Webswing {#step-1-making-it-webswing-aware}

De eerste stap is ervoor zorgen dat de Swing-app detecteert of deze onder Webswing wordt uitgevoerd. Zo kan de app zijn gedrag aanpassen zonder de compatibiliteit met de desktopversie te verliezen.

### De Webswing-omgeving detecteren {#detecting-the-webswing-environment}

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

Het belangrijkste inzicht is dat `WebswingUtil.getWebswingApi()` `null` retourneert wanneer de app als gewone desktopapp wordt uitgevoerd. Zo blijft de compatibiliteit met beide uitvoermodi behouden.

### Gedrag aanpassen voor webimplementatie {#adapting-behavior-for-web-deployment}

Nu de detectie is ingesteld, kun je het gedrag van de app aanpassen. De belangrijkste wijziging betreft de afhandeling van gebruikersinteracties:

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

Door het gedrag te laten afhangen van de waarde van `isWebswing`, kan de codebase beide omgevingen ondersteunen.

## Stap 2: de webforJ-wrapper maken {#step-2-creating-the-webforj-wrapper}

Nu de Swing-app via gebeurtenissen kan communiceren, maak je een webforJ-app die de Swing-app insluit en moderne webfuncties toevoegt, zoals webgebaseerde dialogen en formulieren.

### De connector instellen {#setting-up-the-connector}

De component `WebswingConnector` sluit je door Webswing gehoste app in een webforJ-weergave in:

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

De connector maakt verbinding met je Webswing-server en brengt een bidirectioneel communicatiekanaal tot stand.

### Gebeurtenissen uit Swing afhandelen {#handling-events-from-swing}

Wanneer de Swing-app gebeurtenissen verstuurt, bijvoorbeeld wanneer een gebruiker dubbelklikt op een rij, ontvangt de connector deze:

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

In plaats van de Swing-dialoog zien gebruikers nu een modern webformulier dat met webforJ-componenten is gebouwd.

## Stap 3: bidirectionele communicatie {#step-3-bidirectional-communication}

De integratie wordt krachtig wanneer de communicatie in beide richtingen verloopt. De webforJ-app kan updates terugsturen naar de Swing-app, zodat beide gebruikersinterfaces gesynchroniseerd blijven.

### Updates naar Swing versturen {#sending-updates-to-swing}

Nadat de gebruiker een klant in de webforJ-dialoog heeft bewerkt:

```java
dialog.onSave(() -> {
  // Send updated customer back to Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Updates in Swing verwerken {#processing-updates-in-swing}

De Swing-app luistert naar deze updates en vernieuwt de weergave:

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

## Voordelen van de architectuur {#architecture-benefits}

Deze aanpak biedt verschillende voordelen ten opzichte van een volledige herschrijving:

### Directe webimplementatie {#immediate-web-deployment}

Je Swing-app wordt meteen toegankelijk via het web, zonder codewijzigingen. Gebruikers kunnen de app via een browser gebruiken terwijl je aan verbeteringen werkt.

### Stapsgewijze uitbreiding {#progressive-enhancement}

Begin met het vervangen van alleen de bewerkingsdialoog en vervang vervolgens geleidelijk meer componenten:

1. **Fase 1**: Sluit de volledige Swing-app in en vervang alleen de bewerkingsdialoog
2. **Fase 2**: Voeg webforJ-navigatie en -menu’s toe rond de ingesloten app
3. **Fase 3**: Vervang de tabel door een webforJ-tabel en behoud Swing voor functies die niet vervangbaar zijn
4. **Fase 4**: Vervang uiteindelijk alle Swing-componenten

### Risico’s beperken {#risk-mitigation}

Omdat de oorspronkelijke Swing-app bruikbaar blijft, kun je:

- Zo nodig terugvallen op desktopimplementatie
- Nieuwe functies naast bestaande functies testen
- Gebruikers geleidelijk migreren
- Dezelfde bedrijfslogica behouden
