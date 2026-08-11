---
sidebar_position: 2
title: Lifecycle Observers
description: >-
  Hook into route lifecycle stages by implementing WillEnter, DidEnter,
  WillLeave, DidLeave, and Activate observer interfaces.
_i18n_hash: 3f39161991064d0d2506c0cb1dcd3503
---
Observers allow components to react to lifecycle events by implementing interfaces for specific stages. This pattern ensures a clean separation of concerns and simplifies handling navigation logic.

## Saatavilla olevat tarkkailijat {#available-observers}

- **`WillEnterObserver`**: Mahdollistaa tehtävien käsittelyn ennen reitille siirtymistä, kuten tarvittavien tietojen hakemista tai navigoinnin estämistä.
- **`DidEnterObserver`**: Ihanteellinen toimintojen käsittelyyn sen jälkeen, kun komponentti on liitetty, kuten tietojen näyttäminen tai animaatioiden käynnistäminen.
- **`WillLeaveObserver`**: Tarjoaa keinon hallita logiikkaa ennen kuin käyttäjä poistuu reitiltä, kuten tallentamattomien muutosten tarkistamisen.
- **`DidLeaveObserver`**: Käytetään siivoustoimiin tai muihin tehtäviin, jotka tulisi suorittaa sen jälkeen, kun komponentti on irrotettu DOMista.
- **`ActivateObserver`**: <DocChip chip='since' label='25.03' /> Käynnistetään, kun välimuistissa oleva komponentti aktivoidaan uudelleen, esimerkiksi siirrettäessä samaan reittiin eri parametreilla.

## Esimerkki: todennus `WillEnterObserver` kanssa {#example-authentication-with-willenterobserver}

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

Tässä `onWillEnter` tarkistaa, onko käyttäjä todennettu. Jos ei, navigointi estetään, mikä estää navigoinnin viimeistelyn ja ohjaa sen sijaan kirjautumissivulle.

:::warning Esimerkki todennetuista reiteistä - Ei tuotantokäyttöön
Tämä esimerkki on vain esimerkki siitä, kuinka käyttää todennettuja reittejä.
Tämä **ei ole** esimerkki siitä, kuinka kirjoittaa tuotantotason todennusjärjestelmä.
Sinun on otettava tässä esimerkissä käytetyt käsitteet ja mallit ja mukautettava ne toimimaan sovelluksesi todennusvirtauksen/järjestelmän kanssa.
:::

## Esimerkki: tietojen hakeminen reitille siirryttäessä `DidEnterObserver` kanssa {#example-fetching-data-on-route-entry-with-didenterobserver}

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
    // Koodi UI:n päivittämiseen profiilitietojen kanssa
  }
}
```

Tämä esimerkki havainnollistaa `DidEnterObserver` käyttöä tietojen hakemiseen ja näyttämiseen, kun komponentti on liitetty DOMiin.

## Esimerkki: Tallentamattomien muutosten käsittely `WillLeaveObserver` kanssa {#example-handling-unsaved-changes-with-willleaveobserver}

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
          "On olemassa tallentamattomia muutoksia. Haluatko hylätä tai tallentaa ne?",
          "Tallentamattomat muutokset",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

Tässä esimerkissä `onWillLeave` kehottaa käyttäjää vahvistusdialogilla, jos tallentamattomia muutoksia on, estäen navigoinnin, jos käyttäjä valitsee jäädä.

:::info Navigoinnin estäminen ja vetoaminen
Lisätietoja navigoinnin estämisestä on [Navigoinnin estäminen ja vetoaminen](./navigation-blocking)
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

Tässä esimerkissä tyhjennetään ilmoitukset sen jälkeen, kun käyttäjä poistuu `NotificationsView`:stä, käyttäen `DidLeaveObserver` siivouksen suorittamiseen.

## Esimerkki: Datan päivittäminen `ActivateObserver` kanssa <DocChip chip='since' label='25.03' /> {#example-refreshing-data-with-activateobserver}

```java
@Route(value = "product/:id")
public class ProductView extends Composite<Div> implements ActivateObserver {
  private String currentProductId;

  @Override
  public void onActivate(ActivateEvent event, ParametersBag parameters) {
    String productId = parameters.get("id").orElseThrow();

    // Komponenttia käytetään uudelleen eri parametreilla
    if (!productId.equals(currentProductId)) {
      currentProductId = productId;
      refreshProductData(productId);
    }
  }

  private void refreshProductData(String productId) {
    // Koodi uuden tuotetiedon hakemiseen ja näyttämiseen
    ProductService.fetchProduct(productId).thenAccept(
        product -> updateProductUI(product));
  }
}
```

Tämä esimerkki osoittaa `ActivateObserver` käyttöä datan päivittämiseen, kun siirrytään samaan reittiin eri parametreilla. Komponentti pysyy välimuistissa ja aktivoidaan uudelleen sen sijaan, että se luotaisiin uudelleen, joten UI päivitetään näyttämään oikeat tiedot nykyisille parametreille ilman uuden komponentin instansointia.

:::tip Aktivointi komponenttipuissa
Kun siirrytään reitille, `Activate` tapahtuma laukaistaan **kaikille välimuistiin jääneille komponenteille hierarkiassa**, jotka pysyvät nykyisellä polulla. Esimerkiksi siirryttäessä `/products/123` reitiltä `/products/456` molemmat vanhempi `ProductsLayout` komponentti ja lapsi `ProductView` komponentti vastaanottavat `Activate` tapahtuman, jos ne ovat välimuistissa ja pysyvät reitti-hierarkiassa.
:::
