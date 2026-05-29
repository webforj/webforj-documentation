---
sidebar_position: 3
title: Component Basics
slug: basics
draft: false
_i18n_hash: 0a9127dc9219a32aeb1eef280b386d77
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Komponentit ovat perusrakennuspalikoita, joita voidaan lisätä ikkunaan, ja ne tarjoavat käyttöliittymätoimintoja ja mukautettua käyttäytymistä. webforJ:ssa `Component`-luokka toimii kaikkien moottorin komponenttien perustana.

## Elinkaaren hallinta {#lifecycle-management}

Ymmärtäminen komponentin elinkaaresta on olennaista komponenttien luomiseksi, hallitsemiseksi ja käyttämiseksi tehokkaasti. Seuraavilla kahdella elinkaaren tilalla on menetelmiä, joilla niiden käyttäytymistä voidaan manipuloida. Näitä metodeja ei pitäisi käyttäjän kutsua suoraan.

### Luo ja tuhoa koukut {#create-and-destroy-hooks}

Kaikkien luokkien, jotka laajentavat `Component`-luokkaa, vastuulla on toteuttaa toiminnallisuus, joka suoritetaan, kun `Component` luodaan ja kun se tuhotaan. Tämä tapahtuu ylikirjoittamalla `onCreate()` ja `onDestroy()` -menetelmät vastaavasti.

#### `onCreate()` {#oncreate}

`onCreate()`-metodi kutsutaan, kun komponentti lisätään ikkunaan. Komponenttien luominen sisältää niiden alkuperäisen tilan ja toiminnallisuuden asetuksen. Tässä määritellään, mitä komponentin tulisi tehdä, kun se ensimmäisen kerran luodaan. Oli kyseessä muuttujien alustus, tapahtumakuuntelijoiden asettaminen tai muu valmistelu, `onCreate()`-metodi on sisäänkäynnin piste räätälöidä komponentin käyttäytymistä.

Tämä koukku saa ikkunan instanssin, joka mahdollistaa komponenttien lisäämisen komponentin sisälle.

```java
@Override
protected void onCreate(Window window) {
  TextField text = new TextField();
  Button btn = new Button();

  window.add(text, btn);
}
```

:::tip
`onCreate()`-metodi on se, jossa komponentti ja kaikki sen osat tulisi lisätä ikkunaan.
:::

#### `onDestroy()` {#ondestroy}

Komponenttien tuhoaminen on olennaista resurssien hallinnan ja asianmukaisen siivouksen varmistamiseksi. Komponentin tuhoaminen on tarpeellista, kun sitä ei enää tarvita tai kun halutaan vapauttaa siihen liittyvät resurssit. Tämä antaa kehittäjälle mahdollisuuden suorittaa siivoustehtäviä, kuten ajastimien pysäyttämistä, muistin vapauttamista tai tapahtumakuuntelijoiden irrottamista. Se myös sallii `destroy()`-metodin kutsumisen kaikille osakompONENTeille.

:::tip
`onDestroy()`-metodi on vastuussa `destroy()`-metodin kutsumisesta kaikille osakompONENTeille. Muuten nämä komponentit jäävät edelleen olemassa DOM:iin, mutta niitä ei voi saavuttaa API:n kautta.
:::

### Asynkroninen liittäminen {#asynchronous-attachment}

`whenAttached()`-metodi mahdollistaa toiminnallisuuden suorittamisen sen jälkeen, kun komponentti on lisätty ikkunaan. Tämä metodi palauttaa <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joka mahdollistaa lisätyön asynkronisen suorittamisen, kun komponentti on kiinnitetty DOM:iin.

:::tip
Toisin kuin kolme edellistä metodia, `whenAttached()` on tarkoitettu käyttäjän kutsuttavaksi suoraan.
:::

```java
public class Demo extends App {
  @Override
  public void run() throws WebforjException {
    Frame window = new Frame();

    Button button = new Button(); 

    /* Suora kutsu whenAttached()-metodiin, joka näyttää 
    viestiruudun, kun nappi on kiinnitetty Frameen.*/
    button.whenAttached().thenAccept( e -> {
      showMessageDialog("Olen kiinnitetty!", "Asynkroninen liittäminen");
    });
  
    // onCreate()-metodi kutsutaan
    window.add(button); 
  }
}
```

### Observerit {#observers}

Observerit näyttelevät keskeistä roolia komponentin elinkaaritapahtumien seuraamisessa. Observereita voidaan lisätä ja poistaa käyttämällä `addLifecycleObserver()` ja `removeLifecycleObserver()` -menetelmiä, ja ne saavat ilmoituksia tapahtumista, kuten komponenttien luomisesta ja tuhoamisesta.

Lisäämällä observeereita voit toimia, kun komponentti luodaan tai tuhotaan. Tämä on erityisen hyödyllistä mukautetun logiikan toteuttamisessa tai erityisten skenaarioiden käsittelemisessä komponenttitapahtumien perusteella.

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    // toteutettu logiikka, joka suoritetaan, kun nappi tuhotaan
  }
});
```

## Komponentin ominaisuudet {#component-properties}

### Komponentin tunnisteet {#component-identifiers}

Komponentti-ID:t toimivat ainutkertaisina tunnisteina komponenteille, mikä mahdollistaa niiden vuorovaikutuksen ja tilan hallinnan tehokkaasti.

#### Palvelinpuolen komponentin ID {#server-side-component-id}

Jokaiselle `Component`-luokasta luodulle komponentille annetaan automaattisesti palvelinpuolen tunniste. Palvelinpuolen ID:t ovat olennaisia sisäiseen seurantaan ja komponenttien tunnistamiseen keh框uksessa. Voit palauttaa palvelinpuolen komponentin ID:n käyttämällä `getComponentId()`-metodia.

Tämä voi olla hyödyllistä monissa tilanteissa, joissa ainutkertaisen, palvelinpuolen tunnisteen saaminen on tarpeen, esimerkiksi kyselyssä tietyn komponentin löytämiseksi säiliöstä.

#### Asiakaspuolen komponentin ID {#client-side-component-id}

Asiakaspuolen ID:t mahdollistavat käyttäjän saada asiakaspuolen esityksen palvelinpuolen komponentista, joka on luotu Javassa. Kaikilla tarjotuilla webforJ-komponenteilla on tämän ID:n toteutus. Jos haluat saada pääsyn ja käyttää asiakaspuolen komponenttia, voit suorittaa `object.get()` asiakas-ID:n kanssa saadaksesi halutun asiakas komponentin.

:::important
Tämä ID ei ole **DOM:n elementin ID-attribuutti**.
:::

Alla olevassa esimerkissä `onClick`-tapahtuma lisätään napille, joka laukaistaan kutsumalla menetelmää asiakas komponentille sen jälkeen, kun se on saatu `object.get()`-menetelmällä.

```java
@Override
public void run() throws WebforjException {
  Frame frame = new Frame();
  Button btn = new Button("Klikkaa minua");
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

`Component`-luokka mahdollistaa lisätietojen sisällyttämisen komponenttiin käyttämällä `setUserData()`-metodia. Tämä tieto on käytettävissä vain komponentin palvelinpuolella `getUserData()`-metodin kautta, eikä sitä lähetetä asiakkaalle.

Tämä on erittäin hyödyllistä, kun on tietoa, joka tulisi sisällyttää komponenttiin ja joka tulisi olla saatavilla ilman, että asiakkaalle on tehtävä matkaa.
