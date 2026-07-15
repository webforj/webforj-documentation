---
sidebar_position: 1
title: Accordion
description: >-
  Group collapsible panels with the Accordion and AccordionPanel components to
  toggle visibility and coordinate expand or collapse behavior.
_i18n_hash: b11e2d2ef8854f757454635c984da1d6
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

Le composant `Accordion` fournit un ensemble de panneaux superposés verticalement et réductibles. Chaque panneau a un en-tête cliquable qui bascule la visibilité de son contenu. Un `AccordionPanel` peut être utilisé comme une section de divulgation autonome, ou regroupé à l'intérieur d'un `Accordion` pour coordonner le comportement d'expansion et de réduction entre plusieurs panneaux.

<!-- INTRO_END -->

:::tip Quand utiliser un accordéon
Les accordéons fonctionnent bien pour les FAQ, les pages de paramètres et les flux étape par étape où révéler tout le contenu à la fois créerait une mise en page écrasante. Si les sections sont également importantes et que les utilisateurs bénéficient de les voir simultanément, envisagez plutôt [des onglets](/docs/components/tabbedpane).
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` est le bloc de construction de base du système d'accordéon. Passez une chaîne de caractères label au constructeur pour définir le texte de l'en-tête, puis ajoutez des composants enfants pour remplir le corps. Un panneau fonctionne de manière autonome sans groupe `Accordion` environnant, ce qui en fait un widget de divulgation léger utile lorsque vous avez juste besoin d'une seule section réductible. Le constructeur sans argument est également disponible lorsque vous préférez configurer le panneau entièrement après sa construction.

```java
// Label uniquement - ajouter le contenu du corps séparément
AccordionPanel panel = new AccordionPanel("Titre de la section");
panel.add(new Paragraph("Le contenu du corps va ici."));

// Label et contenu du corps passés directement dans le constructeur
AccordionPanel panel = new AccordionPanel("Titre", new Paragraph("Contenu du corps."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java']}
height='550px'
/>
<!-- vale on -->

### Ouverture et fermeture {#opening-and-closing}

Contrôlez l'état ouvert/fermé par programme à tout moment. `isOpened()` est utile lorsque vous devez lire l'état actuel avant de décider quoi faire. Par exemple, vous pourriez basculer un panneau à l'état opposé ou afficher ou masquer conditionnellement d'autres parties de l'UI.

```java
// Développer le panneau
panel.open();

// Réduire le panneau
panel.close();

// Renvoie true si actuellement développé
boolean isOpen = panel.isOpened();
```

Utilisez `setLabel()` pour mettre à jour le texte de l'en-tête après la construction. `setText()` est un alias pour la même opération, donc le label peut être synchronisé avec des données dynamiques :

```java
panel.setLabel("Label mis à jour");
```

## Groupes d'accordéons {#accordion-groups}

Enveloppant plusieurs instances `AccordionPanel` à l'intérieur d'un `Accordion` crée un groupe coordonné. Par défaut, le groupe utilise le **mode unique** : ouvrir un panneau réduit automatiquement tous les autres, ne laissant qu'une seule section visible à la fois. Ce paramètre par défaut est intentionnel, cela garde l'utilisateur concentré sur un élément de contenu et empêche la page de devenir visuellement écrasante lorsque les panneaux ont un contenu substantiel.

Les panneaux sont construits indépendamment et passés au `Accordion`, vous pouvez donc configurer chaque panneau avant de les regrouper. Plusieurs instances `Accordion` séparées peuvent également exister sur la même page—chaque groupe gère son propre état de manière indépendante, donc développer un panneau dans un groupe n'a aucun effet sur un autre.

```java
AccordionPanel panel1 = new AccordionPanel("Qu'est-ce que webforJ ?");
AccordionPanel panel2 = new AccordionPanel("Comment fonctionnent les panneaux regroupés ?");
AccordionPanel panel3 = new AccordionPanel("Puis-je avoir plusieurs groupes ?");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiongroup'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionGroupView.java']}
height='400px'
/>
<!-- vale on -->

### Mode multiple {#multiple-mode}

Le mode multiple permet à n'importe quel nombre de panneaux de rester ouverts simultanément. Cela est utile lorsque les utilisateurs ont besoin de comparer le contenu de plusieurs sections à la fois, ou lorsque chaque panneau est assez court pour que l'expansion de plusieurs à la fois ne crée pas une mise en page encombrée.

```java
accordion.setMultiple(true);
```

Avec le mode multiple actif, tous les panneaux dans le groupe peuvent être étendus ou réduits à la fois en utilisant les méthodes de masse :

```java
// Développer chaque panneau dans le groupe
accordion.openAll();

// Réduire chaque panneau dans le groupe
accordion.closeAll();
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java']}
height='575px'
/>
<!-- vale on -->

:::info Restriction du mode unique
`openAll()` n'est disponible que lorsque le mode multiple est activé. L'appeler en mode unique n'a aucun effet. `closeAll()` fonctionne dans les deux modes.
:::

<!-- vale off -->
## État désactivé {#disabled-state}
<!-- vale on -->

Les panneaux individuels peuvent être désactivés pour empêcher l'interaction de l'utilisateur tout en restant visibles. Ceci est pratique pendant les états de chargement ou lorsque certaines sections sont conditionnellement indisponibles, montrant la structure du panneau sans permettre une interaction prématurée. Un panneau désactivé qui était déjà ouvert reste ouvert, mais son en-tête ne peut plus être cliqué pour le réduire. Désactiver le groupe `Accordion` applique l'état désactivé à tous les panneaux contenus en une seule opération, donc vous n'avez pas besoin de passer en boucle à travers les panneaux individuellement.

```java
// Désactiver un seul panneau
panel.setEnabled(false);

// Désactiver tous les panneaux dans le groupe
accordion.setEnabled(false);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiondisabled'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionDisabledView.java']}
height='650px'
/>
<!-- vale on -->

## Personnalisation des panneaux {#customizing-panels}

Au-delà des labels et du comportement d'ouverture/fermeture de base, chaque `AccordionPanel` prend en charge une personnalisation plus riche tant de son contenu d'en-tête que de l'icône d'expansion/réduction.

### En-tête personnalisé {#custom-header}

L'en-tête d'un panneau rend son label sous forme de texte brut par défaut. Utilisez `addToHeader()` pour remplacer ce texte par n'importe quel composant ou combinaison de composants, rendant simple l'inclusion d'icônes, de badges, d'indicateurs de statut ou d'autres balisages riches à côté du label du panneau. Cela est particulièrement utile dans les tableaux de bord ou les panneaux de paramètres où chaque en-tête de section doit transmettre un contexte supplémentaire en un coup d'œil, tel qu'un nombre d'éléments, un badge d'avertissement ou un statut d'achèvement, sans exiger que l'utilisateur développe le panneau d'abord.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("En-tête personnalisé avec icône"));
panel.addToHeader(headerContent);
```

:::info Remplacement du label
Le contenu ajouté via `addToHeader()` remplace entièrement le texte de label par défaut. `setLabel()` et `setText()` continuent de fonctionner avec `addToHeader()`, mais puisque l'espace d'en-tête a une priorité visuelle, le texte du label ne sera pas affiché à moins que vous ne l'incluiez explicitement dans votre contenu sloté.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### Icône personnalisée {#custom-icon}

L'indicateur d'expansion/réduction par défaut est un chevron qui est visible dans les états ouverts et fermés. `setIcon()` le remplace par n'importe quel composant [`Icon`](/docs/components/icon), utile pour une iconographie de marque ou lorsque une autre métaphore visuelle correspond mieux au contenu. Passer `null` restaure le chevron par défaut. `getIcon()` renvoie l'icône actuellement définie, ou `null` si le chevron par défaut est en cours d'utilisation.

```java
// Remplacer le chevron par défaut par une icône plus
panel.setIcon(FeatherIcon.PLUS.create());

// Restaurer le chevron par défaut
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java']}
height='200px'
/>
<!-- vale on -->

## Accordions imbriqués {#nested-accordions}

Les accordéons peuvent être imbriqués dans d'autres panneaux d'accordéon, ce qui est utile pour représenter un contenu hiérarchique tel que des paramètres catégorisés ou une navigation multi-niveaux. Ajoutez un `Accordion` interne à un `AccordionPanel` externe comme n'importe quel autre composant enfant. Gardez l'imbrication peu profonde. Un ou deux niveaux sont généralement suffisants. Des hiérarchies plus profondes tendent à être plus difficiles à naviguer et signalent souvent que la structure du contenu elle-même doit être repensée.

```java
AccordionPanel innerA = new AccordionPanel("Panneau Intérieur A");
AccordionPanel innerB = new AccordionPanel("Panneau Intérieur B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Panneau Extérieur");
outer.add(innerAccordion);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionnested'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java']}
height='550px'
/>
<!-- vale on -->

## Événements {#events}

`AccordionPanel` déclenche des événements à chaque étape du cycle de vie de basculement. Les trois types d'événements couvrent différents moments, choisissez donc en fonction de quand votre logique doit s'exécuter :

| Événement | Se déclenche |
|-----------|--------------|
| `AccordionPanelToggleEvent` | Avant que l'état ne change |
| `AccordionPanelOpenEvent` | Après que le panneau a complètement ouvert |
| `AccordionPanelCloseEvent` | Après que le panneau a complètement fermé |

```java
panel.onToggle(e -> {
    // Se déclenche avant que le panneau change d'état.
    // e.isOpened() reflète l'état vers lequel on transitionne, pas l'état actuel.
    String direction = e.isOpened() ? "ouverture" : "fermeture";
});

panel.onOpen(e -> {
    // Se déclenche après que le panneau est entièrement ouvert — bon pour le chargement paresseux du contenu.
});

panel.onClose(e -> {
    // Se déclenche après que le panneau est entièrement fermé — bon pour le nettoyage ou les mises à jour de résumé.
});
```

## Style {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
