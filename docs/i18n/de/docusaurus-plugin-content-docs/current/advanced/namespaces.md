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

Namespaces in webforJ bieten einen Mechanismus zum Speichern und Abrufen von gemeinsam genutzten Daten über verschiedene Bereiche in einer Webanwendung. Sie ermöglichen die Kommunikation von Daten zwischen Komponenten und über Sitzungen hinweg, ohne sich auf traditionelle Speicherungstechniken wie Sitzungsattribute oder statische Felder zu stützen. Diese Abstraktion erlaubt es Entwicklern, den Zustand auf kontrollierte, thread-sichere Weise zu kapseln und darauf zuzugreifen. Namespaces sind ideal zum Aufbau von Multi-User-Kollaborationstools oder einfach zur Aufrechterhaltung konsistenter globaler Einstellungen und ermöglichen es Ihnen, Daten sicher und effizient zu koordinieren.

## Was ist ein Namespace? {#whats-a-namespace}

Ein Namespace ist ein benannter Container, der Schlüssel-Wert-Paare speichert. Diese Werte können je nach verwendetem Namespace-Typ in verschiedenen Teilen Ihrer Anwendung zugegriffen und geändert werden. Stellen Sie sich das vor wie eine thread-sichere, verteilte Karte mit integrierter Ereignisbehandlung und Sperrmechanismen.

### Wann sollten Namespaces verwendet werden? {#when-to-use-namespaces}

Verwenden Sie Namespaces, wenn:

- Sie Werte über Benutzersitzungen oder Anwendungs-Komponenten hinweg teilen müssen.
- Sie auf Wertänderungen über Listener reagieren möchten.
- Sie feingranulare Sperren für kritische Abschnitte benötigen.
- Sie den Zustand effizient in Ihrer gesamten Anwendung speichern und abrufen müssen.

### Arten von Namespaces {#types-of-namespaces}

webforJ bietet drei Arten von Namespaces:

| Typ         | Umfang                                                                                                               | Typische Verwendung                             |
|-------------|---------------------------------------------------------------------------------------------------------------------|------------------------------------------------|
| **Privat**  | Wird unter Clients geteilt, die dasselbe Präfix und den gleichen Namen verwenden. Der Speicher wird automatisch freigegeben, wenn keine Referenzen mehr vorhanden sind. | Geteilter Zustand zwischen verwandten Benutzersitzungen. |
| **Gruppe**  | Wird von allen Threads, die vom selben übergeordneten Thread erzeugt werden, geteilt.                              | Koordinierung des Zustands innerhalb einer Thread-Gruppe.   |
| **Global**  | Von allen Server-Threads (JVM-weit) zugänglich. Der Speicher bleibt erhalten, bis die Schlüssel ausdrücklich entfernt werden. | Anwendungsübergreifender geteilter Zustand.    |

:::tip Standard wählen - Bevorzugen Sie `PrivateNamespace`
Wenn Sie sich nicht sicher sind, verwenden Sie ein `PrivateNamespace`. Es bietet sicheres, abgegrenztes Teilen zwischen verwandten Sitzungen, ohne den globalen oder serverweiten Zustand zu beeinträchtigen. Dies macht es zu einem zuverlässigen Standard für die meisten Anwendungen.
:::

## Erstellen und Verwenden eines Namespaces {#creating-and-using-a-namespace}

Namespaces werden erstellt, indem einer der verfügbaren Typen instanziiert wird. Jeder Typ definiert, wie und wo die Daten geteilt werden. Die folgenden Beispiele zeigen, wie man einen Namespace erstellt und mit seinen Werten interagiert.

### `Privat` Namespace {#private-namespace}

Der Name des privaten Namespaces setzt sich aus zwei Teilen zusammen:

- **Präfix**: Ein vom Entwickler definierter Bezeichner, der einzigartig für Ihre Anwendung oder Ihr Modul sein sollte, um Konflikte zu vermeiden.
- **Basename** : Der spezifische Name für den gemeinsamen Kontext oder die Daten, die Sie verwalten möchten.

Zusammen bilden sie den vollständigen Namen des Namespaces im Format:

```text
prefix + "." + baseName
```

Zum Beispiel, `"myApp.sharedState"`.

Namespaces, die mit demselben Präfix und Basename erstellt wurden, beziehen sich immer auf die _selbe zugrunde liegende Instanz_. Dies gewährleistet einen konsistenten gemeinsamen Zugriff auf alle Aufrufe von `PrivateNamespace`, die dieselben Bezeichner verwenden.

```java
// Erstellen oder Abrufen eines privaten Namespaces
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Sie können vor der Erstellung auf die Existenz prüfen:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Benennungsrichtlinien
Beim Benennen eines `PrivateNamespace` sollten Sie diese Regeln befolgen:

- Beide Teile müssen nicht leer sein.
- Jeder muss mit einem Buchstaben beginnen.
- Nur druckbare Zeichen sind erlaubt.
- Leerzeichen sind nicht zulässig.

Beispiele:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (zu allgemein, wahrscheinlich Konflikte)
:::

### `Gruppe` und `Global` Namespaces {#group-and-global-namespaces}

Neben `PrivateNamespace` bietet webforJ zwei weitere Typen für breitere Freigabekontexte. Diese sind nützlich, wenn der Zustand über eine einzelne Sitzung oder Thread-Gruppe hinaus bestehen bleiben muss.

- **Globaler Namespace**: Über alle Server-Threads (JVM-weit) zugänglich.
- **Gruppen-Namespaces**: Wird unter Threads geteilt, die von derselben Eltern-Thread abstammen.

```java
// Globaler gemeinsamer Zustand, anwendungsweit zugänglich
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Gruppen-spezifischer Zustand, beschränkt auf Threads, die einen gemeinsamen Eltern haben
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Arbeiten mit Werten {#working-with-values}

Namespaces bieten eine konsistente Schnittstelle zum Verwalten gemeinsamer Daten durch Schlüssel-Wert-Paare. Dies umfasst das Setzen, Abrufen, Entfernen von Werten, das Synchronisieren von Zugriffen und das Beobachten von Änderungen in Echtzeit.

### Werte setzen und entfernen {#setting-and-removing-values}

Verwenden Sie `put()`, um einen Wert unter einem bestimmten Schlüssel zu speichern. Wenn der Schlüssel derzeit gesperrt ist, wartet die Methode, bis die Sperre aufgehoben wird oder der Timeout abläuft.

```java
// Wartet bis zu 20ms (Standard), um den Wert zu setzen
ns.put("username", "admin");

// Geben Sie einen benutzerdefinierten Timeout in Millisekunden an
ns.put("config", configObject, 100);
```

Um einen Schlüssel aus dem Namespace zu entfernen:

```java
ns.remove("username");
```

Sowohl `put()` als auch `remove()` sind blockierende Vorgänge, wenn der Zielschlüssel gesperrt ist. Wenn der Timeout abläuft, bevor die Sperre aufgehoben wird, wird eine `NamespaceLockedException` ausgelöst.

Für sichere gleichzeitige Aktualisierungen, bei denen Sie nur den Wert überschreiben müssen, verwenden Sie `atomicPut()`. Es sperrt den Schlüssel, schreibt den Wert und hebt die Sperre in einem Schritt auf:

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

Wenn Sie einen Wert bei Bedarf nur dann initialisieren möchten, wenn er fehlt, verwenden Sie `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Dies ist nützlich für gemeinsame Werte, die einmal erstellt und wiederverwendet werden, wie Sitzungstoken, Konfigurationsblöcke oder zwischengespeicherte Daten.

### Manuelle Sperrung {#manual-locking}

Wenn Sie mehrere Operationen am selben Schlüssel ausführen oder über mehrere Schlüssel hinweg koordinieren müssen, verwenden Sie die manuelle Sperrung.

```java
ns.setLock("flag", 500); // Warte bis zu 500ms auf die Sperre

// Kritischer Abschnitt beginnt
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Kritischer Abschnitt endet

ns.removeLock("flag");
```

Verwenden Sie dieses Muster, wenn eine Reihe von Operationen atomar über Lese- und Schreibzugriffe hinweg ausgeführt werden muss. Stellen Sie immer sicher, dass die Sperre aufgehoben wird, um andere Threads nicht zu blockieren.

### Auf Änderungen hören {#listening-for-changes}

Namespaces unterstützen Ereignis-Listener, die es Ihnen ermöglichen, auf den Zugriff oder die Änderung von Werten zu reagieren. Dies ist nützlich für Szenarien wie:

- Protokollierung oder Prüfung des Zugriffs auf sensible Schlüssel
- Auslösen von Aktualisierungen, wenn sich ein Konfigurationswert ändert
- Überwachung von Änderungen des gemeinsamen Zustands in Multi-User-Anwendungen

#### Verfügbare Listener-Methoden {#available-listener-methods}

| Methode                     | Auslöser                         | Umfang              |
|-----------------------------|----------------------------------|---------------------|
| `onAccess`                  | Jeder Schlüssel wird gelesen     | Ganzer Namespace     |
| `onChange`                  | Jeder Schlüssel wird geändert    | Ganzer Namespace     |
| `onKeyAccess("key")`       | Ein spezifischer Schlüssel wird gelesen  | Pro Schlüssel       |
| `onKeyChange("key")`       | Ein spezifischer Schlüssel wird geändert | Pro Schlüssel       |

Jeder Listener erhält ein Ereignisobjekt, das enthält:
- Den Schlüsselname
- Den alten Wert
- Den neuen Wert
- Eine Referenz auf den Namespace

#### Beispiel: Auf jede Schlüsseländerung reagieren {#example-respond-to-any-key-change}

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
  System.out.println("Token wurde aufgerufen: " + event.getNewValue());
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

Die [webforJ Tic-Tac-Toe-Demo](https://github.com/webforj/webforj-tictactoe) bietet ein einfaches Zwei-Spieler-Spiel, bei dem die Züge zwischen Benutzern geteilt werden. Das Projekt zeigt, wie `Namespace` verwendet werden kann, um den Zustand zu koordinieren, ohne sich auf externe Tools wie Datenbanken oder APIs zu verlassen.

In diesem Beispiel wird ein gemeinsames Java-Spielobjekt in einem `PrivateNamespace` gespeichert, das es mehreren Clients ermöglicht, mit derselben Spiel-Logik zu interagieren. Der Namespace dient als zentraler Container für den Spielzustand und stellt sicher, dass:

- Beide Spieler konsistente Aktualisierungen des Bretts sehen
- Züge synchronisiert sind
- Die Spiel-Logik zwischen den Sitzungen geteilt wird

Es sind keine externen Dienste (wie REST oder WebSockets) erforderlich. Alle Koordination erfolgt über Namespaces, was ihre Fähigkeit zur Verwaltung des gemeinsamen Zustands in Echtzeit mit minimaler Infrastruktur hervorhebt.

Erforschen Sie den Code: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
