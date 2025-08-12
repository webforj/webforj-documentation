---
sidebar_position: 6
title: Jakarta Validation
_i18n_hash: eec00fac283efce49d856b1d40a48252
---
[Java Bean Validation](https://beanvalidation.org/) ist weithin als der Standard für die Integration von Validierungslogik in Java-Anwendungen anerkannt. Es nutzt einen einheitlichen Ansatz zur Validierung, indem es Entwicklern ermöglicht, Eigenschaften des Domänenmodells mit deklarativen Validierungsbeschränkungen zu annotieren. Diese Beschränkungen werden zur Laufzeit durchgesetzt, mit Optionen für sowohl eingebaut als auch benutzerdefiniert definierte Regeln.

webforJ integriert sich nahtlos mit Bean Validation über den `JakartaValidator`-Adapter und bietet sofort robuste Unterstützung.

## Installation {#installation}

Es ist notwendig, eine kompatible Implementierung wie [Hibernate Validator](https://hibernate.org/validator/) in Ihren Klassenpfad einzufügen. Wenn Ihre Umgebung dieses Implementierung standardmäßig nicht enthält, können Sie es manuell hinzufügen, indem Sie die folgenden Maven-Abhängigkeiten verwenden:

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

Die Klasse `JakartaValidator` dient als Adapter, der den webforJ-Bindungskontext mit Jakarta Validation verbindet. Diese Integration ermöglicht die Verwendung komplexer Validierungsregeln direkt über Anmerkungen in der Bean-Klasse.

### Aktivierung von `JakartaValidator` {#activating-jakartavalidator}

Um den `JakartaValidator` im gesamten Kontext zu aktivieren, verwenden Sie typischerweise den Parameter `useJakartaValidator`, wenn Sie den `BindingContext` konstruieren.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Definieren von Constraints für Bean-Eigenschaften {#defining-constraints-for-bean-properties}

Anmerkungsbasierte Constraints werden direkt in der Bean-Klasse angewendet, um die Validierungsbedingungen anzugeben, wie im folgenden Beispiel veranschaulicht:

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

Solche Constraints sind genauso effektiv wie die programmgesteuert während der Bindungsinitialisierung festgelegten, und sorgen für konsistente Validierungsergebnisse.

:::warning
Derzeit erkennt der `JakartaValidator` nur Constraints, die direkt auf Eigenschaften angewendet werden, und ignoriert alle Validierungen, die nicht direkt mit Eigenschaften verbunden sind.
:::
