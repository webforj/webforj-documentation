---
sidebar_position: 4
title: PropertyDescriptorTester
description: >-
  Validate PropertyDescriptor fields on wrapped web components by checking
  getters, setters, and default values with PropertyDescriptorTester.
_i18n_hash: 5b14fba4a11a4da57a032123bd27be6b
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

Le `PropertyDescriptorTester` dans webforJ simplifie les tests des **composants web tiers** intégrés dans votre application. Il valide que les propriétés définies avec [`PropertyDescriptor`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/PropertyDescriptor.html) sont correctement liées à leurs méthodes getter et setter et garantit que les comportements par défaut sont gérés de manière cohérente. Cet outil est particulièrement utile pour vérifier la fonctionnalité des propriétés exposées par des composants tiers sans nécessiter de logique de test répétitive.

<ExperimentalWarning />

## Vue d'ensemble {#overview}

Lors de l'utilisation de composants web tiers, il est essentiel de s'assurer que les propriétés se comportent comme prévu. Le `PropertyDescriptorTester` automatise ce processus en validant que les propriétés :
- Sont correctement mappées à leurs méthodes getter et setter.
- Maintiennent des valeurs par défaut et des comportements personnalisés attendus.
- Évitent les problèmes d'intégration courants, tels que des noms de propriétés non correspondants ou des défauts incohérents.

L'outil prend en charge les annotations pour des cas d'utilisation plus complexes, tels que l'exclusion de propriétés non pertinentes ou la définition de méthodes getter et setter personnalisées, ce qui en fait une option polyvalente pour les tests d'intégration.

## Comment fonctionne `PropertyDescriptorTester` {#how-propertydescriptortester-works}

Le processus de test implique plusieurs étapes automatisées :

1. **Analyse de classe** :
   Le `PropertyDescriptorScanner` identifie tous les champs `PropertyDescriptor` dans une classe de composant, en excluant automatiquement les champs annotés avec `@PropertyExclude`.

2. **Résolution de méthode** :
   Les méthodes getter et setter standard sont détectées en fonction des conventions de nommage (`get<PropertyName>`/`set<PropertyName>`). Pour les implémentations non standard, des annotations comme `@PropertyMethods` spécifient des noms de méthodes personnalisés ou des classes cibles.

3. **Validation** :
   Les valeurs par défaut sont attribuées à l'aide de la méthode setter, récupérées à l'aide du getter, et comparées pour garantir leur exactitude. Tout décalage déclenche une `AssertionError`, soulignant le problème spécifique.

4. **Rapport d'erreurs** :
   Le testeur fournit des retours détaillés sur toute défaillance de validation, telles que des méthodes manquantes, des défauts incohérents ou des configurations de propriétés incorrectes.

## Rédaction de tests avec `PropertyDescriptorTester` {#writing-tests-with-propertydescriptortester}

Voici un exemple démontrant la validation basique des propriétés pour un composant `AppLayout` :

### Exemple : validation basique {#example-basic-validation}

```java title="MyComponent.java"
public class MyComponent extends ElementCompositeContainer {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Titre par Défaut");

  // setters et getters
}
```

#### Cas de test {#test-case}

```java title="MyComponentTest.java"
import com.webforj.component.element.PropertyDescriptorTester;
import org.junit.jupiter.api.Test;

class MyComponentTest {

  MyComponent component = new MyComponent();

  @Test
  void validateProperties() {
    try {
      PropertyDescriptorTester.run(MyComponent.class, component);
    } catch (Exception e) {
      fail("Le test PropertyDescriptor a échoué : " + e.getMessage());
    }
  }
}
```

Ce test vérifie automatiquement :
- Que `drawerOpened` possède des méthodes getter et setter valides.
- Que `headerTitle` par défaut est `"Titre par Défaut"`.

## Cas d'utilisation avancés avec des annotations {#advanced-use-cases-with-annotations}

Pour des scénarios plus complexes, `PropertyDescriptorTester` prend en charge les annotations pour personnaliser ou exclure des propriétés des tests.

### Exclure des propriétés avec `@PropertyExclude` {#exclude-properties-with-propertyexclude}

Excluez les propriétés qui dépendent de systèmes externes ou qui ne sont pas pertinentes pour le test. Par exemple :

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
  PropertyDescriptor.property("excludedProperty", "Exclu");
```

### Personnaliser les méthodes avec `@PropertyMethods` {#customize-methods-with-propertymethods}

Définissez des getter, setter ou classes cibles personnalisés lorsque les conventions de nommage par défaut ne s'appliquent pas :

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
  PropertyDescriptor.property("customProperty", "Valeur par Défaut");
```
