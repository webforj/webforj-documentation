---
sidebar_position: 7
title: State Management
_i18n_hash: 0766f2c08642792af2fe62e832b4fa1a
---
Luodakseen saumattomia, dynaamisia käyttäjäkokemuksia, vaaditaan usein, että verkkosovelluksen tila heijastuu URL-osoitteessa ja säilyy selaimen navigointitapahtumien aikana. Voit saavuttaa tämän ilman sivun lataamista hyödyntämällä URL-parametrien päivityksiä ja selaushistorian tilan hallintaa. Tämä varmistaa, että käyttäjät voivat jakaa, lisätä kirjanmerkkejä tai palata tiettyihin näkymiin, joista sovellus on täysin tietoinen heidän aiemmista vuorovaikutuksistaan.

## URL:n päivittäminen {#updating-the-url}

Kun verkkosivun tila muuttuu, kuten tuotteiden listauksen suodattamisessa tai eri näkymien välillä navigoimassa, tarvitset usein, että URL heijastaa näitä muutoksia. Voit käyttää `replaceState` tai `pushState` -menetelmiä, joita `BrowserHistory`-luokka tarjoaa, muokataksesi URL:ia ilman sivun lataamista:

- **`pushState`**: Lisää uuden merkinnän selaimen historiakärryyn ilman sivun lataamista. Tämä on hyödyllistä, kun navigoidaan erilaisten näkymien tai dynaamisen sisällön välillä.
- **`replaceState`**: Päivittää nykyisen merkinnän selaimen historiassa lisäämättä uutta merkintää. Tämä on ihanteellinen tilan päivittämiseen samalla näkymällä.

### Esimerkki: URL:n päivittäminen kyselyparametrien avulla {#example-updating-the-url-with-query-parameters}

Tässä esimerkissä, kun "Päivitä URL" -painiketta napsautetaan, käyttöliittymä päivitetään näyttämään valittu kategoria ja lajittelu, ja URL päivitetään uusilla kyselyparametreilla `category` ja `sort`:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> {
  private final Div self = getBoundComponent();
  Paragraph paragraph = new Paragraph();
  Random random = new Random();

  public ProductView() {
    Button update = new Button("Update URL", ButtonTheme.PRIMARY);
    update.onClick(ev -> {
      filter("electronics", String.valueOf(random.nextInt(3) - 1));
    });

    self.add(update);
    self.add(paragraph);
  }

  public void filter(String category, String sort) {
    // päivittää käyttöliittymän
    updateUI(category, sort);

    // päivittää URL:n
    updateUrl(category, sort);
  }

  private void updateUI(String category, String sort) {
    paragraph.setText("Näytetään kategoria: " + category + " ja lajitellaan: " + sort);
  }

  private void updateUrl(String category, String sort) {
    ParametersBag queryParameters = new ParametersBag();
    queryParameters.put("category", category);
    queryParameters.put("sort", sort);

    Location newLocation = new Location("/products?" + queryParameters.getQueryString());
    Router.getCurrent().getHistory()
        // Päivittää URL:n ilman sivun lataamista
        .replaceState(null, newLocation);
  }
}
```

### Selitys: {#explanation}

- **`filter`-metodi**: Metodi käsittelee sekä käyttöliittymän että URL:n päivittämistä valitun `category` ja `sort` mukaan.
- **`updateUrl`-metodi**: Tämä metodi luo uuden `ParametersBag` kyselyparametreille, rakentaa uuden URL:n ja käyttää sitten `replaceState`-menetelmää päivittääkseen selaimen URL:n ilman sivun lataamista.
- **`replaceState`**: Tämä metodi muuttaa URL:n uudeksi sijainniksi säilyttäen nykyisen tilan ilman sivun uudelleen lataamista.

## Tilanteen tallentaminen ja palauttaminen selaimen historiassa {#saving-and-restoring-state-in-browser-history}

URL:n päivittämisen lisäksi on mahdollista tallentaa mielivaltaisia tiloja selaimen historiaan. Tämä tarkoittaa, että voit säilyttää lisätietoja nykyisestä näkymästä (esimerkiksi lomakekentät, suodattimet jne.) ilman, että niitä upotetaan suoraan URL:iin.

### Esimerkki: Valinnan tilan tallentaminen {#example-saving-selection-state}

Seuraavassa esimerkissä `ProfileView` koostuu useista väliotsikoista (Profiili, Tilaukset ja Asetukset). Kun käyttäjä vaihtaa välilehtiä, valitun välilehden tila tallennetaan selaimen historiaan käyttäen `replaceState`-menetelmää. Tämä mahdollistaa sovelluksen muistavan viimeksi aktiivisen välilehden, jos käyttäjä navigoi takaisin tähän näkymään tai päivittää sivun.

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {
  private final Div self = getBoundComponent();
  TabbedPane sections = new TabbedPane();
  int currentSection = 0;

  public ProfileView() {
    sections.addTab("Profile");
    sections.addTab("Orders");
    sections.addTab("Settings");

    sections.onSelect(ev -> {
      currentSection = ev.getTabIndex();
       // Tallenna tila käyttäen replaceState
      updateState(currentSection);
    });

    self.add(sections);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    // Yritetään palauttaa viimeksi tallennettu osio selaimen historiasta
    Optional<Integer> lastSavedSection = event.getState(Integer.class);

    // Jos osio oli tallennettu, palautetaan välilehden valinta
    lastSavedSection.ifPresent(section -> sections.select(section));
  }

  private void updateState(int section) {
    Router router = Router.getCurrent();
    Location currentLocation = router.getHistory().getLocation().get();

    // Päivitä nykyinen tila valittuun osioon
    Router.getCurrent().getHistory()
        .replaceState(section, currentLocation);
  }
}
```

### Selitys: {#explanation-1}

1. **TabbedPane-komponentti**: Näkymä koostuu `TabbedPane`-komponentista, jossa on kolme välilehteä: Profiili, Tilaukset ja Asetukset.
2. **Tilannetta tallentaminen välilehden vaihdossa**: Joka kerta kun välilehti valitaan, nykyinen osioindeksi tallennetaan selaimen historiaan käyttäen `replaceState`-menetelmää.
3. **Tilanteen palauttaminen navigoinnin yhteydessä**: Kun käyttäjä navigoi takaisin `ProfileView`-näkymään, sovellus palauttaa tallennetun osion historiasta käyttäen `event.getState()`-menetelmää ja palauttaa oikean välilehden valinnan.
