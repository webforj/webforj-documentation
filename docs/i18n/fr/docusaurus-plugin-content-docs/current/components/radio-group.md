---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 8e58efd7b052a00eaf8cfce276cda92e
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

La classe `RadioButtonGroup` est utilisée pour regrouper des boutons radio connexes, ce qui permet d'établir l'exclusivité mutuelle parmi les options dans ce groupe. Les utilisateurs ne peuvent sélectionner qu'un seul bouton radio dans un groupe donné. Lorsqu'un utilisateur sélectionne un bouton radio dans un groupe, tout bouton radio précédemment sélectionné dans le même groupe devient automatiquement désélectionné. Cela garantit qu'une seule option peut être choisie à la fois.

:::tip
Un composant `RadioButton` stocke le groupe auquel il appartient, qui peut être accédé via la méthode `getButtonGroup()`.
:::

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important
Le composant `RadioButtonGroup` ne rend pas un élément HTML sur la page. Au lieu de cela, il s'agit uniquement
de logique qui garantit qu'un groupe de boutons radio se comporte comme un groupe plutôt qu'individuellement.
:::

## Usages {#usages}

Le `RadioButtonGroup` est le mieux utilisé dans des scénarios où les utilisateurs doivent faire une seule sélection parmi un ensemble prédéfini d'options présentées sous forme de boutons radio. Voici quelques exemples de quand utiliser le `RadioButtonGroup` :

1. **Enquêtes ou questionnaires** : Les composants `RadioButtonGroup` sont couramment utilisés dans les enquêtes ou les questionnaires où les utilisateurs doivent sélectionner une seule réponse parmi une liste d'options.

2. **Paramètres de préférence** : Les applications qui impliquent des panneaux de préférences ou de paramètres utilisent souvent le composant RadioButtonGroup pour permettre aux utilisateurs de choisir une seule option parmi un ensemble de choix mutuellement exclusifs.

3. **Filtrage ou tri** : Un `RadioButton` peut être utilisé dans des applications qui exigent que les utilisateurs sélectionnent une seule option de filtrage ou de tri, comme trier une liste d'éléments par différents critères.

<!-- vale off -->
## Ajouter et supprimer des RadioButtons {#adding-and-removing-radiobuttons}
<!-- vale on -->

Il est possible d'ajouter et de supprimer des objets `RadioButton` individuels ou multiples à un groupe, garantissant qu'ils exhibent un comportement de sélection mutuellement exclusif et sont associés à tout nom pouvant appartenir au groupe.

## Nommer {#naming}

L'attribut name dans un `RadioButtonGroup` regroupe des boutons radio connexes, permettant aux utilisateurs de faire un choix unique parmi les options fournies et renforçant l'exclusivité parmi les boutons radio. Le nom d'un groupe n'est pas reflété dans le DOM, cependant, et est un utilitaire de commodité pour le développeur Java.

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant RadioButton, considérez les meilleures pratiques suivantes :

1. **Étiquetez clairement les options** : Fournissez des étiquettes claires et concises pour chaque option `RadioButton` afin de décrire avec précision le choix. Les étiquettes doivent être faciles à comprendre et à distinguer les unes des autres.

2. **Fournissez une sélection par défaut** : Si applicable, envisagez de fournir une sélection par défaut pour les boutons radio afin d'orienter les utilisateurs lorsqu'ils rencontrent pour la première fois les options. La sélection par défaut doit correspondre au choix le plus fréquent ou préféré.
