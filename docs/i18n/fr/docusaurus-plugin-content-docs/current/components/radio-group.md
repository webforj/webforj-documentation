---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 91d753e882e3d6d59deef5044ee7bc4c
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

La classe `RadioButtonGroup` est utilisée pour regrouper des boutons radio liés, ce qui aide à établir l'exclusivité mutuelle parmi les options de ce groupe. Les utilisateurs ne peuvent sélectionner qu'un seul bouton radio dans un groupe donné. Lorsqu'un utilisateur sélectionne un bouton radio dans un groupe, tout bouton radio précédemment sélectionné dans le même groupe devient automatiquement désélectionné. Cela garantit qu'une seule option peut être choisie à la fois.

:::tip
Un composant `RadioButton` stocke le groupe auquel il appartient, qui peut être accessible via la méthode `getButtonGroup()`.
:::

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important
Le composant `RadioButtonGroup` ne rend pas un élément HTML sur la page. Au contraire, c'est uniquement une logique qui garantit qu'un groupe de boutons radio se comporte comme un groupe et non individuellement.
:::

## Usages {#usages}

Le `RadioButtonGroup` est mieux utilisé dans des scénarios où les utilisateurs doivent faire une seule sélection parmi un ensemble prédéfini d'options présentées sous forme de boutons radio. Voici quelques exemples d'utilisation du `RadioButtonGroup` :

1. **Enquêtes ou Questionnaires** : Les composants `RadioButtonGroup` sont couramment utilisés dans des enquêtes ou des questionnaires où les utilisateurs doivent sélectionner une seule réponse à partir d'une liste d'options.

2. **Paramètres de Préférence** : Les applications impliquant des panneaux de préférences ou de paramètres utilisent souvent le composant RadioButtonGroup pour permettre aux utilisateurs de choisir une seule option parmi un ensemble de choix mutuellement exclusifs.

3. **Filtrage ou Tri** : Un `RadioButton` peut être utilisé dans des applications qui nécessitent que les utilisateurs sélectionnent un seul filtre ou option de tri, tel que le tri d'une liste d'éléments selon différents critères.

<!-- vale off -->
## Ajouter et retirer des RadioButtons {#adding-and-removing-radiobuttons}
<!-- vale on -->

Il est possible d'ajouter et de retirer des objets `RadioButton` uniques ou multiples d'un groupe, en veillant à ce qu'ils présentent un comportement de vérification mutuellement exclusif et soient associés à tout nom qui pourrait appartenir au groupe.

## Nommer {#naming}

L'attribut name dans un `RadioButtonGroup` regroupe des RadioButtons liés, permettant aux utilisateurs de faire un choix unique parmi les options fournies et renforçant l'exclusivité parmi les RadioButtons. Le nom d'un groupe n'est toutefois pas reflété dans le DOM et est un utilitaire pratique pour le développeur Java.

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant RadioButton, envisagez les meilleures pratiques suivantes :

1. **Étiquetez clairement les options** : Fournissez des étiquettes claires et concises pour chaque option `RadioButton` afin de décrire avec précision le choix. Les étiquettes doivent être faciles à comprendre et à distinguer les unes des autres.

2. **Fournissez une sélection par défaut** : Le cas échéant, envisagez de fournir une sélection par défaut pour les boutons radio afin de guider les utilisateurs lorsqu'ils rencontrent d'abord les options. La sélection par défaut doit correspondre au choix le plus courant ou le plus préféré.
