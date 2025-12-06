---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 853a4bb057c1a3499c26d4714120170f
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing on verkkopalvelin teknologia, joka mahdollistaa Java-työpöytäsovellusten (Swing, JavaFX, SWT) ajamisen verkkoselaimessa ilman muutoksia alkuperäiseen lähdekoodiin. Se renderöi työpöytäsovelluksen palvelimella ja suoratoistaa käyttöliittymän selainikkunaan HTML5-kankaan avulla, käsitellen kaikki käyttäjävuorovaikutukset läpinäkyvästi.

## Mitä Webswing ratkaisee {#what-webswing-solves}

Monilla organisaatioilla on merkittäviä investointeja Java-työpöytäsovelluksiin, jotka sisältävät kriittistä liiketoimintalogiikkaa, joka on kehitetty vuosien tai jopa vuosikymmenien aikana. Näitä sovelluksia ei usein voida helposti kirjoittaa uudelleen seuraavista syistä:

- Monimutkainen alan logiikka, jonka uudelleen luominen olisi riskialtista
- Integraatio työpöytäspesifisten kirjastoiden tai laitteiston kanssa
- Aika- ja kustannusrajoitukset täydelle uudelleenkirjoitukselle
- Tarve ylläpitää ominaisuuksien pariteettia olemassa olevan toiminnallisuuden kanssa

Webswing mahdollistaa näiden sovellusten verkkosaatavuuden ilman muokkauksia, säilyttäen niiden alkuperäisen toiminnallisuuden ja ulkoasun.

## Integraatio webforJ:n kanssa {#integration-with-webforj}

WebforJ Webswing -integraatio tarjoaa `WebswingConnector`-komponentin, joka mahdollistaa Webswing-isäntä sovellusten upottamisen suoraan webforJ-sovellukseesi. Tämä luo mahdollisuuksia:

### Progressiivinen modernisointi {#progressive-modernization}

Sen sijaan, että tekisit kaiken tai ei mitään -uudelleenkirjoitusta, voit:

1. Aloittaa upottamalla olemassa olevan Swing-sovelluksesi `WebswingConnector`-komponentin avulla
2. Rakentaa uusia ominaisuuksia webforJ:n ympärille upotetun sovelluksen
3. Korvata vähitellen Swing-komponentteja webforJ:n vastaavilla
4. Lopulta poistua täysin perintösovelluksesta

### Hybridisovellukset {#hybrid-applications}

Yhdistä moderni verkkokäyttöliittymä, joka on rakennettu webforJ:llä, erikoistuneeseen työpöytätoiminnallisuuteen:

- Käytä webforJ:tä käyttäjille suunnatuissa käyttöliittymissä, koosteissa ja raportteissa
- Hyödynnä Swingiä monimutkaisissa visualisoinneissa tai erikoistoimittimissa
- Ylläpidä yhtä integroitua sovelluskokemusta

## Kuinka se toimii {#how-it-works}

Integraatio toimii kolmen kerroksen kautta:

1. **Webswing-palvelin**: ajaa Java-työpöytäsovellustasi, vangiten sen visuaalisen ulosteensa ja käsitellen käyttäjän syötteitä
2. **WebswingConnector-komponentti**: webforJ-komponentti, joka upottaa Webswing-asiakkaan, hallinnoi yhteyttä ja viestintää palvelimen kanssa
3. **Viestintäpöytä**: kaksisuuntainen viestintä, joka mahdollistaa webforJ-sovelluksesi lähettää komentoja Swing-sovellukselle ja vastaanottaa tapahtumia takaisin

Kun käyttäjä pääsee webforJ-sovellukseesi, `WebswingConnector` luo yhteyden Webswing-palvelimeen. Palvelin luo tai yhdistää sovelluksen instanssiin ja alkaa suoratoistaa visuaalista tilaa selaimeen. Käyttäjävuorovaikutukset (hiiri, näppäimistö) tallennetaan ja lähetetään palvelimelle, missä ne toistetaan varsinaisessa Swing-sovelluksessa.

## Aiheet {#topics}

<DocCardList className="topics-section" />
