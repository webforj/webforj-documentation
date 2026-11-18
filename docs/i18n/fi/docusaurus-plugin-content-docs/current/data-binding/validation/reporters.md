---
sidebar_position: 3
title: Raportoijat
description: >-
  Näytä validoinnin tulokset DefaultBindingReporterilla tai liitä sidontoihin
  mukautettuja raportointiohjelmia useReporter-callbackilla.
_i18n_title_hash: 33224a43ff84f1a2547d0b6b171715c0
_i18n_description_hash: 95a0ed8a06d35ed608b37f9fbee9f328
_i18n_hash: e642fa150e90534cdaef8bb0955d4ff0
---
Validation reporters ovat työkaluja, joita käytetään antamaan palautetta validaatioprosessista käyttäjärajapinnalle. Tämä ominaisuus on välttämätön käyttäjille heidän syötteidensä validaation tulosten kertomiseksi, erityisesti monimutkaisissa lomakkeissa tai tietoa vaativissa sovelluksissa.

## Mikä on validaatiotoimittaja? {#whats-a-validation-reporter}

Validaatiotoimittaja on komponentti, joka käsittelee ja näyttää validoitumiset käyttäjille. Se toimii siltoina validaatiologiikan ja käyttäjärajapinnan välillä varmistaen, että validointituloksia viestitään tehokkaasti ja selkeästi.

:::tip Ydinkomponentit Oletustoimittaja
webforJ sisältää `DefaultBindingReporter`-oletustoimittajan, joka on suunniteltu toimimaan saumattomasti kaikkien ydinkomponenttien kanssa. Tämä sisäänrakennettu toimittaja näyttää automaattisesti validaatiovirheet, eliminoiden monissa tapauksissa tarvetta mukautetulle toteutukselle. Komponentin määrityksestä riippuen `DefaultBindingReporter` näyttää validoitumiset suoraan ponnahtavassa ikkunassa tai rivin alla, suoraan komponentin alapuolella. Tämä ominaisuus yksinkertaistaa virheiden raportointiprosessia huomattavasti, varmistaen selkeän ja suoran kommunikoinnin validaatiovirheistä ja parantaen käyttäjäkokemusta tarjoamalla välitöntä ja kontekstiin liittyvää palautetta syötteen validoimisesta.
:::

## Validaatiotoimittajien konfigurointi {#configuring-validation-reporters}

Voit konfiguroida validaatiotoimittajia sitoutumiskontekstissa mukauttaaksesi miten viestejä esitetään. Tyypillisesti toteutat validaatiotoimittajan, joka kokoaa validoitumistulokset yhteen ja näyttää ne käyttäjäystävällisellä tavalla, kuten korostamalla virheellisiä kenttiä, näyttämällä virheviestejä tai päivittämällä tilanilmaisin.

Tässä on esimerkki, kuinka asettaa validaatiotoimittaja kentälle

<Tabs>
<TabItem value="UserRegistration" label="UserRegistration.java">

```java showLineNumbers
@StyleSheet("ws://css/styles.css")
public class UserRegistration extends App {
  Div errors = new Div();
  TextField emailField = new TextField("Sähköpostiosoite");

  FlexLayout layout = FlexLayout.create(emailField, errors).vertical().build();

  @Override
  public void run() throws WebforjException {
    errors.addClassName("error");
    errors.setVisible(false);

    layout.addClassName("form");

    BindingContext<User> context = new BindingContext<>(User.class);
    context.bind(emailField, "email")
        .useValidator(
            Validator.from(new EmailValidator(), "Mukautettu viesti virheellisestä sähköpostiosoitteesta"))
        .useReporter((validationResult, binding) -> {
          errors.setVisible(!validationResult.isValid());

          if (!validationResult.isValid()) {
            errors.setText(validationResult.getMessages().stream().findFirst().orElse(""));
          }
        })
        .add();

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="User" label="User.java">

```java showLineNumbers
public class User {
  private String name;
  private String email;
  private String password;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
```

</TabItem>
<TabItem value="styles" label="styles.css">

```css showLineNumbers
.error {
  border: 1px solid #f1aeb5;
  border-radius: 5px;
  background-color: #f8d7da;
  color: #58151c;
  padding: 5px;
}

.form {
  margin: 20px auto;
  max-width: 400px;
}
```

</TabItem>
</Tabs>

Yllä olevassa koodissa sähköpostisitoutuminen sisältää mukautetun toimittajan, joka näyttää suoraan validoitumisviestit syöttökentän alapuolella. Tämä asetus hyödyntää `useReporter`-menetelmää, joka konfiguroi miten sitoutuminen käsittelee ja esittää validoitumistuloksia. Tämä menetelmä linkittää validaatiologiikan käyttäjärajapintaan varmistaen, että kaikki validoitumisongelmat ovat heti käyttäjän nähtävillä, parantaen lomakkeen vuorovaikutteisuutta ja käyttäjäkokemusta.
