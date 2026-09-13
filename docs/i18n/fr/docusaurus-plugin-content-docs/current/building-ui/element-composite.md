---
sidebar_position: 6
title: Composition d'éléments
sidebar_class_name: new-content
description: >-
  Wrap a custom HTML element or third-party web component in Java with
  ElementComposite, exposing its properties, attributes, and events through the
  Java API.
_i18n_hash: 2f1ddb4b3375c89dc29d9dbc9cee7303
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La classe `ElementComposite` enveloppe un élément HTML personnalisé ou un [composant web](https://developer.mozilla.org/fr/docs/Web/API/Web_components). Elle lie votre classe Java à l'élément sous-jacent `Element` et vous permet de travailler avec les propriétés, attributs et événements de cet élément via Java. Utilisez-la lorsque vous intégrez des composants web dans une application webforJ.

:::tip Quand utiliser `ElementComposite`
Utilisez `ElementComposite` lorsque vous enveloppez un composant web tiers dont webforJ ne fournit pas déjà. Si un composant webforJ intégré couvre le cas d'utilisation (comme `TextField`, `ColorField`, `Button`, etc.), utilisez celui-ci à la place. Pour un travail DOM unique qui n'a pas besoin d'être réutilisé, la classe `Element` peut être utilisée directement sans enveloppe.
:::

Ce guide démontre comment implémenter le [composant web de temps relatif Web Awesome](https://webawesome.com/docs/components/relative-time/) en utilisant la classe `ElementComposite`.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Annotations de classe {#class-annotations}

Trois annotations apparaissent couramment au début d'une sous-classe `ElementComposite` : `@NodeName` déclare la balise HTML que le composant encapsule, et `@JavaScript` et `@StyleSheet` chargent les ressources côté client dont dépend le composant web sous-jacent. `@NodeName` est requis et spécifique à `ElementComposite`. `@JavaScript` et `@StyleSheet` sont des annotations de ressources webforJ générales et fonctionnent sur n'importe quelle classe, y compris les vues, composants, ou la classe `App`.

### `@NodeName` {#nodename}

L'annotation `@NodeName` déclare la balise HTML que le composant encapsule. webforJ utilise ce nom lors de la création de l'élément sous-jacent dans le DOM.

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Le nom de la balise doit correspondre à l'élément personnalisé enregistré sur le client. Sans cette annotation, le framework ne peut pas déterminer quel élément créer.

À l'intérieur d'une sous-classe, `getNodeName()` lit la balise déclarée, et `getElement()` renvoie l'élément sous-jacent `Element` afin que vous puissiez appeler des méthodes de niveau DOM dessus directement.

### `@JavaScript` {#javascript}

L'annotation `@JavaScript` charge le script qui définit ou enregistre le composant web sous-jacent. Placez-la sur la classe afin que le script ne se charge que lorsque le composant est utilisé.

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Plusieurs annotations `@JavaScript` sont autorisées, et webforJ élimine automatiquement les doublons de chargement. Le même script ne sera pas chargé deux fois si plusieurs composants en dépendent.

Consultez [Importation de fichiers JavaScript](../managing-resources/importing-assets#importing-javascript-files) pour l'ensemble complet des options, y compris `top`, `attributes`, et le timing de chargement.

### `@StyleSheet` {#stylesheet}

L'annotation `@StyleSheet` charge un fichier CSS dont le composant dépend. Elle est utile pour les composants tiers qui livrent une feuille de style séparée, ou pour regrouper le style spécifique du composant aux côtés de l'enveloppe.

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

Pour les ressources emballées localement, utilisez le préfixe `ws://` pour référencer les fichiers dans `resources/static` :

```java
@StyleSheet("ws://components/relative-time.css")
```

Consultez [Importation de fichiers CSS](../managing-resources/importing-assets#importing-css-files) pour l'ensemble complet des options.

## Descripteurs de propriétés et d'attributs {#property-and-attribute-descriptors}

Les propriétés et attributs représentent l'état d'un composant web, tenant généralement des données ou une configuration. `ElementComposite` expose les deux via `PropertyDescriptor`.

Deux méthodes de fabrique sur `PropertyDescriptor` produisent le descripteur lui-même, une par cible de liaison :

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` lie à une propriété JavaScript sur le nœud DOM. `PropertyDescriptor.attribute()` lie à un attribut HTML. Le premier argument est le nom que le composant web attend. Le second est une valeur par défaut, qui fixe également le type Java du descripteur.

Déclarez le descripteur comme un champ privé sur le composant, puis lisez et écrivez à travers celui-ci avec `set(PropertyDescriptor<V> property, V value)` et `get(PropertyDescriptor<V> property)`.

:::info
Les propriétés sont l'état interne sur le nœud DOM et ne se reflètent pas dans le balisage. Les attributs sontun balisage HTML, visible pour les scripts externes et CSS.
:::

```java
// Exemples de propriété appelée "title" dans une classe ElementComposite
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Exemples d'attribut appelé "value" dans une classe ElementComposite
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "Mon Titre");
set(value, "Ma Valeur");
```

Les appels ci-dessus utilisent `set()` directement pour montrer la forme primitive. En pratique, `set()` et `get()` sont des méthodes `protected` sur `ElementComposite`. Ce sont la couche primitive qui synchronise les valeurs Java avec l'élément sous-jacent, et non l'API publique que les consommateurs appellent. Le modèle prévu est de garder le `PropertyDescriptor` privé et d'écrire des méthodes publiques `setX()` et `getX()` qui délèguent aux primitives.

```java
@NodeName("my-card")
public class Card extends ElementComposite {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Card setHeading(String value) {
    set(heading, value);     // primitive protégée
    return this;
  }

  public String getHeading() {
    return get(heading);     // primitive protégée
  }
}
```

Un seul appel à `set(descriptor, value)` effectue trois choses à la fois. Il pousse la valeur au client via `setProperty()` pour les propriétés, ou `setAttribute()` pour les attributs. Il stocke la valeur dans un cache serveur local, une carte par instance de composant. Et il enregistre le type d'exécution aux côtés de la valeur, de sorte que les appels `get()` ultérieurs sachent comment désérialiser.

Ce cache local est la raison pour laquelle `get()` peut être bon marché par défaut. `get(descriptor)` renvoie la valeur mise en cache du magasin côté serveur sans appel réseau, car chaque `set()` maintient le cache synchronisé avec le client. Le deuxième argument optionnel `boolean` contrôle s'il faut contourner le cache et lire plutôt dans le navigateur.

```java
String cached = get(heading);            // lit à partir du cache côté serveur
String live = get(heading, true);        // force une lecture depuis le navigateur
```

Définissez `fromClient` sur true lorsque la valeur peut changer sur le client sans la connaissance du serveur, comme une valeur `<input>` saisie. Pour les propriétés pilotées par le serveur, la valeur par défaut évite un aller-retour.

Le troisième argument optionnel est un `java.lang.reflect.Type` et contrôle comment le résultat est désérialisé. webforJ résout le type dans cet ordre : l'argument `Type` explicite s'il est passé, puis le type d'exécution enregistré par un précédent `set()` sur le même descripteur, puis `Object.class`. En pratique, le type enregistré par un `set()` précédent est suffisant, il est donc généralement possible d'omettre le troisième argument. Il est nécessaire lorsque la classe enregistrée perd des informations sur lesquelles le désérialiseur dépend, comme un type paramétré tel que `List<String>` dont la classe d'exécution est simplement `ArrayList`.

La démo ci-dessous ajoute des propriétés pour le temps relatif sur la base de la documentation du composant web et les expose via des accesseurs et des mutateurs. Chaque ligne dans le flux d'activité utilise des valeurs `format` et `numeric` différentes pour montrer comment le même composant se rend sous des configurations variées.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### Propriétés versus attributs {#properties-versus-attributes}

Bien que `PropertyDescriptor.property()` et `PropertyDescriptor.attribute()` semblent interchangeables, ils ciblent différentes parties de l'élément sous-jacent. Choisir le mauvais résulte en valeurs qui échouent silencieusement à s'appliquer.

Les propriétés sont des propriétés d'objet JavaScript sur le nœud DOM. Elles peuvent contenir n'importe quel type, y compris des chaînes, des booléens, des nombres, des objets et des tableaux, et elles représentent l'état d'exécution actuel de l'élément. Définir une propriété est une affectation JavaScript directe.

Les attributs sont un balisage HTML. Ils vivent sur la balise d'ouverture de l'élément, sont toujours des chaînes, et représentent la configuration initiale de l'élément. Définir un attribut déclenche une mutation DOM et une conversion en chaîne.

Pour certains cas, les deux restent synchronisés. Pour d'autres, elles divergent. La `value` d'un `<input>` est l'exemple classique : l'attribut `value` est la valeur initiale, tandis que la propriété `value` est la valeur actuelle que l'utilisateur a tapée. Lire l'attribut après que l'utilisateur ait tapé renvoie le balisage d'origine, mais lire la propriété renvoie le contenu actuel du champ.

Utilisez **les propriétés** pour :

- **Un état d'exécution qui change fréquemment** : compteurs, sélections actuelles, valeurs saisies
- **Types non-chaînes** : booléens, nombres, objets, tableaux
- **Mises à jour sensibles aux performances** : les propriétés contournent la conversion en chaîne requise pour les attributs

Utilisez **les attributs** pour :

- **Configuration initiale** : paramètres que le composant lit une fois lorsqu'il se connecte
- **Sélecteurs CSS** : valeurs que vous voulez cibler avec des sélecteurs comme `[disabled]` ou `[variant="danger"]`
- **Crochets d'accessibilité** : `aria-label`, `role`, et d'autres attributs ARIA
- **Paramètres de type chaîne qui changent rarement**

Lors de l'enveloppement d'un composant web tiers, consultez la documentation du composant pour confirmer quel nom correspond à une propriété et quel nom correspond à un attribut. Utiliser `PropertyDescriptor.attribute()` pour quelque chose que le composant expose uniquement comme une propriété ne fonctionnera pas, et il en va de même dans l'autre sens. Le composant ignorera silencieusement la valeur.

### Typage des propriétés {#typing-properties}

Un descripteur est paramétré par le type Java de sa valeur. La syntaxe complète de déclaration est :

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

Le paramètre générique `<T>` déclare le type de la valeur. Le type d'exécution de la valeur par défaut fixe également `T`, donc l'argument générique doit rarement être spécifié explicitement. webforJ utilise `T` pour sérialiser et désérialiser des valeurs lors de la communication avec le client.

```java
private final PropertyDescriptor<String> label =
    PropertyDescriptor.property("label", "");

private final PropertyDescriptor<Boolean> disabled =
    PropertyDescriptor.property("disabled", false);

private final PropertyDescriptor<Integer> max =
    PropertyDescriptor.property("max", 100);

private final PropertyDescriptor<Double> step =
    PropertyDescriptor.property("step", 1.0);
```

La sérialisation est automatique pour les primitifs, leurs équivalents encapsulés, et `String`. Pour les types complexes, la valeur est sérialisée au format JSON avant d'être assignée à la propriété sur le client.

### Validation des valeurs {#validating-values}

Validez les valeurs dans le mutateur avant d'appeler `set()`. Le mutateur est le point d'application naturel car chaque mutation passe par celui-ci.

```java
private final PropertyDescriptor<Integer> max =
    PropertyDescriptor.property("max", 100);

public Slider setMax(int value) {
  if (value < 0) {
    throw new IllegalArgumentException("max doit être non-négatif");
  }
  set(max, value);
  return this;
}
```

Pour des références qui peuvent être null, utilisez `Objects.requireNonNull()` afin que l'échec émerge à la frontière plutôt que plus tard dans le pipeline de rendu.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading ne peut pas être null");
  set(heading, value);
  return this;
}
```

Évitez de valider dans `get()`. Les lectures doivent rester bon marché et cohérentes.

### Propriétés de style énuméré {#enum-style-properties}

La plupart des composants web s'attendent à des valeurs de chaîne en minuscules ou en kebab-case pour les propriétés de style énuméré (`theme="primary"`, `expanse="xs"`). webforJ utilise Gson pour sérialiser les énumérations, mais la représentation par défaut de Gson est le nom de la constante en majuscules. Annotez chaque constante avec `@SerializedName` afin que la valeur sérialisée corresponde à ce que le composant web attend.

```java
import com.google.gson.annotations.SerializedName;

public enum Variant {
  @SerializedName("primary")
  PRIMARY,

  @SerializedName("secondary")
  SECONDARY,

  @SerializedName("danger")
  DANGER
}
```

Déclarez le descripteur avec le type énuméré et utilisez l'énumération directement dans le mutateur et l'accesseur.

```java
private final PropertyDescriptor<Variant> variant =
    PropertyDescriptor.property("variant", Variant.PRIMARY);

public MyButton setVariant(Variant value) {
  set(variant, value);
  return this;
}

public Variant getVariant() {
  return get(variant);
}
```

C'est le même modèle que les composants intégrés de webforJ utilisent pour `Theme`, `Expanse` et des énumérations similaires. L'API Java publique reste sûre pour les types, et la valeur que le composant web reçoit est la chaîne provenant de `@SerializedName`.

### Tester les propriétés {#testing-properties}

`PropertyDescriptorTester` valide que chaque `PropertyDescriptor` dans un composant est correctement câblé. Il scanne la classe pour les champs de descripteur, appelle chaque mutateur avec la valeur par défaut, et compare le résultat à ce que l'accesseur renvoie. Le testeur détecte les erreurs d'intégration avant qu'elles n'atteignent une application en cours d'exécution : un mutateur qui écrit sur le mauvais descripteur, un accesseur qui lit une propriété différente, une valeur par défaut qui ne fait pas de round-trip, ou un accesseur manquant pour un descripteur déclaré.

Un test de base pour un composant ressemble à ceci :

```java
import com.webforj.component.element.PropertyDescriptorTester;
import org.junit.jupiter.api.Test;

class CardTest {

  @Test
  void validateProperties() {
    Card component = new Card();
    PropertyDescriptorTester.run(Card.class, component);
  }
}
```

#### Exclusion des propriétés {#excluding-properties}

Certaines descripteurs ne suivent pas les conventions standard d'accesseurs, ou dépendent d'un état externe que le test ne peut pas satisfaire. Annoter ceux-ci avec `@PropertyExclude` pour les ignorer.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Noms d'accesseurs et de mutateurs personnalisés {#custom-getter-and-setter-names}

Si un descripteur utilise des noms d'accesseurs non standards, déclarez-les avec `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

Le paramètre `target` accepte une classe lorsque les accesseurs se trouvent ailleurs que dans le composant lui-même.

Pour plus de détails sur la surface de test, consultez [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Interfaces de préoccupation {#concern-interfaces}

Les interfaces de préoccupation donnent à une sous-classe `ElementComposite` des capacités sans écrire vous-même l'implémentation. Les interfaces transmettent les appels à l'élément sous-jacent. Implémentez celles que le composant doit prendre en charge, paramétrées avec le type de sous-classe afin que la chaîne retourne le composant :

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // Aucune implémentation requise.
}

MyBadge badge = new MyBadge()
    .setText("Nouveau")
    .addClassName("highlight")
    .setStyle("color", "var(--dwc-color-primary)");
```

Les trois interfaces ci-dessus couvrent tout ce que `MyBadge` a besoin sans aucune méthode dans la classe. `HasText` expose `setText()` et écrit dans le contenu textuel de l'élément. `HasClassName` expose `addClassName()`, ce qui permet au badge d'être ciblé depuis CSS. `HasStyle` expose `setStyle()` pour le style en ligne.

Pour l'ensemble complet des interfaces disponibles et ce que chacune fournit, voir [Interfaces de préoccupation](./component-fundamentals#concern-interfaces) dans l'article Comprendre les composants. Si un transfert par défaut ne correspond pas à ce que l'élément enveloppé expose, overridez la méthode dans la sous-classe.

## Événements {#events}

### Enregistrement d'événements {#event-registration}

Les composants web déclenchent des événements DOM lorsque quelque chose se produit dans le navigateur. Pour réagir depuis Java, écoutez ces événements avec `addEventListener()`. L'ensemble des événements qu'un composant déclenche varie, donc vérifiez la documentation du composant pour les noms et les charges utiles disponibles.

`ElementComposite` prend en charge le débouncing, le throttling, le filtrage, et des données d'événements personnalisées sur les écouteurs enregistrés.

Enregistrez les écouteurs d'événements en utilisant la méthode `addEventListener()` :

```java
// Exemple : Ajout d'un écouteur d'événements de clic
addEventListener(ElementClickEvent.class, event -> {
  // Gérer l'événement de clic
});
```

:::info
`ElementComposite` n'accepte que les classes d'événements annotées avec `@EventName`, contrairement à `Element`, qui accepte tout nom d'événement en chaîne.
:::

### Classes d'événements intégrées {#built-in-event-classes}

`ElementClickEvent` est la seule classe d'événement intégrée qui vient avec `ElementComposite`. Elle expose les événements de clic de souris sur l'élément sous-jacent avec des accesseurs typés pour les coordonnées (`getClientX()`, `getClientY()`), des informations sur les boutons (`getButton()`), et des touches de modification (`isCtrlKey()`, `isShiftKey()`, etc.).

Pour exposer la gestion des clics sur l'API publique d'une sous-classe, implémentez l'interface de préoccupation `HasElementClickListener<T>`. Elle fournit les méthodes par défaut `onClick()` et `addClickListener()` qui délèguent à la primitive protégée `addEventListener()`.

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasElementClickListener<MyBadge> {
  // onClick() et addClickListener() sont maintenant disponibles sur MyBadge
}

new MyBadge().onClick(event -> {
  if (event.isShiftKey()) {
    // ...
  }
});
```

Pour tout autre événement que le composant web sous-jacent déclenche, définissez une classe d'événement personnalisée. Voir [Classes d'événements personnalisées](#custom-event-classes).

### Charges utiles des événements {#event-payloads}

Les événements transportent des données du client à votre code Java. Accédez à ces données via `getData()` pour les données d'événements brutes ou utilisez des méthodes typées lorsque disponibles sur les classes d'événements intégrées. Voir le [guide des événements](../building-ui/events) pour plus d'informations sur la gestion efficace des charges utiles.

### Classes d'événements personnalisées {#custom-event-classes}

Définissez des classes d'événements personnalisées avec `@EventName` et `@EventOptions` pour capturer des données côté client dans un événement Java typé. Utilisez cela lorsque le gestionnaire Java a besoin de valeurs provenant du navigateur.

`@EventName` lie la classe Java à l'événement que le composant déclenche dans le navigateur, de sorte qu'une classe annotée `@EventName("change")` se déclenche chaque fois que l'élément sous-jacent émet `change`. `@EventOptions` contrôle ce qui voyage avec cet événement. Chaque `@EventData` à l'intérieur lui associe une clé avec une expression JavaScript évaluée par rapport à l'événement DOM. Le résultat est disponible dans la classe d'événements Java via `getData().get(key)`.

Le formulaire d'examen de produit ci-dessous utilise ce modèle avec [`wa-rating`](https://webawesome.com/docs/components/rating/). Le `ChangeEvent` personnalisé porte la valeur de la note en tant que `double` typé, et l'auditeur l'utilise pour activer le bouton de soumission :

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Options d'événements {#event-options}

`ElementEventOptions` configure la charge utile de l'événement, le timing de débounce ou throttle, les expressions de filtrage, et le code d'exécution préalable. Le fragment ci-dessous montre les options :

```java
ElementEventOptions options = new ElementEventOptions()
  // Collecter des données personnalisées du client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Exécuter JavaScript avant que l'événement ne se déclenche
  .setCode("component.classList.add('processing');")

  // Ne déclencher que si les conditions sont remplies
  .setFilter("component.value.length >= 2")

  // Retarder l'exécution jusqu'à ce que l'utilisateur cesse de taper (300 ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Appliquer ces options lors de l'enregistrement d'un écouteur pour une classe d'événement personnalisée
// (voir la section Classes d'événements personnalisées ci-dessus sur la façon d'en définir une) :
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` expose uniquement la forme basée sur les classes `addEventListener(Class, listener, options)`. Utilisez-le avec une classe d'événement annotée avec `@EventName`. Pour s'enregistrer directement contre un nom d'événement en chaîne, appelez `getElement().addEventListener("input", listener, options)`.
:::

#### Contrôle des performances {#performance-control}

**Le débounce** retarde l'exécution jusqu'à ce que l'activité cesse :

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Attendre 300 ms après le dernier événement
```

Phases de débounce disponibles :

- `LEADING`: Déclencher immédiatement, puis attendre
- `TRAILING`: Attendre une période de silence, puis déclencher (par défaut)
- `BOTH`: Déclencher immédiatement et après une période de silence

**Le throttling** limite la fréquence d'exécution :

```java
options.setThrottle(100); // Déclencher au plus une fois toutes les 100 ms
```

## Interagir avec des slots {#interacting-with-slots}

Les slots sont des espaces réservés à l'intérieur d'un composant web que les utilisateurs remplissent avec du contenu. Le composant web déclare ses slots dans son modèle avec `<slot>` ou `<slot name="...">`, et l'enveloppe expose des méthodes qui placent des composants Java dans ces slots.

Pour ajouter du contenu dans des slots, étendez `ElementCompositeContainer` au lieu de `ElementComposite`. Le conteneur porte le même mécanisme de propriété et d'attribut, plus les méthodes nécessaires pour ajouter des enfants. Les enfants ajoutés via `add()` vont dans le slot par défaut. Les enfants ajoutés via `getElement().add(slotName, components)` vont dans le slot nommé.

```java
@NodeName("my-dialog")
public class Dialog extends ElementCompositeContainer {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Dialog setHeading(String value) {
    set(heading, value);
    return this;
  }

  public Dialog addToFooter(Component... components) {
    getElement().add("footer", components);
    return this;
  }
}
```

La démo ci-dessous montre deux cartes de prix construites avec [`wa-card`](https://webawesome.com/docs/components/card/), remplissant les slots `header`, par défaut et `footer` depuis Java :

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### Inspection du contenu des slots {#inspecting-slot-contents}

L'élément sous-jacent `Element` (accessible via `getElement()`) fournit des méthodes pour lire ce qui est actuellement assigné aux slots :

- **`findComponentSlot()`** : cherche tous les slots pour un composant spécifique et renvoie le nom du slot contenant ce composant, ou une chaîne vide si le composant n'est dans aucun slot.
- **`getComponentsInSlot()`** : renvoie la liste des composants assignés à un slot donné. Prend en option un type de classe pour filtrer les résultats.
- **`getFirstComponentInSlot()`** : renvoie le premier composant assigné à un slot. Prend en option un type de classe pour filtrer.
