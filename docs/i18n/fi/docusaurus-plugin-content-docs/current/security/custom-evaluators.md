---
sidebar_position: 6
title: Custom Evaluators
_i18n_hash: 9b448cdd74811b257b78cc6c9f04e7c2
---
Custom evaluators laajentavat webforJ:n turvallisuusjärjestelmää erikoistuneella pääsynhallintalogikalla, joka ylittää perusautentikoinnin ja roolien tarkistukset. Käytä niitä silloin, kun sinun on vahvistettava dynaamisia olosuhteita, jotka riippuvat pyyntökontekstista, eivät vain käyttäjäoikeuksista.

:::info Spring Security keskittynyt
Tässä oppaassa käsitellään mukautettuja arvioijia Spring Securitylle. Jos et käytä Spring Bootia, katso [Evaluator Chain -opas](/docs/security/architecture/evaluator-chain) ymmärtääksesi, miten arvioijat toimivat, sekä [Complete Implementation](/docs/security/architecture/custom-implementation) toimivasta esimerkistä.
:::

## Mitkä ovat mukautetut arvioijat {#what-are-custom-evaluators}

Arvioija määrittää, voiko käyttäjä käyttää tiettyä reittiä mukautetun logiikan perusteella. Arvioijat tarkistetaan navigoinnin aikana ennen kuin mitään komponenttia renderöidään, jolloin voit keskeyttää ja hallita pääsyä dynaamisesti.

webforJ sisältää sisäänrakennetut arvioijat standardeille Jakarta-annotaatioille:

- `AnonymousAccessEvaluator` - Käsittelee `@AnonymousAccess`
- `PermitAllEvaluator` - Käsittelee `@PermitAll`
- `RolesAllowedEvaluator` - Käsittelee `@RolesAllowed`
- `DenyAllEvaluator` - Käsittelee `@DenyAll`

Mukautetut arvioijat seuraavat samaa kaavaa, mikä mahdollistaa omien annotaatioiden ja pääsynhallintalogikan luomisen.

:::tip[Oplearn lisää sisäänrakennetusta annotaatioista]
Lisätietoja `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` ja `@DenyAll` -annotaatioista, katso [Security Annotations -opas](/docs/security/annotations).
:::

## Käyttötapa: Omistajuuden vahvistaminen {#use-case-ownership-verification}

Yleinen vaatimus on sallia käyttäjien käyttää vain omia resurssejaan. Esimerkiksi käyttäjien tulisi voida muokata vain omaa profiiliaan, ei muiden profiilia.

**Ongelma**: `@RolesAllowed("USER")` myöntää pääsyn kaikille todennetuille käyttäjille, mutta ei vahvista, onko käyttäjä käyttämässä omaa resurssiaan. Sinun on verrattava kirjautuneen käyttäjän ID:tä URL:in resurssi ID:hen.

**Esimerkkitilanne:**
- Käyttäjä-ID `123` on kirjautuneena
- He navigoivat osoitteeseen `/users/456/edit`
- Saako he käyttää tätä sivua? **EI** - he voivat muokata vain `/users/123/edit`

Et voi ratkaista tätä rooleilla, koska se riippuu reittiparametrista `:userId`, joka muuttuu jokaiselle pyyntöä varten.

### Mukautetun annotaation luominen {#creating-a-custom-annotation}

Määrittele annotaatio merkitsemään reittejä, jotka vaativat omistajuuden vahvistamista:

```java title="RequireOwnership.java"
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireOwnership {
  /**
   * Reittiparametrin nimi, joka sisältää käyttäjä-ID:n.
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

Luo Springin hallinnoima arvioija, joka vertaa kirjautuneen käyttäjän ID:tä reittiparametriin:

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

    // Ensiksi tarkista todennus
    if (!securityContext.isAuthenticated()) {
      return RouteAccessDecision.denyAuthentication();
    }

    // Hanki annotaatio
    RequireOwnership annotation = routeClass.getAnnotation(RequireOwnership.class);
    String paramName = annotation.value();

    // Hanki kirjautuneen käyttäjän ID turvallisuuskontextista
    String currentUserId = securityContext.getPrincipal()
        .filter(p -> p instanceof UserDetails)
        .map(p -> ((UserDetails) p).getUsername())
        .orElse(null);

    // Hanki :userId reittiparametreista
    String requestedUserId = context.getRouteParameters()
        .get(paramName)
        .orElse(null);

    // Tarkista, vastaavatko ne toisiaan
    if (currentUserId != null && currentUserId.equals(requestedUserId)) {
      // Omistajuus vahvistettu - jatka ketjua salliaksesi muiden arvioijien toimia
      return chain.evaluate(routeClass, context, securityContext);
    }

    return RouteAccessDecision.deny("Voit käyttää vain omia resurssejasi");
  }
}
```

Spring havaitsee ja rekisteröi automaattisesti arvioijat, joilla on `@RegisteredEvaluator` -annotation.

### Miten se toimii {#how-it-works}

Arvioijan toteutuksessa on kaksi keskeistä metodia:

#### `supports(Class<?> routeClass)` {#supports-method}

- Palauttaa `true`, jos tämän arvioijan tulisi käsitellä reitti
- Vain arvioijat, jotka palauttavat `true`, kutsutaan reitille
- Suodattaa reittejä tarkistamalla, onko `@RequireOwnership` annotaatio

#### `evaluate(...)` {#evaluate-method}

- Tarkistaa ensin, onko käyttäjä todennettu
- Hakee kirjautuneen käyttäjän ID:n `securityContext.getPrincipal()`-metodista
- Hakee reittiparametrin arvon `context.getRouteParameters().get(paramName)`-metodista
- Vertaa kahta ID:tä
- Jos ne vastaavat, delegoi `chain.evaluate()`:lle salliakseen muiden arvioijien toimia
- Jos ne eivät vastaa, palauttaa `deny()` syyn kanssa

### Virtaesimerkki {#flow-example}

**Kun omistajuuden tarkistus epäonnistuu:**

1. Käyttäjä `123` kirjautuu sisään ja navigoi osoitteeseen `/users/456/edit`
2. `OwnershipEvaluator.supports()` palauttaa `true` (reittillä on `@RequireOwnership`)
3. `OwnershipEvaluator.evaluate()` aktiivinen:
   - `currentUserId = "123"` (turvallisuuskontextista)
   - `requestedUserId = "456"` (reittiparametrista `:userId`)
   - `"123".equals("456")` → `false`
   - Palauttaa `RouteAccessDecision.deny("Voit käyttää vain omia resurssejasi")`
4. Käyttäjä ohjataan pääsyn estämissivulle

**Kun omistajuuden tarkistus onnistuu:**

1. Käyttäjä `123` kirjautuu sisään ja navigoi osoitteeseen `/users/123/edit`
2. `OwnershipEvaluator.evaluate()` aktivoituu:
   - `currentUserId = "123"`, `requestedUserId = "123"`
   - ID:t vastaavat → kutsuu `chain.evaluate()` jatkaakseen
3. Jos mikään muu arvioija ei estä pääsyä, käyttäjälle myönnetään pääsy

## Arvioijaketjun ymmärtäminen {#understanding-the-evaluator-chain}

Turvallisuusjärjestelmä käyttää **vastuun siirtoketjun mallia**, jossa arvioijat käsitellään prioriteetti järjestyksessä. Arvioijat voivat joko tehdä päättävissä päätöksiä tai delegoida ketjulle useiden tarkistusten yhdistämiseksi.

### Miten ketju toimii {#how-chain-works}

1. Arvioijat lajitellaan prioriteetin mukaan (matalammat numerot ensin)
2. Jokaiselle arvioijalle kutsutaan `supports(routeClass)` tarkistamaan, onko se sovellettavissa
3. Jos `supports()` palauttaa `true`, arvioijan `evaluate()`-metodia kutsutaan
4. Arvioija voi joko:
   - **Palauttaa päättävän päätöksen** (`grant()` tai `deny()`) - **pysäyttää ketjun**
   - **Delegoida ketjulle** kutsumalla `chain.evaluate()` - **sallii muiden arvioijien toimia**
5. Jos ketju päättyy ilman päätöstä ja oletus "turvallinen" pääsy on aktivoitu, todennetut käyttäjät estetään

### Päättävät päätökset {#terminal-decisions}

Pysäyttää ketjun välittömästi:

#### `RouteAccessDecision.grant()`

- Myöntää pääsyn ja pysäyttää lisäarvioinnit
- Käytetään `@AnonymousAccess` ja `@PermitAll` -annotaatioilla - nämä ovat täydellisiä valtuutuksia, jotka eivät yhdisty muihin tarkastuksiin

#### `RouteAccessDecision.deny(reason)`

- Estää pääsyn ja pysäyttää lisäarvioinnit
- Käytetään `@DenyAll` ja kun mukautetut tarkastukset epäonnistuvat
- Esimerkki: `RouteAccessDecision.deny("Voit käyttää vain omia resurssejasi")`

#### `RouteAccessDecision.denyAuthentication()`

- Ohjaa kirjautumissivulle
- Käytetään, kun todennus on vaadittu mutta puuttuu

### Ketjun delegointi {#chain-delegation}

Mahdollistaa tarkistusten yhdistämisen:

#### `chain.evaluate(routeClass, context, securityContext)`

- Siirtää ohjauksen seuraavalle arvioijalle ketjussa
- Mahdollistaa useiden valtuutustarkistusten yhdistämisen
- Käytetään `@RolesAllowed` ja `@RouteAccess` jälkeen, kun heidän tarkistuksensa ovat onnistuneet
- Mukautettujen arvioijien tulisi käyttää tätä kaavaa, kun tarkistukset onnistuvat mahdollistamaan yhdistämisen

## Arvioijien prioriteetti {#evaluator-priority}

Arvioijat tarkistetaan prioriteetti järjestyksessä (matalammat numerot ensin). Kehyksen arvioijat käyttävät prioriteetteja 1-9, mukautetut arvioijat pitäisi käyttää 10 tai korkeampia.

Sisäänrakennetut arvioijat rekisteröidään tässä järjestyksessä:

```java
// Prioriteetti 1: @DenyAll - estää kaiken
// Prioriteetti 2: @AnonymousAccess - sallii todennuksettomat pääsyt
// Prioriteetti 3: AuthenticationRequiredEvaluator - varmistaa todennuksen @PermitAll/@RolesAllowed
// Prioriteetti 4: @PermitAll - vaatii vain todennusta
// Prioriteetti 5: @RolesAllowed - vaatii erityisiä rooleja
// Prioriteetti 6: @RouteAccess - SpEL-lausekkeet (vain Spring Security)
// Prioriteetti 10+: Mukautetut arvioijat (kuten @RequireOwnership)
```

### Miten prioriteetti vaikuttaa arviointiin {#priority-affects-evaluation}

- Alempiprioriteettiset arvioijat toimivat ensin ja voivat "lyhyttää" ketjun
- `@DenyAll` (prioriteetti 1) toimii ensin - jos se on läsnä, pääsy on aina estetty
- `@AnonymousAccess` (prioriteetti 2) toimii seuraavaksi - jos se on läsnä, pääsy on aina myönnetty (ilman todennusta)
- `AuthenticationRequiredEvaluator` (prioriteetti 3) tarkistaa, tarvitsee reitti todennuksen ja onko käyttäjä todennettu
- Jos mikään arvioija ei käsittele reittiä, turvallinen oletuslogiikka sovelletaan

### Prioriteetin asettaminen {#setting-priority}

Aseta prioriteetti `@RegisteredEvaluator` -annotaatiolla:

```java
@RegisteredEvaluator(priority = 10)  // Toimii sisäänrakennettujen arvioijien jälkeen
public class OwnershipEvaluator implements RouteSecurityEvaluator {
  // ...
}
```

:::tip Prioriteettialue
Mukauttujen arvioijien tulisi käyttää prioriteettia 10 tai korkeampia. Prioriteetti 1-9 on varattu kehyksen arvioijille. Jos käytät varattua aluetta, saat varoituksen lokiin.
:::

## Arvioijien yhdistäminen {#combining-evaluators}

Arvioijat, jotka delegoivat ketjulle, voidaan yhdistää luomaan monimutkaista valtuutuslogiikkaa. Reiteillä voi olla useita turvallisuusannotaatioita:

### Roolitarkistusten yhdistäminen mukautettuun logiikkaan {#combining-roles-custom}

```java
@Route("/users/:userId/settings")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class UserSettingsView extends Composite<Div> {
  // Käyttäjällä on oltava USER-rooli JA päästävä omiin asetuksiinsa
}
```

**Miten se toimii:**
1. `RolesAllowedEvaluator` (prioriteetti 5) tarkistaa, onko käyttäjällä "USER" -rooli
2. Jos on, kutsuu `chain.evaluate()` jatkaakseen
3. `OwnershipEvaluator` (prioriteetti 10) tarkistaa, vastaako `userId` kirjautunutta käyttäjää
4. Jos kyllä, kutsuu `chain.evaluate()` jatkaakseen
5. Ketju päättyy → pääsy myönnetään

### SpEL-lausekkeiden yhdistäminen mukautettuun logiikkaan {#combining-spel-custom}

```java
@Route("/admin/users/:userId/edit")
@RouteAccess("hasRole('ADMIN')")
@RequireOwnership("userId")
public class AdminEditUserView extends Composite<Div> {
  // Pitää olla ylläpitäjä JA päästä omaan tiliinsä
}
```

### Mitä ei voi yhdistää {#cant-combine}

`@AnonymousAccess` ja `@PermitAll` tekevät **päättävät päätökset** - ne myöntävät pääsyn välittömästi ilman ketjun kutsumista. Et voi yhdistää niitä mukautettuihin arvioijiin:

```java
// @PermitAll myöntää pääsyn välittömästi, @RequireOwnership ei koskaan toimi
@Route("/users/:userId/profile")
@PermitAll
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // ...
}
```

Resursseille, joihin kaikki todennetut käyttäjät voivat päästä, käytä `@RolesAllowed` yleisellä roolilla:

```java
// @RolesAllowed delegoi ketjulle
@Route("/users/:userId/profile")
@RolesAllowed("USER")
@RequireOwnership("userId")
public class ProfileView extends Composite<Div> {
  // Käyttäjän on oltava todennettu JA päästävä omaan profiiliinsa
}
```
