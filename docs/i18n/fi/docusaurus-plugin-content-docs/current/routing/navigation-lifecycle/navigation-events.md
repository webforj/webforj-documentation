---
sidebar_position: 4
title: Navigation Events
_i18n_hash: f41ebca54f574eeac4834234cf3a0e5b
---
Lisäksi komponenttikohtaisten elinkaaritapahtumien ohella voit rekisteröidä **globaalit tapahtumakuuntelijat** reitittimen tasolla. Tämä mahdollistaa navigoinnin seuraamisen globaalisti koko sovelluksessa, mikä on hyödyllistä lokitusta, analytiikkaa tai muita poikkileikkavia huolenaiheita varten.

## Esimerkki: Globaali navigointikuuntelija {#example-global-navigation-listener}

```java
Router.getCurrent().addNavigateListener(event -> {
  Location location = event.getLocation();
  console().log("Navigated to: " + location.getFullURI());
});
```

Tässä esimerkissä globaalinen kuuntelija rekisteröidään lokittamaan jokainen navigointitapahtuma sovelluksessa. Tämä on hyödyllistä tarkastelua tai sivunäkymien seuraamista varten.

## Globaalien elinkaaritapahtumakuuntelijoiden rekisteröinti {#registering-global-lifecycle-event-listeners}

Globaalit kuuntelijat voidaan liittää erilaisiin elinkaaritapahtumiin, mukaan lukien:

- **`WillEnterEvent`**: Laukaisee ennen kuin minkään reitin komponentti liitetään DOM:iin.
- **`DidEnterEvent`**: Laukaisee sen jälkeen, kun komponentti on liitetty DOM:iin.
- **`WillLeaveEvent`**: Laukaisee ennen kuin komponentti irrotetaan DOM:ista.
- **`DidLeaveEvent`**: Laukaisee sen jälkeen, kun komponentti on irrotettu DOM:ista.
- **`NavigateEvent`**: Laukaisee joka kerta, kun navigointi tapahtuu.
- **`ActivateEvent`** (desde 25.03): Laukaisee, kun välimuistissa oleva komponentti aktivoidaan uudelleen.

:::tip Käyttäen Observereita elinkaaritapahtumiin liittymiseksi
Voit myös liittää elinkaaritapahtumiin käyttämällä observereita. Lisätietoja varten katso [Lifecycle Observers](./observers).
:::

## Esimerkki: Globaali `WillLeaveEvent` -kuuntelija {#example-global-willleaveevent-listener}

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

Tässä tapauksessa `WillLeaveEvent` käytetään globaalisti näyttämään vahvistusdialogi ennen kuin käyttäjä navigoi pois mistä tahansa näkymästä.
