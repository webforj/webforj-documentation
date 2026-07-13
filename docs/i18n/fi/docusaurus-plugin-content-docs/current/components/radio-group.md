---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
description: >-
  Coordinate mutually exclusive RadioButton selections with RadioButtonGroup,
  including nested containers and dynamic membership.
_i18n_hash: a616c60faaf0d58f9d9a1e778318880a
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

`RadioButtonGroup` hallitsee kokoelman [`RadioButton`](/docs/components/radiobutton) -komponentteja. Vain yksi `RadioButton` voi olla valittuna `RadioButtonGroup` -ryhmässä. Kun käyttäjä valitsee uuden radiopainikkeen, aiemmin valittu painike ryhmässä poistuu automaattisesti valinnasta.

<!-- INTRO_END -->

## `RadioButtonGroup` luominen {#creating-a-radiobuttongroup}

:::important `RadioButtonGroup` renderointi
`RadioButtonGroup` -komponentti ei renderöi HTML-elementtiä. Se tarjoaa vain logiikan, jotta `RadioButton` -komponentit käyttäytyvät ryhmänä sen sijaan, että ne toimisivat yksilöinä.
:::

Luo erilliset `RadioButton` -komponentit ja siirrä ne `RadioButtonGroup` -rakentajaan. Vain yksi painike ryhmässä voi olla valittuna kerrallaan.

<ComponentDemo
path='/webforj/radiobuttongroup'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java']}
height='200px'
/>


## `RadioButton` -komponenttien lisääminen ja poistaminen {#adding-and-removing-radiobuttons}

Voit sisällyttää `RadioButton` -komponentteja `RadioButtonGroup` -rakentajaan luodaksesi ryhmän annetuista komponenteista. 
Voit lisätä tai poistaa `RadioButton` -painikkeen olemassa olevasta `RadioButtonGroup` -ryhmästä käyttämällä `add()` tai `remove()` -menetelmiä.

:::tip `RadioButton` ryhmän saaminen
`RadioButton` -komponentilla on `getButtonGroup()` -menetelmä, joka palauttaa sille kuuluvan `RadioButtonGroup` -ryhmän, tai `null`, jos sillä ei ole ryhmää.
:::

## Sisäkkäinen <DocChip chip='since' label='25.11' /> {#nesting}

Kuten muutkin komponentit, voit sisällyttää `RadioButtonGroup` -ryhmän säiliöön, jotta et tarvitse lisätä jokaista yksittäistä `RadioButton` -painiketta suoraan.

```java
RadioButton agree = new RadioButton("Samaa mieltä");
RadioButton neutral = new RadioButton("Neutraali");
RadioButton disagree = new RadioButton("Eri mieltä");

RadioButtonGroup group = new RadioButtonGroup("valinnat", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Vaihtoehdot");
fieldset.add(group);
```

## `RadioButtonGroupChangeEvent` käyttäminen {#using-radiobuttongroupchangeevent}

Jokaisella `RadioButton` -painikkeella voi olla oma tapahtumakuuntelija, joka havaitsee, kun käyttäjä vaihtaa sitä. Yksi `RadioButtonGroup` -ryhmän etu on kuitenkin se, että voit käyttää yhtä tapahtumakuuntelijaa, joka reagoi kaikkiin ryhmän radiopainikkeisiin [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html) -tapahtuman avulla.

**Tapahtumakuuntelijoiden lisääminen jokaiselle `RadioButton` -painikkeelle**

```java
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Yhden tapahtumakuuntelijan lisääminen `RadioButtonGroup` -ryhmälle**

```java
RadioButtonGroup group = new RadioButtonGroup("valinnat", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

Seuraava esimerkki [Lokeron sijoittelusta](/docs/components/drawer#placement) käyttää `RadioButtonGroupChangeEvent` -tapahtumaa muuttaakseen automaattisesti `Drawer` -komponentin sijoittelua:

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Nimeäminen {#naming}

`name` -attribuutti `RadioButtonGroup` -ryhmässä ryhmittelee liittyvät radiopainikkeet yhteen, mikä mahdollistaa käyttäjien tekemän vain yhden valinnan annetusta vaihtoehdosta ja pakottaa ekskluusiivisuuden radiopainikkeiden kesken. Ryhmän nimi ei heijastu DOM:iin, vaan se on kätevä työkalu Java-kehittäjälle.
