---
sidebar_position: 10
title: Navigational Frame Titles
description: >-
  Set browser frame titles per route with the @FrameTitle annotation or generate
  them dynamically using HasFrameTitle.
_i18n_hash: 7b190f89d8eeb58df6d8a25ce863cc5e
---
In webforJ, kaikki reitit renderöidään Frame-kontainerissa, joka toimii yläpään säilönä nykyisen reitin sisällön näyttämiseksi. Kun käyttäjät navigoivat eri reittien välillä, Frame-otsikko päivitetään dynaamisesti heijastamaan aktiivista näkymää, mikä auttaa tarjoamaan selkeää kontekstia käyttäjän nykyisestä sijainnista sovelluksessa.

Frame-otsikko voidaan asettaa joko staattisesti annotaatioiden avulla tai dynaamisesti koodissa ajonaikaisesti. Tämä joustava lähestymistapa mahdollistaa kehittäjille otsikoiden määrittämisen, jotka vastaavat kunkin näkymän tarkoitusta, samalla kun ne mukautuvat tarpeen mukaan erityisiin skenaarioihin tai parametreihin.

## Frame-otsikko annotaatioilla {#frame-title-with-annotations}

Yksinkertaisin tapa asettaa Frame-otsikko näkymässä on käyttää `@FrameTitle`-annotaatiota. Tämä annotaatio sallii sinun määrittää staattisen otsikon mille tahansa reitti-komponentille, joka sitten sovelletaan Frameen, kun komponentti renderöidään.

### `@FrameTitle`-annotaation käyttäminen {#using-the-frametitle-annotation}

`@FrameTitle`-annotaatio sovelletaan luokan tasolla ja se sallii sinun määrittää merkkijonon, joka edustaa sivun otsikkoa. Kun reititin navigoi komponenttiin, jossa on tämä annotaatio, määritetty otsikko asetetaan automaattisesti selainikkunalle.

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
- `DashboardView`-luokka on merkitty `@Route`-annotaatiolla reitin määrittelemiseksi.
- `@FrameTitle("Dashboard")`-annotaatio asettaa Frame-otsikon muotoon "Dashboard".
- Kun käyttäjä navigoi `/dashboard`-reitille, Frame-otsikko päivitetään automaattisesti määrätyn arvon mukaiseksi.

Tämä menetelmä on hyödyllinen reiteille, joilla on staattinen otsikko eikä vaadita usein päivityksiä reitin kontekstista riippuen.

:::tip `@AppTitle` ja `@FrameTitle`
Jos sovelluksen otsikko on asetettu, Frame-otsikko sisältää sen. Esimerkiksi, jos sovellus määrittelee otsikon muotoon `@AppTitle("webforJ")` ja Frame-otsikko on asetettu muotoon `@FrameTitle("Dashboard")`, lopullinen sivun otsikko on `Dashboard - webforJ`. Voit mukauttaa lopullisen otsikon muotoa `@AppTitle`-annotaatiossa `format`-attribuutin avulla, jos tarpeen.
:::

## Dynaamiset Frame-otsikot {#dynamic-frame-titles}

Tapauksissa, joissa Frame-otsikon on muututtava dynaamisesti sovelluksen tilan tai reittiparametrien perusteella, webforJ tarjoaa rajapinnan nimeltä `HasFrameTitle`. Tämä rajapinta mahdollistaa komponenttien tarjoavan Frame-otsikon nykyisen navigointikontekstin ja reittiparametrien perusteella.

### `HasFrameTitle`-rajapinnan toteuttaminen {#implementing-the-hasframetitle-interface}

`HasFrameTitle`-rajapinta sisältää yhden metodin `getFrameTitle()`, jota kutsutaan ennen kuin Frame-otsikko päivitetään. Tämä metodi tarjoaa joustavuutta luoda otsikko dynaamisesti navigointikontekstin tai muiden dynaamisten tekijöiden perusteella.

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {
  private final Div self = getBoundComponent();

  public ProfileView() {
    self.add(new H1("Profiilisivu"));
  }

  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // Aseta dynaaminen Frame-otsikko reittiparametrien avulla
    String userId = parameters.get("id").orElse("Tuntematon");
    return "Profiili - Käyttäjä " + userId;
  }
}
```

Tässä esimerkissä:
- `ProfileView`-komponentti toteuttaa `HasFrameTitle`-rajapinnan.
- `getFrameTitle()`-metodi luo dynaamisesti otsikon käyttäen URL:n `id`-parametria.
- Jos reitti on `/profile/123`, otsikko päivitetään muotoon "Profiili - Käyttäjä 123".

:::tip Yhdistämällä annotaatiot ja dynaamiset otsikot
Voit yhdistää sekä staattiset että dynaamiset menetelmät. Jos reittikomponentilla on sekä `@FrameTitle`-annotaatio että se toteuttaa `HasFrameTitle`-rajapinnan, dynaamisesti annettu otsikko `getFrameTitle()`-metodista on etusijalla staattisen arvon yli annotaatiossa.
:::
