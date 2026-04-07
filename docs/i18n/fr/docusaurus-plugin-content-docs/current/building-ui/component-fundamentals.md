---
sidebar_position: 2
title: Understanding Components
_i18n_hash: 313ad47b29e1d9b40def363613c66f48
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Avant de construire des composants personnalisés dans webforJ, il est important de comprendre l'architecture fondamentale qui façonne le fonctionnement des composants. Cet article explique la hiérarchie des composants, l'identité des composants, les concepts de cycle de vie, et comment les interfaces de préoccupation fournissent des capacités aux composants.

## Comprendre la hiérarchie des composants {#understanding-the-component-hierarchy}

webforJ organise les composants en une hiérarchie avec deux groupes : les classes internes au framework que vous ne devez jamais étendre, et les classes conçues spécifiquement pour la création de composants personnalisés. Cette section explique pourquoi webforJ utilise la composition plutôt que l'héritage et ce que chaque niveau de la hiérarchie offre.

### Pourquoi la composition plutôt que l'extension ? {#why-composition-instead-of-extension}

Dans webforJ, les composants intégrés tels que [`Button`](../components/button) et [`TextField`](../components/fields/textfield) sont des classes finales - vous ne pouvez pas les étendre :

```java
// Cela ne fonctionnera pas dans webforJ
public class MyButton extends Button {
  // Button est final - ne peut pas être étendu 
}
```

webforJ utilise **la composition plutôt que l'héritage**. Au lieu d'étendre les composants existants, vous créez une classe qui étend `Composite` et combine des composants à l'intérieur. `Composite` agit comme un conteneur qui enveloppe un composant unique (appelé le composant lié) et vous permet d'ajouter vos propres composants et comportements.

```java
public class SearchBar extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private TextField searchField;
  private Button searchButton;
  
  public SearchBar() {
    searchField = new TextField("Recherche");
    searchButton = new Button("Go");
    
    self.setDirection(FlexDirection.ROW)
      .add(searchField, searchButton);
  }
}
```

### Pourquoi vous ne pouvez pas étendre les composants intégrés {#why-you-cant-extend-built-in-components}

Les composants webforJ sont marqués comme finaux pour maintenir l'intégrité du composant web côté client sous-jacent. Étendre les classes de composants webforJ donnerait un contrôle sur le composant web sous-jacent, ce qui pourrait entraîner des conséquences indésirables et briser la cohérence et la prévisibilité du comportement des composants.

Pour une explication détaillée, voir [Classes final et restrictions d'extension](https://docs.webforj.com/docs/architecture/controls-components#final-classes-and-extension-restrictions) dans la documentation d'architecture.

### La hiérarchie des composants {#the-component-hierarchy}

<div style={{textAlign: 'center'}}>
```mermaid
graph TD
  A[Composant<br/><small>Base abstraite - interne au framework</small>]
  
  A --> B[DwcComponent<br/><small>Composants intégrés webforJ</small>]
  A --> C[Composite<br/><small>Combiner des composants webforJ</small>]
  
  B --> E[Button, TextField,<br/>DateField, ComboBox]
  
  C --> D[ElementComposite<br/><small>Envelopper des composants web</small>]
  D --> F[ElementCompositeContainer<br/><small>Composants avec emplacements</small>]
  
  style A fill:#f5f5f5,stroke:#666
  style B fill:#fff4e6,stroke:#ff9800
  style C fill:#e6ffe6,stroke:#00cc00
  style D fill:#e6f3ff,stroke:#0066cc
  style E fill:#fff4e6,stroke:#ff9800
  style F fill:#e6f3ff,stroke:#0066cc
  
  classDef userClass stroke-width:3px
  class C,D,F userClass
```
</div>

Classes pour les développeurs (utilisez celles-ci) :
- `Composite`
- `ElementComposite`
- `ElementCompositeContainer`

Classes internes du framework (ne jamais étendre directement) :
- `Component`
- `DwcComponent`

:::warning[N'oubliez jamais d'étendre `Component` ou `DwcComponent`]
N'étendez jamais `Component` ou `DwcComponent` directement. Tous les composants intégrés sont finaux. Utilisez toujours des modèles de composition avec `Composite` ou `ElementComposite`.

Tenter d'étendre `DwcComponent` générera une exception d'exécution.
:::

## Interfaces de préoccupation {#concern-interfaces}

Les interfaces de préoccupation sont des interfaces Java qui fournissent des capacités spécifiques à vos composants. Chaque interface ajoute un ensemble de méthodes liées. Par exemple, `HasSize` ajoute des méthodes pour contrôler la largeur et la hauteur, tandis que `HasFocus` ajoute des méthodes pour gérer l'état de focus.

Lorsque vous implémentez une interface de préoccupation sur votre composant, vous accédez à ces capacités sans écrire de code d'implémentation. L'interface fournit des implémentations par défaut qui fonctionnent automatiquement.

Implémenter des interfaces de préoccupation donne à vos composants personnalisés les mêmes API que les composants intégrés webforJ :

```java
// Implémentez HasSize pour obtenir automatiquement des méthodes de largeur/hauteur
public class SizedCard extends Composite<Div> implements HasSize<SizedCard> {
  private final Div self = getBoundComponent();
  
  public SizedCard() {
    self.setText("Contenu de la carte");
  }
  
  // Pas besoin de les implémenter - vous les obtenez gratuitement :
  // setWidth(), setHeight(), setSize()
}

// Utilisez-le comme tout composant webforJ
SizedCard card = new SizedCard();
card.setWidth("300px")
  .setHeight("200px");
```

Le composite transmet automatiquement ces appels au `Div` sous-jacent. Aucun code supplémentaire n'est nécessaire.

**Interfaces de préoccupation courantes :**
- `HasSize` - `setWidth()`, `setHeight()`, `setSize()`
- `HasFocus` - `focus()`, `setFocusable()`, événements de focus
- `HasClassName` - `addClassName()`, `removeClassName()`
- `HasStyle` - `setStyle()`, gestion des CSS en ligne
- `HasVisibility` - `setVisible()`, capacité à montrer/masquer
- `HasText` - `setText()`, gestion du contenu textuel
- `HasAttribute` - `setAttribute()`, gestion des attributs HTML

:::warning
Si le composant sous-jacent ne prend pas en charge la capacité de l'interface, vous obtiendrez une exception d'exécution. Fournissez votre propre implémentation dans ce cas.
:::

Pour une liste complète des interfaces de préoccupation disponibles, consultez le [JavaDoc de webforJ](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/concern/package-summary.html).

## Vue d'ensemble du cycle de vie du composant {#component-lifecycle-overview}

webforJ gère automatiquement le cycle de vie du composant. Le framework prend en charge la création, l'attachement et la destruction des composants sans nécessiter d'intervention manuelle.

**Hooks de cycle de vie** sont disponibles lorsque vous en avez besoin :
- `onDidCreate()` - Appelé après que le composant soit attaché au DOM
- `onDidDestroy()` - Appelé lorsque le composant est détruit

Ces hooks sont **optionnels**. Utilisez-les lorsque vous en avez besoin :
- Nettoyer les ressources (arrêter les intervalles, fermer les connexions)
- Initialiser des composants qui nécessitent une attache au DOM
- Intégrer avec JavaScript côté client

Pour la plupart des cas simples, vous pouvez initialiser des composants directement dans le constructeur. Utilisez des hooks de cycle de vie comme `onDidCreate()` pour différer le travail lorsque cela est nécessaire.
