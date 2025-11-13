---
title: Projektin asetukset
sidebar_position: 1
_i18n_hash: f8ad0e22acf56c824b05db580be2203b
---
Tässä oppaassa sovellus jaetaan **neljään vaiheeseen**, joista jokainen esittelee uusia ominaisuuksia projektin edetessä. Seuraamalla mukana saat selkeän käsityksen siitä, kuinka sovellus kehittyy ja kuinka kukin ominaisuus toteutetaan.

Aloittaaksesi voit ladata koko projektin tai kloonata sen GitHubista:
<!-- vale off -->
- Lataa ZIP: [webforj-demo-sovellus.zip](https://github.com/webforj/webforj-demo-application/archive/refs/heads/main.zip)
- GitHub-repositorio: Kloa projektin [suoraan GitHubista](https://github.com/webforj/webforj-demo-application)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-demo-application.git
```

Sekä ZIP-tiedosto että GitHub-repositorio sisältävät täydellisen projektirakenteen kaikilla neljällä vaiheella, joten voit aloittaa mistä tahansa tai seurata vaihe vaiheelta.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/project-setup.mp4" type="video/mp4"/>
  </video>
</div>

## Projektin rakenne {#project-structure}

Projekti on jaettu neljään erilliseen hakemistoon, joista kukin edustaa tiettyä vaihetta sovelluksen kehityksessä. Nämä vaiheet auttavat sinua näkemään, kuinka sovellus kehittyy yksinkertaisesta asetuksesta täysin toimivaksi asiakashallintajärjestelmäksi.

Projektikansion sisällä löydät neljä alihakemistoa, jotka vastaavat oppaan vaiheita:

```
webforj-demo-application
│   .gitignore
│   LICENSE
│   README.md
│   tree.txt
│
├───1-creating-a-basic-app  
├───2-working-with-data
├───3-scaling-with-routing-and-composites
└───4-validating-and-binding-data
```

### Sovelluksen käynnistäminen {#running-the-app}

Nähdäksesi sovelluksen toiminnassa missä tahansa vaiheessa:

1) Siirry halutun vaiheen hakemistoon. Tämä pitäisi olla kyseisen vaiheen ykkösluokan hakemisto, joka sisältää `pom.xml`-tiedoston.

2) Käytä Maven Jetty -lisäosaa sovelluksen paikalliseen käyttöönottoon suorittamalla:

```bash
mvn jetty:run
```

3) Avaa selain ja siirry osoitteeseen http://localhost:8080 nähdäksesi sovelluksen.

Toista tämä prosessi jokaiselle vaiheelle seuratessasi opasta, jolloin voit tutkia sovelluksen ominaisuuksia, kun niitä lisätään.
