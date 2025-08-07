---
sidebar_position: 2
title: Bindings
_i18n_hash: fa6155c6e1eb2724d684d042f561c8a3
---
A binding in webforJ yhdistää tietyn ominaisuuden Java Beanista UI-komponenttiin. Tämä yhteys mahdollistaa automaattiset päivitykset UI:n ja taustamallin välillä. Kukin binding voi käsitellä tietojen synkronointia, validointia, muunnosta ja tapahtumien hallintaa.

Voit aloittaa bindingien luomisen vain `BindingContext`in kautta. Se hallitsee kokoelmaa binding-instansseja, jotka kaikki yhdistävät UI-komponentin beanin ominaisuuteen. Se helpottaa ryhmätoimenpiteitä bindingeille, kuten validointia ja synkronointia UI-komponenttien ja beanin ominaisuuksien välillä. Se toimii kokoajana, jolloin useita bindingeja voidaan käsitellä kerralla, mikä virtaviivaistaa tiedonhallintaa sovelluksissa.

:::tip Automaattinen Binding
Tässä osiossa esitellään manuaalisten bindingien perusasiat. Lisäksi voit luoda bindingeja automaattisesti sen perusteella, mitä UI-komponentteja lomakkeessasi on. Kun ymmärrät perusteet, voit oppia lisää lukemalla [Automaattinen Binding](./automatic-binding) -osiota.
:::

## Konfiguroi bindingit {#configure-bindings}

Aloita luomalla uusi `BindingContext`-instanssi, joka hallitsee kaikkia bindingeja tietylle mallille. Tämä konteksti varmistaa, että kaikki bindingit voidaan validoida ja päivittää yhdessä.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Jokaisella lomakkeella tulisi olla vain yksi `BindingContext`-instanssi, ja sinun tulisi käyttää tätä instanssia kaikille lomakkeen komponenteille.
:::

### Sidottu ominaisuus {#the-bound-property}

Sidottu ominaisuus on tietty kenttä tai attribuutti Java Beanissa, joka voidaan yhdistää UI-komponenttiin sovelluksessasi. Tämä yhteys mahdollistaa sen, että UI:ssä tapahtuvat muutokset vaikuttavat suoraan datamallin vastaavaan ominaisuuteen ja päinvastoin, mikä helpottaa reaktiivista käyttäjäkokemusta.

Bindingia määritettäessä sinun tulee antaa ominaisuuden nimi merkkijonona. Tämän nimen on vastattava kentän nimeä Java Bean -luokassa. Tässä yksinkertainen esimerkki:

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

`bind`-menetelmä palauttaa `BindingBuilder`:n, joka luo `Binding`-objektin ja voit käyttää tätä konfiguroidaksesi bindingin erilaisia asetuksia, `add`-menetelmä, joka itse asiassa lisää bindingin kontekstiin.

### Sidottu komponentti {#the-bound-component}

Bindingin toinen puoli on sidottu komponentti, joka viittaa UI-komponenttiin, joka vuorovaikuttaa Java Beanin ominaisuuden kanssa. Sidottu komponentti voi olla mikä tahansa UI-komponentti, joka tukee käyttäjävuorovaikutusta ja näyttöä, kuten tekstikentät, yhdistelmälaitteet, valintaruudut tai mikä tahansa mukautettu komponentti, joka toteuttaa `ValueAware`-rajapinnan.

Sidottu komponentti toimii käyttäjän vuorovaikutuspisteenä taustalla olevan datamallin kanssa. Se näyttää tietoja käyttäjälle ja tallentaa myös käyttäjän syötteet, jotka sitten palautetaan malliin.

```java
TextField nameTextField = new TextField("Nimi");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Tietojen lukeminen ja kirjoittaminen {#reading-and-writing-data}

### Tietojen lukeminen {#reading-data}

Tietojen lukeminen tarkoittaa UI-komponenttien populointia datamallin arvoilla. Tämä tehdään tyypillisesti, kun lomake näytetään ensimmäisen kerran, tai kun tarvitset datan lataamista uudelleen taustamallin muutosten vuoksi. `read`-menetelmä, jota `BindingContext` tarjoaa, tekee tästä prosessista suoraviivaisen.

```java
// Oletetaan, että Hero-objekti on instansioitu ja alustettu
Hero hero = new Hero("Clark Kent", "Lentäminen");

// BindingContext on jo konfiguroitu bindingeilla
context.read(hero);
```

Tässä esimerkissä `read`-menetelmä ottaa vastaan `Hero`-instanssin ja päivittää kaikki sidotut UI-komponentit heimon ominaisuuksien mukaisesti. Jos heimon nimi tai voima muuttuu, vastaavat UI-komponentit (kuten `TextField` nimelle ja `ComboBox` voimille) näyttävät nämä uudet arvot.

### Tietojen kirjoittaminen {#writing-data}

Tietojen kirjoittaminen tarkoittaa arvojen keräämistä UI-komponenteista ja datamallin päivittämistä. Tämä tapahtuu tyypillisesti, kun käyttäjä lähettää lomakkeen. `write`-menetelmä käsittelee validointia ja mallin päivittämistä yhdellä kertaa.

```java
// Tämä voisi laukaista lomakkeen lähettämisen tapahtuma
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Tiedot ovat voimassa, ja hero-objekti on päivitetty
    // repository.save(hero);
  } else {
    // Käsittele validointivirheitä
    // results.getMessages();
  }
});
```

Yllä olevassa koodissa, kun käyttäjä napsauttaa lähetyspainiketta, `write`-menetelmää kutsutaan. Se suorittaa kaikki konfiguroidut validoinnit ja, jos tiedot läpäisevät kaikki tarkistukset, päivittää `Hero`-objektin uusilla arvoilla sidotuista komponenteista. Jos tiedot ovat voimassa, voit tallentaa tietokantaan tai käsitellä edelleen. Jos on validointivirheitä, sinun tulisi käsitellä niitä asianmukaisesti, tyypillisesti näyttämällä virheviestejä käyttäjälle.

:::tip Virheiden raportointi varmennettaessa
Kaikilla webforJ:n ydin komponenteilla on oletusasetuksia, jotka raportoivat automaattisesti validointivirheitä, joko inline tai popoverin kautta. Voit mukauttaa tätä käyttäen [Raportointia](./validation/reporters.md).
:::

<!-- vale off -->
## Vain luku -tiedot {#readonly-data}
<!-- vale on -->

Tietyissä skenaarioissa saatat haluta, että sovelluksesi näyttää tietoja ilman, että loppukäyttäjä voi muokata niitä suoraan UI:n kautta. Tässä vain luku -bindingit ovat tärkeitä. webforJ tukee bindingien konfiguroimista vain lomituksina, varmistaen, että voit näyttää tietoja, mutta et muokata niitä sidottujen UI-komponenttien kautta.

### Vain luku -bindingien konfigurointi {#configuring-readonly-bindings}

Asettaaksesi vain luku -bindingin, voit konfiguroida bindingin niin, että se estää tai ohittaa UI-komponentin syötteen. Tämä varmistaa, että tiedot pysyvät muuttumattomina UI:n näkökulmasta, vaikka niitä päivitettäisiin ohjelmallisesti tarvittaessa.

```java
// Konfiguroimme tekstikentän olemaan vain luku -binding-kontekstissa
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
    .readOnly()
    .add();
```

Tässä konfiguraatiossa `readOnly` varmistaa, että `nameTextField` ei hyväksy käyttäjän syötettä, tehden tekstikentästä vain luku -tilassa datan näyttämiseksi ilman mahdollisuutta muuttaa sitä.

:::info
Binding voi merkitä komponentin vain luku -tilaksi vain, jos UI-komponentit toteuttavat `ReadOnlyAware`-rajapinnan.
:::

:::tip Komponentin vain luku -tila vs Bindingin vain luku -tila
On tärkeää erottaa vain luku -bindingit, joita olet konfiguroinut, ja UI-komponentit, joiden olet saanut näyttämään vain luku -tilassa. Kun merkitset bindingin vain luku -tilaksi, se vaikuttaa siihen, kuinka binding hallinnoi tietoja kirjoitusprosessin aikana, ei vain UI-käyttäytymiseen.

Kun merkitset bindingin vain luku -tilaksi, järjestelmä ohittaa tietopäivitykset. Kaikki muutokset UI-komponenttiin eivät lähetä tietoja takaisin datamalliin. Tämä varmistaa, että vaikka UI-komponentti jollain tavalla saisi käyttäjän syötteen, se ei päivitä taustalla olevaa datamallia. Tämän eron säilyttäminen on ratkaisevan tärkeää tietojen eheyden säilyttämiseksi tilanteissa, joissa käyttäjän toimet eivät saa muuttaa tietoja.

Sitä vastoin, jos asetat UI-komponentin vain luku -tilaksi ilman, että konfiguroit itse bindingia vain luku -tilaksi, se yksinkertaisesti estää käyttäjää tekemästä muutoksia UI-komponenttiin, mutta ei estä bindingin päivittämästä datamallia, jos muutoksia tapahtuu ohjelmallisesti tai muilla tavoilla.
:::

## Binding-setterit ja -getterit {#binding-getters-and-setters}

Setterit ja getterit ovat metodeja Javassa, jotka asettavat ja saavat ominaisuuksien arvot, vastaavasti. Data-binding -kontekstissa niitä käytetään määrittelemään, kuinka ominaisuuksia päivitetään ja haetaan binding-kehyksessä.

### Setterien ja getterien mukauttaminen {#customizing-setters-and-getters}

Vaikka webforJ voi automaattisesti käyttää standardeja JavaBean-nimityskäytäntöjä (esimerkiksi `getName()`, `setName()` ominaisuudelle `name`), saatat joutua määrittämään mukautettua käyttäytymistä. Tämä on tarpeellista, kun ominaisuus ei seuraa tavanomaista nimitystä tai kun tietojen käsittely vaatii lisälogiikkaa.

### Mukautettujen getterien käyttäminen {#using-custom-getters}

Mukautettuja gettereitä käytetään, kun arvon hakuprosessi sisältää enemmän kuin pelkästään ominaisuuden palauttamisen. Esimerkiksi saatat haluta muotoilla merkkijonon, laskea arvon tai kirjata tiettyjä toimintoja ominaisuuden käyttämisen yhteydessä.

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

Mukautetut setterit astuvat kuvaan, kun ominaisuuden asettaminen sisältää lisätoimia, kuten validointia, muunnosta tai sivuvaikutuksia, kuten lokittamista tai muiden sovelluksen osien ilmoittamista.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useSetter((hero, name) -> {
        System.out.println("Päivitetään nimeä " + hero.getName() + " uudeksi " + name);
        hero.setName(name); // Lisätoimenpide: lokittaminen
    });
```
