---
sidebar_position: 6
title: Jakarta Validation
sidebar_class_name: updated-content
_i18n_hash: fa09682ac85db8e2c53ff9eea2d0633e
---
[Java Bean Validation](https://beanvalidation.org/) wird weithin als der Standard für die Integration von Validierungslogik in Java-Anwendungen anerkannt. Es verwendet einen einheitlichen Ansatz zur Validierung, indem es Entwicklern ermöglicht, Eigenschaften des Domänenmodells mit deklarativen Validierungsbeschränkungen zu annotieren. Diese Beschränkungen werden zur Laufzeit durchgesetzt, mit Optionen für sowohl integrierte als auch benutzerdefinierte Regeln.

webforJ integriert sich über den `JakartaValidator`-Adapter mit Bean Validation und bietet sofortige vollständige Unterstützung.

## Installation {#installation}

Es ist erforderlich, eine kompatible Implementierung, wie [Hibernate Validator](https://hibernate.org/validator/), in Ihrem Klassenpfad einzuschließen. Wenn Ihre Umgebung diese Implementierung nicht standardmäßig enthält, können Sie sie manuell mit den folgenden Maven-Abhängigkeiten hinzufügen:

```xml
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>8.0.1.Final</version>
</dependency>
<dependency>
    <groupId>org.glassfish.expressly</groupId>
    <artifactId>expressly</artifactId>
    <version>5.0.0</version>
</dependency>
```

## Der `JakartaValidator` {#the-jakartavalidator}

Die Klasse `JakartaValidator` dient als Adapter, der den webforJ-Bindungskontext mit Jakarta Validation verbindet. Diese Integration ermöglicht die Verwendung komplexer Validierungsregeln direkt über Annotations in der Bean-Klasse.

### Aktivierung des `JakartaValidator` {#activating-jakartavalidator}

Um den `JakartaValidator` im gesamten Kontext zu aktivieren, verwenden Sie normalerweise den Parameter `useJakartaValidator`, wenn Sie den `BindingContext` erstellen.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Definieren von Einschränkungen für Bean-Eigenschaften {#defining-constraints-for-bean-properties}

Annotationsbasierte Einschränkungen werden direkt in der Bean-Klasse angewendet, um die Validierungsbedingungen festzulegen, wie im nachstehenden Beispiel veranschaulicht:

```java
public class Hero {
  @NotEmpty(message = "Name darf nicht leer sein")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Unbestimmte Kraft")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Ungültige Kraft")
  private String power;

  // Getter und Setter
}
```

Solche Einschränkungen sind ebenso wirksam wie die programmgesteuerten, die während der Bindungsinitialisierung festgelegt werden, und erzeugen konsistente Validierungsergebnisse.

:::warning
Derzeit erkennt der `JakartaValidator` nur Einschränkungen, die direkt an Eigenschaften zugewiesen sind, und ignoriert alle Validierungen, die nicht direkt mit Eigenschaften verknüpft sind.
:::

### Lokalisierte Validierungsnachrichten <DocChip chip='since' label='25.12' /> {#locale-aware-validation-messages}

Jakarta Validation unterstützt lokalisierte Einschränkungsnachrichten durch standardisierte Nachrichteninterpolation. Wenn Sie die App-Lokalisierung ändern, muss der `JakartaValidator` die neue Lokalisierung kennen, damit er die Nachrichten in der richtigen Sprache auflösen kann.

`JakartaValidator` implementiert das Interface `LocaleAware`, was bedeutet, dass `BindingContext.setLocale()` automatisch die Lokalisierung an alle Jakarta-Validatoren im Kontext weitergibt. Sie müssen jeden Validator nicht manuell aktualisieren.

```java {5}
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);

// Wenn sich die Lokalisierung ändert, erzeugen Jakarta-Validatoren automatisch
// Nachrichten in der neuen Lokalisierung
context.setLocale(Locale.GERMAN);
```

In einer Komponente, die `LocaleObserver` implementiert, rufen Sie `context.setLocale()` innerhalb von `onLocaleChange()` auf, um die Validierungsnachrichten mit der UI-Sprache synchron zu halten:

```java {3}
@Override
public void onLocaleChange(LocaleEvent event) {
  context.setLocale(event.getLocale());
}
```

Siehe [dynamische Validierungsnachrichten](/docs/data-binding/validation/validators#dynamic-validation-messages) für weitere Informationen zu lokalisierungsbewussten Validierern.
