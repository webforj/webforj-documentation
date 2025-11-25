---
title: Aan de slag
sidebar_position: 2
_i18n_hash: e8996d53f35e093d9ba65c54774d1935
---
Spring Security biedt authenticatie en autorisatie voor Spring Boot-toepassingen. Wanneer het is geïntegreerd met webforJ, beschermt het routes met behulp van annotaties, terwijl Spring het gebruikersbeheer en de sessies afhandelt.

Deze gids behandelt het toevoegen van Spring Security aan je webforJ-app, het configureren van authenticatie, het creëren van inlogweergaven en het beschermen van routes met op rollen gebaseerde toegangscontrole.

:::tip[Leer meer over Spring Security]
Voor een uitgebreide uitleg van de functies en concepten van Spring Security, zie [Spring Security-documentatie](https://docs.spring.io/spring-security/reference/).
:::

## Voeg Spring Security-afhankelijkheid toe {#add-spring-security-dependency}

Voeg de Spring Security-starter toe aan je `pom.xml`:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Deze enkele afhankelijkheid brengt het authenticatiekader van Spring Security, wachtwoordversleutelaars en sessiebeheer met zich mee. De versie wordt automatisch beheerd door je Spring Boot-ouder-POM.

## Configureer Spring Security {#configure-spring-security}

Maak een beveiligingsconfiguratieklasse die Spring Security met webforJ verbindt. Deze klasse definieert hoe gebruikers worden geauthenticeerd en welke pagina's inlog- en toegangsweigeringen afhandelen:

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

**`SecurityFilterChain`** verbindt Spring Security met het routeringssysteem van webforJ. De methode `WebforjSecurityConfigurer.webforj()` integreert de authenticatie van Spring Security met de routering van webforJ. Je specificeert welke componentklassen de inloggen en toegangsweigeringen afhandelen en Spring zal authenticatie afdwingen voordat beveiligde routes worden gerenderd.

- De methode `loginPage()` vertelt Spring Security waar gebruikers zich moeten authenticeren. Geef je inlogweergavecomponentklasse door, en webforJ lost het routepad automatisch op vanuit de `@Route`-annotatie. Wanneer niet-geauthenticeerde gebruikers proberen toegang te krijgen tot beveiligde routes, worden ze hier naartoe geleid.

- De methode `accessDeniedPage()` definieert waar geauthenticeerde gebruikers heen gaan wanneer ze geen toestemming hebben voor een route. Bijvoorbeeld, een gebruiker die probeert toegang te krijgen tot een alleen-admin-route wordt omgeleid naar deze pagina.

- De methode `logout()` activeert het afmeld-eindpunt op `/logout`. Na het afmelden worden gebruikers teruggeleid naar de inlogpagina met een `?logout` parameter.

**`PasswordEncoder`** gebruikt BCrypt om wachtwoorden veilig te hashen. Spring Security past deze encoder automatisch toe tijdens de login om het ingediende wachtwoord te vergelijken met de opgeslagen hash.

**`UserDetailsService`** vertelt Spring Security waar gebruikersinformatie te vinden is tijdens authenticatie. Dit voorbeeld gebruikt een in-memory opslag met twee gebruikers: `user/password` en `admin/admin`.

**`AuthenticationManager`** coördineert het authenticatieproces. Het gebruikt een provider die gebruikers laadt uit de `UserDetailsService` en wachtwoorden verifieert met de `PasswordEncoder`.

## Maak inlogweergave {#create-login-view}

Maak een weergave die een inlogdialoog aanbiedt en inloggegevens naar Spring Security verzendt. De volgende weergave gebruikt de [`Login`](/docs/components/login) component:

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
      Toast.show("Je bent succesvol afgemeld.", Theme.GRAY);
    }
  }
}
```

De `@AnonymousAccess` annotatie markeert deze route als publiek zodat niet-geauthenticeerde gebruikers toegang hebben tot de inlogpagina. Zonder deze annotatie zouden gebruikers worden omgeleid van de inlogpagina, wat een oneindige lus zou creëren.

De regel `setAction("/signin")` is cruciaal, het configureert de Login-component om inloggegevens naar het authenticatie-eindpunt van Spring te POSTen. Spring onderschept deze indiening, verifieert de inloggegevens en verleent toegang of leidt terug om met een foutparameter.

De `onDidEnter` observer controleert op queryparameters die Spring toevoegt om resultaten te communiceren. Wanneer de authenticatie mislukt, wordt omgeleid naar `/signin?error`. Na het afmelden wordt omgeleid naar `/signin?logout`. De observer toont geschikte berichten op basis van deze parameters.

:::tip Eindpuntmatching
Het pad in `setAction("/signin")` moet overeenkomen met je `@Route("/signin")` pad. Spring onderschept formulierindieningen naar dit exacte pad voor authenticatie. Als je verschillende paden nodig hebt voor de inlogpagina en authenticatieverwerking, configureer ze dan afzonderlijk in `SecurityConfig`:

```java
.loginPage("/signin", "/authenticate")
```

Dit toont de inlogpagina op `/signin`, maar verwerkt de authenticatie op `/authenticate`.
:::

## Maak toegangsweigeringweergave {#create-access-denied-view}

Maak een weergave die wordt weergegeven wanneer gebruikers geen toestemming hebben om toegang te krijgen tot een route:

```java title="AccessDenyView.java"
@Route(value = "/access-denied", outlet = MainLayout.class)
public class AccessDenyView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public AccessDenyView() {
    Paragraph message = new Paragraph("Oeps! Dit gebied is alleen voor VIP's.");
    Paragraph subMessage = new Paragraph(
        "Het lijkt erop dat je hebt geprobeerd binnen te sneaken in de executive lounge! Of krijg betere inloggegevens of ga terug naar de publieke gebieden waar de koffie in ieder geval gratis is.");

    self.add(message, subMessage);
  }
}
```

Deze weergave wordt weergegeven wanneer geauthenticeerde gebruikers proberen toegang te krijgen tot de routes waarvoor ze geen toestemming hebben, zoals een gebruiker die probeert toegang te krijgen tot een alleen-admin-route.

## Bescherm je routes {#protect-your-routes}

Met de geconfigureerde authenticatie kun je nu je routes beschermen met beveiligingsannotaties. Deze annotaties vertellen Spring Security wie toegang heeft tot elke weergave en het beveiligingssysteem handhaaft deze regels automatisch voordat een component wordt gerenderd.

Wanneer een gebruiker naar een route navigeert, onderschept Spring Security de navigatie en controleert de beveiligingsannotaties van de route. Als de gebruiker geauthenticeerd is (ingelogd met geldige inloggegevens) en de vereiste machtigingen heeft, wordt de weergave normaal gerenderd. Zo niet, dan worden ze omgeleid naar de inlogpagina of de toegangsweigeringpagina.

```java title="InboxView.java"
// Inbox - toegankelijk voor alle geverifieerde gebruikers
@Route(value = "/", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

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
  private FlexLayout self = getBoundComponent();

  public TeamsView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("Teams"));
  }
}
```

De `InboxView` heeft geen annotatie, wat betekent dat elke geauthenticeerde gebruiker toegang heeft. Wanneer een gebruiker succesvol inlogt met de inloggegevens die je hebt gedefinieerd in `UserDetailsService` (`user/password` of `admin/admin`), kan hij deze route bekijken. Niet-geauthenticeerde gebruikers die proberen toegang te krijgen tot deze route worden omgeleid naar de inlogpagina.

De `TeamsView` maakt gebruik van `@RolesAllowed("ADMIN")` om de toegang te beperken tot gebruikers met de adminrol. Hoewel zowel het "gebruikers" als het "admin"-account geauthenticeerde gebruikers zijn, kunnen alleen gebruikers met de adminrol toegang krijgen tot deze route omdat deze zowel `USER` als `ADMIN` rollen heeft. Het gebruikersaccount heeft alleen de `USER` rol, dus het proberen toegang te krijgen tot deze route leidt hen om naar de toegangsweigeringpagina.

:::tip Beveiligingsannotaties
Zie de [gids voor beveiligingsannotaties](/docs/security/annotations) voor alle beschikbare annotaties.
:::

## Voeg afmeldfunctionaliteit toe {#add-logout-capability}

Gebruik `SpringSecurityFormSubmitter` om een afmeldknop te maken:

```java
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.IconButton;
import com.webforj.spring.security.SpringSecurityFormSubmitter;

IconButton logout = new IconButton(FeatherIcon.LOG_OUT.create());
logout.onClick(e -> SpringSecurityFormSubmitter.logout("/logout").submit());
```

Wanneer erop geklikt wordt, dient dit een formulier in bij het `/logout` eindpunt van Spring Security, dat de sessie van de gebruiker leegt en hen omleidt naar de inlogpagina met een afmeldsuccesbericht.

## Hoe het samenwerkt {#how-it-works-together}

Wanneer Spring Boot je app start:

1. **Auto-configuratie detecteert** zowel de afhankelijkheden van `webforj-spring-boot-starter` als `spring-boot-starter-security`
2. **Beveiligingsmanager wordt automatisch aangemaakt** om een brug te slaan tussen de webforJ-routering en de Spring Security-authenticatie
3. **Beveiligingsevaluatoren worden geregistreerd** om `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` en `@DenyAll` annotaties af te handelen
4. **Route-observer wordt gekoppeld** om navigatie te onderscheppen en beveiligingsregels te evalueren voordat componenten worden gerenderd

Je hoeft deze componenten niet handmatig te verbinden—de auto-configuratie van Spring Boot handelt de integratie af. Je definieert alleen je `SecurityConfig` met gebruikersbeheer en pagina-locaties.

Wanneer een gebruiker navigeert:

1. De beveiligingsobserver onderschept de navigatie
2. Evaluatoren verifiëren de beveiligingsannotaties van de route
3. Spring Security's `SecurityContextHolder` biedt authenticatie-informatie
4. Als geautoriseerd, wordt de component gerenderd; anders wordt de gebruiker omgeleid
