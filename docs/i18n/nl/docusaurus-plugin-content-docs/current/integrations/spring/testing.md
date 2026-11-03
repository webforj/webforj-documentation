---
sidebar_position: 30
title: Testen
description: >-
  Test a webforJ Spring Boot app without a browser with @SpringBootTest and
  @WebforjTest, with injected beans and Spring Security on routes.
sidebar_class_name: new-content
_i18n_hash: be6b059b304f8b957535c4ce318ac31c
---
# Testen <DocChip chip='since' label='26.03' />

De [TestBed](/docs/testing/testbed/overview) voert weergaven uit in een gewone JUnit-test en heeft geen browser nodig. In een Spring Boot-app halen de weergaven hun beans op uit Spring en worden hun routes beveiligd door Spring Security. Daarom heeft de test ook de Spring-context nodig. Een testklasse bevat `@SpringBootTest` voor de context en `@WebforjTest` voor de weergaven. Deze werken samen zonder verdere configuratie.

Op deze pagina lees je wat Spring toevoegt. Componenten zoeken, ermee werken, assertions en snapshots werken op dezelfde manier met en zonder Spring. De pagina's over [TestBed](/docs/testing/testbed/overview) behandelen deze onderwerpen.

<!-- INTRO_END -->

## De afhankelijkheid toevoegen {#adding-the-dependency}

Voeg `webforj-spring-testbed` toe met de testscope. De webforJ bill of materials beheert de versie en de module bevat de JUnit TestBed:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-testbed</artifactId>
  <scope>test</scope>
</dependency>
```

Een project dat met de `webforj-spring`-variant is gegenereerd, importeert de bill of materials en `spring-boot-starter-test` al. Deze laatste levert JUnit.

## Weergaven met Spring-beans {#views-with-spring-beans}

De volgende weergave ontvangt een service via de constructor en begroet de naam die de gebruiker invoert:

```java title="WelcomeView.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.Route;
import com.webforj.router.security.annotation.AnonymousAccess;
import com.webforj.samples.spring.GreetingService;

@Route("/")
@AnonymousAccess
public class WelcomeView extends Composite<Div> {

  public WelcomeView(GreetingService greetings) {
    TextField name = new TextField("Name");
    Paragraph greeting = new Paragraph();
    name.onModify(e -> greeting.setText(greetings.greet(name.getValue())));

    getBoundComponent().add(name, greeting);
  }
}
```

```java title="GreetingService.java" showLineNumbers
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

  public String greet(String name) {
    return "Welcome, " + name + "!";
  }
}
```

De test bevat beide annotaties. `@SpringBootTest` start de app-context vanuit de klasse met `@SpringBootApplication`, zoals bij elke Spring Boot-test. `@WebforjTest` registreert de weergave. De test ontvangt met `@Autowired` dezelfde service-bean, zodat deze kan vergelijken wat de weergave toont met wat de service retourneert:

```java title="WelcomeViewTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.samples.spring.GreetingService;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@WebforjTest(routeClasses = WelcomeView.class)
class WelcomeViewTest {
  @Autowired
  private GreetingService greetings;

  @Test
  void shouldGreetWithTheServiceSpringInjected(WebforjTestbed context) {
    context.navigate(WelcomeView.class);

    UserInteraction.type(
        ComponentLocator.of(TextField.class).byLabel("Name").locate(), "Ada");

    verifyThat(ComponentLocator.of(Paragraph.class).locate())
        .hasText(greetings.greet("Ada"));
  }
}
```

`navigate` maakt de weergave aan via Spring, zodat de weergave de service op dezelfde manier ontvangt als in de draaiende app. Weergaven respecteren ook hun bean-[scopes](./scopes).

Elke test krijgt een eigen `WebforjTestbed`, die vóór de test wordt gestart en erna wordt beëindigd. Zo begint elke test met een lege pagina. De Spring-context wordt gedeeld en tussen tests gecachet, zoals Spring dat doet voor elke `@SpringBootTest`. Een singleton-bean behoudt dus zijn status van de ene test naar de volgende.

## De routes kiezen {#choosing-the-routes}

`@WebforjTest` bepaalt waarmee de test begint, net als bij elke TestBed-test. `routeClasses` registreert precies de opgegeven weergaven, `routePackages` alle `@Route`-klassen in de opgegeven pakketten en `app` de routes van de app-klasse via de annotatie `@Routify`. De app wordt daarbij uitgevoerd zoals in productie. Een test van één weergave registreert die weergave. Een test van routebeveiliging start de app, omdat [beveiliging alleen actief is met de app](#testing-secured-routes).

## Beveiligde routes testen {#testing-secured-routes}

Met [Spring Security](/docs/security/getting-started) zijn route-annotaties zoals `@RolesAllowed` en `@AnonymousAccess` tijdens het navigeren in een test van toepassing, net als in de draaiende app. De volgende configuratie beveiligt de routes en geeft de inlogpagina en de pagina voor geweigerde toegang op:

```java title="SecurityConfig.java" showLineNumbers
import com.webforj.samples.spring.views.DeniedView;
import com.webforj.samples.spring.views.LoginView;
import com.webforj.spring.security.WebforjSecurityConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .with(
            WebforjSecurityConfigurer.webforj(),
            configurer -> configurer.loginPage(LoginView.class)
                .accessDeniedPage(DeniedView.class))
        .build();
  }

  @Bean
  UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager(
        User.withDefaultPasswordEncoder()
            .username("ada")
            .password("secret")
            .roles("ADMIN")
            .build(),
        User.withDefaultPasswordEncoder()
            .username("bob")
            .password("secret")
            .roles("USER")
            .build());
  }
}
```

De volgende weergave is alleen toegankelijk voor beheerders:

```java title="AdminView.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.router.annotation.Route;
import jakarta.annotation.security.RolesAllowed;

@Route("admin")
@RolesAllowed("ADMIN")
public class AdminView extends Composite<Div> {

  public AdminView() {
    getBoundComponent().add(new H1("Administration"));
  }
}
```

De test start de app en meldt een gebruiker aan met `@WithMockUser` uit `spring-security-test`, dat door Spring Boot wordt beheerd. Een test zonder deze annotatie wordt uitgevoerd als anonieme bezoeker:

```java title="AdminViewTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.webforj.samples.spring.Application;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@WebforjTest(app = Application.class)
class AdminViewTest {
  @Test
  @WithMockUser(username = "ada", roles = "ADMIN")
  void shouldOpenTheAdministrationForAnAdmin(WebforjTestbed context) {
    AdminView admin = context.navigate(AdminView.class);

    verifyThat(admin).isAttached();
  }

  @Test
  @WithMockUser(username = "bob", roles = "USER")
  void shouldDenyAUserWithoutTheRole(WebforjTestbed context) {
    assertInstanceOf(DeniedView.class, context.navigate("admin"));
  }

  @Test
  void shouldSendAnAnonymousVisitorToTheLogin(WebforjTestbed context) {
    assertInstanceOf(LoginView.class, context.navigate("admin"));
  }
}
```

Met de vereiste rol opent `navigate(Class)` de weergave. Als toegang wordt geweigerd, stuurt de routebeveiliging door naar de pagina voor geweigerde toegang of de inlogpagina die in de configuratie is opgegeven. `navigate(String)` retourneert de weergave die daar is weergegeven, zodat de test de doorverwijzing controleert aan de hand van het type. `navigate(Class)` met een weergave waartoe toegang is geweigerd, mislukt met een bericht waarin de weergave wordt genoemd die in plaats daarvan is weergegeven.

Routebeveiliging is actief wanneer de test de app start met `@WebforjTest(app = ...)`. Met `routeClasses` of `routePackages` worden weergaven zonder routebeveiliging weergegeven, zodat een test een beveiligde weergave afzonderlijk kan controleren, zonder aan te melden.
