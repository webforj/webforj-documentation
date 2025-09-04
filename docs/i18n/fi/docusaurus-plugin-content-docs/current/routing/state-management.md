---
sidebar_position: 7
title: State Management
_i18n_hash: e10d155e02722ea38419a79813a2f5af
---
Luodaan saumatonta, dynaamista käyttäjäkokemusta vaativat usein sen, että verkkosovelluksen tila heijastuu URL-osoitteeseen ja säilyy selainviestinnän aikana. Voit saavuttaa tämän lataamatta sivua uudelleen hyödyntämällä URL-parametrien päivityksiä ja selainhistorian hallintaa. Tämä varmistaa, että käyttäjät voivat jakaa, merkitä kirjanmerkkeihin tai palata tiettyihin näkymiin sovelluksen ollessa täysin tietoinen heidän aiemmista vuorovaikutuksistaan.

## URL-osoitteen päivittäminen {#updating-the-url}

Kun verkkosivun tila muuttuu, kuten tuotteiden luettelon suodattamisen tai eri näkymien selaamisen yhteydessä, sinun on usein varmistettava, että URL-osoite heijastaa näitä muutoksia. Voit käyttää `replaceState` tai `pushState` -menetelmiä, joita `BrowserHistory`-luokka tarjoaa, manipuloidaksesi URL-osoitetta lataamatta sivua uudelleen:

- **`pushState`**: Lisää uuden merkinnän selainhistorian pinoon lataamatta sivua uudelleen. Tämä on hyödyllistä eri näkymien tai dynaamisen sisällön välillä navigoimisessa.
- **`replaceState`**: Päivittää nykyisen merkinnän selainhistorian ilman uuden merkinnän lisäämistä. Tämä on ihanteellinen tilan päivittämiseen samalla näkymällä.

### Esimerkki: URL-osoitteen päivittäminen kyselyparametrien kanssa {#example-updating-the-url-with-query-parameters}

Tässä esimerkissä, kun "Päivitä URL" -painiketta napsautetaan, käyttäjäliittymä päivitetään näyttämään valittu kategoria ja lajittelu, ja URL-osoitetta päivitetään uusilla kyselyparametreilla `category` ja `sort`:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> {
  Paragraph paragraph = new Paragraph();
  Random random = new Random();

  public ProductView() {
    Button update = new Button("Update URL", ButtonTheme.PRIMARY);
    update.onClick(ev -> {
      filter("electronics", String.valueOf(random.nextInt(3) - 1));
    });

    Div div = getBoundComponent();
    div.add(update);
    div.add(paragraph);
  }

  public void filter(String category, String sort) {
    // päivitä UI
    updateUI(category, sort);

    // päivitä URL
    updateUrl(category, sort);
  }

  private void updateUI(String category, String sort) {
    paragraph.setText("Näytetään kategoria: " + category + " ja lajittelu: " + sort);
  }

  private void updateUrl(String category, String sort) {
    ParametersBag queryParameters = new ParametersBag();
    queryParameters.put("category", category);
    queryParameters.put("sort", sort);

    Location newLocation = new Location("/products?" + queryParameters.getQueryString());
    Router.getCurrent().getHistory()
        // Päivitä URL ilman sivun uudelleenlatausta
        .replaceState(null, newLocation);
  }
}
```

### Selitys: {#explanation}

- **`filter`-metodi**: Metodi käsittelee sekä UI:n että URL-osoitteen päivittämistä valitun `category` ja `sort` perusteella.
- **`updateUrl`-metodi**: Tämä metodi luo uuden `ParametersBag`-kyselyparametreille, rakentaa uuden URL-osoitteen ja käyttää sitten `replaceState`-menetelmää päivittääkseen selaimen URL-osoitetta lataamatta sivua uudelleen.
- **`replaceState`**: Tämä metodi vaihtaa URL-osoitteen uuteen sijaintiin säilyttäen nykyisen tilan ilman sivun lataamista.

## Tilanteen tallentaminen ja palauttaminen selainhistoriassa {#saving-and-restoring-state-in-browser-history}

URL-osoitteen päivittämisen lisäksi on mahdollista tallentaa satunnaisia tiloja selaimen historiaan. Tämä tarkoittaa, että voit säilyttää lisätietoja nykyisestä näkymästä (esimerkiksi: lomakekentät, suodattimet jne.) ilman, että tallennat niitä suoraan URL-osoitteeseen.

### Esimerkki: Valinnan tilan tallentaminen {#example-saving-selection-state}

Seuraavassa esimerkissä `ProfileView` koostuu useista välilehdistä (Profiili, Tilaukset ja Asetukset). Kun käyttäjä vaihtaa välilehtien välillä, valitun välilehden tila tallennetaan selaimen historiaan käyttäen `replaceState`-menetelmää. Tämä mahdollistaa sovelluksen muistavan viimeksi aktiivisen välilehden, jos käyttäjä navigoi takaisin tähän näkymään tai päivittää sivun.

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {
  TabbedPane sections = new TabbedPane();
  int currentSection = 0;

  public ProfileView() {
    sections.addTab("Profiili");
    sections.addTab("Tilaukset");
    sections.addTab("Asetukset");

    sections.onSelect(ev -> {
      currentSection = ev.getTabIndex();
       // Tallenna tila käyttäen replaceState
      updateState(currentSection);
    });

    getBoundComponent().add(sections);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    // Yritä noutaa viimeksi tallennettu osio selaimen historia tilasta
    Optional<Integer> lastSavedSection = event.getState(Integer.class);

    // Jos osio on tallennettu, palautetaan välilehden valinta
    lastSavedSection.ifPresent(section -> sections.select(section));
  }

  private void updateState(int section) {
    Router router = Router.getCurrent();
    Location currentLocation = router.getHistory().getLocation().get();

    // Päivitä nykyinen tila valitun osion kanssa
    Router.getCurrent().getHistory()
        .replaceState(section, currentLocation);
  }
}
```

### Selitys: {#explanation-1}

1. **TabbedPane-komponentti**: Näkymä koostuu `TabbedPane`-komponentista, jossa on kolme välilehteä: Profiili, Tilaukset ja Asetukset.
2. **Tilanteen tallennus välilehden vaihdon yhteydessä**: Jokaisella kerralla, kun välilehti valitaan, nykyinen osioindeksi tallennetaan selaimen historiaan `replaceState`-menetelmän avulla.
3. **Tilanteen palauttaminen navigoinnin yhteydessä**: Kun käyttäjä navigoi takaisin `ProfileView`-näkymään, sovellus noutaa tallennetun osion historiasta käyttäen `event.getState()` ja palauttaa oikean välilehden valinnan.
