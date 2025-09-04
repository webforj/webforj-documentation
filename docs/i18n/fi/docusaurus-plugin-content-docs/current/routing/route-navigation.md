---
sidebar_position: 4
title: Route Navigation
_i18n_hash: 2ca468b09b2ae9e2ab3813119d31bf44
---
In webforJ, reitittäminen eri reittien välillä on keskeinen mekanismi näkymien ja komponenttien vaihtamiseen käyttäjän toimien tai URL-muutosten perusteella. Navigointi mahdollistaa käyttäjien siirtymisen saumattomasti sovelluksen eri osien välillä ilman sivun päivittämistä. Tämä asiakaspuolen navigointi pitää sovelluksen responsiivisena ja sujuvana samalla kun se säilyttää sovelluksen tilan.

## Ohjelmallinen navigointi {#programmatic-navigation}

Voit laukaista navigoinnin mistä tahansa sovelluksessasi käyttämällä `Router`-luokkaa. Tämä mahdollistaa dynaamiset muutokset näytettävissä komponenteissa tapahtumien, kuten painikkeiden napsautusten tai muiden käyttäjäinteraktioiden, perusteella.

Tässä on esimerkki siitä, miten navigoida tiettyyn reittiin:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Komponenttilogikka tässä
}
```

```java
// navigoi näkymään
Router.getCurrent().navigate(DashboardView.class);
```

Tässä esimerkissä ohjelmallinen navigointi `DashboardView`-komponenttiin saa aikaan sen renderöinnin ja selaimen URL:n päivittämisen kohdassa `/dashboard`.

On myös mahdollista navigoida näkymään välittämällä uusi `Location`

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Luokka vs. Sijainti: Menetelmät näkymän reitittämiseen
Kun navigoidaan näkymien välillä, kehittäjillä on kaksi vaihtoehtoa: he voivat joko välittää näkymän tai reittiluokan, jolloin reititin voi automaattisesti luoda URL-osoitteen ja renderoida näkymän, tai välittää sijainti suoraan. Molemmat menetelmät ovat voimassa, mutta **näkymäluokan käyttäminen on suositeltavaa**, koska se tarjoaa paremman joustavuuden tuleville muutoksille. Esimerkiksi, jos päätät myöhemmin päivittää reittiä, sinun tarvitsee vain muuttaa `@Route`-annotaatiota ilman tarvetta muuttaa mitään koodia, joka käyttää näkymäluokkaa navigointiin.
:::

### Navigointi parametreilla {#navigation-with-parameters}

Kun sinun täytyy välittää parametreja reitin mukana, webforJ sallii parametrien upottamisen URL-osoitteeseen. Tässä on, miten voit navigoida reitille parametrien kanssa:

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

Tämä navigoi osoitteeseen `/user/JohnDoe`, jossa `JohnDoe` voi edustaa käyttäjän ID:tä. Tämän reitin komponentti voi sitten purkaa parametrin ja käyttää sitä tarpeen mukaan.

## Luotu näkymäinstanssi {#created-view-instance}

`navigate`-metodi hyväksyy Java `Consumer` -olion, joka kutsutaan navigoinnin valmistuttua. `Consumer` vastaanottaa luodun näkymäkomponentin instanssin, joka on kääritty java `Optional`-olioon, jolloin kehittäjä voi olla vuorovaikutuksessa näkymän kanssa onnistuneen navigoinnin jälkeen.

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
Kuluttaja vastaanottaa Java `Optional`-olion komponentille, koska se voi olla `null`, tai sitä ei ole luotu eri syistä. Esimerkiksi komponenttia ei ehkä renderöidä, jos navigointiseurannat estävät navigoinnin ja pysäyttävät prosessin.
:::

## Navigointivaihtoehdot {#navigation-options}

`NavigationOptions`-luokka mahdollistaa kehittäjien hienosäätää, kuinka navigointia käsitellään sovelluksessa. Asettamalla erityisiä vaihtoehtoja voit hallita navigoinnin käyttäytymistä, kuten sitä, päivitetäänkö selaimen historiaa, kutsutaanko elinkaariseurannat tai jopa laukaistaan navigointitapahtumia.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Navigointivaihtoehtojen asettaminen {#setting-navigation-options}

`NavigationOptions`-luokka tarjoaa useita menetelmiä navigointikäyttäytymisen mukauttamiseksi. Näihin kuuluu reittien käsittelyn hallinta, ilmoitetaanko seurannat ja miten selaimen historia päivitetään.

Tässä ovat pääasialliset konfigurointivaihtoehdot, jotka ovat saatavilla `NavigationOptions`-luokassa:

1. **Navigointityyppi (`setNavigationType`)**  
   Tämä vaihtoehto määrittelee, tulisiko uusi reitti lisätä selaimen historiaan vai korvata nykyinen reitti.

   - **`PUSH`**: Lisää uusi reitti historian pinoon, säilyttäen nykyisen sijainnin.
   - **`REPLACE`**: Korvää nykyisen reitin historian pinossa uudella sijainnilla, estäen takaisinpainikkeen käyttämisen edelliseen reittiin palaamiseksi.

2. **Tuli tapahtumia (`setFireEvents`)**  
   Määrää, tuleeko navigoinnin aikana laukaista [elinkaaritapahtumia](./navigation-lifecycle/navigation-events). Oletuksena tämä on asetettu `true`:ksi, ja tapahtumia lauotaan. Jos asetetaan `false`:ksi, tapahtumia ei lauota, mikä on hyödyllistä hiljaiselle navigoinnille.

3. **Kutsu seurannat (`setInvokeObservers`)**  
   Tämä lippu hallitsee, laukaiseeko navigointi [seurannat](./navigation-lifecycle/observers) navigoiduissa komponenteissa. Seurannat käsittelevät tyypillisesti tapahtumia, kuten reitin sisääntuloa tai poistumista. Asettamalla tämä `false`:ksi estetään seurantojen kutsuminen.

4. **Päivitä historia (`setUpdateHistory`)**  
   Kun tämä on asetettu `false`:ksi, tämä vaihtoehto estää historian sijaintia päivittymästä. Tämä on hyödyllistä, kun haluat muuttaa näkymää ilman, että se vaikuttaa selaimen takaisin- tai eteenpäin-navigointiin. Se vaikuttaa vain historian hallintaan, ei komponentin elinkaaren tai reitin käsittelyyn.

5. **Tilaobjekti (`setState`)**  
   [Tilaobjekti](./state-management#saving-and-restoring-state-in-browser-history) mahdollistaa lisätiedon välittämisen selaimen historian päivittämisen yhteydessä. Tämä objekti tallennetaan selaimen historian tilaan ja sitä voidaan käyttää myöhemmin erityisiin tarkoituksiin, kuten sovelluksen tilan tallentamiseen navigoinnin aikana.

## Reittien sijaintojen generoiminen {#generating-locations-for-views}

Reititin voi generoida sijainnin näkymille perustuen reittimalliin, joka on määritelty näkymässä. Voit myös antaa lisäparametreja dynaamisille ja pakollisille osille URL-osoitteessa. Tämä voi olla hyödyllistä linkkien rakentamisessa tai suoran pääsyn tarjoamisessa tiettyihin näkymiin sovelluksessa.

Tässä on, miten generoida `Location` näkymäluokan ja reittiparametrien perusteella:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Tämä tuottaa `Location`-objektin polulla `/user/JohnDoe`, täysin URI:n merkkijonona.
