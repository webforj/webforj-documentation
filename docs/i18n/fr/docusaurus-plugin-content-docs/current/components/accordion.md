---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 560172f4743427476d9ecaadebd1d61d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

Le composant `Accordion` fournit un ensemble empilé verticalement de panneaux collapsibles. Chaque panneau a un en-tête cliquable qui bascule la visibilité de son contenu. Un `AccordionPanel` peut être utilisé comme une section de divulgation autonome ou regroupé à l'intérieur d'un `Accordion` pour coordonner le comportement d'expansion et de contraction à travers plusieurs panneaux.

<!-- INTRO_END -->

:::tip Quand utiliser un accordéon
Les accordéons fonctionnent bien pour les FAQ, les pages de paramètres et les flux étape par étape où révéler tout le contenu à la fois créerait une mise en page écrasante. Si les sections sont également importantes et que les utilisateurs bénéficient de les voir simultanément, envisagez des [onglets](/docs/components/tabbedpane) à la place.
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` est le bloc de construction principal du système d'accordéon. Passez une chaîne de label au constructeur pour définir le texte de l'en-tête, puis ajoutez des composants enfants pour remplir le corps. Un panneau fonctionne de manière autonome sans aucun groupe `Accordion` entourant, ce qui en fait un widget de divulgation léger utile lorsque vous avez juste besoin d'une seule section collapsible. Le constructeur sans argument est également disponible lorsque vous préférez configurer le panneau entièrement après sa création.

```java
// Label uniquement - ajout de contenu corporel séparément
AccordionPanel panel = new AccordionPanel("Titre de section");
panel.add(new Paragraph("Le contenu du corps va ici."));

// Label et contenu du corps passés directement dans le constructeur
AccordionPanel panel = new AccordionPanel("Titre", new Paragraph("Contenu du corps."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
height='500px'
/>
<!-- vale on -->

### Ouverture et fermeture {#opening-and-closing}

Contrôlez l'état ouvert/fermé par programmation à tout moment. `isOpened()` est utile lorsque vous devez lire l'état actuel avant de décider quoi faire. Par exemple, vous pourriez basculer un panneau vers l'état opposé ou afficher ou masquer conditionnellement d'autres parties de l'interface utilisateur.

```java
// Déployer le panneau
panel.open();

// Réduire le panneau
panel.close();                    

// Retourne vrai si actuellement déployé
boolean isOpen = panel.isOpened();
```

Utilisez `setLabel()` pour mettre à jour le texte de l'en-tête après construction. `setText()` est un alias pour la même opération, donc le label peut être synchronisé avec des données dynamiques :

```java
panel.setLabel("Label mis à jour");
```

## Groupes d'accordéon {#accordion-groups}

Enveloppant plusieurs instances de `AccordionPanel` à l'intérieur d'un `Accordion` crée un groupe coordonné. Par défaut, le groupe utilise le **mode simple** : ouvrir un panneau réduit automatiquement tous les autres, gardant une seule section visible à la fois. Ce défaut est intentionnel, il garde l'utilisateur concentré sur un morceau de contenu et empêche la page de devenir visuellement écrasante lorsque les panneaux ont un contenu corporel substantiel.

Les panneaux sont construits indépendamment et passés au `Accordion`, vous pouvez donc configurer chacun avant de les regrouper. Plusieurs instances séparées d'`Accordion` peuvent également exister sur la même page — chaque groupe gère son propre état de manière indépendante, donc développer un panneau dans un groupe n'a pas d'effet sur un autre.

```java
AccordionPanel panel1 = new AccordionPanel("Qu'est-ce que webforJ ?");
AccordionPanel panel2 = new AccordionPanel("Comment fonctionnent les panneaux groupés ?");
AccordionPanel panel3 = new AccordionPanel("Puis-je avoir plusieurs groupes ?");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiongroup'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionGroupView.java'
height='400px'
/>
<!-- vale on -->

### Mode multiple {#multiple-mode}

Le mode multiple permet à n'importe quel nombre de panneaux de rester déployés simultanément. Cela est utile lorsque les utilisateurs ont besoin de comparer le contenu de plusieurs sections à la fois ou lorsque chaque panneau est assez court pour que l'expansion de plusieurs à la fois ne crée pas une mise en page encombrée.

```java
accordion.setMultiple(true);
```

Avec le mode multiple actif, tous les panneaux du groupe peuvent être déployés ou réduits à la fois à l'aide des méthodes de masse :

```java
// Déployer chaque panneau dans le groupe
accordion.openAll();

// Réduire chaque panneau dans le groupe
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java'
height='500px'
/>
<!-- vale on -->

:::info Restriction du mode simple
`openAll()` n'est disponible que lorsque le mode multiple est activé. L'appeler en mode simple n'a aucun effet. `closeAll()` fonctionne dans les deux modes.
:::

<!-- vale off -->
## État désactivé {#disabled-state}
<!-- vale on -->

Les panneaux individuels peuvent être désactivés pour empêcher l'interaction utilisateur tout en restant visibles. Cela est pratique pendant les états de chargement ou lorsque certaines sections ne sont pas disponibles de manière conditionnelle, montrant la structure du panneau sans permettre une interaction prématurée. Un panneau désactivé qui était déjà ouvert reste déployé, mais son en-tête ne peut plus être cliqué pour le réduire. Désactiver le groupe `Accordion` applique l'état désactivé à tous les panneaux contenus en même temps, donc vous n'avez pas besoin de parcourir les panneaux individuellement.

```java
// Désactiver un seul panneau
panel.setEnabled(false);

// Désactiver tous les panneaux dans le groupe
accordion.setEnabled(false);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiondisabled'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionDisabledView.java'
height='600px'
/>
<!-- vale on -->

## Personnalisation des panneaux {#customizing-panels}

Au-delà des étiquettes et du comportement d'ouverture/fermeture de base, chaque `AccordionPanel` prend en charge une personnalisation plus riche de son contenu d'en-tête et de l'icône d'expansion/contraction.

### En-tête personnalisé {#custom-header}

L'en-tête d'un panneau rend son label sous forme de texte brut par défaut. Utilisez `addToHeader()` pour remplacer ce texte par n'importe quel composant ou combinaison de composants, facilitant ainsi l'inclusion d'icônes, de badges, d'indicateurs d'état ou d'autres balises riches à côté du label du panneau. Cela est particulièrement utile dans les tableaux de bord ou les panneaux de paramètres où chaque en-tête de section doit transmettre un contexte supplémentaire d'un coup d'œil, comme un compte d'éléments, un badge d'avertissement ou un état de complétion, sans nécessiter que l'utilisateur étende d'abord le panneau.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("En-tête personnalisé avec icône"));
panel.addToHeader(headerContent);
```

:::info Remplacement de l'étiquette
Le contenu ajouté via `addToHeader()` remplace complètement le texte de label par défaut. `setLabel()` et `setText()` continuent de fonctionner avec `addToHeader()`, mais puisque l'emplacement de l'en-tête prend une prépondérance visuelle, le texte du label ne sera pas affiché à moins que vous ne l'incluiez explicitement dans votre contenu nommé.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### Icône personnalisée {#custom-icon}

L'indicateur d'expansion/contraction par défaut est une chevron qui est visible à la fois dans les états ouvert et fermé. `setIcon()` le remplace par n'importe quel composant [`Icon`](/docs/components/icon), utile pour l'iconographie de marque ou lorsque une métaphore visuelle différente convient mieux au contenu. Passer `null` restaure le chevron par défaut. `getIcon()` retourne l'icône actuellement définie, ou `null` si le chevron par défaut est en cours d'utilisation.

```java
// Remplacer le chevron par défaut par une icône plus
panel.setIcon(FeatherIcon.PLUS.create());

// Restaurer le chevron par défaut
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java'
height='200px'
/>
<!-- vale on -->

## Accordéons imbriqués {#nested-accordions}

Les accordéons peuvent être imbriqués à l'intérieur d'autres panneaux d'accordéon, ce qui est utile pour représenter un contenu hiérarchique tel que des paramètres catégorisés ou une navigation à plusieurs niveaux. Ajoutez un `Accordion` intérieur à un `AccordionPanel` extérieur comme n'importe quel autre composant enfant. Gardez l'imbrication peu profonde. Une ou deux niveaux sont généralement suffisants. Des hiérarchies plus profondes ont tendance à être plus difficiles à naviguer et signalent souvent que la structure du contenu elle-même a besoin d'être repensée.

```java
AccordionPanel innerA = new AccordionPanel("Panneau intérieur A");
AccordionPanel innerB = new AccordionPanel("Panneau intérieur B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Panneau extérieur");
outer.add(innerAccordion);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionnested'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java'
height='550px'
/>
<!-- vale on -->

## Événements {#events}

`AccordionPanel` déclenche des événements à chaque étape du cycle de basculement. Les trois types d'événements couvrent différents moments, donc choisissez en fonction de quand votre logique doit s'exécuter :

| Événement | Se déclenche |
|-----------|--------------|
| `AccordionPanelToggleEvent` | Avant que l'état ne change |
| `AccordionPanelOpenEvent` | Après que le panneau soit entièrement ouvert |
| `AccordionPanelCloseEvent` | Après que le panneau soit entièrement fermé |

```java
panel.onToggle(e -> {
    // Se déclenche avant que le panneau change d'état.
    // e.isOpened() reflète l'état vers lequel on se transforme, et non l'état actuel.
    String direction = e.isOpened() ? "ouverture" : "fermeture";
});

panel.onOpen(e -> {
    // Se déclenche après que le panneau soit entièrement ouvert — bon pour charger du contenu paresseusement.
});

panel.onClose(e -> {
    // Se déclenche après que le panneau soit entièrement fermé — bon pour le nettoyage ou les mises à jour de résumé.
});
```

## Style {#styling}

<TableBuilder name="AccordionPanel" />
