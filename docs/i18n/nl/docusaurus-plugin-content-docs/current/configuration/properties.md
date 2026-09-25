---
title: Property-configuratie
sidebar_position: 1
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
sidebar_class_name: updated-content
_i18n_hash: 0f672146394b053aaa5d59a7e59841b2
---
# Configureren van webforJ-eigenschappen

Om een webforJ-app met succes te implementeren en uit te voeren, zijn een paar belangrijke configuratiebestanden vereist: `webforj.conf` en `web.xml`. Elk van deze bestanden regelt verschillende aspecten van het gedrag van de app, van ingangs- en debuginstellingen tot servlet-mappingen.

## Configureren van `webforj.conf` {#configuring-webforjconf}

Het `webforj.conf`-bestand is een kernconfiguratiebestand in webforJ, dat app-instellingen specificeert zoals ingangen, debugmodus en interactie tussen client en server. Het bestand is in [HOCON-formaat](https://github.com/lightbend/config/blob/master/HOCON.md) en moet zich in de `resources`-map bevinden.

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

### Configuratieopties {#configuration-options}

| Eigenschap                             | Type    | Uitleg                                                       | Standaard                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | String  | Cache-Control-header voor statische bronnen.                        | `null` |
| **`webforj.assetsDir`**              | String  | De routernaam die wordt gebruikt om statische bestanden te serveren, terwijl de werkelijke mapnaam `static` blijft. Deze configuratie is nuttig als de standaard `static`-route in conflict komt met een route die in jouw app is gedefinieerd, waardoor je de routernaam kunt wijzigen zonder de map zelf te hernoemen.       | `null`               |
| **`webforj.assetsExt`**              | String  | Standaard bestandsextensie voor statische bestanden.| `null` |
| **`webforj.assetsIndex`**            | String  | Standaardbestand dat wordt geserveerd voor directoryverzoeken (bijv. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | Het interval waarop de client de server pings om te zien of deze nog actief is. Voor ontwikkeling, stel dit in op een kortere interval, bijvoorbeeld `8s`, om snel de beschikbaarheid van de server te detecteren. Stel dit in op 50 seconden of hoger in productie om overmatige aanvragen te vermijden. | `50s`           |
| **`webforj.components`**             | String  | Wanneer gespecificeerd, bepaalt het basispad waar DWC-componenten worden geladen. Standaard worden componenten geladen vanaf de server die de app host. Echter, een aangepast basispad instellen maakt het mogelijk om componenten te laden van een alternatieve server of CDN. Bijvoorbeeld, om componenten van jsdelivr.com te laden, stel je het basispad in op: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Het is belangrijk dat de geladen componenten compatibel zijn met de versie van het webforJ-framework dat in gebruik is; anders werkt de app mogelijk niet zoals verwacht. Deze instelling wordt genegeerd wanneer een standaard BBj-installatie zonder de engine wordt gebruikt. Voor een standaard BBj-installatie kan de instelling worden beheerd met de `!COMPONENTS` STBL. | `null`          |
| **`webforj.debug`**                  | Boolean | Schakelt de debugmodus in. In de debugmodus zal webforJ extra informatie naar de console printen en zullen alle uitzonderingen in de browser worden weergegeven. Debugmodus is standaard uitgeschakeld. | `null`          |
| **`webforj.devtools.craftforj.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | **Alleen voor ontwikkelomgevingen.** Schakelt [craftforJ](../craftforj/overview.md) in, de ontwikkelomgeving die de draaiende app inspecteert, component eigenschappen bewerkt en wijzigingen terugschrijft naar Java-bron. Vereist dat `webforj.debug` ook ingeschakeld is. Geen van beide eigenschappen is op zichzelf voldoende. | `false` |
| **`webforj.devtools.craftforj.hosts-allowed`**&nbsp;<DocChip chip='since' label='26.02' /> | Lijst | Clientadressen die toegang hebben tot craftforJ buiten de machine waarop de app draait. Standaard kan alleen een browser op die machine het bereiken. Een invoer die eindigt op `*` komt overeen met een prefix, en een enkele `*` verwijdert de beperking. Zie [craftforJ-beveiliging](../craftforj/security.md). | alleen loopback |
| **`webforj.devtools.craftforj.project-root`**&nbsp;<DocChip chip='since' label='26.02' /> | String | De map waar craftforJ naar je bronnen kijkt, voor gevallen waarin het dat niet kan bepalen op basis van hoe de app is gestart. | gedetecteerd |
| **`webforj.devtools.craftforj.source-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Of craftforJ wijzigingen in eigenschappen en route-toegangsregels in je Java-bron mag schrijven. | `true` |
| **`webforj.devtools.craftforj.stylesheet-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Of craftforJ thema's en stijlen in je app-stylesheet mag opslaan. | `true` |
| **`webforj.devtools.craftforj.ai.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Of de [craftforJ AI-assistent](../craftforj/ai.md) beschikbaar is. | `true` |
| **`webforj.devtools.craftforj.ai.freeform-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Of de assistent zelf Java mag schrijven in plaats van alleen eigenschappen te wijzigen. Elke wijziging moet nog steeds compileren en vereist jouw goedkeuring. | `true` |
| **`webforj.entry`**                  | String  | Definieert het toegangspunt van de app door de volledig gekwalificeerde naam van de klasse op te geven die `webforj.App` uitbreidt. Als er geen toegangspunt is gedefinieerd, zal webforJ automatisch de classpath scannen naar klassen die `webforj.App` uitbreiden. Als meerdere klassen worden gevonden, zal er een fout optreden. Wanneer een pakket meer dan één potentieel toegangspunt bevat, is het expliciet instellen hiervan vereist om ambiguïteit te voorkomen, of alternatieven kan de annotatie `AppEntry` worden gebruikt om het toegangspunt tijdens runtime te specificeren. | `null`          |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Lijst | Lijst van ondersteunde locale's als BCP 47-taallabels (bijv. `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Wanneer auto-detectie is ingeschakeld, worden de voorkeurslocale's van de browser vergeleken met deze lijst. De eerste locale in de lijst wordt gebruikt als de standaard fallback. Zie [Vertaling](../advanced/i18n-localization.md). | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Wanneer `true`, wordt de applicatielocale automatisch ingesteld op basis van de voorkeurstaal van de browser bij het opstarten. De locale wordt bepaald door de voorkeurlocale's van de browser te vergelijken met de `supported-locales`-lijst. Wanneer `false` of wanneer `supported-locales` leeg is, gebruikt de applicatie `webforj.locale`. Zie [Vertaling](../advanced/i18n-localization.md). | `false` |
| **`webforj.fileUpload.accept`**      | Lijst    | De toegestane bestandstypen voor bestanduploads. Standaard zijn alle bestandstypen toegestaan. Ondersteunde formaten omvatten MIME-types zoals `image/*`, `application/pdf`, `text/plain`, of bestandsextensies zoals `*.txt`. Wanneer een standaard BBj-installatie wordt gebruikt, wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | De maximale bestandsgrootte die is toegestaan voor bestanduploads, in bytes. Standaard is er geen limiet. Wanneer een standaard BBj-installatie wordt gebruikt, wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `null`          |
| **`webforj.iconsDir`**               | String  | URL-eindpunt voor de icons-map (standaard serveert vanuit `resources/icons/`). | `icons/` |
| **`webforj.legacyHtmlInText`**&nbsp;<DocChip chip='since' label='26.01' /> | Boolean | Wanneer `true`, wordt een waarde die in `<html>` is gewikkeld, weergegeven als HTML. Wanneer `false`, wordt dezelfde waarde letterlijk weergegeven. | `true` |
| **`webforj.license.cfg`**            | String  | De map voor de licentieconfiguratie. Standaard is dit dezelfde als de configuratiemap van webforJ, maar dit kan indien nodig worden aangepast. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Tijdslimiet voor licentie bij opstart in seconden. | `null` |
| **`webforj.locale`**                 | String  | De locale voor de app, die taal, regio-instellingen en formaten voor data, tijden en nummers bepaalt. | `null` |
| **`webforj.quiet`**                  | Boolean | Schakelt de laadafbeelding uit tijdens de opstart van de applicatie. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Alleen voor ontwikkelomgevingen.** In een ontwikkelomgeving, automatisch de pagina herladen bij fouten die verband houden met warme herimplementatie, maar niet bij andere fouttypen. Wanneer warme herimplementatie wordt gebruikt, kan er een fout optreden als de client een verzoek naar de server stuurt terwijl deze opnieuw opstart. Aangezien de server waarschijnlijk snel weer online zal zijn, stelt deze instelling de client in staat om een pagina automatisch opnieuw te laden. | `false` |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Het grootste verzoek dat de app accepteert, in bytes, ter bescherming tegen te grote verzoeken die bedoeld zijn om servergeheugen uit te putten. Stel in op `0` om de limiet uit te schakelen. | `0` |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Hoeveel nieuwe applicatiesessies de app elke minuut zal starten, ter bescherming tegen snelle sessiecreatie die bedoeld is om serverbronnen uit te putten. Stel in op `0` om de snelheid te beperken. | `0` |
| **`webforj.servlets[n].name`**       | String  | Servletnaam (maakt gebruik van de klasnaam als deze niet is opgegeven). | `null` |
| **`webforj.servlets[n].className`**  | String | Volledig gekwalificeerde classnaam van de servlet. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet-initialisatieparameters. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Duur van de sessietijdslimiet in seconden. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Een kaart van sleutel-waardeparen die worden gebruikt om strings op te slaan voor gebruik in de app. Handig voor het opslaan van app-berichten of labels. Meer informatie over `StringTable` is te vinden [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Aangepaste MIME-type mappings voor bestandsextensies wanneer statische bestanden worden geserveerd. Hiermee kun je standaard MIME-types overschrijven of MIME-types voor aangepaste extensies definiëren. De sleutel van de kaart is de bestandsextensie (zonder de punt), en de waarde is het MIME-type. | `{}`            |

## Configureren van `web.xml` {#configuring-webxml}

Het `web.xml`-bestand is een essentieel configuratiebestand voor Java-webapps, en in webforJ definieert het belangrijke instellingen zoals de servletconfiguratie, URL-patronen en welkomstpagina's. Dit bestand moet zich in de `WEB-INF`-map van de implementatiestructuur van je project bevinden.

| Instelling                                 | Uitleg                                                                                                                                                                                   | Standaardwaarde               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Stelt de weergavenaam in voor de webapp, doorgaans afgeleid van de projectnaam. Deze naam verschijnt in de beheertoevoegingen van app-servers.                                                        | `${project.name}`           |
| **`<servlet>` en `<servlet-mapping>`** | Definieert de `WebforjServlet`, de belangrijkste servlet voor het verwerken van webforJ-verzoeken. Deze servlet is gekoppeld aan alle URL's (`/*`), waardoor het het belangrijkste toegangspunt voor webverzoeken is.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Specificeert dat `WebforjServlet` moet worden geladen wanneer de app start. Dit instellen op `1` zorgt ervoor dat de servlet onmiddellijk wordt geladen, wat de initiële verzoekverwerking verbetert.                | `1`                         |
