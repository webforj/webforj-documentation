---
title: Virheenkorjaus
sidebar_position: 15
description: >-
  Attach a remote Java debugger to a running webforJ app from Visual Studio
  Code, IntelliJ IDEA, or Eclipse using Jetty on port 8000.
_i18n_hash: d3f71dbbc660b90f2f4c33fd071c9819
---
Virheenkorjaus on olennainen osa Java-kehitystä, sillä se auttaa kehittäjiä tunnistamaan ja korjaamaan ongelmia tehokkaasti. Tässä oppaassa kerrotaan, miten virheenkorjaus määritetään webforJ:ssä Visual Studio Codea, IntelliJ IDEAa ja Eclipseä varten.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/vscode.mp4" type="video/mp4" />
      </video>
</div>

1. Avaa webforJ-projektisi VS Codessa.
2. Avaa Suorita ja virheenkorjaus -paneeli painamalla <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (tai Macissa <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>).
3. Napsauta "create a launch.json file".
4. Valitse ympäristöksi Java.
5. Muokkaa `launch.json`-tiedostoa seuraavasti:

```json title="launch.json"
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Attach to Jetty",
      "request": "attach",
      "hostName": "localhost",
      "port": 8000
    }
  ]
}
```

6. Tallenna tiedosto ja napsauta Aloita virheenkorjaus.

</TabItem>
<TabItem value="intellij" label="IntelliJ IDEA">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/intellij.mp4" type="video/mp4" />
      </video>
</div>

1. Avaa projektisi IntelliJ IDEAssa.
2. Valitse Run → Edit Configurations.
3. Napsauta <kbd>+</kbd>-painiketta ja valitse Remote JVM Debug.
4. Aseta palvelimeksi `localhost` ja portiksi `8000`.
5. Tallenna määritys ja liitä se käynnissä olevaan sovellukseen napsauttamalla Debug.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/eclipse.mp4" type="video/mp4" />
      </video>
</div>

1. Avaa projektisi Eclipsessä.
2. Valitse Run → Edit Configurations.
3. Valitse Remote Java Application.
4. Napsauta New Configuration ja määritä:
   - Host: `localhost`
   - Port: `8000`
5. Tallenna määritys ja käynnistä virheenjäljitin.

</TabItem>
</Tabs>

## Virheenkorjaimen suorittaminen {#running-the-debugger}

Kun olet määrittänyt IDE:n:

1. Käynnistä webforJ-sovelluksesi komennolla `mvnDebug`.
2. Suorita virheenkorjausmääritys IDE:ssäsi.
3. Aseta keskeytyskohdat ja aloita virheenkorjaus.

:::tip Virheenkorjausvinkkejä
1. Varmista, että portti 8000 on käytettävissä eikä palomuuri estä sitä.
2. Jos käytät jotakin webforJ-arkkityyppiä ja olet muuttanut porttinumeroa pom.xml-tiedostossa, varmista, että virheenkorjauksessa käytetään uutta porttinumeroa.
:::

## Käynnissä olevan sovelluksen tarkastelu {#inspecting-the-running-app}

Virheenkorjain näyttää, mitä koodisi tekee. [craftforJ](/docs/craftforj) näyttää sovelluksen, jonka koodi tuotti. Näet esimerkiksi webforJ:n luoman komponenttipuun, kunkin komponentin ominaisuudet, aktiivisen reitin ja sen, kenellä on pääsy reitille. Voit muuttaa ominaisuutta, nähdä muutoksen vaikutuksen käynnissä olevassa sovelluksessa ja kirjoittaa muutoksen takaisin sitä vastaavaan Java-koodiin.

craftforJ toimitetaan webforJ:n mukana, ja se käyttää samaa virheenkorjaustilaa, jonka olet jo ottanut käyttöön, sekä yhtä lisäominaisuutta:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Katso [craftforJ:n käytön aloittaminen](/docs/craftforj/getting-started).
