---
sidebar_position: 3
title: Reporters
_i18n_hash: c563479cec7e1fe29d483bcd121bb5fc
---
Validation reportterit käytetään palautteen antamiseen käyttäjille varmennusprosessista käyttäjäliittymässä. Tämä ominaisuus on olennaista käyttäjille syötetyn validoinnin tuloksista tiedottamisessa, erityisesti monimutkaisissa lomakkeissa tai tietointensiivisissä sovelluksissa.

## Mikä on validointi reportteri? {#whats-a-validation-reporter}

Validointi reportteri on komponentti, joka käsittelee ja näyttää validoinnin tulokset käyttäjille. Se toimii sillan tavoin validointilogiikan ja käyttäjäliittymän välillä varmistaen, että validointitulokset viestitään tehokkaasti ja selkeästi.

:::tip Ydinkomponentit Oletus Reportteri
webforJ sisältää `DefaultBindingReporter` -oletusbindingreportterin, joka on suunniteltu toimimaan saumattomasti kaikkien core webforJ -komponenttien kanssa. Tämä sisäänrakennettu raportteri näyttää automaattisesti validointivirheet, jolloin mukautetulle toteutukselle ei usein ole tarvetta. Komponentin konfiguraation mukaan `DefaultBindingReporter` näyttää validointivirheet suoraan popoverina tai inline-muodossa, suoraan komponentin alapuolella. Tämä ominaisuus yksinkertaistaa virheilmoitusprosessia merkittävästi, varmistaen selkeän ja suoran kommunikaation validointivirheistä ja parantaen käyttäjäkokemusta tarjoamalla välitöntä, kontekstisensitiivistä palautetta syöttövalidoinnista.
:::

## Validointi reportterien konfigurointi {#configuring-validation-reporters}

Voit konfiguroida validointi reporttereita sidontakontekstissa mukauttaaksesi, miten viestit esitetään. Tyypillisesti toteutat validointi reportterin kootaksesi validointitulokset ja esittääksesi ne käyttäjälle ystävällisellä tavalla, kuten korostamalla virheellisiä kenttiä, näyttämällä virheilmoituksia tai päivittämällä tilan indikaattoreita.

Tässä on esimerkki siitä, miten asettaa validointi reportteri kentälle

<Tabs>
<TabItem value="UserRegistration" label="UserRegistration.java">

```java showLineNumbers
@InlineStyleSheet("context://styles.css")
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

Edellä olevassa koodissa sähköpostin sidonta sisältää mukautetun raportterin, joka näyttää suoraan validointiviestit syöttökentän alapuolella. Tämä asetelma hyödyntää `useReporter` -metodia, joka määrittää, miten sidonta käsittelee ja esittää validointituloksia. Tämä metodi linkittää tehokkaasti validointilogiikan käyttäjäliittymään varmistaen, että mahdolliset validointiongelmat ovat heti näkyvissä käyttäjälle, parantaen lomakkeen vuorovaikutteisuutta ja käyttäjäkokemusta.
