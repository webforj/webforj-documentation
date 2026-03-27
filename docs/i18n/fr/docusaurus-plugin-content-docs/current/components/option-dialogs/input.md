---
title: Input Dialog
sidebar_position: 25
_i18n_hash: 1dbd6d7664b01a9c3282ff4f3df65ea8
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Un `InputDialog` est une boîte de dialogue modale conçue pour demander à l'utilisateur des informations. La boîte de dialogue bloque l'exécution de l'application jusqu'à ce que l'utilisateur fournisse les informations nécessaires ou ferme la boîte de dialogue.

<!-- INTRO_END -->

## Usages {#usages}

Le `InputDialog` demande aux utilisateurs de fournir des informations, telles que du texte, des numéros ou d'autres données. Comme la boîte de dialogue est modale, l'application attend que l'utilisateur réponde avant de continuer :

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Types {#types}

### Types d'entrée {#input-types}

Le `InputDialog` prend en charge différents types de champs d'entrée, vous permettant d'adapter la méthode de saisie à vos besoins spécifiques :

1. **TEXTE** : Un champ de saisie de texte standard sur une seule ligne.
2. **MOT DE PASSE** : Un champ de saisie de mot de passe qui masque l'entrée de l'utilisateur.
3. **NOMBRE** : Un champ de saisie numérique.
4. **EMAIL** : Un champ de saisie pour les adresses électroniques.
5. **URL** : Un champ de saisie pour les URLs.
6. **RECHERCHE** : Un champ de saisie de texte pour la recherche.
7. **DATE** : Un champ de saisie pour sélectionner des dates.
8. **HEURE** : Un champ de saisie pour sélectionner l'heure.
9. **DATETIME_LOCAL** : Un champ de saisie pour sélectionner la date et l'heure locales.
10. **COULEUR** : Un champ de saisie pour sélectionner une couleur.

### Type de message {#message-type}

Le `InputDialog` prend en charge les types de messages suivants. Lorsque vous configurez un type, la boîte de dialogue affiche une icône à côté du message, et le thème de la boîte de dialogue se met à jour selon les règles du système de design webforJ.

1. `PLAIN` : Affiche le message sans icône, en utilisant le thème par défaut.
2. `ERROR` : Affiche une icône d'erreur à côté du message avec le thème d'erreur appliqué.
3. `QUESTION` : Affiche une icône de point d'interrogation à côté du message, en utilisant le thème primaire.
4. `WARNING` : Affiche une icône d'avertissement à côté du message avec le thème d'avertissement appliqué.
5. `INFO` : Affiche une icône d'information à côté du message, en utilisant le thème d'information.

Dans l'exemple suivant, l'utilisateur est invité à entrer son mot de passe pour accéder à l'application. Si la connexion échoue, l'utilisateur sera invité à nouveau.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Résultat {#result}

Le `InputDialog` renvoie l'entrée de l'utilisateur sous forme de chaîne. Si l'utilisateur ferme la boîte de dialogue sans fournir d'entrée, le résultat sera `null`.

:::important
La chaîne résultante sera renvoyée par la méthode `show()`, ou la méthode `OptionDialog` équivalente comme indiqué ci-dessous. 
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
    "Veuillez entrer votre âge :", "Entrée d'âge", "", InputDialog.InputType.NUMBER);

if (result != null) {
    OptionDialog.showMessageDialog("Vous avez entré : " + result, "Entrée reçue");
} else {
    OptionDialog.showMessageDialog("Aucune entrée reçue", "Entrée annulée");
}
```

## Valeur par défaut {#default-value}

Le `InputDialog` vous permet de spécifier une valeur par défaut qui apparaît dans le champ d'entrée lorsque la boîte de dialogue est affichée. Cela peut fournir aux utilisateurs une suggestion ou une valeur précédemment saisie.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Veuillez entrer votre nom :", "Entrée de nom", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Délai d'attente {#timeout}

Le `InputDialog` vous permet de définir une durée de délai d'attente après laquelle la boîte de dialogue se ferme automatiquement. Cette fonctionnalité est utile pour les demandes d'entrée non critiques ou les actions qui ne nécessitent pas l'interaction immédiate de l'utilisateur.

Vous pouvez configurer le délai d'attente de la boîte de dialogue à l'aide de la méthode `setTimeout(int timeout)`. La durée du délai d'attente est en secondes. Si le temps spécifié s'écoule sans aucune interaction de l'utilisateur, la boîte de dialogue se ferme automatiquement.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Veuillez entrer votre nom :", "Entrée de nom", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "Vous avez entré : " + result, "Entrée reçue", "OK", MessageDialog.MessageType.INFO);
```

## Meilleures pratiques {#best-practices}

1. **Invitations claires et concises** : Assurez-vous que le message d'invite explique clairement quelles informations l'utilisateur est invité à fournir.
2. **Types de saisie appropriés** : Choisissez des types d'entrée qui correspondent aux données requises pour garantir une entrée utilisateur précise et pertinente.
3. **Valeurs par défaut logiques** : Définissez des valeurs par défaut qui fournissent des suggestions utiles ou des entrées précédentes pour simplifier la saisie par l'utilisateur.
5. **Utilisation judicieuse du délai d'attente** : Définissez des délais d'attente pour les demandes d'entrée non critiques, en veillant à ce que les utilisateurs aient suffisamment de temps pour fournir les informations requises.
6. **Minimiser l'utilisation excessive** : Utilisez les boîtes de dialogue de saisie avec parcimonie pour éviter la frustration des utilisateurs. Réservez-les pour les actions nécessitant une saisie spécifique de l'utilisateur.
