---
sidebar_position: 4
title: Route Navigation
description: >-
  Trigger client-side navigation programmatically with Router.navigate, pass
  parameters, and switch views without reloads.
_i18n_hash: c32517b16f185d4b54682b95c82d38d3
---
In webforJ, reittien välillä liikkuminen on keskeinen mekanismi näkymien ja komponenttien vaihtamiseksi käyttäjän toimien tai URL-muutosten perusteella. Navigointi mahdollistaa käyttäjien siirtymisen saumattomasti sovelluksen eri osien välillä ilman sivun lataamista uudelleen. Tämä asiakaspään navigointi pitää sovelluksen responsiivisena ja sujuvana säilyttäen sovelluksen tilan.

## Ohjelmallinen navigointi {#programmatic-navigation}

Voit laukaista navigoinnin mistä tahansa sovelluksessasi käyttämällä `Router`-luokkaa. Tämä mahdollistaa dynaamiset muutokset näytettävissä komponenteissa tapahtumien, kuten painallusten tai muiden käyttäjäinteraktioiden, perusteella.

Tässä on esimerkki siitä, miten navigoidaan tiettyyn reittiin:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Komponentin logiikka täällä
}
```

```java
// navigoi näkymään
Router.getCurrent().navigate(DashboardView.class);
```

Tässä esimerkissä navigointi `DashboardView`-komponenttiin ohjelmallisesti saa aikaan sen, että `DashboardView`-komponentti renderoidaan ja selaimen URL päivitetään `/dashboard`.

On myös mahdollista navigoida näkymään siirtämällä uusi `Location`

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Luokka vs. Location: Menetelmät näkymien reitittämiseen 
Kun navigoidaan näkymien välillä, kehittäjillä on kaksi vaihtoehtoa: he voivat joko siirtää näkymän tai reittiluokan, jolloin reititin luo automaattisesti URL:n ja renderoi näkymän, tai siirtää sijainnin suoraan. Molemmat menetelmät ovat voimassa, mutta **näkymäluokan käyttäminen on suositeltava lähestymistapa**, koska se tarjoaa paremman joustavuuden tuleville muutoksille. Esimerkiksi, jos päätät myöhemmin päivittää reittiä, sinun tarvitsee vain muuttaa `@Route`-annotaatiota, ilman että sinun tarvitsee muuttaa mitään koodia, joka käyttää näkymäluokkaa navigointiin.
:::

### Navigointi parametreilla {#navigation-with-parameters}

Kun tarvitset siirtää parametreja reitin mukana, webforJ mahdollistaa parametrien upottamisen URL:ään. Tässä on, miten voit navigoida reittiin parametreilla:

```java
@Route("user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {
  private final Div self = getBoundComponent();
  H1 title = new H1();

  public UserProfileView() {
    self.add(title);
  }

  public void setTile(String title) {
    this.title.setText(title);
  }

  public String getTitle() {
    return title.getText();
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String id = parameters.getAlpha("id").orElse("Tuntematon");
    setTile(id);
  }
}
```

```java
// navigoi näkymään ja siirrä käyttäjän id
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Tämä navigoi `/user/JohnDoe`, jossa `JohnDoe` voi edustaa käyttäjän ID:tä. Tämän reitin komponentti voi sitten purkaa parametrin ja käyttää sitä tarpeen mukaan.

## Luotu näkymäinstanssi {#created-view-instance}

`navigate`-menetelmä hyväksyy Java `Consumer`:in, jota kutsutaan, kun navigointi on valmis. `Consumer` saa luodun näkymäkomponentin instanssin, joka on pakattu java `Optional`:iin, jolloin kehittäjä voi vuorovaikuttaa näkymän kanssa onnistuneen navigoinnin jälkeen.

```java
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe"), (component) -> {
    component.ifPresent(view -> {
      console().log("Uusi otsikko on: " + view.getTitle());
    });
  });
```

:::info Null-instanssit 
Kuluttaja saa Java `Optional`:n komponentille, koska se saattaa olla `null` tai sitä ei ole luotu eri syistä. Esimerkiksi komponenttia ei ehkä renderöidä, jos navigoinnin tarkkailijat estävät navigoinnin ja pysäyttävät prosessin.
:::

## Navigointivaihtoehdot {#navigation-options}

`NavigationOptions`-luokka mahdollistaa kehittäjille navigoinnin käsittelyn hienosäätämisen sovelluksessa. Asettamalla erityisiä vaihtoehtoja voit hallita navigoinnin käyttäytymistä, kuten selaimen historian päivitystä, elinkaaritarkkailijoiden kutsumista tai jopa navigointitapahtumien laukaiselemista.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Navigointivaihtoehtojen asettaminen {#setting-navigation-options}

`NavigationOptions`-luokka tarjoaa useita menetelmiä navigointikäyttäytymisen mukauttamiseksi. Näihin kuuluvat reittien käsittelyn hallinta, tarkkailijoiden ilmoittaminen ja selaimen historian päivittämisen hallinta.

Tässä ovat pääkonfiguraatio-optioita, jotka ovat käytettävissä `NavigationOptions`-luokassa:

1. **Navigointityyppi (`setNavigationType`)** 
   Tämä vaihtoehto määrittelee, lisätäänkö uusi reitti selaimen historiaan vai korvataanko nykyinen reitti.

   - **`PUSH`**: Lisää uuden reitin historiaan, säilyttäen nykyisen sijainnin.
   - **`REPLACE`**: Korvataan nykyinen reitti historiassa uudella sijainnilla, estäen takaisin-painikkeen navigoimisen edelliseen reittiin.

2. **Tapahtumien laukaiseminen (`setFireEvents`)** 
   Määrittää, laukaistaanko navigoinnin [elinkaaritapahtumat](./navigation-lifecycle/navigation-events) navigoinnin aikana. Oletusarvoisesti tämä on asetettu `true`, ja tapahtumat laukaistaan. Jos se asetetaan `false`:ksi, ei tapahtumia laukaista, mikä on hyödyllistä hiljaisessa navigoinnissa.

3. **Tarkkailijoiden kutsuminen (`setInvokeObservers`)** 
   Tämä lippu hallitsee, laukaiseeko navigointi [tarkkailijoita](./navigation-lifecycle/observers) navigoiduissa komponenteissa. Tarkkailijat käsittelevät tyypillisesti tapahtumia, kuten reittien sisään- tai uloskäyntiä. Asettamalla tämä `false`:ksi estää tarkkailijoiden kutsumisen.

4. **Historian päivittäminen (`setUpdateHistory`)** 
   Kun tämä asetetaan `false`:ksi, tämä vaihtoehto estää historian sijainnin päivittämisen. Tämä on hyödyllistä, kun haluat muuttaa näkymää vaikuttamatta selaimen taakse- tai eteenpäin navigointiin. Se vaikuttaa vain historian hallintaan, ei komponenttien elinkaaren tai reitinhallinnan.

5. **Tilaobjekti (`setState`)** 
   [Tilaobjekti](./state-management#saving-and-restoring-state-in-browser-history) mahdollistaa lisätietojen siirtämisen selaimen historian päivittämisen yhteydessä. Tämä objekti tallennetaan selaimen historian tilaan ja sitä voidaan käyttää myöhemmin mukautettuihin tarkoituksiin, kuten sovelluksen tilan tallentamiseen navigoinnin aikana.

## Sijaintien generointi näkymille {#generating-locations-for-views}

Reititin voi generoida sijainnin näkymille reittimallin perusteella, joka on määritelty näkymässä. Voit myös antaa lisäparametreja dynaamisille ja vaadituille segmentoille URL:ssä. Tämä voi olla hyödyllistä linkkien rakentamisessa tai suoran pääsyn tarjoamisessa sovelluksen tiettyihin näkymiin.

Tässä on, miten generoida `Location` näkymäluokan ja reittiparametrien perusteella:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Tämä generoi `Location`-objektin polulla `/user/JohnDoe`, täydellinen URI merkkijonona.
