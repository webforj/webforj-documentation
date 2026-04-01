---
title: Input Dialog
sidebar_position: 25
_i18n_hash: 3c045d4085b917bd2f338916cc61d276
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

Un `InputDialog` est une boîte de dialogue modale conçue pour demander à l'utilisateur de fournir une entrée. La boîte de dialogue bloque l'exécution de l'application jusqu'à ce que l'utilisateur fournisse l'entrée ou ferme la boîte de dialogue.

<!-- INTRO_END -->

## Usages {#usages}

Le `InputDialog` invite les utilisateurs à fournir des entrées, telles que du texte, des chiffres ou d'autres données. Étant donné que la boîte de dialogue est modale, l'application attend que l'utilisateur réponde avant de continuer :

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Types {#types}

### Types d'entrée {#input-types}

Le `InputDialog` prend en charge différents types de champs d'entrée, vous permettant d'adapter le mode d'entrée à vos besoins spécifiques :

1. **TEXTE** : Une entrée de texte standard sur une seule ligne.
2. **MOT DE PASSE** : Un champ d'entrée de mot de passe qui masque l'entrée de l'utilisateur.
3. **NOMBRE** : Un champ d'entrée numérique.
4. **EMAIL** : Un champ d'entrée pour les adresses électroniques.
5. **URL** : Un champ d'entrée pour les URL.
6. **RECHERCHE** : Un champ d'entrée de texte pour la recherche.
7. **DATE** : Un champ d'entrée pour sélectionner des dates.
8. **HEURE** : Un champ d'entrée pour sélectionner une heure.
9. **DATETIME_LOCAL** : Un champ d'entrée pour sélectionner une date et une heure locales.
10. **COULEUR** : Un champ d'entrée pour sélectionner une couleur.

### Type de message {#message-type}

Le `InputDialog` prend en charge les types de message suivants. Lorsque vous configurez un type, la boîte de dialogue affiche une icône à côté du message, et le thème de la boîte de dialogue est mis à jour selon les règles du système de design webforJ.

1. `PLAIN` : Affiche le message sans une icône, en utilisant le thème par défaut.
2. `ERREUR` : Affiche une icône d'erreur à côté du message avec le thème d'erreur appliqué.
3. `QUESTION` : Affiche une icône de point d'interrogation à côté du message, en utilisant le thème principal.
4. `AVERTISSEMENT` : Affiche une icône d'avertissement à côté du message avec le thème d'avertissement appliqué.
5. `INFO` : Affiche une icône d'information à côté du message, en utilisant le thème d'information.

Dans l'exemple suivant, l'utilisateur est invité à entrer son mot de passe pour accéder à l'application. Si la connexion échoue, l'utilisateur sera à nouveau invité.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Résultat {#result}

Le `InputDialog` renvoie l'entrée de l'utilisateur sous forme de chaîne. Si l'utilisateur ferme la boîte de dialogue sans fournir d'entrée, le résultat sera `null`.

:::important
La chaîne résultante sera renvoyée par la méthode `show()`, ou la méthode équivalente `OptionDialog` comme indiqué ci-dessous. 
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
  "Veuillez entrer votre âge :", "Saisie d'âge", "", InputDialog.InputType.NUMBER);

if (result != null) {
  OptionDialog.showMessageDialog("Vous avez saisi : " + result, "Saisie reçue");
} else {
  OptionDialog.showMessageDialog("Aucune saisie reçue", "Saisie annulée");
}
```

## Valeur par défaut {#default-value}

Le `InputDialog` vous permet de spécifier une valeur par défaut qui apparaît dans le champ d'entrée lorsque la boîte de dialogue est affichée. Cela peut fournir aux utilisateurs une suggestion ou une valeur précédemment entrée.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Veuillez entrer votre nom :", "Saisie de nom", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Délai d'attente {#timeout}

Le `InputDialog` vous permet de définir une durée de délai d'attente après laquelle la boîte de dialogue se ferme automatiquement. Cette fonctionnalité est utile pour les demandes d'entrée non critiques ou les actions qui ne nécessitent pas l'interaction immédiate de l'utilisateur.

Vous pouvez configurer le délai d'attente pour la boîte de dialogue en utilisant la méthode `setTimeout(int timeout)`. La durée du délai d'attente est en secondes. Si le temps spécifié s'écoule sans aucune interaction de l'utilisateur, la boîte de dialogue se ferme automatiquement.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Veuillez entrer votre nom :", "Saisie de nom", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
  "Vous avez saisi : " + result, "Saisie reçue", "OK", MessageDialog.MessageType.INFO);
```

## Meilleures pratiques {#best-practices}

1. **Invitations claires et concises** : Assurez-vous que le message d'invitation explique clairement quelle information l'utilisateur est invité à fournir.
2. **Types d'entrée appropriés** : Choisissez des types d'entrée qui correspondent aux données requises pour garantir des saisies précises et pertinentes de l'utilisateur.
3. **Valeurs par défaut logiques** : Définissez des valeurs par défaut qui fournissent des suggestions utiles ou des entrées précédentes pour rationaliser la saisie de l'utilisateur.
5. **Utilisation judicieuse du délai d'attente** : Définissez des délais d'attente pour les demandes d'entrée non critiques, en veillant à ce que les utilisateurs aient suffisamment de temps pour fournir les informations requises.
6. **Minimiser l'utilisation excessive** : Utilisez les boîtes de dialogue d'entrée avec parcimonie pour éviter la frustration des utilisateurs. Réservez-les pour les actions nécessitant une saisie spécifique de la part de l'utilisateur.
