---
sidebar_position: 1
title: Accordion
_i18n_hash: 207c70347cc18d88661a8a9279988417
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

Le composant `Accordion` fournit un ensemble de panneaux empilés verticalement et repliables. Chaque panneau dispose d'un en-tête cliquable qui bascule la visibilité de son contenu. Un `AccordionPanel` peut être utilisé comme une section de divulgation autonome, ou groupé à l'intérieur d'un `Accordion` pour coordonner le comportement d'expansion et de repli entre plusieurs panneaux.

<!-- INTRO_END -->

:::tip Quand utiliser un accordéon
Les accordéons fonctionnent bien pour les FAQ, les pages de paramètres et les flux étape par étape où révéler tout le contenu en même temps créerait une mise en page écrasante. Si les sections sont également importantes et que les utilisateurs bénéficient de les voir simultanément, envisagez plutôt [des onglets](/docs/components/tabbedpane).
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` est le bloc de construction essentiel du système d'accordéon. Passez une chaîne de label au constructeur pour définir le texte de l'en-tête, puis ajoutez des composants enfants pour remplir le corps. Un panneau fonctionne seul sans groupe `Accordion` environnant, ce qui en fait un widget de divulgation léger utile lorsque vous avez simplement besoin d'une seule section repliable. Le constructeur sans arguments est également disponible lorsque vous préférez configurer le panneau entièrement après sa construction.

```java
// Label seulement - ajoutez le contenu du corps séparément
AccordionPanel panel = new AccordionPanel("Titre de la Section");
panel.add(new Paragraph("Le contenu du corps va ici."));

// Label et contenu du corps transmis directement dans le constructeur
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

Contrôlez l'état ouvert/fermé par programmation à tout moment. `isOpened()` est utile lorsque vous devez lire l'état actuel avant de décider quoi faire. Par exemple, vous pourriez basculer un panneau vers l'état opposé ou afficher ou cacher conditionnellement d'autres parties de l'interface utilisateur.

```java
// Étendre le panneau
panel.open();

// Réduire le panneau
panel.close();                    

// Renvoie true s'il est actuellement étendu
boolean isOpen = panel.isOpened();
```

Utilisez `setLabel()` pour mettre à jour le texte de l'en-tête après la construction. `setText()` est un alias pour la même opération, donc le label peut être synchronisé avec des données dynamiques :

```java
panel.setLabel("Label Mis à Jour");
```

## Groupes d'accordéons {#accordion-groups}

Envelopper plusieurs instances de `AccordionPanel` à l'intérieur d'un `Accordion` crée un groupe coordonné. Par défaut, le groupe utilise le mode **simple** : l'ouverture d'un panneau réduit automatiquement tous les autres, ne laissant visible qu'une seule section à la fois. Ce comportement par défaut est intentionnel, il garde l'utilisateur concentré sur un morceau de contenu et empêche la page de devenir visuellement écrasante lorsque les panneaux ont un contenu substantiel dans le corps.

Les panneaux sont construits indépendamment et passés au `Accordion`, vous pouvez donc configurer chacun avant de les regrouper. Plusieurs instances d'`Accordion` séparées peuvent également exister sur la même page—chaque groupe gère son propre état indépendamment, donc étendre un panneau dans un groupe n'a aucun effet sur un autre.

```java
AccordionPanel panel1 = new AccordionPanel("Qu'est-ce que webforJ ?");
AccordionPanel panel2 = new AccordionPanel("Comment fonctionnent les panneaux groupés ?");
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

Le mode multiple permet à un nombre quelconque de panneaux de rester étendus simultanément. Cela est utile lorsque les utilisateurs doivent comparer le contenu de plusieurs sections à la fois, ou lorsque chaque panneau est suffisamment court pour que l'expansion de plusieurs à la fois ne crée pas une mise en page encombrée.

```java
accordion.setMultiple(true);
```

Avec le mode multiple actif, tous les panneaux du groupe peuvent être étendus ou réduits en même temps en utilisant les méthodes en masse :

```java
// Étendre chaque panneau dans le groupe
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

:::info Restriction du mode simple
`openAll()` n'est disponible que lorsque le mode multiple est activé. L'appeler en mode simple n'a aucun effet. `closeAll()` fonctionne dans les deux modes.
:::

<!-- vale off -->
## État désactivé {#disabled-state}
<!-- vale on -->

Des panneaux individuels peuvent être désactivés pour empêcher l'interaction de l'utilisateur tout en restant visibles. C'est pratique lors des états de chargement ou lorsque certaines sections ne sont pas disponibles de manière conditionnelle, montrant la structure du panneau sans autoriser une interaction prématurée. Un panneau désactivé qui était déjà ouvert reste étendu, mais son en-tête ne peut plus être cliqué pour le réduire. La désactivation du groupe `Accordion` applique l'état désactivé à tous les panneaux contenus en même temps, ainsi vous n'avez pas besoin de passer en boucle à travers chaque panneau individuellement.

```java
// Désactiver un seul panneau
panel.setEnabled(false);

// Désactiver tous les panneaux du groupe
accordion.setEnabled(false);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiondisabled'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionDisabledView.java']}
height='650px'
/>
<!-- vale on -->

## Personnaliser les panneaux {#customizing-panels}

Au-delà des étiquettes et du comportement d'ouverture/fermeture de base, chaque `AccordionPanel` prend en charge une personnalisation plus riche de son contenu d'en-tête et de l'icône d'expansion/repli.

### En-tête personnalisé {#custom-header}

L'en-tête d'un panneau rend son label en tant que texte brut par défaut. Utilisez `addToHeader()` pour remplacer ce texte par tout composant ou combinaison de composants, permettant ainsi d'inclure facilement des icônes, des badges, des indicateurs de statut ou d'autres marquages enrichis aux côtés du label du panneau. Cela est particulièrement utile dans les tableaux de bord ou les panneaux de paramètres où chaque en-tête de section doit transmettre un contexte supplémentaire d'un coup d'œil, tel qu'un nombre d'articles, un badge d'avertissement ou un statut d'achèvement, sans nécessiter que l'utilisateur développe d'abord le panneau.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("En-tête Personnalisé avec Icône"));
panel.addToHeader(headerContent);
```

:::info Remplacement de l'étiquette
Le contenu ajouté via `addToHeader()` remplace entièrement le texte d'étiquette par défaut. `setLabel()` et `setText()` continuent de fonctionner en parallèle avec `addToHeader()`, mais comme l’emplacement de l'en-tête a une priorité visuelle, le texte de l'étiquette ne sera pas affiché à moins que vous ne l'incluez explicitement dans votre contenu inclus.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### Icône personnalisée {#custom-icon}

L'indicateur d'expansion/repli par défaut est un chevron visible dans les états ouvert et fermé. `setIcon()` le remplace par tout composant [`Icon`](/docs/components/icon), utile pour une iconographie de marque ou lorsque une autre métaphore visuelle convient mieux au contenu. Passer `null` restaure le chevron par défaut. `getIcon()` renvoie l'icône actuellement définie, ou `null` si le chevron par défaut est utilisé.

```java
// Remplacer le chevron par défaut par une icône plus
panel.setIcon(FeatherIcon.PLUS.create());

// Rétablir le chevron par défaut
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java']}
height='200px'
/>
<!-- vale on -->

## Accordéons imbriqués {#nested-accordions}

Les accordéons peuvent être imbriqués à l'intérieur d'autres panneaux d'accordéon, ce qui est utile pour représenter un contenu hiérarchique tel que des paramètres catégorisés ou une navigation multi-niveaux. Ajoutez un `Accordion` interne à un `AccordionPanel` externe comme n'importe quel autre composant enfant. Gardez l'imbrication peu profonde. Un ou deux niveaux sont généralement suffisants. Des hiérarchies plus profondes tendent à être plus difficiles à naviguer et signalent souvent que la structure du contenu elle-même a besoin d'être repensée.

```java
AccordionPanel innerA = new AccordionPanel("Panneau Interne A");
AccordionPanel innerB = new AccordionPanel("Panneau Interne B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Panneau Externe");
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

`AccordionPanel` déclenche des événements à chaque étape du cycle de basculement. Les trois types d'événements couvrent différents moments, choisissez-en un en fonction de quand votre logique doit s'exécuter :

| Événement | Se déclenche |
|-------|-------|
| `AccordionPanelToggleEvent` | Avant que l'état ne change |
| `AccordionPanelOpenEvent` | Après que le panneau se soit entièrement ouvert |
| `AccordionPanelCloseEvent` | Après que le panneau se soit entièrement fermé |

```java
panel.onToggle(e -> {
    // Se déclenche avant que le panneau ne change d'état.
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

## Stylisation {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
