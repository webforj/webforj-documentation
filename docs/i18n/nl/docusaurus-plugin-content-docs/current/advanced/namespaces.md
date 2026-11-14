---
title: Naamruimten
sidebar_position: 40
description: >-
  Share thread-safe key-value state across sessions, thread groups, or the
  entire JVM using Private, Group, and Global namespaces.
_i18n_hash: f932a175d81df4147cae1335991db3d5
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Naamruimten in webforJ bieden een manier om gedeelde gegevens op te slaan en op te halen binnen verschillende scopes in een webapp. Ze maken communicatie tussen componenten en sessies mogelijk zonder afhankelijk te zijn van traditionele opslagtechnieken zoals sessieattributen of statische velden. Dankzij deze abstractie kunnen ontwikkelaars de status op een gecontroleerde, threadveilige manier inkapselen en benaderen. Naamruimten zijn ideaal voor het bouwen van samenwerkingstools voor meerdere gebruikers of voor het onderhouden van consistente globale instellingen. Ze maken het mogelijk om gegevens veilig en efficiënt te coördineren.

## Wat is een naamruimte? {#whats-a-namespace}

Een naamruimte is een benoemde container waarin sleutel-waardeparen worden opgeslagen. Afhankelijk van het type naamruimte dat je gebruikt, zijn deze waarden toegankelijk en aanpasbaar vanuit verschillende delen van je app. Je kunt het zien als een threadveilige, gedistribueerde map met ingebouwde gebeurtenisafhandeling en vergrendelingsmechanismen.

### Wanneer gebruik je naamruimten? {#when-to-use-namespaces}

Gebruik naamruimten wanneer:

- Je waarden wilt delen tussen gebruikerssessies of app-componenten.
- Je met listeners wilt reageren op wijzigingen in waarden.
- Je fijnmazige vergrendeling nodig hebt voor kritieke secties.
- Je de status efficiënt wilt bewaren en ophalen in je app.

### Typen naamruimten {#types-of-namespaces}

webforJ biedt drie typen naamruimten:

| Type        | Scope                                                                                                                       | Typisch gebruik                                   |
| ----------- | --------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------- |
| **Private** | Gedeeld door clients die hetzelfde voorvoegsel en dezelfde naam gebruiken. Het geheugen wordt automatisch vrijgegeven zodra er geen verwijzingen meer zijn. | Gedeelde status tussen verwante gebruikerssessies. |
| **Group**   | Gedeeld door alle threads die door dezelfde bovenliggende thread zijn gestart.                                               | Status coördineren binnen een threadgroep.        |
| **Global**  | Toegankelijk voor alle serverthreads (JVM-breed). Het geheugen blijft behouden totdat sleutels expliciet worden verwijderd.  | Applicatiebreed gedeelde status.                  |

:::tip Een standaard kiezen - geef de voorkeur aan `PrivateNamespace`
Gebruik bij twijfel een `PrivateNamespace`. Deze biedt veilige, afgebakende deling tussen verwante sessies zonder globale of serverbrede status te beïnvloeden. Daarmee is dit een betrouwbare standaardkeuze voor de meeste applicaties.
:::

## Een naamruimte maken en gebruiken {#creating-and-using-a-namespace}

Naamruimten worden gemaakt door een van de beschikbare typen te instantiëren. Elk type bepaalt hoe en waar de gegevens worden gedeeld. In de onderstaande voorbeelden zie je hoe je een naamruimte maakt en met de waarden ervan werkt.

### Naamruimte `Private` {#private-namespace}

De naam van een private naamruimte bestaat uit twee delen:

- **Prefix**: Een door de ontwikkelaar gedefinieerde identificatie die uniek moet zijn voor je app of module om conflicten te voorkomen.
- **Basisnaam**: De specifieke naam voor de gedeelde context of gegevens die je wilt beheren.

Samen vormen ze de volledige naam van de naamruimte, volgens deze indeling:

```text
prefix + "." + baseName
```

Bijvoorbeeld: `"myApp.sharedState"`.

Naamruimten die met hetzelfde voorvoegsel en dezelfde basisnaam worden gemaakt, verwijzen altijd naar _dezelfde onderliggende instantie_. Zo is gedeelde toegang consistent bij alle aanroepen van `PrivateNamespace` met dezelfde identificaties.

```java
// Create or retrieve a private namespace
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Je kunt controleren of de naamruimte al bestaat voordat je deze maakt:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Richtlijnen voor naamgeving
Volg deze regels bij het benoemen van een `PrivateNamespace`:

- Beide delen moeten niet-leeg zijn.
- Elk deel moet met een letter beginnen.
- Alleen afdrukbare tekens zijn toegestaan.
- Witruimte is niet toegestaan.

Voorbeelden:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (te algemeen en waarschijnlijk conflicterend)
:::

### Naamruimten `Group` en `Global` {#group-and-global-namespaces}

Naast `PrivateNamespace` biedt webforJ nog twee typen voor bredere deelcontexten. Deze zijn handig wanneer de status langer behouden moet blijven dan één sessie of threadgroep.

- **Global Namespace**: Toegankelijk voor alle serverthreads (JVM-breed).
- **Group Namespace**: Gedeeld door threads die van dezelfde bovenliggende thread afkomstig zijn.

```java
// Global shared state, accessible application-wide
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Group-specific state, limited to threads sharing a common parent
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Werken met waarden {#working-with-values}

Naamruimten bieden een consistente interface voor het beheren van gedeelde gegevens met sleutel-waardeparen. Dit omvat het instellen, ophalen en verwijderen van waarden, het synchroniseren van toegang en het in realtime volgen van wijzigingen.

### Waarden instellen en verwijderen {#setting-and-removing-values}

Gebruik `put()` om een waarde onder een specifieke sleutel op te slaan. Als de sleutel momenteel vergrendeld is, wacht de methode totdat de vergrendeling wordt opgeheven of de time-out verstrijkt.

```java
// Waits up to 20ms (default) to set the value
ns.put("username", "admin");

// Specify custom timeout in milliseconds
ns.put("config", configObject, 100);
```

Een sleutel uit de naamruimte verwijderen:

```java
ns.remove("username");
```

Zowel `put()` als `remove()` zijn blokkerende bewerkingen als de doelsleutel vergrendeld is. Als de time-out verstrijkt voordat de vergrendeling wordt opgeheven, wordt een `NamespaceLockedException` gegenereerd.

Gebruik `atomicPut()` voor veilige gelijktijdige updates waarbij je alleen de waarde hoeft te overschrijven. Deze methode vergrendelt de sleutel, schrijft de waarde en heft de vergrendeling in één stap op:

```java
ns.atomicPut("counter", 42);
```

Zo voorkom je raceconditions en hoef je in eenvoudige updatesituaties niet handmatig te vergrendelen.

### Waarden ophalen {#getting-values}

Gebruik `get()` om een waarde op te halen:

```java
Object value = ns.get("username");
```

Als de sleutel niet bestaat, wordt een `NoSuchElementException` gegenereerd. Gebruik `getOrDefault()` om uitzonderingen te voorkomen:

```java
Object value = ns.getOrDefault("username", "guest");
```

Controleren of een sleutel is gedefinieerd:

```java
if (ns.contains("username")) {
  // key exists
}
```

Gebruik `computeIfAbsent()` om een waarde alleen te initialiseren als deze ontbreekt:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Dit is handig voor gedeelde waarden die één keer worden aangemaakt en vervolgens opnieuw worden gebruikt, zoals sessietokens, configuratieblokken of gecachte gegevens.

### Handmatig vergrendelen {#manual-locking}

Gebruik handmatige vergrendeling als je meerdere bewerkingen op dezelfde sleutel moet uitvoeren of meerdere sleutels moet coördineren.

```java
ns.setLock("flag", 500); // Wait up to 500ms for the lock

// Critical section starts
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Critical section ends

ns.removeLock("flag");
```

Gebruik dit patroon wanneer een reeks bewerkingen atomair moet worden uitgevoerd tijdens het lezen en schrijven. Zorg er altijd voor dat de vergrendeling wordt opgeheven, zodat andere threads niet worden geblokkeerd.

### Luisteren naar wijzigingen {#listening-for-changes}

Naamruimten ondersteunen gebeurtenislisteners waarmee je kunt reageren wanneer waarden worden benaderd of gewijzigd. Dit is handig voor situaties zoals:

- Toegang tot gevoelige sleutels registreren of controleren
- Updates activeren wanneer een configuratiewaarde verandert
- Wijzigingen in gedeelde status in apps voor meerdere gebruikers volgen

#### Beschikbare listenermethoden {#available-listener-methods}

| Methode                  | Trigger                          | Scope               |
|--------------------------|----------------------------------|---------------------|
| `onAccess`               | Er wordt een sleutel gelezen     | Hele naamruimte     |
| `onChange`               | Er wordt een sleutel gewijzigd   | Hele naamruimte     |
| `onKeyAccess("key")`     | Er wordt een specifieke sleutel gelezen | Per sleutel        |
| `onKeyChange("key")`     | Er wordt een specifieke sleutel gewijzigd | Per sleutel        |

Elke listener ontvangt een gebeurtenisobject met daarin:
- De naam van de sleutel
- De oude waarde
- De nieuwe waarde
- Een verwijzing naar de naamruimte

#### Voorbeeld: Reageren op een wijziging van een willekeurige sleutel {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Key changed: " + event.getVariableName());
  System.out.println("Old value: " + event.getOldValue());
  System.out.println("New value: " + event.getNewValue());
});
```

#### Voorbeeld: Toegang tot een specifieke sleutel volgen {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Token was accessed: " + event.getNewValue());
});
```

Listeners retourneren een `ListenerRegistration`-object waarmee je de listener later kunt afmelden:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // logic
});
reg.remove();
```

## Voorbeeld: Spelstatus delen in Boter-kaas-en-eieren {#example-sharing-game-state-in-tic-tac-toe}

De [webforJ-demo van Boter-kaas-en-eieren](https://github.com/webforj/webforj-tictactoe) is een eenvoudig spel voor twee spelers waarin de beurten tussen gebruikers worden gedeeld. Het project laat zien hoe `Namespace` kan worden gebruikt om de status te coördineren zonder afhankelijk te zijn van externe tools zoals databases of API's.

In dit voorbeeld wordt een gedeeld Java-spelobject opgeslagen in een `PrivateNamespace`, zodat meerdere clients met dezelfde spellogica kunnen werken. De naamruimte fungeert als centrale container voor de spelstatus en zorgt ervoor dat:

- Beide spelers dezelfde bordupdates zien
- Beurten worden gesynchroniseerd
- De spellogica tussen sessies wordt gedeeld

Er zijn geen externe services nodig, zoals REST of WebSockets. Alle coördinatie verloopt via naamruimten, wat laat zien hoe die met minimale infrastructuur de gedeelde status in realtime kunnen beheren.

Bekijk de code: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<VideoPlayer video="namespaces/tic-tac-toe" width={1660} height={902} title="Tic tac toe" />
