---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
sidebar_class_name: updated-content
_i18n_hash: da7906128f0d003b9ed8c48c99c3aefc
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

`RadioButtonGroup` hallinnoi kokoelmaa [`RadioButton`](/docs/components/radiobutton) komponentteja. Vain yksi `RadioButton` voi olla valittuna `RadioButtonGroup`-komponentissa. Kun käyttäjä valitsee uuden radiopainikkeen, aiemmin valittu painike ryhmässä poistetaan automaattisesti valinnasta.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important `RadioButtonGroup` renderöinti
`RadioButtonGroup` komponentti ei renderöi HTML-elementtiä. Se tarjoaa vain logiikkaa, jotta `RadioButton` komponentit käyttäytyvät ryhmänä sen sijaan, että ne olisivat erillisiä.
:::

## Lisääminen ja poistaminen `RadioButton` komponenteista {#adding-and-removing-radiobuttons}

Voit sisällyttää `RadioButton` komponentteja `RadioButtonGroup` konstruktoriin luodaksesi ryhmän annetuista komponenteista. 
Voit lisätä tai poistaa `RadioButton` olemassa olevasta `RadioButtonGroup`-komponentista käyttämällä `add()` tai `remove()` menetelmiä.

:::tip `RadioButton` ryhmän saaminen
`RadioButton` komponentilla on `getButtonGroup()` metodi, joka palauttaa `RadioButtonGroup`:n, johon se kuuluu, tai `null`, jos sillä ei ole ryhmää.
:::

## Sisäkkäin <DocChip chip='since' label='25.11' /> {#nesting}

Kuten muidenkin komponenttien kanssa, voit upottaa `RadioButtonGroup`-komponentin säiliöön, joten sinun ei tarvitse suoraan lisätä jokaista erillistä `RadioButton`:ia.

```java
RadioButton agree = new RadioButton("Hyväksyn");
RadioButton neutral = new RadioButton("Neutraali");
RadioButton disagree = new RadioButton("En hyväksy");

RadioButtonGroup group = new RadioButtonGroup("valinnat", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Vaihtoehdot");
fieldset.add(group);
```

## Käyttäen `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Jokaisella `RadioButton`-komponentilla voi olla oma tapahtumakuuntelijansa havaitakseen, kun käyttäjä vaihtaa sen tilaa. Yksi `RadioButtonGroup`:n etu on se, että voit käyttää yhtä tapahtumakuuntelijaa, joka reagoi kaikkiin ryhmän radiopainikkeisiin [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html) avulla.

**Tapahtumakuuntelijoiden lisääminen jokaiselle `RadioButton`:ille**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Yhden tapahtumakuuntelijan lisääminen `RadioButtonGroup`:iin**

```java
RadioButtonGroup group = new RadioButtonGroup("valinnat", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

Seuraava esimerkki [Drawer Placement](/docs/components/drawer#placement) käyttää `RadioButtonGroupChangeEvent`-tapahtumaa muuttaakseen automaattisesti `Drawer` komponentin sijoittelua:

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Nimeäminen {#naming}

`RadioButtonGroup`:n `name` attribuutti ryhmittelee liittyvät RadioButtonit yhteen, jolloin käyttäjät voivat tehdä yksittäisen valinnan tarjotuista vaihtoehdoista ja pakottaa eksklusiivisuuden RadioButtonien kesken. Ryhmän nimeä ei heijasteta DOM:iin, mutta se on mukavuusväline Java-kehittäjälle.
