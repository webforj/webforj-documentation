---
sidebar_position: 1
title: Routing
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: ca4837305e1ca2ca2b6a4a244c8103f1
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

In modernen Webanwendungen bezieht sich **Routing** auf den Prozess der Verwaltung von Navigation zwischen verschiedenen Ansichten oder Komponenten basierend auf der aktuellen URL oder dem Pfad. In webforJ etabliert Routing ein ausgefeiltes Framework für **Client-seitige Navigation**, bei dem UI-Updates dynamisch erfolgen, ohne vollständige Seitenneuladungen, was die Leistung Ihrer App verbessert.

## Traditionelles vs. Client-seitiges Routing {#traditional-vs-client-side-routing}

Beim traditionellen serverseitigen Routing sendet der Browser, wenn ein Benutzer auf einen Link klickt, eine Anfrage an den Server nach einem neuen Dokument. Der Server antwortet mit einer neuen HTML-Seite, die den Browser zwingt, CSS und JavaScript neu zu evaluieren, das gesamte Dokument neu zu rendern und den Zustand der App zurückzusetzen. Dieser Zyklus führt zu Verzögerungen und Ineffizienzen, da der Browser Ressourcen und den Seitenzustand neu laden muss. Der Prozess umfasst typischerweise:

1. **Anfrage**: Der Benutzer navigiert zu einer neuen URL, was eine Anfrage an den Server auslöst.
2. **Antwort**: Der Server sendet ein neues HTML-Dokument zusammen mit zugehörigen Assets (CSS, JS) zurück.
3. **Rendering**: Der Browser rendert die gesamte Seite neu, verliert oft den Zustand zuvor geladener Seiten.

Dieser Ansatz kann zu Leistungsengpässen und suboptimalen Benutzererfahrungen aufgrund wiederholter vollständiger Seitenneuladungen führen.

**Client-seitiges Routing** in webforJ löst dies, indem es Navigation direkt im Browser ermöglicht, die UI dynamisch aktualisiert, ohne eine neue Anfrage an den Server zu senden. So funktioniert es:

1. **Einfache initiale Anfrage**: Der Browser lädt die App einmal, einschließlich aller erforderlichen Assets (HTML, CSS, JavaScript).
2. **URL-Management**: Der Router hört auf URL-Änderungen und aktualisiert die Ansicht basierend auf der aktuellen Route.
3. **Dynamisches Rendering von Komponenten**: Der Router verknüpft die URL mit einer Komponente und rendert sie dynamisch, ohne die Seite zu aktualisieren.
4. **Zustandserhaltung**: Der Zustand der App bleibt zwischen den Navigationen erhalten, was einen reibungslosen Übergang zwischen den Ansichten gewährleistet.

Dieses Design ermöglicht **Deep Linking** und **URL-gesteuertes Zustandsmanagement**, sodass Benutzer spezifische Seiten innerhalb der App bookmarken und teilen können, während sie ein reibungsloses Erlebnis auf einer einzelnen Seite genießen.

## Kernprinzipien {#core-principles}

- **URL-basiertes Komponenten-Mapping**: In webforJ sind Routen direkt mit UI-Komponenten verbunden. Ein URL-Muster wird einer bestimmten Komponente zugeordnet, die bestimmt, welcher Inhalt basierend auf dem aktuellen Pfad angezeigt wird.
- **Deklaratives Routing**: Routen werden deklarativ definiert, typischerweise unter Verwendung von Anmerkungen. Jede Route entspricht einer Komponente, die gerendert wird, wenn die Route übereinstimmt.
- **Dynamische Navigation**: Der Router wechselt dynamisch zwischen Ansichten, ohne die Seite neu zu laden, und hält die App reaktionsschnell und schnell.

## Beispiel für client-seitiges Routing in webforJ {#example-of-client-side-routing-in-webforj}

Hier ist ein Beispiel für die Definition einer Route für eine `UserProfileView`-Komponente, um Benutzerdetails basierend auf dem `id`-Parameter in der URL anzuzeigen:

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

- Die Navigation zu `/user/john` würde die `UserProfileView`-Komponente rendern.
- Der `id`-Parameter würde `john` aus der URL erfassen und es Ihnen ermöglichen, ihn innerhalb der Komponente zu verwenden, um Benutzerdaten abzurufen und anzuzeigen.

## Themen {#topics}

<DocCardList className="topics-section" />
