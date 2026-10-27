---
sidebar_position: 3
title: Utilisation des Composants
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
_i18n_hash: df0f3d5a956eda1abd755f646899a7cc
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Les composants sont les éléments de base des applications webforJ. Que vous utilisiez des composants intégrés comme `Button` et `TextField`, ou que vous travailliez avec des composants personnalisés fournis par votre équipe, la façon dont vous interagissez avec eux suit le même modèle cohérent : vous configurez des propriétés, gérez des états et composez des composants en mises en page.

Ce guide se concentre sur ces opérations quotidiennes : non pas sur les détails internes du fonctionnement des composants, mais sur la façon de faire les choses en pratique.

## Propriétés du composant {#component-properties}

Chaque composant expose des propriétés qui contrôlent son contenu, son apparence et son comportement. La plupart d'entre elles disposent de méthodes Java typées dédiées (`setText()`, `setTheme()`, `setExpanse()`, etc.), qui sont le moyen principal de configurer les composants dans webforJ. Les sections ci-dessous couvrent les propriétés et méthodes qui s'appliquent généralement à tous les types de composants.

### Contenu texte {#text-content}

La méthode `setText()` définit le texte visible d'un composant comme des caractères littéraux, tels que la légende d'un `Button` ou le contenu d'un `Label`. Pour les composants d'entrée comme `TextField`, utilisez `setValue()` à la place pour définir la valeur actuelle du champ.

```java
Button button = new Button();
button.setText("Cliquez Ici");

Label label = new Label();
label.setText("Statut : prêt");

TextField field = new TextField();
field.setValue("Valeur initiale");
```

Le balisage écrit avec `setText()` apparaît sous la forme de ces caractères et n'est jamais exécuté, ce qui empêche que le texte provenant d'une entrée utilisateur ou de données externes soit interprété comme du balisage en direct.

```java
// Affiché comme les caractères littéraux "<b>Statut : prêt</b>"
component.setText("<b>Statut : prêt</b>");
```

:::note Utilisation de la balise `<html>`
Les versions antérieures de webforJ traitaient une valeur encapsulée dans `<html>` et passée à `setText()` comme du HTML. Ce comportement est obsolète et sera supprimé dans webforJ 27.00.

La première fois qu'une valeur encapsulée dans `<html>` atteint `setText()`, un avertissement est enregistré qui nomme le composant et le site d'appel, afin que l'appel puisse être déplacé vers `setHtml()`.

Pour adopter le comportement par défaut de webforJ 27.00 à l'avance, définissez `webforj.legacyHtmlInText` sur `false`. Dans une application Spring, la même valeur est définie via `webforj.legacy-html-in-text`.

```java
// webforj.legacyHtmlInText = true (par défaut)
component.setText("<html><b>Statut : prêt</b></html>"); // rend le texte en gras

// webforj.legacyHtmlInText = false
component.setText("<html><b>Statut : prêt</b></html>"); // affiche les caractères <b>Statut : prêt</b>
```
:::

### Rendu HTML {#rendering-html}

Certains composants prennent également en charge `setHtml()` pour les cas où vous devez rendre du balisage HTML en ligne dans le contenu :

```java
Div container = new Div();
container.setHtml("<strong>Texte en gras</strong> et <em>texte en italique</em>");
```

:::danger Cross-site Scripting (XSS)
Par précaution contre [les attaques de scripting inter-site (XSS)](/docs/security/application-security/common-threats#cross-site-scripting-xss), utilisez `setHtml()` uniquement avec un contenu que vous contrôlez directement.
:::

### Attributs HTML {#html-attributes}

La plupart des configurations dans webforJ sont effectuées via des méthodes Java typées plutôt que par des attributs HTML bruts. Cependant, `setAttribute()` est utile pour transmettre des attributs d'accessibilité qui n'ont pas d'API dédiée :

```java
Button button = new Button("Soumettre");
button.setAttribute("aria-label", "Soumettre le formulaire");
button.setAttribute("aria-describedby", "hint-formulaire");
```

:::note Vérifier le support des composants
Tous les composants ne prennent pas en charge des attributs arbitraires. Cela dépend de l'implémentation sous-jacente du composant.
:::

### Identifiants de composant {#component-ids}

Vous pouvez attribuer un identifiant à l'élément HTML d'un composant en utilisant `setAttribute()` :

```java
Button submitButton = new Button("Soumettre");
submitButton.setAttribute("id", "btn-soumettre");

TextField emailField = new TextField("Email");
emailField.setAttribute("id", "input-email");
```

Les ID DOM sont couramment utilisés pour des sélecteurs de test et le ciblage CSS dans vos feuilles de style.

:::tip Préférez les classes pour le ciblage de plusieurs composants
Contrairement aux classes CSS, les ID doivent être uniques dans votre application. Si vous devez cibler plusieurs composants, utilisez `addClassName()` à la place.
:::

:::info ID gérés par le framework
webforJ attribue également des identifiants automatiques aux composants en interne. L'ID côté serveur (accessible via `getComponentId()`) est utilisé pour le suivi par le framework, tandis que l'ID côté client (accessible via `getClientComponentId()`) est utilisé pour la communication client-serveur. Ceux-ci sont séparés de l'attribut DOM `id` que vous définissez avec `setAttribute()`.
:::

### Style {#styling}

Trois méthodes couvrent la plupart des besoins en matière de style : `setStyle()` pour des valeurs de propriété CSS individuelles, et `addClassName()` et `removeClassName()` pour appliquer ou supprimer des classes CSS définies dans vos feuilles de style.
Utilisez `setStyle()` pour des ajustements de style mineurs ou ponctuels, et utilisez des classes CSS pour appliquer un style plus large ou réutilisable.

```java
Div container = new Div();
container.setStyle("padding", "20px");

if (isHighPriority) {
    container.setStyle("border-left", "4px solid red");
}

Button button = new Button("Basculer");
button.addClassName("primaire", "grande");

if (isLoading) {
    button.addClassName("chargement");
}
```

## État du composant {#component-state}

Au-delà du contenu et de l'apparence, les composants possèdent des propriétés d'état qui déterminent s'ils sont visibles et s'ils réagissent à l'interaction de l'utilisateur. Les deux les plus couramment utilisés sont `setVisible()` et `setEnabled()`.

`setVisible()` contrôle si le composant est rendu dans l'interface utilisateur. `setEnabled()` contrôle s'il accepte les entrées ou l'interaction tout en restant visible. Dans la plupart des cas, désactiver est préférable à cacher : un bouton désactivé communique toujours qu'une action existe mais n'est pas encore disponible, ce qui est moins déroutant que de le faire apparaître et disparaître.

```java
// Révéler un champ supplémentaire lorsque la case à cocher est cochée
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

:::warning Désactivé et caché ne sont pas la sécurité
`setVisible(false)` et `setEnabled(false)` affectent uniquement l'interface utilisateur. Ils ne stoppent pas un utilisateur déterminé d'invoquer l'action sous-jacente via le navigateur ou une requête forgée, donc ne comptez jamais sur eux pour protéger des opérations sensibles. Toujours imposer le contrôle d'accès sur le serveur. Voir [Désactivé et caché ne sont pas la sécurité](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security) pour plus de détails.
:::

Le formulaire de connexion suivant démontre `setEnabled()` en pratique. Le bouton de connexion reste désactivé tant que les deux champs ont du contenu, ce qui rend clair pour l'utilisateur que des informations sont requises avant de poursuivre :

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
]}
height='450px'
/>

## Travailler avec des conteneurs {#working-with-containers}

Dans webforJ, la mise en page est gérée par des conteneurs, qui sont des composants qui contiennent d'autres composants et contrôlent leur agencement. Vous ne positionnez pas manuellement les composants enfants ; à la place, vous les ajoutez à un conteneur et configurez les propriétés de mise en page de ce conteneur.

### Ajouter des composants {#adding-components}

Tous les conteneurs fournissent une méthode `add()`. Vous pouvez passer des composants un par un ou tous en même temps :

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Cliquez Ici"));

TextField nameField = new TextField("Nom");
TextField emailField = new TextField("Email");
Button submitButton = new Button("Soumettre");

container.add(nameField, emailField, submitButton);
```

### Options de mise en page {#layout-options}

`FlexLayout` est le conteneur de mise en page principal dans webforJ et couvre la majorité des cas d'utilisation : rangées, colonnes, alignement, espacement et emballage. Pour des arrangements plus complexes tels que CSS Grid ou un positionnement personnalisé, vous pouvez appliquer du CSS directement via `setStyle()` ou `addClassName()` sur n'importe quel composant de conteneur. Consultez la documentation [FlexLayout](/docs/components/flex-layout) pour la gamme complète d'options de mise en page.

### Afficher et masquer des sections {#showing-hiding-sections}

Une utilisation courante de `setVisible()` dans les conteneurs consiste à révéler une interface utilisateur supplémentaire uniquement lorsqu'elle est pertinente. Cela garde l'interface concentrée et réduit l'encombrement visuel. Plutôt que de naviguer vers une nouvelle vue, vous pouvez afficher une section de la mise en page actuelle en réponse directe à l'entrée de l'utilisateur.

Le panneau de configuration suivant illustre cela : les préférences de notification de base sont toujours visibles, et une section d'options avancées n'apparaît que lorsque l'utilisateur le demande. Le bouton de sauvegarde s'active dès qu'un paramètre est modifié :

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
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

Ceci est utile lorsque vous avez besoin de remplacer complètement le contenu, comme échanger un indicateur de chargement contre les données chargées.

## Validation de formulaire {#form-validation}

Coordonner plusieurs composants pour réguler une action de soumission est un modèle courant dans les interfaces webforJ. L'idée de base est que chaque champ d'entrée enregistre un écouteur, et chaque fois qu'une valeur change, le formulaire réévalue si tous les critères sont remplis et met à jour le bouton de soumission en conséquence.

L'exemple ci-dessous met cela en place manuellement afin que vous puissiez voir comment l'état des composants et les écouteurs d'événements interagissent. Ce n'est pas l'approche recommandée pour des formulaires réels : la logique d'écouteur manuelle devient difficile à maintenir au fur et à mesure que les formulaires grandissent, et elle ne relie pas vos composants à un modèle de données sous-jacent.

:::tip Utiliser le binding de données pour la validation de formulaire
Pour les formulaires de production, utilisez le [binding de données](/docs/data-binding/overview). Il couvre la validation, la synchronisation bidirectionnelle entre les composants et votre modèle, et la transformation de valeur via `BindingContext`. Le modèle manuel montré ici est seulement à titre d'illustration.
:::

Dans ce formulaire de contact, le champ du nom ne doit pas être vide, l'email doit contenir un symbole `@`, et le message doit comporter au moins 10 caractères :

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
]}
height='500px'
/>

## Mises à jour de contenu dynamique {#dynamic-content-updates}

Les composants n'ont pas à rester dans un état fixe après leur création. Vous pouvez mettre à jour le texte, échanger des classes CSS, et basculer l'état activé à tout moment en réponse aux événements de l'application. Un exemple courant est de fournir un retour d'information pendant une tâche prolongée :

```java
Label statusLabel = new Label("Prêt");
Button startButton = new Button("Démarrer le Processus");

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

Désactiver le bouton pendant que la tâche s'exécute empêche les soumissions en double, et mettre à jour le label garde l'utilisateur informé de ce qui se passe.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

L'interface `ComponentLifecycleObserver` vous permet d'observer les événements du cycle de vie des composants depuis l'extérieur du composant lui-même. Cela est utile lorsque vous devez réagir à un composant étant créé ou détruit sans modifier son implémentation. Par exemple, vous pourriez l'utiliser pour maintenir un registre des composants actifs ou libérer des ressources externes lors de la suppression d'un composant.

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

### Modèle : Registre des ressources {#pattern-resource-registry}

L'événement DESTROY est particulièrement utile pour maintenir un registre en synchronisation automatique. Plutôt que de supprimer manuellement des composants lorsqu'ils ne sont plus nécessaires, vous laissez le composant notifier le registre lui-même :

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

### Modèle : Coordination de composants {#pattern-component-coordination}

Une classe de coordination qui gère un ensemble de composants liés peut utiliser la même approche pour tenir sa liste interne à jour :

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

### Quand l'utiliser {#when-to-use}

Utilisez `ComponentLifecycleObserver` pour :
- Construire des registres de composants
- Implémenter la journalisation ou la surveillance
- Coordonner plusieurs composants
- Nettoyer les ressources externes

Pour exécuter du code après qu'un composant soit attaché au DOM, voyez `whenAttached()` dans le guide [Composing Components](/docs/building-ui/composing-components).

## Données utilisateur {#user-data}

Les composants peuvent transporter des données côté serveur arbitraires via `setUserData()` et `getUserData()`. Les deux méthodes prennent une clé pour identifier les données. Cela est utile lorsque vous devez associer des objets de domaine ou un contexte avec un composant sans gérer une structure de recherche séparée.

```java
Button button = new Button("Traiter");
button.setUserData("contexte", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("contexte");
    processTask(context.getUserId(), context.getTaskId());
});
```

Comme les données utilisateur ne sont jamais envoyées au client, vous pouvez stocker en toute sécurité des informations sensibles ou des objets volumineux sans affecter le trafic réseau.
