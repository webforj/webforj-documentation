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
_i18n_hash: 771ef18b5a680d5b9260f06a2704f877
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

**craftforJ** on webforJ:n mukana toimitettava visuaalinen kehitysympäristö. Se toimii sovelluksessasi kehitystilassa ja näyttää reaaliaikaisesti Java-koodisi luomat komponentit. Voit valita komponentin, muuttaa sen ominaisuuksia, nähdä sovelluksen päivittyvän välittömästi ja kirjoittaa säilytettävät muutokset takaisin komponentin luoneeseen Java-tiedostoon.

<!-- INTRO_END -->

Koska craftforJ lukee sovellusta webforJ:n kautta, se kuvaa sovellusta samoilla käsitteillä, joilla olet sen kirjoittanut. Puussa näkyvät komponenttisi selaimen renderöimän merkinnän sijaan, ominaisuuksina ovat komponenttiesi määrittelemät ominaisuudet ja reitteinä reitittimesi rekisteröimät reitit sekä niihin määrittämäsi käyttöoikeussäännöt.

<VideoPlayer video="craftforj/intro" width={1920} height={1080} title="Intro" />

## Mitä voit tehdä sillä {#what-you-can-do-with-it}

- **[Tarkastele komponentteja](/docs/craftforj/inspector)** – selaa komponenttipuuta, valitse komponentti napsauttamalla sitä sivulla ja muuta sen ominaisuuksia sovelluksen ollessa käynnissä.
- **[Kirjoita muutokset lähdekoodiin](/docs/craftforj/source-changes)** – tarkastele reaaliaikaisia muokkauksiasi muutoksina ja ota ne käyttöön Java-tiedostoissasi.
- **[Työskentele reittien parissa](/docs/craftforj/routes)** – tarkastele reititystaulukkoa, siirry mille tahansa reitille ja muuta sille määritettyjä käyttöoikeussääntöjä.
- **[Teemoita sovellus](/docs/craftforj/theme)** – säädä sovelluksesi perustana olevia suunnittelutunnisteita ja tallenna tulos tyylitiedostoosi.
- **[Käytä tekoälyagenttia](/docs/craftforj/ai)** – käynnissä olevan sovelluksen sisäinen koodausagentti, joka kirjoittaa Java-koodia vapaasti, kääntää kirjoittamansa ja ottaa sen käyttöön hyväksynnälläsi.

## Miten se eroaa virheenjäljittimestä {#how-it-differs-from-a-debugger}

Virheenjäljitin pysäyttää koodisi ja näyttää muuttujiesi tilan sillä hetkellä. craftforJ pitää sovelluksen käynnissä ja näyttää koodisi tuottaman käyttöliittymän, joten työskentelet tuloksen etkä suorituksen parissa. Työkalut vastaavat eri kysymyksiin, ja niitä käytetään usein yhdessä.

## Vain kehitystilassa {#development-mode-only}

craftforJ edellyttää kahden erillisen asetuksen käyttöönottoa, ja oletuksena se vastaa vain sovelluksen kanssa samalla koneella olevalle selaimelle. [startforJ](https://docs.webforj.com/startforj)-ohjelmalla tai webforJ:n [arkkityypistä](/docs/building-ui/archetypes/overview) luoduissa projekteissa asetukset otetaan käyttöön puolestasi, joten craftforJ on käytettävissä heti, kun käynnistät projektin ensimmäisen kerran. Katso kohdasta [Tietoturva](/docs/craftforj/security), mihin craftforJ pääsee käsiksi ja miten voit varmistaa, että se on poistettu käytöstä tuotannossa.

## Aiheet {#topics}

<DocCardList className="topics-section" />
