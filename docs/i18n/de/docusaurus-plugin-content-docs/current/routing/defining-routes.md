---
sidebar_position: 3
title: Defining Routes
_i18n_hash: 6d7133c5636f63b82b13dd0a07a97620
---
Routen zu definieren ist entscheidend, um URLs bestimmten Komponenten zuzuordnen. Dadurch können Sie steuern, wie verschiedene Teile Ihrer Benutzeroberfläche basierend auf der URL-Struktur gerendert werden. Das Framework verwendet die `@Route`-Annotation, um diesen Prozess deklarativ und einfach zu gestalten, sodass der Bedarf an manueller Konfiguration reduziert wird.

:::info Routenregistrierung
Routen können statisch mit der `@Route`-Annotation oder dynamisch über die `RouteRegistry`-API registriert werden. Weitere Informationen finden Sie in der [Dokumentation zur Routenregistrierung](./routes-registration).
:::

## Definieren einer Route mit `@Route` {#defining-a-route-with-route}

Die `@Route`-Annotation wird verwendet, um einen URL-Pfad an eine bestimmte Komponente zu binden. Dadurch kann die Komponente gerendert werden, wann immer die App zu dieser URL navigiert. Hier ist ein einfaches Beispiel:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Komponentenlogik hier
}
```

In diesem Beispiel:
- Die `DashboardView`-Komponente ist an die URL `/dashboard` gebunden.
- Wenn ein Benutzer zu `/dashboard` navigiert, wird die `DashboardView` dynamisch vom Framework gerendert.

### Der `value`-Parameter {#the-value-parameter}

Der `value`-Parameter in der `@Route`-Annotation definiert den URL-Pfad. Dies kann ein statischer Pfad wie `"dashboard"` oder dynamischer sein, um flexibles Routing zu ermöglichen.

```java
@Route(value = "user/:id")
public class UserView extends Composite<Div> {
  // Komponentenlogik hier
}
```

In diesem Fall wird beim Navigieren zu `/user/123` die `UserView` angezeigt.

:::tip Routenmuster
Das `user/:id` ist als Routemuster bekannt. Der Router kann sowohl einfache Muster verarbeiten, die ein einziges statisches Segment abgleichen, als auch komplexe Muster, die mehrere statische, erforderliche und optionale Segmente abgleichen können. Weitere Informationen zur Konfiguration von Mustern finden Sie in der [Vertiefung zu Routenmuster](./route-patterns).
:::

## Definieren von Routen-Aliasen {#defining-route-aliases}

In einigen Fällen möchten Sie möglicherweise mehreren URLs erlauben, auf dieselbe Komponente zu zeigen. Zum Beispiel möchten Sie vielleicht, dass Benutzer auf ihr Profil über entweder `/profile` oder `/user/me` zugreifen können. webforJ ermöglicht dies durch die **`@RouteAlias`**-Annotation, mit der Sie mehrere Aliase für eine einzige Route definieren können.

Hier ist ein Beispiel, in dem die Komponente sowohl über `/profile` als auch über `/user/me` zugänglich ist:

```java
@Route(value = "profile")
@RouteAlias("user/me")
public class UserProfileView extends Composite<Div> {
  // Komponentenlogik hier
}
```

Das Definieren von Routen-Aliassen erhöht die Flexibilität Ihres Navigationsdesigns und ermöglicht es den Benutzern, dieselben Inhalte über unterschiedliche URLs zuzugreifen.
