---
sidebar_position: 4
title: Route Navigation
_i18n_hash: 103905bf14bb1fe9f4813dfa26fd6828
---
In webforJ, reitittäminen eri reittien välillä on keskeinen mekanismi näkymien ja komponenttien vaihtamisessa käyttäjän toimien tai URL-muutosten perusteella. Navigointi mahdollistaa käyttäjien siirtyä saumattomasti sovelluksen eri osien välillä ilman sivun uudelleen lataamista. Tämä asiakaspuolen navigointi pitää sovelluksen reaktiivisena ja sujuvana samalla säilyttäen sovelluksen tilan.

## Ohjelmallinen navigointi {#programmatic-navigation}

Voit käynnistää navigoinnin mistä tahansa sovelluksestasi käyttämällä `Router`-luokkaa. Tämä mahdollistaa dynaamiset muutokset näytettävissä komponenteissa tapahtumien, kuten napin painallusten tai muiden käyttäjävuorovaikutusten perusteella.

Tässä on esimerkki siitä, kuinka navigoida tiettyyn reittiin:

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

Tässä esimerkissä navigointi `DashboardView`-komponenttiin ohjelmallisesti aiheuttaa `DashboardView`-komponentin renderöinnin ja selaimen URL-osoitteen päivityksen `/dashboard`.

On myös mahdollista navigoida näkymään siirtämällä uusi `Location`:

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Luokka vs. Sijainti: Menetelmiä Näyttöreittaukseen
Kun navigoidaan näkymien välillä, kehittäjillä on kaksi vaihtoehtoa: he voivat joko siirtää näkymän tai reittiluokan, jolloin reititin voi automaattisesti luoda URL-osoitteen ja renderöidä näkymän, tai siirtää sijainnin suoraan. Molemmat menetelmät ovat voimassa, mutta **näkymäluokan käyttäminen on suositeltavaa**, koska se tarjoaa paremman joustavuuden tulevia muutoksia varten. Esimerkiksi, jos päätät myöhemmin päivittää reitin, sinun tarvitsee vain muuttaa `@Route`-annotaatiota, ilman että sinun tarvitsee muuttaa mitään koodia, joka käyttää näkymäluokkaa navigointiin.
:::

### Navigointi parametreilla {#navigation-with-parameters}

Kun sinun tarvitsee lähettää parametreja reitin mukana, webforJ sallii parametrien upottamisen URL-osoitteeseen. Tässä on, kuinka voit navigoida reittiin parametrien kanssa:

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
// navigoi näkymään ja siirtää käyttäjän id:n
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Tämä navigoi `/user/JohnDoe`, jossa `JohnDoe` voi edustaa käyttäjän tunnusta. Tämän reitin komponentti voi sitten poimia parametrin ja käyttää sitä asiaankuuluvalla tavalla.

## Luotu näkymäinstanssi {#created-view-instance}

`navigate`-metodi hyväksyy Java `Consumer`-olion, jota kutsutaan, kun navigointi on suoritettu. `Consumer` saa luodun näkymäkomponentin instanssin, joka on kääritty Java `Optional`-olioon, jolloin kehittäjä voi vuorovaikuttaa näkymän kanssa onnistuneen navigoinnin jälkeen.

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
Kuluttaja saa Java `Optional`-komponentin, koska se voi olla `null` tai ei luotu monista syistä. Esimerkiksi komponenttia ei ehkä renderöidä, jos navigoinnin havaitsijat estävät navigoinnin ja pysäyttävät prosessin.
:::

## Navigointivaihtoehdot {#navigation-options}

`NavigationOptions`-luokka mahdollistaa kehittäjien hienosäätää, miten navigointi käsitellään sovelluksessa. Asettamalla erityisiä vaihtoehtoja voit ohjata navigoinnin käyttäytymistä, kuten päivittää selaimen historiaa, kutsua elinkaaren havaitsijoita tai jopa laukaista navigointitapahtumia.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Navigointivaihtoehtojen asettaminen {#setting-navigation-options}

`NavigationOptions`-luokka tarjoaa useita menetelmiä navigointikäyttäytymisen mukauttamiseen. Näihin kuuluu se, miten reittejä käsitellään, ilmoitetaanko havaitsijoille ja miten selaimen historiaa päivitetään.

Tässä ovat tärkeimmät konfigurointivaihtoehdot, jotka ovat saatavilla `NavigationOptions`-luokassa:

1. **Navigointityyppi (`setNavigationType`)**  
   Tämä vaihtoehto määrittelee, tulisiko uusi reitti lisätä selaimen historiaan vai korvata nykyinen reitti.

   - **`PUSH`**: Lisää uusi reitti historiaan, säilyttäen nykyisen sijainnin.
   - **`REPLACE`**: Korvataan nykyinen reitti historiassa uudella sijainnilla, estäen takaisinpainikkeen navigoinnin edelliseen reittiin.

2. **Laadi tapahtumat (`setFireEvents`)**  
   Määrittää, tulisiko navigoinnin [elinkaaritapahtumat](./navigation-lifecycle/navigation-events) laukaista navigoinnin aikana. Oletusarvoisesti tämä on asetettu `true`:ksi, ja tapahtumia laukaistaan. Jos se on asetettu `false`:ksi, tapahtumia ei laukaista, mikä on hyödyllistä hiljaiselle navigoinnille.

3. **Käynnistä havaitsijat (`setInvokeObservers`)**  
   Tämä lippu ohjaa, tulisiko navigoinnin laukaista [havaitsijat](./navigation-lifecycle/observers) navigoiduissa komponenteissa. Havaitsijat käsittelevät tyypillisesti tapahtumia, kuten reitin sisääntuloa tai lähtöä. Asettaminen tälle `false`:ksi estää havaitsijoita tulemasta kutsutuksi.

4. **Päivitä historia (`setUpdateHistory`)**  
   Kun se on asetettu `false`:ksi, tämä vaihtoehto estää historian sijainnin päivittämisen. Tämä on hyödyllistä, kun haluat vaihtaa näkymää ilman, että se vaikuttaa selaimen takaisin- tai eteenpäin navigointiin. Se vaikuttaa vain historiakäsittelyyn, ei komponentin elinkaaren tai reitin käsittelyyn.

5. **Tilaobjekti (`setState`)**  
   [Tilaobjekti](./state-management#saving-and-restoring-state-in-browser-history) mahdollistaa lisätiedon siirtämisen selaimen historian päivittämisen yhteydessä. Tämä objekti tallennetaan selaimen historian tilaan ja sitä voidaan käyttää myöhemmin mukautettuihin tarkoituksiin, kuten sovelluksen tilan tallentamiseen navigoinnin aikana.

## Sijaintien luominen näkymille {#generating-locations-for-views}

Reititin voi luoda sijainnin näkymille perustuen reittimalliin, joka on määritelty näkymässä. Voit myös antaa lisäparametreja dynaamisille ja pakollisille osille URL-osoitteessa. Tämä voi olla hyödyllistä, kun rakennat linkkejä tai jaat suoria pääsy kohtia tiettyihin näkymiin sovelluksessa.

Tässä on, kuinka luoda `Location` näkymäluokan ja reittiparametrien perusteella:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Tämä luo `Location`-objektin polulla `/user/JohnDoe`, täydellinen URI merkkijonona.
