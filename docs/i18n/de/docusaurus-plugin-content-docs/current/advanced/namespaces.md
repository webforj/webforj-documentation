---
title: Namespaces
sidebar_position: 30
_i18n_hash: 7e34cfb824d0e1e4637bd40f4f1133cc
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Namespaces in webforJ bieten einen Mechanismus zur Speicherung und Abfrage gemeinsamer Daten über verschiedene Bereiche in einer Webanwendung hinweg. Sie ermöglichen die Kommunikation von Daten zwischen Komponenten und über Sitzungen hinweg, ohne auf traditionelle Speichertechniken wie Sitzungsattribute oder statische Felder angewiesen zu sein. Diese Abstraktion erlaubt es Entwicklern, den Zustand kontrolliert und threadsicher zu kapseln und darauf zuzugreifen. Namespaces sind ideal, um Tools für die Zusammenarbeit mehrerer Benutzer zu erstellen oder einfach konsistente globale Einstellungen zu pflegen, und ermöglichen Ihnen, Daten sicher und effizient zu koordinieren.

## Was ist ein Namespace? {#whats-a-namespace}

Ein Namespace ist ein benannter Container, der Schlüssel-Wert-Paare speichert. Diese Werte können je nach verwendetem Namespace-Typ in verschiedenen Teilen Ihrer Anwendung zugegriffen und verändert werden. Man kann sich das wie eine threadsichere, verteilte Karte mit integrierter Ereignisbehandlung und Sperrmechanismen vorstellen.

### Wann sollten Namespaces verwendet werden? {#when-to-use-namespaces}

Verwenden Sie Namespaces, wenn:

- Sie Werte über Benutzersitzungen oder Anwendungsbestandteile hinweg teilen müssen.
- Sie auf Wertänderungen über Listener reagieren möchten.
- Sie feingranulare Sperren für kritische Abschnitte benötigen.
- Sie den Zustand effizient über Ihre Anwendung hinweg speichern und abrufen müssen.

### Arten von Namespaces {#types-of-namespaces}

webforJ bietet drei Arten von Namespaces:

| Typ          | Geltungsbereich                                                                                                   | Typische Verwendung                          |
|--------------|-------------------------------------------------------------------------------------------------------------------|---------------------------------------------|
| **Privat**   | Geteilt zwischen Clients, die dasselbe Präfix und denselben Namen verwenden. Der Speicher wird automatisch freigegeben, wenn keine Referenzen mehr vorhanden sind. | Geteilter Zustand zwischen verwandten Benutzersitzungen. |
| **Gruppe**   | Von allen Threads, die von demselben übergeordneten Thread erzeugt werden, gemeinsam genutzt.                       | Koordination des Zustands innerhalb einer Thread-Gruppe. |
| **Global**   | Über alle Server-Threads (JVM-weit) zugänglich. Der Speicher bleibt erhalten, bis Schlüssel explizit entfernt werden. | Anwendungsweiter geteilter Zustand.         |

:::tip Auswahl eines Standards - Bevorzugen Sie `PrivateNamespace`
Im Zweifelsfall verwenden Sie einen `PrivateNamespace`. Er bietet sicheres, scoped Teilen zwischen verwandten Sitzungen, ohne den globalen oder serverweiten Zustand zu beeinflussen. Dies macht ihn zu einem zuverlässigen Standard für die meisten Anwendungen. 
:::

## Erstellen und Verwenden eines Namespaces {#creating-and-using-a-namespace}

Namespaces werden durch die Instanziierung eines der verfügbaren Typen erstellt. Jeder Typ definiert, wie und wo die Daten geteilt werden. Die folgenden Beispiele zeigen, wie man einen Namespace erstellt und mit seinen Werten interagiert.

### `Privat` Namespace {#private-namespace}

Der Name des privaten Namespaces besteht aus zwei Teilen:

- **Präfix**: Ein von Entwicklern definiertes Kennzeichen, das eindeutig für Ihre Anwendung oder Ihr Modul sein sollte, um Konflikte zu vermeiden.
- **Basename**: Der spezifische Name für den gemeinsamen Kontext oder die Daten, die Sie verwalten möchten.

Zusammen bilden sie den vollständigen Namen des Namespaces mit dem Format:

```text
prefix + "." + baseName
```

Zum Beispiel: `"myApp.sharedState"`.

Namespaces, die mit demselben Präfix und Basename erstellt wurden, beziehen sich immer auf die _gleiche zugrunde liegende Instanz_. Dies gewährleistet einen konsistenten gemeinsamen Zugang über alle Aufrufe zu `PrivateNamespace`, die dieselben Identifikatoren verwenden.

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
Beim Benennen eines `PrivateNamespace` sollten Sie folgende Regeln beachten:

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

Zusätzlich zu `PrivateNamespace` bietet webforJ zwei weitere Typen für breitere Freigabekontexte. Diese sind nützlich, wenn der Zustand über eine einzelne Sitzung oder Thread-Gruppe hinaus bestehen bleiben muss.

- **Global Namespace**: Über alle Server-Threads (JVM-weit) zugänglich.
- **Group Namespace**: Zwischen Threads, die von demselben übergeordneten Thread stammen, geteilt.

```java
// Global geteilter Zustand, anwendungsweit zugänglich
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Gruppespezifischer Zustand, begrenzt auf Threads, die einen gemeinsamen Eltern haben
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Arbeiten mit Werten {#working-with-values}

Namespaces bieten eine konsistente Schnittstelle zur Verwaltung gemeinsamer Daten über Schlüssel-Wert-Paare. Dazu gehört das Setzen, Abrufen, Entfernen von Werten, die Synchronisierung des Zugriffs und das Beobachten von Änderungen in Echtzeit.

### Werte setzen und entfernen {#setting-and-removing-values}

Verwenden Sie `put()`, um einen Wert unter einem bestimmten Schlüssel zu speichern. Wenn der Schlüssel derzeit gesperrt ist, wartet die Methode, bis die Sperre freigegeben wird oder die Zeitüberschreitung abläuft.

```java
// Wartet bis zu 20ms (Standard), um den Wert zu setzen
ns.put("username", "admin");

// Geben Sie eine benutzerdefinierte Zeitüberschreitung in Millisekunden an
ns.put("config", configObject, 100);
```

Um einen Schlüssel aus dem Namespace zu entfernen:

```java
ns.remove("username");
```

Sowohl `put()` als auch `remove()` sind blockierende Operationen, wenn der Zielschlüssel gesperrt ist. Wenn die Zeitüberschreitung abläuft, bevor die Sperre freigegeben wird, wird eine `NamespaceLockedException` ausgelöst.

Für sichere gleichzeitige Updates, bei denen Sie nur den Wert überschreiben müssen, verwenden Sie `atomicPut()`. Es sperrt den Schlüssel, schreibt den Wert und gibt die Sperre in einem Schritt frei:

```java
ns.atomicPut("counter", 42);
```

Dies verhindert Wettlaufbedingungen und vermeidet die Notwendigkeit manueller Sperren in einfachen Aktualisierungsszenarien.

### Werte abrufen {#getting-values}

Um einen Wert abzurufen, verwenden Sie `get()`:

```java
Object value = ns.get("username");
```

Wenn der Schlüssel nicht existiert, wirft dies eine `NoSuchElementException`. Um Ausnahmen zu vermeiden, verwenden Sie `getOrDefault()`:

```java
Object value = ns.getOrDefault("username", "guest");
```

Um zu überprüfen, ob ein Schlüssel definiert ist:

```java
if (ns.contains("username")) {
  // Schlüssel existiert
}
```

Wenn Sie einen Wert nur dann lazy initialisieren möchten, wenn er fehlt, verwenden Sie `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Dies ist nützlich für gemeinsame Werte, die einmal erstellt und wiederverwendet werden, wie Sitzungs-Token, Konfigurationsblöcke oder zwischengespeicherte Daten.

### Manuelle Sperrung {#manual-locking}

Wenn Sie mehrere Operationen auf demselben Schlüssel ausführen oder über mehrere Schlüssel hinweg koordinieren müssen, verwenden Sie die manuelle Sperrung.

```java
ns.setLock("flag", 500); // Wartet bis zu 500ms auf die Sperre

// Kritischer Abschnitt beginnt
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Kritischer Abschnitt endet

ns.removeLock("flag");
```

Verwenden Sie dieses Muster, wenn eine Folge von Operationen atomar über Lese- und Schreibvorgänge hinweg durchgeführt werden muss. Stellen Sie immer sicher, dass die Sperre freigegeben wird, um andere Threads nicht zu blockieren.

### Auf Änderungen hören {#listening-for-changes}

Namespaces unterstützen Ereignislistener, die es Ihnen ermöglichen, auf den Zugriff oder die Änderung von Werten zu reagieren. Dies ist nützlich für Szenarien wie:

- Protokollierung oder Prüfung des Zugriffs auf sensible Schlüssel
- Auslösen von Aktualisierungen, wenn sich ein Konfigurationswert ändert
- Überwachung von Änderungen des gemeinsamen Zustands in Mehrbenutzeranwendungen

#### Verfügbare Listener-Methoden {#available-listener-methods}

| Methode                      | Auslöser                          | Geltungsbereich        |
|------------------------------|----------------------------------|------------------------|
| `onAccess`                   | Ein Schlüssel wird gelesen        | Gesamter Namespace      |
| `onChange`                   | Ein Schlüssel wird modifiziert    | Gesamter Namespace      |
| `onKeyAccess("key")`         | Ein bestimmter Schlüssel wird gelesen | Pro Schlüssel         |
| `onKeyChange("key")`         | Ein bestimmter Schlüssel wird modifiziert | Pro Schlüssel         |

Jeder Listener erhält ein Ereignisobjekt, das enthält:
- Den Schlüsselnamen
- Den alten Wert
- Den neuen Wert
- Eine Referenz zum Namespace

#### Beispiel: Reagieren auf jede Schlüsseländerung {#example-respond-to-any-key-change}

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

Die [webforJ Tic-Tac-Toe Demo](https://github.com/webforj/webforj-tictactoe) bietet ein einfaches Zwei-Spieler-Spiel, bei dem die Züge zwischen den Benutzern geteilt werden. Das Projekt zeigt, wie `Namespace` verwendet werden kann, um den Zustand zu koordinieren, ohne auf externe Tools wie Datenbanken oder APIs angewiesen zu sein.

In diesem Beispiel wird ein gemeinsames Java-Spielobjekt in einem `PrivateNamespace` gespeichert, sodass mehrere Clients mit derselben Spiel-Logik interagieren können. Der Namespace dient als zentrales Container für den Spielzustand und stellt sicher, dass:

- Beide Spieler konsistente Aktualisierungen des Boards sehen
- Züge synchronisiert werden
- Die Spiel-Logik über Sitzungen hinweg geteilt wird

Es sind keine externen Dienste (wie REST oder WebSockets) erforderlich. Alle Koordination erfolgt über Namespaces und hebt deren Fähigkeit hervor, den gemeinsamen Zustand in Echtzeit mit minimaler Infrastruktur zu verwalten.

Erforschen Sie den Code: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
