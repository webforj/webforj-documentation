---
sidebar_position: 30
title: Message
_i18n_hash: 575bdfd5364ffdbd911ac0ebe0628359
---
# Boîte de dialogue message

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

Une `MessageDialog` est une boîte de dialogue modale conçue pour afficher un message à l'utilisateur avec un bouton `OK` pour fermer la boîte de dialogue. Elle bloque l'exécution de l'application jusqu'à ce que l'utilisateur interagisse avec elle ou qu'elle se ferme en raison d'un délai d'attente.

```java
OptionDialog.showMessageDialog("Hello World!");
```

## Utilisations {#usages}

La boîte de dialogue message fournit un moyen d'afficher des alertes informatives, telles que des notifications, des mises à jour ou des messages simples qui nécessitent uniquement que l'utilisateur les reconnaisse sans fournir d'entrée.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "Hello World", "Hello World", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Type de message {#message-type}

La `MessageDialog` prend en charge les types de message suivants. Lorsque vous configurez un type, la boîte de dialogue affiche une icône à côté du message, et le thème de la boîte de dialogue se met à jour conformément aux règles du système de design webforJ.

1. `PLAIN`: Affiche le message sans icône, en utilisant le thème par défaut.
2. `ERROR`: Affiche une icône d'erreur à côté du message avec le thème d'erreur appliqué.
3. `QUESTION`: Affiche une icône de point d'interrogation à côté du message, en utilisant le thème principal.
4. `WARNING`: Affiche une icône d'avertissement à côté du message avec le thème d'avertissement appliqué.
5. `INFO`: Affiche une icône d'information à côté du message, en utilisant le thème d'information.

Dans l'exemple suivant, le code configure une boîte de dialogue message de type `WARNING`, avec un titre et un message personnalisés.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Thème de la boîte de dialogue & du bouton
Par défaut, la boîte de dialogue détermine le thème en fonction du type de message. Vous pouvez personnaliser le thème de la boîte de dialogue à l'aide de la méthode `setTheme(Theme theme)` et ajuster indépendamment le thème du bouton avec la méthode `setButtonTheme(ButtonTheme theme)` pour créer différentes variantes.
:::

## Texte du bouton {#button-text}

Vous pouvez configurer le texte du bouton de la boîte de dialogue en utilisant `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("Hello World!", "Titre", "Compris");
```

## Traitement HTML {#html-processing}

Par défaut, la boîte de dialogue message traite et rend le contenu HTML. Vous pouvez désactiver cette fonctionnalité en le configurant pour afficher du texte brut à la place.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>Hello World</b>", "Hello World", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Délai d'attente {#timeout}

La `MessageDialog` vous permet de définir une durée de délai d'attente après laquelle la boîte de dialogue se ferme automatiquement. Cette fonctionnalité est utile pour des notifications non critiques ou des informations qui ne nécessitent pas l'interaction immédiate de l'utilisateur.

Vous pouvez configurer le délai d'attente de la boîte de dialogue en utilisant la méthode `setTimeout(int timeout)`. La durée du délai d'attente est en secondes. Si le temps spécifié s'écoule sans aucune interaction de l'utilisateur, la boîte de dialogue se ferme automatiquement.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Cette boîte de dialogue va bientôt expirer", "Délai d'attente");
dialog.setTimeout(2);
dialog.show();
```

## Meilleures pratiques {#best-practices}

1. **Messages clairs et concis**: Gardez les messages courts et directs et évitez le jargon technique ; utilisez un langage accessible.
2. **Types de message appropriés** :
   - Utilisez `ERROR` pour les problèmes critiques.
   - Utilisez `WARNING` pour les notifications de prudence.
   - Utilisez `INFO` pour des informations générales.
3. **Thématisation cohérente**: Alignez les thèmes de la boîte de dialogue et des boutons avec le design de votre application.
4. **Utilisation judicieuse des délais d'attente**: Établissez des délais d'attente pour des notifications non critiques et assurez-vous que les utilisateurs ont suffisamment de temps pour lire le message.
5. **Évitez une utilisation excessive**: Utilisez les boîtes de dialogue avec parcimonie pour éviter la frustration des utilisateurs et réservez-les pour les messages importants nécessitant une action ou une reconnaissance de la part de l'utilisateur.
