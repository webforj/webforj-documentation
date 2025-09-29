---
title: Property Configuration
sidebar_position: 30
_i18n_hash: 66df4ab330f26adccbed654c27c6be23
---
# Configureren van webforJ-eigenschappen

Om een webforJ-app succesvol te implementeren en uit te voeren, zijn een paar belangrijke configuratiebestanden vereist: `webforj.conf` en `web.xml`. Elk van deze bestanden beheert verschillende aspecten van het gedrag van de app, van ingangen en foutopsporingsinstellingen tot servlet-mappingen.

## Configureren van `webforj.conf` {#configuring-webforjconf}

Het `webforj.conf`-bestand is een essentieel configuratiebestand in webforJ, waarin app-instellingen zoals ingangen, foutopsporingsmodus en client-server-interactie worden gespecificeerd. Het bestand is in [HOCON-indeling](https://github.com/lightbend/config/blob/master/HOCON.md) en moet zich bevinden in de `resources`-directory.

:::tip
Als je integreert met het [Spring Framework](../integrations/spring/overview.md), kun je deze `webforj.conf`-eigenschappen instellen in het `application.properties`-bestand.
:::

### Voorbeeld `webforj.conf`-bestand {#example-webforjconf-file}

```Ini
# Dit configuratiebestand is in HOCON-indeling:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Configuratie-opties {#configuration-options}

| Eigenschap                             | Type    | Uitleg                                                       | Standaard                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | String  | Cache-Control-header voor statische bronnen.                        | `null` |
| **`webforj.assetsDir`**              | String  | De routenaam die wordt gebruikt om statische bestanden te serveren, terwijl de werkelijke mapnaam `static` blijft. Deze configuratie is nuttig als de standaard `static`-route conflicteert met een route die in je app is gedefinieerd, waardoor je de routenaam kunt wijzigen zonder de map zelf te hernoemen.       | `null`               |
| **`webforj.assetsExt`**              | String  | Standaard bestandsindeling voor statische bestanden. | `null` |
| **`webforj.assetsIndex`**            | String  | Standaard bestand dat wordt bediend voor directory-aanvragen (bijv. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | Het interval waartegen de client de server pingt om te zien of deze nog steeds actief is. Voor ontwikkeling moet je dit instellen op een korter interval, bijvoorbeeld `8s`, om de beschikbaarheid van de server snel te detecteren. Stel dit in op 50 seconden of langer in productie om overmatige verzoeken te voorkomen. | `50s`           |
| **`webforj.components`**             | String  | Wanneer gespecificeerd, bepaalt het basispad waar DWC-componenten worden geladen. Standaard worden componenten geladen van de server waarop de app wordt gehost. Het instellen van een aangepast basispad stelt componenten in staat om te worden geladen van een alternatieve server of CDN. Bijvoorbeeld, om componenten van jsdelivr.com te laden, stel je het basispad in op: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Het is belangrijk dat de geladen componenten compatibel zijn met de versie van het webforJ-framework dat in gebruik is; anders functioneert de app mogelijk niet zoals verwacht. Deze instelling wordt genegeerd bij gebruik van een standaard BBj-installatie zonder de engine. Voor een standaard BBj-installatie kan de instelling worden beheerd met de `!COMPONENTS` STBL. | `null`          |
| **`webforj.debug`**                  | Boolean | Schakelt de foutopsporingsmodus in. In de foutopsporingsmodus print webforJ extra informatie naar de console en toont het alle uitzonderingen in de browser. De foutopsporingsmodus is standaard uitgeschakeld. | `null`          |
| **`webforj.entry`**                  | String  | Bepaalt het ingangs- of startpunt van de app door de volledig gekwalificeerde naam van de klasse die `webforj.App` uitbreidt op te geven. Als er geen ingangs- of startpunt is gedefinieerd, zal webforJ automatisch de classpath scannen op klassen die `webforj.App` uitbreiden. Als er meerdere klassen worden gevonden, zal er een fout optreden. Wanneer een pakket meer dan één potentieel startpunt bevat, is het nodig om dit expliciet in te stellen om ambiguïteit te voorkomen, of de `AppEntry`-annotatie kan worden gebruikt om het startpunt tijdens runtime op te geven. | `null`          |
| **`webforj.fileUpload.accept`**      | List    | De toegestane bestandstypen voor bestandsuploads. Standaard zijn alle bestandstypen toegestaan. Ondersteunde formaten omvatten MIME-typen zoals `image/*`, `application/pdf`, `text/plain`, of bestandsextensies zoals `*.txt`. Bij gebruik van een standaard BBj-installatie wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | De maximale bestandsgrootte die is toegestaan voor bestandsuploads, in bytes. Standaard is er geen limiet. Bij gebruik van een standaard BBj-installatie wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `null`          |
| **`webforj.iconsDir`**               | String  | URL-eindpunt voor de map met iconen (standaard bedient van `resources/icons/`). | `icons/` |
| **`webforj.license.cfg`**            | String  | De map voor de licentieconfiguratie. Standaard is dit dezelfde als de configuratiemap van webforJ, maar dit kan indien nodig worden aangepast. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Tijdslimiet voor licentie-opstart in seconden. | `null` |
| **`webforj.locale`**                 | String  | De lokale instellingen voor de app, die taal, regio-instellingen en formaten voor datums, tijden en getallen bepalen. | `null` |
| **`webforj.quiet`**                  | Boolean | Schakelt de laad afbeelding uit tijdens de opstart van de applicatie. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Enkel voor ontwikkelomgevingen.** In een ontwikkelomgeving vernieuwt de pagina automatisch bij fouten die verband houden met hot deployment, maar niet bij andere fouttypes. Bij het gebruik van hot redeployment kan er een fout optreden terwijl de WAR-file wordt vervangen, als de client een verzoek naar de server stuurt terwijl deze opnieuw opstart. Omdat de server waarschijnlijk snel weer online is, maakt deze instelling het de client mogelijk om automatisch een pagina vernieuwen te proberen.  | `false` |
| **`webforj.servlets[n].name`**       | String  | Servlet-naam (gebruikt de klassenaam als deze niet is opgegeven). | `null` |
| **`webforj.servlets[n].className`**  | String | Volledig gekwalificeerde klassenaam van de servlet. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Initialisatieparameters voor de servlet. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Duur van de sessietijdslimiet in seconden. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Een map van sleutel-waardeparen die worden gebruikt om strings op te slaan voor gebruik in de app. Nuttig voor het opslaan van app-berichten of labels. Meer informatie over `StringTable` is te vinden [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |

## Configureren van `web.xml` {#configuring-webxml}

Het `web.xml`-bestand is een essentieel configuratiebestand voor Java-webapps, en in webforJ definieert het belangrijke instellingen zoals de servletconfiguratie, URL-patronen en welkomstpagina's. Dit bestand moet zich bevinden in de `WEB-INF`-directory van de implementatiestructuur van je project.

| Instelling                                 | Uitleg                                                                                                                                                                                   | Standaardwaarde               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------- |
| **`<display-name>`**                    | Stelt de weergegeven naam voor de webapp in, doorgaans afgeleid van de projectnaam. Deze naam verschijnt in de beheerdersconsole van app-servers.                                                        | `${project.name}`             |
| **`<servlet>` en `<servlet-mapping>`** | Definieert de `WebforjServlet`, de kernservlet voor het afhandelen van webforJ-verzoeken. Deze servlet is gemapt op alle URL's (`/*`), waardoor het het belangrijkste toegangspunt voor webverzoeken is.                     | `WebforjServlet`              |
| **`<load-on-startup>`**                 | Geeft aan dat `WebforjServlet` moet worden geladen wanneer de app start. Dit instellen op `1` zorgt ervoor dat de servlet onmiddellijk wordt geladen, wat de initiële verwerking van aanvragen verbetert.                | `1`                           |
