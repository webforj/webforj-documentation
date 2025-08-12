---
sidebar_position: 25
title: Input Dialog
_i18n_hash: 60c8f92b63b241996eda4f5a08df8027
---
# Input Dialog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Un `InputDialog` est une boîte de dialogue modale conçue pour demander une saisie à l'utilisateur. La boîte de dialogue bloque l'exécution de l'application jusqu'à ce que l'utilisateur fournisse la saisie ou ferme la boîte de dialogue.


<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Usages {#usages}

Le `InputDialog` offre un moyen de demander des saisies aux utilisateurs, telles que du texte, des nombres ou d'autres données, en s'assurant qu'ils fournissent les informations nécessaires avant de continuer.

## Types {#types}

### Types de saisie {#input-types}

Le `InputDialog` prend en charge différents types de champs de saisie, vous permettant d'adapter la méthode de saisie à vos besoins spécifiques :

1. **TEXTE** : Une saisie standard sur une seule ligne.
2. **MOT DE PASSE** : Un champ de saisie de mot de passe qui masque la saisie de l'utilisateur.
3. **NOMBRE** : Un champ de saisie numérique.
4. **EMAIL** : Un champ de saisie pour les adresses électroniques.
5. **URL** : Un champ de saisie pour les URL.
6. **RECHERCHE** : Un champ de saisie de texte pour la recherche.
7. **DATE** : Un champ de saisie pour la sélection de dates.
8. **HEURE** : Un champ de saisie pour la sélection de l'heure.
9. **DATETIME_LOCAL** : Un champ de saisie pour la sélection de la date et de l'heure locales.
10. **COULEUR** : Un champ de saisie pour la sélection d'une couleur.

### Type de message {#message-type}

Le `InputDialog` prend en charge les types de messages suivants. Lorsque vous configurez un type, la boîte de dialogue affiche une icône à côté du message, et le thème de la boîte de dialogue se met à jour selon les règles du système de design webforJ.

1. `PLAIN` : Affiche le message sans icône, en utilisant le thème par défaut.
2. `ERROR` : Affiche une icône d'erreur à côté du message avec le thème d'erreur appliqué.
3. `QUESTION` : Affiche une icône de point d'interrogation à côté du message, en utilisant le thème principal.
4. `WARNING` : Affiche une icône d'avertissement à côté du message avec le thème d'avertissement appliqué.
5. `INFO` : Affiche une icône d'information à côté du message, en utilisant le thème d'information.

Dans l'exemple suivant, l'utilisateur est invité à entrer son mot de passe pour accéder à l'application. Si la connexion échoue, l'utilisateur sera invité à nouveau.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Résultat {#result}

Le `InputDialog` renvoie la saisie de l'utilisateur sous forme de chaîne. Si l'utilisateur ferme la boîte de dialogue sans fournir de saisie, le résultat sera `null`.

:::important
La chaîne résultante sera renvoyée par la méthode `show()`, ou la méthode équivalente `OptionDialog` comme indiqué ci-dessous. 
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
    "Veuillez entrer votre âge :", "Saisie d'âge", "", InputDialog.InputType.NUMBER);

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
    "Veuillez entrer votre nom :", "Saisie de nom", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Délai d'expiration {#timeout}

Le `InputDialog` vous permet de définir une durée de délai d'expiration après laquelle la boîte de dialogue se ferme automatiquement. Cette fonctionnalité est utile pour les demandes de saisie non critiques ou les actions qui ne nécessitent pas l'interaction immédiate de l'utilisateur.

Vous pouvez configurer le délai d'expiration pour la boîte de dialogue en utilisant la méthode `setTimeout(int timeout)`. La durée du délai d'expiration est en secondes. Si le temps spécifié s'écoule sans aucune interaction de l'utilisateur, la boîte de dialogue se ferme automatiquement.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Veuillez entrer votre nom :", "Saisie de nom", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "Vous avez entré : " + result, "Saisie reçue", "OK", MessageDialog.MessageType.INFO);
```

## Meilleures pratiques {#best-practices}

1. **Messages clairs et concis** : Assurez-vous que le message d'invite explique clairement quelles informations l'utilisateur est invité à fournir.
2. **Types de saisie appropriés** : Choisissez des types de saisie qui correspondent aux données requises pour garantir une saisie utilisateur précise et pertinente.
3. **Valeurs par défaut logiques** : Définissez des valeurs par défaut qui fournissent des suggestions utiles ou des entrées précédentes pour rationaliser la saisie utilisateur.
4. **Utilisation judicieuse des délais** : Définissez des délais pour les demandes de saisie non critiques, en vous assurant que les utilisateurs disposent de suffisamment de temps pour fournir les informations requises.
5. **Minimiser l'utilisation excessive** : Utilisez les boîtes de dialogue de saisie avec parcimonie pour éviter la frustration des utilisateurs. Réservez-les pour des actions nécessitant une saisie spécifique de l'utilisateur.
