---
sidebar_position: 6
title: Jakarta Validation
sidebar_class_name: updated-content
description: >-
  Apply Jakarta Bean Validation annotations to bean properties and activate
  JakartaValidator on a BindingContext with locale-aware messages.
_i18n_hash: e5b90cd31ee5ca5eab453a1c087967da
---
[Java Bean Validation](https://beanvalidation.org/) wird weithin als der Standard für die Integration von Validierungslogik in Java-Anwendungen anerkannt. Es verwendet einen einheitlichen Ansatz zur Validierung, indem es Entwicklern ermöglicht, Eigenschaften des Domänenmodells mit deklarativen Validierungsbeschränkungen zu annotieren. Diese Beschränkungen werden zur Laufzeit durchgesetzt, mit Optionen sowohl für integrierte als auch für benutzerdefinierte Regeln.

webforJ integriert sich über den `JakartaValidator`-Adapter mit Bean Validation und bietet sofortige volle Unterstützung.

## Installation {#installation}

Es ist erforderlich, eine kompatible Implementierung wie [Hibernate Validator](https://hibernate.org/validator/) in Ihren Klassenpfad aufzunehmen. Wenn Ihre Umgebung standardmäßig nicht mit dieser Implementierung kommt, können Sie sie manuell hinzufügen, indem Sie die folgenden Maven-Abhängigkeiten verwenden:

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

Die Klasse `JakartaValidator` dient als Adapter, der den Binding-Kontext von webforJ mit Jakarta Validation verbindet. Diese Integration ermöglicht die Verwendung komplexer Validierungsregeln direkt über Annotations in der Bean-Klasse.

### Aktivierung des `JakartaValidator` {#activating-jakartavalidator}

Um den `JakartaValidator` im gesamten Kontext zu aktivieren, verwenden Sie typischerweise den Parameter `useJakartaValidator`, wenn Sie den `BindingContext` erstellen.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Definieren von Einschränkungen für Bean-Eigenschaften {#defining-constraints-for-bean-properties}

Annotationsbasierte Einschränkungen werden direkt innerhalb der Bean-Klasse angewendet, um Validierungsbedingungen anzugeben, wie im folgenden Beispiel veranschaulicht:

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

Solche Einschränkungen sind ebenso effektiv wie die programmatisch während der Bindungsinitialisierung festgelegten und führen zu konsistenten Validierungsergebnissen.

:::warning
Derzeit erkennt der `JakartaValidator` nur Einschränkungen, die direkt an Eigenschaften zugewiesen sind, und ignoriert alle Validierungen, die nicht direkt mit Eigenschaften verbunden sind.
:::

### Validierung geschachtelter Beans <DocChip chip='since' label='26.01' /> {#validating-nested-beans}

Einschränkungen werden direkt auf die eigenen Felder der geschachtelten Bean erklärt. Wenn Sie eines dieser Felder über einen [punktierten Eigenschaftspfad](/docs/data-binding/bindings#nested-bean-properties) binden, gilt die Einschränkung für diese Eigenschaft auf die gleiche Weise wie für eine oberste Eigenschaft.

```java
public class Address {
  @NotBlank(message = "Straße ist erforderlich")
  @Size(max = 80, message = "Straße ist zu lang")
  private String street;

  // Getter und Setter
}
```

```java {6-7}
public class Hero {
  @NotEmpty(message = "Name darf nicht leer sein")
  @Length(min = 3, max = 20)
  private String name;

  // Eine geschachtelte Bean mit den Einschränkungen für address.street
  private Address address;

  // Getter und Setter
}
```

Die Bindung von `address.street` wird gegen `@NotBlank` auf `Address.street` validiert. Jede Bindung validiert die Eigenschaft am Ende ihres Pfades.

Das [Beispiel für geschachtelte Beans](https://github.com/webforj/built-with-webforj) bindet einen `Employee` mit geschachtelten `Address`- und `EmergencyContact`-Beans über einen einzigen `BindingContext`, der dieses Muster verwendet.

### Locale-sensible Validierungsnachrichten <DocChip chip='since' label='25.12' /> {#locale-aware-validation-messages}

Jakarta Validation unterstützt lokalisierte Einschränkungsnachrichten durch standardmäßige Nachrichteninterpolation. Wenn Sie die App-Lokalisierung ändern, muss der `JakartaValidator` die neue Lokalisierung kennen, damit er Nachrichten in der richtigen Sprache auflösen kann.

`JakartaValidator` implementiert das `LocaleAware`-Interface, was bedeutet, dass `BindingContext.setLocale()` die Lokalisierung automatisch an alle Jakarta-Validatoren im Kontext weitergibt. Sie müssen jeden Validator nicht manuell aktualisieren.

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

Siehe [dynamische Validierungsnachrichten](/docs/data-binding/validation/validators#dynamic-validation-messages) für weitere Informationen zu lokalen sensiblen Validatoren.
