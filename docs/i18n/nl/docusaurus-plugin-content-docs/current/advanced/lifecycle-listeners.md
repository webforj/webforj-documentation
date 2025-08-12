---
sidebar_position: 10
title: Lifecycle Listeners
sidebar_class_name: new-content
_i18n_hash: 95e3a7e349b0cf54679daf76d2bf209c
---
<!-- vale off -->
# Levenscyclusluisteraars <DocChip chip='since' label='25.02' />
<!-- vale on -->

De `AppLifecycleListener` interface stelt externe code in staat om de levenscyclusgebeurtenissen van de app te observeren en erop te reageren. Door deze interface te implementeren, kunt u code uitvoeren op specifieke punten tijdens de opstart en afsluiting van de app zonder de `App`-klasse zelf te wijzigen.

Levenscyclusluisteraars worden automatisch ontdekt en geladen tijdens runtime via configuratiebestanden van serviceproviders. Elke instantie van de app ontvangt zijn eigen set van luisteraars, waardoor isolatie tussen verschillende apps die in dezelfde omgeving draaien mogelijk is.

## Wanneer levenscyclusluisteraars te gebruiken {#when-to-use-lifecycle-listeners}

Gebruik levenscyclusluisteraars wanneer u moet:
- Hulpbronnen of diensten initialiseren voordat een app draait
- Hulpbronnen opruimen wanneer een app beëindigt  
- Grensoverschrijdende zorgen toevoegen zonder de `App`-klasse te wijzigen
- Pluginarchitecturen opbouwen

## De `AppLifecycleListener` interface {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
    default void onWillRun(App app) {}
    default void onDidRun(App app) {}
    default void onWillTerminate(App app) {}
    default void onDidTerminate(App app) {}
}
```

:::info App-isolatie
Elke instantie van de app ontvangt zijn eigen set van luisteraars:
- Luisteraars zijn geïsoleerd tussen verschillende apps
- Statische velden in luisteraars worden niet gedeeld tussen apps
- Instantie van luisteraars worden gemaakt wanneer de app opstart en vernietigd wanneer deze beëindigt

Als u gegevens tussen apps moet delen, gebruik dan externe opslagmechanismen zoals databases of gedeelde diensten.
:::

### Levenscyclusgebeurtenissen {#lifecycle-events}

| Gebeurtenis | Wanneer opgeroepen | Veel voorkomende toepassingen |
|-------|-------------|-------------|
| `onWillRun` | Voordat `app.run()` wordt uitgevoerd | Hulpbronnen initialiseren, diensten configureren |
| `onDidRun` | Nadat `app.run()` succesvol is voltooid | Achtergrondtaken starten, succesvolle opstart registreren |
| `onWillTerminate` | Voor beëindiging van de app | Status opslaan, voorbereiden op afsluiting |
| `onDidTerminate` | Na beëindiging van de app | Hulpbronnen opruimen, laatste logging |

## Een levenscyclusluisteraar maken {#creating-a-lifecycle-listener}

### Basisimplementatie {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;

public class StartupListener implements AppLifecycleListener {
    
    @Override
    public void onWillRun(App app) {
        System.out.println("App starten: " + app.getId());
    }
    
    @Override
    public void onDidRun(App app) {
        System.out.println("App gestart: " + app.getId());
    }
}
```

### De luisteraar registreren {#registering-the-listener}

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

## Beheersen van de uitvoeringsvolgorde {#controlling-execution-order}

Wanneer meerdere luisteraars zijn geregistreerd, kunt u hun uitvoeringsvolgorde beheersen met de `@AppListenerPriority` annotatie. Dit is vooral belangrijk wanneer luisteraars afhankelijk van elkaar zijn of wanneer bepaalde initialisatie moet plaatsvinden voordat andere dat doen.

Prioriteitswaarden werken in oplopende volgorde - **lagere nummers worden als eerste uitgevoerd**. De standaardprioriteit is 10, dus luisteraars zonder expliciete prioriteitsannotaties worden uitgevoerd na die met lagere prioriteitswaarden.

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // Wordt als eerste uitgevoerd - kritieke beveiligingsinstelling
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

### Uitvoeringsstroom met App-haakjes {#execution-flow-with-app-hooks}

Naast het beheersen van de volgorde tussen meerdere luisteraars, is het belangrijk om te begrijpen hoe luisteraars interageren met de eigen levenscyclushaakjes van de `App`-klasse. Voor elke levenscyclusgebeurtenis volgt het framework een specifieke uitvoeringsvolgorde die bepaalt wanneer uw luisteraars draaien ten opzichte van de ingebouwde haakjes van de app.

Het diagram hieronder illustreert deze uitvoeringsstroom, die de precieze timing toont van wanneer `AppLifecycleListener`-methoden worden aangeroepen in relatie tot de bijbehorende `App`-haakjes:

<div align="center">

![AppLifecycleListener luisteraars VS `App` haakjes](/img/lifecycle-listeners.svg)

</div>

## Foutafhandeling {#error-handling}

Uitzonderingen die door luisteraars worden opgegooid, worden gelogd, maar verhinderen niet dat andere luisteraars worden uitgevoerd of de app draait. Behandel altijd uitzonderingen binnen uw luisteraars:

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
