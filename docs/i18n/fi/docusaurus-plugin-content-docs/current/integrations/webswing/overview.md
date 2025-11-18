---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: e8f61966c5b7d0745f65f23172dd114a
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing on verkkopalvelinteknologia, joka mahdollistaa Java-työpöytäsovellusten (Swing, JavaFX, SWT) suorittamisen verkkoselaimessa ilman muutoksia alkuperäiseen lähdekoodiin. Se renderöi työpöytäsovelluksen palvelimella ja suoratoistaa käyttöliittymän selaimeen HTML5-kankaan avulla, käsitellen kaikki käyttäjäinteraktiot läpinäkyvästi.

## Mitä Webswing ratkaisee

Monilla organisaatioilla on merkittäviä investointeja Java-työpöytäsovelluksiin, jotka sisältävät vuosien tai jopa vuosikymmenien aikana kehitettyä kriittistä liiketoimintalogikkaa. Näitä sovelluksia ei usein voi helposti kirjoittaa uusiksi seuraavista syistä:

- Monimutkainen domain-logiikka, jonka uudelleen luominen olisi riskialtista
- Integrointi työpöytätiettyihin kirjastoihin tai laitteisiin
- Ajan ja kustannusten rajoitteet täydessä kirjoittamisessa
- Tarve säilyttää ominaisuuspariteetti olemassa olevan toiminnallisuuden kanssa

Webswing mahdollistaa näiden sovellusten olevan verkkoselainkäyttöisiä ilman muutoksia, säilyttäen niiden alkuperäisen toiminnallisuuden ja ulkoasun.

## Integraatio webforJ:n kanssa

webforJ:n Webswing-integraatio tarjoaa `WebswingConnector`-komponentin, joka mahdollistaa Webswingin isännöimien sovellusten upottamisen suoraan webforJ-sovellukseesi. Tämä luo mahdollisuuksia:

### Progressiivinen modernisointi

Sen sijaan, että suoritat kokonaan uuden kirjoittamisen, voit:

1. Aloittaa upottamalla nykyisen Swing-sovelluksesi `WebswingConnector`in avulla
2. Rakentaa uusia ominaisuuksia webforJ:n ympärille upotetun sovelluksen
3. Vähitellen korvata Swing-komponentteja webforJ:n vastaavilla
4. Lopulta poistaa perinteinen sovellus kokonaan

### Hybridisovellukset

Yhdistä moderni verkkokäyttöliittymä, joka on rakennettu webforJ:llä, erityisellä työpöytätoiminnallisuudella:

- Käytä webforJ:tä käyttäjille suunnatuissa käyttöliittymissä, hallintapaneeleissa ja raporteissa
- Hyödynnä Swingiä monimutkaisissa visualisoinneissa tai erikoiseditorissa
- Säilytä yksi integroitu sovelluskokemus

## Kuinka se toimii

Integraatio toimii kolmen kerroksen kautta:

1. **Webswing-palvelin**: suorittaa Java-työpöytäsovelluksesi, vangitsee sen visuaalisen ulostulon ja käsittelee käyttäjän syötteen
2. **WebswingConnector-komponentti**: webforJ-komponentti, joka upottaa Webswing-asiakkaan halliten yhteyttä ja viestintää palvelimen kanssa
3. **Viestintäprotokolla**: kaksisuuntainen viestintä, joka mahdollistaa webforJ-sovelluksesi lähettää komentoja Swing-sovellukseen ja vastaanottaa tapahtumia takaisin

Kun käyttäjä pääsee webforJ-sovellukseesi, `WebswingConnector` luo yhteyden Webswing-palvelimeen. Palvelin luo tai ottaa yhteyden sovellusinstanssiin ja alkaa suoratoistaa visuaalista tilaa selaimeen. Käyttäjäinteraktiot (hiiri, näppäimistö) vangitaan ja lähetetään palvelimelle, jossa ne toistetaan todellisessa Swing-sovelluksessa.

## Aiheet {#topics}

<DocCardList className="topics-section" />
