---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 99f4482faa552334ce209b3f9296f4f5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

Le composant `Accordion` fournit un ensemble empilé verticalement de panneaux collapsibles. Chaque panneau a un en-tête cliquable qui bascule la visibilité de son contenu. Un `AccordionPanel` peut être utilisé comme une section de divulgation autonome ou groupé à l'intérieur d'un `Accordion` pour coordonner le comportement d'expansion et de contraction entre plusieurs panneaux.

<!-- INTRO_END -->

:::tip Quand utiliser un accordéon
Les accordéons fonctionnent bien pour les FAQs, les pages de paramètres et les flux étape par étape où révéler tout le contenu en même temps créerait une mise en page écrasante. Si les sections sont également importantes et que les utilisateurs bénéficient de les voir simultanément, envisagez plutôt des [onglets](/docs/components/tabbedpane).
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` est le bloc de construction principal du système d'accordéon. Passez une chaîne de texte d'étiquette au constructeur pour définir le texte de l'en-tête, puis ajoutez des composants enfants pour peupler le corps. Un panneau fonctionne de manière autonome sans groupe `Accordion` autour, ce qui en fait un widget de divulgation léger utile lorsque vous avez juste besoin d'une seule section collapsible. Le constructeur sans argument est également disponible si vous préférez configurer le panneau entièrement après la construction.

```java
// Étiquette seulement - ajoutez le contenu du corps séparément
AccordionPanel panel = new AccordionPanel("Titre de la section");
panel.add(new Paragraph("Le contenu du corps va ici."));

// Étiquette et contenu du corps passés directement au constructeur
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

Contrôlez l'état ouvert/fermé par programmation à tout moment. `isOpened()` est utile lorsque vous avez besoin de lire l'état actuel avant de décider quoi faire. Par exemple, vous pourriez basculer un panneau à l'état opposé ou afficher ou masquer conditionnellement d'autres parties de l'interface utilisateur.

```java
// Développer le panneau
panel.open();

// Réduire le panneau
panel.close();                    

// Retourne vrai si actuellement développé
boolean isOpen = panel.isOpened();
```

Utilisez `setLabel()` pour mettre à jour le texte de l'en-tête après construction. `setText()` est un alias pour la même opération, donc l'étiquette peut être synchronisée avec des données dynamiques :

```java
panel.setLabel("Étiquette mise à jour");
```

## Groupes d'accordéons {#accordion-groups}

Envelopper plusieurs instances `AccordionPanel` à l'intérieur d'un `Accordion` crée un groupe coordonné. Par défaut, le groupe utilise le **mode unique** : ouvrir un panneau réduit automatiquement tous les autres, ne gardant qu'une seule section visible à la fois. Ce paramètre par défaut est intentionnel, il garde l'utilisateur concentré sur un seul élément de contenu et évite que la page ne devienne visuellement écrasante lorsque les panneaux ont un contenu substantiel.

Les panneaux sont construits indépendamment et passés au `Accordion`, vous pouvez donc configurer chacun avant de les regrouper. Plusieurs instances `Accordion` séparées peuvent également exister sur la même page—chaque groupe gère son propre état indépendamment, donc développer un panneau dans un groupe n'a aucun effet sur un autre.

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

Le mode multiple permet à un nombre quelconque de panneaux de rester développés simultanément. Cela est utile lorsque les utilisateurs doivent comparer le contenu de plusieurs sections en même temps, ou lorsque chaque panneau est assez court pour que l'expansion de plusieurs à la fois ne crée pas une mise en page encombrée.

```java
accordion.setMultiple(true);
```

Avec le mode multiple actif, tous les panneaux dans le groupe peuvent être développés ou réduits en une seule fois en utilisant les méthodes de groupe :

```java
// Développez chaque panneau du groupe
accordion.openAll();

// Réduisez chaque panneau du groupe
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java'
height='500px'
/>
<!-- vale on -->

:::info Restriction du mode unique
`openAll()` n'est disponible que lorsque le mode multiple est activé. L'appeler en mode unique n'a aucun effet. `closeAll()` fonctionne dans les deux modes.
:::

<!-- vale off -->
## État désactivé {#disabled-state}
<!-- vale on -->

Les panneaux individuels peuvent être désactivés pour empêcher l'interaction de l'utilisateur tout en restant visibles. Cela est pratique pendant les états de chargement ou lorsque certaines sections sont conditionnellement indisponibles, montrant la structure du panneau sans permettre une interaction prématurée. Un panneau désactivé qui était déjà ouvert reste développé, mais son en-tête ne peut plus être cliqué pour le réduire. Désactiver le groupe `Accordion` applique l'état désactivé à tous les panneaux contenus en même temps, de sorte que vous n'ayez pas besoin de passer en boucle à travers les panneaux individuellement.

```java
// Désactiver un seul panneau
panel.setEnabled(false);

// Désactiver tous les panneaux du groupe
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

L'en-tête d'un panneau affiche son étiquette en tant que texte brut par défaut. Utilisez `addToHeader()` pour remplacer ce texte par n'importe quel composant ou combinaison de composants, rendant simple l'inclusion d'icônes, de badges, d'indicateurs de statut, ou d'autres balisages riches aux côtés de l'étiquette du panneau. Ceci est particulièrement utile dans les tableaux de bord ou les panneaux de paramètres où chaque en-tête de section doit transmettre un contexte supplémentaire d'un coup d'œil, comme un compte d'éléments, un badge d'avertissement, ou un état de réalisation, sans nécessiter que l'utilisateur développe d'abord le panneau.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("En-tête personnalisé avec icône"));
panel.addToHeader(headerContent);
```

:::info Remplacement de l'étiquette
Le contenu ajouté via `addToHeader()` remplace complètement le texte d'étiquette par défaut. `setLabel()` et `setText()` continuent de fonctionner aux côtés de `addToHeader()`, mais puisque le slot d'en-tête prend la priorité visuelle, le texte de l'étiquette ne sera pas affiché à moins que vous ne l'incluez explicitement dans votre contenu slotted.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### Icône personnalisée {#custom-icon}

L'indicateur d'expansion/contraction est par défaut un chevron qui est visible dans les états ouvert et fermé. `setIcon()` le remplace par n'importe quel composant [`Icon`](/docs/components/icon), utile pour une iconographie de marque ou lorsque une métaphore visuelle différente convient mieux au contenu. Passer `null` restaure le chevron par défaut. `getIcon()` retourne l'icône actuellement définie, ou `null` si le chevron par défaut est utilisé.

```java
// Remplacer le chevron par défaut par une icône plus
panel.setIcon(FeatherIcon.PLUS.create());

// Rétablir le chevron par défaut
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

Les accordéons peuvent être imbriqués à l'intérieur d'autres panneaux d'accordéon, ce qui est utile pour représenter un contenu hiérarchique tel que des paramètres catégorisés ou une navigation multi-niveaux. Ajoutez un `Accordion` intérieur à un `AccordionPanel` extérieur comme n'importe quel autre composant enfant. Gardez l'imbrication peu profonde. Un ou deux niveaux sont généralement suffisants. Des hiérarchies plus profondes tendent à être plus difficiles à naviguer et signalent souvent que la structure du contenu elle-même doit être réexaminée.

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

| Événement | Se produit |
|-------|-------|
| `AccordionPanelToggleEvent` | Avant que l'état change |
| `AccordionPanelOpenEvent` | Après que le panneau soit complètement ouvert |
| `AccordionPanelCloseEvent` | Après que le panneau soit complètement fermé |

```java
panel.onToggle(e -> {
    // Se produit avant que le panneau change d'état.
    // e.isOpened() reflète l'état vers lequel on passe, pas l'état actuel.
    String direction = e.isOpened() ? "ouverture" : "fermeture";
});

panel.onOpen(e -> {
    // Se produit après que le panneau est complètement ouvert — bon pour charger du contenu paresseux.
});

panel.onClose(e -> {
    // Se produit après que le panneau est complètement fermé — bon pour le nettoyage ou les mises à jour de résumé.
});
```

## Style {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
