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

Debouncing is een techniek die het uitvoeren van een actie vertraagt totdat er een bepaalde tijd is verstreken sinds de laatste oproep. Elke nieuwe oproep reset de timer. Dit is nuttig voor scenario's zoals zoeken terwijl je typt, waarbij je wilt wachten tot de gebruiker stopt met typen voordat je een zoekopdracht uitvoert.

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/debouncer'
files={['src/main/java/com/webforj/samples/views/debouncer/DebouncerView.java']}
height='265px'
/>

Maak een `Debouncer` met een vertraging in seconden en roep vervolgens `run()` aan met de actie die je wilt debouncen:

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

In dit voorbeeld wordt de `search()`-methode alleen aangeroepen nadat de gebruiker 300 milliseconden is gestopt met typen. Elke toetsaanslag reset de timer via de `onModify`-evenement, zodat snel typen meerdere zoekopdrachten niet activeert.

## Hoe het werkt {#how-it-works}

Wanneer je `run()` aanroept met een actie:

1. Als er geen actie in uitvoering is, plant de `Debouncer` de actie om na de vertraging uit te voeren
2. Als er al een actie in uitvoering is, wordt de vorige actie geannuleerd en begint de timer opnieuw met de nieuwe actie
3. Zodra de vertraging verstrijkt zonder een andere oproep, wordt de actie uitgevoerd

De `Debouncer` draait op de UI-draad met behulp van webforJ's [`Interval`](/docs/advanced/interval) mechanisme, zodat je UI-updates niet hoeft te verpakken in `Environment.runLater()`.

:::tip Vertraging eenheden
De vertraging parameter gebruikt seconden als eenheid, niet milliseconden. Gebruik `0.3f` voor 300 ms of `1.5f` voor 1,5 seconden.
:::

## Uitvoering beheersen {#controlling-execution}

De volgende methoden kunnen worden gebruikt om de uitvoering en het gebruik van de `Debouncer` nauwkeuriger te beheren:

### Een uitstaande actie annuleren {#cancelling-a-pending-action}

Gebruik `cancel()` om een uitstaande actie te stoppen:

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// Gebruiker navigeert weg voordat het opslaan wordt uitgevoerd
debounce.cancel();
```

:::tip Annuleren van uitstaande debounces
Net als bij intervallen is het een goede gewoonte om uitstaande gedebouncte acties te annuleren wanneer een component wordt vernietigd. Dit voorkomt geheugenlekken en vermijdt fouten door acties die worden uitgevoerd op vernietigde componenten:

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

### Dwing een onmiddellijke uitvoering af {#forcing-immediate-execution}

Gebruik `flush()` om een uitstaande actie onmiddellijk uit te voeren:

```java
Debouncer debounce = new Debouncer(0.5f);

textField.onModify(e -> {
  debounce.run(() -> validateInput(textField.getText()));
});

// Dwing validatie af vóór formulierinvoer
submitButton.onClick(e -> {
  debounce.flush();
  if (isValid()) {
    submitForm();
  }
});
```

### Controleren op uitstaande status {#checking-pending-status}

Gebruik `isPending()` om te verifiëren of een actie in afwachting is om uitgevoerd te worden:

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("Bezig met verwerken...");
}
```

## Evenementniveau debouncing versus `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ biedt twee benaderingen voor debouncing:

| Kenmerk | `Debouncer` | `ElementEventOptions.setDebounce()` |
|---------|-------------|-------------------------------------|
| Bereik | Elke actie | Alleen elementevenementen |
| Locatie | Serverzijde | Clientzijde |
| Eenheid | Seconden (float) | Milliseconden (int) |
| Flexibiliteit | Volledige controle met annuleren/flush | Automatisch met evenement |

Gebruik `Debouncer` wanneer je programmatische controle over debouncing nodig hebt, zoals het annuleren of flushen van uitstaande acties. Gebruik `ElementEventOptions` wanneer je eenvoudige clientzijde debouncing wilt voor elementevenementen zonder extra serverrondes.

```java
// ElementEventOptions gebruiken voor clientzijde debouncing
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // Deze handler is gedebounced op de client
}, options);
```
