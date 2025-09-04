---
sidebar_position: 1
title: Component Basics
slug: basics
draft: false
_i18n_hash: e4d0cb9dd9f53dabda8bebe6664bf0d3
---
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Komponentit ovat perustavanlaatuisia rakennuspalikoita, jotka voidaan lisätä ikkunaan, tarjoten käyttäjäliittymän toiminnallisuutta ja mukautettua käyttäytymistä. webforJ:ssä `Component`-luokka toimii kaikkien moottorin komponenttien perustana.

## Elinkaaren hallinta {#lifecycle-management}

Komponenttien elinkaaren ymmärtäminen on välttämätöntä komponenttien tehokkaalle luomiselle, hallinnalle ja hyödyntämiselle. Seuraavissa kahdessa elinkaarivaiheessa on menetelmiä, joilla voidaan manipuloida niiden käyttäytymistä. Näitä menetelmiä ei tulisi kutsua suoraan käyttäjän toimesta.

### Luo ja tuhoa koukut {#create-and-destroy-hooks}

Kaikkien `Component`-luokkaa laajentavien luokkien on toteutettava toiminnallisuus, joka suoritetaan, kun `Component` luodaan ja kun se tuhotaan. Tämä tapahtuu korvaamalla `onCreate()` ja `onDestroy()` -menetelmät vastaavasti.

#### `onCreate()` {#oncreate}

`onCreate()`-menetelmää kutsutaan, kun komponentti lisätään ikkunaan. Komponenttien luominen sisältää niiden alkuperäisen tilan ja toiminnallisuuden määrittämisen. Tässä määritellään, mitä komponentin tulisi tehdä sen ensimmäisellä luomisella. Olipa kyseessä muuttujien alustaminen, tapahtumakuuntelijoiden asettaminen tai muu asetus, `onCreate()`-menetelmä on sisäänkäyntisi komponentin käyttäytymisen mukauttamiseen. 

Tämä koukku vastaanottaa ikkunahypoteesin, joka mahdollistaa komponenttien lisäämisen, jotka sijaitsevat komponentin sisällä.

```java
@Override
protected void onCreate(Window window) {
  TextField text = new TextField();
  Button btn = new Button();

  window.add(text, btn);
}
```

:::tip
`onCreate()`-menetelmässä komponentti ja kaikki sen osat tulisi lisätä ikkunaan.
:::

#### `onDestroy()` {#ondestroy}

Komponenttien tuhoaminen on olennainen osa resurssien hallintaa ja asianmukaisen siivouksen varmistamista. Komponentin tuhoaminen on tarpeen, kun sitä ei enää tarvita tai kun halutaan vapauttaa siihen liittyvät resurssit. Se mahdollistaa kehittäjän suorittaa siivoustehtäviä, kuten ajastinten pysäyttämistä, muistin vapauttamista tai tapahtumakuuntelijoiden irrottamista. Se myös mahdollistaa `destroy()`-menetelmän kutsumisen mihin tahansa osakomponentteihin.

:::tip
`onDestroy()`-menetelmä on vastuussa `destroy()`-menetelmän kutsumisesta mihin tahansa osakomponentteihin. Muutoin nämä komponentit jäävät edelleen olemassa DOM:iin, mutta niitä ei voida saavuttaa API:n kautta.
:::

### Asynkroninen liittäminen {#asynchronous-attachment}

`whenAttached()`-menetelmä mahdollistaa toiminnallisuuden suorittamisen sen jälkeen, kun komponentti on lisätty ikkunaan. Tämä menetelmä palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joka mahdollistaa lisättyä määriteltyä käyttäytymistä suorittamaan asynkronisesti, kun komponentti on liitetty DOM:iin. 

:::tip
Toisin kuin edellisessä kolmessa menetelmässä, `whenAttached()` on tarkoitettu käyttäjän nimenomaan kutsuttavaksi.
:::

```java
public class Demo extends App {
  @Override
  public void run() throws WebforjException {
    Frame window = new Frame();

    Button button = new Button(); 

    /* Suora kutsu whenAttached(), joka näyttää 
    viestiruudun, kun painike on liitetty Frameen.*/
    button.whenAttached().thenAccept( e -> {
      showMessageDialog("Olen liitetty!", "Asynkroninen liittäminen");
    });
  
    // onCreate() -menetelmää kutsutaan
    window.add(button); 
  }
}
```

### Tarkkailijat {#observers}

Tarkkailijat ovat keskeisessä asemassa komponenttien elinkaaritapahtumien seuraamisessa. Tarkkailijoita voidaan lisätä ja poistaa käyttäen `addLifecycleObserver()` ja `removeLifecycleObserver()` -menetelmiä, ja ne saavat ilmoituksia tapahtumista, kuten komponenttien luomisesta ja tuhoamisesta.

Lisäämällä tarkkailijoita voit tehdä toimia, kun komponentti luodaan tai tuhotaan. Tämä on erityisen hyödyllistä mukautetun logiikan toteuttamisessa tai erityisten skenaarioiden käsittelemisessä komponenttitapahtumien perusteella.

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    // toteutettu logiikka, joka suoritetaan, kun painike tuhotaan
  }
});
```

## Komponentin ominaisuudet {#component-properties}

### Komponentin tunnisteet {#component-identifiers}

Komponenttien ID:t toimivat ainutlaatuisina tunnisteina komponenteille, mikä mahdollistaa niiden hallinnan ja tilan tehokkaan käsittelyn.

#### Palvelinpuolen komponentin ID {#server-side-component-id}

Jokaiselle `Component`-luokasta luodulle komponentille annetaan automaattisesti palvelinpuolen tunniste. Palvelinpuolen ID:t ovat olennaisia sisäiseen seurantaan ja komponenttien tunnistamiseen viitekehyksessä. Voit palauttaa palvelinpuolen komponentin ID:n käyttämällä `getComponentId()`-menetelmää.

Tämä voi olla hyödyllistä monissa tilanteissa, joissa ainutlaatuinen, palvelinpuolen tunniste on tarpeen, kuten tietyistä komponenteista kysyttäessä säiliössä.

#### Asiakaspuolen komponentin ID {#client-side-component-id}

Asiakaspuolen ID:t mahdollistavat käyttäjän saada asiakasedustuksen palvelinpuolen komponentista, joka on luotu Javalla. Kaikilla tarjotuilla webforJ-komponenteilla on tämän ID:n toteutus. Jos haluat saada pääsyn ja käyttää asiakaspuolen komponenttia, voit suorittaa `object.get()` asiakas-ID:llä saadaksesi halutun asiakaspuolen komponentin.

:::important
Tämä ID **ei** ole elementin ID-attribuutti DOM:ssa.
:::

Alla olevassa esimerkissä `onClick`-tapahtuma lisätään painikkeelle, joka sitten laukaistaan kutsumalla menetelmää asiakaspuolen komponentilla sen jälkeen, kun se on saatu käyttämällä `object.get()`-menetelmää.

```java
@Override
public void run() throws WebforjException {
  Frame frame = new Frame();
  Button btn = new Button("Napsauta minua");
  btn.onClick(e -> {
    showMessageDialog("Painiketta napsautettiin", "Tapahtuma tapahtui");
  });

  btn.whenAttached().thenAccept(e -> {
    getPage().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
  });
  frame.add(btn);
}
```

### Käyttäjädata {#user-data}

`Component`-luokka mahdollistaa lisätietojen sisällyttämisen komponenttiin käyttämällä `setUserData()`-menetelmää. Nämä tiedot ovat käytettävissä vain komponentin palvelinpuolella `getUserData()`-menetelmän kautta, eikä niitä lähetetä asiakkaalle. 

Tämä on varsin hyödyllistä, kun on tietoa, joka tulisi sisällyttää komponenttiin ja kun tämän tiedon pitäisi olla käytettävissä ilman, että siihen tarvitsisi siirtyä asiakkaalle sen hakemiseksi.
