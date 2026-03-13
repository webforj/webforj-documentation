---
title: Google Charts
sidebar_position: 50
_i18n_hash: 3fe2f0cf8eb09dad5a6e8fb8f6cfe3cf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

`GoogleChart`-komponentti integroi [Google Charts](https://developers.google.com/chart) -kirjaston webforJ:hen, mikä antaa sinulle pääsyn kaaviotyyppeihin kuten pylväs, viiva, piirakka, geo ja muuta. Kaavioita voidaan konfiguroida Javalla käyttämällä tyyppiä, tietojoukkoa ja vaihtoehtokarttaa, joka säätelee ulkoasua ja käyttäytymistä.

<!-- INTRO_END -->

## Luodaan kaavio {#creating-a-chart}

:::info Google Chartsin tuonti
Käyttääksesi `GoogleChart`-luokkaa sovelluksessasi, käytä seuraavaa XML-koodia POM-tiedostossasi:

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-googlecharts</artifactId>
    <version>${webforj.version}</version>
</dependency>
```
:::

Luodaksesi kaavion, määrittele kaaviotyyppi, säädä visuaaliset vaihtoehdot ja anna näytettävä data.

Tässä esimerkissä luodaan geo-kaavio, joka kartoittaa voitot eri maissa, mukautetuilla väreillä, legendan sijoittelulla ja kaavioalueen koon säädöillä:

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>

## Kaaviotyypit {#chart-types}

`GoogleChart`-lisäosa tarjoaa kattavan valikoiman kaaviotyyppejä, jotka sopivat erilaisiin datan visualisointitarpeisiin. Oikean kaaviotyypin valinta on olennaista datan tarinan tehokkaassa viestinnässä. Katso alla olevasta galleriasta esimerkkejä yleisistä kaavioista, joita voidaan käyttää webforJ-sovelluksessa.

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## Vaihtoehdot {#options}

`GoogleChart`-lisäosa mahdollistaa laajan mukauttamisen erilaisilla vaihtoehdoilla. Nämä vaihtoehdot antavat sinulle mahdollisuuden räätälöidä kaavioidesi ulkoasua ja toiminnallisuutta sovelluksesi tarpeisiin. Vaihtoehdot siirretään `Map<String, Object>` -tyyppinä kaavion `setOptions()`-metodiin.

Tässä esimerkki kaavion vaihtoehtojen asettamiseksi:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Kuukausittainen voitto");
options.put("backgroundColor", "#EFEFEF");

// Ota vaihtoehdot käyttöön kaaviossa
chart.setOptions(options);
```

Lisätietoa erityisten kaavioiden saatavilla olevista vaihtoehdoista löydät [Google Visualization API -viite (Chart Gallery)](https://developers.google.com/chart/interactive/docs/gallery).

## Datan asettaminen {#setting-data}

Datat visualisoiminen `GoogleChart`-kaaviossa edellyttää datan oikeaa rakennetta ja asettamista. Tämä opas ohjaa sinua valmistelemaan datasi ja soveltamaan sitä kaavioihisi.

### Perusdatat valmistelu {#basic-data-setup}

Yksinkertaisin tapa määritellä data on käyttää `List<Object>`-tyyppiä, jossa jokainen rivi on lista arvoja.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Tehtävä", "Tunteja päivässä"));
data.add(Arrays.asList("Työ", 11));
data.add(Arrays.asList("Syö", 2));
data.add(Arrays.asList("Matka", 2));
data.add(Arrays.asList("Katso TV", 2));
data.add(Arrays.asList("Nuku", 7));
chart.setData(data);
```

### Kartat monimutkaisemmille rakenteille {#using-maps-for-more-complex-structures}

Monimutkaisemmille datarakenteille voit käyttää karttoja rivien esittämiseen ja muuttaa ne sitten vaadittuun muotoon.

```java
List<Object> data = new ArrayList<>();

// Otsikkorivi
data.add(Arrays.asList("Maa", "Voitto"));

// Data rivit
Map<String, Object> row1 = Map.of("Maa", "Saksa", "Voitto", 1000);
Map<String, Object> row2 = Map.of("Maa", "Yhdysvallat", "Voitto", 1170);
Map<String, Object> row3 = Map.of("Maa", "Brasilia", "Voitto", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

Kun data on valmisteltu, se voidaan asettaa GoogleChartin `setData`-metodin avulla.

<ComponentDemo 
path='/webforj/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### Datan ja vaihtoehtojen lataaminen JSON:ista {#loading-data-and-options-from-json}

Voit myös ladata dataa ja vaihtoehtoja JSON-tiedostoista käyttämällä Gsonia helpottamaan hallintaa. Tämä lähestymistapa auttaa pitämään datasi ja vaihtoehtosi järjestyksessä ja helposti päivitettävissä.

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

## Kaavion ulkoasun päivittäminen {#updating-chart-visuals}

Kaavioiden ulkoasun päivittäminen tai niiden päivittäminen datan muutosten, käyttäjien vuorovaikutuksen tai visuaalisten vaihtoehtojen säädön perusteella on yksinkertaista `redraw()`-metodin avulla. Tämä metodi varmistaa, että kaaviosi pysyvät tarkkoina ja visuaalisesti linjassa perustuvan datan tai asetusten muutosten kanssa.

Kutsu `redraw()`-metodia seuraavissa tilanteissa:

- **Datan muokkaamisen jälkeen**: Varmistaa, että kaavio heijastaa kaikki päivitykset sen tietolähteeseen.
- **Vaihtoehtojen vaihtamisen yhteydessä**: Käyttää uusia tyylittely- tai konfiguraatiomuutoksia kaavioon.
- **Reaktiivisten säätöjen vuoksi**: Säättää kaavion asettelua tai kokoa, kun säilön mitat muuttuvat, varmistaen optimaalisen esityksen kaikilla laitteilla.

<ComponentDemo 
path='/webforj/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='/css/googlecharts/chartRedraw.css'
height='650px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## Kaavioiden vienti kuvina {#exporting-charts-as-images}

`getImageUri()`-metodi tarjoaa tavan viedä Google Charts -kaaviosi base64-koodattuina PNG-kuvina. Tämä metodi on erityisen hyödyllinen kaavioiden jakamisessa verkkoympäristön ulkopuolella, niiden upottamiseen sähköposteihin tai asiakirjoihin tai yksinkertaisesti arkistointitarkoituksiin.

Kutsu `getImageUri()`-metodia kaavio-instanssillesi sen jälkeen, kun kaavio on täysin renderöity. Tyypillisesti tätä metodia käytetään "ready" -tapahtumakuuntelijassa varmistaen, että kaavio on valmis vientiin:

```java
chart.addReadyListener(e -> {
    String imageUri = chart.getImageUri();
    // Nyt voit käyttää imageUria, esimerkiksi img-tagin src-attribuutin yhtenä
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

`GoogleChartSelectedEvent` laukaisee aina, kun käyttäjä valitsee datapisteen tai segmentin Google Chart -komponentissa. Tämä tapahtuma mahdollistaa vuorovaikutuksen valitun kaaviodatan kanssa, tarjoten tietoja siitä, mitä on valittu. Tapahtumaa voidaan kuunnella käyttämällä `addSelectedListener()`-metodia `GoogleChart`-instanssilla.

`GoogleChartSelectedEvent` on hyödyllinen sovelluksissa, joissa käyttäjän vuorovaikutus kaavion kanssa on tarpeen.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Lisää valittukuuntelija kaavioon
chart.addSelectedListener(event -> {
    // Hanki valinta
    List<Object> selection = chart.getSelection();
    
    // Käsittele valittua tapahtumaa
    if (!selection.isEmpty()) {
        System.out.println("Valittu rivi: " + selection.get(0));
        // Lisäkäsittely valinnan rivin/sarakkeen mukaan
    }
});
```

### Payload {#payload}
`GoogleChartSelectedEvent` tarjoaa pääsyn valintadataan, joka voidaan saada käyttämällä `getSelection()`-metodia kaavio-objektilta. Tämä metodi palauttaa listan objekteista, joissa jokaisella objektilla on seuraavat ominaisuudet:

- **row**: Valitun rivin indeksi kaavion datataulukossa.
- **column**: Datan taulukon sarakkeen indeksi, mikä on valinnainen ja koskee kaavioita, jotka sallivat yksittäisten solujen valinnan, kuten taulukko-kaavio.

Pie- tai pylväskaavioissa annetaan tyypillisesti vain `row`, joka osoittaa valitun datapisteen.

Tässä esimerkki payloadista:
```java
[
  {
    "row": 3,  // Valitun rivin indeksi datassa
    "column": 2  // (Valinnainen) Valitun sarakkeen indeksi
  }
]
```

:::info Useiden datapisteiden valinta
Jos käyttäjä valitsee useita datapisteitä, `getSelection()`-metodi palauttaa taulukon objekteista, joista jokainen edustaa valittua elementtiä. Payload voi vaihdella kaaviotyypin ja käyttäjän suorittaman vuorovaikutuksen mukaan.
:::
