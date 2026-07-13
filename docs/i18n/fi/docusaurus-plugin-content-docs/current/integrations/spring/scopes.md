---
title: Scopes
sidebar_position: 16
description: >-
  Use WebforjSessionScope, EnvironmentScope, and RouteScope to control bean
  lifetimes across sessions, browser tabs, and route hierarchies.
_i18n_hash: ea33564c3dec0bc26426440f3b448c53
---
# Skopit <DocChip chip='since' label='25.03' />

Spring hallitsee beanin elinkaaren skooppejen kautta. Jokainen skooppi määrittelee, milloin bean luodaan, kuinka kauan se elää ja milloin se tuhotaan. Vakiokoppien lisäksi webforJ lisää kolme mukautettua skooppi: `@WebforjSessionScope`, `@EnvironmentScope` ja `@RouteScope`.

:::tip[Lisätietoja Springin skopeista]
Kattava kuvaus Springin skooppausmekanismista ja vakiokopoista löytyy [Springin beanin skooppi-dokumentaatiosta](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Yhteenveto {#overview}

webforJ tarjoaa kolme mukautettua skooppi, jotka on suunniteltu verkkosovellusten tilanhallintaan:

- **`@WebforjSessionScope`**: Beans, jotka jaetaan kaikissa selainvälilehdissä/ikkunoissa saman käyttäjäistunnon aikana. Erinomainen autentikointia, käyttäjäasetuksia ja ostoskoreja varten.
- **`@EnvironmentScope`**: Beans, jotka on eristetty yhteen selainvälilehteen/ikkunaan. Ihanteellinen välilehtikohtaisille työnkuluissa, lomakedatoille ja itsenäiselle asiakirjojen muokkaukselle.
- **`@RouteScope`**: Beans, jotka jaetaan reitinhierarkian sisällä. Hyödyllinen navigointitilan ja datan osalta, joka tulisi nollata, kun käyttäjät navigoivat sovelluksen osien välillä.

[![webforJ spring scopes](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## Istuntoskoppi {#session-scope}

`@WebforjSessionScope` -annotaatio luo beans, jotka pysyvät aktiivisina koko webforJ-istunnon ajan. Toisin kuin [ympäristökoppi](#environment-scope), joka eristää beansin per selainikkuna/välilehti, istuntoon liittyvät beans jaetaan kaikissa saman selaimen ikkunoissa ja välilehdissä. Nämä beans elävät niin kauan kuin webforJ-istunto pysyy aktiivisena, yleensä kunnes käyttäjä kirjautuu ulos tai istunto vanhenee.

Istuntoskoppi on ihanteellinen autentikointitilan, käyttäjäasetusten, ostoskorejen ja datan osalta, joka tulisi säilyttää useiden selainvälilehtien välillä, mutta säilyttää eristyksessä eri käyttäjien välillä. Jokaisen käyttäjän selainistunto saa oman instanssinsa istuntoskopioon liittyvistä beansista.

:::info Beansit tarvitsevat olla Serializable
Istuntokoppiin liittyvien beansien on toteutettava `Serializable`, koska ne tallennetaan HTTP-istuntoasetuksiin. Kaikkien ei-tilapäisten kenttien on myös oltava sarjallistettavissa (primitivi, `String` tai `Serializable`-rajapinnan toteuttavat luokat). Merkitse kentät `tilapäisiksi`, jos niitä ei tulisi säilyttää.
:::

Lisää `@WebforjSessionScope` mihin tahansa Spring-komponenttiin:

```java title="AuthenticationService.java" {2}
@Service
@WebforjSessionScope
public class AuthenticationService {
  private User authenticatedUser;
  private Instant loginTime;

  public void login(String username, String password) {
    // Käyttäjän todennus
    authenticatedUser = authenticate(username, password);
    loginTime = Instant.now();
  }

  public void logout() {
    authenticatedUser = null;
    loginTime = null;
    // Käyttöoikeuden kumoaminen
  }

  public boolean isAuthenticated() {
    return authenticatedUser != null;
  }

  public User getCurrentUser() {
    return authenticatedUser;
  }
}
```

### Istunnon jakaminen välilehtien välillä {#session-sharing-across-tabs}

Istuntokoppiin liittyvät beansit ylläpitävät tilaa kaikissa selainikkunoissa ja -välilehdissä. Sovelluksen avaaminen useissa välilehdissä jakaa saman beansin instanssin:

```java
@Route
public class LoginView extends Composite<Div> {

  public LoginView(AuthenticationService authService) {
    if (authService.isAuthenticated()) {
      // Käyttäjä on jo kirjautunut sisään toisesta välilehdestä
      Router.getCurrent().navigate("/dashboard");
      return;
    }

    Button loginButton = new Button("Kirjaudu sisään");
    loginButton.onClick(e -> {
      authService.login(username, password);
      // Käyttäjä on nyt kirjautunut sisään kaikissa välilehdissä
    });
  }
}

@Route
public class DashboardView extends Composite<Div> {

  public DashboardView(AuthenticationService authService) {
    // Sama AuthenticationService-instanssi kaikissa välilehdissä
    User user = authService.getCurrentUser();
    if (user == null) {
      Router.getCurrent().navigate("/login");
      return;
    }

    // Näytä käyttäjän hallinta
  }
}
```

Kun käyttäjä kirjautuu sisään yhdessä välilehdessä, kaikki muut välilehdet saavat heti pääsyn todennettuun tilaan. Uusien välilehtien tai ikkunoiden avaaminen ylläpitää kirjautunutta tilaa. Kirjautuminen ulos mistä tahansavälilehdestä vaikuttaa kaikkiin välilehtiin, koska ne jakavat saman istuntoskoppiin liittyvän beansin.

## Ympäristökoppi {#environment-scope}

`@EnvironmentScope` -annotaatio luo beansit, jotka elävät yhden selainikkunan tai -välilehden istunnon ajan. Kun käyttäjä avaa sovelluksen selainikkunassa tai -välilehdessä, webforJ luo ympäristön. Kaikki `@EnvironmentScope` -annotaatiolla merkitty bean luodaan kerran per selainikkuna/välilehti ja pysyy saatavilla, kunnes käyttäjä sulkee välilehden tai istunto vanhenee.

Jokainen ympäristö edustaa eristettyä selainikkunaa tai -välilehteä. Ympäristökoppia ei voida jakaa eri selainikkunoiden tai -välilehtien välillä, koska jokainen ikkuna/välilehti saa oman instanssinsa.

Lisää `@EnvironmentScope` mihin tahansa Spring-komponenttiin:

```java title="TabWorkspace.java" {2}
@Component
@EnvironmentScope
public class TabWorkspace {
  private String documentId;
  private Map<String, Object> workspaceData = new HashMap<>();

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public String getDocumentId() {
    return documentId;
  }

  public void setWorkspaceData(String key, Object value) {
    workspaceData.put(key, value);
  }

  public Object getWorkspaceData(String key) {
    return workspaceData.get(key);
  }
}
```

`TabWorkspace` -beans ylläpitää tilaa koko selainikkunan tai -välilehden elinajan. Jokainen selainikkuna/välilehti saa eristetyn instanssin.

### Ympäristökoppien käyttäminen {#using-environment-scoped-beans}

Reitit vastaanottavat ympäristökopiaan liittyvät beansit konstruktorin injektion kautta:

```java
@Route
public class EditorView extends Composite<Div> {

  public EditorView(TabWorkspace workspace) {
    String documentId = workspace.getDocumentId();
    // Lataa asiakirja tälle välilehdelle
    if (documentId == null) {
      // Luo uusi asiakirja
      workspace.setDocumentId(generateDocumentId());
    }
  }
}

@Route
public class PreviewView extends Composite<Div> {

  public PreviewView(TabWorkspace workspace) {
    // Sama TabWorkspace-instanssi kuin EditorView tässä välilehdessä
    workspace.setWorkspaceData("lastView", "preview");
    String documentId = workspace.getDocumentId();
    // Esikatso muokattavaa asiakirjaa tässä välilehdessä
  }
}
```

Spring injektoi saman `TabWorkspace` -instanssin molempiin näkymiin samassa selainikkunassa/välilehdessä. Navigointi editorin ja esikatselun välillä säilyttää työtilan instanssin. Jos käyttäjä avaa sovelluksen uudessa selainikkunassa tai -välilehdessä, tuo ikkuna saa oman erillisen `TabWorkspace` -instanssin, mikä mahdollistaa erilaisten asiakirjojen itsenäisen muokkauksen.

## Reitinskoppi {#route-scope}

`@RouteScope` -annotaatio luo beansit, jotka jaetaan reitinhierarkian sisällä. Navigointi `/admin/users` rakentaa komponenttihierarkian, jossa admin-näkymä on vanhempi ja käyttäjien näkymä on lapsi. Reitinskooppiset beans instansioidaan kerran per hierarkia ja jaetaan vanhempien ja lapsikomponenttien välillä.

Reitinskooppit eroaa ympäristökopista hienosyvyyteensä. Vaikka ympäristöskopeissa olevat beansit elävät koko selainikkunan/välilehden istunnon ajan, reitinskooppiset beansit elävät vain niin kauan kuin käyttäjä on tietyssä reitinhierarkiassa. Navigointi hierarkian ulkopuolelle tuhoaa beansit ja palatessa luodaan uudet instanssit. Tämä skooppi on ihanteellinen tilalle, joka tulisi nollata, kun käyttäjät navigoivat sovelluksen eri osiin.

Lisää `@RouteScope` mihin tahansa Spring-komponenttiin:

```java title="NavigationState" {2}
@Component
@RouteScope
public class NavigationState {
  private String activeTab;
  private List<String> breadcrumbs = new ArrayList<>();

  public void setActiveTab(String tab) {
    this.activeTab = tab;
  }

  public void addBreadcrumb(String crumb) {
    breadcrumbs.add(crumb);
  }

  public List<String> getBreadcrumbs() {
    return Collections.unmodifiableList(breadcrumbs);
  }
}
```

### Reitinhierarkiat ja jakaminen {#route-hierarchies-and-sharing}

Reitit muodostavat hierarkioita `outlet`-parametrin kautta. Vanhempi reitti tarjoaa ulosmenon, johon lapsireitit renderöidään. Kun määrität reitin ulosmenolla, webforJ rakentaa komponenttipuun, jossa ulosmenokomponentti muuttuu vanhemmaksi ja reittikomponentti muuttuu lapseksi. Tämä vanhemman ja lapsen suhde määrää, mitkä komponentit jakavat reitinskooppiset beansit.

```java {11}
@Route
public class AdminView extends Composite<Div> {

  public AdminView(NavigationState navState) {
    navState.addBreadcrumb("Koti");
    navState.addBreadcrumb("Admin");
    // ...
  }
}

@Route(value = "users", outlet = AdminView.class)
public class UsersView extends Composite<Div> {

  public UsersView(NavigationState navState) {
    // Sama NavigationState-instanssi kuin AdminView
    navState.setActiveTab("users");
    navState.addBreadcrumb("Käyttäjät");
  }
}
```

`AdminView` ja `UsersView` jakavat saman `NavigationState` -instanssin. Asennus määrittää navigointirakenteen, kun taas näkymä päivittää aktiivisen tilan. Navigointi ulkopuolelle `admin`-osiosta (esimerkiksi `/public`) tuhoaa nykyisen `NavigationState` -instanssin ja luo uuden seuraavalle hierarkialle.

Skoopin raja seuraa reittipuun rakennetta. Kaikki komponentit hierarkian juuresta lehtiin jakavat samat reitinskooppiset beaninstanssit. Navigointi sisar-reitteihin samalla hierarkialla säilyttää beansit, kun taas navigointi ei-liittyviin hierarkioihin käynnistää beansin tuhoamisen ja uudelleenluomisen.

### Skoopin rajojen mukauttaminen `@SharedFrom` -annotaatiolla {#customizing-scope-boundaries}

Reitinskooppiset beansit jaetaan oletuksena ylimmästä komponentista. `@SharedFrom` -annotaatio määrittelee vaihtoehtoisen juurikomponentin. Tämä annotaatio muuttaa sen hierarkiassa, mistä bean tulee saataville, ja mahdollistaa pääsyn rajoittamisen reittirakenteesi tiettyihin alipuoliskoihin:

```java title="TeamContext" {2,3}
@Component
@RouteScope
@SharedFrom(TeamSection.class)
public class TeamContext {
  private String teamId;
  private List<String> permissions = new ArrayList<>();

  public void setTeamId(String id) {
    this.teamId = id;
  }

  public String getTeamId() {
    return teamId;
  }
}
```

Bean on saatavilla yksinomaan `TeamSection` ja sen lapsikomponenteille:

```java
@Route("/")
public class MainView extends Composite<Div> {}

@Route(value = "teams", outlet = MainView.class)
public class TeamSection extends Composite<Div> {

  public TeamSection(TeamContext context) {
    // Bean luodaan täällä
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // Ei voi injektoida TeamContext - se on sakoitettu TeamSection
    // Yrittäminen injektoida heittää IllegalStateException
  }
}
```

`@SharedFrom` -annotaatio valvoo arkkitehtonisia rajoja. Komponentit, jotka sijaitsevat määritetyn skoopin ulkopuolella, eivät voi käyttää beania. Kun Spring yrittää injektoida `@SharedFrom` -beanin komponenttiin, joka on sen määritetyn hierarkian ulkopuolella, injektio epäonnistuu `IllegalStateException` -virheellä. Tämä valvonta tapahtuu ajonaikana, kun reittiä käytetään, joten beans pysyvät oikein skoopattuina tarkoitettuihin komponenttipuihin.

Annotaatio hyväksyy yhden parametrin: komponenttityypin, joka tulisi toimia jakelupisteenä. Vain tämä komponentti ja sen jälkeläiset reittihierarkiassa voivat käyttää beania. Vanhemmat komponentit ja sisarhierarkiat eivät voi injektoida sitä.
