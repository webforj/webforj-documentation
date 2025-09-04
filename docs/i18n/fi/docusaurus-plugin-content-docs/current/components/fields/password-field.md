---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
sidebar_class_name: updated-content
_i18n_hash: ca055ca343a756152533eb1ab3ec5c8c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

<ParentLink parent="Field" />

`PasswordField`-komponentti sallii käyttäjien syöttää salasanan turvallisesti. Se näytetään yksirivisenä tekstieditorina, jossa syötetty teksti on peitetty, tyypillisesti vaihdettuna symboleihin, kuten tähden (“*”) tai pisteen (“•”). Tarkka symboli voi vaihdella selaimen ja käyttöjärjestelmän mukaan.

<ComponentDemo 
path='/webforj/passwordfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java'
/>

## Kentän arvo {#field-value}

`PasswordField`-komponentti tallentaa ja hakee arvonsa tavallisena `String`-muotoisena, samaan tapaan kuin `TextField`, mutta visuaalinen esitys peittää merkit näkyvistä.


Voit noutaa nykyisen arvon seuraavasti:

```java
passwordField.getValue();
```

:::warning herkkä tieto
Vaikka kenttä peittää sisällön visuaalisesti, `getValue()`-metodista palautettu arvo on silti tavallinen merkkijono. Ole tietoinen tästä käsitellessäsi arkaluontoista tietoa ja salaa tai muunna se ennen tallennusta.
:::

Aseta tai nollaa arvo ohjelmallisesti:

```java
passwordField.setValue("MySecret123!");
```

Jos käyttäjä ei ole syöttänyt arvoa eikä oletusarvoa ole asetettu, kenttä palauttaa tyhjää merkkijonoa (`""`).

Tämä käyttäytyminen jäljittelee HTML:n natiivin `<input type="password">`-elementin toimintaa, jossa `value`-ominaisuus sisältää nykyisen syötteen.


## Käytännöt {#usages}

`PasswordField`-komponenttia kannattaa käyttää tilanteissa, joissa arkaluontoisten tietojen, kuten salasanojen tai muiden luottamuksellisten tietojen, tallentaminen tai käsittely on tärkeää sovelluksessasi. Tässä on joitakin esimerkkejä, milloin käyttää `PasswordField`-komponenttia:

1. **Käyttäjän tunnistaminen ja rekisteröinti**: Salasanakentät ovat elintärkeitä sovelluksissa, jotka liittyvät käyttäjän tunnistamiseen tai rekisteröintiprosesseihin, joissa vaaditaan turvallista salasanan syöttämistä.

2. **Turvalliset lomakekentät**: Kun suunnittelet lomakkeita, jotka vaativat arkaluontoisten tietojen syöttämistä, kuten luottokorttitietoja tai henkilöllisyysnumeroita (PIN), `PasswordField`-komponentin käyttö varmistaa sellaisten tietojen syöttämisen turvallisesti.

3. **Tilin hallinta ja profiiliasetukset**: Salasanakentät ovat arvokkaita sovelluksissa, jotka liittyvät tilin hallintaan tai profiiliasetuksiin, jolloin käyttäjät voivat muuttaa tai päivittää salasanojaan turvallisesti.

## Salasanan näkyvyys {#password-visibility}

Käyttäjät voivat paljastaa `PasswordField`-kentän arvon napsauttamalla paljastuskuvaketta. Tämä mahdollistaa käyttäjien tarkistaa mitä he ovat syöttäneet tai kopioida tiedon leikepöydälle. Kuitenkin korkeaturvallisuuskäyttöympäristöissä voit käyttää `setPasswordReveal()`-metodia poistaaksesi paljastuskuvakkeen ja estääksesi käyttäjiä näkemästä arvoa. Voit tarkistaa, voivatko käyttäjät käyttää paljastuskuvaketta näyttääkseen arvon `isPasswordReveal()`-metodilla.

## Mallin tarkistaminen {#pattern-matching}

On vahvasti suositeltavaa soveltaa säännöllistä lauseketta `PasswordField`-komponenttiin `setPattern()`-metodin avulla. Tämä antaa sinun pakottaa merkki- ja rakennevaatimuksia, jotka pakottavat käyttäjiä luomaan turvallisia ja vaatimusten mukaisia tunnuksia. Mallin tarkistaminen on erityisen hyödyllistä, kun pakotetaan vahvoja salasanasääntöjä, kuten vaaditaan sekoitus isoja ja pieniä kirjaimia, numeroita ja symboleita. 

Malli on oltava [JavaScriptin säännöllisen lausekkeen](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) syntaksin mukainen, kuten selain tulkitsee sen. `u` (Unicode) -lippua käytetään sisäisesti varmistamaan validointi kaikkien Unicode-koodipisteiden osalta. Älä sisällytä etu- tai takasuoria viivoja (`/`) mallin ympärille.

Seuraavassa pätkässä malli vaatii vähintään yhden pienen kirjaimen, yhden ison kirjaimen, yhden numeron ja vähimmäispituuden 8 merkkiä.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

Jos malli on puuttuva tai virheellinen, mitään validointia ei sovelleta.

:::tip
Käytä `setLabel()`-metodia tarjotaksesi selkeä etiketti, joka kuvaa salasanakentän tarkoitusta. Auttaaksesi käyttäjiä ymmärtämään salasanavaatimuksia, käytä `setHelperText()`-metodia näyttämään ohjeita tai sääntöjä suoraan kentän alapuolella.
:::


## Vähimmäis- ja enimmäispituus {#minimum-and-maximum-length}

Voit hallita salasanasyötteen sallittua pituutta käyttämällä `setMinLength()`- ja `setMaxLength()`-metodeja `PasswordField`-komponentissa.

`setMinLength()`-metodi määrittelee vähimmäismäärän merkkejä, jotka käyttäjän on syötettävä kentässä hyväksymiseksi. Tämän arvon on oltava ei-negatiivinen kokonaisluku, eikä se saa ylittää enimmäispituutta, jos sellainen on asetettu.

```java
passwordField.setMinLength(8); // Vähintään 8 merkkiä
```

Jos käyttäjä syöttää vähemmän merkkejä kuin vähimmäismäärä, syöte epäonnistuu rajoitusvalidoinnissa. Tätä validointia sovelletaan vain, kun kentän arvoa muokataan käyttäjän toimesta.

`setMaxLength()`-metodi asettaa kentässä sallitut enimmäismerkkimäärät. Arvon on oltava 0 tai suurempi. Jos sitä ei määritellä tai se asetetaan virheelliseksi arvoksi, kentällä ei ole ylärajaa merkeille.

```java
passwordField.setMaxLength(20); // Enintään 20 merkkiä
```

Jos syöte ylittää enimmäismerkkirajan, kenttä epäonnistuu rajoitusvalidoinnissa. Kuten minimimäärä, tätä sääntöä sovelletaan vain, kun käyttäjä päivittää kentän arvoa.

:::tip
Käytä sekä `setMinLength()` että `setMaxLength()` yhdessä luodaksesi tehokkaita syötearvoja. Katso [HTML-pituusrajoitteiden dokumentaatio](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength) lisää tietoa varten.
:::


## Parhaat käytännöt {#best-practices}

Koska `PasswordField`-komponentti liittyy usein arkaluontoisiin tietoihin, ota huomioon seuraavat parhaat käytännöt käytettäessä `PasswordField`-komponenttia:

- **Tarjoa salasanan vahvuuden palautetta**: Sisällytä salasanan vahvuuden indikaattoreita tai palautemekanismeja auttaaksesi käyttäjiä luomaan vahvoja ja turvallisia salasanoja. Arvioi tekijöitä, kuten pituus, monimutkaisuus ja isoilla sekä pienillä kirjaimilla, numeroilla ja erikoisilla merkeillä sekoittaminen.

- **Pakota salasanan tallennus**: Älä koskaan tallenna salasanoja tavallisessa tekstimuodossa. Sen sijaan toteuta asianmukaiset turvallisuustoimenpiteet hallitaksesi ja tallentaaksesi salasanoja turvallisesti sovelluksessasi. Käytä alan standardin mukaista salausalgoritmia salasanojen ja muiden arkaluontoisten tietojen tallentamiseen.

- **Salasanan vahvistus**: Sisällytä lisävahvistuskenttä, kun käyttäjä vaihtaa tai luo salasanoja. Tämä toimenpide auttaa vähentämään kirjoitusvirheiden todennäköisyyttä ja varmistaa, että käyttäjät syöttävät haluamansa salasanan tarkasti.

- **Salli salasanan palautus**: Jos sovelluksesi sisältää käyttäjätilit, tarjoa käyttäjille mahdollisuus palauttaa salasanansa. Tämä voi olla "Unohditko salasanan" -toimintona, joka käynnistää salasanan palautusprosessin.

- **Saavutettavuus**: Määrittele `PasswordField`-komponentti saavutettavuus mielessä pitäen, jotta se täyttää saavutettavuusstandardit, kuten asianmukaiset etiketit ja yhteensopivuus apuvälineiden kanssa.
