---
title: Getting Started
sidebar_position: 2
_i18n_hash: e8996d53f35e093d9ba65c54774d1935
---
Spring Security bietet Authentifizierung und Autorisierung für Spring Boot-Anwendungen. Bei der Integration mit webforJ schützt es Routen mithilfe von Annotationen, während Spring die Benutzerverwaltung und Sitzungen übernimmt.

Dieser Leitfaden behandelt das Hinzufügen von Spring Security zu Ihrer webforJ-App, die Konfiguration der Authentifizierung, das Erstellen von Anmeldeseiten und den Schutz von Routen mit rollenbasierter Zugangskontrolle.

:::tip[Erfahren Sie mehr über Spring Security]
Für ein umfassendes Verständnis der Funktionen und Konzepte von Spring Security siehe [Spring Security-Dokumentation](https://docs.spring.io/spring-security/reference/).
:::

## Fügen Sie die Spring Security-Abhängigkeit hinzu {#add-spring-security-dependency}

Fügen Sie den Spring Security-Starter zu Ihrem `pom.xml` hinzu:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Diese einzige Abhängigkeit bringt das Authentifizierungsframework von Spring Security, Passwort-Encoder und Sitzungsmanagement mit sich. Die Version wird automatisch von Ihrem Spring Boot-Eltern-POM verwaltet.

## Konfigurieren Sie Spring Security {#configure-spring-security}

Erstellen Sie eine Sicherheitskonfigurationsklasse, die Spring Security mit webforJ verbindet. Diese Klasse definiert, wie Benutzer authentifiziert werden und welche Seiten Anmeldungen und Zugriffsverweigerungen behandeln:

```java title="SecurityConfig.java"
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .with(WebforjSecurityConfigurer.webforj(), configurer -> configurer
            .loginPage(LoginView.class)
            .accessDeniedPage(AccessDenyView.class)
            .logout())
        .build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    UserDetails user = User.builder()
        .username("user")
        .password(passwordEncoder.encode("password"))
        .roles("USER")
        .build();

    UserDetails admin = User.builder()
        .username("admin")
        .password(passwordEncoder.encode("admin"))
        .roles("USER", "ADMIN")
        .build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  AuthenticationManager authenticationManager(
      UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);

    return new ProviderManager(authenticationProvider);
  }
}
```

Diese Konfiguration erstellt vier Spring-Beans, die zusammenarbeiten:

**`SecurityFilterChain`** verbindet Spring Security mit dem Routingsystem von webforJ. Die Methode `WebforjSecurityConfigurer.webforj()` integriert die Spring Security-Authentifizierung mit dem Routing von webforJ. Sie geben an, welche Komponentenklassen Anmeldungen und Zugriffsverweigerungen behandeln, und Spring wird die Authentifizierung durchsetzen, bevor geschützte Routen gerendert werden.

- Die Methode `loginPage()` gibt an, wo sich Benutzer authentifizieren sollten. Übergeben Sie Ihre Komponentenkassenansicht zur Anmeldung, und webforJ löst automatisch den Routenpfad aus der `@Route`-Annotation auf. Wenn nicht authentifizierte Benutzer versuchen, auf geschützte Routen zuzugreifen, werden sie hierher umgeleitet.

- Die Methode `accessDeniedPage()` definiert, wohin authentifizierte Benutzer gehen, wenn ihnen die Berechtigung für eine Route fehlt. Beispielsweise wird ein Benutzer, der versucht, auf eine nur für Administratoren bestimmte Route zuzugreifen, auf diese Seite umgeleitet.

- Die Methode `logout()` aktiviert den Logout-Endpunkt unter `/logout`. Nach dem Abmelden werden Benutzer zurück zur Anmeldeseite umgeleitet, mit einem `?logout`-Parameter.

**`PasswordEncoder`** verwendet BCrypt, um Passwörter sicher zu hashen. Spring Security wendet diesen Encoder automatisch während der Anmeldung an, um das übermittelte Passwort mit dem gespeicherten Hash zu vergleichen.

**`UserDetailsService`** sagt Spring Security, wo Benutzerdaten während der Authentifizierung zu finden sind. Dieses Beispiel verwendet einen In-Memory-Speicher mit zwei Benutzern: `user/password` und `admin/admin`.

**`AuthenticationManager`** koordiniert den Authentifizierungsprozess. Es verwendet einen Anbieter, der Benutzer aus dem `UserDetailsService` lädt und Passwörter mit dem `PasswordEncoder` überprüft.

## Erstellen Sie die Anmeldeseite {#create-login-view}

Erstellen Sie eine Ansicht, die einen Anmelde-Dialog zeigt und Anmeldeinformationen an Spring Security übermittelt. Die folgende Ansicht verwendet die [`Login`](/docs/components/login)-Komponente:

```java title="LoginView.java"
@Route("/signin")
@AnonymousAccess
public class LoginView extends Composite<Login> implements DidEnterObserver {
  private final Login self = getBoundComponent();

  public LoginView() {
    self.setAction("/signin");
    whenAttached().thenAccept(c -> self.open());
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag params) {
    ParametersBag queryParams = event.getLocation().getQueryParameters();

    if (queryParams.containsKey("error")) {
      Toast.show("Ungültiger Benutzername oder Passwort. Bitte versuchen Sie es erneut.", Theme.DANGER);
    }

    if (queryParams.containsKey("logout")) {
      Toast.show("Sie wurden erfolgreich abgemeldet.", Theme.GRAY);
    }
  }
}
```

Die Annotation `@AnonymousAccess` kennzeichnet diese Route als öffentlich, sodass nicht authentifizierte Benutzer auf die Anmeldeseite zugreifen können. Ohne diese Annotation würden Benutzer von der Anmeldeseite umgeleitet, was eine Endlosschleife erzeugen würde.

Die Zeile `setAction("/signin")` ist entscheidend, sie konfiguriert die Login-Komponente, um die Anmeldeinformationen an den Authentifizierungspunkt von Spring zu POSTen. Spring fängt diese Übermittlung ab, überprüft die Anmeldeinformationen und gewährt entweder den Zugriff oder leitet zurück mit einem Fehlerparameter.

Der Observer `onDidEnter` überprüft die Abfrageparameter, die Spring hinzufügt, um Ergebnisse zu kommunizieren. Wenn die Authentifizierung fehlschlägt, leitet Spring zu `/signin?error` um. Nach dem Abmelden wird zu `/signin?logout` umgeleitet. Der Observer zeigt anhand dieser Parameter geeignete Nachrichten an.

:::tip Endpunktübereinstimmung
Der Pfad in `setAction("/signin")` muss mit Ihrem `@Route("/signin")`-Pfad übereinstimmen. Spring fängt Formularübermittlungen zu diesem genauen Pfad zur Authentifizierung ab. Wenn Sie unterschiedliche Pfade für die Anmeldeseite und die Authentifizierungsverarbeitung benötigen, konfigurieren Sie diese separat in `SecurityConfig`:

```java
.loginPage("/signin", "/authenticate")
```

Dies zeigt die Anmeldeseite unter `/signin`, jedoch wird die Authentifizierung unter `/authenticate` verarbeitet.
:::

## Erstellen Sie die Zugriffsverweigerungsansicht {#create-access-denied-view}

Erstellen Sie eine Ansicht, die angezeigt wird, wenn Benutzer nicht die Berechtigung haben, auf eine Route zuzugreifen:

```java title="AccessDenyView.java"
@Route(value = "/access-denied", outlet = MainLayout.class)
public class AccessDenyView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public AccessDenyView() {
    Paragraph message = new Paragraph("Hoppla! Dieser Bereich ist nur für VIPs.");
    Paragraph subMessage = new Paragraph(
        "Es sieht so aus, als hätten Sie versucht, in die Lounge der Geschäftsführung zu schlüpfen! Entweder bessere Anmeldeinformationen besorgen oder zu den öffentlichen Bereichen zurückkehren, wo der Kaffee ohnehin kostenlos ist.");

    self.add(message, subMessage);
  }
}
```

Diese Ansicht wird gerendert, wenn authentifizierte Benutzer versuchen, auf Routen zuzugreifen, für die sie keine Berechtigung haben, z. B. ein Benutzer, der versucht, auf eine nur für Administratoren zugängliche Route zuzugreifen.

## Schützen Sie Ihre Routen {#protect-your-routes}

Mit konfiguriertem Authentifizierungssystem können Sie jetzt Ihre Routen mithilfe von Sicherheitsanmerkungen schützen. Diese Annotations sagen Spring Security, wer auf jede Ansicht zugreifen kann, und das Sicherheitssystem setzt diese Regeln automatisch durch, bevor eine Komponente gerendert wird.

Wenn ein Benutzer zu einer Route navigiert, fängt Spring Security die Navigation ab und überprüft die Sicherheitsanmerkungen der Route. Wenn der Benutzer authentifiziert ist (mit gültigen Anmeldeinformationen angemeldet) und die erforderlichen Berechtigungen hat, wird die Ansicht normal gerendert. Andernfalls werden sie entweder zur Anmeldeseite oder zur Zugriffsverweigerungsseite umgeleitet.

```java title="InboxView.java"
// Inbox - für alle authentifizierten Benutzer zugänglich
@Route(value = "/", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public InboxView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("Posteingang"));
  }
}
```

```java title="TeamsView.java" {3}
// Teams - benötigt ADMIN-Rolle
@Route(value = "/teams", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TeamsView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public TeamsView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("Teams"));
  }
}
```

Die `InboxView` hat keine Annotation, was bedeutet, dass jeder authentifizierte Benutzer darauf zugreifen kann. Wenn ein Benutzer sich erfolgreich mit den in `UserDetailsService` definierten Anmeldeinformationen anmeldet (`user/password` oder `admin/admin`), kann er diese Route anzeigen. Nicht authentifizierte Benutzer, die versuchen, auf diese Route zuzugreifen, werden zur Anmeldeseite umgeleitet.

Die `TeamsView` verwendet `@RolesAllowed("ADMIN")`, um den Zugriff auf Benutzer mit der Administratorrolle zu beschränken. Obwohl sowohl die "Benutzer"- als auch die "Administrator"-Konten authentifizierte Benutzer sind, können nur Benutzer mit der Administratorrolle auf diese Route zugreifen, da sie sowohl `USER`- als auch `ADMIN`-Rollen haben. Das Benutzerkonto hat nur die `USER`-Rolle, sodass der Versuch, auf diese Route zuzugreifen, sie zur Zugriffsverweigerungsseite umleitet.

:::tip Sicherheitsanmerkungen
Siehe die [Sicherheitsanmerkungsanleitung](/docs/security/annotations) für alle verfügbaren Annotationen.
:::

## Fügen Sie die Abmeldefunktion hinzu {#add-logout-capability}

Verwenden Sie `SpringSecurityFormSubmitter`, um eine Abmelde-Schaltfläche zu erstellen:

```java
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.IconButton;
import com.webforj.spring.security.SpringSecurityFormSubmitter;

IconButton logout = new IconButton(FeatherIcon.LOG_OUT.create());
logout.onClick(e -> SpringSecurityFormSubmitter.logout("/logout").submit());
```

Bei einem Klick sendet dies ein Formular an den `/logout`-Endpunkt von Spring Security, der die Sitzung des Benutzers löscht und zur Anmeldeseite mit einer Abmeldebestätigungsnachricht umleitet.

## Wie es zusammen funktioniert {#how-it-works-together}

Wenn Spring Boot Ihre App startet:

1. **Auto-Konfiguration erkennt** sowohl die Abhängigkeiten `webforj-spring-boot-starter` als auch `spring-boot-starter-security`.
2. **Sicherheitsmanager wird automatisch erstellt**, um das Routing von webforJ und die Authentifizierung von Spring Security zu verbinden.
3. **Sicherheitsbewertungsfunktionen werden registriert**, um die Annotationen `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` und `@DenyAll` zu behandeln.
4. **Routing-Observer wird angeschlossen**, um Navigation abzufangen und Sicherheitsregeln zu evaluieren, bevor Komponenten gerendert werden.

Sie verkabeln diese Komponenten nicht manuell - die Auto-Konfiguration von Spring Boot übernimmt die Integration. Sie definieren nur Ihre `SecurityConfig` mit Benutzerverwaltung und Seitenstandorten.

Wenn ein Benutzer navigiert:

1. Der Sicherheitsbeobachter fängt die Navigation ab.
2. Bewertungsfunktionen überprüfen die Sicherheitsanmerkungen der Route.
3. Spring Security's `SecurityContextHolder` stellt Authentifizierungsinformationen bereit.
4. Wenn autorisiert, wird die Komponente gerendert; andernfalls wird der Benutzer umgeleitet.
