---
title: Splitter
sidebar_position: 115
_i18n_hash: 0f8ea00bed7b930d5b7a8efe6bcd5446
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

Le composant `Splitter`, conçu pour diviser et redimensionner le contenu de votre application, encapsule deux composants redimensionnables : le composant maître et le composant de détail. Un diviseur sépare ces composants, permettant aux utilisateurs d'ajuster dynamiquement la taille de chaque composant selon leurs préférences.

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
height='300px'
/>

## Taille minimale et maximale {#min-and-max-size}

Le composant `Splitter` fournit des méthodes pour définir des tailles minimales et maximales pour ses panneaux, vous permettant de contrôler le comportement de redimensionnement des composants au sein du `Splitter`. Lorsque les utilisateurs essaient de redimensionner les panneaux au-delà des tailles minimales ou maximales spécifiées, le composant splitter impose ces contraintes, garantissant que les panneaux restent dans les limites définies.

### Définir les tailles {#setting-sizes}

La méthode `setMasterMinSize(String masterMinSize)` spécifie la taille minimale pour le panneau maître du splitter. De même, la méthode `setMasterMaxSize(String masterMaxSize)` spécifie la taille maximale pour le panneau maître.

Vous pouvez spécifier des tailles en utilisant n'importe quelle unité CSS valide, comme montré ci-dessous :

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
height='300px'
/>

## Orientation {#orientation}

Vous pouvez configurer l'orientation dans le composant `Splitter`, vous permettant de créer des mises en page adaptées à des exigences de conception spécifiques. En spécifiant l'orientation, le composant arrange les panneaux horizontalement ou verticalement, offrant une polyvalence dans la conception de la mise en page.

Pour configurer l'orientation, utilisez l'énumération des orientations prises en charge pour spécifier si le `Splitter` doit être rendu horizontalement ou verticalement :

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
height='300px'
/>

## Position relative {#relative-position}

Pour définir la position initiale de la barre de séparation dans le composant `Splitter`, utilisez `setPositionRelative`. Cette méthode prend une valeur numérique de `0` à `100` représentant le pourcentage de l'espace donné dans le `Splitter`, et affiche le diviseur au pourcentage donné de la largeur totale :

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
height='300px'
/>

## Imbrication {#nesting}

L'imbrication de splitters vous permet de créer des mises en page complexes avec des niveaux de panneaux redimensionnables. Cela permet la création d'interfaces utilisateur sophistiquées avec un contrôle granulaire sur l'agencement et le redimensionnement du contenu.

Pour imbriquer des composants Splitter, instanciez de nouveaux `Splitter` et ajoutez-les en tant qu'enfants aux composants `Splitter` existants. Cette structure hiérarchique permet de créer des mises en page multi-niveaux avec des capacités de redimensionnement flexibles. Le programme ci-dessous le démontre :

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
height='300px'
/>

## Sauvegarde automatique {#auto-save}

Le composant `Splitter` comprend une option de Sauvegarde Automatique, laquelle sauvegarde l'état des tailles de panneaux dans le stockage local pour maintenir les dimensions cohérentes entre les rechargements.

Lorsque vous définissez la configuration de sauvegarde automatique, le composant `Splitter` stocke automatiquement l'état des tailles de panneaux dans le stockage local du navigateur web. Cela garantit que les tailles choisies par les utilisateurs pour les panneaux persistent à travers les rechargements de page ou les sessions de navigateur, réduisant le besoin d'ajustements manuels.

### Nettoyage de l'état {#cleaning-the-state}

Pour revenir programmatique au `Splitter` à ses paramètres et dimensions par défaut, appelez la méthode `cleanState()` pour supprimer toutes les données d'état sauvegardées liées au composant `Splitter` du stockage local du navigateur web.

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
height='400px'
/>

Dans la démo précédente, chaque instance de Splitter active la fonctionnalité de Sauvegarde Automatique en appelant la méthode `setAutosave`. Cela garantit que les tailles des panneaux sont automatiquement sauvegardées dans le stockage local. Ainsi, lors du rechargement du navigateur, les tailles de ces splitters restent les mêmes.

Cliquer sur le bouton "Effacer l'état" appelle la méthode `cleanState()` et actualise la fenêtre du navigateur pour afficher les dimensions originales.

## Style {#styling}

<TableBuilder name="Splitter" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `Splitter`, considérez les meilleures pratiques suivantes : 

- **Ajustez en fonction du contenu** : Lors de la décision sur l'orientation et les tailles initiales des panneaux, tenez compte de la priorité du contenu. Par exemple, dans une mise en page avec une barre de navigation latérale et une zone de contenu principale, la barre latérale devrait généralement rester plus étroite avec une taille minimale définie pour une navigation claire.

- **Imbrication stratégique** : Imbriquer des splitters peut créer des mises en page polyvalentes mais peut compliquer l'UI et impacter les performances. Planifiez vos mises en page imbriquées pour assurer qu'elles soient intuitives et améliorent l'expérience utilisateur.

- **Rappelez-vous des préférences des utilisateurs** : Utilisez la fonctionnalité de Sauvegarde Automatique pour mémoriser les ajustements des utilisateurs entre les sessions, améliorant l'expérience utilisateur. Fournissez une option permettant aux utilisateurs de réinitialiser aux paramètres par défaut.
