---
title: Avatar
sidebar_position: 7
sidebar_class_name: new-content
_i18n_hash: 928db2bff36515d2d9a41aeca9a233e0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

`Avatar`-komponentti tarjoaa visuaalisen esityksen käyttäjästä tai entiteetistä. Se voi näyttää kuvan, automaattisesti lasketut alkuperäiset kirjaimet, mukautetut alkuperäiset kirjaimet tai ikonin. Avatarit ovat yleisesti käytössä käyttäjien tunnistamiseen kommenttiosioissa, navigointivalikoissa, keskustelusovelluksissa ja yhteystiedoissa.

<!-- INTRO_END -->

## Luominen avatarit {#creating-avatars}

Jotta voit luoda `Avatar`-komponentin, siirrä etiketti, joka toimii saavutettavana nimenä. Komponentti laskee automaattisesti alkuperäiset kirjaimet ottamalla jokaisen sanan ensimmäisen kirjaimen etiketistä.

```java
// Luo avatarin, joka näyttää "JD" etiketistä
Avatar avatar = new Avatar("John Doe");
```

Voit myös antaa eksplisiittiset alkuperäiset kirjaimet, jos haluat enemmän hallintaa siitä, mitä näytetään:

```java
// Luo avatar mukautetuilla alkuperäisillä kirjaimilla
Avatar avatar = new Avatar("John Doe", "J");
```

Esimerkki alla esittelee avatarit tiimipaneelissa. Jokainen `Avatar` näyttää joko profiilikuvaa tai automaattisesti luotuja alkuperäisiä kirjaimia käyttäjän nimen perusteella. Napsauttamalla `Avatar`-komponenttia avaa dialogin suurennetulla näkymällä.

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '450px'
/>

## Kuvien näyttäminen {#displaying-images}

`Avatar`-komponentti voi näyttää kuvan alkuperäisten kirjainten sijaan sijoittamalla `Img`-komponentin lapsielementtänä. Kun kuva on annettu, se on etusijalla alkuperäisten kirjainten yläpuolella.

```java
import com.webforj.component.html.elements.Img;

// Avatar kuvalla
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Kuvan koko
Kuva skaalaa automaattisesti sopimaan avatarin mittoihin nykyisen laajennusasetuksen perusteella.
:::

## Kuvien näyttäminen {#displaying-icons}

Voit näyttää ikonin `Avatar`-komponentissa lisäämällä `Icon`-komponentin lapsielementtänä:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar ikonilla
Avatar avatar = new Avatar("Guest User", TablerIcon.create("user"));
```

## Etiketti ja alkuperäiset kirjaimet {#label-and-initials}

`Avatar`-komponentti käyttää etikettiä saavutettavuuden ja työkaluvihjeen generointiin. `setLabel()` ja `setText()` -menetelmät ovat synonyymejä, jotka molemmat asettavat saavutettavan etiketin `Avatar`-komponentille.

:::info Automaattisesti lasketut alkuperäiset kirjaimet
Kun luot `Avatar`-komponentin vain etiketillä, alkuperäiset kirjaimet lasketaan automaattisesti ottamalla jokaisen sanan ensimmäinen merkki. Esimerkiksi `Avatar`, jonka etiketti on "John Doe", näyttää automaattisesti "JD" käyttöliittymässä.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Asettaa etiketin ja automaattisesti generoi työkaluvihjeen
avatar.setInitials("JS");       // Ohittaa automaattisesti lasketut alkuperäiset kirjaimet
```

:::tip Automaattinen työkaluvihje
Komponentti generoi automaattisesti työkaluvihjeen etiketistä, mikä helpottaa täydellisen nimen näkemistä hiiren kanssa. Tämä käyttäytyminen on pois päältä käytettäessä oletusetikettiä "Avatar".
:::

## Klikkaustapahtumat {#click-events}

`Avatar`-komponentti toteuttaa `HasElementClickListener`, jolloin voit reagoida käyttäjän klikkauksiin. Tämä on hyödyllistä esimerkiksi käyttäjäprofiilin avaamiseksi tai valikon näyttämiseksi.

```java
avatar.onClick(event -> {
  // Käsittele avatarin klikkaus - esim. avaa käyttäjäprofiili
  System.out.println("Avatar clicked!");
});
```

## Muodot {#shapes}

Avatarit voidaan esittää ympyröinä tai neliöinä. Oletusmuoto on `CIRCLE`, joka on tavallinen käyttäjäavatarille. Käytä `SQUARE`-muotoa entiteeteille, kuten tiimeille, yrityksille tai sovelluksille.

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## Teemat {#themes}

Teemat viestivät merkitystä tai tilaa; voit käyttää niitä ilmaisemaan saatavuutta, korostamaan tärkeitä käyttäjiä tai vastaamaan sovelluksesi muotoilua.

Seuraavat teemat ovat saatavilla:

- `DEFAULT`: Vakio ulkoasu
- `GRAY`: Neutraali, vaimea ulkoasu
- `PRIMARY`: Korostaa ensisijaisia toimintoja tai käyttäjiä
- `SUCCESS`: Ilmaisee positiivista tilaa (esim. verkossa)
- `WARNING`: Ilmaisee varovaisuutta (esim. poissa)
- `DANGER`: Ilmaisee virhe- tai kiireistä tilaa
- `INFO`: Antaa tietokontekstia

Jokaisella teemalla on myös ääriviivamuunnos kevyemmälle visuaaliselle käsittelylle:

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## Laajennukset {#expanses}

Ohjaa avatarin kokoa käyttämällä `setExpanse()`-menetelmää. Komponentti tukee yhdeksää koon vaihtoehtoa, jotka vaihtelevat `XXXSMALL`-kokeesta `XXXLARGE`-koen.

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>

## Tyylit {#styling}

<TableBuilder name="Avatar" />
