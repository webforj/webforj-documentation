---
sidebar_position: 10
title: Lifecycle Listeners
sidebar_class_name: new-content
_i18n_hash: 475cc2842226c605bbe7f2ee931955dd
---
<!-- vale off -->
# Lifecycle Luisteraars <DocChip chip='since' label='25.02' />
<!-- vale on -->

De `AppLifecycleListener` interface stelt externe code in staat om app lifecycle-evenementen te observeren en daarop te reageren. Door deze interface te implementeren, kunt u code uitvoeren op specifieke momenten tijdens de opstart en het afsluiten van de app, zonder de `App` klasse zelf te wijzigen.

Lifecycle luisteraars worden automatisch ontdekt en geladen tijdens runtime via configuratiebestanden van serviceproviders. Elke app-instantie ontvangt zijn eigen set van luisteraarinstanties, waardoor isolatie tussen verschillende apps die in dezelfde omgeving draaien behouden blijft.

## Wanneer lifecycle luisteraars te gebruiken {#when-to-use-lifecycle-listeners}

Gebruik lifecycle luisteraars wanneer u moet:
- Hulpbronnen of diensten initialiseren voordat een app draait
- Hulpbronnen opruimen wanneer een app beëindigt  
- Cross-cutting concerns toevoegen zonder de `App` klasse te wijzigen
- Plugin-architecturen bouwen

## De `AppLifecycleListener` interface {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
    default void onWillRun(App app) {}
    default void onDidRun(App app) {}
    default void onWillTerminate(App app) {}
    default void onDidTerminate(App app) {}
}
```

:::info App isolatie
Elke app-instantie ontvangt zijn eigen set van luisteraarinstanties:
- Luisteraars zijn geïsoleerd tussen verschillende apps
- Statische velden in luisteraars worden niet gedeeld tussen apps
- Luisteraarinstanties worden gemaakt wanneer de app start en worden vernietigd wanneer deze eindigt

Als u gegevens tussen apps moet delen, gebruik dan externe opslagmechanismen zoals databases of gedeelde diensten.
:::

### Lifecycle evenementen {#lifecycle-events}

| Evenement | Wanneer Aangeroepen | Veelvoorkomende Toepassingen |
|-------|-------------|-------------|
| `onWillRun` | Voordat `app.run()` wordt uitgevoerd | Hulpbronnen initialiseren, diensten configureren |
| `onDidRun` | Nadat `app.run()` succesvol is voltooid | Achtergrondtaken starten, succesvolle opstart loggen |
| `onWillTerminate` | Voor beëindiging van de app | Toestand opslaan, voorbereiden op afsluiten |
| `onDidTerminate` | Na beëindiging van de app | Hulpbronnen opruimen, laatste logging |

## Een lifecycle luisteraar creëren {#creating-a-lifecycle-listener}

### Basisimplementatie {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;

public class StartupListener implements AppLifecycleListener {
    
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

Maak een configuratiebestand voor de serviceprovider:

**Bestand**: `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip AutoService gebruiken
Het is gemakkelijk om te vergeten servicebeschrijvingen bij te werken. Gebruik Google's [AutoService](https://github.com/google/auto/blob/main/service/README.md) om het servicebestand automatisch te genereren:

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
    // Implementatie
}
```
:::

## Controle van uitvoeringsvolgorde {#controlling-execution-order}

Wanneer meerdere luisteraars zijn geregistreerd, kunt u hun uitvoeringsvolgorde controleren met behulp van de annotatie `@AppListenerPriority`. Dit is vooral belangrijk wanneer luisteraars afhankelijkheden van elkaar hebben of wanneer bepaalde initiëringen moeten plaatsvinden voordat andere dat doen.

Prioriteitswaarden werken in oplopende volgorde - **lagere nummers worden als eerste uitgevoerd**. De standaardprioriteit is 10, dus luisteraars zonder expliciete prioriteitsannotaties worden uitgevoerd na die met lagere prioriteitswaarden.

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

### Uitvoeringsflow met App hooks {#execution-flow-with-app-hooks}

Naast het controleren van de volgorde tussen meerdere luisteraars, is het belangrijk om te begrijpen hoe luisteraars interageren met de eigen lifecycle hooks van de `App` klasse. Voor elk lifecycle-evenement volgt het framework een specifieke uitvoeringsvolgorde die bepaalt wanneer uw luisteraars worden uitgevoerd in relatie tot de ingebouwde hooks van de app.

Het diagram hieronder illustreert deze uitvoeringsflow en toont het precieze tijdstip waarop `AppLifecycleListener` methoden worden aangeroepen in relatie tot de overeenkomstige `App` hooks: 

<div align="center">

![AppLifecycleListener luisteraars VS `App` hooks  ](/img/lifecycle-listeners.svg)

</div>


## Foutafhandeling {#error-handling}

Uitzonderingen die door luisteraars worden opgegooid, worden gelogd maar voorkomen niet dat andere luisteraars worden uitgevoerd of dat de app wordt uitgevoerd. Behandel altijd uitzonderingen binnen uw luisteraars:

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
