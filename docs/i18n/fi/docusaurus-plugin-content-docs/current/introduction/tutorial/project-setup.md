---
title: Project Setup
sidebar_position: 1
_i18n_hash: b1ac0a58b11558f40824c8caedeb95b3
---
 tässä oppaassa sovellus jaetaan **neljään vaiheeseen**, joista jokainen esittelee uusia ominaisuuksia projektin edetessä. Seuraamalla mukana saat selkeän käsityksen siitä, miten sovellus kehittyy ja miten kukin ominaisuus toteutetaan.

Aloittaaksesi voit ladata koko projektin tai kloonata sen GitHubista:
<!-- vale off -->
- Lataa ZIP: [webforj-demo-application.zip](https://github.com/webforj/webforj-demo-application/archive/refs/heads/main.zip)
- GitHub-repositorio: Kloonaa projekti [suoraan GitHubista](https://github.com/webforj/webforj-demo-application)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-demo-application.git
```

Sekä ZIP-tiedosto että GitHub-repositorio sisältävät täydellisen projektirakenteen kaikilla neljällä vaiheella, joten voit aloittaa mistä tahansa kohdasta tai seurata vaihe vaiheelta.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/project-setup.mp4" type="video/mp4"/>
  </video>
</div>

## Projektirakenne {#project-structure}

Projekti on jaettu neljään erilliseen hakemistoon, joista jokainen edustaa tiettyä vaihetta sovelluksen kehityksessä. Nämä vaiheet mahdollistavat sen näkemisen, miten sovellus kehittyy yksinkertaisesta kokoonpanosta täysin toimivaksi asiakashallintajärjestelmäksi.

Projektikansiossa löydät neljä alihakemistoa, jotka vastaavat oppaan vaiheita:

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

### Sovelluksen suorittaminen {#running-the-app}

Nähdäksesi sovelluksen toiminnassa missä tahansa vaiheessa:

1) Siirry halutun vaiheen hakemistoon. Tämä tulisi olla kyseisen vaiheen ylimmän tason hakemisto, joka sisältää `pom.xml`-tiedoston.

2) Käytä Maven Jetty -lisäosaa sovelluksen paikalliseen käyttöönottoon suorittamalla:

```bash
mvn jetty:run
```

3) Avaa selain ja siirry osoitteeseen http://localhost:8080 nähdäksesi sovelluksen.

Toista tämä prosessi jokaisessa vaiheessa seuraten opasta, jolloin voit tutkia sovelluksen ominaisuuksia niiden lisätessä.
