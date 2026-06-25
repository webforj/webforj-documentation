---
sidebar_position: 2
title: Bindings
sidebar_class_name: updated-content
description: >-
  Link Java Bean properties to webforJ UI components through BindingContext to
  synchronize reads and writes between model and view.
_i18n_hash: 047676a64833283bcc160d7a8d226559
---
webforJ:ssä bindaus yhdistää tietyn Java Bean -ominaisuuden käyttöliittymäkomponenttiin. Tämä yhteys mahdollistaa automaattiset päivitykset käyttöliittymän ja taustamallin välillä. Jokainen bindaus voi käsitellä tietosynkronointia, validointia, muunnoksia ja tapahtumien hallintaa.

Voit aloittaa sidokset vain `BindingContext`-kontekstin avulla. Se hallitsee kokoelmaa bindauksia, jotka kukin yhdistävät käyttöliittymäkomponentin beanin ominaisuuteen. Se helpottaa ryhmätoimia sidoksille, kuten validointia ja synkronointia käyttöliittymäkomponenttien ja beanin ominaisuuksien välillä. Se toimii aggregoijana, jolloin voit tehdä kokonaisvaltaisia toimintoja useille sidoksille, jonka ansiosta tietovirran hallinta sovelluksissa sujuvoituu.

:::tip Automaattinen sidonta
Tässä osiossa esitellään manuaalisen sidonnan perusteet. Voit myös automaattisesti luoda sidoksia käyttöliittymäkomponenteista lomakkeessasi. Kun hallitset perusasiat, voit oppia lisää lukemalla [Automaattinen sidonta](/docs/data-binding/automatic-binding) -osiota.
:::

## Konfiguroi sidokset {#configure-bindings}

Aloita luomalla uusi `BindingContext`-instanssi, joka hallitsee kaikkia sidoksia tietylle mallille. Tämä konteksti validoi ja päivittää kaikki sidokset yhdessä.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Jokaisessa lomakkeessa tulisi olla vain yksi `BindingContext`-instanssi, ja sinun tulisi käyttää tätä instanssia kaikille lomakkeen komponenteille.
:::

### Sidottu ominaisuus {#the-bound-property}

Sidottu ominaisuus on tietty kenttä tai attribuutti Java Beanissa, joka voidaan liittää käyttöliittymäkomponenttiin sovelluksessasi. Tämä yhteys mahdollistaa käyttöliittymässä tapahtuvien muutosten vaikuttavan suoraan tietomallin vastaavaan ominaisuuteen ja päinvastoin, jolloin käyttöliittymä ja tietomalli pysyvät synkronoituna.

Side-asetusta varten sinun tulee antaa ominaisuuden nimi merkkijonona. Tämän nimen on oltava sama kuin kentän nimi Java Bean -luokassa. Tässä on yksinkertainen esimerkki:

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);
context
  .bind(textField, "power")
  .add();
```

```java
public class Hero  {
  private String name;
  private String power;

  // setters and getters
}
```

`bind`-metodi palauttaa `BindingBuilder`-objektin, joka luo `Binding`-objektin, ja sitä voidaan käyttää sidonnan asetusten määrittämiseen; `add`-metodi on se, joka todellisuudessa lisää sidoksen kontekstiin.

### Sidottu komponentti {#the-bound-component}

Sidonnan toinen puoli on sidottu komponentti, joka viittaa käyttöliittymäkomponenttiin, joka on vuorovaikutuksessa Java Beanin ominaisuuden kanssa. Sidottu komponentti voi olla mikä tahansa käyttöliittymäkomponentti, joka tukee käyttäjävuorovaikutusta ja esitystä, kuten tekstikentät, yhdistävät laatikot, rastit eikä tai mikä tahansa mukautettu komponentti, joka toteuttaa `ValueAware`-rajapinnan.

Sidottu komponentti toimii käyttäjän vuorovaikutuspisteenä perustavan datamallin kanssa. Se näyttää dataa käyttäjälle ja tallentaa myös käyttäjän syötteitä, jotka sitten siirretään takaisin malliin.

```java
TextField nameTextField = new TextField("Nimi");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Datan lukeminen ja kirjoittaminen {#reading-and-writing-data}

### Datan lukeminen {#reading-data}

Datan lukeminen tarkoittaa käyttöliittymäkomponenttien täyttämistä arvoilla datamallista. Tämä tapahtuu tyypillisesti, kun lomake näytetään aluksi tai kun sinun on ladattava data uudelleen taustamallissa tapahtuneiden muutosten vuoksi. `read`-metodi, jonka `BindingContext` tarjoaa, tekee tästä prosessista suoraviivaisen.

```java
// Oletetaan, että Hero-objekti on instansioitu ja alustettu
Hero hero = new Hero("Clark Kent", "Lentäminen");

// BindingContext on jo konfiguroitu sidontasuhteilla
context.read(hero);
```

Tässä esimerkissä `read`-metodi ottaa `Hero`-instanssin vastaan ja päivittää kaikki sidotut käyttöliittymäkomponentit heimon ominaisuuksien mukaan. Jos heimon nimi tai voima muuttuu, vastaavat käyttöliittymäkomponentit (kuten `TextField` nimelle ja `ComboBox` voimalle) näyttävät nämä uudet arvot.

### Datan kirjoittaminen {#writing-data}

Datan kirjoittaminen tarkoittaa arvojen keräämistä käyttöliittymäkomponenteista ja datamallin päivittämistä. Tämä tapahtuu tyypillisesti, kun käyttäjä lähettää lomakkeen. `write`-metodi hallitsee validointia ja mallin päivitystä yhdessä vaiheessa.

```java
// Tämä voitaisiin aktivoida lomakkeen lähetyksen yhteydessä
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Data on voimassa, ja hero-objekti on päivitetty
    // repository.save(hero); 
  } else {
    // Käsittele validointivirheitä
    // results.getMessages();
  }
});
```

Yllä olevassa koodissa, kun käyttäjä napsauttaa Lähetä-painiketta, `write`-metodia kutsutaan. Se suorittaa kaikki määritetyt validoinnit ja, jos data läpäisee kaikki tarkistukset, päivittää `Hero`-objektin uusilla arvoilla sidotuista komponenteista. 
Jos data on voimassa, voit tallentaa sen tietokantaan tai käsitellä sitä edelleen. Jos on validointivirheitä, sinun tulisi käsitellä niitä asianmukaisesti, tyypillisesti näyttämällä virheilmoituksia käyttäjälle.

:::tip Validointivirheiden raportointi
Kaikilla webforJ:n ydin komponenteilla on oletusasetuksia automaattiseen validointivirheiden raportointiin, joko suoraan tai ponnahdusikkunan kautta. Voit mukauttaa tätä käyttäen [Raportteja](./validation/reporters.md).
:::

## Sisäkkäisten bean-ominaisuuksien sidonta <DocChip chip='since' label='26.01' /> {#nested-bean-properties}

Sidottu ominaisuus voi olla pisteetun polun, joka osoittaa sisäkkäisen beanin ominaisuuteen. Jokainen segmentti polussa seuraa standardeja JavaBean-haku- ja asetusmerkintöjä, joten `address.street` luetaan `getAddress().getStreet()`- ja kirjoitetaan `getAddress().setStreet()`-metodin kautta.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(streetField, "address.street").add();
context.bind(cityField, "address.city").add();
```

```java
public class Hero {
  private String name;
  private Address address;

  // getters and setters
}

public class Address {
  private String street;
  private String city;
  private String zip;

  // getters and setters
}
```

Lukiessasi polku ratkaistaan turvallisesti, vaikka välikankaan bean olisi `null`. Jos `Hero`-objekti ei ole `Address`, `address.street`- ja `address.city`-kenttien avulla sidotut komponentit lukevat tyhjää sen sijaan, että heittäisivät virhettä, jolloin lomake väijyy edelleen.

Kirjoitusprosessissa konteksti luo kaikki puuttuvat välikankaat niiden ei-argumenttikonstuktoria käyttäen, joten muotoillessasi lomaketta `Hero`-objektiin, jolla ei ole `Address`, saadaan uusi ja täytetty `Address`. Jo olemassa oleva `Address` käytetään uudelleen.

[Jakarta validointi](/docs/data-binding/validation/jakarta-validation) -annotaatiot sisäkkäisessä ominaisuudessa havaitaan samalla tavalla kuin ylimmän tason ominaisuudessa. Annotointi, kuten `@NotNull` `Address.street`:ssä, merkitsee `address.street` sidostan [vaadittavaksi](/docs/data-binding/automatic-binding#bindingrequired-annotation).

:::info Polut validoidaan etukäteen
Koko polku validoidaan, kun kutsut `bind`. Kirjoitusvirhe tai missään segmentissä, ylä- tai alempana polussa, heittää `IllegalArgumentException`-virheen, joten sidontavirheet syntyvät heti sen sijaan, että taatava luku tai kirjoitus.
:::

<!-- vale off -->
## Vain luku data {#readonly-data}
<!-- vale on -->

Tietyissä tilanteissa saatat haluta sovelluksesi näyttävän dataa ilman, että loppukäyttäjä voi muuttaa sitä suoraan käyttöliittymän kautta. Vain luku -sidonnat käsittelevät tätä. webforJ tukee sidontojen konfiguroimista vain luku -tilassa, joten voit näyttää dataa, mutta et muuttaa sitä sidottujen käyttöliittymäkomponenttien kautta.

### Vain luku -sidontojen konfigurointi {#configuring-readonly-bindings}

Asettaaksesi vain luku -sidonnan voit konfiguroida sidonteen estämään tai ohittamaan käyttöliittymäkomponentin syötteen. Tällöin data pysyy muuttumattomana käyttöliittymän näkökulmasta, mutta se päivitetään ohjelmallisesti tarvittaessa.

```java
// Konfiguroimalla tekstikenttä vain luku -tilaan sidontakontekstissa
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

Tässä konfiguraatiossa `readOnly` estää `nameTextField`:n hyväksymästä käyttäjän syötteitä, joten tekstikenttä näyttää dataa ilman, että muutoksia voidaan tehdä.

:::info
Sidonta voi merkitä komponenttia vain luku -tilaiseksi vain, jos käyttöliittymäkomponentti toteuttaa `ReadOnlyAware`-rajapinnan.
:::

:::tip Komponentin vain luku vs Sidonnan vain luku
On tärkeää erottaa sidonnat, jotka määrität vain luku -tilassa, ja käyttöliittymäkomponentit, jotka asetat näyttämään vain lukuna. Kun merkitset sidonnan vain luku -tilaksi, se vaikuttaa sidontaprosessiin ja datan hallintaan kirjoitusprosessin aikana, ei vain käyttöliittymän käyttäytymiseen.

Kun merkitset sidonnan vain luku -tilaksi, järjestelmä ohittaa datan päivitykset. Kaikki muutokset käyttöliittymäkomponenttiin eivät siirry takaisin datamalliin. Tämän seurauksena, vaikka käyttöliittymäkomponentti saisi käyttäjän syötteen, se ei päivitä perustavaa datamallia. Tämän erottelun ylläpitäminen suojaa datan eheyttä tilanteissa, joissa käyttäjän toimet eivät saisi muuttaa dataa.

Sitä vastoin asettaminen käyttöliittymäkomponentti vain luku -tilassa, ilman että määritetään sidontaa itsessään vain luku -tilaksi, vain estää käyttäjää tekemästä muutoksia käyttöliittymäkomponenttiin, mutta ei estä sidontaa päivittämästä datamallia, jos muutoksia tapahtuu ohjelmallisesti tai muista syistä.
:::

## Sidontasettejä ja -getterit {#binding-getters-and-setters}

Asetus- ja hakumetodit ovat Java-menetelmiä, jotka asettavat ja saavat ominaisuuksien arvot vastaavasti. Tietosidonnan yhteydessä niitä käytetään määrittämään, kuinka ominaisuuksia päivitetään ja haetaan sidontakehyksessä.

### Asetus- ja hakumetodien mukauttaminen {#customizing-setters-and-getters}

Vaikka webforJ voi automaattisesti käyttää standardeja JavaBean-nimeämiskäytäntöjä (esim. `getName()`, `setName()` ominaisuudelle `name`), saatat tarvita mukautetun käyttäytymisen määrittämistä. Tätä tarvitaan, kun ominaisuus ei seuraa tavanomaista nimeämistapaa tai kun datan käsittelylle tarvitaan lisälokia.

### Mukautettujen setterien käyttäminen {#using-custom-getters}

Mukautettuja hakumetodeja käytetään, kun arvon hakuprosessi sisältää enemmän kuin pelkästään ominaisuuden palauttamisen. Esimerkiksi saatat haluta muotoilla merkkijonon, laskea arvon tai lokittaa tiettyjä toimintoja, kun ominaisuus on käytössä.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // Mukautettu logiikka: muuntuu nimeksi isoiksi kirjaimiksi
  });
```

### Mukautettujen setterien käyttäminen {#using-custom-setters}

Mukautetut setterit tulevat esiin, kun ominaisuuden asettaminen vaatii lisätoimia, kuten validointia, muunnoksia tai sivuvaikutuksia, kuten lokituksen tai muiden osien ilmoittamista sovelluksessasi.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("Päivitetään nimi " + hero.getName() + " -> viime nimeksi " + name);
    hero.setName(name); // Lisätoimi: lokitus
  });
```
