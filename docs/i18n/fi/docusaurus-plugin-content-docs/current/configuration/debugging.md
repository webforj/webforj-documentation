---
title: Debugging
sidebar_position: 15
description: >-
  Attach a remote Java debugger to a running webforJ app from Visual Studio
  Code, IntelliJ IDEA, or Eclipse using Jetty on port 8000.
sidebar_class_name: updated-content
_i18n_hash: c7b0a48745ef8f5793e38a3dd7691176
---
Virheenkorjaus on olennainen osa Java-kehitystä, ja se auttaa kehittäjiä tunnistamaan ja korjaamaan ongelmia tehokkaasti. Tämä opas selittää, kuinka konfiguroidaan virheenkorjaus webforJ:ssä Visual Studio Codessa, IntelliJ IDEA:ssa ja Eclipsessä.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/vscode.mp4" type="video/mp4" />
      </video>
</div>

1. Avaa webforJ-projektisi VS Codessa.
2. Paina <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (tai <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> Macilla) avataksesi Suorita ja virheenkorjaus -paneelin.
3. Napsauta "luo launch.json-tiedosto"
4. Valitse ympäristöksi Java.
5. Muokkaa `launch.json` seuraavaksi:

```json title="launch.json"
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Liity Jettyyn",
      "request": "attach",
      "hostName": "localhost",
      "port": 8000
    }
  ]
}
```

6. Tallenna tiedosto ja napsauta Käynnistä virheenkorjaus.

</TabItem>
<TabItem value="intellij" label="IntelliJ IDEA">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/intellij.mp4" type="video/mp4" />
      </video>
</div>

1. Avaa projektisi IntelliJ IDEA:ssa.
2. Siirry kohtaan Suorita → Muokkaa konfiguraatioita.
3. Napsauta <kbd>+</kbd> -painiketta ja valitse Etä-JVM-virheenkorjaus.
4. Aseta isäntäkoneeksi `localhost` ja portiksi `8000`.
5. Tallenna konfiguraatio ja napsauta Virheenkorjaus liittääksesi käynnissä olevaan sovellukseen.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/eclipse.mp4" type="video/mp4" />
      </video>
</div>

1. Avaa projektisi Eclipsessä.
2. Siirry kohtaan Suorita → Muokkaa konfiguraatioita.
3. Valitse Etäinen Java-sovellus.
4. Napsauta Uusi konfiguraatio ja aseta:
   - Isäntä: `localhost`
   - Portti: `8000`
5. Tallenna ja käynnistä virheenkorjaus.

</TabItem>
</Tabs>

## Virheenkorjaimen käyttö {#running-the-debugger}

Kun olet konfiguroinut IDE:si:

1. Käynnistä webforJ-sovelluksesi vastaavalla komennolla:
    - Jettylle käytä `mvnDebug jetty:run`
    - Spring Bootille käytä `mvnDebug spring-boot:run`
2. Suorita virheenkorjauskonfiguraatio IDE:ssäsi.
3. Aseta katkokohdat ja ala virheenkorjata.

:::tip Virheenkorjausvinkit
1. Varmista, että portti 8000 on käytettävissä eikä minkään palomuurin estämä.
2. Jos käytät jotain webforJ-mallia ja olet muuttanut porttinumeroa pom.xml-tiedostossa, varmista, että virheenkorjauksessa käytettävä portti vastaa päivitettyä arvoa.
:::

## Käynnissä olevan sovelluksen tarkastelu {#inspecting-the-running-app}

Virheenkorjausohjelma näyttää, mitä koodisi tekee. [craftforJ](/docs/craftforj) näyttää sinulle sovelluksen, jonka koodi tuotti, mukaan lukien komponenttipuun, jonka webforJ rakensi, kunkin komponentin ominaisuudet, mikä reitti on aktiivinen ja kuka voi käyttää sitä. Voit muuttaa ominaisuutta, nähdä tuloksen käynnissä olevassa sovelluksessa ja kirjoittaa muutoksen takaisin siihen Javaan, josta se tuli.

craftforJ toimitetaan webforJ:n mukana ja käyttää samaa virheenkorjaustilaa, jonka olet jo mahdollistanut, plus yhden lisäominaisuuden:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Katso [Aloitus craftforJ:n kanssa](/docs/craftforj/getting-started).
