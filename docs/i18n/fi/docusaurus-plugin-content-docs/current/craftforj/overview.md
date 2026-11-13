---
sidebar_position: 1
title: craftforJ
slug: /craftforj
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Inspect the component tree of a running webforJ app, change components live,
  and write the changes you keep back into your Java source.
sidebar_class_name: new-content
_i18n_hash: 727e01ac5960e204363665aba672d6c7
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# craftforJ <DocChip chip='since' label='26.02' />
<!-- vale on -->

**craftforJ** on webforJ:n mukana toimitettava visuaalinen kehitysympäristö. Se toimii sovelluksessasi kehitystilassa ja näyttää reaaliajassa Java-koodisi luomat komponentit. Voit valita komponentin, muuttaa sen ominaisuuksia, nähdä sovelluksen päivittyvän heti ja kirjoittaa haluamasi muutokset takaisin komponentin luoneeseen Java-tiedostoon.

<!-- INTRO_END -->

Koska craftforJ lukee sovellusta webforJ:n kautta, se kuvaa sovellusta niillä käsitteillä, joilla olet sen kirjoittanut. Puussa näkyvät komponenttisi selaimen renderöimän merkintäkielen sijaan, ominaisuudet ovat komponenttiesi määrittämiä ominaisuuksia, ja reitit ovat reitittimesi rekisteröimiä reittejä sekä niihin lisäämiäsi käyttöoikeussääntöjä.

<div class="videos-container">
      <video controls preload="metadata">
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4#t=0.5" type="video/mp4" />
      </video>
</div>

## Mitä voit tehdä sillä {#what-you-can-do-with-it}

- **[Tutki komponentteja](/docs/craftforj/inspector)** – selaa komponenttipuuta, valitse komponentti napsauttamalla sitä sivulla ja muuta sen ominaisuuksia sovelluksen ollessa käynnissä.
- **[Kirjoita muutokset lähdekoodiin](/docs/craftforj/source-changes)** – tarkastele reaaliaikaisia muokkauksiasi diff-muodossa ja ota ne käyttöön Java-tiedostoissasi.
- **[Työskentele reittien kanssa](/docs/craftforj/routes)** – tarkastele reititystaulukkoa, siirry mille tahansa reitille ja muuta sille määritettyjä käyttöoikeussääntöjä.
- **[Teemoita sovellus](/docs/craftforj/theme)** – säädä sovelluksesi suunnittelutunnisteita ja tallenna tulos tyylitiedostoosi.
- **[Käytä tekoälyagenttia](/docs/craftforj/ai)** – käynnissä olevan sovelluksen sisäinen koodausagentti, joka kirjoittaa vapaasti Java-koodia, kääntää kirjoittamansa koodin ja ottaa sen käyttöön hyväksynnälläsi.

## Miten se eroaa virheenjäljittimestä {#how-it-differs-from-a-debugger}

Virheenjäljitin pysäyttää koodisi ja näyttää muuttujiesi tilan sillä hetkellä. craftforJ jättää sovelluksen käyntiin ja näyttää koodisi tuottaman käyttöliittymän, joten työskentelet suorituksen sijaan sen tuloksen parissa. Ne vastaavat eri kysymyksiin, ja niitä käytetään usein yhdessä.

## Vain kehitystilassa {#development-mode-only}

craftforJ edellyttää kahden erillisen asetuksen käyttöönottoa, ja oletusarvoisesti se vastaa vain sovelluksen kanssa samalla koneella toimivalle selaimelle. [startforJ](https://docs.webforj.com/startforj)-ohjelmalla tai webforJ:n [arkkityypistä](/docs/building-ui/archetypes/overview) luoduissa projekteissa se otetaan käyttöön puolestasi, joten se on käytettävissä heti ensimmäisellä käynnistyskerralla. Katso kohdasta [Tietoturva](/docs/craftforj/security), mihin craftforJ pääsee käsiksi ja miten voit varmistaa, että se on poistettu käytöstä tuotannossa.

## Aiheet {#topics}

<DocCardList className="topics-section" />
