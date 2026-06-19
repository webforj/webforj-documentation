---
title: Namespaces
sidebar_position: 40
description: >-
  Share thread-safe key-value state across sessions, thread groups, or the
  entire JVM using Private, Group, and Global namespaces.
_i18n_hash: 82037bcac961ffa8fefb90bf7579a3af
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Namespaces in webforJ bieden een mechanisme voor het opslaan en ophalen van gedeelde data over verschillende scopes in een webapp. Ze maken communicatie van data tussen componenten en over sessies heen mogelijk zonder afhankelijk te zijn van traditionele opslagtechnieken zoals sessie-attributen of statische velden. Deze abstractie stelt ontwikkelaars in staat om status te encapuleren en op een gecontroleerde, thread-veilige manier toegang te krijgen. Namespaces zijn ideaal voor het bouwen van samenwerkingshulpmiddelen voor meerdere gebruikers of gewoon om consistente globale instellingen te onderhouden, en laten je toe om data veilig en efficiënt te coördineren.

## Wat is een namespace? {#whats-a-namespace}

Een namespace is een benoemde container die key-value paren opslaat. Deze waarden kunnen worden benaderd en gewijzigd in verschillende delen van je app, afhankelijk van het type namespace dat je gebruikt. Zie het als een thread-veilige, gedistribueerde kaart met ingebouwde gebeurtenisafhandeling en vergrendelmechanismen.

### Wanneer namespaces gebruiken {#when-to-use-namespaces}

Gebruik namespaces wanneer:

- Je waarden moet delen tussen gebruikerssessies of app-componenten.
- Je wilt reageren op waarde wijzigingen via listeners.
- Je fijne vergrendeling nodig hebt voor kritieke secties.
- Je status efficiënt moet behouden en ophalen over je app.

### Types namespaces {#types-of-namespaces}

webforJ biedt drie types namespaces:

| Type        | Scope                                                                                                               | Typisch Gebruik                                |
| ----------- | ------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------- |
| **Privé**   | Gedeeld onder cliënten die dezelfde prefix en naam gebruiken. Geheugen wordt automatisch vrijgegeven wanneer er geen referenties meer zijn. | Gedeelde status tussen gerelateerde gebruikerssessies. |
| **Groep**   | Gedeeld door alle threads die zijn ontstaan uit dezelfde bovenliggende thread.                                      | Coördineren van status binnen een threadgroep. |
| **Globaal** | Toegankelijk over alle serverthreads (JVM-breed). Geheugen wordt behouden totdat sleutels expliciet worden verwijderd. | Toepassing-brede gedeelde status.               |

:::tip Standaard kiezen - Geef de voorkeur aan `PrivateNamespace`
Als je twijfelt, gebruik dan een `PrivateNamespace`. Dit biedt veilige, afgebakende delen tussen gerelateerde sessies zonder invloed op globale of server-brede status. Dit maakt het een betrouwbare standaard voor de meeste applicaties. 
:::

## Een namespace maken en gebruiken {#creating-and-using-a-namespace}

Namespaces worden gemaakt door een van de beschikbare types te instantieren. Elk type definieert hoe en waar de data wordt gedeeld. De onderstaande voorbeelden demonstreren hoe je een namespace kunt maken en interactie kunt hebben met de waarden.

### `Privé` namespace {#private-namespace}

De naam van de privé namespace bestaat uit twee delen:

- **Prefix**: Een door de ontwikkelaar gedefinieerde identifier die uniek moet zijn voor je app of module om conflicten te voorkomen.
- **Basisnaam**: De specifieke naam voor de gedeelde context of data die je wilt beheren.

Samen vormen ze de volledige naam van de namespace met het formaat:

```text
prefix + "." + baseName
```

Bijvoorbeeld, `"myApp.sharedState"`.

Namespaces die zijn gemaakt met dezelfde prefix en basisnaam verwijzen altijd naar de _zelfde onderliggende instantie_. Dit zorgt voor consistente gedeelde toegang over alle aanroepen van `PrivateNamespace` met dezelfde identifiers.

```java
// Maak of haal een privé namespace op
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Je kunt controleren op bestaan vóór de creatie:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Naamgevingsrichtlijnen
Bij het benoemen van een `PrivateNamespace`, volg deze regels:

- Beide delen moeten niet leeg zijn.
- Elk moet beginnen met een letter.
- Alleen afdrukbare tekens zijn toegestaan.
- Witruimtes zijn niet toegestaan.

Voorbeelden:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (te algemeen, waarschijnlijk om conflicten te voorkomen)
:::

### `Groep` en `Globaal` namespaces {#group-and-global-namespaces}

Naast PrivateNamespace, biedt webforJ twee andere types voor bredere deelcontexten. Deze zijn nuttig wanneer de status moet aanhouden buiten een enkele sessie of threadgroep.

- **Globale Namespace**: Toegankelijk over alle serverthreads (JVM-breed).
- **Groep Namespace**: Gedeeld onder threads die afkomstig zijn van dezelfde bovenliggende.

```java
// Globale gedeelde staat, toegankelijk over de hele applicatie
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Groepsspecifieke staat, beperkt tot threads die een gemeenschappelijke ouder delen
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Werken met waarden {#working-with-values}

Namespaces bieden een consistente interface voor het beheren van gedeelde data via key-value paren. Dit omvat het instellen, ophalen, verwijderen van waarden, synchroniseren van toegang en observeren van veranderingen in real-time.

### Waarden instellen en verwijderen {#setting-and-removing-values}

Gebruik `put()` om een waarde op te slaan onder een specifieke sleutel. Als de sleutel momenteel vergrendeld is, wacht de methode totdat de vergrendeling is vrijgegeven of de time-out verstrijkt.

```java
// Wacht tot 20ms (standaard) om de waarde in te stellen
ns.put("username", "admin");

// Geef een aangepaste time-out op in milliseconden
ns.put("config", configObject, 100);
```

Om een sleutel uit de namespace te verwijderen:

```java
ns.remove("username");
```

Zowel `put()` als `remove()` zijn blokkering operaties als de doel sleutel vergrendeld is. Als de time-out verstrijkt voordat de vergrendeling is vrijgegeven, wordt een `NamespaceLockedException` opgegooid.

Voor veilige gelijktijdige updates waarbij je alleen de waarde hoeft te overschrijven, gebruik `atomicPut()`. Dit vergrendelt de sleutel, schrijft de waarde en geeft de vergrendeling vrij in één stap:

```java
ns.atomicPut("counter", 42);
```

Dit voorkomt race-omstandigheden en vermijdt de noodzaak voor handmatige vergrendeling in eenvoudige update-scenario's.

### Waarden ophalen {#getting-values}

Om een waarde op te halen, gebruik je `get()`:

```java
Object value = ns.get("username");
```

Als de sleutel niet bestaat, wordt dit een `NoSuchElementException`. Om uitzonderingen te vermijden, gebruik `getOrDefault()`:

```java
Object value = ns.getOrDefault("username", "guest");
```

Om te controleren of een sleutel gedefinieerd is:

```java
if (ns.contains("username")) {
  // sleutel bestaat
}
```

Als je een waarde alleen wilt traag initialiseren wanneer deze ontbreekt, gebruik dan `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Dit is nuttig voor gedeelde waarden die eenmaal worden gemaakt en hergebruikt, zoals sessietokens, configuratieblokken of gecachete data.

### Handmatige vergrendeling {#manual-locking}

Als je meerdere bewerkingen op dezelfde sleutel moet uitvoeren of meerdere sleutels moet coördineren, gebruik dan handmatige vergrendeling.

```java
ns.setLock("flag", 500); // Wacht tot 500ms voor de vergrendeling

// Kritieke sectie begint
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Kritieke sectie eindigt

ns.removeLock("flag");
```

Gebruik dit patroon wanneer een reeks operaties atomair moet worden uitgevoerd over lezen en schrijven. Zorg er altijd voor dat de vergrendeling wordt vrijgegeven om te voorkomen dat andere threads worden geblokkeerd.

### Luisteren naar veranderingen {#listening-for-changes}

Namespaces ondersteunen gebeurtenislisteners waarmee je kunt reageren op toegang of wijziging van waarden. Dit is nuttig voor scenario's zoals:

- Loggen of auditen van toegang tot gevoelige sleutels
- Triggeren van updates wanneer een configuratiewaarde verandert
- Monitoren van gedeelde statuswijzigingen in multi-gebruikersapps

#### Beschikbare luistermethoden {#available-listener-methods}

| Methode                   | Trigger                           | Scope              |
|---------------------------|-----------------------------------|--------------------|
| `onAccess`                | Een sleutel wordt gelezen          | Hele namespace      |
| `onChange`                | Een sleutel wordt gewijzigd        | Hele namespace      |
| `onKeyAccess("key")`      | Een specifieke sleutel wordt gelezen| Per sleutel         |
| `onKeyChange("key")`      | Een specifieke sleutel wordt gewijzigd| Per sleutel         |

Elke listener ontvangt een gebeurtenisobject met:
- De sleutelnaam
- De oude waarde
- De nieuwe waarde
- Een verwijzing naar de namespace

#### Voorbeeld: Reageren op elke sleutelwijziging {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Sleutel gewijzigd: " + event.getVariableName());
  System.out.println("Oude waarde: " + event.getOldValue());
  System.out.println("Nieuwe waarde: " + event.getNewValue());
});
```

#### Voorbeeld: Toegang tot een specifieke sleutel bijhouden {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Token werd benaderd: " + event.getNewValue());
});
```

Listeners retourneren een `ListenerRegistration` object dat je later kunt gebruiken om de listener af te melden:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // logica
});
reg.remove();
```

## Voorbeeld: Gedeelde spelstatus in Tic-Tac-Toe {#example-sharing-game-state-in-tic-tac-toe}

De [webforJ Tic-Tac-Toe demo](https://github.com/webforj/webforj-tictactoe) biedt een eenvoudige twee-speler game waarbij beurten worden gedeeld tussen gebruikers. Het project demonstreert hoe `Namespace` kan worden gebruikt om status te coördineren zonder afhankelijk te zijn van externe tools zoals databases of API's.

In dit voorbeeld wordt een gedeeld Java-spelobject opgeslagen in een `PrivateNamespace`, zodat meerdere cliënten met dezelfde spel logica kunnen interageren. De namespace fungeert als een centrale container voor de spelstatus, wat ervoor zorgt dat:

- Beide spelers consistente bordupdates zien
- Beurten worden gesynchroniseerd
- De spel logica wordt gedeeld over sessies

Geen externe diensten (zoals REST of WebSockets) zijn nodig. Alle coördinatie gebeurt via namespaces, wat hun vermogen benadrukt om gedeelde status in real-time te beheren met minimale infrastructuur.

Verken de code: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
