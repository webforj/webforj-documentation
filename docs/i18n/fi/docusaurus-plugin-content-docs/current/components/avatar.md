---
title: Avatar
sidebar_position: 7
_i18n_hash: 7974a5de785a24d8b83507dd8c81d03d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

`Avatar`-komponentti tarjoaa visuaalisen esityksen käyttäjästä tai entiteetistä. Se voi näyttää kuvan, automaattisesti lasketut alut, mukautettuja aluja tai ikonin. Avatarit ovat yleisesti käytössä käyttäjien tunnistamiseen kommenttiosioissa, navigointivalikoissa, chat-sovelluksissa ja kontaktiluetteloissa.

<!-- INTRO_END -->

## Luodaan avatarit {#creating-avatars}

Luodaksesi `Avatar`, anna etiketti, joka toimii saavutettavana nimenä. Komponentti laskee automaattisesti alut ottamalla ensimmäisen kirjaimen jokaisesta sanasta etiketissä.

```java
// Luodaan avatar, joka näyttää "JD" etiketistä
Avatar avatar = new Avatar("John Doe");
```

Voit myös antaa erikseen määritellyt alut, jos haluat enemmän hallintaa näytettävästä sisällöstä:

```java
// Luodaan avatar mukautetuilla alulla
Avatar avatar = new Avatar("John Doe", "J");
```

Alla oleva esimerkki esittelee avatarit tiimin paneeliyhteydessä. Jokainen `Avatar` näyttää joko profiilikuvan tai automaattisesti luodut alut käyttäjän nimen perusteella. Klikkaaminen `Avatar`-komponenttia avaa dialogin suurennetulla näkymällä.

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '500px'
/>

## Kuvien näyttäminen {#displaying-images}

`Avatar`-komponentti voi näyttää kuvan alujen sijasta liittämällä `Img`-komponentin lapsena. Kun kuva on annettu, se saa etusijan aluille.

```java
import com.webforj.component.html.elements.Img;

// Avatar kuvalla
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Kuvan koko
Kuva skaalaa automaattisesti sopimaan avatarin mittoihin nykyisen laajuusasetuksen perusteella.
:::

## Kuvakkeiden näyttäminen {#displaying-icons}

Voit näyttää kuvakkeen `Avatar`-komponentissa lisäämällä `Icon`-komponentin lapsena:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar kuvakkeella
Avatar avatar = new Avatar("Guest User", TablerIcon.create("user"));
```

## Etiketti ja alut {#label-and-initials}

`Avatar`-komponentti käyttää etikettiä saavutettavuuden ja työkaluvinkkien luomiseen. `setLabel()`- ja `setText()`-metodit ovat aliaksia, jotka molemmat asettavat saavutettavan etiketin `Avatar`:lle.

:::info Automaattisesti lasketut alut
Kun luot `Avatar`-komponentin vain etiketillä, alut lasketaan automaattisesti ottamalla jokaisen sanan ensimmäinen merkki. Esimerkiksi `Avatar`, jonka etiketti on "John Doe", näyttää automaattisesti "JD" käyttöliittymässä.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Asettaa etiketin ja automaattisesti luo työkaluvinkin
avatar.setInitials("JS");       // Korvata automaattisesti lasketut alut
```

:::tip Automaattinen työkaluvinkki
Komponentti luo automaattisesti työkaluvinkin etiketistä, mikä helpottaa koko nimen näkemistä hiiren yli. Tämä käyttäytyminen on poissa käytöstä käytettäessä oletusetikettiä `"Avatar"`.
:::

## Klikkaustapahtumat {#click-events}

`Avatar`-komponentti toteuttaa `HasElementClickListener`, jolloin voit reagoida käyttäjän klikkauksiin. Tämä on hyödyllistä toimintojen käynnistämiseen, kuten käyttäjäprofiilin avaamiseen tai valikon näyttämiseen.

```java
avatar.onClick(event -> {
  // Käsittele avatarin klikkaamista - esim. avaa käyttäjäprofiili
  System.out.println("Avatar clicked!");
});
```

## Muodot {#shapes}

Avatarit voidaan näyttää ympyröinä tai neliöinä. Oletusmuoto on `CIRCLE`, mikä on standardi käyttäjäavatareille. Käytä `SQUARE`-muotoa entiteeteille, kuten tiimeille, yrityksille tai sovelluksille.

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## Teemat {#themes}

Teemat välittävät merkitystä tai tilaa; voit käyttää niitä indikoimaan saatavuutta, korostamaan tärkeitä käyttäjiä tai sovittamaan sovelluksesi ulkoasuun.

Seuraavat teemat ovat saatavilla:

- `DEFAULT`: Vakio ulkoasu
- `GRAY`: Neutraali, hillitty ulkoasu
- `PRIMARY`: Korostaa ensisijaisia toimintoja tai käyttäjiä
- `SUCCESS`: Indikoi positiivista tilaa (esim. online)
- `WARNING`: Indikoi varoitusta (esim. poissa)
- `DANGER`: Indikoi virhe- tai kiiretilaa
- `INFO`: Tarjoaa tiedollista kontekstia

Jokaisella teemalla on myös ääriviirteinen variantti kevyempää visuaalista käsittelyä varten:

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## Laajuudet {#expanses}

Säädä avatarin kokoa käyttämällä `setExpanse()`-metodia. Komponentti tukee yhdeksää kokovaihtoehtoa, jotka vaihtelevat `XXXSMALL`-kokoisesta `XXXLARGE`-kokoon.

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>


## Tyylittely {#styling}

<TableBuilder name="Avatar" />
