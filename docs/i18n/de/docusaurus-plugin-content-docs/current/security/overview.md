---
sidebar_position: 1
title: Security
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Protect webforJ routes with declarative annotations and centralized
  authentication and authorization enforcement.
_i18n_hash: 850b9636996cb17a07a7aff25ac3cd0e
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Sicherheit <DocChip chip='since' label='25.10' />

:::note Öffentliches Preview
Dieses Feature befindet sich in der öffentlichen Vorschau und ist bereit für den produktiven Einsatz. Während der Vorschauperiode können APIs basierend auf dem Feedback der Entwicklergemeinschaft verfeinert werden. Änderungen werden im Voraus durch Veröffentlichungsnotizen angekündigt, und Migrationsleitfäden werden bereitgestellt, wenn dies erforderlich ist.
:::

In modernen Webanwendungen bezieht sich **Sicherheit** auf die Kontrolle des Zugriffs auf verschiedene Teile deiner App basierend auf der Benutzeridentität und den Berechtigungen. In webforJ bietet die Sicherheit ein Framework für die **Zugriffskontrolle auf Routenebene**, bei dem du Ansichten schützen, eine Authentifizierung erfordern und rollenbasierte Berechtigungen durchsetzen kannst.

<AISkillTip skill="webforj-securing-apps" />

## Traditionelles VS gesichertes Routing {#traditional-vs-secured-routing}

Im traditionellen unsicheren Routing sind alle Routen in deiner App für jeden zugänglich, der die URL kennt. Das bedeutet, dass Benutzer zu sensiblen Seiten wie Admin-Panels oder Benutzer-Dashboards navigieren können, ohne dass eine Authentifizierung oder Autorisierungsprüfungen stattfinden. Die Verantwortung liegt bei den Entwicklern, die Berechtigungen in jeder Komponente manuell zu überprüfen, was zu inkonsistenter Durchsetzung der Sicherheit und potenziellen Schwachstellen führt.

Dieser Ansatz führt zu mehreren Problemen:

1. **Manuelle Überprüfungen**: Entwickler müssen daran denken, in jeder geschützten Ansicht oder jedem Layout Sicherheitslogik hinzuzufügen.
2. **Inkonsistente Durchsetzung**: Sicherheitsprüfungen, die im gesamten Code verstreut sind, führen zu Lücken und Fehlern.
3. **Wartungsaufwand**: Änderungen der Zugriffsrichtlinien erfordern das Aktualisieren mehrerer Dateien.
4. **Keine zentrale Kontrolle**: Es gibt keinen einzigen Ort, um die Sicherheit der App zu verstehen oder zu verwalten.

**Gesichertes Routing** in webforJ löst dieses Problem, indem Zugriffskontrolle direkt auf Routenebene ermöglicht wird. Das Sicherheitssystem erzwingt automatisch Regeln, bevor eine Komponente gerendert wird, was einen zentralisierten, deklarativen Ansatz zur Sicherheit der App bietet. So funktioniert es:

1. **Deklarative Annotations**: Markiere Routen mit Sicherheitsannotations, um Zugriffsanforderungen zu definieren.
2. **Automatische Durchsetzung**: Das Sicherheitssystem prüft die Berechtigungen, bevor eine Ansicht gerendert wird.
3. **Zentralisierte Konfiguration**: Definiere das Sicherheitsverhalten an einem Ort und wende es konsistent an.
4. **Flexibele Implementierungen**: Wähle zwischen der Integration von Spring Security oder einer benutzerdefinierten Implementierung in reinem Java.

Dieses Design ermöglicht **Authentifizierung** (Verifizierung der Identität des Benutzers) und **Autorisierung** (Verifizierung, auf was der Benutzer zugreifen kann), sodass nur autorisierte Benutzer Zugang zu geschützten Routen erhalten. Unautorisierte Benutzer werden automatisch umgeleitet oder der Zugang wird basierend auf den konfigurierten Sicherheitsregeln verweigert.

## Beispiel für gesichertes Routing in webforJ {#example-of-secured-routing-in-webforj}

Hier ist ein Beispiel, das verschiedene Sicherheitslevels in einer webforJ-App zeigt:

```java title="LoginView.java"
// Öffentliche Anmeldeseite - jeder kann darauf zugreifen
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {
  private final Login self = getBoundComponent();

  public LoginView() {
    self.onSubmit(e -> {
      handleLogin(e.getUsername(), e.getPassword());
    });

    whenAttached().thenAccept(c -> {
      self.open();
    });
  }
}
```

```java title="ProductsView.java"
// Produkte - erfordert Authentifizierung
@Route(value = "/", outlet = MainLayout.class)
public class ProductsView extends Composite<FlexLayout> {

  public ProductsView() {
    // Produkteansicht
  }
}
```

```java title="InvoicesView.java"
// Rechnungen - erfordert die ROLLE ACCOUNTANT
@Route(value = "/invoices", outlet = MainLayout.class)
@RolesAllowed("ACCOUNTANT")
public class InvoicesView extends Composite<FlexLayout> {

  public InvoicesView() {
    // Rechnungsansicht
  }
}
```

In diesem Setup:

- Die `LoginView` ist mit `@AnonymousAccess` gekennzeichnet, was unauthentifizierten Benutzern den Zugriff ermöglicht.
- Die `ProductsView` hat keine Sicherheitsannotation, was bedeutet, dass sie standardmäßig eine Authentifizierung erfordert (wenn der `secure-by-default`-Modus aktiviert ist).
- Die `InvoicesView` erfordert die Rolle `ACCOUNTANT`, sodass nur Benutzer mit Buchhaltungsberechtigungen auf Rechnungen zugreifen können.

## Wie Sicherheit funktioniert {#how-security-works}

Wenn ein Benutzer versucht, zu einer Route zu navigieren, folgt das Sicherheitssystem diesem Ablauf:

1. **Navigation initiiert**: Benutzer klickt auf einen Link oder gibt eine URL ein.
2. **Sicherheitsüberprüfung**: Bevor die Komponente gerendert wird, bewertet das System die Sicherheitsannotations und -regeln.
3. **Entscheidung**: Basierend auf dem Authentifizierungsstatus und den Rollen des Benutzers:
   - **Gewähren**: Navigation erlauben und die Komponente rendern.
   - **Verweigern**: Navigation blockieren und zur Anmeldeseite oder zur Seite "Zugriff verweigert" umleiten.
4. **Rendern oder Umleiten**: Entweder wird die angeforderte Komponente angezeigt, oder der Benutzer wird entsprechend umgeleitet.

Mit automatischer Durchsetzung werden Sicherheitsregeln konsistent in der gesamten App angewendet, sodass die Zugriffskontrolle behandelt wird, bevor eine Komponente gerendert wird, und Entwickler nicht manuelle Überprüfungen in jeder Ansicht hinzufügen müssen.

## Authentifizierung VS Autorisierung {#authentication-vs-authorization}

Um Sicherheit in deiner App korrekt zu implementieren, ist es wichtig, den Unterschied zwischen diesen beiden Konzepten zu kennen:

- **Authentifizierung**: Verifizierung, wer der Benutzer ist. Dies geschieht typischerweise während der Anmeldung, wenn der Benutzer Anmeldeinformationen (Benutzername und Passwort) bereitstellt. Nach der Authentifizierung wird die Identität des Benutzers in der Sitzung oder im Sicherheitskontext gespeichert.

- **Autorisierung**: Verifizierung, auf was der authentifizierte Benutzer zugreifen kann. Dabei wird überprüft, ob der Benutzer über die erforderlichen Rollen oder Berechtigungen verfügt, um auf eine bestimmte Route zuzugreifen. Die Autorisierung erfolgt jedes Mal, wenn ein Benutzer zu einer geschützten Route navigiert.

Das Sicherheitssystem von webforJ behandelt beide Aspekte:

- Annotations wie `@PermitAll` behandeln Anforderungen an die Authentifizierung.
- Annotations wie `@RolesAllowed` behandeln Anforderungen an die Autorisierung.

## Erste Schritte {#getting-started}

Dieser Leitfaden geht davon aus, dass du **Spring Boot mit Spring Security** verwendest, was der empfohlene Ansatz für die meisten webforJ-Anwendungen ist. Spring Security bietet branchenübliche Authentifizierungs- und Autorisierungsmechanismen mit automatischer Konfiguration durch Spring Boot.

Der Rest dieser Dokumentation führt dich durch die Sicherung deiner Routen mit Spring Security, vom einfachen Setup bis zu fortgeschrittenen Funktionen. Wenn du Spring Boot nicht verwendest oder eine benutzerdefinierte Sicherheitsimplementierung benötigst, sieh dir den [Sicherheitsarchitektur Leitfaden](/docs/security/architecture/overview) an, um zu erfahren, wie das System funktioniert und wie du benutzerdefinierte Sicherheit implementieren kannst.

## Themen {#topics}

<DocCardList className="topics-section" />
