---
sidebar_position: 39
sidebar_class_name: new-content
title: Push Notifications
description: >-
  Use the Push class, PushSender, and PushMessage to subscribe browsers and send
  notifications from the server, even when the app isn't open.
_i18n_hash: 47adf06762f8af67111f20937368723c
---
<DocChip chip='since' label='26.02' />
<JavadocLink type="push" location="com/webforj/push/Push" top='true'/>

Push-notifikaatiot voivat saavuttaa käyttäjät jopa silloin, kun sovellus ei ole auki. Selain tilaa kerran, sovellus tallentaa tilauksen, ja palvelin käyttää sitä lähettääkseen ilmoituksia, kun tapahtuma occurs. <JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink> hallitsee tilausten tekemistä ja peruuttamista selainympäristössä. Palvelimella <JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> lähettää <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink>:in tallennettuun tilaukseen.

<!-- INTRO_END -->

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/push-notifications/push.mp4" type="video/mp4"/>
  </video>
</div>

## Asennus ja edellytykset {#setup-and-prerequisites}

Push-notifikaatiot tarjoavat erillinen moduuli. Lisää se sovellukseesi:

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

Push-notifikaatiot vaativat:

- Servletin käyttöönoton, kuten Jetty, Spring Boot tai WAR-tiedosto.
- Avaintparin, joka luodaan alla ja jota käyttöönotto käyttää allekirjoittaakseen ilmoituksia.
- Suojatun alkuperän. Selaimet hylkäävät tilaukset, joita toimitetaan muulla kuin `https`:llä, paitsi `localhost`-osoitteesta kehityksen aikana.

:::info Suojatut alkuperät
<!-- vale off -->
Lisätietoja suojatuista konteksteista ja siitä, miksi ne ovat tärkeitä, katso [Suojatut kontekstit MDN -dokumentaatiossa](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

### Avainten luominen {#generating-the-keys}

Push-palvelut hyväksyvät vain ilmoituksia, jotka on allekirjoitettu käyttöönoton toimesta, johon selain on tilannut. Suorita [rakennusliitännäinen](/docs/configuration/build-plugin) kerran jokaiselle käyttöönotolle luodaksesi sen avaintparin:

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

Komento tulostaa kolme konfiguraatioriviä. Liitä ne `application.properties`-tiedostoon ilman lainausmerkkejä tai kopioi ne sellaisina kuin ne on tulostettu `webforj.conf`-tiedostoon. Korvaa aihealue käyttöönoton yhteystiedolla. Sen on oltava `mailto:` tai `https://`-osoite, jota push-palvelut voivat käyttää ottamaan yhteyttä operaattoriin.

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| Ominaisuus | Selitys |
|------------|---------|
| `webforj.push.public-key` | Julkinen puolisko avaintparista, jota käyttöönotto käyttää ilmoitusten allekirjoittamiseen |
| `webforj.push.private-key` | Yksityinen puolisko avaintparista. Kuten kaikki muut salaisuudet, pidä se poissa lähdekoodinhallinnasta |
| `webforj.push.subject` | Käyttöönoton yhteystieto. Sen on oltava `mailto:` tai `https://`-osoite, jota push-palvelut voivat käyttää ottaakseen yhteyttä operaattoriin |

Sovellus lukee nämä ominaisuudet käynnistyksessä. Jos konfiguraatio sisältää vain joitain niistä, käynnistys epäonnistuu ja ilmoittaa, mitkä ominaisuudet puuttuvat.

:::warning Avainten kierto
Jokainen selain tilaa yhden avaintparin. Jos avaimet muuttuvat, push-palvelu hylkää olemassa olevat tilaukset. Seuraava `subscribe()`-kutsu jokaisessa selaimessa korvataa sen tilauksen.
:::

## Kuinka se toimii {#how-it-works}

Prosessissa on kolme vaihetta:

1. **Tilaa.** Näkymästä, `Push.getCurrent().subscribe()` pyytää käyttäjän lupaa ja palauttaa `PushSubscription`:n, joka tunnistaa selaimen osoitteen.
2. **Tallenna.** Sovellus tallentaa tilauksen sen tietojen kanssa ja liittää sen vastaavaan käyttäjään.
3. **Lähetä.** Myöhemmin mistä tahansa säikeestä, `PushSender.send(subscription, message)` siirtää viestin selaimen valmistajan push-palveluun. Palvelu näyttää ilmoituksen riippumatta siitä, onko sovellus auki vai ei.

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("Tilauksesi on lähetetty").setUrl("/orders/42").build());
```

Seuraavat osiot selittävät, mitä selain näyttää ja kuinka käsitellä epäonnistumisia jokaisessa vaiheessa.

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

Kutsu `subscribe()` käyttäjän toimintaan vastaamiseksi, kuten napsauttamalla "Ota ilmoitukset käyttöön" -painiketta. Palatettu <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> valmistuu selaimen <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink>:lla. Jos selain ei voi tilata, se valmistuu poikkeuksellisesti <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink> -poikkeuksella.

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

Jos selain on jo tilattu, `subscribe()`-kutsumisen palauttaa olemassa olevan tilauksen. Voit siis kutsua sitä turvallisesti jokaisella vierailulla.

:::info Selaimen lupa
Ensimmäinen `subscribe()`-kutsu pyytää käyttäjältä lupaa. Selain näyttää tämän kehotteen, se ei ole osa sovelluksen UI:ta. Koska selaimet näyttävät kehotteen vain käyttäjän toimintaan vastauksena, kutsu `subscribe()` napsautustunnistimesta sen sijaan, että sen näkymän rakentajasta.

Jos käyttäjä estää kehotteen, sovellus ei voi pyytää uudelleen kyseisestä alkuperästä.
:::

### Tilausten tallentaminen {#storing-subscriptions}

Tilauksen osoittaa yhden selaimen osoitteen ja kuuluu palvelimelle. Tallenna se sovelluksen tietojen kanssa, käyttäen sen päätepistettä avaimena. Sisällytä kaikki tiedot, joita sovellus tarvitsee valitakseen sopivat selaimet myöhemmin, kuten liitetty käyttäjä. Jokainen tilaus sisältää kolme tekstiarvoa:

| Arvo | Merkitys |
|------|----------|
| `getEndpoint()` | Toimitus-URL, jonka selainvalmistajan push-palvelu on määrittänyt |
| `getP256dh()` | Selaimen julkinen avain |
| `getAuth()` | Selaimen todennussalaisuus |

Käyttäjä, joka tilaa kahdesta selainympäristöstä, on kahdelle tilaukselle. Poista tilaus, kun sen selain peruuttaa tai kun lähetys ilmoittaa, että se on vanhentunut. Katso [Epäonnistumisen tila](#failure-status).

### Tilauksen palauttaminen {#restoring-a-subscription}

`getSubscription()` palauttaa selaimen nykyisen tilauksen tai tyhjän tuloksen, jos sellaista ei ole. Käytä sitä synkronoimaan palvelimen kopio esimerkiksi sen jälkeen, kun sovelluksen tallennus on nollattu:

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

<JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink> kautta, `getPermission()` raportoi, myönnettiinkö käyttäjälle lupa, estettiinkö se vai onko käyttäjä vielä vastannut ilmoituskehotteeseen. Käytä tätä tulosta piilottaaksesi "Ota ilmoitukset käyttöön" -painike, kun napsauttaminen ei vaikuttaisi.

### Peruuttaminen {#unsubscribing}

`unsubscribe()` peruuttaa selaimen tilauksen. Se valmistuu poistetulla tilauksella, jotta sovellus voi poistaa sen tallennetun kopion, tai tyhjällä tuloksella, jos selaimella ei ollut tilausta.

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## Ilmoitusten lähettäminen {#sending-notifications}

<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> lähettää <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink>:n tallennettuun tilaukseen. Se allekirjoittaa viestin käyttöönoton avaimilla ja siirtää sen selaimen valmistajan push-palveluun. Tämä palvelu herättää selaimen ja näyttää ilmoituksen. Koska operaatio ei koskaan blokkaa kutsuvaa säiettä, voit kutsua sen napsautustunnistimesta, ajoitetusta työstä tai pyyntöjen käsittelijästä.

Kun ominaisuudet on konfiguroitu, lähettäjä on saatavilla beanina, jonka voit sisällyttää näkymiin, palveluihin ja ajoitettuihin tehtäviin. Korvataaksesi sen, määritä oma `PushSender`-bean.

```java
@Route("/orders")
public class OrdersView extends Composite<FlexLayout> {

  public OrdersView(PushSender sender, PushSubscriptions subscriptions) {
    // ...
  }
}
```

Ilman Springiä, `new PushSender()` lukee avaimet sovelluksen konfiguraatiosta. Luo lähettäjä sovellus-threadissä, joko näkymässä tai `App.run()`:ssa, ja käytä sitä sitten mistä tahansa säikeestä. Kaikki lähettäjät jakavat yhden yhteysaltaan push-palveluille, joten niiden luominen missä tahansa ei aiheuta kustannuksia.

Ilmoituksia, jotka on lähetettävä myöhemmin tai sen jälkeen, kun käyttäjä on poistunut, käytä ajastinta palvelimella, kuten Springin `TaskScheduler`. Älä käytä sivun ajastinta kuten `Interval`, koska se pysähtyy, kun välilehti suljetaan.

### Viestin muodostaminen {#composing-a-message}

Luo viesti sen otsikoilla, ja määritä sitten kaikki muut vaihtoehdot rakentajassa:

```java
PushMessage message = PushMessage.create("Tilauksesi on lähetetty")
    .setBody("Tilauksesi #42 on matkalla")
    .setIcon("icons://icon-192x192.png")
    .setUrl("/orders/42")
    .setActions(List.of(new PushAction("track", "Seuraa", "/orders/42/tracking")))
    .build();

PendingResult<Void> sent = sender.send(subscription, message);
sent.thenAccept(v -> status.setText("Lähetetty"));
sent.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  status.setText(error.getStatus() + ": " + error.getMessage());

  return null;
});
```

`send()` palauttaa heti. <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> valmistuu, kun push-palvelu hyväksyy viestin, tai valmistuu poikkeuksellisesti, jos palvelu ei hyväksy sitä. Jos `send()` kutsutaan sovellus-threadissä, kuten kuuntimen kautta, sen palautteet suoritetaan siinä säikeessä ja voivat päivittää komponentteja. Jos istunto, joka kutsui `send()`-toimintoa, päättyy ennen kuin vastaus saapuu, palautteet eivät suoriteta, mutta ilmoitus toimitetaan silti.

Lähetys odottaa enintään 30 sekuntia push-palvelua ennen kuin se epäonnistuu `UNREACHABLE`-tilassa. Käytä `setTimeout(Duration)` muuttaaksesi aikarajaa jokaiselle lähetykselle.

| Vaihtoehto | Vaikutus |
|------------|----------|
| `setBody` | Asettaa tekstin, joka näkyy otsikon alapuolella |
| `setIcon` | Asettaa ilmoituksen yhteydessä näytettävän kuvan. Se hyväksyy absoluuttiset URL-osoitteet sekä `icons://` ja `ws://` protokollat. Katso [Resurssit](/docs/managing-resources/assets-protocols). Se ei hyväksy `context://` protokollaa, koska push-palvelut rajoittavat viestin 4 kB:hen |
| `setUrl` | Asettaa sivun, joka avautuu, kun käyttäjä napsauttaa ilmoitusta. Suhteelliset URL-osoitteet ratkaistaan sovelluksen juurta vasten. Jos URL-osoitetta ei aseteta, avautuu sovelluksen juuressa |
| `setActions` | Asettaa ilmoituksessa näytettävät painikkeet, joissa jokaiselle painikkeelle on erillinen URL-osoite. Katso [Selaintuki](#browser-support) |
| `setTag` | Asettaa tunnisteen. Jos näytettävällä ilmoituksella on sama tunniste, uusi ilmoitus korvataan sille |
| `setSilent` | Näyttää ilmoituksen ilman ääntä tai värinää |
| `setTimeToLive` | Asettaa kuinka kauan push-palvelu säilyttää viestin offline-laitteelle, enintään neljä viikkoa |
| `setUrgency` | Käyttää <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink> ilmoittamaan laitteelle, että se voi viivästyttää alhaisen kiireellisyyden viestejä ja säästää akkua |
| `setTopic` | Korvata viesti, joka odottaa vielä push-palvelussa, kun molemmat viestit sisältävät saman aiheen. Aiheet voivat sisältää enintään 32 merkkiä, jotka ovat URL:ssä turvallisia |

Kun välilehti jo näyttää sivua, ilmoituksen napsauttaminen keskittyy sovellukseen. Muussa tapauksessa sivu avataan uudessa välilehdessä. Napsauttaessasi ilmoituksen painiketta avataan sen URL samalla tavalla.

:::info Yksi ilmoitus viestiä kohti
Jokainen viesti näyttää ilmoituksen. Koska selaimet eivät herätä sivua viestille, joka ei näytä mitään, push-palveluja ei voida käyttää hiljaisten tietopäivitysten lähettämiseen.
:::

## Epäonnistumisen tila {#failure-status}

Kun `subscribe()` tai `send()` epäonnistuu, sen `PendingResult` ilmoittaa `WebforjPushException`:sta. <JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink> tunnistaa syyn:

| Tila | Milloin | Mitä tehdä |
|------|---------|------------|
| `PERMISSION_DENIED` | Käyttäjä on estänyt ilmoitukset sovellukselle | Selitä, mistä käyttäjä voi sallia ilmoitukset selaimen asetuksissa |
| `UNSUPPORTED` | Push ei ole tuettu selaimessa, sivu ei ole suojatussa kontekstissa tai sovellusta ei ole otettu käyttöön servletinä | Piilota toiminto |
| `NOT_CONFIGURED` | Ainakin yksi `webforj.push.*` -ominaisuus puuttuu tai on puutteellinen | Luo avaimet ja konfiguroi kaikki kolme ominaisuutta |
| `SUBSCRIPTION_EXPIRED` | Push-palvelu ei enää tunnista tilausta, koska käyttäjä peruutti tai asensi selaimen uudelleen | Poista tallennettu tilaus |
| `REJECTED` | Push-palvelu hylkäsi viestin; `getStatusCode()` sisältää sen vastauksen | Vahvista avaimet ja viestin koko |
| `UNREACHABLE` | Push-palvelu ei vastannut ennen aikarajan umpeutumista | Yritä myöhemmin uudelleen |
| `UNKNOWN` | Tallennettu päätepiste ei ole voimassa oleva URL, tai tilausta tai viestiä ei voitu koodata | Vahvista tallennettu tilaus |

Poista vanhentuneet tilaukset jokaisen lähetyksen aikana:

```java
sender.send(subscription, message).exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
    subscriptions.delete(subscription);
  }

  return null;
});
```

:::tip Vanhentuminen tapahtuu yksi viesti myöhässä
Push-palvelut poistoilmoittavat tilausten laiskasti. Ne hyväksyvät edelleen ensimmäisen viestin sen jälkeen, kun käyttäjä on perunut, mutta se ei mene minnekään. Seuraava viesti ilmoittaa `SUBSCRIPTION_EXPIRED`. Hyväksytty lähetys tarkoittaa, että viesti saavutti push-palvelun, ei sitä, että käyttäjä näki sen.
:::

## Selaintuki {#browser-support}

Kaikki tärkeimmät työpöytä- ja mobiiliselaimet näyttävät push-ilmoituksia tilattaessa. Pidä nämä rajoitukset mielessä:

- iPhoneilla ja iPadeilla push toimii vain web-sovelluksille, jotka on lisätty Koti-näyttöön iOS 16.4:ssä tai sitä uudemmassa versiossa. Safari-välilehdessä `subscribe()` raportoi `UNSUPPORTED`. Katso [Asennettavat sovellukset](/docs/configuration/installable-apps) vaaditulle sovellussuunnitelmalle.
- Safari ei näytä ilmoituspainikkeita. Se näyttää viestejä toiminnoilla ilman painikkeitaan, mutta napsauttamalla ilmoitusta avataan edelleen viestin URL.
- Android- ja iOS-web-näkymät eivät näytä ilmoituksia.

Lisätietoja selaimittain, katso MDN [showNotification-yhteensopivuus-taulukko](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility).

## Täydellinen esimerkki {#complete-example}

Seuraava näkymä tilaa ja peruuttaa selaimen, tallentaa tilaukset muistiin ja lähettää viestin jokaiselle tallennetulle tilaukselle. Se voi lähettää heti tai odottaa kahdeksan sekuntia käyttämällä Springin `TaskScheduler`:ia, jolloin välilehti voi sulkeutua ennen ilmoituksen saapumista. Sovellusluokka käyttää `@EnableScheduling` -komentoa, jotta aikatauluttaja on saatavilla.

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
  private final Paragraph status = new Paragraph("Tarkistetaan tilausta…");
  private final TextField message = new TextField("Viesti", "Tilauksesi #42 on matkalla");
  private final Button subscribe =
      new Button("Ota ilmoitukset käyttöön", ButtonTheme.PRIMARY);
  private final Button unsubscribe = new Button("Poista ilmoitukset käytöstä");
  private final Button sendNow = new Button("Lähetä nyt");
  private final Button sendLater = new Button("Lähetä 8 sekunnin kuluttua");

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
              ? "Ilmoitukset on estetty tässä selaimessa"
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
      status.setText("Lähetetään 8 sekunnin kuluttua, sulje välilehti nyt");
      scheduler.schedule(() -> sendToAll(subscriptions, sender, text, outcome -> {
      }), Instant.now().plusSeconds(8));
    });

    Push.getCurrent().getSubscription().thenAccept(existing -> {
      existing.ifPresent(subscriptions::save);
      status.setText(existing.isPresent() ? "Tilattu" : "Ei tilattu");
    });

    self.add(status, message, subscribe, unsubscribe, sendNow, sendLater);
  }

  private static void sendToAll(PushSubscriptions subscriptions, PushSender sender, String text,
      Consumer<String> report) {
    report.accept("Lähetetään " + subscriptions.findAll().size() + " tilausta kohden");

    for (PushSubscription subscription : subscriptions.findAll()) {
      PendingResult<Void> sent = sender.send(subscription, PushMessage.create("Tilaukset")
          .setBody(text)
          .setIcon("icons://icon-192x192.png")
          .setUrl("/push")
          .setActions(List.of(new PushAction("home", "Avaa pää", "/")))
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
