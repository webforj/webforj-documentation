---
title: Virheenjäljitys
sidebar_position: 15
description: >-
  Attach a remote Java debugger to a running webforJ app from Visual Studio
  Code, IntelliJ IDEA, or Eclipse using Jetty on port 8000.
_i18n_hash: 6e761f399c4a1f8d562acf85f92af615
---
Virheenkorjaus on olennainen osa Java-kehitystä, sillä se auttaa kehittäjiä tunnistamaan ja korjaamaan ongelmia tehokkaasti. Tässä oppaassa kerrotaan, miten virheenkorjaus määritetään webforJ:ssä Visual Studio Codea, IntelliJ IDEAa ja Eclipseä varten.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<VideoPlayer video="debugging/vscode" width={1920} height={1080} title="VS Code" />

1. Avaa webforJ-projektisi VS Codessa.
2. Avaa Suorita ja virheenkorjaus -paneeli painamalla <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (tai Macissa <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>).
3. Valitse ”create a launch.json file”.
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

6. Tallenna tiedosto ja valitse Aloita virheenkorjaus.

</TabItem>
<TabItem value="intellij" label="IntelliJ IDEA">

<VideoPlayer video="debugging/intellij" width={1920} height={1080} title="IntelliJ" />

1. Avaa projektisi IntelliJ IDEAssa.
2. Siirry kohtaan Suorita → Muokkaa määrityksiä.
3. Napsauta <kbd>+</kbd>-painiketta ja valitse Remote JVM Debug.
4. Määritä palvelimeksi `localhost` ja portiksi `8000`.
5. Tallenna määritys ja liitä virheenkorjaus käynnissä olevaan sovellukseen valitsemalla Debug.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<VideoPlayer video="debugging/eclipse" width={1920} height={1080} title="Eclipse" />

1. Avaa projektisi Eclipsessä.
2. Siirry kohtaan Suorita → Muokkaa määrityksiä.
3. Valitse Remote Java Application.
4. Valitse New Configuration ja määritä:
   - Palvelin: `localhost`
   - Portti: `8000`
5. Tallenna määritys ja käynnistä virheenkorjaus.

</TabItem>
</Tabs>

## Virheenkorjaimen käynnistäminen {#running-the-debugger}

Kun olet määrittänyt IDE:n:

1. Käynnistä webforJ-sovelluksesi komennolla `mvnDebug`.
2. Suorita virheenkorjausmääritys IDE:ssäsi.
3. Aseta keskeytyskohdat ja aloita virheenkorjaus.

:::tip Vinkkejä virheenkorjaukseen
1. Varmista, että portti 8000 on käytettävissä eikä palomuuri estä sitä.
2. Jos käytät jotakin webforJ-arkkityypeistä ja olet muuttanut portin numeroa pom.xml-tiedostossa, varmista, että virheenkorjauksessa käytettävä portti vastaa päivitettyä arvoa.
:::

## Käynnissä olevan sovelluksen tarkastelu {#inspecting-the-running-app}

Virheenkorjain näyttää, mitä koodisi tekee. [craftforJ](/docs/craftforj) näyttää koodin tuottaman sovelluksen, mukaan lukien webforJ:n luoman komponenttipuun, kunkin komponentin ominaisuudet, aktiivisen reitin ja sen, kenellä on siihen pääsy. Voit muuttaa ominaisuutta, nähdä muutoksen vaikutuksen käynnissä olevassa sovelluksessa ja kirjoittaa muutoksen takaisin sen tuottaneeseen Java-koodiin.

craftforJ toimitetaan webforJ:n mukana. Se käyttää samaa virheenkorjaustilaa, jonka olet jo ottanut käyttöön, ja lisäksi yhtä lisäominaisuutta:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Katso [craftforJ:n käytön aloittaminen](/docs/craftforj/getting-started).
