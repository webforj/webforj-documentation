---
sidebar_position: 25
title: Input Dialog
_i18n_hash: ba46203db1b4c35878c6509a514a70e5
---
# Dialogue d'entrée

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Un `InputDialog` est une boîte de dialogue modale conçue pour demander à l'utilisateur de fournir une saisie. La boîte de dialogue bloque l'exécution de l'application jusqu'à ce que l'utilisateur fournisse les informations ou ferme la boîte de dialogue.

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Utilisations {#usages}

Le `InputDialog` fournit un moyen de demander des informations aux utilisateurs, telles que du texte, des chiffres ou d'autres données, en veillant à ce qu'ils fournissent les informations nécessaires avant de continuer.

## Types {#types}

### Types de saisie {#input-types}

Le `InputDialog` prend en charge différents types de champs de saisie, vous permettant d'adapter la méthode de saisie à vos besoins spécifiques :

1. **TEXTE** : Un champ de saisie de texte standard sur une seule ligne.
2. **MOT DE PASSE** : Un champ de saisie de mot de passe qui masque la saisie de l'utilisateur.
3. **NOMBRE** : Un champ de saisie numérique.
4. **EMAIL** : Un champ de saisie pour les adresses électroniques.
5. **URL** : Un champ de saisie pour les URL.
6. **RECHERCHE** : Un champ de saisie de texte pour la recherche.
7. **DATE** : Un champ de saisie pour sélectionner des dates.
8. **HEURE** : Un champ de saisie pour sélectionner l'heure.
9. **DATETIME_LOCAL** : Un champ de saisie pour sélectionner une date et une heure locales.
10. **COULEUR** : Un champ de saisie pour sélectionner une couleur.

### Type de message {#message-type}

Le `InputDialog` prend en charge les types de messages suivants. Lorsque vous configurez un type, la boîte de dialogue affiche une icône à côté du message, et le thème de la boîte de dialogue se met à jour conformément aux règles du système de design de webforJ.

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

Le `InputDialog` retourne la saisie de l'utilisateur sous forme de chaîne. Si l'utilisateur ferme la boîte de dialogue sans fournir d'entrée, le résultat sera `null`.

:::important
La chaîne résultante sera retournée par la méthode `show()`, ou la méthode équivalente `OptionDialog` comme indiqué ci-dessous. 
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

Le `InputDialog` vous permet de définir une durée de délai d'expiration après laquelle la boîte de dialogue se ferme automatiquement. Cette fonctionnalité est utile pour des demandes de saisie ou des actions non critiques qui ne nécessitent pas l'interaction immédiate de l'utilisateur.

Vous pouvez configurer le délai d'expiration pour la boîte de dialogue en utilisant la méthode `setTimeout(int timeout)`. La durée du délai est exprimée en secondes. Si le temps spécifié s'écoule sans aucune interaction de l'utilisateur, la boîte de dialogue se ferme automatiquement.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Veuillez entrer votre nom :", "Saisie de nom", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "Vous avez entré : " + result, "Saisie reçue", "OK", MessageDialog.MessageType.INFO);
```

## Meilleures pratiques {#best-practices}

1. **Invitations claires et concises** : Assurez-vous que le message d'invitation explique clairement quelles informations l'utilisateur est prié de fournir.
2. **Types de saisie appropriés** : Choisissez des types de saisie qui correspondent aux données requises pour assurer une saisie utilisateur précise et pertinente.
3. **Valeurs par défaut logiques** : Définissez des valeurs par défaut qui fournissent des suggestions utiles ou des entrées précédentes pour simplifier la saisie utilisateur.
5. **Utilisation judicieuse des délais** : Définissez des délais pour les demandes de saisie non critiques, en veillant à ce que les utilisateurs aient suffisamment de temps pour fournir les informations requises.
6. **Minimiser les abus** : Utilisez les boîtes de dialogue de saisie avec parcimonie pour éviter la frustration des utilisateurs. Réservez-les pour les actions nécessitant une saisie spécifique de l'utilisateur.
