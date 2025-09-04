---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: 18390849527056ed2780b761ae7919c1
---
Observers mahdollistavat komponenttien reagoinnin elinkaaritapahtumiin toteuttamalla rajapintoja tietyille vaiheille. Tämä malli varmistaa selkeän vastuunjaon ja yksinkertaistaa navigointilogikan käsittelyä.

## Saatavilla olevat observerit {#available-observers}

- **`WillEnterObserver`**: Mahdollistaa tehtävien käsittelyn ennen reittiin siirtymistä, kuten välttämättömien tietojen hakemisen tai navigoinnin estämisen.
- **`DidEnterObserver`**: Ihanteellinen toimien käsittelyyn sen jälkeen, kun komponentti on liitetty, kuten tietojen renderöimiseen tai animaatioiden laukaisemiseen.
- **`WillLeaveObserver`**: Tarjoaa tavan hallita logiikkaa ennen kuin käyttäjä poistuu reitiltä, kuten tallentamattomien muutosten tarkistamiseksi.
- **`DidLeaveObserver`**: Käytetään siivoustoimintoihin tai muihin tehtäviin, jotka pitäisi suorittaa sen jälkeen, kun komponentti on irrotettu DOMista.
- **`ActivateObserver`** (alkaen 25.03): Käynnistyy, kun välimuistissa oleva komponentti aktivoidaan uudelleen, kuten vieraillessa samassa reitissä eri parametreilla.

## Esimerkki: todennus `WillEnterObserver`-objektilla {#example-authentication-with-willenterobserver}

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

Tässä `onWillEnter` tarkistaa, onko käyttäjä todennettu. Jos ei, navigointi estetään, mikä estää navigoinnin päättymisen ja ohjaa sen sijaan kirjautumissivulle.

:::warning Esimerkki todennetuista reiteistä - Ei tuotantovalmiita
Tämä esimerkki on vain esimerkki siitä, kuinka käyttää todennettuja reittejä.
Tämä **ei ole** esimerkki siitä, kuinka kirjoittaa tuotantotason todennusjärjestelmä.
Sinun on otettava käyttöön tässä esimerkissä käytetyt käsitteet ja mallit ja mukautettava ne toimimaan sovelluksesi todennusvirrassa/järjestelmässä.
:::

## Esimerkki: tietojen hakeminen reitin sisäänkäynnillä `DidEnterObserver`-objektilla {#example-fetching-data-on-route-entry-with-didenterobserver}

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

Tässä esimerkissä käytetään `DidEnterObserver`-objektia tietojen hakemiseen ja näyttämiseen profiilitietojen saatuaan komponentin liitettyä DOMiin.

## Esimerkki: tallentamattomien muutosten käsittely `WillLeaveObserver`-objektilla {#example-handling-unsaved-changes-with-willleaveobserver}

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
          "Tallentamattomia muutoksia on olemassa. Haluatko hylätä vai tallentaa ne?",
          "Tallentamattomat muutokset",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

Tässä esimerkissä `onWillLeave` kehottaa käyttäjää vahvistuskeskustelulla, jos tallentamattomia muutoksia on olemassa, estäen navigoinnin, jos käyttäjä valitsee jäädä.

:::info Navigoinnin estäminen ja vetoaminen
Lisätietoja navigoinnin estämisestä, katso [Navigoinnin estäminen ja vetoaminen](./navigation-blocking)
:::

## Esimerkki: siivous `DidLeaveObserver`-objektilla {#example-cleanup-with-didleaveobserver}

```java
@Route(value = "notifications")
public class NotificationsView extends Composite<Div> implements DidLeaveObserver {

  @Override
  public void onDidLeave(DidLeaveEvent event, ParametersBag parameters) {
    NotificationService.clearActiveNotifications();
  }
}
```

Tämä esimerkki tyhjentää ilmoitukset sen jälkeen, kun käyttäjä on poistunut `NotificationsView`:stä, käyttäen `DidLeaveObserver`-objektia siivoukseen.

## Esimerkki: tietojen päivittäminen `ActivateObserver`-objektilla {#example-refreshing-data-with-activateobserver}

:::info Alkaen 25.03
`ActivateObserver` ja `ActivateEvent` ovat saatavilla ensimmäistä kertaa webforJ-version `25.03` myötä.
:::

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
    // Koodi uuden tuotetiedon hakemiseksi ja näyttämiseksi
    ProductService.fetchProduct(productId).thenAccept(
        product -> updateProductUI(product));
  }
}
```

Tässä esimerkissä käytetään `ActivateObserver`-objektia tietojen päivittämiseen, kun liikkuu samalle reitille eri parametreilla. Komponentti pysyy välimuistissa ja aktivoituu uudelleen sen sijaan, että se luotaisiin uudelleen, joten käyttöliittymä päivitetään näyttämään oikeat tiedot nykyisille parametreille ilman uuden komponentin instanssia.

:::tip Aktivointi komponenttieriteissä
Kun navigoidaan reitille, `Activate`-tapahtuma laukaisee **kaikille välimuistissa oleville komponenteille hierarkiassa**, jotka pysyvät nykyisessä polussa. Esimerkiksi navigoitaessa `/products/123` reitistä `/products/456` reitille, sekä vanhempi `ProductsLayout` -komponentti että lapsi `ProductView` -komponentti saavat `Activate`-tapahtuman, jos ne ovat välimuistissa ja pysyvät reittihierarkiassa.
:::
