---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
_i18n_hash: b0641475acf187af7c45d6786506010d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

`PasswordField`-komponentti mahdollistaa käyttäjien syöttää salasanoja turvallisesti. Se näytetään yksirivisenä tekstieditorina, jossa syötetty teksti on peitetty, yleensä korvattu symboleilla kuten tähti ("*") tai pisteet ("•"). Tarkka symboli voi vaihdella selaimen ja käyttöjärjestelmän mukaan.

<!-- INTRO_END -->

## Using `PasswordField` {#using-passwordfield}

<ParentLink parent="Field" />

`PasswordField` laajentaa jaettua `Field`-luokkaa, joka tarjoaa yhteisiä ominaisuuksia kaikille kenttäkomponenteille. Seuraava esimerkki luo `PasswordField`-komponentin, jossa on etiketti ja paikkamerkkiteksti.

<ComponentDemo
path='/webforj/passwordfield'
files={['src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java']}
/>

## Kentän arvo {#field-value}

`PasswordField`-komponentti tallentaa ja palauttaa arvonsa tavallisena `String`-arvona, samanlaisena kuin `TextField`, mutta peitettynä visuaalisesti piilottamaan merkit.

Voit palauttaa nykyisen arvon käyttämällä:

```java
passwordField.getValue();
```

:::warning arkaluontoiset tiedot
Vaikka kenttä visuaalisesti peittää sisällön, `getValue()`-kutsusta palautettu arvo on edelleen tavallinen merkkijono. Ole tietoinen tästä, kun käsittelet arkaluontoisia tietoja ja salaa tai muunna se ennen tallentamista.
:::

Voit asettaa tai nollata arvon ohjelmallisesti:

```java
passwordField.setValue("MySecret123!");
```

Jos käyttäjä ei ole syöttänyt arvoa eikä oletusarvoa ole asetettu, kenttä palauttaa tyhjän merkkijonon (`""`).

Tämä käyttäytyminen jäljittelee natiivin HTML `<input type="password">`-kentän toimintaa, jossa `value`-ominaisuus pitää nykyistä syötettä.

## Käytöt {#usages}

`PasswordField`-komponentti on parhaiten käytettävissä skenaarioissa, joissa on tärkeää tallentaa tai käsitellä arkaluontoisia tietoja, kuten salasanoja tai muita luottamuksellisia tietoja. Tässä on joitakin esimerkkejä siitä, milloin käyttää `PasswordField`-komponenttia:

1. **Käyttäjän tunnistus ja rekisteröinti**: Salasanakentät ovat välttämättömiä sovelluksissa, jotka liittyvät käyttäjän tunnistamiseen tai rekisteröintiprosesseihin, joissa tarvitaan turvallista salasanan syöttöä.

2. **Turvalliset lomakeinputit**: Suunniteltaessa lomakkeita, jotka vaativat arkaluontoisten tietojen syöttämistä, kuten luottokorttitietoja tai henkilöllisyysnumerot (PIN), `PasswordField`-komponentin käyttö suojaa näiden tietojen syöttämistä.

3. **Tilinhallinta ja profiiliasetukset**: Salasanakentät ovat arvokkaita sovelluksissa, jotka liittyvät tilinhallintaan tai profiiliasetuksiin, mikä mahdollistaa käyttäjien vaihtaa tai päivittää salasanojaan turvallisesti.

## Salasanan näkyvyys {#password-visibility}

Käyttäjät voivat paljastaa `PasswordField`-kentän arvon napsauttamalla paljastusikonia. Tämä mahdollistaa käyttäjien tarkistaa, mitä he ovat syöttäneet, tai kopioida tiedot leikepöydälle. Kuitenkin korkean turvallisuuden ympäristöissä voit käyttää `setPasswordReveal()`-menetelmää poistaaksesi paljastusikoni ja estääksesi käyttäjiä näkemästä arvoa. Voit tarkistaa, voiko käyttäjä käyttää paljastusikonia arvon näyttämiseen `isPasswordReveal()`-menetelmällä.

## Mallin vastaavuus {#pattern-matching}

Säännöllisen lausekkeen mallin soveltaminen `PasswordField`-komponenttiin `setPattern()`-menetelmän avulla on vahvasti suositeltavaa. Tämä mahdollistaa merkkisääntöjen ja rakenteellisten vaatimusten noudattamisen, pakottaen käyttäjiä luomaan turvallisia ja vaatimusten mukaisia tunnuksia. Mallinvastaavuus on erityisen hyödyllistä, kun noudatetaan vahvoja salasanasääntöjä, kuten vaaditaan sekoitus suurista ja pienistä kirjaimista, numeroista ja symboleista.

Mallin on noudatettava [JavaScriptin säännöllisten lausekkeiden](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) syntaksia selaimen tulkitsemana. `u` (Unicode) -lippua käytetään sisäisesti varmistaamaan voimassaolon kaikilla Unicode-koodipisteillä. Älä **lisää** eteen tai taakse vinoviivoja (`/`) mallin ympärille.

Seuraavassa koodinäytteessä malli vaatii vähintään yhden pienen kirjaimen, yhden suuren kirjaimen, yhden numeron ja vähintään 8 merkin pituuden.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

Jos mallia ei ole tai se on virheellinen, voimassaoloa ei sovelleta.

:::tip
Käytä `setLabel()`-menetelmää tarjotaksesi selkeä etiketti, joka kuvaa salasanakentän tarkoitusta. Auttaaksesi käyttäjiä ymmärtämään salasana vaatimuksia, käytä `setHelperText()`-menetelmää antaaksesi ohjeita tai sääntöjä suoraan kentän alle.
:::


## Vähimmäis- ja enimmäispituus {#minimum-and-maximum-length}

Voit hallita salasanan syötteen sallittua pituutta käyttämällä `setMinLength()` ja `setMaxLength()`-menetelmiä `PasswordField`-komponentille.

`setMinLength()`-menetelmä määrittää vähimmäismäärän merkkejä, jotka käyttäjän on syötettävä kenttään tullakseen hyväksytyksi. Tämän arvon on oltava ei-negatiivinen kokonaisluku, eikä sen tulisi ylittää asetettua enimmäispituutta.

```java
passwordField.setMinLength(8); // Vähintään 8 merkkiä
```

Jos käyttäjä syöttää vähemmän merkkejä kuin vähimmäismäärä, syöte epäonnistuu rajoitusvalidoinnissa. Tämä validointi sovelletaan vain silloin, kun kentän arvoa muokkaa käyttäjä.

`setMaxLength()`-menetelmä määrittää kentässä sallitun enimmäismäärän merkkejä. Arvon on oltava 0 tai suurempi. Jos sitä ei ole määritelty tai se on asetettu virheelliseen arvoon, kentällä ei ole ylärajaa merkkimäärälle.

```java
passwordField.setMaxLength(20); // Enintään 20 merkkiä
```

Jos syöte ylittää enimmäismerkki rajan, kenttä epäonnistuu rajoitusvalidoinnissa. Kuten vähimmäismäärä, tämä sääntö sovelletaan vain, kun käyttäjä päivittää kentän arvoa.

:::tip
Käytä sekä `setMinLength()` että `setMaxLength()` yhdessä luodaksesi tehokkaita syöterajoja. Katso [HTML-pituusrajojen asiakirjat](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength) lisää viitteitä varten.
:::


## Parhaat käytännöt {#best-practices}

Koska `PasswordField`-komponentti liittyy usein arkaluontoisiin tietoihin, harkitse seuraavia parhaita käytäntöjä, kun käytät `PasswordField`-komponenttia:

- **Tarjoa salasana vahvuuspalautetta**: Sisällytä salasana vahvuuden indikaattoreita tai palautemechanismeja auttaaksesi käyttäjiä luomaan vahvoja ja turvallisia salasanoja. Arvioi tekijöitä, kuten pituus, monimutkaisuus ja sekoitus suuresta ja pienestä kirjaimesta, numeroista ja erikoismerkeistä.

- **Pakota salasanan tallennus**: Älä koskaan tallenna salasanoja selkokirjana. Sen sijaan toteuta asianmukaisia turvallisuustoimenpiteitä salasanan turvallista käsittelyä ja tallentamista varten sovelluksessasi. Käytä alan standardsalasanayksiköitä salasanoille ja muille arkaluontoisille tiedoille.

- **Salasanan vahvistaminen**: Sisällytä lisävahvistuskenttä, kun käyttäjä vaihtaa tai luo salasanan. Tämä toimenpide auttaa vähentämään kirjoitusvirheiden todennäköisyyttä ja varmistaa, että käyttäjät syöttävät haluamansa salasanan tarkasti.

- **Mahdollista salasanan nollaaminen**: Jos sovelluksesi liittyy käyttäjätiliin, tarjoa vaihtoehto käyttäjien salasanan nollaamiseen. Tämä voi olla "Unohditko salasanasi" -toiminto, joka käynnistää salasanan palautusprosessin.

- **Esteettömyys**: Aseta `PasswordField` esteettömyys mielessä, jotta se täyttää esteettömyysstandardit, kuten asianmukaiset etikettit ja yhteensopivuuden apuvälineiden kanssa.
