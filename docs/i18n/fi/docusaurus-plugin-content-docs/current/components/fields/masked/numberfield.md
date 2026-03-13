---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: 6eae8d772ec386aff55df31b674a1e84
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` on tekstikenttä, joka on suunniteltu jäsennellylle numeeriselle syötteelle. Se varmistaa, että numerot on muotoiltu johdonmukaisesti määritellyn maskin mukaan, mikä tekee siitä erityisen hyödyllisen talouslomakkeissa, hinnoittelukentissä tai missä tahansa syötteessä, jossa tarkkuus ja luettavuus ovat tärkeitä.

Tämä komponentti tukee numeromuotoilua, desimaalien/ryhmittelymerkkien lokalisointia sekä valinnaisia arvorajoituksia, kuten vähimmäisiä tai enimmäisiä.

<!-- INTRO_END -->

## Perusteet {#basics}

`MaskedNumberField` voidaan instansioida parametreilla tai ilman. Se tukee alkuperäisen arvon, etiketin, paikan pidikkeen ja tapahtumakuuntelijan asettamista, joka reagoi arvojen muutoksiin.

Tämä demo esittelee **Vinkkilaskuria**, joka käyttää `MaskedNumberField` intuitiiviseen numeeriseen syöttöön. Yksi kenttä on määritetty hyväksymään muotoiltu laskun määrä, kun taas toinen tallentaa kokonaissumman tipin prosenttimäärän. Molemmat kentät käyttävät numeerisia maskeja varmistaakseen johdonmukaisen ja ennustettavan muotoilun.

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## Maskisäännöt {#mask-rules}

`MaskedNumberField` käyttää maskimerkkijonoa ohjaamaan, kuinka numeerinen syöte muotoillaan ja näytetään. 
Jokainen merkki maskissa määrittää erityisen muotoilukäyttäytymisen, mikä mahdollistaa tarkan hallinnan siitä, kuinka numerot näkyvät.

### Maskimerkit {#mask-characters}

| Merkki | Kuvaus |
|--------|--------|
| `0`    | Korvataan aina digillä (0–9). |
| `#`    | Estää alkavat nollat. Korvataan täyttömerkillä desimaalipisteen vasemmalla puolella. Jälkimmäiset merkit korvataan tyhjällä tai nollalla. Muuten korvataan digillä. |
| `,`    | Käytetään ryhmittelyerottimena (esim. tuhannet). Korvataan täyttömerkillä, jos mitään numeroita ei ole ennen sitä. Muuten näkyy pilkku. |
| `-`    | Näyttää miinusmerkin (`-`), jos luku on negatiivinen. Korvataan täyttömerkillä, jos positiivinen. |
| `+`    | Näyttää `+` positiivisille tai `-` negatiivisille lukuille. |
| `$`    | Aina tuloksena dollari-merkki. |
| `(`    | Lisää vasemman sulku `( ` negatiivisille arvoille. Korvataan täyttömerkillä, jos positiivinen. |
| `)`    | Lisää oikean sulku `)` negatiivisille arvoille. Korvataan täyttömerkillä, jos positiivinen. |
| `CR`   | Näyttää `CR` negatiivisille luvuilla. Näyttää kaksi välilyöntiä, jos luku on positiivinen. |
| `DR`   | Näyttää `CR` negatiivisille luvuilla. Näyttää `DR` positiivisille luvuilla. |
| `*`    | Lisää tähden `*`. |
| `.`    | Merkitsee desimaalipistettä. Jos tuloksessa ei näy numeroita, korvataan täyttömerkillä. Desimaalin jälkeen täyttömerkkejä käsitellään väliin. |
| `B`    | Aina muuttuu väliksi. Mikä tahansa muu literäärinen merkki näytetään sellaisenaan. |

Joidenkin yllä olevien merkkien voi esiintyä useammin kuin kerran maskissa muotoilua varten. Näitä ovat `-`, `+`, `$` ja `(`. Jos jokin näistä merkeistä on läsnä maskissa, ensimmäinen, joka tulee vastaan siirretään viimeiseen kohtaan, jossa `#` tai `,` korvattiin täyttömerkillä. Jos sellaista sijaintia ei ole, kaksoismerkki jää paikalleen.

:::info Ei automaattista pyöristystä
Maski kentässä ei **pyöristä**. Esimerkiksi, kun syötät arvon kuten `12.34567` kenttään, joka on maskattu `###0.00`, saat `12.34`.
:::

## Ryhmittely- ja desimaalierottimet {#group-and-decimal-separators}

`MaskedNumberField` tukee ryhmittely- ja desimaalimerkkien muokkaamista, mikä helpottaa numeromuotoilun sovittamista eri paikallisiin tai liiketoimintaan liittyviin käytäntöihin.

- **Ryhmittelyerotin** käytetään visuaalisesti erottamaan tuhannet (esim. `1,000,000`).
- **Desimaalierotin** merkitsee osaa luvusta (esim. `123.45`).

Tämä on hyödyllistä kansainvälisissä sovelluksissa, joissa eri alueilla käytetään eri merkkejä (esim. `.` vs `,`).

```java
field.setGroupCharacter(".");   // esim. 1.000.000
field.setDecimalCharacter(","); // esim. 123,45
```

:::tip Oletuskäyttäytyminen
Oletuksena `MaskedNumberField` käyttää ryhmittely- ja desimaalierottimia sovelluksen nykyisen paikallisen asetuksen perusteella. Voit ylittää ne milloin tahansa käyttämällä annettuja asettimia.
:::

## Negoitava {#negateable}

`MaskedNumberField` tukee vaihtoehtoa, joka ohjaa, sallitaanko negatiiviset numerot.

Oletuksena negatiiviset arvot kuten `-123.45` ovat sallittuja. Estääksesi tämän, käytä `setNegateable(false)`, jotta syöte rajoittuu vain positiivisiin arvoihin.

Tämä on hyödyllistä liiketoimintaskenaarioissa, joissa arvojen, kuten määrien, kokonaismäärien tai prosenttien, on aina oltava ei-negatiivisia.

```java
field.setNegateable(false);
```

Kun `negatable` on asetettu arvoon `false`, kenttä estää kaikki yritykset syöttää miinusmerkkiä tai muuten syöttää negatiivisia arvoja.

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## Minimi- ja maksimivarot {#min-and-max-values}

`MaskedNumberField` tukee numeeristen rajojen asettamista käyttämällä `setMin()` ja `setMax()`. 
Nämä rajoitukset auttavat varmistamaan, että käyttäjän syöte pysyy voimassa olevassa, odotetussa alueessa.

- **Vähimmäisarvo**  
  Käytä `setMin()` määrittääksesi alhaisimman hyväksyttävän numeron:

  ```java
  field.setMin(10.0); // Vähimmäisarvo: 10
  ```

  Jos käyttäjä syöttää numeron tämän rajan alapuolella, sitä pidetään virheellisenä.

- **Maksimimäärä**  
  Käytä `setMax()` määrittääksesi korkeimman hyväksyttävän numeron:

  ```java
  field.setMax(100.0); // Maksimimäärä: 100
  ```

  Arvot tämän rajan yläpuolella merkitään virheellisiksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedNumberField` tukee palautusominaisuutta, joka palauttaa kentän arvon määriteltyyn tilaan. 
Tämä voi olla hyödyllistä, kun käyttäjien on peruttava muutoksia, palautettava vahingossa tehtyjä muokkauksia tai palattava tunnettuun oletusarvoon.

Ottaaksesi tämän toiminnon käyttöön, määritä kohdearvo käyttämällä `setRestoreValue()`. 
Kun tarpeen, kenttä voidaan nollata ohjelmallisesti käyttämällä `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti** käyttämällä `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuspalautusavain, ellei sitä olla ohitettu)

Palautusarvo on määritettävä erikseen. Jos sitä ei ole määritelty, ominaisuus ei nollaa kenttää.

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` laajentaa [`MaskedNumberField`](#basics) lisäämällä spinnereitä, jotka antavat käyttäjien lisätä tai vähentää arvoa askelpainikkeiden tai nuolinäppäinten avulla. 
Tämä on ihanteellinen syötteille, kuten määrille, hintamuutoksille, arviointikontrolleille tai missä tahansa tilanteessa, jossa käyttäjät tekevät vähittäismuutoksia.

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### Keskeiset ominaisuudet {#key-features}

- **Askelmuutokset**  
  Käytä `setStep()` määrittääksesi, kuinka paljon arvon tulisi muuttua jokaisella pyörityksellä:

  ```java
  spinner.setStep(5.0); // Jokainen pyöritys lisää tai vähentää 5
  ```

- **Interaktiiviset ohjaimet**  
  Käyttäjät voivat napsauttaa spin-painikkeita tai käyttää näppäimistösyötettä arvon säätämiseen.

- **Kaikki ominaisuudet MaskedNumberFieldista**  
  Täysin tukee maskeja, muotoilua, ryhmittely-/desimaalimerkkejä, minimi/maximin rajoja ja palautuslogiikkaa.

## Tyylittely {#styling}

<TableBuilder name="MaskedNumberField" />
