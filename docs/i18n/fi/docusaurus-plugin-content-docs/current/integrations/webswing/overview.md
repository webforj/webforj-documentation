---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 8dcd8fdee2734f6b4b243b0ea82fa1c2
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing on verkkopalvelinteknologia, joka mahdollistaa Java-työpöytäsovellusten (Swing, JavaFX, SWT) käytön verkkoselaimessa ilman muutoksia alkuperäiseen lähdekoodiin. Se suorittaa työpöytäsovelluksen palvelimella ja suoratoistaa käyttöliittymän selaimeen HTML5-kankaan avulla, käsitellen kaikki käyttäjävuorovaikutukset läpinäkyvästi.

## Mitä Webswing ratkaisee

Monilla organisaatioilla on merkittäviä investointeja Java-työpöytäsovelluksiin, jotka sisältävät kriittistä liiketoimintalogiikkaa, joka on kehitetty vuosien tai jopa vuosikymmenien saatossa. Näitä sovelluksia ei usein voida helposti kirjoittaa uudelleen, koska:

- Monimutkainen alueellinen logiikka, jonka uudelleenluominen olisi riskialtista
- Integraatio työpöytäkohtaisiin kirjastoihin tai laitteistoihin
- Aika- ja kustannusrajoitukset täydelle uudelleenkirjoitukselle
- Tarve säilyttää ominaisuuspariteetti olemassa olevan toiminnallisuuden kanssa

Webswing mahdollistaa näiden sovellusten verkkopääsyn ilman muutoksia, säilyttäen niiden alkuperäiset toiminteet ja ulkoasun.

## Integraatio webforJ:n kanssa

webforJ:n Webswing-integraatio tarjoaa `WebswingConnector`-komponentin, joka mahdollistaa Webswingin isännöimien sovellusten upottamisen suoraan webforJ-sovellukseesi. Tämä luo mahdollisuuksia:

### Progressiivinen modernisointi

Sen sijaan, että suoritat kaiken tai ei mitään -uudelleenkirjoitusta, voit:

1. Aloittaa upottamalla olemassa olevan Swing-sovelluksesi `WebswingConnector`in kautta
2. Rakentaa uusia ominaisuuksia webforJ:n ympärille upotetun sovelluksen
3. Vähitellen korvata Swing-komponentit webforJ-vastaavilla
4. Lopulta poistaa perintösovelluksen kokonaan

### Hybridisovellukset

Yhdistä moderni verkkokäyttöliittymä, joka on rakennettu webforJ:llä, erikoistuneeseen työpöytätoteutukseen:

- Käytä webforJ:ta käyttäjille suunnatuissa käyttöliittymissä, hallintapaneeleissa ja raporteissa
- Hyödynnä Swingiä monimutkaisissa visualisoinneissa tai erikoistoimittimissa
- Säilytä yksi integroitu sovelluskokemus

## Kuinka se toimii

Integraatio toimii kolmen kerroksen kautta:

1. **Webswing-palvelin**: suorittaa Java-työpöytäsovellustasi, kaappaamalla sen visuaalisen lähtönsä ja käsitellen käyttäjän syötteen
2. **WebswingConnector-komponentti**: webforJ-komponentti, joka upottaa Webswing-asiakkaan, hallinnoi yhteyttä ja viestintää palvelimen kanssa
3. **Viestintäprotokolla**: kaksisuuntainen viestintä, joka sallii webforJ-sovelluksesi lähettää komentoja Swing-sovellukseen ja vastaanottaa tapahtumia takaisin

Kun käyttäjä käyttää webforJ-sovellustasi, `WebswingConnector` luo yhteyden Webswing-palvelimeen. Palvelin luo tai yhdistää sovelluksen instanssin ja alkaa suoratoistaa visuaalista tilaa selaimeen. Käyttäjävuorovaikutukset (hiiri, näppäimistö) kaapataan ja lähetetään palvelimelle, jossa ne toistetaan tosiasiallisessa Swing-sovelluksessa.

## Aiheita {#topics}

<DocCardList className="topics-section" />
