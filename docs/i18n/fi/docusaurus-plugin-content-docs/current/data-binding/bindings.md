---
sidebar_position: 2
title: Bindings
_i18n_hash: 0afea0971d509f25324b46172b5e020e
---
A binding in webforJ linkittää tietyn Java Bean -ominaisuuden UI-komponenttiin. Tämä linkitys mahdollistaa automaattiset päivitykset UI:n ja taustamallin välillä. Jokainen binding voi käsitellä datan synkronointia, validointia, muunnosta ja tapahtumien hallintaa.

Voit aloittaa bindingit vain `BindingContext`in kautta. Se hallitsee kokoelmaa binding-instansseja, jotka linkittävät UI-komponentin beanin ominaisuuteen. Se helpottaa ryhmätoimintoja bindingeillä, kuten validointia ja synkronointia UI-komponenttien ja beanin ominaisuuksien välillä. Se toimii aggregaattorina, joka mahdollistaa kollektiiviset toimet useille bindingeille, näin ollen virtaviivaistaa datan hallintaa sovelluksissa.

:::tip Automaattinen binding
Tässä osassa esitellään manuaalisten bindingien perusasetukset. Voit myös luoda bindingeja automaattisesti UI-komponenttien perusteella lomakkeessasi. Kun hallitset perusasiat, voit syventää tietämystäsi lukemalla [Automaattinen Binding](./automatic-binding) -osiota.
:::

## Määritä bindingit {#configure-bindings}

Aloita luomalla uusi `BindingContext`-instanssi, joka hallitsee kaikkia bindingeja tietyssä mallissa. Tämä konteksti varmistaa, että kaikki bindingit voidaan validoida ja päivittää yhdessä.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Jokaisessa lomakkeessa tulisi olla vain yksi `BindingContext`-instanssi, ja sinun tulisi käyttää tätä instanssia kaikille lomakkeen komponenteille.
:::

### Sidottu ominaisuus {#the-bound-property}

Sidottu ominaisuus on tietty kenttä tai attribuutti Java Beanissa, joka voidaan linkittää UI-komponenttiin sovelluksessasi. 
Tämä linkitys mahdollistaa muutokset UI:ssa vaikuttamaan suoraan vastaavaan datamallin ominaisuuteen ja päinvastoin, 
helpottaen reaktiivista käyttäjäkokemusta.

Bindingia asetettaessa sinun tulisi antaa ominaisuuden nimi merkkijonona. Tämän nimen on vastattava kentän nimeä Java Bean -luokassa. Tässä on yksinkertainen esimerkki:

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

`bind`-metodi palauttaa `BindingBuilder`-objektin, joka luo `Binding`-objektin ja jonka avulla voit konfiguroida bindingin useita asetuksia, `add`-metodi on se, joka todella lisää bindingin kontekstiin.

### Sidottu komponentti {#the-bound-component}

Bindingin toinen puoli on sidottu komponentti, joka viittaa UI-komponenttiin, joka vuorovaikuttaa Java Beanin ominaisuuden kanssa. 
Sidottu komponentti voi olla mikä tahansa UI-komponentti, joka tukee käyttäjävuorovaikutusta ja näyttöä, kuten tekstikenttiä, yhdistelmätaiheita, valintaruutuja tai 
mikä tahansa mukautettu komponentti, joka toteuttaa `ValueAware`-rajapinnan.

Sidottu komponentti toimii käyttäjän vuorovaikutuspisteenä taustalla olevan datamallin kanssa. 
Se näyttää tietoa käyttäjälle ja myös tallentaa käyttäjän syötteitä, jotka sitten välitetään takaisin malliin.

```java
TextField nameTextField = new TextField("Nimi");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Datan lukeminen ja kirjoittaminen {#reading-and-writing-data}

### Datan lukeminen {#reading-data}

Datan lukeminen sisältää UI-komponenttien täyttämisen arvoilla datamallista. 
Tämä tehdään tyypillisesti, kun lomake näytetään ensimmäisen kerran, tai kun sinun on ladattava data uudelleen taustamallin muutosten vuoksi. 
`read`-metodi, jonka `BindingContext` tarjoaa, tekee tämän prosessin suoraviivaiseksi.

```java
// Oleta, että Hero-objekti on instansioitu ja alustettu
Hero hero = new Hero("Clark Kent", "Lentäminen");

// BindingContext on jo konfiguroitu bindingeilla
context.read(hero);
```

 Tässä esimerkissä `read`-metodi ottaa vastaan `Hero`-instanssin ja päivittää kaikki sidotut UI-komponentit hejon ominaisuuksien mukaisesti. 
Jos hejon nimi tai voima muuttuu, vastaavat UI-komponentit (kuten `TextField` nimelle ja `ComboBox` voimille) 
näyttävät nämä uudet arvot.

### Datan kirjoittaminen {#writing-data}

Datan kirjoittaminen tarkoittaa arvojen keräämistä UI-komponenteista ja datamallin päivittämistä. 
Tämä tapahtuu tyypillisesti, kun käyttäjä lähettää lomakkeen. `write`-metodi käsittelee validointia ja mallin päivittämistä yhdellä kertaa.

```java
// Tämä voisi käynnistyä lomakkeen lähetysehdosta
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

Yllä olevassa koodissa, kun käyttäjä napsauttaa lähetyspainiketta, `write`-metodia kutsutaan. 
Se suorittaa kaikki määritellyt validoinnit ja, jos data läpäisee kaikki tarkastukset, päivittää `Hero`-objektin 
uuden tiedon perustella sidotuista komponenteista. 
Jos data on voimassa, voit tallentaa sen tietokantaan tai käsitellä edelleen. Jos esiintyy validointivirheitä, 
sinun tulee käsitellä ne asianmukaisesti, tyypillisesti näyttämällä virheilmoituksia käyttäjälle.

:::tip Validointivirheiden raportointi
Kaikilla webforJ:n ydinkomponenteilla on oletusasetukset, jotka automaattisesti raportoivat validointivirheitä, joko inline tai popoverin kautta. Voit mukauttaa tätä käyttäen [Raportteja](./validation/reporters.md).
:::

<!-- vale off -->
## Lukuoikeudettomat tiedot {#readonly-data}
<!-- vale on -->

Tietyissä tilanteissa saatat haluta sovelluksesi näyttävän tietoja ilman, että loppukäyttäjälle annetaan mahdollisuutta muuttaa niitä suoraan UI:n kautta. 
Tässä lukuoikeudettomat tiedot -bindingit tulevat tärkeiksi. webforJ tukee bindingien konfigurointia niin, että ne ovat lukuoikeudettomia, varmistaen, että 
voit näyttää tietoja, mutta et muokata niitä sidottujen UI-komponenttien kautta.

### Lukuoikeudettomien bindingien määrittäminen {#configuring-readonly-bindings}

Asettaaksesi lukuoikeudettoman bindingin, voit konfiguroida bindingin sulkemaan pois tai sivuuttamaan UI-komponentin syötteen. 
Tämä varmistaa, että data pysyy muuttumattomana UI:n näkökulmasta, vaikka sitä päivitettäisiin ohjelmallisesti tarvittaessa.

```java
// Määrittää tekstikentän lukuoikeudettomaksi binding-kontekstissa
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
    .readOnly()
    .add();
```

Tässä konfiguraatiossa `readOnly` varmistaa, että `nameTextField` ei hyväksy käyttäjän syötteitä, tehden näin tekstikentästä näyttävän 
dataa ilman muokkausmahdollisuuksia.

:::info
Binding voi merkitä komponentin lukuoikeudettomaksi vain, jos UI-komponentti toteuttaa `ReadOnlyAware` -rajapinnan.
:::

:::tip Komponentin lukuoikeus vs Bindingin lukuoikeus
On tärkeää erottaa lukuoikeudettomiksi määritellyt bindingit ja UI-komponentit, jotka asetetaan näyttämään lukuoikeudettomilta. 
Kun merkitset bindingin lukuoikeudettomaksi, se vaikuttaa siihen, miten binding hallitsee dataa kirjoitusprosessin aikana, ei vain UI-käyttäytymiseen.

Kun merkitset bindingin lukuoikeudettomaksi, järjestelmä ohittaa data päivitykset. Kaikki muutokset UI-komponentissa eivät siirry takaisin datamalliin. 
Tämä varmistaa, että jopa jos UI-komponentti saa jotenkin käyttäjän syötteen, se ei päivitä taustalla olevaa datamallia. 
Tämän erottelun ylläpitäminen on ratkaisevan tärkeää datan eheyden säilyttämiseksi tilanteissa, joissa käyttäjän toimenpiteet eivät saisi muuttaa dataa.

Sen sijaan, kun asetat UI-komponentin lukuoikeudettomaksi, ilman että konfiguroit bindingia itsessään lukuoikeudettomaksi, se vain estää käyttäjän tekemästä muutoksia 
UI-komponenttiin, mutta ei estä bindingiä päivittämästä datamallia, jos muutoksia tapahtuu ohjelmallisesti tai muilla tavoin.
:::

## Bindingin getterit ja setterit {#binding-getters-and-setters}

Setterit ja getterit ovat Java-metodeja, jotka asettavat ja saavat ominaisuuksien arvot. 
Data bindingin yhteydessä niitä käytetään määrittämään, miten ominaisuuksia päivitetään ja haetaan binding-kehyksessä.

### Getterien ja setterien mukauttaminen {#customizing-setters-and-getters}

Vaikka webforJ voi automaattisesti käyttää standardeja JavaBean-nimityskäytänteitä (esimerkiksi `getName()`, `setName()` omaisuudelle `name`), saatat tarvita mukautettua käyttäytymistä. 
Tämä on tarpeen, kun ominaisuus ei noudata tavanomaista nimeämistä tai kun datan käsittely vaatii lisälokikkaa.

### Mukautettujen getterien käyttäminen {#using-custom-getters}

Mukautettuja gettereitä käytetään, kun arvon hakuprosessi sisältää enemmän kuin pelkän ominaisuuden palauttamisen. 
Esimerkiksi saatat haluta muotoilla merkkijonon, laskea arvon tai kirjata tiettyjä toimia, kun ominaisuutta käytetään.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useGetter(hero -> {
        String name = hero.getName();
        return name.toUpperCase(); // Mukautettu logiikka: muutetaan nimi isoiksi kirjaimiksi
    });
```

### Mukautettujen setterien käyttäminen {#using-custom-setters}

Mukautetut setterit tulevat peliin, kun ominaisuuden asettamiseen liittyy lisätoimintoja, kuten validointi, muuntaminen tai sivuvaikutukset, 
kuten kirjaaminen tai muiden osien ilmoittaminen sovelluksessa.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useSetter((hero, name) -> {
        System.out.println("Päivitetään nimeä " + hero.getName() + " nimeksi " + name);
        hero.setName(name); // Lisätoiminto: kirjaaminen
    });
```
