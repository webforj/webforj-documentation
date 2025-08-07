---
sidebar_position: 4
title: Route Navigation
_i18n_hash: cf1f9e79aa81f240306313a7c0c5a9c4
---
WebforJ:ssa reitillä navigointi on keskeinen mekanismi näkymien ja komponenttien vaihtamiseksi käyttäjän toimien tai URL-muutosten perusteella. Navigointi mahdollistaa käyttäjien siirtyä sujuvasti sovelluksen eri osien välillä ilman sivun päivitystä. Tämä asiakaspuolen navigointi pitää sovelluksen reagoivana ja sujuvana samalla kun säilyttää sovelluksen tilan.

## Ohjelmallinen navigointi {#programmatic-navigation}

Voit laukaista navigoinnin mistä tahansa sovelluksessasi käyttämällä `Router`-luokkaa. Tämä mahdollistaa dynaamiset muutokset näytettävissä komponenteissa tapahtumien, kuten painikkeen napsautusten tai muiden käyttäjävuorovaikuttamisten perusteella.

Tässä on esimerkki siitä, kuinka navigoida tiettyyn reittiin:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Komponenttilogiikkaa täällä
}
```

```java
// navigoi näkymään
Router.getCurrent().navigate(DashboardView.class);
```

Tässä esimerkissä navigointi `DashboardView`-komponenttiin ohjelmallisesti aiheuttaa `DashboardView`-komponentin renderöinnin ja selaimen URL:n päivittymisen osoitteeksi `/dashboard`.

On myös mahdollista navigoida näkymään välittämällä uusi `Location`

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Luokka vs. Sijainti: Menetelmät näkymien reitittämiseen
Kun navigoidaan näkymien välillä, kehittäjillä on kaksi vaihtoehtoa: he voivat joko välittää näkymä- tai reittiluokan, jolloin reititin luo automaattisesti URL:n ja renderöi näkymän, tai välittää sijainnin suoraan. Molemmat menetelmät ovat voimassa, mutta **näkymäluokan käyttäminen on suosittu lähestymistapa** koska se tarjoaa paremman joustavuuden tulevia muutoksia varten. Esimerkiksi, jos päätät myöhemmin päivittää reitin, sinun tarvitsee vain muuttaa `@Route`-annotaatiota ilman, että joudut muuttamaan mitään koodia, joka käyttää navigointiin näkymäluokkaa.
:::

### Navigointi parametreilla {#navigation-with-parameters}

Kun sinun täytyy välittää parametreja reitin mukana, webforJ sallii parametrien upottamisen URL:iin. Näin voit navigoida reittiin parametrien kanssa:

```java
@Route("user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {
  H1 title = new H1();

  public UserProfileView() {
    getBoundComponent().add(title);
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
// navigoi näkymään ja välitä käyttäjän id
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Tämä navigoi osoitteeseen `/user/JohnDoe`, jossa `JohnDoe` voi edustaa käyttäjän ID:tä. Tämän reitin komponentti voi sitten poimia parametrin ja käyttää sitä asianmukaisesti.

## Luotu näkymäinstanssi {#created-view-instance}

`navigate`-menetelmä hyväksyy Java `Consumer`in, joka kutsutaan, kun navigointi on valmis. `Consumer` saa luodun näkymäkomponentin instanssin, joka on kääritty java `Optional`:iin, jolloin kehittäjä voi vuorovaikuttaa näkymän kanssa onnistuneen navigoinnin jälkeen.

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
Kuluttaja saa Java `Optional`:in komponentista, koska se voi olla `null` tai ei ole luotu erilaisista syistä. Esimerkiksi komponenttia ei ehkä renderöidä, jos navigointihavainnot estävät navigoinnin ja pysäyttävät prosessin.
:::

## Navigointivaihtoehdot {#navigation-options}

`NavigationOptions`-luokka mahdollistaa kehittäjille hienosäätää, kuinka navigointi käsitellään sovelluksessa. Määrittämällä erityisiä vaihtoehtoja voit hallita navigoinnin käyttäytymistä, kuten päivitettäisiinkö selaimen historiaa, kutsutaanko elinkaaren havaitsijoita tai jopa laukaistaanko navigointitapahtumia.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Navigointivaihtoehtojen asettaminen {#setting-navigation-options}

`NavigationOptions`-luokka tarjoaa useita menetelmiä navigoinnin käyttäytymisen mukauttamiseen. Näihin kuuluu reittien käsittelyn hallinta, havaitsijoiden ilmoittaminen ja selaimen historian päivittäminen.

Tässä ovat pääasialliset konfiguraatioasetukset, jotka ovat saatavilla `NavigationOptions`:issa:

1. **Navigointityyppi (`setNavigationType`)**  
   Tämä vaihtoehto määrittää, lisätäänkö uusi reitti selaimen historiaan vai korvataanko nykyinen reitti.

   - **`PUSH`**: Lisää uuden reitin historiaan, säilyttäen nykyisen sijainnin.
   - **`REPLACE`**: Korvataan nykyinen reitti historian pinossa uudella sijainnilla, estäen takaisinpainikkeen siirtymisen edelliseen reittiin.

2. **Tuli tapahtumat (`setFireEvents`)**  
   Määrittää, laukaistaanko navigoinnin aikana [elinkaaritapahtumia](./navigation-lifecycle/navigation-events). Oletuksena tämä on asetettu `true`:ksi, ja tapahtumia lauotaan. Jos se asetetaan `false`:ksi, tapahtumia ei lauota, mikä on hyödyllistä hiljaiseen navigointiin.

3. **Kutsu havaitsijoita (`setInvokeObservers`)**  
   Tämä lippu hallitsee, laukoitutko navigointi [havaitsijat](./navigation-lifecycle/observers) navigoiduissa komponenteissa. Havaitsijat käsittelevät yleensä tapahtumia, kuten reitin syöttö tai lähtö. Asettaminen `false`:ksi estää havaitsijoita tulemasta kutsutuiksi.

4. **Päivitä historia (`setUpdateHistory`)**  
   Kun tämä on asetettu `false`:ksi, tämä vaihtoehto estää historian sijainnin päivittämisen. Tämä on hyödyllistä, kun haluat vaihtaa näkymää vaikuttamatta selaimen takaisin- tai eteenpäin navigaatioon. Se vaikuttaa vain historian hallintaan, ei komponentin elinkaaren tai reitin käsittelyyn.

5. **Tilaobjekti (`setState`)**  
   [Tilaobjekti](./state-management#saving-and-restoring-state-in-browser-history) sallii lisätiedon siirtämisen selaimen historian päivittämisen yhteydessä. Tämä objekti tallennetaan selaimen historian tilaan ja sitä voidaan käyttää myöhemmin mukautettuihin tarkoituksiin, kuten sovelluksen tilan tallentamiseen navigoinnin aikana.

## Sijaintien luominen näkymille {#generating-locations-for-views}

Reititin voi luoda sijaintia näkymille reittimallin perusteella, joka on määritelty näkymässä. Voit myös antaa lisäparametreja dynaamisiin ja pakollisiin segmentteihin URL:issa. Tämä voi olla hyödyllistä linkkien muodostamisessa tai suoran pääsyn tarjoamisessa tiettyihin näkymiin sovelluksessa.

Tässä on esimerkki `Location`:n luomisesta näkymäluokan ja reittiparametrien perusteella:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Tämä luo `Location`-objektin polulla `/user/JohnDoe`, täydellinen URI merkkijonona.
