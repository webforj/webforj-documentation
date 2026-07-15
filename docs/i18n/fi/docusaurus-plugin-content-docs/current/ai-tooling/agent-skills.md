---
title: Agent Skills
sidebar_position: 10
description: >-
  Install Agent Skills so AI coding assistants follow webforJ workflows for
  building forms, adding servlets, styling apps, and creating components.
_i18n_hash: 6dc21bfd21fb27f2e71cb2265f6cde8c
---
Agent Skills opettavat AI-koodausassistentteja rakentamaan webforJ-sovelluksia oikeiden API:en, suunnittelutunnusten ja komponenttimallien avulla. Sen sijaan, että arvailisit kehyskonventioita, avustaja lataa taidon ja seuraa rakenteellista työnkulkua tuottaakseen koodia, joka kääntyy ja noudattaa parhaita käytäntöjä ensimmäisellä yrityksellä.

:::tip Käytä liitännäistä
Alla olevat taidot tulevat **[webforJ AI -liitännäisen](/docs/ai-tooling)** mukana yhdessä [MCP-palvelimen](/docs/ai-tooling/mcp) kanssa. Yksi asennus antaa avustajalle molemmat osat.
:::

Taidoilla on avoin [Agent Skills](https://agentskills.io/specification) -standardi, ja ne toimivat monilla AI-avustajilla, kuten Claude Code, GitHub Copilot, Cursor, Gemini CLI, OpenAI Codex ja muilla. Taito kertoo avustajalle, minkälaista tehtävää se käsittelee; avustaja lataa sen automaattisesti, kun pyyntösi täsmää. Esimerkiksi kysyttäessä "teema tämä sovellus sinisen paletin mukaan" aktivoidaan `webforj-styling-apps` -taito, joka ohjaa avustajaa etsimään voimassa olevia DWC-tunnuksia, kirjoittamaan rajoitettua CSS:ää ja validoimaan jokaisen muuttujan nimen ennen kuin mitään kirjoitetaan levyke.

## Miksi käyttää taitoja? {#why-use-skills}

MCP-palvelin tekee tarkan webforJ-tiedon saataville tarpeen mukaan, mutta yksinään se ei kerro avustajalle _milloin_ jotain pitäisi etsiä, _mikä_ lähestymistapa sopii tehtävään tai _missä järjestyksessä_ asioita pitäisi tehdä. Siinä taitot tulevat mukaan.

Taidoilla on avustajalle tehtäväspecifinen pelikirja: kuinka luokitella edessä oleva työ, mitkä webforJ-mallit sopivat, mitkä MCP-työkalut tulee konsultoida jokaisessa vaiheessa ja kuinka validoida tulos ennen sen palauttamista. Tuloksena on johdonmukainen, konventioita noudattava webforJ-koodi sen sijaan, että se olisi kokoelma teknisesti voimassa olevia, mutta tyyliltään epäsopivia koodinpätkiä.

## Kuinka taidot eroavat MCP:stä {#how-skills-differ-from-mcp}

Taidot ja [webforJ MCP-palvelin](/docs/ai-tooling/mcp) palvelevat täydentäviä rooleja. MCP-palvelin tarjoaa live-työkaluja, joita avustaja voi kutsua hakemaan tietoa tai tuottamaan sisältöä. Taidot tarjoavat työnkulun, joka kertoo avustajalle _milloin_ käyttää näitä työkaluja, missä järjestyksessä asioita pitäisi tehdä ja kuinka validoida tulosta.

| | MCP-palvelin | Agent-taitoja |
|---|---|---|
| **Mitä se tarjoaa** | Työkaluja, joita avustaja kutsuu tarpeen mukaan (dokumenthaku, kehystys, teeman generointi, tunnusten validointi) | Työnkulkuja ja päätöstauluja, jotka ohjaavat avustajaa lähestymään tehtävää |
| **Milloin se toimii** | Kun avustaja päättää kutsua työkalua | Automaattisesti, kun avustaja havaitsee vastaavan tehtävän |
| **Paras käyttötarkoitus** | Erityisiin kysymyksiin vastaaminen, artefaktien luominen | End-to-end-tehtävät, jotka tarvitsevat yhdenmukaisen webforJ-lähestymistavan |

Käytännössä nämä kaksi toimivat parhaiten yhdessä - ja [webforJ AI -liitännäinen](https://github.com/webforj/webforj-ai) toimitetaan yhtenä asennuksena.

## Asennus {#installation}

Asenna **[webforJ AI -liitännäinen](/docs/ai-tooling)** - se sisältää molemmat alla olevat taidot yhdessä MCP-palvelimen kanssa. Asiakkaille, jotka eivät tue liitännäisiä, [webforJ AI -repositorio](https://github.com/webforj/webforj-ai#clients) luettelee taitohakemiston, josta jokainen työkalu lukee, jotta voit kopioida taitokansiot käsin.

## Saatavilla olevat taidot {#available-skills}

<AccordionGroup>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-adding-servlets</code></strong>: lisää REST-pisteitä, webhookpeja ja mukautettuja servlettejä
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä, kun tarvitset ei-Käyttöliittymää HTTP-reittiä - REST-pistettä, webhook-käsittelijää tai kolmannen osapuolen servletiä kuten Swagger UI tai Spring Web. Avustaja valitsee oikean lähestymistavan projektiisi (Spring `webforj.exclude-urls`, `WebforjServlet` -reitin muuntaminen alireitiksi tai välitys `webforj.conf` kautta) ja kytkee reitin ilman, että se häiritsee webforJ:n käyttöliittymän reititystä.

**Milloin se aktivoituu**

- *"Lisää REST-piste `/api/orders`."*
- *"Liitä webhook-käsittelijä Stripelle."*
- *"Liitä Swagger UI `/api/docs`."*
- *"Altista mukautettu servlet, joka toimii webforJ-käyttöliittymän rinnalla."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-building-forms</code></strong>: rakenna lomakkeita siteerauksella, validoinnilla ja syöttömaskeilla
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä kaikelle lomaketyölle webforJ-sovelluksessa: tietojen syöttölomakkeet, kaksisuuntainen siteeraus Java-oliolle, Jakarta-validointi, maskatut syöttökomponentit (puhelin, valuutta, IBAN, päivämäärät), taulukon sarakkeiden muotoilu valuuttana tai prosentteina, ja responsiiviset monisarakkeet. Avustaja kuljettaa `BindingContext`:in, `Masked*Field` -komponentit, taulukon maskerin renderöijät ja `ColumnsLayout`.

**Milloin se aktivoituu**

- *"Rakenna rekisteröintilomake, joka on sidottu `User`-oliini."*
- *"Lisää puhelinnumeron syöttö, jossa muotoilu tapahtuu kirjoittaessasi."*
- *"Muotoile tämä taulukon sarake valuuttana."*
- *"Validoi tämä kenttä `@NotEmpty` ja mukautetun sähköpostintarkistimen avulla."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-creating-components</code></strong>: kääri web-komponentit, JS-kirjastot tai koostumukset
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä, kun tarvitset uudelleenkäytettävää Java-komponenttia, joka on kääritty minkä tahansa lähteen ympärille - olemassa olevan Custom Element -kirjaston, tavallisen JavaScript-kirjaston, tai olemassa olevien webforJ-komponenttien koostumuksen. Avustaja valitsee oikean webforJ-perusluokan tehtävään, kytkee ominaisuudet, tapahtumat ja slotit oikeilla kaavoilla, ja tuottaa testit, jotka noudattavat webforJ-konventioita.

**Milloin se aktivoituu**

- *"Kääri tämä Custom Element -kirjasto webforJ-komponenteiksi."*
- *"Koosta nämä webforJ-komponentit uudelleenkäytettäväksi kortiksi."*
- *"Integroi tämä tavallinen JavaScript-kirjasto webforJ-komponenttina."*
- *"Altista tämä Selain-API webforJ-apuna."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-handling-timers-and-async</code></strong>: aikataulu ajastimia, debouncereita ja asynkronista työtä
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä toistuville tehtäville, kyselylle, debouncattulle haku-toiminnolle, throttlingille ja pitkäaikaiselle taustatyölle, joka päivittää käyttöliittymää sen aikana. Avustaja valitsee oikean primitiivin (`Interval`, `Debouncer`, `Environment.runLater`, `PendingResult`) ja välttää suoritusajan ansat, jotka johtuvat raaka `java.util.Timer`, `javax.swing.Timer` tai silmukoista, jotka luodaan webforJ-ympäristön ulkopuolella, joista jokainen heittää `IllegalStateException`, heti kun ne koskettavat käyttöliittymäkomponenttia.

**Milloin se aktivoituu**

- *"Päivitä tämä ohjaamo joka 30 sekunti."*
- *"Lisää haku-toiminnolle debouncer."*
- *"Suorita tämä CPU:ta kuluttava työ taustalla ja päivitä prosenttipalkki."*
- *"Tee kysely tälle REST-pisteelle, kunnes se palauttaa `done`."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-localizing-apps</code></strong>: lisää i18n- ja käännöstuki
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä kaikelle kansainvälistämistyölle: viestikokoelmien lataamiselle, kielten vaihtamiselle ajoituksessa, käyttäjän selaimen paikallisen tunnistamiselle ja komponenttien etikettien kääntämiselle. Avustaja kuljettaa webforJ 25.12:n `BundleTranslationResolver`:in, `HasTranslation` -huolen, `LocaleObserver`:n ja liitettäviä mukautettuja ratkaisijoita, ja kattaa sekä Spring- että tavalliset webforJ-reitit.

**Milloin se aktivoituu**

- *"Lisää monikielituki englannille ja espanjalle."*
- *"Tunnista käyttäjän selaimen paikallinen tunnus ja sovella se käynnistyksessä."*
- *"Lisää kielivalitsin navigaatiopalkkiin."*
- *"Siirrä kaikki kovakoodatut merkkijonot viestikokoelmaan."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-securing-apps</code></strong>: suojaa reitit kirjautumisella ja roolipohjaisella pääsylle
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä kaikkeen, mikä suojaa reittejä webforJ-sovelluksessa: kirjautuminen ja uloskirjautuminen, roolipohjainen pääsy, julkiset laskeutumissivut, vain adminille tarkoitetut osiot, omistus säännöt ja oletussuojauskäytännöt. Avustaja suosii Spring Securityä, kun Spring Boot on luokkapathissa, ja käyttää muuten webforJ:n tavallista turvallisuuskehystä. Se soveltaa oikeita annotaatioita (`@AnonymousAccess`, `@PermitAll`, `@RolesAllowed`, `@RouteAccess`, `@RegisteredEvaluator`) ja selittää, mitkä ovat terminaaleja ja mitkä koottavissa, jotta oletussuojaus toimii kuten pitää.

**Milloin se aktivoituu**

- *"Suojaa `/admin`, jotta vain `ADMIN`-roolin käyttäjät näkevät sen."*
- *"Lisää julkinen laskeutumissivu, jota kuka tahansa voi vierailla."*
- *"Näytä kirjautuneen käyttäjän nimi yläpalkissa."*
- *"Salli vain käyttäjän muokata tietuetta, jonka omistaa."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-styling-apps</code></strong>: teeman sovellukset DWC-suunnittelutunnuksilla
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä kaikelle visuaaliselle työlle webforJ-sovelluksessa: paletti-uudelleenmuotoilu, komponenttitason tyylittely, asettelu ja välistys, typografia, koko teemat, taulukon ulkonäkö tai koordinoidut Google Charts -värit. Avustaja kirjoittaa CSS:n, joka pitää kiinni DWC-suunnittelutunnuksista, rajoittaa valitsimet oikein ja validoi jokaisen `--dwc-*` viittauksen todellista luetteloa vastaan webforJ-versiollesi - joten tummat tilat ja teeman vaihtaminen toimivat edelleen.

**Milloin se aktivoituu**

- *"Teema tämä sovellus sinisen paletin mukaan."*
- *"Muotoile dwc-painike vastaamaan brändin ohjeita."*
- *"Tee tämä asettelu tiukemmaksi - säädä välistystä ja typografiaa."*

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>webforj-upgrading-versions</code></strong>: päivitä webforJ-päivitykset OpenRewrite:n avulla
  </AccordionSummary>
  <AccordionDetails>
    <div>

Käytä tätä suurten versiotason päivityksiin. Avustaja suorittaa virallisen `webforj-rewrite` OpenRewrite-reseptin kohdeversiolle, joka päivittää `<webforj.version>` ja Java-julkaisun, muuttaa nimeä vaihtaneet API:t ja tyypit, ja lisää `TODO webforJ <major>:` -kommentteja jokaiselle poistettavalle metodille, joka vaatii manuaalista päätöstä. Vanhemmille kohteille, joista ei ole julkaistu reseptiä (esimerkiksi 24:stä 25:een), se opastaa manuaaliseen vaihtoehtoon.

**Milloin se aktivoituu**

- *"Päivitä tämä sovellus webforJ:stä 25:stä 26:een."*
- *"Suorita uudelleen kirjoitusresepti ja ratkaise TODO: t."*
- *"Siirrä webforJ:stä 24:stä 25:een manuaalisesti, koska reseptiä ei ole."*
- *"Mitä poistettuja API:ita minun täytyy korjata päivittämisen jälkeen?"*

</div>
  </AccordionDetails>
</Accordion>

</AccordionGroup>
