---
sidebar_position: 21
title: Debouncing
slug: debouncing
sidebar_class_name: new-content
_i18n_hash: be654f5efb68050d8632a27166954583
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

Debouncing is een techniek die het uitvoeren van een actie vertraagt totdat er een bepaalde tijd is verstreken sinds de laatste oproep. Elke nieuwe oproep reset de timer. Dit is handig voor scenario's zoals zoeken terwijl je typt, waar je wilt wachten tot de gebruiker stopt met typen voordat je een zoekopdracht uitvoert.

<ComponentDemo
path='/webforj/debouncer?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/debouncer/DebouncerDemoView.java'
height='265px'
/>

## Basisgebruik {#basic-usage}

De `Debouncer`-klasse biedt een eenvoudige manier om acties te debouncen. Maak een `Debouncer` aan met een vertraging in seconden en roep vervolgens `run()` aan met de actie die je wilt debouncen:

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

In dit voorbeeld wordt de `search()`-methode pas aangeroepen nadat de gebruiker 300 milliseconden is gestopt met typen. Elke toetsaanslag reset de timer via de `onModify`-gebeurtenis, zodat snel typen geen meerdere zoekopdrachten activeert.

## Hoe het werkt {#how-it-works}

Wanneer je `run()` aanroept met een actie:

1. Als er geen actie in afwachting is, plant de `Debouncer` de actie om uit te voeren na de vertraging.
2. Als er al een actie in afwachting is, wordt de vorige actie geannuleerd en wordt de timer opnieuw gestart met de nieuwe actie.
3. Zodra de vertraging verstrijkt zonder een andere oproep, wordt de actie uitgevoerd.

De `Debouncer` draait op de UI-thread met behulp van webforJ's [`Interval`](/docs/advanced/interval) mechanisme, zodat je UI-updates niet in `Environment.runLater()` hoeft te wikkelen.

:::tip Vertragingseenheden
De vertragingparameter gebruikt seconden als eenheid, niet milliseconden. Gebruik `0.3f` voor 300 ms of `1.5f` voor 1,5 seconden.
:::

## Controle over uitvoering {#controlling-execution}

De volgende methoden kunnen worden gebruikt om de uitvoering en het gebruik van de `Debouncer` nauwkeuriger te beheren:

### Een wachtende actie annuleren {#cancelling-a-pending-action}

Gebruik `cancel()` om te voorkomen dat een wachtende actie wordt uitgevoerd:

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// De gebruiker navigeert weg voordat de opslaanactie wordt uitgevoerd
debounce.cancel();
```

:::tip Annuleren van wachtende debounces
Net als bij intervallen is het goed om wachtende gedebouncte acties te annuleren wanneer een component wordt vernietigd. Dit voorkomt geheugenlekken en fouten door acties die worden uitgevoerd op vernietigde componenten:

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

### Gedwongen onmiddellijke uitvoering {#forcing-immediate-execution}

Gebruik `flush()` om een wachtende actie onmiddellijk uit te voeren:

```java
Debouncer debounce = new Debouncer(0.5f);

textField.onModify(e -> {
  debounce.run(() -> validateInput(textField.getText()));
});

// Dwing validatie af voordat het formulier wordt ingediend
submitButton.onClick(e -> {
  debounce.flush();
  if (isValid()) {
    submitForm();
  }
});
```

### Controleren van de status in afwachting {#checking-pending-status}

Gebruik `isPending()` om te verifiëren of een actie wacht op uitvoering:

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("Verwerken...");
}
```

## Debouncing op gebeurtenisniveau vs `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ biedt twee benaderingen voor debouncing:

| Functionaliteit | `Debouncer` | `ElementEventOptions.setDebounce()` |
|----------------|-------------|-------------------------------------|
| Toepassingsgebied | Elke actie | Alleen elementgebeurtenissen |
| Locatie | Serverzijde | Clientzijde |
| Eenheid | Seconden (float) | Milliseconden (int) |
| Flexibiliteit | Volledige controle met annuleren/flushing | Automatisch met evenement |

Gebruik `Debouncer` wanneer je programmacontrole nodig hebt over debouncing, zoals het annuleren of flushen van wachtende acties. Gebruik `ElementEventOptions` wanneer je eenvoudige client-side debouncing voor elementgebeurtenissen wilt zonder extra server ronde-reizen.

```java
// Gebruik van ElementEventOptions voor client-side debouncing
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // Deze handler wordt op de client gedebounced
}, options);
```
