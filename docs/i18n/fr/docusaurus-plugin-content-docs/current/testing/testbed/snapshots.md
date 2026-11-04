---
sidebar_position: 5
title: Instantanés
description: >-
  Compare everything a view shows with a stored text description of it, read
  that description, and add the state of your own components to it.
_i18n_hash: 2f709890e8d1fd70a61debc1de3ae63f
---
Vérifier une vue élément par élément demande une assertion pour chaque libellé, bouton et champ. Un instantané vérifie tout en une seule fois. Il consigne tout ce qu’un utilisateur verrait dans la vue, une ligne par composant, et enregistre cette description à côté du test. À chaque exécution ultérieure, la vue est décrite à nouveau puis comparée à l’instantané : tout changement dans ce que la page affiche fait échouer le test, et le changement apparaît sous la forme d’un diff en texte brut lors de la revue de code.

<!-- INTRO_END -->

## Comparer un instantané {#matching-a-snapshot}

Le récapitulatif suivant affiche un numéro de commande, un champ de note, une ligne de remise masquée, un bouton **Pay** avec une icône et un bouton **Cancel** désactivé :

```java title="OrderSummary.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.TablerIcon;

public class OrderSummary extends Composite<Div> {

  public OrderSummary() {
    Paragraph order = new Paragraph("Order 1042");
    TextField note = new TextField("Note")
        .setPlaceholder("Delivery instructions");
    Paragraph discount = new Paragraph("Discount applied");
    discount.setVisible(false);
    Button pay = new Button("Pay", ButtonTheme.PRIMARY);
    pay.setPrefixComponent(TablerIcon.create("credit-card"));
    Button cancel = new Button("Cancel");
    cancel.setEnabled(false);

    getBoundComponent().add(order, note, discount, pay, cancel);
  }
}
```

Le test ajoute le récapitulatif et le vérifie avec `matchesSnapshot()` :

```java title="OrderSummaryTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.samples.views.testbed.OrderSummary;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class OrderSummaryTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var summary = new OrderSummary();
    context.add(summary);

    verifyThat(summary).matchesSnapshot();
  }
}
```

La première exécution écrit la description dans un fichier et réussit. Toutes les exécutions suivantes échouent si la vue ne correspond plus au fichier enregistré :

```snapshot title="OrderSummaryTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- OrderSummary [composite=Div] [node=div]:
  - Paragraph "Order 1042" [composite=Element] [node=p]
  - TextField [label=Note] [placeholder="Delivery instructions"]
  - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
  - Button "Pay" [theme=primary]:
    - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
  - Button "Cancel" [disabled]
```

`matchesSnapshot` est une vérification de `verifyThat`, comme celles de la page [Assertions](./assertions). Elle fonctionne donc sur n’importe quel composant, et pas uniquement sur une vue.

:::tip Quand utiliser les instantanés
Un instantané fige une vue entière. Utilisez-le pour les vues dont le contenu ne devrait pas changer, et vérifiez les faits isolés, comme le texte d’un bouton après un clic, avec des [assertions](./assertions).
:::

## Lire un instantané {#reading-a-snapshot}

Chaque ligne de l’[instantané enregistré](#matching-a-snapshot) décrit un composant. Les composants qu’il contient sont affichés en dessous, avec une indentation de deux espaces, et une ligne qui en contient d’autres se termine par deux-points. Une ligne se lit dans cet ordre :

1. Le type du composant, par exemple `Button`.
2. Le texte entre guillemets doubles, lorsque le composant en affiche un, par exemple `"Pay"`.
3. Les attributs au format `[key=value]`, par exemple `[theme=primary]` ou `[label=Note]`.
4. Les indicateurs au format `[key]`, par exemple `[disabled]` ou `[hidden]`.

Une ligne n’affiche que ce qui diffère de la valeur par défaut du composant, ce qui permet de lire le récapitulatif en un coup d’œil. **Cancel** porte l’indicateur `[disabled]`, tandis que le bouton **Pay**, qui est activé, n’affiche rien concernant son état. **Pay** porte `[theme=primary]`, tandis qu’un bouton ayant le thème par défaut n’affiche aucun thème. Les tailles, les styles et les noms de classe ne sont jamais affichés.

Certains attributs indiquent où se trouve un composant :

- `slot` nomme la partie de son parent qu’occupe un composant. L’icône de **Pay** se trouve avant le texte ; elle est donc indiquée par `[slot=prefix]`.
- `composite` nomme le composant auquel un `Composite` est lié, et `node` l’élément qu’il génère. Le récapitulatif est lié à un `Div` ; il est donc indiqué par `[composite=Div] [node=div]`.
- `route` nomme la route sous laquelle une vue est enregistrée.
- `hidden` signale un composant masqué, comme la ligne de remise. Masquer ou afficher un composant modifie l’instantané.

Le texte est toujours entre guillemets. La valeur d’un attribut est entre guillemets lorsqu’elle contient autre chose que des lettres, des chiffres ou les caractères `.`, `_`, `+`, `/` et `-`, comme dans `[placeholder="Delivery instructions"]`.

## Listes longues {#long-lists}

Une longue liste masquerait le reste de la vue. Les éléments d’une liste, les lignes d’un tableau et les nœuds d’un arbre sont donc affichés sur dix lignes au maximum sous leur parent. La liste suivante contient douze pays :

```java title="CountryList.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ListBox;
import java.util.List;

public class CountryList extends Composite<Div> {

  public CountryList() {
    ListBox countries = new ListBox("Country");
    for (String country :
        List.of(
            "Austria", "Belgium", "Canada", "Denmark", "Egypt", "France",
            "Germany", "Hungary",
            "India", "Japan", "Kenya", "Mexico", "Hyyan")) {
      countries.add(country);
    }

    getBoundComponent().add(countries);
  }
}
```

```java title="CountryListTest.java" showLineNumbers
@Test
void shouldMatchTheSnapshot(WebforjTestbed context) {
  var list = new CountryList();
  context.add(list);

  verifyThat(list).matchesSnapshot();
}
```

L’instantané enregistré affiche les dix premiers pays, puis une ligne indiquant les deux qui suivent :

```snapshot title="CountryListTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- CountryList [composite=Div] [node=div]:
  - ListBox [label=Country]:
    - ListItem "Austria" [key=Austria]
    - ListItem "Belgium" [key=Belgium]
    - ListItem "Canada" [key=Canada]
    - ListItem "Denmark" [key=Denmark]
    - ListItem "Egypt" [key=Egypt]
    - ListItem "France" [key=France]
    - ListItem "Germany" [key=Germany]
    - ListItem "Hungary" [key=Hungary]
    - ListItem "India" [key=India]
    - ListItem "Japan" [key=Japan]
    - … 3 more
```

Pour enregistrer davantage d’éléments, transmettez la limite dans les [options d’instantané](#snapshot-options) avec `setMaxWidth`. `matchesSnapshot(name, options)` effectue le rendu en utilisant exactement les options reçues. Le test active donc également les trois autres paramètres d’un instantané enregistré et lui attribue un nom :

```java title="CountryListTest.java" showLineNumbers
@Test
void shouldMatchTheSnapshotOfEveryCountry(WebforjTestbed context) {
  var list = new CountryList();
  context.add(list);

  verifyThat(list)
      .matchesSnapshot(
          "every-country",
          new SnapshotOptions()
              .setMaxWidth(20)
              .setBrowserState(true)
              .setHiddenComponents(true)
              .setComposites(true));
}
```

L’instantané enregistré contient les douze pays, dans un fichier portant le nom de l’instantané :

```snapshot title="CountryListTest.every-country.yml" showLineNumbers
# webforJ testbed snapshot v1
- CountryList [composite=Div] [node=div]:
  - ListBox [label=Country]:
    - ListItem "Austria" [key=Austria]
    - ListItem "Belgium" [key=Belgium]
    - ListItem "Canada" [key=Canada]
    - ListItem "Denmark" [key=Denmark]
    - ListItem "Egypt" [key=Egypt]
    - ListItem "France" [key=France]
    - ListItem "Germany" [key=Germany]
    - ListItem "Hungary" [key=Hungary]
    - ListItem "India" [key=India]
    - ListItem "Japan" [key=Japan]
    - ListItem "Kenya" [key=Kenya]
    - ListItem "Mexico" [key=Mexico]
    - ListItem "Hyyan" [key=Hyyan]
```

:::tip Tableaux et arbres
Un tableau ne compte pas les éléments de son dépôt ; il se termine donc par un simple `- …` plutôt que par un décompte. La limite s’applique séparément à chaque parent : un arbre tronque donc chaque niveau à dix nœuds.
:::

## Emplacement des instantanés {#where-snapshots-are-stored}

Les fichiers d’instantané se trouvent sous `src/test/resources/webforj-snapshots`, dans les dossiers correspondant au package de la classe de test. Chaque fichier porte le nom de la classe de test, suivi du nom de l’instantané et de `.yml` :

```text title="src/test/resources"
src/test/resources/webforj-snapshots/
  com/webforj/samples/unit/views/testbed/
    CountryListTest.shouldMatchTheSnapshot.yml
    OrderSummaryTest.shouldMatchTheSnapshot.yml
```

Les fichiers doivent être versionnés à côté des tests. La première ligne de chaque fichier indique la version du format. Si un fichier a été écrit avec une autre version, le test échoue avec un message indiquant les deux versions, plutôt qu’avec un diff.

## Nommer les instantanés {#naming-snapshots}

`matchesSnapshot()` nomme l’instantané d’après la méthode de test en cours d’exécution. L’instantané de `shouldMatchTheSnapshot` dans `OrderSummaryTest` est donc enregistré dans `OrderSummaryTest.shouldMatchTheSnapshot.yml`. Un test qui vérifie plusieurs instantanés transmet un nom à chacun avec `matchesSnapshot(name)` ; ce nom remplace alors celui de la méthode dans le nom du fichier. Un nom conserve les lettres, les chiffres, les espaces, les points, les tirets et le caractère `_` ; tout autre caractère est remplacé par un tiret. Ainsi, `save/form:v2` est enregistré sous le nom `save-form-v2`.

`matchesSnapshot(name, options)` accepte également les [`SnapshotOptions`](#snapshot-options) à utiliser pour le rendu, par exemple pour enregistrer davantage de lignes d’un tableau que la valeur par défaut.

## Quand un instantané ne correspond pas {#when-a-snapshot-doesnt-match}

Lorsque la vue ne correspond plus au fichier enregistré, la vérification échoue et affiche les changements. Après que le texte du bouton **Pay** dans le [récapitulatif de commande](#matching-a-snapshot) a été remplacé par **Pay now**, `mvn test` affiche :

```diff title="Terminal"
java.lang.AssertionError:
Snapshot <shouldMatchTheSnapshot> does not match src/test/resources/webforj-snapshots/…/OrderSummaryTest.shouldMatchTheSnapshot.yml.
First difference at line 5.
--- stored
+++ actual
@@ -3,5 +3,5 @@
   - TextField [label=Note] [placeholder="Delivery instructions"]
   - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
-  - Button "Pay" [theme=primary]:
+  - Button "Pay now" [theme=primary]:
     - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
   - Button "Cancel" [disabled]
Actual:
- OrderSummary [composite=Div] [node=div]:
  - Paragraph "Order 1042" [composite=Element] [node=p]
  - TextField [label=Note] [placeholder="Delivery instructions"]
  - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
  - Button "Pay now" [theme=primary]:
    - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
  - Button "Cancel" [disabled]
Run with -Dwebforj.testbed.updateSnapshots=true to update the snapshot, or verify more than one snapshot in the same test with explicit names.
```

## Mettre à jour les instantanés {#updating-snapshots}

Lorsqu’une modification de la vue est intentionnelle, exécutez les tests une fois avec la propriété système `webforj.testbed.updateSnapshots` définie sur `true`. Chaque instantané vérifié pendant l’exécution est réécrit et les fichiers d’instantané qui ne sont plus vérifiés par aucun test sont supprimés. Examinez le diff des fichiers avant de les valider.

```bash title="Terminal"
mvn test -Dwebforj.testbed.updateSnapshots=true
```

## Instantanés obsolètes {#obsolete-snapshots}

Après l’exécution de tous les tests d’une classe, le TestBed recherche les fichiers d’instantané de cette classe qui n’ont été vérifiés par aucun de ses tests, par exemple le fichier d’un test renommé. Un tel fichier fait échouer la classe de test avec un message qui répertorie chacun de ces fichiers et vous demande de les supprimer, ou d’exécuter les tests avec `-Dwebforj.testbed.updateSnapshots=true`, ce qui les supprime.

:::info Méthodes de test individuelles
La vérification s’exécute uniquement lorsque tous les tests de la classe ont été exécutés. Le lancement d’une seule méthode de test depuis l’IDE l’ignore.
:::

## Intégration continue {#continuous-integration}

Les instantanés sont écrits localement puis validés. Lorsque la variable d’environnement `CI` est définie sur une valeur autre que `false` ou `0`, comme c’est le cas dans la plupart des services d’intégration continue, le TestBed n’écrit jamais de fichier d’instantané :

- Si un instantané est absent, le test échoue avec un message indiquant le fichier attendu au lieu de l’écrire.
- Une exécution avec `-Dwebforj.testbed.updateSnapshots=true` échoue et vous demande de mettre à jour les instantanés localement.

## Afficher un composant {#printing-a-component}

`Snapshot.render(component)` renvoie la description d’un composant sous forme de chaîne, sans créer de fichier. Affichez-la pendant que vous écrivez un test pour voir le contenu de la page. Elle exclut l’état géré par le navigateur, comme la valeur saisie par l’utilisateur, ainsi que les composants masqués, sauf si les options reçues les incluent.

## Options d’instantané {#snapshot-options}

`SnapshotOptions` détermine le niveau de détail de la description. `setMaxWidth` définit le nombre d’éléments affichés sous un parent, soit dix par défaut. `setBrowserState` ajoute l’état géré par le navigateur, comme la valeur saisie par l’utilisateur et le focus. `setHiddenComponents` ajoute les composants masqués avec l’indicateur `hidden`, et `setComposites` ajoute `composite` et `node`.

Un instantané enregistré inclut tout : l’état du navigateur, les composants masqués et les composites. `Snapshot.render` et `new SnapshotOptions()` n’incluent aucun de ces éléments. `matchesSnapshot(name, options)` effectue le rendu en utilisant exactement les options reçues. Pour enregistrer davantage d’éléments, définissez donc une valeur `maxWidth` plus élevée et activez les trois autres paramètres. La référence <JavadocLink type="automation" location="com/webforj/automation/see/SnapshotOptions" code="true">SnapshotOptions</JavadocLink> décrit chaque paramètre.

## Instantanés de vos propres composants {#snapshots-for-your-own-components}

Un composant de votre application affiche son type, son texte et l’état de chaque [interface de préoccupation](/docs/building-ui/component-fundamentals#concern-interfaces) qu’il implémente, comme un libellé ou un thème, sans code supplémentaire. L’état connu uniquement de votre composant nécessite un `SnapshotSerializer`. Le composant d’évaluation suivant compte les étoiles sur lesquelles l’utilisateur clique et les renvoie avec `getStars()` :

```java title="StarRating.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;

public class StarRating extends Composite<Div> {
  private int stars = 0;

  public StarRating() {
    for (int i = 1; i <= 5; i++) {
      int value = i;
      getBoundComponent()
          .add(new Button(String.valueOf(i), e -> stars = value));
    }
  }

  public int getStars() {
    return stars;
  }
}
```

Le sérialiseur indique dans `getContract()` la classe du composant qu’il décrit, puis ajoute le nombre d’étoiles à la ligne du composant dans `describe` :

```java title="StarRatingSerializer.java" showLineNumbers
import com.webforj.automation.see.SnapshotNode;
import com.webforj.automation.see.SnapshotSerializer;
import com.webforj.samples.views.testbed.StarRating;

public final class StarRatingSerializer
    implements SnapshotSerializer<StarRating> {

  @Override
  public Class<StarRating> getContract() {
    return StarRating.class;
  }

  @Override
  public void describe(StarRating rating, SnapshotNode node) {
    node.setAttribute("stars", rating.getStars());
  }
}
```

Le sérialiseur est répertorié par son nom complet dans un fichier situé dans les ressources de test :

```text title="src/test/resources/META-INF/services/com.webforj.automation.see.SnapshotSerializer"
com.webforj.samples.unit.views.testbed.StarRatingSerializer
```

Il est alors utilisé pour chaque instantané de l’exécution des tests. Le test suivant clique sur quatre étoiles et vérifie l’instantané :

```java title="StarRatingSnapshotTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.samples.views.testbed.StarRating;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class StarRatingSnapshotTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var rating = new StarRating();
    context.add(rating);

    UserInteraction.click(
        ComponentLocator.of(Button.class).byText("4").locate());

    verifyThat(rating).matchesSnapshot();
  }
}
```

La ligne de l’évaluation indique le nombre d’étoiles :

```snapshot title="StarRatingSnapshotTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- StarRating [composite=Div] [node=div] [stars=4]:
  - Button "1"
  - Button "2"
  - Button "3"
  - Button "4"
  - Button "5"
```

En plus de `setAttribute`, le `SnapshotNode` reçu par un sérialiseur définit le texte et les indicateurs de la ligne, et ajoute des lignes en dessous pour les parties du composant, comme les colonnes d’un tableau et ses lignes. Un sérialiseur ne lit l’état géré par le navigateur que si `isBrowserState()` renvoie `true`. La référence <JavadocLink type="automation" location="com/webforj/automation/see/SnapshotNode" code="true">SnapshotNode</JavadocLink> répertorie toutes les méthodes.

Un sérialiseur de votre application s’exécute après ceux de webforJ. L’attribut qu’il définit remplace donc la valeur définie par webforJ pour la même clé. Deux sérialiseurs pour la même classe de composant, ou deux sérialiseurs qui écrivent le même attribut sur la même ligne, font échouer l’instantané avec un message indiquant les deux classes.
