---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Embed legacy Swing, JavaFX, or SWT desktop apps inside a webforJ UI through
  Webswing for progressive modernization and hybrid experiences.
_i18n_hash: 300c45aa8cc9f51831219f75d74d31b6
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing on verkkopalvelinteknologia, jonka avulla Java-työpöytäsovelluksia (Swing, JavaFX, SWT) voidaan käyttää verkkoselaimessa muuttamatta alkuperäistä lähdekoodia. Se renderöi työpöytäsovelluksen palvelimella ja välittää käyttöliittymän selaimeen HTML5-canvaksen avulla sekä käsittelee kaikki käyttäjän toiminnot saumattomasti.

## Mitä Webswing ratkaisee {#what-webswing-solves}

Monet organisaatiot ovat investoineet merkittävästi Java-työpöytäsovelluksiin, jotka sisältävät vuosien tai vuosikymmenten aikana kehitettyä kriittistä liiketoimintalogiikkaa. Näitä sovelluksia ei useinkaan voi helposti kirjoittaa uudelleen seuraavista syistä:

- Monimutkainen toimialalogiikka, jonka uudelleenluominen olisi riskialtista
- Integrointi työpöytäkohtaisiin kirjastoihin tai laitteistoon
- Kokonaan uudelleenkirjoittamiseen liittyvät aika- ja kustannusrajoitteet
- Tarve säilyttää nykyisten toimintojen kanssa vastaava ominaisuustaso

Webswing mahdollistaa näiden sovellusten käytön verkossa ilman muutoksia ja säilyttää niiden alkuperäisen toiminnallisuuden ja ulkoasun.

## Integrointi webforJ:n kanssa {#integration-with-webforj}

webforJ:n Webswing-integraatio tarjoaa `WebswingConnector`-komponentin, jonka avulla voit upottaa Webswing-palvelimella isännöityjä sovelluksia suoraan webforJ-sovellukseesi. Tämä mahdollistaa esimerkiksi seuraavat:

### Vaiheittainen modernisointi {#progressive-modernization}

Kaiken kattavan uudelleenkirjoittamisen sijaan voit:

1. Aloittaa upottamalla nykyisen Swing-sovelluksesi `WebswingConnector`-komponentin avulla
2. Rakentaa upotetun sovelluksen ympärille uusia ominaisuuksia webforJ:llä
3. Korvata Swing-komponentteja vähitellen webforJ-vastineilla
4. Siirtyä lopulta kokonaan pois vanhasta sovelluksesta

### Hybridisovellukset {#hybrid-applications}

Yhdistä webforJ:llä rakennettu moderni verkkokäyttöliittymä erikoistuneisiin työpöytätoimintoihin:

- Käytä webforJ:tä käyttäjille suunnatuissa käyttöliittymissä, koontinäytöissä ja raporteissa
- Hyödynnä Swingiä monimutkaisissa visualisoinneissa tai erikoistuneissa muokkaimissa
- Säilytä yhtenäinen, integroitu sovelluskokemus

:::tip Swing-vastineiden korvaaminen
Katso ohjeesta [Siirtyminen Swingistä](/docs/introduction/coming-from-swing) Swing-komponenttien ja niiden webforJ-vastineiden vertailut. Vertailut sisältävät hyödyllisiä viitteitä, joiden avulla voit käyttää kutakin webforJ-vastinetta.
:::

## Miten se toimii {#how-it-works}

Integraatio toimii kolmella tasolla:

1. **Webswing-palvelin**: suorittaa Java-työpöytäsovelluksesi, tallentaa sen visuaalisen tulosteen ja käsittelee käyttäjän syötteet
2. **WebswingConnector-komponentti**: webforJ-komponentti, joka upottaa Webswing-asiakasohjelman ja hallinnoi yhteyttä sekä tiedonsiirtoa palvelimen kanssa
3. **Tiedonsiirtoprotokolla**: kaksisuuntainen viestintä, jonka avulla webforJ-sovelluksesi voi lähettää komentoja Swing-sovellukselle ja vastaanottaa siltä tapahtumia

Kun käyttäjä avaa webforJ-sovelluksesi, `WebswingConnector` muodostaa yhteyden Webswing-palvelimeen. Palvelin luo sovellusinstanssin tai muodostaa siihen uudelleen yhteyden ja alkaa välittää sovelluksen visuaalista tilaa selaimeen. Käyttäjän toiminnot (hiiri ja näppäimistö) tallennetaan ja lähetetään palvelimelle, jossa ne toistetaan varsinaisessa Swing-sovelluksessa.

## Aiheet {#topics}

<DocCardList className="topics-section" />
