---
title: Communication
sidebar_position: 3
_i18n_hash: 4a12006d21bb2a0bd6e82f2f0ff8fa78
---
`WebswingConnector` mahdollistaa kaksisuuntaisen viestinnän webforJ-sovelluksesi ja upotetun Swing-sovelluksen välillä. Tämä mahdollistaa komentoja lähettämisen Swing-sovellukseen ja ilmoitusten vastaanottamisen, kun tapahtumia tapahtuu sen sisällä.

## Toimintojen lähettäminen Swingille

`performAction()`-menetelmä mahdollistaa webforJ-sovelluksesi toiminnallisuuden käynnistämisen Swing-sovelluksessa. Tämä on hyödyllistä tilan synkronoinnissa, päivitysten käynnistämisessä tai Swing-sovelluksen käyttäytymisen hallitsemisessa verkkoliittymästä.

Esimerkiksi, jos Swing-sovelluksellasi on mukautettu toimintakäsittelijä tietojen päivittämiseen:

```java
// Käynnistä päivitys Swing-sovelluksessa webforJ:stä
connector.performAction("refresh");
```

Voit myös lähettää tietoja toiminnon mukana. Swing-sovellus vastaanottaa tämän Webswing API -integraation kautta:

```java
// Lähetä komento tiedolla webforJ:stä
connector.performAction("selectRecord", "12345");

// Lähetä binaaridataa
byte[] fileContent = Files.readAllBytes(path);
connector.performAction("uploadDocument", "invoice.pdf", new String(fileContent));
```

Toimintojen nimet ja odotetut tietomuodot määritellään Swing-sovelluksesi toteutuksessa.

## Tapahtumien vastaanottaminen Swingiltä

Liitin laukaisee kolme tyyppiä tapahtumia, jotka ilmoittavat webforJ-sovelluksellesi Swing-sovelluksen tilasta ja toiminnoista.

### Elinkaaritapahtumat

**initialize-tapahtuma** laukaistaan, kun Webswing-yhteys on perustettu ja valmis viestintään:

```java
connector.onInitialize(event -> {
  // Yhteys luotu
  connector.getInstanceId().ifPresent(id ->
      console.log("Yhdistetty Webswing-instanssiin: " + id)
  );
});
```

**start-tapahtuma** laukaistaan, kun Swing-sovellus on täysin latautunut ja toimii:

```java
connector.onStart(event -> {
  // Swing-sovellus on nyt näkyvissä ja vuorovaikutteinen
  console.log("Sovellus valmis käyttäjävuorovaikutukseen");
});
```

### Mukautetut toimintatapahtumat

Kun Swing-sovelluksesi lähettää mukautettuja toimintoja takaisin verkkoliittymään käyttäen [Webswing Java API](https://www.webswing.org/docs/25.1/integrate/api), näitä vastaanotetaan toimintatapahtumina:

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
            // Binaaridata
            saveFile(fileData);
        });
        break;
  }
});
```
