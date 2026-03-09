---
sidebar_position: 21
title: Debouncing
slug: debouncing
sidebar_class_name: new-content
_i18n_hash: 89cdcc39e4954963d7e19cb0e5665ca4
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

Debouncing ist eine Technik, die die Ausführung einer Aktion verzögert, bis eine bestimmte Zeit seit dem letzten Aufruf vergangen ist. Jeder neue Aufruf setzt den Timer zurück. Dies ist nützlich für Szenarien wie „Suche während der Eingabe“, bei denen Sie warten möchten, bis der Benutzer mit dem Tippen aufgehört hat, bevor eine Suchanfrage ausgeführt wird.

<ComponentDemo
path='/webforj/debouncer?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/debouncer/DebouncerView.java'
height='265px'
/>

## Grundlegende Verwendung {#basic-usage}

Die `Debouncer`-Klasse bietet eine einfache Möglichkeit, Aktionen zu debouncen. Erstellen Sie einen `Debouncer` mit einer Verzögerung in Sekunden und rufen Sie dann `run()` mit der Aktion auf, die Sie debouncen möchten:

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

In diesem Beispiel wird die Methode `search()` nur aufgerufen, nachdem der Benutzer 300 Millisekunden mit dem Tippen aufgehört hat. Jeder Tastendruck setzt den Timer über das `onModify`-Ereignis zurück, sodass schnelles Tippen keine mehrfachen Suchen auslöst.

## Wie es funktioniert {#how-it-works}

Wenn Sie `run()` mit einer Aktion aufrufen:

1. Wenn keine Aktion aussteht, plant der `Debouncer` die Aktion, um nach der Verzögerung ausgeführt zu werden.
2. Wenn bereits eine Aktion aussteht, wird die vorherige Aktion abgebrochen und der Timer wird mit der neuen Aktion neu gestartet.
3. Sobald die Verzögerung ohne einen weiteren Aufruf abläuft, wird die Aktion ausgeführt.

Der `Debouncer` läuft im UI-Thread unter Verwendung von webforJ's [`Interval`](/docs/advanced/interval)-Mechanismus, sodass Sie UI-Aktualisierungen nicht in `Environment.runLater()` einwickeln müssen.

:::tip Verzögerungseinheiten
Der Verzögerungsparameter verwendet Sekunden als Einheit, nicht Millisekunden. Verwenden Sie `0.3f` für 300 ms oder `1.5f` für 1,5 Sekunden.
:::

## Steuerung der Ausführung {#controlling-execution}

Die folgenden Methoden können verwendet werden, um die Ausführung und Nutzung des `Debouncer` genauer zu steuern:

### Abbrechen einer ausstehenden Aktion {#cancelling-a-pending-action}

Verwenden Sie `cancel()`, um eine ausstehende Aktion an der Ausführung zu hindern:

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// Benutzer navigiert weg, bevor die Speicherung ausgeführt wird
debounce.cancel();
```

:::tip Abbrechen ausstehender Debounces
Wie bei Intervallen ist es eine gute Praxis, ausstehende debounced Aktionen abzubrechen, wenn eine Komponente zerstört wird. Dies verhindert Speicherlecks und vermeidet Fehler bei der Ausführung von Aktionen auf zerstörten Komponenten:

```java
public class SearchPanel extends Composite<Div> {
  private final Debouncer debounce = new Debouncer(0.3f);

  @Override
  protected void onDidDestroy() {
    debounce.cancel();
  }
}
```
:::

### Sofortige Ausführung erzwingen {#forcing-immediate-execution}

Verwenden Sie `flush()`, um eine ausstehende Aktion sofort auszuführen:

```java
Debouncer debounce = new Debouncer(0.5f);

textField.onModify(e -> {
  debounce.run(() -> validateInput(textField.getText()));
});

// Validierung vor der Formularübermittlung erzwingen
submitButton.onClick(e -> {
  debounce.flush();
  if (isValid()) {
    submitForm();
  }
});
```

### Überprüfen des ausstehenden Status {#checking-pending-status}

Verwenden Sie `isPending()`, um zu überprüfen, ob eine Aktion auf die Ausführung wartet:

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("Verarbeitung...");
}
```

## Event-Level-Debouncing vs. `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ bietet zwei Ansätze zum Debouncing:

| Funktion | `Debouncer` | `ElementEventOptions.setDebounce()` |
|----------|-------------|-------------------------------------|
| Umfang   | Jede Aktion | Nur Elementereignisse               |
| Standort | Serverseitig | Clientseitig                        |
| Einheit   | Sekunden (float) | Millisekunden (int)              |
| Flexibilität | Volle Kontrolle mit Abbrechen/Flush | Automatisch mit Ereignis    |

Verwenden Sie `Debouncer`, wenn Sie programmatische Kontrolle über das Debouncing benötigen, z. B. zum Abbrechen oder Flushen ausstehender Aktionen. Verwenden Sie `ElementEventOptions`, wenn Sie einfaches clientseitiges Debouncing für Elementereignisse ohne zusätzliche Server-Round-Trips wünschen.

```java
// Verwendung von ElementEventOptions für clientseitiges Debouncing
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // Dieser Handler wird clientseitig debounced
}, options);
```
