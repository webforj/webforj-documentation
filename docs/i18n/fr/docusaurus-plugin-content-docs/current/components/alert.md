---
title: Alert
sidebar_position: 5
_i18n_hash: d6b9cd03da84860fd2768d9633f3b38a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

Le composant `Alert` dans webforJ fournit des messages de retour contextuels pour les utilisateurs. C'est une manière polyvalente d'afficher des informations importantes, des avertissements ou des notifications dans votre application.

Les alertes aident à attirer l'attention sur des informations clés sans perturber le flux de travail de l'utilisateur. Elles sont parfaites pour les messages système, le retour d'information sur la validation des formulaires ou les mises à jour de statut qui doivent être clairement visibles mais non intrusives.

Voici un exemple de composant d'alerte :

<ComponentDemo 
path='/webforj/alert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertView.java'
height = '100px'
/>

## Dismissal des alertes {#dismissing-alerts}

Si vous souhaitez donner aux utilisateurs la possibilité de fermer l'`Alert`, vous pouvez le rendre fermable en appelant la méthode `setClosable()`. 

```java 
Alert alert = new Alert("Attention ! Cette alerte peut être fermée.");
closableAlert.setClosable(true);
```
Les alertes font souvent plus que d'afficher des messages—elles peuvent déclencher des actions de suivi lorsqu'elles sont fermées. Utilisez l'`AlertCloseEvent` pour gérer ces cas et répondre lorsque l'utilisateur ferme l'`Alert`.

<ComponentDemo 
path='/webforj/closablealert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java'
height = '100px'
/>

:::tip Composant d'alerte réutilisable
Fermer l'alerte ne fait que la cacher—cela ne détruit pas le composant, donc vous pouvez le réutiliser plus tard si nécessaire.
:::


## Stylisation {#styling}

### Thèmes {#themes}

Le composant `Alert` prend en charge plusieurs <JavadocLink type="foundation" location="com/webforj/component/Theme"> thèmes </JavadocLink> pour distinguer visuellement les différents types de messages—tels que succès, erreur, avertissement ou info. Ces thèmes peuvent être appliqués en utilisant la méthode `setTheme()` ou directement dans le constructeur.

<ComponentDemo 
path='/webforj/alertthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertThemesView.java' 
height = '475px'
/>


### Expanses {#expanses}

L'expanse définit la taille visuelle du composant `Alert`. Vous pouvez le définir en utilisant la méthode `setExpanse()` ou le passer directement au constructeur. Les options disponibles proviennent de l'énumération Expanse : `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, et `XLARGE`.

<ComponentDemo 
path='/webforj/alertexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java'
height = '400px'
/>

<TableBuilder name="Alerte" />
