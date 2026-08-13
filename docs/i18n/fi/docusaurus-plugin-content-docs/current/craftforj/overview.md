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
_i18n_hash: 6b642a9d173c5943acbb99934542e3a3
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<DocChip chip='since' label='26.02' />

**craftforJ** on visuaalinen kehitysympäristö, joka toimitetaan webforJ:n mukana. Se toimii sovelluksesi sisällä kehitystilassa ja antaa sinulle reaaliaikaisen näkymän komponenteista, jotka Java-koodisi on luonut. Voit valita komponenteista, muuttaa niiden ominaisuuksia, nähdä toimivan sovelluksen päivittyvän heti ja kirjoittaa muutokset, jotka haluat säilyttää, takaisin Java-tiedostoon, joka ne loi.

<!-- INTRO_END -->

Koska craftforJ lukee sovellusta webforJ:n kautta, se kuvaa sovellusta siinä muodossa kuin kirjoitit sen. Puu listaa komponenttisi eikä selaimen renderöimää markupia, ominaisuudet ovat ne, jotka komponenttisi julkistavat, ja reitit ovat ne, jotka reitittimesi rekisteröi yhdessä käyttöoikeuslakien kanssa, joita olet siihen liittänyt.

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4" type="video/mp4" />
      </video>
</div>

## Mitä voit tehdä sillä {#what-you-can-do-with-it}

- **[Tarkastella komponentteja](/docs/craftforj/inspector)** - selata komponenttipuuta, valita komponentti klikkaamalla sitä sivulla ja muuttaa sen ominaisuuksia sovelluksen ollessa käynnissä.
- **[Kirjoittaa muutokset lähteeseen](/docs/craftforj/source-changes)** - tarkistaa reaaliaikaiset muokkauksesi diffinä ja soveltaa niitä Java-tiedostoihisi.
- **[Työskennellä reittien kanssa](/docs/craftforj/routes)** - nähdä reititys-taulukko, navigoida mihin tahansa reittiin ja muuttaa sille ilmoitettuja käyttöoikeuslakia.
- **[Teemoida sovellus](/docs/craftforj/theme)** - säätää suunnittelutunnisteita, joista sovelluksesi on rakennettu ja tallentaa tulos tyylitiedostoon.
- **[Käyttää AI-agenttia](/docs/craftforj/ai)** - koodausagentti toimivassa sovelluksessa, joka kirjoittaa Javaa vapaasti, kääntää mitä se kirjoitti ja soveltaa sitä hyväksyntäsi jälkeen.

## Kuinka se poikkeaa vianetsijästä {#how-it-differs-from-a-debugger}

Vianetsijä keskeyttää koodisi ja näyttää muuttujiesi tilan tuolloin. craftforJ jättää sovelluksen käynnissä ja näyttää sinulle käyttöliittymän, jonka koodisi tuotti, joten työskentelet tuloksen kanssa sen sijaan, että keskittyisit suoritukseen. Kaksi vastaa eri kysymyksiin ja niitä käytetään yleisesti yhdessä.

## Kehitystilassa vain {#development-mode-only}

craftforJ vaatii kahden erillisen asetuksen olevan käytössä, ja oletusarvoisesti se vastaa vain selaimeen, joka toimii samalla koneella kuin sovellus. Projekit, jotka on luotu [startforJ](https://docs.webforj.com/startforj):llä tai webforJ:n [archetypella](/docs/building-ui/archetypes/overview), ottavat sen käyttöön puolestasi, joten se on käytettävissä ensimmäisellä kerralla, kun suoritat ne. Katso [Turvallisuus](/docs/craftforj/security), mitä craftforJ voi tavoittaa ja kuinka varmistaa, että se on pois päältä tuotannossa.

## Aiheet {#topics}

<DocCardList className="topics-section" />
