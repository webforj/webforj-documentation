---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: 626c7e147632731dfdc761116a8abdc9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` on tekstiä syöttävä kenttä, joka on suunniteltu strukturoituun numeeriseen syöttöön. Se varmistaa, että numerot muotoillaan johdonmukaisesti määritetyn maskin perusteella, mikä tekee siitä erityisen hyödyllisen talouslomakkeissa, hintakentissä tai missä tahansa syötteessä, jossa tarkkuus ja luettavuus ovat tärkeitä.

Tämä komponentti tukee numeroiden muotoilua, desimaali- ja ryhmittelymerkkejä sekä valinnaisia arvorajoja, kuten minimi- tai maksimiarvoja.

## Perusasiat {#basics}

`MaskedNumberField` voidaan instansioida parametreilla tai ilman. Se tukee alkuarvon, labelin, paikkamerkin ja tapahtumakuuntelijan asettamista, joka reagoi arvojen muutoksiin.

Tässä demossa esitellään **Vihje Laskin**, joka käyttää `MaskedNumberField` -komponenttia intuitiiviseen numeeriseen syöttöön. Yksi kenttä on määritetty hyväksymään muotoiltu laskutusmäärä, kun taas toinen tallentaa kokonaislukuisen tipin prosenttiosuuden. Molemmat kentät soveltavat numeerisia maskseja varmistaakseen johdonmukaisen ja ennustettavan muotoilun.

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## Maskisäännöt {#mask-rules}

`MaskedNumberField` käyttää maskijonoa ohjaamaan, kuinka numeerinen syöttö muotoillaan ja näytetään. 
Jokainen merkki maskissa määrittää erityisen muotoilukäyttäytymisen, mikä mahdollistaa tarkkaa hallintaa numeroiden näkyvyydestä.

### Maskimerkit {#mask-characters}

| Merkki   | Kuvaus |
|----------|--------|
| `0`      | Korvataan aina numerolla (0–9). |
| `#`      | Estää etunollat. Korvataan täyttömerkillä desimaalipisteen vasemmalla puolella. Viimeisten numeroiden kohdalla korvataan tilalla tai nollalla. Muuten korvataan numerolla. |
| `,`      | Käytetään ryhmittelyerottimena (esim. tuhannet). Korvataan täyttömerkillä, jos edellä ei ole numeroita. Muuten näkyy pilkku. |
| `-`      | Näyttää miinusmerkin (`-`), jos numero on negatiivinen. Korvataan täyttömerkillä, jos positiivinen. |
| `+`      | Näyttää `+` positiivisille tai `-` negatiivisille numeroille. |
| `$`      | Tuottaa aina dollarimerkin. |
| `(`      | Liittää vasemman sulkumerkin `(` negatiivisille arvoille. Korvataan täyttömerkillä, jos positiivinen. |
| `)`      | Liittää oikean sulkumerkin `)` negatiivisille arvoille. Korvataan täyttömerkillä, jos positiivinen. |
| `CR`     | Näyttää `CR` negatiivisille numeroille. Näyttää kaksi tyhjää tilaa, jos numero on positiivinen. |
| `DR`     | Näyttää `CR` negatiivisille numeroille. Näyttää `DR` positiivisille numeroille. |
| `*`      | Liittää asteriskin `*`. |
| `.`      | Merkitsee desimaalipistettä. Jos tulosteessa ei esiinny numeroita, korvataan täyttömerkillä. Desimaaleiden jälkeen täyttömerkit käsitellään tiloina. |
| `B`      | Muuttuu aina tilaksi. Mikä tahansa muu kirjainmerkitään sellaisenaan. |

Joitakin yllä mainituista merkkeistä voi esiintyä useammin maskissa muotoilua varten. Näitä ovat `-`, `+`, `$` ja
`(`. Jos jokin näistä merkeistä on läsnä maskissa, ensimmäinen, jota kohdellaan, siirretään viimeiseen paikkaan, jossa `#` tai `,` korvattiin täyttömerkillä. Jos ei tällaista paikkaa ole, kaksoismerkki jää paikalleen.

:::info Ei automaattista pyöristystä
Maski kentässä ei **PYÖRISTÄ**. Esimerkiksi, jos asetat arvon `12.34567`
kenttään, joka on maskattu `###0.00`, saat `12.34`.
:::

## Ryhmittely- ja desimaalierottimet {#group-and-decimal-separators}

`MaskedNumberField` tukee **ryhmittely**- ja **desimaalimerkkien** mukauttamista, mikä helpottaa numeroiden muotoilua eri paikallisiin vaatimuksiin tai liiketoimintakäytäntöihin.

- **Ryhmittelyerotin** käytetään visuaalisena erottimena tuhansille (esim. `1,000,000`).
- **Desimaalierotin** osoittaa numeroiden murtoluvun osan (esim. `123.45`).

Tämä on hyödyllistä kansainvälisissä sovelluksissa, joissa eri alueet käyttävät erilaisia merkkejä (esim. `.` vs `,`).

```java
field.setGroupCharacter(".");   // esimerkki: 1.000.000
field.setDecimalCharacter(","); // esimerkki: 123,45
```

:::tip Oletuskäyttäytyminen
Oletuksena `MaskedNumberField` soveltaa ryhmittely- ja desimaalierottimia sovelluksen nykyisen paikallisuuden mukaan. Voit ohittaa niitä milloin tahansa tarjoamiesi asetusten avulla.
:::

## Negatiivinen {#negateable}

`MaskedNumberField` tukee vaihtoehtoa, jolla voidaan hallita, sallitaanko negatiiviset numerot.

Oletuksena negatiiviset arvot, kuten `-123.45`, ovat sallittuja. Estääksesi tämän, käytä `setNegateable(false)` rajoittaaksesi syötteen vain positiivisiin arvoihin.

Tämä on hyödyllistä liiketoimintaskenaarioissa, joissa arvot, kuten määrät, yhteismäärät tai prosenttiosuudet, on aina oltava ei-negatiivisia.

```java
field.setNegateable(false);
```

Kun `negatable` asetetaan arvoon `false`, kenttä estää kaikki yrittämät syöttää miinusmerkkiä tai muuten syöttää negatiivisia arvoja.

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## Minimi- ja maksimiarvot {#min-and-max-values}

`MaskedNumberField` tukee numeeristen raja-arvojen asettamista `setMin()` ja `setMax()` avulla. 
Nämä rajoitukset auttavat varmistamaan, että käyttäjän syöte pysyy voimassa olevassa ja odotetussa alueessa.

- **Minimiarvo**  
  Käytä `setMin()` määrittääksesi alin hyväksyttävä numero:

  ```java
  field.setMin(10.0); // Minimiarvo: 10
  ```

  Jos käyttäjä syöttää numeron tämän rajan alapuolelle, se katsotaan voimattomaksi.

- **Maksimiarvo**  
  Käytä `setMax()` määrittääksesi korkein hyväksyttävä numero:

  ```java
  field.setMax(100.0); // Maksimiarvo: 100
  ```

  Arvot, jotka ylittävät tämän rajan, merkitään voimattomiksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedNumberField` tukee palautustointa, joka nollaa kentän arvon ennalta määritettyyn tilaan. 
Tämä on hyödyllistä, kun käyttäjät tarvitsevat muutosten kumoamista, vahingossa tehtyjen muokkausten palauttamista tai paluuta tunnettuun oletusarvoon.

Ota tämä käyttäytyminen käyttöön määrittämällä kohdearvo `setRestoreValue()` avulla. 
Tarvittaessa kenttä voidaan palauttaa ohjelmallisesti käyttämällä `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti** käyttäen `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuspalautusnäppäin, ellei sitä ole ylikirjoitettu)

Palautusarvo on asetettava nimenomaisesti. Jos sitä ei määritetä, toiminto ei palauta kenttää.

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` laajentaa [`MaskedNumberField`](#basics) lisäämällä spinneriohjaimet, jotka antavat käyttäjille mahdollisuuden lisätä tai vähentää arvoa käyttämällä askelpainikkeita tai nuolinäppäimiä. 
Tämä on ihanteellinen syötteissä, kuten määrät, hinnoittelusäätö, arviointiohjaimet tai missä tahansa tilanteessa, jossa käyttäjät tekevät vähittäismuutoksia.

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### Avainominaisuudet {#key-features}

- **Askelveloitukset**  
  Käytä `setStep()` määrittääksesi, kuinka paljon arvon tulee muuttua jokaisen pyörityksen myötä:

  ```java
  spinner.setStep(5.0); // Jokainen pyöritys lisää tai vähentää 5
  ```

- **Interaktiiviset ohjaimet**  
  Käyttäjät voivat napsauttaa spinner-painikkeita tai käyttää näppäimistösyöttöä arvon säätämiseen.

- **Kaikki ominaisuudet MaskedNumberFieldista**  
  Täydellinen tuki maskeille, muotoilulle, ryhmittely-/desimaalimerkeille, min/max-rajoituksille ja palautuslogiikalle.

## Tyylittely {#styling}

<TableBuilder name="MaskedNumberField" />
