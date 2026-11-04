---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons and customizable tab properties.
_i18n_hash: 2d2e78638206283a7423cea736e3bf2c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Plusieurs sections de contenu peuvent être organisées sous un seul `TabbedPane`, où chaque section est liée à un `Tab` cliquable. Une seule section est visible à la fois, et les onglets peuvent afficher du texte, des icônes ou les deux pour aider les utilisateurs à naviguer entre eux.

<!-- INTRO_END -->

## Utilisations {#usages}

La classe `TabbedPane` offre aux développeurs un outil puissant pour organiser et présenter plusieurs onglets ou sections au sein d'une interface utilisateur. Voici quelques scénarios typiques où vous pourriez utiliser un `TabbedPane` dans votre application :

1. **Visionneuse de documents** : Implémentation d'une visionneuse de documents où chaque onglet représente un document ou un fichier différent. Les utilisateurs peuvent facilement basculer entre les documents ouverts pour un multitâche efficace.

2. **Gestion des données** : Utilisez un `TabbedPane` pour organiser les tâches de gestion des données, par exemple :
    - Différents ensembles de données à afficher dans une application
    - Divers profils utilisateur peuvent être affichés dans des onglets séparés
    - Différents profils dans un système de gestion des utilisateurs

3. **Sélection de module** : Un `TabbedPane` peut représenter différents modules ou sections. Chaque onglet peut encapsuler les fonctionnalités d'un module spécifique, permettant aux utilisateurs de se concentrer sur un aspect de l'application à la fois.

4. **Gestion des tâches** : Les applications de gestion des tâches peuvent utiliser un `TabbedPane` pour représenter divers projets ou tâches. Chaque onglet pourrait correspondre à un projet spécifique, permettant aux utilisateurs de gérer et de suivre les tâches séparément.

5. **Navigation dans le programme** : Dans une application qui doit exécuter divers programmes, un `TabbedPane` pourrait :
    - Servir de barre latérale permettant d'exécuter différentes applications ou programmes au sein d'une seule application, comme ce qui est montré dans le modèle [`AppLayout`](./app-layout.md)
    - Créer une barre supérieure qui peut servir un but similaire, ou représenter des sous-applications au sein d'une application déjà sélectionnée

## Onglets <DocChip chip='name' label="dwc-tab" /> {#tabs}

Les onglets sont des éléments d'interface utilisateur qui peuvent être ajoutés aux panneaux d'onglets pour organiser et basculer entre différentes vues de contenu.

:::important
Les onglets ne sont pas destinés à être utilisés comme des composants autonomes. Ils sont conçus pour être utilisés en conjonction avec des panneaux d'onglets. Cette classe n'est pas un `Component` et ne doit pas être utilisée comme telle.
:::

### Propriétés {#properties}

Les onglets sont composés des propriétés suivantes, qui sont utilisées lors de leur ajout dans un `TabbedPane`. Ces propriétés disposent de getters et de setters pour faciliter la personnalisation au sein d'un `TabbedPane`.

1. **Texte(`String`)** : Le texte qui est affiché comme titre pour l'`Onglet` au sein du `TabbedPane`. Cela est également appelé le titre via les méthodes `getTitle()` et `setTitle(String title)`.

2. **Tooltip(`String`)** : Le texte d'info-bulle associé à l'`Onglet`, qui est affiché lorsque le curseur survole l'`Onglet`.

3. **Activé(`boolean`)** : Représente si l'`Onglet` est activé ou non. Peut être modifié avec la méthode `setEnabled(boolean enabled)`.

4. **Fermable(`boolean`)** : Représente si l'`Onglet` peut être fermé. Peut être modifié avec la méthode `setClosable(boolean closable)`. Cela ajoutera un bouton de fermeture sur l'`Onglet` qui peut être cliqué, déclenchant un événement de suppression. Le composant `TabbedPane` dicte comment gérer la suppression.

5. **Slot(`Component`)** :
   Les slots fournissent des options flexibles pour améliorer la capacité d'un `Onglet`. Vous pouvez avoir des icônes, des étiquettes, des spinners de chargement, des fonctions d'effacement/réinitialisation, des photos de profil/avatar et d'autres composants bénéfiques imbriqués au sein d'un `Onglet` pour clarifier davantage le sens prévu pour les utilisateurs.
   Vous pouvez ajouter un composant au slot `prefix` d'un `Onglet` lors de la construction. Alternativement, vous pouvez utiliser les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après l'option affichée dans un `Onglet`.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## Manipulation de l'`Onglet` {#tab-manipulation}

Différentes méthodes existent pour permettre aux développeurs d'ajouter, d'insérer, de supprimer et de manipuler diverses propriétés des éléments `Onglet` au sein du `TabbedPane`.

### Ajout d'un `Onglet` {#adding-a-tab}

Les méthodes `addTab()` et `add()` existent sous différentes surcharges pour permettre aux développeurs de flexibilité lors de l'ajout de nouveaux onglets au `TabbedPane`. L'ajout d'un `Onglet` le placera après tous les onglets existants.

1. **`addTab(String text)`** : ajoute un `Onglet` au `TabbedPane` avec le `String` spécifié comme texte de l'`Onglet`.
2. **`addTab(Tab tab)`** : ajoute l'`Onglet` fourni en paramètre au `TabbedPane`.
3. **`addTab(String text, Component component)`** : ajoute un `Onglet` avec le `String` donné comme texte de l'`Onglet`, et le `Component` fourni affiché dans la section de contenu du `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** : ajoute l'`Onglet` fourni et affiche le `Component` fourni dans la section de contenu du `TabbedPane`.
5. **`add(Component... component)`** : ajoute une ou plusieurs instances de `Component` au `TabbedPane`, créant un `Onglet` distinct pour chacune, avec le texte étant défini sur le nom du `Component`.

:::info
La méthode `add(Component... component)` détermine le nom du `Component` passé en appelant `component.getName()` sur l'argument passé.
:::

### Insertion d'un `Onglet` {#inserting-a-tab}

En plus d'ajouter un `Onglet` à la fin des onglets existants, il est également possible de créer un nouvel onglet à une position désignée. Pour ce faire, plusieurs versions surchargées de `insertTab()` existent.

1. **`insertTab(int index, String text)`** : insère un `Onglet` dans le `TabbedPane` à l'index donné avec le `String` spécifié comme texte de l'`Onglet`.
2. **`insertTab(int index, Tab tab)`** : insère l'`Onglet` fourni en paramètre au `TabbedPane` à l'index spécifié.
3. **`insertTab(int index, String text, Component component)`** : insère un `Onglet` avec le `String` donné comme texte de l'`Onglet`, et le `Component` fourni affiché dans la section de contenu du `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** : insère l'`Onglet` fourni et affiche le `Component` fourni dans la section de contenu du `TabbedPane`.

### Suppression d'un `Onglet` {#removing-a-tab}

Pour supprimer un seul `Onglet` du `TabbedPane`, utilisez l'une des méthodes suivantes :

1. **`removeTab(Tab tab)`** : supprime un `Onglet` du `TabbedPane` en passant l'instance de l'Onglet à supprimer.
2. **`removeTab(int index)`** : supprime un `Onglet` du `TabbedPane` en spécifiant l'index de l'`Onglet` à supprimer.

En plus des deux méthodes ci-dessus pour la suppression d'un `Onglet` unique, utilisez la méthode **`removeAllTabs()`** pour vider le `TabbedPane` de tous les onglets.

:::info
Les méthodes `remove()` et `removeAll()` ne suppriment pas les onglets au sein du composant.
:::

### Association Onglet/Composant {#tabcomponent-association}

Pour changer le `Component` à afficher pour un `Onglet` donné, appelez la méthode `setComponentFor()` et passez soit l'instance de l'`Onglet`, soit l'index de cet Onglet au sein du `TabbedPane`.

:::info
Si cette méthode est utilisée sur un `Onglet` déjà associé à un `Component`, le `Component` précédemment associé sera détruit.
:::

## Configuration et mise en page {#configuration-and-layout}

La classe `TabbedPane` a deux parties constitutives : un `Onglet` qui est affiché à un emplacement spécifié, et un composant à afficher. Cela peut être un seul composant, ou un composant [`Composite`](/docs/building-ui/composing-components), permettant l'affichage de composants plus complexes au sein de la section de contenu d'un onglet.

### Glissement {#swiping}

Le `TabbedPane` prend en charge la navigation à travers les différents onglets via glissement. Cela est idéal pour une application mobile, mais peut également être configuré via une méthode intégrée pour prendre en charge le glissement de la souris. Le glissement et le glissement de la souris sont désactivés par défaut, mais peuvent être activés avec les méthodes `setSwipeable(boolean)` et `setSwipeWithMouse(boolean)`, respectivement.

### Placement des onglets {#tab-placement}

Les `Onglets` dans un `TabbedPane` peuvent être placés à diverses positions au sein du composant en fonction des préférences des développeurs d'application. Les options fournies sont définies à l'aide de l'énumération fournie, qui a les valeurs `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, ou `HIDDEN`. Le paramètre par défaut est `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Alignement {#alignment}

En plus de changer le placement des éléments `Onglet` au sein du `TabbedPane`, il est également possible de configurer comment les onglets vont s'aligner au sein du composant. Par défaut, le paramètre `AUTO` est en vigueur, ce qui permet au placement des onglets de dicter leur alignement.

Les autres options sont `START`, `END`, `CENTER`, et `STRETCH`. Les trois premières décrivent la position par rapport au composant, tandis que `STRETCH` fait en sorte que les onglets remplissent l'espace disponible.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Bordure et indicateur d'activité {#border-and-activity-indicator}

Le `TabbedPane` aura par défaut une bordure affichée pour les onglets qu'il contient, placée en fonction de la `Placement` qui a été définie. Cette bordure aide à visualiser l'espace que prennent les différents onglets au sein du panneau.

Lorsqu'un `Onglet` est cliqué, par défaut, un indicateur d'activité est affiché près de cet `Onglet` pour aider à mettre en évidence lequel est l'`Onglet` actuellement sélectionné.

Ces deux options peuvent être personnalisées en modifiant les valeurs booléennes à l'aide des méthodes de setter appropriées. Pour changer si la bordure est affichée ou non, la méthode `setBorderless(boolean)` peut être utilisée, avec `true` cachant la bordure, et `false`, la valeur par défaut, affichant la bordure.

:::info
Cette bordure ne s'applique pas à l'intégralité du composant `TabbedPane`, et sert uniquement de séparateur entre les onglets et le contenu du composant.
:::

Pour définir la visibilité de l'indicateur actif, la méthode `setHideActiveIndicator(boolean)` peut être utilisée. Passer `true` à cette méthode cachera l'indicateur actif sous un `Onglet` actif, tandis que `false`, la valeur par défaut, maintiendra l'indicateur affiché.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Modes d'activation {#activation-modes}

Pour un contrôle plus précis sur le comportement du `TabbedPane` lors de la navigation au clavier, le mode `Activation` peut être défini pour spécifier comment le composant doit se comporter.

- **`Automatique`** : lorsqu'il est réglé sur automatique, naviguer dans les onglets avec les touches fléchées affichera immédiatement le composant d'onglet correspondant.

- **`Manuel`** : lorsqu'il est réglé sur manuel, l'onglet recevra le focus mais ne s'affichera pas tant que l'utilisateur n'a pas appuyé sur espace ou entrée.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Options de suppression {#removal-options}

Les éléments `Onglet` individuels peuvent être définis pour être fermables. Les onglets fermables auront un bouton de fermeture ajouté à l'onglet, ce qui déclenche un événement de fermeture lorsqu'il est cliqué. Le `TabbedPane` dicte comment ce comportement est géré.

- **`Manuel`** : par défaut, la suppression est définie sur `MANUAL`, ce qui signifie que l'événement est déclenché, mais il appartient au développeur de gérer cet événement de la manière qu'il souhaite.

- **`Automatique`** : alternativement, `AUTO` peut être utilisé, ce qui déclenchera l'événement, et supprimera également le `Onglet` du composant pour le développeur, éliminant ainsi le besoin pour le développeur d'implémenter ce comportement manuellement.

### Contrôle de segment <DocChip chip='since' label='26.00' /> {#segment-control}

Le `TabbedPane` peut être rendu comme un contrôle de segment en activant la propriété `segment` avec `setSegment(true)`. Dans ce mode, les onglets sont affichés avec un indicateur de pilule glissant qui met en évidence la sélection active, offrant une alternative compacte à l'interface d'onglets standard.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Style {#styling}

### Expansé et thème {#expanse-and-theme}

Le `TabbedPane` est livré avec des options d'`Expanse` et de `Thème` intégrées similaires à d'autres composants webforJ. Celles-ci peuvent être utilisées pour ajouter rapidement du style qui transmet diverses significations à l'utilisateur final sans avoir besoin de styliser le composant avec du CSS.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Meilleures pratiques {#best-practices}

Les pratiques suivantes sont recommandées pour l'utilisation du `TabbedPane` au sein des applications :

- **Groupement logique** : Utilisez des onglets pour regrouper logiquement le contenu connexe :
    - Chaque onglet devrait représenter une catégorie ou une fonctionnalité distincte au sein de votre application.
    - Regroupez les onglets similaires ou logiques à proximité les uns des autres.

- **Onglets limités** : Évitez de submerger les utilisateurs avec trop d'onglets. Envisagez d'utiliser une structure hiérarchique ou d'autres motifs de navigation si cela est applicable pour une interface propre.

- **Étiquettes claires** : Étiquetez clairement vos Onglets pour une utilisation intuitive :
    - Fournissez des étiquettes claires et concises pour chaque onglet.
    - Les étiquettes devraient refléter le contenu ou le but, facilitant la compréhension pour les utilisateurs.
    - Utilisez des icônes et des couleurs distinctes lorsque cela est applicable.

- **Navigation au clavier** : Utilisez le support de navigation au clavier de `TabbedPane` de webforJ pour rendre l'interaction avec le `TabbedPane` plus fluide et intuitive pour l'utilisateur final.

- **Onglet par défaut** : Si l'onglet par défaut n'est pas placé au début du `TabbedPane`, envisagez de définir cet onglet comme défaut pour une information essentielle ou couramment utilisée.
