---
title: Communication
sidebar_position: 3
_i18n_hash: 06bf57e08ee82a4970539b73215c1540
---
`WebswingConnector` tarjoaa kaksisuuntaisen viestinnän webforJ-sovelluksesi ja upotetun Swing-sovelluksen välillä. Tämä mahdollistaa komentojen lähettämisen Swing-sovellukseen ja ilmoitusten vastaanottamisen sen sisällä tapahtuvista tapahtumista.

## Toimintojen lähettäminen Swingiin {#sending-actions-to-swing}

`performAction()`-metodi mahdollistaa webforJ-sovelluksesi aktivoinnin Swing-sovelluksessa. Tämä on hyödyllistä tilan synkronoinnissa, päivitysten käynnistämisessä tai Swing-sovelluksen käyttäytymisen ohjaamisessa verkkoliittymästä.

Esimerkiksi, jos Swing-sovelluksellasi on mukautettu toimintokäsittelijä tietojen päivittämiseksi:

```java
// Käynnistä päivittäminen Swing-sovelluksessa webforJ:stä
connector.performAction("refresh");
```

Voit myös lähettää tietoja yhdessä toiminnon kanssa. Swing-sovellus vastaanottaa tämän Webswing API -integraationsa kautta:

```java
// Lähetä komento tietojen kanssa webforJ:stä
connector.performAction("selectRecord", "12345");

// Lähetä binääritietoja
byte[] fileContent = Files.readAllBytes(path);
connector.performAction("uploadDocument", "invoice.pdf", new String(fileContent));
```

Toimintojen nimet ja odotetut tietomuodot määritellään Swing-sovelluksesi toteutuksen mukaan.

## Tapahtumien vastaanottaminen Swingistä {#receiving-events-from-swing}

Liitin lähettää kolmen tyyppisiä tapahtumia, jotka ilmoittavat webforJ-sovelluksellesi Swing-sovelluksen tilasta ja toiminnoista.

### Elinkaaritapahtumat {#lifecycle-events}

**alkamis tapahtuma** laukaisee, kun Webswing-yhteys on muodostettu ja valmis viestintään:

```java
connector.onInitialize(event -> {
  // Yhteys muodostettu
  connector.getInstanceId().ifPresent(id ->
      console.log("Yhdistetty Webswing-instanssiin: " + id)
  );
});
```

**aloitus tapahtuma** laukaisee, kun Swing-sovellus on täysin ladattu ja käynnissä:

```java
connector.onStart(event -> {
  // Swing-sovellus on nyt näkyvissä ja vuorovaikutteinen
  console.log("Sovellus valmis käyttäjän vuorovaikutukseen");
});
```

### Mukautetut toimintotapahtumat {#custom-action-events}

Kun Swing-sovelluksesi lähettää mukautettuja toimintoja takaisin verkkoliittymään käyttäen [Webswing Java API](https://www.webswing.org/docs/25.1/integrate/api), nämä vastaanotetaan toimintotapahtumina:

```java
connector.onAction(event -> {
  String actionName = event.getActionName();

  switch(actionName) {
      case "dataUpdated":
        event.getActionData().ifPresent(data -> {
            // Käsittele päivitysilmoitus
            updateWebInterface(data);
        });
        break;

      case "fileReady":
        event.getActionBinaryData().ifPresent(data -> {
            // Binääritiedot
            saveFile(fileData);
        });
        break;
  }
});
```
