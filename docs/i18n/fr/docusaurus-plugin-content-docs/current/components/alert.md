---
title: Alert
sidebar_position: 5
description: >-
  Display contextual feedback messages with the Alert component, including
  themes, expanses, dismissible close events, and rich content.
_i18n_hash: ad90f6abef16b17547ddcb2a612f4050
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

Le composant `Alert` dans webforJ fournit des messages de retour contextuels pour les utilisateurs. C'est un moyen polyvalent d'afficher des informations importantes, des avertissements ou des notifications dans votre application.

Les alertes aident à attirer l'attention sur des informations clés sans perturber le flux de travail de l'utilisateur. Elles sont parfaites pour les messages système, le retour d'information sur la validation des formulaires ou les mises à jour de statut qui doivent être clairement visibles mais non intrusives.

<!-- INTRO_END -->

## Création d'alertes {#creating-alerts}

Une `Alert` peut contenir du contenu riche comme du texte, des boutons et d'autres composants. Définissez un thème pour distinguer visuellement le type de message affiché.

<ComponentDemo
path='/webforj/alert'
files={['src/main/java/com/webforj/samples/views/alert/AlertView.java']}
height='110px'
/>

## Dissipation des alertes {#dismissing-alerts}

Si vous souhaitez donner aux utilisateurs la possibilité de dissiper l'`Alert`, vous pouvez la rendre fermable en appelant la méthode `setClosable()`.

```java
Alert alert = new Alert("Attention ! Cette alerte peut être dissipée.");
closableAlert.setClosable(true);
```
Les alertes font souvent plus que afficher des messages—elles peuvent déclencher des actions de suivi lorsqu'elles sont dissipées. Utilisez l'`AlertCloseEvent` pour gérer ces cas et répondre lorsque l'utilisateur dissipe l'`Alert`.

<ComponentDemo
path='/webforj/closablealert'
files={['src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java']}
height='100px'
/>

:::tip Composant d'alerte réutilisable
Fermer l'alerte ne fait que la masquer—cela ne détruit pas le composant, vous pouvez donc le réutiliser plus tard si nécessaire.
:::


## Style {#styling}

### Thèmes {#themes}

Le composant `Alert` prend en charge plusieurs <JavadocLink type="foundation" location="com/webforj/component/Theme"> thèmes </JavadocLink> pour distinguer visuellement différents types de messages, tels que succès, erreur, avertissement ou info. Ces thèmes peuvent être appliqués à l'aide de la méthode `setTheme()` ou directement dans le constructeur.

<ComponentDemo
path='/webforj/alertthemes'
files={['src/main/java/com/webforj/samples/views/alert/AlertThemesView.java']}
height='650px'
/>


### Expanses {#expanses}

L'ampleur définit la taille visuelle du composant `Alert`. Vous pouvez l'utiliser en appliquant la méthode `setExpanse()` ou en la passant directement au constructeur. Les options disponibles proviennent de l'énumération Expanse : `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, et `XLARGE`.

<ComponentDemo
path='/webforj/alertexpanses'
files={['src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java']}
height='600px'
/>

<TableBuilder name="Alert" />
