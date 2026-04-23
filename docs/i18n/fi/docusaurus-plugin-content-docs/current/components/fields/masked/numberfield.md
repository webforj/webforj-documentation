---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: b528c4f1b76e02e7bd6fe132df47198c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` on tekstikenttä, joka on suunniteltu rakenteellista numeerista syöttöä varten. Se varmistaa, että numerot on muotoiltu johdonmukaisesti määritellyn maskin mukaan, mikä tekee siitä erityisen hyödyllisen talouslomakkeissa, hinnoittelukentissä tai missä tahansa syötteessä, jossa tarkkuus ja luettavuus ovat tärkeitä.

Tämä komponentti tukee numeroiden muotoilua, desimaalien/ryhmittelymerkkien lokalisointia ja valinnaisia arvorajoituksia, kuten minimien tai maksimiarvojen asettamista.

<!-- INTRO_END -->

## Perusteet {#basics}

`MaskedNumberField` voidaan luoda parametreilla tai ilman. Se tukee alkusarjan, etiketin, paikkamerkin ja tapahtumakuuntelijan asettamista reagoimaan arvon muutoksiin.

Tämä demo esittelee **Vinkkilaskurin**, joka käyttää `MaskedNumberField`-kenttää intuitiivista numeerista syöttöä varten. Yksi kenttä on määritetty hyväksymään muodollinen laskusumma, kun taas toinen kerää kokonaislukuina tippi prosentteina. Molemmat kentät soveltavat numeerisia maskeja varmistaakseen johdonmukaisen ja ennakoitavan muotoilun.

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## Maskisäännöt {#mask-rules}

`MaskedNumberField` käyttää maskin merkkijonoa hallitsemaan, miten numeerinen syöttö muotoillaan ja näytetään. 
Jokainen merkki maskissa määrittelee tietyn muotoilukäyttäytymisen, mikä mahdollistaa tarkan hallinnan siitä, miten numerot näyttävät.

:::tip Maskien soveltaminen ohjelmallisesti
Muotoillaksesi numeroita samaa maskisyntaksia käyttäen kentän ulkopuolella, esimerkiksi kun renderoidaan tietoja [`Table`](/docs/components/table/overview), käytä [`MaskDecorator`](/docs/advanced/mask-decorator) -apuluokkaa.
:::

### Maskimerkit {#mask-characters}

| Merkki | Kuvaus |
|-----------|-------------|
| `0`       | Korvataan aina numerolla (0–9). |
| `#`       | Estää etunenän nollat. Korvataan täyttömerkillä desimaalipisteen vasemmalla puolella. Jäljelle jääville numeroille korvataan tilalla välilyönnillä tai nollalla. Muuten korvataan numerolla. |
| `,`       | Käytetään ryhmittelyerottimena (esim. tuhannet). Korvataan täyttömerkillä, jos sen edessä ei ole numeroita. Muussa tapauksessa näytetään pilkkuna. |
| `-`       | Näyttää miinusmerkin (`-`), jos numero on negatiivinen. Korvataan täyttömerkillä, jos positiivinen. |
| `+`       | Näyttää `+` positiivisille tai `-` negatiivisille numeroille. |
| `$`       | Tuottaa aina dollarimerkin. |
| `(`       | Lisää vasemman sulun `(` negatiivisille arvoille. Korvataan täyttömerkillä, jos positiivinen. |
| `)`       | Lisää oikean sulun `)` negatiivisille arvoille. Korvataan täyttömerkillä, jos positiivinen. |
| `CR`      | Näyttää `CR` negatiivisille numeroille. Näyttää kaksi välilyöntiä, jos numero on positiivinen. |
| `DR`      | Näyttää `CR` negatiivisille numeroille. Näyttää `DR` positiivisille numeroille. |
| `*`       | Lisää tähden `*`. |
| `.`       | Merkitsee desimaalipistettä. Jos tulosteessa ei näy numeroita, korvataan täyttömerkillä. Desimaalin jälkeen täyttömerkit käsitellään tilana. |
| `B`       | Muuttuu aina tilaksi. Muut merkittävät merkit näytetään sellaisenaan. |

Osa yllä olevista merkeistä voi esiintyä useaan otteeseen maskissa muotoilua varten. Näitä ovat `-`, `+`, `$` ja `(`. Jos joku näistä merkeistä on läsnä maskissa, ensimmäinen, joka havaitaan, siirretään viimeiseen paikkaan, johon `#` tai `,` korvattiin täyttömerkillä. Jos tällaista sijaintia ei ole, kaksoismerkki jää paikalleen.

:::info Ei automaattista pyöristystä
Maski kentässä ei **PYÖRISTÄ**. Esimerkiksi, kun sijoitetaan arvo kuten `12.34567` kenttään, joka on maskattu muodolla `###0.00`, saat `12.34`.
:::

## Ryhmä- ja desimaalierottimet {#group-and-decimal-separators}

`MaskedNumberField` tukee ryhmä- ja desimaalimerkkien mukauttamista, mikä tekee sen helpoksi mukauttaa numeroiden muotoilua eri alueiden tai liiketoimintakäytäntöjen mukaan.

- **Ryhmäerotin** on käytössä tuhansien visuaaliseksi erottamiseksi (esim. `1,000,000`).
- **Desimaalierotin** osoittaa luvun murtoluvun osan (esim. `123.45`).

Tämä on hyödyllistä kansainvälisissä sovelluksissa, joissa eri alueet käyttävät eri merkkejä (esim. `.` vs `,`).

```java
field.setGroupCharacter(".");   // esim. 1.000.000
field.setDecimalCharacter(","); // esim. 123,45
```

:::tip Oletuskäyttäytyminen
Oletuksena `MaskedNumberField` soveltaa ryhmä- ja desimaalierottimia sovelluksen nykyisen alueasetuksen mukaan. Voit ylikirjoittaa ne milloin tahansa käyttämällä tarjoamia asetintoja.
:::

## Negoitava {#negateable}

`MaskedNumberField` tukee vaihtoehtoa hallita, sallitaanko negatiiviset numerot.

Oletuksena negatiiviset arvot kuten `-123.45` ovat sallittuja. Estääksesi tämän, käytä `setNegateable(false)` rajoittaaksesi syötettä vain positiivisiin arvoihin.

Tämä on hyödyllistä liiketoimintaskenaarioissa, joissa arvot kuten määrät, summat tai prosenttiosuudet on aina oltava ei-negatiivisia.

```java
field.setNegateable(false);
```

Kun `negatable` on asetettu `false`, kenttä estää kaikki yritykset syöttää miinusmerkkiä tai muuten syöttää negatiivisia arvoja.

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## Minimi- ja maksimimäärät {#min-and-max-values}

`MaskedNumberField` tukee numeeristen rajojen asettamista käyttäen `setMin()` ja `setMax()`. 
Nämä rajoitteet auttavat varmistamaan, että käyttäjän syöte pysyy voimassa olevassa, odotetussa alueessa.

- **Minimiarvo**  
  Käytä `setMin()` määrittämään alin hyväksyttävä luku:

  ```java
  field.setMin(10.0); // Minimiarvo: 10
  ```

  Jos käyttäjä syöttää numeron, joka on alle tämän rajan, se katsotaan kelvottomaksi.

- **Maksimiarvo**  
  Käytä `setMax()` määrittämään korkein hyväksyttävä luku:

  ```java
  field.setMax(100.0); // Maksimiarvo: 100
  ```

  Arvot, jotka ovat tämän rajan yläpuolella, merkitään kelvottomiksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedNumberField` tukee palautustoimintoa, joka palauttaa kentän arvon ennalta määritettyyn tilaan. 
Tämä voi olla hyödyllistä, kun käyttäjät tarvitsevat muutosten kumoamista, vahingossa tehtyjen muokkausten palauttamista tai palatakseen tunnettuihin oletusarvoihin.

Ota tämä toiminto käyttöön määrittämällä kohdearvo käyttäen `setRestoreValue()`. 
Kun tarpeen, kenttä voidaan palauttaa ohjelmallisesti käyttämällä `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti** käyttämällä `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuspalautusnäppäin, ellei sitä ylikirjoiteta)

Palautusarvo on määritettävä eksplisiittisesti. Jos sitä ei ole määritetty, toimintoa ei palauteta kenttään.

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` laajentaa [`MaskedNumberField`](#basics) lisäämällä spinnereitä, jotka antavat käyttäjille mahdollisuuden kasvattaa tai pienentää arvoa käyttämällä askelpainikkeita tai nuolinäppäimiä. 
Tämä on ihanteellinen syötteille kuten määrät, hinnoittelusäätö, arviointiohjaimet tai missä tahansa tilanteessa, jossa käyttäjät tekevät asteittaisia muutoksia.

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### Tärkeimmät ominaisuudet {#key-features}

- **Askel muutokset**  
  Käytä `setStep()` määrittääksesi, kuinka paljon arvon tulisi muuttua jokaisella kerralla:

  ```java
  spinner.setStep(5.0); // Jokainen toisto lisää tai vähentää 5
  ```

- **Interaktiiviset ohjaimet**  
  Käyttäjät voivat napsauttaa spinner-painikkeita tai käyttää näppäimistösyötettä arvon säätämiseen.

- **Kaikki `MaskedNumberField`-ominaisuudet**  
  Täysin tukee maskeja, muotoilua, ryhmittely/ desimaalimerkkejä, min/max-rajoitteita ja palautuslogiikkaa.

## Tyylittely {#styling}

<TableBuilder name="MaskedNumberField" />
