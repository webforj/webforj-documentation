---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons, keys, and customizable tab properties.
_i18n_hash: 563f9251b928e2e9426d69d4b5192880
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Multiple sections of content can be organized under a single `TabbedPane`, where each section is tied to a clickable `Tab`. Only one section is visible at a time, and tabs can display text, icons, or both to help users navigate between them.

<!-- INTRO_END -->

## Usages {#usages}

The `TabbedPane` class is a powerful tool for developers to organize and present multiple tabs or sections within a UI. Here are some typical scenarios where you might utilize a `TabbedPane` in your application:

1. **Visualiseur de documents**: Mise en œuvre d'un visualiseur de documents où chaque onglet représente un document ou un fichier différent. Les utilisateurs peuvent facilement passer d'un document ouvert à un autre pour un multitâche efficace.

2. **Gestion des données**: Utilisez un `TabbedPane` pour organiser les tâches de gestion des données, par exemple :
    >- Différents ensembles de données à afficher dans une application
    >- Divers profils d'utilisateurs peuvent être affichés dans des onglets séparés
    >- Différents profils dans un système de gestion des utilisateurs

3. **Sélection de module**: Un `TabbedPane` peut représenter différents modules ou sections. Chaque onglet peut encapsuler les fonctionnalités d'un module spécifique, permettant aux utilisateurs de se concentrer sur un aspect de l'application à la fois.

4. **Gestion des tâches**: Les applications de gestion des tâches peuvent utiliser un `TabbedPane` pour représenter divers projets ou tâches. Chaque onglet pourrait correspondre à un projet spécifique, permettant aux utilisateurs de gérer et de suivre les tâches séparément.

5. **Navigation dans le programme**: Dans une application qui doit exécuter divers programmes, un `TabbedPane` pourrait :
    >- Servir de barre latérale permettant d'exécuter différentes applications ou programmes dans une seule application, comme ce qui est montré dans le modèle [`AppLayout`](./app-layout.md)
    >- Créer une barre supérieure qui peut servir un objectif similaire, ou représenter des sous-applications au sein d'une application déjà sélectionnée.

## Tabs {#tabs}

Les onglets sont des éléments UI qui peuvent être ajoutés aux panneaux d'onglets pour organiser et passer d'une vue de contenu à une autre.

:::important
Les onglets ne sont pas destinés à être utilisés comme composants autonomes. Ils sont destinés à être utilisés en conjonction avec des panneaux d'onglets. Cette classe n'est pas un `Component` et ne doit pas être utilisée comme tel.
:::

### Propriétés {#properties}

Les onglets sont composés des propriétés suivantes, qui sont ensuite utilisées lors de leur ajout à un `TabbedPane`. Ces propriétés ont des accesseurs et des mutateurs pour faciliter la personnalisation dans un `TabbedPane`.

1. **Key(`Object`)**: Représente l'identifiant unique de l'onglet `Tab`.

2. **Text(`String`)**: Le texte qui sera affiché comme titre de l'onglet dans le `TabbedPane`. Cela est également appelé le titre via les méthodes `getTitle()` et `setTitle(String title)`.

3. **Tooltip(`String`)**: Le texte d'info-bulle associé à l'onglet `Tab`, qui sera affiché lorsque le curseur survole l'onglet.

4. **Enabled(`boolean`)**: Représente si l'onglet `Tab` est actuellement activé ou non. Peut être modifié avec la méthode `setEnabled(boolean enabled)`.

5. **Closeable(`boolean`)**: Représente si l'onglet `Tab` peut être fermé. Peut être modifié avec la méthode `setCloseable(boolean enabled)`. Cela ajoutera un bouton de fermeture sur l'onglet qui peut être cliqué par l'utilisateur et déclenche un événement de suppression. Le composant `TabbedPane` dicte comment gérer la suppression.

6. **Slot(`Component`)**:
    Les slots offrent des options flexibles pour améliorer la capacité d'un onglet `Tab`. Vous pouvez avoir des icônes, des étiquettes, des indicateurs de chargement, une capacité de réinitialisation, des images de profil/avatar et d'autres composants bénéfiques imbriqués dans un onglet pour clarifier davantage le sens prévu aux utilisateurs.
    Vous pouvez ajouter un composant au slot `prefix` d'un onglet pendant la construction. Alternativement, vous pouvez utiliser les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après l'option affichée dans un onglet.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab` manipulation {#tab-manipulation}

Diverses méthodes existent pour permettre aux développeurs d'ajouter, d'insérer, de supprimer et de manipuler diverses propriétés des éléments d'onglets `Tab` dans le `TabbedPane`.

### Ajout d'un `Tab` {#adding-a-tab}

Les méthodes `addTab()` et `add()` existent en différentes capacités surchargées pour permettre aux développeurs une flexibilité lors de l'ajout de nouveaux onglets au `TabbedPane`. L'ajout d'un `Tab` le placera après les onglets déjà existants.

1. **`addTab(String text)`** - Ajoute un onglet `Tab` au `TabbedPane` avec le `String` spécifié comme texte de l’onglet.
2. **`addTab(Tab tab)`** - Ajoute l'onglet `Tab` fourni en paramètre au `TabbedPane`.
3. **`addTab(String text, Component component)`** - Ajoute un onglet `Tab` avec le `String` donné comme texte de l'onglet, et le `Component` fourni affiché dans la section de contenu du `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Ajoute l'onglet `Tab` fourni et affiche le `Component` fourni dans la section de contenu du `TabbedPane`.
5. **`add(Component... component)`** - Ajoute une ou plusieurs instances de `Component` au `TabbedPane`, créant un onglet distinct pour chacune, le texte étant défini par le nom du `Component`.

:::info
La méthode `add(Component... component)` détermine le nom du `Component` passé en appelant `component.getName()` sur l'argument passé.
:::

### Insertion d'un `Tab` {#inserting-a-tab}

En plus d'ajouter un `Tab` à la fin des onglets existants, il est également possible de créer un nouveau à une position désignée. Pour ce faire, plusieurs versions surchargées de `insertTab()` existent.

1. **`insertTab(int index, String text)`** - Insère un onglet `Tab` dans le `TabbedPane` à l'index donné avec le `String` spécifié comme texte de l’onglet.
2. **`insertTab(int index, Tab tab)`** - Insère l'onglet `Tab` fourni en tant que paramètre à le `TabbedPane` à l'index spécifié.
3. **`insertTab(int index, String text, Component component)`** - Insère un onglet `Tab` avec le `String` donné comme texte de l'onglet, et le `Component` fourni affiché dans la section de contenu du `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Insère l'onglet `Tab` fourni et affiche le `Component` fourni dans la section de contenu du `TabbedPane`.

### Suppression d'un `Tab` {#removing-a-tab}

Pour supprimer un seul onglet `Tab` du `TabbedPane`, utilisez l'une des méthodes suivantes :

1. **`removeTab(Tab tab)`** - Supprime un onglet `Tab` du `TabbedPane` en passant l'instance Tab à supprimer.
2. **`removeTab(int index)`** - Supprime un onglet `Tab` du `TabbedPane` en spécifiant l'index de l'onglet à supprimer.

En plus des deux méthodes ci-dessus pour la suppression d'un seul onglet `Tab`, utilisez la méthode **`removeAllTabs()`** pour vider le `TabbedPane` de tous ses onglets.

:::info
Les méthodes `remove()` et `removeAll()` ne suppriment pas les onglets à l'intérieur du composant.
:::

### Association Tab/Component {#tabcomponent-association}

Pour changer le `Component` à afficher pour un onglet donné, appelez la méthode `setComponentFor()`, et passez soit l'instance de l'onglet `Tab`, soit l'index de cet onglet dans le `TabbedPane`.

:::info
Si cette méthode est utilisée sur un `Tab` qui est déjà associé à un `Component`, le `Component` précédemment associé sera détruit.
:::

## Configuration et mise en page {#configuration-and-layout}

La classe `TabbedPane` a deux parties constitutives : un onglet `Tab` qui est affiché à un emplacement spécifié, et un composant à afficher. Cela peut être un composant unique, ou un composant [`Composite`](/docs/building-ui/composing-components), permettant l'affichage de composants plus complexes dans la section de contenu d'un onglet.

### Glissement {#swiping}

Le `TabbedPane` prend en charge la navigation à travers les onglets via le glissement. Cela est idéal pour une application mobile, mais peut également être configuré via une méthode intégrée pour prendre en charge le glissement de souris. Le glissement et le glissement de souris sont désactivés par défaut, mais peuvent être activés avec les méthodes `setSwipable(boolean)` et `setSwipableWithMouse(boolean)` respectivement.

### Placement des onglets {#tab-placement}

Les onglets dans un `TabbedPane` peuvent être placés à différentes positions dans le composant en fonction de la préférence des développeurs d'application. Les options fournies sont définies à l'aide de l'énumération fournie, qui a les valeurs `TOP`, `BOTTOM`, `LEFT`, `RIGHT` ou `HIDDEN`. Le paramètre par défaut est `TOP`.


<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Alignement {#alignment}

En plus de changer le placement des éléments d'onglets dans le `TabbedPane`, il est également possible de configurer comment les onglets s'aligneront dans le composant. Par défaut, le paramètre `AUTO` est en vigueur, ce qui permet au placement des onglets de dicter leur alignement.

Les autres options sont `START`, `END`, `CENTER` et `STRETCH`. Les trois premières décrivent la position par rapport au composant, avec `STRETCH` faisant en sorte que les onglets remplissent l'espace disponible.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Bordure et indicateur d'activité {#border-and-activity-indicator}

Le `TabbedPane` affichera par défaut une bordure pour les onglets qu'il contient, placée en fonction de la `Placement` qui a été définie. Cette bordure aide à visualiser l'espace occupé par les différents onglets dans le panneau.

Lorsqu'un onglet est cliqué, par défaut, un indicateur d'activité est affiché près de cet onglet pour aider à mettre en évidence quel est l'onglet actuellement sélectionné.

Ces deux options peuvent être personnalisées par un développeur en changeant les valeurs booléennes à l'aide des méthodes setter appropriées. Pour changer si la bordure est affichée ou non, la méthode `setBorderless(boolean)` peut être utilisée, avec `true` cachant la bordure et `false`, la valeur par défaut, affichant la bordure.

:::info
Cette bordure ne s'applique pas à l'ensemble du composant `TabbedPane`, et ne sert que de séparateur entre les onglets et le contenu du composant.
:::

Pour définir la visibilité de l'indicateur actif, la méthode `setHideActiveIndicator(boolean)` peut être utilisée. Passer `true` à cette méthode cachera l'indicateur actif sous un onglet actif, tandis que `false`, la valeur par défaut, gardera l'indicateur affiché.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Modes d'activation {#activation-modes}

Pour un contrôle plus fin sur la façon dont le `TabbedPane` se comporte lorsqu'il est navigué au clavier, le mode `Activation` peut être défini pour spécifier comment le composant doit réagir.

- **`Auto`**: Lorsqu'il est réglé sur automatique, la navigation des onglets avec les touches directionnelles montrera instantanément le composant d'onglet correspondant.

- **`Manual`**: Lorsqu'il est réglé sur manuel, l'onglet recevra le focus mais ne s'affichera pas tant que l'utilisateur n'aura pas appuyé sur la barre d'espace ou entrée.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Options de suppression {#removal-options}

Des éléments d'onglet individuels peuvent être définis comme fermables. Les onglets fermables auront un bouton de fermeture ajouté, qui déclenche un événement de fermeture lorsqu'il est cliqué. Le `TabbedPane` dicte la façon dont ce comportement est géré.

- **`Manual`**: Par défaut, la suppression est définie sur `MANUAL`, ce qui signifie que l'événement est déclenché, mais il appartient au développeur de gérer cet événement comme il le souhaite.

- **`Auto`**: Alternativement, `AUTO` peut être utilisé, ce qui déclenchera l'événement et supprimera également l'onglet du composant pour le développeur, supprimant ainsi la nécessité pour le développeur de mettre en œuvre ce comportement manuellement.

### Contrôle de segment <DocChip chip='since' label='26.00' /> {#segment-control}

Le `TabbedPane` peut être rendu comme un contrôle de segment en activant la propriété `segment` avec `setSegment(true)`. Dans ce mode, les onglets sont affichés avec un indicateur de pilule glissant qui met en évidence la sélection active, offrant une alternative compacte à l'interface d'onglets standard.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Styling {#styling}

### Expanse et thème {#expanse-and-theme}

Le `TabbedPane` est livré avec des options `Expanse` et `Theme` intégrées similaires à d'autres composants webforJ. Celles-ci peuvent être utilisées pour ajouter rapidement du style qui transmet diverses significations à l'utilisateur final sans avoir besoin de styliser le composant avec CSS.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Meilleures pratiques {#best-practices}

Les pratiques suivantes sont recommandées pour utiliser le `TabbedPane` dans des applications :

- **Regroupement logique**: Utilisez les onglets pour regrouper logiquement le contenu connexe
    >- Chaque onglet doit représenter une catégorie ou une fonctionnalité distincte dans votre application
    >- Regroupez des onglets similaires ou logiques à proximité les uns des autres

- **Onglets limités**: Évitez de submerger les utilisateurs avec trop d'onglets. Envisagez d'utiliser une structure hiérarchique ou d'autres modèles de navigation lorsque cela est applicable pour une interface propre

- **Étiquettes claires**: Étiquetez clairement vos onglets pour une utilisation intuitive
    >- Fournissez des étiquettes claires et concises pour chaque onglet
    >- Les étiquettes doivent refléter le contenu ou l'objectif, ce qui facilite la compréhension pour les utilisateurs
    >- Utilisez des icônes et des couleurs distinctes lorsque cela est applicable

- **Navigation au clavier**: Utilisez le support de navigation au clavier du `TabbedPane` de webforJ pour rendre l'interaction avec le `TabbedPane` plus transparente et intuitive pour l'utilisateur final

- **Onglet par défaut**: Si l'onglet par défaut n'est pas placé au début du `TabbedPane`, envisagez de définir cet onglet comme par défaut pour des informations essentielles ou souvent utilisées.
