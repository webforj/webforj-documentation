---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
sidebar_class_name: updated-content
_i18n_hash: 180bd1578c78bf1ee9e746d23f76ec96
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

<ParentLink parent="Field" />

`PasswordField`-komponentti mahdollistaa käyttäjien syöttää salasanan turvallisesti. Se esitetään yksirivisenä tekstieditorina, jossa syötetyt tiedot ovat piilotettu, yleensä korvattu symboleilla kuten tähtimerkillä (”*”) tai pisteillä (”•”). Tarkka symboli voi vaihdella selaimen ja käyttöjärjestelmän mukaan.

<ComponentDemo 
path='/webforj/passwordfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java'
/>

## Kentän arvo {#field-value}

`PasswordField`-komponentti tallentaa ja noutaa arvonsa yksinkertaisena `String`-tyyppinä, samankaltaisesti kuin `TextField`, mutta visuaalisesti piilotettuna, jotta merkit eivät ole näkyvissä.

Voit noutaa nykyisen arvon käyttämällä:

```java
passwordField.getValue();
```

:::warning herkkä data
Vaikka kenttä visuaalisesti peittää sisällön, `getValue()`-metodista palautettu arvo on silti yksinkertainen merkkijono. Ole tästä tietoinen käsitellessäsi herkkiä tietoja ja salaa tai muunnat arvo ennen tallennusta.
:::

Aseta tai nollaa arvo ohjelmallisesti:

```java
passwordField.setValue("MySecret123!");
```

Jos käyttäjä ei ole syöttänyt arvoa ja oletus ei ole asetettu, kenttä palauttaa tyhjää merkkijonoa (`""`).

Tämä käyttäytyminen muistuttaa nativen HTML `<input type="password">`, jossa `value`-ominaisuus pitää nykyistä syötettä.

## Käytännöt {#usages}

`PasswordField`-komponenttia kannattaa käyttää tilanteissa, joissa on tärkeää tallentaa tai käsitellä herkkiä tietoja, kuten salasanoja tai muita luottamuksellisia tietoja. Tässä on joitakin esimerkkejä siitä, milloin `PasswordField`-komponenttia tulisi käyttää:

1. **Käyttäjäautentikointi ja rekisteröinti**: Salasanakentät ovat elintärkeitä sovelluksissa, jotka liittyvät käyttäjäautentikointiin tai rekisteröintiprosesseihin, joissa turvallinen salasanan syöttö on tarpeen.

2. **Turvalliset lomake-syötteet**: Kun suunnitellaan lomakkeita, joissa vaaditaan herkän tiedon syöttämistä, kuten luottokorttitietoja tai henkilöllisyysnumeroita (PIN), `PasswordField`-komponentin käyttäminen varmistaa tietojen turvallisen syöttämisen.

3. **Tili- ja profiiliasetukset**: Salasanakentät ovat arvokkaita sovelluksissa, joissa on tili- tai profiiliasetuksia, jolloin käyttäjät voivat vaihtaa tai päivittää salasanojaan turvallisesti.

## Salasanan näkyvyys {#password-visibility}

Käyttäjät voivat paljastaa `PasswordField`-kentän arvon napsauttamalla paljastus-ikonia. Tämä antaa käyttäjille mahdollisuuden tarkistaa syöttämänsä tiedot tai kopioida tiedot leikepöydälle. Kuitenkin erittäin turvallisissa ympäristöissä voit käyttää `setPasswordReveal()`-metodia poistaaksesi paljastus-ikonin ja estääksesi käyttäjiä näkemästä arvoa. Voit tarkistaa, voiko käyttäjä käyttää paljastus-ikonia arvon näyttämiseen `isPasswordReveal()`-metodilla.

## Mallin tarkistus {#pattern-matching}

Suosittelemme vahvasti säännöllisen lausekkeen mallin sovittamista `PasswordField`-komponenttiin `setPattern()`-metodin avulla. Tämä mahdollistaa merkkisääntöjen ja rakenteellisten vaatimusten pakottamisen, jolloin käyttäjät joutuvat luomaan turvallisia ja sääntöjen mukaisia tunnuksia. Mallin tarkistus on erityisen hyödyllinen, kun pakotetaan vahvoja salasanasääntöjä, kuten vaatimusta sekoituksesta suurista ja pienistä kirjaimista, numeroista ja symboleista.

Malli on noudatettava [JavaScriptin säännöllisten lausekkeiden](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) syntaksia, kuten selain tulkitsee sen. `u` (Unicode) -lippua käytetään sisäisesti varmistaaksesi validoinnin kaikkien Unicode-koodipisteiden välillä. Älä **lisää** eteen ja taakse vinoviivoja (`/`) mallin ympärille.

Seuraavassa esimerkissä malli vaatii vähintään yhden pienen kirjaimen, yhden suuremman kirjaimen, yhden numeron ja vähimmäispituuden 8 merkkiä.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

Jos mallia ei ole, tai se on virheellinen, ei validointia sovelleta.

:::tip
Käytä `setLabel()`-metodia antaaksesi selkeän etiketin, joka kuvaa salasanakentän tarkoitusta. Auttaaksesi käyttäjiä ymmärtämään salasanavaatimuksia, käytä `setHelperText()`-metodia näyttämään ohjeita tai sääntöjä suoraan kentän alapuolella.
:::

## Vähimmäis- ja enimmäispituus {#minimum-and-maximum-length}

Voit hallita salasanasyötteen sallittua pituutta käyttämällä `setMinLength()` ja `setMaxLength()` `PasswordField`-komponentissa.

`setMinLength()`-metodi määrittää minimimäärän merkkejä, jotka käyttäjän on syötettävä kenttään hyväksyäkseen validoinnin. Tämän arvon on oltava ei-negatiivinen kokonaisluku, eikä sen tule ylittää enimmäispituutta, jos sellainen on asetettu.

```java
passwordField.setMinLength(8); // Vähintään 8 merkkiä
```

Jos käyttäjä syöttää vähemmän merkkejä kuin minimimäärä, syöte epäonnistuu rajoitteiden validoinnissa. Tämä validointi sovelletaan vain, kun kentän arvoa muokataan käyttäjän toimesta.

`setMaxLength()`-metodi asettaa kentässä sallitun enimmäismäärän merkkejä. Arvon on oltava 0 tai suurempi. Jos sitä ei ole määritetty tai se on asetettu virheelliseksi arvoksi, kentällä ei ole ylärajaa merkkejä.

```java
passwordField.setMaxLength(20); // Enintään 20 merkkiä
```

Jos syöte ylittää enimmäisrajan, kenttä epäonnistuu rajoitteiden validoinnissa. Kuten minimipituus, tämä sääntö sovelletaan vain, kun käyttäjä päivittää kentän arvoa.

:::tip
Käytä sekä `setMinLength()` että `setMaxLength()` yhdessä luodaksesi tehokkaita syöttörajoja. Katso [HTML:n pituusrajoitusten dokumentaatio](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength) lisää viitteitä varten.
:::

## Parhaat käytännöt {#best-practices}

Koska `PasswordField`-komponentti liittyy usein herkkiin tietoihin, harkitse seuraavia parhaita käytäntöjä sen käytössä:

- **Tarjoa salasanan vahvuuden palautetta**: Liitä salasanan vahvuuden indikaattoreita tai palautemekanismeja auttaaksesi käyttäjiä luomaan vahvoja ja turvallisia salasanoja. Arvioi tekijöitä, kuten pituus, monimutkaisuus, sekä suurten ja pienten kirjainten, numeroiden ja erikoismerkkien yhdistelmät.

- **Pakota salasanan tallennus**: Älä koskaan tallenna salasanoja selkokielisinä. Sen sijaan toteuta asianmukaiset turvallisuustoimenpiteet salasanojen turvalliseen käsittelyyn ja tallentamiseen sovelluksessasi. Käytä alan standardin mukaisia salausalgoritmeja salasanoille ja muille herkille tiedoille.

- **Salasanan vahvistus**: Sisällytä lisävahvistuskenttä, kun käyttäjä vaihtaa tai luo salasanan. Tämä toimenpide auttaa vähentämään kirjoitusvirheiden todennäköisyyttä ja varmistamaan, että käyttäjät syöttävät haluamansa salasanan tarkasti.

- **Salli salasanan palautus**: Jos sovelluksesi liittyy käyttäjätiliin, tarjoa käyttäjille mahdollisuus palauttaa salasanansa. Tämä voi olla "Unohditko salasanasi" -toiminnollisuus, joka käynnistää salasanan palautusprosessin.

- **Esteettömyys**: Määrittele `PasswordField` esteettömyys mielessä pitäen, jotta se täyttää esteettömyysstandardit, kuten tarjoamalla asianmukaiset etiketit ja yhteensopivuuden apuvälineiden kanssa.
