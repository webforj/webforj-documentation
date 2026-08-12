---
title: Hotswap
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 0943bf726abb55f753a0149ca3744ad7
---
# Hotswap <DocChip chip='since' label='26.02' />

Un outil de hotswap applique les modifications de classes compilées à l'application en cours d'exécution sans redémarrage. L'application conserve son état entre les mises à jour. L'outil est nommé dans la configuration du [module de build webforJ](/docs/configuration/build-plugin) et s'attache lorsque le build démarre l'application. La commande d'exécution reste la même, et le projet ne déclare aucune dépendance pour cela.

Deux outils sont pris en charge :

- **HotswapAgent** est open source. Le module de build télécharge l'agent lors du premier exécution et le met en cache.
- **JRebel** est un produit commercial. Il nécessite votre propre installation et licence.

Configurez exactement un. Un build qui nomme les deux échoue avec une erreur nommant les deux.

## HotswapAgent {#hotswapagent}

Un élément vide est une configuration complète :

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

Deux options affinent l'attachement :

| Option | Description |
|--------|-------------|
| `version` | Une version spécifique de l'agent au lieu de celle sélectionnée par le module. |
| `path` | Un jar d'agent sur disque, utilisé directement sans téléchargement. Pour les machines sans accès réseau ou pour une construction d'agent personnalisée. |

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

### Class structure changes {#class-structure-changes}

Les modifications du corps de méthode s'appliquent sur n'importe quelle machine virtuelle Java. Les changements dans la structure d'une classe, tels qu'un nouveau champ ou une nouvelle méthode, nécessitent une machine virtuelle qui accepte l'option `-XX:+AllowEnhancedClassRedefinition`, que fournit le [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases). Le build détecte la capacité et l'active. Voir [Prérequis](/docs/introduction/prerequisites#java-development-kit-jdk-21) pour installer un JetBrains Runtime.

Sans cette capacité, les modifications de corps de méthode s'appliquent toujours, et un changement de structure de classe n'atteint pas l'application en cours d'exécution jusqu'à un redémarrage. Le journal de build imprime un avertissement nommant l'exigence, et le navigateur affiche une notification une fois.

## JRebel {#jrebel}

[JRebel](https://www.jrebel.com/) est un produit commercial, sous licence de son fournisseur. webforJ ne l'expédie pas, ne le télécharge pas et ne participe pas à sa licence. Le build lit le chemin configuré, vérifie que le fichier existe, et l'attache tel quel.

Pointant la configuration sur l'agent de votre installation JRebel, une bibliothèque native ou un jar :

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

Le chemin est requis. Un build sélectionnant JRebel sans cela échoue avec une erreur nommant le paramètre manquant.

Avec JRebel, tous les changements de classes, y compris les changements de structure, s'appliquent sur n'importe quel runtime Java.

## Command line selection {#command-line-selection}

La propriété `webforj.hotswap` remplace le fichier de build pour une seule exécution. Les valeurs acceptées sont `hotswapAgent`, `jrebel` et `off`. Toute autre valeur échoue le build avec une erreur listant les valeurs valides. La sélection de `jrebel` nécessite toujours le chemin de l'agent dans la configuration.

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

## Applying a change {#applying-a-change}

Compilez un changement et il atteint l'application en cours d'exécution. Enregistrez dans un IDE qui compile à l'enregistrement, ou exécutez une compilation dans un second terminal.

Lorsque chaque classe modifiée appartient à ce que la page actuelle rend, la partie affectée se reconstruit sur place et l'état de l'application reste. Sinon, la page se recharge entièrement : pour une application sans routage, pour une classe en dehors des itinéraires rendus, ou lorsque la reconstruction ne peut pas être effectuée. Un changement compilé produit une mise à jour dans le navigateur.
