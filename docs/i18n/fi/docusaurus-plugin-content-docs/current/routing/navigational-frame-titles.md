---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: cbd0aa0a56b47ee6270000fc326a7967
---
In webforJ, kaikki reitit renderöidään Frame-tekijällä, joka toimii yläpäällikkönä ja on vastuussa nykyisen reitin sisällön näyttämisestä. Kun käyttäjät siirtyvät eri reittien välillä, Frame-titteli päivitetään dynaamisesti heijastamaan aktiivista näkymää, mikä auttaa tarjoamaan selkeää kontekstia käyttäjän nykyisestä sijainnista sovelluksessa.

Frame-titteli voidaan asettaa joko staattisesti annotaatioiden avulla tai dynaamisesti koodin kautta ajon aikana. Tämä joustava lähestymistapa antaa kehittäjille mahdollisuuden määrittää titteleitä, jotka vastaavat kunkin näkymän tarkoitusta, samalla kun ne mukautuvat tarvittaessa erityisiin skenaarioihin tai parametreihin.

## Frame-titteli annotaatioilla {#frame-title-with-annotations}

Yksinkertaisin tapa asettaa Frame-titteli näkymässä on käyttää `@FrameTitle`-annotaatiota. Tämä annotaatio mahdollistaa staattisen tittelin määrittämisen mille tahansa reittikomponentille, joka sitten sovelletaan Frameen, kun komponentti renderöidään.

### `@FrameTitle`-annotaation käyttäminen {#using-the-frametitle-annotation}

`@FrameTitle`-annotaatio on sovellettava luokan tasolla ja sen avulla voit määrittää merkkijonon arvon, joka edustaa sivun titteliä. Kun reititin navigoi komponenttiin, jossa on tämä annotaatio, määritetty titteli asetetaan automaattisesti selainikkunalle.

Tässä esimerkki:

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
- `DashboardView`-luokka on annotoitu `@Route`-annotaatiolla reitin määrittämiseksi.
- `@FrameTitle("Dashboard")`-annotaatio asettaa Frame-tittelin "Dashboard":iksi.
- Kun käyttäjä navigoi `/dashboard`-reitille, Frame-titteli päivittyy automaattisesti määritettyyn arvoon.

Tämä menetelmä on hyödyllinen reiteille, joilla on staattinen titteli eikä niitä tarvitse päivittää usein reitin kontekstin mukaan.

:::tip `@AppTitle` ja `@FrameTitle`  
Jos sovelluksen titteli on asetettu, Frame-titteli sisällyttää sen. Esimerkiksi, jos sovellus määrittää tittelin `@AppTitle("webforJ")` ja Frame-titteli on asetettu `@FrameTitle("Dashboard")`, lopullinen sivun titteli on `Dashboard - webforJ`. Voit mukauttaa lopullisen tittelin muotoa `@AppTitle`-annotaatiossa käyttämällä `format`-attribuuttia tarvittaessa.  
:::

## Dynaamiset Frame-tittelit {#dynamic-frame-titles}

Tapauksissa, joissa Frame-tittelin on muututtava dynaamisesti sovelluksen tilan tai reittiparametrien perusteella, webforJ tarjoaa `HasFrameTitle`-rajapinnan. Tämä rajapinta mahdollistaa komponenttien toimittaa Frame-tittelin nykyisen navigointikontekstin ja reittiparametrien perusteella.

### `HasFrameTitle`-rajapinnan toteuttaminen {#implementing-the-hasframetitle-interface}

`HasFrameTitle`-rajapinta sisältää yhden metodin `getFrameTitle()`, jota kutsutaan ennen Frame-tittelin päivittämistä. Tämä metodi antaa mahdollisuuden luoda titteli dynaamisesti navigointikontekstin tai muiden dynaamisten tekijöiden perusteella.

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {

  public ProfileView() {
    getBoundComponent().add(new H1("Profile Page"));
  }
  
  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // Aseta dynaamisesti Frame-titteli reittiparametrien avulla
    String userId = parameters.get("id").orElse("Tuntematon");
    return "Profili - Käyttäjä " + userId;
  }
}
```

Tässä esimerkissä:
- `ProfileView`-komponentti toteuttaa `HasFrameTitle`-rajapinnan.
- `getFrameTitle()`-metodi luo dynaamisesti tittelin URL:n `id`-parametrin perusteella.
- Jos reitti on `/profile/123`, titteli päivitetään "Profili - Käyttäjä 123".

:::tip Yhdistämällä annotaatiot ja dynaamiset titteleitä
Voit yhdistää sekä staattisia että dynaamisia menetelmiä. Jos reittikomponentilla on sekä `@FrameTitle`-annotaatio että se toteuttaa `HasFrameTitle`-rajapinnan, dynaamisesti määritetty titteli `getFrameTitle()`-metodissa ottaa etusijan annotaatiossa määritettyyn staattiseen arvoon verrattuna.
:::
