---
sidebar_position: 6
title: Element Composite
description: >-
  Wrap a custom HTML element or third-party web component in Java with
  ElementComposite, exposing its properties, attributes, and events through the
  Java API.
_i18n_hash: 2a742b2589b096aff73a1fcb67e041c1
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La classe `ElementComposite` encapsule un élément HTML personnalisé ou un [web component](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Elle associe votre classe Java à l'élément `Element` sous-jacent et vous permet de travailler avec les propriétés, les attributs et les événements de cet élément via Java. Utilisez-la lorsque vous intégrez des web components dans une application webforJ.

:::tip Quand utiliser `ElementComposite`
Utilisez `ElementComposite` lorsque vous enveloppez un web component tiers que webforJ ne fournit pas déjà. Si un composant intégré de webforJ couvre le cas d'utilisation (`TextField`, `ColorField`, `Button`, etc.), utilisez-le à la place. Pour des travaux DOM ponctuels qui n'ont pas besoin d'être réutilisés, la classe `Element` peut être utilisée directement sans wrapper.
:::

Ce guide démontre comment implémenter le [web component relatif au temps Web Awesome](https://webawesome.com/docs/components/relative-time/) en utilisant la classe `ElementComposite`.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Annotations de classe {#class-annotations}

Trois annotations apparaissent couramment en haut d'un sous-classe `ElementComposite` : `@NodeName` déclare la balise HTML que le composant encapsule, et `@JavaScript` et `@StyleSheet` chargent les actifs côté client dont dépend le web component sous-jacent. `@NodeName` est requis et spécifique à `ElementComposite`. `@JavaScript` et `@StyleSheet` sont des annotations d'actifs webforJ générales et fonctionnent sur n'importe quelle classe, y compris les vues, les composants ou la classe `App`.

### `@NodeName` {#nodename}

L'annotation `@NodeName` déclare la balise HTML que le composant encapsule. webforJ utilise ce nom lors de la création de l'élément sous-jacent dans le DOM.

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Le nom de la balise doit correspondre à l'élément personnalisé enregistré côté client. Sans cette annotation, le framework ne peut pas déterminer quel élément créer.

À l'intérieur d'une sous-classe, `getNodeName()` lit la balise déclarée, et `getElement()` retourne l'élément sous-jacent afin que vous puissiez appeler des méthodes de niveau DOM directement sur celui-ci.

### `@JavaScript` {#javascript}

L'annotation `@JavaScript` charge le script qui définit ou enregistre le web component sous-jacent. Placez-la sur la classe afin que le script ne se charge que lorsque le composant est utilisé.

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Plusieurs annotations `@JavaScript` sont autorisées, et webforJ déduplique automatiquement les chargements. Le même script ne se chargera pas deux fois si plusieurs composants en dépendent.

Voir [Importation de fichiers JavaScript](../managing-resources/importing-assets#importing-javascript-files) pour l'ensemble complet d'options, y compris `top`, `attributes`, et timing de chargement.

### `@StyleSheet` {#stylesheet}

L'annotation `@StyleSheet` charge un fichier CSS dont dépend le composant. Elle est utile pour les composants tiers qui expédient une feuille de style séparée, ou pour regrouper le style spécifique au composant avec le wrapper.

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

Pour les actifs groupés localement, utilisez le préfixe `ws://` pour référencer les fichiers dans `resources/static` :

```java
@StyleSheet("ws://components/relative-time.css")
```

Voir [Importation de fichiers CSS](../managing-resources/importing-assets#importing-css-files) pour l'ensemble complet d'options.

## Descripteurs de propriété et d'attribut {#property-and-attribute-descriptors}

Les propriétés et les attributs représentent l'état d'un web component, contenant généralement des données ou des configurations. `ElementComposite` expose les deux à travers `PropertyDescriptor`.

Deux méthodes de fabrication sur `PropertyDescriptor` produisent le descripteur lui-même, une par cible de liaison :

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` se lie à une propriété JavaScript sur le nœud DOM. `PropertyDescriptor.attribute()` se lie à un attribut HTML. Le premier argument est le nom que le web component attend. Le second est une valeur par défaut, qui fixe également le type Java du descripteur.

Déclarez le descripteur en tant que champ privé sur le composant, puis lisez et écrivez à travers lui avec `set(PropertyDescriptor<V> property, V value)` et `get(PropertyDescriptor<V> property)`.

:::info
Les propriétés sont l'état interne sur le nœud DOM et ne se reflètent pas dans le balisage. Les attributs sont un balisage HTML, visibles pour les scripts et le CSS externes.
:::

```java
// Exemple de propriété appelée "title" dans une classe ElementComposite
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Exemple d'attribut appelé "value" dans une classe ElementComposite
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "Mon Titre");
set(value, "Ma Valeur");
```

Les appels ci-dessus utilisent `set()` directement pour montrer la forme primitive. Dans la pratique, `set()` et `get()` sont des méthodes `protected` sur `ElementComposite`. Ce sont la couche primitive qui synchronise les valeurs Java avec l'élément sous-jacent, et non l'API publique à laquelle les consommateurs font appel. Le modèle intentionnel est de garder le `PropertyDescriptor` privé et d'écrire des méthodes publiques `setX()` et `getX()` qui délèguent aux primitives.

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

Un seul appel à `set(descriptor, value)` fait trois choses à la fois. Il pousse la valeur au client via `setProperty()` pour les propriétés, ou `setAttribute()` pour les attributs. Il stocke la valeur dans un cache local côté serveur, une carte par instance de composant. Et il enregistre le type d'exécution aux côtés de la valeur, afin que les appels ultérieurs à `get()` sachent comment désérialiser.

Ce cache local est la raison pour laquelle `get()` peut être bon marché par défaut. `get(descriptor)` renvoie la valeur mise en cache à partir du magasin côté serveur sans appel réseau, car chaque `set()` maintient le cache synchronisé avec le client. Le second argument `boolean` optionnel contrôle s'il faut contourner le cache et lire depuis le navigateur à la place.

```java
String cached = get(heading);            // lit depuis le cache côté serveur
String live = get(heading, true);        // force une lecture depuis le navigateur
```

Définissez `fromClient` sur true lorsque la valeur peut changer sur le client sans que le serveur le sache, comme une valeur `<input>` tapée. Pour les propriétés pilotées par le serveur, la valeur par défaut évite un aller-retour.

Le troisième argument optionnel est un `java.lang.reflect.Type` et contrôle comment le résultat est désérialisé. webforJ résout le type dans cet ordre : l'argument `Type` explicite s'il est passé, puis le type d'exécution enregistré par un précédent `set()` sur le même descripteur, puis `Object.class`. Dans la pratique, le type enregistré par un précédent `set()` est suffisant, donc le troisième argument peut généralement être omis. Il est nécessaire lorsque la classe enregistrée perd des informations dont le désérialiseur dépend, comme un type paramétré tel que `List<String>` dont la classe d'exécution est juste `ArrayList`.

La démo ci-dessous ajoute des propriétés pour le temps relatif en fonction des documents du web component et les expose via des accesseurs et des mutateurs. Chaque ligne dans le fil d'activité utilise des valeurs différentes pour `format` et `numeric` pour montrer comment le même composant se rend sous des configurations variées.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### Propriétés versus attributs {#properties-versus-attributes}

Bien que `PropertyDescriptor.property()` et `PropertyDescriptor.attribute()` paraissent interchangeables, elles visent des parties différentes de l'élément sous-jacent. Choisir la mauvaise conduit à des valeurs qui échouent silencieusement à s'appliquer.

Les propriétés sont des propriétés d'objet JavaScript sur le nœud DOM. Elles peuvent contenir n'importe quel type, y compris des chaînes, booléens, nombres, objets et tableaux, et elles représentent l'état actuel d'exécution de l'élément. La définition d'une propriété est une simple affectation JavaScript.

Les attributs sont du balisage HTML. Ils se trouvent sur la balise d'ouverture de l'élément, sont toujours des chaînes, et représentent la configuration initiale de l'élément. La définition d'un attribut déclenche une mutation du DOM et une conversion en chaîne.

Dans certains cas, les deux restent synchronisés. Dans d'autres, elles divergent. La `value` d'un `<input>` est l'exemple classique : l'attribut `value` est la valeur initiale, tandis que la propriété `value` est la valeur actuelle que l'utilisateur a tapée. Lire l'attribut après que l'utilisateur a tapé renvoie le balisage d'origine, mais lire la propriété renvoie le contenu actuel du champ.

Utilisez **les propriétés** pour :

- **État d'exécution changeant fréquemment** : compteurs, sélections actuelles, valeurs tapées
- **Types non-chaînes** : booléens, nombres, objets, tableaux
- **Mises à jour sensibles aux performances** : les propriétés évitent la conversion de chaîne requise pour les attributs

Utilisez **les attributs** pour :

- **Configuration initiale** : paramètres que le composant lit une seule fois lorsqu'il se connecte
- **Sélecteurs CSS** : valeurs que vous souhaitez cibler avec des sélecteurs comme `[disabled]` ou `[variant="danger"]`
- **Hooks d'accessibilité** : `aria-label`, `role`, et d'autres attributs ARIA
- **Paramètres semblables à des chaînes qui changent rarement**

Lorsque vous enveloppez un web component tiers, consultez la documentation du composant pour confirmer quel nom correspond à une propriété et lequel à un attribut. L'utilisation de `PropertyDescriptor.attribute()` pour quelque chose que le composant expose uniquement en tant que propriété ne fonctionnera pas, et inversement. Le composant ignorera silencieusement la valeur.

### Typage des propriétés {#typing-properties}

Un descripteur est paramétré par le type Java de sa valeur. La syntaxe de déclaration complète est :

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

Le paramètre générique `<T>` déclare le type de valeur. Le type d'exécution de la valeur par défaut fixe également `T`, donc l'argument générique n'a que rarement besoin d'être spécifié explicitement. webforJ utilise `T` pour sérialiser et désérialiser les valeurs lors de la communication avec le client.

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

La sérialisation est automatique pour les types primitifs, leurs équivalents enveloppés, et `String`. Pour les types complexes, la valeur est sérialisée en JSON avant d'être affectée à la propriété côté client.

### Validation des valeurs {#validating-values}

Validez les valeurs dans le mutateur avant d'appeler `set()`. Le mutateur est le point d'application naturel car chaque mutation passe par lui.

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

Pour les références nullables, utilisez `Objects.requireNonNull()` afin que l'échec se manifeste à la frontière plutôt que plus tard dans le pipeline de rendu.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading ne peut pas être nul");
  set(heading, value);
  return this;
}
```

Évitez de valider dans `get()`. Les lectures doivent rester bon marché et cohérentes.

### Propriétés de type enum {#enum-style-properties}

La plupart des web components s'attendent à des valeurs de chaînes en minuscules ou en kebab-case pour des propriétés de type enum (`theme="primary"`, `expanse="xs"`). webforJ utilise Gson pour sérialiser les énumérations, mais la représentation par défaut de Gson est le nom constant en majuscules. Annotation chaque constante avec `@SerializedName` pour que la valeur sérialisée corresponde à ce que le web component attend.

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

Déclarez le descripteur avec le type enum et utilisez l'enum directement dans le mutateur et l'accesseur.

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

C'est le même modèle utilisé par les composants intégrés de webforJ pour `Theme`, `Expanse`, et des énumérations similaires. L'API publique Java reste de type sûr, et la valeur que le web component reçoit est la chaîne provenant de `@SerializedName`.

### Tester les propriétés {#testing-properties}

`PropertyDescriptorTester` valide que chaque `PropertyDescriptor` dans un composant est correctement câblé. Il scanne la classe pour des champs de descripteur, appelle chaque mutateur avec la valeur par défaut et compare le résultat à ce que renvoie l'accesseur. Le testeur attrape les erreurs d'intégration avant qu'elles n'atteignent une application en cours d'exécution : un mutateur qui écrit dans le mauvais descripteur, un accesseur qui lit une propriété différente, une valeur par défaut qui n'effectue pas un aller-retour, ou un accesseur manquant pour un descripteur déclaré.

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

Certains descripteurs ne suivent pas les conventions standard d'accesseur et de mutateur, ou reposent sur un état externe que le test ne peut pas satisfaire. Annotationnez-les avec `@PropertyExclude` pour les ignorer.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Noms d'accesseur et de mutateur personnalisés {#custom-getter-and-setter-names}

Si un descripteur utilise des noms d'accès non standards, déclarez-les avec `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

Le paramètre `target` accepte une classe lorsque les accesseurs se trouvent ailleurs que dans le composant lui-même.

Pour plus de détails sur la surface de test, voir [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Interfaces de préoccupation {#concern-interfaces}

Les interfaces de préoccupation donnent à une sous-classe `ElementComposite` des capacités sans écrire l'implémentation vous-même. Les interfaces transfèrent les appels à l'élément sous-jacent. Implémentez celles que le composant doit prendre en charge, paramétrées avec le type de sous-classe afin que le chaînage renvoie le composant :

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // Pas d'implémentation nécessaire.
}

MyBadge badge = new MyBadge()
    .setText("Nouveau")
    .addClassName("highlight")
    .setStyle("color", "var(--dwc-color-primary)");
```

Les trois interfaces ci-dessus couvrent tout ce dont `MyBadge` a besoin sans aucun corps de méthode dans la classe. `HasText` expose `setText()` et écrit dans le contenu texte de l'élément. `HasClassName` expose `addClassName()`, ce qui permet de cibler le badge depuis CSS. `HasStyle` expose `setStyle()` pour le style en ligne.

Pour l'ensemble complet d'interfaces disponibles et ce que chacune fournit, voir [Interfaces de préoccupation](./component-fundamentals#concern-interfaces) dans l'article Comprendre les composants. Si un transfert par défaut ne correspond pas à ce que l'élément enveloppé expose, remplacez la méthode dans la sous-classe.

## Événements {#events}

### Enregistrement d'événements {#event-registration}

Les web components déclenchent des événements DOM lorsque quelque chose se produit dans le navigateur. Pour réagir depuis Java, écoutez ces événements avec `addEventListener()`. L'ensemble des événements qu'un composant déclenche varie, donc vérifiez les propres documents du composant pour les noms et les charges utiles disponibles.

`ElementComposite` prend en charge le debounce, le throttle, le filtrage et les données d'événements personnalisées sur les écouteurs enregistrés.

Enregistrez des écouteurs d'événements en utilisant la méthode `addEventListener()` :

```java
// Exemple : Ajout d'un écouteur d'événement clic
addEventListener(ElementClickEvent.class, event -> {
  // Gérer l'événement de clic
});
```

:::info
`ElementComposite` n'accepte que des classes d'événements annotées avec `@EventName`, contrairement à `Element`, qui accepte tout nom d'événement de chaîne.
:::

### Classes d'événements intégrées {#built-in-event-classes}

`ElementClickEvent` est la seule classe d'événement intégrée livrée avec `ElementComposite`. Elle expose les événements de clic de souris sur l'élément sous-jacent avec des accesseurs typés pour les coordonnées (`getClientX()`, `getClientY()`), les informations sur les boutons (`getButton()`), et les touches de modification (`isCtrlKey()`, `isShiftKey()`, etc.).

Pour exposer le traitement des clics sur l'API publique d'une sous-classe, implémentez l'interface de préoccupation `HasElementClickListener<T>`. Elle fournit les méthodes par défaut `onClick()` et `addClickListener()` qui délèguent à la primitive protégée `addEventListener()`.

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

Pour tout autre événement que le web component sous-jacent déclenche, définissez une classe d'événement personnalisée. Voir [Classes d'événements personnalisées](#custom-event-classes).

### Charges utiles d'événements {#event-payloads}

Les événements transportent des données du client vers votre code Java. Accédez à ces données via `getData()` pour les données d'événements brutes ou utilisez des méthodes typées lorsque disponibles sur les classes d'événements intégrées. Voir le [guide des événements](../building-ui/events) pour plus d'informations sur la gestion efficace des charges utiles.

### Classes d'événements personnalisées {#custom-event-classes}

Définissez des classes d'événements personnalisées avec `@EventName` et `@EventOptions` pour capturer les données côté client dans un événement Java typé. Utilisez ceci lorsque le gestionnaire Java a besoin de valeurs du navigateur.

`@EventName` lie la classe Java à l'événement que le composant déclenche dans le navigateur, donc une classe annotée `@EventName("change")` se déclenche chaque fois que l'élément sous-jacent émet `change`. `@EventOptions` contrôle ce qui voyage avec cet événement. Chaque `@EventData` à l'intérieur associe une clé à une expression JavaScript évaluée par rapport à l'événement DOM. Le résultat est accessible dans la classe d'événements Java via `getData().get(key)`.

Le formulaire d'évaluation de produit ci-dessous utilise ce modèle avec [`wa-rating`](https://webawesome.com/docs/components/rating/). Le `ChangeEvent` personnalisé transporte la valeur d'évaluation sous forme de `double` typé, et l'écouteur l'utilise pour activer le bouton de soumission :

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Options d'événements {#event-options}

`ElementEventOptions` configure la charge utile de l'événement, le temps de debounce ou de throttle, les expressions de filtrage, et le code d'exécution préalable. Le fragment ci-dessous montre les options :

```java
ElementEventOptions options = new ElementEventOptions()
  // Collecter des données personnalisées du client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Exécuter JavaScript avant que l'événement ne se produise
  .setCode("component.classList.add('processing');")

  // Ne déclencher que si les conditions sont remplies
  .setFilter("component.value.length >= 2")

  // Retarder l'exécution jusqu'à ce que l'utilisateur arrête de taper (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Appliquer ces options lors de l'enregistrement d'un écouteur pour une classe d'événement personnalisée
// (voir la section Classes d'événements personnalisées ci-dessus pour savoir comment en définir une) :
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` expose uniquement la forme basée sur la classe `addEventListener(Class, listener, options)`. Utilisez-la avec une classe d'événement annotée avec `@EventName`. Pour s'enregistrer contre un nom d'événement de chaîne directement, appelez `getElement().addEventListener("input", listener, options)`.
:::

#### Contrôle des performances {#performance-control}

**Le debounce** retarde l'exécution jusqu'à ce que l'activité cesse :

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Attendre 300ms après le dernier événement
```

Phases de debounce disponibles :

- `LEADING` : se déclenche immédiatement, puis attend
- `TRAILING` : attendre un temps mort, puis se déclenche (par défaut)
- `BOTH` : se déclenche immédiatement et après un temps mort

**Le throttle** limite la fréquence d'exécution :

```java
options.setThrottle(100); // Se déclenche au maximum une fois toutes les 100ms
```

## Interaction avec des slots {#interacting-with-slots}

Les slots sont des espaces réservés à l'intérieur d'un web component que les utilisateurs remplissent avec du contenu. Le web component déclare ses slots dans son modèle avec `<slot>` ou `<slot name="...">`, et le wrapper expose des méthodes qui insèrent des composants Java dans ces slots.

Pour ajouter du contenu aux slots, étendez `ElementCompositeContainer` au lieu de `ElementComposite`. Le conteneur porte le même mécanisme de propriété et d'attribut, plus les méthodes nécessaires pour ajouter des enfants. Les enfants ajoutés via `add()` vont dans le slot par défaut. Les enfants ajoutés via `getElement().add(slotName, components)` vont dans le slot nommé.

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

La démo ci-dessous montre deux cartes de prix construites avec [`wa-card`](https://webawesome.com/docs/components/card/), peuplant les slots `header`, par défaut, et `footer` depuis Java :

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/WebAwesomeCardView.java']}
height='400px'
/>

### Inspection du contenu des slots {#inspecting-slot-contents}

L'élément sous-jacent `Element` (accessible via `getElement()`) fournit des méthodes pour lire ce qui est actuellement assigné aux slots :

- **`findComponentSlot()`** : recherche tous les slots pour un composant spécifique et renvoie le nom du slot contenant celui-ci, ou une chaîne vide si le composant n'est dans aucun slot.
- **`getComponentsInSlot()`** : renvoie la liste des composants assignés à un slot donné. Prend éventuellement un type de classe pour filtrer les résultats.
- **`getFirstComponentInSlot()`** : renvoie le premier composant assigné à un slot. Prend éventuellement un type de classe pour filtrer.
