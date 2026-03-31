---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
_i18n_hash: 7bdcafe322080112206dd70e6a2146c7
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

`PasswordField`-komponentti mahdollistaa käyttäjien syöttää salasanaa turvallisesti. Se näkyy yksirivisenä tekstieditorina, jossa syötetty teksti on peitetty, ja se on tyypillisesti korvattu symboleilla, kuten tähti ("*") tai pisteet ("•"). Tarkka symboli voi vaihdella selaimen ja käyttöjärjestelmän mukaan.

<!-- INTRO_END -->

## Käyttö `PasswordField` {#using-passwordfield}

<ParentLink parent="Field" />

`PasswordField` laajentaa yhteistä `Field`-luokkaa, joka tarjoaa yhteisiä ominaisuuksia kaikille kenttäkomponenteille. Seuraava esimerkki luo `PasswordField`-komponentin, jossa on etiketti ja paikkamerkki.

<ComponentDemo 
path='/webforj/passwordfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java'
/>

## Kentän arvo {#field-value}

`PasswordField`-komponentti tallentaa ja noutaa arvonsa tavallisena `String`:nä, kuten `TextField`, mutta visuaalinen esitys on peitetty piilottaakseen merkit näkymästä.

Voit noutaa nykyisen arvon seuraavasti:

```java
passwordField.getValue();
```

:::warning herkkä tiedot
Vaikka kenttä peittää sisällön visuaalisesti, `getValue()`-funktion palautettu arvo on silti tavallinen merkkijono. Ole varovainen käsitellessäsi herkkiä tietoja ja salaa tai muunna se ennen tallennusta.
:::

Voit asettaa tai resetoinnin arvon ohjelmallisesti:

```java
passwordField.setValue("MySecret123!");
```

Jos käyttäjä ei ole syöttänyt arvoa ja oletusarvoa ei ole asetettu, kenttä palauttaa tyhjän merkkijonon (`""`).

Tämä toiminta jäljittelee HTML:n natiivista `<input type="password">`, jossa `value`-ominaisuus pitää nykyisen syötteen.

## Käytöt {#usages}

`PasswordField`-komponentti on paras käytössä tilanteissa, joissa on tärkeää tallentaa tai käsitellä herkkiä tietoja, kuten salasanoja tai muuta luottamuksellista tietoa. Tässä on esimerkkejä, milloin `PasswordField`-komponenttia tulisi käyttää:

1. **Käyttäjän todennus ja rekisteröinti**: Salasanojen kentät ovat elintärkeitä sovelluksissa, joissa on mukana käyttäjän todennus tai rekisteröintiprosesseja, joissa vaaditaan turvallista salasanasyöttöä.

2. **Turvalliset lomakekentät**: Kun suunnitellaan lomakkeita, jotka vaativat herkkiä tietoja, kuten luottokorttitietoja tai henkilöllisyysnumeroita (PIN), `PasswordField`-komponentin käyttö varmistaa tietojen syöttämisen turvallisesti.

3. **Tilinhallinta ja profiiliasetukset**: Salasanojen kentät ovat arvokkaita sovelluksissa, jotka liittyvät tilinhallintaan tai profiiliasetuksiin, ja ne mahdollistavat käyttäjien salasanan turvallisen muuttamisen tai päivittämisen.

## Salasanan näkyvyys {#password-visibility}

Käyttäjät voivat paljastaa `PasswordField`-kentän arvon napsauttamalla paljastuskuvaketta. Tämä mahdollistaa käyttäjien varmistaa, mitä he ovat syöttäneet, tai kopioida tiedot leikepöydälle. Kuitenkin korkean turvallisuuden ympäristöissä voit käyttää `setPasswordReveal()`-toimintoa poistaaksesi paljastuskuvakkeen ja estääksesi käyttäjiä näkemästä arvoa. Voit tarkistaa, voiko käyttäjä käyttää paljastuskuvaketta arvon näyttämiseen `isPasswordReveal()`-menetelmällä.

## Kaavionmukaisuus {#pattern-matching}

Säännöllisen lausekkeen kaavan soveltaminen `PasswordField`-komponenttiin `setPattern()`-menetelmää käyttäen on vahvasti suositeltavaa. Tämä mahdollistaa merkkisääntöjen ja rakenteellisten vaatimusten täyttämisen, pakottaen käyttäjät luomaan turvallisia ja sääntöjen mukaisia käyttäjätunnuksia. Kaavioidenmukaisuus on erityisen hyödyllistä, kun pakotetaan vahvoja salasanasääntöjä, kuten vaatimuksesta käyttää sekä isoja että pieniä kirjaimia, numeroita ja symboleita.

Kaavan on noudatettava [JavaScriptin säännöllisten lausekkeiden](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) syntaksia selaimen tulkitsemana. `u` (Unicode) -lippua käytetään sisäisesti varmistamaan kelpoisuus kaikilla Unicode-koodipisteillä. Älä **lisää** eteen ja taakse eteenpäin viivoja (`/`) kaavan ympärille.

Seuraavassa pätkässä kaava vaatii ainakin yhden pienen kirjaimen, yhden ison kirjaimen, yhden numeron ja vähintään 8 merkin pituuden.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

Jos kaava on puuttuva tai virheellinen, validointia ei sovelleta.

:::tip
Käytä `setLabel()`-toimintoa antaaksesi selkeän etiketin, joka kuvaa salasana-kentän tarkoitusta. Auttaaksesi käyttäjiä ymmärtämään salasanojen vaatimuksia, käytä `setHelperText()`-toimintoa näyttääksesi ohjeita tai sääntöjä suoraan kentän alapuolella.
:::


## Vähimmäis- ja enimmäispituus {#minimum-and-maximum-length}

Voit hallita salasanan syötteen sallittua pituutta käyttämällä `setMinLength()` ja `setMaxLength()` `PasswordField`-komponentissa.

`setMinLength()`-menetelmä määrittää vähimmäismäärän merkkejä, jotka käyttäjän on syötettävä kenttään, jotta se menee läpi validoinnin. Tämän arvon on oltava ei-negatiivinen kokonaisluku, eikä sen tulisi ylittää enimmäispituutta, jos sellainen on asetettu.

```java
passwordField.setMinLength(8); // Vähintään 8 merkkiä
```

Jos käyttäjä syöttää vähemmän merkkejä kuin vähimmäispituus, syöttö epäonnistuu rajoitusvalidoinnissa. Tätä validaatiota sovelletaan vain, kun kentän arvoa muokkaa käyttäjä.

`setMaxLength()`-menetelmä asettaa kentässä sallitun merkkimäärän enimmäismäärän. Arvon on oltava 0 tai suurempi. Jos sitä ei määritetä tai se on asetettu virheelliseen arvoon, kentällä ei ole ylärajaa merkeille.

```java
passwordField.setMaxLength(20); // Enintään 20 merkkiä
```

Jos syöttö ylittää enimmäismerkkirajan, kenttä epäonnistuu rajoitusvalidoinnissa. Kuten vähimmäisrajoituksen kohdalla, tämä sääntö koskee vain, kun käyttäjä päivittää kentän arvoa.

:::tip
Käytä sekä `setMinLength()` että `setMaxLength()` yhdessä luodaksesi tehokkaita syöttörajoja. Katso [HTML:n pituusrajoitusten dokumentaatio](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength) lisäviitteeksi.
:::


## Parhaat käytännöt {#best-practices}

Koska `PasswordField`-komponentti liittyy usein herkkiin tietoihin, ota huomioon seuraavat parhaat käytännöt käytettäessä `PasswordField`-komponenttia:

- **Tarjoa salasanojen vahvuuspalautetta**: Sisällytä salasanojen vahvuusindikaattoreita tai palautemekanismeja auttaaksesi käyttäjiä luomaan vahvoja ja turvallisia salasanoja. Arvioi tekijöitä, kuten pituus, monimutkaisuus ja isoja ja pieniä kirjaimia, numeroita ja erikoismerkkejä.

- **Pakota salasanojen tallentaminen**: Älä koskaan tallenna salasanoja selkeänä tekstinä. Sen sijaan toteuta asianmukaisia turvallisuustoimenpiteitä käsitelläksesi ja tallentaaksesi salasanoja turvallisesti sovelluksessasi. Käytä teollisuuden standardien mukaisia salausalgoritmeja salasanoille ja muille herkille tiedoille.

- **Salasanan vahvistaminen**: Sisällytä lisävahvistuskenttä, kun käyttäjä muuttaa tai luo salasanan. Tämä toimenpide auttaa vähentämään kirjoitusvirheiden todennäköisyyttä ja varmistaa, että käyttäjät syöttävät haluamansa salasanan oikein.

- **Salli salasanan nollaus**: Jos sovelluksesi liittyy käyttäjätiliin, tarjoa vaihtoehto käyttäjille, että he voivat nollata salasanansa. Tämä voi olla "Unohditko salasanasi" -toiminto, joka käynnistää salasanan palautusprosessin.

- **Esteettömyys**: Määritä `PasswordField` esteettömyys mielessä, jotta se täyttää esteettömyysstandardit, kuten tarjoamalla oikeat etiketit ja yhteensopivuuden apuvälineiden kanssa.
