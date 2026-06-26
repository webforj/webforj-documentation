---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: new-content
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons, keys, and customizable tab properties.
_i18n_hash: 9a348db865b5ea1688eb09c4f6f75a57
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Plusieurs sections de contenu peuvent être organisées sous un seul `TabbedPane`, où chaque section est liée à un `Tab` cliquable. Une seule section est visible à la fois, et les onglets peuvent afficher du texte, des icônes, ou les deux pour aider les utilisateurs à naviguer entre eux.

<!-- INTRO_END -->

## Usages {#usages}

La classe `TabbedPane` donne aux développeurs un outil puissant pour organiser et présenter plusieurs onglets ou sections au sein d'une interface utilisateur. Voici quelques scénarios typiques où vous pourriez utiliser un `TabbedPane` dans votre application :

1. **Visionneuse de documents** : Mise en œuvre d'une visionneuse de documents où chaque onglet représente un document ou un fichier différent. Les utilisateurs peuvent facilement passer d'un document ouvert à un autre pour un multitâche efficace.

2. **Gestion des données** : Utilisez un `TabbedPane` pour organiser les tâches de gestion des données, par exemple :
    >- Différents ensembles de données à afficher dans une application
    >- Divers profils d'utilisateurs peuvent être affichés dans des onglets séparés
    >- Différents profils dans un système de gestion des utilisateurs

3. **Sélection de module** : Un `TabbedPane` peut représenter différents modules ou sections. Chaque onglet peut encapsuler les fonctionnalités d'un module spécifique, permettant aux utilisateurs de se concentrer sur un aspect de l'application à la fois.

4. **Gestion des tâches** : Les applications de gestion des tâches peuvent utiliser un `TabbedPane` pour représenter divers projets ou tâches. Chaque onglet pourrait correspondre à un projet spécifique, permettant aux utilisateurs de gérer et de suivre les tâches séparément.

5. **Navigation dans le programme** : Dans une application qui doit exécuter divers programmes, un `TabbedPane` pourrait :
    >- Servir de barre latérale permettant d'exécuter différentes applications ou programmes au sein d'une seule application, comme ce qui est montré dans le modèle [`AppLayout`](./app-layout.md)
    >- Créer une barre supérieure qui peut servir un but similaire ou représenter des sous-applications au sein d'une application déjà sélectionnée.
  
## Tabs {#tabs}

Les onglets sont des éléments d'interface utilisateur qui peuvent être ajoutés à des panneaux d'onglets pour organiser et passer d'une vue de contenu à une autre.

:::important
Les onglets ne sont pas conçus pour être utilisés comme des composants autonomes. Ils doivent être utilisés en conjonction avec des panneaux d'onglets. Cette classe n'est pas un `Component` et ne doit pas être utilisée comme telle.
:::

### Propriétés {#properties}

Les onglets sont composés des propriétés suivantes, qui sont ensuite utilisées lors de leur ajout dans un `TabbedPane`. Ces propriétés disposent de méthodes d'accès et de modification pour faciliter la personnalisation au sein d'un `TabbedPane`.

1. **Key(`Object`)** : Représente l'identifiant unique pour le `Tab`.

2. **Texte(`String`)** : Le texte qui sera affiché comme un titre pour le `Tab` au sein du `TabbedPane`. Cela est également référé comme le titre via les méthodes `getTitle()` et `setTitle(String title)`.

3. **Tooltip(`String`)** : Le texte de l'infobulle qui est associé au `Tab`, qui sera affiché lorsque le curseur survole le `Tab`.

4. **Enabled(`boolean`)** : Représente si le `Tab` est actuellement activé ou non. Peut être modifié avec la méthode `setEnabled(boolean enabled)`.

5. **Closeable(`boolean`)** : Représente si le `Tab` peut être fermé. Peut être modifié avec la méthode `setCloseable(boolean enabled)`. Cela ajoutera un bouton de fermeture sur le `Tab` qui peut être cliqué par l'utilisateur, et génère un événement de suppression. Le composant `TabbedPane` dicte comment gérer la suppression.

6. **Slot(`Component`)** : 
    Les slots fournissent des options flexibles pour améliorer la capacité d'un `Tab`. Vous pouvez avoir des icônes, des étiquettes, des indicateurs de chargement, la possibilité de réinitialiser/effacer, des photos de profil/avatar, et d'autres composants bénéfiques imbriqués dans un `Tab` pour clarifier davantage le sens prévu aux utilisateurs.
    Vous pouvez ajouter un composant au slot `prefix` d'un `Tab` lors de la construction. Alternativement, vous pouvez utiliser les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après l'option affichée dans un `Tab`.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## Manipulation de `Tab` {#tab-manipulation}

Diverses méthodes existent pour permettre aux développeurs d'ajouter, d'insérer, de supprimer et de manipuler diverses propriétés des éléments `Tab` au sein du `TabbedPane`.

### Ajouter un `Tab` {#adding-a-tab}

Les méthodes `addTab()` et `add()` existent en différentes capacités surchargées pour permettre aux développeurs de flexibilité dans l'ajout de nouveaux onglets au `TabbedPane`. Ajouter un `Tab` le placera après tous les onglets déjà existants.

1. **`addTab(String text)`** - Ajoute un `Tab` au `TabbedPane` avec le `String` spécifié comme texte du `Tab`.
2. **`addTab(Tab tab)`** - Ajoute le `Tab` fourni en paramètre au `TabbedPane`.
3. **`addTab(String text, Component component)`** - Ajoute un `Tab` avec le `String` donné comme texte du `Tab`, et le `Component` fourni affiché dans la section de contenu du `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Ajoute le `Tab` fourni et affiche le `Component` fourni dans la section de contenu du `TabbedPane`.
5. **`add(Component... component)`** - Ajoute une ou plusieurs instances de `Component` au `TabbedPane`, créant un `Tab` distinct pour chacune, avec le texte étant défini sur le nom du `Component`.

:::info
La méthode `add(Component... component)` détermine le nom du `Component` passé en appelant `component.getName()` sur l'argument passé.
:::

### Insérer un `Tab` {#inserting-a-tab}

En plus d'ajouter un `Tab` à la fin des onglets existants, il est également possible d'en créer un nouveau à une position désignée. Pour ce faire, plusieurs versions surchargées de `insertTab()` existent. 

1. **`insertTab(int index, String text)`** - Insère un `Tab` dans le `TabbedPane` à l'index donné avec le `String` spécifié comme texte du `Tab`.
2. **`insertTab(int index, Tab tab)`** - Insère le `Tab` fourni en paramètre dans le `TabbedPane` à l'index spécifié.
3. **`insertTab(int index, String text, Component component)`** - Insère un `Tab` avec le `String` donné comme texte du `Tab`, et le `Component` fourni affiché dans la section de contenu du `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Insère le `Tab` fourni et affiche le `Component` fourni dans la section de contenu du `TabbedPane`.

### Supprimer un `Tab` {#removing-a-tab}

Pour supprimer un seul `Tab` du `TabbedPane`, utilisez l'une des méthodes suivantes :

1. **`removeTab(Tab tab)`** - Supprime un `Tab` du `TabbedPane` en passant l'instance de Tab à supprimer.
2. **`removeTab(int index)`** - Supprime un `Tab` du `TabbedPane` en spécifiant l'index du `Tab` à supprimer.

En plus des deux méthodes ci-dessus pour la suppression d'un seul `Tab`, utilisez la méthode **`removeAllTabs()`** pour vider le `TabbedPane` de tous les onglets.

:::info
Les méthodes `remove()` et `removeAll()` ne suppriment pas les onglets au sein du composant.
:::

### Association Tab/Component {#tabcomponent-association}

Pour changer le `Component` à afficher pour un `Tab` donné, appelez la méthode `setComponentFor()` et passez soit l'instance du `Tab`, soit l'index de ce `Tab` au sein du `TabbedPane`.

:::info
Si cette méthode est utilisée sur un `Tab` déjà associé à un `Component`, le `Component` précédemment associé sera détruit.
:::

## Configuration et mise en page {#configuration-and-layout}

La classe `TabbedPane` a deux parties constitutives : un `Tab` qui est affiché à un emplacement spécifié et un composant à afficher. Cela peut être un seul composant, ou un composant [`Composite`](/docs/building-ui/composing-components), permettant l'affichage de composants plus complexes dans la section de contenu d'un onglet.

### Glissement {#swiping}

Le `TabbedPane` prend en charge la navigation à travers les différents onglets via le glissement. Cela est idéal pour une application mobile, mais peut également être configuré via une méthode intégrée pour prendre en charge le glissement à la souris. Tant le glissement que le glissement à la souris sont désactivés par défaut, mais peuvent être activés avec les méthodes `setSwipable(boolean)` et `setSwipableWithMouse(boolean)` respectivement. 

### Placement des onglets {#tab-placement}

Les `Tabs` au sein d'un `TabbedPane` peuvent être placés à diverses positions au sein du composant en fonction de la préférence des développeurs d'application. Les options fournies sont réglées en utilisant l'énumération fournie, qui a les valeurs `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, ou `HIDDEN`. Le paramètre par défaut est `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Alignement {#alignment}

En plus de changer le placement des éléments `Tab` au sein du `TabbedPane`, il est également possible de configurer comment les onglets s'aligneront au sein du composant. Par défaut, le réglage `AUTO` est en vigueur, ce qui permet au placement des onglets de dicter leur alignement.

Les autres options sont `START`, `END`, `CENTER`, et `STRETCH`. Les trois premières décrivent la position par rapport au composant, tandis que `STRETCH` fait en sorte que les onglets occupent l'espace disponible.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Bordure et indicateur d'activité {#border-and-activity-indicator}

Le `TabbedPane` affichera par défaut une bordure pour les onglets à l'intérieur, placée en fonction de quel `Placement` a été défini. Cette bordure aide à visualiser l'espace occupé par les différents onglets dans le panneau.

Lorsque qu'un `Tab` est cliqué, un indicateur d'activité est affiché près de ce `Tab` pour aider à mettre en évidence lequel est l'onglet actuellement sélectionné.

Ces deux options peuvent être personnalisées par un développeur en changeant les valeurs booléennes à l'aide des méthodes de réglage appropriées. Pour changer si la bordure est affichée ou non, la méthode `setBorderless(boolean)` peut être utilisée, avec `true` masquant la bordure et `false`, la valeur par défaut, affichant la bordure.

:::info
Cette bordure ne s'applique pas à l'ensemble du composant `TabbedPane`, et sert simplement de séparateur entre les onglets et le contenu du composant.
:::

Pour définir la visibilité de l'indicateur actif, la méthode `setHideActiveIndicator(boolean)` peut être utilisée. Passer `true` à cette méthode masquera l'indicateur actif sous un `Tab` actif, tandis que `false`, la valeur par défaut, maintiendra l'indicateur affiché.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Modes d'activation {#activation-modes}

Pour un contrôle plus fin sur le comportement du `TabbedPane` lors de la navigation au clavier, le mode `Activation` peut être défini pour spécifier le comportement du composant.

- **`Auto`** : Lorsqu'il est réglé sur automatique, naviguer entre les onglets avec les touches fléchées affichera instantanément le composant d'onglet correspondant.

- **`Manual`** : Lorsqu'il est réglé sur manuel, l'onglet recevra le focus mais ne s'affichera pas tant que l'utilisateur n'aura pas appuyé sur espace ou entrée.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Options de suppression {#removal-options}

Les éléments `Tab` individuels peuvent être réglés pour être fermables. Les onglets fermables auront un bouton de fermeture ajouté à l'onglet, qui déclenche un événement de fermeture lorsqu'il est cliqué. Le `TabbedPane` dicte comment ce comportement est géré.

- **`Manual`** : Par défaut, la suppression est réglée sur `MANUAL`, ce qui signifie que l'événement est déclenché, mais il appartient au développeur de gérer cet événement de la manière de son choix.

- **`Auto`** : Alternativement, `AUTO` peut être utilisé, ce qui déclenchera l'événement et supprimera également le `Tab` du composant pour le développeur, éliminant ainsi la nécessité pour le développeur de mettre en œuvre ce comportement manuellement. 

### Contrôle de segment <DocChip chip='since' label='26.00' /> {#segment-control}

Le `TabbedPane` peut être rendu comme un contrôle de segment en activant la propriété `segment` avec `setSegment(true)`. Dans ce mode, les onglets sont affichés avec un indicateur de pilule glissante qui met en évidence la sélection active, fournissant une alternative compacte à l'interface standard à onglets. 

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Stylisation {#styling}

### Expanse et thème {#expanse-and-theme}

Le `TabbedPane` est livré avec des options `Expanse` et `Theme` intégrées similaires à d'autres composants webforJ. Celles-ci peuvent être utilisées pour ajouter rapidement du style qui transmet diverses significations à l'utilisateur final sans avoir besoin de styliser le composant avec CSS.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Meilleures pratiques {#best-practices}

Les pratiques suivantes sont recommandées pour l'utilisation du `TabbedPane` dans les applications :

- **Grouper logiquement** : Utilisez des onglets pour grouper logiquement le contenu associé
    >- Chaque onglet doit représenter une catégorie ou fonctionnalité distincte au sein de votre application
    >- Groupez les onglets similaires ou logiques près les uns des autres

- **Onglets limités** : Évitez de submerger les utilisateurs avec trop d'onglets. Envisagez d'utiliser une structure hiérarchique ou d'autres modèles de navigation lorsque cela est applicable pour une interface claire.

- **Étiquettes claires** : Étiquetez clairement vos Tabs pour une utilisation intuitive
    >- Fournir des étiquettes claires et concises pour chaque onglet
    >- Les étiquettes doivent refléter le contenu ou le but, facilitant ainsi la compréhension pour les utilisateurs
    >- Utilisez des icônes et des couleurs distinctes si nécessaire

- **Navigation au clavier** : Utilisez la prise en charge de la navigation au clavier de webforJ `TabbedPane` pour rendre l'interaction avec le `TabbedPane` plus fluide et intuitive pour l'utilisateur final.

- **Onglet par défaut** : Si l'onglet par défaut n'est pas placé au début du `TabbedPane`, envisagez de définir cet onglet comme par défaut pour des informations essentielles ou couramment utilisées.
