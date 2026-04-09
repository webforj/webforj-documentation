---
sidebar_position: 2
title: Understanding Components
_i18n_hash: 7d08b900e422fb45abcd82844c266b88
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Avant de créer des composants personnalisés dans webforJ, il est important de comprendre l'architecture fondamentale qui façonne le fonctionnement des composants. Cet article explique la hiérarchie des composants, l'identité des composants, les concepts de cycle de vie et comment les interfaces de préoccupation fournissent des capacités aux composants.

## Comprendre la hiérarchie des composants {#understanding-the-component-hierarchy}

webforJ organise les composants en une hiérarchie avec deux groupes : des classes internes au framework que vous ne devriez jamais étendre et des classes conçues spécifiquement pour créer des composants personnalisés. Cette section explique pourquoi webforJ privilégie la composition plutôt que l'héritage et ce que chaque niveau de la hiérarchie fournit.

### Pourquoi la composition plutôt que l'extension ? {#why-composition-instead-of-extension}

Dans webforJ, les composants intégrés comme [`Button`](../components/button) et [`TextField`](../components/fields/textfield) sont des classes finales—vous ne pouvez pas les étendre :

```java
// Cela ne fonctionnera pas dans webforJ
public class MyButton extends Button {
  // Button est final - ne peut pas être étendu 
}
```

webforJ utilise **la composition plutôt que l'héritage**. Au lieu d'étendre des composants existants, vous créez une classe qui étend `Composite` et combine les composants à l'intérieur. `Composite` agit comme un conteneur qui enveloppe un seul composant (appelé le composant lié) et vous permet d'ajouter vos propres composants et votre comportement à celui-ci.

```java
public class SearchBar extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private TextField searchField;
  private Button searchButton;
  
  public SearchBar() {
    searchField = new TextField("Rechercher");
    searchButton = new Button("Aller");
    
    self.setDirection(FlexDirection.ROW)
      .add(searchField, searchButton);
  }
}
```

### Pourquoi vous ne pouvez pas étendre les composants intégrés {#why-you-cant-extend-built-in-components}

Les composants webforJ sont marqués comme finaux pour maintenir l'intégrité du composant web côté client sous-jacent. Étendre les classes de composants webforJ donnerait un contrôle sur le composant web sous-jacent, ce qui pourrait entraîner des conséquences indésirables et briser la cohérence et la prévisibilité du comportement des composants.

Pour une explication détaillée, voir [Classes finales et restrictions d'extension](https://docs.webforj.com/docs/architecture/controls-components#final-classes-and-extension-restrictions) dans la documentation d'architecture.

### La hiérarchie des composants {#the-component-hierarchy}

<div style={{textAlign: 'center'}}>
```mermaid
graph TD
  A[Composant<br/><small>Base abstraite - interne au framework</small>]
  
  A --> B[DwcComponent<br/><small>Composants intégrés webforJ</small>]
  A --> C[Composite<br/><small>Combine des composants webforJ</small>]
  
  B --> E[Button, TextField,<br/>DateField, ComboBox]
  
  C --> D[ElementComposite<br/><small>Enveloppe des composants web</small>]
  D --> F[ElementCompositeContainer<br/><small>Composants avec emplacements</small>]

  classDef internal stroke-dasharray:6 4,stroke-width:1px
  classDef primary stroke-width:3px
  classDef secondary stroke-width:2px,stroke-dasharray:2 2
  class A,B,E internal
  class C primary
  class D,F secondary
```
</div>

Classes pour les développeurs (utilisez celles-ci) :
- `Composite`
- `ElementComposite`
- `ElementCompositeContainer`

Classes internes au framework (ne les étendez jamais directement) :
- `Component`
- `DwcComponent`

:::warning[Never extend `Component` or `DwcComponent`]
Ne jamais étendre `Component` ou `DwcComponent` directement. Tous les composants intégrés sont finaux. Utilisez toujours des modèles de composition avec `Composite` ou `ElementComposite`.

Toute tentative d'étendre `DwcComponent` générera une exception d'exécution.
:::

## Interfaces de préoccupation {#concern-interfaces}

Les interfaces de préoccupation sont des interfaces Java qui fournissent des capacités spécifiques à vos composants. Chaque interface ajoute un ensemble de méthodes liées. Par exemple, `HasSize` ajoute des méthodes pour contrôler la largeur et la hauteur, tandis que `HasFocus` ajoute des méthodes pour gérer l'état de focus.

Lorsque vous implémentez une interface de préoccupation sur votre composant, vous obtenez accès à ces capacités sans écrire de code d'implémentation. L'interface fournit des implémentations par défaut qui fonctionnent automatiquement.

Implémenter des interfaces de préoccupation donne à vos composants personnalisés les mêmes API que les composants intégrés webforJ :

```java
// Implémentez HasSize pour obtenir automatiquement des méthodes de largeur/hauteur
public class SizedCard extends Composite<Div> implements HasSize<SizedCard> {
  private final Div self = getBoundComponent();
  
  public SizedCard() {
    self.setText("Contenu de la carte");
  }
  
  // Pas besoin d'implémenter ceux-ci - vous les obtenez sans frais :
  // setWidth(), setHeight(), setSize()
}

// Utilisez-le comme tout composant webforJ
SizedCard card = new SizedCard();
card.setWidth("300px")
  .setHeight("200px");
```

Le composite transmet automatiquement ces appels au `Div` sous-jacent. Pas de code supplémentaire nécessaire.

### Apparence {#concern-interfaces-appearance}

Ces interfaces contrôlent la présentation visuelle d'un composant, y compris ses dimensions, sa visibilité, son style et son thème.

| Interface | Description |
|---|---|
| `HasSize` | Contrôle la largeur et la hauteur, y compris les contraintes min et max. Étend `HasWidth`, `HasHeight` et leurs variantes min/max. |
| `HasVisibility` | Affiche ou masque le composant sans le retirer de la mise en page. |
| `HasClassName` | Gère les noms de classe CSS sur l'élément racine du composant. |
| `HasStyle` | Applique et supprime les styles CSS en ligne. |
| `HasHorizontalAlignment` | Contrôle comment le contenu est aligné horizontalement à l'intérieur du composant. |
| `HasExpanse` | Définit la variante de taille du composant en utilisant les jetons d'expansion standard (`XSMALL` à `XLARGE`). |
| `HasTheme` | Applique une variante de thème telle que `DEFAULT`, `PRIMARY` ou `DANGER`. |
| `HasPrefixAndSuffix` | Ajoute des composants à l'emplacement de préfixe ou de suffixe à l'intérieur du composant. |

### Contenu {#concern-interfaces-content}

Ces interfaces gèrent ce qu'un composant affiche, y compris le texte, le HTML, les étiquettes, les indices et d'autres contenus descriptifs.

| Interface | Description |
|---|---|
| `HasText` | Définit et récupère le contenu en texte brut du composant. |
| `HasHtml` | Définit et récupère le HTML interne du composant. |
| `HasLabel` | Ajoute une étiquette descriptive associée au composant, utilisée pour l'accessibilité. |
| `HasHelperText` | Affiche un texte d'indice secondaire sous le composant. |
| `HasPlaceholder` | Définit le texte de l'espace réservé affiché lorsque le composant n'a pas de valeur. |
| `HasTooltip` | Attache une infobulle qui apparaît au survol. |

### État {#concern-interfaces-state}

Ces interfaces contrôlent l'état interactif d'un composant, y compris s'il est activé, modifiable, requis ou focus lors du chargement.

| Interface | Description |
|---|---|
| `HasEnablement` | Active ou désactive le composant. |
| `HasReadOnly` | Met le composant dans un état de lecture seule où la valeur est visible mais ne peut pas être changée. |
| `HasRequired` | Marque le composant comme requis, généralement pour la validation de formulaire. |
| `HasAutoFocus` | Déplace le focus vers le composant automatiquement lorsque la page se charge. |

### Focus {#concern-interfaces-focus}

Ces interfaces gèrent comment un composant reçoit et réagit à la mise au point du clavier.

| Interface | Description |
|---|---|
| `HasFocus` | Gère l'état de focus et si le composant peut recevoir le focus. |
| `HasFocusStatus` | Vérifie si le composant a actuellement le focus. Nécessite un aller-retour au client. |
| `HasHighlightOnFocus` | Contrôle si le contenu du composant est mis en surbrillance lorsqu'il reçoit le focus, et comment ( `KEY`, `MOUSE`, `KEY_MOUSE`, `ALL`, etc.). |

### Contraintes d'entrée {#concern-interfaces-input-constraints}

Ces interfaces définissent quelles valeurs un composant accepte, y compris la valeur actuelle, les plages autorisées, les limites de longueur, les masques de formatage et le comportement spécifique à la locale.

| Interface | Description |
|---|---|
| `HasValue` | Obtient et définit la valeur actuelle du composant. |
| `HasMin` | Définit une valeur minimale autorisée. |
| `HasMax` | Définit une valeur maximale autorisée. |
| `HasStep` | Définit l'incrément pour les entrées numériques ou de plage. |
| `HasPattern` | Applique un motif d'expression régulière pour contraindre l'entrée acceptée. |
| `HasMinLength` | Définit le nombre minimum de caractères requis dans la valeur du composant. |
| `HasMaxLength` | Définit le nombre maximum de caractères autorisés dans la valeur du composant. |
| `HasMask` | Applique un masque de format à l'entrée. Utilisé par les composants de champ masqué. |
| `HasTypingMode` | Contrôle si les caractères tapés sont insérés ou remplacent les caractères existants ( `INSERT` ou `OVERWRITE`). Utilisé par les champs masqués et `TextArea`. |
| `HasRestoreValue` | Définit une valeur à laquelle le composant se réinitialise lorsque l'utilisateur appuie sur Échap ou appelle `restoreValue()`. Utilisé par les champs masqués. |
| `HasLocale` | Stocke une locale par composant pour le formatage spécifique à la locale. Utilisé par les champs de date et d'heure masqués. |
| `HasPredictedText` | Définit une valeur de texte prédit ou d'auto-complétion. Utilisé par `TextArea` pour prendre en charge les suggestions en ligne. |

### Validation {#concern-interfaces-validation}

Ces interfaces ajoutent un comportement de validation côté client, y compris le marquage des composants comme invalides, l'affichage des messages d'erreur et le contrôle de l'exécution de la validation.

| Interface | Description |
|---|---|
| `HasClientValidation` | Marque un composant comme invalide, définit le message d'erreur et attache un validateur côté client. |
| `HasClientAutoValidation` | Contrôle si le composant valide automatiquement pendant que l'utilisateur tape. |
| `HasClientAutoValidationOnLoad` | Contrôle si le composant valide lorsqu'il se charge pour la première fois. |
| `HasClientValidationStyle` | Contrôle comment les messages de validation sont affichés : `INLINE` (en-dessous du composant) ou `POPOVER`. |

### Accès DOM {#concern-interfaces-dom-access}

Ces interfaces fournissent un accès de bas niveau à l'élément HTML sous-jacent du composant et aux propriétés côté client.

| Interface | Description |
|---|---|
| `HasAttribute` | Lit et écrit des attributs HTML arbitraires sur l'élément du composant. |
| `HasProperty` | Lit et écrit directement les propriétés du composant DWC sur l'élément client. |

### i18n {#concern-interfaces-i18n}

Cette interface fournit un support de traduction pour les composants qui doivent afficher du texte localisé.

| Interface | Description |
|---|---|
| `HasTranslation` | Fournit la méthode d'assistance `t()` pour résoudre les clés de traduction en chaînes de caractères localisées en utilisant la locale actuelle de l'application. |

:::warning
Si le composant sous-jacent ne prend pas en charge la capacité de l'interface, vous obtiendrez une exception d'exécution. Fournissez votre propre implémentation dans ce cas.
:::

Pour une liste complète des interfaces de préoccupation disponibles, consultez le [webforJ JavaDoc](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/concern/package-summary.html).

## Vue d'ensemble du cycle de vie des composants {#component-lifecycle-overview}

webforJ gère automatiquement le cycle de vie des composants. Le framework s'occupe de la création, de l'attachement et de la destruction des composants sans nécessiter d'intervention manuelle.

**Hooks de cycle de vie** sont disponibles lorsque vous en avez besoin :
- `onDidCreate(T container)` - Appelé après que le composant soit attaché au DOM
- `onDidDestroy()` - Appelé lorsque le composant est détruit

Ces hooks sont **optionnels**. Utilisez-les lorsque vous en avez besoin :
- Nettoyer les ressources (arrêter les intervalles, fermer les connexions)
- Initialiser des composants qui nécessitent une attache au DOM
- Intégrer avec JavaScript côté client

Pour la plupart des cas simples, vous pouvez initialiser les composants directement dans le constructeur. Utilisez des hooks de cycle de vie comme `onDidCreate()` pour différer le travail si nécessaire.
