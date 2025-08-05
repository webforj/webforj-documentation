---
sidebar_position: 1
title: Routing
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 115816519ca0212b84eb27923a74ca53
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

In modernen Webanwendungen bezieht sich **Routing** auf den Prozess der Verwaltung der Navigation zwischen verschiedenen Ansichten oder Komponenten basierend auf der aktuellen URL oder dem Pfad. In webforJ etabliert das Routing ein ausgeklügeltes Framework für die **clientseitige Navigation**, bei dem UI-Updates dynamisch erfolgen, ohne dass vollständige Seitenneuladevorgänge erforderlich sind, was die Leistung Ihrer App verbessert.

## Traditionelles vs. clientseitiges Routing {#traditional-vs-client-side-routing}

Beim traditionellen serverseitigen Routing, wenn ein Benutzer auf einen Link klickt, sendet der Browser eine Anfrage an den Server für ein neues Dokument. Der Server antwortet, indem er eine neue HTML-Seite sendet, die den Browser zwingt, CSS und JavaScript neu zu bewerten, das gesamte Dokument neu zu rendern und den Anwendungsstatus zurückzusetzen. Dieser Zyklus führt zu Verzögerungen und Ineffizienzen, da der Browser Ressourcen und den Seitenstatus neu laden muss. Der Prozess umfasst typischerweise:

1. **Anfrage**: Der Benutzer navigiert zu einer neuen URL, was eine Anfrage an den Server auslöst.
2. **Antwort**: Der Server sendet ein neues HTML-Dokument zusammen mit verwandten Assets (CSS, JS) zurück.
3. **Rendering**: Der Browser rendert die gesamte Seite erneut, wodurch oft der Status zuvor geladener Seiten verloren geht.

Dieser Ansatz kann zu Leistungsengpässen und suboptimalen Benutzererfahrungen aufgrund wiederholter vollständiger Seitenneuladevorgänge führen.

**Client-Seitiges Routing** in webforJ löst dies, indem es die Navigation direkt im Browser ermöglicht und die UI dynamisch aktualisiert, ohne eine neue Anfrage an den Server zu senden. So funktioniert es:

1. **Einmalige Initialanfrage**: Der Browser lädt die App einmal, einschließlich aller erforderlichen Assets (HTML, CSS, JavaScript).
2. **URL-Verwaltung**: Der Router hört auf URL-Änderungen und aktualisiert die Ansicht basierend auf der aktuellen Route.
3. **Dynamisches Komponenten-Rendering**: Der Router ordnet die URL einer Komponente zu und rendert sie dynamisch, ohne die Seite zu aktualisieren.
4. **Zustandserhaltung**: Der Zustand der App wird zwischen den Navigationen beibehalten, sodass ein reibungsloser Übergang zwischen den Ansichten gewährleistet ist.

Dieses Design ermöglicht **Deep Linking** und **URL-gesteuertes Zustandsmanagement**, wodurch Benutzer spezifische Seiten innerhalb der App Lesezeichen setzen und teilen können, während sie eine reibungslose, einseitige Erfahrung genießen.

## Grundprinzipien {#core-principles}

- **URL-basiertes Komponenten-Mapping**: In webforJ sind Routen direkt an UI-Komponenten gebunden. Ein URL-Muster wird einer bestimmten Komponente zugeordnet, die bestimmt, welche Inhalte basierend auf dem aktuellen Pfad angezeigt werden.
- **Deklaratives Routing**: Routen werden deklarativ definiert, typischerweise unter Verwendung von Anmerkungen. Jede Route entspricht einer Komponente, die gerendert wird, wenn die Route übereinstimmt.
- **Dynamische Navigation**: Der Router wechselt dynamisch zwischen Ansichten, ohne die Seite neu zu laden, wodurch die App reaktionsschnell und schnell bleibt.

## Beispiel für clientseitiges Routing in webforJ {#example-of-client-side-routing-in-webforj}

Hier ist ein Beispiel zur Definition einer Route für eine `UserProfileView`-Komponente, um Benutzerdetails basierend auf dem `id`-Parameter in der URL anzuzeigen:

```java
@Route(value = "user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String id = parameters.getAlpha("id").orElse("");
    refreshProfile(id);
  }
}
```

In diesem Setup:

- Das Navigieren zu `/user/john` würde die `UserProfileView`-Komponente rendern.
- Der `id`-Parameter würde `john` aus der URL erfassen und es Ihnen ermöglichen, ihn innerhalb der Komponente zu verwenden, um Benutzerdaten abzurufen und anzuzeigen.

## Themen {#topics}

<DocCardList className="topics-section" />
