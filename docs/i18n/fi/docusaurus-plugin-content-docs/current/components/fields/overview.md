---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 288d408cb058dbaa417fea651698123a
---
<JavadocLink type="foundation" location="com/webforj/component/field/AbstractField"/>

webforJ-kehys tukee seitsemää erilaista kenttäkomponenttia, joilla on erilaiset käyttäytymis- ja toteutusominaisuudet, jotka sopivat erilaisiin syöttötarpeisiin. Vaikka jokaisella näistä komponenteista on eroja toteutuksissaan, tämä artikkeli kuvaa yhteisiä ominaisuuksia kaikille kenttäluokille.

:::info
Tämä osio kuvaa erilaisia kenttäkomponentteja koskevia yhteisiä ominaisuuksia webforJ:ssä, eikä se itsessään ole luokka, jota voidaan instanssia ja käyttää.
:::

## Yhteiset kenttäominaisuudet {#shared-field-properties}

### Otsikko {#label}

Kenttäotsikko on kuvaileva teksti tai nimi, joka liittyy kenttään ja joka voidaan määrittää konstruktorissa tai käyttämällä `setLabel()`-metodia. Otsikot tarjoavat lyhyen selityksen tai kehotuksen auttaakseen käyttäjiä ymmärtämään kyseisen kentän tarkoituksen tai odotetun syötteen. Kenttäotsikoilla on tärkeä merkitys käytettävyyden kannalta ja ne ovat ratkaisevassa roolissa esteettömyydessä, sillä ne mahdollistavat ruudunlukijoiden ja apuvälineiden tarjoavan tarkkaa tietoa ja helpottavan näppäimistön navigointia.

### Aputeksti {#helper-text}

Jokainen kenttä voi näyttää aputekstiä syötteen alla käyttämällä `setHelperText()`-metodia. Tämä aputeksti tarjoaa lisäkontekstia tai selityksiä saatavilla olevista syötteistä, varmistaen että käyttäjillä on tarvittavat tiedot tehdä informoituja valintoja.

### Pakollinen {#required}

Voit kutsua `setRequired(true)`-metodia vaatiaksesi käyttäjiä antamaan arvon ennen lomakkeen lähettämistä. Tämä ominaisuus toimii yhdessä kenttäotsikon kanssa, tarjoten visuaalisen vihjeen siitä, että kenttä on välttämätön. Tämä visuaalinen vihje auttaa henkilöitä täyttämään lomakkeita tarkasti.

:::info
Kenttäkomponenteissa on sisäänrakennettua visuaalista validointia, joka ilmoittaa käyttäjille, kun pakollinen kenttä on tyhjillään tai jos käyttäjä on poistanut arvon.
:::

### Oikoluku {#spellcheck}

Käyttämällä `setSpellCheck(true)`, voit antaa selaimen tai käyttäjäagentin tarkistaa käyttäjän syöttämän tekstin oikeinkirjoituksen ja tunnistaa virheet.

### Etuliite ja jälkiliite {#prefix-and-suffix}

Paikat tarjoavat joustavia vaihtoehtoja kenttäkomponenttien toimintakyvyn parantamiseksi. Voit liittää kenttään kuvakkeita, nimilappuja, lataussymboleita, tyhjennys/nollausmahdollisuuksia, avatar/profiilikuvaleita sekä muita hyödyllisiä komponentteja, jotta käyttäjille voidaan selkeyttää tarkoitetut merkitykset. Kentillä on kaksi paikkaa: `prefix`- ja `suffix`-paikat. Käytä `setPrefixComponent()`- ja `setSuffixComponent()`-metodeja lisätäksesi erilaisia komponentteja ennen ja jälkeen näytettävän vaihtoehdon kentässä. Tässä on esimerkki käytettäessä `TextField`-kenttää:

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## Tyylittely {#styling}

:::info
Koska kaikki kenttäkomponentit on rakennettu yhdestä verkkokomponentista, ne jakavat kaikki seuraavat Shadow Parts ja CSS-ominaisuus arvot.
:::

<TableBuilder name="Field" />

## Aiheet {#topics}

<DocCardList className="topics-section" />
