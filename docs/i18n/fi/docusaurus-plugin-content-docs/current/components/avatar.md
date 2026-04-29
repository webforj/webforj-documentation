---
title: Avatar
sidebar_position: 7
_i18n_hash: a09e8f3e668c6818045ca93f0747f100
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

`Avatar`-komponentti tarjoaa visuaalisen esityksen käyttäjästä tai entiteetistä. Se voi näyttää kuvan, automaattisesti lasketut alkuperäiset kirjaimet, räätälöityjä alkuperäisiä kirjaimia tai kuvakkeen. Avatarit ovat yleisesti käytössä käyttäjien tunnistamiseen kommenttiosioissa, navigointivalikoissa, chat-sovelluksissa ja yhteystiedoissa.

<!-- INTRO_END -->

## Luodaan avatarit {#creating-avatars}

Luodaksesi `Avatar`, siirrä etiketti, joka toimii saavutettavana nimenä. Komponentti laskee automaattisesti alkuperäiset kirjaimet ottamalla ensimmäisen kirjaimen jokaisesta sanasta etiketissä.

```java
// Luo avatar, joka näyttää "JD" etiketistä
Avatar avatar = new Avatar("John Doe");
```

Voit myös antaa erikseen määritellyt alkuperäiset kirjaimet, jos haluat enemmän hallintaa näytettävästä:

```java
// Luo avatar räätälöidyillä alkuperäisillä kirjaimilla
Avatar avatar = new Avatar("John Doe", "J");
```

Alla oleva esimerkki esittelee avatarit tiimipaneelin yhteydessä. Jokainen `Avatar` näyttää joko profiilikuvaa tai automaattisesti generoituja alkuperäisiä kirjaimia käyttäjän nimen perusteella. Klikkaamalla `Avatar`-kuvaketta avautuu dialogi suurennettuna näkymänä.

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '500px'
/>

## Kuvien näyttäminen {#displaying-images}

`Avatar`-komponentti voi näyttää kuvan alkuperäisten kirjainten sijaan laittamalla `Img`-komponentin lapsena. Kun kuva on annettu, se vie etusijan alkuperäisiltä kirjaimilta.

```java
import com.webforj.component.html.elements.Img;

// Avatar, jossa on kuva
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Kuvan koko
Kuva skaalaa automaattisesti sopimaan avatarin mittoihin nykyisen laajennusasetuksen mukaan.
:::

## Kuvakkeiden näyttäminen {#displaying-icons}

Voit näyttää kuvakkeen `Avatar`-komponentissa lisäämällä `Icon`-komponentin lapsena:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar, jossa on kuvake
Avatar avatar = new Avatar("Guest User", TablerIcon.create("user"));
```

## Etiketit ja alkuperäiset kirjaimet {#label-and-initials}

`Avatar`-komponentti käyttää etikettiä saavutettavuuden ja työpöytänäkymäsi luomiseen. `setLabel()` ja `setText()` -menetelmät ovat synonyymejä, jotka molemmat asettavat saavutettavan etiketti `Avatar`-komponentille.

:::info Automaattisesti lasketut alkuperäiset kirjaimet
Kun luot `Avatar`-komponentin vain etiketillä, alkuperäiset kirjaimet lasketaan automaattisesti ottamalla ensimmäinen merkki jokaisesta sanasta. Esimerkiksi `Avatar`, jossa on etiketti "John Doe", näyttää automaattisesti "JD" käyttöliittymässä.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Asettaa etiketin ja automaattisesti generoi työpöytänäkymän
avatar.setInitials("JS");       // Ylikirjoita automaattisesti lasketut alkuperäiset kirjaimet
```

:::tip Automaattinen työpöytänäkymä
Komponentti generoi automaattisesti työpöytänäkymän etiketin perusteella, joten on helppo nähdä koko nimi hiiren ollessa sen päällä. Tämä käyttäytyminen on poistettu käytöstä käytettäessä oletusetikettiä `"Avatar"`.
:::

## Klikkaustapahtumat {#click-events}

`Avatar`-komponentti toteuttaa `HasElementClickListener`, joka mahdollistaa käyttäjäklikkauksiin reagoimisen. Tämä on hyödyllistä, kun haluat laukaista toimenpiteitä, kuten avata käyttäjäprofiilin tai näyttää valikon.

```java
avatar.onClick(event -> {
  // Käsittele avatar-näppäilyä - esim. avaa käyttäjäprofiili
  System.out.println("Avatar clicked!");
});
```

## Muodot {#shapes}

Avatarit voidaan näyttää ympyröinä tai neliöinä. Oletusmuoto on `CIRCLE`, joka on standardi käyttäjäavaimmille. Käytä `SQUARE`-muotoa entiteeteille, kuten tiimeille, yrityksille tai sovelluksille.

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## Teemat {#themes}

Teemat välittävät merkityksen tai tilan; voit käyttää niitä osoittamaan saatavuutta, korostamaan tärkeitä käyttäjiä tai vastaamaan sovelluksesi muotoiluun.

Seuraavat teemat ovat saatavilla:

- `DEFAULT`: Vakio ulkoasu
- `GRAY`: Neutraali, hillitty ulkoasu
- `PRIMARY`: Korostaa ensisijaisia toimintoja tai käyttäjiä
- `SUCCESS`: Ilmaisee positiivista tilaa (esim. verkossa)
- `WARNING`: Ilmaisee varovaisuutta (esim. poissa)
- `DANGER`: Ilmaisee virhetilan tai kiireisen tilan
- `INFO`: Tarjoaa tiedollista kontekstia

Jokaisella teemalla on myös ääriviivamuunnos kevyemmälle visuaaliselle käsittelylle:

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## Laajennukset {#expanses}

Hallitse avatarin kokoa käyttämällä `setExpanse()`-menetelmää. Komponentti tukee yhdeksää koon vaihtoehtoa, jotka vaihtelevat `XXXSMALL`-kokoisesta `XXXLARGE`-kokoiseen.

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>


## Tyylit {#styling}

<TableBuilder name="Avatar" />
