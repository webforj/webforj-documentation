---
sidebar_position: 0
title: TestBed
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  The TestBed is webforJ's browserless testing, which runs views and components
  in plain JUnit tests without a browser or a server.
sidebar_class_name: new-content
_i18n_hash: a97354d7621ab9d89fc8b88c7592847f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale Google.Headings = NO -->
# TestBed <DocChip chip='since' label='26.03' />
<!-- vale Google.Headings = YES -->

Een webforJ-weergave testen betekent meestal dat je de app start, een browser opent en deze aanstuurt met Selenium of Playwright. Daarmee vang je op wat alleen in een browser zichtbaar is, maar voor elke test betaal je de prijs van de browser, de server en het wachten tussendoor.

De TestBed is de browserloze testomgeving van webforJ. Een weergave wordt uitgevoerd in een gewone JUnit-test. Er wordt geen browser geopend en geen server gestart. De test voegt de weergave toe, werkt ermee zoals een gebruiker dat doet en controleert het resultaat vanuit Java. Componenten worden gekoppeld, activeren hun events en navigeren precies zoals in een actieve app. Elke interactie is afgerond voordat de volgende regel van de test wordt uitgevoerd, zodat er niets hoeft te wachten en niets onbetrouwbaar is.

## Hoe een test werkt {#how-a-test-works}

Een TestBed-test volgt de stappen van een gebruiker. De test zoekt een component op de pagina, voert er een handeling op uit en controleert daarna wat de pagina toont. Met het volgende formulier kan een gebruiker zich aanmelden met een e-mailadres. De knop **Subscribe** blijft uitgeschakeld totdat het veld een adres bevat:

```java title="NewsletterForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class NewsletterForm extends Composite<Div> {

  public NewsletterForm() {
    TextField email = new TextField("Email");
    Paragraph status = new Paragraph();
    Button subscribe =
        new Button(
            "Subscribe", e -> status.setText("Subscribed " + email.getValue()));
    subscribe.setEnabled(false);
    email.onModify(e -> subscribe.setEnabled(!email.getValue().isBlank()));

    getBoundComponent().add(email, subscribe, status);
  }
}
```

De test vindt het veld en de knop, voert een adres in, klikt op de knop en controleert de bevestiging:

```java title="NewsletterFormTest.java" showLineNumbers
@Test
void shouldSubscribeTheEmailTheUserTyped(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
  var subscribe = ComponentLocator.of(Button.class)
      .byText("Subscribe")
      .locate();

  UserInteraction.type(email, "ada@example.com");
  UserInteraction.click(subscribe);

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Subscribed ada@example.com")
          .isPresent());
}
```

Elke stap heeft een eigen onderdeel in de TestBed:

- **Vinden.** `ComponentLocator` beschrijft een component zoals een gebruiker die herkent, aan de hand van het type en wat de component toont, bijvoorbeeld een veld op basis van het label. Zie [Componenten vinden](./finding-components).
- **Handelen.** `UserInteraction` voert handelingen van een gebruiker uit, en alleen handelingen die een gebruiker ook kan uitvoeren. Als je op een uitgeschakelde knop klikt, mislukt de test in plaats van dat de listener wordt uitgevoerd. Zie [Gebruikersinteractie](./user-interaction).
- **Controleren.** `verifyThat` controleert wat een gebruiker op een component zou zien. Een snapshot vergelijkt de volledige weergave met een opgeslagen beschrijving ervan. Zie [Assertions](./assertions) en [Snapshots](./snapshots).

De test benadert nooit rechtstreeks de velden van de weergave. De test werkt met wat de pagina toont, zodat deze blijft slagen wanneer de weergave anders is opgebouwd, en mislukt wanneer de gebruiker iets anders zou zien.

Naast afzonderlijke weergaven kan de TestBed routes doorlopen, beveiligde weergaven testen, reageren op dialogen en timers uitvoeren, zoals beschreven in [Apps testen](./testing-apps). Ook kan de TestBed worden uitgevoerd in een Spring Boot-app met de bijbehorende beans en beveiliging.

Browsertests blijven nuttig voor wat alleen een browser kan tonen, zoals styling, indeling en gedrag aan de clientkant. De TestBed dekt de rest: wat je weergaven doen wanneer een gebruiker ermee werkt.

Begin met [Aan de slag](./getting-started) om de dependency toe te voegen en je eerste test te schrijven.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
