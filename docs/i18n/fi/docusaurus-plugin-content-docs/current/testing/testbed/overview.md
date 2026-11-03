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

webforJ-näkymän testaaminen tarkoittaa yleensä sovelluksen käynnistämistä, selaimen avaamista ja sen ohjaamista Seleniumilla tai Playwrightilla. Näin havaitaan se, minkä vain selain voi näyttää, mutta jokaisessa testissä kuluu aikaa selaimen ja palvelimen käynnistämiseen sekä niiden välissä odottamiseen.

TestBed mahdollistaa webforJ:n testauksen ilman selainta. Näkymä suoritetaan tavallisessa JUnit-testissä. Selainta ei avata eikä palvelinta käynnistetä. Testi lisää näkymän, käsittelee sitä käyttäjän tavoin ja tarkistaa tuloksen Javasta. Komponentit liitetään näkymään, laukaisevat tapahtumansa ja navigoivat aivan kuten käynnissä olevassa sovelluksessa. Jokainen vuorovaikutus on valmis ennen testin seuraavan rivin suorittamista, joten odottelua ei tarvita eivätkä testit ole epävakaita.

## Testin toiminta {#how-a-test-works}

TestBed-testi etenee käyttäjän tavoin. Se etsii komponentin sivulta, käsittelee sitä ja tarkistaa, mitä sivulla sen jälkeen näkyy. Seuraava lomake tilaa sähköpostiosoitteen uutiskirjeen, ja sen **Subscribe**-painike pysyy poissa käytöstä, kunnes kentässä on osoite:

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

Testi etsii kentän ja painikkeen, kirjoittaa osoitteen, napsauttaa painiketta ja tarkistaa vahvistuksen:

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

TestBed jakautuu vaiheisiin seuraavasti:

- **Etsiminen.** `ComponentLocator` kuvaa komponenttia samaan tapaan kuin käyttäjä tunnistaa sen: tyypin ja näytettävän sisällön perusteella, esimerkiksi kentän tunnisteen avulla. Katso [Komponenttien etsiminen](./finding-components).
- **Käyttäminen.** `UserInteraction` suorittaa käyttäjän eleitä ja sallii vain sellaiset toiminnot, joita käyttäjäkin voisi tehdä. Käytöstä poistetun painikkeen napsauttaminen epäonnistuttaa testin sen sijaan, että painikkeen kuuntelija suoritettaisiin. Katso [Käyttäjän vuorovaikutus](./user-interaction).
- **Tarkistaminen.** `verifyThat` tarkistaa, mitä käyttäjä näkisi komponentissa, ja tilannevedos vertaa koko näkymää sen tallennettuun kuvaukseen. Katso [Väitteet](./assertions) ja [Tilannevedokset](./snapshots).

Testi ei käsittele näkymän kenttiä suoraan. Se toimii sivulla näkyvän sisällön kautta, joten se läpäisee testin edelleen, vaikka näkymä olisi rakennettu eri tavalla, ja epäonnistuu silloin, kun käyttäjä näkisi jotain muuta.

Yksittäisten näkymien lisäksi TestBed navigoi reiteillä, testaa suojattuja näkymiä, käsittelee dialogeja ja suorittaa ajastimia, kuten [Sovellusten testaaminen](./testing-apps) näyttää. TestBed toimii myös Spring Boot -sovelluksessa sen beanien ja tietoturvan kanssa.

Selaintesteillä on edelleen paikkansa, kun halutaan testata vain selaimessa näkyviä asioita, kuten tyylejä, asettelua ja asiakaskäyttäytymistä. TestBed kattaa loput: sen, miten näkymäsi toimivat käyttäjän käsitellessä niitä.

Aloita [Aloittaminen](./getting-started)-osiosta, jossa lisätään riippuvuus ja kirjoitetaan ensimmäinen testi.

## Aiheet {#topics}

<DocCardList className="topics-section" />
