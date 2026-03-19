---
sidebar_position: 2
title: Bindings
_i18n_hash: c567705312942e83f5e83a77f1d510a4
---
A binding webforJ:ssä linkittää java-beanin tietyn ominaisuuden käyttöliittymäkomponenttiin. Tämä liitos mahdollistaa automaattiset päivitykset käyttöliittymän ja taustamallin välillä. Jokainen binding voi käsitellä tietosynkronointia, validointia, muuntamista ja tapahtumahallintaa.

Voit aloittaa bindingsien luomisen vain `BindingContext`in kautta. Se hallinnoi kokoelmaa binding-instansseja, jotka jokainen linkittävät käyttöliittymäkomponentin beanin ominaisuuteen. Se helpottaa ryhmätoimintoja bindingsien osalta, kuten validointia ja synkronointia käyttöliittymäkomponenttien ja beanin ominaisuuksien välillä. Se toimii aggregaattorina, mikä mahdollistaa kollektiiviset toimet useiden bindingsien osalta, näinollen virtaviivaistaen datan hallintaa sovelluksissa.

:::tip Automaattinen Binding
Tässä osiossa esitellään bindingien manuaalisen konfiguroinnin perusteet. Voit myös luoda bindingit automaattisesti käyttöliittymäkomponenttien perusteella lomakkeessasi. Kun ymmärrät perusteet, opi lisää lukemalla [Automaattinen Binding](./automatic-binding) -osio.
:::

## Määritä bindings {#configure-bindings}

Aloita luomalla uusi `BindingContext`-instanssi, joka hallinnoi kaikkia bindingsia tietylle mallille. Tämä konteksti varmistaa, että kaikki bindings voidaan validoida ja päivittää kollektiivisesti.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Jokaisella lomakkeella tulisi olla vain yksi `BindingContext`-instanssi, ja sinun tulisi käyttää tätä instanssia kaikille lomakkeen komponenteille.
:::

### Sidottu ominaisuus {#the-bound-property}

Sidottu ominaisuus on tietty kenttä tai attribuutti Java Beanissa, joka voidaan liittää käyttöliittymäkomponenttiin sovelluksessasi. 
Tämä liitos mahdollistaa käyttöliittymän muutosten vaikuttavan suoraan vastaavaan datamallin ominaisuuteen ja päinvastoin, 
helpottaen reaktiivista käyttäjäkokemusta.

Bindingin määrittämisessä sinun tulee antaa ominaisuuden nimi merkkijonona. Tämän nimen on vastattava kentän nimeä Java Bean -luokassa. Tässä on yksinkertainen esimerkki:

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);
context
  .bind(textField, "power")
  .add()
```

```java
public class Hero  {
  private String name;
  private String power;

  // setterit ja getterit
}
```

`bind`-metodit palauttavat `BindingBuilder`-objektin, joka luo `Binding`-objektin, jonka voit käyttää bindingin eri asetusten määrittämiseen, ja `add`-metodi on se, joka todella lisää bindingin kontekstiin.

### Sidottu komponentti {#the-bound-component}

Bindingin toinen puoli on sidottu komponentti, joka viittaa käyttöliittymäkomponenttiin, joka vuorovaikuttaa Java Beanin ominaisuuden kanssa. 
Sidottu komponentti voi olla mikä tahansa käyttöliittymäkomponentti, joka tukee käyttäjätapahtumaa ja näyttöä, kuten tekstikentät, valintaruudut, valintalaatikot tai 
mikä tahansa mukautettu komponentti, joka toteuttaa `ValueAware`-rajapinnan.

Sidottu komponentti toimii käyttäjän vuorovaikutuspisteenä taustalla olevan datamallin kanssa. 
Se näyttää tietoa käyttäjälle, ja myös kerää käyttäjän syötteet, jotka sitten välitetään takaisin malliin.

```java
TextField nameTextField = new TextField("Nimi");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Datan lukeminen ja kirjoittaminen {#reading-and-writing-data}

### Datan lukeminen {#reading-data}

Datan lukeminen tarkoittaa käyttöliittymäkomponenttien täyttämistä arvoilla datamallista. 
Tämä tehdään tyypillisesti, kun lomake näytetään ensimmäisen kerran, tai kun sinun tarvitsee ladata data uudelleen taustamallissa tapahtuneiden muutosten vuoksi. 
`read`-metodi, jonka `BindingContext` tarjoaa, tekee tämän prosessin yksinkertaiseksi.

```java
// Oletetaan, että Hero-objekti on instanssoitu ja alustettu
Hero hero = new Hero("Clark Kent", "Lentäminen");

// BindingContext on jo konfiguroitu bindingsilla
context.read(hero);
```

Tässä esimerkissä `read`-metodi ottaa `Hero`-instanssin ja päivittää kaikki sidotut käyttöliittymäkomponentit hepun ominaisuuksien mukaisiksi. 
Jos hepun nimi tai voima muuttuu, vastaavat käyttöliittymäkomponentit (kuten `TextField` nimelle ja `ComboBox` voimille) näyttävät nämä uudet arvot.

### Datan kirjoittaminen {#writing-data}

Datan kirjoittaminen tarkoittaa arvojen keräämistä käyttöliittymäkomponenteista ja datamallin päivittämistä. 
Tämä tapahtuu tyypillisesti, kun käyttäjä lähettää lomakkeen. `write`-metodi käsittelee validointia ja mallin päivittämistä yhdessä vaiheessa.

```java
// Tämä voisi laukaista lomakkeen lähettämistapahtuma
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Data on voimassa, ja hero-objektia on päivitetty
    // repository.save(hero); 
  } else {
    // Käsittele validointivirheitä
    // results.getMessages();
  }
});
```

Yllä olevassa koodissa, kun käyttäjä napsauttaa lähetyspainiketta, `write`-metodia kutsutaan. 
Se suorittaa kaikki konfiguroidut validoinnit ja, jos data läpäisee kaikki tarkistukset, päivittää `Hero`-objektin 
uuden arvot sidotuista komponenteista. 
Jos data on voimassa, voit tallentaa sen tietokantaan tai käsitellä sitä edelleen. Jos löytyy validointivirheitä, 
sinun tulee käsitellä niitä asianmukaisesti, tyypillisesti näyttämällä virheilmoituksia käyttäjälle.

:::tip Validointivirheiden Raportointi
Kaikilla core-komponenteilla webforJ:ssä on oletusasetukset, jotka automaattisesti raportoivat validointivirheitä, joko inline tai popoverin kautta. Voit mukauttaa tätä käyttäen [Raportteja](./validation/reporters.md).
:::

<!-- vale off -->
## Vain luku -data {#readonly-data}
<!-- vale on -->

Tietyissä skenaarioissa saatat haluta, että sovelluksesi näyttää dataa ilman, että loppukäyttäjä voi muuttaa sitä suoraan käyttöliittymän kautta. 
Tässä lukkiutumattomat databindings tulevat tärkeiksi. webforJ tukee bindingsien konfigurointia vain luku -tilassa, varmistaen, että 
voit näyttää dataa, mutta et muuttaa sitä sidottujen käyttöliittymäkomponenttien kautta.

### Vain luku -bindingsin konfigurointi {#configuring-readonly-bindings}

Asettaaksesi vain luku -bindingin, voit konfiguroida bindingin estämään tai jättämään huomiotta käyttöliittymäkomponentin syötteen. 
Tämä varmistaa, että data pysyy muuttumattomana käyttöliittymän näkökulmasta, vaikka sitä päivitetään ohjelmallisesti tarvittaessa.

```java
// Konfiguroimalla tekstikenttä vain luku -tilassa binding-kontekstissa
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

Tässä konfiguraatiossa `readOnly` varmistaa, että `nameTextField` ei hyväksy käyttäjän syöttöä, tehden tekstikentästä vain näyttävän datan ilman muutosten mahdollisuutta.

:::info
Binding voi merkitä komponentin vain luku -tilaiseksi vain, jos käyttöliittymäkomponentti toteuttaa `ReadOnlyAware` -rajapinnan.
:::

:::tip Komponentin vain luku vs Bindingin vain luku
On tärkeää erottaa toisistaan bindingsit, jotka konfiguroit vain luku -tilaan, ja käyttöliittymäkomponentit, jotka asetat näyttämään vain luku -tilassa. 
Kun merkitset bindingin vain luku -tilaksi, se vaikuttaa siihen, miten binding hallitsee dataa kirjoitusprosessin aikana, ei vain käyttöliittymän käyttäytymiseen.

Kun merkitset bindingin vain luku -tilaiseksi, järjestelmä ohittaa tietopäivitykset. Kaikki muutokset käyttöliittymäkomponenttiin eivät välity taustalla olevaan datamalliin. 
Tämä varmistaa, että vaikka käyttöliittymäkomponentti jollain tavalla saisi käyttäjän syötteen, se ei päivitä alhaista datamallia. 
Tämän erottelun säilyttäminen on tärkeää datan eheyden suojelemiseksi skenaarioissa, joissa käyttäjän toimet eivät saisi muuttaa dataa.

Sitä vastoin, asettamalla käyttöliittymäkomponentti vain luku -tilaan, ilman että binding itsessään on konfiguroitu vain luku -tilaksi, yksinkertaisesti estää käyttäjää tekemästä muutoksia 
käyttöliittymäkomponenttiin, mutta ei estä bindingia päivittämästä datamallia, jos muutoksia tapahtuu ohjelmallisesti tai muista syistä.
:::

## Bindingien getterit ja setterit {#binding-getters-and-setters}

Setterit ja getterit ovat metodeja Javassa, jotka asettavat ja saavat ominaisuuksien arvot vastaavasti. 
Databindingin kontekstissa niitä käytetään määrittämään, miten ominaisuuksia päivitetään ja haetaan binding-kehyksessä.

### Mukauttaminen setterit ja getterit {#customizing-setters-and-getters}

Vaikka webforJ voi automaattisesti käyttää standardijavabean-nimikehakemistoa
(esimerkiksi `getName()`, `setName()` ominaisuudelle `name`), saatat tarvita räätälöityä käyttäytymistä. 
Tämä on tarpeellista, kun ominaisuus ei noudata tavanomaista nimeämistä tai kun datankäsittelyssä tarvitaan lisälokiikkaa.

### Mukautettujen getterien käyttäminen {#using-custom-getters}

Mukautettuja gettereitä käytetään silloin, kun arvojen hakuprosessi sisältää enemmän kuin pelkän ominaisuuden palauttamisen. 
Esimerkiksi saatat haluta muotoilla merkkijonon, laskea arvon tai kirjata tiettyjä toimintoja, kun ominaisuutta haetaan.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // Mukautettu logiikka: muunna nimi isoiksi kirjaimiksi
  });
```

### Mukautettujen setterien käyttäminen {#using-custom-setters}

Mukautetut setterit astuvat kuvaan, kun ominaisuuden asettaminen sisältää lisätoimintoja, kuten validointia, muuntamista tai sivuvaikutuksia
kuten lokitusta tai muiden osien ilmoittamista sovelluksestasi.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("Päivitetään nimeä " + hero.getName() + " -> " + name);
    hero.setName(name); // Lisätoiminto: lokitus
  });
```
