---
sidebar_position: 4
title: Navigation Events
_i18n_hash: d7beed9a9d607e1decc18fa24417b213
---
Lisäksi komponenttikohtaisille elinkaaritapahtumille voit rekisteröidä **globaalit tapahtumakuuntelijat** reitittimen tasolla. Tämä mahdollistaa navigoinnin seurannan globaalisti koko sovelluksessa, mikä on hyödyllistä lokituksessa, analytiikassa tai muissa ylittävässä asiakysymyksissä.

## Esimerkki: Globaali navigointikuuntelija {#example-global-navigation-listener}

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Navigoitu: " + location.getFullURI());
});
```

Tässä esimerkissä globaali kuuntelija rekisteröidään tallentamaan jokainen navigointitapahtuma sovelluksessa. Tämä on hyödyllistä tarkastuksia tai sivunäkymien seuraamista varten.

## Globaalien elinkaaritapahtumakuuntelijoiden rekisteröinti {#registering-global-lifecycle-event-listeners}

Globaaleja kuuntelijoita voidaan liittää erilaisiin elinkaaritapahtumiin, mukaan lukien:

- **`WillEnterEvent`**: Käynnistyy ennen kuin minkä tahansa reitin komponentti liitetään DOM:iin.
- **`DidEnterEvent`**: Käynnistyy sen jälkeen, kun komponentti on liitetty DOM:iin.
- **`WillLeaveEvent`**: Käynnistyy ennen kuin komponentti irrotetaan DOM:ista.
- **`DidLeaveEvent`**: Käynnistyy sen jälkeen, kun komponentti on irrotettu DOM:ista.
- **`NavigateEvent`**: Käynnistyy joka kerta, kun navigointi tapahtuu.

:::tip Käyttämällä Observereita elinkaaritapahtumiin liittymiseen
Voit myös liittyä elinkaaritapahtumiin käyttäen observereita. Lisätietoja varten katso [Lifecycle Observers](./observers).
:::

## Esimerkki: Globaali `WillLeaveEvent` -kuuntelija {#example-global-willleaveevent-listener}

```java
Router.getCurrent().addWillLeaveListener(event -> {
  ConfirmDialog.Result result = showConfirmDialog(
      "Oletko varma, että haluat poistua tästä näkymästä?",
      "Poistu näkymästä",
      ConfirmDialog.OptionType.OK_CANCEL,
      ConfirmDialog.MessageType.WARNING);

  event.veto(result == ConfirmDialog.Result.CANCEL);
});
```

Tässä tapauksessa `WillLeaveEvent` -tapahtumaa käytetään globaalisti näyttämään vahvistusdialogi ennen kuin käyttäjä navigoi pois mistä tahansa näkymästä.
