---
title: Getting Started
sidebar_position: 2
_i18n_hash: becd2e7bd488a077c08ef5a64dbe0f61
---
Spring Security biedt authenticatie en autorisatie voor Spring Boot-toepassingen. Wanneer geïntegreerd met webforJ, beschermt het routes met behulp van annotaties terwijl Spring verantwoordelijk is voor gebruikersbeheer en sessies.

Deze gids behandelt het toevoegen van Spring Security aan je webforJ-app, het configureren van authenticatie, het creëren van inlogweergaven en het beschermen van routes met rolgebaseerde toegangscontrole.

:::tip[Leer meer over Spring Security]
Voor een uitgebreide uitleg van de functies en concepten van Spring Security, zie de [Spring Security-documentatie](https://docs.spring.io/spring-security/reference/).
:::

## Voeg Spring Security-afhankelijkheid toe {#add-spring-security-dependency}

Voeg de Spring Security-starter toe aan je `pom.xml`:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Deze enkele afhankelijkheid brengt het authenticatiekader van Spring Security, wachtwoordversleutelaars en sessiebeheer binnen. De versie wordt automatisch beheerd door je Spring Boot-ouder POM.

## Configureer Spring Security {#configure-spring-security}

Maak een beveiligingsconfiguratieklasse die Spring Security verbindt met webforJ. Deze klasse definieert hoe gebruikers worden geauthenticeerd en welke pagina's omgaan met inloggen en toegang weigeren:

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

Deze configuratie creëert vier Spring-beans die samenwerken:

**`SecurityFilterChain`** verbindt Spring Security met het routeringssysteem van webforJ. De methode `WebforjSecurityConfigurer.webforj()` integreert de authenticatie van Spring Security met de routering van webforJ. Je specificeert welke componentklassen omgaan met inloggen en toegang weigeren, en Spring zal authenticatie afdwingen voordat het beschermde routes weergeeft.

- De `loginPage()`-methode vertelt Spring Security waar gebruikers zich moeten authenticeren. Geef je inlogweergavecomponentklasse door, en webforJ lost automatisch het routepad op uit de `@Route`-annotatie. Wanneer ongeauthenticeerde gebruikers proberen toegang te krijgen tot beschermde routes, worden ze hierheen omgeleid.

- De `accessDeniedPage()`-methode definieert waar geauthenticeerde gebruikers naartoe gaan als ze geen toestemming hebben voor een route. Als een gebruiker bijvoorbeeld probeert toegang te krijgen tot een admin-only route, wordt hij naar deze pagina omgeleid.

- De `logout()`-methode schakelt het afmeldpunt in op `/logout`. Na het uitloggen worden gebruikers teruggeleid naar de inlogpagina met een `?logout`-parameter.

**`PasswordEncoder`** gebruikt BCrypt om wachtwoorden veilig te hash-en. Spring Security past deze encoder automatisch toe tijdens inloggen om het ingediende wachtwoord te vergelijken met de opgeslagen hash.

**`UserDetailsService`** vertelt Spring Security waar de gebruikersinformatie te vinden is tijdens de authenticatie. Dit voorbeeld maakt gebruik van een in-memory opslag met twee gebruikers: `user/password` en `admin/admin`.

**`AuthenticationManager`** coördineert het authenticatieproces. Het gebruikt een provider die gebruikers laadt van de `UserDetailsService` en wachtwoorden verifieert met de `PasswordEncoder`.

## Maak inlogweergave {#create-login-view}

Maak een weergave die een inlogdialoog presenteert en inloggegevens naar Spring Security indient. De volgende weergave maakt gebruik van de [`Login`](/docs/components/login) component:

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
      Toast.show("Ongeldige gebruikersnaam of wachtwoord. Probeer het opnieuw.", Theme.DANGER);
    }

    if (queryParams.containsKey("logout")) {
      Toast.show("Je bent succesvol uitgelogd.", Theme.GRAY);
    }
  }
}
```

De `@AnonymousAccess`-annotatie markeert deze route als openbaar zodat ongeauthenticeerde gebruikers toegang hebben tot de inlogpagina. Zonder deze annotatie zouden gebruikers van de inlogpagina worden omgeleid, wat een oneindige lus zou creëren.

De regel `setAction("/signin")` is cruciaal; het configureert de Login-component om inloggegevens naar het authenticatie-eindpunt van Spring te POSTen. Spring onderschept deze indiening, verifieert de inloggegevens en verleent of weigert toegang, of leidt terug met een foutparameter.

De observer `onDidEnter` controleert op queryparameters die Spring toevoegt om resultaten te communiceren. Wanneer authenticatie mislukt, leidt Spring om naar `/signin?error`. Na het uitloggen leidt het om naar `/signin?logout`. De observer toont geschikte berichten op basis van deze parameters.

:::tip Eindpuntmatching
Het pad in `setAction("/signin")` moet overeenkomen met je `@Route("/signin")`-pad. Spring onderschept formulierindieningen naar dit exacte pad voor authenticatie. Als je verschillende paden nodig hebt voor de inlogpagina en de authenticatieverwerking, configureer ze dan afzonderlijk in `SecurityConfig`:

```java
.loginPage("/signin", "/authenticate")
```

Dit toont de inlogpagina op `/signin`, maar verwerkt authenticatie op `/authenticate`.
:::

## Maak toegang geweigerd weergave {#create-access-denied-view}

Maak een weergave die wordt weergegeven wanneer gebruikers geen toestemming hebben om toegang te krijgen tot een route:

```java title="AccessDenyView.java"
@Route(value = "/access-denied", outlet = MainLayout.class)
public class AccessDenyView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public AccessDenyView() {
    Paragraph message = new Paragraph("Oeps! Dit gebied is alleen voor VIP's.");
    Paragraph subMessage = new Paragraph(
        "Het lijkt erop dat je geprobeerd hebt binnen te sneaken in de executive lounge! Pak betere inloggegevens of ga terug naar de openbare gebieden waar de koffie toch gratis is.");

    self.add(message, subMessage);
  }
}
```

Deze weergave wordt weergegeven wanneer geauthenticeerde gebruikers proberen toegang te krijgen tot routes waarvoor ze geen toestemming hebben, zoals een gebruiker die probeert toegang te krijgen tot een admin-only route.

## Bescherm je routes {#protect-your-routes}

Met de geconfigureerde authenticatie kun je nu je routes beschermen met beveiligingsannotaties. Deze annotaties vertellen Spring Security wie toegang heeft tot elke weergave, en het beveiligingssysteem handhaaft automatisch deze regels voordat het een component weergeeft.

Wanneer een gebruiker naar een route navigeert, onderschept Spring Security de navigatie en controleert het de beveiligingsannotaties van de route. Als de gebruiker is geauthenticeerd (ingelogd met geldige inloggegevens) en de vereiste machtigingen heeft, wordt de weergave normaal weergegeven. Zo niet, worden ze omgeleid naar de inlogpagina of toegang geweigerd pagina.

```java title="InboxView.java"
// Inbox - toegankelijk voor alle geauthenticeerde gebruikers
@Route(value = "/", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public InboxView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("Inbox"));
  }
}
```

```java title="TeamsView.java" {3}
// Teams - vereist ADMIN rol
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

De `InboxView` heeft geen annotatie, wat betekent dat elke geauthenticeerde gebruiker er toegang toe heeft. Wanneer een gebruiker zich met de inloggegevens die je in `UserDetailsService` hebt gedefinieerd (`user/password` of `admin/admin`) succesvol aanmeldt, kunnen ze deze route bekijken. Ongauthenticeerde gebruikers die proberen deze route te bezoeken, worden omgeleid naar de inlogpagina.

De `TeamsView` gebruikt `@RolesAllowed("ADMIN")` om de toegang te beperken tot gebruikers met de adminrol. Hoewel zowel de "user" als de "admin" accounts geauthenticeerde gebruikers zijn, kunnen alleen gebruikers met de adminrol deze route benaderen, omdat deze zowel `USER` als `ADMIN` rollen heeft. Het gebruikersaccount heeft alleen de `USER` rol, dus het proberen toegang te krijgen tot deze route leidt hen naar de toegang geweigerd pagina.

:::tip Beveiligingsannotaties
Zie de [Security Annotations-gids](/docs/security/annotations) voor alle beschikbare annotaties.
:::

## Voeg afmeldfunctie toe {#add-logout-capability}

Gebruik `SpringSecurityFormSubmitter` om een logout-knop te maken:

```java
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.IconButton;
import com.webforj.spring.security.SpringSecurityFormSubmitter;

IconButton logout = new IconButton(FeatherIcon.LOG_OUT.create());
logout.onClick(e -> SpringSecurityFormSubmitter.logout("/logout").submit());
```

Bij klik wordt een formulier ingediend naar het `/logout` eindpunt van Spring Security, dat de gebruikerssessie opheft en omleidt naar de inlogpagina met een logout-succesbericht.

## Hoe het samenwerkt {#how-it-works-together}

Wanneer Spring Boot je app start:

1. **Auto-configuratie detecteert** zowel `webforj-spring-boot-starter` als `spring-boot-starter-security` afhankelijkheden
2. **Beveiligingsbeheerder wordt automatisch aangemaakt** om de verbinding tussen webforJ-routing en Spring Security-authenticatie te leggen
3. **Beveiligingsevaluatoren worden geregistreerd** om de annotaties `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` en `@DenyAll` af te handelen
4. **Route-observer wordt bevestigd** om navigatie te onderscheppen en beveiligingsregels te evalueren voordat componenten worden weergegeven

Je hoeft deze componenten niet handmatig te verbinden—de auto-configuratie van Spring Boot regelt de integratie. Je hoeft alleen je `SecurityConfig` te definiëren met gebruikersbeheer en pagina-locaties.

Wanneer een gebruiker navigeert:

1. De beveiligingsobserver onderschept de navigatie
2. Evaluatoren verifiëren de beveiligingsannotaties van de route
3. Spring Security's `SecurityContextHolder` biedt authenticatie-informatie
4. Als geautoriseerd, wordt de component weergegeven; anders wordt de gebruiker omgeleid
