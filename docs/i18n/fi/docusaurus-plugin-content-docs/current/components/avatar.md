---
title: Avatar
sidebar_position: 7
_i18n_hash: 77ac4a1373803d1d68a45968175050e0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

`Avatar`-komponentti tarjoaa visuaalisen esityksen käyttäjästä tai entiteetistä. Se voi näyttää kuvan, automaattisesti lasketut alut, mukautetut alut tai kuvakkeen. Avatarit ovat yleisesti käytössä käyttäjien tunnistamiseen kommenttiosioissa, navigointivalikoissa, chat-sovelluksissa ja kontaktitasoilla.

<!-- INTRO_END -->

## Luodaan avatarit {#creating-avatars}

Luodaksesi `Avatar`, välitä etiketti, joka toimii saavutettavana nimenä. Komponentti laskee automaattisesti alut ottamalla ensimmäisen kirjaimen kustakin sanasta etikettiä.

```java
// Luo avatarin, joka näyttää "JD" etiketistä
Avatar avatar = new Avatar("John Doe");
```

Voit myös antaa eksplisiittiset alut, jos haluat enemmän hallintaa siitä, mitä näytetään:

```java
// Luo avatarin mukautetuilla alulla
Avatar avatar = new Avatar("John Doe", "J");
```

Alla oleva esimerkki esittelee avatarit tiimipaneelin kontekstissa. Jokainen `Avatar` näyttää joko profiilikuvan tai automaattisesti luodut alut käyttäjän nimen perusteella. Klikkaamalla `Avatar`-kuvaketta avautuu dialogi suurennetulla näkymällä.

<ComponentDemo
path='/webforj/avatar'
files={[
  'src/main/java/com/webforj/samples/views/avatar/AvatarView.java',
  'src/main/resources/static/css/avatar/avatar.css',
]}
height='500px'
/>

## Kuvien näyttäminen {#displaying-images}

`Avatar`-komponentti voi näyttää kuvan sen sijaan, että näyttäisi alut liittämällä `Img`-komponentin lapsena. Kun kuva annetaan, se saa etuoikeuden aluille.

```java
import com.webforj.component.html.elements.Img;

// Avatar kuvalla
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Kuvan kokoaminen
Kuva skaalaa automaattisesti sopimaan avatarin mitoihin nykyisen laajuusasetuksen mukaan.
:::

## Kuvakkeiden näyttäminen {#displaying-icons}

Voit näyttää kuvakkeen `Avatar`-komponentissa lisäämällä `Icon`-komponentin lapsena:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar kuvakkeen kanssa
Avatar avatar = new Avatar("Guest User", TablerIcon.create("user"));
```

## Etiketti ja alut {#label-and-initials}

`Avatar`-komponentti käyttää etikettiä saavutettavuuden ja työkaluvihjeiden luomiseen. `setLabel()` ja `setText()` -menetelmät ovat viittauksia, jotka molemmat asettavat saavutettavan etiketin `Avatar`-komponentille.

:::info Automaattisesti lasketut alut
Kun luot `Avatar`-komponentin vain etiketillä, alut lasketaan automaattisesti ottamalla ensimmäinen kirjain kustakin sanasta. Esimerkiksi `Avatar`, jonka etiketti on "John Doe", näyttää automaattisesti "JD" käyttöliittymässä.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Asettaa etiketin ja generoi automaattisesti työkaluvihjeen
avatar.setInitials("JS");       // Ohittaa automaattisesti lasketut alut
```

:::tip Automaattinen työkaluvihje
Komponentti luo automaattisesti työkaluvihjeen etiketistä, mikä helpottaa täydellisen nimen näkemistä hiiren kanssa. Tämä käyttäytyminen on pois käytöstä käytettäessä oletusetikettiä "Avatar".
:::

## Napsautustapahtumat {#click-events}

`Avatar`-komponentti toteuttaa `HasElementClickListener`-rajapinnan, jolloin voit reagoida käyttäjän napsautuksiin. Tämä on hyödyllistä toimintojen laukaisevana, kuten käyttäjäprofiilin avaamisena tai valikon esittämisenä.

```java
avatar.onClick(event -> {
  // Käsittele avatarin napsautus - esim. avaa käyttäjäprofiili
  System.out.println("Avatar clicked!");
});
```

## Muodot {#shapes}

Avatarit voidaan näyttää ympyröinä tai neliöinä. Oletusmuoto on `CIRCLE`, mikä on standardi käyttäjäavatarille. Käytä `SQUARE`-muotoa entiteeteille kuten tiimit, yritykset tai sovellukset.

<ComponentDemo
path='/webforj/avatarshapes'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java']}
height='100px'
/>

## Teemat {#themes}

Teemat viestivät merkitystä tai tilaa; voit käyttää niitä osoittamaan saatavuutta, korostamaan tärkeitä käyttäjiä tai vastaamaan sovelluksesi ulkoasua.

Seuraavat teemat ovat saatavilla:

- `DEFAULT`: Standardinäkymä
- `GRAY`: Neutraali, hillitty ulkoasu
- `PRIMARY`: Korostaa ensisijaisia toimintoja tai käyttäjiä
- `SUCCESS`: Ilmaisee positiivista tilaa (esim. verkossa)
- `WARNING`: Ilmaisee varoitusta (esim. poissa)
- `DANGER`: Ilmaisee virhe- tai kiiretilaa
- `INFO`: Antaa informatiivista kontekstia

Jokaisella teemalla on myös ääriviivavariantti kevyempää visuaalista käsittelyä varten:

<ComponentDemo
path='/webforj/avatarthemes'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java']}
height='120px'
/>

## Laajuudet {#expanses}

Hallinnoi avatarin kokoa käyttämällä `setExpanse()`-menetelmää. Komponentti tukee yhdeksää koko vaihtoehtoa, jotka vaihtelevat `XXXSMALL`-kosta `XXXLARGE`-kkoon.

<ComponentDemo
path='/webforj/avatarexpanses'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java']}
height='100px'
/>

## Tyylit {#styling}

<TableBuilder name="Avatar" />
