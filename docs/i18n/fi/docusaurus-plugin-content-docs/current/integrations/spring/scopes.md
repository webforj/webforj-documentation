---
title: Scopes
sidebar_position: 16
sidebar_class_name: new-content
_i18n_hash: 8c977fdef41f6125ac21239e7e397f4d
---
# Skaalat <DocChip chip='since' label='25.03' />

Spring hallitsee beanin elinkaaren skaalausten kautta. Kukin skaalaus määrittää, milloin bean luodaan, kuinka kauan se elää ja milloin se tuhoutuu. Perinteisten Spring-skaalausten lisäksi webforJ lisää kolme mukautettua skaalausta: `@WebforjSessionScope`, `@EnvironmentScope` ja `@RouteScope`.

:::tip[Lisätietoja Spring-skaaloista]
Kattavan käsittelyn Springin skaalausmekanismista ja standardiskaaloista löydät [Springin bean skaalausasiakirjasta](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Yleiskatsaus

webforJ tarjoaa kolme mukautettua skaalausta, jotka on suunniteltu verkkosovelluksen tilanhallintaan:

- **`@WebforjSessionScope`**: Beanit, jotka jaetaan kaikkien selaimen välilehtien / ikkunoiden kesken saman käyttäjäistunnon aikana. Erinomainen autentikointiin, käyttäjäasetuksiin ja ostoskärryihin.
- **`@EnvironmentScope`**: Beanit, jotka on eristetty yhteen selaimen välilehteen / ikkunaan. Ihanteellinen välilehti- tai työlaflujaisiin työnkulkuun, lomaketietoihin ja itsenäiseen asiakirjan muokkaamiseen.
- **`@RouteScope`**: Beanit, jotka jaetaan reittihierarkiassa. Hyödyllinen navigointitilan ja datan hallintaan, joka tulisi nollata kun käyttäjät navigoivat sovelluksen eri osien välillä.

[![webforJ spring scopes](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## Istuntotila {#session-scope}

`@WebforjSessionScope` -annotaatio luo beaneja, jotka säilyvät koko webforJ-istunnon ajan. Toisin kuin [ympäristöskaalaus](#environment-scope), joka eristää beanit selaimen ikkunoiden / välilehtien mukaan, istuntotilan beanit jaetaan kaikissa ikkunoissa ja välilehdissä saman selaimen osalta. Nämä beanit elävät niin kauan kuin webforJ-istunto on aktiivinen, tyypillisesti siihen asti kun käyttäjä kirjautuu ulos tai istunto vanhentuu.

Istuntotila on ihanteellinen autentikaatiosstateeseen, käyttäjäasetuksiin, ostoskärryihin ja dataan, joka tulisi säilyttää useiden selaimen välilehtien yli, mutta pysyä eristyksissä eri käyttäjien välillä. Jokaisen käyttäjän selaimistuntosi saa oman instanssinsa istuntotilan beaneista.

:::info Beanien on oltava Serializable
Istuntotilaan liittyvien beanien on toteutettava `Serializable`, koska ne tallennetaan HTTP-istunnon attribuutteihin. Kaikkien ei-siirtyvien kenttien on myös oltava sarjoitettavia (primitivit, `String` tai luokat, jotka toteuttavat `Serializable`). Merkitse kentät `transient`, jos niitä ei pitäisi säilyttää.
:::

Lisää `@WebforjSessionScope` mihin tahansa Spring-komponenttiin:

```java title="AuthenticationService.java" {2}
@Service
@WebforjSessionScope
public class AuthenticationService {
  private User authenticatedUser;
  private Instant loginTime;

  public void login(String username, String password) {
    // Autentikoi käyttäjä
    authenticatedUser = authenticate(username, password);
    loginTime = Instant.now();
  }

  public void logout() {
    authenticatedUser = null;
    loginTime = null;
    // Virallista istunto
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

Istuntotilan beanit ylläpitävät tilaa kaikissa selaimen ikkunoissa ja välilehdissä. Sovelluksen avaaminen useissa välilehdissä jakaa saman bean-instanssin:

```java
@Route
public class LoginView extends Composite<Div> {

  public LoginView(AuthenticationService authService) {
    if (authService.isAuthenticated()) {
      // Käyttäjä on jo kirjautunut sisään toisessa välilehdessä
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

    // Näytä käyttäjän hallintapaneeli
  }
}
```

Kun käyttäjä kirjautuu sisään yhden välilehden kautta, kaikki muut välilehdet saavat heti pääsyn autentikoituneeseen tilaan. Uuden välilehden tai ikkunan avaaminen säilyttää kirjautumistilan. Kirjautuminen ulos mistä tahansa välilehdestä vaikuttaa kaikkiin välilehtiin, koska ne jakavat saman istuntotilan beanin.

## Ympäristöskaalaus {#environment-scope}

`@EnvironmentScope` -annotaatio luo beaneja, jotka elävät selaimen ikkuna- tai välilehtisession keston ajan. Kun käyttäjä avaa sovelluksen selaimessa, webforJ luo ympäristön. Kaikki beanit, jotka on merkitty `@EnvironmentScope` -annotaatiolla, luodaan kerran jokaiselle selaimen ikkunalle/välilehdelle ja pysyvät saatavilla, kunnes käyttäjä sulkee välilehden tai istunto vanhenee.

Jokainen ympäristö edustaa eristettyä selaimen ikkunaa tai välilehteä. Ympäristöskaalaisia beaneja ei voida jakaa eri selaimen ikkunoiden tai välilehtien kesken, koska jokainen ikkuna/välilehti saa oman instanssinsa.

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

`TabWorkspace` -bean ylläpitää tilaa koko selaimen ikkunan tai välilehden eliniän ajan. Jokainen selaimen ikkuna/välilehti saa eristetyn instanssin.

### Ympäristöskaalisten beanien käyttäminen {#using-environment-scoped-beans}

Reitit saavat ympäristöskaaliset beanit konstruktorin injektoinnin kautta:

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
    // Sama TabWorkspace-instanssi kuin EditorView tätä välilehteä
    workspace.setWorkspaceData("lastView", "preview");
    String documentId = workspace.getDocumentId();
    // Esikatsele asiakirjaa, jota muokataan täällä
  }
}
```

Spring injektoi saman `TabWorkspace` -instanssin molempiin näkymiin saman selaimen ikkunan/välilehden aikana. Navigointi editorin ja esikatselun välillä säilyttää työtilan instanssin. Jos käyttäjä avaa sovelluksen uudessa selaimen ikkunassa tai välilehdessä, se ikkuna saa oman erillisen `TabWorkspace` -instanssinsa, jolloin eri asiakirjojen itsenäinen muokkaaminen on mahdollista.

## Reittitila {#route-scope}

`@RouteScope` -annotaatio luo beaneja, jotka jaetaan reittihierarkiassa. Navigointi `/admin/users` rakentaa komponenttihierarkian, jossa hallintanäkymä on vanhempi ja käyttäjänäkymä on lapsi. Reittitilassa olevat beanit instansioidaan kerran hierarkiassa ja jaetaan vanhemman ja lapsikomponentin kesken.

Reittitila eroaa ympäristöskaalasta hienovaraisuudessaan. Kun ympäristöskaaliset beanit elävät koko selaimen ikkunan/välilehden session ajan, reittitilassa olevat beanit elävät vain niin kauan kuin käyttäjä pysyy tietyssä reittihierarkiassa. Navigointi hierarkian ulkopuolelle tuhoaa beanit ja palaaminen luo uusia instansseja. Tämä skaalaus on ihanteellinen tila, joka tulisi nollata, kun käyttäjät navigoivat sovelluksen eri osien välillä.

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

### Reittihierarkiat ja jakaminen {#route-hierarchies-and-sharing}

Reitit muodostavat hierarkioita `outlet`-parametrin kautta. Vanhempi reitti tarjoaa outletin, johon lapsireitit renderöidään. Kun määrität reitin outletilla, webforJ rakentaa komponenttipuun, jossa outlet-komponentti toimii vanhempana ja reittikomponentti lapsena. Tämä vanhempi-lapsi-suhde määrittää, mitkä komponentit jakavat reittitilan beanit.

```java {11}
@Route
public class AdminView extends Composite<Div> {

  public AdminView(NavigationState navState) {
    navState.addBreadcrumb("Etusivu");
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

`AdminView` ja `UsersView` jakavat saman `NavigationState` -instanssin. Ulkoasu määrittelee navigointirakenteen, kun taas näkymä päivittää aktiivisen tilan. Navigointi ulkopuolelle `admin` osioon (esim. `/public`) tuhoaa nykyisen `NavigationState` -instanssin ja luo uuden seuraavaan hierarkiaan.

Skaalausraja seuraa reittipuun rakennetta. Kaikki komponentit hierarkian juuresta lehtiin jakavat samat reittitilan beanin instanssit. Navigointi sisarreisilla samassa hierarkiassa säilyttää beanit, kun taas navigointi eristyksissä hierarkioissa laukaisee beanin tuhoamisen ja uudelleenluonnin.

### Skaalaparametrien mukauttaminen `@SharedFrom` avulla {#customizing-scope-boundaries}

Reittitilan beanit jaetaan ylhäältä alaspäin oletusarvoisesti. `@SharedFrom` -annotaatio määrittää vaihtoehtoisen juurikomponentin. Tämä annotaatio muuttaa sitä, missä hierarkiassa bean tulee saataville, jolloin voit rajoittaa pääsyä tiettyihin alityypteihin reittirakenteessasi:

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

Bean on käytettävissä vain `TeamSection` ja sen lapsikomponenteissa:

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
    // Ei voi injektoida TeamContextia - se on rajattu TeamSectioniin
    // Yritetään injektointi heittää IllegalStateException
  }
}
```

`@SharedFrom` -annotaatio vahvistaa arkkitehtuurirajoja. Komponentit, jotka ovat määrittelemättömän skaalan ulkopuolella, eivät voi käyttää beania. Kun Spring yrittää injektoida `@SharedFrom` -beanin komponenttiin, joka on sen määritellyn hierarkian ulkopuolella, injektointi epäonnistuu `IllegalStateException` -virheellä. Tämä pakottaminen tapahtuu ajonaikaisesti, kun reittiä käytetään, joten beanit pysyvät asianmukaisesti rajattuina niiden tarkoitetuille komponenttipuille. 

Annotaatio hyväksyy yhden parametrin: komponenttiluokan, joka tulisi toimia jakamisen juurena. Vain tämä komponentti ja sen jälkeläiset reittihierarkiassa voivat käyttää beania. Vanhemmat komponentit ja sisarhierarkiat eivät voi injektoida sitä.
