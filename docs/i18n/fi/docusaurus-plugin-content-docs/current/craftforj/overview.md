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
_i18n_hash: e5fd219fd4360d24535c26696d1c9d1d
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<DocChip chip='since' label='26.02' />

**craftforJ** on visuaalinen kehitysympäristö, joka tulee mukana webforJ:stä. Se toimii sovelluksesi sisällä kehitystilassa ja antaa sinulle reaaliaikaisen näkymän komponenteista, jotka Java-koodisi on luonut. Voit valita komponentin, muuttaa sen ominaisuuksia, nähdä käynnissä olevan sovelluksen päivittyvän heti ja kirjoittaa haluamasi muutokset takaisin Java-tiedostoon, joka ne loi.

<!-- INTRO_END -->

Koska craftforJ lukee sovellusta webforJ:n kautta, se kuvaa sovellusta käyttämilläsi termeillä. Puu listaa komponenttisi sen sijaan, että se näyttäisi selaimen renderöimän merkkijonon, ominaisuudet ovat niitä, joita komponenttisi ilmoittavat, ja reitit ovat niitä, jotka reititin rekisteröi, yhdessä pääsy sääntöjen kanssa, joilla olet ne merkinnyt.

<div class="videos-container">
      <video controls preload="metadata">
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4#t=0.5" type="video/mp4" />
      </video>
</div>

## Mitä voit tehdä sen kanssa {#what-you-can-do-with-it}

- **[Tarkista komponentit](/docs/craftforj/inspector)** - selaa komponenttipuuta, valitse komponentti napsauttamalla sitä sivulla ja muuta sen ominaisuuksia, kun sovellus on käynnissä.
- **[Kirjoita muutokset lähteeseen](/docs/craftforj/source-changes)** - tarkastele reaaliaikaisia muokkauksiasi vertailuna ja sovella niitä Java-tiedostoihisi.
- **[Työskentele reittien kanssa](/docs/craftforj/routes)** - katso reititystaulukko, siirry mihin tahansa reittiin ja muuta siihen määriteltyjä pääsy sääntöjä.
- **[Teemaa sovellus](/docs/craftforj/theme)** - säädä suunnittelutekijöitä, joista sovelluksesi on rakennettu, ja tallenna tulos tyylitiedostoosi.
- **[Käytä AI-agenttia](/docs/craftforj/ai)** - koodausagentti käynnissä olevassa sovelluksessa, joka kirjoittaa Java-koodia vapaasti, kääntää kirjoittamansa ja soveltaa sitä hyväksynnälläsi.

## Kuinka se eroaa virheenkorjaajasta {#how-it-differs-from-a-debugger}

Virheenkorjaaja pysäyttää koodisi ja näyttää muuttujiesi tilan tuossa hetkessä. craftforJ jättää sovelluksen käynnissä ja näyttää käyttöliittymän, jonka koodisi tuotti, joten työskentelet tuloksen kanssa sen sijaan, että tarkastelisit suoritusta. Nämä kaksi vastaavat eri kysymyksiin ja niitä käytetään yleisesti yhdessä.

## Vain kehitystilassa {#development-mode-only}

craftforJ vaatii kahden erillisen asetuksen olevan käytössä, ja oletusarvoisesti se vastaa vain selaimeen, joka toimii samalla koneella kuin sovellus. Projekteja, jotka on luotu käyttämällä [startforJ](https://docs.webforj.com/startforj) tai webforJ:n [archetype](/docs/building-ui/archetypes/overview), aktivoivat sen puolestasi, jotta se on käytettävissä ensimmäisestä käynnistyksestä alkaen. Katso [Turvallisuus](/docs/craftforj/security) siitä, mihin craftforJ voi päästä ja miten voit varmistaa, että se on pois päältä tuotannossa.

## Aiheet {#topics}

<DocCardList className="topics-section" />
