---
sidebar_position: 5
title: Triggers
_i18n_hash: 97f59b66c18e6a2d02174c1ba99f88f1
---
Oletuksena sidokset vahvistavat automaattisesti komponentteja, kun käyttäjät muokkaavat niiden tietoja, kuten syöttämällä uutta tekstiä, tarkistamalla valintaruudun tai valitsemalla uuden vaihtoehdon radiopainikkeessa. Jos haluat poistaa automaattiset vahvistukset käytöstä ja raportoida ne vain tietomalliin kirjoitettaessa, voit määrittää sidonnan poistamaan ne käytöstä. Tämä antaa sinulle hallintaa siitä, milloin ja miten vahvistukset tapahtuvat, jolloin voit hallita vahvistuksia sovelluksen erityisten tarpeiden tai käyttäjäinteraktioiden mukaan.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
  .useValidator(
    Validator.from(new EmailValidator(), "Mukautettu viesti virheelliselle sähköpostiosoitteelle"))
  .autoValidate(false)
  .add();
```

On myös mahdollista poistaa automaattiset vahvistukset koko kontekstilta.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoValidate(false);
```

:::tip Arvon Muutos Tila
Jotkut komponentit, kuten kenttäkomponentit, toteuttavat `ValueChangeModeAware`-rajapinnan, joka antaa sinulle hallintaa siitä, milloin järjestelmä raportoi `ValueChangeEvent`:in. Esimerkiksi voit asettaa kenttäkomponentit raportoimaan arvomuutos vain häipymisen yhteydessä. Tämä konfiguraatio vähentää vahvistusten tiheyttä, optimoi suorituskykyä ja parantaa käyttäjäkokemusta keskittymällä vahvistuksiin hetkinä, jolloin käyttäjä viimeistelee syöttösession, sen sijaan että vahvistuksia suoritetaan aktiivisen kirjoittamisen aikana.

```java
 emailField.setValueChangeMode(ValueChangeMode.ON_BLUR);
```
:::

## Uusintavahvistus {#revalidation}

Vaikka vahvistukset käynnistyvät tyypillisesti automaattisesti tietojen kirjoittamisen aikana, voit myös kutsua niitä manuaalisesti tarkistaaksesi tietojen tilan ilman, että yrität kirjoittaa sitä malliin. Tämä manuaalinen lähestymistapa on erityisesti hyödyllinen tilanteissa, joissa haluat mahdollistaa tai poistaa käytöstä ominaisuuksia lomakedatan voimassaolon mukaan ilman päivityksen tekemistä.

Kuvitellaan klassinen esimerkki Matkapäivävalitsijasta, jossa käyttäjän on valittava kaksi päivämäärää: matkan aloituspäivä ja lopetuspäivä. Ei ole pätevä valita lopetuspäivää, joka tapahtuu ennen aloituspäivää, tai aloituspäivää, joka tapahtuu lopetuspäivän jälkeen. Voit ratkaista nämä riippuvuudet käynnistämällä vahvistukset manuaalisesti:

<Tabs>
<TabItem value="TripBooking" label="TripBooking.java">

```java showLineNumbers
public class TripBooking extends App {
  DateTimeField startDateField = new DateTimeField("Aloituspäivä");
  DateTimeField endDateField = new DateTimeField("Lopetuspäivä");
  FlexLayout layout = FlexLayout.create(startDateField, endDateField).vertical().build().setStyle("margin", "20px auto")
      .setMaxWidth("400px");

  LocalDateTime startDate;
  LocalDateTime endDate;

  @Override
  public void run() throws WebforjException {
    BindingContext<Trip> context = new BindingContext<>(Trip.class);
    context.bind(startDateField, "startDate")
        .useValidator(Objects::nonNull, "Aloituspäivä on pakollinen")
        .useValidator(value -> endDate != null && value.isBefore(endDate),
            "Aloituspäivän on oltava ennen lopetuspäivää")
        .add();

    context.bind(endDateField, "endDate")
        .useValidator(Objects::nonNull, "Lopetuspäivä on pakollinen")
        .useValidator(value -> startDate != null && value.isAfter(startDate),
            "Lopetuspäivän on oltava jälkeen aloituspäivän")
        .add();

    startDateField.setValueChangeMode(ValueChangeMode.ON_BLUR);
    startDateField.addValueChangeListener(event -> {
      startDate = event.getValue();
      context.getBinding("endDate").validate();
    });

    endDateField.setValueChangeMode(ValueChangeMode.ON_BLUR);
    endDateField.addValueChangeListener(event -> {
      endDate = event.getValue();
      context.getBinding("startDate").validate();
    });

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="Trip" label="Trip.java">

```java showLineNumbers
public class Trip {
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }
}
```

</TabItem>
</Tabs>
