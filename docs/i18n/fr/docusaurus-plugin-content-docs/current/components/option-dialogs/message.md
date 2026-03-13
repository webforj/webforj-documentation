---
title: Message
sidebar_position: 30
_i18n_hash: 1925f377637c75ea99d29272f31258ff
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

Un `MessageDialog` est une boîte de dialogue modale conçue pour afficher un message à l'utilisateur avec un bouton `OK` pour fermer la boîte de dialogue. Elle bloque l'exécution de l'application jusqu'à ce que l'utilisateur interagisse avec elle ou qu'elle se ferme en raison d'un délai d'attente.

<!-- INTRO_END -->

## Usages {#usages}

Utilisez la méthode statique `showMessageDialog` pour afficher un message de base.

```java
OptionDialog.showMessageDialog("Bonjour le monde !");
```

Pour un meilleur contrôle sur l'apparence et le comportement de la boîte de dialogue, créez une instance de `MessageDialog` directement.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "Bonjour le monde", "Bonjour le monde", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Type de message {#message-type}

Le `MessageDialog` prend en charge les types de message suivants. En configurant un type, la boîte de dialogue affiche une icône à côté du message, et le thème de la boîte de dialogue est mis à jour selon les règles du système de design webforJ.

1. `PLAIN`: Affiche le message sans icône, en utilisant le thème par défaut.
2. `ERROR`: Affiche une icône d'erreur à côté du message avec le thème d'erreur appliqué.
3. `QUESTION`: Affiche une icône de point d'interrogation à côté du message, en utilisant le thème principal.
4. `WARNING`: Affiche une icône d'avertissement à côté du message avec le thème d'avertissement appliqué.
5. `INFO`: Affiche une icône d'information à côté du message, en utilisant le thème d'information.

Dans l'exemple suivant, le code configure une boîte de dialogue de message de type `WARNING` avec un titre et un message personnalisés.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Thème de la boîte de dialogue et des boutons
Par défaut, la boîte de dialogue détermine le thème en fonction du type de message. Vous pouvez personnaliser le thème de la boîte de dialogue à l'aide de la méthode `setTheme(Theme theme)` et ajuster indépendamment le thème du bouton avec la méthode `setButtonTheme(ButtonTheme theme)` pour créer différentes variations.
:::

## Texte des boutons {#button-text}

Vous pouvez configurer le texte du bouton de la boîte de dialogue à l'aide de la méthode `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("Bonjour le monde !", "Titre", "Compris");
```

## Traitement HTML {#html-processing}

Par défaut, la boîte de dialogue de message traite et rend le contenu HTML. Vous pouvez désactiver cette fonctionnalité en la configurant pour afficher du texte brut à la place.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>Bonjour le monde</b>", "Bonjour le monde", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Délai d'expiration {#timeout}

Le `MessageDialog` vous permet de définir une durée de délai après laquelle la boîte de dialogue se ferme automatiquement. Cette fonctionnalité est utile pour les notifications non critiques ou les informations qui ne nécessitent pas d'interaction immédiate de l'utilisateur.

Vous pouvez configurer le délai pour la boîte de dialogue à l'aide de la méthode `setTimeout(int timeout)`. La durée du délai est en secondes. Si le temps spécifié s'écoule sans aucune interaction de l'utilisateur, la boîte de dialogue se ferme automatiquement.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Cette boîte de dialogue se fermera bientôt", "Délai");
dialog.setTimeout(2);
dialog.show();
```

## Meilleures pratiques {#best-practices}

1. **Messages clairs et concis**: Gardez les messages courts et directs et évitez le jargon technique ; utilisez un langage convivial.
2. **Types de message appropriés**:
   - Utilisez `ERROR` pour des problèmes critiques.
   - Utilisez `WARNING` pour des avis de prudence.
   - Utilisez `INFO` pour des informations générales.
3. **Thème cohérent**: Alignez les thèmes de la boîte de dialogue et des boutons avec le design de votre application.
4. **Utilisation judicieuse des délais**: Définissez des délais pour les notifications non critiques et assurez-vous que les utilisateurs ont suffisamment de temps pour lire le message.
5. **Évitez l'utilisation excessive**: Utilisez les boîtes de dialogue avec parcimonie pour éviter la frustration des utilisateurs et réservez-les pour des messages importants nécessitant une action ou une reconnaissance de l'utilisateur.
