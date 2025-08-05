---
title: Property Configuration
sidebar_position: 30
_i18n_hash: dea9eb679150ca6124fb625c7d04d27c
---
# Configureren van webforJ-eigenschappen

Om een webforJ-app succesvol te implementeren en te draaien, zijn een paar belangrijke configuratiebestanden vereist: `webforJ.conf` en `web.xml`. Elk van deze bestanden controleert verschillende aspecten van het gedrag van de app, van toegangs- en foutopsporingsinstellingen tot servlet-mappingen.

## Configureren van `webforj.conf` {#configuring-webforjconf}

Het `webforJ.conf`-bestand is een essentieel configuratiebestand in webforJ, waarin app-instellingen zoals toegangs- en foutopsporingsmodus en de interactie tussen cliënt en server zijn vastgelegd. Het bestand is geschreven in [HOCON-formaat](https://github.com/lightbend/config/blob/master/HOCON.md) en moet zich in de `resources`-directory bevinden.

### Voorbeeld `webforj.conf`-bestand {#example-webforjconf-file}

```Ini
# Dit configuratiebestand is in HOCON-formaat:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Configuratie-opties {#configuration-options}

| Eigenschap                       | Uitleg                                                                                                                                                                            | Standaard         |
|----------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| **`webforj.entry`**              | Bepaalt het toegangspunt van de app door de volledig gekwalificeerde naam van de klasse op te geven die `webforj.App` uitbreidt. Als er geen toegangspunt is gedefinieerd, zal webforJ automatisch de classpath scannen naar klassen die `webforj.App` uitbreiden. Als er meerdere klassen worden gevonden, zal er een fout optreden. Wanneer een pakket meer dan één potentieel toegangspunt bevat, is het nodig om dit expliciet in te stellen om ambiguïteit te voorkomen, of de `AppEntry`-annotatie kan worden gebruikt om het toegangspunt tijdens runtime op te geven. | `null`            |
| **`webforj.debug`**              | Schakelt de foutopsporingsmodus in. In de foutopsporingsmodus zal webforJ extra informatie naar de console afdrukken en zullen alle uitzonderingen in de browser worden weergegeven. De foutopsporingsmodus is standaard uitgeschakeld. | `null`            |
| **`webforj.reloadOnServerError`** | Bij het gebruik van hot redeploy wordt het hele WAR-bestand verwisseld. Als de cliënt een verzoek naar de server probeert te verzenden terwijl deze opnieuw opstart, treedt er een fout op. Deze instelling stelt de cliënt in staat om een pagina opnieuw te laden als de server tijdelijk niet beschikbaar is, in de hoop dat deze binnenkort weer online is. Dit is alleen van toepassing op ontwikkelomgevingen en behandelt alleen fouten die specifiek zijn voor hot redeployment, niet andere soorten fouten. | `on`              |
| **`webforj.clientHeartbeatRate`** | Stelt de interval in waarop de cliënt de server controleert of deze nog in leven is. Dit helpt de communicatie te behouden. Voor ontwikkeling moet dit op een kortere interval worden ingesteld, bijvoorbeeld `8s`, om snel de beschikbaarheid van de server te detecteren. Stel dit niet in onder de 50 seconden in productie om overbodige verzoeken te vermijden. | `50s`             |
| **`webforj.components`**         | Wanneer dit is opgegeven, bepaalt het basispad waar DWC-componenten vandaan worden geladen. Standaard worden componenten geladen van de server die de app host. Het instellen van een aangepast basispad maakt het mogelijk om componenten te laden van een alternatieve server of CDN. Bijvoorbeeld, om componenten van jsdelivr.com te laden, stel het basispad in op: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} Het is belangrijk dat de geladen componenten compatibel zijn met de versie van het webforJ-framework die in gebruik is; anders werkt de app mogelijk niet zoals verwacht. Deze instelling wordt genegeerd bij gebruik van een standaard BBj-installatie zonder de engine. Voor een standaard BBj-installatie kan deze instelling worden beheerd met de `!COMPONENTS` STBL. | `null`            |
| **`webforj.locale`**             | Bepaalt de locale voor de app, wat de taal, regiovoorwaarden en formaten voor data, tijden en nummers bepaalt. | `null`            |
| **`webforj.assetsDir`**         | Geeft de route naam op die wordt gebruikt om statische bestanden te serveren, terwijl de fysieke mapnaam `static` blijft. Deze configuratie is nuttig als de standaard `static` route in conflict komt met een route die in je app is gedefinieerd, zodat je de routenaam kunt wijzigen zonder de map zelf te hernoemen. | `static`          |
| **`webforj.stringTable`**        | Een map van sleutel-waarde paren die worden gebruikt om strings op te slaan voor gebruik in de app. Nuttig voor het opslaan van app-berichten of labels. Meer informatie over `StringTable` is te vinden [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`              |
| **`webforj.fileUpload.accept`**  | Geeft de toegestane bestandstypen voor bestandsuploads op. Standaard zijn alle bestandstypen toegestaan. Ondersteunde formaten zijn inclusief MIME-typen zoals `image/*`, `application/pdf`, `text/plain`, of bestandsextensies zoals `*.txt`. Bij gebruik van een standaard BBj-installatie wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `[]`              |
| **`webforj.fileUpload.maxSize`** | Bepaalt de maximale bestandsgrootte voor bestandsuploads, in bytes. Standaard is er geen limiet. Bij gebruik van een standaard BBj-installatie wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `null`            |
| **`license.cfg`**                | Configureert de directory voor de licentieconfiguratie. Standaard is het dezelfde als de webforJ-configuratiemap, maar dit kan indien nodig worden aangepast. | `"."`             |

## Configureren van `web.xml` {#configuring-webxml}

Het web.xml-bestand is een essentieel configuratiebestand voor Java-webapps, en in webforJ definieert het belangrijke instellingen zoals de servletconfiguratie, URL-patronen, en welkomstpagina's. Dit bestand moet zich in de `WEB-INF`-directory van de implementatiestructuur van je project bevinden.

| Instelling                                   | Uitleg                                                                                                                                                                                   | Standaard Waarde           |
|----------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------|
| **`<display-name>`**                         | Stelt de weergavenaam in voor de webapp, meestal afgeleid van de projectnaam. Deze naam verschijnt in de beheerdersconsole van de appservers.                                            | `${project.name}`           |
| **`<servlet>` en `<servlet-mapping>`**      | Bepaalt de `WebforjServlet`, de kernservlet voor het afhandelen van webforJ-verzoeken. Deze servlet is toegewezen aan alle URL's (`/*`), waardoor het de belangrijkste toegangspunt voor webverzoeken is. | `WebforjServlet`            |
| **`<load-on-startup>`**                      | Geeft aan dat `WebforjServlet` moet worden geladen wanneer de app start. Dit op `1` instellen zorgt ervoor dat de servlet onmiddellijk laadt, wat de initiale verzoekverwerking verbetert.     | `1`                         |
