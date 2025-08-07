---
sidebar_position: 7
title: State Management
_i18n_hash: cba905abd01a780dea1f459ec4397cda
---
Luodaksesi saumatonta, dynaamista käyttäjäkokemusta, tarvitaan usein, että verkkosovelluksesi tila heijastuu URL-osoitteessa ja säilyy selain navigaatio-tapahtumien aikana. Voit saavuttaa tämän ilman sivun lataamista hyödyntämällä URL-parametrien päivityksiä ja selainhistorian tilanhallintaa. Tämä varmistaa, että käyttäjät voivat jakaa, lisätä kirjanmerkkejä tai palata tiettyihin näkymiin, kun sovellus on täysin tietoinen heidän aiemmista vuorovaikutuksistaan.

## URL-osoitteen päivittäminen {#updating-the-url}

Kun verkkosivun tila muuttuu, kuten tuotelistan suodattaminen tai eri näkymien välillä navigointi, tarvitset usein, että URL-osoite heijastaa näitä muutoksia. Voit käyttää `replaceState` tai `pushState` -menetelmiä, joita `BrowserHistory`-luokka tarjoaa manipulointiin URL-osoitteessa ilman sivun lataamista:

- **`pushState`**: Lisää uuden merkinnän selaimen historiaan lataamatta sivua. Tämä on hyödyllistä eri näkymien tai dynaamisen sisällön välillä navigoimiseen.
- **`replaceState`**: Päivittää nykyistä merkintää selainhistoriassa lisäämättä uutta merkintää. Tämä on ihanteellinen tilan päivittämiseen saman näkymän sisällä.

### Esimerkki: URL-osoitteen päivittäminen kyselyparametreilla {#example-updating-the-url-with-query-parameters}

Tässä esimerkissä, kun "Päivitä URL" -painiketta napsautetaan, käyttöliittymä päivitetään näyttämään valittu kategoria ja lajittelu, ja URL-osoite päivitetään uusilla kyselyparametreilla `category` ja `sort`:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> {
  Paragraph paragraph = new Paragraph();
  Random random = new Random();

  public ProductView() {
    Button update = new Button("Päivitä URL", ButtonTheme.PRIMARY);
    update.onClick(ev -> {
      filter("electronics", String.valueOf(random.nextInt(3) - 1));
    });

    Div div = getBoundComponent();
    div.add(update);
    div.add(paragraph);
  }

  public void filter(String category, String sort) {
    // päivitä käyttöliittymä
    updateUI(category, sort);

    // päivitä URL
    updateUrl(category, sort);
  }

  private void updateUI(String category, String sort) {
    paragraph.setText("Näyttää kategoria: " + category + " ja lajittelu: " + sort);
  }

  private void updateUrl(String category, String sort) {
    ParametersBag queryParameters = new ParametersBag();
    queryParameters.put("category", category);
    queryParameters.put("sort", sort);

    Location newLocation = new Location("/products?" + queryParameters.getQueryString());
    Router.getCurrent().getHistory()
        // Päivitä URL ilman sivun lataamista
        .replaceState(null, newLocation);
  }
}
```

### Selitys: {#explanation}

- **`filter`-metodi**: Metodi käsittelee käyttöliittymän ja URL-osoitteen päivitystä valitun `category` ja `sort` mukaan.
- **`updateUrl`-metodi**: Tämä metodi luo uuden `ParametersBag`-kyselyparametreille, rakentaa uuden URL-osoitteen ja käyttää sitten `replaceState`-menetelmää päivittääkseen selaimen URL-osoitteet ilman sivun lataamista.
- **`replaceState`**: Tämä metodi muuttuu URL-osoitteeksi uuteen sijaintiin samalla säilyttäen nykyisen tilan, ilman että sivua ladataan uudelleen.

## Tilanteen tallentaminen ja palauttaminen selainhistoriassa {#saving-and-restoring-state-in-browser-history}

URL-osoitteen päivittämisen lisäksi on mahdollista tallentaa mielivaltaisia tiloja selainhistoriaan. Tämä tarkoittaa, että voit säilyttää lisätietoja, jotka liittyvät nykyiseen näkymään (esimerkiksi: lomakekentät, suodattimet jne.) ilman, että ne upotetaan suoraan URL-osoitteeseen.

### Esimerkki: Valinnan tilan tallentaminen {#example-saving-selection-state}

Seuraavassa esimerkissä `ProfileView` koostuu useista välilehdistä (Profiili, Tilaukset ja Asetukset). Kun käyttäjä siirtyy välilehtien välillä, valitun välilehden tila tallennetaan selainhistorian avulla `replaceState`. Tämä mahdollistaa sovelluksen muistaa viimeksi aktiivisen välilehden, jos käyttäjä navigoi takaisin tähän näkymään tai päivittää sivun.

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
       // Tallenna tila using replaceState
      updateState(currentSection);
    });

    getBoundComponent().add(sections);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    // Yritä palauttaa viimeksi tallennettu osio selainhistorian tilasta
    Optional<Integer> lastSavedSection = event.getState(Integer.class);

    // Jos osio on tallennettu, palauta välilehden valinta
    lastSavedSection.ifPresent(section -> sections.select(section));
  }

  private void updateState(int section) {
    Router router = Router.getCurrent();
    Location currentLocation = router.getHistory().getLocation().get();

    // Päivitä nykyinen tila valitun osion mukaan
    Router.getCurrent().getHistory()
        .replaceState(section, currentLocation);
  }
}
```

### Selitys: {#explanation-1}

1. **TabbedPane-komponentti**: Näkymä koostuu `TabbedPane`-komponentista, jossa on kolme välilehteä: Profiili, Tilaukset ja Asetukset.
2. **Tilantallennus välilehden vaihdossa**: Joka kerta, kun välilehti valitaan, nykyinen osion indeksi tallennetaan selainhistorian avulla `replaceState`-metodilla.
3. **Tilanteen palauttaminen navigoinnin aikana**: Kun käyttäjä navigoi takaisin `ProfileView`:hin, sovellus palauttaa tallennetun osion historiasta käyttämällä `event.getState()` ja palauttaa oikean välilehden valinnan.
