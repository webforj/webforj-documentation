---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: 97df9e800c5792a1ff22fb6e0e9a33e9
---
Dieses Tutorial zeigt, wie man eine bestehende Java Swing-App modernisiert, indem man sie mit webforJ unter Verwendung des `WebswingConnector` integriert. Sie lernen, wie man eine traditionelle Desktop-App webzugänglich macht und schrittweise moderne Webfunktionen wie webbasierte Dialoge und interaktive Formulare mit webforJ-Komponenten hinzufügt.

:::note Voraussetzungen
Bevor Sie mit diesem Tutorial beginnen, schließen Sie die Schritte zur [Einrichtung und Konfiguration](./setup) ab, um Ihren Webswing-Server und die CORS-Einstellungen zu konfigurieren.
:::

:::tip Quellcode
Der vollständige Quellcode für dieses Tutorial ist auf GitHub verfügbar: [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/webswing/modernization-tutorial.mp4#t=5" type="video/mp4"/>
  </video>
</div>

## Das Szenario {#the-scenario}

Stellen Sie sich vor, Sie haben eine Kundenmanagement-App, die mit Swing erstellt wurde und seit Jahren in Produktion ist. Sie funktioniert gut, aber die Benutzer erwarten jetzt Webzugang und eine moderne Benutzeroberfläche. Anstatt von Grund auf neu zu schreiben, verwenden Sie Webswing, um sie sofort webzugänglich zu machen, und fügen dann schrittweise moderne Webfunktionen wie webbasierte Dialoge und Formulare mit webforJ-Komponenten hinzu.

## Ausgangspunkt: die Swing-App {#starting-point-the-swing-app}

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
          // Doppelklick zum Bearbeiten behandeln
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

Diese App funktioniert perfekt als Desktop-App, bietet aber keinen Webzugang. Benutzer müssen Java installieren und die JAR-Datei lokal ausführen.

## Schritt 1: Webswing-bewusst machen {#step-1-making-it-webswing-aware}

Der erste Schritt besteht darin, die Swing-App zu veranlassen, zu erkennen, ob sie unter Webswing läuft. Dadurch kann sie ihr Verhalten anpassen, ohne die Desktop-Kompatibilität zu beeinträchtigen.

### Erkennen der Webswing-Umgebung {#detecting-the-webswing-environment}

Fügen Sie Ihrer Swing-Anwendung die Webswing-API-Abhängigkeit hinzu:

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Ändern Sie dann Ihre App, um die Webswing-Laufzeit zu erkennen:

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

Der Schlüsselgedanke hier ist, dass `WebswingUtil.getWebswingApi()` `null` zurückgibt, wenn es als reguläre Desktop-App ausgeführt wird, was es Ihnen ermöglicht, die Dual-Modus-Kompatibilität aufrechtzuerhalten.

### Verhalten für die Webbereitstellung anpassen {#adapting-behavior-for-web-deployment}

Mit der Erkennung können Sie nun das Verhalten der App anpassen. Die wichtigste Änderung besteht darin, wie Benutzerinteraktionen gehandhabt werden:

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

Durch die Verzweigung des Verhaltens entsprechend dem Wert von `isWebswing` kann der Code beide Umgebungen verwalten.

## Schritt 2: den webforJ-Wrapper erstellen {#step-2-creating-the-webforj-wrapper}

Jetzt, da die Swing-App über Ereignisse kommunizieren kann, erstellen Sie eine webforJ-App, die die Swing-App einbettet und moderne Webfunktionen wie webbasierte Dialoge und Formulare hinzufügt.

### Den Connector einrichten {#setting-up-the-connector}

Die `WebswingConnector`-Komponente bettet Ihre von Webswing gehostete App innerhalb einer webforJ-Ansicht ein:

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

### Ereignisse von Swing behandeln {#handling-events-from-swing}

Wenn die Swing-App Ereignisse sendet (z. B. wenn ein Benutzer doppelt auf eine Zeile klickt), empfängt der Connector diese:

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

Jetzt sehen die Benutzer anstelle des Swing-Dialogs ein modernes Webformular, das mit webforJ-Komponenten erstellt wurde.

## Schritt 3: bidirektionale Kommunikation {#step-3-bidirectional-communication}

Die Integration wird leistungsstark, wenn die Kommunikation in beide Richtungen fließt. Die webforJ-App kann Updates an die Swing-App zurücksenden, um beide UI synchron zu halten.

### Updates an Swing senden {#sending-updates-to-swing}

Nachdem der Benutzer einen Kunden im webforJ-Dialog bearbeitet hat:

```java
dialog.onSave(() -> {
  // Aktualisierten Kunden zurück an Swing senden
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Updates in Swing verarbeiten {#processing-updates-in-swing}

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

## Architekturvorteile {#architecture-benefits}

Dieser Ansatz bietet mehrere Vorteile gegenüber einer vollständigen Neuschreibung:

### Sofortige Webbereitstellung {#immediate-web-deployment}

Ihre Swing-App wird sofort webzugänglich, ohne dass Codeänderungen erforderlich sind. Benutzer können über einen Browser darauf zugreifen, während Sie an Verbesserungen arbeiten.

### Fortschreitende Verbesserung {#progressive-enhancement}

Beginnen Sie damit, nur den Bearbeitungsdialog zu ersetzen, und ersetzen Sie dann schrittweise weitere Komponenten:

1. **Phase 1**: Betten Sie die gesamte Swing-App ein, ersetzen Sie nur den Bearbeitungsdialog
2. **Phase 2**: Fügen Sie WebforJ-Navigation und Menüs um die eingebettete App hinzu
3. **Phase 3**: Ersetzen Sie die Tabelle durch eine webforJ-Tabelle, behalten Sie Swing für unverzichtbare Funktionen
4. **Phase 4**: Schließlich alle Swing-Komponenten ersetzen

### Risikominderung {#risk-mitigation}

Da die ursprüngliche Swing-App funktionsfähig bleibt, können Sie:

- Bei Bedarf auf die Desktop-Bereitstellung zurückgreifen
- Neue Funktionen neben vorhandenen testen
- Benutzer schrittweise migrieren
- Die gleiche Geschäftslogik beibehalten
