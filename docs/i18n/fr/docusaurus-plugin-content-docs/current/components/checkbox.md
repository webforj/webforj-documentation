---
title: CheckBox
sidebar_position: 20
_i18n_hash: 0c55e1e2b7f92aa8f1f65151bfa3e096
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

La classe `CheckBox` crée un composant qui peut être sélectionné ou désélectionné, et qui affiche son état à l'utilisateur. Lorsqu'on clique, une coche apparaît à l'intérieur de la boîte pour indiquer un choix positif (activé). Lorsqu'on clique à nouveau, la coche disparaît, indiquant un choix négatif (désactivé).

En fournissant une indication visuelle claire et directe de l'état de sélection, les cases à cocher améliorent l'interaction et la prise de décision des utilisateurs, en faisant un élément essentiel des interfaces utilisateur modernes.

## Usages {#usages}

Le `CheckBox` est mieux utilisé dans les scénarios où les utilisateurs doivent effectuer plusieurs sélections à partir d'une liste d'options. Voici quelques exemples de quand utiliser le `CheckBox` :

1. **Sélection de tâches ou de fonctionnalités** : Les cases à cocher sont couramment utilisées lorsque les utilisateurs doivent sélectionner plusieurs tâches ou fonctionnalités pour effectuer certaines actions ou configurations.

2. **Paramètres de préférence** : Les applications qui impliquent des panneaux de préférences ou de paramètres utilisent souvent des cases à cocher pour permettre aux utilisateurs de choisir plusieurs options à partir d'un ensemble de choix. C'est le mieux pour les options qui ne sont pas mutuellement exclusives. Par exemple :

> - Activer ou désactiver les notifications
> - Choisir un thème en mode sombre ou clair
> - Sélectionner des préférences de notification par e-mail

3. **Filtrage ou tri** : Un `CheckBox` peut être utilisé dans des applications qui nécessitent que les utilisateurs sélectionnent plusieurs filtres ou catégories, comme le filtrage des résultats de recherche ou la sélection de plusieurs éléments pour d'autres actions.

4. **Entrées de formulaire** : Les cases à cocher sont couramment utilisées dans les formulaires pour permettre aux utilisateurs de sélectionner plusieurs options ou de faire des choix binaires. Par exemple :
   > - S'abonner à une newsletter
   > - Accepter les termes et conditions
   > - Sélectionner des articles pour un achat ou une réservation

## Texte et positionnement {#text-and-positioning}

Les cases à cocher peuvent utiliser la méthode <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink>, qui sera positionnée près de la case à cocher selon la <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> intégrée.

Les cases à cocher ont une fonctionnalité intégrée pour définir le texte à afficher soit à droite, soit à gauche de la boîte. Par défaut, le texte sera affiché à droite du composant. Le positionnement du texte est pris en charge par l'utilisation de l'énumération <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>. Voici les deux paramètres : <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## Indéterminisme {#indeterminism}

Le composant `CheckBox` prend en charge l'indéterminisme, qui est un modèle UI couramment utilisé dans les formulaires et les listes pour indiquer qu'un groupe de cases à cocher a un mélange d'états cochés et non cochés. Cet état est représenté par un troisième état visuel, généralement affiché sous forme de carré rempli ou de tiret à l'intérieur de la case à cocher. Il existe quelques cas d'utilisation communs associés à l'indéterminisme :

- **Sélection de plusieurs éléments** : L'indéterminisme est utile lorsque les utilisateurs doivent sélectionner plusieurs éléments à partir d'une liste ou d'un ensemble d'options. Cela permet aux utilisateurs d'indiquer qu'ils souhaitent sélectionner certains, mais pas tous, des choix disponibles.

- **Données hiérarchiques** : L'indéterminisme peut être employé dans des scénarios où il existe une relation hiérarchique entre les checkboxes. Par exemple, lors de la sélection de catégories et sous-catégories, l'indéterminisme peut représenter que certaines sous-catégories sont sélectionnées tandis que d'autres ne le sont pas, et le composant parent est dans un état indéterminé.

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## Style {#styling}

### Expanses {#expanses}

Les valeurs suivantes <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Expanses </JavadocLink> permettent un style rapide sans utiliser de CSS.
Les expanse sont soutenues par l'utilisation de la classe d'énumération `Expanse`. Voici les expanse prises en charge pour le composant case à cocher : <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Meilleures pratiques {#best-practices}

Pour assurer une expérience utilisateur optimale lors de l'utilisation du composant `Checkbox`, considérez les meilleures pratiques suivantes :

1. **Étiquetez clairement les options** : Fournissez des étiquettes claires et concises pour chaque option `CheckBox` pour décrire avec précision le choix. Les étiquettes doivent être faciles à comprendre et à distinguer les unes des autres.

2. **Groupez les CheckBoxes** : Regroupez les cases à cocher connexes pour indiquer leur association. Cela aide les utilisateurs à comprendre que plusieurs options peuvent être sélectionnées au sein d'un groupe spécifique.

3. **Fournissez une sélection par défaut** : Si applicable, envisagez de fournir une sélection par défaut pour les cases à cocher pour guider les utilisateurs lorsqu'ils rencontrent pour la première fois les options. La sélection par défaut doit correspondre au choix le plus courant ou préféré.

4. **Indéterminisme** : Si un composant `CheckBox` parent a plusieurs composants qui lui appartiennent de manière à ce que certains puissent être cochés et d'autres décochés, utilisez la propriété indéterminée pour montrer que tous les composants `CheckBox` ne sont pas cochés ou décochés.
