---
title: Hotswap
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 5c17c1ef76de9c15b7177aee4b2b8419
---
# Remplacement à chaud <DocChip chip='since' label='26.02' />

Un outil de remplacement à chaud applique les modifications des classes compilées à l’application en cours d’exécution, sans redémarrage. L’application conserve son état entre les mises à jour. L’outil est indiqué dans la configuration du [plug-in de build webforJ](/docs/configuration/build-plugin) et se connecte au démarrage de l’application par le build. La commande d’exécution reste la même et le projet ne déclare aucune dépendance pour cet outil.

Deux outils sont pris en charge :

- **HotswapAgent** est open source. Le plug-in de build télécharge l’agent lors de la première exécution et le met en cache.
- **JRebel** est un produit commercial. Il nécessite votre propre installation et une licence.

Configurez-en un seul. Un build qui en indique deux échoue en affichant une erreur mentionnant les deux.

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

Deux options permettent d’affiner la connexion :

| Option | Description |
|--------|-------------|
| `version` | Une version précise de l’agent, au lieu de celle sélectionnée par le plug-in. |
| `path` | Un fichier JAR de l’agent présent sur le disque, utilisé directement sans téléchargement. Pour les machines sans accès réseau ou pour une version personnalisée de l’agent. |

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

### Modifications de la structure d’une classe {#class-structure-changes}

Les modifications du corps d’une méthode s’appliquent sur n’importe quelle machine virtuelle Java. Les changements de structure d’une classe, comme l’ajout d’un champ ou d’une méthode, nécessitent une machine virtuelle qui accepte l’option `-XX:+AllowEnhancedClassRedefinition`, fournie par le [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases). Le build détecte cette fonctionnalité et l’active. Consultez la section [Prérequis](/docs/introduction/prerequisites#java-development-kit-jdk-21) pour installer un JetBrains Runtime.

Sans cette fonctionnalité, les modifications du corps des méthodes s’appliquent toujours, mais les changements de structure d’une classe ne sont pas pris en compte par l’application en cours d’exécution avant son redémarrage. Le journal du build affiche un avertissement indiquant le prérequis, et le navigateur affiche une notification une seule fois.

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) est un produit commercial, distribué sous licence par son fournisseur. webforJ ne le fournit pas, ne le télécharge pas et ne participe pas à son octroi de licence. Le build lit le chemin configuré, vérifie que le fichier existe et le connecte tel quel.

Indiquez dans la configuration le chemin vers l’agent de votre installation JRebel, qu’il s’agisse d’une bibliothèque native ou d’un fichier JAR :

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

Le chemin est obligatoire. Un build qui sélectionne JRebel sans ce chemin échoue en affichant une erreur indiquant le paramètre manquant.

Avec JRebel, toutes les modifications des classes, y compris les changements de structure, s’appliquent sur n’importe quel runtime Java.

## Sélection en ligne de commande {#command-line-selection}

La propriété `webforj.hotswap` remplace le fichier de build pour une seule exécution. Les valeurs acceptées sont `hotswapAgent`, `jrebel` et `off`. Toute autre valeur fait échouer le build avec une erreur qui répertorie les valeurs valides. La sélection de `jrebel` nécessite toujours que le chemin de l’agent soit indiqué dans la configuration.

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

## Appliquer une modification {#applying-a-change}

Compilez une modification pour qu’elle soit appliquée à l’application en cours d’exécution. Enregistrez les fichiers dans un IDE qui compile à l’enregistrement, ou lancez une compilation dans un deuxième terminal.

Lorsque toutes les classes modifiées sont utilisées par la page affichée, la partie concernée est reconstruite sur place et l’état de l’application est conservé. Sinon, la page est entièrement rechargée : si l’application n’utilise pas de routage, si une classe est hors des routes affichées ou si la reconstruction ne peut pas être effectuée. Une modification compilée produit une seule mise à jour dans le navigateur.

## Accélérer les rechargements dans IntelliJ IDEA {#speeding-up-reloads-in-intellij-idea}

IntelliJ IDEA ne compile à l’enregistrement que si les builds automatiques sont activés, et marque une pause avant chaque étape. Réduisez ces délais pour que les modifications soient appliquées plus rapidement à l’application en cours d’exécution.

### Activer les builds automatiques {#enable-automatic-builds}

Ouvrez les paramètres et activez les options suivantes :

1. Dans **Build, Execution, Deployment** > **Compiler**, activez **Build project automatically**.
<!-- vale off -->
2. Dans **Advanced Settings** > **Compiler**, activez **Allow auto-make to start even if developed application is currently running**.
<!-- vale on -->
3. Dans **Appearance & Behavior** > **System Settings**, sous **Autosave**, activez **Save files if the IDE is idle for** et définissez la valeur sur `1` seconde.

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

Appuyez sur <kbd>Ctrl</kbd>+<kbd>ALt</kbd>+<kbd>S</kbd>, ou choisissez **File** > **Settings**.

</TabItem>
<TabItem value="macos" label="macOS">

Appuyez sur <kbd>Cmd</kbd>+<kbd>+</kbd>, ou choisissez **IntelliJ IDEA** > **Settings**.

</TabItem>
</Tabs>

### Réduire les délais de build {#shorten-the-build-delays}

Définissez les clés de registre suivantes sur `0` :

| Clé | Description |
|-----|-------------|
| `compiler.automake.trigger.delay` | Délai entre une modification de fichier et le démarrage d’un build. |
| `compiler.automake.build.while.idle.timeout` | Durée d’inactivité avant le démarrage d’un build. |
| `compiler.automake.postpone.when.idle.less.than` | Durée d’inactivité en dessous de laquelle IntelliJ IDEA reporte un build. |

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

1. Appuyez sur <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>A</kbd> pour ouvrir **Find Action**.
2. Saisissez `Registry...` et sélectionnez l’option correspondante.

</TabItem>
<TabItem value="macos" label="macOS">

1. Appuyez sur <kbd>Cmd</kbd>+<kbd>Shift</kbd>+<kbd>A</kbd> pour ouvrir **Find Action**.
2. Saisissez `Registry...` et sélectionnez l’option correspondante.

</TabItem>
</Tabs>

Vous pouvez également appuyer deux fois sur <kbd>Shift</kbd> pour ouvrir **Search Everywhere**, passer à l’onglet **Actions**, puis saisir `Registry`.

Dans la boîte de dialogue **Registry**, saisissez le nom d’une clé pour filtrer la liste, puis définissez sa valeur sur `0`.

:::warning
Les entrées de registre sont des paramètres avancés d’IntelliJ IDEA qui ne sont pas pris en charge. Sélectionnez **Restore Defaults** dans la boîte de dialogue **Registry** pour les réinitialiser.
:::
