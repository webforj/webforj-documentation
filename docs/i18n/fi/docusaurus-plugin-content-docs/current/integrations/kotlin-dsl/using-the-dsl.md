---
title: Using the DSL
sidebar_position: 10
sidebar_class_name: new-content
_i18n_hash: 54b936e846c3049cd3d6528e37c864d6
---
Kotlin DSL tarjoaa rakennusfunktioita webforJ-komponenteille. Jokainen funktio luo komponentin, lisää sen pääsäiliöön ja suorittaa konfiguraatioblokin. Tämä sivu kattaa mallit ja käytännöt, joita käytät rakennettaessa käyttölöitä DSL:llä.

## Nimeämiskäytännöt {#naming-conventions}

DSL-funktioita tarjoavat kaikki standardin webforJ-komponentit, mukaan lukien painikkeet, kentät, asettelut, dialogit, laatikot, luettelot ja HTML-elementit. Jokainen funktio käyttää komponenttien luokan nimeä **camelCase-muodossa**. `Button` muuttuu `button()`:ksi, `TextField` muuttuu `textField()`:ksi ja `FlexLayout` muuttuu `flexLayout()`:ksi.

```kotlin
div {
    button("Napsauta minua")
    textField("Käyttäjänimi")
    flexLayout {
        // sisäkkäinen sisältö
    }
}
```
:::important `Header` ja `Footer` -menetelmät
`header` ja `footer` DSL-menetelmiä on muutettu nimiksi `nativeHeader` ja `nativeFooter` konfliktien välttämiseksi muiden komponenttien ylä- ja alaosien kanssa.
:::

:::important `Break`-komponentin käyttö
Yksi poikkeus: `Break` käyttää backtickeja, koska `break` on Kotlinin avainsana:

```kotlin
div {
    span("Rivi yksi")
    `break`()
    span("Rivi kaksi")
}
```
:::

## Komponenttien luominen {#creating-components}

Luo komponentti lisäämällä sen DSL-funktio päälohkoon, yhdessä valinnaisten argumenttien ja konfiguraatioblokin kanssa, kuten alla on esitetty:

```kotlin
div {
    // Luo painikkeen, lisää sen tähän div:iin ja sitten suorittaa lohkon
    button("Lähetä") {
        theme = ButtonTheme.PRIMARY
        onClick { handleSubmit() }
    }
}
```

Kun käytät komponentin DSL-funktiota, se luo komponentin, lisää sen pääsäiliöön ja sitten suorittaa konfiguraatioblokin. 
Konfiguraatioblokki saa komponentin vastaanottimekseen (`this`), joten voit käyttää ominaisuuksia ja metodeja suoraan:

```kotlin
textField("Sähköposti") {
    placeholder = "you@example.com"   // this.placeholder
    required = true                   // this.required
    onModify { validate() }           // this.onModify(...)
}
```

## Komponenttien sisäkkäisyys {#nesting-components}

Komponentit, jotka voivat sisältää lapsia, hyväksyvät sisäkkäisiä DSL-kutsuja sisään lohkoonsa:

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

### Scope-turvallisuus {#scope-safety}

DSL valvoo asianmukaista alueen hallintaa. Voit lisätä lapsia vain komponenteille, jotka tukevat niitä, ja kääntäjä estää vahingossa tapahtuvat viittaukset ulkoisiin alueisiin:

```kotlin
div {
    button("Lähetä") {
        // Tämä näyttää siltä, että se lisää kappaleen painikkeen sisään,
        // mutta se lisäisi sen itse asiassa ulompaan div:iin.
        // DSL havaitsee tämän virheen käännösaikana.
        paragraph("Lähetetään...") // Ei käännetä
    }
}
```

Jos sinun tarvitsee lisätä ulkoiseen alueeseen, käytä nimettyä `this`:tä tarkoituksen selkeyttämiseksi:

```kotlin
div {
    button("Lähetä") {
        this@div.add(Paragraph("Lähetetään..."))  // Erityinen sallittu
    }
}
```

Tämä pitää UI-koodin ennakoitavana tekemällä alueen hyppäykset näkyviksi.

## Komponenttien tyylitys {#styling-components}

Kotlin DSL tarjoaa `styles`-laajennusominaisuuden, joka antaa karttamaisen hakemuksen CSS-ominaisuuksiin, vastaavan `setStyle()` ja `getStyle()` Java-kielellä:

```kotlin
button("Tyylitetty") {
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

Komponenttien tarvitsee lähes aina reagoida käyttäjän vuorovaikutukseen. DSL tarjoaa tiiviin tapahtumakuuntelijan syntaksin käyttämällä `on`-etuliitettä, joka hyväksyy lambda-function:

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

Yhdessä konfiguraatioblokkien kanssa useimmat DSL-funktiot hyväksyvät myös yhteisiä parametrejä ennen lohkoa usein käytetyille vaihtoehdoille:

```kotlin
// Tekstiparametri etiketeille/sisällölle
button("Napsauta minua")
h1("Sivun otsikko")
paragraph("Sisältöteksti")

// Etiketit ja paikan pitäminen kentille
textField("Käyttäjänimi", placeholder = "Syötä käyttäjänimi")
passwordField("Salasana", placeholder = "Syötä salasana")

// Arvoparametrit syötteille
numberField("Määrä", value = 1.0) {
    min = 0.0
    max = 100.0
}
```

:::tip Nimetyillä argumenteilla
Nimetyillä argumenteilla voit välittää parametrejä missä tahansa järjestyksessä, riippumatta siitä, miten ne näkyvät funktion allekirjoituksessa.
:::

## Kokonaisen näkymän rakentaminen {#building-a-complete-view}

Näiden mallien avulla tässä on täydellinen lomake, joka yhdistää ne:

``` kotlin
@Route("kontakt")
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

DSL pitää UI-rakenteen luettavana, samalla kun se antaa täydellisen pääsyn komponenttien konfiguraatioon.
