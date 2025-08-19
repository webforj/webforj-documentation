---
title: Property Configuration
sidebar_position: 30
_i18n_hash: e2cc183e859c85e0d1f4a24c196b8a55
---
# Configureren van webforJ-eigenschappen

Om een webforJ-app succesvol te implementeren en uit te voeren, zijn enkele belangrijke configuratiebestanden vereist: `webforJ.conf` en `web.xml`. Elk van deze bestanden controleert verschillende aspecten van het gedrag van de app, van toegangspunten en debug-instellingen tot servlet-mappingen.

## Configureren van `webforj.conf` {#configuring-webforjconf}

Het `webforJ.conf`-bestand is een kernconfiguratiebestand in webforJ, dat app-instellingen specificeert zoals toegangspunten, debug-modus en client-server-interactie. Het bestand is geschreven in [HOCON-formaat](https://github.com/lightbend/config/blob/master/HOCON.md) en moet zich bevinden in de `resources`-map.

### Voorbeeld van een `webforj.conf`-bestand {#example-webforjconf-file}

```Ini
# Dit configuratiebestand is in HOCON-formaat:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Configuratie-opties {#configuration-options}

| Eigenschap                       | Uitleg                                                                                                                                                                | Standaard         |
|----------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| **`webforj.entry`**              | Definieert het toegangspunt van de app door de volledig gekwalificeerde naam van de klasse die `webforj.App` uitbreidt, op te geven. Als er geen toegangspunt is gedefinieerd, zal webforJ automatisch de classpath scannen naar klassen die `webforj.App` uitbreiden. Als er meerdere klassen worden gevonden, zal er een fout optreden. Wanneer een pakket meer dan één potentieel toegangspunt bevat, is het noodzakelijk om dit expliciet in te stellen om ambiguïteit te voorkomen, of alternatieve kan de annotatie `AppEntry` worden gebruikt om het toegangspunt tijdens runtime op te geven. | `null`            |
| **`webforj.debug`**              | Zet de debug-modus aan. In de debug-modus zal webforJ extra informatie naar de console afdrukken en alle uitzonderingen in de browser tonen. De debug-modus is standaard uitgeschakeld. | `null`            |
| **`webforj.reloadOnServerError`** | Wanneer gebruik wordt gemaakt van hot redeploy, wordt het hele WAR-bestand verwisseld. Als de client probeert een verzoek naar de server te sturen terwijl deze opnieuw opstart, treedt er een fout op. Deze instelling staat de client toe om een pagina opnieuw te laden als de server tijdelijk niet beschikbaar is, in de hoop dat deze snel weer online is. Dit geldt alleen voor ontwikkelomgevingen en behandelt alleen fouten die specifiek zijn voor hot redeployment, niet andere soorten fouten. | `on`              |
| **`webforj.clientHeartbeatRate`** | Stelt het interval in waarop de client de server "pingt" om te controleren of deze nog beschikbaar is. Dit helpt de communicatie te behouden. Voor ontwikkeling kan dit op een korter interval, bijvoorbeeld `8s`, worden ingesteld om snel de beschikbaarheid van de server te detecteren. Stel dit in de productie niet lager dan 50 seconden in om overmatige verzoeken te vermijden. | `50s`             |
| **`webforj.components`**         | Wanneer opgegeven, bepaalt het basispad waar DWC-componenten van worden geladen. Standaard worden componenten geladen vanaf de server waarop de app wordt gehost. Echter, door een aangepast basispad in te stellen, kunnen componenten van een alternatieve server of CDN worden geladen. Bijvoorbeeld, om componenten van jsdelivr.com te laden, stel het basispad in op: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Het is belangrijk dat de geladen componenten compatibel zijn met de versie van het webforJ-framework die in gebruik is; anders werkt de app mogelijk niet zoals verwacht. Deze instelling wordt genegeerd wanneer een standaard BBj-installatie zonder de engine wordt gebruikt. Voor een standaard BBj-installatie kan de instelling worden beheerd met de `!COMPONENTS` STBL. | `null`            |
| **`webforj.locale`**             | Definieert de lokale instelling voor de app, met bepalingen voor taal, regionale instellingen en indelingen voor data, tijden en nummers. | `null`            |
| **`webforj.assetsDir`**         | Bevat de routenaam die wordt gebruikt om statische bestanden te serveren, terwijl de fysieke mappennaam `static` blijft. Deze configuratie is handig als de standaard `static` route in conflict komt met een route die in jouw app is gedefinieerd, waarmee je de routenaam kunt wijzigen zonder de map zelf te hernoemen. | `static`          |
| **`webforj.stringTable`**        | Een kaart van sleutel-waarde-paren die wordt gebruikt om strings op te slaan voor gebruik in de app. Handig voor het opslaan van app-berichten of labels. Meer informatie over `StringTable` is te vinden [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`              |
| **`webforj.fileUpload.accept`**  | Specificeert de toegestane bestandstypen voor bestand uploads. Standaard zijn alle bestandstypen toegestaan. Ondersteunde indelingen zijn onder andere MIME-types zoals `image/*`, `application/pdf`, `text/plain`, of bestandsextensies zoals `*.txt`. Bij gebruik van een standaard BBj-installatie, wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `[]`              |
| **`webforj.fileUpload.maxSize`** | Definieert de maximale bestandsgrootte die is toegestaan voor bestand uploads, in bytes. Standaard is er geen limiet. Bij gebruik van een standaard BBj-installatie, wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `null`            |
| **`license.cfg`**                | Configureert de map voor de licentieconfiguratie. Standaard is dit dezelfde als de configuratiemap van webforJ, maar dit kan worden aangepast indien nodig. | `"."`             |

## Configureren van `web.xml` {#configuring-webxml}

Het web.xml-bestand is een essentieel configuratiebestand voor Java-webapps en in webforJ definieert het belangrijke instellingen zoals de servlet-configuratie, URL-patronen en welkomstpagina's. Dit bestand moet zich bevinden in de `WEB-INF`-map van de implementatiestructuur van jouw project.

| Instelling                                  | Uitleg                                                                                                                                                                               | Standaardwaarde              |
| ------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----------------------------- |
| **`<display-name>`**                        | Stelt de weergegeven naam voor de webapp in, die meestal is afgeleid van de projectnaam. Deze naam verschijnt in de beheerschermen van app-servers.                                  | `${project.name}`             |
| **`<servlet>` en `<servlet-mapping>`**     | Definieert de `WebforjServlet`, de kernservlet voor het afhandelen van webforJ-verzoeken. Deze servlet is gemapt op alle URL's (`/*`), waardoor het het belangrijkste toegangspunt voor webverzoeken is. | `WebforjServlet`              |
| **`<load-on-startup>`**                     | Specificeert dat `WebforjServlet` moet worden geladen wanneer de app start. Dit op `1` instellen zorgt ervoor dat de servlet onmiddellijk wordt geladen, wat de initiële aanvraagverwerking verbetert. | `1`                           |
