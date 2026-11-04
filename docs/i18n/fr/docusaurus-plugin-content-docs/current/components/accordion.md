---
sidebar_position: 1
title: Accordion
description: >-
  Group collapsible panels with the Accordion and AccordionPanel components to
  toggle visibility and coordinate expand or collapse behavior.
_i18n_hash: 6537c2d24bfc99dd866f27277ed4f197
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

Le composant `Accordion` fournit un ensemble de panneaux empilés verticalement et réductibles. Chaque panneau a un en-tête cliquable qui bascule la visibilité de son contenu. Un `AccordionPanel` peut être utilisé comme une section de divulgation autonome, ou regroupé à l'intérieur d'un `Accordion` pour coordonner le comportement d'expansion et de réduction entre plusieurs panneaux.

<!-- INTRO_END -->

:::tip Quand utiliser un accordéon
Les accordéons fonctionnent bien pour les FAQ, les pages de paramètres et les flux étape par étape où révéler tout le contenu en même temps créerait une mise en page écrasante. Si les sections sont également importantes et que les utilisateurs bénéficient de les voir simultanément, envisagez plutôt les [onglets](/docs/components/tabbedpane).
:::

## `AccordionPanel` <DocChip chip='name' label="dwc-accordion-panel" /> {#accordion-panel}

`AccordionPanel` est le bloc de construction de base du système d'accordéon. Passez une chaîne étiquetée au constructeur pour définir le texte de l'en-tête, puis ajoutez des composants enfants pour peupler le corps. Un panneau fonctionne indépendamment sans groupe `Accordion` environnant, ce qui en fait un widget de divulgation léger utile lorsque vous avez juste besoin d'une seule section réductible. Le constructeur sans argument est également disponible lorsque vous préférez configurer le panneau entièrement après sa construction.

```java
// Label uniquement - ajouter le contenu du corps séparément
AccordionPanel panel = new AccordionPanel("Titre de section");
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

Contrôlez l'état ouvert/fermé par programme à tout moment. `isOpened()` est utile lorsque vous avez besoin de lire l'état actuel avant de décider quoi faire. Par exemple, vous pourriez basculer un panneau à l'état opposé ou afficher ou masquer conditionnellement d'autres parties de l'interface utilisateur.

```java
// Développer le panneau
panel.open();

// Réduire le panneau
panel.close();

// Renvoie vrai s'il est actuellement étendu
boolean isOpen = panel.isOpened();
```

Utilisez `setLabel()` pour mettre à jour le texte de l'en-tête après la construction. `setText()` est un alias pour la même opération, donc l'étiquette peut être synchronisée avec des données dynamiques :

```java
panel.setLabel("Étiquette mise à jour");
```

## Groupes d'accordéons {#accordion-groups}

Envelopper plusieurs instances de `AccordionPanel` à l'intérieur d'un `Accordion` crée un groupe coordonné. Par défaut, le groupe utilise le **mode unique** : ouvrir un panneau réduit automatiquement tous les autres, ne gardant qu'une seule section visible à la fois. Ce comportement par défaut est intentionnel, il garde l'utilisateur concentré sur un seul morceau de contenu et empêche la page de devenir visuellement écrasante lorsque les panneaux contiennent un contenu substantiel.

Les panneaux sont construits indépendamment et passés à l'`Accordion`, vous pouvez donc configurer chacun d'eux avant de les regrouper. Plusieurs instances séparées d'`Accordion` peuvent également exister sur la même page—chaque groupe gère son propre état indépendamment, donc développer un panneau dans un groupe n'a aucun effet sur un autre.

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

Le mode multiple permet à n'importe quel nombre de panneaux de rester étendus simultanément. Ceci est utile lorsque les utilisateurs ont besoin de comparer le contenu de plusieurs sections à la fois, ou lorsque chaque panneau est suffisamment court pour que l'expansion de plusieurs panneaux à la fois ne crée pas une mise en page encombrée.

```java
accordion.setMultiple(true);
```

Avec le mode multiple actif, tous les panneaux du groupe peuvent être développés ou réduits en une seule fois en utilisant les méthodes de masse :

```java
// Développer chaque panneau du groupe
accordion.openAll();

// Réduire chaque panneau du groupe
accordion.closeAll();
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java']}
height='575px'
/>
<!-- vale on -->

:::info Restriction de mode unique
`openAll()` n'est disponible que lorsque le mode multiple est activé. L'appeler en mode unique n'a aucun effet. `closeAll()` fonctionne dans les deux modes.
:::

<!-- vale off -->
## État désactivé {#disabled-state}
<!-- vale on -->

Des panneaux individuels peuvent être désactivés pour empêcher l'interaction de l'utilisateur tout en restant visibles. Cela est pratique pendant les états de chargement ou lorsque certaines sections sont conditionnellement indisponibles, montrant la structure du panneau sans permettre une interaction prématurée. Un panneau désactivé qui était déjà ouvert reste étendu, mais son en-tête ne peut plus être cliqué pour le réduire. Désactiver le groupe `Accordion` applique l'état désactivé à tous les panneaux contenus en une seule fois, donc vous n'avez pas besoin de parcourir les panneaux individuellement.

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

Au-delà des étiquettes et du comportement de base d'ouverture/fermeture, chaque `AccordionPanel` prend en charge une personnalisation plus riche de son contenu d'en-tête et de l'icône d'expansion/réduction.

### En-tête personnalisé {#custom-header}

L'en-tête d'un panneau rend son étiquette en texte brut par défaut. Utilisez `addToHeader()` pour remplacer ce texte par n'importe quel composant ou combinaison de composants, ce qui facilite l'inclusion d'icônes, de badges, d'indicateurs de statut ou d'autres balisages riches à côté de l'étiquette du panneau. Cela est particulièrement utile dans les tableaux de bord ou les panneaux de réglages où chaque en-tête de section doit transmettre un contexte supplémentaire en un coup d'œil, comme un compte d'éléments, un badge d'avertissement ou un statut d'achèvement, sans nécessiter que l'utilisateur développe d'abord le panneau.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("En-tête personnalisé avec icône"));
panel.addToHeader(headerContent);
```

:::info Remplacement d'étiquette
Le contenu ajouté via `addToHeader()` remplace entièrement le texte d'étiquette par défaut. `setLabel()` et `setText()` fonctionnent toujours en parallèle avec `addToHeader()`, mais puisque le slot de l'en-tête a la priorité visuelle, le texte d'étiquette ne sera pas affiché à moins que vous l'incluez explicitement dans votre contenu inséré.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### Icône personnalisée {#custom-icon}

L'indicateur d'expansion/réduction par défaut est un chevron visible dans les états ouvert et fermé. `setIcon()` le remplace par n'importe quel composant [`Icon`](/docs/components/icon), utile pour la signalétique de marque ou lorsque une métaphore visuelle différente convient mieux au contenu. Passer `null` restaure le chevron par défaut. `getIcon()` renvoie l'icône actuellement définie, ou `null` si le chevron par défaut est utilisé.

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

## Accordéons imbriqués {#nested-accordions}

Les accordéons peuvent être imbriqués à l'intérieur d'autres panneaux d'accordéon, ce qui est utile pour représenter un contenu hiérarchique tel que des paramètres catégorisés ou une navigation multi-niveaux. Ajoutez un `Accordion` intérieur à un `AccordionPanel` extérieur comme tout autre composant enfant. Gardez l'imbrication peu profonde. Un ou deux niveaux suffisent généralement. Des hiérarchies plus profondes ont tendance à être plus difficiles à naviguer et signalent souvent que la structure de contenu elle-même nécessite d'être repensée.

```java
AccordionPanel innerA = new AccordionPanel("Panneau interne A");
AccordionPanel innerB = new AccordionPanel("Panneau interne B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Panneau externe");
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

`AccordionPanel` déclenche des événements à chaque étape du cycle de vie du basculement. Les trois types d'événements couvrent différents moments, choisissez donc en fonction de l'endroit où votre logique doit s'exécuter :

| Événement | Déclenchement |
|-----------|---------------|
| `AccordionPanelToggleEvent` | Avant que l'état ne change |
| `AccordionPanelOpenEvent` | Après que le panneau soit complètement ouvert |
| `AccordionPanelCloseEvent` | Après que le panneau soit complètement fermé |

```java
panel.onToggle(e -> {
    // Déclenché avant que le panneau ne change d'état.
    // e.isOpened() reflète l'état vers lequel on se dirige, pas l'état actuel.
    String direction = e.isOpened() ? "ouverture" : "fermeture";
});

panel.onOpen(e -> {
    // Déclenché après que le panneau soit complètement ouvert — bon pour le chargement paresseux du contenu.
});

panel.onClose(e -> {
    // Déclenché après que le panneau soit complètement fermé — bon pour le nettoyage ou les mises à jour de résumé.
});
```

## Style {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
