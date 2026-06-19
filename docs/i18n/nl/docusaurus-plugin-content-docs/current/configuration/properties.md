---
title: Property Configuration
sidebar_position: 30
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
_i18n_hash: 2eb59302da44bcdd27d6366419bd78ad
---
# Configureren van webforJ-eigenschappen

Om een webforJ-app met succes te implementeren en uit te voeren, zijn een paar belangrijke configuratiebestanden vereist: `webforj.conf` en `web.xml`. Elk van deze bestanden bestuurt verschillende aspecten van het gedrag van de app, van toegangspunten en debuginstellingen tot servlet-mappingen.

## Configureren van `webforj.conf` {#configuring-webforjconf}

Het `webforj.conf`-bestand is een kernconfiguratiebestand in webforJ, dat app-instellingen specificeert zoals toegangspunten, debugmodus en client-server interactie. Het bestand is in [HOCON-indeling](https://github.com/lightbend/config/blob/master/HOCON.md) en bevindt zich in de map `resources`.

:::tip
Als je integreert met [Spring](../integrations/spring/overview.md), kun je deze `webforj.conf`-eigenschappen instellen in het `application.properties`-bestand.
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

### Configuratie-opties {#configuration-options}

| Eigenschap                             | Type    | Uitleg                                                               | Standaard              |
|----------------------------------------|---------|---------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**       | String  | Cache-Control-header voor statische middelen.                       | `null`                 |
| **`webforj.assetsDir`**                | String  | De routenaam die wordt gebruikt om statische bestanden te serveren, terwijl de werkelijke mappennaam `static` blijft. Deze configuratie is nuttig als de standaard `static`-route conflicteert met een route die in je app is gedefinieerd, waardoor je de routenaam kunt wijzigen zonder de map zelf te hernoemen. | `null`                |
| **`webforj.assetsExt`**                | String  | Standaard bestandsextensie voor statische bestanden.                | `null`                 |
| **`webforj.assetsIndex`**              | String  | Standaard bestand dat wordt geserveerd voor directoryverzoeken (bijv., index.html). | `null`                 |
| **`webforj.clientHeartbeatRate`**      | String  | Het interval waarop de client de server pingt om te zien of deze nog actief is. Voor ontwikkeling moet je dit instellen op een kortere interval, bijvoorbeeld `8s`, om de beschikbaarheid van de server snel te detecteren. Stel in productie in op 50 seconden of meer om overmatige verzoeken te vermijden. | `50s`                  |
| **`webforj.components`**               | String  | Wanneer gespecificeerd, bepaalt het basispad waar DWC-componenten vanaf worden geladen. Standaard worden componenten geladen van de server waarop de app wordt gehost. Door een aangepast basispad in te stellen, kunnen componenten vanaf een alternatieve server of CDN worden geladen. Bijvoorbeeld, om componenten van jsdelivr.com te laden, stel het basispad in op: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Het is belangrijk dat de geladen componenten compatibel zijn met de versie van het webforJ-framework dat in gebruik is; anders functioneert de app mogelijk niet zoals verwacht. Deze instelling wordt genegeerd bij gebruik van een standaard BBj-installatie zonder de engine. Voor een standaard BBj-installatie kan de instelling worden beheerd met de `!COMPONENTS` STBL. | `null`                |
| **`webforj.debug`**                    | Boolean | Schakelt de debugmodus in. In de debugmodus print webforJ extra informatie naar de console en toont het alle uitzonderingen in de browser. De debugmodus is standaard uitgeschakeld. | `null`                |
| **`webforj.entry`**                    | String  | Bepaalt het toegangspunt van de app door de volledig gekwalificeerde naam van de klasse op te geven die `webforj.App` uitbreidt. Als er geen toegangspunt is gedefinieerd, zal webforJ automatisch de classpath scannen naar klassen die `webforj.App` uitbreiden. Als er meerdere klassen worden gevonden, zal er een fout optreden. Wanneer een pakket meer dan één potentieel toegangspunt bevat, is het vereist om dit expliciet in te stellen om ambiguïteit te voorkomen, of alternatief kan de annotatie `AppEntry` worden gebruikt om het toegangspunt tijdens runtime op te geven. | `null`                |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Lijst   | Lijst van ondersteunde lokale als BCP 47-taal tags (bijv., `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Wanneer automatische detectie is ingeschakeld, worden de favoriete lokale van de browser vergeleken met deze lijst. De eerste lokale in de lijst wordt gebruikt als de standaard terugval. Zie [Vertaling](../advanced/i18n-localization.md). | `[]`                  |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Wanneer `true`, wordt de app-locale automatisch ingesteld vanaf de voorkeurs taal van de browser bij opstarten. De locale wordt vastgesteld door de voorkeur lokale van de browser te vergelijken met de lijst `supported-locales`. Wanneer `false` of wanneer `supported-locales` leeg is, gebruikt de app `webforj.locale`. Zie [Vertaling](../advanced/i18n-localization.md). | `false`               |
| **`webforj.fileUpload.accept`**        | Lijst   | De toegestane bestandstypen voor bestandsuploads. Standaard zijn alle bestandstypen toegestaan. Ondersteunde indelingen zijn onder andere MIME-typen zoals `image/*`, `application/pdf`, `text/plain` of bestandsextensies zoals `*.txt`. Bij gebruik van een standaard BBj-installatie wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `[]`                  |
| **`webforj.fileUpload.maxSize`**       | Long    | De maximale bestandsgrootte die is toegestaan voor bestandsuploads, in bytes. Standaard is er geen limiet. Bij gebruik van een standaard BBj-installatie wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `null`                |
| **`webforj.iconsDir`**                 | String  | URL-eindpunt voor de iconenmap (standaard dient deze vanaf `resources/icons/`). | `icons/`               |
| **`webforj.license.cfg`**              | String  | De map voor de licentieconfiguratie. Standaard is het hetzelfde als de webforJ-configuratiemap, maar dit kan indien nodig worden aangepast. | `"."`                  |
| **`webforj.license.startupTimeout`**   | Integer | Licentie-opstarttimeout in seconden. | `null`                |
| **`webforj.locale`**                   | String  | De locale voor de app, die taal, regio-instellingen en formaten voor data, tijd en getallen bepaalt. | `null`                |
| **`webforj.quiet`**                    | Boolean | Schakelt de laadafbeelding uit tijdens het opstarten van de applicatie. | `false`               |
| **`webforj.reloadOnServerError`**      | Boolean | **Alleen voor ontwikkelingsomgevingen.** In een ontwikkelingsomgeving wordt de pagina automatisch opnieuw geladen bij fouten met betrekking tot hot redeployment, maar niet bij andere fouttypen. Bij gebruik van hot redeploy, als de client een verzoek naar de server stuurt terwijl deze herstart, kan er een fout optreden terwijl het WAR-bestand wordt verwisseld. Omdat de server waarschijnlijk snel weer online is, stelt deze instelling de client in staat om automatisch een pagina opnieuw te laden. | `false`               |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Grootste verzoek dat de app zal accepteren, in bytes, als een beveiligingsmaatregel tegen te grote verzoeken die bedoeld zijn om servergeheugen uit te putten. Stel in op `0` om de limiet te deactiveren. | `0`                   |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Hoeveel nieuwe applicatiesessies de app elke minuut zal starten, als een beveiligingsmaatregel tegen snelle sessiecreatie die bedoeld is om serverbronnen uit te putten. Stel in op `0` om de snelheidslimiet uit te schakelen. | `0`                   |
| **`webforj.servlets[n].name`**         | String  | Servletnaam (maakt gebruik van de classnaam als deze niet is opgegeven). | `null`                |
| **`webforj.servlets[n].className`**    | String  | Volledig gekwalificeerde classnaam van de servlet. | `null`                |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet-initialisatieparameters. | `null`                |
| **`webforj.sessionTimeout`**           | Integer | Sessie-timeoutduur in seconden. | `60`                  |
| **`webforj.stringTable`**              | `Map<String,String>` | Een map van sleutel-waarde paren die worden gebruikt om tekenreeksen op te slaan voor gebruik in de app. Handig voor het opslaan van app-berichten of labels. Meer informatie over `StringTable` is te vinden [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`                  |
| **`webforj.mime.extensions`**           | `Map<String,String>` | Aangepaste MIME-type mappings voor bestandsextensies bij het serveren van statische bestanden. Hiermee kun je standaard MIME-typen overschrijven of MIME-typen definiëren voor aangepaste extensies. De sleutel in de map is de bestandsextensie (zonder de punt), en de waarde is het MIME-type. | `{}`                  |

## Configureren van `web.xml` {#configuring-webxml}

Het `web.xml`-bestand is een essentieel configuratiebestand voor Java-webapps, en in webforJ definieert het belangrijke instellingen zoals de servletconfiguratie, URL-patronen en welkomstpagina's. Dit bestand moet zich bevinden in de map `WEB-INF` van de implementatiestructuur van je project.

| Instelling                                | Uitleg                                                                                                                                                                                          | Standaardwaarde           |
|-------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------|
| **`<display-name>`**                      | Stelt de weergavenaam voor de web-app in, typisch afgeleid van de projectnaam. Deze naam verschijnt in de beheersconsole van app-servers.                                                        | `${project.name}`          |
| **`<servlet>` en `<servlet-mapping>`**   | Definieert de `WebforjServlet`, de kernservlet voor het verwerken van webforJ-verzoeken. Deze servlet is gekoppeld aan alle URL's (`/*`), waardoor het het hoofdtoegangspunt voor webverzoeken is. | `WebforjServlet`           |
| **`<load-on-startup>`**                   | Geeft aan dat `WebforjServlet` moet worden geladen wanneer de app start. Dit instellen op `1` maakt de servlet onmiddellijk laadbaar, wat de verwerking van eerste verzoeken verbetert.             | `1`                        |
