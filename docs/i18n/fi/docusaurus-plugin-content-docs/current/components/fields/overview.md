---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: b04acdedbd800790417edfe940160bf2
---
<JavadocLink type="foundation" location="com/webforj/component/field/AbstractField"/>

webforJ-kehys tukee seitsemää eri tyyppistä kenttäkomponenttia, joilla on erilaisia käyttäytymisiä ja toteutuksia, jotka sopivat eri tarpeisiin syötteissä. Vaikka jokaisella näistä komponenteista on vaihteluja toteutuksissaan, tämä artikkeli kuvaa yhteisiä ominaisuuksia kaikille kenttäluokille.

:::info
Tässä osiossa kuvataan erilaisten kenttäkomponenttien yhteisiä ominaisuuksia webforJ:ssä, eikä se itsessään ole luokka, jota voitaisi luoda ja käyttää.
:::

## Yhteiset kenttäominaisuudet {#shared-field-properties}

### Otsikko {#label}

Kentän otsikko on kuvaava teksti tai nimi, joka liittyy kenttään ja jonka voi määrittää konstruktorissa tai käyttämällä `setLabel()`-metodia. Otsikot tarjoavat lyhyen selityksen tai kehotuksen auttaakseen käyttäjiä ymmärtämään kyseisen kentän tarkoituksen tai odotetun syötteen. Kentän otsikot ovat tärkeitä käytettävyyden kannalta ja ne näyttelevät keskeistä roolia saavutettavuudessa, sillä ne antavat ruudunlukijoille ja apuvälineille mahdollisuuden tarjota tarkkaa tietoa ja helpottavat näppäimistön navigointia.

### Avustava teksti {#helper-text}

Jokainen kenttä voi näyttää avustavaa tekstiä syötteen alapuolella käyttämällä `setHelperText()`-metodia. Tämä avustava teksti tarjoaa lisäkontekstia tai selityksiä tarjolla olevista syötteistä, varmistaen, että käyttäjillä on tarvittavat tiedot tehdä informoituja valintoja.

### Vaatimus {#required}

Voit kutsua `setRequired(true)`-metodia vaatiaksesi käyttäjiä antamaan arvon ennen lomakkeen lähettämistä. Tämä ominaisuus toimii yhdessä kenttäotsikon kanssa, antaen visuaalisen merkinnän siitä, että kenttä on pakollinen. Tämä visuaalinen vihje auttaa henkilöitä täyttämään lomakkeet oikein.

:::info
Kenttäkomponenteissa on sisäänrakennettu visuaalinen validointi, joka ilmoittaa käyttäjille, kun pakollinen kenttä on tyhjö tai jos käyttäjä on poistanut arvon.
:::

### Oikoluku {#spellcheck}

Käyttämällä `setSpellCheck(true)`-metodia voit sallia selaimen tai käyttäjäagentin tarkistaa käyttäjän syöttämän tekstin oikeinkirjoituksen ja tunnistaa mahdolliset virheet.

### Etuliite ja jälkiliite {#prefix-and-suffix}

Slots tarjoavat joustavia vaihtoehtoja kenttäkomponenttien toiminnallisuuden parantamiseksi. Voit liittää kenttään ikkunoita, etikettejä, latauspyöriä, tyhjennys/ajastinmahdollisuuksia, avatar/kuva-profiileja ja muita hyödyllisiä komponentteja selvittääksesi käyttäjien tarkoitettua merkitystä. Kentillä on kaksi slotia: `prefix` ja `suffix`. Käytä `setPrefixComponent()` ja `setSuffixComponent()` -metodeja erilaisien komponenttien lisäämiseksi ennen ja jälkeen näytettävän vaihtoehdon kentässä. Tässä on esimerkki, joka käyttää `TextField`-kenttää:

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## Tyylit {#styling}

:::info
Koska kaikki kenttäkomponentit on rakennettu yhdestä web-komponentista, ne jakavat kaikki seuraavat Shadow Parts ja CSS Property -arvot
:::

<TableBuilder name="Field" />

## Aiheet {#topics}

<DocCardList className="topics-section" />
