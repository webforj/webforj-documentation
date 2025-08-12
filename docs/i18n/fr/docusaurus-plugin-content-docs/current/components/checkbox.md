---
title: CheckBox
sidebar_position: 20
_i18n_hash: c2be55222401962b275faf28ff6ddba3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

La classe `CheckBox` crée un composant qui peut être sélectionné ou désélectionné et qui affiche son état à l'utilisateur. Lorsqu'il est cliqué, une coche apparaît à l'intérieur de la case, pour indiquer un choix affirmatif (activé). Lorsqu'il est cliqué à nouveau, la coche disparaît, indiquant un choix négatif (désactivé).

En fournissant une indication visuelle claire et simple de l'état de sélection, les cases à cocher améliorent l'interaction et la prise de décision des utilisateurs, ce qui en fait un élément essentiel des interfaces utilisateur modernes.

## Usages {#usages}

La `CheckBox` est mieux utilisée dans des scénarios où les utilisateurs doivent faire plusieurs sélections à partir d'une liste d'options. Voici quelques exemples de moments où utiliser la `CheckBox` :

1. **Sélection de tâches ou de fonctionnalités** : Les cases à cocher sont couramment utilisées lorsque les utilisateurs doivent sélectionner plusieurs tâches ou fonctionnalités pour effectuer certaines actions ou configurations.

2. **Paramètres de préférences** : Les applications impliquant des panneaux de préférences ou de paramètres utilisent souvent des cases à cocher pour permettre aux utilisateurs de choisir plusieurs options parmi un ensemble de choix. C'est mieux pour des options qui ne sont pas mutuellement exclusives. Par exemple :

> - Activer ou désactiver les notifications
> - Choisir un thème en mode sombre ou clair
> - Sélectionner les préférences de notification par e-mail

3. **Filtrage ou tri** : Une `CheckBox` peut être utilisée dans des applications nécessitant que les utilisateurs sélectionnent plusieurs filtres ou catégories, comme filtrer les résultats de recherche ou sélectionner plusieurs éléments pour d'autres actions.

4. **Entrées de formulaire** : Les cases à cocher sont couramment utilisées dans les formulaires pour permettre aux utilisateurs de sélectionner plusieurs options ou de faire des choix binaires. Par exemple :
   > - S'abonner à une newsletter
   > - Accepter les termes et conditions
   > - Sélectionner des articles à acheter ou à réserver

## Texte et positionnement {#text-and-positioning}

Les cases à cocher peuvent utiliser la méthode <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink>, qui sera positionnée près de la case à cocher selon la position intégrée <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>.

Les cases à cocher ont une fonctionnalité intégrée pour définir le texte à afficher soit à droite, soit à gauche de la case. Par défaut, le texte sera affiché à droite du composant. Le positionnement du texte est pris en charge par l'utilisation de l'énumération <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>. Voici les deux réglages : <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## Indéterminisme {#indeterminism}

Le composant `CheckBox` prend en charge l'indéterminisme, qui est un modèle d'interface utilisateur couramment utilisé dans les formulaires et les listes pour indiquer qu'un groupe de cases à cocher a un mélange d'états cochés et non cochés. Cet état est représenté par un troisième état visuel, généralement affiché sous forme de carré rempli ou de tiret à l'intérieur de la case à cocher. Voici quelques cas d'utilisation courants associés à l'indéterminisme :

- **Sélection de plusieurs éléments** : L'indéterminisme est utile lorsque les utilisateurs doivent sélectionner plusieurs éléments à partir d'une liste ou d'un ensemble d'options. Cela permet aux utilisateurs d'indiquer qu'ils souhaitent en sélectionner certains, mais pas tous, des choix disponibles.

- **Données hiérarchiques** : L'indéterminisme peut être employé dans des scénarios où il existe une relation hiérarchique entre les CheckBoxes. Par exemple, lors de la sélection de catégories et de sous-catégories, l'indéterminisme peut représenter le fait que certaines sous-catégories sont sélectionnées tandis que d'autres ne le sont pas, et que le composant parent est dans un état indéterminé.

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## Style {#styling}

### Espaces {#expanses}

Les valeurs suivantes de <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Espaces </JavadocLink> permettent un style rapide sans utiliser de CSS.
Les espaces sont pris en charge par l'utilisation de la classe énumérée `Expanse`. Voici les espaces pris en charge pour le composant de case à cocher : <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `Checkbox`, envisagez les meilleures pratiques suivantes :

1. **Étiqueter clairement les options** : Fournissez des étiquettes claires et concises pour chaque option `CheckBox` afin de décrire avec précision le choix. Les étiquettes doivent être faciles à comprendre et à distinguer les unes des autres.

2. **Grouper les CheckBoxes** : Regroupez les cases à cocher connexes pour indiquer leur association. Cela aide les utilisateurs à comprendre que plusieurs options peuvent être sélectionnées à l'intérieur d'un groupe spécifique.

3. **Fournir une sélection par défaut** : Si applicable, envisagez de fournir une sélection par défaut pour les cases à cocher afin de guider les utilisateurs lorsqu'ils rencontrent les options pour la première fois. La sélection par défaut doit correspondre au choix le plus courant ou préféré.

4. **Indéterminisme** : Si un composant `CheckBox` parent a plusieurs composants qui lui appartiennent de manière à ce que certains puissent être cochés et d'autres décochés, utilisez la propriété indéterminée pour montrer que tous les composants `CheckBox` ne sont pas cochés ou décochés.
