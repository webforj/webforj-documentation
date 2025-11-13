---
title: Configuratie
sidebar_position: 30
sidebar_class_name: updated-content
_i18n_hash: fe000276baa9ac8b0773e5c4372d8463
---
# Configureren van webforJ-eigenschappen

Om een webforJ-app succesvol te implementeren en uit te voeren, zijn enkele belangrijke configuratiebestanden vereist: `webforj.conf` en `web.xml`. Elk van deze bestanden bestuurt verschillende aspecten van het gedrag van de app, van toegangspunten en debuginstellingen tot servlet-mappingen.

## Configureren van `webforj.conf` {#configuring-webforjconf}

Het `webforj.conf`-bestand is een kernconfiguratiebestand in webforJ, waarin app-instellingen zoals toegangspunten, debugmodus en client-server-interactie zijn gespecificeerd. Het bestand bevindt zich in [HOCON-formaat](https://github.com/lightbend/config/blob/master/HOCON.md) en zou in de map `resources` moeten staan.

:::tip
Als je integreert met [Spring](../integrations/spring/overview.md), kun je deze `webforj.conf`-eigenschappen instellen in het bestand `application.properties`.
:::

### Voorbeeld `webforj.conf`-bestand {#example-webforjconf-file}

```Ini
# Dit configuratiebestand is in HOCON-formaat:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Configuratieopties {#configuration-options}

| Eigenschap                           | Type    | Uitleg                                                          | Standaard               |
|--------------------------------------|---------|------------------------------------------------------------------|-------------------------|
| **`webforj.assetsCacheControl`**     | String  | Cache-Control header voor statische bronnen.                     | `null`                  |
| **`webforj.assetsDir`**              | String  | De routenaam die wordt gebruikt om statische bestanden te serveren, terwijl de daadwerkelijke mapnaam `static` blijft. Deze configuratie is nuttig als de standaard `static`-route conflicteert met een route die in jouw app is gedefinieerd, zodat je de routenaam kunt wijzigen zonder de map zelf te hernoemen. | `null`                  |
| **`webforj.assetsExt`**              | String  | Standaardbestandsextensie voor statische bestanden.              | `null`                  |
| **`webforj.assetsIndex`**            | String  | Standaardbestand dat wordt geserveerd voor directoryverzoeken (bijv. index.html). | `null`                  |
| **`webforj.clientHeartbeatRate`**    | String  | Het interval waarop de client de server pingt om te controleren of deze nog actief is. Stel dit in op een kortere interval, bijvoorbeeld `8s`, voor ontwikkeling, om serverbeschikbaarheid snel te detecteren. Stel het in op 50 seconden of langer in productie om overmatige verzoeken te voorkomen. | `50s`                  |
| **`webforj.components`**             | String  | Wanneer gespecificeerd, bepaalt het basispad waar DWC-componenten worden geladen. Standaard worden componenten geladen van de server die de app host. Het instellen van een aangepast basispad stelt componenten in staat om van een alternatieve server of CDN te worden geladen. Bijvoorbeeld, om componenten van jsdelivr.com te laden, stel je het basispad in op: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} Het is belangrijk dat de geladen componenten compatibel zijn met de versie van het webforJ-framework dat in gebruik is; anders functioneert de app mogelijk niet zoals verwacht. Deze instelling wordt genegeerd wanneer een standaard BBj-installatie zonder de engine wordt gebruikt. Voor een standaard BBj-installatie kan de instelling worden beheerd met de `!COMPONENTS` STBL. | `null`                  |
| **`webforj.debug`**                  | Boolean | Schakelt de debugmodus in. In de debugmodus zal webforJ extra informatie naar de console afdrukken en alle uitzonderingen in de browser tonen. Debugmodus is standaard uitgeschakeld. | `null`                  |
| **`webforj.entry`**                  | String  | Definieert het toegangspunt van de app door de volledig gekwalificeerde naam van de klasse op te geven die `webforj.App` uitbreidt. Als er geen toegangspunt is gedefinieerd, zal webforJ automatisch de classpath scannen op klassen die `webforj.App` uitbreiden. Als er meerdere klassen worden gevonden, treedt er een fout op. Wanneer een package meer dan één potentieel toegangspunt bevat, is het nodig om dit expliciet in te stellen om ambiguïteit te voorkomen, of alternatieven kan de annotatie `AppEntry` worden gebruikt om het toegangspunt tijdens runtime op te geven. | `null`                  |
| **`webforj.fileUpload.accept`**      | List    | De toegestane bestandstypen voor bestand uploads. Standaard zijn alle bestandstypen toegestaan. Ondersteunde indelingen omvatten MIME-types zoals `image/*`, `application/pdf`, `text/plain`, of bestandsextensies zoals `*.txt`. Bij gebruik van een standaard BBj-installatie wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `[]`                    |
| **`webforj.fileUpload.maxSize`**     | Long    | De maximale bestandsgrootte die is toegestaan voor bestand uploads, in bytes. Standaard is er geen limiet. Bij gebruik van een standaard BBj-installatie wordt deze instelling genegeerd en beheerd via `fileupload-accept.txt`. | `null`                  |
| **`webforj.iconsDir`**               | String  | URL-eindpunt voor de iconenmap (standaard vanaf `resources/icons/` serven). | `icons/`                |
| **`webforj.license.cfg`**            | String  | De map voor de licentieconfiguratie. Standaard is het dezelfde als de configuratiemap van webforJ, maar dit kan indien nodig gepersonaliseerd worden. | `"."`                   |
| **`webforj.license.startupTimeout`** | Integer | Licentie opstarttimeout in seconden. | `null`                  |
| **`webforj.locale`**                 | String  | De lokale instelling voor de app, die de taal, regionale instellingen en formaten voor datums, tijden en getallen bepaalt. | `null`                  |
| **`webforj.quiet`**                  | Boolean | Schakelt de laadafbeelding uit tijdens het opstarten van de applicatie. | `false`                 |
| **`webforj.reloadOnServerError`**    | Boolean | **Alleen voor ontwikkelomgevingen.** In een ontwikkelomgeving, auto-herladen van de pagina bij fouten gerelateerd aan hot redeployment, maar niet andere fouttypes. Bij het gebruik van hot redeploy, als de client een verzoek naar de server stuurt terwijl deze opnieuw opstart, kan er een fout optreden terwijl het WAR-bestand wordt verwisseld. Omdat de server waarschijnlijk snel weer online is, stelt deze instelling de client in staat om automatisch een pagina-herlaadpoging te doen. | `false`                 |
| **`webforj.servlets[n].name`**       | String  | Servletnaam (gebruik de klassenaam als deze niet is gespecificeerd). | `null`                  |
| **`webforj.servlets[n].className`**  | String  | Volledig gekwalificeerde klassenaam van de servlet. | `null`                  |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Initialisatieparameters voor de servlet. | `null`                  |
| **`webforj.sessionTimeout`**         | Integer | Sessietimeoutduur in seconden. | `60`                    |
| **`webforj.stringTable`**            | `Map<String,String>` | Een kaart van sleutel-waarde-paren die worden gebruikt om strings op te slaan voor gebruik in de app. Nuttig voor het opslaan van app-berichten of labels. Meer informatie over `StringTable` is te vinden [hier](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`                    |
| **`webforj.mime.extensions`**        | `Map<String,String>` | Aangepaste MIME-type mappings voor bestandsextensies bij het serveren van statische bestanden. Hiermee kun je standaard MIME-types overschrijven of MIME-types voor aangepaste extensies definiëren. De sleutel van de map is de bestandsextensie (zonder de punt), en de waarde is het MIME-type. | `{}`                    |

## Configureren van `web.xml` {#configuring-webxml}

Het `web.xml`-bestand is een essentieel configuratiebestand voor Java-webapps, en in webforJ definieert het belangrijke instellingen zoals de servletconfiguratie, URL-patronen en welkomstpagina's. Dit bestand moet zich in de map `WEB-INF` van de implementatiestructuur van jouw project bevinden.

| Instelling                            | Uitleg                                                                                                                                                                  | Standaardwaarde           |
|---------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------|
| **`<display-name>`**                  | Stelt de weergavenaam in voor de webapp, meestal afgeleid van de projectnaam. Deze naam verschijnt in het beheersconsole van de appservers.                             | `${project.name}`         |
| **`<servlet>` en `<servlet-mapping>`** | Definieert de `WebforjServlet`, de kernservlet voor het afhandelen van webforJ-verzoeken. Deze servlet is gemapt naar alle URL's (`/*`), waardoor het de belangrijkste toegangspunt voor webverzoeken is. | `WebforjServlet`          |
| **`<load-on-startup>`**               | Geeft aan dat `WebforjServlet` moet worden geladen wanneer de app start. Door dit in te stellen op `1`, wordt de servlet onmiddellijk geladen, wat de initiale verzoekverwerking verbetert. | `1`                       |
