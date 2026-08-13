---
title: Avatar
sidebar_position: 7
description: >-
  Represent users with the Avatar component, showing profile images,
  auto-computed initials, custom initials, or icons for identification.
_i18n_hash: a19b6cefc7a422d075f42ddedfcddfce
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

`Avatar`-komponentti tarjoaa visuaalisen esityksen käyttäjästä tai entiteetistä. Se voi näyttää kuvan, automaattisesti lasketut alkukirjaimet, mukautetut alkukirjaimet tai ikonin. Avatareita käytetään yleisesti käyttäjien tunnistamiseen kommenttiosioissa, navigointivalikoissa, chat-sovelluksissa ja kontaktiluetteloissa.

<!-- INTRO_END -->

## Luodaan avatareita {#creating-avatars}

Luodaksesi `Avatar`-objektin, siirrä etiketti, joka toimii saavutettavana nimenä. Komponentti laskee automaattisesti alkukirjaimet ottamalla ensimmäisen kirjaimen jokaisesta sanasta etiketissä.

```java
// Luo avatar, joka näyttää "JD" etiketistä
Avatar avatar = new Avatar("John Doe");
```

Voit myös antaa eksplisiittiset alkukirjaimet, jos haluat enemmän hallintaa siitä, mitä näytetään:

```java
// Luo avatar mukautetuilla alkukirjaimilla
Avatar avatar = new Avatar("John Doe", "J");
```

Alla oleva esimerkki esittelee avatareita tiimipaneelissa. Jokainen `Avatar` näyttää joko profiilikuva tai automaattisesti luodut alkukirjaimet käyttäjän nimen perusteella. Klikkaamalla `Avatar`-objektia avautuu dialogi, jossa on suurennettu näkymä.

<ComponentDemo
path='/webforj/avatar'
files={[
  'src/main/java/com/webforj/samples/views/avatar/AvatarView.java',
  'src/main/frontend/css/avatar/avatar.css',
]}
height='500px'
/>

## Kuvien näyttäminen {#displaying-images}

`Avatar`-komponentti voi näyttää kuvan alkukirjainten sijasta sijoittamalla `Img`-komponentin lapsena. Kun kuva on annettu, se ottaa etusijan alkukirjaimiin nähden.

```java
import com.webforj.component.html.elements.Img;

// Avatar kuvalla
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Kuvan koko
Kuva skaalaa automaattisesti sopimaan avatarin mittoihin nykyisen laajuusasetuksen mukaan.
:::

## Ikonien näyttäminen {#displaying-icons}

Voit näyttää ikonin `Avatar`-komponentissa lisäämällä `Icon`-komponentin lapsena:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar ikonilla
Avatar avatar = new Avatar("Guest User", TablerIcon.create("user"));
```

## Etiketti ja alkukirjaimet {#label-and-initials}

`Avatar`-komponentti käyttää etikettiä saavutettavuuteen ja työkaluvihjeen luomiseen. `setLabel()` ja `setText()` -menetelmät ovat synonyymejä, jotka molemmat asettavat saavutettavan etiketin `Avatar`-objektille.

:::info Automaattisesti lasketut alkukirjaimet
Kun luot `Avatar`-objektin vain etiketillä, alkukirjaimet lasketaan automaattisesti ottamalla ensimmäinen merkki jokaisesta sanasta. Esimerkiksi `Avatar`, jonka etiketti on "John Doe", näyttää automaattisesti "JD" käyttöliittymässä.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Aseta etiketti ja luo automaattisesti työkaluvihje
avatar.setInitials("JS");       // Ylikirjoita automaattisesti lasketut alkukirjaimet
```

:::tip Automaattinen työkaluvihje
Komponentti luo automaattisesti työkaluvihjeen etiketistä, jolloin koko nimen näkeminen on helppoa hiiren päällä. Tämä käyttäytyminen on poistettu käytöstä, kun käytetään oletusetikettiä "Avatar".
:::

## Klikkaustapahtumat {#click-events}

`Avatar`-komponentti toteuttaa `HasElementClickListener`-rajapinnan, mikä mahdollistaa käyttäjän klikkausten käsittelyn. Tämä on hyödyllistä, kun halutaan käynnistää toimintoja, kuten avata käyttäjäprofiili tai näyttää valikko.

```java
avatar.onClick(event -> {
  // Käsittele avatarin klikkaus - esim. avaa käyttäjäprofiili
  System.out.println("Avatar clicked!");
});
```

## Muodot {#shapes}

Avatareita voidaan näyttää ympyröinä tai neliöinä. Oletusmuoto on `CIRCLE`, mikä on standardi käyttäjäavatareille. Käytä `SQUARE`-muotoa entiteeteille, kuten tiimeille, yrityksille tai sovelluksille.

<ComponentDemo
path='/webforj/avatarshapes'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java']}
height='100px'
/>

## Teemat {#themes}

Teemat välittävät merkitystä tai tilaa; voit käyttää niitä osoittamaan saatavuutta, korostamaan tärkeitä käyttäjiä tai vastaamaan sovelluksesi muotoilua.

Seuraavat teemat ovat saatavilla:

- `DEFAULT`: Vakio-ilme
- `GRAY`: Neutraali, hillitty ilme
- `PRIMARY`: Korostaa päätoimintoja tai käyttäjiä
- `SUCCESS`: Ilmaisee positiivista tilaa (esim. online)
- `WARNING`: Ilmaisee varoitusta (esim. poissa)
- `DANGER`: Ilmaisee virhe- tai kiireistä tilaa
- `INFO`: Tarjoaa informaatiota kontekstista

Jokaisella teemalla on myös viivoitettu variantti kevyempää visuaalista käsittelyä varten:

<ComponentDemo
path='/webforj/avatarthemes'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java']}
height='120px'
/>

## Laajuudet {#expanses}

Säädä avatarin kokoa käyttämällä `setExpanse()`-menetelmää. Komponentti tukee yhdeksää koovaihtoehtoa, jotka vaihtelevat `XXXSMALL`-kokoisesta `XXXLARGE`-kokoon.

<ComponentDemo
path='/webforj/avatarexpanses'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java']}
height='100px'
/>


## Tyylittely {#styling}

<TableBuilder name="Avatar" />
