---
sidebar_position: 21
title: Debouncing
slug: debouncing
_i18n_hash: 2096c774627674739fd237aed9a4f79e
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

Debouncing is een techniek die het uitvoeren van een actie uitstelt totdat er een bepaalde tijd is verstreken sinds de laatste oproep. Elke nieuwe oproep reset de timer. Dit is nuttig voor scenario's zoals zoeken terwijl je typt, waar je wilt wachten tot de gebruiker stopt met typen voordat je een zoekopdracht uitvoert.

<ComponentDemo
path='/webforj/debouncer'
files={['src/main/java/com/webforj/samples/views/debouncer/DebouncerView.java']}
height='265px'
/>

## Basisgebruik {#basic-usage}

De `Debouncer` klasse biedt een eenvoudige manier om acties te debouncen. Maak een `Debouncer` aan met een vertraging in seconden en roep vervolgens `run()` aan met de actie die je wilt debouncen:

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

In dit voorbeeld wordt de `search()` methode alleen aangeroepen nadat de gebruiker 300 milliseconden heeft gestopt met typen. Elke toetsaanslag reset de timer via de `onModify` gebeurtenis, zodat snel typen geen meerdere zoekopdrachten triggert.

## Hoe het werkt {#how-it-works}

Wanneer je `run()` aanroept met een actie:

1. Als er geen actie in behandeling is, plant de `Debouncer` de actie in om na de vertraging uit te voeren
2. Als er al een actie in behandeling is, wordt de vorige actie geannuleerd en de timer wordt opnieuw gestart met de nieuwe actie
3. Zodra de vertraging verstrijkt zonder een andere oproep, wordt de actie uitgevoerd

De `Debouncer` draait op de UI-thread met behulp van webforJ's [`Interval`](/docs/advanced/interval) mechanisme, dus je hoeft UI-updates niet in `Environment.runLater()` te wikkelen.

:::tip Vertragingseenheden
De vertragingparameter gebruikt seconden als eenheid, niet milliseconden. Gebruik `0.3f` voor 300 ms of `1.5f` voor 1,5 seconden.
:::

## Controle van uitvoering {#controlling-execution}

De volgende methoden kunnen worden gebruikt om de uitvoering en het gebruik van de `Debouncer` preciezer te beheren:

### Een wachtende actie annuleren {#cancelling-a-pending-action}

Gebruik `cancel()` om een wachtende actie te stoppen:

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// De gebruiker navigeert weg voordat de save wordt uitgevoerd
debounce.cancel();
```

:::tip Anuleren van wachtende debounces
Net als bij intervallen is het goed om wachtende debounced acties te annuleren wanneer een component wordt vernietigd. Dit voorkomt geheugenlekken en voorkomt fouten door acties die worden uitgevoerd op vernietigde componenten:

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

### Dwingen tot onmiddellijke uitvoering {#forcing-immediate-execution}

Gebruik `flush()` om een wachtende actie onmiddellijk uit te voeren:

```java
Debouncer debounce = new Debouncer(0.5f);

textField.onModify(e -> {
  debounce.run(() -> validateInput(textField.getText()));
});

// Dwing validatie af voor het indienen van het formulier
submitButton.onClick(e -> {
  debounce.flush();
  if (isValid()) {
    submitForm();
  }
});
```

### Controleren van de wachtende status {#checking-pending-status}

Gebruik `isPending()` om te controleren of een actie wacht om uitgevoerd te worden:

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("Verwerken...");
}
```

## Evenementniveau debouncing vs `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ biedt twee benaderingen voor debouncing:

| Kenmerk | `Debouncer` | `ElementEventOptions.setDebounce()` |
|---------|-------------|-------------------------------------|
| Bereik | Elke actie | Alleen elementgebeurtenissen |
| Locatie | Serverzijde | Clientzijde |
| Eenheid | Seconden (float) | Milliseconden (int) |
| Flexibiliteit | Volledige controle met annuleren/flushing | Automatisch met gebeurtenis |

Gebruik `Debouncer` wanneer je programmatiche controle over debouncing nodig hebt, zoals annuleren of flushen van wachtende acties. Gebruik `ElementEventOptions` wanneer je eenvoudige client-side debouncing wilt voor elementgebeurtenissen zonder extra server-rondreizen.

```java
// Gebruik ElementEventOptions voor client-side debouncing
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // Deze handler is gedebounced op de client
}, options);
```
