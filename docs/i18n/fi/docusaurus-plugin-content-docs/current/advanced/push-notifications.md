---
sidebar_position: 39
sidebar_class_name: new-content
title: Push Notifications
description: >-
  Use the Push class, PushSender, and PushMessage to subscribe browsers and send
  notifications from the server, even when the app isn't open.
_i18n_hash: 3e487693f1f11322be81f1c5a93c1ad0
---
<DocChip chip='since' label='26.02' />
<JavadocLink type="push" location="com/webforj/push/Push" top='true'/>

Push-ilmoitukset voivat saavuttaa käyttäjät jopa silloin, kun sovellus ei ole avoinna. Selain tilaa sen kerran, sovellus tallentaa tilauksen ja palvelin käyttää sitä ilmoitusten toimittamiseen tapahtumien esiintyessä. <JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink> hallitsee tilausta ja peruutusta selaimessa. Palvelimella <JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> lähettää <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink>:n tallennettuun tilaukseen.

<!-- INTRO_END -->

## Asennus ja vaatimus {#setup-and-prerequisites}

Push-ilmoitukset tarjotaan erillisellä moduulilla. Lisää se sovellukseesi:

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

Push-ilmoitukset vaativat:

- Servlet-levitys, kuten Jetty, Spring Boot tai WAR-tiedosto.
- Avaipari, joka on luotu alla, jota levitys käyttää ilmoitusten allekirjoittamiseen.
- Turvallinen alkuperä. Selaimet hylkäävät tilaukset, joita tarjotaan muilla kuin `https`-protokollilla, paitsi `localhost`-osoitteesta kehityksen aikana.

:::info Turvalliset alkuperät
<!-- vale off -->
Lisätietoja turvallisista konteksteista ja niiden tärkeydestä saat [Turvalliset kontekstit MDN dokumentista](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

### Avainten luominen {#generating-the-keys}

Push-palvelut hyväksyvät vain ilmoituksia, joita on allekirjoitettu levityksellä, johon selain on tilannut. Suorita [rakennuslaajennus](/docs/configuration/build-plugin) kerran jokaiselle levitykselle avainparin luomiseksi:

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

Komentorivi tulostaa kolme konfiguraatiorivie. Liitä ne `application.properties`-tiedostoon ilman lainausmerkkejä, tai kopioi ne sellaisenaan `webforj.conf`-tiedostoon. Korvaa aiheella levityksen yhteystiedot. Sen on oltava `mailto:` tai `https://`-osoite, jota push-palvelut voivat käyttää operaattorin tavoittamiseen.

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| Ominaisuus | Selitys |
|----------|-------------|
| `webforj.push.public-key` | Avaiparin julkinen osa, jota levitys käyttää ilmoitusten allekirjoittamiseen |
| `webforj.push.private-key` | Avaiparin yksityinen osa. Kuten muutkin salaisuudet, pidä se poissa lähdekoodin hallinnasta |
| `webforj.push.subject` | Levityksen yhteystiedot. Sen on oltava `mailto:` tai `https://`-osoite, jonka kautta push-palvelut voivat tavoittaa operaattorin |

Sovellus lukee nämä ominaisuudet käynnistyksen yhteydessä. Jos konfiguraatio sisältää vain osan niistä, käynnistys epäonnistuu ja ilmoittaa puuttuvat ominaisuudet.

:::warning Avainten kiertäminen
Jokainen selain tilaavat yhden avainparin. Jos avaimet muuttuvat, push-palvelu hylkää olemassa olevat tilaukset. Seuraava `subscribe()`-kutsu jokaisessa selaimessa korvataan sen tilaus.
:::

## Miten se toimii {#how-it-works}

Prosessissa on kolme vaihetta:

1. **Tilaa.** Näkymästä `Push.getCurrent().subscribe()` pyytää käyttäjän lupaa ja palauttaa `PushSubscription`:n, joka tunnistaa selaimen osoitteen.
2. **Tallenna.** Sovellus tallentaa tilauksen sen tietojen kanssa ja yhdistää sen vastaavaan käyttäjään.
3. **Lähetä.** Myöhemmin, mistä tahansa säikeestä, `PushSender.send(subscription, message)` välittää viestin selaimen tarjoajalle push-palvelulle. Palvelu näyttää ilmoituksen riippumatta siitä, onko sovellus avoinna vai ei.

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("Tilauksen toimitus").setUrl("/tilaukset/42").build());
```

Seuraavat osat selittävät, mitä selain näyttää ja kuinka käsitellä epäonnistumisia jokaisessa vaiheessa.

## Instanssi {#instance}

Hanki push-instanssi nykyisestä ympäristöstä:

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

Kutsu `subscribe()` vastauksena käyttäjän toimintaan, kuten napsauttamalla "Ota ilmoitukset käyttöön" -painiketta. Palautettu <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> valmistuu selaimen <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink>:n kanssa. Jos selain ei voi tilata, se valmistuu poikkeuksellisesti <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink>:n kanssa.

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

Jos selain on jo tilattu, `subscribe()`-kutsuminen palauttaa olemassa olevan tilauksen. Voit siten kutsua sitä turvallisesti jokaisella vierailulla.

:::info Selaimen lupa
Ensimmäinen kutsu `subscribe()` pyytää käyttäjältä lupaa. Selain näyttää tämän kehotteen, se ei ole osa sovelluksen UI:ta. Koska selaimet näyttävät kehotteen vain käyttäjän toiminnan seurauksena, kutsu `subscribe()` napsautustapahtumasta sen sijaan, että näkymäkonstruoitteesta.

Jos käyttäjä estää kehotteen, sovellus ei voi pyytää sitä uudelleen tälle alkuperälle.
:::

### Tilauksien tallentaminen {#storing-subscriptions}

Tilaus edustaa yhden selaimen osoitetta ja kuuluu palvelimelle. Tallenna se sovelluksen tietojen kanssa, käyttäen sen päätepistettä avaimena. Sisällytä kaikki tiedot, joita sovellus tarvitsee valitakseen oikeat selaimet myöhemmin, kuten liitetty käyttäjä. Jokainen tilaus sisältää kolme tekstiarvoa:

| Arvo | Merkitys |
|-------|---------|
| `getEndpoint()` | Toimitus-URL, jonka selainpalvelun push-palvelu on määrittänyt |
| `getP256dh()` | Selaimen julkinen avain |
| `getAuth()` | Selaimen vahvistussalaisuus |

Käyttäjä, joka tilaa kahdesta selaimesta, saa kaksi tilausta. Poista tilaus, kun sen selain peruuttaa tai kun lähetys ilmoittaa, että se on vanhentunut. Katso [Epäonnistumistila](#failure-status).

### Tilauksen palauttaminen {#restoring-a-subscription}

`getSubscription()` palauttaa selaimen nykyisen tilauksen tai tyhjät tulokset, jos sellaista ei ole. Käytä sitä synkronoimaan palvelimen kopiot, esimerkiksi sen jälkeen, kun sovelluksen tallennus on palautettu:

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

Kautta <JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink>, `getPermission()` ilmoittaa, onko käyttäjä myöntänyt, evännyt tai ei ole vielä vastannut ilmoituskehotteeseen. Käytä tätä tulosta piilottaaksesi "Ota ilmoitukset käyttöön" -painikkeen, jos sen napsauttaminen ei vaikuta.

### Peruuttaminen {#unsubscribing}

`unsubscribe()` peruuttaa selaimen tilauksen. Se valmistuu poistetuilla tilauksilla, jotta sovellus voi poistaa sen tallennetun kopion tai tyhjillä tuloksilla, jos selaimella ei ollut tilausta.

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## Ilmoitusten lähettäminen {#sending-notifications}

<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> lähettää <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink>:n tallennettuun tilaukseen. Se allekirjoittaa viestin levityksen avaimilla ja välittää sen selaimen tarjoajan push-palvelulle. Tämä palvelu herättää selaimen ja näyttää ilmoituksen. Koska operaatio ei koskaan estä kutsuvaa säiettä, voit kutsua sitä napsautustapahtumasta, aikataulutetusta työstä tai pyyntö-käsittelystä.

Kun ominaisuudet on konfiguroitu, lähettäjä on saatavilla beanina, jonka voit injektoida näkymiin, palveluihin ja aikataulutettuihin työpäiviin. Jos haluat korvata sen, määritä oma `PushSender`-beanisi.

```java
@Route("/tilaukset")
public class OrdersView extends Composite<FlexLayout> {

  public OrdersView(PushSender sender, PushSubscriptions subscriptions) {
    // ...
  }
}
```

Ilman Springiä `new PushSender()` lukee avaimet sovelluksen konfiguraatiosta. Luo lähettäjä sovellus- säikeessä, joko näkymässä tai `App.run()`-menetelmässä, ja käytä sitä sitten mistä tahansa säikeestä. Kaikki lähettäjät jakavat yhden yhteyspoolin push-palveluihin, joten niiden luominen missä tahansa tarvitaan ei aiheuta kustannuksia.

Viesteille, jotka on lähetettävä myöhemmin tai käyttäjän poistuttua, käytä palvelimen aikataulutinta, kuten Springin `TaskScheduler`. Älä käytä sivuaikataulutinta, kuten `Interval`, koska se pysähtyy, kun välilehti sulkeutuu.

### Viestin kokoaminen {#composing-a-message}

Luo viesti sen otsikolla ja määritä sitten jokainen muu vaihtoehto rakennusohjelmassa:

```java
PushMessage message = PushMessage.create("Tilauksen toimitus")
    .setBody("Tilaus #42 on matkalla")
    .setIcon("icons://icon-192x192.png")
    .setUrl("/tilaukset/42")
    .setActions(List.of(new PushAction("seuraa", "Seuraa", "/tilaukset/42/seuranta")))
    .build();

PendingResult<Void> sent = sender.send(subscription, message);
sent.thenAccept(v -> status.setText("Lähetetty"));
sent.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  status.setText(error.getStatus() + ": " + error.getMessage());

  return null;
});
```

`send()` palauttaa heti. <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> valmistuu, kun push-palvelu hyväksyy viestin tai valmistuu poikkeuksellisesti, jos palvelu ei hyväksy sitä. Jos `send()`-kutsu tehdään sovellus-säikeessä, kuten kuuntelijasta, sen palautteet suoritetaan tuolla säikeellä ja voivat päivittää komponentteja. Jos sesio, joka kutsui `send()`, päättyy ennen kuin vastaus saapuu, palautteet eivät suoriteta, mutta ilmoitus toimitetaan silti.

Send-kutsu odottaa korkeintaan 30 sekuntia push-palvelulta ennen kuin se epäonnistuu `UNREACHABLE`-tilassa. Käytä `setTimeout(Duration)` muuttaaksesi aikarajaa jokaiselle lähettäjälle.

| Vaihtoehto | Vaikutus |
|--------|--------|
| `setBody` | Asettaa tekstin, joka näkyy otsikon alapuolella |
| `setIcon` | Asettaa kuvan, joka näkyy ilmoituksen mukana. Se hyväksyy absoluuttiset URL-osoitteet ja `icons://`- ja `ws://`-protokollat. Katso [Varat](/docs/managing-resources/assets-protocols). Se ei hyväksy `context://`-protokollaa, koska push-palvelut rajoittavat viestin 4 kt:n |
| `setUrl` | Asettaa sivun, joka avautuu, kun käyttäjä napsauttaa ilmoitusta. Suhteelliset URL-osoitteet ratkaistaan sovelluksen juurta vastaan. Jos URL-osoitetta ei aseteta, avautuu sovelluksen juuri |
| `setActions` | Asettaa ilmoituksessa näytettävät painikkeet, joilla on erilliset URL-osoitteet jokaiselle painikkeelle. Katso [Selaimen tuki](#browser-support) |
| `setTag` | Asettaa tunnistavan tagin. Jos ilmoitus, joka on näkyvissä, on sama tagi, uusi ilmoitus korvataan sillä |
| `setSilent` | Näyttää ilmoituksen ilman ääntä tai tärinää |
| `setTimeToLive` | Asettaa, kuinka kauan push-palvelu säilyttää viestin offline-laitteelle, enintään neljä viikkoa |
| `setUrgency` | Käyttää <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink>:a, jotta laite voi viivästyttää alhaisen kiireen viestejä ja säästää akkua |
| `setTopic` | Korvataan viesti, joka on edelleen odottamassa push-palvelussa, kun molemmat viestit sisältävät saman aiheen. Aiheilla voi olla enintään 32 merkkiä, jotka ovat turvallisia URL-osoitteissa |

Kun välilehti näyttää jo sivun, napsauttaminen ilmoituksessa keskittyy sovellukseen. Muuten sivu avautuu uuteen välilehteen. Napsauttaessa ilmoituspainiketta avautuu sen URL-osoite samalla tavalla.

:::info Yksi ilmoitus viestiä kohti
Jokainen viesti näyttää ilmoituksen. Koska selaimet eivät herätä sivua viestille, joka ei näytä mitään, pushia ei voida käyttää hiljaisiin tietopäivityksiin.
:::

## Epäonnistumistila {#failure-status}

Kun `subscribe()` tai `send()` epäonnistuu, sen `PendingResult` ilmoittaa `WebforjPushException`:n. <JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink> tunnistaa syyn:

| Tila | Milloin | Mitä tehdä |
|--------|------|------------|
| `PERMISSION_DENIED` | Käyttäjä on estänyt ilmoitukset sovellukselle | Selitä käyttäjälle, mistä hän voi sallia ilmoitukset selaimen asetuksista |
| `UNSUPPORTED` | Push ei ole tuettu selaimessa, sivu ei ole turvallisessa kontekstissa tai sovellusta ei ole otettu käyttöön servletinä | Piilota toiminto |
| `NOT_CONFIGURED` | Vähintään yksi `webforj.push.*`-ominaisuus on puuttuva tai puutteellinen | Luo avaimet ja konfiguroi kaikki kolme ominaisuutta |
| `SUBSCRIPTION_EXPIRED` | Push-palvelu ei tunnista tilausta enää, koska käyttäjä peruutti tai asensi selaimen uudelleen | Poista tallennettu tilaus |
| `REJECTED` | Push-palvelu hylkäsi viestin; `getStatusCode()` sisältää sen vastauksen | Varmista avaimet ja viestin koko |
| `UNREACHABLE` | Push-palvelu ei vastannut ennen aikarajan umpeutumista | Yritä uudelleen myöhemmin |
| `UNKNOWN` | Tallennettu päätepiste ei ole voimassa oleva URL-osoite, tai tilausta tai viestiä ei voitu koodata | Varmista tallennettu tilaus |

Poista vanhentuneet tilaukset jokaisen lähetyksen yhteydessä:

```java
sender.send(subscription, message).exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
    subscriptions.delete(subscription);
  }

  return null;
});
```

:::tip Vanhentuminen saapuu yksi viesti myöhässä
Push-palvelut peruutavat tilauksia laiskasti. Ne hyväksyvät edelleen ensimmäisen viestin sen jälkeen, kun käyttäjä peruuttaa, mutta se ei mene minnekään. Seuraava viesti ilmoittaa `SUBSCRIPTION_EXPIRED`. Hyväksytyn lähetyksen merkki on se, että viesti saapui push-palveluun, ei että käyttäjä näki sen.
:::

## Selaimen tuki {#browser-support}

Kaikki suuret työpöytä- ja mobiiliselaimet näyttävät push-ilmoituksia tilaamisen jälkeen. Pidä mielessä seuraavat rajoitukset:

- iPhone- ja iPad-laitteella push toimii vain verkko-sovelluksille, jotka on lisätty Käynnistysnäyttöön iOS 16.4 tai uudemmassa. Safari-välilehdessä `subscribe()` ilmoittaa `UNSUPPORTED`. Katso [Asennettavat sovellukset](/docs/configuration/installable-apps) vaadittaessa sovellusmanifesteja varten.
- Safari ei näytä ilmoituspainikkeita. Se näyttää viestejä toiminnolla niiden painiketta, mutta napsauttamalla ilmoitusta avaa silti viestin URL-osoitteen.
- Android- ja iOS-web-näkymät eivät näytä ilmoituksia.

Selaimen yksityiskohtia katso MDN [showNotification-yhteensopivuustaulukosta](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility).

## Täydellinen esimerkki {#complete-example}

Seuraava näkymä tilaa ja peruuttaa selaimen, tallentaa tilaukset muistiin ja lähettää viestin jokaiselle tallennetulle tilaukselle. Se voi lähettää välittömästi tai odottaa kahdeksan sekuntia käyttämällä Springin `TaskScheduler`:ia, joka mahdollistaa välilehden sulkemisen ennen ilmoituksen saapumista. Sovellusluokka käyttää `@EnableScheduling`-asetusta tehdäksesi aikatauluttajan saatavaksi.

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
  private final Paragraph status = new Paragraph("Tarkistetaan tilausta...");
  private final TextField message = new TextField("Viesti", "Tilauksesi #42 on matkalla");
  private final Button subscribe =
      new Button("Ota ilmoitukset käyttöön", ButtonTheme.PRIMARY);
  private final Button unsubscribe = new Button("Poista ilmoitukset käytöstä");
  private final Button sendNow = new Button("Lähetä nyt");
  private final Button sendLater = new Button("Lähetä kahdeksan sekunnin päästä");

  public PushView(PushSubscriptions subscriptions, PushSender sender, TaskScheduler scheduler) {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    subscribe.onClick(ev -> Push.getCurrent().subscribe()
        .thenAccept(subscription -> {
          subscriptions.save(subscription);
          status.setText("Tilattu");
        })
        .exceptionally(throwable -> {
          WebforjPushException error = (WebforjPushException) throwable.getCause();
          status.setText(error.getStatus() == PushStatus.PERMISSION_DENIED
              ? "Ilmoituksia on estetty tässä selaimessa"
              : error.getMessage());

          return null;
        }));

    unsubscribe.onClick(ev -> Push.getCurrent().unsubscribe().thenAccept(removed -> {
      removed.ifPresent(subscriptions::delete);
      status.setText(removed.isPresent() ? "Peruutettu" : "Ei ollut tilausta");
    }));

    sendNow.onClick(ev -> sendToAll(subscriptions, sender, message.getValue(), status::setText));

    sendLater.onClick(ev -> {
      String text = message.getValue();
      status.setText("Lähetetään kahdeksan sekunnin päästä, sulje välilehti nyt");
      scheduler.schedule(() -> sendToAll(subscriptions, sender, text, outcome -> {
      }), Instant.now().plusSeconds(8));
    });

    Push.getCurrent().getSubscription().thenAccept(existing -> {
      existing.ifPresent(subscriptions::save);
      status.setText(existing.isPresent() ? "Tilattu" : "Ei tilaus");
    });

    self.add(status, message, subscribe, unsubscribe, sendNow, sendLater);
  }

  private static void sendToAll(PushSubscriptions subscriptions, PushSender sender, String text,
      Consumer<String> report) {
    report.accept("Lähetetään " + subscriptions.findAll().size() + " tilausta");

    for (PushSubscription subscription : subscriptions.findAll()) {
      PendingResult<Void> sent = sender.send(subscription, PushMessage.create("Tilaukset")
          .setBody(text)
          .setIcon("icons://icon-192x192.png")
          .setUrl("/push")
          .setActions(List.of(new PushAction("etusivu", "Avaa etusivu", "/")))
          .build());
      sent.thenAccept(v -> report.accept("Toimitettu"));
      sent.exceptionally(throwable -> {
        WebforjPushException error = (WebforjPushException) throwable;
        if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
          subscriptions.delete(subscription);
          report.accept("Tilaus vanhentui ja poistettiin");
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
