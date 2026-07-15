---
sidebar_position: 10
title: Lifecycle Listeners
description: >-
  Hook into webforJ app startup and shutdown phases with AppLifecycleListener to
  initialize services, modify config, or clean up resources.
_i18n_hash: 3ef33ca5104ef421c38d3db16c9fa453
---
<!-- vale off -->
# Levenscyclusluisteraars <DocChip chip='since' label='25.02' />
<!-- vale on -->

De interface `AppLifecycleListener` stelt externe code in staat om de gebeurtenissen in de levenscyclus van de app te observeren en erop te reageren. Door deze interface te implementeren, kunt u code uitvoeren op specifieke momenten tijdens het opstarten en afsluiten van de app zonder de `App`-klasse zelf te wijzigen.

Levenscyclusluisteraars worden automatisch ontdekt en geladen tijdens runtime via configuratiebestanden voor serviceproviders. Elke app-instantie ontvangt zijn eigen set luisterinstellingen, waardoor isolatie tussen verschillende apps die in dezelfde omgeving draaien, behouden blijft.

## Wanneer levenscyclusluisteraars te gebruiken {#when-to-use-lifecycle-listeners}

Gebruik levenscyclusluisteraars wanneer u moet:

- Hulpbronnen of diensten initialiseren voordat een app draait
- Hulpbronnen opschonen wanneer een app beëindigt
- Cross-cutting concerns toevoegen zonder de `App`-klasse te wijzigen
- Pluginarchitecturen bouwen

## De interface `AppLifecycleListener` {#the-applifecyclelistener-interface}

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
Elke app-instantie ontvangt zijn eigen set van luisterinstellingen:

- Luisteraars zijn geïsoleerd tussen verschillende apps
- Statische velden in luisteraars worden niet gedeeld tussen apps
- Luisterinstellingen worden gemaakt wanneer de app start en vernietigd wanneer deze beëindigt

Als u gegevens tussen apps moet delen, gebruik dan externe opslagmechanismen zoals databases of gedeelde services.
:::

### Levenscyclusgebeurtenissen {#lifecycle-events}

| Evenement         | Wanneer Aangeroepen                                    | Veelvoorkomende Toepassingen                          |
| ----------------- | ------------------------------------------------------ | ----------------------------------------------------- |
| `onWillCreate`&nbsp;<DocChip chip='since' label='25.03' /> | Na initialisatie van de omgeving, voor app-creatie   | Configuratie aanpassen, externe configuratiebronnen samenvoegen |
| `onDidCreate`&nbsp;<DocChip chip='since' label='25.03' />  | Na app-instantiering, voor initialisatie              | Vroegtijdige app-niveau setup, diensten registreren    |
| `onWillRun`       | Voor het uitvoeren van `app.run()`                    | Hulpbronnen initialiseren, diensten configureren      |
| `onDidRun`        | Na succesvolle voltooiing van `app.run()`             | Achtergrondtaken starten, succesvolle opstart registreren |
| `onWillTerminate` | Voor beëindiging van de app                            | Toestand opslaan, voorbereiden op afsluiten           |
| `onDidTerminate`  | Na beëindiging van de app                             | Hulpbronnen opschonen, uiteindelijke logging          |

## Een levenscyclusluisteraar maken {#creating-a-lifecycle-listener}

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
    // Configuratie aanpassen voor app-creatie
    Config additionalConfig = ConfigFactory.parseString(
      "myapp.feature.enabled = true"
    );
    env.setConfig(additionalConfig);
  }

  @Override
  public void onDidCreate(App app) {
    System.out.println("App gemaakt: " + app.getId());
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

### De luisteraar registreren {#registering-the-listener}

Maak een configuratiebestand voor serviceproviders aan:

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

## Controle over de uitvoering volgorde {#controlling-execution-order}

Wanneer meerdere luisteraars zijn geregistreerd, kunt u hun uitvoeringsvolgorde regelen met behulp van de annotatie `@AppListenerPriority`. Dit is bijzonder belangrijk wanneer luisteraars afhankelijkheden van elkaar hebben of wanneer bepaalde initialisatie vóór andere moet plaatsvinden.

Prioriteitswaarden werken in oplopende volgorde - **lagere nummers worden eerst uitgevoerd**. De standaardprioriteit is 10, dus luisteraars zonder expliciete prioriteitsannotaties worden uitgevoerd na die met lagere prioriteitswaarden.

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // Voert eerst uit - kritieke beveiligingssetup
public class SecurityListener implements AppLifecycleListener {
  @Override
  public void onWillRun(App app) {
    initializeSecurity();
  }
}

@AutoService(AppLifecycleListener.class)
@AppListenerPriority(10) // Standaardprioriteit - algemene logging
public class LoggingListener implements AppLifecycleListener {
  @Override
  public void onWillRun(App app) {
    initializeLogging();
  }
}
```

### Uitvoeringsstroom met App-hooks {#execution-flow-with-app-hooks}

Naast het regelen van de volgorde tussen meerdere luisteraars, is het belangrijk om te begrijpen hoe luisteraars interageren met de eigen levenscyclushooks van de `App`-klasse. Voor elke gebeurtenis in de levenscyclus volgt het framework een specifieke uitvoeringsvolgorde die bepaalt wanneer uw luisteraars draaien in relatie tot de ingebouwde hooks van de app.

De onderstaande diagram illustreert deze uitvoeringsstroom, die de exacte timing toont van wanneer `AppLifecycleListener`-methoden worden aangeroepen in relatie tot de overeenkomstige `App`-hooks:

<div align="center">

![AppLifecycleListener luisteraars VS `App` hooks ](/img/lifecycle-listeners.svg)

</div>


## Foutafhandeling {#error-handling}

Uitzonderingen die door luisteraars worden gegenereerd, worden gelogd maar voorkomen niet dat andere luisteraars worden uitgevoerd of de app draait. Behandel altijd uitzonderingen binnen uw luisteraars:

```java title="Foutafhandeling voorbeeld"
@Override
public void onWillRun(App app) {
  try {
    riskyInitialization();
  } catch (Exception e) {
    logger.error("Initialisatie mislukt", e);
  }
}
```
