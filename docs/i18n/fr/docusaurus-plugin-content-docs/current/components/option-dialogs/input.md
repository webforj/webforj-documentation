---
title: Input Dialog
sidebar_position: 25
description: >-
  Prompt users for text, numbers, dates, colors, or other typed values with the
  modal InputDialog and message-type styling.
_i18n_hash: b797a58a2e413b1be6d2cfd814d74efa
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Un `InputDialog` est une boîte de dialogue modale conçue pour inviter l'utilisateur à fournir une entrée. La boîte de dialogue bloque l'exécution de l'application jusqu'à ce que l'utilisateur fournisse l'entrée ou ferme la boîte de dialogue.

<!-- INTRO_END -->

## Usages {#usages}

Le `InputDialog` invite les utilisateurs à fournir une entrée, telle que du texte, des nombres ou d'autres données. Étant donné que la boîte de dialogue est modale, l'application attend que l'utilisateur réponde avant de continuer :

<ComponentDemo
path='/webforj/inputdialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java']}
height='500px'
/>

## Types {#types}

### Types d'entrée {#input-types}

Le `InputDialog` prend en charge différents types de champs d'entrée, vous permettant d'adapter la méthode d'entrée à vos besoins spécifiques :

1. **TEXTE** : Un champ de saisie de texte standard à une seule ligne.
2. **MOT DE PASSE** : Un champ de saisie de mot de passe qui masque l'entrée de l'utilisateur.
3. **NOMBRE** : Un champ de saisie numérique.
4. **EMAIL** : Un champ de saisie pour les adresses email.
5. **URL** : Un champ de saisie pour les URL.
6. **RECHERCHE** : Un champ de saisie de texte de recherche.
7. **DATE** : Un champ de saisie pour sélectionner des dates.
8. **HEURE** : Un champ de saisie pour sélectionner l'heure.
9. **DATETIME_LOCAL** : Un champ de saisie pour sélectionner la date et l'heure locales.
10. **COULEUR** : Un champ de saisie pour sélectionner une couleur.

### Type de message {#message-type}

Le `InputDialog` prend en charge les types de message suivants. Lorsque vous configurez un type, la boîte de dialogue affiche une icône à côté du message, et le thème de la boîte de dialogue se met à jour selon les règles du système de conception webforJ.

1. `PLAIN` : Affiche le message sans icône, en utilisant le thème par défaut.
2. `ERROR` : Affiche une icône d'erreur à côté du message avec le thème d'erreur appliqué.
3. `QUESTION` : Affiche une icône de point d'interrogation à côté du message, en utilisant le thème principal.
4. `WARNING` : Affiche une icône d'avertissement à côté du message avec le thème d'avertissement appliqué.
5. `INFO` : Affiche une icône d'information à côté du message, en utilisant le thème d'information.

Dans l'exemple suivant, l'utilisateur est invité à entrer son mot de passe pour accéder à l'application. Si la connexion échoue, l'utilisateur sera invité à nouveau.

<ComponentDemo
path='/webforj/inputdialogtype'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java']}
height='350px'
/>

## Résultat {#result}

Le `InputDialog` renvoie l'entrée de l'utilisateur sous forme de chaîne. Si l'utilisateur ferme la boîte de dialogue sans fournir d'entrée, le résultat sera `null`.

:::important
La chaîne résultante sera renvoyée par la méthode `show()`, ou la méthode `OptionDialog` équivalente comme indiqué ci-dessous.
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
  "Veuillez entrer votre âge :", "Saisie de l'âge", "", InputDialog.InputType.NUMBER);

if (result != null) {
  OptionDialog.showMessageDialog("Vous avez entré : " + result, "Saisie reçue");
} else {
  OptionDialog.showMessageDialog("Aucune saisie reçue", "Saisie annulée");
}
```

## Valeur par défaut {#default-value}

Le `InputDialog` vous permet de spécifier une valeur par défaut qui apparaît dans le champ de saisie lorsque la boîte de dialogue est affichée. Cela peut fournir aux utilisateurs une suggestion ou une valeur précédemment saisie.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Veuillez entrer votre nom :", "Saisie du nom", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Délai d'expiration {#timeout}

Le `InputDialog` vous permet de définir une durée de délai d'expiration après laquelle la boîte de dialogue se ferme automatiquement. Cette fonctionnalité est utile pour des demandes ou des actions d'entrée non critiques qui ne nécessitent pas l'interaction immédiate de l'utilisateur.

Vous pouvez configurer le délai d'expiration pour la boîte de dialogue en utilisant la méthode `setTimeout(int timeout)`. La durée de délai est en secondes. Si le temps spécifié s'écoule sans aucune interaction utilisateur, la boîte de dialogue se ferme automatiquement.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Veuillez entrer votre nom :", "Saisie du nom", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
  "Vous avez entré : " + result, "Saisie reçue", "OK", MessageDialog.MessageType.INFO);
```

## Meilleures pratiques {#best-practices}

1. **Invitations Claires et Concises** : Assurez-vous que le message d'invitation explique clairement quelles informations l'utilisateur est invité à fournir.
2. **Types d'Entrée Appropriés** : Choisissez des types d'entrée qui correspondent aux données requises pour garantir des entrées utilisateur précises et pertinentes.
3. **Valeurs Par Défaut Logiques** : Définissez des valeurs par défaut qui fournissent des suggestions utiles ou des entrées précédentes pour rationaliser la saisie des utilisateurs.
4. **Utilisation Judicieuse du Délai d'Expiration** : Fixez des délais pour les demandes d'entrée non critiques, en garantissant que les utilisateurs disposent de suffisamment de temps pour fournir les informations requises.
5. **Minimiser l'Utilisation Abusive** : Utilisez les boîtes de dialogue d'entrée avec parcimonie pour éviter la frustration des utilisateurs. Réservez-les pour les actions nécessitant une saisie spécifique de l'utilisateur.
