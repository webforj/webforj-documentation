---
title: Property Configuration
sidebar_position: 30
_i18n_hash: e7a922cb3f035dd19fdc282d245bdf2c
---
# Configureren van webforJ-eigenschappen

Om een webforJ-app succesvol te implementeren en uit te voeren, zijn een paar belangrijke configuratiebestanden vereist: `webforj.conf` en `web.xml`. Elk van deze bestanden controleert verschillende aspecten van het gedrag van de app, van instappunten en foutopsporingsinstellingen tot servlet-mappingen.

## Configureren van `webforj.conf` {#configuring-webforjconf}

Het `webforj.conf`-bestand is een kernconfiguratiebestand in webforJ, dat app-instellingen specificeert zoals instappunten, foutopsporingsmodus en client-serverinteractie. Het bestand is in [HOCON-indeling](https://github.com/lightbend/config/blob/master/HOCON.md) en moet zich in de map `resources` bevinden.

:::tip
Als je integreert met [Spring](../integrations/spring/overview.md), kun je deze `webforj.conf`-eigenschappen instellen in het `application.properties`-bestand.
:::

### Voorbeeld van `webforj.conf`-bestand {#example-webforjconf-file}

```Ini
# Dit configuratiebestand is in HOCON-indeling:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Configuratieopties {#configuration-options}

| Eigenschap                             | Type    | Uitleg                                                       | Standaard                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | String  | Cache-Control-header voor statische bronnen.                        | `null` |
| **`webforj.assetsDir`**              | String  | De routernaam die wordt gebruikt om statische bestanden te bedienen, terwijl de eigenlijke mapnaam `static` blijft. Deze configuratie is nuttig als de standaard `static`-route conflicteert met een route die in jouw app is gedefinieerd.       | `null`               |
| **`webforj.assetsExt`**              | String  | Standaard bestandsextensie voor statische bestanden. | `null` |
| **`webforj.assetsIndex`**            | String  | Standaard bestand dat wordt bediend voor directory-aanvragen (bijv. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | Het interval waarop de client de server pingt om te zien of deze nog actief is. Voor ontwikkeling kun je dit op een korter interval instellen, bijvoorbeeld `8s`, om de beschikbaarheid van de server snel te detecteren. Stel dit in op 50 seconden of meer in productie om overmatige verzoeken te voorkomen. | `50s`           |
| **`webforj.components`**             | String  | Wanneer gespecificeerd, bepaalt het basispad waar DWC-componenten worden geladen. Standaard worden componenten geladen vanaf de server die de app host. Het instellen van een aangepast basispad maakt het mogelijk om componenten te laden van een alternatieve server of CDN. Bijvoorbeeld, om componenten te laden van jsdelivr.com, stel het basispad in op: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} Het is belangrijk dat de geladen componenten compatibel zijn met de versie van het webforJ-framework die wordt gebruikt; anders werkt de app mogelijk niet zoals verwacht. Deze instelling wordt genegeerd bij gebruik van een standaard BBj-installatie zonder de engine. Voor een standaard BBj-installatie kan de instelling worden beheerd met de `!COMPONENTS` STBL. | `null`          |
| **`webforj.debug`**                  | Boolean | Zet de foutopsporingsmodus aan. In de foutopsporingsmodus geeft webforJ aanvullende informatie weer in de console en toont het alle uitzonderingen in de browser. De foutopsporingsmodus is standaard uitgeschakeld. | `null`          |
| **`webforj.entry`**                  | String  | Bepaalt het instappunt van de app door de volledig gekwalificeerde naam van de klasse op te geven die `webforj.App` uitbreidt. Als er geen instappunt is gedefinieerd, zal webforJ automatisch de classpath scannen naar klassen die `webforj.App` uitbreiden. Als er meerdere klassen worden gevonden, treedt er een fout op. Wanneer een pakket meer dan één potentieel instappunt bevat, is het expliciet instellen hiervan vereist om ambiguïteit te voorkomen, of alternatieven kan de annotatie `AppEntry` worden gebruikt om het instappunt tijdens runtime op te geven. | `null`          |
| **`webforj.fileUpload.accept`**      | List    | De toegestane bestandstypen voor bestandsuploads. Standaard zijn alle bestandstypen toegestaan. Ondersteunde formaten zijn onder andere MIME-typen zoals `image/*`, `application/pdf`, `text/plain`, of bestandsextensies zoals `*.txt`. Bij gebruik van een standaard BBj-installatie wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | De maximale bestandsgrootte die is toegestaan voor bestandsuploads, in bytes. Standaard is er geen limiet. Bij gebruik van een standaard BBj-installatie wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `null`          |
| **`webforj.iconsDir`**               | String  | URL-eindpunt voor de iconenmap (standaard dient het vanaf `resources/icons/`). | `icons/` |
| **`webforj.license.cfg`**            | String  | De map voor de licentieconfiguratie. Standaard is dit dezelfde als de configuratiemap van webforJ, maar dit kan indien nodig worden aangepast. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Licentie-opstarttijdslimiet in seconden. | `null` |
| **`webforj.locale`**                 | String  | De lokale instelling voor de app, die de taal, regionale instellingen en formaten voor data, tijden en getallen bepaalt. | `null` |
| **`webforj.quiet`**                  | Boolean | Schakelt de laadafbeelding uit tijdens het opstarten van de applicatie. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Alleen voor ontwikkelomgevingen.** In een ontwikkelomgeving zou de pagina automatisch opnieuw moeten worden geladen bij fouten gerelateerd aan hot redeployment, maar niet andere fouttypes. Bij gebruik van hot redeploy, kan een fout optreden als de client een verzoek naar de server stuurt terwijl deze opnieuw opstart. Aangezien de server waarschijnlijk snel weer online zal zijn, stelt deze instelling de client in staat om automatisch een pagina-opnieuw te laden.  | `false` |
| **`webforj.servlets[n].name`**       | String  | De naam van de servlet (gebruik de klassenaam als deze niet is opgegeven). | `null` |
| **`webforj.servlets[n].className`**  | String | Volledig gekwalificeerde klassenaam van de servlet. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Initalisatieparameters van de servlet. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Sessie-time-outduur in seconden. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Een map van sleutel-waardeparen die worden gebruikt om strings op te slaan voor gebruik in de app. Nuttig voor het opslaan van app-berichten of labels. Meer informatie over `StringTable` is te vinden [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |

## Configureren van `web.xml` {#configuring-webxml}

Het `web.xml`-bestand is een essentieel configuratiebestand voor Java-webapps en definieert in webforJ belangrijke instellingen zoals de servletconfiguratie, URL-patronen en welkomstpagina's. Dit bestand moet zich bevinden in de `WEB-INF`-map van de implementatiestructuur van jouw project.

| Instelling                                 | Uitleg                                                                                                                                                                                   | Standaardwaarde               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Stelt de weergavenaam voor de webapp in, meestal afgeleid van de projectnaam. Deze naam verschijnt in de beheertooling van app-servers.                                                        | `${project.name}`           |
| **`<servlet>` en `<servlet-mapping>`** | Definieert de `WebforjServlet`, de kernservlet voor het afhandelen van webforJ-verzoeken. Deze servlet is gemapt op alle URL's (`/*`), waardoor het het belangrijkste instappunt voor webverzoeken is.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Geeft aan dat `WebforjServlet` moet worden geladen wanneer de app start. Dit op `1` instellen zorgt ervoor dat de servlet onmiddellijk wordt geladen, wat de verwerking van het initiële verzoek verbetert.                | `1`                         |
