---
title: Troubleshooting
sidebar_position: 11
description: >-
  Fix the common cases where craftforJ doesn't appear, a feature is unavailable,
  or the assistant doesn't answer.
_i18n_hash: fcc5f7188c92523c0fb500bfc7b0ce58
---
### Mikään ei näy sivulla {#nothing-appears-on-the-page}

craftforJ liitetään vain, kun kaikki vaatimukset [Aloittaminen](/docs/craftforj/getting-started#requirements) on täytetty, ja se ei näytä mitään, kun jokin niistä puuttuu. Tarkista ne järjestyksessä: `webforj-devtools` riippuvuus luokkapolussa, virheenkorjaustila, craftforJ-omaisuus, selain koneella, jossa sovellus pyörii, ja voimassa oleva kehittäjälisenssi. Konfiguraatiotiedosto väärässä sijainnissa tai profiili, joka ohittaa jonkin omaisuuden, tuottaa täsmälleen saman tuloksen kuin omaisuuden ollessa pois päältä.

### Ominaisuus ei ole saatavilla {#a-feature-is-unavailable}

craftforJ näyttää pois päältä olevan ominaisuuden piilottamisen sijaan, joten ohjaus, joka on läsnä mutta merkitty tuettavaksi, on tarkoituksella kytketty pois päältä. Joko se on estetty [ominaisuuslipulla](/docs/craftforj/configuration#feature-flags) sovelluksen konfiguraatiossa tai `webforj-devtools` versio luokkapolussasi on vanhempi.

Kirjoittaminen lähteeseen tarvitsee myös projektin juuren, jonka craftforJ voi löytää. Tarkista se, jonka se havaitsi [Sovellustiedot](/docs/craftforj/app-info), ja aseta [`project-root`](/docs/craftforj/configuration#project-root), jos se on väärin.

### Java-validointi on heikompi kuin odotettu {#java-validation-is-weaker-than-expected}

Avustajan [käännösvalidointi](/docs/craftforj/ai#it-writes-java) tarvitsee JDK:n. Tarkista Java-versio [Sovellustiedoista](/docs/craftforj/app-info), ja suorita sovellus JDK:ssa sen sijaan, että käyttäisit JRE:tä.

### craftforJ näyttää vanhentuneelta päivityksen jälkeen {#craftforj-looks-out-of-date-after-an-update}

Selaimesi välimuisti on tallentanut aiemman version. Lataa sivu kovasti uudelleen, tai avaa sovellus yksityisessä ikkunassa. Jos ongelma jatkuu, varmista, mikä `webforj-devtools` versio on todella luokkapolussa [Sovellustiedoissa](/docs/craftforj/app-info), koska vanha jar-tiedosto paikallisessa Maven-repositoriossasi näyttää selaimesta samalta.

### Avustaja ei vastaa {#the-assistant-doesnt-answer}

Avustaja tarvitsee määritellyn tarjoajan ja mallin, joka voi käyttää työkaluja. Malli ilman työkalutukea voi pitää keskustelua, mutta se ei voi tarkistaa tai muuttaa mitään. Paikallinen malli, joka jatkuvasti unohtaa keskustelun, toimii yleensä liian pienellä kontekstikkunalla.

Jos paikallinen malli on määritelty ja saavutettavissa, mutta jokainen pyyntö hylätään, mallipalvelin hylkää sivun alkuperän. Ollama-palvelimessa salli alkuperä ja käynnistä se uudelleen:

```bash
launchctl setenv OLLAMA_ORIGINS "*"
pkill ollama && ollama serve
```

Linuxissa aseta `OLLAMA_ORIGINS` ympäristöön, josta Ollama käynnistetään, ja käynnistä se uudelleen.

### craftforJ sanoo, että sovellus käynnistyy uudelleen {#craftforj-says-the-app-is-restarting}

Sovelluksesi katoaa säännöllisesti kehityksessä, joka kerta kun se rakennetaan uudelleen. craftforJ raportoi, mitä tapahtuu jäätymättä, joten se näyttää, kun sovellus käynnistyy uudelleen tai sivu ladataan uudelleen, ja sen ohjaimet pysyvät toimettomina, kunnes sovellus on takaisin. Se yhdistää itsensä automaattisesti valintasi ja odottavien töidesi kanssa, joten mitään ei tarvitse tehdä kuin odottaa. Jos se ilmoittaa, että se ei voi tavoittaa sovellusta lainkaan, varmista, että sovellus edelleen pyörii ja lataa sivu uudelleen.

### Sovellus käynnistyy jatkuvasti uudelleen {#the-app-keeps-restarting}

Muutoksen soveltaminen lähteeseen käynnistää sovelluksen uudelleen, kuten on kuvattu [Sen jälkeen kun olet soveltanut](/docs/craftforj/source-changes#after-you-apply). Uudelleenkäynnistykset, jotka tapahtuvat ilman soveltamista, johtuvat rakennuksesi tiedostovahdista, eivät craftforJ:stä.

### Lokien kerääminen {#collecting-logs}

Ennen ongelman raportointia, kytke päälle yksityiskohtainen lokitus craftforJ-asetuksissa, tyhjennä loki, toista ongelma ja lataa sitten loki. Liitä se yhdessä [Sovellustietojen](/docs/craftforj/app-info) sisällön kanssa.
