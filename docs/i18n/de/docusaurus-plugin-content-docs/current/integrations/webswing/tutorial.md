---
title: Tutorial zur Modernisierung
sidebar_position: 4
description: >-
  Modernize a Swing customer management app step by step by embedding it with
  WebswingConnector and layering in webforJ dialogs and forms.
_i18n_hash: ded3a5a3abe3905086532c58861652f8
---
Dieses Tutorial zeigt Schritt für Schritt, wie Sie eine bestehende Java-Swing-App modernisieren, indem Sie sie mit webforJ und dem `WebswingConnector` integrieren. Sie erfahren, wie Sie eine herkömmliche Desktop-App über das Web zugänglich machen und schrittweise moderne Webfunktionen wie webbasierte Dialoge und interaktive Formulare mit webforJ-Komponenten hinzufügen.

:::note Voraussetzungen
Bevor Sie mit diesem Tutorial beginnen, führen Sie die Schritte unter [Einrichtung und Konfiguration](./setup) aus, um Ihren Webswing-Server und die CORS-Einstellungen zu konfigurieren.
:::

:::tip Quellcode
Der vollständige Quellcode für dieses Tutorial ist auf GitHub verfügbar: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<VideoPlayer video="webswing/modernization-tutorial" width={1920} height={1080} title="Modernization tutorial" />

## Das Szenario {#the-scenario}

Stellen Sie sich vor, Sie haben eine mit Swing entwickelte Kundenverwaltungs-App, die seit Jahren produktiv eingesetzt wird. Sie funktioniert gut, aber die Nutzer erwarten inzwischen Zugriff über das Web und eine moderne Benutzeroberfläche. Statt die App von Grund auf neu zu schreiben, verwenden Sie Webswing, um sie sofort über das Web zugänglich zu machen. Anschließend fügen Sie schrittweise moderne Webfunktionen wie webbasierte Dialoge und Formulare mit webforJ-Komponenten hinzu.

## Ausgangspunkt: die Swing-App {#starting-point-the-swing-app}

Die beispielhafte Swing-App zeigt eine Kundentabelle mit typischen CRUD-Operationen. Wie viele Swing-Apps in Unternehmen folgt sie gängigen Mustern:

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

Diese App funktioniert als Desktop-App einwandfrei, ist aber nicht über das Web zugänglich. Nutzer müssen Java installieren und die JAR-Datei lokal ausführen.

## Schritt 1: Unterstützung für Webswing hinzufügen {#step-1-making-it-webswing-aware}

Im ersten Schritt soll die Swing-App erkennen, ob sie unter Webswing ausgeführt wird. So kann sie ihr Verhalten entsprechend anpassen, ohne die Kompatibilität mit der Desktop-Version zu beeinträchtigen.

### Webswing-Umgebung erkennen {#detecting-the-webswing-environment}

Fügen Sie Ihrem Swing-Projekt die Webswing-API-Abhängigkeit hinzu:

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Ändern Sie anschließend Ihre App so, dass sie die Webswing-Laufzeitumgebung erkennt:

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

Entscheidend ist, dass `WebswingUtil.getWebswingApi()` bei einer gewöhnlichen Desktop-App `null` zurückgibt. So bleibt die Kompatibilität mit beiden Betriebsarten erhalten.

### Verhalten für die Webbereitstellung anpassen {#adapting-behavior-for-web-deployment}

Nach der Erkennung können Sie nun das Verhalten der App anpassen. Die wichtigste Änderung betrifft die Verarbeitung von Nutzerinteraktionen:

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

Indem das Verhalten anhand des Werts von `isWebswing` verzweigt wird, kann die Codebasis beide Umgebungen unterstützen.

## Schritt 2: Den webforJ-Wrapper erstellen {#step-2-creating-the-webforj-wrapper}

Nachdem die Swing-App Ereignisse senden kann, erstellen Sie eine webforJ-App, die die Swing-App einbettet und moderne Webfunktionen wie webbasierte Dialoge und Formulare ergänzt.

### Den Connector einrichten {#setting-up-the-connector}

Die Komponente `WebswingConnector` bettet Ihre auf Webswing gehostete App in eine webforJ-Ansicht ein:

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

Der Connector stellt eine Verbindung zu Ihrem Webswing-Server her und richtet einen bidirektionalen Kommunikationskanal ein.

### Ereignisse aus Swing verarbeiten {#handling-events-from-swing}

Wenn die Swing-App Ereignisse sendet, etwa wenn ein Nutzer auf eine Zeile doppelklickt, empfängt der Connector sie:

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

Anstelle des Swing-Dialogs sehen Nutzer nun ein modernes Webformular, das mit webforJ-Komponenten erstellt wurde.

## Schritt 3: Bidirektionale Kommunikation {#step-3-bidirectional-communication}

Die Integration entfaltet ihr volles Potenzial, wenn die Kommunikation in beide Richtungen funktioniert. Die webforJ-App kann Aktualisierungen an die Swing-App zurücksenden, sodass beide Benutzeroberflächen synchron bleiben.

### Aktualisierungen an Swing senden {#sending-updates-to-swing}

Nachdem der Nutzer einen Kunden im webforJ-Dialog bearbeitet hat:

```java
dialog.onSave(() -> {
  // Send updated customer back to Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Aktualisierungen in Swing verarbeiten {#processing-updates-in-swing}

Die Swing-App lauscht auf diese Aktualisierungen und aktualisiert ihre Anzeige:

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

## Vorteile der Architektur {#architecture-benefits}

Dieser Ansatz bietet mehrere Vorteile gegenüber einer vollständigen Neuentwicklung:

### Sofortige Webbereitstellung {#immediate-web-deployment}

Ihre Swing-App wird ohne Codeänderungen sofort über das Web zugänglich. Nutzer können über einen Browser darauf zugreifen, während Sie an Erweiterungen arbeiten.

### Schrittweise Erweiterung {#progressive-enhancement}

Beginnen Sie damit, nur den Bearbeitungsdialog zu ersetzen, und tauschen Sie dann nach und nach weitere Komponenten aus:

1. **Phase 1**: Die gesamte Swing-App einbetten und nur den Bearbeitungsdialog ersetzen
2. **Phase 2**: webforJ-Navigation und -Menüs um die eingebettete App ergänzen
3. **Phase 3**: Die Tabelle durch eine webforJ-Tabelle ersetzen und Swing für unverzichtbare Funktionen beibehalten
4. **Phase 4**: Schließlich alle Swing-Komponenten ersetzen

### Risikominimierung {#risk-mitigation}

Da die ursprüngliche Swing-App weiterhin funktioniert, können Sie:

- bei Bedarf auf die Desktopbereitstellung zurückgreifen
- neue Funktionen parallel zu bestehenden Funktionen testen
- Nutzer schrittweise migrieren
- dieselbe Geschäftslogik beibehalten
