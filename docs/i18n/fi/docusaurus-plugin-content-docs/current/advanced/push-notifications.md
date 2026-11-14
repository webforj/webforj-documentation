---
sidebar_position: 39
title: Push-ilmoitukset
description: >-
  Use the Push class, PushSender, and PushMessage to subscribe browsers and send
  notifications from the server, even when the app isn't open.
_i18n_hash: be5e6f5947bf38824dde0289d6415971
---
<DocChip chip='since' label='26.02' />
<JavadocLink type="push" location="com/webforj/push/Push" top='true'/>

Push-ilmoitukset voivat tavoittaa käyttäjät silloinkin, kun sovellus ei ole avoinna. Selain tilaa ilmoitukset kerran, sovellus tallentaa tilauksen ja palvelin käyttää sitä ilmoitusten toimittamiseen tapahtuman sattuessa. <JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink> hallinnoi selaimessa tilaamista ja tilauksen perumista. Palvelimella <JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> lähettää <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink>-viestin tallennettuun tilaukseen.

<!-- INTRO_END -->

<VideoPlayer video="push-notifications/push" width={1920} height={1072} title="Push notifications" />

## Asennus ja edellytykset {#setup-and-prerequisites}

Push-ilmoitukset toimitetaan erillisen moduulin kautta. Lisää se sovellukseesi:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-push</artifactId>
</dependency>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
dependencies {
  implementation 'com.webforj:webforj-push'
}
```

</TabItem>
</Tabs>

Push-ilmoitukset edellyttävät seuraavia:

- Servlet-käyttöönottoa, esimerkiksi Jettyä, Spring Bootia tai WAR-tiedostoa.
- Alla luotavaa avainparia, jota käyttöönotto käyttää ilmoitusten allekirjoittamiseen.
- Suojattua alkuperää. Selaimet hylkäävät tilaukset, jotka tarjotaan muualta kuin `https`-yhteydestä. Kehityksen aikana poikkeuksena on `localhost`.

:::info Suojatut alkuperät
<!-- vale off -->
Lisätietoja suojatuista konteksteista ja niiden merkityksestä on MDN:n [suojattuja konteksteja käsittelevässä dokumentaatiossa](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

### Avainten luominen {#generating-the-keys}

Push-palvelut hyväksyvät vain niiden käyttöönottojen allekirjoittamat ilmoitukset, joihin selain on tilannut ilmoitukset. Suorita [koontilaajennus](/docs/configuration/build-plugin) kerran kutakin käyttöönottoa kohti sen avainparin luomiseksi:

<Tabs>
<TabItem value="maven" label="Maven">

```bash
mvn webforj:push-keys
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```bash
./gradlew webforjPushKeys
```

</TabItem>
</Tabs>

Komento tulostaa kolme määritysriviä. Liitä ne `application.properties`-tiedostoon ilman lainausmerkkejä tai kopioi ne sellaisinaan `webforj.conf`-tiedostoon. Korvaa aihe käyttöönoton yhteystieto-osoitteella. Osoitteen on oltava `mailto:`- tai `https://`-osoite, jonka kautta push-palvelut voivat ottaa yhteyttä ylläpitäjään.

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| Ominaisuus | Selitys |
|----------|-------------|
| `webforj.push.public-key` | Avainparin julkinen osa, jota käyttöönotto käyttää ilmoitusten allekirjoittamiseen |
| `webforj.push.private-key` | Avainparin yksityinen osa. Kuten muutkin salaisuudet, pidä se poissa versionhallinnasta |
| `webforj.push.subject` | Käyttöönoton yhteystieto-osoite. Sen on oltava `mailto:`- tai `https://`-osoite, jonka kautta push-palvelut voivat tavoittaa ylläpitäjän |

Sovellus lukee nämä ominaisuudet käynnistyksen yhteydessä. Jos määritys sisältää vain osan niistä, käynnistys epäonnistuu ja ilmoittaa puuttuvat ominaisuudet.

:::warning Avainten vaihtaminen
Kukin selain tilaa ilmoitukset yhdellä avainparilla. Jos avaimet muuttuvat, push-palvelu hylkää olemassa olevat tilaukset. Seuraava `subscribe()`-kutsu kussakin selaimessa korvaa tilauksen.
:::

## Miten se toimii {#how-it-works}

Prosessissa on kolme vaihetta:

1. **Tilaaminen.** Näkymästä kutsuttu `Push.getCurrent().subscribe()` pyytää käyttäjän lupaa ja palauttaa `PushSubscription`-olion, joka yksilöi selaimen osoitteen.
2. **Tallentaminen.** Sovellus tallentaa tilauksen siihen liittyvine tietoineen ja yhdistää sen vastaavaan käyttäjään.
3. **Lähettäminen.** Myöhemmin mistä tahansa säikeestä kutsuttu `PushSender.send(subscription, message)` välittää viestin selaimen toimittajan push-palveluun. Palvelu näyttää ilmoituksen riippumatta siitä, onko sovellus avoinna.

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("Order shipped").setUrl("/orders/42").build());
```

Seuraavissa osioissa kerrotaan, mitä selain näyttää ja miten kunkin vaiheen virheitä käsitellään.

## Instanssi {#instance}

Hae nykyisen ympäristön push-instanssi:

```java
import com.webforj.push.Push;

Push push = Push.getCurrent();

if (Push.isPresent()) {
  // ...
}

Push.ifPresent(p -> {
  // ...
});
```

## Selaimen tilaaminen {#subscribing-the-browser}

Kutsu `subscribe()` käyttäjän toiminnon, kuten ”Ota ilmoitukset käyttöön” -painikkeen napsautuksen, seurauksena. Palautettu <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> valmistuu selaimen <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink>-tilauksella. Jos selain ei voi tilata ilmoituksia, tulos valmistuu poikkeuksellisesti ja palauttaa <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink>-poikkeuksen.

```java
PendingResult<PushSubscription> request = Push.getCurrent().subscribe();
request.thenAccept(subscription -> {
  subscriptions.save(subscription);
});
request.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable.getCause();
  PushStatus status = error.getStatus();
  String message = error.getMessage();

  return null;
});
```

Jos selain on jo tilannut ilmoitukset, uusi `subscribe()`-kutsu palauttaa olemassa olevan tilauksen. Voit siis kutsua sitä turvallisesti jokaisella käyntikerralla.

:::info Selaimen lupa
Ensimmäinen `subscribe()`-kutsu pyytää käyttäjältä luvan. Selaimen näyttämä kehotus ei ole osa sovelluksen käyttöliittymää. Koska selaimet näyttävät kehotuksen vain käyttäjän toiminnon seurauksena, kutsu `subscribe()` napsautuksen kuuntelijasta näkymän konstruktorin sijaan.

Jos käyttäjä estää kehotuksen, sovellus ei voi pyytää lupaa uudelleen kyseiselle alkuperälle.
:::

### Tilausten tallentaminen {#storing-subscriptions}

Tilaus edustaa yhden selaimen osoitetta, ja se kuuluu tallentaa palvelimelle. Tallenna se sovelluksen tietojen yhteyteen käyttäen avaimena sen päätepistettä. Sisällytä mukaan kaikki tiedot, joita sovellus tarvitsee sopivien selainten valitsemiseen myöhemmin, kuten käyttäjä, johon tilaus liittyy. Kukin tilaus sisältää kolme tekstiarvoa:

| Arvo | Merkitys |
|-------|---------|
| `getEndpoint()` | Selaimen toimittajan push-palvelun määrittämä toimitus-URL |
| `getP256dh()` | Selaimen julkinen avain |
| `getAuth()` | Selaimen todennussalaisuus |

Käyttäjällä, joka tilaa ilmoitukset kahdella selaimella, on kaksi tilausta. Poista tilaus, kun sen selain peruu tilauksen tai kun lähetyksen yhteydessä ilmoitetaan, että se on vanhentunut. Katso [Virhetilanne](#failure-status).

### Tilauksen palauttaminen {#restoring-a-subscription}

`getSubscription()` palauttaa selaimen nykyisen tilauksen tai tyhjän tuloksen, jos tilausta ei ole. Käytä sitä palvelimella olevan kopion synkronoimiseen esimerkiksi sen jälkeen, kun sovelluksen tallennustila on nollattu:

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

<JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink>-luokan kautta `getPermission()` ilmoittaa, onko käyttäjä antanut tai evännyt luvan vai eikö hän ole vielä vastannut ilmoituskehotukseen. Käytä tulosta piilottaaksesi ”Ota ilmoitukset käyttöön” -painikkeen silloin, kun sen napsauttamisella ei olisi vaikutusta.

### Tilauksen peruuttaminen {#unsubscribing}

`unsubscribe()` peruuttaa selaimen tilauksen. Se valmistuu poistetulla tilauksella, jotta sovellus voi poistaa tallennetun kopion, tai tyhjällä tuloksella, jos selaimella ei ollut tilausta.

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## Ilmoitusten lähettäminen {#sending-notifications}

<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> lähettää <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink>-viestin tallennettuun tilaukseen. Se allekirjoittaa viestin käyttöönoton avaimilla ja välittää sen selaimen toimittajan push-palveluun. Palvelu herättää selaimen ja näyttää ilmoituksen. Koska toiminto ei koskaan estä kutsuvaa säiettä, voit kutsua sitä napsautuksen kuuntelijasta, ajastetusta työstä tai pyyntökäsittelijästä.

Kun ominaisuudet on määritetty, lähettäjä on saatavilla beanina, jonka voit injektoida näkymiin, palveluihin ja ajastettuihin töihin. Voit korvata sen määrittelemällä oman `PushSender`-beanin.

```java
@Route("/orders")
public class OrdersView extends Composite<FlexLayout> {

  public OrdersView(PushSender sender, PushSubscriptions subscriptions) {
    // ...
  }
}
```

Ilman Springiä `new PushSender()` lukee avaimet sovelluksen määrityksistä. Luo lähettäjä sovelluksen säikeessä, joko näkymässä tai `App.run()`-metodissa, ja käytä sitä sen jälkeen mistä tahansa säikeestä. Kaikki lähettäjät jakavat saman push-palveluyhteyksien yhteyspoolin, joten niiden luomisesta tarpeen mukaan ei aiheudu lisäkustannuksia.

Jos ilmoitukset on lähetettävä myöhemmin tai käyttäjän poistuttua, käytä palvelimella ajastinta, kuten Springin `TaskScheduler`-luokkaa. Älä käytä sivun ajastinta, kuten `Interval`-luokkaa, sillä se pysähtyy välilehden sulkeutuessa.

### Viestin kokoaminen {#composing-a-message}

Luo viesti sen otsikolla ja määritä sitten kaikki muut asetukset rakentajassa:

```java
PushMessage message = PushMessage.create("Order shipped")
    .setBody("Order #42 is on its way")
    .setIcon("icons://icon-192x192.png")
    .setUrl("/orders/42")
    .setActions(List.of(new PushAction("track", "Track", "/orders/42/tracking")))
    .build();

PendingResult<Void> sent = sender.send(subscription, message);
sent.thenAccept(v -> status.setText("Sent"));
sent.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  status.setText(error.getStatus() + ": " + error.getMessage());

  return null;
});
```

`send()` palauttaa välittömästi. <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> valmistuu, kun push-palvelu hyväksyy viestin, tai poikkeuksellisesti, jos palvelu ei hyväksy sitä. Jos `send()`-kutsu tehdään sovelluksen säikeessä, esimerkiksi kuuntelijasta, sen takaisinkutsut suoritetaan kyseisessä säikeessä ja niillä voi päivittää komponentteja. Jos `send()`-kutsun tehnyt istunto päättyy ennen vastauksen saapumista, takaisinkutsuja ei suoriteta, mutta ilmoitus toimitetaan silti.

Lähetys odottaa push-palvelun vastausta enintään 30 sekuntia ja epäonnistuu sen jälkeen `UNREACHABLE`-tilaan. Voit muuttaa kunkin lähettäjän aikakatkaisua `setTimeout(Duration)`-metodilla.

| Asetus | Vaikutus |
|--------|--------|
| `setBody` | Asettaa otsikon alapuolella näytettävän tekstin |
| `setIcon` | Asettaa ilmoituksen yhteydessä näytettävän kuvan. Hyväksyy absoluuttiset URL-osoitteet sekä `icons://`- ja `ws://`-protokollat. Katso [Resurssit](/docs/managing-resources/assets-protocols). Ei hyväksy `context://`-protokollaa, koska push-palvelut rajoittavat viestin koon 4 kilotavuun |
| `setUrl` | Asettaa sivun, joka avataan, kun käyttäjä napsauttaa ilmoitusta. Suhteelliset URL-osoitteet ratkaistaan sovelluksen juurta vasten. Jos URL-osoitetta ei aseteta, sovelluksen juuri avataan |
| `setActions` | Asettaa ilmoituksessa näytettävät painikkeet sekä kullekin painikkeelle erillisen URL-osoitteen. Katso [Selainyhteensopivuus](#browser-support) |
| `setTag` | Asettaa yksilöivän tunnisteen. Jos näytetyllä ilmoituksella on sama tunniste, uusi ilmoitus korvaa sen |
| `setSilent` | Näyttää ilmoituksen ilman ääntä tai värinää |
| `setTimeToLive` | Asettaa ajan, jonka push-palvelu säilyttää viestin offline-laitetta varten, enintään neljä viikkoa |
| `setUrgency` | Käyttää <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink>-luokkaa, jotta laite voi viivästyttää vähäisen kiireellisyyden viestejä ja säästää akkua |
| `setTopic` | Korvaa push-palvelussa yhä odottavan viestin, jos molemmilla viesteillä on sama aihe. Aiheessa voi olla enintään 32 URL-osoitteissa turvallista merkkiä |

Kun välilehdellä on jo kyseinen sivu avoinna, ilmoituksen napsauttaminen tuo sovelluksen näkyviin. Muussa tapauksessa sivu avautuu uudessa välilehdessä. Ilmoituspainikkeen napsauttaminen avaa sen URL-osoitteen samalla tavalla.

:::info Yksi ilmoitus viestiä kohden
Jokainen viesti näyttää ilmoituksen. Koska selaimet eivät herätä sivua viestille, joka ei näytä mitään, push-ilmoituksia ei voi käyttää hiljaisiin tietopäivityksiin.
:::

## Virhetilanne {#failure-status}

Kun `subscribe()`- tai `send()`-kutsu epäonnistuu, sen `PendingResult` ilmoittaa `WebforjPushException`-poikkeuksesta. <JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink> kertoo syyn:

| Tila | Milloin | Toimenpide |
|--------|------|------------|
| `PERMISSION_DENIED` | Käyttäjä on estänyt sovelluksen ilmoitukset | Kerro, mistä selainasetuksista käyttäjä voi sallia ilmoitukset |
| `UNSUPPORTED` | Selain ei tue push-ilmoituksia, sivu ei ole suojatussa kontekstissa tai sovellusta ei ole otettu käyttöön servlet-sovelluksena | Piilota ominaisuus |
| `NOT_CONFIGURED` | Vähintään yksi `webforj.push.*`-ominaisuus puuttuu tai on keskeneräinen | Luo avaimet ja määritä kaikki kolme ominaisuutta |
| `SUBSCRIPTION_EXPIRED` | Push-palvelu ei enää tunnista tilausta, koska käyttäjä perui sen tai asensi selaimen uudelleen | Poista tallennettu tilaus |
| `REJECTED` | Push-palvelu hylkäsi viestin; `getStatusCode()` sisältää sen vastauksen | Tarkista avaimet ja viestin koko |
| `UNREACHABLE` | Push-palvelu ei vastannut ennen aikakatkaisua | Yritä myöhemmin uudelleen |
| `UNKNOWN` | Tallennettu päätepiste ei ole kelvollinen URL-osoite tai tilausta tai viestiä ei voitu koodata | Tarkista tallennettu tilaus |

Poista vanhentuneet tilaukset kunkin lähetyksen yhteydessä:

```java
sender.send(subscription, message).exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
    subscriptions.delete(subscription);
  }

  return null;
});
```

:::tip Vanhentumisesta ilmoitetaan yhden viestin viiveellä
Push-palvelut poistavat tilauksia käytöstä viiveellä. Ne hyväksyvät yhä ensimmäisen viestin sen jälkeen, kun käyttäjä peruuttaa tilauksen, mutta viesti ei mene perille. Seuraavan viestin yhteydessä ilmoitetaan `SUBSCRIPTION_EXPIRED`. Hyväksytty lähetys tarkoittaa, että viesti saavutti push-palvelun, ei sitä, että käyttäjä näki sen.
:::

## Selainyhteensopivuus {#browser-support}

Kaikki tärkeimmät työpöytä- ja mobiiliselaimet näyttävät push-ilmoitukset tilauksen jälkeen. Ota huomioon seuraavat rajoitukset:

- iPhonessa ja iPadissa push-ilmoitukset toimivat vain iOS 16.4:ssä tai uudemmassa aloitusnäyttöön lisätyissä verkkosovelluksissa. Safari-välilehdessä `subscribe()` ilmoittaa `UNSUPPORTED`. Katso kohdasta [Asennettavat sovellukset](/docs/configuration/installable-apps), millainen sovellusluettelo tarvitaan.
- Safari ei näytä ilmoituspainikkeita. Se näyttää toimintopainikkeita sisältävät viestit ilman painikkeita, mutta ilmoituksen napsauttaminen avaa silti viestin URL-osoitteen.
- Androidin ja iOS:n WebView-komponentit eivät näytä ilmoituksia.

Lisätietoja selaimittain on MDN:n [showNotification-yhteensopivuustaulukossa](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility).

## Täydellinen esimerkki {#complete-example}

Seuraava näkymä tilaa selaimen ilmoitukset ja peruu tilauksen, tallentaa tilaukset muistiin ja lähettää viestin kaikille tallennetuille tilauksille. Viestin voi lähettää heti tai kahdeksan sekunnin viiveellä Springin `TaskScheduler`-luokan avulla, jolloin välilehden voi sulkea ennen ilmoituksen saapumista. Sovellusluokka ottaa ajastimen käyttöön `@EnableScheduling`-merkinnällä.

```java title="PushSubscriptions.java"
package com.example;

import com.webforj.push.PushSubscription;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class PushSubscriptions {

  private final Map<String, PushSubscription> byEndpoint = new ConcurrentHashMap<>();

  public void save(PushSubscription subscription) {
    byEndpoint.put(subscription.getEndpoint(), subscription);
  }

  public void delete(PushSubscription subscription) {
    byEndpoint.remove(subscription.getEndpoint());
  }

  public Collection<PushSubscription> findAll() {
    return byEndpoint.values();
  }
}
```

<!-- vale off -->

<ExpandableCode title="PushView.java" language="java" startLine={40} endLine={73}>

```java
package com.example;

import com.webforj.PendingResult;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.push.Push;
import com.webforj.push.PushAction;
import com.webforj.push.PushMessage;
import com.webforj.push.PushSender;
import com.webforj.push.PushStatus;
import com.webforj.push.PushSubscription;
import com.webforj.push.exception.WebforjPushException;
import com.webforj.router.annotation.Route;
import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.scheduling.TaskScheduler;

@Route("/push")
public class PushView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph status = new Paragraph("Checking subscription…");
  private final TextField message = new TextField("Message", "Order #42 is on its way");
  private final Button subscribe =
      new Button("Enable notifications", ButtonTheme.PRIMARY);
  private final Button unsubscribe = new Button("Disable notifications");
  private final Button sendNow = new Button("Send now");
  private final Button sendLater = new Button("Send in 8 seconds");

  public PushView(PushSubscriptions subscriptions, PushSender sender, TaskScheduler scheduler) {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    subscribe.onClick(ev -> Push.getCurrent().subscribe()
        .thenAccept(subscription -> {
          subscriptions.save(subscription);
          status.setText("Subscribed");
        })
        .exceptionally(throwable -> {
          WebforjPushException error = (WebforjPushException) throwable.getCause();
          status.setText(error.getStatus() == PushStatus.PERMISSION_DENIED
              ? "Notifications are blocked in this browser"
              : error.getMessage());

          return null;
        }));

    unsubscribe.onClick(ev -> Push.getCurrent().unsubscribe().thenAccept(removed -> {
      removed.ifPresent(subscriptions::delete);
      status.setText(removed.isPresent() ? "Unsubscribed" : "There was no subscription");
    }));

    sendNow.onClick(ev -> sendToAll(subscriptions, sender, message.getValue(), status::setText));

    sendLater.onClick(ev -> {
      String text = message.getValue();
      status.setText("Sending in 8 seconds, close the tab now");
      scheduler.schedule(() -> sendToAll(subscriptions, sender, text, outcome -> {
      }), Instant.now().plusSeconds(8));
    });

    Push.getCurrent().getSubscription().thenAccept(existing -> {
      existing.ifPresent(subscriptions::save);
      status.setText(existing.isPresent() ? "Subscribed" : "Not subscribed");
    });

    self.add(status, message, subscribe, unsubscribe, sendNow, sendLater);
  }

  private static void sendToAll(PushSubscriptions subscriptions, PushSender sender, String text,
      Consumer<String> report) {
    report.accept("Sending to " + subscriptions.findAll().size() + " subscriptions");

    for (PushSubscription subscription : subscriptions.findAll()) {
      PendingResult<Void> sent = sender.send(subscription, PushMessage.create("Orders")
          .setBody(text)
          .setIcon("icons://icon-192x192.png")
          .setUrl("/push")
          .setActions(List.of(new PushAction("home", "Open home", "/")))
          .build());
      sent.thenAccept(v -> report.accept("Delivered"));
      sent.exceptionally(throwable -> {
        WebforjPushException error = (WebforjPushException) throwable;
        if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
          subscriptions.delete(subscription);
          report.accept("A subscription expired and was removed");
        } else {
          report.accept(error.getMessage());
        }

        return null;
      });
    }
  }
}
```

</ExpandableCode>

<!-- vale on -->
