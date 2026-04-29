---
sidebar_position: 1
title: Security
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: fe28b9f0c456b9880785afcc5d4d5f23
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Sicherheit <DocChip chip='since' label='25.10' />

:::note Öffentliche Vorschau
Dieses Feature befindet sich in der öffentlichen Vorschau und ist bereit für den Produktionsgebrauch. Während der Vorschauphase können APIs basierend auf dem Feedback der Entwicklergemeinschaft verfeinert werden. Alle Änderungen werden im Voraus durch Veröffentlichungsnotizen angekündigt, und Migrationsanleitungen werden bei Bedarf bereitgestellt.
:::

In modernen Webanwendungen bezieht sich **Sicherheit** auf die Kontrolle des Zugangs zu verschiedenen Teilen Ihrer Anwendung basierend auf der Benutzeridentität und den Berechtigungen. In webforJ bietet die Sicherheit ein Framework für die **Zugangskontrolle auf Routenebene**, bei dem Sie Ansichten schützen, eine Authentifizierung erfordern und rollenbasierte Berechtigungen durchsetzen können.

<AISkillTip skill="webforj-securing-apps" />

## Traditionelles VS gesichertes Routing {#traditional-vs-secured-routing}

Beim traditionellen ungesicherten Routing sind alle Routen in Ihrer Anwendung für jeden zugänglich, der die URL kennt. Das bedeutet, dass Benutzer zu sensiblen Seiten wie Admin-Panels oder Benutzerdashboards navigieren können, ohne dass Authentifizierungs- oder Autorisierungsprüfungen erforderlich sind. Die Verantwortung liegt bei den Entwicklern, die Berechtigungen in jeder Komponente manuell zu überprüfen, was zu inkonsistenter Sicherheitsdurchsetzung und potenziellen Schwachstellen führt.

Dieser Ansatz bringt mehrere Probleme mit sich:

1. **Manuelle Überprüfungen**: Entwickler müssen daran denken, Sicherheitslogik in jede geschützte Ansicht oder jedes Layout einzufügen.
2. **Inkonsistente Durchsetzung**: Sicherheitsprüfungen, die im Code verstreut sind, führen zu Lücken und Fehlern.
3. **Wartungsaufwand**: Änderungen der Zugangsregeln erfordern die Aktualisierung mehrerer Dateien.
4. **Kein zentraler Zugriff**: Es gibt keinen einzigen Ort, um die Sicherheit der Anwendung zu verstehen oder zu verwalten.

**Gesichertes Routing** in webforJ löst dieses Problem, indem es die Zugangskontrolle direkt auf der Routenebene ermöglicht. Das Sicherheitssystem wendet automatisch Regeln an, bevor eine Komponente gerendert wird, und bietet einen zentralisierten, deklarativen Ansatz für die Sicherheit der Anwendung. So funktioniert es:

1. **Deklarative Annotationen**: Markieren Sie Routen mit Sicherheitsannotationen, um Zugangsanforderungen zu definieren.
2. **Automatische Durchsetzung**: Das Sicherheitssystem überprüft die Berechtigungen, bevor eine Ansicht gerendert wird.
3. **Zentralisierte Konfiguration**: Definieren Sie das Sicherheitsverhalten an einem Ort und wenden Sie es konsistent an.
4. **Flexible Implementierungen**: Wählen Sie zwischen der Integration von Spring Security oder einer benutzerdefinierten einfachen Java-Implementierung.

Dieses Design ermöglicht **Authentifizierung** (Überprüfung der Identität des Benutzers) und **Autorisierung** (Überprüfung, auf was der Benutzer zugreifen kann), sodass nur autorisierte Benutzer Zugang zu geschützten Routen erhalten. Unautorisierte Benutzer werden je nach konfigurierten Sicherheitsregeln automatisch umgeleitet oder der Zugriff wird verweigert.

## Beispiel für gesichertes Routing in webforJ {#example-of-secured-routing-in-webforj}

Hier ist ein Beispiel, das verschiedene Sicherheitsstufen in einer webforJ-Anwendung zeigt:

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
// Produkte - benötigt Authentifizierung
@Route(value = "/", outlet = MainLayout.class)
public class ProductsView extends Composite<FlexLayout> {

  public ProductsView() {
    // Produktansicht
  }
}
```

```java title="InvoicesView.java"
// Rechnungen - benötigt die Rolle ACCOUNTANT
@Route(value = "/invoices", outlet = MainLayout.class)
@RolesAllowed("ACCOUNTANT")
public class InvoicesView extends Composite<FlexLayout> {

  public InvoicesView() {
    // Rechnungsansicht
  }
}
```

In diesem Setup:

- Die `LoginView` ist mit `@AnonymousAccess` markiert, wodurch nicht authentifizierte Benutzer darauf zugreifen können.
- Die `ProductsView` hat keine Sicherheitsannotation, was bedeutet, dass sie standardmäßig eine Authentifizierung erfordert (wenn der Modus `secure-by-default` aktiviert ist).
- Die `InvoicesView` erfordert die Rolle `ACCOUNTANT`, sodass nur Benutzer mit Buchhaltungsberechtigungen auf Rechnungen zugreifen können.

## Wie Sicherheit funktioniert {#how-security-works}

Wenn ein Benutzer versucht, zu einer Route zu navigieren, folgt das Sicherheitssystem diesem Ablauf:

1. **Navigation initiiert**: Benutzer klickt auf einen Link oder gibt eine URL ein.
2. **Sicherheitsüberprüfung**: Bevor die Komponente gerendert wird, bewertet das System Sicherheitsannotationen und -regeln.
3. **Entscheidung**: Basierend auf dem Authentifizierungsstatus und den Rollen des Benutzers:
   - **Gewähren**: Navigation zulassen und die Komponente rendern.
   - **Verweigern**: Navigation blockieren und auf die Anmeldeseite oder eine Seite mit Zugriff verweigert umleiten.
4. **Rendern oder umleiten**: Entweder wird die angeforderte Komponente angezeigt oder der Benutzer wird entsprechend umgeleitet.

Mit automatischer Durchsetzung werden Sicherheitsregeln konsistent über Ihre gesamte Anwendung angewendet, sodass die Zugangskontrolle behandelt wird, bevor irgendeine Komponente gerendert wird und Entwickler keine manuellen Überprüfungen in jeder Ansicht hinzufügen müssen.

## Authentifizierung VS Autorisierung {#authentication-vs-authorization}

Um die Sicherheit in Ihrer Anwendung korrekt umzusetzen, ist es wichtig, den Unterschied zwischen diesen beiden Konzepten zu kennen:

- **Authentifizierung**: Überprüfung, wer der Benutzer ist. Dies geschieht typischerweise während der Anmeldung, wenn der Benutzer Anmeldeinformationen (Benutzername und Passwort) bereitstellt. Nach der Authentifizierung wird die Identität des Benutzers in der Sitzung oder im Sicherheitskontext gespeichert.

- **Autorisierung**: Überprüfung, auf was der authentifizierte Benutzer zugreifen kann. Dies umfasst die Überprüfung, ob der Benutzer die erforderlichen Rollen oder Berechtigungen hat, um auf eine bestimmte Route zuzugreifen. Die Autorisierung erfolgt jedes Mal, wenn ein Benutzer zu einer geschützten Route navigiert.

Das Sicherheitssystem von webforJ behandelt beide Aspekte:

- Annotationen wie `@PermitAll` behandeln Authentifizierungsanforderungen.
- Annotationen wie `@RolesAllowed` behandeln Autorisierungsanforderungen.

## Erste Schritte {#getting-started}

Dieser Leitfaden geht davon aus, dass Sie **Spring Boot mit Spring Security** verwenden, was der empfohlene Ansatz für die meisten webforJ-Anwendungen ist. Spring Security bietet branchenübliche Authentifizierung und Autorisierung mit automatischer Konfiguration über Spring Boot.

Der Rest dieser Dokumentation führt Sie durch die Sicherung Ihrer Routen mit Spring Security, vom grundlegenden Setup bis hin zu erweiterten Funktionen. Wenn Sie kein Spring Boot verwenden oder eine benutzerdefinierte Sicherheitsimplementierung benötigen, siehe den [Leitfaden zur Sicherheitsarchitektur](/docs/security/architecture/overview), um zu erfahren, wie das System funktioniert und wie Sie eine benutzerdefinierte Sicherheit implementieren können.

## Themen {#topics}

<DocCardList className="topics-section" />
