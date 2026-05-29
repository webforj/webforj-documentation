---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: a2b0a5275077beea1c053993d47aa861
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` on tekstikenttä, joka on suunniteltu jäsennellyn numeerisen syötteen vastaanottamiseen. Se varmistaa, että numerot muotoillaan johdonmukaisesti määritetyn maskin perusteella, mikä tekee siitä erityisen hyödyllisen talouslomakkeissa, hinnoittelukentissä tai missä tahansa syötteessä, jossa tarkkuus ja luettavuus ovat tärkeitä.

Tämä komponentti tukee numeromuotoilua, desimaali- ja ryhmittelymerkkejä sekä valinnaisia arvorajoja, kuten vähimmäis- tai enimmäisarvoja.

<!-- INTRO_END -->

## Perusteet {#basics}

`MaskedNumberField` voidaan alustaa parametreilla tai ilman. Se tukee aloitusarvon, etiketin, paikkamerkin ja tapahtumakuuntelijan asettamista reagoimaan arvomuutoksiin.

Tässä demossa esitellään **Vinkkilaskuri**, joka käyttää `MaskedNumberField` intuitiivista numeerista syöttöä varten. Yksi kenttä on määritetty hyväksymään muotoiltu laskutusmäärä, kun taas toinen tallentaa kokonaisluvuksi vinkin prosenttiosuuden. Molemmat kentät soveltavat numeromaskia varmistaakseen johdonmukaisen ja ennakoitavan muotoilun.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Maskisäännöt {#mask-rules}

`MaskedNumberField` käyttää maskimerkkijonoa hallitsemaan, miten numeerinen syöte muotoillaan ja näytetään. 
Jokainen merkki maskissa määrittää erityisen muotoilukäyttäytymisen, jolloin käyttäjällä on tarkka hallinta numeroiden ulkonäköön.

:::tip Maskin soveltaminen ohjelmallisesti
Jos haluat muotoilla numeroita samalla maskisynntaksilla kentän ulkopuolella, esimerkiksi tietojen renduroidessa [`Table`](/docs/components/table/overview), käytä [`MaskDecorator`](/docs/advanced/mask-decorator) -apuluokkaa.
:::

### Maskimerkit {#mask-characters}

| Merkki  | Kuvaus |
|---------|--------|
| `0`     | Korvataan aina numerolla (0–9). |
| `#`     | Estää ylimääräiset nollat. Korvataan täyttömerkillä desimaalipisteen vasemmalla puolella. Jatkuville numeroille korvataan tilalla tai nollalla. Muuten korvataan numerolla. |
| `,`     | Käytetään ryhmittelyerottimena (esim. tuhansille). Korvataan täyttömerkillä, jos sen edessä ei ole numeroita. Muussa tapauksessa näkyy pilkkuna. |
| `-`     | Näyttää miinusmerkin (`-`), jos numero on negatiivinen. Korvataan täyttömerkillä, jos positiivinen. |
| `+`     | Näyttää `+` positiivisille tai `-` negatiivisille numeroille. |
| `$`     | Aina tuloksena dollari. |
| `(`     | Lisää vasemman sulkumerkin `(` negatiivisille arvoille. Korvataan täyttömerkillä, jos positiivinen. |
| `)`     | Lisää oikean sulkumerkin `)` negatiivisille arvoille. Korvataan täyttömerkillä, jos positiivinen. |
| `CR`    | Näyttää `CR` negatiivisille numeroille. Näyttää kaksi välilyöntiä, jos numero on positiivinen. |
| `DR`    | Näyttää `CR` negatiivisille numeroille. Näyttää `DR` positiivisille numeroille. |
| `*`     | Lisää tähden `*`. |
| `.`     | Merkitsee desimaalipistettä. Jos tulosteessa ei näy numeroita, se korvataan täyttömerkillä. Desimaalin jälkeen täyttömerkit käsitellään välilyönteinä. |
| `B`     | Aina muuttuu välilyönniksi. Kaikki muut kirjaimelliset merkit näytetään sellaisenaan. |

Joidenkin edellä mainittujen merkkien voi esiintyä useammin kuin kerran maskissa muotoilua varten. Näitä ovat `-`, `+`, `$`, ja
`(`. Jos mitään näistä merkeistä on läsnä maskissa, ensimmäinen tavattu siirretään viimeiseen paikkaan, jossa `#` tai `,` oli korvattu täyttömerkillä. Jos tällaista paikkaa ei ole, kaksinkertainen merkki jätetään paikalleen.

:::info Ei automaattista pyöristystä
Maski kentässä ei **PYÖRISTÄ**. Esimerkiksi, kun sijoitat arvon kuten `12.34567`
kenttään, joka on maskattu `###0.00`, saat `12.34`.
:::

## Ryhmitys- ja desimaalierottimet {#group-and-decimal-separators}

`MaskedNumberField` tukee **ryhmittely-** ja **desimaalimerkkien** mukauttamista, mikä tekee numeeristen muotoilujen sovittamisesta eri paikallisiin tai liiketoimintakäytäntöihin helppoa.

- **Ryhmittelyerotin** käytetään visuaalisesti erottamaan tuhansia (esim. `1,000,000`).
- **Desimaalierotin** osoittaa numeerisen osan (esim. `123.45`).

Tämä on hyödyllistä kansainvälisissä sovelluksissa, joissa eri alueet käyttävät erilaisia merkkejä (esim. `.` vs `,`).

```java
field.setGroupCharacter(".");   // e.g. 1.000.000
field.setDecimalCharacter(","); // e.g. 123,45
```

:::tip Oletuskäyttäytyminen
Oletuksena `MaskedNumberField` soveltaa ryhmittely- ja desimaalierottimia sovelluksen nykyisen paikallisuuden perusteella. Voit koko ajan ylittää ne käyttämällä tarjottuja asettajia.
:::

## Negatiiviset arvot {#negateable}

`MaskedNumberField` tukee vaihtoehtoa hallita, sallitaanko negatiivisten arvojen syöttäminen.

Oletuksena negatiiviset arvot kuten `-123.45` ovat sallittuja. Estääksesi tämän, käytä `setNegateable(false)` rajoittaaksesi syötteitä vain positiivisiin arvoihin.

Tämä on hyödyllistä liiketoimintaskenaarioissa, joissa arvot kuten määrät, yhteensä tai prosentit on aina oltava ei-negatiivisia.

```java
field.setNegateable(false);
```

Kun `negatable` on asetettu `false`, kenttä estää kaikki yritykset syöttää miinusmerkkiä tai muuten syöttää negatiivisia arvoja.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Minimum- ja maksimumarvot {#min-and-max-values}

`MaskedNumberField` tukee numeeristen rajojen asettamista käyttämällä `setMin()` ja `setMax()`. 
Nämä rajoitukset auttavat varmistamaan, että käyttäjän syöte pysyy voimassa olevassa, odotetussa alueessa.

- **Minimiarvo**  
  Käytä `setMin()` määrittääksesi alhaisimman hyväksyttävän arvon:

  ```java
  field.setMin(10.0); // Minimiarvo: 10
  ```

  Jos käyttäjä syöttää arvon, joka on tämän rajan alapuolella, se katsotaan virheelliseksi.

- **Maksimiarvo**  
  Käytä `setMax()` määrittääksesi korkeimman hyväksyttävän arvon:

  ```java
  field.setMax(100.0); // Maksimiarvo: 100
  ```

  Arvot, jotka ovat tämän rajan yläpuolella, merkitään virheellisiksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedNumberField` tukee palautusominaisuutta, joka palauttaa kentän arvon esimäärättyyn tilaan. 
Tämä voi olla hyödyllistä, kun käyttäjät tarvitsevat peruuttaa muutoksia, palauttaa vahingossa tehtyjä muokkauksia tai palata tunnettuihin oletusarvoihin.

Tämän käyttäytymisen mahdollistamiseksi määritä tavoitearvo käyttäen `setRestoreValue()`. 
Tarvittaessa kenttä voidaan palauttaa ohjelmallisesti käyttämällä `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti** käyttäen `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuksena palautusnäppäin, ellei se ole ylitettu)

Palautusarvon on oltava määritelty selkeästi. Jos sitä ei ole määritetty, ominaisuus ei peruuta kenttää.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` laajentaa [`MaskedNumberField`](#basics) lisäämällä spinnereitä, jotka antavat käyttäjille mahdollisuuden lisätä tai vähentää arvoa käyttämällä vaihepainikkeita tai nuolinäppäimiä. 
Tämä on ihanteellinen syötteille, kuten määrille, hinnoittelusäätöille, arviointikontrolleille tai mihin tahansa skenaarioon, jossa käyttäjät tekevät asteittaisia muutoksia.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Avainominaisuudet {#key-features}

- **Askelinjektiot**  
  Käytä `setStep()` määrittääksesi, kuinka paljon arvon pitäisi muuttua jokaisella spinnillä:

  ```java
  spinner.setStep(5.0); // Jokainen spin lisää tai vähentää 5
  ```

- **Interaktiiviset ohjaimet**  
  Käyttäjät voivat napsauttaa spinner-painikkeita tai käyttää näppäimistösisältöä arvon säätämiseen.

- **Kaikki ominaisuudet MaskedNumberFieldistä**  
  Täysin tukee maskeja, muotoilua, ryhmittely- ja desimaalimerkkejä, minimi- ja maksimi-rajoja sekä palautuslogiikkaa.

## Tyylittely {#styling}

<TableBuilder name="MaskedNumberField" />
