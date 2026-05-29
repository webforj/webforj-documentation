---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: 7a3b1c6780e7032040802a936bfb83fb
---
In webforJ, kaikki reitit renderöidään Frame-elementin sisällä, joka toimii ylätason säiliönä vastaavan reitin sisällön näyttämiseksi. Kun käyttäjät navigoivat eri reittien välillä, Frame-nimi päivitetään dynaamisesti heijastamaan aktiivista näkymää, mikä auttaa tarjoamaan selkeän kontekstin käyttäjän nykyiselle sijainnille sovelluksessa.

Frame-elementin nimikkeen voi asettaa joko staattisesti käyttämällä annotaatioita tai dynaamisesti koodin kautta ajonaikana. Tämä joustava lähestymistapa mahdollistaa kehittäjien määrittää nimikkeitä, jotka vastaavat kunkin näkymän tarkoitusta, ja sopeutuvat erityisiin skenaarioihin tai parametreihin tarpeen mukaan.

## Frame-nimi annotaatioilla {#frame-title-with-annotations}

Yksinkertaisin tapa asettaa Frame-elementin nimi näkymässä on käyttää `@FrameTitle`-annotaatiota. Tämä annotaatio sallii sinun määrittää staattisen nimen mille tahansa reittikomponentille, joka sitten sovelletaan Frame-elementtiin, kun komponentti renderöidään.

### Käyttämällä `@FrameTitle`-annotaatiota {#using-the-frametitle-annotation}

`@FrameTitle`-annotaatio on sovellettuna luokan tasolla ja sallii sinun määritellä merkkijonovälin, joka edustaa sivun nimeä. Kun reititin navigoi komponenttiin, jossa on tämä annotaatio, määritetty nimike asetetaan automaattisesti selainikkunalle.

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

 tässä esimerkissä:
- `DashboardView`-luokkaa on merkitty `@Route`-annotaatiolla reitin määrittämiseksi.
- `@FrameTitle("Dashboard")`-annotaatio asettaa Frame-nimeksi "Dashboard".
- Kun käyttäjä navigoi osoitteeseen `/dashboard`, Frame-elementin nimi päivitetään automaattisesti määritettyyn arvoon.

Tämä menetelmä on hyödyllinen reiteille, joilla on staattinen nimi eikä vaadi usein päivityksiä reitin kontekstin mukaan.

:::tip `@AppTitle` ja `@FrameTitle`  
Jos sovelluksen nimi on asetettu, Frame-nimi sisältää sen. Esimerkiksi, jos sovellus määrittelee nimen `@AppTitle("webforJ")` ja Frame-nimi on asetettu `@FrameTitle("Dashboard")`, lopullinen sivun nimi on `Dashboard - webforJ`. Voit mukauttaa lopullisen nimen muotoa `@AppTitle`-annotaatiossa käyttämällä `format`-attribuuttia tarvittaessa.  
:::

## Dynaamiset Frame-nimet {#dynamic-frame-titles}

Tapauksissa, joissa Frame-nimi tarvitsee muuttua dynaamisesti sovelluksen tilan tai reittiparametrien mukaan, webforJ tarjoaa rajapinnan nimeltä `HasFrameTitle`. Tämä rajapinta sallii komponenttien tarjota Frame-nimen nykyisen navigointikontekstin ja reittiparametrien perusteella.

### `HasFrameTitle`-rajapinnan toteuttaminen {#implementing-the-hasframetitle-interface}

`HasFrameTitle`-rajapinta sisältää yhden metodin `getFrameTitle()`, joka kutsutaan ennen kuin Frame-elementin nimeä päivitetään. Tämä metodi tarjoaa joustavuutta luoda nimi dynaamisesti navigointikontekstin tai muiden dynaamisten tekijöiden perusteella.

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {
  private final Div self = getBoundComponent();

  public ProfileView() {
    self.add(new H1("Profile Page"));
  }
  
  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // Dynaamisesti asetetaan Frame-nimi käyttämällä reittiparametreja
    String userId = parameters.get("id").orElse("Unknown");
    return "Profile - User " + userId;
  }
}
```

 tässä esimerkissä:
- `ProfileView`-komponentti toteuttaa `HasFrameTitle`-rajapinnan.
- `getFrameTitle()`-metodi luo dynaamisesti nimen käyttäen URL:stä saatua `id`-parametriä.
- Jos reitti on `/profile/123`, nimi päivitetään muotoon "Profile - User 123".

:::tip Yhdistämällä annotaatiot ja dynaamiset nimet
Voit yhdistää sekä staattiset että dynaamiset menetelmät. Jos reittikomponentilla on sekä `@FrameTitle`-annotaatio että se toteuttaa `HasFrameTitle`-rajapinnan, `getFrameTitle()`-metodista dynaamisesti tarjottu nimi saa etusijan annotaatiosta saadun staattisen arvon yli.
:::
