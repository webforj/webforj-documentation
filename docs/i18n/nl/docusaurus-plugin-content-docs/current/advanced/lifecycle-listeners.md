---
sidebar_position: 10
title: Lifecycle Listeners
_i18n_hash: ffb3121402861d501b322c7efca6f669
---
<!-- vale off -->
# Lifecyclelisteners <DocChip chip='since' label='25.02' />
<!-- vale on -->

De `AppLifecycleListener` interface stelt externe code in staat om app-levenscyclusgebeurtenissen te observeren en erop te reageren. Door deze interface te implementeren, kun je code uitvoeren op specifieke momenten tijdens het opstarten en afsluiten van de app, zonder de `App`-klasse zelf te wijzigen.

Lifecyclelisteners worden automatisch ontdekt en geladen tijdens runtime via configuratiebestanden voor serviceproviders. Elke app-instantie ontvangt zijn eigen set listener-instanties, wat isolatie onderhoudt tussen verschillende apps die in dezelfde omgeving draaien.

## Wanneer lifecyclelisteners te gebruiken {#when-to-use-lifecycle-listeners}

Gebruik lifecyclelisteners wanneer je moet:

- Hulpbronnen of services initialiseren voordat een app draait
- Hulpbronnen opruimen wanneer een app beëindigt
- Cross-cutting concerns toevoegen zonder de `App`-klasse te wijzigen
- Pluginarchitecturen bouwen

## De `AppLifecycleListener` interface {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
  default void onWillCreate(Environment env) {}     // Sinds 25.03
  default void onDidCreate(App app) {}              // Sinds 25.03
  default void onWillRun(App app) {}
  default void onDidRun(App app) {}
  default void onWillTerminate(App app) {}
  default void onDidTerminate(App app) {}
}
```

:::info App-isolatie
Elke app-instantie ontvangt zijn eigen set van listener-instanties:

- Listeners zijn geïsoleerd tussen verschillende apps
- Statische velden in listeners worden niet gedeeld tussen apps
- Listener-instanties worden aangemaakt wanneer de app start en vernietigd wanneer deze eindigt

Als je gegevens tussen apps moet delen, gebruik dan externe opslagmechanismen zoals databases of gedeelde services.
:::

### Lifecyclegebeurtenissen {#lifecycle-events}

| Gebeurtenis            | Wanneer opgeroepen                                     | Veelvoorkomende toepassingen                           |
| ---------------------- | ------------------------------------------------------ | ----------------------------------------------------- |
| `onWillCreate`&nbsp;<DocChip chip='since' label='25.03' /> | Na omgevinginitialisatie, vóór app-creatie             | Wijzig configuratie, haal externe configuratieniches op |
| `onDidCreate`&nbsp;<DocChip chip='since' label='25.03' />  | Na app-instantie, vóór initialisatie                   | Vroeg app-niveau instellen, registreer services        |
| `onWillRun`          | Vóór `app.run()` wordt uitgevoerd                      | Initialiseer hulpbronnen, configureer services         |
| `onDidRun`           | Nadat `app.run()` succesvol is voltooid                | Start achtergrondtaken, log succesvolle opstart       |
| `onWillTerminate`    | Vóór app-afsluiting                                   | Sla status op, bereid je voor op afsluiten             |
| `onDidTerminate`     | Na app-afsluiting                                     | Ruim hulpbronnen op, laatste logging                   |

## Een lifecyclelistener maken {#creating-a-lifecycle-listener}

### Basisimplementatie {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;
import com.webforj.Environment;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartupListener implements AppLifecycleListener {

  @Override
  public void onWillCreate(Environment env) {
    // Wijzig configuratie vóór app-creatie
    Config additionalConfig = ConfigFactory.parseString(
      "myapp.feature.enabled = true"
    );
    env.setConfig(additionalConfig);
  }

  @Override
  public void onDidCreate(App app) {
    System.out.println("App aangemaakt: " + app.getId());
  }

  @Override
  public void onWillRun(App app) {
    System.out.println("App startend: " + app.getId());
  }

  @Override
  public void onDidRun(App app) {
    System.out.println("App gestart: " + app.getId());
  }
}
```

### De listener registreren {#registering-the-listener}

Maak een configuratiebestand voor de serviceprovider aan:

**Bestand**: `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip Gebruik AutoService
Het is gemakkelijk om te vergeten servicebeschrijvingen bij te werken. Gebruik Google's [AutoService](https://github.com/google/auto/blob/main/service/README.md) om het servicebestand automatisch te genereren:

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
  // Implementatie
}
```
:::

## Uitvoeringvolgorde beheersen {#controlling-execution-order}

Wanneer meerdere listeners zijn geregistreerd, kun je hun uitvoeringsvolgorde controleren met de annotatie `@AppListenerPriority`. Dit is vooral belangrijk wanneer listeners afhankelijkheden van elkaar hebben of wanneer bepaalde initialisatie vóór andere moet plaatsvinden.

Prioriteitswaarden werken in oplopende volgorde - **lagere nummers worden eerst uitgevoerd**. De standaardprioriteit is 10, dus listeners zonder expliciete prioriteitsannotaties worden uitgevoerd na die met lagere prioriteitswaarden.

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // Voert als eerste uit - kritische beveiligingsinstellingen
public class SecurityListener implements AppLifecycleListener {
  @Override
  public void onWillRun(App app) {
    initializeSecurity();
  }
}

@AutoService(AppLifecycleListener.class)
@AppListenerPriority(10) // Standaard prioriteit - algemene logging
public class LoggingListener implements AppLifecycleListener {
  @Override
  public void onWillRun(App app) {
    initializeLogging();
  }
}
```

### Uitvoeringsstroom met App-haken {#execution-flow-with-app-hooks}

Naast het beheersen van de volgorde tussen meerdere listeners is het belangrijk om te begrijpen hoe listeners interageren met de eigen lifecyclehaken van de `App`-klasse. Voor elke lifecyclegebeurtenis volgt het framework een specifieke uitvoeringsvolgorde die bepaalt wanneer jouw listeners draaien in relatie tot de ingebouwde haken van de app.

Het diagram hieronder illustreert deze uitvoeringsstroom en toont de precieze timing van wanneer de `AppLifecycleListener`-methoden worden aangeroepen in relatie tot de overeenkomstige `App`-haken: 

<div align="center">

![AppLifecycleListener listeners VS `App` hooks  ](/img/lifecycle-listeners.svg)

</div>


## Foutafhandeling {#error-handling}

Uitzonderingen die door listeners worden opgegooid, worden gelogd maar voorkomen niet dat andere listeners worden uitgevoerd of de app wordt uitgevoerd. Behandel altijd uitzonderingen binnen je listeners:

```java title="Foutafhandelingsvoorbeeld"
@Override
public void onWillRun(App app) {
  try {
    riskyInitialization();
  } catch (Exception e) {
    logger.error("Initialisatie mislukt", e);
  }
}
```
