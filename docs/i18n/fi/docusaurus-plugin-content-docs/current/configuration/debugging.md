---
title: Virheiden korjaaminen
sidebar_position: 1
_i18n_hash: 057e00d21a3392bb3bf8d1fba1dea15f
---
Virheenkorjauksen hallinta on olennainen osa Java-kehitystä, ja se auttaa kehittäjiä tunnistamaan ja korjaamaan ongelmia tehokkaasti. Tämä opas selittää, kuinka virheenkorjaus konfiguroidaan webforJ:ssä Visual Studio Codessa, IntelliJ IDEA:ssa ja Eclipsessä.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/vscode.mp4" type="video/mp4" />
      </video>
</div>

1. Avaa webforJ-projektisi VS Codessa.
2. Paina <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (tai <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> Macilla) avataksesi Suorita ja virheenkorjaus -paneelin.
3. Napsauta "create a launch.json file".
4. Valitse ympäristöksi Java.
5. Muokkaa `launch.json` seuraamaan seuraavaa:

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
3. Napsauta <kbd>+</kbd> -painiketta ja valitse Etä JVM -virheenkorjaus.
4. Aseta isäntäkoneeksi `localhost` ja portiksi `8000`.
5. Tallenna asetukset ja napsauta Virheenkorjaus liittääksesi käynnissä olevaan sovellukseen.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/eclipse.mp4" type="video/mp4" />
      </video>
</div>

1. Avaa projektisi Eclipsessä.
2. Siirry kohtaan Suorita → Muokkaa asetuksia.
3. Valitse Etä Java -sovellus.
4. Napsauta Uusi konfiguraatio ja aseta:
   - Isäntä: `localhost`
   - Portti: `8000`
5. Tallenna ja käynnistä virheenkorjain.

</TabItem>
</Tabs>

## Virheenkorjaimen suorittaminen {#running-the-debugger}

Kun olet konfiguroinut IDE:si:

1. Käynnistä webforJ-sovelluksesi vastaavalla komennolla: 
    - Jettylle käytä `mvnDebug jetty:run` 
    - Spring Bootille käytä `mvnDebug spring-boot:run`
2. Suorita virheenkorjauskonfiguraatio IDE:ssäsi.
3. Aseta katkaisupisteitä ja aloita virheenkorjaus.

:::tip Virheenkorjausvinkit
1. Varmista, että portti 8000 on käytettävissä eikä sitä estä mikään palomuuri.
2. Jos käytät jotain webforJ-arkkitehtuureista ja olet muuttanut porttinumeroa pom.xml-tiedostossa, varmista, että virheenkorjauksessa käytettävä portti vastaa päivitettyä arvoa.
:::
