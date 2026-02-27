---
title: Avatar
sidebar_position: 7
sidebar_class_name: new-content
_i18n_hash: 3a915fc4eb3ca5d51dc1909a34eb5bd1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

`Avatar`-komponentti tarjoaa visuaalisen esityksen käyttäjästä tai entiteetistä. Se voi näyttää kuvan, automaattisesti lasketut alkuperäiset kirjaimet, räätälöidyt alkuperäiset kirjaimet tai ikonit. Avatarit ovat yleisesti käytössä käyttäjien tunnistamiseen kommenttiosioissa, navigointivalikoissa, chat-sovelluksissa ja yhteystiedoissa.

## Luodaan avatarit {#creating-avatars}

Luodaksesi `Avatar`, siirrä etiketti, joka toimii saavutettavana nimikkeenä. Komponentti laskee automaattisesti alkuperäiset kirjaimet ottamalla ensimmäisen kirjaimen jokaisesta sanasta etiketissä.

```java
// Luo avatarin, joka näyttää "JD" etiketistä
Avatar avatar = new Avatar("John Doe");
```

Voit myös antaa selkeitä alkuperäisiä kirjaimia, jos haluat enemmän hallintaa näytettävään:

```java
// Luo avatarin räätälöityillä alkuperäisillä kirjaimilla
Avatar avatar = new Avatar("John Doe", "J");
```

Alla oleva esimerkki esittelee avatarit tiimipaneelin yhteydessä. Jokainen `Avatar` näyttää joko profiilikuvaan tai automaattisesti luotujen alkuperäisten kirjainten käyttäjän nimen mukaan. Klikkaamalla `Avatar`-kuvaketta avautuu dialogi suurennetulla näkymällä.

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '450px'
/>

## Kuvien näyttäminen {#displaying-images}

`Avatar`-komponentti voi näyttää kuvan alkuperäisten kirjainten sijasta liittämällä `Img`-komponentin lapsena. Kun kuva on tarjottu, se saa etusijan alkuperäisten kirjainten yli.

```java
import com.webforj.component.html.elements.Img;

// Avatar, jossa on kuva
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Kuvan koko
Kuva skaalaa automaattisesti sopimaan avatarin mittoihin nykyisen laajuuden asetuksen perusteella.
:::

## Kuvakkeiden näyttäminen {#displaying-icons}

Voit näyttää kuvakkeen `Avatar`-komponentin sisällä lisäämällä `Icon`-komponentin lapsena:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar, jossa on kuvake
Avatar avatar = new Avatar("Guest User", TablerIcon.create("user"));
```

## Etiketti ja alkuperäiset kirjaimet {#label-and-initials}

`Avatar`-komponentti käyttää etikettiä saavutettavuuden ja työkaluvihjeiden luomiseksi. `setLabel()` ja `setText()` -menetelmät ovat aliaksia, jotka molemmat asettavat saavuttavan etiketin `Avatar`-komponentille.

:::info Automaattisesti lasketut alkuperäiset kirjaimet
Kun luot `Avatar`-komponentin vain etiketillä, alkuperäiset kirjaimet lasketaan automaattisesti ottamalla ensimmäinen merkki jokaisesta sanasta. Esimerkiksi "John Doe" tulee "JD".
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Asettaa etiketin ja luo työkaluvihjeen automaattisesti
avatar.setInitials("JS");       // Ohittaa automaattisesti lasketut alkuperäiset kirjaimet
```

:::tip Automaattinen työkaluvihje
Komponentti luo automaattisesti työkaluvihjeen etiketistä, mikä helpottaa koko nimen näkemistä hiiren jopa kohdalla. Tämä toiminta on poistettu käytöstä käytettäessä oletusetikettiä "Avatar".
:::

## Napsautustapahtumat {#click-events}

`Avatar`-komponentti toteuttaa `HasElementClickListener`, mikä mahdollistaa käyttäjäklikkeihin vastaamisen. Tämä on hyödyllistä, kun haluat laukaista toimintoja, kuten avata käyttäjäprofiili tai näyttää valikko.

```java
avatar.onClick(event -> {
  // Käsittele avatarin napsautusta - esim. avaa käyttäjäprofiili
  System.out.println("Avatar clicked!");
});
```

## Muodot {#shapes}

Avatarit voidaan näyttää ympyröinä tai neliöinä. Oletusmuoto on `CIRCLE`, joka on normaali käyttäjäavatareille. Käytä `SQUARE`-muotoa entiteeteille, kuten tiimeille, yrityksille tai sovelluksille.

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## Teemat {#themes}

Teemat välittävät merkitystä tai tilaa; voit käyttää niitä ilmoittaaksesi saatavuudesta, korostaa tärkeitä käyttäjiä tai sovittaa sovelluksesi suunnitteluun.

Seuraavat teemat ovat saatavilla:

- `DEFAULT`: Vakiomainen ulkonäkö
- `GRAY`: Neutraali, hillitty ulkonäkö
- `PRIMARY`: Korostaa ensisijaisia toimintoja tai käyttäjiä
- `SUCCESS`: Ilmaisee positiivista tilaa (esim. online)
- `WARNING`: Ilmaisee varovaisuutta (esim. pois)
- `DANGER`: Ilmaisee virhe- tai varattu-tilaa
- `INFO`: Antaa tietoa antavaa kontekstia

Jokaisella teemalla on myös ääriviivavariantti kevyempää visuaalista käsittelyä varten:

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## Laajuudet {#expanses}

Säädä avatarin kokoa käyttämällä `setExpanse()` -menetelmää. Komponentti tukee yhdeksää kokoa, jotka vaihtelevat `XXXSMALL` - `XXXLARGE`.

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>

## Tyylitys {#styling}

<TableBuilder name="Avatar" />
