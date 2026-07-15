---
sidebar_position: 6
title: Custom Evaluators
description: >-
  Write custom RouteSecurityEvaluators for context-aware checks like ownership
  verification beyond role-based permissions.
_i18n_hash: d1edb7260efb2928e988a2cdf313f380
---
Custom evaluators laajentavat webforJ:n turvallisuusjärjestelmää erikoistuneella pääsynhallintalogiikalla, joka menee perusasiakirjojen ja roolien tarkistusten yli. Käytä niitä, kun sinun tarvitsee tarkistaa dynaamisia ehtoja, jotka riippuvat pyyntöyhteydestä, ei vain käyttäjäoikeuksista.

:::info Spring Security keskittynyt
Tämä opas kattaa mukautetut arvioijat Spring Securitylle. Jos et käytä Spring Bootia, katso [Evaluator Chain guide](/docs/security/architecture/evaluator-chain) ymmärtääksesi, miten arvioijat toimivat ja [Complete Implementation](/docs/security/architecture/custom-implementation) toimivan esimerkin saamiseksi.
:::

## Mitä ovat mukautetut arvioijat {#what-are-custom-evaluators}

Arvioija määrittää, voiko käyttäjä käyttää tiettyä reittiä mukautetun logiikan perusteella. Arvioijat tarkistetaan navigoinnin aikana ennen kuin mitään komponenttia renderöidään, mikä mahdollistaa pääsyn keskeyttämisen ja hallinnan dynaamisesti.

webforJ sisältää valmiit arvioijat standardeille Jakarta-anotaatioille:

- `AnonymousAccessEvaluator` - Käsittelee `@AnonymousAccess`
- `PermitAllEvaluator` - Käsittelee `@PermitAll`
- `RolesAllowedEvaluator` - Käsittelee `@RolesAllowed`
- `DenyAllEvaluator` - Käsittelee `@DenyAll`

Mukautetut arvioijat seuraavat samaa kaavaa, mikä mahdollistaa omien anotaatioiden ja pääsynhallintalogiikan luomisen.

:::tip[OpLearn more about built-in annotations]
Tiedot `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` ja `@DenyAll` -anotaatiosta löytyy [Security Annotations guide](/docs/security/annotations).
:::

## Käyttötapa: Omistajuuden vahvistaminen {#use-case-ownership-verification}

Yksi yleinen vaatimus on sallia käyttäjille pääsy vain omiin resursseihinsa. Esimerkiksi käyttäjät voivat vain muokata omaa profiiliaan, eivätkä jonkun toisen profiilia.

**Ongelma**: `@RolesAllowed("USER")` myöntää pääsyn kaikille todennetuille käyttäjille, mutta ei tarkista, pääseekö käyttäjä omaan resurssiinsa. Sinun on vertailtava kirjautuneen käyttäjän ID:t resurssi-ID:hen URL-osoitteessa.

**Esimerkkitapaus:**
- Käyttäjä ID `123` on kirjautunut
- He siirtyvät osoitteeseen `/users/456/edit`
- Saavatko he käyttää tätä sivua? **EI** - he voivat muokata vain `/users/123/edit`

Et voi ratkaista tätä roolien avulla, koska se riippuu reitti-parametrista `:userId`, joka muuttuu jokaiselle pyynnölle.

### Mukautetun anotaatio luominen {#creating-a-custom-annotation}

Määrittele anotaatio merkitsemään reittejä, jotka vaativat omistajuuden vahvistamista:

```java title="RequireOwnership.java"
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireOwnership {
  /**
   * Reitti-parametrin nimi, joka sisältää käyttäjän ID:n.
   */
  String value() default "userId";
}
```

Käytä sitä reiteillä, jotka vaativat omistajuuden tarkistuksia:

```java title="EditProfileView.java"
@Route(value = "/users/:userId/edit", outlet = MainLayout.class)
@RequireOwnership("userId")
public class EditProfileView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public EditProfileView() {
    self.setText("Muokkaa profiilisivua");
  }
}
```

### Arvioijan toteuttaminen {#implementing-the-evaluator}

Luo Springin hallinnoima arvioija, joka vertailee kirjautuneen käyttäjän ID:tä reitti-parametriin:

```java title="OwnershipEvaluator.java"
@RegisteredEvaluator(priority = 10)
public class OwnershipEvaluator implements RouteSecurityEvaluator {

  @Override
  public boolean supports(Class<?> routeClass) {
    return routeClass.isAnnotationPresent(RequireOwnership.class);
  }

  @Override
  public RouteAccessDecision evaluate(Class<?> routeClass, NavigationContext context,
      RouteSecurityContext securityContext, SecurityEvaluatorChain chain) {

    // Tarkista ensin todennus
    if (!securityContext.isAuthenticated()) {
      return RouteAccessDecision.denyAuthentication();
    }

    // Hae anotaatio
    RequireOwnership annotation = routeClass.getAnnotation(RequireOwnership.class);
    String paramName = annotation.value();

    // Hae kirjautuneen käyttäjän ID turvallisuuskontekstista
    String currentUserId = securityContext.getPrincipal()
        .filter(p -> p instanceof UserDetails)
        .map(p -> ((UserDetails) p).getUsername())
        .orElse(null);

    // Hae :userId reitti-parametreista
    String requestedUserId = context.getRouteParameters()
        .get(paramName)
        .orElse(null);

    // Tarkista, vastaavatko ne
    if (currentUserId != null && currentUserId.equals(requestedUserId)) {
      // Omistus vahvistettu - jatka ketjua sallimaan muiden arvioijien suorittaminen
      return chain.evaluate(routeClass, context, securityContext);
    }

    return RouteAccessDecision.deny("Voit käyttää vain omia resurssejasi");
  }
}
```

Spring löytää ja rekisteröi automaattisesti arvioijat, jotka on merkitty `@RegisteredEvaluator`.

### Miten se toimii {#how-it-works}

Arvioijan toteutuksessa on kaksi avainmenetelmää:

#### `supports(Class<?> routeClass)` {#supports-method}

- Palauttaa `true`, jos tämä arvioija pitäisi käsitellä reittiä
- Vain arvioijat, jotka palauttavat `true`, kutsutaan reitille
- Suodattaa reittejä tarkistamalla `@RequireOwnership` -anotaation

#### `evaluate(...)` {#evaluate-method}

- Tarkistaa, onko käyttäjä ensin todennettu
- Hakee kirjautuneen käyttäjän ID turvallisuuskontekstista käyttäen `securityContext.getPrincipal()`
- Hakee reitti-parametrin arvon käyttämällä `context.getRouteParameters().get(paramName)`
- Vertailee kahta ID:tä
- Jos ne vastaavat, delegoi `chain.evaluate()` -menetelmälle, jotta muut arvioijat voivat suorittaa 
- Jos ne eivät vastaa, palauttaa `deny()` syyn kanssa

### Virtauksen esimerkki {#flow-example}

**Kun omistajuuden tarkistus epäonnistuu:**

1. Käyttäjä `123` kirjautuu ja siirtyy `/users/456/edit`
2. `OwnershipEvaluator.supports()` palauttaa `true` (reitti sisältää `@RequireOwnership`)
3. `OwnershipEvaluator.evaluate()` suoritetaan:
   - `currentUserId = "123"` (turvallisuuskontekstista)
   - `requestedUserId = "456"` (reitistä saadusta parametrista `:userId`)
   - `"123".equals("456")` → `false`
   - Palauttaa `RouteAccessDecision.deny("Voit käyttää vain omia resurssejasi")`
4. Käyttäjä ohjataan pääsy kielletty -sivulle

**Kun omistajuuden tarkistus onnistuu:**

1. Käyttäjä `123` kirjautuu ja siirtyy `/users/123/edit`
2. `OwnershipEvaluator.evaluate()` suoritetaan:
   - `currentUserId = "123"`, `requestedUserId = "123"`
   - ID:t vastaavat → kutsuu `chain.evaluate()` jatkaakseen
3. Jos ei ole muita arvioijia, jotka estävät pääsyn, käyttäjälle myönnetään pääsy

## Arvioijaketjun ymmärtäminen {#understanding-the-evaluator-chain}

Turvallisuusjärjestelmä käyttää **vastuu- ja ketju-käytäntöä**, jossa arvioijat käsitellään prioriteettijärjestyksessä. Arvioijat voivat joko tehdä lopullisia päätöksiä tai delegoida ketjulle moninkertaisten tarkistusten yhdistämiseksi.

### Miten ketju toimii {#how-chain-works}

1. Arvioijat lajitellaan prioriteettinsa mukaan (alhaiset numerot ensin)
2. Jokaiselle arvioijalle kutsutaan `supports(routeClass)` tarkistamaan, soveltuuko se
3. Jos `supports()` palauttaa `true`, arvioijan `evaluate()` -menetelmää kutsutaan
4. Arvioija voi joko:
   - **Palauttaa lopullisen päätöksen** (`grant()` tai `deny()`) - **keskeyttää ketjun**
   - **Delegoida ketjulle** kutsumalla `chain.evaluate()` - **sallii muiden arvioijien suorittaa**
5. Jos ketju päättyy ilman päätöstä ja oletus turvallisuus on käytössä, todennetut käyttäjät eivät saa pääsyä

### Lopulliset päätökset {#terminal-decisions}

Keskeytä ketju heti:

#### `RouteAccessDecision.grant()` {#routeaccessdecisiongrant}

- Myöntää pääsyn ja lopettaa edelleen arvioinnin
- Käytetään `@AnonymousAccess` ja `@PermitAll` -anotaatioilla - nämä ovat täydellisiä valtuutuksia, jotka eivät yhdisty muihin tarkistuksiin

#### `RouteAccessDecision.deny(reason)` {#routeaccessdecisiondenyauthentication}

- Kieltää pääsyn ja lopettaa edelleen arvioinnin
- Käytetään `@DenyAll` ja kun mukautetut tarkistukset epäonnistuvat
- Esimerkki: `RouteAccessDecision.deny("Voit käyttää vain omia resurssejasi")`

#### `RouteAccessDecision.denyAuthentication()` {#routeaccessdecisiondenyauthentication}

- Ohjaa kirjautumissivulle
- Käytetään, kun todennus on vaadittu mutta puuttuu

### Ketjun delegointi {#chain-delegation}

Mahdollistaa tarkistusten yhdistämisen:

#### `chain.evaluate(routeClass, context, securityContext)` {#chainevaluaterouteclass-context-securitycontext}

- Siirtää ohjausta seuraavalle arvioijalle ketjussa
- Mahdollistaa useiden valtuutustarkistusten yhdistämisen
- Käytetään `@RolesAllowed` ja `@RouteAccess` jälkeen, kun niiden tarkistukset onnistuvat
- Mukautettujen arvioijien tulisi käyttää tätä kaavaa, kun tarkistukset onnistuvat, jotta sallitaan koostumus

## Arvioijan prioriteetti {#evaluator-priority}

Arvioijat tarkistetaan prioriteettijärjestyksessä (alhaiset numerot ensin). Kehyksen arvioijat käyttävät prioriteettia 1-9, mukautettujen arvioijien tulisi käyttää 10 tai enemmän.

Valmiit arvioijat rekisteröidään tässä järjestyksessä:

```java
// Prioriteetti 1: @DenyAll - estää kaiken
// Prioriteetti 2: @AnonymousAccess - sallii todennuksen
// Prioriteetti 3: AuthenticationRequiredEvaluator - varmistaa todennuksen @PermitAll/@RolesAllowed
// Prioriteetti 4: @PermitAll - vaatii vain todennuksen
// Prioriteetti 5: @RolesAllowed - vaatii erityisiä rooleja
// Prioriteetti 6: @RouteAccess - SpEL lausekkeet (vain Spring Security)
// Prioriteetti 10+: Mukautetut arvioijat (kuten @RequireOwnership)
```

### Miten prioriteetti vaikuttaa arviointiin {#priority-affects-evaluation}

- Alhaisemman prioriteetin arvioijat toimivat ensin ja voivat "lyödä ketjun"
- `@DenyAll` (prioriteetti 1) toimii ensin - jos tämä on läsnä, pääsy kielletty
- `@AnonymousAccess` (prioriteetti 2) toimii seuraavaksi - jos tämä on läsnä, pääsy sallitaan aina (ilman todennusta)
- `AuthenticationRequiredEvaluator` (prioriteetti 3) tarkistaa, tarvitseeko reitti todennusta ja onko käyttäjä todennettu
- Jos mikään arvioija ei käsittele reittiä, oletus turvallisuuslogiikka pätee

### Prioriteetin asettaminen {#setting-priority}

Aseta prioriteetti `@RegisteredEvaluator` -anotaatiolla:

```java
@RegisteredEvaluator(priority = 10)  // Toimii valmiiden arvioijien jälkeen
public class OwnershipEvaluator implements RouteSecurityEvaluator {
  // ...
}
```

:::tip Prioriteettialue
Mukautettujen arvioijien tulisi käyttää prioriteettia 10 tai suurempi. Prioriteetti 1-9 on varattu kehysarvioijille. Jos käytät varattua aluetta, saat varoituksen lokissa.
:::

## Arvioijien yhdistäminen {#combining-evaluators}

Arvioijat, jotka delegoivat ketjulle, voidaan yhdistää monimutkaisten valtuutustarkistusten luomiseksi. Reiteillä voi olla useita tietoturvaan liittyviä anotaation:

### Yhdistetään roolitarkistukset mukautetun logiikan kanssa {#combining-roles-custom}

```java
@Route("/users/:userId/settings")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class UserSettingsView extends Composite<Div> {
  // Täytyy olla USER-rooli JA käyttää itseään asetuksia
}
```

**Miten se toimii:**
1. `RolesAllowedEvaluator` (prioriteetti 5) tarkistaa, onko käyttäjällä "USER" -rooli
2. Jos yes, kutsuu `chain.evaluate()` jatkaakseen
3. `OwnershipEvaluator` (prioriteetti 10) tarkistaa, vastaako `userId` kirjautunutta käyttäjää
4. Jos yes, kutsuu `chain.evaluate()` jatkaakseen
5. Ketju päättyy → pääsy myönnetään

### Yhdistetään SpEL-lausekkeet mukautetun logiikan kanssa {#combining-spel-custom}

```java
@Route("/admin/users/:userId/edit")
@RouteAccess("hasRole('ADMIN')")
@RequireOwnership("userId")
public class AdminEditUserView extends Composite<Div> {
  // Täytyy olla admin JA käyttää omaa tiliä
}
```

### Mitä ei voi yhdistää {#cant-combine}

`@AnonymousAccess` ja `@PermitAll` tekevät **lopullisia päätöksiä** - ne myöntävät pääsyn välittömästi ilman, että ketjua kutsutaan. Et voi yhdistää niitä mukautettujen arvioijien kanssa:

```java
// @PermitAll myöntää pääsyn välittömästi, @RequireOwnership ei koskaan toimi
@Route("/users/:userId/profile")
@PermitAll
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // ...
}
```

Resursseille, joihin kaikki todennetut käyttäjät voivat käyttää, käytä `@RolesAllowed` yhteisen roolin kanssa:

```java
// @RolesAllowed delegoi ketjulle
@Route("/users/:userId/profile")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // Täytyy olla todennettu käyttäjä JA käyttää omaa profiiliaan
}
```
