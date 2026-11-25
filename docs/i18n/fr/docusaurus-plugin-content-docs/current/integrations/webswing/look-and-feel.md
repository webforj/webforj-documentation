---
title: Apparence et comportement Swing
sidebar_position: 5
description: >-
  Donnez à une application Swing intégrée les couleurs, polices, boutons et
  contours de focus de webforJ avec le DWC Look and Feel for Swing, en mode
  clair ou sombre.
_i18n_title_hash: 3c0ddc5fa92ce0ba34ad9e57d896d42a
_i18n_description_hash: 8f86f1732f1d00ba4e1e87a16ff232f1
_i18n_hash: f7227a823be8a2f867fdf399bc56a1b4
---
Lorsque `WebswingConnector` intègre une application Swing dans une application webforJ, les utilisateurs voient les écrans Swing à côté des écrans webforJ. Avec l’apparence Swing par défaut, les deux côtés semblent appartenir à des applications différentes. L’apparence DWC pour Swing donne à l’application Swing les polices, les couleurs, les boutons et les contours de focus des composants DWC utilisés par webforJ. Elle comprend une palette claire et une palette sombre.

Il s’agit d’une apparence Swing standard contenue dans un seul JAR. Ajoutez-la au classpath de l’application Swing et sélectionnez-la au démarrage. Le reste du code Swing ne change pas.

:::info Correspondance au mieux
Swing et DWC dessinent leurs contrôles différemment. Les deux côtés peuvent utiliser la même palette claire ou sombre, mais de petites différences restent visibles lorsqu’on passe d’un écran Swing à un écran webforJ.
:::

## Essayer la démo {#try-the-demo}

Order Desk est une application Swing dans laquelle le thème est installé. [Téléchargez la démo Order Desk](https://dwc.style/docs/#/swing/downloads), puis exécutez-la avec Java 21 ou une version ultérieure :

```bash
java -jar dwc-swing-laf-demo.jar
```

Choisissez **View → DWC light** ou **View → DWC dark**, puis **View → Primary color**, et comparez la commande sélectionnée, l’onglet actif et le bouton Save order.

Tous les contrôles d’Order Desk sont des composants Swing standard. Changez la palette et la couleur principale sous la capture d’écran, puis sélectionnez-la pour l’agrandir.

<!-- vale off -->
<ThemeGallery alt="The Order Desk Swing demo" items={[
  { label: "Default", color: "#134ddf", light: "/img/swing-laf/order-desk-light-default.webp", dark: "/img/swing-laf/order-desk-dark-default.webp" },
  { label: "Violet", color: "#7c3aed", light: "/img/swing-laf/order-desk-light-violet.webp", dark: "/img/swing-laf/order-desk-dark-violet.webp" },
  { label: "Teal", color: "#0d9488", light: "/img/swing-laf/order-desk-light-teal.webp", dark: "/img/swing-laf/order-desk-dark-teal.webp" },
  { label: "Orange", color: "#ea580c", light: "/img/swing-laf/order-desk-light-orange.webp", dark: "/img/swing-laf/order-desk-dark-orange.webp" }
]} />
<!-- vale on -->

## Installer le thème {#install-the-theme}

[Téléchargez le JAR du thème](https://dwc.style/docs/#/swing/downloads) et ajoutez-le au classpath de l’application Swing. Il nécessite Java 21 ou une version ultérieure et inclut les deux palettes ainsi que la police Inter.

Sur le thread de distribution des événements Swing, sélectionnez l’apparence avant que l’application ne crée sa première fenêtre :

```java
try {
  UIManager.setLookAndFeel(new DwcLookAndFeel());
} catch (UnsupportedLookAndFeelException e) {
  throw new IllegalStateException(e);
}
```

Pour utiliser la palette sombre, utilisez plutôt `DwcDarkLookAndFeel`. Les deux classes se trouvent dans le package `com.basis.dwc.swing.laf`. Si l’application sélectionne son apparence à partir de son nom de classe, utilisez `com.basis.dwc.swing.laf.DwcLookAndFeel` ou `com.basis.dwc.swing.laf.DwcDarkLookAndFeel`.

Si l’application utilise des modules Java, placez le JAR sur le module path et ajoutez `com.basis.dwc.swing.laf` aux modules requis.

## Adapter le thème à celui de webforJ {#match-your-webforj-theme}

Le thème lit les mêmes noms de jetons que les [thèmes webforJ](/docs/styling/themes). Définissez un jeton dans l’application Swing avec la valeur utilisée par votre application webforJ.

Pour utiliser la couleur principale de votre application webforJ, définissez la valeur de départ de la couleur principale après avoir installé l’apparence :

```java
DwcLookAndFeel.setToken("--dwc-color-primary-seed", new Color(117, 67, 188));
```

Les boutons principaux, les sélections et les contours de focus adoptent cette couleur. Les autres couleurs sémantiques, comme celle des erreurs, conservent leur palette. Pour rétablir la couleur principale de la palette, passez `null` comme valeur.

Par défaut, le thème utilise Inter. webforJ utilise la police de la plateforme via [`--dwc-font-family`](/docs/styling/typography). Pour utiliser la même police dans l’application Swing, définissez la valeur du jeton sur `system-ui` :

```java
DwcLookAndFeel.setToken("--dwc-font-family", "system-ui");
```

Une autre police peut modifier la largeur des libellés et la hauteur des champs ; ajustez donc les fenêtres après avoir créé leur contenu.

## Styliser un seul contrôle {#style-one-control}

Une propriété client permet de modifier un seul contrôle. Dans cet exemple, un champ est libellé et marqué comme obligatoire, et le bouton Save est défini comme principal :

```java
email.putClientProperty(DwcLookAndFeel.LABEL, "Adresse e-mail de livraison");
email.putClientProperty(DwcLookAndFeel.REQUIRED, true);
save.putClientProperty(DwcLookAndFeel.THEME, ButtonTheme.PRIMARY);
```

Pour afficher un message de validation sous un champ, définissez l’état invalide et le texte d’aide :

```java
boolean missing = email.getText().isBlank();
email.putClientProperty(DwcLookAndFeel.INVALID, missing);
email.putClientProperty(DwcLookAndFeel.HELPER_TEXT,
    missing ? "Saisissez une adresse e-mail de livraison." : "");
```

La [référence des propriétés client](https://dwc.style/docs/#/swing/client-properties) répertorie les clés de chaque contrôle, notamment les tableaux, les arbres, les onglets, les curseurs et les boîtes de dialogue.

Le [guide complet du thème](https://dwc.style/docs/#/swing/) aborde les boîtes de dialogue, le focus clavier et les animations.
