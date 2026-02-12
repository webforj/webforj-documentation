---
title: JBang
sidebar_position: 15
sidebar_class_name: new-content
_i18n_hash: a8ffb21c2834adc74528dc39cb6d0497
---
# JBang <DocChip chip='since' label='25.11' />

[JBang](https://www.jbang.dev/) est un outil qui vous permet d'exécuter du code Java sous forme de scripts, sans fichiers de construction, configuration de projet ou compilation manuelle. L'intégration de webforJ avec JBang vous permet de créer rapidement des applications webforJ, particulièrement adaptées au prototypage rapide, à l'apprentissage et aux démonstrations rapides, sans avoir besoin des dépendances traditionnelles et de l'infrastructure d'un programme Java complet.

## Pourquoi utiliser JBang avec webforJ {#why-use-jbang}

Les projets traditionnels de webforJ utilisent Maven ou Gradle avec plusieurs fichiers de configuration et une structure de projet standard. Cette configuration est standard pour les applications de production, mais peut sembler lourde pour des expériences ou des démonstrations simples.

Avec JBang, vous pouvez :

- **Commencer instantanément** : Écrire un seul fichier `.java` et l'exécuter immédiatement
- **Sauter la configuration du projet** : Pas de `pom.xml`, pas de `build.gradle`, pas de structure de répertoire
- **Partager facilement** : Envoyer à quelqu'un un seul fichier qu'il peut exécuter avec une commande
- **Apprendre plus rapidement** : Se concentrer sur les concepts de webforJ sans la complexité des outils de construction

L'intégration comprend l'arrêt automatique du serveur lorsque vous fermez l'onglet du navigateur, gardant ainsi votre flux de travail de développement propre.

## Prérequis {#prerequisites}

### Installer JBang {#install-jbang}

Choisissez votre méthode d'installation préférée :

```bash
# Universel (Linux/macOS/Windows avec bash)
curl -Ls https://sh.jbang.dev | bash -s - app setup

# SDKMan
sdk install jbang

# Homebrew (macOS)
brew install jbangdev/tap/jbang

# Chocolatey (Windows)
choco install jbang

# Scoop (Windows)
scoop install jbang
```

Vérifiez l'installation :

```bash
jbang --version
```

:::info[Version Java par défaut]
Lorsque vous exécutez JBang pour la première fois sans JDK installé, JBang le télécharge automatiquement. Vous pouvez définir la version et le fournisseur du JDK avant d'exécuter JBang :

```bash
export JBANG_DEFAULT_JAVA_VERSION=21
export JBANG_JDK_VENDOR=temurin
```
:::

:::tip[En savoir plus sur JBang]
Pour une documentation complète sur JBang, consultez :
- [JBang Guide de démarrage](https://www.jbang.dev/documentation/jbang/latest/index.html) - Installation et notions de base
- [Référence des directives de script](https://www.jbang.dev/documentation/jbang/latest/script-directives.html) - Toutes les directives disponibles
- [Dépendances](https://www.jbang.dev/documentation/jbang/latest/dependencies.html) - Gestion avancée des dépendances
:::

## Création d'un script webforJ {#creating-a-script}

Créez un fichier nommé `HelloWorld.java` avec le contenu suivant :

```java title="HelloWorld.java"
///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.webforj:webforj-jbang-starter:25.11
//JAVA 21

package bang;

import com.webforj.App;
import com.webforj.annotation.Routify;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.Route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Routify
public class HelloWorld extends App {
  public static void main(String[] args) {
    SpringApplication.run(HelloWorld.class, args);
  }
}

@Route("/")
class MainView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("Quel est votre nom ?");
  private Button btn = new Button("Dire Bonjour");

  public MainView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(FeatherIcon.BELL.create())
        .setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> Toast.show("Bienvenue dans le starter webforJ JBang " + hello.getValue() + " !", Theme.GRAY));

    self.add(hello, btn);
  }
}

```

### Comprendre la structure du script {#script-structure}

| Ligne | Objectif |
|------|---------|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | Ligne shebang permettant l'exécution du script directement sur les systèmes Unix |
| `//JAVA 21` | Spécifie la version Java minimale requise ; JBang la télécharge automatiquement si nécessaire |
| `//DEPS com.webforj:webforj-jbang-starter:25.11` | Déclare le starter webforJ JBang comme dépendance en utilisant des coordonnées Maven |
| `@SpringBootApplication` | Active la configuration automatique de Spring Boot |
| `extends App` | Rendre cette classe une application webforJ |

La dépendance `webforj-jbang-starter` inclut tout ce dont vous avez besoin pour exécuter une application webforJ : le starter Spring Boot, des outils de développement, et l'ouverture automatique du navigateur.

:::note[Version]
Remplacez `25.11` par la dernière version de webforJ. Consultez [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj-jbang-starter) pour la dernière version.
:::
### Ajout de dépendances {#adding-dependencies}

Vous pouvez ajouter des dépendances Maven supplémentaires en utilisant plusieurs lignes `//DEPS` :

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21
//DEPS com.webforj:webforj-jbang-starter:25.11
//DEPS com.google.code.gson:gson:2.11.0
//DEPS org.apache.commons:commons-lang3:3.14.0
```

Les dépendances utilisent des coordonnées Maven standard (`groupId:artifactId:version`). JBang les récupère automatiquement depuis Maven Central lors de la première exécution.

## Exécution de votre script {#running-your-script}

Exécutez le script avec JBang :

```bash
jbang HelloWorld.java
```

JBang va :

1. Télécharger les dépendances (uniquement lors de la première exécution)
2. Compiler le script
3. Démarrer le serveur embarqué sur un port aléatoire disponible
4. Ouvrir votre navigateur par défaut sur l'application

### Rendre le script exécutable {#executable-script}

Sur les systèmes Unix, vous pouvez rendre le script directement exécutable :

```bash
chmod +x HelloWorld.java
./HelloWorld.java
```

Cela fonctionne grâce à la ligne shebang en haut du fichier.

## Support IDE {#ide-support}

JBang s'intègre avec des IDE Java populaires, y compris VS Code, IntelliJ IDEA, Eclipse, et d'autres. Ces intégrations offrent des fonctionnalités telles que l'auto-complétion des directives, la résolution automatique des dépendances et la capacité de lancer et de déboguer des scripts directement depuis l'IDE.

Consultez la [documentation d'intégration IDE de JBang](https://www.jbang.dev/documentation/jbang/latest/editing.html) pour des instructions d'installation et des éditeurs supportés.

## Configuration {#configuration}

Le starter webforJ JBang inclut des valeurs par défaut raisonnables optimisées pour les scripts. Vous pouvez personnaliser le comportement en utilisant des propriétés système.

### Arrêt automatique {#auto-shutdown}

Par défaut, le serveur s'arrête automatiquement lorsque tous les onglets de navigateur connectés à l'application sont fermés. Cela garde votre flux de travail de développement propre en ne laissant pas de serveurs orphelins en cours d'exécution.

| Propriété | Par défaut | Description |
|----------|---------|-------------|
| `webforj.jbang.auto-shutdown` | `true` | Active ou désactive l'arrêt automatique |
| `webforj.jbang.idle-timeout` | `5` | Secondes à attendre après la dernière déconnexion du navigateur avant de s'arrêter |

Pour désactiver l'arrêt automatique :

```bash
jbang -Dwebforj.jbang.auto-shutdown=false HelloWorld.java
```

Pour changer le délai d'inactivité :

```bash
jbang -Dwebforj.jbang.idle-timeout=30 HelloWorld.java
```

### Paramètres par défaut {#default-settings}

Le starter JBang configure les valeurs par défaut suivantes :

| Paramètre | Valeur | Description |
|---------|-------|-------------|
| `server.port` | `0` | Attribution de port aléatoire pour éviter les conflits lors de l'exécution de plusieurs scripts |
| `server.shutdown` | `immediate` | Arrêt rapide pour une terminaison rapide du script |
| `spring.main.banner-mode` | `off` | Masque la bannière Spring Boot pour une sortie plus propre |
| `logging.level.root` | `ERROR` | Journalisation minimale pour garder la sortie de la console propre |
| `logging.level.com.webforj` | `WARN` | Affiche uniquement les avertissements et les erreurs de webforJ |
| `webforj.devtools.browser.open` | `true` | Ouvre automatiquement le navigateur lorsque l'application démarre |

### Redeploiement et rechargement en direct {#development-workflow}

Les scripts JBang ne supportent pas le rechargement en direct. Pour voir les changements :

1. Arrêtez le script en cours d'exécution (fermez l'onglet du navigateur ou appuyez sur `Ctrl+C`)
2. Modifiez votre code
3. Exécutez à nouveau `jbang HelloWorld.java`

Pour un redéploiement automatique pendant le développement, considérez l'utilisation d'un [projet Maven complet avec Spring DevTools](/docs/integrations/spring/spring-boot). Consultez la [documentation sur le rechargement en direct](/docs/configuration/deploy-reload/overview) pour plus de détails.

## Transition vers un projet complet {#transitioning}

Lorsque votre prototype devient plus complexe qu'un seul fichier, créez un projet approprié en utilisant [startforJ](https://docs.webforj.com/startforj) ou l'[archétype Maven](./spring/spring-boot#option-2-using-the-command-line). Vous pouvez copier votre logique de script directement dans la structure de projet générée.
