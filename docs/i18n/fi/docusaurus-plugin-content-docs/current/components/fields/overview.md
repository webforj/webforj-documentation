---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
description: >-
  Share common field properties across webforJ input components, including
  labels, helper text, required state, spellcheck, and prefix or suffix slots.
_i18n_hash: 56462cf4d659ce15f72a624acae60f0f
---
<JavadocLink type="foundation" location="com/webforj/component/field/DwcField"/>

webforJ-kehys tukee yksitoista eri kenttäkomponenttityyppiä, mukaan lukien neljä maskattua kenttää, joilla on erilaisia käyttäytymisiä ja toteutuksia, jotka sopivat erilaisiin syöttötarpeisiin. Vaikka jokaisella näistä komponenteista on eroja toteutuksissaan, tämä artikkeli kuvaa yhteiset ominaisuudet kaikille kenttäluokille.

:::info
Tässä osiossa kuvataan monien kenttäkomponenttien yleisiä ominaisuuksia webforJ:ssä, ja se ei itsessään ole luokka, jota voidaan instansioida ja käyttää.
:::

## Yhteiset kenttäominaisuudet {#shared-field-properties}

### Otsikko {#label}

Kenttäotsikko on kuvaileva teksti tai nimike, joka liittyy kenttään ja jonka voi määrittää konstruktiossa tai käyttämällä `setLabel()`-menetelmää. Otsikot tarjoavat lyhyen selityksen tai vihjeen, joka auttaa käyttäjiä ymmärtämään kyseisen kentän tarkoitusta tai odotettua syötettä. Kenttäotsikot ovat tärkeitä käytettävyyden kannalta ja niillä on keskeinen rooli saavutettavuudessa, sillä ne mahdollistavat ruudunlukijoiden ja apuvälineiden tarjoavan tarkkaa tietoa ja helpottavan näppäimistön navigointia.

### Aputeksti {#helper-text}

Jokainen kenttä voi näyttää aputekstiä syötteen alapuolella käyttämällä `setHelperText()`-menetelmää. Tämä aputeksti tarjoaa lisäkontekstia tai selityksiä käytettävissä olevista syötteistä varmistaen, että käyttäjillä on tarvittavat tiedot harkittujen valintojen tekemiseksi.

### Pakollinen {#required}

Voit kutsua `setRequired(true)`-menetelmää vaatiaksesi, että käyttäjä antaa arvon ennen lomakkeen lähettämistä. Tämä ominaisuus toimii yhdessä kenttäotsikon kanssa, tarjoten visuaalisen indikaation siitä, että kenttä on välttämätön. Tämä visuaalinen vihje auttaa yksilöitä täyttämään lomakkeita tarkasti.

:::info
Kenttäkomponentit sisältävät sisäänrakennettua visuaalista validoimista ilmoittaakseen käyttäjille, kun pakollinen kenttä on tyhjää tai jos käyttäjä on poistanut arvon.
:::

### Oikoluku {#spellcheck}

Käyttämällä `setSpellCheck(true)` voit sallia selaimen tai käyttäjäagentin tarkistaa syötetyn tekstin oikeinkirjoituksen ja tunnistaa mahdolliset virheet.

### Etuliite ja pääte {#prefix-and-suffix}

Slotit tarjoavat joustavia vaihtoehtoja kenttäkomponenttien kyvykkyyden parantamiseksi. Voit liittää kentän sisään kuvakkeita, otsikoita, latauspyörijöitä, tyhjennys/palautusmahdollisuuksia, avatar/profiilikuvia ja muita hyödyllisiä komponentteja, jotta käyttäjille selvennetään aikomuksia. Kentillä on kaksi slotia: `prefix` ja `suffix` -slotit. Käytä `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä eri komponenttien lisäämiseksi näyttövalinnan eteen ja taakse kentässä. Tässä on esimerkki `TextField`-kentän käytöstä:

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## Tyylitys {#styling}

:::info
Koska kaikki kenttäkomponentit on rakennettu yhdestä verkkokomponentista, ne jakavat kaikki seuraavat Shadow Parts ja CSS-ominaisuusasetukset.
:::

<TableBuilder name="Field" />

## Aiheet {#topics}

<DocCardList className="topics-section" />
