---
title: Namespaces
sidebar_position: 30
_i18n_hash: f3d79da01b17871bddf7543682a5e7e5
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Namespaces in webforJ bieden een mechanisme voor het opslaan en ophalen van gedeelde gegevens over verschillende scopes in een webapplicatie. Ze stellen inter-component en cross-sessie datacommunicatie mogelijk zonder afhankelijk te zijn van traditionele opslagtechnieken zoals sessie-attributen of statische velden. Deze abstractie stelt ontwikkelaars in staat om toestand op een gecontroleerde, thread-veilige manier te encapculeren en toegang te krijgen. Namespaces zijn ideaal voor het bouwen van samenwerkingshulpmiddelen voor meerdere gebruikers of simpelweg voor het behouden van consistente globale instellingen, en laten je gegevens veilig en efficiënt coördineren.

## Wat is een namespace? {#whats-a-namespace}

Een namespace is een benoemde container die sleutel-waarde paren opslaat. Deze waarden kunnen in verschillende delen van je app worden benaderd en gewijzigd, afhankelijk van het type namespace dat je gebruikt. Je kunt het beschouwen als een thread-veilige, gedistribueerde map met ingebouwde gebeurtenishandling en vergrendelmechanismen.

### Wanneer namespaces gebruiken {#when-to-use-namespaces}

Gebruik namespaces wanneer:

- Je waarden moet delen over gebruikerssessies of app-componenten.
- Je wilt reageren op waardewijzigingen via luisteraars.
- Je fijne vergrendeling nodig hebt voor kritieke secties.
- Je efficiënt toestand moet persistent maken en ophalen in je app.

### Typen namespaces {#types-of-namespaces}

webforJ biedt drie typen namespaces:

| Type        | Scope                                                                                                               | Typisch gebruik                               |
| ----------- | ------------------------------------------------------------------------------------------------------------------- | --------------------------------------------- |
| **Privé**   | Gemeenschappelijk onder cliënten die dezelfde prefix en naam gebruiken. Geheugen wordt automatisch vrijgegeven wanneer er geen verwijzingen meer zijn. | Gedeelde toestand tussen verwante gebruikerssessies. |
| **Groep**   | Gedeeld door alle threads die zijn gestart vanuit dezelfde bovenliggende thread.                                      | Coördineren van toestand binnen een threadgroep. |
| **Globaal** | Toegankelijk over alle serverthreads (JVM-breed). Geheugen wordt behouden totdat sleutels expliciet worden verwijderd. | Toegankelijkheid bredere gedeelde toestand binnen de applicatie. |

:::tip Kiezen van een standaard - Geef de voorkeur aan `PrivateNamespace`
Wanneer je twijfelt, gebruik een `PrivateNamespace`. Het biedt veilige, scoped sharing tussen verwante sessies zonder de globale of serverbrede toestand te beïnvloeden. Dit maakt het een betrouwbare standaard voor de meeste applicaties. 
:::

## Een namespace maken en gebruiken {#creating-and-using-a-namespace}

Namespaces worden gemaakt door een van de beschikbare typen te instantieren. Elk type definieert hoe en waar de gegevens worden gedeeld. De onderstaande voorbeelden demonstreren hoe je een namespace maakt en interacteert met zijn waarden.

### `Privé` namespace {#private-namespace}

De naam van de privé namespace bestaat uit twee delen:

- **Prefix**: Een door de ontwikkelaar gedefinieerde identifier die uniek moet zijn voor je app of module om conflicten te voorkomen.
- **Basisnaam** : De specifieke naam voor de gedeelde context of gegevens die je wilt beheren.

Samen vormen ze de volledige naam van de namespace met het formaat:

```text
prefix + "." + baseName
```

Bijvoorbeeld, `"myApp.sharedState"`.

Namespaces die zijn gemaakt met dezelfde prefix en basisnaam verwijzen altijd naar de _zelfde onderliggende instantie_. Dit zorgt voor consistente gedeelde toegang bij alle aanroepen van `PrivateNamespace` die dezelfde identificators gebruiken.

```java
// Maak of haal een privé namespace op
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Je kunt controleren op bestaan voordat je deze maakt:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Richtlijnen voor namen
Volg deze regels bij het benoemen van een `PrivateNamespace`:

- Beide delen moeten niet leeg zijn.
- Elk moet beginnen met een letter.
- Alleen afdrukbare karakters zijn toegestaan.
- Witruimten zijn niet toegestaan.

Voorbeelden:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (te algemeen, waarschijnlijk conflicterend)
:::

### `Groep` en `Globaal` namespaces {#group-and-global-namespaces}

Naast PrivateNamespace biedt webforJ twee andere typen voor bredere deelcontexten. Deze zijn nuttig wanneer toestand moet persistent zijn voorbij een enkele sessie of threadgroep.

- **Globale Namespace**: Toegankelijk over alle serverthreads (JVM-breed).
- **Groepsnamespace**: Gedeeld tussen threads die afkomstig zijn van dezelfde bovenliggende.

```java
// Globale gedeelde toestand, toegankelijk binnen de hele applicatie
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Groep-specifieke toestand, beperkt tot threads die een gemeenschappelijke oorsprong delen
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Werken met waarden {#working-with-values}

Namespaces bieden een consistente interface voor het beheren van gedeelde gegevens via sleutel-waarde paren. Dit omvat het instellen, ophalen, verwijderen van waarden, synchroniseren van toegang en het observeren van veranderingen in realtime.

### Waarden instellen en verwijderen {#setting-and-removing-values}

Gebruik `put()` om een waarde op te slaan onder een specifieke sleutel. Als de sleutel momenteel vergrendeld is, wacht de methode totdat de vergrendeling is opgeheven of de tijdsduur verstrijkt.

```java
// Wacht tot 20ms (standaard) om de waarde in te stellen
ns.put("username", "admin");

// Specificeer een aangepaste time-out in milliseconden
ns.put("config", configObject, 100);
```

Om een sleutel uit de namespace te verwijderen:

```java
ns.remove("username");
```

Zowel `put()` als `remove()` zijn blokkeringen als de doelsleutel vergrendeld is. Als de time-out verstrijkt voordat de vergrendeling is opgeheven, wordt er een `NamespaceLockedException` opgegooid.

Voor veilige concurrerende updates waar je alleen de waarde wilt overschrijven, gebruik `atomicPut()`. Het vergrendelt de sleutel, schrijft de waarde en geeft de vergrendeling in één stap vrij:

```java
ns.atomicPut("counter", 42);
```

Dit voorkomt race-condities en vermijdt de noodzaak voor handmatige vergrendeling in eenvoudige updatescenario's.

### Waarden ophalen {#getting-values}

Om een waarde op te halen, gebruik je `get()`:

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

Als je een waarde alleen wilt lazy initialiseren wanneer deze ontbreekt, gebruik dan `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Dit is nuttig voor gedeelde waarden die eenmaal worden aangemaakt en hergebruikt, zoals sessietokens, configuratieblokken of gecachte gegevens.

### Handmatige vergrendeling {#manual-locking}

Als je meerdere bewerkingen op dezelfde sleutel moet uitvoeren of moet coördineren over meerdere sleutels, gebruik dan handmatige vergrendeling.

```java
ns.setLock("flag", 500); // Wacht tot 500ms voor de vergrendeling

// Kritieke sectie begint
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Kritieke sectie eindigt

ns.removeLock("flag");
```

Gebruik dit patroon wanneer een reeks bewerkingen atomisch moet worden uitgevoerd over lezen en schrijven. Zorg altijd ervoor dat de vergrendeling wordt opgeheven om te voorkomen dat andere threads worden geblokkeerd.

### Luisteren naar veranderingen {#listening-for-changes}

Namespaces ondersteunen gebeurtenisluiters waarmee je kunt reageren op toegang of wijziging van waarden. Dit is nuttig voor scenario's zoals:

- Loggen of auditen van toegang tot gevoelige sleutels
- Triggers voor updates wanneer een configuratiewaarde verandert
- Monitoren van gedeelde toestand veranderingen in multi-gebruikersapps

#### Beschikbare listener-methoden {#available-listener-methods}

| Methode                     | Trigger                          | Bereik               |
|-----------------------------|----------------------------------|----------------------|
| `onAccess`                  | Elke sleutel wordt gelezen        | Hele namespace       |
| `onChange`                  | Elke sleutel wordt gewijzigd      | Hele namespace       |
| `onKeyAccess("key")`        | Een specifieke sleutel wordt gelezen | Per sleutel          |
| `onKeyChange("key")`        | Een specifieke sleutel wordt gewijzigd | Per sleutel          |

Elke listener ontvangt een gebeurtenisobject met:
- De sleutels naam
- De oude waarde
- De nieuwe waarde
- Een verwijzing naar de namespace

#### Voorbeeld: Reageer op elke sleutelwijziging {#example-respond-to-any-key-change}

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
  System.out.println("Token is geopend: " + event.getNewValue());
});
```

Listeners geven een `ListenerRegistration` object terug dat je kunt gebruiken om de listener later te annuleren:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // logica
});
reg.remove();
```

## Voorbeeld: Delen van speltoestand in Tic-Tac-Toe {#example-sharing-game-state-in-tic-tac-toe}

De [webforJ Tic-Tac-Toe demo](https://github.com/webforj/webforj-tictactoe) biedt een eenvoudig twee-spelersspel waar beurten worden gedeeld tussen gebruikers. Het project demonstreert hoe `Namespace` kan worden gebruikt om toestand te coördineren zonder afhankelijk te zijn van externe tools zoals databases of API's.

In dit voorbeeld wordt een gedeeld Java-spelobject opgeslagen in een `PrivateNamespace`, zodat meerdere cliënten met dezelfde spel logica kunnen interageren. De namespace dient als een centrale container voor de speltoestand, wat zorgt voor:

- Beide spelers zien consistente bordupdates
- Beurten zijn gesynchroniseerd
- De spel logica wordt over sessies gedeeld

Geen externe services (zoals REST of WebSockets) zijn nodig. Alle coördinatie gebeurt via namespaces, wat hun vermogen benadrukt om gedeelde toestand in realtime te beheren met minimale infrastructuur.

Verken de code: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
