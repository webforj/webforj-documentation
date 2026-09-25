---
sidebar_position: 21
title: Debouncing
slug: debouncing
description: >-
  Delay actions until activity settles using the Debouncer class for
  search-as-you-type, autosave, and other rate-limited UI work.
_i18n_hash: fd81dccbd2aeb6e50922c2d09de536de
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

Debouncing ist eine Technik, die die Ausführung einer Aktion verzögert, bis eine festgelegte Zeit seit dem letzten Aufruf vergangen ist. Jeder neue Aufruf setzt den Timer zurück. Dies ist nützlich für Szenarien wie die Suche beim Tippen, bei denen Sie warten möchten, bis der Benutzer mit dem Tippen aufhört, bevor eine Suchanfrage ausgeführt wird.

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/debouncer'
files={['src/main/java/com/webforj/samples/views/debouncer/DebouncerView.java']}
height='265px'
/>

Erstellen Sie einen `Debouncer` mit einer Verzögerung in Sekunden und rufen Sie dann `run()` mit der Aktion auf, die Sie debouncen möchten:

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

In diesem Beispiel wird die Methode `search()` nur aufgerufen, nachdem der Benutzer 300 Millisekunden lang aufgehört hat zu tippen. Jeder Tastendruck setzt den Timer über das `onModify`-Ereignis zurück, sodass schnelles Tippen keine mehrfachen Suchanfragen auslöst.

## Wie es funktioniert {#how-it-works}

Wenn Sie `run()` mit einer Aktion aufrufen:

1. Wenn keine Aktion aussteht, plant der `Debouncer` die Aktion nach der Verzögerung.
2. Wenn bereits eine Aktion aussteht, wird die vorherige Aktion abgebrochen und der Timer wird mit der neuen Aktion neu gestartet.
3. Sobald die Verzögerung vergeht, ohne dass ein weiterer Aufruf erfolgt, wird die Aktion ausgeführt.

Der `Debouncer` läuft im UI-Thread unter Verwendung des [`Interval`](/docs/advanced/interval)-Mechanismus von webforJ, sodass Sie UI-Updates nicht in `Environment.runLater()` einwickeln müssen.

:::tip Verzögerungseinheiten
Der Verzögerungsparameter verwendet Sekunden als Einheit, nicht Millisekunden. Verwenden Sie `0.3f` für 300 ms oder `1.5f` für 1,5 Sekunden.
:::

## Ausführung steuern {#controlling-execution}

Die folgenden Methoden können verwendet werden, um die Ausführung und Nutzung des `Debouncer` präziser zu steuern:

### Abbrechen einer ausstehenden Aktion {#cancelling-a-pending-action}

Verwenden Sie `cancel()`, um eine ausstehende Aktion von der Ausführung abzuhalten:

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// Benutzer navigiert weg, bevor das Speichern erfolgt
debounce.cancel();
```

:::tip Abbrechen ausstehender Debounces
Wie bei Intervallen ist es eine gute Praxis, ausstehende debouncierte Aktionen abzubrechen, wenn eine Komponente zerstört wird. Dies verhindert Speicherlecks und vermeidet Fehler durch Aktionen, die an zerstörten Komponenten ausgeführt werden:

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

### Zwangsausführung sofort {#forcing-immediate-execution}

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

### Prüfung des ausstehenden Status {#checking-pending-status}

Verwenden Sie `isPending()`, um zu überprüfen, ob eine Aktion auf die Ausführung wartet:

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("Verarbeite...");
}
```

## Ereignisebene Debouncing vs `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ bietet zwei Ansätze zum Debouncing:

| Feature | `Debouncer` | `ElementEventOptions.setDebounce()` |
|---------|-------------|-------------------------------------|
| Bereich | Jede Aktion | Nur Elementereignisse |
| Standort | Serverseitig | Clientseitig |
| Einheit | Sekunden (float) | Millisekunden (int) |
| Flexibilität | Volle Kontrolle mit cancel/flush | Automatisch mit Ereignis |

Verwenden Sie `Debouncer`, wenn Sie programmatische Kontrolle über das Debouncing benötigen, wie das Abbrechen oder Flushen ausstehender Aktionen. Verwenden Sie `ElementEventOptions`, wenn Sie einfaches clientseitiges Debouncing für Elementereignisse ohne zusätzliche Server-Round-Trips wünschen.

```java
// Verwendung von ElementEventOptions für clientseitiges Debouncing
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // Dieser Handler wird clientseitig debounct
}, options);
```
