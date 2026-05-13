---
title: CheckBox
sidebar_position: 20
_i18n_hash: 7946f6753a03194ecdcf7e20a7053012
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

Un `CheckBox` peut être sélectionné ou désélectionné, et affiche son état actuel sous forme de coche. Les cases à cocher fonctionnent bien pour basculer des paramètres individuels ou permettre aux utilisateurs de choisir plusieurs options dans un ensemble.

<!-- INTRO_END -->

## Usages {#usages}

Le `CheckBox` est mieux utilisé dans des situations où les utilisateurs doivent effectuer plusieurs sélections parmi une liste d'options. Voici quelques exemples de quand utiliser le `CheckBox` :

1. **Sélection de tâches ou de fonctionnalités** : Les cases à cocher sont couramment utilisées lorsque les utilisateurs doivent sélectionner plusieurs tâches ou fonctionnalités pour effectuer certaines actions ou configurations.

2. **Paramètres de préférence** : Les applications qui impliquent des panneaux de préférences ou de paramètres utilisent souvent des cases à cocher pour permettre aux utilisateurs de choisir plusieurs options parmi un ensemble de choix. Ceci est particulièrement adapté pour des options qui ne sont pas mutuellement exclusives. Par exemple :

> - Activer ou désactiver les notifications
> - Choisir un thème de mode sombre ou clair
> - Sélectionner les préférences de notification par e-mail

3. **Filtrage ou tri** : Un `CheckBox` peut être utilisé dans des applications qui exigent que les utilisateurs sélectionnent plusieurs filtres ou catégories, comme le filtrage des résultats de recherche ou la sélection de plusieurs éléments pour des actions ultérieures.

4. **Saisie de formulaire** : Les cases à cocher sont couramment utilisées dans les formulaires pour permettre aux utilisateurs de sélectionner plusieurs options ou de faire des choix binaires. Par exemple :
   > - S'abonner à une newsletter
   > - Accepter les termes et conditions
   > - Sélectionner des articles pour un achat ou une réservation

## Texte et positionnement {#text-and-positioning}

Les cases à cocher peuvent utiliser la méthode <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink>, qui sera positionnée près de la case à cocher selon la <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> intégrée.

Les cases à cocher ont une fonctionnalité intégrée pour définir le texte affiché soit à droite, soit à gauche de la case. Par défaut, le texte sera affiché à droite du composant. Le positionnement du texte est pris en charge par l'utilisation de l'énumération <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>. Ci-dessous sont affichés les deux paramètres : <br/>

<ComponentDemo
path='/webforj/checkboxhorizontaltext'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java']}
height='200px'
/>

<br/>

## Indéterminisme {#indeterminism}

Le composant `CheckBox` prend en charge l'indéterminisme, qui est un modèle d'interface utilisateur couramment utilisé dans les formulaires et les listes pour indiquer qu'un groupe de cases à cocher présente un mélange d'états cochés et décochés. Cet état est représenté par un troisième état visuel, généralement affiché sous forme de carré rempli ou de tiret à l'intérieur de la case à cocher. Il existe quelques cas d'utilisation courants associés à l'indéterminisme :

- **Sélection de plusieurs éléments** : L'indéterminisme est utile lorsque les utilisateurs doivent sélectionner plusieurs éléments dans une liste ou un ensemble d'options. Cela permet aux utilisateurs d'indiquer qu'ils souhaitent sélectionner certains, mais pas tous, des choix disponibles.

- **Données hiérarchiques** : L'indéterminisme peut être utilisé dans des scénarios où il existe une relation hiérarchique entre les `CheckBoxes`. Par exemple, lors de la sélection de catégories et de sous-catégories, l'indéterminisme peut représenter que certaines sous-catégories sont sélectionnées tandis que d'autres ne le sont pas, et que le composant parent est dans un état indéterminé.

<ComponentDemo
path='/webforj/checkboxindeterminate'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java']}
height='150px'
/>

## Style {#styling}

### Expanses {#expanses}

Les valeurs <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Expanses </JavadocLink> suivantes permettent un style rapide sans utiliser de CSS.
Les expanses sont prises en charge par l'utilisation de la classe énumération `Expanse`. Ci-dessous sont les expanses prises en charge pour le composant case à cocher : <br/>

<ComponentDemo
path='/webforj/checkboxexpanse'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java']}
height='150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `Checkbox`, envisagez les meilleures pratiques suivantes :

1. **Étiquetez clairement les options** : Fournissez des étiquettes claires et concises pour chaque option `CheckBox` afin de décrire précisément le choix. Les étiquettes doivent être faciles à comprendre et à distinguer les unes des autres.

2. **Grouper les cases à cocher** : Groupez les cases à cocher connexes pour indiquer leur association. Cela aide les utilisateurs à comprendre que plusieurs options peuvent être sélectionnées au sein d'un groupe spécifique.

3. **Fournir une sélection par défaut** : Si applicable, envisagez de fournir une sélection par défaut pour les cases à cocher afin de guider les utilisateurs lorsqu'ils rencontrent pour la première fois les options. La sélection par défaut doit correspondre au choix le plus courant ou préféré.

4. **Indéterminisme** : Si un composant `CheckBox` parent a plusieurs composants qui lui appartiennent de manière à ce que certains puissent être cochés et d'autres décochés, utilisez la propriété indéterminée pour montrer que tous les composants `CheckBox` ne sont pas cochés ou décochés.
