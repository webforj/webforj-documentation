---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
_i18n_hash: d626a230fe04d316c48e3cae7e292599
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La classe `ElementComposite` encapsule un élément HTML personnalisé ou un [web component](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Elle lie votre classe Java à l'élément sous-jacent `Element` et vous permet de travailler avec les propriétés, attributs et événements de cet élément via Java. Utilisez-le lors de l'intégration de web components dans une application webforJ.

:::tip Quand utiliser `ElementComposite`
Utilisez `ElementComposite` lorsque vous encapsulez un web component de tiers que webforJ ne fournit pas déjà. Si un composant webforJ intégré couvre le cas d'utilisation (comme `TextField`, `ColorField`, `Button`, etc.), utilisez-le à la place. Pour un travail DOM ponctuel qui n'a pas besoin d'être réutilisé, la classe `Element` peut être utilisée directement sans wrapper.
:::

Ce guide démontre comment implémenter le [web component de temps relatif Shoelace](https://shoelace.style/components/relative-time) en utilisant la classe `ElementComposite`.

<ComponentDemo 
path='/webforj/relativetime' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Annotations de classe {#class-annotations}

Trois annotations apparaissent couramment au début d'un sous-classe `ElementComposite`: `@NodeName` déclare la balise HTML que le composant encapsule, et `@JavaScript` et `@StyleSheet` chargent les ressources côté client dont dépend le web component sous-jacent. `@NodeName` est requise et spécifique à `ElementComposite`. `@JavaScript` et `@StyleSheet` sont des annotations de ressources générales webforJ et fonctionnent sur n'importe quelle classe, y compris les vues, composants, ou la classe `App`.

### `@NodeName` {#nodename}

L'annotation `@NodeName` déclare la balise HTML que le composant encapsule. webforJ utilise ce nom lors de la création de l'élément sous-jacent dans le DOM.

```java
@NodeName("sl-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Le nom de la balise doit correspondre à l'élément personnalisé enregistré côté client. Sans cette annotation, le framework ne peut pas déterminer quel élément créer.

À l'intérieur d'une sous-classe, `getNodeName()` lit la balise déclarée, et `getElement()` retourne l'`Element` sous-jacent afin que vous puissiez appeler des méthodes de niveau DOM directement sur celui-ci.

### `@JavaScript` {#javascript}

L'annotation `@JavaScript` charge le script qui définit ou enregistre le web component sous-jacent. Placez-le sur la classe afin que le script ne se charge que lorsque le composant est utilisé.

```java
@NodeName("sl-relative-time")
@JavaScript("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/shoelace-autoloader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Plusieurs annotations `@JavaScript` sont autorisées, et webforJ déduplique les chargements automatiquement. Le même script ne se chargera pas deux fois si plusieurs composants en dépendent.

Voir [Importation de fichiers JavaScript](../managing-resources/importing-assets#importing-javascript-files) pour l'ensemble des options, y compris `top`, `attributes`, et le chronométrage de chargement.

### `@StyleSheet` {#stylesheet}

L'annotation `@StyleSheet` charge un fichier CSS dont le composant dépend. Elle est utile pour les composants de tiers qui expédient une feuille de style séparée, ou pour grouper des styles spécifiques au composant aux côtés de l'enveloppe.

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/themes/light.css")
```

Pour les ressources emballées localement, utilisez le préfixe `ws://` pour référencer des fichiers dans `resources/static`:

```java
@StyleSheet("ws://components/relative-time.css")
```

Voir [Importation de fichiers CSS](../managing-resources/importing-assets#importing-css-files) pour l'ensemble des options.

## Descripteurs de propriétés et d'attributs {#property-and-attribute-descriptors}

Les propriétés et attributs représentent l'état d'un web component, généralement en retenant des données ou une configuration. `ElementComposite` expose les deux via `PropertyDescriptor`.

Deux méthodes de fabrication sur `PropertyDescriptor` produisent le descripteur lui-même, une par cible de liaison :

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` se lie à une propriété JavaScript sur le nœud DOM. `PropertyDescriptor.attribute()` se lie à un attribut HTML. Le premier argument est le nom que le web component attend. Le second est une valeur par défaut, qui fixe également le type Java du descripteur.

Déclarez le descripteur comme un champ privé sur le composant, puis lisez et écrivez via `set(PropertyDescriptor<V> property, V value)` et `get(PropertyDescriptor<V> property)`.

:::info
Les propriétés sont un état interne du nœud DOM et ne se reflètent pas dans le balisage. Les attributs sont un balisage HTML, visibles pour les scripts et CSS externes.
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

Les appels ci-dessus utilisent `set()` directement pour montrer la forme primitive. En pratique, `set()` et `get()` sont des méthodes `protected` sur `ElementComposite`. Elles sont la couche primitive qui synchronise les valeurs Java avec l'élément sous-jacent, et non l'API publique que consomment les utilisateurs. Le modèle prévu consiste à garder le `PropertyDescriptor` privé et à écrire des méthodes publiques `setX()` et `getX()` qui délèguent aux primitives.

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

Un appel unique à `set(descriptor, value)` fait trois choses à la fois. Il pousse la valeur au client via `setProperty()` pour les propriétés, ou `setAttribute()` pour les attributs. Il stocke la valeur dans un cache local côté serveur, une carte par instance de composant. Et il enregistre le type d'exécution aux côtés de la valeur, pour que les appels `get()` ultérieurs sachent comment désérialiser.

Ce cache local est la raison pour laquelle `get()` peut être bon marché par défaut. `get(descriptor)` renvoie la valeur mise en cache depuis le stockage côté serveur sans appel réseau, car chaque `set()` garde le cache synchronisé avec le client. L'argument `boolean` optionnel permet de contrôler si le cache doit être contourné et de lire depuis le navigateur à la place.

```java
String cached = get(heading);            // lit depuis le cache côté serveur
String live = get(heading, true);        // force une lecture depuis le navigateur
```

Définissez `fromClient` sur vrai lorsque la valeur peut changer sur le client sans que le serveur en ait connaissance, comme une valeur `<input>` saisie. Pour les propriétés pilotées par le serveur, la valeur par défaut évite un aller-retour.

Le troisième argument optionnel est un `java.lang.reflect.Type` et contrôle comment le résultat est désérialisé. webforJ résout le type dans cet ordre : l'argument `Type` explicite s'il est passé, puis le type d'exécution enregistré par un précédent `set()` sur le même descripteur, puis `Object.class`. En pratique, le type enregistré par un précédent `set()` suffit, donc le troisième argument peut généralement être omis. Il est nécessaire lorsque la classe enregistrée perd des informations dont la désérialisation dépend, comme un type paramétré comme `List<String>` dont la classe d'exécution est simplement `ArrayList`.

La démo ci-dessous ajoute des propriétés pour le temps relatif en fonction de la documentation du web component et les expose via des accesseurs. Chaque ligne dans le fil d'activité utilise des valeurs différentes `format` et `numeric` pour montrer comment le même composant se rend sous des configurations variées.

<ComponentDemo 
path='/webforj/relativetimeproperties' 
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/resources/static/css/elementcomposite/activity-feed.css',
]}
height='450px'
/>

### Propriétés versus attributs {#properties-versus-attributes}

Bien que `PropertyDescriptor.property()` et `PropertyDescriptor.attribute()` semblent interchangeables, ils ciblent différentes parties de l'élément sous-jacent. Choisir le mauvais entraîne des valeurs qui échouent silencieusement à s'appliquer.

Les propriétés sont des propriétés d'objet JavaScript sur le nœud DOM. Elles peuvent contenir n'importe quel type, y compris des chaînes, des booléens, des nombres, des objets et des tableaux, et représentent l'état d'exécution actuel de l'élément. Définir une propriété est une simple assignation JavaScript directe.

Les attributs sont un balisage HTML. Ils vivent sur la balise d'ouverture de l'élément, sont toujours des chaînes, et représentent la configuration initiale de l'élément. Définir un attribut déclenche une mutation DOM et une conversion de chaîne.

Pour certains cas, les deux restent synchronisés. Pour d'autres, ils divergent. La `value` d'un `<input>` est l'exemple classique : l'attribut `value` est la valeur initiale, tandis que la propriété `value` est la valeur actuelle saisie par l'utilisateur. Lire l'attribut après que l'utilisateur a saisi renvoie le balisage d'origine, mais lire la propriété renvoie le contenu actuel du champ.

Utilisez **les propriétés** pour :

- **L'état d'exécution fréquemment changeant** : compteurs, sélections actuelles, valeurs saisies
- **Les types non-chaînes** : booléens, nombres, objets, tableaux
- **Mises à jour sensibles à la performance** : les propriétés évitent la conversion de chaîne requise pour les attributs

Utilisez **les attributs** pour :

- **La configuration initiale** : réglages que le composant lit une fois lorsqu'il se connecte
- **Sélecteurs CSS** : valeurs que vous souhaitez cibler avec des sélecteurs comme `[disabled]` ou `[variant="danger"]`
- **Points d'accès d'accessibilité** : `aria-label`, `role`, et d'autres attributs ARIA
- **Réglages de type chaîne qui changent rarement**

Lors de l'encapsulation d'un web component tiers, vérifiez la documentation du composant pour confirmer quel nom correspond à une propriété et quel nom à un attribut. Utiliser `PropertyDescriptor.attribute()` pour quelque chose que le composant expose uniquement en tant que propriété ne fonctionnera pas, et inversement. Le composant ignorera silencieusement la valeur.

### Typage des propriétés {#typing-properties}

Un descripteur est paramétré par le type Java de sa valeur. La syntaxe complète de déclaration est :

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

La sérialisation est automatique pour les types primitifs, leurs équivalents enveloppants et `String`. Pour les types complexes, la valeur est sérialisée au format JSON avant d'être assignée à la propriété sur le client.

### Validation des valeurs {#validating-values}

Validez les valeurs dans le setter avant d'appeler `set()`. Le setter est le point naturel d'application car chaque mutation y passe.

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

Pour les références pouvant être nulles, utilisez `Objects.requireNonNull()` afin que l'échec se manifeste à la frontière plutôt que plus tard dans le pipeline de rendu.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading ne peut pas être null");
  set(heading, value);
  return this;
}
```

Évitez de valider dans `get()`. Les lectures doivent rester bon marché et cohérentes.

### Propriétés de type Enum {#enum-style-properties}

La plupart des web components s'attendent à des valeurs de chaînes en minuscules ou en kebab-case pour des propriétés semblables à des énumérations (`theme="primary"`, `expanse="xs"`). webforJ utilise Gson pour sérialiser les énumérations, mais la représentation par défaut de Gson est le nom de la constante en majuscules. Annotez chaque constante avec `@SerializedName` afin que la valeur sérialisée corresponde à ce que le web component attend.

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

Déclarez le descripteur avec le type de l'énumération et utilisez l'énumération directement dans le setter et le getter.

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

C'est le même modèle que les composants intégrés de webforJ utilisent pour `Theme`, `Expanse`, et des énumérations similaires. L'API publique Java reste sûre du type, et la valeur que le web component reçoit est la chaîne de `@SerializedName`.

### Tester les propriétés {#testing-properties}

`PropertyDescriptorTester` valide que chaque `PropertyDescriptor` dans un composant est câblé correctement. Il scanne la classe pour les champs de descripteur, appelle chaque setter avec la valeur par défaut et compare le résultat à ce que le getter retourne. Le testeur attrape les erreurs d'intégration avant qu'elles n'atteignent une application en cours d'exécution : un setter qui écrit sur le mauvais descripteur, un getter qui lit une propriété différente, une valeur par défaut qui ne fait pas de round-trip, ou un accesseur manquant pour un descripteur déclaré.

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

#### Exclure des propriétés {#excluding-properties}

Certains descripteurs ne suivent pas les conventions standard des accesseurs, ou ils dépendent d'un état externe que le test ne peut pas satisfaire. Annotez-les avec `@PropertyExclude` pour les ignorer.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Noms d'accesseurs personnalisés {#custom-getter-and-setter-names}

Si un descripteur utilise des noms d'accesseurs non standards, déclarez-les avec `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

Le paramètre `target` accepte une classe lorsque les accesseurs résident ailleurs que dans le composant lui-même.

Pour plus de détails sur la surface de test, voir [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Interfaces de préoccupation {#concern-interfaces}

Les interfaces de préoccupation donnent à une sous-classe `ElementComposite` des capacités sans que vous ayez à écrire l'implémentation vous-même. Les interfaces transfèrent les appels à l'élément sous-jacent. Implémentez celles que le composant doit prendre en charge, paramétrées avec le type de sous-classe afin que le chaînage renvoie le composant :

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

Les trois interfaces ci-dessus couvrent tout ce dont MyBadge a besoin sans aucun corps de méthode dans la classe. `HasText` expose `setText()` et écrit dans le contenu textuel de l'élément. `HasClassName` expose `addClassName()`, ce qui permet au badge d'être ciblé à partir de CSS. `HasStyle` expose `setStyle()` pour le style inline.

Pour l'ensemble des interfaces disponibles et ce que chacune offre, voir [Interfaces de préoccupation](./component-fundamentals#concern-interfaces) dans l'article Comprendre les composants. Si un transfert par défaut ne correspond pas à ce que l'élément encapsulé expose, substituez la méthode dans la sous-classe.

## Événements {#events}

### Enregistrement d'événements {#event-registration}

Les web components envoient des événements DOM lorsque quelque chose se produit dans le navigateur. Pour réagir depuis Java, écoutez ces événements avec `addEventListener()`. L'ensemble des événements qu'un composant envoie varie, alors vérifiez la documentation du composant pour les noms et charges disponibles.

`ElementComposite` prend en charge le débouncage, le throttling, le filtrage, et les données d'événements personnalisées sur les écouteurs enregistrés.

Enregistrez les écouteurs d'événements en utilisant la méthode `addEventListener()` :

```java
// Exemple : Ajouter un écouteur d'événements de clic
addEventListener(ElementClickEvent.class, event -> {
  // Gérer l'événement de clic
});
```

:::info
`ElementComposite` n'accepte que les classes d'événements annotées avec `@EventName`, contrairement à `Element`, qui accepte n'importe quel nom d'événement de chaîne.
:::

### Classes d'événements intégrées {#built-in-event-classes}

`ElementClickEvent` est la seule classe d'événements intégrée que `ElementComposite` propose. Elle surface les événements de clic souris sur l'élément sous-jacent avec des accesseurs typés pour les coordonnées (`getClientX()`, `getClientY()`), les informations des boutons (`getButton()`), et les touches de modification (`isCtrlKey()`, `isShiftKey()`, etc.).

Pour exposer la gestion des clics sur l'API publique d'une sous-classe, implémentez l'interface de préoccupation `HasElementClickListener<T>`. Elle fournit des méthodes par défaut `onClick()` et `addClickListener()` qui délèguent à la primitive protégée `addEventListener()`.

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasElementClickListener<MyBadge> {
  // onClick() et addClickListener() sont désormais disponibles sur MyBadge
}

new MyBadge().onClick(event -> {
  if (event.isShiftKey()) {
    // ...
  }
});
```

Pour tout autre événement que le web component sous-jacent envoie, définissez une classe d'événement personnalisée. Voir [Classes d'événements personnalisées](#custom-event-classes).

### Charges d'événements {#event-payloads}

Les événements transportent des données du client à votre code Java. Accédez à ces données via `getData()` pour des données d'événements brutes ou utilisez des méthodes typées lorsque disponibles sur les classes d'événements intégrées. Voir le [guide des événements](../building-ui/events) pour plus d'informations sur la gestion efficace des charges.

### Classes d'événements personnalisées {#custom-event-classes}

Définissez des classes d'événements personnalisées avec `@EventName` et `@EventOptions` pour capturer les données côté client dans un événement Java typé. Utilisez cela lorsque le gestionnaire Java a besoin de valeurs provenant du navigateur.

`@EventName` lie la classe Java à l'événement que le composant envoie dans le navigateur, donc une classe annotée `@EventName("sl-change")` se déclenche chaque fois que l'élément sous-jacent émet `sl-change`. `@EventOptions` contrôle ce qui voyage avec cet événement. Chaque `@EventData` à l'intérieur d'elle associe une clé à une expression JavaScript évaluée par rapport à l'événement DOM. Le résultat est disponible dans la classe d'événements Java via `getData().get(key)`.

Le formulaire d'évaluation du produit ci-dessous utilise ce modèle avec [`sl-rating`](https://shoelace.style/components/rating). Le `ChangeEvent` personnalisé transporte la valeur de l'évaluation sous forme de `double` typé, et le listener l'utilise pour activer le bouton de soumission :

<ComponentDemo 
path='/webforj/rating' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Options d'événements {#event-options}

`ElementEventOptions` configure la charge de l'événement, le chronométrage de débounce ou de throttle, les expressions de filtre, et le code d'exécution préalable. Le snippet ci-dessous montre les options :

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
  
  // Retarder l'exécution jusqu'à ce que l'utilisateur arrête de taper (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Appliquez ces options lors de l'enregistrement d'un listener pour une classe d'événement personnalisée
// (voir la section Classes d'événements personnalisées ci-dessus pour savoir comment en définir une) :
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` expose uniquement la forme basée sur les classes `addEventListener(Class, listener, options)`. Utilisez-la avec une classe d'événement annotée `@EventName`. Pour s'inscrire directement contre un nom d'événement de chaîne, utilisez `getElement().addEventListener("input", listener, options)`.
:::

#### Contrôle de performance {#performance-control}

**Le débouncage** retarde l'exécution jusqu'à ce que l'activité s'arrête :

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Attendre 300ms après le dernier événement
```

Phases de débouncage disponibles :

- `LEADING`: Déclencher immédiatement, puis attendre
- `TRAILING`: Attendre une période de silence, puis déclencher (par défaut)
- `BOTH`: Déclencher immédiatement et après la période de silence

**Le throttling** limite la fréquence d'exécution :

```java
options.setThrottle(100); // Déclencher au maximum une fois toutes les 100ms
```

## Interagir avec les slots {#interacting-with-slots}

Les slots sont des espaces réservés à l'intérieur d'un web component que les utilisateurs remplissent avec du contenu. Le web component déclare ses slots dans son modèle avec `<slot>` ou `<slot name="...">`, et l'enveloppe expose des méthodes qui placent des composants Java dans ces slots.

Pour ajouter du contenu aux slots, étendez `ElementCompositeContainer` au lieu de `ElementComposite`. Le conteneur porte les mêmes mécanismes de propriété et d'attribut, plus les méthodes nécessaires pour ajouter des enfants. Les enfants ajoutés via `add()` vont dans le slot par défaut. Les enfants ajoutés via `getElement().add(slotName, components)` vont dans le slot nommé.

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

La démo ci-dessous montre deux cartes de prix construites avec [`sl-card`](https://shoelace.style/components/card), peuplant les slots `header`, par défaut, et `footer` à partir de Java :

<ComponentDemo 
path='/webforj/card' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### Inspection du contenu des slots {#inspecting-slot-contents}

L'`Element` sous-jacent (accédé via `getElement()`) fournit des méthodes pour lire ce qui est actuellement assigné aux slots :

- **`findComponentSlot()`** : recherche tous les slots pour un composant spécifique et renvoie le nom du slot le contenant, ou une chaîne vide si le composant n'est dans aucun slot.
- **`getComponentsInSlot()`** : renvoie la liste des composants assignés à un slot donné. Prend en option un type de classe pour filtrer les résultats.
- **`getFirstComponentInSlot()`** : renvoie le premier composant assigné à un slot. Prend en option un type de classe pour filtrer.
