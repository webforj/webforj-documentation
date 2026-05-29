---
sidebar_position: 4
title: Route Navigation
_i18n_hash: 91739f35b8d47f6e90e276623864aac4
---
webforJ:ssä reitillä navigointi on perustavanlaatuinen mekanismi, joka mahdollistaa näkymien ja komponenttien vaihtamisen käyttäjän toimien tai URL-muutosten perusteella. Navigointi mahdollistaa käyttäjien siirtymisen saumattomasti sovelluksen eri osien välillä sivua päivittämättä. Tämä asiakaspuolen navigointi pitää sovelluksen reagoivana ja sujuvana säilyttäen sovelluksen tilan.

## Ohjelmallinen navigointi {#programmatic-navigation}

Voit laukaista navigoinnin mistä tahansa sovelluksessasi käyttämällä `Router`-luokkaa. Tämä mahdollistaa dynaamiset muutokset näytettävissä komponenteissa tapahtumien, kuten painikkeiden klikkausten tai muiden käyttäjäinteraktioiden perusteella.

Tässä on esimerkki siitä, kuinka navigoida tiettyyn reittiin:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Komponentin logiikka tähän
}
```

```java
// navigoi näkymään
Router.getCurrent().navigate(DashboardView.class);
```

Tässä esimerkissä navigoiminen `DashboardView`-komponenttiin ohjelmallisesti aiheuttaa, että `DashboardView`-komponentti renderöidään ja selaimen URL päivitetään osoitteeseen `/dashboard`.

On myös mahdollista navigoida näkymään siirtämällä uusi `Location`

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Luokka vs. Sijainti: Näkymien reitityksen menetelmät
Kun navigoidaan näkymien välillä, kehittäjillä on kaksi vaihtoehtoa: he voivat joko siirtää näkymän tai reittiluokan, jolloin reititin luo automaattisesti URL-osoitteen ja renderöi näkymän, tai siirtää sijainnin suoraan. Molemmat menetelmät ovat päteviä, mutta **näkymäluokan käyttäminen on suositeltava lähestymistapa**, koska se tarjoaa parempaa joustavuutta tulevia muutoksia varten. Esimerkiksi, jos päätät myöhemmin päivittää reittiä, sinun tarvitsee vain muuttaa `@Route`-annotaatiota, ilman että sinun tarvitsee muuttaa mitään koodia, joka käyttää näkymäluokkaa navigointiin.
:::

### Navigointi parametreilla {#navigation-with-parameters}

Kun sinun on siirrettävä parametreja reitin mukana, webforJ sallii parametrien upottamisen URL:iin. Tässä on, kuinka voit navigoida reittiin, jossa on parametreja:

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
// navigoi näkymään ja siirtää käyttäjän id
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Tämä navigoi osoitteeseen `/user/JohnDoe`, jossa `JohnDoe` voi edustaa käyttäjän ID:tä. Komponentti tälle reitille voi sitten purkaa parametrin ja käyttää sitä tarpeen mukaan.

## Luotu näkymäinstanssi {#created-view-instance}

`navigate`-metodi hyväksyy Java `Consumer`:n, jota kutsutaan heti navigoinnin jälkeen. `Consumer` vastaanottaa luodun näkymäkomponentin instanssin, joka on kääritty java `Optional`:iin, jonka avulla kehittäjä voi vuorovaikuttaa näkymän kanssa onnistuneen navigoinnin jälkeen.

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
Kuluttaja vastaanottaa Java `Optional`:in komponentille, koska se voi olla `null` tai ei luotu eri syistä. Esimerkiksi komponenttia ei ehkä renderöidä, jos navigoinnin tarkkailijat estävät navigoinnin ja pysäyttävät prosessin.
:::

## Navigointivaihtoehdot {#navigation-options}

`NavigationOptions`-luokka mahdollistaa kehittäjien hienosäätää, miten navigointi käsitellään sovelluksessa. Asettamalla erityisiä vaihtoehtoja voit hallita navigoinnin käyttäytymistä, kuten sen, päivitetäänkö selaimen historiaa, laukaistaanko elinkaaren tarkkailijat tai jopa käynnistetäänkö navigointitapahtumia.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Navigointivaihtoehtojen asettaminen {#setting-navigation-options}

`NavigationOptions`-luokka tarjoaa useita menetelmiä navigointikäyttäytymisen mukauttamiseksi. Nämä sisältävät sen, miten reittejä käsitellään, ilmoitetaanko tarkkailijoille ja miten selaimen historia päivitetään.

Tässä ovat pääasialliset konfigurointivaihtoehdot, jotka ovat saatavilla `NavigationOptions`:issa:

1. **Navigointityyppi (`setNavigationType`)**  
   Tämä vaihtoehto määrittelee, tulisiko uusi reitti lisätä selaimen historiaan vai korvata nykyinen reitti.

   - **`PUSH`**: Lisää uusi reitti historia pinoon, säilyttäen nykyisen sijainnin.
   - **`REPLACE`**: Korvataan nykyinen reitti historian pinossa uudella sijainnilla, estäen takaisin-näppäimen navigoinnin edelliseen reittiin.

2. **Tapahtumien laukaiseminen (`setFireEvents`)**  
   Määrittää, pitäisikö navigoinnin [elinkaaren tapahtumia](./navigation-lifecycle/navigation-events) laukaista navigoinnin aikana. Oletuksena tämä on asetettu `true`:ksi, ja tapahtumia laukaistaan. Jos asetetaan `false`:ksi, tapahtumia ei laukaista, mikä on hyödyllistä hiljaiselle navigoinnille.

3. **Tarkkailijoiden kutsuminen (`setInvokeObservers`)**  
   Tämä lippu hallitsee sitä, laukaiseeko navigointi [tarkkailijoita](./navigation-lifecycle/observers) navigoiduissa komponenteissa. Tarkkailijat käsittelevät yleensä tapahtumia, kuten reitin sisään- tai ulosmenoa. Asettamalla tämä `false`:ksi estetään tarkkailijoiden kutsuminen.

4. **Päivitä historia (`setUpdateHistory`)**  
   Kun tämä asetetaan `false`:ksi, tämä vaihtoehto estää historian sijainnin päivittämisen. Tämä on hyödyllistä, kun haluat muuttaa näkymää vaikuttamatta selaimen takaisin- tai eteenpäin navigointiin. Tämä vaikuttaa vain historian hallintaan, ei komponentin elinkaaren tai reitinhallinnan.

5. **Tilaobjekti (`setState`)**  
   [Tilaobjekti](./state-management#saving-and-restoring-state-in-browser-history) sallii sinun siirtää lisätietoja päivittäessäsi selaimen historiaa. Tämä objekti tallennetaan selaimen historian tilaan ja sitä voidaan käyttää myöhemmin mukautettuja tarkoituksia varten, kuten sovelluksen tilan tallentamiseen navigoinnin aikana.

## Sijaintien generoiminen näkymille {#generating-locations-for-views}

Reititin voi generoida sijainnin näkymille reittimallin perusteella, joka on määritelty näkymässä. Voit myös toimittaa lisäparametreja dynaamisille ja pakollisille segmentteille URL:issa. Tämä voi olla hyödyllistä linkkien luomisessa tai suoran pääsyn tarjoamisessa sovelluksen tiettyihin näkymiin.

Tässä on, kuinka generoida `Location` näkymäluokan ja reittiparametrien perusteella:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Tämä luo `Location`-olion, jonka polku on `/user/JohnDoe`, täydellinen URI merkkijonona.
