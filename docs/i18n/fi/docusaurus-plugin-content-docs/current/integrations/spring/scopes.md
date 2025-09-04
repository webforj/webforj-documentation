---
title: Scopes
sidebar_position: 16
sidebar_class_name: new-content
_i18n_hash: 79be5bc5e8982111f185b0f474a1f746
---
# Scope <DocChip chip='since' label='25.03' />

Spring hallitsee beanien elinkaarta scopejen kautta. Jokainen scope määrittelee, milloin bean luodaan, kuinka kauan se elää ja milloin se tuhoutuu. Standardien Spring scopesien ohella webforJ lisää kolme mukautettua scopea: `@WebforjSessionScope`, `@EnvironmentScope` ja `@RouteScope`.

:::tip[Lisätietoja Spring-scopeista]
Kattavasta kuvasta Springin scope-mekanismista ja standardiscopeista katso [Springin bean-scope-dokumentaatio](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Yleiskatsaus

webforJ tarjoaa kolme mukautettua scopea, jotka on suunniteltu web-sovellusten tilanhallintaan:

- **`@WebforjSessionScope`**: Beans, jotka jaetaan kaikkien saman käyttäjän selaintabien/ikkunoiden kesken. Ihanteellinen todennukseen, käyttäjäasetuksiin ja ostoskoreihin.
- **`@EnvironmentScope`**: Beans, jotka on eristetty yhteen selaintablettiin/ikkunaan. Erinomainen välilehtikohtaisiin työnkulkuihin, lomaketietoihin ja itsenäiseen asiakirjan muokkaamiseen.
- **`@RouteScope`**: Beans, jotka jaetaan reitti-hierarkiassa. Hyödyllinen navigointitilan ja datan hallintaan, joka palautuu käyttäjien liikkuessa sovelluksen eri osien välillä.

[![webforJ spring scopes](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## Istunt scope {#session-scope}

`@WebforjSessionScope`-annotaatio luo beans, jotka säilyvät koko webforJ-istunnon ajan. Ero [environment scopeen](#environment-scope), joka eristää beansit jokaiselle selainten ikkunalle/tabille, istuntokohtaiset beans jaetaan kaikkien samasta selaimesta avattujen ikkunoiden ja välilehtien kesken. Nämä beans elävät niin kauan kuin webforJ-istunto pysyy aktiivisena, tyypillisesti siihen asti, kun käyttäjä kirjautuu ulos tai istunto vanhenee.

Istunt scope on ihanteellinen todennustilan, käyttäjäasetusten ja ostoskorejen hallintaan. Tiedot, jotka tulee säilyttää useiden selaintabien välillä, mutta pysyä eristyksissä eri käyttäjien välillä. Jokaisen käyttäjän selaimistuntiin kuuluu oma istuntokohtainen beans-instanssi.

:::info Beansit tarvitsevat olla Serializable
Istuntokohtaiset beansit tulee toteuttaa `Serializable`, koska ne tallennetaan HTTP-istuntomuuttujissa. Kaikkien ei-siirrettävien kenttien tulee myös olla serialisoitavia (perusmuodot, `String` tai luokat, jotka toteuttavat `Serializable`). Merkitse kentät `transient`-näin, jos niitä ei tule säilyttää.
:::

Lisää `@WebforjSessionScope` mihin tahansa Spring-komponenttiin:

```java title="AuthenticationService.java" {2}
@Service
@WebforjSessionScope
public class AuthenticationService {
  private User authenticatedUser;
  private Instant loginTime;

  public void login(String username, String password) {
    // Vahvista käyttäjä
    authenticatedUser = authenticate(username, password);
    loginTime = Instant.now();
  }

  public void logout() {
    authenticatedUser = null;
    loginTime = null;
    // Kumoa istunto
  }

  public boolean isAuthenticated() {
    return authenticatedUser != null;
  }

  public User getCurrentUser() {
    return authenticatedUser;
  }
}
```

### Istunnon jakaminen iskuilla {#session-sharing-across-tabs}

Istuntokohtaiset beansit säilyttävät tilan kaikkien selainten ikkunoiden ja tabien välillä. Avaamalla sovelluksen useassa tabissa jaetaan sama beans-instanssi:

```java
@Route
public class LoginView extends Composite<Div> {

  public LoginView(AuthenticationService authService) {
    if (authService.isAuthenticated()) {
      // Käyttäjä on jo kirjautunut sisään toisessa tabissa
      Router.getCurrent().navigate("/dashboard");
      return;
    }

    Button loginButton = new Button("Kirjaudu");
    loginButton.onClick(e -> {
      authService.login(username, password);
      // Käyttäjä on nyt kirjautunut sisään kaikissa tabissa
    });
  }
}

@Route
public class DashboardView extends Composite<Div> {

  public DashboardView(AuthenticationService authService) {
    // Sama AuthenticationService-instanssi kaikissa tabissa
    User user = authService.getCurrentUser();
    if (user == null) {
      Router.getCurrent().navigate("/login");
      return;
    }

    // Näytä käyttäjän hallintapaneeli
  }
}
```

Kun käyttäjä kirjautuu sisään yhdellä tabilla, kaikilla muilla tabilla on heti pääsy todennettuun tilaan. Uuden tabin tai ikkunan avaaminen säilyttää kirjautumistilan. Kirjautuminen ulos mistä tahansa tabista vaikuttaa kaikkiin tabiin, koska ne jakavat saman istuntokohtaisen beanin.

## Ympäristön scope {#environment-scope}

`@EnvironmentScope`-annotaatio luo beans, jotka elävät selaimen ikkunan tai tabin istuntokäytön ajan. Kun käyttäjä avaa sovelluksen selaimen ikkunassa tai tabissa, webforJ luo ympäristön. Mikä tahansa bean, joka on merkitty `@EnvironmentScope`, luodaan kerran per selaimen ikkuna/tab ja pysyy käytettävissä, kunnes käyttäjä sulkee tabin tai istunto vanhenee.

Jokainen ympäristö edustaa eristettyä selaimen ikkunaa tai tabia. Ympäristön scopeen liittyvät beansit eivät voi jakaa eri selaimen ikkunoiden tai tabien välillä, koska jokainen ikkuna/tab saa oman instanssinsä.

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

`TabWorkspace`-bean ylläpitää tilaa selaimen ikkunan tai tabin elinkaaren ajan. Jokainen selaimen ikkuna/tab saa eristetyn instanssin.

### Ympäristön scopeen liittyvien beansien käyttäminen {#using-environment-scoped-beans}

Reitit saavat ympäristön scopeen liittyvät beansit konstruktorin injektoinnin kautta:

```java
@Route
public class EditorView extends Composite<Div> {

  public EditorView(TabWorkspace workspace) {
    String documentId = workspace.getDocumentId();
    // Lataa asiakirja tälle tabille
    if (documentId == null) {
      // Luo uusi asiakirja
      workspace.setDocumentId(generateDocumentId());
    }
  }
}

@Route
public class PreviewView extends Composite<Div> {

  public PreviewView(TabWorkspace workspace) {
    // Sama TabWorkspace-instanssi kuin EditorView tälle tabille
    workspace.setWorkspaceData("lastView", "preview");
    String documentId = workspace.getDocumentId();
    // Esikatselu asiakirjasta, jota muokataan tälle tabille
  }
}
```

Spring injektoi saman `TabWorkspace`-instanssin molemmille näkymille samassa selaimen ikkunassa/tabissa. Navigointi editorin ja esikatselun välillä säilyttää työtilan instanssin. Jos käyttäjä avaa sovelluksen uudessa selaimen ikkunassa tai tabissa, se ikkuna saa oman erillisen `TabWorkspace`-instanssin, mikä mahdollistaa erilaisten asiakirjojen itsenäisen muokkaamisen.

## Reitti scope {#route-scope}

`@RouteScope`-annotaatio luo beans, jotka jaetaan reitti-hierarkiassa. Navigointi `/admin/users` rakentaa komponenttihierarkian, jossa hallintanäkymä on vanhempi ja käyttäjänäkymä on lapsi. Reitti-scope-Beans luodaan kerran hierarkian mukaan ja jaetaan vanhempien ja lasten komponenttien kesken.

Reitti scope eroaa ympäristön scopeista granulaariudeltaan. Kun ympäristön scopeen liittyvät beansit ovat käytössä koko selaimen ikkunan/tabin istuntokäytössä, reitti-scope-beansit ovat käytössä vain, kun käyttäjä pysyy tietyssä reitti-hierarkiassa. Navigointi hierarkian ulkopuolelle tuhoaa beansit, ja palaaminen luo uusia instansseja. Tämä scope on ihanteellinen tilan hallintaan, joka tulee palauttaa, kun käyttäjät liikkuvat sovelluksen eri osien välillä.

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

### Reitti-hierarkiat ja jakaminen {#route-hierarchies-and-sharing}

Reitit muodostavat hierarkioita `outlet`-parametrin kautta. Vanha reitti tarjoaa outletin, johon lapsireitit renderöidään. Kun määrität reitin outletilla, webforJ rakentaa komponenttipuun, jossa outlet-komponentti tulee vanhemmaksi ja reitti-komponentti lapsikeskellä. Tämä vanhempi-lapsisuhde määrää, mitkä komponentit jakavat reitti-scope-beansit.

```java {11}
@Route
public class AdminView extends Composite<Div> {

  public AdminView(NavigationState navState) {
    navState.addBreadcrumb("Koti");
    navState.addBreadcrumb("Hallinta");
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

`AdminView` ja `UsersView` jakavat saman `NavigationState`-instanssin. Asennus määrittelee navigointirakenteen, kun taas näkymä päivittää aktiivisen tilan. Navigointi `admin`-osion ulkopuolelle (esimerkiksi `/public`) tuhoaa nykyisen `NavigationState`-instanssin ja luo uuden seuraavalle hierarkialle.

Scope-raja seuraa reitti-puustruktuuria. Kaikki komponentit hierarkian juuresta alas lehtiin jakavat saman reitti-scope-bean-instanssin. Navigointi sisar-reiteille saman hierarkian sisällä säilyttää beansit, kun taas navigointi täysin etäisille hierarkioille laukaisee beanin tuhoamisen ja uudelleenluomisen.

### Scope-rajojen mukauttaminen `@SharedFrom`:llä {#customizing-scope-boundaries}

Reitti-scope-beansit jaetaan oletusarvoisesti ylimmäisestä komponentista. `@SharedFrom`-annotaatio määrittää vaihtoehtoisen juurikomponentin. Tämä annotaatio muuttaa, mihin hierarkian kohtaan bean tulee saataville, jolloin voit rajoittaa pääsyä tiettyihin alapuolisiin versioihin reittirakenteessasi:

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

Bean on käytettävissä vain `TeamSection`:ssä ja sen lapsikomponenteissa:

```java
@Route("/")
public class MainView extends Composite<Div> {}

@Route(value = "teams", outlet = MainView.class)
public class TeamSection extends Composite<Div> {

  public TeamSection(TeamContext context) {
    // Bean luotu täällä
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // Ei voi injektoida TeamContextia - se on rajoitettu TeamSectioniin
    // Yritys injektoida heittää IllegalStateExceptionin
  }
}
```

`@SharedFrom`-annotaatio vahvistaa arkkitehtoniset rajat. Komponentit, jotka ovat määritetyn scope rakenneyhteisön ulkopuolella, eivät voi käyttää beania. Kun Spring yrittää injektoida `@SharedFrom`-beanin komponenttiin, joka on sen tarkoitetun hierarkian ulkopuolella, injektio epäonnistuu `IllegalStateException`-virheellä. Tämä toteutus tapahtuu ajon aikana, kun reittiä käytetään, jotta beans pysyvät asianmukaisesti rajattuna haluttuihiin komponenttipuihin.

Annotaatio hyväksyy yhden parametrin - komponenttiluokan, joka tulee toimia juurena jakamista varten. Vain tämä komponentti ja sen jälkeläiset reittihierarkiassa voivat käyttää beania. Vanhempia komponentteja ja sisarhierarkioita ei voi injektoida sitä.
