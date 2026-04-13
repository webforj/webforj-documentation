---
sidebar_position: 3
title: Using Components
sidebar_class_name: new-content
_i18n_hash: 3ffe2e3b31ea278e434f7319f8019637
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Les composants sont les briques fondamentales des applications webforJ. Que vous utilisiez des composants intégrés comme `Button` et `TextField`, ou que vous travailliez avec des composants personnalisés fournis par votre équipe, la façon dont vous interagissez avec eux suit le même modèle cohérent : vous configurez des propriétés, gérez l'état et composez des composants dans des mises en page.

Ce guide se concentre sur ces opérations quotidiennes : non pas sur les aspects internes de fonctionnement des composants, mais sur la manière de les utiliser efficacement en pratique.

## Propriétés des composants {#component-properties}

Chaque composant expose des propriétés qui contrôlent son contenu, son apparence et son comportement. La plupart d'entre elles possèdent des méthodes Java typées dédiées (`setText()`, `setTheme()`, `setExpanse()`, etc.), qui est la principale méthode que vous utiliserez pour configurer les composants dans webforJ. Les sections ci-dessous couvrent les propriétés et les méthodes qui s'appliquent largement à tous les types de composants.

### Contenu textuel {#text-content}

La méthode `setText()` définit le texte visible d'un composant, tel que le légende d'un `Button` ou le contenu d'un `Label`. Pour les composants d'entrée comme `TextField`, utilisez `setValue()` à la place pour définir la valeur actuelle du champ.

```java
Button button = new Button();
button.setText("Cliquez Moi");

Label label = new Label();
label.setText("Statut : prêt");

TextField field = new TextField();
field.setValue("Valeur initiale");
```

Certains composants prennent également en charge `setHtml()` pour les cas où vous avez besoin d'éléments HTML en ligne dans le contenu :

```java
Div container = new Div();
container.setHtml("<strong>Texte en gras</strong> et <em>texte en italique</em>");
```

### Attributs HTML {#html-attributes}

La plupart des configurations dans webforJ se font par le biais de méthodes Java typées plutôt qu'à l'aide d'attributs HTML bruts. Cependant, `setAttribute()` est utile pour passer des attributs d'accessibilité qui n'ont pas d'API dédiée :

```java
Button button = new Button("Soumettre");
button.setAttribute("aria-label", "Soumettre le formulaire");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Vérifiez la prise en charge des composants
Tous les composants ne prennent pas en charge des attributs arbitraires. Cela dépend de l'implémentation sous-jacente du composant.
:::

### Identifiants de composants {#component-ids}

Vous pouvez assigner un identifiant à l'élément HTML d'un composant en utilisant `setAttribute()` :

```java
Button submitButton = new Button("Soumettre");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("Email");
emailField.setAttribute("id", "email-input");
```

Les identifiants DOM sont couramment utilisés pour les sélecteurs de test et le ciblage CSS dans vos feuilles de style.

:::tip Préférez les classes pour le ciblage de plusieurs composants
Contrairement aux classes CSS, les identifiants doivent être uniques dans votre application. Si vous devez cibler plusieurs composants, utilisez `addClassName()` à la place.
:::

:::info Identifiants gérés par le framework
webforJ attribue également des identifiants automatiques aux composants en interne. L'identifiant côté serveur (accessible via `getComponentId()`) est utilisé pour le suivi du framework, tandis que l'identifiant côté client (accessible via `getClientComponentId()`) est utilisé pour la communication client-serveur. Ceux-ci sont distincts de l'attribut `id` DOM que vous définissez avec `setAttribute()`.
:::

### Style {#styling}

Trois méthodes couvrent la plupart des besoins de style : `setStyle()` pour des valeurs de propriété CSS individuelles, et `addClassName()` et `removeClassName()` pour appliquer ou retirer des classes CSS définies dans vos feuilles de style. 
Utilisez `setStyle()` pour des ajustements mineurs ou ponctuels, et utilisez des classes CSS pour appliquer un style plus large ou réutilisable.

```java
Div container = new Div();
container.setStyle("padding", "20px");

if (isHighPriority) {
    container.setStyle("border-left", "4px solid red");
}

Button button = new Button("Basculer");
button.addClassName("primaire", "grand");

if (isLoading) {
    button.addClassName("chargement");
}
```

:::note Approche héritée
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) est une approche héritée et n'est généralement pas recommandée pour les nouveaux projets. Dans la plupart des cas, gardez vos styles dans des fichiers CSS séparés.
:::

## État du composant {#component-state}

Au-delà du contenu et de l'apparence, les composants ont des propriétés d'état qui déterminent s'ils sont visibles et s'ils réagissent à l'interaction de l'utilisateur. Les deux plus couramment utilisés sont `setVisible()` et `setEnabled()`.

`setVisible()` contrôle si le composant est rendu dans l'interface utilisateur. `setEnabled()` contrôle s'il accepte les entrées ou les interactions tout en restant visible. Dans la plupart des cas, désactiver est préférable à cacher : un bouton désactivé communique toujours qu'une action existe mais n'est pas encore disponible, ce qui est moins désorientant que de le voir apparaître et disparaître.

```java
// Révéler un champ supplémentaire lorsque une case à cocher est cochée
TextField advancedField = new TextField("Paramètre avancé");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Afficher les paramètres avancés");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Activer un bouton uniquement lorsque le champ requis a une valeur
Button submitButton = new Button("Soumettre");
submitButton.setEnabled(false);

TextField nameField = new TextField("Nom");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

Le formulaire de connexion suivant démontre `setEnabled()` en pratique. Le bouton de connexion reste désactivé jusqu'à ce que les deux champs contiennent du contenu, ce qui indique clairement à l'utilisateur qu'une entrée est requise avant de continuer :

<ComponentDemo 
path='/webforj/conditionalstate?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/conditionalstate.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java'
height='400px'
/>

## Travailler avec des conteneurs {#working-with-containers}

Dans webforJ, la mise en page est gérée par des conteneurs, qui sont des composants qui contiennent d'autres composants et contrôlent comment ils sont disposés. Vous ne positionnez pas manuellement les composants enfants ; au lieu de cela, vous les ajoutez à un conteneur et configurez les propriétés de mise en page de ce conteneur.

### Ajout de composants {#adding-components}

Tous les conteneurs fournissent une méthode `add()`. Vous pouvez passer des composants un par un ou tous en même temps :

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Cliquez Moi"));

TextField nameField = new TextField("Nom");
TextField emailField = new TextField("Email");
Button submitButton = new Button("Soumettre");

container.add(nameField, emailField, submitButton);
```

### Options de mise en page {#layout-options}

`FlexLayout` est le conteneur de mise en page principal dans webforJ et couvre la majorité des cas d'utilisation : lignes, colonnes, alignement, espacement et enveloppement. Pour des arrangements plus complexes comme CSS Grid ou un positionnement personnalisé, vous pouvez appliquer du CSS directement via `setStyle()` ou `addClassName()` sur n'importe quel composant conteneur. Consultez la documentation [FlexLayout](/docs/components/flex-layout) pour l'ensemble des options de mise en page.

### Affichage et masquage des sections {#showing-hiding-sections}

Une utilisation courante de `setVisible()` dans les conteneurs consiste à révéler une interface utilisateur supplémentaire uniquement lorsqu'elle est pertinente. Cela maintient l'interface concentrée et réduit l'encombrement visuel. Au lieu de naviguer vers une nouvelle vue, vous pouvez afficher une section de la mise en page actuelle en réponse directe à l'entrée de l'utilisateur.

Le panneau de configuration suivant illustre cela : les préférences de notification de base sont toujours visibles, et une section d'options avancées n'apparaît que lorsque l'utilisateur le demande. Le bouton de sauvegarde s'active dès qu'un paramètre est modifié :

<ComponentDemo 
path='/webforj/progressivedisclosure?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/progressivedisclosure.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java'
height='450px'
/>

### Gestion des conteneurs {#container-management}

Utilisez `remove()` et `removeAll()` pour retirer des composants d'un conteneur au moment de l'exécution :

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Temporaire");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Ceci est utile lorsque vous devez remplacer complètement du contenu, comme échanger un indicateur de chargement contre des données chargées.

## Validation des formulaires {#form-validation}

Coordonner plusieurs composants pour bloquer une action de soumission est l'un des modèles les plus courants dans les interfaces webforJ. L'idée de base est simple : chaque champ de saisie enregistre un écouteur, et chaque fois qu'une valeur change, le formulaire réévalue si tous les critères sont satisfaits et met à jour le bouton de soumission en conséquence.

Ceci est préférable à l'affichage des erreurs de validation uniquement après que l'utilisateur a cliqué sur soumettre, car cela donne un retour continu et empêche les soumissions inutiles. Le bouton de soumission sert d'indicateur : désactivé signifie que le formulaire n'est pas prêt, activé signifie qu'il l'est.

Dans ce formulaire de contact, le champ du nom ne doit pas être vide, l'email doit contenir un symbole `@`, et le message doit comporter au moins 10 caractères :

<ComponentDemo 
path='/webforj/formvalidation?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/formvalidation.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java'
height='500px'
/>

## Mises à jour de contenu dynamique {#dynamic-content-updates}

Les composants n'ont pas à rester dans un état fixe après leur création. Vous pouvez mettre à jour le texte, échanger des classes CSS et basculer l'état activé à tout moment en réponse aux événements de l'application. Un exemple courant consiste à fournir un retour pendant une tâche de longue durée :

```java
Label statusLabel = new Label("Prêt");
Button startButton = new Button("Démarrer le processus");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("Traitement...");
    statusLabel.addClassName("traitement");
    
    performTask(() -> {
        statusLabel.setText("Terminé");
        statusLabel.removeClassName("traitement");
        statusLabel.addClassName("succès");
        startButton.setEnabled(true);
    });
});
```

Désactiver le bouton pendant l'exécution de la tâche empêche les soumissions en double, et mettre à jour l'étiquette tient l'utilisateur informé de ce qui se passe.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

L'interface `ComponentLifecycleObserver` vous permet d'observer les événements de cycle de vie des composants de l'extérieur du composant lui-même. Cela est utile lorsque vous devez réagir à la création ou à la destruction d'un composant sans modifier son implémentation. Par exemple, vous pourriez l'utiliser pour maintenir un registre des composants actifs ou libérer des ressources externes lorsqu'un composant est supprimé.

### Utilisation de base {#basic-usage}

Appelez `addLifecycleObserver()` sur n'importe quel composant pour enregistrer un rappel. Le rappel reçoit le composant et l'événement de cycle de vie :

```java
Button button = new Button("Regardez Moi");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("Le bouton a été créé");
            break;
        case DESTROY:
            System.out.println("Le bouton a été détruit");
            break;
    }
});
```

### Modèle : Registre de ressources {#pattern-resource-registry}

L'événement DESTROY est particulièrement utile pour maintenir automatiquement un registre synchronisé. Plutôt que de retirer manuellement des composants lorsqu'ils ne sont plus nécessaires, vous laissez le composant notifier le registre lui-même :

```java
public class ResourceRegistry {
    private final Map<String, Component> activeComponents = new ConcurrentHashMap<>();
    
    public void track(Component component, String name) {
        activeComponents.put(name, component);
        
        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                activeComponents.remove(name);
            }
        });
    }
}
```

### Modèle : Coordination des composants {#pattern-component-coordination}

Une classe de coordonnateur qui gère un ensemble de composants connexes peut utiliser la même approche pour maintenir sa liste interne précise :

```java
public class FormCoordinator {
    private final List<DwcComponent<?>> managedComponents = new ArrayList<>();
    
    public void manage(DwcComponent<?> component) {
        managedComponents.add(component);
        
        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                managedComponents.remove(comp);
            }
        });
    }
    
    public void disableAll() {
        managedComponents.forEach(c -> c.setEnabled(false));
    }
}
```

### Quand utiliser {#when-to-use}

Utilisez `ComponentLifecycleObserver` pour :
- Construire des registres de composants
- Implémenter des journaux ou du suivi
- Coordonner plusieurs composants
- Nettoyer des ressources externes

Pour exécuter du code après qu'un composant est attaché au DOM, consultez [`whenAttached()`](/docs/building-ui/composite-components) dans le guide des composants composites.

## Données utilisateur {#user-data}

Les composants peuvent transporter des données arbitraires côté serveur via `setUserData()` et `getUserData()`. Les deux méthodes prennent une clé pour identifier les données. Cela est utile lorsque vous devez associer des objets de domaine ou du contexte avec un composant sans gérer une structure de recherche séparée.

```java
Button button = new Button("Traiter");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Comme les données utilisateur ne sont jamais envoyées au client, vous pouvez stocker en toute sécurité des informations sensibles ou de grands objets sans affecter le trafic réseau.
