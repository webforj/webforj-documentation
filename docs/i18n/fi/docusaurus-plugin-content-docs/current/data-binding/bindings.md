---
sidebar_position: 2
title: Bindings
sidebar_class_name: updated-content
description: >-
  Link Java Bean properties to webforJ UI components through BindingContext to
  synchronize reads and writes between model and view.
_i18n_hash: 9a4b6da2f5a3bd524a0b3cf6a1eb86e1
---
WebforJ:ssä sidonta yhdistää tietyn Java Bean -ominaisuuden käyttöliittymäkomponenttiin. Tämä yhteys mahdollistaa automaattiset päivitykset käyttöliittymän ja taustamallin välillä. Jokainen sidonta voi hallita tiedonsiirtoa, validoimista, muuntamista ja tapahtumien hallintaa.

Voit käynnistää sidonnat vain `BindingContext`in kautta. Se hallitsee sidontainstanseja, jotka yhdistävät käyttöliittymäkomponentin beanin ominaisuuteen. Se helpottaa ryhmätoimintoja sidontojen osalta, kuten validoimista ja synkronointia käyttöliittymäkomponenttien ja beanin ominaisuuksien välillä. Se toimii aggregaattorina, jolloin useita sidontoja voidaan hallita samanaikaisesti, näin virtaviivaistaen tietovirran hallintaa sovelluksissa.

:::tip Automaattinen sidonta
Tässä osassa esitellään oletus sidontojen manuaalinen konfigurointi. Lisäksi voit automaattisesti luoda sidontoja käyttöliittymäkomponenttien perusteella lomakkeessasi. Kun ymmärrät perusteet, voit oppia lisää lukemalla [Automaattinen sidonta](/docs/data-binding/automatic-binding) -osuutta.
:::

## Konfiguroi sidontat {#configure-bindings}

Aloita luomalla uusi `BindingContext`-instanssi, joka hallitsee kaikkia sidontoja tietyssä mallissa. Tämä konteksti validoi ja päivittää kaikki sidonnat yhdessä.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Jokaisella lomakkeella tulisi olla vain yksi `BindingContext`-instanssi, ja sinun tulisi käyttää tätä ilmaisinta kaikille komponentille lomakkeessa.
:::

### Sidottu ominaisuus {#the-bound-property}

Sidontaelementti on tietty kenttä tai ominaisuus Java Beanissa, joka voidaan yhdistää sovelluksesi käyttöliittymäkomponenttiin. Tämä yhteys mahdollistaa muutosten tapahtuvan käyttöliittymässä, jotka vaikuttavat suoraan vastaavaan tietomallin ominaisuuteen ja päinvastoin, jotta käyttöliittymä ja tietomalli pysyvät synkronoituna.

Kun asetat sidontaa, sinun on annettava ominaisuuden nimi merkkijonona. Tämän nimen on vastattava kentän nimeä Java Bean -luokassa. Tässä on yksinkertainen esimerkki:

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

  // setterit ja getterit
}
```

`bind`-metodi palauttaa `BindingBuilder`-objektin, jota käytetään sidonnan useiden asetusten määrittämiseen, ja `add`-metodi, joka on se, mikä todella lisää sidonnan kontekstiin.

### Sidottu komponentti {#the-bound-component}

Sidonnan toinen puoli on sidottu komponentti, joka viittaa käyttöliittymäkomponenttiin, joka vuorovaikuttaa Java Beanin ominaisuuden kanssa. Sidottu komponentti voi olla mitä tahansa käyttöliittymäkomponenttia, joka tukee käyttäjän vuorovaikutusta ja näyttöä, kuten tekstikenttiä, yhdistelmälahjoja, valintaruutuja tai mitä tahansa mukautettua komponenttia, joka toteuttaa `ValueAware`-rajapinnan.

Sidottu komponentti toimii käyttäjän vuorovaikutuspisteenä taustalla olevan tietomallin kanssa. Se näyttää tietoja käyttäjälle ja myös tallentaa käyttäjän syötteet, jotka sitten siirretään taustamalliin.

```java
TextField nameTextField = new TextField("Nimi");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Tietojen lukeminen ja kirjoittaminen {#reading-and-writing-data}

### Tietojen lukeminen {#reading-data}

Tietojen lukeminen tarkoittaa käyttöliittymäkomponenttien täyttämistä tietomallin arvoilla. Tämä tapahtuu tyypillisesti silloin, kun lomake näytetään ensimmäisen kerran tai kun sinun on ladattava tiedot uudelleen taustamallin muutosten vuoksi. `read`-metodi, jonka `BindingContext` tarjoaa, tekee prosessista vaivattoman.

```java
// Oletetaan, että Hero-objekti on instanssoitu ja alustettu
Hero hero = new Hero("Clark Kent", "Lentäminen");

// BindingContext on jo konfiguroitu sidontojen kanssa
context.read(hero);
```

Tässä esimerkissä `read`-metodi ottaa `Hero`-instanssin ja päivittää kaikki sidotut käyttöliittymäkomponentit heimon ominaisuuksia vastaavaksi. Jos heimon nimi tai voima muuttuu, vastaavat käyttöliittymäkomponentit (kuten `TextField` nimelle ja `ComboBox` voimille) näyttävät nämä uudet arvot.

### Tietojen kirjoittaminen {#writing-data}

Tietojen kirjoittaminen tarkoittaa arvojen keräämistä käyttöliittymäkomponenteista ja tietomallin päivittämistä. Tämä tapahtuu tyypillisesti, kun käyttäjä lähettää lomakkeen. `write`-metodi hoitaa validoimisen ja mallin päivittämisen yhdellä vaiheella.

```java
// Tämä voisi laukaista lomakkeen lähettämisen tapahtuman
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Tiedot ovat voimassa, ja hero-objektia on päivitetty
    // repository.save(hero);
  } else {
    // Käsittele validoimisen virheet
    // results.getMessages();
  }
});
```

Yllä olevassa koodissa, kun käyttäjä napsauttaa lähetysnappia, `write`-metodia kutsutaan. Se suorittaa kaikki konfiguroidut validoimiset ja, jos tiedot läpäisevät kaikki tarkistukset, päivittää `Hero`-objektin uusilla arvoilla sidotuista komponenteista. Jos tiedot ovat voimassa, saatat tallentaa ne tietokantaan tai käsitellä niitä edelleen. Jos validoimisessa on virheitä, sinun tulisi käsitellä niitä asianmukaisesti, tyypillisesti näyttämällä virheilmoituksia käyttäjälle.

:::tip Virheiden raportointi validoimisen aikana
Kaikilla webforJ:n ydinkomponenteilla on oletusasetuksia, jotka raportoivat validoimisvirheitä automaattisesti joko sisäisesti tai ponnahdusikkunan kautta. Voit mukauttaa tätä käyttäen [Raportoijat](./validation/reporters.md).
:::

## Sisäkkäiset bean-ominaisuudet <DocChip chip='since' label='26.01' /> {#nested-bean-properties}

Sidontaelementti voi olla pisteellä erotettu polku, joka osoittaa sisäkkäisen beanin ominaisuuteen. Jokainen segmentti polussa seuraa standardin JavaBean-getter- ja setter-käytäntöjä, joten `address.street` luetaan `getAddress().getStreet()`-menetelmällä ja kirjoitetaan `getAddress().setStreet()`-menetelmällä.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(streetField, "address.street").add();
context.bind(cityField, "address.city").add();
```

```java
public class Hero {
  private String name;
  private Address address;

  // getterit ja setterit
}

public class Address {
  private String street;
  private String city;
  private String zip;

  // getterit ja setterit
}
```

Lukemisen yhteydessä polku ratkeaa turvallisesti, vaikka välikappale bean olisi `null`. Jos `Hero`-objektilla ei ole `Addressia`, sidotuissa komponentteissa `address.street` ja `address.city` luetaan tyhjiksi sen sijaan, että heittäisivät virheen, joten lomake populoi edelleen.

Kirjoittaessa konteksti luo kaikki puuttuvat välikappaleet sen ilmanargumenttista konstruktorista, joten lomakkeen kirjoittaminen `Hero`-objektiin ilman `Addressia` tuottaa uuden, täytetyn `Addressin`. Jo olemassa oleva `Address` käytetään uudelleen.

[Jakarta-validointi](/docs/data-binding/validation/jakarta-validation) -annotaatioita sisäkkäisessä ominaisuudessa havaitaan samalla tavalla kuin ylimmän tason ominaisuuksissa. Annotaatio, kuten `@NotNull` osoittaa `Address.street`-omaisuudessa, merkitsee `address.street`-sidontaa [vaatimukset täyttäväksi](/docs/data-binding/automatic-binding#bindingrequired-annotation).

:::info Polkuja validoidaan etukäteen
Koko polku validoidaan, kun kutsut `bind`. Kirjoitusvirhe missä tahansa segmentissä, ylimmällä tasolla tai syvemmällä polussa, heittää `IllegalArgumentExceptionin`, joten sidontavirheet tulevat esiin heti, eikä lukemis- tai kirjoitusaikana.
:::

<!-- vale off -->
## Lukuoikeudelliset tiedot {#readonly-data}
<!-- vale on -->

Tietyissä tilanteissa saatat haluta, että sovelluksesi näyttää tietoja ilman, että loppukäyttäjä voi muokata sitä suoraan käyttöliittymän kautta. Lukuoikeudelliset sidonnat käsittelevät tätä. WebforJ tukee sidontojen määrittämistä lukuoikeudellisiksi, joten voit näyttää tietoja, mutta et muokata niitä sidottujen käyttöliittymäkomponenttien kautta.

### Lukuoikeudellisten sidontojen konfigurointi {#configuring-readonly-bindings}

Lukuoikeudellisen sidonnan määrittämiseksi voit määrittää sidonnan, jotta se sammuu tai jättää huomiotta käyttöliittymäkomponentin syötteen. Tieto pysyy muuttumattomana käyttöliittymässä, mutta päivittyy ohjelmallisesti tarvittaessa.

```java
// Määritetään tekstikenttä lukuoikeudelliseksi sidontakontekstissa
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

Tässä konfiguraatiossa `readOnly` estää `nameTextField` -komponentin hyväksymästä käyttäjän syötettä, joten tekstikenttä näyttää tietoja ilman, että muutoksia voidaan tehdä.

:::info
Sidonta voi merkitä komponenttia lukuoikeudelliseksi vain, jos käyttöliittymäkomponentti toteuttaa `ReadOnlyAware`-rajapinnan.
:::

:::tip Komponentti Lukuoikeudellinen vs Sidonta Lukuoikeudellinen
On tärkeää erottaa sidontat, jotka määrität lukuoikeudellisiksi, ja käyttöliittymäkomponentit, jotka asetat näyttämään lukuoikeudellisina. Kun merkitset sidonnan lukuoikeudelliseksi, se vaikuttaa siihen, kuinka sidonta hallitsee tietoja kirjoitusprosessin aikana, eikä vain käyttöliittymän käyttäytymiseen.

Kun merkitset sidonnan lukuoikeudelliseksi, järjestelmä ohittaa tietopäivitykset. Mikä tahansa muutokset käyttöliittymäkomponentissa eivät siirry takaisin tietomalliin. Tämän seurauksena, vaikka käyttöliittymäkomponentti jollain tavalla vastaanottaisi käyttäjän syötteen, se ei päivitä taustalla olevaa tietomallia. Tämän erottelun ylläpitäminen suojaa tietojen eheyden skenaarioissa, joissa käyttäjän toimet eivät saisi muuttaa tietoja.

Toisaalta, jos asetat käyttöliittymäkomponentin lukuoikeudelliseksi ilman, että määrität sidontaa itsessään lukuoikeudelliseksi, se yksinkertaisesti estää käyttäjää tekemästä muutoksia käyttöliittymäkomponenttiin, mutta ei estä sidontaa päivittämästä tietomallia, jos muutoksia tapahtuu ohjelmallisesti tai muilla tavoin.
:::

## Sidontojen setterit ja getterit {#binding-getters-and-setters}

Setterit ja getterit ovat metodeja Javassa, jotka asettavat ja saavat ominaisuuksien arvoja vastaavasti. Tietosidonnan yhteydessä niitä käytetään määrittämään, kuinka ominaisuuksia päivitetään ja haetaan sidontakehyksessä.

### Setterien ja getterien mukauttaminen {#customizing-setters-and-getters}

Vaikka webforJ voi automaattisesti käyttää standardin JavaBean-nimimallin käytäntöjä (esimerkiksi `getName()`, `setName()` ominaisuudelle `name`), sinun saattaa olla tarpeen määrittää mukautettua käyttäytymistä. Tämä on tarpeen, kun ominaisuus ei noudata perinteistä nimeämiskäytäntöä tai kun tietojen käsittely vaatii lisälogiikkaa.

### Mukautettujen getterien käyttäminen {#using-custom-getters}

Mukautettuja gettereitä käytetään, kun arvon hakuprosessissa on enemmän kuin pelkästään ominaisuuden palauttaminen. Esimerkiksi saatat haluta muotoilla merkkijonon, laskea arvon tai kirjata tiettyjä toimintoja, kun ominaisuus otetaan käyttöön.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // Mukautettu logiikka: muuntaa nimen isoiksi kirjaimiksi
  });
```

### Mukautettujen setterien käyttö {#using-custom-setters}

Mukautetut setterit tulevat peliin, kun ominaisuuden asettaminen edellyttää lisätoimia, kuten validoimista, muuntamista tai sivuvaikutuksia, kuten kirjaamista tai muiden osien ilmoittamista sovelluksessasi.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("Päivitetään nimeä " + hero.getName() + " -> " + name);
    hero.setName(name); // Lisätoimi: kirjaaminen
  });
```
