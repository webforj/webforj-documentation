---
sidebar_position: 11
title: Execute JavaScript
sidebar_class_name: new-content
description: >-
  Run client-side JavaScript from Java with executeJs, executeJsAsync, and
  executeJsVoidAsync at the app or element level.
slug: execute-javascript
_i18n_hash: c1d5b030c6f39ac6c83afc05ca4bb398
---
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

webforJ s'exécute sur le serveur, mais il y a des moments où vous devez atteindre le client : faire défiler la fenêtre, mettre l'accent sur un champ, lire une valeur du navigateur ou appeler une méthode sur un composant web. L'interface <JavadocLink type="foundation" location="com/webforj/concern/HasJsExecution" code='true'>HasJsExecution</JavadocLink> fournit ce pont. Elle est implémentée à deux niveaux :

- La [`Page`](#app-level-execution) exécute le script dans le contexte de l'ensemble de la page.
- Un [`Element`](#element-level-execution) exécute le script limité à un seul élément client.

Les deux exposent les mêmes trois méthodes, donc une fois que vous connaissez les formes ci-dessous, elles se lisent de la même manière que vous les appeliez sur `Page` ou un `Element`.

## Méthodes d'exécution {#execution-methods}

Chaque niveau offre une méthode synchronisée et deux asynchrones. La différence réside dans le fait que le thread appelant attend ou non et si un résultat revient.

1. **`executeJs(String script)`** : exécute le script de manière synchronisée. Le **thread d'exécution est bloqué** jusqu'à ce que le client renvoie, ce qui coûte un aller-retour serveur-client. Le résultat revient sous forme d'`Object` que vous pouvez caster et utiliser en Java.

2. **`executeJsAsync(String script)`** : exécute le script de manière asynchrone et **ne bloque pas le thread d'exécution**. Il renvoie un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> qui se termine lorsque le script est terminé, vous permettant ainsi de réagir au résultat plus tard.

3. **`executeJsVoidAsync(String script)`** : exécute le script de manière asynchrone et ne renvoie rien au serveur. Utilisez-le pour des travaux de type "fire-and-forget" où vous n'avez pas besoin du résultat. Disponible depuis `24.11`.

:::tip Choisir une méthode
Privilégiez `executeJsVoidAsync` par défaut lorsque vous ne faites qu’entraîner un effet secondaire sur le client (défilement, mise au point, appel d'une méthode). Utilisez `executeJsAsync` lorsque vous avez besoin de la valeur mais que vous souhaitez rester non-bloquant, et réservez le synchronique `executeJs` pour les rares cas où vous devez avoir le résultat avant que la ligne suivante de Java ne s'exécute, car cela bloque le thread pour un aller-retour complet.
:::

### Lecture des résultats {#reading-results}

Lorsqu'un script renvoie une valeur, webforJ la convertit au type Java correspondant :

| Valeur JavaScript       | Type Java                           |
| ----------------------- | ----------------------------------- |
| number                  | `Integer`, `Long` ou `Double`      |
| string                  | `String`                            |
| boolean                 | `Boolean`                           |
| `null` ou `undefined`   | `null`                              |
| tout autre type         | sa représentation sous forme de chaîne |

Lisez les valeurs avec `executeJsAsync`, qui applique la conversion de manière fiable. Un nombre retourné peut arriver sous la forme d'`Integer`, `Long` ou `Double`, alors lisez-le via `Number` :

```java
Page.getCurrent()
    .executeJsAsync("return window.innerWidth;")
    .thenAccept(result -> {
      int width = ((Number) result).intValue();
      // utiliser width
    });
```

:::warning Préférez la forme asynchrone lorsque vous avez besoin de la valeur
Le synchronique `executeJs` renvoie `null` lorsque le contexte d'exécution n'est pas prêt, par exemple lorsqu'il est appelé avant que le composant ne soit attaché. Utilisez `executeJsAsync` chaque fois que vous dépendez de la valeur retournée, et évitez de caster un résultat synchronique en un type spécifique.
:::

## Exécution au niveau de l'application {#app-level-execution}

Appelez les méthodes sur <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink> lorsque le script concerne la page dans son ensemble plutôt qu'un composant. Obtenez la page actuelle avec `Page.getCurrent()`.

Un cas courant consiste à faire défiler jusqu'en haut après un changement de routage. Rien n'a besoin de revenir, donc `executeJsVoidAsync` convient :

```java
Page.getCurrent().executeJsVoidAsync(
    "window.scrollTo({ top: 0, behavior: 'smooth' });");
```

Lorsque vous avez besoin d'une valeur cliente sur le serveur, lisez-la de manière asynchrone et agissez sur le résultat lorsqu'il arrive :

```java
Page.getCurrent()
    .executeJsAsync("return navigator.language;")
    .thenAccept(language -> {
      // language est la locale du navigateur, par exemple "en-US"
      applyLocale(String.valueOf(language));
    });
```

:::info Portée de la page par rapport à l'élément
Utilisez [l'exécution au niveau de l'élément](#element-level-execution) lorsque le script doit agir sur un élément client spécifique plutôt que sur la page entière.
:::

Dans la démo ci-dessous, sélectionner **Copier le lien** exécute un script par le biais de `Page` avec `executeJsVoidAsync` pour écrire le lien d'invitation dans le presse-papiers du visiteur. Copier est un effet secondaire sans rien à retourner, donc la méthode "fire-and-forget" est la bonne option.

<ComponentDemo
path='/webforj/executejavascript'
files={[
  'src/main/java/com/webforj/samples/views/javascript/ExecuteJavaScriptView.java',
]}
height='260px'
/>

## Exécution au niveau de l'élément {#element-level-execution}

Appeler les mêmes méthodes sur un <JavadocLink type="foundation" location="com/webforj/component/element/Element" code='true'>Element</JavadocLink> limite le script à cet élément au lieu de la page. Les valeurs de retour et le comportement synchronique et asynchrone correspondent aux méthodes au niveau de la page précédentes.

Les scripts d'éléments sont mis en file d'attente jusqu'à ce que l'élément soit attaché au DOM, puis s'exécutent, donc vous pouvez les appeler pendant la configuration sans attendre l'attachement vous-même.

### Appeler une fonction sur un élément {#calling-a-function}

Lorsque vous souhaitez invoquer une fonction côté client nommée plutôt que d'exécuter une chaîne de script, `Element` offre un ensemble de méthodes parallèles. Au lieu d'un script, vous passez le nom de la fonction et ses arguments, que webforJ sérialise et passe. Deux types d'arguments sont traités de manière spéciale : `this` est remplacé par l'élément client, et tout argument `Component` est remplacé par son instance client une fois attachée.

Celles-ci reflètent les méthodes d'exécution, ne différant que par le fait que le thread attend ou non et si un résultat est retourné :

1. **`callJsFunction(String name, Object... args)`** : appelle la fonction de manière synchronisée et renvoie son résultat sous forme d'`Object`. Le thread d'exécution bloque pour un aller-retour.

2. **`callJsFunctionAsync(String name, Object... args)`** : appelle la fonction de manière asynchrone sans bloquer, renvoyant un `PendingResult` qui se termine avec le résultat de la fonction. Disponible depuis `24.11`.

3. **`callJsFunctionVoidAsync(String name, Object... args)`** : appelle la fonction de manière asynchrone et ne renvoie rien au serveur. Utilisez-le pour des appels de type "fire-and-forget" où vous n'avez pas besoin de la valeur de retour. Disponible depuis `24.11`.

Comme l'appel attend que chaque argument `Component` soit attaché avant de s'exécuter, un appel qui passe un composant qui ne s'attache jamais ne se termine jamais.

```java
// Mettre l'accent sur l'entrée d'un composant web en appelant sa méthode côté client
searchElement.callJsFunctionVoidAsync("focus");
```
