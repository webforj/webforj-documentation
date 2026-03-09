---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: a584e996523ba2b98ecb9d7ab2f366f3
---
Observers allow components to react to lifecycle events by implementing interfaces for specific stages. This pattern ensures a clean separation of concerns and simplifies handling navigation logic.

## Saatavilla olevat observat
- **`WillEnterObserver`**: Antaa sinun käsitellä tehtäviä ennen reitin sisään menemistä, kuten tarvittavien tietojen hakemista tai navigoinnin estämistä.
- **`DidEnterObserver`**: Ihanteellinen toimintojen käsittelyyn komponentin kiinnittämisen jälkeen, kuten tietojen renderöinnissä tai animaatioiden laukaisemisessa.
- **`WillLeaveObserver`**: Tarjoaa tavan hallita logiikkaa ennen kuin käyttäjä poistuu reitiltä, kuten tarkistaakseen tallentamattomat muutokset.
- **`DidLeaveObserver`**: Käytetään siivoustoimiin tai muihin tehtäviin, jotka tulisi suorittaa komponentin irrotuksen jälkeen DOM:ista.
- **`ActivateObserver`**: <DocChip chip='since' label='25.03' /> Laukaisee, kun välimuistissa oleva komponentti aktivoidaan uudelleen, esimerkiksi siirryttäessä samaan reittiin eri parametreilla.

## Esimerkki: todennus `WillEnterObserver` avulla {#example-authentication-with-willenterobserver}

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

Tässä `onWillEnter` tarkistaa, onko käyttäjä todennettu. Jos ei, navigointi estetään, jolloin siirto nykyisestä reitistä keskeytetään ja ohjataan kirjautumissivulle.

:::warning Esimerkki todennetuista reiteistä - ei tuotantovalmiina
Tämä edellinen on vain esimerkki todennettujen reittien käytöstä.
Tämä **ei ole** esimerkki siitä, miten kirjoitat tuotantotason todennusjärjestelmän.
Sinun on otettava tämän esimerkin käsitteet ja mallit käyttöön ja mukautettava ne toimimaan oman todennusvirtaus/järjestelmäsi kanssa sovelluksessasi.
:::

## Esimerkki: tietojen hakeminen reittiin siirryttäessä `DidEnterObserver` avulla {#example-fetching-data-on-route-entry-with-didenterobserver}

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
    // Koodi päivitykseen UI:lle profiilitiedoilla
  }
}
```

Tämä esimerkki osoittaa `DidEnterObserver` käytön tietojen hakemiseen ja näyttämiseen profiilitiedot komponentin kiinnittämisen jälkeen DOM:iin.

## Esimerkki: tallentamattomien muutosten käsittely `WillLeaveObserver` avulla {#example-handling-unsaved-changes-with-willleaveobserver}

```java
@Route(value = "edit-profile")
public class EditProfileView extends Composite<Div> implements WillLeaveObserver {
  private boolean hasUnsavedChanges = false;

  public EditProfileView() {
    // Logiikka tallentamattomien muutosten havaitsemiseen
  }

  @Override
  public void onWillLeave(WillLeaveEvent event, ParametersBag parameters) {
    event.veto(hasUnsavedChanges);

    if(hasUnsavedChanges) {
      ConfirmDialog.Result result = showConfirmDialog(
          "On tallentamattomia muutoksia. Haluatko hylätä ne tai tallentaa ne?",
          "Tallentamattomat muutokset",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

Tässä esimerkissä `onWillLeave` kysyy käyttäjältä vahvistusvalintaikkunalla, jos on tallentamattomia muutoksia, estäen navigoinnin, jos käyttäjä päättää jäädä.

:::info Navigoinnin estäminen ja estäminen
Lisätietoa navigoinnin estämisestä, katso [Navigoinnin estäminen ja estäminen](./navigation-blocking)
:::

## Esimerkki: Siivous `DidLeaveObserver` avulla {#example-cleanup-with-didleaveobserver}

```java
@Route(value = "notifications")
public class NotificationsView extends Composite<Div> implements DidLeaveObserver {

  @Override
  public void onDidLeave(DidLeaveEvent event, ParametersBag parameters) {
    NotificationService.clearActiveNotifications();
  }
}
```

Tämä esimerkki tyhjentää ilmoitukset sen jälkeen, kun käyttäjä poistuu `NotificationsView`-näkymästä, käyttäen `DidLeaveObserver` siivoukselle.

## Esimerkki: Datan päivittäminen `ActivateObserver` avulla <DocChip chip='since' label='25.03' /> {#example-refreshing-data-with-activateobserver}

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

Tämä esimerkki osoittaa `ActivateObserver`-käytön tietojen päivittämiseen, kun siirrytään samaan reittiin eri parametreilla. Komponentti pysyy välimuistissa ja aktivoituu uudelleen sen sijaan, että se luotaisiin uudelleen, joten UI päivittyy näyttämään oikeat tiedot nykyisille parametreille ilman uuden komponentin instansointia.

:::tip Aktivointi komponenttihierarkioissa
Kun navigoidaan reittiin, `Activate`-tapahtuma laukaisee **kaikille välimuistissa oleville komponenteille hierarkiassa**, jotka pysyvät nykyisellä polulla. Esimerkiksi, kun navigoidaan reitiltä `/products/123` reitille `/products/456`, sekä vanhempi `ProductsLayout` komponentti että lapsi `ProductView` komponentti saavat `Activate`-tapahtuman, jos ne ovat välimuistissa ja pysyvät reittihierarkiassa.
:::
