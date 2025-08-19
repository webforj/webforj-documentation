---
sidebar_position: 4
title: Navigation Events
_i18n_hash: efd06f0c5d04fb782fc27b187d1b063d
---
Lisäksi komponenttikohtaisille elinkaaritapahtumille voit rekisteröidä **globaaleja tapahtumakuuntelijoita** reitittimen tasolla. Tämä mahdollistaa navigoinnin seuraamisen koko sovelluksessa, mikä on hyödyllistä lokitusta, analytiikkaa tai muita poikkileikkaavia huolenaiheita varten.

## Esimerkki: Globaali navigointikuuntelija {#example-global-navigation-listener}

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Navigated to: " + location.getFullURI());
});
```

Tässä esimerkissä rekisteröidään globaali kuuntelija, joka kirjaa jokaisen navigointitapahtuman sovelluksessa. Tämä on hyödyllistä tarkastelua tai sivunäkymien seuraamista varten.

## Globaalien elinkaaritapahtumakuuntelijoiden rekisteröinti {#registering-global-lifecycle-event-listeners}

Globaalit kuuntelijat voivat olla kiinnitettyinä erilaisiin elinkaaritapahtumiin, mukaan lukien:

- **`WillEnterEvent`**: Käynnistyy ennen kuin minkä tahansa reitin komponentti liitetään DOM:iin.
- **`DidEnterEvent`**: Käynnistyy sen jälkeen, kun komponentti on liitetty DOM:iin.
- **`WillLeaveEvent`**: Käynnistyy ennen kuin komponentti irrotetaan DOM:ista.
- **`DidLeaveEvent`**: Käynnistyy sen jälkeen, kun komponentti on irrotettu DOM:ista.
- **`NavigateEvent`**: Käynnistyy joka kerta, kun navigointi tapahtuu.

:::tip Observerien käyttäminen elinkaaritapahtumiin
Voit myös liittää elinkaaritapahtumiin käyttämällä observereita. Lisätietoja löytyy [Elinkaarikontrollerit](./observers).
:::

## Esimerkki: Globaali `WillLeaveEvent` kuuntelija {#example-global-willleaveevent-listener}

```java
Router.getCurrent().addWillLeaveListener(event -> {
  ConfirmDialog.Result result = showConfirmDialog(
      "Are you sure you want to leave this view?",
      "Leave View",
      ConfirmDialog.OptionType.OK_CANCEL,
      ConfirmDialog.MessageType.WARNING);

  event.veto(result == ConfirmDialog.Result.CANCEL);
});
```

Tässä tapauksessa `WillLeaveEvent` käytetään globaalisti näyttämään vahvistusdialogi ennen kuin käyttäjä navigoi pois mistään näkymästä.
