---
sidebar_position: 30
title: Testaus
description: >-
  Test a webforJ Spring Boot app without a browser with @SpringBootTest and
  @WebforjTest, with injected beans and Spring Security on routes.
sidebar_class_name: new-content
_i18n_hash: be6b059b304f8b957535c4ce318ac31c
---
# Testaus <DocChip chip='since' label='26.03' />

[TestBed](/docs/testing/testbed/overview) suorittaa näkymiä tavallisessa JUnit-testissä eikä tarvitse selainta. Spring Boot -sovelluksessa näkymät saavat beaninsa Springiltä ja niiden reittejä suojaa Spring Security, joten testissä tarvitaan myös Spring-konteksti. Testiluokassa käytetään kontekstia varten merkintää `@SpringBootTest` ja näkymiä varten merkintää `@WebforjTest`. Ne toimivat yhdessä ilman lisäasetuksia.

Tällä sivulla käsitellään Springin tuomia ominaisuuksia. Komponenttien etsiminen, niiden käsittely, väitteet ja tilannevedokset toimivat samalla tavalla Springin kanssa ja ilman sitä, ja [TestBed](/docs/testing/testbed/overview) -sivuilla käsitellään niitä.

<!-- INTRO_END -->

## Riippuvuuden lisääminen {#adding-the-dependency}

Lisää `webforj-spring-testbed` testiriippuvuudeksi. webforJ:n bill of materials hallitsee sen versiota, ja moduuli tuo mukanaan JUnit TestBedin:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-testbed</artifactId>
  <scope>test</scope>
</dependency>
```

`webforj-spring`-variantilla luotu projekti tuo jo valmiiksi käyttöön bill of materialsin ja `spring-boot-starter-test`-riippuvuuden, joka tarjoaa JUnitin.

## Näkymät Spring-beaneilla {#views-with-spring-beans}

Seuraava näkymä saa palvelun konstruktorinsa kautta ja tervehtii käyttäjän kirjoittamaa nimeä:

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

Testissä käytetään molempia merkintöjä. `@SpringBootTest` käynnistää sovelluksen kontekstin `@SpringBootApplication`-luokasta, kuten mikä tahansa Spring Boot -testi, ja `@WebforjTest` rekisteröi näkymän. Testi saa käyttöönsä saman palvelubeanin merkinnällä `@Autowired`, joten se voi verrata näkymän näyttämää tekstiä palvelun palauttamaan tekstiin:

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

`navigate` luo näkymän Springin kautta, joten näkymä saa palvelun samalla tavalla kuin käynnissä olevassa sovelluksessa. Näkymissä noudatetaan myös niiden beanin [laajuuksia](./scopes).

Jokainen testi saa oman `WebforjTestbed`-instanssin, joka käynnistyy ennen testiä ja päättyy sen jälkeen. Siksi jokainen testi alkaa tyhjältä sivulta. Spring-konteksti jaetaan testien kesken ja tallennetaan välimuistiin samalla tavalla kuin Spring tallentaa sen välimuistiin missä tahansa `@SpringBootTest`-testissä, joten singleton-bean säilyttää tilansa testistä toiseen.

## Reittien valitseminen {#choosing-the-routes}

`@WebforjTest` määrittää, millä testissä aloitetaan, kuten missä tahansa TestBed-testissä. `routeClasses` rekisteröi täsmälleen luetellut näkymät, `routePackages` kaikki lueteltujen pakettien `@Route`-luokat ja `app` sovellusluokan reitit sen `@Routify`-merkinnän kautta niin, että sovellus toimii kuten tuotannossa. Yhden näkymän testissä rekisteröidään kyseinen näkymä. Reittien tietoturvan testausta varten sovellus käynnistetään, sillä [tietoturva toimii vain sovelluksen kanssa](#testing-secured-routes).

## Suojattujen reittien testaaminen {#testing-secured-routes}

Kun käytössä on [Spring Security](/docs/security/getting-started), reittimerkinnät, kuten `@RolesAllowed` ja `@AnonymousAccess`, vaikuttavat navigointiin testissä samalla tavalla kuin käynnissä olevassa sovelluksessa. Seuraava määritys suojaa reitit ja määrittää kirjautumis- ja pääsyn estämisen sivut:

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

Seuraava näkymä on avoinna vain järjestelmänvalvojille:

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

Testi käynnistää sovelluksen ja kirjautuu sisään käyttäjänä, jolla on `spring-security-test`-riippuvuudesta saatava `@WithMockUser`-merkintä. Spring Boot hallitsee riippuvuutta. Testi ilman tätä merkintää suoritetaan anonyyminä kävijänä:

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

Kun käyttäjällä on näkymän edellyttämä rooli, `navigate(Class)` avaa näkymän. Jos pääsy evätään, reittien tietoturva ohjaa käyttäjän määrityksessä nimettyyn pääsyn estämisen sivuun tai kirjautumissivulle. `navigate(String)` palauttaa siellä renderöidyn näkymän, joten testi tarkistaa uudelleenohjauksen näkymän tyypin perusteella. Jos pääsy evätään, `navigate(Class)` epäonnistuu ja virheilmoitus kertoo, mikä näkymä renderöitiin sen sijaan.

Reittien tietoturva on käytössä, kun testi käynnistää sovelluksen määrityksellä `@WebforjTest(app = ...)`. Jos käytössä on `routeClasses` tai `routePackages`, näkymät renderöidään ilman reittien tietoturvaa, joten suojattua näkymää voi testata erikseen ilman kirjautumista.
