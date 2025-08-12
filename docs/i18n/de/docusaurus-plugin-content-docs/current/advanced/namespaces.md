---
title: Namespaces
sidebar_position: 30
_i18n_hash: f3d79da01b17871bddf7543682a5e7e5
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Namespaces in webforJ bieten einen Mechanismus zum Speichern und Abrufen von gemeinsam genutzten Daten über verschiedene Bereiche in einer Webanwendung. Sie ermöglichen die Inter-Komponenten- und Sitzungsübergreifende Datenkommunikation, ohne auf traditionelle Speichertechniken wie Sitzungsattribute oder statische Felder angewiesen zu sein. Diese Abstraktion ermöglicht es Entwicklern, den Zustand auf kontrollierte und threadsichere Weise zu kapseln und zuzugreifen. Namespaces sind ideal für den Aufbau von Mehrbenutzer-Kollaborationstools oder einfach zur Pflege konsistenter globaler Einstellungen und ermöglichen es Ihnen, Daten sicher und effizient zu koordinieren.

## Was ist ein Namespace? {#whats-a-namespace}

Ein Namespace ist ein benannter Container, der Schlüssel-Wert-Paare speichert. Diese Werte können in verschiedenen Teilen Ihrer App abhängig von dem verwendeten Namespace-Typ zugegriffen und geändert werden. Man kann sich das wie eine threadsichere, verteilte Karte mit integrierter Ereignisbehandlung und Sperrmechanismen vorstellen.

### Wann sollte man Namespaces verwenden? {#when-to-use-namespaces}

Verwenden Sie Namespaces, wenn:

- Sie Werte zwischen Benutzersitzungen oder App-Komponenten teilen müssen.
- Sie auf Wertänderungen über Listener reagieren möchten.
- Sie feinkörnige Sperren für kritische Abschnitte benötigen.
- Sie den Zustand effizient über Ihre App hinweg anhalten und abrufen müssen.

### Typen von Namespaces {#types-of-namespaces}

webforJ bietet drei Typen von Namespaces:

| Typ          | Bereich                                                                                                                | Typische Verwendung                           |
|--------------|----------------------------------------------------------------------------------------------------------------------|----------------------------------------------|
| **Privat**   | Geteilt unter Clients, die dasselbe Präfix und den gleichen Namen verwenden. Der Speicher wird automatisch freigegeben, wenn keine Referenzen mehr bestehen. | Geteilter Zustand zwischen zusammenhängenden Benutzersitzungen. |
| **Gruppe**   | Wird von allen Threads, die aus dem gleichen übergeordneten Thread gestartet werden, geteilt.                          | Koordination des Zustands innerhalb einer Thread-Gruppe. |
| **Global**   | Von allen Server-Threads (JVM-weit) zugänglich. Der Speicher bleibt bestehen, bis die Schlüssel explizit entfernt werden. | Anwendungsweit geteilter Zustand.            |

:::tip Auswahl eines Standardwerts - Bevorzugen Sie `PrivateNamespace`
Wenn Sie unsicher sind, verwenden Sie `PrivateNamespace`. Es bietet sicheres, bereichseingeschränktes Teilen zwischen zusammenhängenden Sitzungen, ohne den globalen oder serverweit Zustand zu beeinträchtigen. Das macht es zu einem zuverlässigen Standard für die meisten Anwendungen. 
:::

## Erstellen und Verwenden eines Namespaces {#creating-and-using-a-namespace}

Namespaces werden erstellt, indem Sie einen der verfügbaren Typen instanziieren. Jeder Typ definiert, wie und wo die Daten geteilt werden. Die nachstehenden Beispiele zeigen, wie man einen Namespace erstellt und mit seinen Werten interagiert.

### `Privat` Namespace {#private-namespace}

Der Name des privaten Namespaces besteht aus zwei Teilen:

- **Präfix**: Ein vom Entwickler definierter Bezeichner, der einzigartig für Ihre App oder Ihr Modul sein sollte, um Konflikte zu vermeiden.
- **Basisname**: Der spezifische Name für den gemeinsamen Kontext oder die Daten, die Sie verwalten möchten.

Zusammen bilden sie den vollständigen Namen des Namespaces mit dem Format:

```text
prefix + "." + baseName
```

Zum Beispiel: `"myApp.sharedState"`.

Namespaces, die mit demselben Präfix und Basisnamen erstellt wurden, beziehen sich immer auf die _gleiche zugrunde liegende Instanz_. Dies gewährleistet einen konsistenten gemeinsamen Zugriff über alle Aufrufe von `PrivateNamespace`, die dieselben Bezeichner verwenden.

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
Beim Benennen eines `PrivateNamespace` sollten Sie diese Regeln befolgen:

- Beide Teile müssen nicht leer sein.
- Jeder muss mit einem Buchstaben beginnen.
- Nur druckbare Zeichen sind erlaubt.
- Leerzeichen sind nicht gestattet.

Beispiele:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (zu generisch, wahrscheinlich Konflikte)
:::

### `Gruppe` und `Global` Namespaces {#group-and-global-namespaces}

Zusätzlich zu `PrivateNamespace` bietet webforJ zwei weitere Typen für breitere Sharing-Kontexte. Diese sind nützlich, wenn der Zustand über eine einzelne Sitzung oder Thread-Gruppe hinaus bestehen bleibt.

- **Global Namespace**: Von allen Server-Threads (JVM-weit) zugänglich.
- **Gruppen-Namespace**: Wird unter Threads geteilt, die von demselben übergeordneten Thread stammen.

```java
// Globaler geteilter Zustand, anwendungsweit zugänglich
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Gruppen-spezifischer Zustand, begrenzt auf Threads, die einen gemeinsamen Elternteil teilen
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Arbeiten mit Werten {#working-with-values}

Namespaces bieten eine konsistente Schnittstelle zum Verwalten von gemeinsam genutzten Daten über Schlüssel-Wert-Paare. Dazu gehört das Setzen, Abrufen, Entfernen von Werten, Synchronisieren des Zugriffs und Beobachten von Änderungen in Echtzeit.

### Werte setzen und entfernen {#setting-and-removing-values}

Verwenden Sie `put()`, um einen Wert unter einem bestimmten Schlüssel zu speichern. Wenn der Schlüssel derzeit gesperrt ist, wartet die Methode, bis die Sperre aufgehoben wird oder der Timeout abläuft.

```java
// Wartet bis zu 20 ms (Standard), um den Wert festzulegen
ns.put("username", "admin");

// Geben Sie einen benutzerdefinierten Timeout in Millisekunden an
ns.put("config", configObject, 100);
```

Um einen Schlüssel aus dem Namespace zu entfernen:

```java
ns.remove("username");
```

Sowohl `put()` als auch `remove()` sind blockierende Operationen, wenn der Zielschlüssel gesperrt ist. Wenn der Timeout abläuft, bevor die Sperre aufgehoben wird, wird eine `NamespaceLockedException` ausgelöst.

Für sichere gleichzeitige Aktualisierungen, bei denen Sie nur den Wert überschreiben möchten, verwenden Sie `atomicPut()`. Damit wird der Schlüssel gesperrt, der Wert geschrieben und die Sperre in einem Schritt aufgehoben:

```java
ns.atomicPut("counter", 42);
```

Dies verhindert Rennbedingungen und vermeidet die Notwendigkeit für manuelles Sperren in einfachen Aktualisierungsszenarien.

### Werte abrufen {#getting-values}

Um einen Wert abzurufen, verwenden Sie `get()`:

```java
Object value = ns.get("username");
```

Wenn der Schlüssel nicht existiert, wird dies eine `NoSuchElementException` auslösen. Um Ausnahmen zu vermeiden, verwenden Sie `getOrDefault()`:

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

Wenn Sie mehrere Operationen auf demselben Schlüssel ausführen oder über mehrere Schlüssel koordinieren müssen, verwenden Sie die manuelle Sperrung.

```java
ns.setLock("flag", 500); // Wartet bis zu 500 ms auf die Sperre

// Kritischer Abschnitt beginnt
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Kritischer Abschnitt endet

ns.removeLock("flag");
```

Verwenden Sie dieses Muster, wenn eine Abfolge von Operationen atomar über Lese- und Schreibvorgänge hinweg ausgeführt werden muss. Stellen Sie stets sicher, dass die Sperre aufgehoben wird, um andere Threads nicht zu blockieren.

### Auf Änderungen hören {#listening-for-changes}

Namespaces unterstützen Ereignis-Listener, die es Ihnen ermöglichen, auf den Zugriff oder die Änderung von Werten zu reagieren. Dies ist nützlich in Szenarien wie:

- Protokollierung oder Überwachung des Zugriffs auf sensible Schlüssel
- Auslösen von Aktualisierungen bei Änderungen eines Konfigurationswerts
- Überwachung von Änderungen des gemeinsamen Zustands in Mehrbenutzer-Apps

#### Verfügbare Listener-Methoden {#available-listener-methods}

| Methode                   | Auslöser                        | Bereich                       |
|---------------------------|---------------------------------|-------------------------------|
| `onAccess`                | Jeder Schlüssel wird gelesen     | Gesamter Namespace            |
| `onChange`                | Jeder Schlüssel wird geändert    | Gesamter Namespace            |
| `onKeyAccess("key")`      | Ein spezifischer Schlüssel wird gelesen | Pro Schlüssel                |
| `onKeyChange("key")`      | Ein spezifischer Schlüssel wird geändert | Pro Schlüssel                |

Jeder Listener erhält ein Ereignisobjekt mit:
- Dem Schlüsselnamen
- Dem alten Wert
- Dem neuen Wert
- Einem Verweis auf den Namespace

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

Die [webforJ Tic-Tac-Toe-Demo](https://github.com/webforj/webforj-tictactoe) bietet ein einfaches Zwei-Spieler-Spiel, bei dem die Züge zwischen den Benutzern geteilt werden. Das Projekt zeigt, wie `Namespace` verwendet werden kann, um den Zustand zu koordinieren, ohne auf externe Werkzeuge wie Datenbanken oder APIs angewiesen zu sein.

In diesem Beispiel wird ein gemeinsames Java-Spielobjekt in einem `PrivateNamespace` gespeichert, sodass mehrere Clients mit derselben Spiel-Logik interagieren können. Der Namespace dient als zentraler Container für den Spielzustand, der sicherstellt, dass:

- Beide Spieler konsistente Board-Aktualisierungen sehen
- Die Züge synchronisiert werden
- Die Spiel-Logik über Sitzungen hinweg geteilt wird

Es sind keine externen Dienste (wie REST oder WebSockets) erforderlich. Alle Koordination erfolgt über Namespaces, die deren Fähigkeit hervorhebt, gemeinsam genutzten Zustand in Echtzeit mit minimaler Infrastruktur zu verwalten.

Erkunden Sie den Code: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
