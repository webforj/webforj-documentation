---
sidebar_position: 30
title: Pruebas
description: >-
  Test a webforJ Spring Boot app without a browser with @SpringBootTest and
  @WebforjTest, with injected beans and Spring Security on routes.
sidebar_class_name: new-content
_i18n_hash: be6b059b304f8b957535c4ce318ac31c
---
# Pruebas <DocChip chip='since' label='26.03' />

[TestBed](/docs/testing/testbed/overview) ejecuta vistas en una prueba JUnit normal y no necesita un navegador. En una aplicación Spring Boot, las vistas reciben sus beans de Spring y sus rutas están protegidas por Spring Security, por lo que la prueba también necesita el contexto de Spring. Una clase de prueba lleva `@SpringBootTest` para el contexto y `@WebforjTest` para las vistas; ambas anotaciones funcionan juntas sin ninguna configuración adicional.

Esta página explica qué aporta Spring. La búsqueda de componentes, las interacciones, las aserciones y las instantáneas funcionan igual con Spring o sin él; las páginas de [TestBed](/docs/testing/testbed/overview) las describen.

<!-- INTRO_END -->

## Añadir la dependencia {#adding-the-dependency}

Añade `webforj-spring-testbed` con alcance de prueba. La lista de materiales de webforJ administra su versión, y el módulo incluye JUnit TestBed:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-testbed</artifactId>
  <scope>test</scope>
</dependency>
```

Un proyecto generado con la variante `webforj-spring` ya importa la lista de materiales y `spring-boot-starter-test`, que proporciona JUnit.

## Vistas con beans de Spring {#views-with-spring-beans}

La siguiente vista recibe un servicio mediante su constructor y saluda al nombre que introduce el usuario:

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

La prueba lleva ambas anotaciones. `@SpringBootTest` inicia el contexto de la aplicación desde la clase `@SpringBootApplication`, como cualquier prueba de Spring Boot, y `@WebforjTest` registra la vista. La prueba recibe el mismo bean de servicio con `@Autowired`, así que puede comparar lo que muestra la vista con lo que devuelve el servicio:

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

`navigate` crea la vista mediante Spring, de modo que la vista recibe el servicio igual que en la aplicación en ejecución. Las vistas también respetan sus [ámbitos](./scopes) de bean.

Cada prueba obtiene su propio `WebforjTestbed`, que se inicia antes de la prueba y finaliza después; así, cada prueba comienza con una página vacía. El contexto de Spring se comparte y se almacena en caché entre las pruebas, tal como Spring lo hace con cualquier `@SpringBootTest`; por eso, un bean singleton conserva su estado de una prueba a la siguiente.

## Seleccionar las rutas {#choosing-the-routes}

`@WebforjTest` determina con qué empieza la prueba, como en cualquier prueba de TestBed. `routeClasses` registra exactamente las vistas indicadas; `routePackages`, todas las clases `@Route` de los paquetes indicados; y `app`, las rutas de la clase de aplicación mediante su anotación `@Routify`, con la aplicación en ejecución como en producción. Una prueba de una sola vista registra esa vista. Una prueba de la seguridad de las rutas inicia la aplicación, porque [la seguridad solo se aplica con la aplicación](#testing-secured-routes).

## Probar rutas protegidas {#testing-secured-routes}

Con [Spring Security](/docs/security/getting-started), las anotaciones de ruta como `@RolesAllowed` y `@AnonymousAccess` se aplican durante la navegación en una prueba, igual que en la aplicación en ejecución. La siguiente configuración protege las rutas e indica las páginas de inicio de sesión y de acceso denegado:

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

La siguiente vista solo está disponible para los administradores:

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

La prueba inicia la aplicación e inicia sesión como usuario con `@WithMockUser` de `spring-security-test`, que Spring Boot administra. Una prueba sin esta anotación se ejecuta como visitante anónimo:

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

Con el rol que requiere la vista, `navigate(Class)` la abre. Cuando se deniega el acceso, la seguridad de rutas redirige a la página de acceso denegado o a la página de inicio de sesión indicada en la configuración, y `navigate(String)` devuelve la vista que se mostró allí, por lo que la prueba comprueba la redirección mediante su tipo. Si se deniega el acceso, `navigate(Class)` con la vista solicitada falla y muestra un mensaje que indica qué vista se mostró en su lugar.

La seguridad de rutas se aplica cuando la prueba inicia la aplicación con `@WebforjTest(app = ...)`. Con `routeClasses` o `routePackages`, las vistas se muestran sin seguridad de rutas, por lo que una prueba puede comprobar una vista protegida por separado, sin iniciar sesión.
