---
title: Property Configuration
sidebar_position: 30
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
sidebar_class_name: updated-content
_i18n_hash: c58a4908cfbde685bc0b30f6023e1df6
---
# Configureren van webforJ-eigenschappen

Om een webforJ-app met succes te implementeren en uit te voeren, zijn een paar belangrijke configuratiebestanden vereist: `webforj.conf` en `web.xml`. Elk van deze bestanden beheert verschillende aspecten van het gedrag van de app, van ingangen en debug-instellingen tot servlet-mappingen.

## Configureren van `webforj.conf` {#configuring-webforjconf}

Het `webforj.conf`-bestand is een van de belangrijkste configuratiebestanden in webforJ, waarin app-instellingen worden gespecificeerd, zoals ingangen, debugmodus en client-server-interactie. Het bestand is in [HOCON-formaat](https://github.com/lightbend/config/blob/master/HOCON.md) en moet zich in de map `resources` bevinden.

:::tip
Als je integreert met [Spring](../integrations/spring/overview.md), kun je deze `webforj.conf`-eigenschappen instellen in het `application.properties`-bestand.
:::

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

| Eigenschap                             | Type    | Uitleg                                                       | Standaard                |
|--------------------------------------|---------|---------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | String  | Cache-Control-header voor statische bronnen.                        | `null` |
| **`webforj.assetsDir`**              | String  | De route naam die wordt gebruikt om statische bestanden te serveren, terwijl de daadwerkelijke mapnaam `static` blijft. Deze configuratie is handig als de standaard `static`-route conflicteert met een route die in je app is gedefinieerd, waardoor je de routenamen kunt wijzigen zonder de map zelf te hernoemen.       | `null`               |
| **`webforj.assetsExt`**              | String  | Standaard bestandsextensie voor statische bestanden. | `null` |
| **`webforj.assetsIndex`**            | String  | Standaard bestand dat wordt geserveerd voor mapverzoeken (bijv. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | Het interval waarop de client de server aanroept om te controleren of deze nog leeft. Voor ontwikkeling kun je dit instellen op een kortere interval, bijvoorbeeld `8s`, om snel de beschikbaarheid van de server te detecteren. Stel in productie in op 50 seconden of meer om overmatige verzoeken te voorkomen. | `50s`           |
| **`webforj.components`**             | String  | Wanneer gespecificeerd, bepaalt het basispad waar DWC-componenten worden geladen. Standaard worden componenten vanuit de server die de app host, geladen. Echter, door een aangepast basispad in te stellen, kunnen componenten worden geladen van een alternatieve server of CDN. Bijv. om componenten van jsdelivr.com te laden, stel je het basispad in op: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Het is belangrijk dat de geladen componenten compatibel zijn met de versie van het webforJ-framework dat in gebruik is; anders functioneert de app mogelijk niet zoals verwacht. Deze instelling wordt genegeerd bij gebruik van een standaard BBj-installatie zonder de engine. Voor een standaard BBj-installatie kan de instelling worden beheerd met de `!COMPONENTS` STBL. | `null`          |
| **`webforj.debug`**                  | Boolean | Schakelt de debugmodus in. In de debugmodus zal webforJ extra informatie naar de console printen en alle uitzonderingen in de browser tonen. De debugmodus is standaard uitgeschakeld. | `null`          |
| **`webforj.entry`**                  | String  | Bepaalt het ingangspunt van de app door de volledig gekwalificeerde naam van de klasse op te geven die `webforj.App` uitbreidt. Als er geen ingangspunt is gedefinieerd, zal webforJ automatisch de classpath scannen op klassen die `webforj.App` uitbreiden. Als er meerdere klassen worden gevonden, zal zich een fout voordoen. Wanneer een pakket meer dan één potentieel ingangspunt bevat, is het vereist om dit expliciet in te stellen om ambiguïteit te voorkomen, of alternately kan de annotatie `AppEntry` worden gebruikt om het ingangspunt tijdens runtime te specificeren. | `null`          |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Lijst | Lijst van ondersteunde taalinstellingen als BCP 47-taallabels (bijv. `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Wanneer auto-detectie is ingeschakeld, wordt de voorkeurstaal van de browser vergeleken met deze lijst. De eerste taal in de lijst wordt gebruikt als de standaard terugval. Zie [Vertaling](../advanced/i18n-localization.md). | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Wanneer `waar`, wordt de applicatietaal automatisch ingesteld vanuit de voorkeurstaal van de browser bij het opstarten. De taal wordt opgelost door de voorkeurstalen van de browser te vergelijken met de lijst van `supported-locales`. Wanneer `onwaar` of wanneer `supported-locales` leeg is, gebruikt de applicatie `webforj.locale`. Zie [Vertaling](../advanced/i18n-localization.md). | `onwaar` |
| **`webforj.fileUpload.accept`**      | Lijst    | De toegestane bestandstypen voor bestandsuploads. Standaard zijn alle bestandstypen toegestaan. Ondersteunde formaten omvatten MIME-typen zoals `image/*`, `application/pdf`, `text/plain`, of bestandsextensies zoals `*.txt`. Bij gebruik van een standaard BBj-installatie wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | De maximale bestandsgrootte die is toegestaan voor bestandsuploads, in bytes. Standaard is er geen limiet. Bij gebruik van een standaard BBj-installatie wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `null`          |
| **`webforj.iconsDir`**               | String  | URL-eindpunt voor het pictogrammenmap (standaard bedient vanuit `resources/icons/`). | `icons/` |
| **`webforj.legacyHtmlInText`**&nbsp;<DocChip chip='since' label='26.01' /> | Boolean | Wanneer `waar`, wordt een waarde in `<html>` weergegeven als HTML. Wanneer `onwaar`, wordt dezelfde waarde letterlijk weergegeven. | `waar` |
| **`webforj.license.cfg`**            | String  | De map voor de licentieconfiguratie. Standaard is dit dezelfde als de configuratiemap van webforJ, maar dit kan indien nodig worden aangepast. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Tijdslimiet voor licentie bij opstarten in seconden. | `null` |
| **`webforj.locale`**                 | String  | De locale voor de app, die de taal, regio-instellingen en formaten voor datums, tijden en nummers bepaalt. | `null` |
| **`webforj.quiet`**                  | Boolean | Schakelt de laadafbeelding uit tijdens het opstarten van de applicatie. | `onwaar` |
| **`webforj.reloadOnServerError`**    | Boolean | **Alleen voor ontwikkelomgevingen.** In een ontwikkelomgeving, herlaad de pagina automatisch bij fouten die verband houden met hot redeployment, maar niet andere fouttypen. Bij gebruik van hot redeploy, kan er een fout optreden wanneer de client een verzoek naar de server stuurt terwijl deze opnieuw opstart. Aangezien de server waarschijnlijk snel weer online is, stelt deze instelling de client in staat om automatisch een pagina-herlaadpoging te doen.  | `onwaar` |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Grootste verzoek dat de app zal accepteren, in bytes, ter bescherming tegen te grote verzoeken die zijn bedoeld om servergeheugen te verbruiken. Stel in op `0` om de limiet uit te schakelen. | `0` |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Hoeveel nieuwe applicatiesessies de app elke minuut zal starten, ter bescherming tegen snelle sessiecreatie die bedoeld is om serverbronnen uit te putten. Stel in op `0` om rate limiting uit te schakelen. | `0` |
| **`webforj.servlets[n].name`**       | String  | Servletnaam (gebruikt de klassenaam als deze niet is gespecificeerd). | `null` |
| **`webforj.servlets[n].className`**  | String | Volledig gekwalificeerde klassenaam van de servlet. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Initialisatieparameters voor de servlet. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Duur van de sessietime-out in seconden. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Een map van sleutel-waarde paren die wordt gebruikt om strings op te slaan voor gebruik in de app. Nuttig voor het opslaan van app-berichten of labels. Meer informatie over `StringTable` is hier te vinden [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Aangepaste MIME-type mappings voor bestandsextensies bij het serveren van statische bestanden. Sta je toe om standaard MIME-typen te overschrijven of MIME-typen te definiëren voor aangepaste extensies. De map sleutel is de bestandsextensie (zonder de punt), en de waarde is het MIME-type. | `{}`            |

## Configureren van `web.xml` {#configuring-webxml}

Het `web.xml`-bestand is een essentieel configuratiebestand voor Java-webapps en definieert in webforJ belangrijke instellingen zoals de servletconfiguratie, URL-patronen en welkomstpagina's. Dit bestand moet zich in de map `WEB-INF` van de implementatiestructuur van je project bevinden.

| Instelling                                 | Uitleg                                                                                                                                                                                   | Standaardwaarde               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Stelt de weergavenaam voor de webapp in, die meestal is afgeleid van de projectnaam. Deze naam verschijnt in de beheertoevoegsels van app-servers.                                                        | `${project.name}`           |
| **`<servlet>` en `<servlet-mapping>`** | Definieert de `WebforjServlet`, de kernservlet voor het afhandelen van webforJ-verzoeken. Deze servlet is toegewezen aan alle URL's (`/*`), waardoor het het belangrijkste ingangspunt voor webverzoeken is.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Specificeert dat `WebforjServlet` moet worden geladen wanneer de app start. Door dit in te stellen op `1` wordt de servlet onmiddellijk geladen, wat de afhandeling van initiële verzoeken verbetert.                | `1`                         |
