---
sidebar_position: 20
title: Rendering
slug: rendering
sidebar_class_name: new-content
_i18n_hash: c6f33a66de68ddcd600382bf0dc449f2
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

Un renderer contrôle comment chaque cellule dans une colonne est affichée. Au lieu de montrer une valeur brute, un renderer transforme les données de chaque cellule en texte stylisé, icônes, badges, liens, boutons d'action ou tout autre visuel qui rend les données plus faciles à lire et à traiter.

Le rendu se fait entièrement dans le navigateur. Le serveur envoie des données brutes et le client gère la présentation, ce qui rend la 'Table' rapide, quelle que soit le nombre de lignes.

Attribuez un renderer à une colonne en utilisant `setRenderer()`. Le renderer s'applique uniformément à chaque cellule de cette colonne :

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderers vs. value providers
Si vous avez uniquement besoin de transformer ou de formater une valeur de cellule sans produire de structure DOM, utilisez un [value provider](/docs/components/table/columns#value-providers) à la place. Les renderers créent des éléments DOM supplémentaires pour chaque ligne rendue, ce qui implique un coût en temps de rendu. Réservez les renderers pour une sortie visuelle telle que des icônes, des badges, des boutons, ou toute présentation basée sur HTML.
:::

webforJ est livré avec des renderers intégrés pour les cas d'utilisation les plus courants. Pour tout besoin spécifique à votre application, étendez `Renderer` et implémentez `build()` pour renvoyer une chaîne de modèle lodash qui s'exécute dans le navigateur pour chaque cellule.

## Renderers communs {#common-renderers}

Les exemples suivants passent en revue quatre renderers fréquemment utilisés et démontrent le modèle `setRenderer()` en pratique.

### TextRenderer {#text-renderer}

Affiche le contenu de la cellule comme texte brut ou stylisé. Appliquez une couleur de thème ou une décoration de texte à une colonne sans changer sa structure, comme mettre en surbrillance un champ de priorité en rouge ou rendre un identifiant clé en gras.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Enveloppe la valeur de la cellule dans un élément de badge. Supporte les thèmes, les nuances, la coloration automatique (couleurs distinctes par valeur unique), et une icône facultative en tête. Utilisez-le pour des valeurs catégorielles telles que des étiquettes, types, ou des identificateurs où des chips visuels distincts aident les utilisateurs à parcourir et à comparer rapidement les lignes.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Remplace les valeurs `true`, `false`, et `null` par des icônes. Utilisez-le pour toute colonne vrai/faux où une icône communique la valeur plus rapidement que du texte, comme des indicateurs de fonctionnalités, des états actifs/inactifs, ou des champs d'opt-in.

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

Formate une valeur numérique comme un montant en monnaie selon les règles du `Locale` fourni. Utilisez-le pour toute colonne monétaire où le formatage correct selon la locale (symbole, séparateurs, décimales) est important.

```java
// Dollars américains
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euros avec la locale allemande
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Rendu conditionnel {#conditional-rendering}

`ConditionalRenderer` choisit un renderer différent par cellule en fonction de la valeur de celle-ci. Les conditions sont évaluées dans l'ordre ; la première correspondance l'emporte. Un retour générique peut être défini avec `otherwise()`.

L'exemple suivant montre le rendu conditionnel appliqué à une colonne d'état de facture, switchant entre les variantes de `BadgeRenderer` en fonction de la valeur :

<!-- vale off -->
<ComponentDemo
path='/webforj/invoicelist'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/InvoiceListView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Invoice.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/InvoiceService.java']}
height='600px'
/>
<!-- vale on -->

Il fonctionne également bien pour les seuils numériques. Ce tableau de bord du serveur utilise `ConditionalRenderer` pour changer les thèmes de `ProgressBarRenderer` selon les niveaux d'utilisation CPU et de mémoire :

<!-- vale off -->
<ComponentDemo
path='/webforj/serverdashboard?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ServerDashboardView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Server.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ServerService.java']}
height='600px'
/>
<!-- vale on -->

### API Condition {#condition-api}

Les conditions sont construites avec des méthodes de fabrique statiques et peuvent être composées avec `and()`, `or()`, et `negate()`.

```java
// Égalité des valeurs
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// Comparaisons numériques
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// Boolean / vide
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

// Vérification entre colonnes
Condition.column("status").equalTo("active")

// Expression JavaScript brute
Condition.expression("cell.value % 2 === 0")
```

## Rendu composite {#composite-rendering}

`CompositeRenderer` combine plusieurs renderers côte à côte dans une seule cellule en utilisant une mise en page flexible. Utilisez-le pour associer une icône avec du texte, montrer un avatar à côté d'un nom, ou empiler un badge à côté d'un indicateur d'état.

Le répertoire des employés ci-dessous utilise un `CompositeRenderer` sur la colonne *Employee* pour afficher un avatar généré automatiquement à côté du nom de chaque employé :

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java'
height='600px'
/>
<!-- vale on -->

## Renderers personnalisés {#custom-renderers}

Lorsque aucun renderer intégré ne correspond à votre cas d'utilisation, étendez `Renderer` et implémentez `build()`. La méthode renvoie une chaîne de modèle lodash qui s'exécute dans le navigateur pour chaque cellule de la colonne, exprimée sous forme de mélange de HTML et JavaScript.

### Création d'un renderer personnalisé {#creating-a-custom-renderer}

**Étape 1 :** Étendre `Renderer` avec votre type de données de ligne.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Étape 2 :** Surchargez `build()` et renvoyez une chaîne de modèle lodash.

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

**Étape 3 :** Attribuez le renderer à une colonne.

```java
table.addColumn("rating", MusicRecord::getRating)
     .setRenderer(new RatingRenderer());
```

:::tip
Pour plus d'informations sur la syntaxe Lodash utilisée pour accéder aux informations de cellule et créer des renderers informatifs, consultez [cette section de référence](#template-reference).
:::

### Accès à plusieurs colonnes {#accessing-multiple-columns}

Utilisez `cell.row.getValue("columnId")` pour lire les colonnes voisines à l'intérieur du modèle. Cela est utile pour combiner des champs, calculer des deltas, ou faire référence à des données liées.

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

`IconButtonRenderer` et `ButtonRenderer` exposent `addClickListener()` directement. L'événement de clic donne accès à l'objet de données de la ligne via `e.getItem()`.

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

Pour les colonnes qui utilisent des renderers visuellement coûteux tels que des badges, des barres de progression, des avatars ou des composants web, activez le rendu paresseux pour améliorer les performances de défilement.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Lorsque `setLazyRender(true)` est défini sur une colonne, les cellules affichent un espace réservé léger et animé pendant que l'utilisateur fait défiler. Le contenu réel de la cellule se rend une fois le défilement arrêté. C'est un paramètre au niveau de la colonne, donc vous pouvez l'activer sélectivement uniquement pour les colonnes qui en bénéficient.

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java'
height='600px'
/>
<!-- vale on -->

:::tip Quand activer le rendu paresseux
Les renderers de cellules créent plus d'entités au sein du DOM, ce qui signifie plus de travail CPU pendant le rendu, peu importe quel renderer le crée.

Le rendu paresseux peut aider à réduire l'impact sur la performance si un renderer est vraiment nécessaire. Si vous avez seulement besoin de changer ou de formater la valeur, et que vous ne créez pas un DOM complexe, utilisez un value provider à la place pour transformer la valeur.
:::

## Référence des renderers intégrés {#built-in-renderers}

webforJ est livré avec un ensemble complet de renderers pour les cas d'utilisation les plus courants. Attribuez-en un à une colonne en utilisant `column.setRenderer(renderer)`.

<!-- vale off -->
<ComponentDemo
path='/webforj/productcatalog?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ProductCatalogView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Product.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ProductService.java']}
height='600px'
/>
<!-- vale on -->

### Textes et étiquettes {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  texte stylisé avec thème et décorations optionnels
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Affiche le contenu de la cellule comme texte brut ou stylisé. Supporte les couleurs de thème et les décorations de texte telles que le gras, l'italique, et le souligné.

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
<strong>BadgeRenderer</strong>  -  valeur affichée à l'intérieur d'un badge chip
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Enveloppe la valeur de la cellule dans un élément de badge. Supporte les thèmes, les nuances, la coloration automatique (couleurs distinctes par valeur unique), et une icône en tête optionnelle.

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

Rend une chaîne de remplacement configurable lorsque la valeur de la cellule est `null` ou vide ; sinon, rend la valeur telle quelle.

```java
table.addColumn("notes", MusicRecord::getNotes)
     .setRenderer(new NullRenderer<>("N/A"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Status et indicateurs {#status-and-indicators}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BooleanRenderer</strong>  -  true/false/null affiché sous forme d'icônes
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Remplace les valeurs `true`, `false`, et `null` par des icônes. Par défaut, un coche, une croix et un tiret.


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
<strong>StatusDotRenderer</strong>  -  point d'indicateur coloré à côté du texte de la cellule
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rend un petit point coloré à gauche de la valeur de la cellule. Mappez les valeurs individuelles sur les thèmes, les chaînes de couleur CSS, ou les instances de `java.awt.Color`.

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

### Nombres, monnaies, et dates {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  formatage monétaire sensible à la locale
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formate une valeur numérique comme un montant en monnaie selon les règles du `Locale` fourni.

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
<strong>PercentageRenderer</strong>  -  pourcentage avec barre de progression mini optionnelle
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Affiche une valeur numérique comme un pourcentage. Définissez le deuxième argument du constructeur sur `false` pour éviter de rendre une fine barre de progression sous le texte.

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ProgressBarRenderer</strong>  -  barre de progression pleine pour valeurs numériques
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
<strong>MaskedNumberRenderer</strong>  -  valeur numérique formatée avec un masque de nombre
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Formate une valeur numérique à l'aide d'une chaîne de modèle avec des séparateurs sensibles à la locale. `0` force un chiffre ; `#` est optionnel. Voir [règles de masque de nombre](/docs/components/fields/masked/numberfield#mask-rules) pour tous les caractères de masque pris en charge.

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

Formate une valeur de date ou d'heure en utilisant des tokens de motif : `%Mz` (mois), `%Dz` (jour), `%Yz` (année), et d'autres. Voir [règles de masque de date](/docs/components/fields/masked/datefield#mask-rules) pour tous les tokens disponibles.

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

Enveloppe la valeur de la cellule dans un ancre `mailto:`. Une icône de courrier de thème principal sert d'indicateur visuel par défaut.

```java
// Icône de courrier par défaut
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

Enveloppe la valeur de la cellule dans un ancre `tel:`. Sur mobile, appuyer ouvre le composeur. Une icône de téléphone de thème principal est affichée par défaut.

```java
// Icône de téléphone par défaut
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
<strong>AnchorRenderer</strong>  -  valeur de cellule sous forme d'hyperlien configurable
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rend un élément d'ancre cliquable. Le `href` supporte les expressions de modèle lodash afin que vous puissiez construire des URLs dynamiquement à partir de la valeur de la cellule.

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
<strong>ImageRenderer</strong>  -  image en ligne dans une cellule
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Affiche une image. L'attribut `src` prend en charge des expressions de modèle lodash afin que chaque ligne puisse montrer une image différente.

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
<strong>IconRenderer</strong>  -  icône autonome, cliquable optionnellement
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Rend une icône unique. Attachez un écouteur de clic pour un comportement interactif.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  bouton d'icône actionnable avec accès à la ligne
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Rend un bouton d'icône cliquable. L'événement de clic expose l'élément de ligne via `e.getItem()`, ce qui le rend idéal pour les actions de niveau ligne.

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

Rend n'importe quel élément HTML avec une chaîne de contenu de modèle lodash. C'est un échappatoire pour les situations où aucun renderer intégré ne convient.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Référence de modèle {#template-reference}

Les renderers offrent un mécanisme puissant pour personnaliser la manière dont les données sont affichées dans une `Table`. La classe principale, `Renderer`, est conçue pour être étendue afin de créer des renderers personnalisés basés sur des modèles lodash, permettant un rendu de contenu dynamique et interactif.

Les modèles lodash permettent l'insertion de HTML directement dans les cellules du tableau, les rendant très efficaces pour afficher des données de cellules complexes dans une `Table`. Cette approche permet la génération dynamique de HTML basée sur des données de cellules, facilitant un contenu de cellule de tableau riche et interactif.

### Syntaxe Lodash {#lodash-syntax}

La section suivante décrit les bases de la syntaxe Lodash. Bien qu'il ne s'agisse pas d'une vue exhaustive ou complète, elle peut être utilisée pour commencer à utiliser Lodash dans le composant `Table`.

#### Vue d'ensemble de la syntaxe pour les modèles lodash : {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpole des valeurs, insérant le résultat du code JavaScript dans le modèle.
- `<% ... %>` - Exécute du code JavaScript, permettant des boucles, des conditionnelles, et plus encore.
- `<%- ... %>` - Échappe le contenu HTML, garantissant que les données interpolées soient sûres contre les attaques par injection HTML.

#### Exemples utilisant les données de cellule : {#examples-using-cell-data}

**1. Interpolation de valeur simple** : affiche directement la valeur de la cellule.

`<%= cell.value %>`

**2. Rendu conditionnel** : utilisez la logique JavaScript pour rendre conditionnellement le contenu.

`<% if (cell.value > 100) { %> 'High' <% } else { %> 'Normal' <% } %>`

**3. Combinaison de champs de données** : rendre le contenu en utilisant plusieurs champs de données de la cellule.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Échappement de contenu HTML** : rendre en toute sécurité le contenu généré par l'utilisateur.

Le renderer a accès aux propriétés détaillées de cellule, de ligne et de colonne sur le côté client :

**Propriétés de TableCell :**

|Propriété	|Type	|Description|
|-|-|-|
|column|`TableColumn`|L'objet colonne associé.|
|first|`boolean`|Indique si la cellule est la première de la ligne.|
|id|`String`|L'ID de la cellule.|
|index|`int`|L'index de la cellule dans sa ligne.|
|last|`boolean`|Indique si la cellule est la dernière de la ligne.|
|row|`TableRow`|L'objet ligne associé à la cellule.|
|value|`Object`|La valeur brute de la cellule, directement issue de la source de données.|

**Propriétés de TableRow :**

|Propriété|Type|Description|
|-|-|-|
|cells|`TableCell[]`|Les cellules de la ligne.|
|data|`Object`|Les données fournies par l'application pour la ligne.|
|even|`boolean`|Indique si la ligne est numérotée pair (pour des fins de style).|
|first|`boolean`|Indique si la ligne est la première du tableau.|
|id|`String`|ID unique pour la ligne.|
|index|`int`|L'index de la ligne.|
|last|`boolean`|Indique si la ligne est la dernière du tableau.|
|odd|`boolean`|Indique si la ligne est numérotée impair (pour des fins de style).|

**Propriétés de TableColumn :**

|Propriété	|Type	|Description|
|-|-|-|
|align|ColumnAlignment|L'alignement de la colonne (gauche, centre, droite).|
|id|String|Le champ de l'objet de ligne pour obtenir les données de la cellule.|
|label|String|Le nom à afficher dans l'en-tête de la colonne.|
|pinned|ColumnPinDirection|La direction de l'épinglage de la colonne (gauche, droite, automatique).|
|sortable|boolean|Si vrai, la colonne peut être triée.|
|sort|SortDirection|L'ordre de tri de la colonne.|
|type|ColumnType|Le type de la colonne (texte, nombre, boolean, etc.).|
|minWidth|number|La largeur minimale de la colonne en pixels.|
