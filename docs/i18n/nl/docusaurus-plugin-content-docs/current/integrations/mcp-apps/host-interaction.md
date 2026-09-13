---
title: Werken met de MCP-client
sidebar_position: 25
description: Connect a rendered webforJ view to its MCP client.
_i18n_hash: 082797b568bd8f308b625306c524d7ef
---
Een MCP-app hoeft niet elke interactie binnen zijn ingebedde weergave te houden. Het kan informatie naar het gesprek sturen, het model op de hoogte houden naarmate de gebruiker de UI wijzigt, of de cliënt vragen om iets buiten het kader af te handelen.

Dezelfde route kan ook in een normale browser worden geopend. Begin elke interactie met de cliënt door te controleren of er een MCP-host aanwezig is.

## Zet het gesprek voort vanuit de weergave {#send-a-message}

Overweeg een inventarisapp waarin de gebruiker een magazijn selecteert en vervolgens de AI vraagt om de voorraad te bekijken. De knop kan dat verzoek als het volgende gebruikersbericht verzenden:

```java
Paragraph warehouse = new Paragraph("Magazijn: BER");
Button review = new Button("Bekijk voorraad");

review.addClickListener(event -> McpHost.ifPresent(host ->
    host.sendMessage("Bekijk de huidige voorraad voor " + warehouse.getText())));
```

`McpHost.ifPresent` voert de callback alleen uit wanneer de weergave is verbonden met een MCP-cliënte. In een normale browser heeft de knop geen hostzijde-effect.

## Houd het model op de hoogte {#update-model-context}

Niet elke wijziging in de UI moet een nieuw bericht creëren. Wanneer het geselecteerde magazijn of de filters veranderen, kan de app de context die het bijdraagt aan het model vervangen:

```java
McpHost host = McpHost.getCurrent();
if (host != null) {
  PendingResult<Void> result = host.updateModelContext(
      Map.of("warehouse", warehouse.getText(), "source", "inventaris-app"));

  result.exceptionally(error -> {
    warehouse.setText("Delen mislukt: " + error.getMessage());
    return null;
  });
}
```

De bijgewerkte status wordt beschikbaar voor latere modelreacties zonder een zichtbaar bericht aan het gesprek toe te voegen. Host-aanroepen zijn asynchroon en retourneren een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, dus behandel voltooiing of falen zonder de webforJ UI-thread te blokkeren.

## Verlaat de ingebedde weergave {#leave-the-view}

Sommige werkzaamheden behoren buiten het app-kader. Gebruik `openLink` wanneer de gebruiker verder moet gaan op een externe pagina. Gebruik `requestDisplayMode` wanneer de huidige inhoud een andere presentatie nodig heeft, zoals fullscreen voor een gedetailleerde tabel. De cliënt beslist of hij aan een van beide verzoeken kan voldoen.

:::tip[Houd de browserervaring compleet]

Beschouw hostintegratie als een verbetering. De route moet nuttig blijven wanneer deze in een browser wordt uitgevoerd of wanneer de verbonden cliënt een gevraagd vermogen niet ondersteunt.
:::

## Volg wijzigingen vanuit het gesprek {#host-events}

De cliënt kan doorgaan met werken met de app nadat deze is gerenderd. Bijvoorbeeld, de weergave kan een laadstatus wissen wanneer een hulpprogramma-aanroep wordt geannuleerd en verhelderende tekst vernieuwen wanneer de gesprekscontext verandert:

```java
McpHost.ifPresent(host -> {
  host.onToolCancelled(event ->
      warehouse.setText("Het voorraadverzoek is geannuleerd."));
  host.onHostContextChanged(event ->
      warehouse.setText("De gesprekscontext is veranderd."));
});
```

Registreer alleen de listeners die de weergave nodig heeft en ga er niet vanuit dat elke cliënt elk evenement verzendt. Zie de `McpHost` Javadocs voor de beschikbare verzoeken, evenementen, payloads en methodehandtekeningen.
