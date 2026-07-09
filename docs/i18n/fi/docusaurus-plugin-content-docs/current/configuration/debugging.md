---
title: Debugging
sidebar_position: 15
description: >-
  Attach a remote Java debugger to a running webforJ app from Visual Studio
  Code, IntelliJ IDEA, or Eclipse using Jetty on port 8000.
_i18n_hash: d418992cee0dea04f98e4d4760acc2db
---
Virheiden korjaaminen on olennainen osa Java-kehitystä, ja se auttaa kehittäjiä tunnistamaan ja korjaamaan ongelmat tehokkaasti. Tämä opas selittää, kuinka voit määrittää virheiden korjaamisen webforJ:ssä Visual Studio Codessa, IntelliJ IDEA:ssa ja Eclipsessä.

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
4. Valitse Java ympäristönä.
5. Muokkaa `launch.json` seuraavasti:

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

1. Avaa projektisi IntelliJ IDEA:ssa.
2. Siirry kohtaan Suorita → Muokkaa asetuksia.
3. Napsauta <kbd>+</kbd>-painiketta ja valitse Etä-JVM-virheenkorjaus.
4. Aseta isäntäkoneeksi `localhost` ja portiksi `8000`.
5. Tallenna asetukset ja napsauta Virheenkorjaus liittääksesi meneillään olevaan sovellukseen.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/eclipse.mp4" type="video/mp4" />
      </video>
</div>

1. Avaa projektisi Eclipsessä.
2. Mene kohtaan Suorita → Muokkaa asetuksia.
3. Valitse Etä-Java-sovellus.
4. Napsauta Uusi asetukset ja aseta:
   - Isäntä: `localhost`
   - Portti: `8000`
5. Tallenna ja käynnistä virheenkorjaus.

</TabItem>
</Tabs>

## Virheenkorjaus {#running-the-debugger}

Kun olet määrittänyt IDE:n:

1. Käynnistä webforJ-sovelluksesi vastaavalla komennolla:
    - Jettylle käytä `mvnDebug jetty:run`
    - Spring Bootille käytä `mvnDebug spring-boot:run`
2. Suorita virheenkorjausasetukset IDE:ssasi.
3. Aseta katkaisupisteet ja aloita virheenkorjaus.

:::tip Virheenkorjausvinkit
1. Varmista, että portti 8000 on käytettävissä eikä mitään palomuuria estä sitä.
2. Jos käytät jotakin webforJ-arkkitehtuureista ja olet muuttanut porttinumeroa pom.xml-tiedostossa, varmista, että virheenkorjaamiseen käytettävä portti vastaa päivittynyttä arvoa.
:::
