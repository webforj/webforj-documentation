---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 2bf90130b3a767840e2604045504ee91
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

Le composant `Accordion` fournit un ensemble de panneaux empilés verticalement qui peuvent être repliés. Chaque panneau a un en-tête cliquable qui bascule la visibilité de son contenu. Un `AccordionPanel` peut être utilisé comme une section de divulgation autonome, ou regroupé à l'intérieur d'un `Accordion` pour coordonner le comportement de déploiement et de repliement à travers plusieurs panneaux.

<!-- INTRO_END -->

:::tip Quand utiliser un accordéon
Les accordéons fonctionnent bien pour les FAQ, les pages de paramètres et les flux étape par étape où révéler tout le contenu à la fois créerait une mise en page écrasante. Si les sections sont également importantes et que les utilisateurs bénéficient de les voir simultanément, envisagez plutôt des [onglets](/docs/components/tabbedpane).
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` est le bloc de construction central du système d'accordéon. Passez une chaîne d'étiquetage au constructeur pour définir le texte de l'en-tête, puis ajoutez des composants enfants pour peupler le corps. Un panneau fonctionne de manière autonome sans aucun groupe `Accordion` environnant, ce qui en fait un widget de divulgation léger utile lorsque vous avez seulement besoin d'une seule section repliable. Le constructeur sans argument est également disponible lorsque vous préférez configurer le panneau entièrement après la construction.

```java
// Étiquette uniquement - ajouter le contenu du corps séparément
AccordionPanel panel = new AccordionPanel("Titre de la section");
panel.add(new Paragraph("Le contenu du corps va ici."));

// Étiquette et contenu du corps passés directement dans le constructeur
AccordionPanel panel = new AccordionPanel("Titre", new Paragraph("Contenu du corps."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
height='550px'
/>
<!-- vale on -->

### Ouverture et fermeture {#opening-and-closing}

Contrôlez l'état ouvert/fermé par programme à tout moment. `isOpened()` est utile lorsque vous avez besoin de lire l'état actuel avant de décider quoi faire. Par exemple, vous pouvez basculer un panneau à l'état opposé ou afficher conditionnellement d'autres parties de l'interface utilisateur.

```java
// Développer le panneau
panel.open();

// Réduire le panneau
panel.close();                    

// Retourne true si actuellement développé
boolean isOpen = panel.isOpened();
```

Utilisez `setLabel()` pour mettre à jour le texte de l'en-tête après la construction. `setText()` est un alias pour la même opération, donc l'étiquette peut être synchronisée avec des données dynamiques :

```java
panel.setLabel("Étiquette mise à jour");
```

## Groupes d'accordéon {#accordion-groups}

Enveloppant plusieurs instances `AccordionPanel` à l'intérieur d'un `Accordion` crée un groupe coordonné. Par défaut, le groupe utilise le **mode unique** : ouvrir un panneau réduit automatiquement tous les autres, ne gardant qu'une seule section visible à la fois. Ce paramètre par défaut est intentionnel, il garde l'utilisateur concentré sur un morceau de contenu et empêche la page de devenir visuellement écrasante lorsque les panneaux ont un contenu substantiel.

Les panneaux sont construits indépendamment et passés à l'`Accordion`, ce qui permet de configurer chacun avant de les regrouper. Plusieurs instances `Accordion` séparées peuvent également exister sur la même page, chaque groupe gérant son propre état de manière indépendante, donc développer un panneau dans un groupe n'a aucun effet sur un autre.

```java
AccordionPanel panel1 = new AccordionPanel("Qu'est-ce que webforJ?");
AccordionPanel panel2 = new AccordionPanel("Comment fonctionnent les panneaux regroupés?");
AccordionPanel panel3 = new AccordionPanel("Puis-je avoir plusieurs groupes?");

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

Le mode multiple permet à tout nombre de panneaux de rester développés simultanément. Ceci est utile lorsque les utilisateurs ont besoin de comparer le contenu de plusieurs sections à la fois, ou lorsque chaque panneau est suffisamment court pour que le déploiement de plusieurs à la fois ne crée pas une mise en page encombrée.

```java
accordion.setMultiple(true);
```

Avec le mode multiple actif, tous les panneaux du groupe peuvent être développés ou repliés en une seule fois à l'aide des méthodes de masse :

```java
// Développer chaque panneau dans le groupe
accordion.openAll();

// Réduire chaque panneau dans le groupe
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java'
height='575px'
/>
<!-- vale on -->

:::info Restriction du mode unique
`openAll()` n'est disponible que lorsque le mode multiple est activé. L'appeler en mode unique n'a aucun effet. `closeAll()` fonctionne dans les deux modes.
:::

<!-- vale off -->
## État désactivé {#disabled-state}
<!-- vale on -->

Les panneaux individuels peuvent être désactivés pour empêcher l'interaction utilisateur tout en restant visibles. C'est pratique pendant les états de chargement ou lorsque certaines sections ne sont pas disponibles de manière conditionnelle, montrant la structure du panneau sans permettre une interaction prématurée. Un panneau désactivé qui était déjà ouvert reste étendu, mais son en-tête ne peut plus être cliqué pour le réduire. Désactiver le groupe `Accordion` applique l'état désactivé à tous les panneaux contenus en une seule fois, donc vous n'avez pas besoin de parcourir les panneaux individuellement.

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
height='650px'
/>
<!-- vale on -->

## Personnalisation des panneaux {#customizing-panels}

Au-delà des étiquettes et du comportement de déploiement/repliement de base, chaque `AccordionPanel` prend en charge une personnalisation plus riche tant de son contenu d'en-tête que de l'icône de déploiement/repliement.

### En-tête personnalisé {#custom-header}

L'en-tête d'un panneau rend son étiquette en tant que texte brut par défaut. Utilisez `addToHeader()` pour remplacer ce texte par n'importe quel composant ou combinaison de composants, rendant facile l'inclusion d'icones, de badges, d'indicateurs de statut, ou d'autres marquages riches aux côtés de l'étiquette du panneau. Ceci est particulièrement utile dans les tableaux de bord ou les panneaux de paramètres où chaque en-tête de section doit transmettre du contexte supplémentaire d'un coup d'œil, tel qu'un compteur d'éléments, un badge d'avertissement, ou un statut de complétion, sans nécessiter que l'utilisateur développe d'abord le panneau.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("En-tête personnalisé avec icône"));
panel.addToHeader(headerContent);
```

:::info Remplacement d'étiquette
Le contenu ajouté via `addToHeader()` remplace entièrement le texte d'étiquette par défaut. `setLabel()` et `setText()` continuent de fonctionner avec `addToHeader()`, mais puisque l'emplacement d'en-tête prend une priorité visuelle, le texte de l'étiquette ne sera pas affiché à moins que vous ne l'incluez explicitement dans votre contenu inséré.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### Icône personnalisée {#custom-icon}

L'indicateur de déploiement/repliement par défaut est un chevron qui est visible dans les états ouverts et fermés. `setIcon()` le remplace par n'importe quel composant [`Icon`](/docs/components/icon), utile pour l'iconographie de marque ou lorsque une métaphore visuelle différente correspond mieux au contenu. Passer `null` restaure le chevron par défaut. `getIcon()` retourne l'icône actuellement définie, ou `null` si le chevron par défaut est utilisé.

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

Les accordéons peuvent être imbriqués à l'intérieur d'autres panneaux d'accordéon, ce qui est utile pour représenter un contenu hiérarchique tel que des paramètres catégorisés ou une navigation à plusieurs niveaux. Ajoutez un `Accordion` interne à un `AccordionPanel` externe comme n'importe quel autre composant enfant. Gardez l'imbrication peu profonde. Une ou deux niveaux suffisent généralement. Des hiérarchies plus profondes ont tendance à être plus difficiles à naviguer et signalent souvent que la structure du contenu elle-même doit être repensée.

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
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java'
height='550px'
/>
<!-- vale on -->

## Événements {#events}

`AccordionPanel` déclenche des événements à chaque étape du cycle de basculement. Les trois types d'événements couvrent différents moments, donc choisissez en fonction de quand votre logique doit s'exécuter :

| Événement | Déclenche |
|-----------|-----------|
| `AccordionPanelToggleEvent` | Avant que l'état ne change |
| `AccordionPanelOpenEvent` | Après que le panneau s'est complètement ouvert |
| `AccordionPanelCloseEvent` | Après que le panneau s'est complètement fermé |

```java
panel.onToggle(e -> {
    // Déclenche avant que le panneau change d'état.
    // e.isOpened() reflète l'état vers lequel on passe, pas l'état actuel.
    String direction = e.isOpened() ? "ouverture" : "fermeture";
});

panel.onOpen(e -> {
    // Déclenche après que le panneau soit complètement ouvert — bon pour charger le contenu paresseusement.
});

panel.onClose(e -> {
    // Déclenche après que le panneau soit complètement fermé — bon pour le nettoyage ou les mises à jour de résumé.
});
```

## Style {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
