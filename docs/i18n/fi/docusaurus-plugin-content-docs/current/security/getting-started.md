---
title: Aloittaminen
sidebar_position: 2
_i18n_hash: e8996d53f35e093d9ba65c54774d1935
---
Spring Security tarjoaa vahvistuksen ja valtuutuksen Spring Boot -sovelluksille. Kun se integroidaan webforJ:n kanssa, se suojaa reittejä käyttämällä annotaatioita samalla kun Spring hoitaa käyttäjähallinnan ja istunnot.

Tämä opas kattaa Spring Securityn lisäämisen webforJ-sovellukseesi, vahvistuksen konfiguroinnin, kirjautumisnäkymien luomisen ja reittien suojaamisen rooliin perustuvalla pääsyoikeudella.

:::tip[Lisätietoa Spring Securitysta]
Yksityiskohtaisen ymmärryksen saamiseksi Spring Securityn ominaisuuksista ja käsitteistä, katso [Spring Securityn dokumentaatio](https://docs.spring.io/spring-security/reference/).
:::

## Lisää Spring Security -riippuvuus {#add-spring-security-dependency}

Lisää Spring Security starter `pom.xml`-tiedostoon:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Tämä yksittäinen riippuvuus tuo mukanaan Spring Securityn vahvistuskehyksen, salasanan kooderoinnit ja istunnonhallinnan. Versio hallitaan automaattisesti Spring Boot -vanhempien POM-tiedostojen kautta.

## Konfiguroi Spring Security {#configure-spring-security}

Luo turvallisuuskokoonpanoluokka, joka yhdistää Spring Securityn webforJ:n kanssa. Tämä luokka määrittelee, miten käyttäjiä vahvistetaan ja mitkä sivut käsittelevät kirjautumista ja pääsyn estämistä:

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

Tämä kokoonpano luo neljä Spring-beania, jotka toimivat yhdessä:

**`SecurityFilterChain`** yhdistää Spring Securityn webforJ:n reititysjärjestelmään. `WebforjSecurityConfigurer.webforj()` -menetelmä integroi Spring Security -vahvistuksen webforJ-reititykseen. Määrität, mitkä komponenttiluokat käsittelevät kirjautumista ja pääsyn estämistä, ja Spring valvoo vahvistusta ennen suojattujen reittien renderöintiä.

- `loginPage()` -menetelmä kertoo Spring Securitylle, mistä käyttäjien tulisi vahvistaa. Anna kirjautumisnäkymäkomponenttisi luokka, ja webforJ ratkaisee reitin automaattisesti `@Route`-annotaation perusteella. Kun vahvistamattomat käyttäjät yrittävät käyttää suojattuja reittejä, heidät ohjataan tänne.

- `accessDeniedPage()` -menetelmä määrittelee, minne vahvistetut käyttäjät menevät, kun heiltä puuttuu pääsy oikeudet reitille. Esimerkiksi käyttäjä, joka yrittää käyttää vain ylläpitäjille tarkoitettua reittiä, ohjataan tälle sivulle.

- `logout()` -menetelmä aktivoi kirjautumisen päätepisteen `/logout`. Kirjautumisen jälkeen käyttäjät ohjataan takaisin kirjautumissivulle, jossa on `?logout` -parametri.

**`PasswordEncoder`** käyttää BCryptia salasanojen turvalliseen muuntamiseen. Spring Security soveltaa tätä kooderia automaattisesti kirjautumisen yhteydessä vertaillessaan annettua salasanaa tallennettuun hash-arvoon.

**`UserDetailsService`** kertoo Spring Securitylle, mistä löytää käyttäjätiedot vahvistuksen aikana. Tämä esimerkki käyttää muistivarastoa, jossa on kaksi käyttäjää: `user/password` ja `admin/admin`.

**`AuthenticationManager`** koordinoi vahvistusprosessia. Se käyttää tarjoajaa, joka lataa käyttäjiä `UserDetailsService`:stä ja vahvistaa salasanojen `PasswordEncoder`in avulla.

## Luo kirjautumisnäkymä {#create-login-view}

Luo näkymä, joka esittelee kirjautumisdialogin ja lähettää tunnistetiedot Spring Securitylle. Seuraava näkymä käyttää [`Login`](/docs/components/login) komponenttia:

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
      Toast.show("Virheellinen käyttäjänimi tai salasana. Yritä uudelleen.", Theme.DANGER);
    }

    if (queryParams.containsKey("logout")) {
      Toast.show("Olet kirjautunut ulos onnistuneesti.", Theme.GRAY);
    }
  }
}
```

`@AnonymousAccess` -annotaatio merkitsee tämän reitin julkiseksi, jotta vahvistamattomat käyttäjät voivat käyttää kirjautumissivua. Ilman tätä annotaatiota käyttäjiä ohjattaisiin pois kirjautumissivulta, mikä luo äärettömän silmukan.

Rivi `setAction("/signin")` on kriittinen, se määrittää Kirjautuminen komponentin POSTamaan tunnistetiedot Springin vahvistuspisteeseen. Spring keskeyttää tämän lähetyksen, vahvistaa tunnistetiedot ja joko myöntää pääsyn tai ohjaa takaisin virheparametrilla.

`onDidEnter` -havaitsija tarkistaa kyselyparametrit, joita Spring lisää tulosten viestimiseen. Kun vahvistaminen epäonnistuu, Spring ohjaa `/signin?error`-osoitteeseen. Kirjautumisen jälkeen se ohjaa `/signin?logout`-osoitteeseen. Havaitsija näyttää asianmukaiset viestit näiden parametrien perusteella.

:::tip Pääteosoitteiden vastine
Rivin `setAction("/signin")` on vastaava kuin `@Route("/signin")` -reitti. Spring keskeyttää lomakevykset tarkalleen tälle osoitteelle vahvistamista varten. Jos tarvitset eri reittejä kirjautumissivulle ja vahvistusprosessille, määritä ne erikseen `SecurityConfig`:ssä:

```java
.loginPage("/signin", "/authenticate")
```

Tämä näyttää kirjautumissivun osoitteessa `/signin`, mutta käsittelee vahvistamista osoitteessa `/authenticate`.
:::

## Luo pääsy estetty -näkymä {#create-access-denied-view}

Luo näkymä, joka näytetään, kun käyttäjiltä puuttuu oikeus käyttää reittiä:

```java title="AccessDenyView.java"
@Route(value = "/access-denied", outlet = MainLayout.class)
public class AccessDenyView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public AccessDenyView() {
    Paragraph message = new Paragraph("Hups! Tämä alue on vain VIP:lle.");
    Paragraph subMessage = new Paragraph(
        "Näyttää siltä, että yritit piiloutua johtokunnan oleskelutilaan! Joko hanki paremmat tunnistetiedot tai palaa takaisin julkisiin tiloihin, joissa kahvi on ilmaista.");

    self.add(message, subMessage);
  }
}
```

Tämä näkymä renderöidään, kun vahvistetut käyttäjät yrittävät käyttää reittejä, joihin heillä ei ole pääsyä, kuten käyttäjä, joka yrittää käyttää vain ylläpitäjille tarkoitettua reittiä.

## Suojaa reittisi {#protect-your-routes}

Kun vahvistus on konfiguroitu, voit nyt suojata reittisi turvallisuusannotaatioilla. Nämä annotaatiot kertovat Spring Securitylle, kuka voi käyttää kutakin näkymää, ja turvallisuusjärjestelmä valvoo näitä sääntöjä automaattisesti ennen komponenttien renderöintiä.

Kun käyttäjä navigoi reitille, Spring Security keskeyttää navigoinnin ja tarkistaa reitin turvallisuusannotaatiot. Jos käyttäjä on vahvistettu (kirjautunut sisään voimassa olevilla tunnistetiedoilla) ja hänellä on vaaditut oikeudet, näkymä renderöidään normaalisti. Muuten käyttäjä ohjataan joko kirjautumissivulle tai pääsy estetty -sivulle.

```java title="InboxView.java"
// Inbox - kaikille vahvistetuille käyttäjille
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
// Teams - vaatii ADMIN -roolin
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

`InboxView` ei sisällä annotaatiota, mikä tarkoittaa, että mikä tahansa vahvistettu käyttäjä voi käyttää sitä. Kun käyttäjä kirjautuu onnistuneesti sisään `UserDetailsService`:ssä määritetyillä tunnistetiedoilla (`user/password` tai `admin/admin`), hän voi nähdä tämän reitin. Vahvistamattomat käyttäjät, jotka yrittävät käyttää tätä reittiä, ohjataan kirjautumissivulle.

`TeamsView` käyttää `@RolesAllowed("ADMIN")`, joka rajoittaa pääsyn vain ylläpitäjäroolille. Vaikka sekä "user" että "admin" -tilit ovat vahvistettuja käyttäjiä, vain ylläpitäjäroolin omaavilla käyttäjillä on pääsy tälle reitille, koska se sisältää sekä `USER` että `ADMIN` -roolit. Käyttäjätilillä on vain `USER`-rooli, joten yritys päästä tälle reitille ohjaa heidät pääsy estetty -sivulle.

:::tip Turvallisuusannotaatiot
Katso [Turvallisuusannotaatiot opas](/docs/security/annotations) saadaksesi lisätietoa kaikista käytettävissä olevista annotaatioista.
:::

## Lisää uloskirjautumismahdollisuus {#add-logout-capability}

Käytä `SpringSecurityFormSubmitter`-komponenttia luodaksesi uloskirjautumispainikkeen:

```java
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.IconButton;
import com.webforj.spring.security.SpringSecurityFormSubmitter;

IconButton logout = new IconButton(FeatherIcon.LOG_OUT.create());
logout.onClick(e -> SpringSecurityFormSubmitter.logout("/logout").submit());
```

Kun painiketta klikataan, se lähettää lomakkeen Spring Securityn `/logout`-päätepisteeseen, joka tyhjentää käyttäjän istunnon ja ohjaa kirjautumissivulle uloskirjautumismenestyksen viestin kanssa.

## Kuinka kaikki toimii yhdessä {#how-it-works-together}

Kun Spring Boot käynnistää sovelluksesi:

1. **Automaattinen konfigurointi havaitsee** sekä `webforj-spring-boot-starter` että `spring-boot-starter-security` riippuvuudet.
2. **Turvallisuusmanageri luodaan** automaattisesti yhdistämään webforJ-reititys ja Spring Security -vahvistus.
3. **Turvallisuusarvioijat rekisteröidään** käsittelemään `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` ja `@DenyAll` -annotaatioita.
4. **Reittihavaitsija liitetään** keskeyttämään navigointi ja arvioimaan turvallisuussäännöt ennen komponenttien renderöintiä.

Et kytke näitä komponentteja käsin - Spring Bootin automaattinen konfigurointi huolehtii integraatiosta. Määrität vain `SecurityConfig`-tiedoston käyttäjähallinnalla ja sivupolkuilla.

Kun käyttäjä navigoi:

1. Turvallisuushavaitsija keskeyttää navigoinnin.
2. Arvioijat vahvistavat reitin turvallisuusannotaatiot.
3. Spring Securityn `SecurityContextHolder` tarjoaa vahvistustiedot.
4. Jos valtuudet on myönnetty, komponentti renderöidään; muuten käyttäjä ohjataan.
