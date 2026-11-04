---
sidebar_position: 20
title: Rendu
slug: rendering
description: >-
  Transform Table cells into text, badges, icons, links, or custom HTML with
  built-in renderers and the setRenderer method.
_i18n_hash: 7d9beff25d6b372fb4309fe491ecad08
---
<!-- vale Google.Headings = NO -->
<!-- vale Google.Colons = NO -->
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

Un moteur de rendu contrôle l’affichage de chaque cellule d’une colonne. Au lieu d’afficher une valeur brute, il transforme les données de chaque cellule en texte stylisé, icônes, badges, liens, boutons d’action ou tout autre élément visuel qui permet de lire les données plus rapidement et de les exploiter plus facilement.

Le rendu est entièrement effectué dans le navigateur. Le serveur envoie les données brutes et le client se charge de leur présentation, ce qui permet à la `Table` de rester rapide quel que soit le nombre de lignes.

Attribuez un moteur de rendu à une colonne avec `setRenderer()`. Il s’applique uniformément à toutes les cellules de cette colonne :

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Moteurs de rendu et fournisseurs de valeurs
Si vous avez uniquement besoin de transformer ou de formater une valeur de cellule sans créer de structure DOM, utilisez plutôt un [fournisseur de valeurs](/docs/components/table/columns#value-providers). Les moteurs de rendu créent des éléments DOM supplémentaires pour chaque ligne rendue, ce qui entraîne un coût au moment du rendu. Réservez-les à la présentation visuelle, par exemple pour afficher des icônes, des badges, des boutons ou tout autre contenu HTML.
:::

webforJ fournit des moteurs de rendu intégrés pour les cas d’utilisation les plus courants. Pour les besoins propres à votre application, étendez `Renderer` et implémentez `build()` pour renvoyer une chaîne de modèle lodash exécutée dans le navigateur pour chaque cellule.

## Moteurs de rendu courants {#common-renderers}

Les exemples suivants présentent quatre moteurs de rendu fréquemment utilisés et illustrent la pratique de `setRenderer()`.

### TextRenderer {#text-renderer}

Affiche le contenu des cellules sous forme de texte brut ou stylisé. Appliquez une couleur de thème ou une décoration de texte à une colonne sans en modifier la structure, par exemple pour mettre en évidence un champ de priorité en rouge ou afficher un identifiant clé en gras.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Enveloppe la valeur de la cellule dans un élément badge. Prend en charge les thèmes, les dimensions, l’attribution automatique de couleurs distinctes à chaque valeur unique et une icône facultative en tête. Utilisez-le pour les valeurs catégorielles telles que les balises, les types ou les libellés, lorsque des pastilles visuellement distinctes aident les utilisateurs à parcourir et comparer rapidement les lignes.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Remplace les valeurs `true`, `false` et `null` par des icônes. Utilisez-le pour toute colonne booléenne où une icône communique la valeur plus rapidement qu’un texte, par exemple pour les indicateurs de fonctionnalités, les états actif/inactif ou les champs d’adhésion facultative.

```java
// Default icons
BooleanRenderer<Task> renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Custom icons
BooleanRenderer<Task> custom = new BooleanRenderer<>(
  TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
  TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);
table.addColumn("completed", Task::isCompleted).setRenderer(custom);
```

### CurrencyRenderer {#currency-renderer}

Formate une valeur numérique comme un montant monétaire selon les règles du `Locale` fourni. Utilisez-le pour toute colonne monétaire où le formatage adapté aux paramètres régionaux (symbole, séparateurs, décimales) est important.

```java
// US dollars
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euros with German locale
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Rendu conditionnel {#conditional-rendering}

`ConditionalRenderer` sélectionne un moteur de rendu différent pour chaque cellule en fonction de sa valeur. Les conditions sont évaluées dans l’ordre ; la première correspondance est retenue. Une condition de repli couvrant tous les cas peut être définie avec `otherwise()`.


L’exemple suivant montre un rendu conditionnel appliqué à une colonne d’état de facture, où différentes variantes de `BadgeRenderer` sont utilisées selon la valeur :


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

Cette approche convient également aux seuils numériques. Ce tableau de bord de serveur utilise `ConditionalRenderer` pour changer les thèmes de `ProgressBarRenderer` en fonction des niveaux d’utilisation du processeur et de la mémoire :

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

### API des conditions {#condition-api}

Les conditions sont créées à l’aide de méthodes de fabrique statiques et peuvent être combinées avec `and()`, `or()` et `negate()`.

```java
// Value equality
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// Numeric comparisons
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// Boolean / emptiness
Condition.isTrue()
Condition.isFalse()
Condition.isEmpty()

// String matching
Condition.contains("error")
Condition.containsIgnoreCase("warn")

// Composition
Condition.greaterThan(0).and(Condition.lessThan(100))
Condition.isEmpty().or(Condition.equalTo("N/A"))
Condition.isTrue().negate()

// Cross-column check
Condition.column("status").equalTo("active")

// Raw JavaScript expression
Condition.expression("cell.value % 2 === 0")
```

## Rendu composite {#composite-rendering}

`CompositeRenderer` associe plusieurs moteurs de rendu côte à côte dans une même cellule, à l’aide d’une disposition flex. Utilisez-le pour associer une icône à du texte, afficher un avatar à côté d’un nom ou placer un badge à côté d’un indicateur d’état.

L’annuaire des employés ci-dessous utilise `CompositeRenderer` dans la colonne *Employee* pour afficher un avatar généré automatiquement à côté du nom de chaque employé :

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Employee.java',
  'src/main/java/com/webforj/samples/views/table/renderers/EmployeeService.java',
]}
height='600px'
/>
<!-- vale on -->

## Moteurs de rendu personnalisés {#custom-renderers}

Si aucun moteur de rendu intégré ne convient à votre cas d’utilisation, étendez `Renderer` et implémentez `build()`. Cette méthode renvoie une chaîne de modèle lodash, composée de HTML et de JavaScript, qui s’exécute dans le navigateur pour chaque cellule de la colonne.

### Création d’un moteur de rendu personnalisé {#creating-a-custom-renderer}

**Étape 1 :** Étendez `Renderer` en précisant le type de données de vos lignes.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Étape 2 :** Redéfinissez `build()` et renvoyez une chaîne de modèle lodash.

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

**Étape 3 :** Attribuez le moteur de rendu à une colonne.

```java
table.addColumn("rating", MusicRecord::getRating)
     .setRenderer(new RatingRenderer());
```

:::tip
Pour en savoir plus sur la syntaxe Lodash utilisée pour accéder aux informations des cellules et créer des moteurs de rendu informatifs, consultez [cette section de référence](#template-reference).
:::

### Accès à plusieurs colonnes {#accessing-multiple-columns}

Utilisez `cell.row.getValue("columnId")` pour lire les colonnes voisines dans le modèle. Cette méthode est utile pour combiner des champs, calculer des écarts ou recouper des données associées.

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

`IconButtonRenderer` et `ButtonRenderer` proposent directement `addClickListener()`. L’événement de clic donne accès à l’objet de données de la ligne via `e.getItem()`.

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

## Performances : rendu différé <DocChip chip='since' label='25.12' /> {#lazy-rendering}

Pour les colonnes utilisant des moteurs de rendu coûteux sur le plan visuel, comme les badges, les barres de progression, les avatars ou les composants web, activez le rendu différé afin d’améliorer les performances de défilement.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Lorsque `setLazyRender(true)` est défini sur une colonne, les cellules affichent un espace réservé animé léger pendant le défilement. Le contenu réel des cellules est rendu lorsque le défilement s’arrête. Ce paramètre s’applique au niveau de la colonne : vous pouvez donc l’activer uniquement pour les colonnes concernées.

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Server.java',
  'src/main/java/com/webforj/samples/views/table/renderers/ServerService.java',
]}
height='600px'
/>
<!-- vale on -->

:::tip Quand activer le rendu différé
Les moteurs de rendu de cellule créent davantage d’entités dans le DOM, ce qui demande plus de travail au processeur pendant le rendu, quel que soit le moteur de rendu utilisé.

Le rendu différé peut contribuer à réduire l’impact sur les performances si un moteur de rendu est vraiment nécessaire. Si vous avez uniquement besoin de modifier ou de formater la valeur et que vous ne créez pas de DOM complexe, utilisez plutôt un fournisseur de valeurs pour transformer cette valeur.
:::

## Référence des moteurs de rendu intégrés {#built-in-renderers}

webforJ fournit un ensemble complet de moteurs de rendu pour les cas d’utilisation les plus courants. Attribuez n’importe lequel d’entre eux à une colonne à l’aide de `column.setRenderer(renderer)`.

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

### Texte et libellés {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  texte stylisé avec thème et décorations facultatifs
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Affiche le contenu des cellules sous forme de texte brut ou stylisé. Prend en charge les couleurs de thème et les décorations de texte telles que le gras, l’italique et le soulignement.

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
<strong>BadgeRenderer</strong>  -  valeur affichée dans une pastille
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Enveloppe la valeur de la cellule dans un élément badge. Prend en charge les thèmes, les dimensions, l’attribution automatique de couleurs distinctes à chaque valeur unique et une icône facultative en tête.

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
<strong>NullRenderer</strong>  -  valeur de remplacement pour les valeurs nulles ou vides
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Affiche une chaîne de remplacement configurable lorsque la valeur de la cellule est `null` ou vide ; sinon, affiche la valeur telle quelle.

```java
table.addColumn("notes", MusicRecord::getNotes)
     .setRenderer(new NullRenderer<>("N/A"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### États et indicateurs {#status-and-indicators}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BooleanRenderer</strong>  -  valeurs true/false/null représentées par des icônes
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Remplace les valeurs `true`, `false` et `null` par des icônes. Par défaut, il affiche une coche, une croix et un tiret.


```java
// Default icons
BooleanRenderer renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Custom icons
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

Affiche un petit point coloré à gauche de la valeur de la cellule. Associez chaque valeur à un thème, une chaîne de couleur CSS ou une instance de `java.awt.Color`.

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

### Nombres, devises et dates {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  formatage monétaire adapté aux paramètres régionaux
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formate une valeur numérique comme un montant monétaire selon les règles du `Locale` fourni.

```java
// US dollars
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euros with German locale
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PercentageRenderer</strong>  -  pourcentage avec mini-barre de progression facultative
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Affiche une valeur numérique sous forme de pourcentage. Définissez le deuxième argument du constructeur à `false` pour empêcher l’affichage d’une fine barre de progression sous le texte.

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ProgressBarRenderer</strong>  -  barre de progression complète pour les valeurs numériques
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Affiche une barre de progression pleine largeur avec des limites minimales et maximales configurables, un mode indéterminé et un affichage rayé ou animé. Utilisez `setText()` avec une expression lodash pour superposer du texte personnalisé sur la barre.

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
<strong>MaskedTextRenderer</strong>  -  chaîne formatée à l’aide d’un masque de texte
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Applique un masque de caractères à une chaîne. `#` correspond à n’importe quel chiffre ; les caractères littéraux sont conservés. Consultez [les règles des masques de texte](/docs/components/fields/masked/textfield#mask-rules) pour connaître tous les caractères de masque pris en charge.

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  valeur numérique formatée à l’aide d’un masque de nombres
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formate une valeur numérique à l’aide d’une chaîne de modèle avec des séparateurs adaptés aux paramètres régionaux. `0` impose un chiffre ; `#` est facultatif. Consultez [les règles des masques de nombres](/docs/components/fields/masked/numberfield#mask-rules) pour connaître tous les caractères de masque pris en charge.

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  valeur de date/heure formatée à l’aide d’un masque de date
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formate une valeur de date ou d’heure à l’aide de jetons de modèle : `%Mz` (mois), `%Dz` (jour), `%Yz` (année) et d’autres. Consultez [les règles des masques de date](/docs/components/fields/masked/datefield#mask-rules) pour connaître tous les jetons disponibles.

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
<strong>EmailRenderer</strong>  -  adresse e-mail sous forme de lien mailto cliquable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Enveloppe la valeur de la cellule dans une ancre `mailto:`. Par défaut, une icône de courrier utilisant le thème principal sert d’indice visuel.

```java
// Default mail icon
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>());

// Custom icon
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

Enveloppe la valeur de la cellule dans une ancre `tel:`. Sur un appareil mobile, appuyer sur le lien ouvre le composeur. Une icône de téléphone utilisant le thème principal est affichée par défaut.

```java
// Default phone icon
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>());

// Custom icon
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>(TablerIcon.create("device-mobile")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AnchorRenderer</strong>  -  valeur de cellule affichée sous forme de lien configurable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Affiche une ancre cliquable. `href` prend en charge les expressions de modèle lodash, ce qui permet de créer dynamiquement des URL à partir de la valeur de la cellule.

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

Affiche une image. L’attribut `src` prend en charge les expressions de modèle lodash, ce qui permet d’afficher une image différente pour chaque ligne.

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

Affiche un composant avatar. Les initiales sont automatiquement déduites de la valeur de la cellule. Prend en charge les thèmes et une icône de remplacement.

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
<strong>IconRenderer</strong>  -  icône seule, éventuellement cliquable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Affiche une seule icône. Ajoutez un écouteur de clic pour lui associer un comportement interactif.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  bouton d’icône avec accès à la ligne
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Affiche un bouton d’icône cliquable. L’événement de clic expose l’élément de la ligne via `e.getItem()`, ce qui en fait un choix idéal pour les actions associées à une ligne.

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

Affiche un composant `Button` complet dans la cellule.

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

Affiche n’importe quel élément HTML avec une chaîne de contenu de modèle lodash. C’est la solution de dernier recours lorsqu’aucun moteur de rendu intégré ne convient.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Référence des modèles {#template-reference}

Les moteurs de rendu offrent un mécanisme puissant pour personnaliser l’affichage des données dans une `Table`. La classe principale, `Renderer`, est conçue pour être étendue afin de créer des moteurs de rendu personnalisés à partir de modèles lodash et ainsi générer du contenu dynamique et interactif.

Les modèles lodash permettent d’insérer du HTML directement dans les cellules du tableau, ce qui les rend particulièrement efficaces pour afficher des données complexes dans une `Table`. Cette approche permet de générer dynamiquement du HTML à partir des données des cellules et de créer ainsi un contenu riche et interactif.

### Syntaxe lodash {#lodash-syntax}

La section suivante présente les bases de la syntaxe Lodash. Il ne s’agit pas d’un aperçu exhaustif ni complet, mais ces informations peuvent vous aider à commencer à utiliser Lodash avec le composant `Table`.

#### Présentation de la syntaxe des modèles lodash : {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpole des valeurs en insérant dans le modèle le résultat du code JavaScript.
- `<% ... %>` - Exécute du code JavaScript, ce qui permet d’utiliser des boucles, des conditions et bien plus encore.
- `<%- ... %>` - Échappe le contenu HTML afin de protéger les données interpolées contre les attaques par injection HTML.

#### Exemples d’utilisation des données des cellules : {#examples-using-cell-data}

**1. Interpolation simple d’une valeur** : affiche directement la valeur de la cellule.

`<%= cell.value %>`

**2. Rendu conditionnel** : utilisez la logique JavaScript pour afficher du contenu de manière conditionnelle.

`<% if (cell.value > 100) { %> 'High' <% } else { %> 'Normal' <% } %>`

**3. Combinaison de champs de données** : affiche du contenu à partir de plusieurs champs de la cellule.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Échappement du contenu HTML** : affiche en toute sécurité du contenu généré par l’utilisateur.

Le moteur de rendu a accès aux propriétés détaillées des cellules, des lignes et des colonnes côté client :

**Propriétés de TableCell :**

|Propriété	|Type	|Description|
|-|-|-|
|column|`TableColumn`|L’objet colonne associé.|
|first|`boolean`|Indique si la cellule est la première de la ligne.|
|id|`String`|L’identifiant de la cellule.|
|index|`int`|L’index de la cellule dans sa ligne.|
|last|`boolean`|Indique si la cellule est la dernière de la ligne.|
|row|`TableRow`|L’objet ligne associé à la cellule.|
|value|`Object`|La valeur brute de la cellule, directement issue de la source de données.|

**Propriétés de TableRow :**

|Propriété|Type|Description|
|-|-|-|
|cells|`TableCell[]`|Les cellules de la ligne.
|data|`Object`|Les données fournies par l’application pour la ligne.
|even|`boolean`|Indique si la ligne est paire (à des fins de style).
|first|`boolean`|Indique si la ligne est la première du tableau.
|id|`String`|Identifiant unique de la ligne.
|index|`int`|L’index de la ligne.
|last|`boolean`|Indique si la ligne est la dernière du tableau.
|odd|`boolean`|Indique si la ligne est impaire (à des fins de style).

**Propriétés de TableColumn :**

|Propriété	|Type	|Description|
|-|-|-|
|align|ColumnAlignment|L’alignement de la colonne (left, center, right).|
|id|String|Le champ de l’objet ligne depuis lequel récupérer les données de la cellule.|
|label|String|Le nom à afficher dans l’en-tête de la colonne.|
|pinned|ColumnPinDirection|Le côté où la colonne est épinglée (left, right, auto).|
|sortable|boolean|Si cette valeur est true, la colonne peut être triée.|
|sort|SortDirection|L’ordre de tri de la colonne.|
|type|ColumnType|Le type de la colonne (text, number, boolean, etc.).|
|minWidth|number|La largeur minimale de la colonne, en pixels.|
