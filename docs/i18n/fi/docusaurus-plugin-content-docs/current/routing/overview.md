---
sidebar_position: 1
title: Routing
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 115816519ca0212b84eb27923a74ca53
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

Nykyajan verkkosovelluksissa **reititys** viittaa prosessiin, jossa hallitaan navigointia eri näkymien tai komponenttien välillä nykyisen URL-osoitteen tai polun perusteella. webforJ:ssä reititys luo hienostuneen kehyksen **asiakasyhteyden** hallintaan, jossa käyttöliittymän päivitykset tapahtuvat dynaamisesti ilman täydellisiä sivun päivityksiä, parantaen sovelluksen suorituskykyä.

## Perinteinen vs asiakaspohjainen reititys {#traditional-vs-client-side-routing}

Perinteisessä palvelinpuoleisessa reitityksessä, kun käyttäjä napsauttaa linkkiä, selain lähettää palvelimelle pyynnön uudelle asiakirjalle. Palvelin vastaa lähettämällä uuden HTML-sivun, mikä pakottaa selaimen arvioimaan CSS:n ja JavaScriptin uudelleen, renderöimään koko asiakirjan uudelleen ja nollaamaan sovelluksen tilan. Tämä sykle tulee mukana viiveitä ja tehottomuutta, sillä selaimen on ladattava resursseja ja sivun tila uudelleen. Prosessi sisältää tyypillisesti:

1. **Pyyntö**: Käyttäjä navigoi uuteen URL-osoitteeseen, mikä laukaisee palvelimelle pyynnön.
2. **Vastaus**: Palvelin lähettää takaisin uuden HTML-asiakirjan yhdessä siihen liittyvien resurssien (CSS, JS) kanssa.
3. **Renderöinti**: Selain renderöi koko sivun uudelleen, mikä usein johtaa aiemmin ladattujen sivujen tilan häviämiseen.

Tämä lähestymistapa voi johtaa suorituskyvyn pullonkauloihin ja suboptimaisiin käyttäjäkokemuksiin toistuvien täydellisten sivupäivitysten vuoksi.

**Asiakaspohjainen reititys** webforJ:ssa ratkaisee tämän mahdollistamalla navigoinnin suoraan selaimessa, dynaamisesti päivittämällä käyttöliittymän ilman uutta pyyntöä palvelimelle. Näin se toimii:

1. **Yksi Alkuperäinen Pyyntö**: Selain lataa sovelluksen kerran, mukaan lukien kaikki tarvittavat resurssit (HTML, CSS, JavaScript).
2. **URL-hallinta**: Reititin kuuntelee URL-osoitteen muutoksia ja päivittää näkymän nykyisen reitin perusteella.
3. **Dynaaminen Komponenttien Renderöinti**: Reititin mappaa URL-osoitteen komponenttiin ja renderöi sen dynaamisesti ilman sivun päivitystä.
4. **Tilanteen Säilyttäminen**: Sovelluksen tila säilyy navigaatioiden välillä, varmistaen sujuvan siirtymisen näkymien välillä.

Tämä suunnittelu mahdollistaa **syvälliset linkitykset** ja **URL-pohjaisen tilan hallinnan**, jolloin käyttäjät voivat kirjanmerkkijuttia ja jakaa erityisiä sivuja sovelluksessa nauttien samalla sujuvasta, yksisivuisesta kokemuksesta.

## Ydinasetukset {#core-principles}

- **URL-pohjainen Komponenttien Mappaus**: webforJ:ssä reitit ovat suoraan sidottuja käyttöliittymäkomponentteihin. URL-malli on mappattu tiettyyn komponenttiin, mikä määrittää, mitä sisältöä näytetään nykyisen polun perusteella.
- **Deklaratiivinen Reititys**: Reitit määritellään deklaratiivisesti, tyypillisesti käyttäen annotaatioita. Jokainen reitti vastaa komponenttia, joka renderöidään, kun reitti vastaa.
- **Dynaaminen Navigointi**: Reititin vaihtaa dynaamisesti näkymien välillä ilman sivun päivitystä, pitäen sovelluksen responsiivisena ja nopeana.

## Esimerkki asiakaspohjaisesta reitityksestä webforJ:ssa {#example-of-client-side-routing-in-webforj}

Tässä on esimerkki reitin määrittämisestä `UserProfileView`-komponentille näyttämään käyttäjän tietoja URL-osoitteen `id`-parametrin perusteella:

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

- Navigoiminen osoitteeseen `/user/john` renderöi `UserProfileView`-komponentin.
- `id`-parametri kaappaa `john` URL-osoitteesta ja mahdollistaa sen käytön komponentissa käyttäjätietojen hakemiseen ja näyttämiseen.

## Aiheita {#topics}

<DocCardList className="topics-section" />
