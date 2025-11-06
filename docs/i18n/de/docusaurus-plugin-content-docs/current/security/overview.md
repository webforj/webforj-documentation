---
sidebar_position: 1
title: Security
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: b6707cb6491075a82ac19fb808840245
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
Dieses Feature befindet sich in der öffentlichen Vorschau und ist bereit für den produktiven Einsatz. Während der Vorschau-Phase können APIs basierend auf dem Feedback der Entwickler-Community verfeinert werden. Alle Änderungen werden im Voraus durch Versionshinweise angekündigt, und wenn nötig, werden Migrationsleitfäden bereitgestellt.
:::

In modernen Webanwendungen bezieht sich **Sicherheit** darauf, den Zugriff auf verschiedene Teile Ihrer App basierend auf der Benutzeridentität und -berechtigungen zu kontrollieren. In webforJ bietet die Sicherheit ein Framework für die **Zugriffskontrolle auf Routenebene**, bei dem Sie Ansichten schützen, eine Authentifizierung erforderlich machen und rollenbasierte Berechtigungen durchsetzen können.

## Traditionelles vs. gesichertes Routing {#traditional-vs-secured-routing}

Beim traditionellen ungesicherten Routing sind alle Routen in Ihrer App für jeden zugänglich, der die URL kennt. Das bedeutet, dass Benutzer zu sensiblen Seiten wie Admin-Panels oder Benutzer-Dashboards navigieren können, ohne dass Authentifizierungs- oder Autorisierungsüberprüfungen erforderlich sind. Die Verantwortung liegt bei den Entwicklern, Berechtigungen in jeder Komponente manuell zu überprüfen, was zu inkonsistenten Sicherheitsdurchsetzungen und potenziellen Schwachstellen führt.

Dieser Ansatz bringt mehrere Probleme mit sich:

1. **Manuelle Überprüfungen**: Entwickler müssen daran denken, Sicherheitslogik in jede geschützte Ansicht oder Layout hinzuzufügen.
2. **Inkonsistente Durchsetzung**: Sicherheitsüberprüfungen, die über den Code verteilt sind, führen zu Lücken und Fehlern.
3. **Wartungsaufwand**: Änderungen der Zugriffsregeln erfordern die Aktualisierung mehrerer Dateien.
4. **Keine zentrale Kontrolle**: Es gibt keinen zentralen Ort, um die Sicherheit der App zu verstehen oder zu verwalten.

**Gesichertes Routing** in webforJ löst dies, indem es die Zugriffskontrolle direkt auf Routenebene ermöglicht. Das Sicherheitssystem erzwingt automatisch Regeln, bevor eine Komponente gerendert wird, und bietet damit einen zentralisierten, deklarativen Ansatz zur App-Sicherheit. So funktioniert es:

1. **Deklarative Annotations**: Markieren Sie Routen mit Sicherheitsannotationen, um Zugriffsanforderungen zu definieren.
2. **Automatische Durchsetzung**: Das Sicherheitssystem überprüft die Berechtigungen, bevor eine Ansicht gerendert wird.
3. **Zentralisierte Konfiguration**: Definieren Sie das Sicherheitsverhalten an einem Ort und wenden Sie es konsistent an.
4. **Flexible Implementierungen**: Wählen Sie zwischen der Integration von Spring Security oder einer benutzerdefinierten Implementierung in reinem Java.

Dieses Design ermöglicht **Authentifizierung** (Überprüfung der Identität des Benutzers) und **Autorisierung** (Überprüfung, auf was der Benutzer zugreifen kann), sodass nur autorisierte Benutzer Zugriff auf geschützte Routen erhalten. Unautorisierte Benutzer werden automatisch basierend auf den konfigurierten Sicherheitsregeln umgeleitet oder der Zugriff wird verweigert.

## Beispiel für gesichertes Routing in webforJ {#example-of-secured-routing-in-webforj}

Hier ist ein Beispiel, das verschiedene Sicherheitsstufen in einer webforJ-App zeigt:

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
// Rechnungen - erfordert die Rolle ACCOUNTANT
@Route(value = "/invoices", outlet = MainLayout.class)
@RolesAllowed("ACCOUNTANT")
public class InvoicesView extends Composite<FlexLayout> {

  public InvoicesView() {
    // Rechnungsansicht
  }
}
```

In diesem Setup:

- Die `LoginView` ist mit `@AnonymousAccess` gekennzeichnet, was es nicht authentifizierten Benutzern ermöglicht, darauf zuzugreifen.
- Die `ProductsView` hat keine Sicherheitsannotation, was bedeutet, dass sie standardmäßig eine Authentifizierung erfordert (wenn der `secure-by-default` Modus aktiviert ist).
- Die `InvoicesView` erfordert die Rolle `ACCOUNTANT`, sodass nur Benutzer mit Buchhaltungsberechtigungen auf Rechnungen zugreifen können.

## Wie Sicherheit funktioniert {#how-security-works}

Wenn ein Benutzer versucht, zu einer Route zu navigieren, folgt das Sicherheitssystem diesem Ablauf:

1. **Navigation initiiert**: Der Benutzer klickt auf einen Link oder gibt eine URL ein.
2. **Sicherheitsüberprüfung**: Bevor die Komponente gerendert wird, bewertet das System Sicherheitsannotationen und -regeln.
3. **Entscheidung**: Basierend auf dem Authentifizierungsstatus und den Rollen des Benutzers:
   - **Gewähren**: Erlauben Sie die Navigation und rendern Sie die Komponente.
   - **Verweigern**: Blockieren Sie die Navigation und leiten Sie zur Anmeldeseite oder zur Seite „Zugriff verweigert“ um.
4. **Rendern oder umleiten**: Entweder wird die angeforderte Komponente angezeigt oder der Benutzer wird entsprechend umgeleitet.

Durch die automatische Durchsetzung werden Sicherheitsregeln konsistent in der gesamten App angewendet, sodass die Zugriffskontrolle behandelt wird, bevor eine Komponente gerendert wird und Entwickler keine manuellen Überprüfungen in jeder Ansicht hinzufügen müssen.

## Authentifizierung vs. Autorisierung {#authentication-vs-authorization}

Um die Sicherheit in Ihrer App korrekt umzusetzen, ist es wichtig, den Unterschied zwischen diesen beiden Konzepten zu kennen:

- **Authentifizierung**: Überprüfung, wer der Benutzer ist. Dies geschieht typischerweise während der Anmeldung, wenn der Benutzer Anmeldeinformationen (Benutzername und Passwort) bereitstellt. Nach der Authentifizierung wird die Identität des Benutzers in der Sitzung oder im Sicherheitskontext gespeichert.

- **Autorisierung**: Überprüfung, auf was der authentifizierte Benutzer zugreifen kann. Dies umfasst die Überprüfung, ob der Benutzer die erforderlichen Rollen oder Berechtigungen hat, um auf eine bestimmte Route zuzugreifen. Die Autorisierung erfolgt jedes Mal, wenn ein Benutzer zu einer geschützten Route navigiert.

Das Sicherheitssystem von webforJ behandelt beide Aspekte:

- Annotations wie `@PermitAll` behandeln Authentifizierungsanforderungen.
- Annotations wie `@RolesAllowed` behandeln Autorisierungsanforderungen.

## Erste Schritte {#getting-started}

Dieser Leitfaden geht davon aus, dass Sie **Spring Boot mit Spring Security** verwenden, was der empfohlene Ansatz für die meisten webforJ-Anwendungen ist. Spring Security bietet branchenübliche Authentifizierung und Autorisierung mit automatischer Konfiguration über Spring Boot.

Der Rest dieser Dokumentation führt Sie durch die Sicherung Ihrer Routen mit Spring Security, von der grundlegenden Einrichtung bis hin zu erweiterten Funktionen. Wenn Sie Spring Boot nicht verwenden oder eine benutzerdefinierte Sicherheitsimplementierung benötigen, lesen Sie den [Leitfaden zur Sicherheitsarchitektur](/docs/security/architecture/overview), um zu erfahren, wie das System funktioniert und wie Sie benutzerdefinierte Sicherheit implementieren können.

## Themen {#topics}

<DocCardList className="topics-section" />
