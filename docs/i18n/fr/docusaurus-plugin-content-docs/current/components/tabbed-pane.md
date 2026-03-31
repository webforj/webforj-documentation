---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: d1522c6bd692420a02df494fa0509a23
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Plusieurs sections de contenu peuvent être organisées sous un seul `TabbedPane`, où chaque section est liée à un `Tab` cliquable. Une seule section est visible à la fois, et les onglets peuvent afficher du texte, des icônes ou les deux pour aider les utilisateurs à naviguer entre eux.

<!-- INTRO_END -->

## Usages {#usages}

La classe `TabbedPane` offre aux développeurs un outil puissant pour organiser et présenter plusieurs onglets ou sections au sein d'une interface utilisateur. Voici quelques scénarios typiques où vous pourriez utiliser un `TabbedPane` dans votre application :

1. **Visionneuse de documents** : Mise en œuvre d'une visionneuse de documents où chaque onglet représente un document ou un fichier différent. Les utilisateurs peuvent facilement passer d'un document ouvert à un autre pour une multitâche efficace.

2. **Gestion des données** : Utilisez un `TabbedPane` pour organiser les tâches de gestion des données, par exemple :
    >- Différents ensembles de données à afficher dans une application
    >- Divers profils d'utilisateur peuvent être affichés dans des onglets séparés
    >- Différents profils dans un système de gestion des utilisateurs

3. **Sélection de module** : Un `TabbedPane` peut représenter différents modules ou sections. Chaque onglet peut encapsuler les fonctionnalités d'un module spécifique, permettant aux utilisateurs de se concentrer sur un aspect de l'application à la fois.

4. **Gestion des tâches** : Les applications de gestion des tâches peuvent utiliser un `TabbedPane` pour représenter divers projets ou tâches. Chaque onglet pourrait correspondre à un projet spécifique, permettant aux utilisateurs de gérer et de suivre les tâches séparément.

5. **Navigation dans le programme** : Au sein d'une application qui doit exécuter divers programmes, un `TabbedPane` pourrait :
    >- Servir de barre latérale permettant d'exécuter différentes applications ou programmes dans une seule application, comme ce qui est montré dans le modèle [`AppLayout`](./app-layout.md)
    >- Créer une barre supérieure qui peut servir un objectif similaire ou représenter des sous-applications au sein d'une application déjà sélectionnée.
  
## Onglets {#tabs}

Les onglets sont des éléments d'interface utilisateur qui peuvent être ajoutés aux panneaux à onglets pour organiser et passer entre différentes vues de contenu.

:::important
Les onglets ne sont pas destinés à être utilisés comme des composants autonomes. Ils sont conçus pour être utilisés en conjonction avec des panneaux à onglets. Cette classe n'est pas un `Component` et ne doit pas être utilisée comme telle.
:::

### Propriétés {#properties}

Les onglets sont composés des propriétés suivantes, qui sont ensuite utilisées lors de leur ajout dans un `TabbedPane`. Ces propriétés ont des accesseurs et des mutateurs pour faciliter la personnalisation au sein d'un `TabbedPane`.

1. **Clé (`Object`)** : Représente l'identifiant unique pour l'`Onglet`.

2. **Texte (`String`)** : Le texte qui sera affiché comme titre pour l'`Onglet` dans le `TabbedPane`. Cela est également référé comme le titre via les méthodes `getTitle()` et `setTitle(String title)`.

3. **Info-bulle (`String`)** : Le texte de l'info-bulle qui est associé à l'`Onglet`, qui sera affiché lorsque le curseur passe au-dessus de l'`Onglet`.

4. **Activé (`boolean`)** : Représente si l'`Onglet` est actuellement activé ou non. Peut être modifié avec la méthode `setEnabled(boolean enabled)`.

5. **Fermable (`boolean`)** : Représente si l'`Onglet` peut être fermé. Peut être modifié avec la méthode `setCloseable(boolean enabled)`. Cela ajoutera un bouton de fermeture sur l'`Onglet` qui peut être cliqué par l'utilisateur et déclenche un événement de suppression. Le composant `TabbedPane` dicte comment gérer la suppression.

6. **Slot (`Component`)** : 
    Les slots offrent des options flexibles pour améliorer la capacité d'un `Onglet`. Vous pouvez avoir des icônes, des étiquettes, des spinners de chargement, des capacités de réinitialisation, des avatars/images de profil et d'autres composants bénéfiques imbriqués dans un `Onglet` pour clarifier davantage le sens destiné aux utilisateurs.
    Vous pouvez ajouter un composant au slot `prefix` d'un `Onglet` lors de la construction. Alternativement, vous pouvez utiliser les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après l'option affichée au sein d'un `Onglet`.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## Manipulation d'`Onglet` {#tab-manipulation}

Diverses méthodes existent pour permettre aux développeurs d'ajouter, d'insérer, de supprimer et de manipuler diverses propriétés des éléments `Onglet` au sein du `TabbedPane`.

### Ajout d'un `Onglet` {#adding-a-tab}

Les méthodes `addTab()` et `add()` existent dans différentes capacités surchargées pour permettre aux développeurs de flexibilité dans l'ajout de nouveaux onglets au `TabbedPane`. Ajouter un `Onglet` le placera après tout onglet existant précédemment.

1. **`addTab(String text)`** - Ajoute un `Onglet` au `TabbedPane` avec le `String` spécifié comme texte de l'`Onglet`.
2. **`addTab(Tab tab)`** - Ajoute l'`Onglet` fourni comme paramètre au `TabbedPane`.
3. **`addTab(String text, Component component)`** - Ajoute un `Onglet` avec le `String` donné comme texte de l'`Onglet`, et le `Component` fourni affiché dans la section de contenu du `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Ajoute l'`Onglet` fourni et affiche le `Component` fourni dans la section de contenu du `TabbedPane`.
5. **`add(Component... component)`** - Ajoute une ou plusieurs instances de `Component` au `TabbedPane`, créant un `Onglet` distinct pour chacun, le texte étant défini sur le nom du `Component`.

:::info
Le `add(Component... component)` détermine le nom du `Component` passé en appelant `component.getName()` sur l'argument passé.
:::

### Insertion d'un `Onglet` {#inserting-a-tab}

En plus d'ajouter un `Onglet` à la fin des onglets existants, il est également possible de créer un nouvel onglet à une position désignée. Pour ce faire, plusieurs versions surchargées de `insertTab()` existent. 

1. **`insertTab(int index, String text)`** - Insère un `Onglet` dans le `TabbedPane` à l'index donné avec le `String` spécifié comme texte de l'`Onglet`.
2. **`insertTab(int index, Tab tab)`** - Insère l'`Onglet` fourni comme paramètre au `TabbedPane` à l'index spécifié.
3. **`insertTab(int index, String text, Component component)`** - Insère un `Onglet` avec le `String` donné comme texte de l'`Onglet`, et le `Component` fourni affiché dans la section de contenu du `TabbedPane`.
4. **`insertTab(int index, Tab tab, Component component)`** - Insère l'`Onglet` fourni et affiche le `Component` fourni dans la section de contenu du `TabbedPane`.

### Suppression d'un `Onglet` {#removing-a-tab}

Pour supprimer un `Onglet` unique du `TabbedPane`, utilisez l'une des méthodes suivantes :

1. **`removeTab(Tab tab)`** - Supprime un `Onglet` du `TabbedPane` en passant l'instance d'Onglet à supprimer.
2. **`removeTab(int index)`** - Supprime un `Onglet` du `TabbedPane` en spécifiant l'index de l'`Onglet` à supprimer.

En plus des deux méthodes ci-dessus pour la suppression d'un `Onglet` unique, utilisez la méthode **`removeAllTabs()`** pour effacer le `TabbedPane` de tous les onglets.

:::info
Les méthodes `remove()` et `removeAll()` ne suppriment pas les onglets au sein du composant.
:::

### Association Onglet/Composant {#tabcomponent-association}

Pour changer le `Component` qui sera affiché pour un `Onglet` donné, appelez la méthode `setComponentFor()` et passez soit l'instance de l'`Onglet`, soit l'index de cet Onglet au sein du `TabbedPane`.

:::info
Si cette méthode est utilisée sur un `Onglet` qui est déjà associé à un `Component`, le `Component` précédemment associé sera détruit.
:::

## Configuration et disposition {#configuration-and-layout}

La classe `TabbedPane` a deux parties constitutives : un `Onglet` qui est affiché dans un emplacement spécifié, et un composant à afficher. Cela peut être un seul composant, ou un [`Composite`](../building-ui/composite-components), permettant l'affichage de composants plus complexes dans la section de contenu d'un onglet.

### Glissement {#swiping}

Le `TabbedPane` prend en charge la navigation à travers les différents onglets via le glissement. Cela est idéal pour une application mobile, mais peut également être configuré via une méthode intégrée pour supporter le glissement de la souris. Le glissement et le glissement de la souris sont désactivés par défaut, mais peuvent être activés avec les méthodes `setSwipable(boolean)` et `setSwipableWithMouse(boolean)` respectivement. 

### Placement des Onglets {#tab-placement}

Les `Tabs` au sein d'un `TabbedPane` peuvent être placés à différentes positions au sein du composant en fonction de la préférence des développeurs d'application. Les options fournies sont définies à l'aide de l'énumération proposée, qui a les valeurs `TOP`, `BOTTOM`, `LEFT`, `RIGHT`, ou `HIDDEN`. Le paramètre par défaut est `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Alignement {#alignment}

En plus de changer le placement des éléments `Onglet` au sein du `TabbedPane`, il est également possible de configurer comment les onglets seront alignés au sein du composant. Par défaut, le paramètre `AUTO` est en vigueur, ce qui permet au placement des onglets de dicter leur alignement.

Les autres options sont `START`, `END`, `CENTER`, et `STRETCH`. Les trois premières décrivent la position par rapport au composant, tandis que `STRETCH` permet aux onglets de remplir l'espace disponible.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Bordure et indicateur d'activité {#border-and-activity-indicator}

Le `TabbedPane` aura une bordure affichée pour les onglets à l'intérieur par défaut, placée en fonction de quel `Placement` a été défini. Cette bordure aide à visualiser l'espace que les différents onglets au sein du panneau occupent. 

Lorsque un `Onglet` est cliqué, par défaut, un indicateur d'activité est affiché près de cet `Onglet` pour aider à mettre en évidence quel est l'`Onglet` actuellement sélectionné.

Ces deux options peuvent être personnalisées par un développeur en changeant les valeurs booléennes en utilisant les méthodes setters appropriées. Pour changer si la bordure est affichée ou non, la méthode `setBorderless(boolean)` peut être utilisée, avec `true` cachant la bordure, et `false`, la valeur par défaut, affichant la bordure.

:::info
Cette bordure ne s'applique pas à l'ensemble du composant `TabbedPane`, et sert simplement de séparateur entre les onglets et le contenu du composant.
:::

Pour définir la visibilité de l'indicateur actif, la méthode `setHideActiveIndicator(boolean)` peut être utilisée. Passer `true` à cette méthode cachera l'indicateur actif sous un `Onglet` actif, tandis que `false`, la valeur par défaut, garde l'indicateur affiché.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Modes d'activation {#activation-modes}

Pour un contrôle plus précis sur la façon dont le `TabbedPane` se comporte lorsqu'il est navigué par le clavier, le mode `Activation` peut être défini pour spécifier comment le composant doit se comporter.

- **`Auto`** : Lorsqu'il est défini sur auto, naviguer entre les onglets avec les touches fléchées affichera instantanément le composant d'onglet correspondant.

- **`Manual`** : Lorsqu'il est défini sur manuel, l'onglet recevra le focus mais ne s'affichera pas tant que l'utilisateur n'appuie pas sur espace ou entrée.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Options de suppression {#removal-options}

Les éléments d'`Onglet` individuels peuvent être configurés pour être fermables. Les onglets fermables auront un bouton de fermeture ajouté à l'onglet, qui déclenche un événement de fermeture lorsqu'il est cliqué. Le `TabbedPane` dicte la façon dont ce comportement est géré.

- **`Manual`** : Par défaut, la suppression est définie sur `MANUAL`, ce qui signifie que l'événement est déclenché, mais il appartient au développeur de gérer cet événement de la manière dont il le souhaite.

- **`Auto`** : Alternativement, `AUTO` peut être utilisé, ce qui déclenchera l'événement et supprimera également l'`Onglet` du composant pour le développeur, rendant inutile pour le développeur d'implémenter ce comportement manuellement. 

## Styles {#styling}

### Expansivité et thème {#expanse-and-theme}

Le `TabbedPane` est livré avec des options `Expanse` et `Thème` intégrées similaires à d'autres composants webforJ. Celles-ci peuvent être utilisées pour ajouter rapidement des styles qui transmettent différentes significations à l'utilisateur final sans avoir besoin de styliser le composant avec CSS.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Meilleures pratiques {#best-practices}

Les pratiques suivantes sont recommandées pour utiliser le `TabbedPane` au sein des applications :

- **Groupe logique** : Utilisez des onglets pour regrouper logiquement le contenu lié
    >- Chaque onglet doit représenter une catégorie ou une fonctionnalité distincte au sein de votre application
    >- Regroupez des onglets similaires ou logiques près les uns des autres

- **Onglets limités** : Évitez de submerger les utilisateurs avec trop d'onglets. Envisagez d'utiliser une structure hiérarchique ou d'autres modèles de navigation lorsque cela est applicable pour une interface propre

- **Étiquettes claires** : Étiquetez clairement vos Onglets pour une utilisation intuitive
    >- Fournissez des étiquettes claires et concises pour chaque onglet
    >- Les étiquettes doivent refléter le contenu ou l'objectif, facilitant ainsi la compréhension des utilisateurs
    >- Utilisez des icônes et des couleurs distinctes lorsque cela est applicable

- **Navigation clavier** : Utilisez le support de navigation clavier du `TabbedPane` de webforJ pour rendre l'interaction avec le `TabbedPane` plus fluide et intuitive pour l'utilisateur final.

- **Onglet par défaut** : Si l'onglet par défaut n'est pas placé au début du `TabbedPane`, envisagez de définir cet onglet comme par défaut pour des informations essentielles ou couramment utilisées.
