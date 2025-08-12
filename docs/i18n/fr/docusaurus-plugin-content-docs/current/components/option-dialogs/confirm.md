---
sidebar_position: 5
title: Confirm
_i18n_hash: 99babacee9e77d9376b00554e47d7ca3
---
# Dialog de confirmation

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

Un `ConfirmDialog` est une boîte de dialogue modale conçue pour permettre à l'utilisateur de choisir l'une des options parmi un ensemble allant jusqu'à 3 options. La boîte de dialogue bloque l'exécution de l'application jusqu'à ce que l'utilisateur interagisse avec elle ou qu'elle se ferme en raison d'un délai d'attente.

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "Confirmez-vous ?",
    "Confirmation",
    ConfirmDialog.OptionType.OK_CANCEL,
    ConfirmDialog.MessageType.QUESTION);
```

## Usages {#usages}

Le `ConfirmDialog` fournit un moyen de demander aux utilisateurs une confirmation ou de choisir parmi plusieurs options, telles que `Oui/Non` ou `OK/Annuler`, garantissant qu'ils reconnaissent et confirment leurs actions.

<ComponentDemo 
path='/webforj/confirmdialogconstructor?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java'
height = '350px'
/>

## Types {#types}

### Type d'option {#option-type}

Le `ConfirmDialog` prend en charge les types d'options suivants, qui déterminent les boutons affichés dans la boîte de dialogue :

1. **`OK`** : Affiche un bouton `OK`.
2. **`OK_CANCEL`** : Affiche les boutons `OK` et `Annuler`.
3. **`ABORT_RETRY_IGNORE`** : Affiche les boutons `Abandonner`, `Réessayer` et `Ignorer`.
4. **`YES_NO_CANCEL`** : Affiche les boutons `Oui`, `Non`, et `Annuler`.
5. **`YES_NO`** : Affiche les boutons `Oui` et `Non`.
6. **`RETRY_CANCEL`** : Affiche les boutons `Réessayer` et `Annuler`.
7. **`CUSTOM`** : Affiche des boutons personnalisés comme spécifié.

### Type de message {#message-type}

Le `ConfirmDialog` prend en charge les types de message suivants. Lorsque vous configurez un type, la boîte de dialogue affiche une icône à côté du message, et le thème de la boîte de dialogue se met à jour selon les règles du système de design webforJ.

1. `PLAIN` : Affiche le message sans icône, utilisant le thème par défaut.
2. `ERROR` : Affiche une icône d'erreur à côté du message avec le thème d'erreur appliqué.
3. `QUESTION` : Affiche une icône de point d'interrogation à côté du message, utilisant le thème principal.
4. `WARNING` : Affiche une icône d'avertissement à côté du message avec le thème d'avertissement appliqué.
5. `INFO` : Affiche une icône d'information à côté du message, utilisant le thème d'information.

Dans l'exemple suivant, le code configure une boîte de dialogue de confirmation de type `CUSTOM` avec un titre et un message personnalisés.

<ComponentDemo 
path='/webforj/confirmdialogoptions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java'
height = '350px'
/>

## Résultat {#result}

Le `ConfirmDialog` renvoie un résultat basé sur l'interaction de l'utilisateur avec la boîte de dialogue. Ce résultat indique quel bouton l'utilisateur a cliqué ou si la boîte de dialogue a été rejetée en raison d'un délai d'attente.

:::important
Le résultat sera renvoyé par la méthode `show()`, ou la méthode équivalente `OptionDialog` comme montré ci-dessous. 
:::

L'énumération `ConfirmDialog.Result` comprend les résultats possibles suivants :

1. **`OK`** : L'utilisateur a cliqué sur le bouton `OK`.
2. **`CANCEL`** : L'utilisateur a cliqué sur le bouton `CANCEL`.
3. **`YES`** : L'utilisateur a cliqué sur le bouton `YES`.
4. **`NO`** : L'utilisateur a cliqué sur le bouton `NO`.
5. **`ABORT`** : L'utilisateur a cliqué sur le bouton `ABORT`.
6. **`RETRY`** : L'utilisateur a cliqué sur le bouton `RETRY`.
7. **`IGNORE`** : L'utilisateur a cliqué sur le bouton `IGNORE`.
8. **`FIRST_CUSTOM_BUTTON`** : L'utilisateur a cliqué sur le premier bouton personnalisé.
9. **`SECOND_CUSTOM_BUTTON`** : L'utilisateur a cliqué sur le deuxième bouton personnalisé.
10. **`THIRD_CUSTOM_BUTTON`** : L'utilisateur a cliqué sur le troisième bouton personnalisé.
11. **`TIMEOUT`** : La boîte de dialogue a dépassé le délai.
12. **`UNKNOWN`** : Un résultat inconnu, généralement utilisé comme état par défaut ou d'erreur.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
    OptionDialog.showMessageDialog("Modifications rejetées", "Rejeté", "Compris");
} else {
    OptionDialog.showMessageDialog(
        "Modifications enregistrées", "Enregistré", "Compris", MessageDialog.MessageType.INFO);
}
```

## Bouton par défaut {#default-button}

Le `ConfirmDialog` vous permet de spécifier un bouton par défaut qui est présélectionné lorsque la boîte de dialogue est affichée. Cela améliore l'expérience utilisateur en fournissant une action suggérée qui peut être rapidement confirmée en appuyant sur la touche <kbd>Entrée</kbd>.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Êtes-vous sûr ?", "Confirmer", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // deuxième bouton
dialog.show();
```

## Texte des boutons {#buttons-text}

Vous pouvez configurer le texte des boutons en utilisant la méthode `setButtonText(ConfirmDialog.Button button, String text)`.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Êtes-vous sûr ?", "Confirmer", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Absolument");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Non");
dialog.show();
```

## Traitement HTML {#html-processing}

Par défaut, la boîte de dialogue de confirmation traite et rend le contenu HTML. Vous pouvez désactiver cette fonctionnalité en la configurant pour afficher du texte brut à la place.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "<b>Êtes-vous sûr ?</b>", "Confirmer",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Délai d'attente {#timeout}

Le `ConfirmDialog` vous permet de définir une durée de délai d'attente après laquelle la boîte de dialogue se ferme automatiquement. Cette fonctionnalité est utile pour des confirmations non critiques ou des actions qui ne nécessitent pas l'interaction immédiate de l'utilisateur.

Vous pouvez configurer le délai d'attente pour la boîte de dialogue en utilisant la méthode `setTimeout(int timeout)`. La durée du délai d'attente est en secondes. Si le temps spécifié s'écoule sans interaction de l'utilisateur, la boîte de dialogue se ferme automatiquement.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Êtes-vous sûr ?", "Confirmer", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "Vous avez pris trop de temps pour décider", "Délai d'attente", "Compris",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "Vous avez cliqué sur Oui", "Oui", "Compris",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "Vous avez cliqué sur Non", "Non", "Compris",
        MessageDialog.MessageType.INFO);
    break;
}
```

## Meilleures pratiques {#best-practices}

1. **Invitations claires et concises** : Assurez-vous que le message d'invite explique clairement quelle action l'utilisateur est invité à confirmer. Évitez les ambiguïtés.
2. **Types d'options appropriés** : Choisissez des types d'options qui correspondent au contexte de l'action. Pour des décisions simples oui/non, utilisez des options simples. Pour des scénarios plus complexes, fournissez des boutons supplémentaires comme "Annuler" pour permettre aux utilisateurs de revenir sans faire de choix.
3. **Bouton par défaut logique** : Définissez un bouton par défaut qui s'aligne avec l'action utilisateur la plus probable ou recommandée pour rationaliser la prise de décision.
4. **Thématisation cohérente** : Alignez les thèmes de la boîte de dialogue et des boutons avec le design de votre application pour une expérience utilisateur cohérente.
5. **Utilisation judicieuse des délais d'attente** : Définissez des délais d'attente pour des confirmations non critiques, garantissant aux utilisateurs suffisamment de temps pour lire et comprendre l'invite.
6. **Minimiser l'utilisation excessive** : Utilisez les boîtes de dialogue de confirmation avec parcimonie pour éviter la frustration des utilisateurs. Réservez-les pour des actions critiques nécessitant une confirmation explicite de l'utilisateur.
