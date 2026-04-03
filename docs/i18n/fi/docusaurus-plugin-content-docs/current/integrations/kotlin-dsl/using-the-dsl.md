---
title: Using the DSL
sidebar_position: 10
_i18n_hash: 05d1319dd97f2d32392408b2e4ae9058
---
Kotlin DSL tarjoaa rakentajatoimintoja webforJ-komponenteille. Kukin toiminto luo komponentin, lisää sen parentti-konttiinsa ja suorittaa konfigurointilohkon. Tämä sivu käsittelee malleja ja käytäntöjä, joita käytät rakentaessasi käyttöliittymiä DSL:llä.

## Nimeämiskäytännöt {#naming-conventions}

DSL-toimintoja on saatavilla kaikille vakiowebforJ-komponenteille, mukaan lukien painikkeet, kentät, layoutit, dialogit, vetovalikot, luettelot ja HTML-elementit. Kukin toiminto käyttää komponentin luokan nimeä **camelCase**-muodossa. `Button` muuttuu `button()`:ksi, `TextField` muuttuu `textField()`:ksi, ja `FlexLayout` muuttuu `flexLayout()`:ksi.

```kotlin
div {
  button("Klikkaa minua")
  textField("Käyttäjätunnus")
  flexLayout {
    // sisäkkäinen sisältö
  }
}
```

:::important Käytettäessä `Break`-komponenttia
Yksi poikkeus: `Break` käyttää takaviivoja, koska `break` on Kotlin-avainsana:

```kotlin
div {
  span("Rivi yksi")
  `break`()
  span("Rivi kaksi")
}
```
:::

## Komponenttien luominen {#creating-components}

Luo komponentti lisäämällä sen DSL-toiminto parentti-blokkiin yhdessä valinnaisten argumenttien ja konfigurointilohkon kanssa, kuten alla on esitetty:

```kotlin
div {
  // Luo Button, lisää se tähän div:iin ja suorittaa sitten lohkon
  button("Lähetä") {
    theme = ButtonTheme.PRIMARY
    onClick { handleSubmit() }
  }
}
```

Kun käytät komponentin DSL-toimintoa, se luo komponentin, lisää sen vanhempaan ja suorittaa myöhemmin konfigurointilohkon.
Konfigurointilohko saa komponentin vastaanottajaksi (`this`), joten voit käyttää ominaisuuksia ja metodeja suoraan:

```kotlin
textField("Sähköposti") {
  placeholder = "sinä@esimerkki.com"   // this.placeholder
  required = true                        // this.required
  onModify { validate() }                // this.onModify(...)
}
```

## Komponenttien sisällyttäminen {#nesting-components}

Komponentit, jotka voivat sisältää lapsia, hyväksyvät sisäkkäiset DSL-kutsut niiden lohkossaan:

```kotlin
flexLayout {
  direction = FlexDirection.COLUMN

  h1("Koontinäyttö")

  div {
    paragraph("Tervetuloa takaisin!")
    button("Näytä raportit")
  }

  flexLayout {
    direction = FlexDirection.ROW
    button("Asetukset")
    button("Kirjaudu ulos")
  }
}
```

### Laajuuden turvallisuus {#scope-safety}

DSL varmistaa asianmukaisen laajuuden. Voit lisätä lapsia vain komponentteihin, jotka tukevat niitä, ja kääntäjä estää vahingossa tapahtuvat viittaukset ulkoisiin laajuuksiin:

```kotlin
div {
  button("Lähetä") {
    // Tämä näyttää siltä, että se lisää kappaleen painikkeen sisään,
    // mutta se lisää sen oikeasti ulompaan div:iin.
    // DSL huomaa tämän virheen käännösvaiheessa.
    paragraph("Lähetetään...") // Ei käänny
  }
}
```

Jos sinun tarvitsee lisätä ulompaan laajuuteen, käytä nimettyä `this`:a, jotta tarkoitus tulee selväksi:

```kotlin
div {
  button("Lähetä") {
    this@div.add(Paragraph("Lähetetään..."))  // Ilmaise tarkoitus
  }
}
```

Tämä pitää käyttöliittymän koodin ennakoitavana ja tekee laajuuden hyppäykset näkyviksi.

## Komponenttien tyylitys {#styling-components}

Kotlin DSL tarjoaa `styles`-laajennusominaisuuden, joka antaa karttamaisen hakemisen CSS-ominaisuuksiin, vastaten Java:n `setStyle()` ja `getStyle()` -toimintoja:

```kotlin
button("Tyylitelty") {
  styles["background-color"] = "#007bff"
  styles["color"] = "valkoinen"
  styles["padding"] = "12px 24px"
  styles["border-radius"] = "4px"
}
```

:::tip[CSS-luokat]
Uudelleenkäytettäviä tyylejä varten, lisää CSS-luokkia inline-tyylien sijaan. `HasClassName`-laajennus mahdollistaa luokkien lisäämisen `+=`-merkillä:

```kotlin
button("Ensisijainen toiminto") {
  classNames += "btn-primary"
}
```
:::

## Tapahtumien käsittely {#event-handling}

Komponenttien on melkein aina reagoitava käyttäjän vuorovaikuttamiseen. DSL tarjoaa tiiviin tapahtumankuuntelijasyntaksin käyttämällä `on`-etuliitteellä varustettuja metodeja, jotka hyväksyvät lambdat:

```kotlin
button("Tallenna") {
  onClick {
    saveData()
    showNotification("Tallennettu!")
  }
}

textField("Haku") {
  onModify { event ->
    performSearch(event.text)
  }
}
```

## Yhteiset parametrit {#common-parameters}

Konfigurointilohkojen lisäksi useimmat DSL-toiminnot hyväksyvät myös yleisiä parametreja lohkon eteen usein käytettävien vaihtoehtojen vuoksi:

```kotlin
// Teksti parametri etiketeille/sisällölle
button("Klikkaa minua")
h1("Sivun otsikko")
paragraph("Kehysteksti")

// Etiketit ja paikkamerkit kentille
textField("Käyttäjätunnus", placeholder = "Syötä käyttäjätunnus")
passwordField("Salasana", placeholder = "Syötä salasana")

// Arvoparametrit syötteille
numberField("Määrä", value = 1.0) {
  min = 0.0
  max = 100.0
}
```

:::tip Nimettyjen argumenttien etu
Nimetyt argumentit antavat sinun siirtää parametreja missä tahansa järjestyksessä, riippumatta siitä, kuinka ne esiintyvät funktion allekirjoituksessa.
:::

## Kokonaisen näkymän rakentaminen {#building-a-complete-view}

Näitä malleja käyttäen tässä on täydellinen lomake, joka yhdistää ne:

```kotlin
@Route("contact")
class ContactView : Composite<Div>() {

  private val self = boundComponent

  init {
    self.apply {
      styles["max-width"] = "400px"
      styles["padding"] = "20px"

      h2("Ota yhteyttä")

      val nameField = textField("Nimi", placeholder = "Nimesi") {
        styles["width"] = "100%"
        styles["margin-bottom"] = "16px"
      }

      val emailField = textField("Sähköposti", placeholder = "sinä@esimerkki.com") {
        styles["width"] = "100%"
      }

      val messageField = textArea("Viesti", placeholder = "Miten voimme auttaa?") {
        styles["width"] = "100%"
      }

      button("Lähetä viesti") {
        theme = ButtonTheme.PRIMARY
        styles["width"] = "100%"

        onClick {
          submitForm(
            name = nameField.text,
            email = emailField.text,
            message = messageField.text
          )
        }
      }
    }
  }

  private fun submitForm(name: String, email: String, message: String) {
    // Käsittele lomakkeen lähetys
  }
}
```

DSL pitää käyttöliittymän rakenteen luettavana samalla, kun se antaa sinulle täydellisen pääsyn komponenttien konfigurointiin.
