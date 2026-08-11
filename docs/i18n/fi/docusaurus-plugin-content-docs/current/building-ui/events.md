---
sidebar_position: 7
title: Events
description: >-
  Listen for component events, read the event payload, configure element events,
  and dispatch your own custom events with the EventDispatcher.
slug: events
sidebar_class_name: new-content
_i18n_hash: 5ceda90a316ff6a1528a686565011f88
---
Komponentit, olivatpa ne mukautettuja tai osa kehystä, tukevat tapahtumankäsittelyä. Voit lisätä tapahtumakuuntelijoita kaapataksesi erilaisia tapahtumia, kuten käyttäjän vuorovaikutusta, tilan muutoksia tai omia määrittelemäsi tapahtumia. Nämä kuuntelijat antavat sinun laukaista tiettyä käyttäytymistä sen perusteella, mitä sovelluksessasi tapahtuu.

## Lisäämällä tapahtumia {#adding-events}

Lisää kuuntelija komponenttiin tapahtumaspesifisellä menetelmällä. Kukin komponentti tarjoaa parin: `addXxxListener`-menetelmän ja useimmissa tapauksissa lyhyemmän `on`-aliasin, joka tekee saman asian. Esimerkiksi `Button` tarjoaa sekä `addClickListener` että `onClick`.

Voit välittää kuuntelijan lambda-funktiona:

```java
Button button = new Button("Tallenna");
button.onClick(event -> {
  // Käsittele klikkaus
});
```

tai metodiviittauksena:

```java
button.onClick(this::handleSave);
```

Kaikilla tapahtumilla ei ole `on`-aliasia. Arvomuutokset, esimerkiksi, lisätään vain `addValueChangeListener`-menetelmällä:

```java
TextField name = new TextField("Nimi");
name.addValueChangeListener(event -> {
  String value = event.getValue();
  // Käsittele uusi arvo
});
```

## Tapahtuman poistaminen {#removing-an-event}

Kuuntelijan lisääminen palauttaa `ListenerRegistration`-objektin. Säilytä se, jotta voit poistaa kuuntelijan myöhemmin.

```java
ListenerRegistration<ButtonClickEvent> registration =
    button.onClick(event -> {
      // Käsittele klikkaus
    });

// Myöhemmin, kun kuuntelijaa ei enää tarvita
registration.remove();
```

## Käyttäen tapahtuman payloadia {#using-event-payload}

Tapahtumat kuljettavat mukana payloadin, joka sisältää tietoa siitä, mitä tapahtui. Tämän payloadin lukeminen käsittelijässä antaa sinulle olennaiset tiedot ilman tarvetta pyytää niitä asiakkaalta.

Esimerkiksi `ModifyEvent` `TextField`-komponentista sisältää kentän nykyisen tekstin. Voit kysyä komponentilta:

```java
TextField field = new TextField("Haku");
field.onModify(event -> {
  String text = field.getText();
  // Käytä tekstiä
});
```

Sama arvo on jo tapahtumassa, joten sen lukeminen payloadista välttää paluun komponenttiin:

```java
field.onModify(event -> {
  String text = event.getText();
  // Käytä tekstiä
});
```

Lue payloadista aina, kun tapahtuma paljastaa tarvitsemiasi tietoja. Lisätietoa siitä, miksi tämä on tärkeää, katso [Asiakas/Palvelin Vuorovaikutus](../architecture/client-server).

## Elementtien tapahtumien konfigurointi {#configuring-element-events}

Kun työskentelet suoraan <JavadocLink type="foundation" location="com/webforj/component/element/Element" code='true'>Element</JavadocLink>n kanssa, sen tapahtumat konfiguroidaan <JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" code='true'>ElementEventOptions</JavadocLink> avulla. Tämä hallitsee, mitä tietoja tapahtuma kuljettaa, syttyykö se lainkaan ja miten usein, kaikki arvioituna asiakkaalla ennen kuin tapahtuma saavuttaa palvelimen.

### Tapahtumatiedot {#event-data}

Tapahtumatiedot liittävät asiakaspuolen arvoja tapahtumaan, joten tiedot ovat saatavilla palvelimella ilman lisäpyyntöä. Lisäät sen `addData()`-menetelmällä, antaen jokaiselle merkinnälle avaimen ja JavaScript-ilmaisu, joka tuottaa arvon.

Kaksi muuttujaa on käytettävissä näissä ilmauksissa: `event`, asiakaspuolen tapahtumaobjekti, ja `component`, elementti, johon kuuntelija on liitetty.

```java
ElementEventOptions options = new ElementEventOptions()
    .addData("value", "component.value")
    .addData("key", "event.key");
```

Palvelimella kukin arvo luetaan tapahtumasta sen avaimen avulla.

### JavaScriptin suorittaminen {#executing-javascript}

`setCode()` ajaa JavaScript-koodinpätkän asiakkaalla ennen kuin tapahtuma syttyy. Tämä on hyödyllistä tapahtumatietojen valmistelussa tai reagoimisessa asiakkaalla ilman palvelimen pyynnön käsittelyä.

```java
ElementEventOptions options = new ElementEventOptions()
    .setCode("event.target.value = event.target.value.trim();");
```

### Tapahtumien suodattaminen {#filtering-events}

`setFilter()` asettaa JavaScript-ilmauksen, joka päättää, syttyykö tapahtuma. Jos se arvioidaan epätodeksi, tapahtuma ei koskaan saavuta palvelinta. Tämä on hyödyllistä, kun sinua kiinnostaa vain tapahtuma tietyissä olosuhteissa, kuten syötteen ollessa riittävän pitkä.

```java
ElementEventOptions options = new ElementEventOptions()
    .setFilter("event.target.value.length > 2");
```

### Debouncing ja throttling {#debouncing-and-throttling}

Debouncing ja throttling rajoittavat tapahtumien saapumistiheyttä palvelimelle, mikä on hyödyllistä nopeissa tapahtumissa kuten kirjoittamisessa tai vierittämisessä.

Debouncing odottaa, kunnes aktiivisuus rauhoittuu, ennen kuin tapahtuma syttyy. `setDebounce()` ottaa aikakatkaisun millisekunteina ja valinnaisen <JavadocLink type="foundation" location="com/webforj/component/element/event/DebouncePhase" code='true'>DebouncePhase</JavadocLink>: `LEADING` syttyy purkauksen alussa, `TRAILING` syttyy sen päätyttyä, ja `BOTH` syttyy jokaisella reunalla. Kun vaihe jätetään mainitsematta, se oletusarvoisesti syttyy `TRAILING`.

```java
ElementEventOptions options = new ElementEventOptions()
    .setDebounce(300, DebouncePhase.TRAILING);
```

Throttling syttyy tasaisella maksiminopeudella, kun aktiivisuus jatkuu. `setThrottle()` ottaa aikakatkaisun millisekunteina.

```java
ElementEventOptions options = new ElementEventOptions()
    .setThrottle(300);
```

Tapahtuma käyttää jompaa kumpaa. Debouncen asettaminen tyhjentää kaiken throttlen samat optioissa, ja throttlen asettaminen tyhjentää kaiken debouncen.

### Annotaatiot {#annotations}

Elementtien tapahtumavalinnat voidaan myös asettaa annotaatioiden avulla, mikä on tiiviimpi tapa konfiguroida kuuntelija. `@EventOptions`-annotaatio pitää sisällään tietokirjamerkinnät, sekä suodatin-, debounce- ja throttle-asetukset.

```java
@EventOptions(
    data = {@EventData(key = "value", exp = "component.value")},
    debounce = @DebounceSettings(value = 200))
```

Kun myös toimitat `ElementEventOptions`-objektin kutsupaikassa, sen tiedot yhdistyvät annotaation tietojen kanssa, ja sen koodi, suodatin, debounce ja throttle peittävät annotaation.

## Omien tapahtumien lähettäminen {#dispatching-your-own-events}

Toistaiseksi käsitellyt tapahtumat tulevat komponentista, jota kuuntelet. Komponentti, jonka kirjoitat, voi julkaista omia tapahtumiaan samalla tavalla, jotta sen koodilla on mahdollisuus reagoida ilman tarvetta upota komponentin sisäosiin.

:::tip Milloin lähettää mukautettu tapahtuma
Lähetä mukautettu tapahtuma, kun komponenttisi päättää, että jotain on tapahtunut, kuten lomake ilmoittaa suoritetusta lähetyksestä tai editori ilmoittaa tallennetusta tietueesta. Asiakkaan vuorovaikutuksesta alusta lähteviä tapahtumia konfiguroidaan [elementtien tapahtumavalintojen](#configuring-element-events) avulla.
:::

Komponentit eivät tule tapahtumien lähettimen kanssa, joten komponentti, joka julkaisee omia tapahtumiaan, pitää sisällään oman <JavadocLink type="foundation" location="com/webforj/dispatcher/EventDispatcher" code='true'>EventDispatcher</JavadocLink> -objektin ja julkaisee sitä kautta.

### Tapahtuman määrittäminen {#defining-the-event}

Määritä tapahtuma luokkana, joka laajentaa `EventObject`-luokkaa. Anna lähde, tapahtumaa julkaiseva objekti, yliluokalle ja lisää pääsyfunktiot, joita kuuntelijat tarvitsevat.

```java
public class OrderSubmittedEvent extends EventObject {
  private final String orderId;
  private final double total;

  public OrderSubmittedEvent(Object source, String orderId, double total) {
    super(source);
    this.orderId = orderId;
    this.total = total;
  }

  public String getOrderId() {
    return orderId;
  }

  public double getTotal() {
    return total;
  }
}
```

Tietojen lukeminen tapahtumasta seuraa samaa logiikkaa kuin [käyttäen tapahtuman payloadia](#using-event-payload). Kuuntelijat saavat tarvitsemansa tiedot tapahtumasta sen sijaan, että kysyisivät tietoja lähteeltä myöhemmin.

### Rekisteröinti ja lähettäminen {#registering-and-dispatching}

Luo lähettimen, rekisteröi kuuntelijat tietyn tapahtumatyyppiin ja lähetä kyseisen tyypin instanssi, kun tapahtuma tapahtuu. Rekisteröinti palauttaa `ListenerRegistration`-objektin, jonka säilytät poistaaksesi kuuntelijan myöhemmin.

```java
EventDispatcher dispatcher = new EventDispatcher();

ListenerRegistration<OrderSubmittedEvent> registration =
    dispatcher.addListener(OrderSubmittedEvent.class, event -> {
      String id = event.getOrderId();
      // Käsittele tapahtuma
    });

dispatcher.dispatchEvent(new OrderSubmittedEvent(this, "ORD-1001", 49.99));
```

Jokainen tälle tapahtumatyyppille rekisteröity kuuntelija suoritetaan, kun tapahtuma lähetetään.

Komponentti, joka julkaisee tapahtuman, pitää lähettimen sisäisesti ja tarjoaa `onXxx`-menetelmän sen sijaan, että lähettimen itsensä, jotta kutsujat voivat tilata siten, että he tekevät sen samalla tavalla kuin sisäänrakennettu tapahtuma:

```java
public ListenerRegistration<OrderSubmittedEvent> onSubmit(
    EventListener<OrderSubmittedEvent> listener) {
  return dispatcher.addListener(OrderSubmittedEvent.class, listener);
}
```

### Kuuntelijoiden poistaminen {#removing-listeners}

Poista kuuntelija sen rekisteröinnin kautta tai palauttamalla kuuntelija lähettimen takaisin:

```java
registration.remove();

//TAI

dispatcher.removeListener(OrderSubmittedEvent.class, registration.getListener());
```

Poistaaksesi kaikki kuuntelijat, jotka on rekisteröity tietylle tapahtumatyypille kerralla:

```java
dispatcher.removeAllListeners(OrderSubmittedEvent.class);
```

### Muistivuotojen välttäminen {#avoiding-memory-leaks}

Lähettimen säilyttää kuuntelijansa, ja jokainen kuuntelija säilyttää sen, mihin se on liittynyt. Lambda tai sisäinen luokka sieppaa automaattisesti `this`:n yhdessä kaikkien paikallisten muuttujien kanssa, joten kuuntelijaan liittyvät objektit pysyvät saavutettavina niin kauan kuin lähetin pitää niitä.

Tämä muuttuu ongelmaksi, kun kuuntelija ylittää sen, mihin se viittaa. Jos dialogi rekisteröi kuuntelijan, joka lukee sen oman mallinsa ja sulkee ilman, että se poistaa sen, lähettimen pitää edelleen kuuntelijan, kuuntelija pitää edelleen dialogin, eikä kumpikaan voi olla roskattuna. Sovelluksessa, joka luo monia lyhytaikaisia näkymiä, pidät kuuntelijat kasautuvat tällä tavalla.

Poista kuuntelija:

- Kun se objekti, joka rekisteröi, on valmis, kuten suljettu dialogi tai näkymä, josta on siirrytty pois.
- Kun tilaus oli sidottu lyhytaikaiseen tehtävään tai kertaluonteiseen prosessiin.

Pidä palautettu `ListenerRegistration`, johon pääset käsiksi puhdistuksessa, sen sijaan, että rekisteröisit kuuntelijan, jota et voi myöhemmin poistaa. Komponentissa `onDidDestroy()` on puhdistuspaikka.
