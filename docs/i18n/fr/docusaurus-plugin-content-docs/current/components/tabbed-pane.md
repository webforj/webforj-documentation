---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: new-content
_i18n_hash: 0b623c02434c6f0d140de0ade3a22c5d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Plusieurs sections de contenu peuvent être organisées sous un seul `TabbedPane`, où chaque section est liée à un `Tab` cliquable. Une seule section est visible à la fois, et les onglets peuvent afficher du texte, des icônes ou les deux pour aider les utilisateurs à naviguer entre eux.

<!-- INTRO_END -->

## Usages {#usages}

La classe `TabbedPane` offre aux développeurs un outil puissant pour organiser et présenter plusieurs onglets ou sections dans une interface utilisateur. Voici quelques scénarios typiques où vous pourriez utiliser un `TabbedPane` dans votre application :

1. **Visionneuse de documents** : Implémentation d'une visionneuse de documents où chaque onglet représente un document ou un fichier différent. Les utilisateurs peuvent facilement passer d'un document ouvert à l'autre pour une gestion efficace des tâches.

2. **Gestion des données** : Utilisez un `TabbedPane` pour organiser les tâches de gestion des données, par exemple :
    >- Différents ensembles de données à afficher dans une application
    >- Divers profils d'utilisateur peuvent être affichés dans des onglets séparés
    >- Différents profils dans un système de gestion des utilisateurs

3. **Sélection de module** : Un `TabbedPane` peut représenter différents modules ou sections. Chaque onglet peut encapsuler les fonctionnalités d'un module spécifique, permettant aux utilisateurs de se concentrer sur un aspect de l'application à la fois.

4. **Gestion de tâches** : Les applications de gestion de tâches peuvent utiliser un `TabbedPane` pour représenter divers projets ou tâches. Chaque onglet pourrait correspondre à un projet spécifique, permettant aux utilisateurs de gérer et de suivre les tâches séparément.

5. **Navigation dans le programme** : Dans une application qui doit exécuter divers programmes, un `TabbedPane` pourrait :
    >- Servir de barre latérale permettant différents programmes ou applications d'être exécutés dans une seule application, comme ce qui est montré dans le modèle [`AppLayout`](./app-layout.md)
    >- Créer une barre supérieure qui peut servir un objectif similaire, ou représenter des sous-applications dans une application déjà sélectionnée.

## Onglets {#tabs}

Les onglets sont des éléments d'interface utilisateur qui peuvent être ajoutés aux panneaux à onglets pour organiser et basculer entre différentes vues de contenu.

:::important
Les onglets ne sont pas destinés à être utilisés comme des composants autonomes. Ils doivent être utilisés en conjonction avec des panneaux à onglets. Cette classe n'est pas un `Component` et ne doit pas être utilisée comme telle.
:::

### Propriétés {#properties}

Les onglets sont composés des propriétés suivantes, qui sont ensuite utilisées lors de leur ajout dans un `TabbedPane`. Ces propriétés ont des accesseurs et des mutateurs pour faciliter la personnalisation dans un `TabbedPane`.

1. **Clé(`Object`)** : Représente l'identifiant unique pour le `Tab`.

2. **Texte(`String`)** : Le texte qui sera affiché comme titre pour le `Tab` dans le `TabbedPane`. Cela est également appelé titre via les méthodes `getTitle()` et `setTitle(String title)`.

3. **Info-bulle(`String`)** : Le texte de l'info-bulle associé au `Tab`, qui sera affiché lorsque le curseur survole le `Tab`.

4. **Activé(`boolean`)** : Représente si le `Tab` est actuellement activé ou non. Peut être modifié avec la méthode `setEnabled(boolean enabled)`.

5. **Fermable(`boolean`)** : Représente si le `Tab` peut être fermé. Peut être modifié avec la méthode `setCloseable(boolean enabled)`. Cela ajoutera un bouton de fermeture sur le `Tab` qui pourra être cliqué par l'utilisateur, et déclenche un événement de suppression. Le composant `TabbedPane` dicte comment gérer la suppression.

6. **Slot(`Component`)** : 
    Les slots offrent des options flexibles pour améliorer la capacité d'un `Tab`. Vous pouvez avoir des icônes, des étiquettes, des indicateurs de chargement, une capacité de réinitialisation/clarté, des photos de profil/avatar, et d'autres composants bénéfiques imbriqués dans un `Tab` pour clarifier davantage le sens voulu pour les utilisateurs.
    Vous pouvez ajouter un composant au slot `prefix` d'un `Tab` lors de la construction. Alternativement, vous pouvez utiliser les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après l'option affichée dans un `Tab`.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## Manipulation de `Tab` {#tab-manipulation}

Diverses méthodes existent pour permettre aux développeurs d'ajouter, d'insérer, de supprimer et de manipuler diverses propriétés des éléments `Tab` dans le `TabbedPane`.

### Ajout d'un `Tab` {#adding-a-tab}

Les méthodes `addTab()` et `add()` existent dans différentes capacités surchargeées pour permettre aux développeurs de flexibilité pour ajouter de nouveaux onglets au `TabbedPane`. L'ajout d'un `Tab` le placera après tout onglet existant précédemment.

1. **`addTab(String text)`** - Ajoute un `Tab` au `TabbedPane` avec le `String` spécifié comme texte du `Tab`.
2. **`addTab(Tab tab)`** - Ajoute le `Tab` fourni en paramètre au `TabbedPane`.
3. **`addTab(String text, Component component)`** - Ajoute un `Tab` avec le `String` donné comme texte du `Tab`, et le `Component` fourni affiché dans la section de contenu du `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Ajoute le `Tab` fourni et affiche le `Component` fourni dans la section de contenu du `TabbedPane`.
5. **`add(Component... component)`** - Ajoute une ou plusieurs instances de `Component` au `TabbedPane`, créant un `Tab` distinct pour chacune, avec le texte défini sur le nom du `Component`.

:::info
Le `add(Component... component)` détermine le nom du `Component` passé en appelant `component.getName()` sur l'argument passé.
:::

### Insertion d'un `Tab` {#inserting-a-tab}

En plus d'ajouter un `Tab` à la fin des onglets existants, il est également possible de créer un nouveau onglet à une position désignée. Pour cela, plusieurs versions surchargées de `insertTab()` existent.

1. **`insertTab(int index, String text)`** - Insère un `Tab` dans le `TabbedPane` à l'index donné avec le `String` spécifié comme texte du `Tab`.
2. **`insertTab(int index, Tab tab)`** - Insère le `Tab` fourni en tant que paramètre au `TabbedPane` à l'index spécifié.
3. **`insertTab(int index, String text, Component component)`** - Insère un `Tab` avec le `String` donné comme texte du `Tab`, et le `Component` fourni affiché dans la section de contenu du `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Insère le `Tab` fourni et affiche le `Component` fourni dans la section de contenu du `TabbedPane`.

### Suppression d'un `Tab` {#removing-a-tab}

Pour supprimer un seul `Tab` du `TabbedPane`, utilisez l'une des méthodes suivantes :

1. **`removeTab(Tab tab)`** - Supprime un `Tab` du `TabbedPane` en passant l'instance du `Tab` à supprimer.
2. **`removeTab(int index)`** - Supprime un `Tab` du `TabbedPane` en spécifiant l'index du `Tab` à supprimer.

En plus des deux méthodes ci-dessus pour la suppression d'un seul `Tab`, utilisez la méthode **`removeAllTabs()`** pour vider le `TabbedPane` de tous les onglets.

:::info
Les méthodes `remove()` et `removeAll()` ne suppriment pas les onglets à l'intérieur du composant.
:::

### Association `Tab`/`Component` {#tabcomponent-association}

Pour changer le `Component` à afficher pour un `Tab` donné, appelez la méthode `setComponentFor()` et passez soit l'instance du `Tab`, soit l'index de ce Tab dans le `TabbedPane`.

:::info
Si cette méthode est utilisée sur un `Tab` qui est déjà associé à un `Component`, le `Component` précédemment associé sera détruit.
:::

## Configuration et mise en page {#configuration-and-layout}

La classe `TabbedPane` a deux parties constitutives : un `Tab` qui est affiché à un endroit spécifié, et un composant à afficher. Cela peut être un seul composant, ou un composant [`Composite`](../building-ui/composing-components), permettant d'afficher des composants plus complexes dans la section de contenu d'un onglet.

### Glissement {#swiping}

Le `TabbedPane` prend en charge la navigation à travers les différents onglets via le glissement. Cela est idéal pour une application mobile, mais peut également être configuré via une méthode intégrée pour prendre en charge le glissement à la souris. Le glissement et le glissement à la souris sont désactivés par défaut, mais peuvent être activés avec les méthodes `setSwipable(boolean)` et `setSwipableWithMouse(boolean)`, respectivement.

### Placement des onglets {#tab-placement}

Les `Tabs` dans un `TabbedPane` peuvent être placés dans diverses positions au sein du composant en fonction des préférences des développeurs d'application. Les options fournies sont définies à l'aide de l'énumération fournie, qui a les valeurs de `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, ou `HIDDEN`. Le paramètre par défaut est `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Alignement {#alignment}

En plus de changer le placement des éléments `Tab` dans le `TabbedPane`, il est également possible de configurer comment les onglets s'aligneront dans le composant. Par défaut, le paramètre `AUTO` est en vigueur, ce qui permet au placement des onglets de dicter leur alignement.

Les autres options sont `START`, `END`, `CENTER`, et `STRETCH`. Les trois premières décrivent la position par rapport au composant, avec `STRETCH` faisant en sorte que les onglets occupent tout l'espace disponible.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Bordure et indicateur d'activité {#border-and-activity-indicator}

Le `TabbedPane` aura une bordure affichée pour les onglets qui s'y trouvent par défaut, placée en fonction du `Placement` qui a été défini. Cette bordure aide à visualiser l'espace que prennent les différents onglets dans le panneau.

Lorsqu'un `Tab` est cliqué, par défaut, un indicateur d'activité est affiché près de ce `Tab` pour aider à mettre en évidence lequel est le `Tab` actuellement sélectionné.

Ces deux options peuvent être personnalisées par un développeur en modifiant les valeurs booléennes à l'aide des méthodes de définisseur appropriées. Pour changer si la bordure est affichée ou non, la méthode `setBorderless(boolean)` peut être utilisée, avec `true` cachant la bordure, et `false`, la valeur par défaut, affichant la bordure.

:::info
Cette bordure ne s'applique pas à l'ensemble du composant `TabbedPane`, et sert simplement de séparateur entre les onglets et le contenu du composant.
:::

Pour définir la visibilité de l'indicateur actif, la méthode `setHideActiveIndicator(boolean)` peut être utilisée. Passer `true` à cette méthode cachera l'indicateur actif sous un `Tab` actif, tandis que `false`, la valeur par défaut, maintiendra l'indicateur affiché.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Modes d'activation {#activation-modes}

Pour un contrôle plus précis de la façon dont le `TabbedPane` se comporte lors de la navigation au clavier, le mode d'activation peut être défini pour spécifier comment le composant doit se comporter.

- **`Auto`** : Lorsqu'il est réglé sur automatique, naviguer dans les onglets avec les flèches affiche instantanément le composant d'onglet correspondant.

- **`Manual`** : Lorsqu'il est réglé sur manuel, l'onglet recevra le focus mais ne s'affichera pas tant que l'utilisateur n'appuie pas sur espace ou entrée.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Options de suppression {#removal-options}

Des éléments `Tab` individuels peuvent être définis comme étant fermables. Les onglets fermables auront un bouton de fermeture ajouté à l'onglet, qui déclenche un événement de fermeture lorsqu'il est cliqué. Le `TabbedPane` dicte comment ce comportement est géré.

- **`Manual`** : Par défaut, la suppression est définie sur `MANUAL`, ce qui signifie que l'événement est déclenché, mais il appartient au développeur de gérer cet événement de la manière qu'il choisit.

- **`Auto`** : Alternativement, `AUTO` peut être utilisé, ce qui déclenchera l'événement et retirera également le `Tab` du composant pour le développeur, éliminant ainsi le besoin pour le développeur de mettre en œuvre ce comportement manuellement. 

### Contrôle de segment <DocChip chip='since' label='26.00' /> {#segment-control}

Le `TabbedPane` peut être rendu comme un contrôle de segment en activant la propriété `segment` avec `setSegment(true)`. Dans ce mode, les onglets sont affichés avec un indicateur de pilule glissant qui met en évidence la sélection active, offrant une alternative compacte à l'interface à onglets standard.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Style {#styling}

### Expanses et thème {#expanse-and-theme}

Le `TabbedPane` est livré avec des options `Expanse` et `Thème` intégrées similaires à d'autres composants webforJ. Celles-ci peuvent être utilisées pour ajouter rapidement un style qui transmet diverses significations à l'utilisateur final sans avoir besoin de styliser le composant avec des CSS.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Meilleures pratiques {#best-practices}

Les pratiques suivantes sont recommandées pour utiliser le `TabbedPane` dans les applications :

- **Groupement logique** : Utilisez des onglets pour regrouper logiquement du contenu connexe
    >- Chaque onglet doit représenter une catégorie ou une fonctionnalité distincte dans votre application
    >- Regroupez des onglets similaires ou logiques à proximité les uns des autres

- **Onglets limités** : Évitez de submerger les utilisateurs avec trop d'onglets. Envisagez d'utiliser une structure hiérarchique ou d'autres modèles de navigation si applicable pour une interface propre

- **Étiquettes claires** : Étiquetez clairement vos onglets pour une utilisation intuitive
    >- Fournissez des étiquettes claires et concises pour chaque onglet
    >- Les étiquettes doivent refléter le contenu ou l'objectif, facilitant ainsi la compréhension pour les utilisateurs
    >- Utilisez des icônes et des couleurs distinctes si nécessaire

- **Navigation au clavier** : Utilisez le support de navigation au clavier de webforJ `TabbedPane` pour rendre l'interaction avec le `TabbedPane` plus fluide et intuitive pour l'utilisateur final

- **Onglet par défaut** : Si l'onglet par défaut n'est pas placé au début du `TabbedPane`, envisagez de définir cet onglet comme par défaut pour des informations essentielles ou couramment utilisées.
