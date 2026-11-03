---
sidebar_position: 30
title: Tests
description: >-
  Test a webforJ Spring Boot app without a browser with @SpringBootTest and
  @WebforjTest, with injected beans and Spring Security on routes.
sidebar_class_name: new-content
_i18n_hash: be6b059b304f8b957535c4ce318ac31c
---
# Tests <DocChip chip='since' label='26.03' />

[TestBed](/docs/testing/testbed/overview) exécute les vues dans un test JUnit classique, sans navigateur. Dans une application Spring Boot, les vues obtiennent leurs beans depuis Spring et leurs routes sont protégées par Spring Security ; le test a donc également besoin du contexte Spring. Une classe de test porte `@SpringBootTest` pour le contexte et `@WebforjTest` pour les vues, et les deux fonctionnent ensemble sans configuration supplémentaire.

Cette page décrit ce qu’apporte Spring. La recherche de composants, les interactions avec eux, les assertions et les instantanés fonctionnent de la même façon avec ou sans Spring ; les pages [TestBed](/docs/testing/testbed/overview) les présentent.

<!-- INTRO_END -->

## Ajouter la dépendance {#adding-the-dependency}

Ajoutez `webforj-spring-testbed` avec la portée de test. La nomenclature de webforJ gère sa version, et le module inclut le TestBed JUnit :

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-testbed</artifactId>
  <scope>test</scope>
</dependency>
```

Un projet généré avec la variante `webforj-spring` importe déjà la nomenclature et `spring-boot-starter-test`, qui fournit JUnit.

## Vues avec des beans Spring {#views-with-spring-beans}

La vue suivante reçoit un service par son constructeur et salue le nom saisi par l’utilisateur :

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

Le test porte les deux annotations. `@SpringBootTest` démarre le contexte de l’application à partir de la classe `@SpringBootApplication`, comme dans n’importe quel test Spring Boot, et `@WebforjTest` enregistre la vue. Le test reçoit le même bean de service avec `@Autowired`, ce qui lui permet de comparer ce qu’affiche la vue à ce que renvoie le service :

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

`navigate` crée la vue via Spring ; celle-ci reçoit donc le service de la même façon que dans l’application en cours d’exécution. Les vues respectent également leurs [portées](./scopes) de bean.

Chaque test reçoit son propre `WebforjTestbed`, qui démarre avant le test et se termine après lui ; chaque test commence donc avec une page vide. Le contexte Spring est partagé et mis en cache entre les tests, comme Spring le fait pour tout `@SpringBootTest` ; un bean singleton conserve donc son état d’un test à l’autre.

## Choisir les routes {#choosing-the-routes}

`@WebforjTest` détermine avec quoi le test démarre, comme pour tout test TestBed. `routeClasses` enregistre exactement les vues répertoriées, `routePackages` toutes les classes `@Route` des packages répertoriés, et `app` les routes de la classe d’application via son annotation `@Routify`, avec l’application exécutée comme en production. Un test d’une seule vue enregistre cette vue. Un test de la sécurité des routes lance l’application, car [la sécurité ne s’exécute qu’avec l’application](#testing-secured-routes).

## Tester les routes protégées {#testing-secured-routes}

Avec [Spring Security](/docs/security/getting-started), les annotations de route telles que `@RolesAllowed` et `@AnonymousAccess` s’appliquent pendant la navigation dans un test, comme dans l’application en cours d’exécution. La configuration suivante protège les routes et définit les pages de connexion et d’accès refusé :

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

La vue suivante est réservée aux administrateurs :

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

Le test lance l’application et connecte un utilisateur avec `@WithMockUser` de `spring-security-test`, dont Spring Boot gère la dépendance. Un test sans cette annotation s’exécute comme un visiteur anonyme :

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

Avec le rôle requis par la vue, `navigate(Class)` ouvre celle-ci. Lorsque l’accès est refusé, la sécurité des routes redirige vers la page d’accès refusé ou la page de connexion définie dans la configuration, et `navigate(String)` renvoie la vue qui s’y est affichée ; le test vérifie donc la redirection à l’aide de son type. Si l’accès est refusé, `navigate(Class)` échoue avec un message indiquant la vue qui s’est affichée à la place.

La sécurité des routes s’exécute lorsque le test lance l’application avec `@WebforjTest(app = ...)`. Avec `routeClasses` ou `routePackages`, les vues s’affichent sans sécurité des routes ; un test peut donc vérifier une vue protégée isolément, sans se connecter.
