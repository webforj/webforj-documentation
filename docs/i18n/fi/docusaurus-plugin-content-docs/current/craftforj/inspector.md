---
title: Komponenttien tarkastelu
sidebar_position: 3
sidebar_class_name: new-content
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 91fbaf620dc8967a50c4a5ce6b455841
---
Inspector näyttää Java-koodisi rakentaman komponenttipuun. `Composite` näkyy kirjoittamanasi luokkana, joka sisältää sille antamasi lapset siinä järjestyksessä, jossa webforJ ne pitää. Näin craftforJ:n rakenne vastaa lähdekoodisi rakennetta.

![Komponenttipuu, jossa komponentti on valittuna ja korostettuna käynnissä olevassa sovelluksessa](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Komponentin valitseminen {#selecting-a-component}

Voit valita komponentin sivulta painamalla <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> ja napsauttamalla sitä. craftforJ valitsee vastaavan solmun puusta. Kun viet hiiren osoittimen puun solmun päälle, vastaava komponentti korostuu sivulla. Näin voit siirtyä näytön ja puun välillä kumpaankin suuntaan.

<VideoPlayer video="craftforj/pick-mode" width={1920} height={992} title="Pick mode" />

Voit hakea puusta painamalla <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Jos ympäröit hakusanan kauttaviivoilla, sitä käsitellään säännöllisenä lausekkeena. Napsauttamalla solmua hiiren oikealla painikkeella avaat sille käytettävissä olevat toiminnot. Voit avata sen lähdekoodin tai lähettää sen [avustajalle](/docs/craftforj/ai).

## Ominaisuuksien tarkasteleminen ja muuttaminen {#reading-and-changing-properties}

Kun valitset komponentin, sen ominaisuudet näkyvät sivupalkissa ryhmiteltyinä sen mukaan, mihin ne vaikuttavat. Komponentin käytettävissä olevat ominaisuudet riippuvat komponentista, ja osa niistä on vain luettavissa. Ominaisuuksille, joita on hankala esittää pelkkänä tekstinä, näytetään niiden arvoon sopiva muokkain. Arvon muuttaminen vaikuttaa käynnissä olevaan sovellukseen välittömästi.

:::info Live-muokkaukset eivät muuta tiedostojasi
Ominaisuuden muokkaus muuttaa vain edessäsi olevaa sovellusta. Muutoksen vieminen lähdekoodiisi on erillinen vaihe, jonka teet tarkoituksella. Katso lisätietoja kohdasta [Muutosten kirjoittaminen lähdekoodiin](/docs/craftforj/source-changes).
:::

<VideoPlayer video="craftforj/property-edit" width={1920} height={1000} title="Property edit" />

## Komponentin lähdekoodin tarkasteleminen {#viewing-the-source-of-a-component}

Voit jäljittää minkä tahansa komponentin sen rakentaneeseen Java-koodiin. Oletusarvoisesti lähdekoodi avautuu craftforJ:ssä vain luettavana ja kohdistuu komponentin luoneeseen riviin. Voit määrittää craftforJ:n avaamaan lähdekoodin sen sijaan editorissasi samasta kohdasta. Jos komponenttia ei voi jäljittää tietylle riville, craftforJ ilmoittaa siitä tyhjän katseluohjelman avaamisen sijaan.

![Lähdekoodin katseluohjelma kohdistettuna valitun komponentin luoneeseen riviin](/img/craftforj/inspector/source-viewer.png#rounded-border)

## Komponentin poistaminen <DocChip chip='since' label='26.03' /> {#removing-a-component}

Kun napsautat puussa komponenttia hiiren oikealla painikkeella ja valitset **Remove Component**, voit poistaa sen kahdella tavalla. Toinen poistaa sen edessäsi olevasta sovelluksesta ja toinen projektistasi.

<VideoPlayer video="craftforj/delete-components" width={1872} height={1080} title="Delete components" />

### Poistaminen vain selaimesta {#removing-from-the-browser-only}

**Remove from browser only** poistaa komponentin käynnissä olevasta sovelluksesta muuttamatta Java-lähdekoodiasi. Käytä tätä toimintoa, kun haluat nähdä, miltä asettelu näyttää ilman tiettyä komponenttia, ennen kuin sitoudut mihinkään.

Poistaminen on väliaikaista. Komponentti palaa, kun sovellus rakentaa näkymän uudelleen lähdekoodistasi, esimerkiksi sivun uudelleenlataamisen jälkeen.

### Poistaminen lähdekoodista {#removing-from-source}

**Remove from source** muokkaa komponentin luonutta Java-tiedostoa, joten komponentti pysyy poistettuna sovelluksen uudelleenrakentamisen jälkeenkin. Poisto kirjoitetaan tiedostoon heti eikä sitä lisätä [odottaviin muutoksiin](/docs/craftforj/source-changes#pending-changes), joten muutosta ei tarvitse tarkistaa ennen sen viemistä tiedostoon.

Java-koodin kirjoittaminen saa sovelluksen rakentumaan ja käynnistymään uudelleen samalla tavoin kuin [muutosten käyttöönotto](/docs/craftforj/source-changes#after-you-apply). Jos [live reload](/docs/configuration/deploy-reload/overview) on määritetty, tämä tapahtuu automaattisesti. Muussa tapauksessa käynnistä sovellus itse uudelleen.

Oletetaan esimerkiksi, että lomakkeessa on **Save**- ja **Cancel**-painikkeet ja **Save**-painikkeella on teema ja napsautuskuuntelija:

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

Kun valitset **Save** ja valitset **Remove from source**, jäljelle jää seuraava koodi. Määrittely, teema, kuuntelija ja painikkeen paikka `add()`-kutsussa poistetaan, samoin kuin `ButtonTheme`-tuonti, jota mikään muu ei käytä. Sovelluksen uudelleenrakentamisen jälkeen **Cancel** on yhä paikallaan:

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

Poistaminen koskee komponenttia eikä mitään sen ulkopuolista:

- Säiliön poistaminen poistaa myös sen sisältämät komponentit.
- Vain komponentin luomiseen, määrittämiseen ja liittämiseen tarvittu koodi siivotaan pois. Tämä koskee myös kenttiä ja tuonteja, joita mikään muu ei käytä.
- Muualla tarvittava koodi säilyy. Jos luokan jokin toinen osa käyttää komponenttia edelleen, esimerkiksi toisen komponentin kuuntelija, craftforJ irrottaa vain komponentin ja jättää sen luovan ja määrittävän koodin paikalleen.
- Uudelleenkäytettävän komponentin, kuten kirjoittamasi `Composite`-komponentin, esiintymän poistaminen poistaa vain kyseisen käyttökerran. Komponentin määrittävää luokkaa ei poisteta eikä muuteta.

craftforJ kirjoittaa tiedoston vain, jos lopputulos kääntyy edelleen. Tiedostoja ei koskaan poisteta.

### Kun poistaminen ei ole käytettävissä {#when-removal-isnt-available}

**Remove from source** on käytettävissä vain, kun Java-koodin kirjoittaminen on sallittu sekä sovelluksen [määrityksissä](/docs/craftforj/configuration#feature-flags) että craftforJ:n **Java source changes** -asetuksessa. Toimintoa ei tarjota Kotlinilla rakennetuille komponenteille eikä komponenteille, joita craftforJ ei pysty jäljittämään lähdekoodin riville.

Joitakin komponentteja ei voi poistaa lähdekoodista lainkaan:

- Reitit, jotka reititin sijoittaa niiden `@Route`-määrityksen perusteella
- Kehys
- Komponentit, jotka toinen komponentti rakentaa osana itseään. Poista sen sijaan ulompi komponentti.

craftforJ voi myös estää poistamisen, jos se ei löydä komponentin lähdekoodia tai pysty päättelemään, mikä koodi pitäisi poistaa. Jos esimerkiksi yhdellä rivillä luodaan useampi kuin yksi samantyyppinen komponentti, pelkästä rivistä ei käy ilmi, minkä niistä valitsit.

Jos poisto estetään, tiedostosi säilyvät ennallaan ja saat ilmoituksen syystä:

```
Could not remove FlexLayout from source: FlexLayout is a part ContactCard builds for itself, remove that instead
```

### Poiston kumoaminen {#undoing-a-removal}

Voit palauttaa lähdekoodista poistamasi komponentin painamalla craftforJ:ssä <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd>. Tämä palauttaa tiedoston tilaan, jossa se oli ennen poistamista. Jos olet muokannut tiedostoa omassa editorissasi sen jälkeen, kumoaminen estetään sen sijaan, että muutoksesi ylikirjoitettaisiin. Katso kohdasta [Kumoa ja tee uudelleen](/docs/craftforj/source-changes#undoing-and-redoing), miten kumoaminen toimii ja mitä tehdä, jos se estetään.
