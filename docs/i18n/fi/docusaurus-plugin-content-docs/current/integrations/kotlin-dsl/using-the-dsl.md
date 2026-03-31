---
title: Using the DSL
sidebar_position: 10
_i18n_hash: cde3a82377e800021761e5d430328ed9
---
Kotlin DSL tarjoaa rakennusfunktioita webforJ-komponenteille. Jokainen funktio luo komponentin, lisää sen vanhempaan säilöön ja suorittaa konfigurointiblokin. Tämä sivu kattaa kuvastot ja käytännöt, joita käytät rakentaessasi käyttöliittymiä DSL:n kanssa.

## Nimeämiskäytännöt {#naming-conventions}

DSL-funktioita on saatavilla kaikille vakiowebforJ-komponenteille, mukaan lukien painikkeet, kentät, asettelut, dialogit, laatikot, listat ja HTML-elementit. Jokainen funktio käyttää komponentti-luokan nimeä **camelCase**-muodossa. `Button` muuttuu `button()`, `TextField` muuttuu `textField()` ja `FlexLayout` muuttuu `flexLayout()`.

```kotlin
div {
  button("Klikkaa minua")
  textField("Käyttäjänimi")
  flexLayout {
    // sisäkkäinen sisältö
  }
}
```

:::important Käyttämällä `Break`-komponenttia
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

Luo komponentti lisäämällä sen DSL-funktio vanhempaan lohkoon yhdessä vapaaehtoisten argumenttien ja konfigurointiblokin kanssa, kuten alla on esitetty:

```kotlin
div {
  // Luo Painike, lisää se tähän diviin, ja suorittaa sitten lohkon
  button("Lähetä") {
    theme = ButtonTheme.PRIMARY
    onClick { handleSubmit() }
  }
}
```

Kun käytät komponentin DSL-funktiota, se luo komponentin, lisää sen vanhempaan ja suorittaa sitten konfigurointiblokin.
Konfigurointiblokki saa komponentin vastaanottajakseen (`this`), joten voit käyttää ominaisuuksia ja metodeja suoraan:

```kotlin
textField("Sähköposti") {
  placeholder = "you@example.com"   // this.placeholder
  required = true                   // this.required
  onModify { validate() }           // this.onModify(...)
}
```

## Komponenttien sisällyttäminen {#nesting-components}

Komponentit, jotka voivat sisältää lapsia, hyväksyvät sisäkkäisiä DSL-kutsuja lohkonsa sisällä:

```kotlin
flexLayout {
  direction = FlexDirection.COLUMN

  h1("Ohjaamo")

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
    // mutta se lisäisi sen oikeasti ulompaan diviin.
    // DSL havaitsisi tämän virheen käännösaikana.
    paragraph("Lähetetään...") // Ei käänny
  }
}
```

Jos sinun tarvitsee lisätä ulkopuoliseen laajuuteen, käytä nimettyä `this`-avainsanaa, jotta aikomus tulee selväksi:

```kotlin
div {
  button("Lähetä") {
    this@div.add(Paragraph("Lähetetään..."))  // Ilmoitetaan, että tämä on sallittu
  }
}
```

Tämä pitää käyttöliittymäkoodin ennakoitavana tekemällä laajuuden hypyt näkyviksi.

## Komponenttien tyylittely {#styling-components}

Kotlin DSL tarjoaa `styles`-laajennusominaisuuden, joka antaa karttamaisen hakumahdollisuuden CSS-ominaisuuksiin, vastaavasti kuin `setStyle()` ja `getStyle()` Java:ssa:

```kotlin
button("Tyylitelty") {
  styles["background-color"] = "#007bff"
  styles["color"] = "valkoinen"
  styles["padding"] = "12px 24px"
  styles["border-radius"] = "4px"
}
```

:::tip[CSS-luokat]
Uudelleenkäytettäviä tyylejä varten lisää CSS-luokkia sen sijaan, että käyttäisit inline-tyylejä. `HasClassName`-laajennus mahdollistaa luokkien lisäämisen `+=`-operaattorilla:

```kotlin
button("Päätoiminto") {
  classNames += "btn-primary"
}
```
:::

## Tapahtumankäsittely {#event-handling}

Komponenttien tarvitsee lähes aina reagoida käyttäjän vuorovaikutukseen. DSL tarjoaa tiiviin tapahtumakuuntelijan syntaksin käyttämällä `on`-alkuisia metodeja, jotka hyväksyvät lambda-lausekkeet:

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

Konfigurointiblokkien lisäksi useimmat DSL-funktiot hyväksyvät myös yhteisiä parametrejä ennen lohkoa yleisesti käytetyille vaihtoehdoille:

```kotlin
// Teksti-parametri nimilappuja/sisältöä varten
button("Klikkaa minua")
h1("Sivun otsikko")
paragraph("Kehys teksti")

// Nimilappu ja sijoitus kentille
textField("Käyttäjänimi", placeholder = "Syötä käyttäjänimi")
passwordField("Salasana", placeholder = "Syötä salasana")

// Arvoparametrit syötteille
numberField("Määrä", value = 1.0) {
  min = 0.0
  max = 100.0
}
```

:::tip Nimillä määritellyt argumentit
Nimelliset argumentit antavat sinun välittää parametreja missä tahansa järjestyksessä, riippumatta siitä, miten ne näkyvät funktion määrittelyssä.
:::

## Kokonaisen näkymän rakentaminen {#building-a-complete-view}

Näiden mallien avulla tässä on täydellinen lomake, joka kokoaa ne yhteen:

``` kotlin
@Route("contact")
class ContactView : Composite<Div>() {

  init {
    boundComponent.apply {
      styles["max-width"] = "400px"
      styles["padding"] = "20px"

      h2("Ota yhteyttä")

      val nameField = textField("Nimi", placeholder = "Nimesi") {
        styles["width"] = "100%"
        styles["margin-bottom"] = "16px"
      }

      val emailField = textField("Sähköposti", placeholder = "you@example.com") {
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

DSL pitää käyttöliittymän rakenteen luettavana, samalla kun se antaa sinulle täyden pääsyn komponentin konfigurointiin.
