---
sidebar_position: 6
title: Custom Implementation Example
description: >-
  Build a session-based security stack by implementing SecurityConfiguration,
  SecurityContext, SecurityManager, and SecurityRegistrar.
_i18n_hash: 02c468d495da2fc6a00be56e72821d2c
---
Tämä opas käy läpi täydellisen mukautetun turvallisuusratkaisun rakentamisen istuntopohjaista todennusta käyttäen. Opit, kuinka neljä keskeistä rajapintaa toimii yhdessä toteuttamalla ne alusta alkaen.

:::tip[Useimmissa sovelluksissa tulisi käyttää Spring Securityä]
[Spring Security -integraatio](/docs/security/getting-started) konfiguroi automaattisesti kaiken tämän. Rakenna mukautettu turvallisuus vain, jos sinulla on erityisiä vaatimuksia tai et käytä Spring Bootia.
:::

## Mitä rakennat {#what-youll-build}

Toimiva turvallisuusjärjestelmä neljällä luokalla:

- **SecurityConfiguration** - Määrittelee turvallisuuskäyttäytymisen ja uudelleenohjauspaikat
- **SecurityContext** - Seuraa, kuka on kirjautuneena HTTP-istuntojen avulla
- **SecurityManager** - Koordinoi turvallisuustarkastuksia ja tarjoaa kirjautumis-/uloskirjautumisratkaisuja
- **SecurityRegistrar** - Yhdistää kaiken sovelluksen käynnistyksen yhteydessä

Tässä esimerkissä käytetään istuntopohjaista tallennusta, mutta voit toteuttaa samat rajapinnat käyttämällä tietokantakyselyitä, LDAP:ia tai mitä tahansa muuta todennusjärjestelmää.

## Miten osat toimivat yhdessä {#how-the-pieces-work-together}

```mermaid
sequenceDiagram
  box Käynnistysvaihe
  participant Registrar as SecurityRegistrar
  end
  box Suoritusvaihe
  participant Observer as RouteSecurityObserver
  participant Manager as SecurityManager
  participant Evaluators
  participant Context as SecurityContext
  participant Config as SecurityConfiguration
  end

  Note over Registrar: Sovellus käynnistyy
  Registrar->>Manager: Luo
  Registrar->>Evaluators: Rekisteröi
  Registrar->>Observer: Liitä reitittimeen

  Note over Observer,Config: Käyttäjä navigoi reitille
  Observer->>Manager: Pyydä päätöstä
  Manager->>Evaluators: Suorita arvioijat
  Evaluators->>Context: Tarkista käyttäjä
  Evaluators->>Config: Hae uudelleenohjaukset
  Evaluators-->>Manager: Päätös
  Manager-->>Observer: Myönnä tai Hylkää
```

**Virta:**
1. **`SecurityRegistrar`** toimii käynnistyksessä, luo hallitsijan, rekisteröi arvioijat ja liittää tarkkailijan
2. **`SecurityManager`** koordinoi kaiken - se tarjoaa kontekstin ja konfiguraation arvioijille
3. **`SecurityContext`** vastaa kysymykseen "Kuka on kirjautuneena?" lukemalla HTTP-istunnoista
4. **`SecurityConfiguration`** vastaa kysymykseen "Minne ohjata?" kirjautumis- ja käyttöoikeuden hylkäämisen sivuille
5. **`Evaluators`** tekevät käyttöoikeuspäätöksiä käyttäen kontekstia ja konfiguraatiota

## Vaihe 1: Määrittele turvallisuuskonfiguraatio {#step-1-define-security-configuration}

Konfiguraatio kertoo turvallisuusjärjestelmälle, miten toimia ja minne ohjata käyttäjiä:

```java title="SecurityConfiguration.java"
package com.securityplain.security;

import com.webforj.router.history.Location;
import com.webforj.router.security.RouteSecurityConfiguration;
import java.util.Optional;

/**
 * Sovelluksen turvallisuuskonfiguraatio.
 *
 * <p>
 * Määrittelee, minne ohjata käyttäjiä, kun todennusta vaaditaan tai pääsy hylätään.
 * </p>
 */
public class SecurityConfiguration implements RouteSecurityConfiguration {

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean isSecureByDefault() {
    return false;
  }

  @Override
  public Optional<Location> getAuthenticationLocation() {
    return Optional.of(new Location("/login"));
  }

  @Override
  public Optional<Location> getDenyLocation() {
    return Optional.of(new Location("/access-denied"));
  }
}
```

- `isEnabled() = true` - Turvallisuus on aktiivinen
- `isSecureByDefault() = false` - Reitit ovat julkisia, ellei niitä ole merkitty (käytä `true` vaatiaksesi todennusta kaikilta reiteiltä oletuksena)
- `/login` - Minne tunnistautumattomat käyttäjät menevät
- `/access-denied` - Minne todennettujen käyttäjien, joilla ei ole lupia, tulee mennä

## Vaihe 2: Toteuta turvallisuuskonteksti {#step-2-implement-security-context}

Konteksti seuraa, kuka on kirjautuneena. Tämä toteutus käyttää HTTP-istuntoja käyttäjätietojen tallentamiseen:

<!-- vale off -->

<ExpandableCode title="SecurityContext.java" language="java">
{`package com.securityplain.security;

import com.webforj.Environment;
import com.webforj.router.security.RouteSecurityContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Yksinkertainen istuntopohjainen turvallisuuskonteksti.
 *
 * <p>
 * Tallentaa käyttäjäprincipaalin ja roolit HTTP-istuntoon. Tämä on minimaalinen toteutus opetustarkoituksiin.
 * </p>
 */
public class SecurityContext implements RouteSecurityContext {
  private static final String SESSION_USER_KEY = "security.user";
  private static final String SESSION_ROLES_KEY = "security.roles";
  private static final String SESSION_ATTRS_KEY = "security.attributes";

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAuthenticated() {
    return getPrincipal().isPresent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Object> getPrincipal() {
    return getSessionAttribute(SESSION_USER_KEY);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasRole(String role) {
    Optional<Object> rolesObj = getSessionAttribute(SESSION_ROLES_KEY);
    if (rolesObj.isPresent() && rolesObj.get() instanceof Set) {
      @SuppressWarnings("unchecked")
      Set<String> roles = (Set<String>) rolesObj.get();
      return roles.contains(role);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasAuthority(String authority) {
    // Tässä yksinkertaisessa toteutuksessa lupia käsitellään samoina kuin rooleina
    return hasRole(authority);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Object> getAttribute(String name) {
    Optional<Object> attrsObj = getSessionAttribute(SESSION_ATTRS_KEY);
    if (attrsObj.isPresent() && attrsObj.get() instanceof Map) {
      @SuppressWarnings("unchecked")
      Map<String, Object> attrs = (Map<String, Object>) attrsObj.get();
      return Optional.ofNullable(attrs.get(name));
    }
    return Optional.empty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAttribute(String name, Object value) {
    Environment.ifPresent(env -> {
      env.getSessionAccessor().ifPresent(accessor -> {
        accessor.access(session -> {
          @SuppressWarnings("unchecked")
          Map<String, Object> attrs =
              (Map<String, Object>) session.getAttribute(SESSION_ATTRS_KEY);
          if (attrs == null) {
            attrs = new HashMap<>();
            session.setAttribute(SESSION_ATTRS_KEY, attrs);
          }
          attrs.put(name, value);
        });
      });
    });
  }

  private Optional<Object> getSessionAttribute(String key) {
    final Object[] result = new Object[1];
    Environment.ifPresent(env -> {
      env.getSessionAccessor().ifPresent(accessor -> {
        accessor.access(session -> {
          result[0] = session.getAttribute(key);
        });
      });
    });
    return Optional.ofNullable(result[0]);
  }
}`}
</ExpandableCode>

<!-- vale on -->

**Miten se toimii:**

- `isAuthenticated()` tarkistaa, onko käyttäjäprincipaali olemassa istunnossa
- `getPrincipal()` hakee käyttäjätunnuksen istuntovarastosta
- `hasRole()` tarkistaa, sisältääkö käyttäjän roolijoukko määritellyn roolin
- `getAttribute()` / `setAttribute()` hallinnoi mukautettuja turvallisuusattribuutteja
- `Environment.getSessionAccessor()` tarjoaa säikeiden turvallisen pääsyn istuntoon

## Vaihe 3: Luo turvallisuuden hallitsija {#step-3-create-security-manager}

Hallitsija koordinoi turvallisuuspäätöksiä. Se laajentaa `AbstractRouteSecurityManager`-luokkaa, joka käsittelee arviointiketjuja ja käyttöoikeuden hylkäystä:

<!-- vale off -->

<ExpandableCode title="SecurityManager.java" language="java">
{`package com.securityplain.security;

import com.webforj.environment.ObjectTable;
import com.webforj.environment.SessionObjectTable;
import com.webforj.router.Router;
import com.webforj.router.security.AbstractRouteSecurityManager;
import com.webforj.router.security.RouteAccessDecision;
import com.webforj.router.security.RouteSecurityConfiguration;
import com.webforj.router.security.RouteSecurityContext;

import java.util.Set;

/**
 * Yksinkertainen turvallisuuden hallitsijatoteutus.
 *
 * <p>
 * Tarjoaa staattisia metodeja kirjautumiseen/uloskirjautumiseen ja hallitsee turvallisuuskontextia.
 * </p>
 */
public class SecurityManager extends AbstractRouteSecurityManager {
  private static final String SESSION_USER_KEY = "security.user";
  private static final String SESSION_ROLES_KEY = "security.roles";

  /**
   * {@inheritDoc}
   */
  @Override
  public RouteSecurityConfiguration getConfiguration() {
    return new SecurityConfiguration();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RouteSecurityContext getSecurityContext() {
    return new SecurityContext();
  }

  /**
   * Kirjaa käyttäjän rooleineen sisään.
   *
   * @param username käyttäjänimi
   * @param password salasana
   */
  public RouteAccessDecision login(String username, String password) {
    if ("user".equals(username) && "password".equals(password)) {
      Set<String> roles = Set.of("USER");
      persistUser(username, roles);
      return RouteAccessDecision.grant();
    } else if ("admin".equals(username) && "admin".equals(password)) {
      Set<String> roles = Set.of("USER", "ADMIN");
      persistUser(username, roles);
      return RouteAccessDecision.grant();
    }

    return RouteAccessDecision.deny("Virheellinen käyttäjänimi tai salasana");
  }

  /**
   * Kirjaa nykyisen käyttäjän ulos ja ohjaa kirjautumissivulle.
   */
  public void logout() {
    SessionObjectTable.clear(SESSION_USER_KEY);
    SessionObjectTable.clear(SESSION_ROLES_KEY);

    Router router = Router.getCurrent();
    if (router != null) {
      getConfiguration().getAuthenticationLocation().ifPresent(location -> router.navigate(location));
    }
  }

  /**
   * Hanki nykyinen hallitsija-instanssi.
   *
   * @return nykyinen hallitsija-instanssi
   */
  public static SecurityManager getCurrent() {
    String key = SecurityManager.class.getName();
    if (ObjectTable.contains(key)) {
      return (SecurityManager) ObjectTable.get(key);
    }

    SecurityManager instance = new SecurityManager();
    ObjectTable.put(key, instance);

    return instance;
  }

  void saveCurrent(SecurityManager manager) {
    String key = SecurityManager.class.getName();
    ObjectTable.put(key, manager);
  }

  private void persistUser(String username, Set<String> roles) {
    SessionObjectTable.put(SESSION_USER_KEY, username);
    SessionObjectTable.put(SESSION_ROLES_KEY, roles);
  }
}`}
</ExpandableCode>

<!-- vale on -->

**Miten se toimii:**

- Laajentaa `AbstractRouteSecurityManager`-luokkaa perimään arviointiketjun logiikka
- Tarjoaa `getConfiguration()`- ja `getSecurityContext()`-toteutukset
- Lisää `login()`-metodin käyttäjien todennusta varten ja tallentaa käyttöoikeudet istuntoon
- Lisää `logout()`-metodin tyhjentämään istunnon ja ohjaamaan kirjautumissivulle
- Käyttää [`SessionObjectTable`](/docs/advanced/object-string-tables#sessionobjecttable) yksinkertaista istuntovarastointia varten
- Tallentaa itsensä [`ObjectTable`](/docs/advanced/object-string-tables#objecttable) sovelluksen laajuista pääsyä varten

## Vaihe 4: Liitä kaikki käynnistyksessä {#step-4-wire-everything-at-startup}

Rekisteröijä yhdistää kaikki osat, kun sovellus käynnistyy:

```java title="SecurityRegistrar.java"
package com.securityplain.security;

import com.webforj.App;
import com.webforj.AppLifecycleListener;
import com.webforj.annotation.AppListenerPriority;
import com.webforj.router.Router;
import com.webforj.router.security.RouteSecurityObserver;
import com.webforj.router.security.evaluator.AnonymousAccessEvaluator;
import com.webforj.router.security.evaluator.DenyAllEvaluator;
import com.webforj.router.security.evaluator.PermitAllEvaluator;
import com.webforj.router.security.evaluator.RolesAllowedEvaluator;

/**
 * Rekisteröi reitin turvallisuuskomponentit sovelluksen käynnistyksen aikana.
 *
 * <p>
 * Asettuu turvallisuusmanageri ja arvioijat reitittimen kanssa.
 * </p>
 */
@AppListenerPriority(1)
public class SecurityRegistrar implements AppLifecycleListener {

  /**
   * {@inheritDoc}
   */
  @Override
  public void onWillRun(App app) {
    // Luo turvallisuusmanageri
    SecurityManager securityManager = new SecurityManager();
    securityManager.saveCurrent(securityManager);

    // Rekisteröi sisäänrakennetut arvioijat prioriteetti järjestyksessä
    securityManager.registerEvaluator(new DenyAllEvaluator(), 0);
    securityManager.registerEvaluator(new AnonymousAccessEvaluator(), 1);
    securityManager.registerEvaluator(new PermitAllEvaluator(), 2);
    securityManager.registerEvaluator(new RolesAllowedEvaluator(), 3);

    // Luo turvallisuustarkkailija ja liitä se reitittimeen
    RouteSecurityObserver securityObserver = new RouteSecurityObserver(securityManager);
    Router router = Router.getCurrent();
    if (router != null) {
      router.getRenderer().addObserver(securityObserver);
    }
  }
}
```

**Rekisteröi kuuntelija:**

Luo `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener` sisältäen:

```text
com.securityplain.security.SecurityRegistrar
```

Tämä rekisteröi [`AppLifecycleListener`](/docs/advanced/lifecycle-listeners), jotta se suoritetaan sovelluksen käynnistyessä.

**Miten se toimii:**

- Suorittaa aikaisin (`@AppListenerPriority(1)`) asettaakseen turvallisuuden ennen reittien lataamista
- Luo turvallisuusmanagerin ja tallentaa sen globaalisti
- Rekisteröi sisäänrakennetut arvioijat prioriteetti järjestyksessä (alac numerot suoritetaan ensin)
- Luo tarkkailijan, joka puuttuu navigointiin
- Liittää tarkkailijan reitittimeen, jotta turvallisuustarkastukset tapahtuvat automaattisesti

Kun tämä on suoritettu, turvallisuus on aktiivinen kaikissa navigoinneissa.

## Käytä toteutustasi {#using-your-implementation}

### Luo kirjautumisikkuna {#create-a-login-view}

Seuraava ikkuna käyttää [`Login`](/docs/components/login) -komponenttia.

```java title="LoginView.java"
package com.securityplain.views;

import com.securityplain.security.SecurityManager;
import com.webforj.component.Composite;
import com.webforj.component.login.Login;
import com.webforj.router.Router;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.Location;
import com.webforj.router.security.annotation.AnonymousAccess;

@Route("/login")
@FrameTitle("Kirjaudu sisään")
@AnonymousAccess
public class LoginView extends Composite<Login> {
  private final Login self = getBoundComponent();

  public LoginView() {
    self.onSubmit(e -> {
      var result = SecurityManager.getCurrent().login(
        e.getUsername(), e.getPassword()
      );

      if (result.isGranted()) {
        Router.getCurrent().navigate(new Location("/"));
      } else {
        self.setError(true);
        self.setEnabled(true);
      }
    });

    self.whenAttached().thenAccept(c -> self.open());
  }
}
```
