---
sidebar_position: 21
title: Debouncing
slug: debouncing
_i18n_hash: 2096c774627674739fd237aed9a4f79e
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

Debouncing ist eine Technik, die die Ausführung einer Aktion verzögert, bis eine bestimmte Zeit seit dem letzten Aufruf vergangen ist. Jeder neue Aufruf setzt den Timer zurück. Dies ist nützlich für Szenarien wie „Suche während der Eingabe“, bei denen gewartet werden soll, bis der Benutzer mit der Eingabe aufhört, bevor eine Suchanfrage ausgeführt wird.

<ComponentDemo
path='/webforj/debouncer'
files={['src/main/java/com/webforj/samples/views/debouncer/DebouncerView.java']}
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

In diesem Beispiel wird die Methode `search()` nur aufgerufen, nachdem der Benutzer 300 Millisekunden mit der Eingabe aufgehört hat. Jede Tastenanschläge setzen den Timer über das Ereignis `onModify` zurück, sodass schnelles Tippen keine mehrfachen Suchen auslöst.

## Wie es funktioniert {#how-it-works}

Wenn Sie `run()` mit einer Aktion aufrufen:

1. Wenn keine Aktion aussteht, plant der `Debouncer` die Aktion nach der Verzögerung.
2. Wenn bereits eine Aktion aussteht, wird die vorherige Aktion abgebrochen und der Timer wird mit der neuen Aktion neu gestartet.
3. Sobald die Verzögerung ohne einen weiteren Aufruf abläuft, wird die Aktion ausgeführt.

Der `Debouncer` läuft im UI-Thread unter Verwendung des [`Interval`](/docs/advanced/interval)-Mechanismus von webforJ, sodass Sie keine UI-Aktualisierungen in `Environment.runLater()` einwickeln müssen.

:::tip Verzögerungseinheiten
Der Verzögerungsparameter verwendet als Einheit Sekunden, nicht Millisekunden. Verwenden Sie `0.3f` für 300 ms oder `1.5f` für 1,5 Sekunden.
:::

## Ausführung steuern {#controlling-execution}

Die folgenden Methoden können verwendet werden, um die Ausführung und Verwendung des `Debouncer` präziser zu steuern:

### Eine ausstehende Aktion abbrechen {#cancelling-a-pending-action}

Verwenden Sie `cancel()`, um eine ausstehende Aktion an der Ausführung zu hindern:

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// Benutzer navigiert weg, bevor der Speichervorgang ausgeführt wird
debounce.cancel();
```

:::tip Abbrechen ausstehender Debounces
Ähnlich wie bei Intervallen ist es eine gute Praxis, ausstehende debouncte Aktionen abzubrechen, wenn eine Komponente zerstört wird. Dadurch werden Speicherlecks vermieden und Fehler aus Aktionen, die auf zerstörten Komponenten ausgeführt werden, verhindert:

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

## Ereignislevel-Debouncing vs `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ bietet zwei Ansätze zum Debouncing:

| Funktion | `Debouncer` | `ElementEventOptions.setDebounce()` |
|----------|-------------|-------------------------------------|
| Umfang   | Jede Aktion | Nur Elementereignisse              |
| Standort | Serverseite | Clientseite                         |
| Einheit   | Sekunden (float) | Millisekunden (int)                |
| Flexibilität | Volle Kontrolle mit cancel/flush | Automatisch mit Ereignis         |

Verwenden Sie `Debouncer`, wenn Sie programmatische Kontrolle über das Debouncing benötigen, wie das Abbrechen oder Flushen ausstehender Aktionen. Verwenden Sie `ElementEventOptions`, wenn Sie einfaches clientseitiges Debouncing für Elementereignisse ohne zusätzliche Server-Round-Trips wünschen.

```java
// Verwendung von ElementEventOptions für clientseitiges Debouncing
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // Dieser Handler wird clientseitig debounced
}, options);
```
