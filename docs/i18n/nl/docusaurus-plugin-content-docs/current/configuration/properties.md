---
title: Property Configuration
sidebar_position: 30
_i18n_hash: 668649d2e0f92ebc4012e0c58cd1b706
---
# Configureren van webforJ-eigenschappen

Om een webforJ-app met succes te implementeren en uit te voeren, zijn een paar belangrijke configuratiebestanden vereist: `webforj.conf` en `web.xml`. Elk van deze bestanden regelt verschillende aspecten van het gedrag van de app, van toegangspunten en debuginstellingen tot servlet-mappingen.

## Configureren van `webforj.conf` {#configuring-webforjconf}

Het `webforj.conf`-bestand is een kernconfiguratiebestand in webforJ, waarin app-instellingen zoals toegangspunten, debugmodus en interactie tussen cliënt en server worden gespecificeerd. Het bestand is in [HOCON-indeling](https://github.com/lightbend/config/blob/master/HOCON.md) en moet zich bevinden in de `resources`-map.

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
|---------------------------------------|---------|--------------------------------------------------------------|--------------------------|
| **`webforj.assetsCacheControl`**     | String  | Cache-Control-header voor statische bronnen.                 | `null`                   |
| **`webforj.assetsDir`**              | String  | De routenaam die wordt gebruikt om statische bestanden te serveren, terwijl de werkelijke mapnaam `static` blijft. Deze configuratie is handig als de standaard `static`-route conflicteert met een in jouw app gedefinieerde route, zodat je de routenaam kunt wijzigen zonder de map zelf te hernoemen.       | `null`                   |
| **`webforj.assetsExt`**              | String  | Standaard bestandsextensie voor statische bestanden.         | `null`                   |
| **`webforj.assetsIndex`**            | String  | Standaard bestand dat wordt geserveerd voor directory-aanvragen (bijv. index.html). | `null`                   |
| **`webforj.clientHeartbeatRate`**    | String  | Het interval waarin de cliënt de server pingt om te controleren of deze nog alive is. Stel dit tijdens de ontwikkeling in op een kortere interval, bijvoorbeeld `8s`, om de beschikbaarheid van de server snel te detecteren. Stel dit in op 50 seconden of meer in productie om overmatige verzoeken te vermijden. | `50s`                   |
| **`webforj.components`**              | String  | Wanneer gespecificeerd, bepaalt het basispad waar DWC-componenten worden geladen. Standaard worden componenten geladen van de server die de app host. Het instellen van een aangepast basispad maakt het mogelijk componenten te laden van een alternatieve server of CDN. Bijvoorbeeld, om componenten van jsdelivr.com te laden, stel je het basispad in op: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Het is belangrijk dat de geladen componenten compatibel zijn met de versie van het webforJ-framework dat in gebruik is; anders functioneert de app mogelijk niet zoals verwacht. Deze instelling wordt genegeerd bij gebruik van een standaard BBj-installatie zonder de engine. Voor een standaard BBj-installatie kan de instelling worden beheerd met de `!COMPONENTS` STBL. | `null`                   |
| **`webforj.debug`**                  | Boolean | Zet de debugmodus aan. In debugmodus zal webforJ extra informatie naar de console afdrukken en alle uitzonderingen in de browser tonen. De debugmodus is standaard uitgeschakeld. | `null`                   |
| **`webforj.entry`**                  | String  | Definieert het toegangspunt van de app door de volledig gekwalificeerde naam van de klasse op te geven die `webforj.App` uitbreidt. Als er geen toegangspunt is gedefinieerd, scant webforJ automatisch het classpath op klassen die `webforj.App` uitbreiden. Als er meerdere klassen worden gevonden, zal er een fout optreden. Wanneer een pakket meer dan één potentieel toegangspunt bevat, is het expliciet instellen hiervan vereist om ambiguïteit te voorkomen, of alternatief kan de annotatie `AppEntry` worden gebruikt om het toegangspunt tijdens runtime op te geven. | `null`                   |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Lijst | Lijst van ondersteunde locaties als BCP 47-taal-tags (bijv. `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Wanneer automatische detectie is ingeschakeld, worden de voorkeurslocaties van de browser vergeleken met deze lijst. De eerste locatie in de lijst wordt gebruikt als de standaardfallback. Zie [Vertaling](../advanced/i18n-localization.md). | `[]`                     |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Wanneer `true`, wordt de applicatielocatie automatisch ingesteld op basis van de voorkeurs taal van de browser bij opstarten. De locatie wordt opgelost door de voorkeurslocaties van de browser te matchen met de lijst `supported-locales`. Wanneer `false` of wanneer `supported-locales` leeg is, gebruikt de applicatie `webforj.locale`. Zie [Vertaling](../advanced/i18n-localization.md). | `false`                  |
| **`webforj.fileUpload.accept`**      | Lijst    | De toegestane bestandstypen voor bestand uploads. Standaard zijn alle bestandstypen toegestaan. Ondersteunde formaten omvatten MIME-typen zoals `image/*`, `application/pdf`, `text/plain`, of bestandsextensies zoals `*.txt`. Bij gebruik van een standaard BBj-installatie wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `[]`                     |
| **`webforj.fileUpload.maxSize`**     | Long    | De maximale bestandsgrootte die is toegestaan voor bestand uploads, in bytes. Standaard is er geen limiet. Bij gebruik van een standaard BBj-installatie wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `null`                   |
| **`webforj.iconsDir`**               | String  | URL-eindpunt voor het pictogrammenmap (standaard bedient vanuit `resources/icons/`). | `icons/`                  |
| **`webforj.license.cfg`**            | String  | De map voor de licentieconfiguratie. Standaard is dit dezelfde als de webforJ-configuratiemap, maar dit kan indien nodig worden aangepast. | `"."`                    |
| **`webforj.license.startupTimeout`** | Integer | Licentie opstarttimeout in seconden. | `null`                   |
| **`webforj.locale`**                 | String  | De locatie voor de app, die taal, regiovoorwaarden en opmaak voor data, tijden en getallen bepaalt. | `null`                   |
| **`webforj.quiet`**                  | Boolean | Schakelt de laad afbeelding uit tijdens de opstart van de applicatie. | `false`                  |
| **`webforj.reloadOnServerError`**    | Boolean | **Alleen ontwikkelingsomgevingen.** In een ontwikkelingsomgeving, herlaad automatisch de pagina bij fouten gerelateerd aan hot redeployment, maar niet bij andere fouttypen. Bij gebruik van hot redeploy, kan er een fout optreden als de cliënt een verzoek naar de server stuurt terwijl deze opnieuw opstart. Aangezien de server waarschijnlijk snel weer online is, laat deze instelling de cliënt automatisch proberen de pagina opnieuw te laden. | `false`                  |
| **`webforj.servlets[n].name`**       | String  | Servlets naam (gebruikt classnaam als niet gespecificeerd). | `null`                   |
| **`webforj.servlets[n].className`**  | String | Volledig gekwalificeerde classnaam van de servlet. | `null`                   |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet-initialisatieparameters. | `null`                   |
| **`webforj.sessionTimeout`**         | Integer | Sessietimeoutduur in seconden. | `60`                     |
| **`webforj.stringTable`**            | `Map<String,String>` | Een kaart van sleutel-waardeparen die worden gebruikt om strings op te slaan voor gebruik in de app. Nuttig voor het opslaan van app-berichten of labels. Meer informatie over `StringTable` is te vinden [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`                     |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Aangepaste MIME-type mappingen voor bestandsextensies bij het serveren van statische bestanden. Maakt het mogelijk om standaard MIME-typen te overschrijven of MIME-typen voor aangepaste extensies te definiëren. De sleutel van de kaart is de bestandsextensie (zonder de punt), en de waarde is het MIME-type. | `{}`                     |

## Configureren van `web.xml` {#configuring-webxml}

Het `web.xml`-bestand is een essentieel configuratiebestand voor Java-webapps, en in webforJ definieert het belangrijke instellingen zoals de servletconfiguratie, URL-patronen en welkomstpagina's. Dit bestand moet zich bevinden in de `WEB-INF`-map van de implementatiestructuur van jouw project.

| Instelling                             | Uitleg                                                                                                                                                                                        | Standaardwaarde            |
| ------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                  | Stelt de weergeefnaam in voor de webapp, meestal afgeleid van de projectnaam. Deze naam verschijnt in de beheerconsoles van app-servers.                                                     | `${project.name}`           |
| **`<servlet>` en `<servlet-mapping>`** | Definieert de `WebforjServlet`, de kernservlet voor het afhandelen van webforJ-verzoeken. Deze servlet is gemapt op alle URL's (`/*`), waardoor het de belangrijkste toegangspunt voor webverzoeken is. | `WebforjServlet`            |
| **`<load-on-startup>`**               | Specificeert dat `WebforjServlet` moet worden geladen wanneer de app start. Het instellen van dit op `1` zorgt ervoor dat de servlet onmiddellijk laadt, wat de afhandeling van het eerste verzoek verbetert. | `1`                         |
