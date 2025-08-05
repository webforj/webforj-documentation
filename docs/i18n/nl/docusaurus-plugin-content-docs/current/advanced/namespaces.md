---
title: Namespaces
sidebar_position: 30
_i18n_hash: 7e34cfb824d0e1e4637bd40f4f1133cc
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Namespaces in webforJ bieden een mechanisme voor het opslaan en ophalen van gedeelde gegevens over verschillende scopes in een webapplicatie. Ze mogelijk maken voor inter-component en cross-sessie gegevenscommunicatie zonder afhankelijk te zijn van traditionele opslagtechnieken zoals sessie-attributen of statische velden. Deze abstractie stelt ontwikkelaars in staat om de toestand op een gecontroleerde, veilige manier voor threads te encapsuleren en toegankelijk te maken. Namespaces zijn ideaal voor het bouwen van samenwerkingshulpmiddelen voor meerdere gebruikers of eenvoudigweg het onderhouden van consistente globale instellingen, en stellen je in staat om gegevens veilig en efficiënt te coördineren.

## Wat is een namespace? {#whats-a-namespace}

Een namespace is een genummerde container die sleutel-waarde paren opslaat. Deze waarden kunnen worden geopend en gewijzigd in verschillende delen van je app, afhankelijk van het type namespace dat je gebruikt. Je kunt het zien als een thread-veilige, gedistribueerde kaart met ingebouwde gebeurtenisafhandeling en vergrendelingsmechanismen.

### Wanneer namespaces te gebruiken {#when-to-use-namespaces}

Gebruik namespaces wanneer:

- Je waarden moet delen over gebruikerssessies of appcomponenten.
- Je wilt reageren op waardewijzigingen via luisteraars.
- Je fijne vergrendeling nodig hebt voor kritische secties.
- Je efficiënt de toestand wilt behouden en ophalen in je app.

### Typen namespaces {#types-of-namespaces}

webforJ biedt drie soorten namespaces:

| Type        | Scope                                                                                                               | Typisch Gebruik                             |
| ----------- | ------------------------------------------------------------------------------------------------------------------- | ------------------------------------------- |
| **Privé**   | Gedeeld tussen clients die dezelfde prefix en naam gebruiken. Geheugen wordt automatisch vrijgemaakt wanneer er geen referenties meer zijn. | Gedeelde toestand tussen gerelateerde gebruikerssessies. |
| **Groep**   | Gedeeld door alle threads die zijn voortgekomen uit dezelfde ouderthread.                                           | Coördineren van toestand binnen een threadgroep. |
| **Globaal** | Toegankelijk voor alle serverthreads (JVM-breed). Geheugen wordt behouden totdat de sleutels expliciet worden verwijderd. | Toepassing-brede gedeelde toestand.         |

:::tip Een standaard kiezen - Geef de voorkeur aan `PrivateNamespace`
Als je het niet zeker weet, gebruik dan een `PrivateNamespace`. Het biedt veilige, scope-gebonden delen tussen gerelateerde sessies zonder de globale of server-brede toestand te beïnvloeden. Dit maakt het een betrouwbare standaard voor de meeste applicaties.
:::

## Een namespace maken en gebruiken {#creating-and-using-a-namespace}

Namespaces worden gemaakt door een van de beschikbare typen te instantiëren. Elke type definieert hoe en waar de gegevens worden gedeeld. De onderstaande voorbeelden tonen aan hoe je een namespace kunt maken en met zijn waarden kunt omgaan.

### `Privé` namespace {#private-namespace}

De naam van de privé namespace bestaat uit twee delen:

- **Prefix**: Een door de ontwikkelaar gedefinieerde identificatie die uniek moet zijn voor je app of module om conflicten te voorkomen.
- **Basisnaam** : De specifieke naam voor de gedeelde context of gegevens die je wilt beheren.

Samen vormen ze de volledige namespace-naam volgens het volgende formaat:

```text
prefix + "." + baseName
```

Bijvoorbeeld, `"myApp.sharedState"`.

Namespaces die zijn gemaakt met dezelfde prefix en basisnaam verwijzen altijd naar dezelfde onderliggende instantie. Dit zorgt voor consistente gedeelde toegang bij alle oproepen naar `PrivateNamespace` met dezelfde identificatoren.

```java
// Maak of haal een privé namespace op
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Je kunt de aanwezigheid controleren voordat je deze maakt:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Naamgevingsrichtlijnen
Volg deze regels bij het benoemen van een `PrivateNamespace`:

- Beide delen moeten niet-leeg zijn.
- Elk moet beginnen met een letter.
- Alleen afdrukbare tekens zijn toegestaan.
- Witruimten zijn niet toegestaan.

Voorbeelden:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (te algemeen, waarschijnlijk conflicten)
:::

### `Groep` en `Globaal` namespaces {#group-and-global-namespaces}

Naast `PrivateNamespace` biedt webforJ nog twee andere typen voor bredere deelcontexten. Deze zijn nuttig wanneer de toestand moet blijven bestaan buiten een enkele sessie of threadgroep.

- **Globale Namespace**: Toegankelijk voor alle serverthreads (JVM-breed).
- **Groep Namespace**: Gedeeld onder threads die afkomstig zijn van dezelfde ouder.

```java
// Globale gedeelde toestand, toegankelijk over de applicatie
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Groep-specifieke toestand, beperkt tot threads die een gemeenschappelijke ouder delen
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Werken met waarden {#working-with-values}

Namespaces bieden een consistente interface voor het beheren van gedeelde gegevens via sleutel-waarde paren. Dit omvat het instellen, ophalen, verwijderen van waarden, synchroniseren van toegang en het observeren van wijzigingen in realtime.

### Waarden instellen en verwijderen {#setting-and-removing-values}

Gebruik `put()` om een waarde op te slaan onder een specifieke sleutel. Als de sleutel momenteel vergrendeld is, wacht de methode tot de vergrendeling wordt vrijgegeven of de timeout verstrijkt.

```java
// Wacht tot 20ms (standaard) om de waarde in te stellen
ns.put("username", "admin");

// Specificeer een aangepaste timeout in milliseconden
ns.put("config", configObject, 100);
```

Om een sleutel uit de namespace te verwijderen:

```java
ns.remove("username");
```

Zowel `put()` als `remove()` zijn blokkeringsope rato's als de doel sleutel is vergrendeld. Als de timeout verstrijkt voordat de vergrendeling is vrijgegeven, wordt een `NamespaceLockedException` opgegooid.

Voor veilige gelijktijdige updates waarbij je alleen de waarde hoeft te overschrijven, gebruik `atomicPut()`. Het vergrendelt de sleutel, schrijft de waarde en geeft de vergrendeling in één stap weer vrij:

```java
ns.atomicPut("counter", 42);
```

Dit voorkomt raceomstandigheden en vermijdt de noodzaak voor handmatige vergrendeling in eenvoudige update-scenario's.

### Waarden ophalen {#getting-values}

Om een waarde op te halen, gebruik `get()`:

```java
Object value = ns.get("username");
```

Als de sleutel niet bestaat, gooit dit een `NoSuchElementException`. Om uitzonderingen te vermijden, gebruik `getOrDefault()`:

```java
Object value = ns.getOrDefault("username", "guest");
```

Om te controleren of een sleutel gedefinieerd is:

```java
if (ns.contains("username")) {
  // sleutel bestaat
}
```

Als je een waarde alleen wilt initieel laden wanneer deze ontbreekt, gebruik `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Dit is nuttig voor gedeelde waarden die eenmaal worden aangemaakt en hergebruikt, zoals sessietokens, configuratieblokken of geheugengegevens.

### Handmatige vergrendeling {#manual-locking}

Als je meerdere bewerkingen op dezelfde sleutel moet uitvoeren of coördineren tussen meerdere sleutels, gebruik dan handmatige vergrendeling.

```java
ns.setLock("flag", 500); // Wacht tot 500ms op de vergrendeling

// Kritieke sectie begint
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Kritieke sectie eindigt

ns.removeLock("flag");
```

Gebruik dit patroon wanneer een reeks bewerkingen atomisch moet worden uitgevoerd over lees- en schrijfbewerkingen. Zorg ervoor dat de vergrendeling altijd wordt vrijgegeven om andere threads niet te blokkeren.

### Luisteren naar wijzigingen {#listening-for-changes}

Namespaces ondersteunen gebeurtenisluiters waarmee je kunt reageren op toegang of wijziging van waarden. Dit is nuttig voor scenario's zoals:

- Loggen of auditen van toegang tot gevoelige sleutels
- Triggeren van updates wanneer een configuratiewaarde verandert
- Monitoren van gedeelde toestandswijzigingen in multi-gebruikers apps

#### Beschikbare luistermethoden {#available-listener-methods}

| Methode                     | Trigger                          | Scope              |
|----------------------------|----------------------------------|--------------------|
| `onAccess`                 | Een sleutel wordt gelezen        | Hele namespace      |
| `onChange`                 | Een sleutel wordt gewijzigd      | Hele namespace      |
| `onKeyAccess("key")`       | Een specifieke sleutel wordt gelezen | Per sleutel        |
| `onKeyChange("key")`       | Een specifieke sleutel wordt gewijzigd | Per sleutel       |

Elke luisteraar ontvangt een gebeurtenisobject met:
- De sleutelnaam
- De oude waarde
- De nieuwe waarde
- Een verwijzing naar de namespace

#### Voorbeeld: Reageren op een wijziging van een sleutel {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Sleutel gewijzigd: " + event.getVariableName());
  System.out.println("Oude waarde: " + event.getOldValue());
  System.out.println("Nieuwe waarde: " + event.getNewValue());
});
```

#### Voorbeeld: Toegang tot een specifieke sleutel volgen {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Token werd geopend: " + event.getNewValue());
});
```

Listeners geven een `ListenerRegistration` object terug dat je kunt gebruiken om de listener later af te melden:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // logica
});
reg.remove();
```

## Voorbeeld: Delen van speeltoestand in Tic-Tac-Toe {#example-sharing-game-state-in-tic-tac-toe}

De [webforJ Tic-Tac-Toe demo](https://github.com/webforj/webforj-tictactoe) biedt een eenvoudige twee-speler game waar beurten worden gedeeld tussen gebruikers. Het project demonstreert hoe `Namespace` kan worden gebruikt om toestanden te coördineren zonder afhankelijk te zijn van externe tools zoals databases of API's.

In dit voorbeeld wordt een gedeeld Java-gameobject opgeslagen in een `PrivateNamespace`, waardoor meerdere clients met dezelfde game-logica kunnen interageren. De namespace dient als een centrale container voor de speelse toestand, waardoor ervoor wordt gezorgd dat:

- Beide spelers consistente bordupdates zien
- Beurten worden gesynchroniseerd
- De game-logica wordt gedeeld over sessies

Er zijn geen externe services (zoals REST of WebSockets) nodig. Alle coördinatie gebeurt via namespaces, wat hun vermogen benadrukt om gedeelde toestand in realtime te beheren met minimale infrastructuur.

Verken de code: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
