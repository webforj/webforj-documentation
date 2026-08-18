---
sidebar_position: 4
title: Route Navigation
description: >-
  Trigger client-side navigation programmatically with Router.navigate, pass
  parameters, and switch views without reloads.
sidebar_class_name: updated-content
_i18n_hash: 0284f2481f307d68da728d81f4b3a6a2
---
In webforJ, navigointi reittien välillä on keskeinen mekanismi näkymien ja komponenttien vaihtamiseksi käyttäjän toimien tai URL-muutosten perusteella. Navigointi mahdollistaa käyttäjien siirtymisen saumattomasti sovelluksen eri osien välillä ilman, että sivua tarvitsisi päivittää. Tämä asiakaspuolen navigointi pitää sovelluksen responsiivisena ja sujuvana samalla, kun se säilyttää sovelluksen tilan.

## Ohjelmallinen navigointi {#programmatic-navigation}

Voit laukaista navigoinnin mistä tahansa sovelluksessasi käyttämällä `Router`-luokkaa. Tämä mahdollistaa dynaamiset muutokset näytettävissä komponenteissa tapahtumien, kuten painikkeen napsautusten tai muiden käyttäjävuorovaikutusten perusteella.

Tässä on esimerkki siitä, miten navigoida tiettyyn reittiin:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Komponenttilogiikka tähän
}
```

```java
// navigoi näkymään
Router.getCurrent().navigate(DashboardView.class);
```

Tässä esimerkissä navigointi `DashboardView`-komponenttiin ohjelmallisesti aiheuttaa, että `DashboardView`-komponentti renderoidaan ja selaimen URL päivitetään osoitteeseen `/dashboard`.

On myös mahdollista navigoida näkymään antamalla uusi `Location`

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Luokka vs. Sijainti: Menetelmät näkymän reitittämiseen
Kun navigoidaan näkymien välillä, kehittäjillä on kaksi vaihtoehtoa: he voivat joko antaa näkymän tai reittiluokan, jolloin reititin voi automaattisesti luoda URL-osoitteen ja renderoida näkymän, tai antaa sijainti suoraan. Molemmat menetelmät ovat voimassa, mutta **näkymäluokan käyttäminen on suositeltava lähestymistapa**, koska se tarjoaa parempaa joustavuutta tulevalle muutokselle. Esimerkiksi, jos päätät myöhemmin päivittää reitin, sinun tarvitsee vain muokata `@Route`-annotaatiota ilman, että sinun tarvitsee muuttaa mitään koodia, joka käyttää näkymäluokkaa navigointiin.
:::

### Navigointi parametreilla {#navigation-with-parameters}

Kun sinun tarvitsee lähettää parametreja reitin mukana, webforJ sallii parametrien upottamisen URL-osoitteeseen. Tässä on, miten voit navigoida reittiin parametreilla:

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
// navigoi näkymään ja lähetä käyttäjän id
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Tämä navigoi osoitteeseen `/user/JohnDoe`, jossa `JohnDoe` voi edustaa käyttäjän ID:tä. Tämän reitin komponentti voi sitten poimia parametrin ja käyttää sitä asianmukaisesti.

## Luotu näkymäinstanssi {#created-view-instance}

`navigate`-menetelmä hyväksyy Java `Consumer`-tyypin, jota kutsutaan, kun navigointi on valmis. `Consumer` saa luodun näkymäkomponentin instanssin, joka on pakattu java `Optional`-tyyppiin, mikä mahdollistaa kehittäjän vuorovaikuttaa näkymän kanssa onnistuneen navigoinnin jälkeen.

```java
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe"), (component) -> {
    component.ifPresent(view -> {
      console().log("Uuden otsikon on: " + view.getTitle());
    });
  });
```

:::info Null-instanssit
Kuluttaja saa Java `Optional`-tyypin komponentille, koska se voi olla `null` tai ei luotu erilaisista syistä. Esimerkiksi komponenttia ei ehkä renderöidä, jos navigointihavainnot estävät navigoinnin ja pysäyttävät prosessin.
:::

## Navigointivaihtoehdot {#navigation-options}

`NavigationOptions`-luokka sallii kehittäjien hienosäätää, kuinka navigointi käsitellään sovelluksessa. Asettamalla erityisiä vaihtoehtoja voit hallita navigoinnin käyttäytymistä, kuten sitä, päivitetäänkö selaimen historiaa, kutsutaanko elinkaarihavainnot tai jopa laukaisiko navigointitapahtumia.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Navigointivaihtoehtojen asettaminen {#setting-navigation-options}

`NavigationOptions`-luokka tarjoaa useita menetelmiä navigointikäyttäytymisen mukauttamiseksi. Näihin kuuluu reittien käsittelyn hallinta, havainnoijien ilmoittaminen ja selaimen historian päivittämisen tapa.

Tässä ovat pääasetukset, jotka ovat saatavilla `NavigationOptions`-luokassa:

1. **Navigointityyppi (`setNavigationType`)**

   Tämä vaihtoehto määrittelee, lisätäänkö uusi reitti selaimen historiaan vai korvataanko nykyinen reitti.

   - **`PUSH`**: Lisää uusi reitti historian pinokseen, säilyttäen nykyisen sijainnin.
   - **`REPLACE`**: Korvataan nykyinen reitti historian pinossa uudella sijainnilla, estäen takaisinpainikkeen navigoimasta edelliseen reittiin.

2. **Laukaise tapahtumat (`setFireEvents`)**

   Määrittää, laukaisevatko navigoinnin [elinkaaritapahtumat](./navigation-lifecycle/navigation-events). Oletuksena tämä on asetettu `true`, ja tapahtumia laukaistaan. Jos se asetetaan `false`:ksi, tapahtumia ei laukaista, mikä on hyödyllistä hiljaiselle navigoinnille.

3. **Kutsu havainnoijia (`setInvokeObservers`)**

   Tämä lippu ohjaa sitä, laukaiseeko navigointi [havaitsijoita](./navigation-lifecycle/observers) navigoitavissa komponenteissa. Havaitsejat käsittelevät tyypillisesti tapahtumia, kuten reitin sisään- tai uloskäyntiä. Tämän asettaminen `false`:ksi estää havaitsijoiden kutsumisen.

4. **Päivitä historia (`setUpdateHistory`)**

   Kun tämä on asetettu `false`:ksi, tämä vaihtoehto estää historian sijainnin päivittämisen. Tämä on hyödyllistä, kun haluat muuttaa näkymää vaikuttamatta selaimen taaksepäin tai eteenpäin navigointiin. Se vaikuttaa vain historian hallintaan, ei komponenttielinkaaren tai reitin käsittelyn.

5. **Tilaobjekti (`setState`)**

   [Tilaobjekti](./state-management#saving-and-restoring-state-in-browser-history) sallii sinun siirtää lisätietoja päivityksen yhteydessä selaimen historiassa. Tämä objekti tallennetaan selaimen historian tilaan ja voidaan käyttää myöhemmin erityisiin tarkoituksiin, kuten sovelluksen tilan tallentamiseen navigoinnin aikana.

6. **Instanssien uudelleenluonti (`setRecreateFrom`)** <DocChip chip='since' label='26.02' />

    Kun reitti komponentti on määritetty, tämä vaihtoehto sallii navigoinnin tuhota kaikki renderöidyt instanssit kyseisestä komponentista ja sen alapuolella olevista komponenteista ennen uudelleen renderöintiä. Tämä sallii kyseisen osan hierarkiasta käyttää tuoreita instansseja ilman, että se vaikuttaa aikaisemmin renderöityihin instansseihin.

    ```java
    NavigationOptions options = new NavigationOptions()
        .setRecreateFrom(DashboardView.class);

    Router.getCurrent().navigate(
        new Location("/dashboard"), options);
    ```

    Oletusreitti `setRecreateFrom()`-metodille on `null`, mikä sallii reitittimen käyttää uudelleen renderöityjä reitti komponentteja, jotka jäävät polkuun. Jos annettua komponenttia ei ole renderöity, navigointi käyttäytyy normaalisti. Lisäksi elinkaarihavaitsija voi estää tuhon, mikä epäonnistuu navigoinnissa.

## Sijaintien luominen näkymille {#generating-locations-for-views}

Reititin voi luoda sijainteja näkymille reittimallin perusteella, joka on määritelty näkymässä. Voit myös antaa lisäparametreja dynaamisille ja pakollisille segmentoille URL-osoitteessa. Tämä voi olla hyödyllistä linkkien rakentamisessa tai suoran pääsyn tarjoamisessa erityisiin näkymiin sovelluksessa.

Tässä on, miten luoda `Location` näkymäluokan ja reittiparametrien perusteella:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Tämä luo `Location`-objektin, jonka polku on `/user/JohnDoe`, täydellinen URI merkkijonona.
