---
title: Namespaces
sidebar_position: 40
description: >-
  Share thread-safe key-value state across sessions, thread groups, or the
  entire JVM using Private, Group, and Global namespaces.
_i18n_hash: fb5d7a0ef2a65790f0692612c07d9044
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Namespaces in webforJ bieden een mechanisme voor het opslaan en ophalen van gedeelde gegevens over verschillende scope's in een webapplicatie. Ze stellen intercomponent- en cross-sessie gegevenscommunicatie mogelijk zonder gebruik te maken van traditionele opslagtechnieken zoals sessie-attributen of statische velden. Deze abstractie stelt ontwikkelaars in staat om status op een gecontroleerde, threadveilige manier te verpakken en te benaderen. Namespaces zijn ideaal voor het bouwen van samenwerkingshulpmiddelen voor meerdere gebruikers of gewoon voor het behouden van consistente globale instellingen, en stellen je in staat om gegevens veilig en efficiënt te coördineren.

## Wat is een namespace? {#whats-a-namespace}

Een namespace is een benoemde container die sleutel-waarde paren opslaat. Deze waarden kunnen afhankelijk van het type namespace dat je gebruikt, vanuit verschillende delen van je app worden benaderd en gewijzigd. Beschouw het als een thread-veilige, gedistribueerde kaart met ingebouwde gebeurtenishandling en vergrendelmechanismen.

### Wanneer namespaces te gebruiken {#when-to-use-namespaces}

Gebruik namespaces wanneer:

- Je waarden wilt delen tussen gebruikerssessies of app-componenten.
- Je wilt reageren op waarde veranderingen via luisteraars.
- Je fijne vergrendeling vereist voor kritieke secties.
- Je status efficiënt wilt opslaan en ophalen in je app.

### Typen namespaces {#types-of-namespaces}

webforJ biedt drie typen namespaces:

| Type        | Scope                                                                                                               | Typisch gebruik                               |
| ----------- | ------------------------------------------------------------------------------------------------------------------- | --------------------------------------------- |
| **Privé**   | Gedeeld tussen clients die dezelfde prefix en naam gebruiken. Geheugen wordt automatisch vrijgegeven wanneer er geen referenties meer zijn. | Gedeelde status tussen gerelateerde gebruikerssessies. |
| **Groep**   | Gedeeld door alle threads die zijn aangemaakt vanuit dezelfde bovenliggende thread.                                   | Coördineren van status binnen een threadgroep.  |
| **Globaal** | Toegankelijk over alle serverthreads (JVM-breed). Geheugen wordt behouden totdat sleutels expliciet worden verwijderd.  | Applicatie-brede gedeelde status.             |

:::tip Kies een standaard - Geef de voorkeur aan `PrivateNamespace`
Wanneer je twijfelt, gebruik dan een `PrivateNamespace`. Het biedt veilige, scoped sharing tussen gerelateerde sessies zonder de globale of server-brede status te beïnvloeden. Dit maakt het een betrouwbare standaard voor de meeste applicaties.
:::

## Een namespace creëren en gebruiken {#creating-and-using-a-namespace}

Namespaces worden gemaakt door één van de beschikbare types te instantiëren. Elk type definieert hoe en waar de gegevens worden gedeeld. De onderstaande voorbeelden demonstreren hoe je een namespace kunt creëren en interactie kunt hebben met de waarden ervan.

### `Privé` namespace {#private-namespace}

De naam van de privé namespace bestaat uit twee delen:

- **Prefix**: Een door de ontwikkelaar gedefinieerde identificator die uniek moet zijn voor jouw app of module om conflicten te voorkomen.
- **Basis naam**: De specifieke naam voor de gedeelde context of gegevens die je wilt beheren.

Samen vormen ze de volledige naam van de namespace met het formaat:

```text
prefix + "." + baseName
```

Bijvoorbeeld, `"myApp.sharedState"`.

Namespaces die met dezelfde prefix en basisnaam zijn gemaakt, verwijzen altijd naar de _zelfde onderliggende instantie_. Dit zorgt voor consistente gedeelde toegang bij alle aanroepen van `PrivateNamespace` met behulp van dezelfde identificatoren.

```java
// Maak of haal een privé namespace op
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Je kunt controleren op aanwezigheid voordat je deze maakt:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Naamgevingsrichtlijnen
Bij het benoemen van een `PrivateNamespace` volg je deze regels:

- Beide delen moeten niet leeg zijn.
- Elke moet beginnen met een letter.
- Alleen afdrukbare tekens zijn toegestaan.
- Witruimte is niet toegestaan.

Voorbeelden:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (te algemeen, waarschijnlijk in conflict)
:::

### `Groep` en `Globaal` namespaces {#group-and-global-namespaces}

Naast PrivateNamespace biedt webforJ twee andere typen voor bredere deelcontexten. Deze zijn nuttig wanneer status moet voortduren voorbij een enkele sessie of threadgroep.

- **Globale Namespace**: Toegankelijk over alle serverthreads (JVM-breed).
- **Groep Namespace**: Gedeeld onder threads die afkomstig zijn van dezelfde bovenliggende.

```java
// Globale gedeelde status, toegankelijk applicatie-breed
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Groep-specifieke status, beperkt tot threads die een gemeenschappelijke ouder delen
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Werken met waarden {#working-with-values}

Namespaces bieden een consistente interface voor het beheren van gedeelde gegevens via sleutel-waarde paren. Dit omvat het instellen, ophalen, verwijderen van waarden, synchroniseren van toegang, en observeren van veranderingen in realtime.

### Waarden instellen en verwijderen {#setting-and-removing-values}

Gebruik `put()` om een waarde op te slaan onder een specifieke sleutel. Als de sleutel momenteel is vergrendeld, wacht de methode totdat de vergrendeling is vrijgegeven of de timeout is verstreken.

```java
// Wacht tot 20 ms (standaard) om de waarde in te stellen
ns.put("username", "admin");

// Specificeer aangepaste timeout in milliseconden
ns.put("config", configObject, 100);
```

Om een sleutel uit de namespace te verwijderen:

```java
ns.remove("username");
```

Zowel `put()` als `remove()` zijn blokkeringoperaties als de doel sleutel is vergrendeld. Als de timeout verstrijkt voordat de vergrendeling is vrijgegeven, wordt een `NamespaceLockedException` opgegooid.

Voor veilige gelijktijdige updates waarbij je alleen de waarde wilt overschrijven, gebruik `atomicPut()`. Het vergrendelt de sleutel, schrijft de waarde en geeft de vergrendeling in één stap vrij:

```java
ns.atomicPut("counter", 42);
```

Dit voorkomt race-voorwaarden en vermijdt de noodzaak voor handmatige vergrendeling in eenvoudige update scenario's.

### Waarden ophalen {#getting-values}

Om een waarde op te halen, gebruik `get()`:

```java
Object value = ns.get("username");
```

Als de sleutel niet bestaat, wordt hiermee een `NoSuchElementException` opgegooid. Om uitzonderingen te vermijden, gebruik `getOrDefault()`:

```java
Object value = ns.getOrDefault("username", "guest");
```

Om te controleren of een sleutel gedefinieerd is:

```java
if (ns.contains("username")) {
  // sleutel bestaat
}
```

Als je een waarde alleen wilt lazy initialiseren wanneer deze ontbreekt, gebruik dan `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Dit is nuttig voor gedeelde waarden die eenmaal worden aangemaakt en hergebruikt, zoals sessietokens, configuratieblokken of gecachte gegevens.

### Handmatige vergrendeling {#manual-locking}

Als je meerdere operaties op dezelfde sleutel moet uitvoeren of coördineren over meerdere sleutels, gebruik handmatige vergrendeling.

```java
ns.setLock("flag", 500); // Wacht tot 500 ms voor de vergrendeling

// Kritieke sectie begint
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Kritieke sectie eindigt

ns.removeLock("flag");
```

Gebruik dit patroon wanneer een reeks operaties atomisch moet worden uitgevoerd over lees- en schrijfbewerkingen. Zorg er altijd voor dat de vergrendeling wordt vrijgegeven om te voorkomen dat andere threads worden geblokkeerd.

### Luisteren naar veranderingen {#listening-for-changes}

Namespaces ondersteunen gebeurtenislisteren die je in staat stellen te reageren op toegang of wijziging van waarden. Dit is nuttig voor scenario's zoals:

- Loggen of auditen van toegang tot gevoelige sleutels
- Triggeren van updates wanneer een configuratiewaarde verandert
- Monitoren van gedeelde statusveranderingen in multi-gebruikers apps

#### Beschikbare luistermethoden {#available-listener-methods}

| Methode                     | Trigger                          | Scope              |
|----------------------------|----------------------------------|--------------------|
| `onAccess`                 | Elke sleutel wordt gelezen       | Hele namespace      |
| `onChange`                 | Elke sleutel wordt gewijzigd     | Hele namespace      |
| `onKeyAccess("key")`      | Een specifieke sleutel wordt gelezen | Per sleutel        |
| `onKeyChange("key")`      | Een specifieke sleutel wordt gewijzigd | Per sleutel        |

Elke luisteraar ontvangt een gebeurtenisobject met:
- De sleutelnaam
- De oude waarde
- De nieuwe waarde
- Een referentie naar de namespace

#### Voorbeeld: Reageer op elke sleutelwijziging {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Sleutel veranderd: " + event.getVariableName());
  System.out.println("Oude waarde: " + event.getOldValue());
  System.out.println("Nieuwe waarde: " + event.getNewValue());
});
```

#### Voorbeeld: Houd toegang tot een specifieke sleutel bij {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Token werd benaderd: " + event.getNewValue());
});
```

Luisteraars retourneren een `ListenerRegistration` object dat je kunt gebruiken om de luisteraar later te unregisteren:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // logica
});
reg.remove();
```

## Voorbeeld: Gedeelde spelstatus in Tic-Tac-Toe {#example-sharing-game-state-in-tic-tac-toe}

De [webforJ Tic-Tac-Toe demo](https://github.com/webforj/webforj-tictactoe) biedt een eenvoudige tweespelersgame waarbij beurten worden gedeeld tussen gebruikers. Het project demonstreert hoe `Namespace` kan worden gebruikt om status te coördineren zonder te vertrouwen op externe hulpmiddelen zoals databases of API's.

In dit voorbeeld wordt een gedeeld Java-gameobject opgeslagen in een `PrivateNamespace`, waardoor meerdere clients kunnen interageren met dezelfde spel logica. De namespace dient als een centrale container voor de spelstatus, waardoor wordt gegarandeerd dat:

- Beide spelers consistente bordupdates zien
- Beurten gesynchroniseerd zijn
- De spel logica wordt gedeeld over sessies

Geen externe services (zoals REST of WebSockets) zijn nodig. Alle coördinatie gebeurt via namespaces, wat hun vermogen benadrukt om gedeelde status in realtime te beheren met minimale infrastructuur.

Verken de code: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
