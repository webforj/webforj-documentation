---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
sidebar_class_name: updated-content
_i18n_hash: 564d1d0c46ef2395fb2ad2785ba18d53
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

`RadioButtonGroup` hallitsee kokoelman [`RadioButton`](/docs/components/radiobutton) -komponentteja. Vain yksi `RadioButton` voi olla valittuna `RadioButtonGroup` -komponentissa. Kun käyttäjä valitsee uuden radiopainikkeen, aiemmin valittu painike ryhmässä poistuu automaattisesti valinnasta.

<!-- INTRO_END -->

## Luominen `RadioButtonGroup` {#creating-a-radiobuttongroup}

:::important `RadioButtonGroup` renderöinti
`RadioButtonGroup` -komponentti ei renderöi HTML-elementtiä. Se tarjoaa vain logiikkaa, jotta `RadioButton` -komponentit käyttäytyvät ryhmänä sen sijaan, että ne olisivat yksittäisiä.
:::

Luo yksittäisiä `RadioButton` -komponentteja ja siirrä ne `RadioButtonGroup` -konstruktorille. Ryhmässä voi olla kerralla vain yksi valittu painike.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>


## `RadioButton` -komponenttien lisääminen ja poistaminen {#adding-and-removing-radiobuttons}

Voit sisällyttää `RadioButton` -komponentteja `RadioButtonGroup` -konstruktorille luodaksesi ryhmän annetuista komponenteista.
Lisätäksesi tai poistaaksesi `RadioButton` -komponentin olemassa olevasta `RadioButtonGroup` -komponentista, käytä `add()` tai `remove()` -menetelmiä.

:::tip `RadioButton` ryhmän saaminen
`RadioButton` -komponentilla on `getButtonGroup()` -menetelmä, joka palauttaa siihen kuuluvan `RadioButtonGroup`:in tai `null`, jos sillä ei ole ryhmää.
:::

## Nikos <DocChip chip='since' label='25.11' /> {#nesting}

Kuten muut komponentit, voit sisällyttää `RadioButtonGroup` -komponentin konttiin, joten sinun ei tarvitse lisätä jokaista yksittäistä `RadioButton` -komponenttia suoraan.

```java
RadioButton agree = new RadioButton("Hyväksyn");
RadioButton neutral = new RadioButton("Neutraali");
RadioButton disagree = new RadioButton("En hyväksy");

RadioButtonGroup group = new RadioButtonGroup("valinnat", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Vaihtoehdot");
fieldset.add(group);
```

## `RadioButtonGroupChangeEvent` käyttäminen {#using-radiobuttongroupchangeevent}

Jokaisella `RadioButton` -komponentilla voi olla oma tapahtumakuuntelija havaitakseen, kun käyttäjä vaihtaa sitä. Yksi `RadioButtonGroup` -komponentin etu on se, että voit käyttää yhtä tapahtumakuuntelijaa, joka reagoi kaikkiin ryhmän radiopainikkeisiin käyttäen [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html).

**Tapahtumakuuntelijoiden lisääminen jokaiselle `RadioButton`:lle**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Yhden tapahtumakuuntelijan lisääminen `RadioButtonGroup`:lle**

```java
RadioButtonGroup group = new RadioButtonGroup("valinnat", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

Seuraava esimerkki [Drawer Placement](/docs/components/drawer#placement) käyttää `RadioButtonGroupChangeEvent` -tapahtumaa muuttaakseen `Drawer` -komponentin sijaintia automaattisesti:

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Nimeäminen {#naming}

`name` -attribuutti `RadioButtonGroup` -komponentissa ryhmittelee samanlaiset RadioButtonit yhteen, joten käyttäjät voivat valita yhden vaihtoehdon. Nimen ei kuitenkaan ole tarkoitus näkyä DOM:ssa, vaan se on kätevä työkalu Java-kehittäjälle.
