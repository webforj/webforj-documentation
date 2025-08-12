---
sidebar_position: 3
title: Defining Routes
_i18n_hash: 4f7189d5ef27386506e9ecf950f145ed
---
Die Definition von Routen ist entscheidend, um URLs bestimmten Komponenten zuzuordnen. Dadurch können Sie steuern, wie verschiedene Teile Ihrer Benutzeroberfläche basierend auf der URL-Struktur gerendert werden. Das Framework verwendet die `@Route`-Annotation, um diesen Prozess deklarativ und unkompliziert zu gestalten, wodurch der Bedarf an manueller Konfiguration verringert wird.

:::info Routenregistrierung
Routen können statisch mit der `@Route`-Annotation oder dynamisch über die `RouteRegistry`-API registriert werden. Für weitere Informationen siehe die [Dokumentation zur Routenregistrierung](./routes-registration).
:::

## Eine Route mit `@Route` definieren {#defining-a-route-with-route}

Die `@Route`-Annotation wird verwendet, um einen URL-Pfad an eine bestimmte Komponente zu binden. Dadurch kann die Komponente gerendert werden, wann immer die App zu dieser URL navigiert. Hier ist ein einfaches Beispiel:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Komponentenlogik hier
}
```

In diesem Beispiel:
- Die `DashboardView`-Komponente ist an die `/dashboard`-URL gebunden.
- Wenn ein Benutzer zu `/dashboard` navigiert, wird die `DashboardView` dynamisch vom Framework gerendert.

### Der `value`-Parameter {#the-value-parameter}

Der `value`-Parameter in der `@Route`-Annotation definiert den URL-Pfad. Dies kann ein statischer Pfad wie `"dashboard"` oder dynamischer sein, was flexibles Routing ermöglicht.

```java
@Route(value = "user/:id")
public class UserView extends Composite<Div> {
  // Komponentenlogik hier
}
```

In diesem Fall zeigt das Navigieren zu `/user/123` die `UserView` an.

:::tip Routenmuster
Das `user/:id` wird als Routenmuster bezeichnet. Der Router kann sowohl einfache Muster verarbeiten, die ein einzelnes statisches Segment abgleichen, als auch komplexe Muster, die mehrere statische, erforderliche und optionale Segmente abgleichen können. Für weitere Informationen zur Konfiguration von Mustern siehe den [tieferen Einblick in Routenmuster](./route-patterns).
:::

## Routenaliase definieren {#defining-route-aliases}

In einigen Fällen möchten Sie möglicherweise zulassen, dass mehrere URLs auf dieselbe Komponente verweisen. Beispielsweise möchten Sie möglicherweise, dass Benutzer über `/profile` oder `/user/me` auf ihr Profil zugreifen können. webforJ ermöglicht dies durch die **`@RouteAlias`**-Annotation, die es Ihnen ermöglicht, mehrere Aliase für eine einzelne Route zu definieren.

Hier ist ein Beispiel, in dem die Komponente sowohl über `/profile` als auch über `/user/me` zugänglich ist:

```java
@Route(value = "profile")
@RouteAlias("user/me")
public class UserProfileView extends Composite<Div> {
  // Komponentenlogik hier
}
```

Das Definieren von Routenaliase erhöht die Flexibilität Ihres Navigationsdesigns und ermöglicht es Benutzern, denselben Inhalt über verschiedene URLs zuzugreifen.
