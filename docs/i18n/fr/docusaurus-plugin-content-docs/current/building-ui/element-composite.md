---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
_i18n_hash: b8099816ab51d246d3a69c2ca8bd9108
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La classe `ElementComposite` enveloppe un élément HTML personnalisé ou un [web component](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Elle lie votre classe Java à l'élément `Element` sous-jacent et vous permet de travailler avec les propriétés, attributs et événements de cet élément via Java. Utilisez-la lorsque vous intégrez des web components dans une application webforJ.

:::tip Quand utiliser `ElementComposite`
Utilisez `ElementComposite` lorsque vous enveloppez un web component tiers que webforJ ne fournit pas déjà. Si un composant webforJ intégré couvre le cas d'utilisation ( `TextField`, `ColorField`, `Button`, etc.), utilisez-le à la place. Pour un travail DOM ponctuel qui n'a pas besoin d'être réutilisé, la classe `Element` peut être utilisée directement sans enveloppe.
:::

Ce guide démontre comment implémenter le [web component de temps relatif Shoelace](https://shoelace.style/components/relative-time) en utilisant la classe `ElementComposite`.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Annotations de classe {#class-annotations}

Trois annotations apparaissent couramment en haut d'un sous-classe `ElementComposite` : `@NodeName` déclare la balise HTML que le composant enveloppe, et `@JavaScript` et `@StyleSheet` chargent les ressources côté client dont dépend le web component sous-jacent. `@NodeName` est obligatoire et spécifique à `ElementComposite`. `@JavaScript` et `@StyleSheet` sont des annotations de ressources webforJ générales et fonctionnent sur n'importe quelle classe, y compris les vues, les composants ou la classe `App`.

### `@NodeName` {#nodename}

L'annotation `@NodeName` déclare la balise HTML que le composant enveloppe. webforJ utilise ce nom lors de la création de l'élément sous-jacent dans le DOM.

```java
@NodeName("sl-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Le nom de la balise doit correspondre à l'élément personnalisé enregistré côté client. Sans cette annotation, le framework ne peut pas déterminer quel élément créer.

À l'intérieur d'une sous-classe, `getNodeName()` lit le tag déclaré, et `getElement()` retourne l'élément `Element` sous-jacent afin que vous puissiez appeler des méthodes de niveau DOM directement sur lui.

### `@JavaScript` {#javascript}

L'annotation `@JavaScript` charge le script qui définit ou enregistre le web component sous-jacent. Placez-la sur la classe pour que le script se charge uniquement lorsque le composant est utilisé.

```java
@NodeName("sl-relative-time")
@JavaScript("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/shoelace-autoloader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Plusieurs annotations `@JavaScript` sont autorisées, et webforJ déduplique automatiquement les chargements. Le même script ne sera pas chargé deux fois si plusieurs composants en dépendent.

Voir [Importation de fichiers JavaScript](../managing-resources/importing-assets#importing-javascript-files) pour l'ensemble complet d'options, y compris `top`, `attributes`, et le timing de chargement.

### `@StyleSheet` {#stylesheet}

L'annotation `@StyleSheet` charge un fichier CSS dont le composant dépend. Elle est utile pour les composants tiers qui expédient une feuille de style séparée, ou pour regrouper le style spécifique du composant avec l'enveloppe.

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/themes/light.css")
```

Pour les ressources regroupées localement, utilisez le préfixe `ws://` pour référencer les fichiers dans `resources/static` :

```java
@StyleSheet("ws://components/relative-time.css")
```

Voir [Importation de fichiers CSS](../managing-resources/importing-assets#importing-css-files) pour l'ensemble complet des options.

## Descripteurs de propriété et d'attribut {#property-and-attribute-descriptors}

Les propriétés et attributs représentent l'état d'un web component, généralement en contenant des données ou des configurations. `ElementComposite` expose les deux via `PropertyDescriptor`.

Deux méthodes de fabrique sur `PropertyDescriptor` produisent le descripteur lui-même, une par cible de liaison :

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` se lie à une propriété JavaScript sur le nœud DOM. `PropertyDescriptor.attribute()` se lie à un attribut HTML. Le premier argument est le nom que le web component attend. Le second est une valeur par défaut, qui fixe également le type Java du descripteur.

Déclarez le descripteur comme un champ privé sur le composant, puis lisez et écrivez à travers lui avec `set(PropertyDescriptor<V> property, V value)` et `get(PropertyDescriptor<V> property)`.

:::info
Les propriétés sont un état interne sur le nœud DOM et ne se reflètent pas dans le balisage. Les attributs sont un balisage HTML, visibles pour les scripts externes et CSS.
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

Un seul appel à `set(descriptor, value)` fait trois choses à la fois. Il pousse la valeur vers le client via `setProperty()` pour les propriétés, ou `setAttribute()` pour les attributs. Il stocke la valeur dans un cache local côté serveur, une carte par instance de composant. Et il enregistre le type d'exécution aux côtés de la valeur, afin que les futurs appels `get()` sachent comment désérialiser.

Ce cache local est la raison pour laquelle `get()` peut être bon marché par défaut. `get(descriptor)` retourne la valeur mise en cache du magasin côté serveur sans appel réseau, car chaque `set()` garde le cache synchronisé avec le client. L'argument `boolean` optionnel contrôle s'il faut contourner le cache et lire depuis le navigateur à la place.

```java
String cached = get(heading);            // lit depuis le cache côté serveur
String live = get(heading, true);        // force une lecture depuis le navigateur
```

Définissez `fromClient` sur true lorsque la valeur peut changer côté client sans la connaissance du serveur, comme une valeur saisie de `<input>`. Pour les propriétés pilotées par le serveur, la valeur par défaut évite un tour de réseau.

L'argument optionnel `third` est un `java.lang.reflect.Type` et contrôle comment le résultat est désérialisé. webforJ résout le type dans cet ordre : l'argument `Type` explicite s'il est passé, puis le type d'exécution enregistré par un précédent `set()` sur le même descripteur, puis `Object.class`. En pratique, le type enregistré par un précédent `set()` suffit, donc le troisième argument peut généralement être omis. Il est nécessaire lorsque la classe enregistrée perd des informations dont le désérialiseur dépend, comme un type paramétré comme `List<String>` dont la classe d'exécution est juste `ArrayList`.

La démo ci-dessous ajoute des propriétés pour le temps relatif en fonction de la documentation du web component et les expose via des accesseurs et des mutateurs. Chaque ligne dans le fil d'activité utilise différentes valeurs de `format` et `numeric` pour montrer comment le même composant se rend sous des configurations variées.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/css/elementcomposite/activity-feed.css',
]}
height='450px'
/>

### Propriétés versus attributs {#properties-versus-attributes}

Bien que `PropertyDescriptor.property()` et `PropertyDescriptor.attribute()` semblent interchangeables, elles ciblent différentes parties de l'élément sous-jacent. Choisir la mauvaise entraîne des valeurs qui échouent silencieusement à s'appliquer.

Les propriétés sont des propriétés d'objet JavaScript sur le nœud DOM. Elles peuvent contenir n'importe quel type, y compris des chaînes, des booléens, des nombres, des objets et des tableaux, et elles représentent l'état d'exécution actuel de l'élément. Définir une propriété est une affectation JavaScript directe.

Les attributs sont un balisage HTML. Ils vivent sur la balise d'ouverture de l'élément, sont toujours des chaînes, et représentent la configuration initiale de l'élément. Définir un attribut déclenche une mutation DOM et une conversion de chaîne.

Pour certains cas, les deux restent synchronisés. Pour d'autres, ils divergent. La `value` d'un `<input>` est l'exemple classique : l'attribut `value` est la valeur initiale, tandis que la propriété `value` est la valeur actuelle que l'utilisateur a saisie. Lire l'attribut après que l'utilisateur a saisi la valeur renvoie le balisage d'origine, mais lire la propriété renvoie le contenu actuel du champ.

Utilisez **les propriétés** pour :

- **État d'exécution changeant fréquemment** : compteurs, sélections actuelles, valeurs saisies
- **Types non-chaînes** : booléens, nombres, objets, tableaux
- **Mises à jour sensibles à la performance** : les propriétés évitent la conversion en chaîne requise pour les attributs

Utilisez **les attributs** pour :

- **Configuration initiale** : paramètres que le composant lit une seule fois lorsqu'il se connecte
- **Sélecteurs CSS** : valeurs que vous souhaitez cibler avec des sélecteurs comme `[disabled]` ou `[variant="danger"]`
- **Hooks d'accessibilité** : `aria-label`, `role`, et d'autres attributs ARIA
- **Paramètres de type chaîne qui changent rarement**

Lors de l'enveloppement d'un web component tiers, consultez la documentation du composant pour confirmer quel nom correspond à une propriété et lequel à un attribut. Utiliser `PropertyDescriptor.attribute()` pour quelque chose que le composant expose uniquement comme propriété ne fonctionnera pas, et il en va de même à l'inverse. Le composant ignorera silencieusement la valeur.

### Typage des propriétés {#typing-properties}

Un descripteur est paramétré par le type Java de sa valeur. La syntaxe de déclaration complète est :

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

Le paramètre générique `<T>` déclare le type de la valeur. Le type d'exécution de la valeur par défaut fixe également `T`, donc l'argument générique n'a que rarement besoin d'être spécifié explicitement. webforJ utilise `T` pour sérialiser et désérialiser les valeurs lors de la communication avec le client.

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

La sérialisation est automatique pour les types primitifs, leurs équivalents encapsulés, et `String`. Pour les types complexes, la valeur est sérialisée en JSON avant d'être assignée à la propriété sur le client.

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

Pour les références pouvant être nulles, utilisez `Objects.requireNonNull()` afin que l'échec survienne à la frontière plutôt que plus tard dans le pipeline de rendu.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading ne peut pas être null");
  set(heading, value);
  return this;
}
```

Évitez de valider dans `get()`. Les lectures doivent rester bon marché et cohérentes.

### Propriétés de style enum {#enum-style-properties}

La plupart des web components s'attendent à ce que des valeurs de chaînes en minuscules ou en kebab-case soient fournies pour des propriétés de type enum (`theme="primary"`, `expanse="xs"`). webforJ utilise Gson pour sérialiser les enums, mais la représentation par défaut de Gson est le nom de la constante en majuscules. Annotez chaque constante avec `@SerializedName` afin que la valeur sérialisée corresponde à ce que le web component attend.

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

Il s'agit du même modèle que les composants intégrés de webforJ utilisent pour `Theme`, `Expanse`, et des enums similaires. L'API publique Java reste sûre pour le type, et la valeur que le web component reçoit est la chaîne issue de `@SerializedName`.

### Test des propriétés {#testing-properties}

`PropertyDescriptorTester` valide que chaque `PropertyDescriptor` dans un composant est correctement câblé. Il scanne la classe à la recherche de champs de descripteur, appelle chaque mutateur avec la valeur par défaut et compare le résultat à ce que retourne l'accesseur. Le testeur capture les erreurs d'intégration avant qu'elles n'atteignent une application en cours d'exécution : un mutateur qui écrit dans le mauvais descripteur, un accesseur qui lit une propriété différente, une valeur par défaut qui ne tourne pas, ou un accesseur manquant pour un descripteur déclaré.

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

Certains descripteurs ne suivent pas les conventions standard des accesseurs, ou s'appuient sur un état externe que le test ne peut pas satisfaire. Annotez-les avec `@PropertyExclude` pour les ignorer.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Noms d'accesseur et de mutateur personnalisés {#custom-getter-and-setter-names}

Si un descripteur utilise des noms d'accesseurs non standards, déclarez-les avec `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

Le paramètre `target` accepte une classe lorsque les accesseurs se trouvent ailleurs que dans le composant lui-même.

Pour plus de détails sur la surface de test, voir [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Interfaces de préoccupation {#concern-interfaces}

Les interfaces de préoccupation confèrent à un sous-composant d'`ElementComposite` des capacités sans avoir à écrire l'implémentation vous-même. Les interfaces transmettent les appels à l'élément sous-jacent. Implémentez celles que le composant doit prendre en charge, paramétrées avec le type de sous-classe afin que le chaînage retourne le composant :

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // Aucune implémentation nécessaire.
}

MyBadge badge = new MyBadge()
    .setText("Nouveau")
    .addClassName("highlight")
    .setStyle("color", "var(--dwc-color-primary)");
```

Les trois interfaces ci-dessus couvrent tout ce dont `MyBadge` a besoin sans aucune méthode dans la classe. `HasText` expose `setText()` et écrit dans le contenu textuel de l'élément. `HasClassName` expose `addClassName()`, ce qui permet de cibler le badge depuis CSS. `HasStyle` expose `setStyle()` pour le style en ligne.

Pour l'ensemble complet des interfaces disponibles et ce que chacune fournit, voir [Interfaces de préoccupation](./component-fundamentals#concern-interfaces) dans l'article Comprendre les composants. Si un transfert par défaut ne correspond pas à ce que l'élément enveloppé expose, vous pouvez remplacer la méthode dans la sous-classe.

## Événements {#events}

### Enregistrement des événements {#event-registration}

Les web components déclenchent des événements DOM lorsque quelque chose se produit dans le navigateur. Pour réagir depuis Java, écoutez ces événements avec `addEventListener()`. L'ensemble des événements qu'un composant déclenche varie, donc consultez la documentation du composant pour les noms et charges utiles disponibles.

`ElementComposite` prend en charge la mise en veille, le filtrage et les données d'événement personnalisées sur les écouteurs enregistrés.

Enregistrez les écouteurs d'événements à l'aide de la méthode `addEventListener()` :

```java
// Exemple : Ajouter un écouteur d'événement de clic
addEventListener(ElementClickEvent.class, event -> {
  // Gérez l'événement de clic
});
```

:::info
`ElementComposite` n'accepte que des classes d'événements annotées avec `@EventName`, contrairement à `Element`, qui accepte n'importe quel nom d'événement sous forme de chaîne.
:::

### Classes d'événements intégrées {#built-in-event-classes}

`ElementClickEvent` est la seule classe d'événements intégrée avec laquelle `ElementComposite` est fourni. Elle révèle les événements de clic de souris sur l'élément sous-jacent avec des accesseurs typés pour les coordonnées (`getClientX()`, `getClientY()`), des informations sur le bouton (`getButton()`) et des touches modificateurs (`isCtrlKey()`, `isShiftKey()`, etc.).

Pour exposer la gestion des clics sur l'API publique d'une sous-classe, implémentez l'interface de préoccupation `HasElementClickListener<T>`. Elle fournit des méthodes `onClick()` et `addClickListener()` par défaut qui délèguent au primitif `addEventListener()` protégé.

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

### Charges utiles des événements {#event-payloads}

Les événements transportent des données du client vers votre code Java. Accédez à ces données via `getData()` pour les données d'événements brutes ou utilisez les méthodes typées lorsque disponibles sur les classes d'événements intégrées. Voir le [guide des événements](../building-ui/events) pour plus d'informations sur la gestion des charges utiles.

### Classes d'événements personnalisées {#custom-event-classes}

Définissez des classes d'événements personnalisées avec `@EventName` et `@EventOptions` pour capturer des données côté client dans un événement Java typé. Utilisez cela lorsque le gestionnaire Java a besoin de valeurs du navigateur.

`@EventName` lie la classe Java à l'événement que le composant déclenche dans le navigateur, donc une classe annotée `@EventName("sl-change")` se déclenche chaque fois que l'élément sous-jacent émet `sl-change`. `@EventOptions` contrôle ce qui voyage avec cet événement. Chaque `@EventData` à l'intérieur de celui-ci associe une clé à une expression JavaScript évaluée par rapport à l'événement DOM. Le résultat est disponible dans la classe d'événement Java via `getData().get(key)`.

Le formulaire d'évaluation de produit ci-dessous utilise ce modèle avec [`sl-rating`](https://shoelace.style/components/rating). Le `ChangeEvent` personnalisé transporte la valeur d'évaluation sous forme de `double` typé, et l'auditeur l'utilise pour activer le bouton de soumission :

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Options d'événements {#event-options}

`ElementEventOptions` configure la charge utile d'événements, le temps de mise en veille ou de restriction, les expressions de filtrage, et le code d'exécution préalable. Le code ci-dessous montre les options :

```java
ElementEventOptions options = new ElementEventOptions()
  // Collecter des données personnalisées du client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Exécuter du JavaScript avant que l'événement ne se déclenche
  .setCode("component.classList.add('processing');")

  // Ne se déclencher que si les conditions sont remplies
  .setFilter("component.value.length >= 2")

  // Retarder l'exécution jusqu'à ce que l'utilisateur cesse de taper (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Appliquer ces options lors de l'enregistrement d'un auditeur pour une classe d'événement personnalisée
// (voir la section Classes d'événements personnalisées ci-dessus pour savoir comment en définir une) :
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` expose uniquement la forme basée sur la classe `addEventListener(Class, listener, options)`. Utilisez-la avec une classe d'événement annotée avec `@EventName`. Pour vous enregistrer directement contre un nom d'événement sous forme de chaîne, appelez `getElement().addEventListener("input", listener, options)`.
:::

#### Contrôle de performance {#performance-control}

**La mise en veille** retarde l'exécution jusqu'à ce que l'activité cesse :

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Attendre 300ms après le dernier événement
```

Phases de mise en veille disponibles :

- `LEADING` : Déclencher immédiatement, puis attendre
- `TRAILING` : Attendre une période de calme, puis déclencher (par défaut)
- `BOTH` : Déclencher immédiatement et après la période de calme

**La restriction** limite la fréquence d'exécution :

```java
options.setThrottle(100); // Déclencher au plus une fois par 100ms
```

## Interagir avec les slots {#interacting-with-slots}

Les slots sont des espaces réservés à l'intérieur d'un web component que les utilisateurs remplissent avec du contenu. Le web component déclare ses slots dans son modèle avec `<slot>` ou `<slot name="...">`, et l'enveloppe expose des méthodes qui placent des composants Java dans ces slots.

Pour ajouter du contenu aux slots, étendez `ElementCompositeContainer` au lieu de `ElementComposite`. Le conteneur emporte la même machinerie de propriétés et d'attributs plus les méthodes nécessaires pour ajouter des enfants. Les enfants ajoutés via `add()` vont dans le slot par défaut. Les enfants ajoutés via `getElement().add(slotName, components)` vont dans le slot nommé.

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

La démo ci-dessous montre deux cartes de prix construites avec [`sl-card`](https://shoelace.style/components/card), remplissant les slots `header`, par défaut et `footer` depuis Java :

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### Inspection du contenu des slots {#inspecting-slot-contents}

L'élément sous-jacent `Element` (accessé via `getElement()`) fournit des méthodes pour lire ce qui est actuellement assigné aux slots :

- **`findComponentSlot()`** : recherche tous les slots pour un composant spécifique et retourne le nom du slot contenant celui-ci, ou une chaîne vide si le composant n'est dans aucun slot.
- **`getComponentsInSlot()`** : retourne la liste des composants assignés à un slot donné. Prend optionnellement un type de classe pour filtrer les résultats.
- **`getFirstComponentInSlot()`** : retourne le premier composant assigné à un slot. Prend optionnellement un type de classe pour filtrer.
