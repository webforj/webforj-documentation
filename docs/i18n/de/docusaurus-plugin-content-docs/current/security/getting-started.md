---
title: Getting Started
sidebar_position: 2
_i18n_hash: becd2e7bd488a077c08ef5a64dbe0f61
---
Spring Security bietet Authentifizierung und Autorisierung für Spring Boot-Anwendungen. Wenn es mit webforJ integriert wird, schützt es Routen mithilfe von Anmerkungen, während Spring das Benutzermanagement und die Sitzungen übernimmt.

Dieses Handbuch behandelt das Hinzufügen von Spring Security zu Ihrer webforJ-App, das Konfigurieren der Authentifizierung, das Erstellen von Anmeldeansichten und das Schützen von Routen mit rollenbasiertem Zugriff.

:::tip[Erfahren Sie mehr über Spring Security]
Für ein umfassendes Verständnis der Funktionen und Konzepte von Spring Security siehe die [Spring Security-Dokumentation](https://docs.spring.io/spring-security/reference/).
:::

## Fügen Sie die Spring Security-Abhängigkeit hinzu {#add-spring-security-dependency}

Fügen Sie den Spring Security-Starter zu Ihrer `pom.xml` hinzu:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Diese einzelne Abhängigkeit bringt das Authentifizierungsframework von Spring Security, Passwortencoder und Sitzungsmanagement mit. Die Version wird automatisch von Ihrer Spring Boot-Eltern-POM verwaltet.

## Konfigurieren Sie Spring Security {#configure-spring-security}

Erstellen Sie eine Sicherheitskonfigurationsklasse, die Spring Security mit webforJ verbindet. Diese Klasse definiert, wie Benutzer authentifiziert werden und welche Seiten für Anmeldung und Zugriffsverweigerung zuständig sind:

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

**`SecurityFilterChain`** verbindet Spring Security mit dem Routing-System von webforJ. Die Methode `WebforjSecurityConfigurer.webforj()` integriert die Authentifizierung von Spring Security mit dem Routing von webforJ. Sie geben an, welche Komponentenklassen für die Anmeldung und Zugriffsverweigerung zuständig sind, und Spring wird die Authentifizierung durchsetzen, bevor geschützte Routen gerendert werden.

- Die Methode `loginPage()` gibt an, wo sich Benutzer authentifizieren sollen. Übergeben Sie Ihre Login-Ansichtskomponentenklasse, und webforJ löst den Routenpfad automatisch aus der `@Route`-Anmerkung auf. Wenn nicht authentifizierte Benutzer versuchen, auf geschützte Routen zuzugreifen, werden sie hierher umgeleitet.

- Die Methode `accessDeniedPage()` definiert, wohin authentifizierte Benutzer gehen, wenn ihnen die Berechtigung für eine Route fehlt. Beispielsweise wird ein Benutzer, der versucht, auf eine nur für Administratoren vorgesehene Route zuzugreifen, auf diese Seite umgeleitet.

- Die Methode `logout()` aktiviert den Ausstiegsendpunkt unter `/logout`. Nach dem Abmelden werden die Benutzer zurück zur Anmeldeseite mit einem `?logout`-Parameter umgeleitet.

**`PasswordEncoder`** verwendet BCrypt, um Passwörter sicher zu hashen. Spring Security wendet diesen Encoder automatisch während der Anmeldung an, um das übermittelte Passwort mit dem gespeicherten Hash zu vergleichen.

**`UserDetailsService`** sagt Spring Security, wo es Benutzerinformationen während der Authentifizierung finden kann. Dieses Beispiel verwendet einen In-Memory-Speicher mit zwei Benutzern: `user/password` und `admin/admin`.

**`AuthenticationManager`** koordiniert den Authentifizierungsprozess. Es verwendet einen Anbieter, der Benutzer aus dem `UserDetailsService` lädt und Passwörter mit dem `PasswordEncoder` verifiziert.

## Erstellen Sie die Anmeldeansicht {#create-login-view}

Erstellen Sie eine Ansicht, die einen Anmelde-Dialog anzeigt und Anmeldeinformationen an Spring Security übermittelt. Die folgende Ansicht verwendet die [`Login`](/docs/components/login)-Komponente:

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

Die Annotation `@AnonymousAccess` markiert diese Route als öffentlich, sodass nicht authentifizierte Benutzer auf die Anmeldeseite zugreifen können. Ohne diese Annotation würden Benutzer von der Anmeldeseite umgeleitet werden, was eine endlose Schleife erzeugen würde.

Die Zeile `setAction("/signin")` ist entscheidend, da sie die Login-Komponente konfiguriert, um Anmeldeinformationen an den Authentifizierungspunkt von Spring zu POSTen. Spring unterbricht diese Übermittlung, überprüft die Anmeldeinformationen und gewährt entweder Zugriff oder leitet zurück mit einem Fehlerparameter.

Der Beobachter `onDidEnter` überprüft auf Abfrageparameter, die Spring hinzufügt, um Ergebnisse zu kommunizieren. Wenn die Authentifizierung fehlschlägt, leitet Spring zu `/signin?error` um. Nach dem Abmelden leitet es zu `/signin?logout` um. Der Beobachter zeigt geeignete Nachrichten basierend auf diesen Parametern an.

:::tip Endpunkterfassung
Der Pfad in `setAction("/signin")` muss mit Ihrem `@Route("/signin")`-Pfad übereinstimmen. Spring unterbricht Formularübermittlungen an diesen genauen Pfad zur Authentifizierung. Wenn Sie verschiedene Pfade für die Anmeldeseite und die Authentifizierungsverarbeitung benötigen, konfigurieren Sie sie separat in `SecurityConfig`:

```java
.loginPage("/signin", "/authenticate")
```

Dies zeigt die Anmeldeseite unter `/signin` an, verarbeitet die Authentifizierung jedoch unter `/authenticate`.
:::

## Erstellen Sie die Zugriffsverweigerungsansicht {#create-access-denied-view}

Erstellen Sie eine Ansicht, die angezeigt wird, wenn Benutzer keine Berechtigung haben, auf eine Route zuzugreifen:

```java title="AccessDenyView.java"
@Route(value = "/access-denied", outlet = MainLayout.class)
public class AccessDenyView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public AccessDenyView() {
    Paragraph message = new Paragraph("Hoppla! Dieser Bereich ist nur für VIPs.");
    Paragraph subMessage = new Paragraph(
        "Es sieht so aus, als ob Sie versucht haben, in die executive Lounge zu gelangen! Entweder holen Sie sich bessere Anmeldeinformationen oder kehren Sie in die öffentlichen Bereiche zurück, wo der Kaffee kostenlos ist.");

    self.add(message, subMessage);
  }
}
```

Diese Ansicht wird gerendert, wenn authentifizierte Benutzer versuchen, auf Routen zuzugreifen, für die sie keine Berechtigung haben, z. B. ein Benutzer, der versucht, auf eine nur für Administratoren vorgesehene Route zuzugreifen.

## Schützen Sie Ihre Routen {#protect-your-routes}

Mit konfigurierter Authentifizierung können Sie jetzt Ihre Routen mithilfe von Sicherheitsanmerkungen schützen. Diese Anmerkungen sagen Spring Security, wer auf jede Ansicht zugreifen darf, und das Sicherheitssystem durchsetzt diese Regeln automatisch, bevor eine Komponente gerendert wird.

Wenn ein Benutzer auf eine Route navigiert, unterbricht Spring Security die Navigation und überprüft die Sicherheitsanmerkungen der Route. Wenn der Benutzer authentifiziert ist (mit gültigen Anmeldeinformationen angemeldet) und die erforderlichen Berechtigungen hat, wird die Ansicht normal gerendert. Andernfalls werden sie entweder zur Anmeldeseite oder zur Zugriffsverweigerungsseite umgeleitet.

```java title="InboxView.java"
// Posteingang - für alle authentifizierten Benutzer zugänglich
@Route(value = "/", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public InboxView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("Posteingang"));
  }
}
```

```java title="TeamsView.java" {3}
// Teams - erfordert ADMIN-Rolle
@Route(value = "/teams", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TeamsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public TeamsView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("Teams"));
  }
}
```

Die `InboxView` hat keine Annotation, was bedeutet, dass jeder authentifizierte Benutzer darauf zugreifen kann. Wenn ein Benutzer mit den Anmeldeinformationen, die Sie im `UserDetailsService` definiert haben (`user/password` oder `admin/admin`), erfolgreich anmeldet, kann er diese Route ansehen. Nicht authentifizierte Benutzer, die versuchen, auf diese Route zuzugreifen, werden zur Anmeldeseite umgeleitet.

Die `TeamsView` verwendet `@RolesAllowed("ADMIN")`, um den Zugriff auf Benutzer mit der Administratorrolle einzuschränken. Obwohl sowohl das „Benutzer“- als auch das „Admin“-Konto authentifiziert sind, können nur Benutzer mit der Administratorrolle auf diese Route zugreifen, da sie sowohl die Rollen `USER` als auch `ADMIN` hat. Das Benutzerkonto hat nur die `USER`-Rolle, sodass der Versuch, auf diese Route zuzugreifen, sie zur Zugriffsverweigerungsseite umleitet.

:::tip Sicherheitsanmerkungen
Siehe den [Leitfaden zu Sicherheitsanmerkungen](/docs/security/annotations) für alle verfügbaren Anmerkungen.
:::

## Fügen Sie die Ausstiegsfunktionalität hinzu {#add-logout-capability}

Verwenden Sie `SpringSecurityFormSubmitter`, um einen Logout-Button zu erstellen:

```java
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.IconButton;
import com.webforj.spring.security.SpringSecurityFormSubmitter;

IconButton logout = new IconButton(FeatherIcon.LOG_OUT.create());
logout.onClick(e -> SpringSecurityFormSubmitter.logout("/logout").submit());
```

Beim Klicken wird ein Formular an den Ausstiegsendpunkt von Spring Security unter `/logout` übermittelt, das die Sitzung des Benutzers löscht und zur Anmeldeseite mit einer Ausstiegsbestätigungsnachricht umleitet.

## Wie es zusammenarbeitet {#how-it-works-together}

Wenn Spring Boot Ihre Anwendung startet:

1. **Die automatische Konfiguration erkennt** sowohl die Abhängigkeiten `webforj-spring-boot-starter` als auch `spring-boot-starter-security`.
2. **Ein Sicherheitsmanager wird automatisch erstellt**, um das Routing von webforJ mit der Authentifizierung von Spring Security zu verbinden.
3. **Sicherheitsauswerter werden registriert**, um die Anmerkungen `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` und `@DenyAll` zu behandeln.
4. **Ein Routenbeobachter wird angehängt**, um die Navigation abzufangen und Sicherheitsregeln vor dem Rendern von Komponenten zu bewerten.

Sie verkabeln diese Komponenten nicht manuell - die automatische Konfiguration von Spring Boot übernimmt die Integration. Sie definieren lediglich Ihre `SecurityConfig` mit dem Benutzermanagement und den Seitenstandorten.

Wenn ein Benutzer navigiert:

1. Der Sicherheitsbeobachter unterbricht die Navigation.
2. Die Auswerter überprüfen die Sicherheitsanmerkungen der Route.
3. `SecurityContextHolder` von Spring Security stellt Authentifizierungsinformationen bereit.
4. Wenn autorisiert, wird die Komponente gerendert; andernfalls wird der Benutzer umgeleitet.
