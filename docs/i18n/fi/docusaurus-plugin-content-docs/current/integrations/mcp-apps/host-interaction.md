---
title: MCP-asiakkaan käyttö
sidebar_position: 25
description: Connect a rendered webforJ view to its MCP client.
_i18n_hash: 082797b568bd8f308b625306c524d7ef
---
MCP-sovellus ei välttämättä tarvitse pitää jokaista vuorovaikutusta sisällä upotetussa näkymässään. Se voi lähettää tietoa keskusteluun, pitää mallin ajan tasalla, kun käyttäjä muuttaa käyttöliittymää, tai pyytää asiakasta käsittelemään jotain kehykset ulkopuolella.

Sama reitti voidaan myös avata normaalissa selaimessa. Aloita jokainen asiakasvuorovaikutus tarkistamalla, onko MCP-isäntä läsnä.

## Jatka keskustelua näkymästä {#send-a-message}

Kuvittele varastonhallintasovellus, jossa käyttäjä valitsee varaston ja kysyy sitten tekoälyltä sen varastotilannetta. Nappi voi lähettää tämän pyynnön seuraavana käyttäjäviestinä:

```java
Paragraph warehouse = new Paragraph("Varasto: BER");
Button review = new Button("Tarkista varasto");

review.addClickListener(event -> McpHost.ifPresent(host ->
    host.sendMessage("Tarkista nykyinen varasto " + warehouse.getText())));
```

`McpHost.ifPresent` suorittaa palautteen vain silloin, kun näkymä on yhdistetty MCP-asiakkaaseen. Normaalissa selaimessa napilla ei ole isäntäpuoleista vaikutusta.

## Pidä malli ajan tasalla {#update-model-context}

Kaikkia käyttöliittymän muutoksia ei pitäisi luoda toista viestiä. Kun valittu varasto tai suodattimet muuttuvat, sovellus voi korvata sen kontekstin, jota se lisää mallille:

```java
McpHost host = McpHost.getCurrent();
if (host != null) {
  PendingResult<Void> result = host.updateModelContext(
      Map.of("varasto", warehouse.getText(), "lähde", "inventory-app"));

  result.exceptionally(error -> {
    warehouse.setText("Jakaminen epäonnistui: " + error.getMessage());
    return null;
  });
}
```

Päivitetty tila tulee saataville myöhempiä mallivastauksia varten ilman, että näkyvää viestiä lisätään keskusteluun. Isäntäpuolen kutsut ovat asynkronisia ja palauttavat <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joten käsittele valmistumista tai epäonnistumista estämättä webforJ:n käyttöliittymäkappaletta.

## Poistu upotetusta näkymästä {#leave-the-view}

Jotkin työt kuuluvat sovelluksen kehysten ulkopuolelle. Käytä `openLink`-komentoa, kun käyttäjän on jatkettava ulkoisella sivulla. Käytä `requestDisplayMode`-komentoa, kun nykyinen sisältö tarvitsee erilaisen esitystavan, kuten koko näytön yksityiskohtaiselle taulukolle. Asiakas päättää, voiko se tyydyttää joko pyynnön.

:::tip[Pidä selainkokemus täydellisenä]

Käsittele isäntäintegraatiota parannuksena. Reitin tulisi pysyä hyödyllisenä, kun sitä suoritetaan selaimessa tai kun yhdistetty asiakas ei tue pyydettyä ominaisuutta.
:::

## Seuraa keskustelun muutoksia {#host-events}

Asiakas voi jatkaa sovelluksen käyttöä sen renderöinnin jälkeen. Esimerkiksi näkymä voi poistaa lataustilan, kun työkalu kutsu perutaan, ja päivittää selittävän tekstin, kun keskustelun konteksti muuttuu:

```java
McpHost.ifPresent(host -> {
  host.onToolCancelled(event ->
      warehouse.setText("Varastopyyntö peruttiin."));
  host.onHostContextChanged(event ->
      warehouse.setText("Keskustelun konteksti muuttui."));
});
```

Rekisteröi vain kuuntelijat, joita näkymä tarvitsee, äläkä oleta, että jokainen asiakas lähettää jokaisen tapahtuman. Katso `McpHost` Javadocs saatavilla olevista pyynnöistä, tapahtumista, kuormista ja menetelmän allekirjoituksista.
