---
title: Premiers Pas
sidebar_position: 2
_i18n_hash: e8996d53f35e093d9ba65c54774d1935
---
Spring Security fournit une authentification et une autorisation pour les applications Spring Boot. Lorsqu'il est intégré avec webforJ, il protège les routes à l'aide d'annotations pendant que Spring gère la gestion des utilisateurs et des sessions.

Ce guide couvre l'ajout de Spring Security à votre application webforJ, la configuration de l'authentification, la création de vues de connexion et la protection des routes avec un contrôle d'accès basé sur les rôles.

:::tip[En savoir plus sur Spring Security]
Pour une compréhension complète des fonctionnalités et des concepts de Spring Security, consultez la [documentation Spring Security](https://docs.spring.io/spring-security/reference/).
:::

## Ajouter une dépendance Spring Security {#add-spring-security-dependency}

Ajoutez le starter Spring Security à votre `pom.xml` :

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Cette seule dépendance apporte le cadre d'authentification de Spring Security, les encodeurs de mots de passe et la gestion des sessions. La version est automatiquement gérée par le POM parent de votre Spring Boot.

## Configurer Spring Security {#configure-spring-security}

Créez une classe de configuration de sécurité qui connecte Spring Security avec webforJ. Cette classe définit comment les utilisateurs sont authentifiés et quelles pages gèrent la connexion et le refus d'accès :

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

Cette configuration crée quatre beans Spring qui travaillent ensemble :

**`SecurityFilterChain`** connecte Spring Security avec le système de routage de webforJ. La méthode `WebforjSecurityConfigurer.webforj()` intègre l'authentification Spring Security avec le routage de webforJ. Vous spécifiez quelles classes de composants gèrent la connexion et le refus d'accès, et Spring appliquera l'authentification avant de rendre les routes protégées.

- La méthode `loginPage()` indique à Spring Security où les utilisateurs doivent s'authentifier. Passez votre classe de composant de vue de connexion, et webforJ résoudra automatiquement le chemin de la route à partir de l'annotation `@Route`. Lorsque des utilisateurs non authentifiés essaient d'accéder à des routes protégées, ils sont redirigés ici.

- La méthode `accessDeniedPage()` définit où vont les utilisateurs authentifiés lorsqu'ils n'ont pas les autorisations pour une route. Par exemple, un utilisateur essayant d'accéder à une route réservée aux administrateurs est redirigé vers cette page.

- La méthode `logout()` active le point de terminaison de déconnexion à `/logout`. Après la déconnexion, les utilisateurs sont redirigés vers la page de connexion avec un paramètre `?logout`.

**`PasswordEncoder`** utilise BCrypt pour hacher les mots de passe de manière sécurisée. Spring Security applique automatiquement cet encodeur lors de la connexion pour comparer le mot de passe soumis avec le hachage stocké.

**`UserDetailsService`** indique à Spring Security où trouver les informations utilisateur lors de l'authentification. Cet exemple utilise un stockage en mémoire avec deux utilisateurs : `user/password` et `admin/admin`.

**`AuthenticationManager`** coordonne le processus d'authentification. Il utilise un fournisseur qui charge les utilisateurs à partir de `UserDetailsService` et vérifie les mots de passe avec `PasswordEncoder`.

## Créer une vue de connexion {#create-login-view}

Créez une vue qui présente une boîte de dialogue de connexion et soumet les identifiants à Spring Security. La vue suivante utilise le composant [`Login`](/docs/components/login) :

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
      Toast.show("Nom d'utilisateur ou mot de passe invalide. Veuillez réessayer.", Theme.DANGER);
    }

    if (queryParams.containsKey("logout")) {
      Toast.show("Vous avez été déconnecté avec succès.", Theme.GRAY);
    }
  }
}
```

L'annotation `@AnonymousAccess` marque cette route comme publique afin que les utilisateurs non authentifiés puissent accéder à la page de connexion. Sans cette annotation, les utilisateurs seraient redirigés loin de la page de connexion, créant une boucle infinie.

La ligne `setAction("/signin")` est critique, elle configure le composant de connexion pour POST des identifiants vers le point de terminaison d'authentification de Spring. Spring intercepte cette soumission, vérifie les identifiants et accorde l'accès ou redirige en arrière avec un paramètre d'erreur.

L'observateur `onDidEnter` vérifie les paramètres de requête que Spring ajoute pour communiquer les résultats. Lorsque l'authentification échoue, Spring redirige vers `/signin?error`. Après la déconnexion, il redirige vers `/signin?logout`. L'observateur affiche des messages appropriés sur la base de ces paramètres.

:::tip Correspondance des points de terminaison
Le chemin dans `setAction("/signin")` doit correspondre à votre chemin `@Route("/signin")`. Spring intercepte les soumissions de formulaires vers ce chemin exact pour l'authentification. Si vous avez besoin de chemins différents pour la page de connexion et le traitement de l'authentification, configurez-les séparément dans `SecurityConfig` :

```java
.loginPage("/signin", "/authenticate")
```

Cela affiche la page de connexion à `/signin` mais traite l'authentification à `/authenticate`.
:::

## Créer une vue de refus d'accès {#create-access-denied-view}

Créez une vue qui s'affiche lorsque les utilisateurs n'ont pas la permission d'accéder à une route :

```java title="AccessDenyView.java"
@Route(value = "/access-denied", outlet = MainLayout.class)
public class AccessDenyView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public AccessDenyView() {
    Paragraph message = new Paragraph("Oups ! Cette zone est réservée aux VIP uniquement.");
    Paragraph subMessage = new Paragraph(
        "On dirait que vous avez essayé de vous faufiler dans le salon exécutif ! Soit vous obtenez de meilleurs identifiants, soit vous retournez dans les zones publiques où le café est gratuit de toute façon.");

    self.add(message, subMessage);
  }
}
```

Cette vue est rendue lorsque des utilisateurs authentifiés tentent d'accéder à des routes pour lesquelles ils n'ont pas les autorisations, comme un utilisateur tentant d'accéder à une route réservée aux administrateurs.

## Protéger vos routes {#protect-your-routes}

Avec l'authentification configurée, vous pouvez maintenant protéger vos routes en utilisant des annotations de sécurité. Ces annotations indiquent à Spring Security qui peut accéder à chaque vue, et le système de sécurité applique automatiquement ces règles avant de rendre tout composant.

Lorsqu'un utilisateur navigue vers une route, Spring Security intercepte la navigation et vérifie les annotations de sécurité de la route. Si l'utilisateur est authentifié (connecté avec des identifiants valides) et dispose des autorisations requises, la vue s'affiche normalement. Sinon, il est redirigé vers la page de connexion ou la page de refus d'accès.

```java title="InboxView.java"
// Inbox - accessible à tous les utilisateurs authentifiés
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
// Teams - nécessite le rôle ADMIN
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

L'`InboxView` n'a pas d'annotation, ce qui signifie que tout utilisateur authentifié peut y accéder. Lorsqu'un utilisateur se connecte avec succès avec les identifiants que vous avez définis dans `UserDetailsService` (`user/password` ou `admin/admin`), il peut voir cette route. Les utilisateurs non authentifiés essayant d'accéder à cette route sont redirigés vers la page de connexion.

La `TeamsView` utilise `@RolesAllowed("ADMIN")` pour restreindre l'accès aux utilisateurs ayant le rôle d'administrateur. Bien que les comptes "user" et "admin" soient des utilisateurs authentifiés, seuls les utilisateurs avec le rôle d'administrateur peuvent accéder à cette route car elle a les rôles `USER` et `ADMIN`. Le compte utilisateur n'a que le rôle `USER`, donc une tentative d'accès à cette route les redirige vers la page de refus d'accès.

:::tip Annotations de sécurité
Consultez le [guide des annotations de sécurité](/docs/security/annotations) pour toutes les annotations disponibles.
:::

## Ajouter une capacité de déconnexion {#add-logout-capability}

Utilisez `SpringSecurityFormSubmitter` pour créer un bouton de déconnexion :

```java
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.IconButton;
import com.webforj.spring.security.SpringSecurityFormSubmitter;

IconButton logout = new IconButton(FeatherIcon.LOG_OUT.create());
logout.onClick(e -> SpringSecurityFormSubmitter.logout("/logout").submit());
```

Lorsqu'il est cliqué, cela soumet un formulaire au point de terminaison `/logout` de Spring Security, qui efface la session de l'utilisateur et redirige vers la page de connexion avec un message de réussite de déconnexion.

## Comment cela fonctionne ensemble {#how-it-works-together}

Lorsque Spring Boot démarre votre application :

1. **La configuration automatique détecte** à la fois les dépendances `webforj-spring-boot-starter` et `spring-boot-starter-security`
2. **Un gestionnaire de sécurité est créé** automatiquement pour relier le routage de webforJ et l'authentification Spring Security
3. **Les évaluateurs de sécurité sont enregistrés** pour gérer les annotations `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` et `@DenyAll`
4. **Un observateur de route est attaché** pour intercepter la navigation et évaluer les règles de sécurité avant de rendre les composants

Vous ne câblez pas manuellement ces composants—la configuration automatique de Spring Boot gère l'intégration. Vous définissez simplement votre `SecurityConfig` avec la gestion des utilisateurs et les emplacements des pages.

Lorsqu'un utilisateur navigue :

1. L'observateur de sécurité intercepte la navigation
2. Les évaluateurs vérifient les annotations de sécurité de la route
3. Le `SecurityContextHolder` de Spring Security fournit des informations d'authentification
4. Si autorisé, le composant se rend ; sinon, l'utilisateur est redirigé
