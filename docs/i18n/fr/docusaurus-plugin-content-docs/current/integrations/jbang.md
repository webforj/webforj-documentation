---
title: JBang
sidebar_position: 15
description: >-
  Run webforJ apps as single-file JBang scripts with no Maven, Gradle, or
  project structure for rapid prototyping and demos.
_i18n_hash: a7af803fa3e58cb86e0c18289f8fbb40
---
# JBang <DocChip chip='since' label='25.11' />

[JBang](https://www.jbang.dev/) est un outil qui permet d’exécuter du code Java sous forme de scripts, sans fichiers de build, configuration de projet ni compilation manuelle. L’intégration JBang de webforJ vous permet de créer rapidement des applications webforJ, particulièrement adaptées au prototypage rapide, à l’apprentissage et aux démonstrations, sans avoir besoin des dépendances et de l’infrastructure traditionnelles d’un programme Java complet.

## Pourquoi utiliser JBang avec webforJ {#why-use-jbang}

Les projets webforJ traditionnels utilisent Maven ou Gradle, avec plusieurs fichiers de configuration et une structure de projet standard. Cette configuration est la norme pour les applications de production, mais peut sembler lourde pour de simples expérimentations ou démonstrations.

Avec JBang, vous pouvez :

- **Démarrer instantanément** : écrire un seul fichier `.java` et l’exécuter immédiatement
- **Éviter la configuration du projet** : pas de `pom.xml`, pas de `build.gradle`, pas de structure de répertoires
- **Partager facilement** : envoyer à quelqu’un un seul fichier qu’il pourra exécuter avec une seule commande
- **Apprendre plus rapidement** : vous concentrer sur les concepts webforJ sans la complexité des outils de build

L’intégration inclut l’arrêt automatique du serveur lorsque vous fermez l’onglet du navigateur, pour simplifier votre flux de développement.

## Prérequis {#prerequisites}

### Installer JBang {#install-jbang}

Choisissez votre méthode d’installation préférée :

```bash
# Universal (Linux/macOS/Windows with bash)
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

Vérifiez l’installation :

```bash
jbang --version
```

:::info[Version Java par défaut]
Lorsque vous exécutez JBang pour la première fois sans JDK installé, JBang en télécharge automatiquement un. Vous pouvez définir la version du JDK et son fournisseur avant d’exécuter JBang :

```bash
export JBANG_DEFAULT_JAVA_VERSION=21
export JBANG_JDK_VENDOR=temurin
```
:::

:::tip[En savoir plus sur JBang]
Pour obtenir une documentation complète sur JBang, consultez :
- [Prise en main de JBang](https://www.jbang.dev/documentation/jbang/latest/index.html) - Installation et notions de base
- [Référence des directives de script](https://www.jbang.dev/documentation/jbang/latest/script-directives.html) - Toutes les directives disponibles
- [Dépendances](https://www.jbang.dev/documentation/jbang/latest/dependencies.html) - Gestion avancée des dépendances
:::

## Créer un script webforJ {#creating-a-script}

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

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("What is your name?");
  private Button btn = new Button("Say Hello");

  public MainView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(FeatherIcon.BELL.create())
        .setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> Toast.show("Welcome to webforJ JBang Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}

```

### Comprendre la structure du script {#script-structure}

| Ligne | Rôle |
|------|---------|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | Ligne shebang qui permet d’exécuter directement le script sur les systèmes Unix |
| `//JAVA 21` | Indique la version minimale de Java requise ; JBang la télécharge automatiquement si nécessaire |
| `//DEPS com.webforj:webforj-jbang-starter:25.11` | Déclare le starter JBang de webforJ comme dépendance à l’aide des coordonnées Maven |
| `@SpringBootApplication` | Active l’auto-configuration de Spring Boot |
| `extends App` | Fait de cette classe une application webforJ |

La dépendance `webforj-jbang-starter` inclut tout le nécessaire pour exécuter une application webforJ : le starter Spring Boot, les outils de développement et l’ouverture automatique du navigateur.

:::note[Version]
Remplacez `25.11` par la dernière version de webforJ. Consultez [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj-jbang-starter) pour connaître la version la plus récente.
:::
### Ajouter des dépendances {#adding-dependencies}

Vous pouvez ajouter d’autres dépendances Maven à l’aide de plusieurs lignes `//DEPS` :

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21
//DEPS com.webforj:webforj-jbang-starter:25.11
//DEPS com.google.code.gson:gson:2.11.0
//DEPS org.apache.commons:commons-lang3:3.14.0
```

Les dépendances utilisent les coordonnées Maven standard (`groupId:artifactId:version`). Lors de la première exécution, JBang les récupère automatiquement depuis Maven Central.

## Exécuter votre script {#running-your-script}

Exécutez le script avec JBang :

```bash
jbang HelloWorld.java
```

JBang va :

1. Télécharger les dépendances (uniquement lors de la première exécution)
2. Compiler le script
3. Démarrer le serveur intégré sur un port disponible aléatoire
4. Ouvrir l’application dans votre navigateur par défaut

### Rendre le script exécutable {#executable-script}

Sur les systèmes Unix, vous pouvez rendre le script directement exécutable :

```bash
chmod +x HelloWorld.java
./HelloWorld.java
```

Cela fonctionne grâce à la ligne shebang au début du fichier.

## Prise en charge des IDE {#ide-support}

JBang s’intègre aux IDE Java populaires, notamment VS Code, IntelliJ IDEA, Eclipse et d’autres. Ces intégrations proposent des fonctionnalités telles que la saisie semi-automatique des directives, la résolution automatique des dépendances et la possibilité d’exécuter et de déboguer des scripts directement depuis l’IDE.

Consultez la [documentation sur l’intégration de JBang aux IDE](https://www.jbang.dev/documentation/jbang/latest/editing.html) pour obtenir les instructions de configuration et la liste des éditeurs pris en charge.

## Configuration {#configuration}

Le starter JBang de webforJ inclut des paramètres par défaut adaptés à l’écriture de scripts. Vous pouvez personnaliser son comportement à l’aide des propriétés système.

### Arrêt automatique {#auto-shutdown}

Par défaut, le serveur s’arrête automatiquement lorsque tous les onglets du navigateur connectés à l’application sont fermés. Cela simplifie votre flux de développement en évitant de laisser des serveurs orphelins en cours d’exécution.

| Propriété | Valeur par défaut | Description |
|----------|---------|-------------|
| `webforj.jbang.auto-shutdown` | `true` | Activer ou désactiver l’arrêt automatique |
| `webforj.jbang.idle-timeout` | `5` | Nombre de secondes à attendre après la déconnexion du dernier navigateur avant l’arrêt |

Pour désactiver l’arrêt automatique :

```bash
jbang -Dwebforj.jbang.auto-shutdown=false HelloWorld.java
```

Pour modifier le délai d’inactivité :

```bash
jbang -Dwebforj.jbang.idle-timeout=30 HelloWorld.java
```

### Paramètres par défaut {#default-settings}

Le starter JBang configure les paramètres par défaut suivants :

| Paramètre | Valeur | Description |
|---------|-------|-------------|
| `server.port` | `0` | Attribution d’un port aléatoire pour éviter les conflits lors de l’exécution de plusieurs scripts |
| `server.shutdown` | `immediate` | Arrêt rapide pour mettre fin rapidement à l’exécution du script |
| `spring.main.banner-mode` | `off` | Masque la bannière Spring Boot pour une sortie plus claire |
| `logging.level.root` | `ERROR` | Journalisation minimale pour garder la console dégagée |
| `logging.level.com.webforj` | `WARN` | Affiche uniquement les avertissements et les erreurs de webforJ |
| `webforj.devtools.browser.open` | `true` | Ouvre automatiquement le navigateur au démarrage de l’application |

### Redéploiement et rechargement à chaud {#development-workflow}

Les scripts JBang ne prennent pas en charge le rechargement à chaud. Pour voir vos modifications :

1. Arrêtez le script en cours d’exécution (fermez l’onglet du navigateur ou appuyez sur <kbd>Ctrl</kbd>+<kbd>C</kbd>)
2. Modifiez votre code
3. Exécutez de nouveau `jbang HelloWorld.java`

Pour automatiser le redéploiement pendant le développement, envisagez d’utiliser un [projet Maven complet avec Spring DevTools](/docs/integrations/spring/spring-boot). Consultez la [documentation sur le rechargement à chaud](/docs/configuration/deploy-reload/overview) pour en savoir plus.

## Passer à un projet complet {#transitioning}

Lorsque votre prototype dépasse le cadre d’un seul fichier, créez un véritable projet avec [startforJ](https://docs.webforj.com/startforj) ou l’[archétype Maven](./spring/spring-boot#option-2-using-the-command-line). Vous pouvez copier directement la logique de votre script dans la structure de projet générée.
