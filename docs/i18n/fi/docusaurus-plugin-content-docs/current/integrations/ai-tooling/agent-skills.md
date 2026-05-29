---
title: Agent Skills
sidebar_position: 10
_i18n_hash: 0458a29cc4337ff83f08afb415097a1c
---
Agent Skills opettaa AI-koodausassistentteja rakentamaan webforJ-sovelluksia käyttäen oikeita API:ita, design-tokeneita ja komponenttimalleja. Sen sijaan, että arvaat kehyksen käytäntöjä, assistentti lataa taidon ja seuraa strukturoitua työnkulkua tuottaakseen koodia, joka kääntyy ja noudattaa parhaita käytäntöjä ensimmäisellä yrityksellä.

:::tip Käytä liitintä
Alla olevat taidot toimitetaan **[webforJ AI liitännäinen](/docs/integrations/ai-tooling)** yhdessä [MCP palvelimen](/docs/integrations/ai-tooling/mcp) kanssa. Yksi asennus antaa assistentillesi molemmat osat.
:::

Taidoissa noudatetaan avointa [Agent Skills](https://agentskills.io/specification) -standardia, ja ne toimivat monilla AI-assistenteilla, mukaan lukien Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex ja monet muut. Taito kertoo assistentille, minkä tyyppisestä tehtävästä on kyse; assistentti lataa sen automaattisesti, kun kehotteesi vastaa. Esimerkiksi kysyessäsi "teemaa tämä sovellus sinisellä paletilla" laukaisee `webforj-styling-apps` -taidon, joka ohjaa assistenttia etsimään voimassa olevia DWC-tokeneita, kirjoittamaan rajattua CSS:ää ja validoimaan jokaisen muuttujan nimen ennen kuin kirjoitetaan mitään levytilalle.

## Miksi käyttää taitoja? {#why-use-skills}

MCP-palvelin tekee tarkan webforJ-tiedon saataville tarvittaessa, mutta yksinään se ei kerro assistentille, _milloin_ etsiä jotain, _mikä_ lähestymistapa sopii tehtävään tai _missä järjestyksessä_ asiat tulisi tehdä. Siihen jäsenet tulevat mukaan.

Taidot antavat assistentille tehtäväkohtaisen pelikirjan: kuinka luokitella edessä oleva työ, mitkä webforJ-mallit sopivat, mitä MCP-työkaluja on käytettävä jokaisessa vaiheessa ja kuinka validoida tulos ennen sen palauttamista. Tuloksena on johdonmukainen, käytäntöjä noudattava webforJ-koodi sen sijaan, että se olisi kokoelma teknisesti kelvollisia mutta tyylillisesti epäsopivia pätkiä.

## Kuinka taidot eroavat MCP:stä {#how-skills-differ-from-mcp}

Taidot ja [webforJ MCP palvelin](/docs/integrations/ai-tooling/mcp) palvelevat täydentäviä rooleja. MCP-palvelin tarjoaa live-työkaluja, joita assistentti voi kutsua tietojen hakemiseen tai tuloksen tuottamiseen. Taidot tarjoavat työnkulun, joka kertoo assistentille _milloin_ kutsua näitä työkaluja, missä järjestyksessä asiat tulisi tehdä ja kuinka validoida tulos.

| | MCP palvelin | Agent-taidot |
|---|---|---|
| **Mitä se tarjoaa** | Työkalut, joita assistentti kutsuu tarvittaessa (asiakirjahaku, kehystys, teeman luonti, token-validation) | Työnkulut ja päätöstaulukot, jotka ohjaavat sitä, kuinka assistentti lähestyy tehtävää |
| **Milloin se toimii** | Kun assistentti päättää kutsua työkalua | Automaattisesti, kun assistentti havaitsee vastaavan tehtävän |
| **Parasta** | Spesifisiin kysymyksiin vastaaminen, artefaktien generointi | Päästä päähän -tehtävät, jotka tarvitsevat johdonmukaisen webforJ-lähestymistavan |

Käytännössä molemmat toimivat parhaiten yhdessä - ja [webforJ AI -liitännäinen](https://github.com/webforj/webforj-ai) toimitetaan yhdessä asennuksessa.

## Asennus {#installation}

Asenna **[webforJ AI liitännäinen](/docs/integrations/ai-tooling)** - se sisältää alla olevat taidot MCP-palvelimen ohella. Asiakkaille, jotka eivät tue liitännäisiä, [webforJ AI -varasto](https://github.com/webforj/webforj-ai#clients) luettelee taitohakemiston, josta jokainen työkalu lukee, joten voit kopioida taidonkansiot käsin.

## Saatavilla olevat taidot {#available-skills}

<AccordionGroup>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-adding-servlets</code></strong>: lisää REST-päätteitä, webhookkeja ja mukautettuja servletteja
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä, kun tarvitset ei-Käyttöliittymä HTTP-reittiä - REST-päätettä, webhook-käsittelijää tai kolmannen osapuolen servlettia, kuten Swagger UI tai Spring Web. Assistentti valitsee oikean lähestymistavan projektiisi (Spring `webforj.exclude-urls`, reittauksen `WebforjServlet` alikansioon tai proxyttamisen `webforj.conf`in kautta) ja kytkee päätteet ilman, että webforJ:n käyttöliittymän reititys häiriintyy.

**Milloin se aktivoituu**

- *"Lisää REST-pääte `/api/orders`."*
- *"Liitä webhook-käsittelijä Stripe:lle."*
- *"Asenna Swagger UI kohteeseen `/api/docs`."*
- *"Altista mukautettu servletti, joka toimii rinnakkain webforJ:n käyttöliittymän kanssa."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-building-forms</code></strong>: rakenna lomakkeita sitomenetelmällä, validoinnilla ja syötemaskilla
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä kaikessa lomatyössä webforJ-sovelluksessa: tietojen syöttölomakkeet, kaksisuuntainen sitominen Java-beaniin, Jakarta-validointi, maskatut syöttökohteet (puhelin, valuutta, IBAN, päivämäärät), Taulukon sarakkeiden muotoileminen valuutaksi tai prosenttina, ja responsiiviset monisarakkeet. Assistentti reitittää `BindingContext`:in, `Masked*Field` -komponenttien, taulukon maskintuottajien ja `ColumnsLayout`:n kautta.

**Milloin se aktivoituu**

- *"Rakenna rekisteröintilomake sidottuna `User` beanini."*
- *"Lisää puhelinnumerosyöttö, jossa muotoillaan kirjoittaessasi."*
- *"Muotoile tämä taulukon sarake valuutaksi."*
- *"Vahvista tämä kenttä `@NotEmpty`:llä ja mukautetulla sähköpostitarkistimella."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: kääri web-komponentit, JS-kirjastot tai koostumukset
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä, kun tarvitset uudelleenkäytettävän Java-komponentin, joka on kääritty minkä tahansa lähteen ympärille - olemassa olevan Mukautetun Elementin kirjaston, tavanomaisen JavaScript-kirjaston tai olemassa olevien webforJ-komponenttien koostumuksen. Assistentti valitsee työn kannalta oikean webforJ-perusluokan, kytkee ominaisuudet, tapahtumat ja slotit oikeilla malleilla ja tuottaa testejä, jotka noudattavat webforJ:n käytäntöjä.

**Milloin se aktivoituu**

- *"Kääri tämä Mukautettu Elementtiin kirjasto webforJ-komponenteiksi."*
- *"Koosta nämä webforJ-komponentit uudelleenkäytettäväksi kortiksi."*
- *"Integroi tämä tavallinen JavaScript-kirjasto webforJ-komponentiksi."*
- *"Altista tämä Selaimen API webforJ-apuna."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-handling-timers-and-async</code></strong>: aikatauluta ajastimia, debounceja ja asynkronista työtä
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä kausiluonteisiin tehtäviin, kyselyyn, viipaloituun hakuun kirjoittaessa, throttlingiin ja pitkiin taustatöihin, jotka päivittävät käyttöliittymää niiden kulkiessa. Assistentti valitsee oikean alkion (`Interval`, `Debouncer`, `Environment.runLater`, `PendingResult`) ja välttää ajonaikaiset ansat, jotka johtuvat raakojen `java.util.Timer`, `javax.swing.Timer` tai webforJ-ympäristön ulkopuolella luoduista säikeistä, jotka kaikki heittävät `IllegalStateException` heti, kun he koskettavat käyttöliittymäkomponenttia.

**Milloin se aktivoituu**

- *"Päivitä tämä ohjaamo 30 sekunnin välein."*
- *"Lisää haku kirjoittaessasi debounce."*
- *"Suorita tämä CPU:ta rasittava työ taustalla ja päivitä edistymispalkki."*
- *"Kysy tätä REST-päätettä, kunnes se palauttaa `done`."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-localizing-apps</code></strong>: lisää i18n ja käännöstuki
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä kaikessa internationalisaatiotyössä: viestikokoelmien lataus, kielenvaihto ajonaikaisesti, käyttäjän selainlokalisoinnin automaattinen havaitseminen ja komponenttilabelien kääntäminen. Assistentti reitittää webforJ 25.12:n `BundleTranslationResolver`:n, `HasTranslation` -huolen, `LocaleObserver`:in ja liitettäviä mukautettuja resolvereita käyttäen, ja kattaa sekä Spring- että tavalliset webforJ-polut.

**Milloin se aktivoituu**

- *"Lisää monikielinen tuki englannille ja espanjalle."*
- *"Havaitse käyttäjän selainlokalisointi ja sovella se käynnistyksen yhteydessä."*
- *"Lisää kielenvaihtaja navigointipalkkiin."*
- *"Siirrä kaikki kovakoodatut merkit viestikokoelmaan."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-securing-apps</code></strong>: suojaa reitit kirjautumisella ja roolipohjaisella pääsyllä
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä kaikessa, mikä suojaa reittejä webforJ-sovelluksessa: kirjautuminen ja uloskirjautuminen, roolipohjainen pääsy, julkiset laskeutumis sivut, admin-kohteet, omistajuus säännöt ja oletusarvoisesti turvalliset käytännöt. Assistentti suosii Spring Securityä, kun Spring Boot on luokkatunnisteessa, ja siirtyy webforJ:n tavanomaiseen turvallisuuskehykseen muissa tapauksissa. Se soveltaa oikeita annotaatioita (`@AnonymousAccess`, `@PermitAll`, `@RolesAllowed`, `@RouteAccess`, `@RegisteredEvaluator`) ja selittää, mitkä ovat päätöksentekovelvoitteita ja mitkä ovat koostettavissa, joten oletusarvoisesti turvallinen toiminta tekee sen, mitä se sanoo.

**Milloin se aktivoituu**

- *"Suojaa `/admin`, jotta vain käyttäjät, joilla on `ADMIN`-rooli, voivat nähdä sen."*
- *"Lisää julkinen laskeutumis sivu, jota kuka tahansa voi vierailla."*
- *"Näytä kirjautuneen käyttäjän nimi yläpalkissa."*
- *"Anna käyttäjän muokata vain omaa rekisteriään."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: teema sovellukset DWC design-tokeneilla
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä kaikessa visuaalisessa työssä webforJ-sovelluksessa: palettimuutokset, komponenttikohtainen tyylittely, asettelu ja väli, typografia, täydet teemat, taulukon ulkoasu tai koordinoidut Google Charts -värit. Assistentti kirjoittaa CSS:ää, joka noudattaa DWC design-tokeneita, rajaa valitsimia oikein ja validoi jokaisen `--dwc-*` viittauksen todelliseen luetteloon webforJ-versiollesi - joten tumman tilan ja teeman vaihtaminen toimii edelleen.

**Milloin se aktivoituu**

- *"Teemaa tämä sovellus sinisellä paletilla."*
- *"Tyyli dwc-painike brändiohjeiden mukaisesti."*
- *"Tee tämä asettelu tiukemmaksi - säädä väliä ja typografiaa."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-upgrading-versions</code></strong>: päivitä webforJ-pääversioiden yli OpenRewrite
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä pääversiopäivityksiin. Assistentti suorittaa virallisen `webforj-rewrite` OpenRewrite-reseptin kohdeversion latauksessa, joka nostaa `<webforj.version>` ja Java-julkaisun, kirjoittaa nimettyjä API:ita ja tyyppejä ja lisää `TODO webforJ <major>:` kommentteja jokaiseen poistettuun menetelmään, joka tarvitsee manuaalisen päätöksen. Vanhemmille kohteille, joilla ei ole julkaistua reseptiä (esimerkiksi 24:stä 25:teen), se ohjaa sinua manuaalisessa vaihtoehdossa.

**Milloin se aktivoituu**

- *"Päivitä tämä sovellus webforJ 25:stä 26:een."*
- *"Suorita uudelleenkirjoitusresepti ja ratkaise TODOt."*
- *"Siirrä webforJ 24:stä 25:een manuaalisesti, sillä reseptiä ei ole."*
- *"Mitä poistettuja API:ita minun pitää korjata päivityksen jälkeen?"*

</div>
  </AccordionDetails>
</Accordion>

</AccordionGroup>
