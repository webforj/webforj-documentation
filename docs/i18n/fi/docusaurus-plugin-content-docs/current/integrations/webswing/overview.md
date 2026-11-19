---
title: 'Webswing webforJ:n kanssa: Swing-sovelluksen modernisointi vaihe vaiheelta'
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Suorita Swing-, JavaFX- tai SWT-sovellusta selaimessa Webswingillä, upota se
  webforJ-sovellukseen ja korvaa se verkkonäkymillä yksi kerrallaan.
_i18n_title_hash: 698c1a9e98e2ef808c204b0bc4be4669
_i18n_description_hash: 3bd3bdba7583bc1699e8d6153577eb56
_i18n_hash: 53117d208e8a0b0a2e46705f9edd1cdc
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing suorittaa Swing-, JavaFX- tai SWT-sovelluksen selaimessa ilman lähdekoodiin tehtäviä muutoksia. webforJ:n avulla voit upottaa sovelluksen verkkosovellukseen ja korvata sen yhden näytön kerrallaan. Jotta sovellukset voivat vaihtaa viestejä keskenään, lisää Webswing API Swing-sovellukseen.

Webswing on verkkopalvelinteknologia. Se renderöi työpöytäsovelluksen palvelimella ja suoratoistaa käyttöliittymän selaimeen HTML5-canvasin avulla käsitellen samalla kaikkia käyttäjän toimintoja läpinäkyvästi.

Yleiskatsauksen koko etenemisestä työpöytäsovelluksen suorittamisesta selaimessa sen käytöstä poistamiseen löydät artikkelista [Swing-sovellusten modernisointi](/modernization).

## Webswingin ratkaisut {#what-webswing-solves}

Monet organisaatiot ovat investoineet merkittävästi Java-työpöytäsovelluksiin, jotka sisältävät vuosien tai vuosikymmenten aikana kehitettyä kriittistä liiketoimintalogiikkaa. Näitä sovelluksia ei usein voi helposti kirjoittaa uudelleen seuraavista syistä:

- Monimutkainen toimialalogiikka, jonka uudelleenluominen olisi riskialtista
- Integrointi työpöytäkohtaisiin kirjastoihin tai laitteistoihin
- Kokonaan uudelleenkirjoittamiseen liittyvät aika- ja kustannusrajoitteet
- Tarve säilyttää ominaisuuksien yhdenmukaisuus nykyisen sovelluksen kanssa

Webswing mahdollistaa näiden sovellusten käytön verkossa ilman muutoksia. Ne säilyttävät alkuperäiset ominaisuutensa ja ulkoasunsa.

## Integrointi webforJ:n kanssa {#integration-with-webforj}

webforJ:n Webswing-integraatio tarjoaa `WebswingConnector`-komponentin, jonka avulla voit upottaa Webswing-palvelimella isännöityjä sovelluksia suoraan webforJ-sovellukseesi. Tämä mahdollistaa muun muassa seuraavan:

### Asteittainen modernisointi {#progressive-modernization}

Kaiken tai ei mitään -periaatteella tehtävän uudelleenkirjoittamisen sijaan voit:

1. Aloittaa upottamalla nykyisen Swing-sovelluksesi `WebswingConnector`-komponentin avulla
2. Rakentaa uusia ominaisuuksia webforJ:llä upotetun sovelluksen ympärille
3. Korvata Swing-komponentteja vähitellen vastaavilla webforJ-komponenteilla
4. Poistaa lopulta vanhan sovelluksen kokonaan käytöstä

### Hybridi-sovellukset {#hybrid-applications}

Yhdistä webforJ:llä rakennettu moderni verkkokäyttöliittymä erityisiin työpöytäominaisuuksiin:

- Käytä webforJ:tä käyttäjille suunnatuissa käyttöliittymissä, koontinäytöissä ja raporteissa
- Säilytä Swing monimutkaisia visualisointeja tai erikoistuneita editoreita varten
- Säilytä yhtenäinen sovelluskokemus

:::tip Swing-vastineiden korvaaminen
Katso artikkelista [Swingistä siirtyminen](/docs/introduction/coming-from-swing) Swing-komponenttien ja niitä vastaavien webforJ-komponenttien vertailut. Vertailuissa on myös hyödyllisiä viitteitä, joiden avulla voit käyttää kunkin webforJ-vastineen ominaisuuksia.
:::

## Näin se toimii {#how-it-works}

Integraatio koostuu kolmesta kerroksesta:

1. **Webswing-palvelin**: suorittaa Java-työpöytäsovelluksesi, tallentaa sen visuaalisen tulosteen ja käsittelee käyttäjän syötteet
2. **WebswingConnector-komponentti**: webforJ-komponentti, joka upottaa Webswing-asiakasohjelman ja hallinnoi yhteyttä sekä viestintää palvelimen kanssa
3. **Viestintäprotokolla**: kaksisuuntainen viestintä, jonka avulla webforJ-sovelluksesi voi lähettää komentoja Swing-sovellukseen ja vastaanottaa siltä tapahtumia

Kun käyttäjä avaa webforJ-sovelluksesi, `WebswingConnector` muodostaa yhteyden Webswing-palvelimeen. Palvelin luo sovellukselle uuden instanssin tai muodostaa yhteyden uudelleen olemassa olevaan instanssiin ja alkaa suoratoistaa sovelluksen visuaalista tilaa selaimeen. Käyttäjän toiminnot (hiiri, näppäimistö) tallennetaan ja lähetetään palvelimelle, jossa ne toistetaan varsinaisessa Swing-sovelluksessa.

## Aiheet {#topics}

<DocCardList className="topics-section" />
