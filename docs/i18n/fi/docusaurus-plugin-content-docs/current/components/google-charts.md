---
title: Google Charts
sidebar_position: 50
_i18n_hash: 84d9b14321275191deb78447cde7c7fe
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

<!-- Koulu yleiskuvaus komponentista ja sen toiminnasta -->

:::info Google Chartsin tuominen
Voit käyttää `GoogleChart`-luokkaa sovelluksessasi käyttämällä seuraavaa XML:ää POM-tiedostossasi:

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-googlecharts</artifactId>
    <version>${webforj.version}</version>
</dependency>
```
:::

`GoogleChart`-luokka on kattava ratkaisu rikkaiden, vuorovaikutteisten kaavioiden upottamiseksi verkkosovelluksiin. Tämä luokka toimii siltana [Google Charts](https://developers.google.com/chart) -kirjastolle, tarjoten laajan valikoiman kaaviotyyppejä, jotka sopivat mihin tahansa tietonäyttötavoitteeseen.

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>

## Kaaviotyypit {#chart-types}

`GoogleChart`-lisäosa tarjoaa kattavan valikoiman kaaviotyyppejä eri tietonäyttötavoitteita varten. Oikean kaaviotyypin valinta on olennaista tietojen tarinan tehokkaaksi viestimiseksi. Näet alla olevan gallerian esimerkkejä yleisistä kaavioista, joita voidaan käyttää webforJ-sovelluksessa.

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## Vaihtoehdot {#options}

`GoogleChart`-lisäosa mahdollistaa laajan mukauttamisen erilaisten vaihtoehtojen avulla. Nämä vaihtoehdot antavat sinun räätälöidä kaaviosi ulkoasun ja toiminnallisuuden sovelluksesi tarpeiden mukaan. Vaihtoehdot siirretään kaavion `setOptions()`-metodiin `Map<String, Object>`-rakenteena. 

Tässä on esimerkki kaavion vaihtoehtojen asettamisesta:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Kuukausittaiset tulot");
options.put("backgroundColor", "#EFEFEF");

// Sovella vaihtoehdot kaavioon
chart.setOptions(options);
```

Lisätietoja saatavilla olevista vaihtoehdoista tietyille kaavioille löytyy [Google Visualization API -viite (Chart Gallery)](https://developers.google.com/chart/interactive/docs/gallery).

## Datan asettaminen {#setting-data}

Datan visualisoiminen `GoogleChart`-kaaviolla vaatii datan asianmukaista rakennetta ja asettamista. Tämä opas käynnistää sinut datasi valmistelussa ja sen soveltamisessa kaavioihisi.

### Perusdatan asettaminen {#basic-data-setup}

Yksinkertaisin tapa määrittää data on käyttää `List<Object>`, jossa kukin rivi on lista arvoja.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Tehtävä", "Tunteja päivässä"));
data.add(Arrays.asList("Työ", 11));
data.add(Arrays.asList("Syö", 2));
data.add(Arrays.asList("Työmatka", 2));
data.add(Arrays.asList("Katso TV", 2));
data.add(Arrays.asList("Nuku", 7));
chart.setData(data);
```

### Karttojen käyttäminen monimutkaisemmissa rakenteissa {#using-maps-for-more-complex-structures}

Monimutkaisemmissa tietorakenteissa voit käyttää karttoja rivien esittämiseen ja muuntaa ne sitten vaadittuun muotoon.

```java
List<Object> data = new ArrayList<>();

// Otsikkorivi
data.add(Arrays.asList("Maa", "Tulot"));

// Datarivit
Map<String, Object> row1 = Map.of("Maa", "Saksa", "Tulot", 1000);
Map<String, Object> row2 = Map.of("Maa", "Yhdysvallat", "Tulot", 1170);
Map<String, Object> row3 = Map.of("Maa", "Brasil", "Tulot", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

Kun data on valmis, se voidaan soveltaa GoogleChartiin käyttämällä setData-menetelmää.

<ComponentDemo 
path='/webforj/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### Datan ja vaihtoehtojen lataaminen JSON:sta {#loading-data-and-options-from-json}

Voit myös ladata dataa ja vaihtoehtoja JSON-tiedostoista käyttämällä Gsonia helpomman hallinnan vuoksi. Tämä lähestymistapa auttaa pitämään datasi ja vaihtoehtosi järjestyksessä ja helppoina päivittää.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Vuosi", "Myynnit", "Kulut"));
data.add(Arrays.asList("2013", 1000, 400));
data.add(Arrays.asList("2014", 1170, 460));
data.add(Arrays.asList("2015", 660, null));
data.add(Arrays.asList("2016", 1030, 540));
chart.setData(data);

Map<String, Object> options = new Gson().fromJson(
    Assets.contentOf("options.json"),
    new TypeToken<Map<String, Object>>() {}.getType()
);
chart.setOptions(options);
```

## Kaavioiden visuaalisten elementtien päivittäminen {#updating-chart-visuals}

Kaavioiden ulkoasun päivittäminen tai päivittäminen datamuutosten, käyttäjäinteractionien tai visuaalisten vaihtoehtojen säätämisen seurauksena on yksinkertaista `redraw()`-menetelmällä. Tämä menetelmä varmistaa, että kaavioisi pysyvät tarkkoina ja visuaalisesti linjassa taustalla olevan datan tai asetusten muutosten kanssa.

Käytä `redraw()`-menetelmää sellaisissa tilanteissa, kuten:

- **Datan muutosten jälkeen**: Varmistaa, että kaavio heijastaa kaikki päivitykset sen datalähteeseen.
- **Asetusten muutoksen yhteydessä**: Soveltaa uusia tyylityksiä tai konfiguraatiomuutoksia kaavioon.
- **Vastaavien säätöjen aikana**: Säätää kaavion asettelua tai kokoa, kun säiliön mitat muuttuvat, varmistaen optimaalinen näyttö eri laitteilla.

<ComponentDemo 
path='/webforj/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='/css/googlecharts/chartRedraw.css'
height='650px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## Kaavioiden vienti kuvina {#exporting-charts-as-images}

`getImageUri()`-menetelmä tarjoaa tavan viedä Google Charts base64-koodattuna PNG-kuvana. Tämä menetelmä on erityisen hyödyllinen kaavioiden jakamiseen verkkoympäristön ulkopuolella, niiden upottamiseen sähköposteihin tai asiakirjoihin tai yksinkertaisesti arkistointitarkoituksiin.

Kutsu `getImageUri()` kaavio-instanssillasi sen jälkeen, kun kaavio on täysin renderöity. Tyypillisesti tätä menetelmää käytetään "ready"-tapahtuman kuuntimessa varmistaaksesi, että kaavio on valmis vietyväksi:

```java
chart.addReadyListener(e -> {
    String imageUri = chart.getImageUri();
    // Nyt voit käyttää imageUri'ta, esimerkiksi img-tagin src-attribuuttina
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

`GoogleChartSelectedEvent` laukaisee aina, kun käyttäjä valitsee datapisteen tai segmentin Google Chart -komponentissa. Tämä tapahtuma mahdollistaa vuorovaikutuksen valitun kaaviorin datan kanssa, tarjoamalla tietoja siitä, mitä on valittu. Tapahtumaa voidaan kuunnella käyttämällä `addSelectedListener()`-menetelmää `GoogleChart`-instanssilla.

`GoogleChartSelectedEvent` on hyödyllinen sovelluksissa, joissa käyttäjän vuorovaikutus kaavion kanssa on tarpeen.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Lisää valittu kuuntelija kaavioon
chart.addSelectedListener(event -> {
    // Hanki valinta
    List<Object> selection = chart.getSelection();
    
    // Käsittele valittu tapahtuma
    if (!selection.isEmpty()) {
        System.out.println("Valittu rivi: " + selection.get(0));
        // Lisäprosessi valitun rivin/kolumnin perusteella
    }
});
```

### Payload {#payload}
`GoogleChartSelectedEvent` tarjoaa pääsyn valintadatasi, joka voidaan noutaa käyttämällä `getSelection()`-menetelmää kaavio-objektilla. Tämä menetelmä palauttaa listan objekteista, joissa jokaisella objektilla on seuraavat ominaisuudet:

- **row**: Rivi-indeksi kaavion datataulukossa, joka valittiin.
- **column**: Tietotaulukon sarakeindeksi, joka on valinnainen ja koskee kaavioita, jotka mahdollistavat yksittäisten solujen valinnan, kuten taulukko-kaavio.

Pii/viivakaavioita tai pylväskaavioita varten tarjotaan yleensä vain `row`, joka osoittaa valittua datapistettä.

Tässä on esimerkki payloadista:
```java
[
  {
    "row": 3,  // Valitun rivin indeksit datassa
    "column": 2  // (Valinnainen) Valitun sarakkeen indeksi
  }
]
```

:::info Monien datapisteiden valitseminen
Jos käyttäjä valitsee useita datapisteitä, `getSelection()`-menetelmä palauttaa objektien taulukon, joista kukin edustaa valittua elementtiä. Payload voi vaihdella kaaviotyypin ja käyttäjän tekemän vuorovaikutuksen mukaan.
:::
