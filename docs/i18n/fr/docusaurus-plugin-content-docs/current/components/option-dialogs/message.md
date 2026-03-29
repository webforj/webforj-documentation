---
title: Message
sidebar_position: 30
_i18n_hash: 4540b0f4317acc598d4970d0f16ae757
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

Un `MessageDialog` est une boîte de dialogue modale conçue pour afficher un message à l'utilisateur avec un bouton `OK` pour fermer la boîte de dialogue. Il bloque l'exécution de l'application jusqu'à ce que l'utilisateur interagisse avec elle ou qu'elle se ferme en raison d'un délai d'attente.

<!-- INTRO_END -->

## Usages {#usages}

Utilisez la méthode statique `showMessageDialog` pour afficher un message de base.

```java
OptionDialog.showMessageDialog("Bonjour tout le monde !");
```

Pour plus de contrôle sur l'apparence et le comportement de la boîte de dialogue, créez directement une instance de `MessageDialog`.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "Bonjour tout le monde", "Bonjour tout le monde", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Type de message {#message-type}

Le `MessageDialog` prend en charge les types de messages suivants. Lorsque vous configurez un type, la boîte de dialogue affiche une icône à côté du message, et le thème de la boîte de dialogue se met à jour en fonction des règles du système de conception webforJ.

1. `PLAIN`: Affiche le message sans icône, en utilisant le thème par défaut.
2. `ERROR`: Affiche une icône d'erreur à côté du message avec le thème d'erreur appliqué.
3. `QUESTION`: Affiche une icône de point d'interrogation à côté du message, en utilisant le thème principal.
4. `WARNING`: Affiche une icône d'avertissement à côté du message avec le thème d'avertissement appliqué.
5. `INFO`: Affiche une icône d'information à côté du message, en utilisant le thème d'information.

Dans l'exemple suivant, le code configure une boîte de dialogue de message de type `WARNING`, avec un titre et un message personnalisés.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Thème de la boîte de dialogue & des boutons
Par défaut, le thème de la boîte de dialogue est déterminé en fonction du type de message. Vous pouvez personnaliser le thème de la boîte de dialogue en utilisant la méthode `setTheme(Theme theme)` et ajuster indépendamment le thème du bouton avec la méthode `setButtonTheme(ButtonTheme theme)` pour créer différentes variations.
:::

## Texte du bouton {#button-text}

Vous pouvez configurer le texte du bouton de la boîte de dialogue en utilisant `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("Bonjour tout le monde !", "Titre", "D'accord");
```

## Traitement HTML {#html-processing}

Par défaut, la boîte de dialogue de message traite et rend le contenu HTML. Vous pouvez désactiver cette fonctionnalité en la configurant pour afficher du texte brut à la place.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "<b>Bonjour tout le monde</b>", "Bonjour tout le monde", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Délai d'attente {#timeout}

Le `MessageDialog` vous permet de définir une durée de délai d'attente après laquelle la boîte de dialogue se ferme automatiquement. Cette fonctionnalité est utile pour les notifications non critiques ou les informations qui ne nécessitent pas une interaction immédiate de l'utilisateur.

Vous pouvez configurer le délai d'attente pour la boîte de dialogue en utilisant la méthode `setTimeout(int timeout)`. La durée du délai d'attente est en secondes. Si le temps spécifié s'écoule sans aucune interaction de l'utilisateur, la boîte de dialogue se ferme automatiquement.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Cette boîte de dialogue va bientôt expirer", "Délai d'attente");
dialog.setTimeout(2);
dialog.show();
```

## Meilleures pratiques {#best-practices}

1. **Messages clairs et concis** : Gardez les messages courts et précis et évitez le jargon technique ; utilisez un langage convivial.
2. **Types de messages appropriés** :
   - Utilisez `ERROR` pour des problèmes critiques.
   - Utilisez `WARNING` pour des avis de prudence.
   - Utilisez `INFO` pour des informations générales.
3. **Thématisation cohérente** : Alignez les thèmes de la boîte de dialogue et des boutons avec le design de votre application.
4. **Utilisation judicieuse du délai d'attente** : Configurez des délais d'attente pour des notifications non critiques et assurez-vous que les utilisateurs ont suffisamment de temps pour lire le message.
5. **Évitez les abus** : Utilisez les boîtes de dialogue avec parcimonie pour éviter la frustration des utilisateurs et réservez-les pour les messages importants nécessitant une action ou une reconnaissance de l'utilisateur.
