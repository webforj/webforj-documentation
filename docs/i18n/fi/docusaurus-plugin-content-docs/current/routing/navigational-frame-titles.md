---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: 9d594a84516af29dde3f66726bc22825
---
In webforJ, kaikki reitit renderöidään Frame-nimisen säilön sisällä, joka toimii ylimpänä säilönä, joka on vastuussa nykyisen reitin sisällön näyttämisestä. Kun käyttäjät navigoivat eri reittien välillä, Frame Otsikko päivittyy dynaamisesti heijastamaan aktiivista näkymää, mikä auttaa antamaan selkeän kontekstin käyttäjän nykyiselle sijainnille sovelluksessa.

Frame-otsikon voi asettaa joko staattisesti käyttämällä annotaatioita tai dynaamisesti koodin kautta ajoittain. Tämä joustava lähestymistapa mahdollittaa kehittäjille otsikoiden määrittämisen, jotka vastaavat jokaisen näkymän tarkoitusta, samalla kun ne mukautuvat tarpeen mukaan erityisiin skenaarioihin tai parametreihin.

## Frame-otsikko annotaatioilla {#frame-title-with-annotations}

Yksinkertaisin tapa asettaa frame-otsikko näkymässä on käyttää `@FrameTitle`-annotaatiota. Tämä annotaatio mahdollistaa staattisen otsikon määrittämisen mihin tahansa reittikomponenttiin, joka sovelletaan frameen komponentin renderöinnin yhteydessä.

### `@FrameTitle`-annotaation käyttö {#using-the-frametitle-annotation}

`@FrameTitle`-annotaatio sovelletaan luokan tasolla ja se mahdollistaa merkkijonon arvon määrittämisen, joka edustaa sivun otsikkoa. Kun reititin navigoi komponenttiin, jossa tämä annotaatio on, määritetty otsikko asetetaan automaattisesti selainikkunalle.

Tässä on esimerkki:

```java
@Route
@FrameTitle("Dashboard")
public class DashboardView extends Composite<Div> {
  public DashboardView() {
     // näkymälogiikka
  }
}
```

Tässä esimerkissä:
- `DashboardView`-luokka on merkitty `@Route`-annotaatiolla reitin määrittämiseksi.
- `@FrameTitle("Dashboard")`-annotaatio asettaa frame-otsikon "Dashboardiksi".
- Kun käyttäjä navigoi osoitteeseen `/dashboard`, frame-otsikon arvo päivitetään automaattisesti määritettyyn arvoon.

Tämä menetelmä on hyödyllinen reiteille, joilla on staattinen otsikko eikä vaadi usein päivityksiä reitin kontekstin perusteella.

:::tip `@AppTitle` ja `@FrameTitle`  
Jos sovelluksen otsikko on asetettu, frame-otsikko ottaa sen huomioon. Esimerkiksi, jos sovellus määrittää otsikon `@AppTitle("webforJ")` ja frame-otsikko on asetettu `@FrameTitle("Dashboard")`, lopullinen sivun otsikko on `Dashboard - webforJ`. Voit mukauttaa lopullisen otsikon muotoa `@AppTitle`-annotaatiossa käyttämällä `format`-attribuuttia tarpeen mukaan.  
:::

## Dynaamiset frame-otsikot {#dynamic-frame-titles}

Tapauksissa, joissa frame-otsikon on muututtava dynaamisesti sovelluksen tilan tai reittiparametrien perusteella, webforJ tarjoaa rajapinnan nimeltä `HasFrameTitle`. Tämä rajapinta mahdollistaa komponenttien tuottaa frame-otsikon nykyisen navigointikontekstin ja reittiparametrien perusteella.

### `HasFrameTitle`-rajapinnan toteuttaminen {#implementing-the-hasframetitle-interface}

`HasFrameTitle`-rajapinnassa on yksi metodi `getFrameTitle()`, jota kutsutaan ennen frame-otsikon päivittämistä. Tämä metodi tarjoaa joustavuutta otsikon dynaamiseen luontiin navigointikontekstin tai muiden dynaamisten tekijöiden perusteella.

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {

  public ProfileView() {
    getBoundComponent().add(new H1("Profile Page"));
  }
  
  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // Aseta frame-otsikko dynaamisesti reittiparametrien avulla
    String userId = parameters.get("id").orElse("Unknown");
    return "Profile - Käyttäjä " + userId;
  }
}
```

Tässä esimerkissä:
- `ProfileView`-komponentti toteuttaa `HasFrameTitle`-rajapinnan.
- `getFrameTitle()`-metodi luo dynaamisesti otsikon käyttäen URL-osoitteen `id`-parametria.
- Jos reitti on `/profile/123`, otsikko päivittyy "Profile - Käyttäjä 123" -muotoon.

:::tip Yhdistämällä annotaatioita ja dynaamisia otsikoita
Voit yhdistää sekä staattiset että dynaamiset menetelmät. Jos reittikomponentilla on sekä `@FrameTitle`-annotaatio että se toteuttaa `HasFrameTitle`-rajapinnan, dynaamisesti annettu otsikko metodista `getFrameTitle()` saa etusijan annotaatiosta saadun staattisen arvon yli.
:::
