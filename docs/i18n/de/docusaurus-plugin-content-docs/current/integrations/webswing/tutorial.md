---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: 32805132a2cf7b320864275fbbae7889
---
Dieses Tutorial führt durch die Modernisierung einer bestehenden Java Swing-Anwendung, indem sie mit webforJ unter Verwendung des `WebswingConnector` integriert wird. Sie lernen, wie man eine traditionelle Desktop-App webzugänglich macht und schrittweise moderne Webfunktionen wie webbasierte Dialoge und interaktive Formulare mit webforJ-Komponenten hinzufügt.

:::tip Quellcode
Der vollständige Quellcode für dieses Tutorial ist auf GitHub verfügbar: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

## Das Szenario

Stellen Sie sich vor, Sie haben eine Kundenverwaltungs-App, die mit Swing erstellt wurde und seit Jahren in Produktion ist. Sie funktioniert gut, aber die Benutzer erwarten jetzt Webzugang und eine moderne Benutzeroberfläche. Anstatt von Grund auf neu zu schreiben, verwenden Sie Webswing, um sie sofort webzugänglich zu machen, und fügen schrittweise moderne Webfunktionen wie webbasierte Dialoge und Formulare mit webforJ-Komponenten hinzu.

## Ausgangspunkt: die Swing-App

Die Beispiel-Swing-App ist eine Kundentabelle mit typischen CRUD-Operationen. Wie viele Unternehmens-Swing-Apps folgt sie standardmäßigen Mustern:

```java
public class Application {
  private List<Customer> customers;
  private DefaultTableModel model;
  private JTable table;

  private void createTable() {
    String[] columnNames = { "Name", "Firma", "E-Mail" };
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
          // Doppelklick zum Bearbeiten
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
        "Firma:", companyField,
        "E-Mail:", emailField
    };

    int result = JOptionPane.showConfirmDialog(null, fields, "Kunden bearbeiten",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
  }
}
```

Diese App funktioniert perfekt als Desktop-App, fehlt jedoch die Webzugänglichkeit. Die Benutzer müssen Java installieren und die JAR-Datei lokal ausführen.

## Schritt 1: Webswing-fähig machen

Der erste Schritt besteht darin, die Swing-App erkennen zu lassen, ob sie unter Webswing läuft. Dadurch kann sie ihr Verhalten anpassen, ohne die Desktop-Kompatibilität zu beeinträchtigen.

### Erkennung der Webswing-Umgebung

Fügen Sie die Webswing-API-Abhängigkeit zu Ihrem Swing-Projekt hinzu:

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Ändern Sie anschließend Ihre App, um zur Webswing-Laufzeit zu erkennen:

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

Der entscheidende Punkt hier ist, dass `WebswingUtil.getWebswingApi()` `null` zurückgibt, wenn es als reguläre Desktop-App ausgeführt wird, sodass Sie die Dualmodus-Kompatibilität aufrechterhalten können.

### Verhalten für die Web-Bereitstellung anpassen

Mit der Erkennung können Sie jetzt das Verhalten der App anpassen. Die wichtigste Änderung betrifft die Handhabung der Benutzerinteraktionen:

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

Durch das Abzweigen des Verhaltens entsprechend dem Wert von `isWebswing` kann der Code sowohl in beiden Umgebungen arbeiten.

## Schritt 2: Erstellen des webforJ Wrappers

Jetzt, da die Swing-App über Ereignisse kommunizieren kann, erstellen Sie eine webforJ-App, die die Swing-App einbettet und moderne Webfunktionen wie webbasierte Dialoge und Formulare hinzufügt.

### Einrichten des Connectors

Die `WebswingConnector`-Komponente bettet Ihre Webswing-gehostete App innerhalb einer webforJ-Ansicht ein:

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

Der Connector verbindet sich mit Ihrem Webswing-Server und stellt einen bidirektionalen Kommunikationskanal her.

### Ereignisse von Swing verarbeiten

Wenn die Swing-App Ereignisse sendet (wie wenn ein Benutzer eine Zeile doppelklickt), empfängt der Connector diese:

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

Jetzt sehen die Benutzer statt des Swing-Dialogs ein modernes Webformular, das mit webforJ-Komponenten erstellt wurde.

## Schritt 3: Bidirektionale Kommunikation

Die Integration wird mächtig, wenn die Kommunikation in beide Richtungen fließt. Die webforJ-App kann Updates zurück an die Swing-App senden, wodurch beide Benutzeroberflächen synchronisiert bleiben.

### Updates an Swing senden

Nachdem der Benutzer einen Kunden im webforJ-Dialog bearbeitet hat:

```java
dialog.onSave(() -> {
  // Aktualisierten Kunden zurück an Swing senden
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Updates in Swing verarbeiten

Die Swing-App hört auf diese Updates und aktualisiert ihre Anzeige:

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

## Architekturvorteile

Dieser Ansatz bietet mehrere Vorteile gegenüber einem vollständigen Rewrite:

### Sofortige Webbereitstellung

Ihre Swing-App wird sofort webzugänglich, ohne Codeänderungen. Die Benutzer können über einen Browser darauf zugreifen, während Sie an Verbesserungen arbeiten.

### Fortschreitende Verbesserung

Beginnen Sie damit, nur den Bearbeitungsdialog zu ersetzen, und ersetzen Sie dann schrittweise weitere Komponenten:

1. **Phase 1**: Betten Sie die gesamte Swing-App ein, ersetzen Sie nur den Bearbeitungsdialog
2. **Phase 2**: Fügen Sie die WebforJ-Navigation und -Menüs um die eingebettete App hinzu
3. **Phase 3**: Ersetzen Sie die Tabelle durch eine webforJ-Tabelle und behalten Sie Swing für unverzichtbare Funktionen
4. **Phase 4**: Ersetzen Sie schließlich alle Swing-Komponenten

### Risikominderung

Da die ursprüngliche Swing-App funktionsfähig bleibt, können Sie:

- Bei Bedarf auf die Desktop-Bereitstellung zurückgreifen
- Neue Funktionen neben bestehenden testen
- Benutzer schrittweise migrieren
- Die gleiche Geschäftslogik beibehalten
