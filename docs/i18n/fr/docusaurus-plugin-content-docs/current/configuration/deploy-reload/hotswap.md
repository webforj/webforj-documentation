---
title: Hotswap
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 97e2fbe0bd74ada89fb19fae84fc4102
---
# Hotswap <DocChip chip='since' label='26.02' />

Un outil de hotswap applique les modifications compilées des classes à l’application en cours d’exécution, sans redémarrage. L’application conserve son état entre les mises à jour. L’outil est indiqué dans la configuration du [plugin de build webforJ](/docs/configuration/build-plugin) et est attaché au démarrage de l’application par le build. La commande d’exécution reste la même et le projet ne déclare aucune dépendance pour cet outil.

Deux outils sont pris en charge :

- **HotswapAgent** est un logiciel open source. Le plugin de build télécharge l’agent lors de la première exécution et le met en cache.
- **JRebel** est un produit commercial. Il nécessite votre propre installation et une licence.

Configurez-en exactement un. Un build qui en indique deux échoue avec une erreur qui les nomme tous les deux.

<!-- vale off -->
## HotswapAgent {#hotswapagent}
<!-- vale on -->

Un élément vide constitue une configuration complète :

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <hotswap>
      <hotswapAgent/>
    </hotswap>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    hotswapAgent {}
  }
}
```

</TabItem>
</Tabs>

Deux options permettent d’affiner l’attachement :

| Option | Description |
|--------|-------------|
| `version` | Une version spécifique de l’agent, à la place de celle sélectionnée par le plugin. |
| `path` | Un fichier jar d’agent sur le disque, utilisé directement sans téléchargement. Pour les machines sans accès réseau ou pour une compilation personnalisée de l’agent. |

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<hotswap>
  <hotswapAgent>
    <path>/path/to/hotswap-agent.jar</path>
  </hotswapAgent>
</hotswap>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    hotswapAgent {
      path = file('/path/to/hotswap-agent.jar')
    }
  }
}
```

</TabItem>
</Tabs>

### Modifications de la structure des classes {#class-structure-changes}

Les modifications du corps des méthodes s’appliquent sur n’importe quelle machine virtuelle Java. Les modifications de la structure d’une classe, comme l’ajout d’un champ ou d’une méthode, nécessitent une machine virtuelle qui accepte l’option `-XX:+AllowEnhancedClassRedefinition`, fournie par le [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases). Le build détecte cette capacité et l’active. Consultez [Prérequis](/docs/introduction/prerequisites#java-development-kit-jdk-21) pour installer un JetBrains Runtime.

Sans cette capacité, les modifications du corps des méthodes s’appliquent tout de même, mais les modifications de la structure des classes ne sont prises en compte par l’application en cours d’exécution qu’après un redémarrage. Le journal du build affiche un avertissement indiquant cette exigence, et le navigateur affiche une notification une seule fois.

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) est un produit commercial, sous licence de son fournisseur. webforJ ne le fournit pas, ne le télécharge pas et n’intervient pas dans son octroi de licence. Le build lit le chemin configuré, vérifie que le fichier existe et l’attache sans le modifier.

Indiquez dans la configuration le chemin vers l’agent de votre installation JRebel, qu’il s’agisse d’une bibliothèque native ou d’un fichier jar :

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<hotswap>
  <jrebel>
    <path>/path/to/libjrebel64.dylib</path>
  </jrebel>
</hotswap>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    jrebel {
      path = file('/path/to/libjrebel64.dylib')
    }
  }
}
```

</TabItem>
</Tabs>

Le chemin est obligatoire. Un build qui sélectionne JRebel sans l’indiquer échoue avec une erreur signalant le paramètre manquant.

Avec JRebel, toutes les modifications de classes, y compris celles de leur structure, s’appliquent sur n’importe quel environnement d’exécution Java.

## Sélection en ligne de commande {#command-line-selection}

La propriété `webforj.hotswap` remplace la configuration du fichier de build pour une seule exécution. Les valeurs acceptées sont `hotswapAgent`, `jrebel` et `off`. Toute autre valeur entraîne l’échec du build, avec une erreur répertoriant les valeurs valides. La sélection de `jrebel` nécessite toujours que le chemin vers l’agent soit indiqué dans la configuration.

<Tabs>
<TabItem value="maven" label="Maven">

```bash
mvn -Dwebforj.hotswap=off
mvn -Dwebforj.hotswap=hotswapAgent
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```bash
./gradlew bootRun -Pwebforj.hotswap=off
./gradlew bootRun -Pwebforj.hotswap=hotswapAgent
```

</TabItem>
</Tabs>

## Application d’une modification {#applying-a-change}

Compilez une modification pour qu’elle soit prise en compte par l’application en cours d’exécution. Enregistrez votre fichier dans un IDE qui compile à l’enregistrement, ou lancez une compilation dans un deuxième terminal.

Lorsque toutes les classes modifiées appartiennent à ce que la page actuelle affiche, la partie concernée est reconstruite sur place et l’état de l’application est conservé. Sinon, la page est entièrement rechargée : si l’application n’a pas de routage, si une classe ne fait pas partie des routes affichées ou si la reconstruction ne peut pas être effectuée. Chaque modification compilée produit une mise à jour dans le navigateur.

## Accélération des rechargements dans IntelliJ IDEA {#speeding-up-reloads-in-intellij-idea}

IntelliJ IDEA ne compile à l’enregistrement que si les compilations automatiques sont activées, et il attend avant chaque étape. Réduisez les délais d’attente pour que les modifications soient prises en compte plus rapidement par l’application en cours d’exécution.

### Activation des compilations automatiques {#enable-automatic-builds}

Ouvrez les paramètres et activez les options suivantes :

1. Dans **Build, Execution, Deployment** > **Compiler**, activez **Build project automatically**.
<!-- vale off -->
2. Dans **Advanced Settings** > **Compiler**, activez **Allow auto-make to start even if developed application is currently running**.
<!-- vale on -->
3. Dans **Appearance & Behavior** > **System Settings**, sous **Autosave**, activez **Save files if the IDE is idle for** et définissez la valeur sur `1` seconde.

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

Appuyez sur <kbd>Ctrl+ALt+S</kbd>, ou choisissez **File** > **Settings**.

</TabItem>
<TabItem value="macos" label="macOS">

Appuyez sur <kbd>Cmd+,</kbd>, ou choisissez **IntelliJ IDEA** > **Settings**.

</TabItem>
</Tabs>

### Réduction des délais de compilation {#shorten-the-build-delays}

Définissez les clés de registre suivantes sur `0` :

| Clé | Description |
|-----|-------------|
| `compiler.automake.trigger.delay` | Délai entre la modification d’un fichier et le début d’un build. |
| `compiler.automake.build.while.idle.timeout` | Durée d’inactivité avant le début d’un build. |
| `compiler.automake.postpone.when.idle.less.than` | Durée d’inactivité en dessous de laquelle IntelliJ IDEA reporte un build. |

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

1. Appuyez sur `Ctrl+Shift+A` pour ouvrir **Find Action**.
2. Saisissez `Registry...` et sélectionnez l’option correspondante.

</TabItem>
<TabItem value="macos" label="macOS">

1. Appuyez sur `Cmd+Shift+A` pour ouvrir **Find Action**.
2. Saisissez `Registry...` et sélectionnez l’option correspondante.

</TabItem>
</Tabs>

Vous pouvez également appuyer deux fois sur `Shift` pour ouvrir **Search Everywhere**, passer à l’onglet **Actions** et saisir `Registry`.

Dans la boîte de dialogue **Registry**, saisissez le nom d’une clé pour filtrer la liste, puis définissez sa valeur sur `0`.

:::warning
Les entrées du registre sont des paramètres avancés d’IntelliJ IDEA qui ne sont pas pris en charge. Sélectionnez **Restore Defaults** dans la boîte de dialogue **Registry** pour les réinitialiser.
:::
