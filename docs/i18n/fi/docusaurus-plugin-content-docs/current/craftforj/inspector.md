---
title: Komponenttien tarkastelu
sidebar_position: 3
sidebar_class_name: new-content
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 585425968807283ea392911908fbd030
---
Inspector näyttää komponenttipuun, jonka Java-koodisi rakensi. `Composite` näkyy kirjoittamanasi luokkana, ja sen lapsina ovat sille antamasi komponentit siinä järjestyksessä, jossa webforJ niitä ylläpitää. Näin craftforJ:n rakenne vastaa lähdekoodisi rakennetta.

![Komponenttipuu, jossa komponentti on valittu ja korostettu käynnissä olevassa sovelluksessa](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Komponentin valitseminen {#selecting-a-component}

Valitse sivulta komponentti painamalla <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> ja napsauttamalla komponenttia. craftforJ valitsee vastaavan solmun puusta. Kun viet hiiren puun solmun päälle, vastaava komponentti korostuu sivulla. Näin voit siirtyä näytön ja puun välillä kumpaankin suuntaan.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Voit hakea puusta painamalla <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Vinoviivojen sisään kirjoitettu hakutermi tulkitaan säännölliseksi lausekkeeksi. Napsauttamalla solmua hiiren oikealla painikkeella näet sille käytettävissä olevat toiminnot. Voit avata lähdekoodin tai antaa sen [avustajalle](/docs/craftforj/ai).

## Ominaisuuksien tarkasteleminen ja muuttaminen {#reading-and-changing-properties}

Kun valitset komponentin, sen ominaisuudet näkyvät sivupalkissa ryhmiteltyinä sen mukaan, mihin ne vaikuttavat. Komponentin tarjoamat ominaisuudet riippuvat komponentista, ja osa niistä on vain luettavissa. Ominaisuuksille, joiden arvoja ei ole kätevää tarkastella pelkkänä tekstinä, tarjotaan niiden arvoon sopiva muokkain. Arvon muuttaminen tulee voimaan käynnissä olevassa sovelluksessa heti.

:::info Live-muokkaukset eivät muuta tiedostojasi
Ominaisuuden muokkaaminen vaikuttaa vain edessäsi olevaan sovellukseen. Muutoksen vieminen lähdekoodiin on erillinen, tietoisesti tehtävä vaihe, jota käsitellään kohdassa [Muutosten kirjoittaminen lähdekoodiin](/docs/craftforj/source-changes).
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Komponentin lähdekoodin tarkasteleminen {#viewing-the-source-of-a-component}

Voit jäljittää minkä tahansa komponentin sen rakentaneeseen Java-koodiin. Oletusarvoisesti lähdekoodi avautuu craftforJ:ssä vain luettavana ja näkymä siirtyy komponentin luoneelle riville. Voit määrittää craftforJ:n avaamaan lähdekoodin sen sijaan editorissasi samalla rivillä. Jos komponenttia ei voi jäljittää tietylle riville, craftforJ ilmoittaa siitä tyhjän näkymän avaamisen sijaan.

![Lähdekoodinäkymä, joka on siirretty valitun komponentin luoneelle riville](/img/craftforj/inspector/source-viewer.png#rounded-border)

## Komponentin poistaminen <DocChip chip='since' label='26.03' /> {#removing-a-component}

Kun napsautat puussa olevaa komponenttia hiiren oikealla painikkeella ja valitset **Poista komponentti**, voit poistaa komponentin kahdella tavalla. Toinen poistaa sen edessäsi olevasta sovelluksesta, ja toinen poistaa sen projektistasi.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/delete-components.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

### Poistaminen vain selaimesta {#removing-from-the-browser-only}

**Poista vain selaimesta** poistaa komponentin käynnissä olevasta sovelluksesta ja jättää Java-lähdekoodisi ennalleen. Käytä tätä vaihtoehtoa, kun haluat nähdä, miltä asettelu näyttää ilman tiettyä komponenttia, ennen kuin sitoudut mihinkään.

Poisto on väliaikainen. Komponentti tulee takaisin, kun sovellus seuraavan kerran rakentaa näkymän lähdekoodistasi, esimerkiksi sivun uudelleenlatauksen jälkeen.

### Poistaminen lähdekoodista {#removing-from-source}

**Poista lähdekoodista** muokkaa komponentin luonutta Java-tiedostoa, joten komponentti pysyy poissa sovelluksen uudelleenrakentamisen jälkeenkin. Poisto kirjoitetaan tiedostoon heti, eikä sitä lisätä [odottaviin muutoksiin](/docs/craftforj/source-changes#pending-changes), joten muutosta ei tarvitse tarkistaa ennen kuin se päätyy tiedostoon.

Java-koodin kirjoittaminen saa sovelluksen rakentumaan ja käynnistymään uudelleen samalla tavalla kuin [muutosten käyttöönotto](/docs/craftforj/source-changes#after-you-apply). Jos [live reload](/docs/configuration/deploy-reload/overview) on määritetty, tämä tapahtuu automaattisesti. Muussa tapauksessa käynnistä sovellus itse uudelleen.

Esimerkkinä on lomake, jossa on **Save**- ja **Cancel**-painikkeet ja jossa **Save**-painikkeella on teema ja napsautuskuuntelija:

```java
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button save = new Button("Save");
    save.setTheme(ButtonTheme.PRIMARY);
    save.onClick(event -> {
      save.setEnabled(false);
    });
    Button cancel = new Button("Cancel");
    layout.add(save, cancel);
  }
}
```

Kun valitset **Save** ja valitset **Poista lähdekoodista**, tuloksena on seuraava. Määrittely, teema, kuuntelija ja painikkeen paikka `add()`-kutsussa poistetaan, samoin kuin `ButtonTheme`-import, jota ei enää käytetä. Kun sovellus on rakentunut uudelleen, **Cancel** on yhä paikallaan:

```java
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button cancel = new Button("Cancel");
    layout.add(cancel);
  }
}
```

Poisto koskee vain komponenttia eikä mitään sen ulkopuolista:

- Säiliön poistaminen poistaa myös sen sisältämät komponentit.
- Vain komponentin luomiseen, määrittämiseen ja liittämiseen tarvittava koodi siivotaan pois. Tämä koskee myös kenttiä ja importteja, joita ei enää käytetä.
- Muualla edelleen tarvittava koodi säilytetään. Jos jokin luokan toinen osa käyttää komponenttia edelleen, esimerkiksi toisen komponentin kuuntelija, craftforJ irrottaa komponentin ja jättää sen luovan ja määrittävän koodin ennalleen.
- Uudelleenkäytettävän komponentin, kuten kirjoittamasi `Composite`-komponentin, esiintymän poistaminen poistaa vain kyseisen käytön. Komponentin määrittelevää luokkaa ei poisteta eikä muuteta.

craftforJ kirjoittaa tiedoston vain, jos tulos kääntyy edelleen. Tiedostoja ei koskaan poisteta.

### Kun poistaminen ei ole käytettävissä {#when-removal-isnt-available}

**Poista lähdekoodista** on käytettävissä vain, jos Java-koodin kirjoittaminen on sallittu sekä sovelluksen [määrityksissä](/docs/craftforj/configuration#feature-flags) että craftforJ:n **Java-lähdekoodin muutokset** -asetuksessa. Toimintoa ei voi käyttää Kotlinilla rakennettuihin komponentteihin eikä komponentteihin, joita craftforJ ei voi jäljittää lähdekoodin riville.

Joitakin komponentteja ei voi poistaa lähdekoodista lainkaan:

- Reitit, jotka reititin sijoittaa niiden `@Route`-määritysten mukaan
- Kehys
- Komponentit, jotka toinen komponentti rakentaa osana itseään. Poista sen sijaan ulompi komponentti.

craftforJ voi myös estää poiston, jos se ei löydä komponentin lähdekoodia tai ei pysty päättelemään, mikä koodi pitäisi poistaa. Näin voi käydä esimerkiksi silloin, kun yksi rivi luo useamman kuin yhden samantyyppisen komponentin, jolloin pelkkä rivi ei kerro, minkä niistä valitsit.

Estetty poisto jättää tiedostot ennalleen ja ilmoittaa syyn:

```
Could not remove FlexLayout from source: FlexLayout is a part ContactCard builds for itself, remove that instead
```

### Poiston kumoaminen {#undoing-a-removal}

Voit palauttaa lähdekoodista poistetun komponentin painamalla craftforJ:ssä <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd>. Tämä palauttaa tiedoston poistamista edeltäneeseen tilaan. Jos olet muokannut tiedostoa omassa editorissasi sen jälkeen, kumoaminen estetään muokkaustesi ylikirjoittamisen sijaan. Katso kohdasta [Kumoa ja tee uudelleen](/docs/craftforj/source-changes#undoing-and-redoing), miten kumoaminen toimii ja mitä tehdä, jos se estetään.
