---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: be571bd197730689ba8346b2ef702a3f
---
Observers allow components to react to lifecycle events by implementing interfaces for specific stages. This pattern ensures a clean separation of concerns and simplifies handling navigation logic.

## Saatavilla olevat observaatit {#available-observers}

- **`WillEnterObserver`**: Mahdollistaa tehtävien käsittelemisen ennen reitille siirtymistä, kuten tarvittavien tietojen noutamisen tai navigoinnin estämisen.
- **`DidEnterObserver`**: Ihanteellinen toimien käsittelemiseen sen jälkeen, kun komponentti on liitetty, kuten tietojen renderöinti tai animaatioiden käynnistäminen.
- **`WillLeaveObserver`**: Tarjoaa tavan hallita logiikkaa ennen kuin käyttäjä poistuu reitiltä, kuten tarkistaa tallentamattomat muutokset.
- **`DidLeaveObserver`**: Käytetään siivoustoimiin tai muihin tehtäviin, jotka on suoritettava komponentin poistamisen jälkeen DOM:sta.

## Esimerkki: todennus `WillEnterObserver`-painotteisena {#example-authentication-with-willenterobserver}

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> implements WillEnterObserver {

  @Override
  public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
    boolean isAuthenticated = authService.isAuthenticated();
    event.veto(!isAuthenticated);

    if (!isAuthenticated) {
      event.getRouter().navigate(LoginView.class);
    }
  }
}
```

Tässä `onWillEnter` tarkistaa, onko käyttäjä todennettu. Jos ei, navigointi estetään, mikä estää navigoinnin suorittamisen ja siirtää käyttäjän kirjautumissivulle.

:::warning Esimerkki todennetuista reiteistä - Ei tuotantovalmiina
Tämä on vain esimerkki siitä, miten käyttää todennettuja reittejä.
Tämä **ei ole** esimerkki siitä, miten kirjoitat tuotantotason todennusjärjestelmän.
Sinun täytyy ottaa tämän esimerkin käyttämät käsitteet ja mallit ja sovittaa ne omiin todennusprosessiisi/järjestelmään sovelluksessasi.
:::

## Esimerkki: tietojen noutaminen reitin sisäänkäynnillä `DidEnterObserver`-painotteisena {#example-fetching-data-on-route-entry-with-didenterobserver}

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String userId = parameters.get("userId").orElseThrow();
    UserService.fetchProfile(userId).thenAccept(
        profile -> updateProfileUI(profile));
  }

  private void updateProfileUI(Profile profile) {
    // Koodi käyttöliittymän päivittämiseksi profiilitiedoilla
  }
}
```

Tässä esimerkissä käytetään `DidEnterObserver`-painotteista toimintoa tietojen noutamiseen ja näyttämiseen käyttäjäprofiilista sen jälkeen, kun komponentti on liitetty DOM:iin.

## Esimerkki: tallentamattomien muutosten käsittely `WillLeaveObserver`-painotteisena {#example-handling-unsaved-changes-with-willleaveobserver}

```java
@Route(value = "edit-profile")
public class EditProfileView extends Composite<Div> implements WillLeaveObserver {
  private boolean hasUnsavedChanges = false;

  public EditProfileView() {
    // Logiikka tallentamattomien muutosten tunnistamiseksi
  }

  @Override
  public void onWillLeave(WillLeaveEvent event, ParametersBag parameters) {
    event.veto(hasUnsavedChanges);

    if(hasUnsavedChanges) {
      ConfirmDialog.Result result = showConfirmDialog(
          "On olemassa tallentamattomia muutoksia. Haluatko hylätä vai tallentaa ne?",
          "Tallentamattomat muutokset",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

Tässä esimerkissä `onWillLeave` kysyy käyttäjältä vahvistusta, jos on olemassa tallentamattomia muutoksia, estäen navigoinnin, jos käyttäjä päättää jäädä.

:::info Navigoinnin estäminen ja vetoaminen
Lisätietoja navigoinnin estämisestä löytyy kohdasta [Navigoinnin estäminen ja vetoaminen](./navigation-blocking)
:::

## Esimerkki: Siivous `DidLeaveObserver`-painotteisena {#example-cleanup-with-didleaveobserver}

```java
@Route(value = "notifications")
public class NotificationsView extends Composite<Div> implements DidLeaveObserver {

  @Override
  public void onDidLeave(DidLeaveEvent event, ParametersBag parameters) {
    NotificationService.clearActiveNotifications();
  }
}
```

Tässä esimerkissä tyhjennetään ilmoitukset sen jälkeen, kun käyttäjä poistuu `NotificationsView`:istä, käyttäen `DidLeaveObserver`-toimintoa siivoukseen.
