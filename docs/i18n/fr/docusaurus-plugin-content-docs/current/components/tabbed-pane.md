---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: ebf6bff550fd69aeb6ab8e4dfefd2323
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

La classe `TabbedPane` fournit une manière compacte et organisée d'afficher du contenu divisé en plusieurs sections, chacune associée à un `Tab`. Les utilisateurs peuvent passer d'une section à l'autre en cliquant sur les onglets respectifs, souvent étiquetés avec du texte et/ou des icônes. Cette classe simplifie la création d'interfaces multifacettes où différents contenus ou formulaires doivent être accessibles mais pas visibles simultanément.

## Usages {#usages}

La classe `TabbedPane` offre aux développeurs un outil puissant pour organiser et présenter plusieurs onglets ou sections au sein d'une interface utilisateur. Voici quelques scénarios typiques où vous pourriez utiliser un `TabbedPane` dans votre application :

1. **Visionneuse de documents** : Mise en œuvre d'une visionneuse de documents où chaque onglet représente un document ou un fichier différent. Les utilisateurs peuvent facilement passer d'un document ouvert à un autre pour un multitâche efficace.

2. **Gestion de données** : Utilisez un `TabbedPane` pour organiser les tâches de gestion des données, par exemple :
    >- Différents jeux de données à afficher dans une application
    >- Divers profils d'utilisateurs peuvent être affichés dans des onglets séparés
    >- Différents profils dans un système de gestion des utilisateurs

3. **Sélection de modules** : Un `TabbedPane` peut représenter différents modules ou sections. Chaque onglet peut encapsuler les fonctionnalités d'un module spécifique, permettant aux utilisateurs de se concentrer sur un aspect de l'application à la fois.

4. **Gestion des tâches** : Les applications de gestion des tâches peuvent utiliser un `TabbedPane` pour représenter divers projets ou tâches. Chaque onglet pourrait correspondre à un projet spécifique, permettant aux utilisateurs de gérer et de suivre les tâches séparément.

5. **Navigation dans le programme** : Au sein d'une application qui doit exécuter divers programmes, un `TabbedPane` pourrait :
    >- Servir de barre latérale permettant d'exécuter différentes applications ou programmes au sein d'une seule application, comme le montre le modèle [`AppLayout`](./app-layout.md)
    >- Créer une barre supérieure qui peut servir un but similaire, ou représenter des sous-applications au sein d'une application déjà sélectionnée.

## Onglets {#tabs}

Les onglets sont des éléments d'interface utilisateur qui peuvent être ajoutés aux panneaux à onglets pour organiser et basculer entre différentes vues de contenu.

:::important
Les onglets ne sont pas destinés à être utilisés comme des composants autonomes. Ils doivent être utilisés en conjonction avec des panneaux à onglets. Cette classe n'est pas un `Component` et ne doit pas être utilisée comme telle.
:::

### Propriétés {#properties}

Les onglets se composent des propriétés suivantes, qui sont ensuite utilisées lors de leur ajout dans un `TabbedPane`. Ces propriétés disposent de getters et de setters pour faciliter la personnalisation au sein d'un `TabbedPane`.

1. **Clé (`Object`)** : Représente l'identifiant unique pour l'`Onglet`.

2. **Texte (`String`)** : Le texte qui sera affiché comme titre de l'`Onglet` au sein du `TabbedPane`. Cela fait également référence au titre via les méthodes `getTitle()` et `setTitle(String title)`.

3. **Info-bulle (`String`)** : Le texte d'info-bulle qui est associé à l'`Onglet`, qui sera affiché lorsque le curseur survole l'`Onglet`.

4. **Activé (`boolean`)** : Représente si l'`Onglet` est actuellement activé ou non. Peut être modifié avec la méthode `setEnabled(boolean enabled)`.

5. **Fermable (`boolean`)** : Représente si l'`Onglet` peut être fermé. Peut être modifié avec la méthode `setCloseable(boolean enabled)`. Cela ajoutera un bouton de fermeture sur l'`Onglet` qui peut être cliqué par l'utilisateur et déclenche un événement de suppression. Le composant `TabbedPane` dicte comment gérer la suppression.

6. **Slot (`Component`)** : 
    Les slots offrent des options flexibles pour améliorer la capacité d'un `Onglet`. Vous pouvez avoir des icônes, des étiquettes, des indicateurs de chargement, des capacités d'effacement/réinitialisation, des photos d'avatar/profil, et d'autres composants utiles imbriqués au sein d'un `Onglet` pour clarifier la signification prévue pour les utilisateurs. Vous pouvez ajouter un composant au slot `prefix` d'un `Onglet` lors de sa construction. Alternativement, vous pouvez utiliser les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après l'option affichée au sein d'un `Onglet`.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## Manipulation d'`Onglet` {#tab-manipulation}

Diverses méthodes existent pour permettre aux développeurs d'ajouter, d'insérer, de supprimer et de manipuler diverses propriétés des éléments `Onglet` au sein du `TabbedPane`.

### Ajout d'un `Onglet` {#adding-a-tab}

Les méthodes `addTab()` et `add()` existent dans différentes capacités surchargées pour permettre aux développeurs la flexibilité d'ajouter de nouveaux onglets au `TabbedPane`. L'ajout d'un `Onglet` le place après tous les onglets existants.

1. **`addTab(String text)`** - Ajoute un `Onglet` au `TabbedPane` avec le `String` spécifié comme texte de l'`Onglet`.
2. **`addTab(Tab tab)`** - Ajoute l'`Onglet` fourni en paramètre au `TabbedPane`.
3. **`addTab(String text, Component component)`** - Ajoute un `Onglet` avec le `String` donné comme texte de l'`Onglet`, et le `Component` fourni affiché dans la section contenu du `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Ajoute l'`Onglet` fourni et affiche le `Component` fourni dans la section contenu du `TabbedPane`.
5. **`add(Component... component)`** - Ajoute une ou plusieurs instances `Component` au `TabbedPane`, créant un `Onglet` distinct pour chacune, avec le texte étant défini sur le nom du `Component`.

:::info
La méthode `add(Component... component)` détermine le nom du `Component` passé en appelant `component.getName()` sur l'argument passé.
:::

### Insertion d'un `Onglet` {#inserting-a-tab}

En plus d'ajouter un `Onglet` à la fin des onglets existants, il est également possible d'en créer un nouveau à une position désignée. Pour ce faire, plusieurs versions surchargées de la méthode `insertTab()` existent.

1. **`insertTab(int index, String text)`** - Insère un `Onglet` dans le `TabbedPane` à l'index donné avec le `String` spécifié comme texte de l'`Onglet`.
2. **`insertTab(int index, Tab tab)`** - Insère l'`Onglet` fourni en paramètre au `TabbedPane` à l'index spécifié.
3. **`insertTab(int index, String text, Component component)`** - Insère un `Onglet` avec le `String` donné comme texte de l'`Onglet`, et le `Component` fourni affiché dans la section contenu du `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Insère l'`Onglet` fourni et affiche le `Component` fourni dans la section contenu du `TabbedPane`.

### Suppression d'un `Onglet` {#removing-a-tab}

Pour supprimer un seul `Onglet` du `TabbedPane`, utilisez l'une des méthodes suivantes :

1. **`removeTab(Tab tab)`** - Supprime un `Onglet` du `TabbedPane` en passant l'instance d'onglet à supprimer.
2. **`removeTab(int index)`** - Supprime un `Onglet` du `TabbedPane` en spécifiant l'index de l'`Onglet` à supprimer.

En plus des deux méthodes ci-dessus pour retirer un seul `Onglet`, utilisez la méthode **`removeAllTabs()`** pour vider le `TabbedPane` de tous les onglets.

:::info
Les méthodes `remove()` et `removeAll()` ne suppriment pas les onglets au sein du composant.
:::

### Association `Onglet`/`Composant` {#tabcomponent-association}

Pour changer le `Composant` à afficher pour un `Onglet` donné, appelez la méthode `setComponentFor()` et passez soit l'instance de l'`Onglet`, soit l'index de cet Onglet dans le `TabbedPane`.

:::info
Si cette méthode est utilisée sur un `Onglet` déjà associé à un `Composant`, le `Composant` précédemment associé sera détruit.
:::

## Configuration et mise en page {#configuration-and-layout}

La classe `TabbedPane` a deux parties constitutives : un `Onglet` qui est affiché dans un emplacement spécifié, et un composant à afficher. Cela peut être un seul composant, ou un composant [`Composite`](../building-ui/composite-components), permettant l'affichage de composants plus complexes dans la section de contenu d'un onglet.

### Glisser {#swiping}

Le `TabbedPane` prend en charge la navigation à travers les différents onglets via le glissement. Cela est idéal pour une application mobile, mais peut également être configuré via une méthode intégrée pour prendre en charge le glissement à la souris. Tant le glissement que le glissement à la souris sont désactivés par défaut, mais peuvent être activés avec les méthodes `setSwipable(boolean)` et `setSwipableWithMouse(boolean)` respectivement.

### Placement des Onglets {#tab-placement}

Les `Onglets` au sein d'un `TabbedPane` peuvent être placés à différentes positions dans le composant en fonction de la préférence des développeurs d'application. Les options fournies sont définies à l'aide de l'énumération fournie, qui a les valeurs `TOP`, `BOTTOM`, `LEFT`, `RIGHT` ou `HIDDEN`. Le paramètre par défaut est `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Alignement {#alignment}

En plus de changer le placement des éléments `Onglet` au sein du `TabbedPane`, il est également possible de configurer comment les onglets s'aligneront au sein du composant. Par défaut, le paramètre `AUTO` est en vigueur, ce qui permet au placement des onglets de dicter leur alignement.

Les autres options sont `START`, `END`, `CENTER` et `STRETCH`. Les trois premières décrivent la position par rapport au composant, tandis que `STRETCH` fait que les onglets remplissent l'espace disponible.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Bordure et indicateur d'activité {#border-and-activity-indicator}

Le `TabbedPane` affichera par défaut une bordure pour les onglets qu'il contient, placée en fonction de quel `Placement` a été défini. Cette bordure aide à visualiser l'espace que les différents onglets au sein du panneau occupent.

Lorsqu'un `Onglet` est cliqué, par défaut, un indicateur d'activité est affiché près de cet `Onglet` pour aider à mettre en évidence quel est l'`Onglet` actuellement sélectionné.

Ces deux options peuvent être personnalisées par un développeur en changeant les valeurs booléennes à l'aide des méthodes de setter appropriées. Pour changer si la bordure est affichée ou non, la méthode `setBorderless(boolean)` peut être utilisée, avec `true` cachant la bordure, et `false`, la valeur par défaut, affichant la bordure.

:::info
Cette bordure ne s'applique pas à la totalité du composant `TabbedPane`, et sert simplement de séparateur entre les onglets et le contenu du composant.
:::

Pour définir la visibilité de l'indicateur actif, la méthode `setHideActiveIndicator(boolean)` peut être utilisée. Passer `true` à cette méthode cachera l'indicateur actif sous un `Onglet` actif, tandis que `false`, la valeur par défaut, maintiendra l'indicateur affiché.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Modes d'activation {#activation-modes}

Pour un contrôle plus précis sur le comportement du `TabbedPane` lors de la navigation au clavier, le mode `Activation` peut être défini pour spécifier comment le composant doit se comporter.

- **`Auto`** : Lorsqu'il est réglé sur automatique, la navigation entre les onglets avec les flèches montrera instantanément le composant d'onglet correspondant.

- **`Manual`** : Lorsqu'il est réglé sur manuel, l'onglet recevra le focus mais ne s'affichera pas tant que l'utilisateur ne presse pas la barre d'espace ou entre.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Options de retrait {#removal-options}

Des éléments `Onglet` individuels peuvent être définis comme fermables. Les onglets fermables auront un bouton de fermeture ajouté à l'onglet, qui déclenche un événement de fermeture lorsqu'il est cliqué. Le `TabbedPane` dicte comment ce comportement est géré.

- **`Manual`** : Par défaut, la suppression est réglée sur `MANUAL`, ce qui signifie que l'événement est déclenché, mais il appartient au développeur de gérer cet événement de la manière de son choix.

- **`Auto`** : Alternativement, `AUTO` peut être utilisé, ce qui déclenchera l'événement et retirera également l'`Onglet` du composant pour le développeur, éliminant la nécessité pour le développeur d'implémenter ce comportement manuellement. 

## Stylisation {#styling}

### Expanse et thème {#expanse-and-theme}

Le `TabbedPane` dispose d'options `Expanse` et `Thème` intégrées semblables à d'autres composants webforJ. Celles-ci peuvent être utilisées pour ajouter rapidement un style qui transmet diverses significations à l'utilisateur final sans avoir besoin de styliser le composant avec CSS.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Meilleures pratiques {#best-practices}

Les pratiques suivantes sont recommandées pour utiliser le `TabbedPane` dans les applications :

- **Regroupement logique** : Utilisez des onglets pour regrouper logiquement le contenu connexe
    >- Chaque onglet doit représenter une catégorie ou fonctionnalité distincte au sein de votre application
    >- Regroupez les onglets similaires ou logiques les uns près des autres

- **Onglets limités** : Évitez de submerger les utilisateurs avec trop d'onglets. Envisagez d'utiliser une structure hiérarchique ou d'autres modèles de navigation lorsque cela est applicable pour une interface propre.

- **Étiquettes claires** : Étiquetez clairement vos Onglets pour une utilisation intuitive
    >- Fournissez des étiquettes claires et concises pour chaque onglet
    >- Les étiquettes doivent refléter le contenu ou le but, ce qui facilite la compréhension pour les utilisateurs
    >- Utilisez des icônes et des couleurs distinctes lorsque cela est applicable

- **Navigation au clavier** : Utilisez le support de navigation au clavier du `TabbedPane` de webforJ pour rendre l'interaction avec le `TabbedPane` plus fluide et intuitive pour l'utilisateur final.

- **Onglet par défaut** : Si l'onglet par défaut n'est pas placé au début du `TabbedPane`, envisagez de définir cet onglet comme par défaut pour des informations essentielles ou couramment utilisées.
