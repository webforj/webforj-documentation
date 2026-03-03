---
title: Avatar
sidebar_position: 7
sidebar_class_name: new-content
_i18n_hash: 88c1ddd6113a8022a977f27413e2eacf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

`Avatar`-komponentti tarjoaa visuaalisen esityksen käyttäjästä tai entiteetistä. Se voi näyttää kuvan, automaattisesti lasketut alkukirjaimet, mukautetut alkukirjaimet tai ikonin. Avatarit ovat yleisesti käytössä käyttäjien tunnistamiseen kommenttiosioissa, navigointivalikoissa, chat-sovelluksissa ja kontaktimalleissa.

<!-- INTRO_END -->

## Luodaan avatareita {#creating-avatars}

Luodaksesi `Avatar`, siirrä etiketti, joka toimii saavutettavana nimenä. Komponentti laskee automaattisesti alkukirjaimet ottamalla ensimmäisen kirjaimen jokaisesta sanasta etiketissä.

```java
// Luo avatar, joka näyttää "JD" etiketistä
Avatar avatar = new Avatar("John Doe");
```

Voit myös antaa eksplisiittiset alkukirjaimet, jos haluat enemmän hallintaa siitä, mitä näytetään:

```java
// Luo avatar mukautetuilla alkukirjaimilla
Avatar avatar = new Avatar("John Doe", "J");
```

Esimerkki alla esittelee avatareita tiimipaneelitilanteessa. Jokainen `Avatar` näyttää joko profiilikuvan tai automaattisesti生成idyt alkukirjaimet käyttäjän nimen perusteella. Klikkaamalla `Avatar`-kuvaketta avautuu ikkuna suurennetulla näkymällä.

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '450px'
/>

## Kuvien näyttäminen {#displaying-images}

`Avatar`-komponentti voi näyttää kuvan sijaan alkukirjaimia lisäämällä `Img`-komponentin lapsena. Kun kuva on annettu, se on ensisijainen alkukirjainten sijaan.

```java
import com.webforj.component.html.elements.Img;

// Avatar kuvan kanssa
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Kuvan koon säätö
Kuva skaalaa automaattisesti sopimaan avatarin mittoihin nykyisen avaruusasetuksen perusteella.
:::

## Ikonien näyttäminen {#displaying-icons}

Voit näyttää ikonin `Avatar`-komponentissa lisäämällä `Icon`-komponentin lapsena:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar ikonilla
Avatar avatar = new Avatar("Guest User", TablerIcon.create("user"));
```

## Etiketti ja alkukirjaimet {#label-and-initials}

`Avatar`-komponentti käyttää etikettiä saavutettavuuden ja työkaluvihjeen luomiseen. `setLabel()` ja `setText()` -metodit ovat alias, jotka molemmat asettavat saavutettavan etiketin `Avatar`-komponentille.

:::info Automatisoidut alkukirjaimet
Kun luot `Avatar`-komponentin vain etiketillä, alkukirjaimet lasketaan automaattisesti ottamalla jokaisen sanan ensimmäinen kirjain. Esimerkiksi "John Doe" muuttuu "JD":ksi.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Asettaa etiketin ja luo automaattisesti työkaluvihjeen
avatar.setInitials("JS");       // Ohittaa automaattisesti lasketut alkukirjaimet
```

:::tip Automatisoitu työkaluvihje
Komponentti luo automaattisesti työkaluvihjeen etiketistä, joten täydellisen nimen näkeminen hiiren yli on helppoa. Tämä toiminto on pois käytöstä käytettäessä oletusetikettiä "Avatar".
:::

## Klikkaustapahtumat {#click-events}

`Avatar`-komponentti toteuttaa `HasElementClickListener`, mikä mahdollistaa käyttäjäklikkauksiin vastaamisen. Tämä on hyödyllistä, kun haluat laukaista toimintoja, kuten käyttäjäprofiilin avaamista tai valikon näyttämistä.

```java
avatar.onClick(event -> {
  // Käsittele avatarin klikkeri - esim. avaa käyttäjäprofiili
  System.out.println("Avatar clicked!");
});
```

## Muodot {#shapes}

Avatarit voidaan näyttää ympyröinä tai neliöinä. Oletusmuoto on `CIRCLE`, joka on standardi käyttäjäavatareille. Käytä `SQUARE`-muotoa entiteeteille, kuten tiimeille, yrityksille tai sovelluksille.

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## Teemat {#themes}

Teemat välittävät merkityksiä tai statuksia; voit käyttää niitä osoittamaan saatavuutta, korostamaan tärkeitä käyttäjiä tai sopeuttamaan sovelluksesi suunnittelua.

Seuraavat teemat ovat käytettävissä:

- `DEFAULT`: Vakioilme
- `GRAY`: Neutraali, hillitty ilme
- `PRIMARY`: Korostaa ensisijaisia toimintoja tai käyttäjiä
- `SUCCESS`: Ilmaisee positiivista statusta (esim. verkossa)
- `WARNING`: Ilmaisee varovaisuutta (esim. poissa)
- `DANGER`: Ilmaisee virhettä tai kiireistä statusta
- `INFO`: Tarjoaa informatiivista kontekstia

Jokaisella teemalla on myös ääriviivaversio kevyempää visuaalista käsittelyä varten:

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## Asetukset {#expanses}

Hallitse avatarin kokoa käyttämällä `setExpanse()`-metodia. Komponentti tukee yhdeksää koko vaihtoehtoa, jotka vaihtelevat `XXXSMALL`-kokoisesta `XXXLARGE`-kokoon.

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>


## Tyylittely {#styling}

<TableBuilder name="Avatar" />
