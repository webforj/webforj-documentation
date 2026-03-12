---
title: Getting Started
sidebar_position: 2
_i18n_hash: becd2e7bd488a077c08ef5a64dbe0f61
---
Spring Security tarjoaa todennusta ja valtuutusta Spring Boot -sovelluksille. Kun se integroidaan webforJ:hen, se suojaa reittejä käyttämällä annotaatioita, kun taas Spring huolehtii käyttäjien hallinnasta ja istunnoista.

Tämä opas kattaa Spring Securityn lisäämisen webforJ-sovellukseesi, todennuksen määrittelyn, kirjautumisnäkymien luomisen ja reittien suojaamisen roolipohjaisella pääsyoikeudella.

:::tip[Lisätietoja Spring Securitystä]
Yhte comprehensiivinen ymmärrys Spring Securityn ominaisuuksista ja käsitteistä löytyy [Spring Securityn dokumentaatiosta](https://docs.spring.io/spring-security/reference/).
:::

## Lisää Spring Security -riippuvuus {#add-spring-security-dependency}

Lisää Spring Securityn aloitus riippuvuus `pom.xml`-tiedostoon:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Tämä yksi riippuvuus tuo mukanaan Spring Securityn todennuskehyksen, salasanan koodarit ja istunnon hallinnan. Versio hallitaan automaattisesti Spring Boot -vanhempa POM:isi.

## Määritä Spring Security {#configure-spring-security}

Luo turvakokoonpanoluokka, joka yhdistää Spring Securityn webforJ:hen. Tämä luokka määrittelee, miten käyttäjät todentuvat ja mitkä sivut käsittelevät kirjautumista ja pääsyn estämistä:

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

Tämä kokoonpano luo neljä Spring-papua, jotka työskentelevät yhdessä:

**`SecurityFilterChain`** yhdistää Spring Securityn webforJ:n reittijärjestelmään. `WebforjSecurityConfigurer.webforj()`-menetelmä integroi Spring Securityn todennuksen webforJ-reitityksen kanssa. Määrität, mitkä komponenttiluokat käsittelevät kirjautumista ja pääsyn estämistä, ja Spring valvoo todennusta ennen suojattujen reittien renderöintiä.

- `loginPage()`-menetelmä kertoo Spring Securitylle, mistä käyttäjien tulisi todentua. Anna kirjautumisnäkymän komponenttiluokka, ja webforJ ratkaisee automaattisesti reittipolun `@Route`-annotaation avulla. Kun todennetut käyttäjät yrittävät päästä suojatuille reiteille, heidät ohjataan tänne.

- `accessDeniedPage()`-menetelmä määrittää, mihin todennetut käyttäjät menevät, kun heiltä puuttuu käyttöoikeudet reitille. Esimerkiksi käyttäjä, joka yrittää päästä vain adminille tarkoitetulle reitille, ohjataan tälle sivulle.

- `logout()`-menetelmä mahdollistaa kirjautumisajon osoitteessa `/logout`. Kirjautumisen jälkeen käyttäjät ohjataan takaisin kirjautumissivulle, jossa on `?logout`-parametri.

**`PasswordEncoder`** käyttää BCryptiä salasanojen turvalliseen hajauttamiseen. Spring Security soveltaa tätä kooderia automaattisesti kirjautumisen yhteydessä verratakseen annettua salasanaa tallennettuun hajautukseen.

**`UserDetailsService`** kertoo Spring Securitylle, mistä käyttäjätietoja löydetään todennuksen aikana. Tämä esimerkki käyttää muistivarastoa, jossa on kaksi käyttäjää: `user/password` ja `admin/admin`.

**`AuthenticationManager`** koordinoi todennusprosessia. Se käyttää tarjotinta, joka lataa käyttäjiä `UserDetailsService`:stä ja tarkistaa salasanoja `PasswordEncoder`:in avulla.

## Luo kirjautumisnäkymä {#create-login-view}

Luo näkymä, joka esittää kirjautumisdialogin ja lähettää asiakirjat Spring Securitylle. Seuraava näkymä käyttää [`Login`](/docs/components/login) -komponenttia:

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

`@AnonymousAccess` -annotaatio merkitsee tämän reitin julkiseksi, jotta todennetut käyttäjät voivat käyttää kirjautumissivua. Ilman tätä annotaatiota käyttäjiä siirrettäisiin pois kirjautumissivulta, mikä luo loputtoman silmukan.

`setAction("/signin")`-rivi on kriittinen, se määrittää Login-komponentin lähettämään asiakirjat Springin todennuspisteeseen. Spring kaappaa tämän lähetyksen, tarkistaa asiakirjat ja myöntää pääsyn tai ohjaa takaisin virheparametrin kanssa.

`onDidEnter`-observer tarkistaa kyselyparametrit, joita Spring lisää tulosten ilmoittamiseen. Kun todennus epäonnistuu, Spring ohjaa osoitteeseen `/signin?error`. Kirjautumisen jälkeen se ohjaa `/signin?logout`. Observer näyttää asianmukaisia viestejä näiden parametrien perusteella.

:::tip Reitin vastaavuus
`setAction("/signin")`-polun on vastattava `@Route("/signin")` -polkua. Spring kaappaa lomakkeen lähetykset tälle tarkalle polulle todennusta varten. Jos tarvitset eri polkuja kirjautumissivulle ja todennuskäsittelylle, määritä ne erikseen `SecurityConfig`:ssä:

```java
.loginPage("/signin", "/authenticate")
```

Tämä näyttää kirjautumissivun osoitteessa `/signin`, mutta käsittelee todennuksen osoitteessa `/authenticate`.
:::

## Luo pääsy kielletty näkymä {#create-access-denied-view}

Luo näkymä, joka näytetään, kun käyttäjillä ei ole oikeuksia päästä reitille:

```java title="AccessDenyView.java"
@Route(value = "/access-denied", outlet = MainLayout.class)
public class AccessDenyView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public AccessDenyView() {
    Paragraph message = new Paragraph("Oops! Tämä alue on vain VIP:lle.");
    Paragraph subMessage = new Paragraph(
        "Näyttää siltä, että yritit tunkeutua johtajien kerhoon! Ota paremmin käyttöoikeuksia tai palaa takaisin julkisille alueille, joissa kahvi on ilmainen.");

    self.add(message, subMessage);
  }
}
```

Tätä näkymää renderöidään, kun todennetut käyttäjät yrittävät päästä reiteille, joihin heillä ei ole käyttöoikeuksia, kuten käyttäjä, joka yrittää päästä vain adminille tarkoitetulle reitille.

## Suojaa reittisi {#protect-your-routes}

Kun todennus on määritetty, voit nyt suojata reittisi turvallisuusannotaatioiden avulla. Nämä annotaatiot kertovat Spring Securitylle, kuka voi käyttää kumpaakin näkymää, ja turvallisuusjärjestelmä valvoo automaattisesti näitä sääntöjä ennen minkään komponentin renderöintiä.

Kun käyttäjä siirtyy reitille, Spring Security kaappaa navigoinnin ja tarkistaa reitin turvallisuusannotaatiot. Jos käyttäjä on todennettu (kirjautunut sisään voimassa olevilla asiakirjoilla) ja hänellä on vaadittavat käyttöoikeudet, näkymä renderöidään normaalisti. Jos ei, heidät ohjataan joko kirjautumissivulle tai pääsykielto-sivulle.

```java title="InboxView.java"
// Saapuneet - kaikille todennetuille käyttäjille saavutettavissa
@Route(value = "/", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public InboxView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("Saapuneet"));
  }
}
```

```java title="TeamsView.java" {3}
// Tiimit - vaatii ADMIN-roolin
@Route(value = "/teams", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TeamsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public TeamsView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("Tiimit"));
  }
}
```

`InboxView`-näkymällä ei ole annotaatiota, mikä tarkoittaa, että mikä tahansa todennettu käyttäjä voi käyttää sitä. Kun käyttäjä kirjautuu onnistuneesti sisään määritettyjen asiakirjojen kanssa `UserDetailsService`-luokasta (`user/password` tai `admin/admin`), he voivat tarkastella tätä reittiä. Todentamattomat käyttäjät, jotka yrittävät päästä tälle reitille, ohjataan kirjautumissivulle.

`TeamsView` käyttää `@RolesAllowed("ADMIN")` -annotaatiota rajoittaakseen pääsyä käyttäjille, joilla on admin-rooli. Vaikka sekä "user" että "admin" -tilit ovat todennettuja käyttäjiä, vain admin-roolin omaavat käyttäjät voivat käyttää tätä reittiä, koska sillä on sekä `USER` että `ADMIN` -roolit. Käyttäjätilillä on vain `USER` -rooli, joten yrittäminen päästä tälle reitille ohjataan heidät pääsykielto-sivulle.

:::tip Turvallisuusannotaatiot
Katso [Turvallisuusannotaatiot opas](/docs/security/annotations) kaikista saatavilla olevista annotaatioista.
:::

## Lisää kirjautumisominaisuus {#add-logout-capability}

Käytä `SpringSecurityFormSubmitter`:ia luodaksesi kirjautumispainikkeen:

```java
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.IconButton;
import com.webforj.spring.security.SpringSecurityFormSubmitter;

IconButton logout = new IconButton(FeatherIcon.LOG_OUT.create());
logout.onClick(e -> SpringSecurityFormSubmitter.logout("/logout").submit());
```

Kun tätä painiketta klikataan, se lähettää lomakkeen Spring Securityn `/logout`-pisteeseen, joka tyhjentää käyttäjän istunnon ja ohjaa takaisin kirjautumissivulle kirjautumisen onnistumisviestin kanssa.

## Kuinka se toimii yhdessä {#how-it-works-together}

Kun Spring Boot käynnistää sovelluksesi:

1. **Automaattinen konfigurointi tunnistaa** sekä `webforj-spring-boot-starter` että `spring-boot-starter-security` -riippuvuudet
2. **Turvahallinta luodaan** automaattisesti yhdistämään webforJ-reititys ja Spring Securityn todennus
3. **Turvallisuusarvioijat rekisteröidään** hallitsemaan `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` ja `@DenyAll` -annotaatioita
4. **Reitin tarkkailija liitetään** kaappaamaan navigointi ja arvioimaan turvallisuus sääntöjä ennen komponenttien renderöintiä

Et yhdistä näitä komponentteja manuaalisesti - Spring Bootin automaattinen konfigurointi hoitaa integraation. Määrittelet vain `SecurityConfig`-luokkasi käyttäjien hallintaan ja sivupaikkoihin.

Kun käyttäjä navigoi:

1. Turvallisuusobserver kaappaa navigoinnin
2. Arvioijat tarkistavat reitin turvallisuusannotaatiot
3. Spring Securityn `SecurityContextHolder` antaa todennus tiedot
4. Jos käyttöoikeus myönnetään, komponentti renderöidään; muuten käyttäjä ohjataan
