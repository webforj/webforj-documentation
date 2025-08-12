---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: 0a4e46f57c88d52966be27b35070a027
---
In webforJ, kaikki reitit renderöidään Frame-ydinrakenteessa, joka toimii ylimpänä säilönä, joka vastaa nykyisen reitin sisällön näyttämisestä. Kun käyttäjät navigoivat eri reittien välillä, Frame-otsikko päivitetään dynaamisesti heijastamaan aktiivista näkymää, mikä auttaa tarjoamaan selkeän kontekstin käyttäjän nykyisestä sijainnista sovelluksessa.

Yhden Frame-otsikon voi asettaa joko staattisesti annotaatioiden avulla tai dynaamisesti koodin kautta ajonaikaisesti. Tämä joustava lähestymistapa mahdollistaa kehittäjille otsikoiden määrittämisen, jotka vastaavat kunkin näkymän tarkoitusta, samalla kun ne sopeutuvat tarvittaessa erityisiin skenaarioihin tai parametreihin.

## Frame-otsikko annotaatioilla {#frame-title-with-annotations}

Yksinkertaisin tapa asettaa Frame-otsikko näkymässä on käyttää `@FrameTitle`-annotaatiota. Tämä annotaatio mahdollistaa staattisen otsikon määrittämisen mille tahansa reittikomponentille, joka sitten sovelletaan Frameen, kun komponentti renderöidään.

### `@FrameTitle`-annotaation käyttäminen {#using-the-frametitle-annotation}

`@FrameTitle`-annotaatio sovelletaan luokan tasolla ja sen avulla voidaan määrittää merkkijonoarvo, joka edustaa sivun otsikkoa. Kun reititin navigoi komponenttiin, jossa on tämä annotaatio, määritetty otsikko asetetaan automaattisesti selaimen ikkunaan.

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
- `DashboardView`-luokkaa on annotoitu `@Route` määrittämään reitti.
- `@FrameTitle("Dashboard")`-annotaatio asettaa Frame-otsikon "Dashboard"-otsikoksi.
- Kun käyttäjä navigoi `/dashboard`, Frame-otsikko päivittyy automaattisesti määritettyyn arvoon.

Tämä menetelmä on hyödyllinen reiteille, joilla on staattinen otsikko eikä tarvitse usein päivityksiä reitin kontekstin perusteella.

:::tip `@AppTitle` ja `@FrameTitle`  
Jos sovelluksen otsikko on asetettu, Frame-otsikko sisällyttää sen. Esimerkiksi, jos sovellus määrittää otsikon `@AppTitle("webforJ")` ja Frame-otsikko on asetettu `@FrameTitle("Dashboard")`, lopullinen sivun otsikko on `Dashboard - webforJ`. Voit mukauttaa lopullisen otsikon muotoa `@AppTitle`-annotaatiossa käyttämällä `format`-attribuuttia tarvittaessa.  
:::

## Dynaamiset Frame-otsikot {#dynamic-frame-titles}

Tapauksissa, joissa Frame-otsikon on muututtava dynaamisesti sovelluksen tilan tai reittiparametrien perusteella, webforJ tarjoaa rajapinnan nimeltä `HasFrameTitle`. Tämä rajapinta mahdollistaa komponenttien tarjoavan Frame-otsikon nykyisen navigointikontekstin ja reittiparametrien perusteella.

### `HasFrameTitle`-rajapinnan toteuttaminen {#implementing-the-hasframetitle-interface}

`HasFrameTitle`-rajapinnassa on yksi metodi `getFrameTitle()`, joka kutsutaan ennen Frame-otsikon päivittämistä. Tämä metodi antaa joustavuutta suurten otsikoiden dynaamiseen luontiin navigointikontekstin tai muiden dynaamisten tekijöiden perusteella.

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {

  public ProfileView() {
    getBoundComponent().add(new H1("Profiilisivu"));
  }
  
  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // Dynaamisesti asetetaan Frame-otsikko reittiparametrien avulla
    String userId = parameters.get("id").orElse("Tuntematon");
    return "Profiili - Käyttäjä " + userId;
  }
}
```

Tässä esimerkissä:
- `ProfileView`-komponentti toteuttaa `HasFrameTitle`-rajapinnan.
- `getFrameTitle()`-metodi luo dynaamisesti otsikon käyttäen URL:n `id`-parametria.
- Jos reitti on `/profile/123`, otsikko päivitetään "Profiili - Käyttäjä 123".

:::tip Yhdistäminen annotaatioihin ja dynaamisiin otsikoihin
Voit yhdistää sekä staattisia että dynaamisia menetelmiä. Jos reittikomponentilla on sekä `@FrameTitle`-annotaatio että se toteuttaa `HasFrameTitle`-rajapinnan, `getFrameTitle()`-metodista dynaamisesti annettu otsikko saa etusijan annotaation staattista arvoa vastaan.
:::
