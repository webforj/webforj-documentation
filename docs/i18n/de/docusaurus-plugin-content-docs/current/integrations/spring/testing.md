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

[TestBed](/docs/testing/testbed/overview) führt Ansichten in einem einfachen JUnit-Test aus und benötigt keinen Browser. In einer Spring-Boot-App beziehen die Ansichten ihre Beans von Spring, und ihre Routen werden durch Spring Security geschützt. Daher benötigt der Test auch den Spring-Kontext. Eine Testklasse verwendet `@SpringBootTest` für den Kontext und `@WebforjTest` für die Ansichten. Beide arbeiten ohne weitere Konfiguration zusammen.

Auf dieser Seite geht es um die Ergänzungen durch Spring. Komponenten finden, mit ihnen interagieren, Assertions und Snapshots funktionieren mit und ohne Spring auf dieselbe Weise. Weitere Informationen dazu enthalten die Seiten zu [TestBed](/docs/testing/testbed/overview).

<!-- INTRO_END -->

## Abhängigkeit hinzufügen {#adding-the-dependency}

Füge `webforj-spring-testbed` mit Test-Scope hinzu. Die webforJ Bill of Materials verwaltet die Version, und das Modul bindet das JUnit TestBed ein:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-testbed</artifactId>
  <scope>test</scope>
</dependency>
```

Bei einem Projekt, das mit der Variante `webforj-spring` erstellt wurde, werden die Bill of Materials und `spring-boot-starter-test`, das JUnit bereitstellt, bereits importiert.

## Ansichten mit Spring-Beans {#views-with-spring-beans}

Die folgende Ansicht erhält einen Service über ihren Konstruktor und begrüßt den eingegebenen Namen:

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

Der Test verwendet beide Annotationen. `@SpringBootTest` startet den App-Kontext aus der Klasse mit `@SpringBootApplication`, wie bei jedem Spring-Boot-Test. `@WebforjTest` registriert die Ansicht. Der Test erhält mit `@Autowired` dieselbe Service-Bean und kann so den von der Ansicht angezeigten Text mit dem Rückgabewert des Service vergleichen:

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

`navigate` erstellt die Ansicht über Spring, sodass die Ansicht den Service genauso erhält wie in der laufenden App. Auch die [Scopes](./scopes) der Beans werden für Ansichten berücksichtigt.

Jeder Test erhält ein eigenes `WebforjTestbed`, das vor dem Test gestartet und danach beendet wird. So beginnt jeder Test mit einer leeren Seite. Der Spring-Kontext wird, wie bei jedem `@SpringBootTest`, testübergreifend gemeinsam genutzt und zwischengespeichert. Eine Singleton-Bean behält daher ihren Zustand von einem Test zum nächsten bei.

## Routen auswählen {#choosing-the-routes}

Wie bei jedem TestBed-Test legt `@WebforjTest` fest, womit der Test startet. `routeClasses` registriert genau die aufgelisteten Ansichten, `routePackages` alle `@Route`-Klassen in den aufgelisteten Paketen und `app` die Routen der App-Klasse über ihre `@Routify`-Annotation. Dabei wird die App wie in der Produktion ausgeführt. Ein Test für eine einzelne Ansicht registriert diese Ansicht. Ein Test der Routensicherheit startet die App, da [Sicherheit nur mit der App ausgeführt wird](#testing-secured-routes).

## Geschützte Routen testen {#testing-secured-routes}

Mit [Spring Security](/docs/security/getting-started) gelten Routen-Annotationen wie `@RolesAllowed` und `@AnonymousAccess` bei der Navigation in einem Test genauso wie in der laufenden App. Die folgende Konfiguration schützt die Routen und gibt die Seiten für die Anmeldung und den Zugriff verweigert an:

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

Die folgende Ansicht ist nur für Administratoren zugänglich:

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

Der Test startet die App und meldet einen Benutzer mit `@WithMockUser` aus `spring-security-test` an, das von Spring Boot verwaltet wird. Ein Test ohne diese Annotation wird als anonymer Besucher ausgeführt:

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

Mit der erforderlichen Rolle öffnet `navigate(Class)` die Ansicht. Wird der Zugriff verweigert, leitet die Routensicherheit zur Seite für den verweigerten Zugriff oder zur Anmeldeseite weiter, die in der Konfiguration angegeben ist. `navigate(String)` gibt die Ansicht zurück, die dort gerendert wurde. Der Test prüft die Weiterleitung daher anhand des Typs. `navigate(Class)` schlägt bei einer Ansicht, auf die der Zugriff verweigert wurde, mit einer Meldung fehl, die die stattdessen gerenderte Ansicht nennt.

Die Routensicherheit wird ausgeführt, wenn der Test die App mit `@WebforjTest(app = ...)` startet. Bei `routeClasses` oder `routePackages` werden die Ansichten ohne Routensicherheit gerendert. So kann ein Test eine geschützte Ansicht einzeln prüfen, ohne sich anzumelden.
