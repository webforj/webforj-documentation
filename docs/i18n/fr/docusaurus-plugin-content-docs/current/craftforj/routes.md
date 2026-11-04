---
title: Routes
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: b276ce8df35b91ed18f07c8a686fb688
---
L'onglet Routes montre le tableau de routage de l'application en cours d'exécution dans la [hiérarchie](/docs/routing/route-hierarchy/overview) que le routeur maintient, avec la route active marquée. Les routes enregistrées [dynamiquement](/docs/routing/routes-registration) apparaissent aux côtés des routes annotées.

![L'arbre de routes avec la route active marquée](/img/craftforj/routes/tree.png#rounded-border)

## Détails de la route {#route-details}

Sélectionner une route montre ce que le routeur sait à son sujet, y compris son chemin, la classe qui la sous-tend, les observateurs de cycle de vie qui lui sont attachés, et sa configuration. Vous pouvez ouvrir cette classe dans le visualiseur de source à partir d'ici.

## Navigation depuis craftforJ {#navigating-from-craftforj}

Vous pouvez naviguer directement vers n'importe quelle route depuis craftforJ. Les routes qui prennent des paramètres offrent un champ pour chacun d'eux et résolvent le chemin au fur et à mesure que vous les remplissez, afin que vous puissiez confirmer où vous allez atterrir avant de partir.

Naviguer de cette manière est une véritable navigation, donc les [observateurs de cycle de vie](/docs/routing/navigation-lifecycle/observers) de votre application s'exécutent exactement comme ils le feraient pour un utilisateur. L'arbre suit également l'application, donc naviguer dans l'application elle-même déplace le marqueur.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-navigate.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Règles d'accès {#access-rules}

Chaque route porte un badge pour l'[annotation de sécurité](/docs/security/annotations) déclarée sur elle, et vous pouvez réduire l'arbre aux routes publiques ou protégées depuis la barre d'outils.

Seuls `@RolesAllowed` et `@DenyAll` comptent comme protégés. `@PermitAll` ne mentionne aucun rôle et requiert seulement que quelqu'un soit connecté, donc le filtre le considère comme public. Gardez cela à l'esprit lorsque vous vérifiez quelles routes restreignent l'accès par rôle.

![L'arbre de routes avec un badge d'accès sur chaque route](/img/craftforj/routes/access-badge.png#rounded-border)

Vous pouvez également changer la règle d'accès d'une route depuis craftforJ. craftforJ écrit l'annotation dans la classe de la route et l'application redémarre, donc le changement passe par le même examen que tout autre [changement de source](/docs/craftforj/source-changes). L'option n'est pas disponible lorsque craftforJ n'est pas autorisé à écrire en Java.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-security.mp4#t=0.5" type="video/mp4" />
  </video>
</div>
