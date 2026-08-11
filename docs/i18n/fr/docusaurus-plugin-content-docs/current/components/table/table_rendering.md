---
sidebar_position: 20
title: Rendering
slug: rendering
description: >-
  Transform Table cells into text, badges, icons, links, or custom HTML with
  built-in renderers and the setRenderer method.
_i18n_hash: 314a210c06d9b920038308ed7c357f97
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

Un renderer contrôle comment chaque cellule d'une colonne est affichée. Au lieu de montrer une valeur brute, un renderer transforme les données de chaque cellule en texte stylisé, icônes, badges, liens, boutons d'action ou tout autre visuel qui rend les données plus rapides à lire et plus faciles à agir.

Le rendu se fait entièrement dans le navigateur. Le serveur envoie des données brutes et le client gère la présentation, ce qui rend la 'Table' rapide, quelle que soit le nombre de lignes.

Attribuez un renderer à une colonne en utilisant `setRenderer()`. Le renderer s'applique uniformément à chaque cellule de cette colonne :

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderers vs. value providers
Si vous avez uniquement besoin de transformer ou formater une valeur de cellule sans produire de structure DOM, utilisez un [value provider](/docs/components/table/columns#value-providers) à la place. Les renderers créent des éléments DOM supplémentaires pour chaque ligne rendue, ce qui entraîne un coût au moment du rendu. Réservez les renderers pour la sortie visuelle comme les icônes, les badges, les boutons, ou toute présentation basée sur HTML.
:::

webforJ est livré avec des renderers intégrés pour les cas d'utilisation les plus courants. Pour tout ce qui est spécifique à votre application, étendez `Renderer` et implémentez `build()` pour retourner une chaîne de modèle lodash qui s'exécute dans le navigateur pour chaque cellule.

## Renderers courants {#common-renderers}

Les exemples suivants traversent quatre renderers fréquemment utilisés et démontrent le modèle `setRenderer()` dans la pratique.

### TextRenderer {#text-renderer}

Affiche le contenu de la cellule en texte brut ou stylisé. Appliquez une couleur de thème ou une décoration de texte à une colonne sans changer sa structure, comme surligner un champ de priorité en rouge ou rendre un identifiant clé en gras.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Enveloppe la valeur de la cellule dans un élément badge. Supporte les thèmes, les étendues, le semis de couleur (couleurs distinctes automatiques par valeur unique) et une icône de tête facultative. Utilisez-le pour des valeurs catégorielles telles que des tags, types ou labels où des puces visuelles distinctes aident les utilisateurs à scanner et à comparer rapidement les lignes.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Remplace les valeurs `true`, `false` et `null` par des icônes. Utilisez-le pour toute colonne vrai/faux où une icône communique la valeur plus rapidement qu'un texte, comme les indicateurs de fonctionnalités, les états actif/inactif ou les champs d'opt-in.

```java
// Icônes par défaut
BooleanRenderer<Task> renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Icônes personnalisées
BooleanRenderer<Task> custom = new BooleanRenderer<>(
  TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
  TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);
table.addColumn("completed", Task::isCompleted).setRenderer(custom);
```

### CurrencyRenderer {#currency-renderer}

Formate une valeur numérique en montant monétaire selon les règles de la `Locale` fournie. Utilisez-le pour toute colonne monétaire où le formatage correct de la locale (symbole, séparateurs, décimales) est important.

```java
// Dollars américains
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euros avec la locale allemande
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Rendu conditionnel {#conditional-rendering}

`ConditionalRenderer` sélectionne un renderer différent par cellule en fonction de la valeur de la cellule. Les conditions sont évaluées dans l'ordre ; la première correspondance est gagnante. Un fallback global peut être défini avec `otherwise()`.

L'exemple suivant montre le rendu conditionnel appliqué à une colonne de statut de facture, changeant entre les variantes de `BadgeRenderer` en fonction de la valeur :


<!-- vale off -->
<ComponentDemo
path='/webforj/invoicelist'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/InvoiceListView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Invoice.java',
  'src/main/java/com/webforj/samples/views/table/renderers/InvoiceService.java',
]}
height='600px'
/>
<!-- vale on -->

Il fonctionne également bien pour les seuils numériques. Ce tableau de bord serveur utilise `ConditionalRenderer` pour changer les thèmes `ProgressBarRenderer` en fonction des niveaux d'utilisation du CPU et de la mémoire :

<!-- vale off -->
<ComponentDemo
path='/webforj/serverdashboard'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/ServerDashboardView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Server.java',
  'src/main/java/com/webforj/samples/views/table/renderers/ServerService.java',
]}
height='600px'
/>
<!-- vale on -->

### API de condition {#condition-api}

Les conditions sont construites avec des méthodes de fabrique statiques et peuvent être composées avec `and()`, `or()`, et `negate()`.

```java
// Égalité de valeur
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// Comparaisons numériques
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// Booléen / vide
Condition.isTrue()
Condition.isFalse()
Condition.isEmpty()

// Correspondance de chaîne
Condition.contains("error")
Condition.containsIgnoreCase("warn")

// Composition
Condition.greaterThan(0).and(Condition.lessThan(100))
Condition.isEmpty().or(Condition.equalTo("N/A"))
Condition.isTrue().negate()

// Vérification inter-colonnes
Condition.column("status").equalTo("active")

// Expression JavaScript brute
Condition.expression("cell.value % 2 === 0")
```

## Rendu composite {#composite-rendering}

`CompositeRenderer` combine plusieurs renderers côte à côte dans une seule cellule en utilisant une mise en page flex. Utilisez-le pour associer une icône à du texte, montrer un avatar à côté d'un nom, ou empiler un badge à côté d'un indicateur de statut.

Le répertoire des employés ci-dessous utilise un `CompositeRenderer` sur la colonne *Employee* pour afficher un avatar généré automatiquement à côté du nom de chaque employé :

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory'
files={['src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java']}
height='600px'
/>
<!-- vale on -->

## Renderers personnalisés {#custom-renderers}

Lorsque aucun renderer intégré ne correspond à votre cas d'utilisation, étendez `Renderer` et implémentez `build()`. La méthode retourne une chaîne de modèle lodash qui s'exécute dans le navigateur pour chaque cellule de la colonne, exprimée comme un mélange de HTML et de JavaScript.

### Création d'un renderer personnalisé {#creating-a-custom-renderer}

**Étape 1 :** Étendez `Renderer` avec votre type de données de ligne.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Étape 2 :** Remplacez `build()` et retournez une chaîne de modèle lodash.

```java
  @Override
  public String build() {
    return /* html */"""
      <%
        const rating = Number(cell.value);
        const stars  = Math.round(Math.min(Math.max(rating, 0), 5));
        const full   = '★'.repeat(stars);
        const empty  = '☆'.repeat(5 - stars);
      %>
      <span><%= full %><%= empty %></span>
      <span style="color: var(--dwc-color-body-text)">(<%= rating.toFixed(1) %>)</span>
    """;
  }
}
```

**Étape 3 :** Assignez le renderer à une colonne.

```java
table.addColumn("rating", MusicRecord::getRating)
     .setRenderer(new RatingRenderer());
```

:::tip
Pour plus d'informations sur la syntaxe Lodash utilisée pour accéder à des informations de cellule et créer des renderers informatifs, consultez [cette section de référence](#template-reference).
:::

### Accès à plusieurs colonnes {#accessing-multiple-columns}

Utilisez `cell.row.getValue("columnId")` pour lire les colonnes des frères et sœurs à l'intérieur du modèle. Cela est utile pour combiner des champs, calculer des deltas, ou faire des références croisées de données connexes.

```java
public class ArtistAvatarRenderer extends Renderer<MusicRecord> {
  @Override
  public String build() {
    return /* html */"""
      <%
        const name     = cell.row.getValue("artist");
        const initials = name
          ? name.split(' ').map(w => w.charAt(0)).join('').substring(0, 2).toUpperCase()
          : '?';
      %>
      <div style="display: flex; align-items: center; gap: 8px;">
        <div style="width: 28px; height: 28px; border-radius: 50%;
          background: var(--dwc-color-primary); color: white;
          display: flex; align-items: center; justify-content: center;
          font-size: 11px; font-weight: 600;">
          <%= initials %>
        </div>
        <span><%= name %></span>
      </div>
    """;
  }
}
```

### Événements de clic {#click-events}

`IconButtonRenderer` et `ButtonRenderer` exposent `addClickListener()` par défaut. L'événement de clic fournit un accès à l'objet de données de la ligne via `e.getItem()`.

```java
IconButtonRenderer<MusicRecord> deleteBtn = new IconButtonRenderer<>(
  TablerIcon.create("trash").setTheme(Theme.DANGER)
);
deleteBtn.addClickListener(e -> {
  MusicRecord record = e.getItem();
  repository.delete(record);
  table.refresh();
});

table.addColumn("delete", r -> "").setRenderer(deleteBtn);
```

## Performance : rendu paresseux <DocChip chip='since' label='25.12' /> {#lazy-rendering}

Pour les colonnes qui utilisent des renderers visuellement coûteux tels que les badges, les barres de progression, les avatars ou les composants web, activez le rendu paresseux pour améliorer les performances de défilement.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Lorsque `setLazyRender(true)` est activé sur une colonne, les cellules affichent un espace réservé animé léger pendant que l'utilisateur fait défiler. Le contenu réel de la cellule se rend une fois que le défilement s'arrête. C'est un paramètre au niveau de la colonne, vous pouvez donc l'activer sélectivement pour les colonnes qui en bénéficient.

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender'
files={['src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java']}
height='600px'
/>
<!-- vale on -->

:::tip Quand activer le rendu paresseux
Les renderers de cellule créent plus d'entités dans le DOM, ce qui signifie plus de travail CPU lors du rendu, quel que soit le renderer qui le crée.

Le rendu paresseux peut aider à réduire l'impact sur les performances si un renderer est vraiment nécessaire. Si vous n'avez besoin que de changer ou de formater la valeur, et que vous ne créez pas un DOM complexe, utilisez un value provider à la place pour transformer la valeur.
:::

## Référence des renderers intégrés {#built-in-renderers}

webforJ est livré avec un ensemble complet de renderers pour les cas d'utilisation les plus courants. Assignez-en n'importe lequel à une colonne en utilisant `column.setRenderer(renderer)`.

<!-- vale off -->
<ComponentDemo
path='/webforj/productcatalog'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/ProductCatalogView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Product.java',
  'src/main/java/com/webforj/samples/views/table/renderers/ProductService.java',
]}
height='600px'
/>
<!-- vale on -->

### Texte et étiquettes {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  texte stylisé avec thème et décorations facultatifs
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Affiche le contenu de la cellule sous forme de texte brut ou stylisé. Supporte les couleurs de thème et les décorations de texte telles que le gras, l'italique, et le souligné.

```java
TextRenderer renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BadgeRenderer</strong>  -  valeur affichée à l'intérieur d'une puce badge
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Enveloppe la valeur de la cellule dans un élément badge. Supporte les thèmes, les étendues, le semis de couleur (couleurs distinctes automatiques par valeur unique), et une icône de tête facultative.

```java
BadgeRenderer renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>NullRenderer</strong>  -  espace réservé pour les valeurs nulles ou vides
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rend une chaîne de repli configurable lorsque la valeur de la cellule est `null` ou vide ; sinon, rend la valeur telle quelle.

```java
table.addColumn("notes", MusicRecord::getNotes)
     .setRenderer(new NullRenderer<>("N/A"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Statuts et indicateurs {#status-and-indicators}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BooleanRenderer</strong>  -  true/false/null affiché sous forme d'icônes
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Remplace les valeurs `true`, `false`, et `null` par des icônes. Par défaut, c'est une coche, une croix, et un tiret.

```java
// Icônes par défaut
BooleanRenderer renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Icônes personnalisées
BooleanRenderer custom = new BooleanRenderer<>(
  TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
  TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>StatusDotRenderer</strong>  -  point indicateur coloré à côté du texte de la cellule
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rend un petit point coloré à gauche de la valeur de la cellule. Mappez les valeurs individuelles avec des thèmes, des chaînes de couleur CSS, ou des instances `java.awt.Color`.

```java
StatusDotRenderer renderer = new StatusDotRenderer<>();
renderer.addMapping("Active",    Theme.SUCCESS);
renderer.addMapping("Pending",   Theme.WARNING);
renderer.addMapping("Cancelled", Theme.DANGER);

table.addColumn("status", Order::getStatus).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Nombres, monnaie et dates {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  formatage monétaire conscient de la locale
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formate une valeur numérique en montant monétaire selon les règles de la `Locale` fournie.

```java
// Dollars américains
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euros avec la locale allemande
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PercentageRenderer</strong>  -  pourcentage avec barre de progression minimale facultative
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Affiche une valeur numérique en tant que pourcentage. Définissez le deuxième argument du constructeur sur `false` pour éviter de rendre une barre de progression fine sous le texte.

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ProgressBarRenderer</strong>  -  barre de progression pleine pour des valeurs numériques
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rend une barre de progression pleine avec des limites minimales et maximales configurables, un mode indéterminé, et un affichage rayé ou animé. Utilisez `setText()` avec une expression lodash pour superposer un texte personnalisé sur la barre.

```java
ProgressBarRenderer renderer = new ProgressBarRenderer<>();
renderer.setMax(100);
renderer.setTheme(Theme.SUCCESS);
renderer.setTextVisible(true);
renderer.setText("<%= cell.value %>/100");

table.addColumn("progress", Task::getProgress).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedTextRenderer</strong>  -  chaîne formatée avec un masque de texte
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Applique un masque de caractère à une valeur de chaîne. `#` correspond à tout chiffre ; les caractères littéraux sont préservés. Voir [règles de masque de texte](/docs/components/fields/masked/textfield#mask-rules) pour tous les caractères de masque pris en charge.

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  valeur numérique formatée avec un masque numérique
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formate une valeur numérique à l'aide d'une chaîne de modèle avec des séparateurs conscients de la locale. `0` force un chiffre ; `#` est facultatif. Voir [règles de masque numérique](/docs/components/fields/masked/numberfield#mask-rules) pour tous les caractères de masque pris en charge.

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  valeur date/heure avec un masque de date
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formate une valeur de date ou d'heure à l'aide de jetons de modèle : `%Mz` (mois), `%Dz` (jour), `%Yz` (année) et d'autres. Voir [règles de masque de date](/docs/components/fields/masked/datefield#mask-rules) pour tous les jetons disponibles.

```java
table.addColumn("released", MusicRecord::getReleaseDate)
     .setRenderer(new MaskedDateTimeRenderer<>("%Mz/%Dz/%Yz"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Liens et médias {#links-and-media}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>EmailRenderer</strong>  -  adresse email sous forme de lien mailto cliquable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Enveloppe la valeur de la cellule dans un lien `mailto:`. Une icône mail à thème primaire sert d'indice visuel par défaut.

```java
// Icône mail par défaut
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>());

// Icône personnalisée
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>(TablerIcon.create("at")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PhoneRenderer</strong>  -  numéro de téléphone sous forme de lien tel cliquable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Enveloppe la valeur de la cellule dans un lien `tel:`. Sur mobile, toucher ouvre le composeur. Une icône de téléphone à thème primaire est affichée par défaut.

```java
// Icône téléphone par défaut
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>());

// Icône personnalisée
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>(TablerIcon.create("device-mobile")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AnchorRenderer</strong>  -  valeur de cellule sous forme de lien hypertexte configurable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rend un élément d'ancre cliquable. Le `href` supporte des expressions de modèle lodash afin que vous puissiez construire des URL dynamiquement à partir de la valeur de cellule.

```java
AnchorRenderer renderer = new AnchorRenderer<>();
renderer.setHref("https://www.google.com/search?q=<%= cell.value %>");
renderer.setTarget("_blank");

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ImageRenderer</strong>  -  image intégrée dans une cellule
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Affiche une image. L'attribut `src` supporte des expressions de modèle lodash afin que chaque ligne puisse afficher une image différente.

```java
ImageRenderer renderer = new ImageRenderer<>();
renderer.setSrc("https://placehold.co/40x40?text=<%= cell.value %>");
renderer.setAlt("Cover");

table.addColumn("cover", MusicRecord::getArtist).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Personnes et avatars {#people-and-avatars}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AvatarRenderer</strong>  -  avatar avec initiales générées automatiquement
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rend un composant avatar. Les initiales sont automatiquement dérivées de la valeur de la cellule. Supporte les thèmes et une icône de repli.

```java
AvatarRenderer renderer = new AvatarRenderer<>();
renderer.setTheme(AvatarTheme.PRIMARY);
renderer.setIcon(TablerIcon.create("user"));

table.addColumn("artist", MusicRecord::getArtist).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Icônes et actions {#icons-and-actions}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconRenderer</strong>  -  icône autonome, éventuellement cliquable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Rend une seule icône. Attachez un écouteur de clic pour un comportement interactif.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  bouton icon actionnable avec accès à la ligne
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rend un bouton icon cliquable. L'événement de clic expose l'élément de ligne via `e.getItem()`, ce qui le rend idéal pour les actions au niveau de la ligne.

```java
IconButtonRenderer renderer = new IconButtonRenderer<>(TablerIcon.create("edit"));
renderer.addClickListener(e -> openEditor(e.getItem()));

table.addColumn("actions", r -> "").setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ButtonRenderer</strong>  -  bouton thématisé dans une cellule
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Rend un composant `Button` complet à l'intérieur de la cellule.

```java
ButtonRenderer renderer = new ButtonRenderer<>("Edit");
renderer.setTheme(ButtonTheme.PRIMARY);
renderer.addClickListener(e -> openEditor(e.getItem()));

table.addColumn("edit", r -> "Edit").setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ElementRenderer</strong>  -  élément HTML brut avec contenu lodash
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Rend n'importe quel élément HTML avec une chaîne de contenu de modèle lodash. C'est la solution de secours pour les situations où aucun renderer intégré ne convient.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Référence de modèle {#template-reference}

Les renderers offrent un mécanisme puissant pour personnaliser la manière dont les données sont affichées au sein d'une `Table`. La classe principale, `Renderer`, est conçue pour être étendue afin de créer des renderers personnalisés basés sur des modèles lodash, permettant un rendu de contenu dynamique et interactif.

Les modèles lodash permettent l'insertion de HTML directement dans les cellules du tableau, ce qui les rend très efficaces pour rendre des données de cellule complexes dans une `Table`. Cette approche permet la génération dynamique de HTML basée sur les données de cellule, facilitant un contenu de cellule de tableau riche et interactif.

### Syntaxe Lodash {#lodash-syntax}

La section suivante décrit les bases de la syntaxe Lodash. Bien qu'il ne s'agisse pas d'une vue d'ensemble exhaustive ou complète, elle peut être utilisée pour aider à commencer à utiliser Lodash au sein du composant `Table`.

#### Vue d'ensemble de la syntaxe pour les modèles lodash : {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpole des valeurs, insérant le résultat du code JavaScript dans le modèle.
- `<% ... %>` - Exécute du code JavaScript, permettant des boucles, des conditionnelles, et plus encore.
- `<%- ... %>` - Échappe le contenu HTML, s'assurant que les données interpolées sont à l'abri des attaques par injection HTML.

#### Exemples utilisant les données de cellule : {#examples-using-cell-data}

**1. Interpolation de valeur simple** : afficher directement la valeur de la cellule.

`<%= cell.value %>`

**2. Rendu conditionnel** : utiliser la logique JavaScript pour rendre conditionnellement le contenu.

`<% if (cell.value > 100) { %> 'High' <% } else { %> 'Normal' <% } %>`

**3. Combinaison de champs de données** : rendre du contenu en utilisant plusieurs champs de données de la cellule.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Échappement de contenu HTML** : rendre en toute sécurité du contenu généré par l'utilisateur.

Le renderer a accès à des propriétés détaillées de la cellule, de la ligne et de la colonne côté client :

**Propriétés de TableCell :**

|Propriété	|Type	|Description|
|-|-|-|
|column|`TableColumn`|L'objet de colonne associé.|
|first|`boolean`|Indique si la cellule est la première de la ligne.|
|id|`String`|L'ID de la cellule.|
|index|`int`|L'index de la cellule dans sa ligne.|
|last|`boolean`|Indique si la cellule est la dernière de la ligne.|
|row|`TableRow`|L'objet de ligne associé à la cellule.|
|value|`Object`|La valeur brute de la cellule, directement à partir de la source de données.|

**Propriétés de TableRow :**

|Propriété|Type|Description|
|-|-|-|
|cells|`TableCell[]`|Les cellules dans la ligne.
|data|`Object`|Les données fournies par l'application pour la ligne.
|even|`boolean`|Indique si la ligne est numérotée paire (pour des raisons de style).
|first|`boolean`|Indique si la ligne est la première dans le tableau.
|id|`String`|ID unique pour la ligne.
|index|`int`|L'index de la ligne.
|last|`boolean`|Indique si la ligne est la dernière dans le tableau.
|odd|`boolean`|Indique si la ligne est numérotée impaire (pour des raisons de style).

**Propriétés de TableColumn :**

|Propriété	|Type	|Description|
|-|-|-|
|align|ColumnAlignment|L'alignement de la colonne (gauche, centre, droite).
|id|String|Le champ de l'objet de ligne pour obtenir les données de la cellule.
|label|String|Le nom à afficher dans l'en-tête de colonne.
|pinned|ColumnPinDirection|La direction de fixation de la colonne (gauche, droite, automatique).
|sortable|boolean|Si vrai, la colonne peut être triée.
|sort|SortDirection|L'ordre de tri de la colonne.
|type|ColumnType|Le type de la colonne (texte, nombre, booléen, etc.).
|minWidth|number|La largeur minimale de la colonne en pixels.
