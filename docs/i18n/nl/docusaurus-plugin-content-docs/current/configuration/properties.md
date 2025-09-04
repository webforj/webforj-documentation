---
title: Property Configuration
sidebar_position: 30
_i18n_hash: 3e14c2d47a7963fe901feda071971419
---
# Configureren van webforJ-eigenschappen

Om een webforJ-app succesvol te implementeren en uit te voeren, zijn er een paar belangrijke configuratiebestanden nodig: `webforj.conf` en `web.xml`. Elk van deze bestanden beheert verschillende aspecten van het gedrag van de app, van toegangspunten en debuginstellingen tot servlet-mappingen.

## Configureren van `webforj.conf` {#configuring-webforjconf}

Het `webforj.conf`-bestand is een belangrijk configuratiebestand in webforJ, waarin app-instellingen zoals toegangspunten, debugmodus en communicatie tussen client en server worden gespecificeerd. Het bestand is in [HOCON-indeling](https://github.com/lightbend/config/blob/master/HOCON.md) en moet zich in de `resources`-map bevinden.

:::tip
Als je integreert met het [Spring Framework](../integrations/spring/overview.md), kun je deze `webforj.conf`-eigenschappen instellen in het `application.properties`-bestand.
:::

### Voorbeeld van een `webforj.conf`-bestand {#example-webforjconf-file}

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
|--------------------------------------|---------|------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | String  | Cache-Control-header voor statische bronnen.               | `null` |
| **`webforj.assetsDir`**              | String  | De routenaam die wordt gebruikt om statische bestanden aan te bieden, terwijl de daadwerkelijke mapnaam `static` blijft. Deze configuratie is nuttig als de standaardroute `static` conflicteert met een route die in jouw app is gedefinieerd, waardoor je de routenaam kunt wijzigen zonder de map zelf te hernoemen. | `null`               |
| **`webforj.assetsExt`**              | String  | Standaard bestandsextensie voor statische bestanden.        | `null` |
| **`webforj.assetsIndex`**            | String  | Standaard bestand dat wordt aangeboden voor directoryverzoeken (bijv. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | Het interval waarop de client de server aanpingt om te controleren of deze nog actief is. Voor ontwikkeling, stel dit in op een korter interval, bijvoorbeeld `8s`, om snel de beschikbaarheid van de server te detecteren. Stel in productie in op 50 seconden of hoger om overmatige verzoeken te voorkomen. | `50s`           |
| **`webforj.components`**             | String  | Wanneer gespecificeerd, bepaalt het basispad van waaruit DWC-componenten worden geladen. Standaard worden componenten geladen vanaf de server die de app host. Het instellen van een aangepast basispad maakt het mogelijk om componenten van een alternatieve server of CDN te laden. Bijvoorbeeld, om componenten van jsdelivr.com te laden, stel het basispad in op: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Het is belangrijk dat de geladen componenten compatibel zijn met de versie van het webforJ-framework dat in gebruik is; anders werkt de app mogelijk niet zoals verwacht. Deze instelling wordt genegeerd bij het gebruik van een standaard BBj-installatie zonder de engine. Voor een standaard BBj-installatie kan de instelling worden beheerd met de `!COMPONENTS` STBL. | `null`          |
| **`webforj.debug`**                  | Boolean | Schakelt de debugmodus in. In de debugmodus print webforJ aanvullende informatie naar de console en toont het alle uitzonderingen in de browser. Debugmodus is standaard uitgeschakeld. | `null`          |
| **`webforj.entry`**                  | String  | Definieert het toegangspunt van de app door de volledig gekwalificeerde naam van de klasse die `webforj.App` uitbreidt, op te geven. Als er geen toegangspunt is gedefinieerd, scant webforJ automatisch de classpath op klassen die `webforj.App` uitbreiden. Als er meerdere klassen worden gevonden, treedt er een fout op. Wanneer een pakket meer dan één potentieel toegangspunt bevat, is het vereist om dit expliciet in te stellen om ambiguïteit te voorkomen, of de `AppEntry`-annotatie kan worden gebruikt om het toegangspunt tijdens runtime op te geven. | `null`          |
| **`webforj.fileUpload.accept`**      | Lijst   | De toegestane bestandstypen voor bestandsuploads. Standaard zijn alle bestandstypen toegestaan. Ondersteunde indelingen zijn onder andere MIME-typen zoals `image/*`, `application/pdf`, `text/plain`, of bestandsextensies zoals `*.txt`. Wanneer je een standaard BBj-installatie gebruikt, wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Lang    | De maximale bestandsgrootte die is toegestaan voor bestandsuploads, in bytes. Standaard is er geen limiet. Wanneer je een standaard BBj-installatie gebruikt, wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `null`          |
| **`webforj.iconsDir`**               | String  | URL-eindpunt voor iconenmap (standaard dient vanuit `resources/icons/`). | `icons/` |
| **`webforj.license.cfg`**            | String  | De map voor de licentieconfiguratie. Standaard is het dezelfde map als de webforJ-configuratiemap, maar dit kan indien nodig worden aangepast. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Licentie-opstarttimeout in seconden. | `null` |
| **`webforj.locale`**                 | String  | De lokale instelling voor de app, die taal, regi-instellingen en indelingen voor datums, tijden en getallen bepaalt. | `null` |
| **`webforj.quiet`**                  | Boolean | Schakelt de laadafbeelding uit tijdens de opstart van de applicatie. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Alleen voor ontwikkelomgevingen.** In een ontwikkelomgeving, auto-herlaad de pagina bij fouten die verband houden met hot redeployment, maar niet bij andere fouttypes. Bij hot redeploy kan het voorkomen dat de client een verzoek naar de server verzendt terwijl deze opnieuw opstart, wat een fout kan veroorzaken terwijl het WAR-bestand wordt verwisseld.  Omdat de server waarschijnlijk binnenkort weer online is, biedt deze instelling de client de mogelijkheid om automatisch een pagina-opnieuw te proberen. | `false` |
| **`webforj.servlets[n].name`**       | String  | Servletnaam (gebruik de klasnaam als niet opgegeven). | `null` |
| **`webforj.servlets[n].className`**  | String  | Volledig gekwalificeerde klasnaam van de servlet. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet-initialisatieparameters. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Duur van de sessietimeout in seconden. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Een kaart van sleutel-waardeparen die worden gebruikt om teken reeksen op te slaan voor gebruik in de app. Handig voor het opslaan van app-berichten of labels. Meer informatie over `StringTable` is te vinden [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |

## Configureren van `web.xml` {#configuring-webxml}

Het web.xml-bestand is een essentieel configuratiebestand voor Java-webapps, en in webforJ definieert het belangrijke instellingen zoals de servletconfiguratie, URL-patronen, welkomstpagina's. Dit bestand moet zich in de `WEB-INF`-map van de implementatiestructuur van jouw project bevinden.

| Instelling                                 | Uitleg                                                                                                                                                                                   | Standaardwaarde               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------- |
| **`<display-name>`**                    | Stelt de weergavenaam voor de webapp in, die doorgaans is afgeleid van de projectnaam. Deze naam verschijnt in de beheerdersconsole van app-servers.                                                        | `${project.name}`           |
| **`<servlet>` en `<servlet-mapping>`** | Definieert de `WebforjServlet`, de kern servlet voor het afhandelen van webforJ-verzoeken. Deze servlet is gemapt naar alle URL's (`/*`), waardoor het het belangrijkste toegangspunt voor webverzoeken is.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Geeft aan dat `WebforjServlet` geladen moet worden wanneer de app start. Dit instellen op `1` zorgt ervoor dat de servlet onmiddellijk laadt, wat de initiële verwerking van verzoeken verbetert.                | `1`                         |
