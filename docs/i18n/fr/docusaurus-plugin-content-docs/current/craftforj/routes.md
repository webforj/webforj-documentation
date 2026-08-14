---
title: Routes
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: 8a8c4099d3bd0d4ff988038cee6a5c15
---
L'onglet Routes affiche le tableau de routage de l'application en cours d'exécution dans la [hiérarchie](/docs/routing/route-hierarchy/overview) que le routeur détient, avec la route active marquée. Les routes enregistrées [dynamiquement](/docs/routing/routes-registration) apparaissent aux côtés de celles annotées.

![L'arbre de routes avec la route active marquée](/img/craftforj/routes/tree.png#rounded-border)

## Détails de la route {#route-details}

Sélectionner une route montre ce que le routeur sait à son sujet, y compris son chemin, la classe qui lui correspond, les observateurs de cycle de vie attachés à celle-ci et sa configuration. Vous pouvez ouvrir cette classe dans le visualiseur de code source à partir d'ici.

## Navigation depuis craftforJ {#navigating-from-craftforj}

Vous pouvez naviguer vers n'importe quelle route directement depuis craftforJ. Les routes qui prennent des paramètres offrent un champ pour chacun d'eux et résolvent le chemin au fur et à mesure que vous les remplissez, vous permettant ainsi de confirmer où vous irez avant de vous engager.

Naviguer de cette manière est une véritable navigation, donc les [observateurs de cycle de vie](/docs/routing/navigation-lifecycle/observers) de votre application s'exécutent exactement comme ils le feraient pour un utilisateur. L'arbre suit également l'application, donc naviguer dans l'application elle-même déplace le marqueur.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-navigate.mp4" type="video/mp4" />
  </video>
</div>

## Règles d'accès {#access-rules}

Chaque route porte un badge pour l'[annotation de sécurité](/docs/security/annotations) qui lui est déclarée, et vous pouvez restreindre l'arbre aux routes publiques ou protégées depuis la barre d'outils.

Seuls `@RolesAllowed` et `@DenyAll` sont considérés comme protégés. `@PermitAll` ne nomme aucun rôle et nécessite uniquement qu'une personne soit connectée, donc le filtre le traite comme public. Gardez cela à l'esprit lorsque vous vérifiez quelles routes restreignent l'accès par rôle.

![L'arbre de routes avec un badge d'accès sur chaque route](/img/craftforj/routes/access-badge.png#rounded-border)

Vous pouvez également changer la règle d'accès d'une route depuis craftforJ. craftforJ écrit l'annotation dans la classe de la route et l'application redémarre, donc le changement passe par la même révision que tout autre [changement de code source](/docs/craftforj/source-changes). L'option n'est pas disponible lorsque craftforJ n'est pas autorisé à écrire en Java.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-security.mp4" type="video/mp4" />
  </video>
</div>
