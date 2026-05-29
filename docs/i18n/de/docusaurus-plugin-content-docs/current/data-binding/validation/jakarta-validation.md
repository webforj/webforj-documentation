---
sidebar_position: 6
title: Jakarta Validation
sidebar_class_name: updated-content
_i18n_hash: 813ccefe385954366010291f50215611
---
[Java Bean Validation](https://beanvalidation.org/) wird allgemein als der Standard für die Integration von Validierungslogik in Java-Anwendungen anerkannt. Es verwendet einen einheitlichen Ansatz zur Validierung, indem es Entwicklern ermöglicht, Eigenschaften des Domainmodells mit deklarativen Validierungsconstraints zu annotieren. Diese Constraints werden zur Laufzeit durchgesetzt, mit Optionen für sowohl integrierte als auch benutzerdefinierte Regeln.

webforJ integriert sich mit Bean Validation über den `JakartaValidator`-Adapter und bietet vollständige Unterstützung direkt out of the box.

## Installation {#installation}

Es ist notwendig, eine kompatible Implementierung wie [Hibernate Validator](https://hibernate.org/validator/) in Ihren Klassenpfad aufzunehmen. Wenn Ihre Umgebung standardmäßig nicht mit dieser Implementierung geliefert wird, können Sie sie manuell mit den folgenden Maven-Abhängigkeiten hinzufügen:

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

Die Klasse `JakartaValidator` dient als Adapter und verbindet den webforJ-Bindungskontext mit Jakarta Validation. Diese Integration ermöglicht die Verwendung komplexer Validierungsregeln direkt über Annotationen in der Bean-Klasse.

### Aktivierung des `JakartaValidator` {#activating-jakartavalidator}

Um den `JakartaValidator` im gesamten Kontext zu aktivieren, verwenden Sie typischerweise den Parameter `useJakartaValidator`, wenn Sie den `BindingContext` erstellen.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Definition von Constraints für Bean-Eigenschaften {#defining-constraints-for-bean-properties}

Annotation-basierte Constraints werden direkt innerhalb der Bean-Klasse angewendet, um Validierungsbedingungen anzugeben, wie im folgenden Beispiel dargestellt:

```java
public class Hero {
  @NotEmpty(message = "Name darf nicht leer sein")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Unspecified power")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Ungültige Kraft")
  private String power;

  // Getter und Setter
}
```

Solche Constraints sind ebenso effektiv wie die, die programmgesteuert während der Bindungsinitialisierung festgelegt werden, und produzieren konsistente Validierungsergebnisse.

:::warning
Aktuell erkennt der `JakartaValidator` nur Constraints, die direkt den Eigenschaften zugewiesen sind, und ignoriert alle Validierungen, die nicht direkt mit Eigenschaften verbunden sind.
:::

### Lokalisierte Validierungsnachrichten <DocChip chip='since' label='25.12' /> {#locale-aware-validation-messages}

Jakarta Validation unterstützt lokalisierte Constraint-Nachrichten durch standardmäßige Nachrichteninterpolation. Wenn Sie die App-Lokalisierung ändern, muss der `JakartaValidator` die neue Lokalisierung kennen, damit er Nachrichten in der richtigen Sprache auflösen kann.

`JakartaValidator` implementiert das `LocaleAware`-Interface, was bedeutet, dass `BindingContext.setLocale()` automatisch die Lokalisierung an alle Jakarta-Validatoren im Kontext weitergibt. Sie müssen nicht jeden Validator manuell aktualisieren.

```java {5}
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);

// Wenn sich die Lokalisierung ändert, erzeugen Jakarta-Validatoren automatisch
// Nachrichten in der neuen Lokalisierung
context.setLocale(Locale.GERMAN);
```

In einer Komponente, die `LocaleObserver` implementiert, rufen Sie `context.setLocale()` innerhalb von `onLocaleChange()` auf, um die Validierungsnachrichten mit der Benutzeroberflächensprache synchron zu halten:

```java {3}
@Override
public void onLocaleChange(LocaleEvent event) {
  context.setLocale(event.getLocale());
}
```

Siehe [dynamische Validierungsnachrichten](/docs/data-binding/validation/validators#dynamic-validation-messages) für weitere Informationen zu lokalisierte Validatoren.
