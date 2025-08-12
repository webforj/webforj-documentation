---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: 2c66b4194e4d93a762d9a8cd75918e49
---
Observers allow components to react to lifecycle events by implementing interfaces for specific stages. This pattern ensures a clean separation of concerns and simplifies handling navigation logic.

## Saatavilla olevat observaatit {#available-observers}

- **`WillEnterObserver`**: Mahdollistaa tehtävien käsittelyn ennen reitin sisäänkäyntiä, kuten tarvittavan tiedon hakemisen tai navigoinnin estämisen.
- **`DidEnterObserver`**: Ihanteellinen toimien käsittelyyn sen jälkeen, kun komponentti on liitetty, kuten tietojen renderöimiseen tai animaatioiden laukaisemiseen.
- **`WillLeaveObserver`**: Tarjoaa tavan hallita logiikkaa ennen kuin käyttäjä siirtyy reitiltä, kuten tallentamattomien muutosten tarkistamiseen.
- **`DidLeaveObserver`**: Käytetään siivoustoimiin tai muihin tehtäviin, jotka tulisi suorittaa sen jälkeen, kun komponentti on irrotettu DOMista.

## Esimerkki: autentikointi `WillEnterObserver` kanssa {#example-authentication-with-willenterobserver}

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

Tässä `onWillEnter` tarkistaa, onko käyttäjä autentikoitu. Jos ei, navigointi estetään, mikä estää navigoinnin suorittamisen ja ohjaa sen sijaan kirjautumissivulle.

:::warning Esimerkki autentikoiduista reiteistä - Ei tuotantokäyttöön
Tämä edellinen on vain esimerkki siitä, miten käyttää autentikoituja reittejä.
Tämä **ei ole** esimerkki siitä, miten kirjoitat tuotantotason autentikointijärjestelmän.
Sinun on otettava käyttöön tässä esimerkissä käytetyt käsitteet ja mallit ja mukautettava ne toimimaan sovelluksesi autentikointivirrassa/järjestelmässä.
:::

## Esimerkki: tietojen hakeminen reitin sisäänkäynnillä `DidEnterObserver` kanssa {#example-fetching-data-on-route-entry-with-didenterobserver}

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
    // Koodi käyttöliittymän päivittämiseen profiilitiedoilla
  }
}
```

Tässä esimerkissä käytetään `DidEnterObserveria` tietojen hakemiseen ja näyttämiseen profiilista, kun komponentti on liitetty DOM:iin.

## Esimerkki: tallentamattomien muutosten käsittely `WillLeaveObserver` kanssa {#example-handling-unsaved-changes-with-willleaveobserver}

```java
@Route(value = "edit-profile")
public class EditProfileView extends Composite<Div> implements WillLeaveObserver {
  private boolean hasUnsavedChanges = false;

  public EditProfileView() {
    // Logiikka tallentamattomien muutosten havaitsemiseksi
  }

  @Override
  public void onWillLeave(WillLeaveEvent event, ParametersBag parameters) {
    event.veto(hasUnsavedChanges);

    if(hasUnsavedChanges) {
      ConfirmDialog.Result result = showConfirmDialog(
          "Tallentamattomia muutoksia on olemassa. Haluatko hylätä tai tallentaa ne?",
          "Tallentamattomat muutokset",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

Tässä esimerkissä `onWillLeave` kysyy käyttäjältä vahvistusta, jos tallentamattomia muutoksia on, estäen navigoinnin, jos käyttäjä päättää jäädä.

:::info Navigoinnin estäminen ja vetoaminen
Lisätietoja navigoinnin estämisestä, katso [Navigoinnin estäminen ja vetoaminen](./navigation-blocking)
:::

## Esimerkki: Siivous `DidLeaveObserver` kanssa {#example-cleanup-with-didleaveobserver}

```java
@Route(value = "notifications")
public class NotificationsView extends Composite<Div> implements DidLeaveObserver {

  @Override
  public void onDidLeave(DidLeaveEvent event, ParametersBag parameters) {
    NotificationService.clearActiveNotifications();
  }
}
```

Tässä esimerkissä tyhjennetään ilmoitukset sen jälkeen, kun käyttäjä poistuu `NotificationsView`-näkymästä, käyttäen `DidLeaveObserveria` siivoukseen.
