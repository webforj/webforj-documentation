---
title: Scopes
sidebar_position: 16
_i18n_hash: 96d36a28887e551ba3f9acab0d7059bc
---
<!-- vale off -->
# Scopet <DocChip chip='since' label='25.03' />
<!-- vale on -->

Spring hallitsee beanin elinkaaren kautta scopeja. Jokainen scope määrittelee, milloin bean luodaan, kuinka kauan se elää ja milloin se tuhotaan. webforJ lisää kaksi mukautettua scopea - `@EnvironmentScope` ja `@RouteScope` - jotka vastaavat sitä, miten webforJ-sovellukset käsittelevät selainistuntoja ja navigointia.

:::tip[Lisätietoja Spring-scopista]
Kattavan käsittelyn Springin scopimekanismista ja vakioscopoista löydät [Springin bean-scopoja käsittelevästä dokumentaatiosta](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Ympäristöscope {#environment-scope}

`@EnvironmentScope`-annotaatio luo beaneja, jotka elävät selainikkunan tai välilehden istunnon ajan. Kun käyttäjä avaa sovelluksen selainikkunassa tai -välilehdessä, webforJ luo ympäristön. Kaikki `@EnvironmentScope`-merkityt beanit luodaan kerran per selainikkuna/välilehti ja pysyvät saatavilla, kunnes käyttäjä sulkee välilehden tai istunto vanhenee.

Jokainen ympäristö edustaa eristettyä selainikkunaa tai -välilehteä. Ympäristöscope-beaneja ei voida jakaa eri selainikkunoiden tai -välilehtien välillä - kukin ikkuna/välilehti saa oman instanssin.

Lisää `@EnvironmentScope` mihin tahansa Spring-komponenttiin:

```java title="UserSession.java" {2}
@Component
@EnvironmentScope
public class UserSession {
  private String userId;
  private Map<String, Object> attributes = new HashMap<>();
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getUserId() {
    return userId;
  }
  
  public void setAttribute(String key, Object value) {
    attributes.put(key, value);
  }
  
  public Object getAttribute(String key) {
    return attributes.get(key);
  }
}
```

`UserSession`-bean ylläpitää tilaa selainikkunan tai -välilehden elinkaaren aikana. Kukin selainikkuna/välilehti saa eristetyn instanssin.

### Ympäristöscope-beanien käyttö {#using-environment-scoped-beans}

Reitit saavat ympäristöscope-beaneja konstruktorisinjektioiden kautta:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  public DashboardView(UserSession session) {
    String userId = session.getUserId();
    // Käytä istuntotietoja
    if (userId == null) {
      // Ohjaa kirjautumissivulle
    }
  }
}

@Route("/profile")
public class ProfileView extends Composite<Div> {
  
  public ProfileView(UserSession session) {
    // Sama UserSession-instanssi kuin DashboardView
    session.setAttribute("lastView", "profile");
  }
}
```

Spring injektoi saman `UserSession`-instanssin molempiin näkymiin samassa selainikkunassa/välilehdessä. Navigointi dashboardin ja profiilin välillä säilyttää istuntokappaleen. Jos käyttäjä avaa sovelluksen uudessa selainikkunassa tai -välilehdessä, tuo ikkuna saa oman erillisen `UserSession`-instanssin.

## Reititescope {#route-scope}

`@RouteScope`-annotaatio luo beaneja, jotka jaetaan reitti-hierarkiassa. Navigointi `/admin/users` rakentaa komponentti-hierarkian, jossa admin-näkymä on vanhempi ja käyttäjien näkymä lapsi. Reititescope-beanit instansioidaan kerran per hierarkia ja jaetaan vanhempien ja lasten komponenttien välillä.

Reititescope eroaa ympäristöscopeista tarkkuudessaan. Kun taas ympäristöscope-beanit ovat olemassa koko selainikkunan/välilehden istunnon ajan, reititescope-beanit ovat olemassa vain, kun käyttäjä on tietyssä reitti-hierarkiassa. Hierarkian ulkopuolelle navigointi tuhoaa beanit ja paluu luo uusia instansseja. Tämä scope on ihanteellinen tilalle, joka tulisi nollata, kun käyttäjät navigoivat sovelluksen eri osien välillä.

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

Reitit muodostavat hierarkioita `outlet`-parametrin avulla. Vanhempi reitti tarjoaa outletin, jossa lapsireitit renderöidään. Kun määrität reitin outletilla, webforJ rakentaa komponenttipuun, jossa outlet-komponentista tulee vanhempi ja reittikomponentista lapsi. Tämä vanhempi-lapsi-suhde määrittää, mitkä komponentit jakavat reititescope-beanit.

```java
@Route("/admin")
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

`AdminView` ja `UsersView` jakavat saman `NavigationState`-instanssin. Asettelu määrittää navigointirakenteen, kun taas näkymä päivittää aktiivisen tilan. Navigointi `admin`-osion ulkopuolelle (esimerkiksi `/public`) tuhoaa nykyisen `NavigationState`-instanssin ja luo uuden seuraavaa hierarkiaa varten.

Scope-raja seuraa reittipuun rakennetta. Kaikki komponentit hierarkian juuresta lehtiin jakavat samat reititescope-beani-instanssit. Navigointi sisar-reiteille samassa hierarkiassa säilyttää beanit, kun taas navigointi epäolennaisiin hierarkioihin laukaisee beanin tuhoamisen ja uudelleenluomisen.

### Scope-rajojen mukauttaminen `@SharedFrom`-annotaatiolla {#customizing-scope-boundaries}

Reititescope-beaneja jaetaan oletuksena ylimmästä komponentista. `@SharedFrom`-annotaatio määrittää vaihtoehtoiset juurikomponentit. Tämä annotaatio muuttaa, missä hierarkiassa beani tulee saataville, jolloin voit rajoittaa pääsyä kyseisen reittirakenteen tiettyihin alapuolisiin solmuihin:

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

Bean on saatavilla yksinomaan `TeamSection`-komponentissa ja sen lapsikomponenteissa:

```java
@Route("/")
public class MainView extends Composite<Div> {}

@Route(value = "teams", outlet = MainView.class)
public class TeamSection extends Composite<Div> {
  
  public TeamSection(TeamContext context) {
    // Beani luodaan täällä
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // Ei voi injektoida TeamContextia - se on rajattu TeamSectioniin
    // Injektointiyritys heittää IllegalStateExceptionin
  }
}
```

`@SharedFrom`-annotaatio vahvistaa arkkitehtonisia rajoja. Komponentit, jotka ovat ulkopuolella määritellyn scope-alueen, eivät voi käyttää beania. Kun Spring yrittää injektoida `@SharedFrom`-beanin komponenttiin sen määritellyn hierarkian ulkopuolella, injektointi epäonnistuu `IllegalStateExceptionilla`. Tämä vahvistus tapahtuu ajon aikana, kun reittiä käytetään, joten beanit pysyvät oikein rajattuina tarkoitettuihin komponenttipuihin.

Annotaatio ottaa vastaan yhden parametrin - komponenttiluokan, joka pitäisi olla juurena jakamiseen. Vain tämä komponentti ja sen jälkeläiset reitti-hierarkiassa voivat käyttää beania. Vanhemmat komponentit ja sisar-hierarkiat eivät voi injektoida sitä.
