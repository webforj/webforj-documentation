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

Namespaces in webforJ bieten einen Mechanismus zum Speichern und Abrufen von gemeinsamen Daten über verschiedene Bereiche in einer Webanwendung. Sie ermöglichen die Kommunikation von Daten zwischen Komponenten und Sitzungen, ohne sich auf traditionelle Speicherungstechniken wie Sitzungsattribute oder statische Felder verlassen zu müssen. Diese Abstraktion erlaubt es Entwicklern, den Zustand in kontrollierter, threadsicherer Weise zu kapseln und zuzugreifen. Namespaces sind ideal für den Aufbau von Multi-User-Kollaborationstools oder zur Aufrechterhaltung konsistenter globaler Einstellungen und ermöglichen Ihnen eine sichere und effiziente Datenkoordination.

## Was ist ein Namespace? {#whats-a-namespace}

Ein Namespace ist ein benannter Container, der Schlüssel-Wert-Paare speichert. Diese Werte können je nach verwendetem Namespace-Typ in verschiedenen Teilen Ihrer Anwendung zugegriffen und geändert werden. Stellen Sie es sich wie eine threadsichere, verteilte Map mit integrierter Ereignisbehandlung und Sperrmechanismen vor.

### Wann man Namespaces verwenden sollte {#when-to-use-namespaces}

Verwenden Sie Namespaces, wenn:

- Sie Werte über Benutzersitzungen oder Anwendungsbestandteile hinweg teilen müssen.
- Sie auf Wertänderungen über Listener reagieren möchten.
- Sie feingranulare Sperren für kritische Abschnitte benötigen.
- Sie Zustand effizient in Ihrer Anwendung speichern und abrufen müssen.

### Arten von Namespaces {#types-of-namespaces}

webforJ bietet drei Arten von Namespaces:

| Typ         | Umfang                                                                                                               | Typische Verwendung                           |
|-------------|---------------------------------------------------------------------------------------------------------------------|----------------------------------------------|
| **Privat**  | Geteilt unter Clients, die dasselbe Präfix und denselben Namen verwenden. Der Speicher wird automatisch freigegeben, wenn keine Referenzen mehr bestehen. | Geteilter Zustand zwischen verwandten Benutzersitzungen. |
| **Gruppe**  | Geteilt von allen Threads, die von demselben übergeordneten Thread erzeugt wurden.                                   | Koordination des Zustands innerhalb einer Thread-Gruppe.    |
| **Global**  | Über alle Server-Threads hinweg zugänglich (JVM-weit). Der Speicher bleibt erhalten, bis die Schlüssel explizit entfernt werden. | Anwendungsweiter geteilter Zustand.          |

:::tip Standardauswahl - Bevorzugen Sie `PrivateNamespace`
Wenn Sie unsicher sind, verwenden Sie ein `PrivateNamespace`. Es bietet sicheres, bereichsspezifisches Teilen zwischen verwandten Sitzungen, ohne den globalen oder serverweiten Zustand zu beeinträchtigen. Dies macht es zu einem zuverlässigen Standard für die meisten Anwendungen. 
:::

## Erstellen und Verwenden eines Namespaces {#creating-and-using-a-namespace}

Namespaces werden erstellt, indem einer der verfügbaren Typen instanziiert wird. Jeder Typ definiert, wie und wo die Daten geteilt werden. Die folgenden Beispiele zeigen, wie man einen Namespace erstellt und mit seinen Werten interagiert.

### `Privater` Namespace {#private-namespace}

Der Name des privaten Namespace setzt sich aus zwei Teilen zusammen:

- **Präfix**: Ein vom Entwickler definierter Bezeichner, der einzigartig für Ihre Anwendung oder Ihr Modul sein sollte, um Konflikte zu vermeiden.
- **Basisname** : Der spezifische Name für den gemeinsam genutzten Kontext oder die Daten, die Sie verwalten möchten.

Zusammen bilden sie den vollständigen Namespace-Namen im Format:

```text
prefix + "." + baseName
```

Zum Beispiel: `"myApp.sharedState"`.

Namespaces, die mit demselben Präfix und Basisnamen erstellt wurden, verweisen immer auf die _gleiche zugrunde liegende Instanz_. Dies gewährleistet einen konsistenten gemeinsamen Zugriff über alle Aufrufe zu `PrivateNamespace` hinweg, die dieselben Bezeichner verwenden.

```java
// Erstellen oder Abrufen eines privaten Namespaces
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Sie können die Existenz vor der Erstellung überprüfen:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Benennungsrichtlinien
Beim Benennen eines `PrivateNamespace` sollten Sie diese Regeln beachten:

- Beide Teile müssen nicht leer sein.
- Jedes muss mit einem Buchstaben beginnen.
- Nur druckbare Zeichen sind erlaubt.
- Leerzeichen sind nicht gestattet.

Beispiele:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (zu allgemein, wahrscheinlich Konflikte)
:::

### `Gruppe` und `Global` Namespaces {#group-and-global-namespaces}

Neben `PrivateNamespace` bietet webforJ zwei weitere Typen für breitere Teilkontexte. Diese sind nützlich, wenn der Zustand über eine einzelne Sitzung oder Thread-Gruppe hinaus bestehen bleiben muss.

- **Globaler Namespace**: Über alle Server-Threads hinweg zugänglich (JVM-weit).
- **Gruppen-namespace**: Geteilt unter Threads, die von denselben übergeordneten Threads stammen.

```java
// Globaler geteilter Zustand, anwendungsweit zugänglich
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Gruppenspezifischer Zustand, beschränkt auf Threads mit einem gemeinsamen Elternteil
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Arbeiten mit Werten {#working-with-values}

Namespaces bieten eine konsistente Schnittstelle zur Verwaltung gemeinsamer Daten durch Schlüssel-Wert-Paare. Dies beinhaltet das Setzen, Abrufen, Entfernen von Werten, Synchronisieren des Zugriffs und Beobachten von Änderungen in Echtzeit.

### Setzen und Entfernen von Werten {#setting-and-removing-values}

Verwenden Sie `put()`, um einen Wert unter einem bestimmten Schlüssel zu speichern. Wenn der Schlüssel derzeit gesperrt ist, wartet die Methode, bis die Sperre aufgehoben ist oder der Timeout abläuft.

```java
// Wartet bis zu 20ms (Standard), um den Wert festzulegen
ns.put("username", "admin");

// Geben Sie benutzerdefinierten Timeout in Millisekunden an
ns.put("config", configObject, 100);
```

Um einen Schlüssel aus dem Namespace zu entfernen:

```java
ns.remove("username");
```

Sowohl `put()` als auch `remove()` sind blockierende Operationen, wenn der Zielschlüssel gesperrt ist. Wenn der Timeout abläuft, bevor die Sperre aufgehoben wird, wird eine `NamespaceLockedException` ausgelöst.

Für sichere gleichzeitige Updates, bei denen Sie nur den Wert überschreiben müssen, verwenden Sie `atomicPut()`. Es sperrt den Schlüssel, schreibt den Wert und hebt die Sperre in einem Schritt auf:

```java
ns.atomicPut("counter", 42);
```

Dies verhindert Wettlaufbedingungen und vermeidet die Notwendigkeit einer manuellen Sperrung in einfachen Aktualisierungsszenarien.

### Werte abrufen {#getting-values}

Um einen Wert abzurufen, verwenden Sie `get()`:

```java
Object value = ns.get("username");
```

Wenn der Schlüssel nicht vorhanden ist, wird eine `NoSuchElementException` ausgelöst. Um Ausnahmen zu vermeiden, verwenden Sie `getOrDefault()`:

```java
Object value = ns.getOrDefault("username", "guest");
```

Um zu überprüfen, ob ein Schlüssel definiert ist:

```java
if (ns.contains("username")) {
  // Schlüssel existiert
}
```

Wenn Sie einen Wert nur dann faul initialisieren möchten, wenn er fehlt, verwenden Sie `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Dies ist nützlich für gemeinsame Werte, die einmal erstellt und wiederverwendet werden, wie Sitzungstoken, Konfigurationsblöcke oder zwischengespeicherte Daten.

### Manuelle Sperrung {#manual-locking}

Wenn Sie mehrere Operationen am selben Schlüssel durchführen oder über mehrere Schlüssel koordinieren müssen, verwenden Sie die manuelle Sperrung.

```java
ns.setLock("flag", 500); // Warte bis zu 500ms auf die Sperre

// Kritischer Abschnitt beginnt
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Kritischer Abschnitt endet

ns.removeLock("flag");
```

Verwenden Sie dieses Muster, wenn eine Reihenfolge von Operationen atomar über Lese- und Schreibvorgänge hinweg durchgeführt werden muss. Stellen Sie immer sicher, dass die Sperre freigegeben wird, um zu verhindern, dass andere Threads blockiert werden.

### Auf Änderungen hören {#listening-for-changes}

Namespaces unterstützen Ereignis-Listener, die es Ihnen ermöglichen, auf den Zugriff oder die Änderung von Werten zu reagieren. Dies ist nützlich für Szenarien wie:

- Protokollierung oder Überprüfung des Zugriffs auf sensible Schlüssel
- Auslösen von Aktualisierungen, wenn sich ein Konfigurationswert ändert
- Überwachung von Änderungen im gemeinsamen Zustand in Multi-User-Anwendungen

#### Verfügbare Listener-Methoden {#available-listener-methods}

| Methode                    | Auslöser                      | Umfang              |
|---------------------------|-------------------------------|---------------------|
| `onAccess`                | Jeder Schlüssel wird gelesen   | Ganzer Namespace     |
| `onChange`                | Jeder Schlüssel wird verändert | Ganzer Namespace     |
| `onKeyAccess("key")`      | Ein spezifischer Schlüssel wird gelesen | Pro Schlüssel       |
| `onKeyChange("key")`      | Ein spezifischer Schlüssel wird verändert | Pro Schlüssel       |

Jeder Listener erhält ein Ereignisobjekt, das enthält:
- Den Schlüsselnamen
- Den alten Wert
- Den neuen Wert
- Eine Referenz auf den Namespace

#### Beispiel: Auf eine Schlüsseländerung reagieren {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Schlüssel geändert: " + event.getVariableName());
  System.out.println("Alter Wert: " + event.getOldValue());
  System.out.println("Neuer Wert: " + event.getNewValue());
});
```

#### Beispiel: Zugriff auf einen bestimmten Schlüssel verfolgen {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Token wurde zugegriffen: " + event.getNewValue());
});
```

Listener geben ein `ListenerRegistration`-Objekt zurück, mit dem Sie den Listener später abmelden können:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // Logik
});
reg.remove();
```

## Beispiel: Teilen des Spielzustands in Tic-Tac-Toe {#example-sharing-game-state-in-tic-tac-toe}

Die [webforJ Tic-Tac-Toe-Demo](https://github.com/webforj/webforj-tictactoe) bietet ein einfaches Zwei-Spieler-Spiel, bei dem die Züge zwischen den Benutzern geteilt werden. Das Projekt zeigt, wie `Namespace` verwendet werden kann, um den Zustand zu koordinieren, ohne auf externe Tools wie Datenbanken oder APIs angewiesen zu sein.

In diesem Beispiel wird ein gemeinsames Java-Spielobjekt in einem `PrivateNamespace` gespeichert, wodurch mehrere Clients mit derselben Spiel-Logik interagieren können. Der Namespace dient als zentrales Container für den Spielzustand, um sicherzustellen, dass:

- Beide Spieler konsistente Aktualisierungen des Boards sehen
- Züge synchronisiert werden
- Die Spiel-Logik über Sitzungen hinweg geteilt wird

Es sind keine externen Dienste (wie REST oder WebSockets) erforderlich. Alle Koordination erfolgt über Namespaces, was ihre Fähigkeit zur Verwaltung gemeinsamer Zustände in Echtzeit mit minimaler Infrastruktur hervorhebt.

Erforschen Sie den Code: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
