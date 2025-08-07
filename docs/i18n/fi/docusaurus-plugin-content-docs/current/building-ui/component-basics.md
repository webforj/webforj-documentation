---
sidebar_position: 1
title: Component Basics
slug: basics
draft: false
_i18n_hash: d517f6169f7ac0798ed073bb27348eb5
---
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Komponentit ovat perus rakennuspalikoita, joita voidaan lisätä ikkunaan, ja ne tarjoavat käyttöliittymätoiminnallisuutta sekä mukautettua käyttäytymistä. webforJ:ssä `Component`-luokka toimii kaikkien komponenttien perustana moottorissa.

## Elinkaaren hallinta {#lifecycle-management}

Komponentin elinkaaren ymmärtäminen on olennaista komponenttien tehokkaalle luomiselle, hallinnalle ja hyödyntämiselle. Seuraavilla kahdella elinkaaritilalla on menetelmät, joilla voidaan manipuloida niiden käyttäytymistä. Näitä menetelmiä ei tulisi käyttäjän kutsua suoraan.

### Luo ja tuhoa koukut {#create-and-destroy-hooks}

Kaikkien luokkien, jotka laajentavat `Component`-luokkaa, on vastuussa toiminnallisuuden toteuttamisesta, joka suoritetaan, kun `Component` luodaan ja kun se tuhotaan. Tämä tapahtuu ylikirjoittamalla `onCreate()` ja `onDestroy()` -menetelmät.

#### `onCreate()` {#oncreate}

`onCreate()`-menetelmää kutsutaan, kun komponentti lisätään ikkunaan. Komponenttien luomiseen liittyy niiden alkuperäisen tilan ja toiminnallisuuden määrittäminen. Tässä määrittelet, mitä komponentin pitäisi tehdä, kun se luodaan ensimmäistä kertaa. Olipa kyseessä muuttujien alustaminen, tapahtumakuuntelijoiden asettaminen tai muu asetustyö, `onCreate()`-menetelmä on sisäänkäyntisi komponentin käyttäytymisen mukauttamiseen.

Tämä koukku vastaanottaa ikkuna-instanssin, joka mahdollistaa komponenttien lisäämisen komponentin sisälle.

```java
@Override
protected void onCreate(Window window) {
  TextField text = new TextField();
  Button btn = new Button();

  window.add(text, btn);
}
```

:::tip
`onCreate()`-menetelmä on se, missä komponentti ja kaikki sen osat tulisi lisätä ikkunaan.
:::

#### `onDestroy()` {#ondestroy}

Komponenttien tuhoaminen on olennaista resurssien hallinnassa ja asianmukaisessa siivouksessa. Komponentin tuhoaminen on tarpeen, kun sitä ei enää tarvita tai kun haluat vapauttaa siihen liittyviä resursseja. Se mahdollistaa kehittäjälle siivoustehtävien suorittamisen, kuten aikarajojen lopettamisen, muistin vapauttamisen tai tapahtumakuuntelijoiden irrottamisen. Se sallii myös `destroy()`-menetelmän kutsumisen kaikille osakomponenteille.

:::tip
`onDestroy()`-menetelmällä on vastuulua kutsua `destroy()`-menetelmää kaikille osakomponenteille. Muussa tapauksessa nämä komponentit ovat yhä olemassa DOM:ssa, mutta niitä ei voida saavuttaa API:n kautta.
:::

### Asynkroninen liittäminen {#asynchronous-attachment}

`whenAttached()`-menetelmä mahdollistaa toiminnallisuuden suorittamisen sen jälkeen, kun komponentti on lisätty ikkunaan. Tämä menetelmä palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joka mahdollistaa lisätyön suorittamisen asynkronisesti, kun komponentti on liitetty DOM:iin.

:::tip
Toisin kuin edellisissä kolmessa menetelmässä, `whenAttached()` on tarkoitettu käyttäjän eksplisiittiseksi kutsumiseksi.
:::

```java
public class Demo extends App {
  @Override
  public void run() throws WebforjException {
    Frame window = new Frame();

    Button button = new Button(); 

    /* Eksplisiittinen kutsu whenAttached() -menetelmälle, joka näyttää 
    viestiruudun, kun nappi on liitetty Frameen. */
    button.whenAttached().thenAccept( e -> {
      showMessageDialog("Olen liitetty!", "Asynkroninen liittäminen");
    });
  
    // onCreate()-menetelmää kutsutaan
    window.add(button); 
  }
}
```

### Observerit {#observers}

Observerit näyttelevät tärkeää roolia komponenttien elinkaaritapahtumien seuraamisessa. Observereita voidaan lisätä ja poistaa käyttäen `addLifecycleObserver()` ja `removeLifecycleObserver()` -menetelmiä, ja ne saavat ilmoituksia tapahtumista, kuten komponenttien luomisesta ja tuhoamisesta.

Observereiden lisääminen mahdollistaa toiminnan suorittamisen, kun komponentti luodaan tai tuhotaan. Tämä on erityisen hyödyllistä mukautetun logiikan toteuttamisessa tai erityisten skenaarioiden käsittelyssä komponenttitapahtumien perusteella.

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    //toiminta, joka suoritetaan, kun nappi tuhotaan
  }
});
```

## Komponentin ominaisuudet {#component-properties}

### Komponentin tunnisteet {#component-identifiers}

Komponenttitunnukset toimivat ainutlaisina tunnisteina komponentoille, jolloin voit vuorovaikuttaa niiden kanssa ja hallita niiden tilaa tehokkaasti.

#### Palvelinpuolen komponentin tunnus {#server-side-component-id}

Jokaiselle `Component`-luokasta luodulle komponentille annetaan automaattisesti palvelinpuolen tunnus. Palvelinpuolen tunnukset ovat olennaisia komponenttien sisäiselle seurannalle ja tunnistamiselle kehyksessä. Voit hakea palvelinpuolen komponentin tunnuksen `getComponentId()`-menetelmällä.

Tämä voi olla hyödyllistä monissa tilanteissa, joissa ainutlaatuinen, palvelinpuolen tunnus on tarpeen, kuten tietyn komponentin kysely containerissa.

#### Asiakaspuolen komponentin tunnus {#client-side-component-id}

Asiakaspuolen tunnukset mahdollistavat käyttäjän saamisen asiakasedustuksesta palvelinpuolen komponentille, joka on luotu Javassa. Kaikilla tarjotuilla webforJ-komponenteilla on tämän tunnuksen toteutus. Jos haluat saada pääsyn asiakaspuolen komponenttiin ja käyttää sitä, voit suorittaa `object.get()` asiakaspuolen tunnuksella haluamasi asiakaspuolen komponentin saamiseksi.

:::important
Tämä tunnus **ei** ole DOM:n elementin ID-attribuutti.
:::

Alla olevassa esimerkissä lisätään `onClick`-tapahtuma nappiin, joka laukaistaan kutsumalla menetelmää asiakaspuolen komponentilla sen jälkeen, kun se on saatu `object.get()`-menetelmällä.

```java
@Override
public void run() throws WebforjException {
  Frame frame = new Frame();
  Button btn = new Button("Napsauta minua");
  btn.onClick(e -> {
    showMessageDialog("Nappia klikattiin", "Tapahtuma tapahtui");
  });

  btn.whenAttached().thenAccept(e -> {
    getPage().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
  });
  frame.add(btn);
}
```

### Käyttäjätiedot {#user-data}

`Component`-luokka mahdollistaa lisätietojen sisällyttämisen komponenttiin `setUserData()`-menetelmän avulla. Nämä tiedot ovat pääsy vain komponentin palvelinpuolelle `getUserData()`-menetelmällä, eikä niitä lähetetä asiakkaalle.

Tämä on hyvin hyödyllistä, kun on tietoja, jotka tulisi sisällyttää komponenttiin, ja kun kyseiset tiedot tulisi olla saatavilla ilman tarpeettomasti asiakaspuolelle siirtämistä niiden hakemiseksi.
