---
sidebar_position: 6
title: Jakarta Validation
_i18n_hash: 68a57d576ce21a9f99b121e5db3cf85f
---
[Java Bean Validation](https://beanvalidation.org/) ist weithin als der Standard für die Integration von Validierungslogik in Java-Anwendungen anerkannt. Es nutzt einen einheitlichen Ansatz zur Validierung, indem es Entwicklern ermöglicht, die Eigenschaften des Domänenmodells mit deklarativen Validierungsbedingungen zu annotieren. Diese Bedingungen werden zur Laufzeit durchgesetzt, wobei Optionen für sowohl integrierte als auch benutzerdefinierte Regeln verfügbar sind.

webforJ integriert sich nahtlos mit Bean Validation durch den `JakartaValidator`-Adapter und bietet sofort robuste Unterstützung.

## Installation {#installation}

Es ist notwendig, eine kompatible Implementierung wie [Hibernate Validator](https://hibernate.org/validator/) in Ihren Klassenpfad einzuschließen. Wenn Ihre Umgebung standardmäßig nicht mit dieser Implementierung geliefert wird, können Sie sie manuell hinzufügen, indem Sie die folgenden Maven-Abhängigkeiten verwenden:

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

Die `JakartaValidator`-Klasse fungiert als Adapter, der den Binding-Kontext von webforJ mit Jakarta Validation verbindet. Diese Integration ermöglicht die Verwendung komplexer Validierungsregeln direkt über Annotations in der Bean-Klasse.

### Aktivierung des `JakartaValidator` {#activating-jakartavalidator}

Um den `JakartaValidator` im gesamten Kontext zu aktivieren, verwenden Sie typischerweise den `useJakartaValidator`-Parameter beim Erstellen des `BindingContext`.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Festlegen von Einschränkungen für Bean-Eigenschaften {#defining-constraints-for-bean-properties}

Annotierte Einschränkungen werden direkt innerhalb der Bean-Klasse angewendet, um Validierungsbedingungen festzulegen, wie im folgenden Beispiel veranschaulicht:

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

Solche Einschränkungen sind genauso effektiv wie die programmatisch während der Bindungsinitialisierung festgelegten, um konsistente Validierungsergebnisse zu gewährleisten.

:::warning
Derzeit erkennt der `JakartaValidator` nur Einschränkungen, die direkt Eigenschaften zugewiesen sind, und ignoriert alle Validierungen, die nicht direkt mit Eigenschaften verbunden sind.
:::
