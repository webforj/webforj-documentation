---
sidebar_position: 1
title: Routing
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: ca4837305e1ca2ca2b6a4a244c8103f1
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

Nykyaikaisissa verkkosovelluksissa **reititys** viittaa prosessiin, jossa hallitaan navigointia eri näkymien tai komponenttien välillä nykyisen URL-osoitteen tai polun perusteella. webforJ:ssä reititys luo monimutkaisen kehyksen **asiakaspään navigaatiolle**, jossa käyttöliittymän päivitykset tapahtuvat dynaamisesti ilman täydellisiä sivulatauksia, mikä parantaa sovelluksen suorituskykyä.

## Perinteinen vs asiakaspään reititys {#traditional-vs-client-side-routing}

Perinteisessä palvelinpuolen reitityksessä, kun käyttäjä napsauttaa linkkiä, selain lähettää pyynnön palvelimelle uudelle asiakirjalle. Palvelin vastaa lähettämällä uuden HTML-sivun, mikä pakottaa selaimen arvioimaan CSS:n ja JavaScriptin uudelleen, renderöimään koko asiakirjan ja palauttamaan sovelluksen tilan. Tämä sykli aiheuttaa viiveitä ja tehottomuuksia, sillä selaimen on ladattava resursseja ja sivuston tila uudelleen. Prosessi sisältää yleensä:

1. **Pyyntö**: Käyttäjä navigoi uuteen URL-osoitteeseen, mikä laukaisee pyynnön palvelimelle.
2. **Vastaus**: Palvelin lähettää takaisin uuden HTML-asiakirjan yhdessä siihen liittyvien resurssien (CSS, JS) kanssa.
3. **Renderöinti**: Selain renderöi koko sivun uudelleen, usein menettämällä aikaisemmin ladattujen sivujen tilan.

Tämä lähestymistapa voi johtaa suorituskyvyn pullonkauloihin ja suboptimaisiin käyttäjäkokemuksiin, jotka johtuvat toistuvista täydellisistä sivulatauksista.

**Asiakaspään reititys** webforJ:ssä ratkaisee tämän mahdollistamalla navigoinnin suoraan selaimessa, dynaamisesti päivittämällä käyttöliittymän ilman, että lähetetään uutta pyyntöä palvelimelle. Tässä on, miten se toimii:

1. **Yksinkertainen alkuperäinen pyyntö**: Selain lataa sovelluksen kerran, mukaan lukien kaikki tarvittavat resurssit (HTML, CSS, JavaScript).
2. **URL-osoitetta hallinta**: Reititin kuuntelee URL-osoitteen muutoksia ja päivittää näkymän nykyisen reitin mukaan.
3. **Dynaaminen komponenttien renderöinti**: Reititin yhdistää URL-osoitteen komponenttiin ja renderöi sen dynaamisesti ilman sivun päivittämistä.
4. **Tilann säilyttäminen**: Sovelluksen tila säilyy navigoinnin aikana, mikä varmistaa sujuvan siirtymisen näkymien välillä.

Tämä suunnittelu mahdollistaa **syvän linkityksen** ja **URL-pohjaisen tilanhallinnan**, mikä antaa käyttäjille mahdollisuuden kirjanmerkitä ja jakaa tiettyjä sivuja sovelluksessa samalla, kun he nauttivat sujuvasta, yksisivuisesta kokemuksesta.

## Ydinperiaatteet {#core-principles}

- **URL-pohjainen komponenttien kartoitus**: webforJ:ssä reitit on suoraan sidottu käyttöliittymän komponentteihin. URL-malli on kartoitettu tiettyyn komponenttiin, joka määrää, mitä sisältöä näytetään nykyisen polun perusteella.
- **Deklaratiivinen reititys**: Reitit määritellään deklaratiivisesti, tyypillisesti käyttämällä annotaatioita. Jokainen reitti vastaa komponenttia, joka renderöidään, kun reitti täsmää.
- **Dynaaminen navigointi**: Reititin vaihtaa dynaamisesti näkymien välillä ilman, että sivua ladataan uudelleen, pitäen sovelluksen responsiivisena ja nopeana.

## Esimerkki asiakaspään reitityksestä webforJ:ssä {#example-of-client-side-routing-in-webforj}

Tässä on esimerkki reitin määrittämisestä `UserProfileView`-komponentille näyttämään käyttäjän tiedot URL-osoitteen `id`-parametrin perusteella:

```java
@Route(value = "user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String id = parameters.getAlpha("id").orElse("");
    refreshProfile(id);
  }
}
```

Tässä asetuksessa:

- Navigointi osoitteeseen `/user/john` renderöisi `UserProfileView`-komponentin.
- `id`-parametri kaappaisi `john`:in URL-osoitteesta ja mahdollistaisi sen käytön komponentissa käyttäjän tietojen hakemiseksi ja näyttämiseksi.

## Aiheet {#topics}

<DocCardList className="topics-section" />
