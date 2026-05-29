---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 5716356b99e40dc53cfdf82a87fd9b3c
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

`RadioButtonGroup` hallitsee kokoelmaa [`RadioButton`](/docs/components/radiobutton) -komponentteja. Ainoastaan yksi `RadioButton` voi olla valittuna `RadioButtonGroup` -ryhmässä. Kun käyttäjä valitsee uuden radio painikkeen, aiemmin valittu painike ryhmässä poistuu automaattisesti valinnasta.

<!-- INTRO_END -->

## Luominen `RadioButtonGroup` {#creating-a-radiobuttongroup}

:::important `RadioButtonGroup` renderointi
`RadioButtonGroup` komponentti ei renderoi HTML-elementtiä. Se tarjoaa vain logiikan, joka saa `RadioButton` komponentit toimimaan ryhmänä sen sijaan, että ne toimisivat erikseen.
:::

Luo yksittäiset `RadioButton` komponentit ja siirrä ne `RadioButtonGroup` konstruktorille. Ainoastaan yksi painike ryhmässä voi olla valittuna kerrallaan.

<ComponentDemo
path='/webforj/radiobuttongroup'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java']}
height='200px'
/>


## `RadioButton` komponenttien lisääminen ja poistaminen {#adding-and-removing-radiobuttons}

Voit sisällyttää `RadioButton` komponentteja `RadioButtonGroup` konstruktorille luodaksesi ryhmän annetuista komponenteista.
Jos haluat lisätä tai poistaa `RadioButton` olemassa olevasta `RadioButtonGroup`:sta, käytä `add()` tai `remove()` metodeja.

:::tip Saadaksesi `RadioButton` ryhmän
`RadioButton` komponentilla on `getButtonGroup()` metodi, joka palauttaa `RadioButtonGroup`:n, johon se kuuluu, tai `null`, jos sillä ei ole ryhmää.
:::

## Nester <DocChip chip='since' label='25.11' /> {#nesting}

Kuten muut komponentit, voit sisällyttää `RadioButtonGroup` containeriin, joten sinun ei tarvitse suoraan lisätä jokaista yksittäistä `RadioButton` -painiketta.

```java
RadioButton agree = new RadioButton("Samaa mieltä");
RadioButton neutral = new RadioButton("Neutraali");
RadioButton disagree = new RadioButton("Eri mieltä");

RadioButtonGroup group = new RadioButtonGroup("valinnat", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Vaihtoehdot");
fieldset.add(group);
```

## `RadioButtonGroupChangeEvent` käyttöönotto {#using-radiobuttongroupchangeevent}

Jokaisella `RadioButton`:lla voi olla oma tapahtumakuuntelijansa havaitakseen, kun käyttäjä kytkee sen. Yksi `RadioButtonGroup`:n etu on, että voit käyttää yhtä tapahtumakuuntelijaa, joka reagoi kaikkiin ryhmän radio painikkeisiin [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html) -tapahtumassa.

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

Seuraava esimerkki [Drawer Placement](/docs/components/drawer#placement) käyttää `RadioButtonGroupChangeEvent`:ia automaattisesti muuttamaan `Drawer` komponentin sijaintia:

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Nimettömyys {#naming}

`name` attribuutti `RadioButtonGroup`:ssa ryhmittelee related RadioButtons yhteen, jolloin käyttäjät voivat tehdä yhden valinnan tarjotuista vaihtoehdoista ja valvovat eksklusiivisuutta RadioButtonien kesken. Ryhmän nimeä ei heijasteta DOM:ssa, vaan se on kätevä työkalu Java-kehittäjälle.
