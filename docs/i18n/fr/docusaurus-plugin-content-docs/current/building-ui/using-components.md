---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: fb67c93e2165a651245a703c772d3bcb
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Les composants sont les éléments de base des applications webforJ. Que vous utilisiez des composants intégrés comme `Button` et `TextField`, ou que vous deviez travailler avec des composants personnalisés fournis par votre équipe, la manière dont vous interagissez avec eux suit le même modèle cohérent : vous configurez des propriétés, gérez l'état et composez des composants dans des mises en page.

Ce guide se concentre sur ces opérations quotidiennes : pas sur les détails internes de la façon dont les composants fonctionnent, mais sur comment les utiliser en pratique.

## Propriétés des composants {#component-properties}

Chaque composant expose des propriétés qui contrôlent son contenu, son apparence et son comportement. La plupart de celles-ci disposent de méthodes Java typées dédiées (`setText()`, `setTheme()`, `setExpanse()`, etc.), qui constituent le moyen principal de configurer les composants dans webforJ. Les sections ci-dessous couvrent les propriétés et méthodes qui s'appliquent largement à travers les types de composants.

### Contenu textuel {#text-content}

La méthode `setText()` définit le texte visible d'un composant comme des caractères littéraux, tels que la légende d'un `Button` ou le contenu d'un `Label`. Pour les composants d'entrée comme `TextField`, utilisez `setValue()` à la place pour définir la valeur actuelle du champ.

```java
Button button = new Button();
button.setText("Cliquez ici");

Label label = new Label();
label.setText("Statut : prêt");

TextField field = new TextField();
field.setValue("Valeur initiale");
```

Le balisage écrit avec `setText()` apparaît sous forme de ces caractères et n'est jamais exécuté, ce qui empêche le texte provenant de l'entrée utilisateur ou des données externes d'être interprété comme du balisage actif.

```java
// Affiché comme les caractères littéraux "<b>Statut : prêt</b>"
component.setText("<b>Statut : prêt</b>");
```

:::note Utilisation de la balise `<html>`
Les versions antérieures de webforJ traitaient une valeur enveloppée dans `<html>` et passée à `setText()` comme du HTML. Ce comportement est obsolète et sera supprimé dans webforJ 27.00.

La première fois qu'une valeur enveloppée dans `<html>` atteint `setText()`, un avertissement est enregistré qui mentionne le composant et le site d'appel, afin que l'appel puisse être déplacé vers `setHtml()`.

Pour adopter à l'avance le comportement par défaut de webforJ 27.00, définissez `webforj.legacyHtmlInText` sur `false`. Dans une application Spring, la même valeur est définie via `webforj.legacy-html-in-text`.

```java
// webforj.legacyHtmlInText = true (par défaut)
component.setText("<html><b>Statut : prêt</b></html>"); // rend en gras

// webforj.legacyHtmlInText = false
component.setText("<html><b>Statut : prêt</b></html>"); // montre les caractères <b>Statut : prêt</b>
```
:::

### Rendu HTML {#rendering-html}

Certains composants prennent également en charge `setHtml()` pour les cas où vous devez rendre un balisage HTML intégré dans le contenu :

```java
Div container = new Div();
container.setHtml("<strong>Texte en gras</strong> et <em>texte en italique</em>");
```

:::danger Script inter-site (XSS)
Par précaution contre les [attaques de script inter-site (XSS)](/docs/security/application-security/common-threats#cross-site-scripting-xss), utilisez `setHtml()` uniquement avec un contenu que vous contrôlez directement.
:::

### Attributs HTML {#html-attributes}

La plupart de la configuration dans webforJ se fait via des méthodes Java typées plutôt qu'avec des attributs HTML bruts. Cependant, `setAttribute()` est utile pour passer des attributs d'accessibilité qui n'ont pas d'API dédiée :

```java
Button button = new Button("Soumettre");
button.setAttribute("aria-label", "Soumettre le formulaire");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Vérifiez le support des composants
Tous les composants ne prennent pas en charge les attributs arbitraires. Cela dépend de l'implémentation sous-jacente du composant.
:::

### Identifiants des composants {#component-ids}

Vous pouvez attribuer un identifiant à l'élément HTML d'un composant en utilisant `setAttribute()` :

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
webforJ assigne également des identifiants automatiques aux composants en interne. L'identifiant côté serveur (accessé via `getComponentId()`) est utilisé pour le suivi par le framework, tandis que l'identifiant côté client (accessé via `getClientComponentId()`) est utilisé pour la communication client-serveur. Ceux-ci sont distincts de l'attribut DOM `id` que vous définissez avec `setAttribute()`.
:::

### Styles {#styling}

Trois méthodes couvrent la plupart des besoins en matière de style : `setStyle()` pour des valeurs de propriétés CSS individuelles, et `addClassName()` et `removeClassName()` pour appliquer ou supprimer des classes CSS définies dans vos feuilles de style. 
Utilisez `setStyle()` pour des ajustements mineurs ou uniques, et utilisez des classes CSS pour appliquer un style plus large ou réutilisable.

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

## État des composants {#component-state}

Au-delà du contenu et de l'apparence, les composants ont des propriétés d'état qui déterminent s'ils sont visibles et s'ils répondent à l'interaction de l'utilisateur. Les deux les plus couramment utilisés sont `setVisible()` et `setEnabled()`.

`setVisible()` contrôle si le composant est affiché dans l'interface utilisateur ou non. `setEnabled()` contrôle s'il accepte une entrée ou une interaction tout en restant visible. Dans la plupart des cas, il est préférable de désactiver plutôt que de cacher : un bouton désactivé communique toujours qu'une action existe mais n'est pas encore disponible, ce qui est moins désorientant que de le voir apparaître et disparaître.

```java
// Révéler un champ supplémentaire lorsqu'une case à cocher est cochée
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

:::warning Désactivé et caché ne sont pas sécurisés
`setVisible(false)` et `setEnabled(false)` n'affectent que l'interface utilisateur. Ils ne font pas obstacle à un utilisateur déterminé qui pourrait invoquer l'action sous-jacente par le biais du navigateur ou d'une requête élaborée, donc ne comptez jamais sur eux pour protéger des opérations sensibles. Appliquez toujours un contrôle d'accès sur le serveur. Voir [Désactivé et caché ne sont pas sécurisés](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security) pour plus de détails.
:::

Le formulaire de connexion suivant démontre `setEnabled()` en pratique. Le bouton de connexion reste désactivé jusqu'à ce que les deux champs contiennent du contenu, ce qui rend clair pour l'utilisateur que des entrées sont requises avant de continuer :

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='450px'
/>

## Travailler avec des conteneurs {#working-with-containers}

Dans webforJ, la mise en page est gérée par des conteneurs, qui sont des composants qui contiennent d'autres composants et contrôlent leur agencement. Vous ne positionnez pas les composants enfants manuellement ; au lieu de cela, vous les ajoutez à un conteneur et configurez les propriétés de mise en page de ce conteneur.

### Ajout de composants {#adding-components}

Tous les conteneurs fournissent une méthode `add()`. Vous pouvez passer des composants un par un ou tous en même temps :

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Cliquez ici"));

TextField nameField = new TextField("Nom");
TextField emailField = new TextField("Email");
Button submitButton = new Button("Soumettre");

container.add(nameField, emailField, submitButton);
```

### Options de mise en page {#layout-options}

`FlexLayout` est le conteneur de mise en page principal dans webforJ et couvre la majorité des cas d'utilisation : lignes, colonnes, alignement, espacement et enveloppement. Pour des dispositions plus complexes telles que CSS Grid ou un positionnement personnalisé, vous pouvez appliquer du CSS directement via `setStyle()` ou `addClassName()` sur n'importe quel composant de conteneur. Voir la documentation sur [FlexLayout](/docs/components/flex-layout) pour toute la gamme d'options de mise en page.

### Affichage et masquage des sections {#showing-hiding-sections}

Une utilisation courante de `setVisible()` dans les conteneurs consiste à révéler une interface utilisateur supplémentaire uniquement lorsqu'elle est pertinente. Cela maintient l'interface concentrée et réduit le désordre visuel. Plutôt que de naviguer vers une nouvelle vue, vous pouvez afficher une section de la mise en page actuelle en réponse directe à l'entrée de l'utilisateur.

Le panneau de paramètres suivant illustre cela : les préférences de notification de base sont toujours visibles, et une section d'options avancées n'apparaît que lorsque l'utilisateur le demande. Le bouton de sauvegarde s'active dès qu'un paramètre est modifié :

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

Ceci est utile lorsque vous devez remplacer complètement le contenu, comme échanger un indicateur de chargement contre des données chargées.

## Validation de formulaire {#form-validation}

Coordonner plusieurs composants pour contrôler une action de soumission est un modèle courant dans les interfaces utilisateur webforJ. L'idée de base est que chaque champ d'entrée enregistre un écouteur, et chaque fois qu'une valeur change, le formulaire réévalue si tous les critères sont remplis et met à jour le bouton de soumission en conséquence.

L'exemple ci-dessous câble cela manuellement afin que vous puissiez voir comment l'état des composants et les écouteurs d'événements fonctionnent ensemble. Ce n'est pas l'approche recommandée pour de vrais formulaires : la logique d'écouteur manuelle devient difficile à maintenir à mesure que les formulaires grandissent, et cela ne connecte pas vos composants à un modèle de données sous-jacent.

:::tip Utilisez la liaison de données pour la validation des formulaires
Pour les formulaires de production, utilisez [la liaison de données](/docs/data-binding/overview). Cela couvre la validation, la synchronisation bidirectionnelle entre les composants et votre modèle, et la transformation de valeur via `BindingContext`. Le modèle manuel montré ici est à titre d'illustration seulement.
:::

Dans ce formulaire de contact, le champ de nom ne doit pas être vide, l'email doit contenir un symbole `@`, et le message doit comporter au moins 10 caractères :

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/resources/static/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## Mises à jour de contenu dynamiques {#dynamic-content-updates}

Les composants n'ont pas à rester dans un état fixe après leur création. Vous pouvez mettre à jour le texte, échanger des classes CSS et basculer l'état activé à tout moment en réponse aux événements de l'application. Un exemple courant est de fournir un retour d'information lors d'une tâche de longue durée :

```java
Label statusLabel = new Label("Prêt");
Button startButton = new Button("Démarrer le processus");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("Traitement...");
    statusLabel.addClassName("processing");
    
    performTask(() -> {
        statusLabel.setText("Terminé");
        statusLabel.removeClassName("processing");
        statusLabel.addClassName("success");
        startButton.setEnabled(true);
    });
});
```

Désactiver le bouton pendant que la tâche s'exécute prévient les soumissions en double, et mettre à jour l'étiquette garde l'utilisateur informé de ce qui se passe.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

L'interface `ComponentLifecycleObserver` vous permet d'observer les événements du cycle de vie des composants de l'extérieur du composant lui-même. Cela est utile lorsque vous devez réagir à la création ou la destruction d'un composant sans modifier son implémentation. Par exemple, vous pourriez l'utiliser pour maintenir un registre des composants actifs ou libérer des ressources externes lorsqu'un composant est supprimé.

### Utilisation de base {#basic-usage}

Appelez `addLifecycleObserver()` sur n'importe quel composant pour enregistrer un rappel. Le rappel reçoit le composant et l'événement du cycle de vie :

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

### Modèle : Registre des ressources {#pattern-resource-registry}

L'événement DESTROY est particulièrement utile pour garder un registre automatiquement synchronisé. Plutôt que de supprimer manuellement les composants lorsqu'ils ne sont plus nécessaires, vous laissez le composant notifier lui-même le registre :

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

Une classe de coordinateur qui gère un ensemble de composants connexes peut utiliser la même approche pour garder sa liste interne précise :

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
- Implémenter des journaux ou du monitoring
- Coordonner plusieurs composants
- Nettoyer des ressources externes

Pour exécuter du code après qu'un composant soit attaché au DOM, consultez `whenAttached()` dans le guide [Composing Components](/docs/building-ui/composing-components).

## Données utilisateur {#user-data}

Les composants peuvent transporter des données arbitraires côté serveur via `setUserData()` et `getUserData()`. Les deux méthodes acceptent une clé pour identifier les données. Cela est utile lorsque vous devez associer des objets de domaine ou un contexte à un composant sans gérer une structure de recherche séparée.

```java
Button button = new Button("Traiter");
button.setUserData("contexte", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("contexte");
    processTask(context.getUserId(), context.getTaskId());
});
```

Étant donné que les données utilisateur ne sont jamais envoyées au client, vous pouvez stocker en toute sécurité des informations sensibles ou de grands objets sans affecter le trafic réseau.
