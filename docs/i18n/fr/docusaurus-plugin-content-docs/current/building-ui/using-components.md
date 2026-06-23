---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: 97722c8e3bf6c3129c078d8ae23cf2a4
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Les composants sont les blocs de construction des applications webforJ. Que vous utilisiez des composants intégrés comme `Button` et `TextField`, ou que vous travailliez avec des composants personnalisés fournis par votre équipe, la manière dont vous interagissez avec eux suit le même modèle cohérent : vous configurez des propriétés, gérez l'état et composez des composants en mises en page.

Ce guide se concentre sur ces opérations quotidiennes : pas sur les détails internes de la façon dont les composants fonctionnent, mais sur la façon de les utiliser en pratique.

## Propriétés des composants {#component-properties}

Chaque composant expose des propriétés qui contrôlent son contenu, son apparence et son comportement. La plupart d'entre elles ont des méthodes Java typées dédiées (`setText()`, `setTheme()`, `setExpanse()`, etc.), qui constituent le moyen principal de configurer les composants dans webforJ. Les sections suivantes couvrent les propriétés et les méthodes qui s'appliquent largement à tous les types de composants.

### Contenu textuel {#text-content}

La méthode `setText()` définit le texte visible d'un composant en tant que caractères littéraux, tels que la légende d'un `Button` ou le contenu d'un `Label`. Pour les composants d'entrée comme `TextField`, utilisez `setValue()` à la place pour définir la valeur actuelle du champ.

```java
Button button = new Button();
button.setText("Cliquez ici");

Label label = new Label();
label.setText("Statut : prêt");

TextField field = new TextField();
field.setValue("Valeur initiale");
```

Le balisage écrit avec `setText()` apparaît comme ces caractères et n'est jamais exécuté, ce qui empêche le texte provenant d'entrées utilisateur ou de données externes d'être interprété comme un balisage actif.

```java
// Affiché comme les caractères littéraux "<b>Statut : prêt</b>"
component.setText("<b>Statut : prêt</b>");
```

:::note Utilisation de la balise `<html>`
Les versions antérieures de webforJ traitaient une valeur enveloppée dans `<html>` et passée à `setText()` comme HTML. Ce comportement est obsolète et sera supprimé dans webforJ 27.00.

La première fois qu'une valeur enveloppée dans `<html>` atteint `setText()`, un avertissement est enregistré qui nomme le composant et le site de l'appel, afin que l'appel puisse être déplacé vers `setHtml()`.

Pour adopter le défaut de webforJ 27.00 à l'avance, définissez `webforj.legacyHtmlInText` sur `false`. Dans une application Spring, la même valeur est définie via `webforj.legacy-html-in-text`.

```java
// webforj.legacyHtmlInText = true (par défaut)
component.setText("<html><b>Statut : prêt</b></html>"); // rend le texte en gras

// webforj.legacyHtmlInText = false
component.setText("<html><b>Statut : prêt</b></html>"); // montre les caractères <b>Statut : prêt</b>
```
:::

### Rendu HTML {#rendering-html}

Certains composants prennent également en charge `setHtml()` pour les cas où vous devez rendre du balisage HTML en ligne dans le contenu :

```java
Div container = new Div();
container.setHtml("<strong>Texte en gras</strong> et <em>texte en italique</em>");
```

:::danger Cross-site Scripting (XSS)
Par précaution contre les [attaques de cross-site scripting (XSS)](/docs/security/application-security/common-threats#cross-site-scripting-xss), utilisez `setHtml()` uniquement avec du contenu que vous contrôlez directement.
:::

### Attributs HTML {#html-attributes}

La plupart de la configuration dans webforJ se fait via des méthodes Java typées plutôt que des attributs HTML bruts. Cependant, `setAttribute()` est utile pour passer des attributs d'accessibilité qui n'ont pas d'API dédiée :

```java
Button button = new Button("Soumettre");
button.setAttribute("aria-label", "Soumettre le formulaire");
button.setAttribute("aria-describedby", "astuce-du-formulaire");
```

:::note Vérifiez le support des composants
Tous les composants ne prennent pas en charge les attributs arbitraires. Cela dépend de l'implémentation sous-jacente du composant.
:::

### Identifiants de composants {#component-ids}

Vous pouvez attribuer un identifiant à l'élément HTML d'un composant en utilisant `setAttribute()` :

```java
Button submitButton = new Button("Soumettre");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("Email");
emailField.setAttribute("id", "email-input");
```

Les ID DOM sont couramment utilisés pour les sélecteurs de test et le ciblage CSS dans vos feuilles de style.

:::tip Préférez les classes pour le ciblage de plusieurs composants
Contrairement aux classes CSS, les ID doivent être uniques dans votre application. Si vous devez cibler plusieurs composants, utilisez `addClassName()` à la place.
:::

:::info IDs gérés par le framework
webforJ attribue également des identifiants automatiques aux composants en interne. L'ID côté serveur (accédé via `getComponentId()`) est utilisé pour le suivi par le framework, tandis que l'ID côté client (accédé via `getClientComponentId()`) est utilisé pour la communication client-serveur. Ceux-ci sont séparés de l'attribut DOM `id` que vous définissez avec `setAttribute()`.
:::

### Style {#styling}

Trois méthodes couvrent la plupart des besoins en style : `setStyle()` pour les valeurs individuelles de propriété CSS, et `addClassName()` et `removeClassName()` pour appliquer ou supprimer les classes CSS définies dans vos feuilles de style. 
Utilisez `setStyle()` pour des ajustements stylistiques mineurs ou ponctuels, et utilisez les classes CSS pour appliquer des styles plus larges ou réutilisables.

```java
Div container = new Div();
container.setStyle("padding", "20px");

if (isHighPriority) {
    container.setStyle("border-left", "4px solid red");
}

Button button = new Button("Basculer");
button.addClassName("primary", "large");

if (isLoading) {
    button.addClassName("loading");
}
```

:::note Approche héritée
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) est une approche héritée et n'est généralement pas recommandée pour de nouveaux projets. Dans la plupart des cas, gardez vos styles dans des fichiers CSS séparés.
:::

## État du composant {#component-state}

Au-delà du contenu et de l'apparence, les composants ont des propriétés d'état qui déterminent s'ils sont visibles et s'ils répondent à l'interaction utilisateur. Les deux plus couramment utilisées sont `setVisible()` et `setEnabled()`.

`setVisible()` contrôle si le composant est rendu dans l'interface utilisateur. `setEnabled()` contrôle s'il accepte des entrées ou des interactions tout en restant visible. Dans la plupart des cas, désactiver est préférable à masquer : un bouton désactivé communique toujours qu'une action existe mais n'est pas encore disponible, ce qui est moins déroutant que de le faire apparaître et disparaître.

```java
// Révèle un champ supplémentaire lorsque une case à cocher est cochée
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

Le formulaire de connexion suivant démontre `setEnabled()` en pratique. Le bouton de connexion reste désactivé tant que les deux champs n'ont pas de contenu, ce qui indique clairement à l'utilisateur qu'une entrée est requise avant de continuer :

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='400px'
/>

## Travailler avec des conteneurs {#working-with-containers}

Dans webforJ, la mise en page est gérée par des conteneurs, qui sont des composants qui contiennent d'autres composants et contrôlent leur arrangement. Vous ne positionnez pas manuellement les composants enfants ; au contraire, vous les ajoutez à un conteneur et configurez les propriétés de mise en page de ce conteneur.

### Ajouter des composants {#adding-components}

Tous les conteneurs fournissent une méthode `add()`. Vous pouvez passer des composants un par un ou tous à la fois :

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Cliquez ici"));

TextField nameField = new TextField("Nom");
TextField emailField = new TextField("Email");
Button submitButton = new Button("Soumettre");

container.add(nameField, emailField, submitButton);
```

### Options de mise en page {#layout-options}

`FlexLayout` est le conteneur de mise en page principal dans webforJ et couvre la majorité des cas d'utilisation : lignes, colonnes, alignement, espacement et enveloppement. Pour des arrangements plus complexes tels que la grille CSS ou le positionnement personnalisé, vous pouvez appliquer du CSS directement via `setStyle()` ou `addClassName()` sur n'importe quel composant conteneur. Consultez la documentation [FlexLayout](/docs/components/flex-layout) pour l'ensemble complet des options de mise en page.

### Afficher et masquer des sections {#showing-hiding-sections}

Une utilisation courante de `setVisible()` dans les conteneurs consiste à révéler une interface utilisateur supplémentaire uniquement lorsque cela est pertinent. Cela maintient l'interface centrée et réduit l'encombrement visuel. Plutôt que de naviguer vers une nouvelle vue, vous pouvez afficher une section de la mise en page actuelle en réponse directe à une entrée utilisateur.

Le panneau de paramètres suivant démontre cela : les préférences de notification de base sont toujours visibles, et une section d'options avancées n'apparaît que lorsque l'utilisateur le demande. Le bouton de sauvegarde s'active dès qu'un paramètre est modifié :

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/resources/static/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### Gestion des conteneurs {#container-management}

Utilisez `remove()` et `removeAll()` pour retirer des composants d'un conteneur à l'exécution :

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Temporaire");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Ceci est utile lorsque vous devez remplacer complètement le contenu, par exemple en échangeant un indicateur de chargement contre les données chargées.

## Validation de formulaire {#form-validation}

Coordonner plusieurs composants pour bloquer une action de soumission est l'un des motifs les plus courants dans les interfaces webforJ. L'idée principale est simple : chaque champ de saisie enregistre un écouteur, et chaque fois qu'une valeur change, le formulaire réévalue si tous les critères sont satisfaits et met à jour le bouton de soumission en conséquence.

Ceci est préférable à l'affichage des erreurs de validation uniquement après que l'utilisateur ait cliqué sur soumettre, car cela donne un retour continu et évite les soumissions inutiles. Le bouton de soumission sert d'indicateur : désactivé signifie que le formulaire n'est pas prêt, activé signifie qu'il l'est.

Dans ce formulaire de contact, le champ de nom ne doit pas être vide, l'email doit contenir un symbole `@`, et le message doit comporter au moins 10 caractères :

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/resources/static/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## Mises à jour de contenu dynamique {#dynamic-content-updates}

Les composants n'ont pas à rester dans un état fixe après leur création. Vous pouvez mettre à jour le texte, échanger des classes CSS et basculer l'état activé à tout moment en réponse à des événements de l'application. Un exemple courant est de fournir un retour pendant une tâche longue :

```java
Label statusLabel = new Label("Prêt");
Button startButton = new Button("Démarrer le processus");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("Traitement en cours...");
    statusLabel.addClassName("processing");
    
    performTask(() -> {
        statusLabel.setText("Complété");
        statusLabel.removeClassName("processing");
        statusLabel.addClassName("success");
        startButton.setEnabled(true);
    });
});
```

Désactiver le bouton pendant l'exécution de la tâche empêche les soumissions en double, et mettre à jour le label informe l'utilisateur de ce qui se passe.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

L'interface `ComponentLifecycleObserver` vous permet d'observer des événements de cycle de vie des composants depuis l'extérieur du composant lui-même. Ceci est utile lorsque vous devez réagir à la création ou à la destruction d'un composant sans modifier son implémentation. Par exemple, vous pourriez l'utiliser pour maintenir un registre des composants actifs ou libérer des ressources externes lorsqu'un composant est retiré.

### Utilisation de base {#basic-usage}

Appelez `addLifecycleObserver()` sur n'importe quel composant pour enregistrer un rappel. Le rappel reçoit le composant et l'événement de cycle de vie :

```java
Button button = new Button("Regardez-moi");

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

### Motif : Registre de ressources {#pattern-resource-registry}

L'événement DESTROY est particulièrement utile pour maintenir un registre automatiquement synchronisé. Plutôt que de supprimer manuellement des composants lorsqu'ils ne sont plus nécessaires, vous laissez le composant notifier lui-même le registre :

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

### Motif : Coordination des composants {#pattern-component-coordination}

Une classe coordonnatrice qui gère un ensemble de composants connexes peut utiliser la même approche pour garder sa liste interne exacte :

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

Pour exécuter du code après qu'un composant soit attaché au DOM, consultez [`whenAttached()`](/docs/building-ui/composing-components) dans le guide des Composants composites.

## Données utilisateur {#user-data}

Les composants peuvent transporter des données serveur arbitraires via `setUserData()` et `getUserData()`. Les deux méthodes prennent une clé pour identifier les données. Cela est utile lorsque vous devez associer des objets de domaine ou du contexte avec un composant sans gérer une structure de recherche distincte.

```java
Button button = new Button("Traiter");
button.setUserData("contexte", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("contexte");
    processTask(context.getUserId(), context.getTaskId());
});
```

Puisque les données utilisateur ne sont jamais envoyées au client, vous pouvez stocker en toute sécurité des informations sensibles ou de grands objets sans affecter le trafic réseau.
