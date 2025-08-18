---
sidebar_position: 10
title: Lifecycle Listeners
sidebar_class_name: new-content
_i18n_hash: 8134c6a2d602b0d69733de9770b44afe
---
<!-- vale off -->
# Lifecycle Luisteraars <DocChip chip='since' label='25.02' />
<!-- vale on -->

De `AppLifecycleListener` interface stelt externe code in staat om app-lifecycle evenementen te observeren en erop te reageren. Door deze interface te implementeren, kunt u code uitvoeren op specifieke momenten tijdens de opstart en afsluiting van de app, zonder de `App` klasse zelf te wijzigen.

Lifecycle luisteraars worden automatisch ontdekt en geladen tijdens runtime via configuratiebestanden voor serviceproviders. Elke app-instantie ontvangt zijn eigen set luisteraars, waardoor isolatie tussen verschillende apps die in dezelfde omgeving draaien wordt gehandhaafd.

## Wanneer lifecycle luisteraars gebruiken {#when-to-use-lifecycle-listeners}

Gebruik lifecycle luisteraars wanneer je moet:

- Hulpbronnen of diensten initialiseren voordat een app draait
- Hulpbronnen opruimen wanneer een app beëindigt
- Cross-cutting concerns toevoegen zonder de `App` klasse te wijzigen
- Plugin-architecturen bouwen

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
Elke app-instantie ontvangt zijn eigen set luisteraars:

- Luisteraars zijn geïsoleerd tussen verschillende apps
- Statische velden in luisteraars worden niet gedeeld tussen apps
- Luisteraarinstanties worden aangemaakt wanneer de app opstart en vernietigd wanneer deze beëindigt

Als je gegevens tussen apps moet delen, gebruik dan externe opslagmechanismen zoals databases of gedeelde diensten.
:::

### Lifecycle evenementen {#lifecycle-events}

| Evenement         | Wanneer Aangeroepen                                   | Gebruikelijke Toepassingen                             |
| ----------------- | ----------------------------------------------------- | ----------------------------------------------------- |
| `onWillCreate`&nbsp;<DocChip chip='since' label='25.03' /> | Na omgevingsinitialisatie, voordat de app wordt aangemaakt  | Configuratie wijzigen, externe configuratiebronnen samenvoegen |
| `onDidCreate`&nbsp;<DocChip chip='since' label='25.03' />  | Na de instantiatie van de app, vóór initialisatie        | Vroeg app-niveau setup, diensten registreren            |
| `onWillRun`       | Voordat `app.run()` wordt uitgevoerd                  | Hulpbronnen initialiseren, diensten configureren        |
| `onDidRun`        | Nadat `app.run()` met succes is voltooid             | Achtergrondtaken starten, succesvolle opstart loggen    |
| `onWillTerminate` | Voor app-beëindiging                                  | Status opslaan, voorbereiden op afsluiting             |
| `onDidTerminate`  | Na app-beëindiging                                   | Hulpbronnen opruimen, laatste logging                   |

## Een lifecycle luisteraar maken {#creating-a-lifecycle-listener}

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
        // Wijzig configuratie voordat de app wordt aangemaakt
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
        System.out.println("App startende: " + app.getId());
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

## Controle over de uitvoeringsvolgorde {#controlling-execution-order}

Wanneer meerdere luisteraars zijn geregistreerd, kun je hun uitvoeringsvolgorde controleren met de annotatie `@AppListenerPriority`. Dit is bijzonder belangrijk wanneer luisteraars afhankelijk zijn van elkaar of wanneer bepaalde initialisatie moet plaatsvinden voordat andere dat doen.

Prioriteitswaarden werken in oplopende volgorde - **lagere nummers worden eerst uitgevoerd**. De standaardprioriteit is 10, dus luisteraars zonder expliciete prioriteitsannotaties worden uitgevoerd na die met lagere prioriteitswaarden.

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // Voert als eerste uit - kritieke beveiligingsconfiguratie
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

Naast het controleren van de volgorde tussen meerdere luisteraars, is het belangrijk om te begrijpen hoe luisteraars omgaan met de eigen lifecycle hooks van de `App` klasse. Voor elk lifecycle-evenement volgt het framework een specifieke uitvoeringsvolgorde die bepaalt wanneer jouw luisteraars worden uitgevoerd ten opzichte van de ingebouwde hooks van de app.

Het onderstaande diagram illustreert deze uitvoeringsstroom, met de precieze timing van wanneer `AppLifecycleListener` methoden worden aangeroepen in relatie tot de bijbehorende `App` hooks:

<div align="center">

![AppLifecycleListener luisteraars VS `App` hooks  ](/img/lifecycle-listeners.svg)

</div>

## Foutafhandeling {#error-handling}

Uitzonderingen die door luisteraars worden opgegooid, worden gelogd maar verhinderen niet dat andere luisteraars worden uitgevoerd of de app draait. Behandel altijd uitzonderingen binnen je luisteraars:

```java title="Voorbeeld foutafhandeling"
@Override
public void onWillRun(App app) {
    try {
        riskyInitialization();
    } catch (Exception e) {
        logger.error("Initialisatie mislukt", e);
    }
}
```
