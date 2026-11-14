---
title: Namensräume
sidebar_position: 40
description: >-
  Share thread-safe key-value state across sessions, thread groups, or the
  entire JVM using Private, Group, and Global namespaces.
_i18n_hash: f932a175d81df4147cae1335991db3d5
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Namespaces in webforJ bieten einen Mechanismus zum Speichern und Abrufen gemeinsam genutzter Daten über verschiedene Bereiche einer Web-App hinweg. Sie ermöglichen die Kommunikation von Daten zwischen Komponenten und Sitzungen, ohne auf herkömmliche Speichertechniken wie Sitzungsattribute oder statische Felder angewiesen zu sein. Diese Abstraktion ermöglicht es Entwicklern, Zustände kontrolliert und threadsicher zu kapseln und darauf zuzugreifen. Namespaces eignen sich ideal zum Erstellen von Tools für die Zusammenarbeit mehrerer Benutzer oder einfach zum Beibehalten einheitlicher globaler Einstellungen. Außerdem ermöglichen sie eine sichere und effiziente Koordination von Daten.

## Was ist ein Namespace? {#whats-a-namespace}

Ein Namespace ist ein benannter Container, in dem Schlüssel-Wert-Paare gespeichert werden. Je nach verwendetem Namespace-Typ lassen sich diese Werte in verschiedenen Bereichen Ihrer App abrufen und ändern. Stellen Sie sich einen threadsicheren, verteilten Schlüssel-Wert-Speicher mit integrierter Ereignisbehandlung und Sperrmechanismen vor.

### Wann sollten Namespaces verwendet werden? {#when-to-use-namespaces}

Verwenden Sie Namespaces, wenn:

- Sie Werte über Benutzersitzungen oder App-Komponenten hinweg gemeinsam nutzen müssen.
- Sie mithilfe von Listenern auf Wertänderungen reagieren möchten.
- Sie eine differenzierte Sperrung kritischer Abschnitte benötigen.
- Sie Zustände effizient in Ihrer App speichern und abrufen müssen.


### Namespace-Typen {#types-of-namespaces}

webforJ bietet drei Namespace-Typen:

| Typ         | Geltungsbereich                                                                                                    | Typische Verwendung                          |
| ----------- | ------------------------------------------------------------------------------------------------------------------- | -------------------------------------------- |
| **Private** | Gemeinsam genutzt von Clients, die dasselbe Präfix und denselben Namen verwenden. Der Speicher wird automatisch freigegeben, sobald keine Referenzen mehr bestehen. | Gemeinsamer Zustand verwandter Benutzersitzungen. |
| **Group**   | Gemeinsam genutzt von allen Threads, die vom selben übergeordneten Thread gestartet wurden.                         | Koordination des Zustands innerhalb einer Threadgruppe. |
| **Global**  | Über alle Server-Threads hinweg zugänglich (JVM-weit). Der Speicher bleibt erhalten, bis Schlüssel explizit entfernt werden. | Anwendungsweit gemeinsam genutzter Zustand. |

:::tip Standardauswahl – `PrivateNamespace` bevorzugen
Verwenden Sie im Zweifelsfall einen `PrivateNamespace`. Er ermöglicht eine sichere, auf den jeweiligen Bereich beschränkte gemeinsame Nutzung zwischen verwandten Sitzungen, ohne den globalen oder serverweiten Zustand zu beeinträchtigen. Damit ist er für die meisten Anwendungen eine zuverlässige Standardwahl.
:::

## Erstellen und Verwenden eines Namespace {#creating-and-using-a-namespace}

Namespaces werden durch Instanziieren eines der verfügbaren Typen erstellt. Jeder Typ legt fest, wie und wo die Daten gemeinsam genutzt werden. Die folgenden Beispiele zeigen, wie ein Namespace erstellt und mit seinen Werten gearbeitet wird.

### `Private`-Namespace {#private-namespace}

Der Name eines privaten Namespace besteht aus zwei Teilen:

- **Präfix**: Ein vom Entwickler festgelegter Bezeichner, der für Ihre App oder Ihr Modul eindeutig sein sollte, um Konflikte zu vermeiden.
- **Basisname**: Der spezifische Name des gemeinsam genutzten Kontexts oder der Daten, die Sie verwalten möchten.

Zusammen bilden sie den vollständigen Namespace-Namen im folgenden Format:

```text
prefix + "." + baseName
```

Zum Beispiel `"myApp.sharedState"`.

Namespaces, die mit demselben Präfix und Basisnamen erstellt werden, verweisen immer auf dieselbe _zugrunde liegende Instanz_. Dadurch ist bei allen Aufrufen von `PrivateNamespace` mit denselben Bezeichnern ein konsistenter gemeinsamer Zugriff gewährleistet.

```java
// Create or retrieve a private namespace
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Sie können vor dem Erstellen prüfen, ob der Namespace bereits vorhanden ist:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Richtlinien zur Namensgebung
Beachten Sie beim Benennen eines `PrivateNamespace` folgende Regeln:

- Beide Teile müssen nicht leer sein.
- Jeder Teil muss mit einem Buchstaben beginnen.
- Es sind nur druckbare Zeichen zulässig.
- Leerzeichen sind nicht zulässig.

Beispiele:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (zu allgemein und wahrscheinlich konfliktträchtig)
:::

### `Group`- und `Global`-Namespaces {#group-and-global-namespaces}

Zusätzlich zu `PrivateNamespace` bietet webforJ zwei weitere Typen für Bereiche mit umfangreicherer gemeinsamer Nutzung. Sie eignen sich, wenn ein Zustand über eine einzelne Sitzung oder Threadgruppe hinaus bestehen bleiben muss.

- **Global Namespace**: Über alle Server-Threads hinweg zugänglich (JVM-weit).
- **Group Namespace**: Wird von Threads gemeinsam genutzt, die denselben übergeordneten Thread haben.

```java
// Global shared state, accessible application-wide
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Group-specific state, limited to threads sharing a common parent
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Mit Werten arbeiten {#working-with-values}

Namespaces bieten eine einheitliche Schnittstelle zur Verwaltung gemeinsam genutzter Daten in Form von Schlüssel-Wert-Paaren. Dazu gehören das Festlegen, Abrufen und Entfernen von Werten, das Synchronisieren des Zugriffs und das Beobachten von Änderungen in Echtzeit.

### Werte festlegen und entfernen {#setting-and-removing-values}

Verwenden Sie `put()`, um einen Wert unter einem bestimmten Schlüssel zu speichern. Ist der Schlüssel derzeit gesperrt, wartet die Methode, bis die Sperre aufgehoben wird oder die Zeitüberschreitung eintritt.

```java
// Waits up to 20ms (default) to set the value
ns.put("username", "admin");

// Specify custom timeout in milliseconds
ns.put("config", configObject, 100);
```

So entfernen Sie einen Schlüssel aus dem Namespace:

```java
ns.remove("username");
```

Sowohl `put()` als auch `remove()` sind blockierende Vorgänge, wenn der betreffende Schlüssel gesperrt ist. Tritt die Zeitüberschreitung ein, bevor die Sperre aufgehoben wird, wird eine `NamespaceLockedException` ausgelöst.

Verwenden Sie für sichere gleichzeitige Aktualisierungen, bei denen der Wert lediglich überschrieben werden soll, `atomicPut()`. Dabei wird der Schlüssel gesperrt, der Wert geschrieben und die Sperre in einem Schritt aufgehoben:

```java
ns.atomicPut("counter", 42);
```

So werden Race-Bedingungen vermieden und bei einfachen Aktualisierungen ist keine manuelle Sperrung erforderlich.

### Werte abrufen {#getting-values}

Verwenden Sie zum Abrufen eines Werts `get()`:

```java
Object value = ns.get("username");
```

Ist der Schlüssel nicht vorhanden, wird eine `NoSuchElementException` ausgelöst. Verwenden Sie `getOrDefault()`, um Ausnahmen zu vermeiden:

```java
Object value = ns.getOrDefault("username", "guest");
```

So prüfen Sie, ob ein Schlüssel definiert ist:

```java
if (ns.contains("username")) {
  // key exists
}
```

Wenn Sie einen Wert erst dann initialisieren möchten, wenn er noch nicht vorhanden ist, verwenden Sie `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Das ist nützlich für gemeinsam genutzte Werte, die einmal erstellt und anschließend wiederverwendet werden, etwa Sitzungstokens, Konfigurationsblöcke oder zwischengespeicherte Daten.

### Manuelle Sperrung {#manual-locking}

Wenn Sie mehrere Vorgänge für denselben Schlüssel ausführen oder mehrere Schlüssel koordinieren müssen, verwenden Sie eine manuelle Sperrung.

```java
ns.setLock("flag", 500); // Wait up to 500ms for the lock

// Critical section starts
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Critical section ends

ns.removeLock("flag");
```

Verwenden Sie dieses Muster, wenn eine Folge von Vorgängen bei Lese- und Schreibzugriffen atomar ausgeführt werden muss. Stellen Sie stets sicher, dass die Sperre aufgehoben wird, damit keine anderen Threads blockiert werden.

### Auf Änderungen reagieren {#listening-for-changes}

Namespaces unterstützen Ereignis-Listener, mit denen Sie auf den Zugriff auf Werte oder deren Änderung reagieren können. Das ist nützlich für Szenarien wie:

- Zugriffe auf vertrauliche Schlüssel protokollieren oder überprüfen
- Aktualisierungen auslösen, wenn sich ein Konfigurationswert ändert
- Änderungen am gemeinsam genutzten Zustand in Apps mit mehreren Benutzern überwachen

#### Verfügbare Listener-Methoden {#available-listener-methods}

| Methode                  | Auslöser                         | Geltungsbereich       |
|--------------------------|----------------------------------|-----------------------|
| `onAccess`               | Ein beliebiger Schlüssel wird gelesen | Gesamter Namespace |
| `onChange`               | Ein beliebiger Schlüssel wird geändert | Gesamter Namespace |
| `onKeyAccess("key")`     | Ein bestimmter Schlüssel wird gelesen | Pro Schlüssel         |
| `onKeyChange("key")`     | Ein bestimmter Schlüssel wird geändert | Pro Schlüssel         |

Jeder Listener erhält ein Ereignisobjekt mit folgenden Angaben:
- Dem Schlüsselnamen
- Dem alten Wert
- Dem neuen Wert
- Einer Referenz auf den Namespace

#### Beispiel: Auf eine Änderung an einem beliebigen Schlüssel reagieren {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Key changed: " + event.getVariableName());
  System.out.println("Old value: " + event.getOldValue());
  System.out.println("New value: " + event.getNewValue());
});
```

#### Beispiel: Zugriffe auf einen bestimmten Schlüssel erfassen {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Token was accessed: " + event.getNewValue());
});
```

Listener geben ein `ListenerRegistration`-Objekt zurück, mit dem Sie den Listener später wieder abmelden können:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // logic
});
reg.remove();
```

## Beispiel: Spielzustand bei Tic-Tac-Toe gemeinsam nutzen {#example-sharing-game-state-in-tic-tac-toe}

Die [webforJ-Tic-Tac-Toe-Demo](https://github.com/webforj/webforj-tictactoe) bietet ein einfaches Spiel für zwei Personen, bei dem die Spielzüge zwischen den Benutzern geteilt werden. Das Projekt veranschaulicht, wie `Namespace` zur Zustandskoordination eingesetzt werden kann, ohne auf externe Tools wie Datenbanken oder APIs angewiesen zu sein.

In diesem Beispiel wird ein gemeinsames Java-Spielobjekt in einem `PrivateNamespace` gespeichert, sodass mehrere Clients mit derselben Spiellogik interagieren können. Der Namespace dient als zentraler Container für den Spielzustand und stellt sicher, dass:

- Beide Spieler konsistente Aktualisierungen des Spielbretts sehen
- Die Spielzüge synchronisiert werden
- Die Spiellogik über Sitzungen hinweg gemeinsam genutzt wird

Es sind keine externen Dienste wie REST oder WebSockets erforderlich. Die gesamte Koordination erfolgt über Namespaces. Das veranschaulicht, wie sich mit minimaler Infrastruktur ein gemeinsam genutzter Zustand in Echtzeit verwalten lässt.

Sehen Sie sich den Code an: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<VideoPlayer video="namespaces/tic-tac-toe" width={1660} height={902} title="Tic tac toe" />
