---
title: Eigenschap Configuratie
sidebar_position: 1
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
sidebar_class_name: updated-content
_i18n_hash: fd84f72aa62a993485d33fe9a94ab0d1
---
# Configureren van webforJ-eigenschappen

Om een webforJ-app succesvol te implementeren en uit te voeren, zijn enkele belangrijke configuratiebestanden vereist: `webforj.conf` en `web.xml`. Elk van deze bestanden controleert verschillende aspecten van de gedrag van de app, van toegangspunten en debug-instellingen tot servlet-mappingen.

## Configureren van `webforj.conf` {#configuring-webforjconf}

Het `webforj.conf`-bestand is een kernconfiguratiebestand in webforJ, waarin app-instellingen worden gespecificeerd, zoals toegangspunten, debug-modus en interactie tussen client en server. Het bestand is in [HOCON-indeling](https://github.com/lightbend/config/blob/master/HOCON.md) en moet zich bevinden in de map `resources`.

:::tip
Als je integreert met [Spring](../integrations/spring/overview.md), kun je deze `webforj.conf`-eigenschappen instellen in het `application.properties`-bestand.
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

### Configuratieopties {#configuration-options}

| Eigenschap                             | Type    | Uitleg                                                         | Standaard                |
|----------------------------------------|---------|----------------------------------------------------------------|---------------------------|
| **`webforj.assetsCacheControl`**      | String  | Cache-Control header voor statische bronnen.                   | `null`                    |
| **`webforj.assetsDir`**               | String  | De route naam die wordt gebruikt om statische bestanden te serveren, terwijl de werkelijke mapnaam `static` blijft. Deze configuratie is nuttig als de standaardroute `static` in conflict komt met een route die in je app is gedefinieerd, waardoor je de routenaam kunt wijzigen zonder de map zelf te hernoemen. | `null`                    |
| **`webforj.assetsExt`**               | String  | Standaard bestandsextensie voor statische bestanden.           | `null`                    |
| **`webforj.assetsIndex`**             | String  | Standaard bestand dat wordt geserveerd voor directoryverzoeken (bijv. index.html).| `null`                    |
| **`webforj.clientHeartbeatRate`**     | String  | Het interval waarop de client de server pingt om te zien of deze nog actief is. Voor ontwikkeling, stel dit in op een korter interval, bijvoorbeeld `8s`, om snel de serverbeschikbaarheid te detecteren. Stel dit in op 50 seconden of langer in productie om overmatige verzoeken te vermijden. | `50s`                     |
| **`webforj.components`**              | String  | Wanneer gespecificeerd, bepaalt het basispad waar DWC-componenten worden geladen. Standaard worden componenten geladen vanaf de server die de app host. Echter, het instellen van een aangepast basispad maakt het mogelijk componenten te laden vanaf een alternatieve server of CDN. Bijvoorbeeld, om componenten van jsdelivr.com te laden, stel je het basispad in op: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Het is belangrijk dat de geladen componenten compatibel zijn met de versie van het webforJ-framework dat in gebruik is; anders werkt de app mogelijk niet zoals verwacht. Deze instelling wordt genegeerd bij het gebruik van een standaard BBj-installatie zonder de engine. Voor een standaard BBj-installatie kan de instelling worden beheerd met de `!COMPONENTS` STBL. | `null`                    |
| **`webforj.debug`**                   | Boolean | Zet de debug-modus aan. In debug-modus zal webforJ extra informatie naar de console printen en alle uitzonderingen in de browser tonen. De debug-modus is standaard uitgeschakeld. | `null`                    |
| **`webforj.devtools.craftforj.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | **Alleen ontwikkeling omgevingen.** Zet [craftforJ](../craftforj/overview.md) aan, de ontwikkelomgeving die de actieve app inspecteert, component-eigenschappen bewerkt en wijzigingen weer terugschrijft naar de Java-bron. Vereist ook dat `webforj.debug` is ingeschakeld. Geen van beide eigenschappen is op zichzelf voldoende. | `false`                   |
| **`webforj.devtools.craftforj.hosts-allowed`**&nbsp;<DocChip chip='since' label='26.02' /> | Lijst   | Clientadressen die zijn toegestaan om craftforJ te bereiken buiten de machine waarop de app draait. Standaard kan alleen een browser op die machine het bereiken. Een invoer die eindigt op `*` komt overeen met een prefix, en een enkele `*` verwijdert de beperking. Zie [craftforJ-beveiliging](../craftforj/security.md). | alleen loopback           |
| **`webforj.devtools.craftforj.source-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Of craftforJ wijzigingen in eigenschappen en route-toegangsregels in je Java-bron mag schrijven. | `true`                    |
| **`webforj.devtools.craftforj.stylesheet-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Of craftforJ thema's en stijlen in de stylesheet van je app mag opslaan. | `true`                    |
| **`webforj.devtools.craftforj.ai.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Of de [craftforJ AI-assistent](../craftforj/ai.md) beschikbaar is. | `true`                    |
| **`webforj.devtools.craftforj.ai.freeform-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Of de assistent Java van zijn eigen mag schrijven in plaats van alleen eigenschappen te wijzigen. Elke wijziging moet nog steeds compileren en moet nog goedgekeurd worden. | `true`                    |
| **`webforj.devtools.project-root`**&nbsp;<DocChip chip='since' label='26.03' /> | String | De directory waar [craftforJ](../craftforj/configuration.md#project-root) naar kijkt voor jouw bronnen, voor gevallen waarin het dat niet kan bepalen op basis van hoe de app is gestart. Vervangt `webforj.devtools.craftforj.project-root`, dat verouderd is maar nog steeds gelezen wordt wanneer de sleutel niet is ingesteld. | gedetecteerd               |
| **`webforj.entry`**                   | String  | Definieert het toegangspunt van de app door de volledig gekwalificeerde naam van de klasse op te geven die `webforj.App` uitbreidt. Als er geen toegangspunt is gedefinieerd, scant webforJ automatisch het classpath naar klassen die `webforj.App` uitbreiden. Als er meerdere klassen worden gevonden, zal er een fout optreden. Wanneer een pakket meer dan één potentieel toegangspunt bevat, is het noodzakelijk om dit expliciet in te stellen om ambiguïteit te voorkomen, of alternately kan de annotatie `AppEntry` worden gebruikt om op runtime het toegangspunt op te geven. | `null`                    |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Lijst   | Lijst van ondersteunde locaties als BCP 47-taagtags (bijv. `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Wanneer auto-detectie is ingeschakeld, worden de voorkeurlocaties van de browser vergeleken met deze lijst. De eerste locatie in de lijst wordt gebruikt als standaardfallback. Zie [Vertaling](../advanced/i18n-localization.md). | `[]`                      |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Wanneer `true`, wordt de applicatielocatie automatisch ingesteld vanuit de voorkeurstaal van de browser bij het opstarten. De locatie wordt opgelost door de voorkeurlocaties van de browser te vergelijken met de lijst `supported-locales`. Wanneer `false` of wanneer `supported-locales` leeg is, gebruikt de applicatie `webforj.locale`. Zie [Vertaling](../advanced/i18n-localization.md). | `false`                   |
| **`webforj.fileUpload.accept`**       | Lijst   | De toegestane bestandstypen voor bestandsuploads. Standaard zijn alle bestandstypen toegestaan. Ondersteunde formaten zijn onder andere MIME-typen zoals `image/*`, `application/pdf`, `text/plain`, of bestandsextensies zoals `*.txt`. Bij het gebruik van een standaard BBj-installatie, wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `[]`                      |
| **`webforj.fileUpload.maxSize`**      | Long    | De maximale bestandsgrootte die is toegestaan voor bestandsuploads, in bytes. Standaard is er geen limiet. Bij het gebruik van een standaard BBj-installatie, wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `null`                    |
| **`webforj.iconsDir`**                | String  | URL-eindpunt voor de iconenmap (standaard dient vanuit `resources/icons/`). | `icons/`                  |
| **`webforj.legacyHtmlInText`**&nbsp;<DocChip chip='since' label='26.01' /> | Boolean | Wanneer `true`, een waarde omgeven door `<html>` rendert zijn inhoud als HTML. Wanneer `false`, wordt dezelfde waarde letterlijk getoond. | `true`                    |
| **`webforj.license.cfg`**             | String  | De map voor de licentieconfiguratie. Standaard is het dezelfde als de webforJ-configuratiemap, maar dit kan indien nodig worden aangepast. | `"."`                     |
| **`webforj.license.startupTimeout`**  | Integer | Licentie opstarttimeout in seconden. | `null`                    |
| **`webforj.locale`**                  | String  | De locatie voor de app, die taal, regio-instellingen en formaten voor data, tijden en nummers bepaalt. | `null`                    |
| **`webforj.quiet`**                   | Boolean | Schakelt de laadbalk uit tijdens het opstarten van de applicatie. | `false`                   |
| **`webforj.reloadOnServerError`**     | Boolean | **Alleen ontwikkeling omgevingen.** In een ontwikkeling omgeving, auto-herladen van de pagina bij fouten gerelateerd aan hot redeployment, maar niet bij andere fouttypes. Bij het gebruik van hot redeploy, als de client een verzoek naar de server verzendt terwijl deze opnieuw opstart, kan er een fout optreden terwijl het WAR-bestand wordt gewisseld. Omdat de server waarschijnlijk snel weer online is, maakt deze instelling het voor de client mogelijk om automatisch te proberen de pagina opnieuw te laden. | `false`                   |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Grootste verzoek dat de app accepteert, in bytes, als beveiliging tegen te grote verzoeken die bedoeld zijn om het servergeheugen uit te putten. Stel in op `0` om de limiet uit te schakelen. | `0`                       |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Hoeveel nieuwe applicatiesessies de app elke minuut zal starten, als beveiliging tegen snelle sessiecreatie die bedoeld is om serverresources uit te putten. Stel in op `0` om snelheidslimiting uit te schakelen. | `0`                       |
| **`webforj.servlets[n].name`**        | String  | Servletnaam (maakt gebruik van de classennaam als deze niet is gespecificeerd). | `null`                    |
| **`webforj.servlets[n].className`**   | String  | Volledig gekwalificeerde naam van de servlet. | `null`                    |
| **`webforj.servlets[n].config.<key>`**| `Map<String,String>` | Servlet-initialisatieparameters. | `null`                    |
| **`webforj.sessionTimeout`**          | Integer | Duur van de sessietimeout in seconden. | `60`                      |
| **`webforj.stringTable`**             | `Map<String,String>` | Een map van sleutelwaardeparen die worden gebruikt om strings op te slaan voor gebruik in de app. Nuttig voor het opslaan van app-berichten of labels. Meer informatie over `StringTable` is te vinden [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`                       |
| **`webforj.mime.extensions`**          | `Map<String,String>` | Aangepaste MIME-type mappings voor bestandsextensies wanneer statische bestanden worden aangeboden. Hiermee kun je standaard MIME-typen overschrijven of MIME-typen definiëren voor aangepaste extensies. De map sleutel is de bestandsextensie (zonder de punt), en de waarde is het MIME-type. | `{}`                       |

## Configureren van `web.xml` {#configuring-webxml}

Het `web.xml`-bestand is een essentieel configuratiebestand voor Java-webapps, en definieert in webforJ belangrijke instellingen zoals de servletconfiguratie, URL-patronen en welkomstpagina's. Dit bestand moet zich bevinden in de map `WEB-INF` van de implementatiestructuur van je project.

| Instelling                             | Uitleg                                                                                                                                                                                                                                | Standaardwaarde            |
|---------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------|
| **`<display-name>`**                  | Stelt de weergavenaam voor de webapp in, meestal afgeleid van de projectnaam. Deze naam verschijnt in de beheertools van app-servers.                                                                                                                                                                                                 | `${project.name}`           |
| **`<servlet>` en `<servlet-mapping>`** | Definieert de `WebforjServlet`, de kernservlet voor het afhandelen van webforJ-verzoeken. Deze servlet is gemapped naar alle URL's (`/*`), en is daarmee het belangrijkste toegangspunt voor webverzoeken.                                             | `WebforjServlet`            |
| **`<load-on-startup>`**               | Geeft aan dat `WebforjServlet` moet worden geladen wanneer de app start. Dit op `1` instellen zorgt ervoor dat de servlet onmiddellijk wordt geladen, wat de afhandeling van initiële verzoeken verbetert.                                                                | `1`                         |
