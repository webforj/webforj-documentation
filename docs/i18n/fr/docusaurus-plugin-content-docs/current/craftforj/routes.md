---
title: Routes
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: ec7a4521cd9c4328f9cd6550ebc9d90b
---
L’onglet Routes affiche la table de routage de l’application en cours d’exécution, dans la [hiérarchie](/docs/routing/route-hierarchy/overview) où le routeur la conserve, avec la route active mise en évidence. Les routes enregistrées [dynamiquement](/docs/routing/routes-registration) apparaissent à côté de celles qui sont annotées.

![Arborescence des routes avec la route active mise en évidence](/img/craftforj/routes/tree.png#rounded-border)

## Détails d’une route {#route-details}

La sélection d’une route affiche les informations que le routeur connaît à son sujet, notamment son chemin, la classe qui la définit, les observateurs du cycle de vie qui lui sont associés et sa configuration. Vous pouvez ouvrir cette classe dans la visionneuse de code source depuis cet écran.

## Naviguer depuis craftforJ {#navigating-from-craftforj}

Vous pouvez naviguer vers n’importe quelle route directement depuis craftforJ. Les routes qui prennent des paramètres proposent un champ pour chacun d’eux et résolvent le chemin au fur et à mesure que vous les renseignez, ce qui vous permet de vérifier où vous arriverez avant de naviguer.

Il s’agit d’une véritable navigation : les [observateurs du cycle de vie](/docs/routing/navigation-lifecycle/observers) de votre application s’exécutent exactement comme pour un utilisateur. L’arborescence suit également l’application : naviguer dans l’application elle-même déplace le marqueur.

<VideoPlayer video="craftforj/route-navigate" width={1920} height={1000} title="Route navigate" />

## Règles d’accès {#access-rules}

Chaque route porte un badge correspondant à l’[annotation de sécurité](/docs/security/annotations) qui y est déclarée. Vous pouvez filtrer l’arborescence pour afficher les routes publiques ou protégées depuis la barre d’outils.

Seules `@RolesAllowed` et `@DenyAll` sont considérées comme des règles de protection. `@PermitAll` ne désigne aucun rôle et exige uniquement qu’une personne soit connectée : le filtre la traite donc comme une règle publique. Gardez cela à l’esprit lorsque vous vérifiez quelles routes restreignent l’accès selon les rôles.

![Arborescence des routes avec un badge d’accès sur chaque route](/img/craftforj/routes/access-badge.png#rounded-border)

Vous pouvez également modifier la règle d’accès d’une route depuis craftforJ. craftforJ écrit l’annotation dans la classe de la route, puis l’application redémarre. La modification suit ainsi le même processus de révision que toute autre [modification du code source](/docs/craftforj/source-changes). Cette option n’est pas disponible lorsque craftforJ n’est pas autorisé à écrire du code Java.

<VideoPlayer video="craftforj/route-security" width={1920} height={1000} title="Route security" />
