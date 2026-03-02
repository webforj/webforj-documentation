---
title: Agent Skills
sidebar_position: 10
sidebar_class_name: new-content
_i18n_hash: cf22942f0e73a936bef31cf8a3a9a043
---
Les compétences des agents enseignent aux assistants de codage AI comment créer des applications webforJ en utilisant les APIs, les tokens de design et les modèles de composants appropriés. Au lieu de deviner les conventions de framework, un assistant AI charge une compétence et suit son workflow structuré pour produire du code qui compile et respecte les meilleures pratiques dès la première tentative.

Les compétences suivent la [spécification des compétences des agents](https://agentskills.io/specification) ouverte et fonctionnent avec plusieurs assistants AI, notamment Claude Code, GitHub Copilot dans VS Code et Cursor. Chaque compétence est un répertoire unique avec un fichier `SKILL.md` décrivant le but et le workflow de la compétence, ainsi que des répertoires `references/` et `scripts/` pour la documentation de support et les scripts d'aide.

Les compétences des agents pour webforJ sont disponibles dans le dépôt GitHub [webforj/webforj-agent-skills](https://github.com/webforj/webforJ-agent-skills). Avec ces compétences installées, une AI chargera automatiquement ces fichiers lorsqu'elle détectera une tâche pertinente. Par exemple, demander à une AI de "thématiser cette application avec une palette bleue" déclenche la compétence `styling-apps`, qui guide l'AI à rechercher les tokens DWC valides, à écrire du CSS scoping et à valider chaque nom de variable avant de produire une sortie.

## Pourquoi utiliser des compétences ? {#why-use-skills}

Sans compétences, les assistants AI produisent souvent du code webforJ qui semble plausible mais échoue en pratique. Les problèmes courants incluent :

- Inventer des noms de tokens `--dwc-*` qui n'existent pas (le CSS compile mais n'a pas d'effet)
- Utiliser la mauvaise classe de base pour les wrappers de composants (`Composite` au lieu de `ElementComposite`, ou vice versa)
- Manquer de motifs `PropertyDescriptor`, d'annotations d'événements ou d'interfaces de préoccupation
- Coder en dur des couleurs qui cassent le mode sombre
- Sauter des étapes de validation qui détectent des échecs silencieux

Les compétences éliminent ces problèmes en donnant à l'AI des tableaux de décision exacts, des scripts de recherche et des listes de vérification pour chaque type de tâche.

## Comment les compétences diffèrent du MCP {#how-skills-differ-from-mcp}

Les compétences et le [serveur MCP webforJ](./mcp) servent des rôles complémentaires. Le MCP fournit des outils en direct que l'AI peut appeler à l'exécution pour rechercher de la documentation ou générer des projets. Les compétences fournissent des connaissances statiques et des workflows étape par étape qui guident la manière dont l'AI aborde une tâche.

| | Serveur MCP | Compétences des agents |
|---|---|---|
| **Ce qu'il fournit** | Outils en direct : recherche de documentation, génération de projets, création de thèmes | Connaissances statiques : workflows, tableaux de décision, documents de référence, scripts d'aide |
| **Quand il agit** | À la demande, lorsque l'AI appelle un outil | Automatiquement, lorsque l'AI détecte une tâche correspondante |
| **Meilleur pour** | Rechercher des APIs spécifiques, générer des projets de démarrage, créer des palettes de thèmes | Tâches de bout en bout qui nécessitent de suivre les conventions de framework et des workflows en plusieurs étapes |

En pratique, les deux fonctionnent bien ensemble. L'outil `webforj-create-theme` du serveur MCP génère une palette valide à partir d'une seule couleur, et la compétence `styling-apps` guide ensuite l'AI à travers le stylisme au niveau des composants et la validation du mode sombre en utilisant cette palette.

Les compétences sont des fichiers statiques lus à partir du disque - elles n'ajoutent pas de surcharge d'exécution ni n'effectuent d'appels d'API externes. L'AI charge le matériel de référence d'une compétence dans sa fenêtre de contexte lorsque cela est pertinent, ce qui utilise des tokens de contexte, mais la qualité de sortie résultante pour le travail spécifique au framework est significativement plus élevée.

## Installation {#installation}

Clonez le [dépôt des compétences agents webforJ](https://github.com/webforj/webforJ-agent-skills), puis copiez les dossiers de compétences à l'emplacement où votre outil AI s'y attend. Chaque outil prend en charge deux scopes :

- **Scope de projet** : la compétence n'est disponible que dans ce projet
- **Scope utilisateur** : la compétence est disponible dans tous vos projets

<Tabs groupId="ide">
<TabItem value="claude-code" label="Claude Code" default>

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Scope de projet
cp -r creating-components /path/to/your/project/.claude/skills/
cp -r styling-apps /path/to/your/project/.claude/skills/

# Scope utilisateur
cp -r creating-components ~/.claude/skills/
cp -r styling-apps ~/.claude/skills/
```

</TabItem>
<TabItem value="vscode" label="VS Code Copilot">

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Scope de projet
cp -r creating-components /path/to/your/project/.github/skills/
cp -r styling-apps /path/to/your/project/.github/skills/

# Scope utilisateur
cp -r creating-components ~/.copilot/skills/
cp -r styling-apps ~/.copilot/skills/
```

</TabItem>
<TabItem value="cursor" label="Cursor">

```bash
git clone https://github.com/webforj/webforJ-agent-skills.git
cd webforJ-agent-skills

# Scope de projet
cp -r creating-components /path/to/your/project/.cursor/skills/
cp -r styling-apps /path/to/your/project/.cursor/skills/

# Scope utilisateur
cp -r creating-components ~/.cursor/skills/
cp -r styling-apps ~/.cursor/skills/
```

</TabItem>
</Tabs>

:::tip[Quel scope utiliser]
Utilisez le **scope de projet** lorsque vous collaborez avec une équipe afin que tous les membres du projet bénéficient des mêmes compétences. Utilisez le **scope utilisateur** lorsque vous travaillez sur plusieurs projets webforJ et souhaitez que les compétences soient disponibles partout sans les copier dans chaque dépôt.
:::

## Compétences disponibles {#available-skills}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>creating-components</code></strong>: construire des composants webforJ réutilisables à partir de bibliothèques de composants web, de bibliothèques JavaScript ou de composants webforJ existants
  </AccordionSummary>
  <AccordionDetails>
    <div>

[Cette compétence](https://github.com/webforj/webforJ-agent-skills/tree/main/creating-components) guide un assistant AI à travers la construction de composants Java réutilisables à partir de n'importe quelle source, qu'il s'agisse d'une bibliothèque de composants web existante, d'une bibliothèque JavaScript ordinaire ou d'une composition de composants webforJ existants.

**Ce qu'elle couvre**

La compétence définit cinq chemins pour créer des composants, et enseigne à l'AI à sélectionner le bon en fonction de la tâche :

| Chemin | Quand utiliser | Classe de base |
|---|---|---|
| Enrober une bibliothèque d'éléments personnalisés existante | La bibliothèque expédie des éléments personnalisés (`<x-button>`, `<x-dialog>`) | `ElementComposite` / `ElementCompositeContainer` |
| Construire un élément personnalisé, puis l'enrober | Nouveau composant visuel ou envelopper une bibliothèque JS ordinaire | `ElementComposite` / `ElementCompositeContainer` |
| Composer des composants webforJ | Combiner des composants webforJ existants en une unité réutilisable | `Composite<T>` |
| Étendre un élément HTML | Intégration légère ponctuelle sans Shadow DOM | `Div`, `Span`, etc. |
| Utilitaire au niveau de la page | API du navigateur ou fonctionnalité globale sans widget DOM | Classe Java ordinaire + `EventDispatcher` |

**Workflow**

Pour l'enrober d'élément personnalisé (le chemin le plus commun), la compétence guide l'AI à travers un workflow structuré :

1. **Configuration** : télécharger des JS/CSS tiers dans le répertoire `src/main/resources/static/libs/` du projet. La compétence ordonne à l'AI de préférer les ressources locales aux liens CDN pour une fiabilité hors ligne.
2. **Extraire les données du composant** : utiliser le script inclus `extract_components.mjs` pour analyser un manifeste d'éléments personnalisés et produire une spécification structurée des propriétés, événements, emplacements et propriétés CSS personnalisées de chaque composant.
3. **Écrire des wrappers Java** : créer des classes `ElementComposite` ou `ElementCompositeContainer` avec des champs `PropertyDescriptor`, des classes d'événements, des méthodes d'emplacement et des interfaces de préoccupation, le tout suivant les conventions webforJ.
4. **Écrire des tests** : générer des tests JUnit 5 à l'aide de `PropertyDescriptorTester` et de motifs de test structurés pour les propriétés, les emplacements et les événements.

**Matériel de référence**

La compétence comprend huit documents de référence couvrant les motifs `ElementComposite`, la composition de composants, les descripteurs de propriété, la gestion d'événements, l'interopérabilité JavaScript, les motifs de test et les anti-motifs courants.

</div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong><code>styling-apps</code></strong>: thématiser et styliser les applications webforJ en utilisant le système de tokens de design DWC
  </AccordionSummary>
  <AccordionDetails>
    <div>

[Cette compétence](https://github.com/webforj/webforJ-agent-skills/tree/main/styling-apps) enseigne à un assistant AI comment styliser des applications webforJ en utilisant le système de tokens de design DWC. Le principe fondamental est que toutes les valeurs visuelles utilisent les propriétés CSS personnalisées `--dwc-*`. La compétence impose cela en fournissant des étapes de validation et des scripts de recherche qui empêchent l'AI d'inventer des noms de tokens ou de coder en dur des couleurs.

**Ce qu'elle couvre**

| Tâche | Approche que la compétence enseigne |
|------|---------------------------|
| Reskinning de couleur | Remplacer les tokens de teinte, de saturation et de contraste à `:root` |
| Stylisation de composant | Rechercher d'abord les variables CSS du composant, faire appel à `::part()` seulement si nécessaire |
| Mise en page et espacement | Utiliser des tokens `--dwc-space-*` et `--dwc-size-*` |
| Typographie | Utiliser des tokens `--dwc-font-*` |
| Thème complet | Configuration de palette avec remapping des tokens sémantiques |
| Stylisation des tables | Sélecteurs `::part()` uniquement (les tables n'exposent aucune variable CSS) |
| Graphiques Google | Fichier de thème JSON chargé via `Assets.contentOf()` et Gson |

**Workflow**

La compétence impose une discipline stricte de recherche avant écriture :

1. **Classifiez la tâche** : déterminez s'il s'agit d'un reskinning de palette, d'une stylisation de composant, d'un travail de mise en page ou d'un thème complet.
2. **Analysez l'application** : lisez le code source Java pour trouver chaque composant, variante de thème et espace en usage.
3. **Recherchez chaque composant** : exécutez le script inclus `component_styles.py` pour récupérer les variables CSS exactes, les noms `::part()` et les attributs réfléchis que chaque composant prend en charge. L'AI n'écrit aucun CSS jusqu'à ce que cette étape soit terminée.
4. **Écrivez le CSS** : produisez du CSS imbriqué et compact qui suit les conventions DWC : les tokens globaux d'abord, suivis des variables CSS des composants, puis les remplacements `::part()` en dernier recours.
5. **Valider** : réexécutez le script de recherche et vérifiez que chaque token, nom de partie et sélecteur dans la sortie existe réellement. Corrigez tout ce qui échoue.

**Règles clés que la compétence impose**

- **Sept palettes seulement** : `primary`, `success`, `warning`, `danger`, `info`, `default` et `gray`. Des noms comme `secondary` ou `accent` n'existent pas dans DWC et échouent silencieusement.
- **Pas de couleurs codées en dur** : chaque valeur de couleur doit être une référence `var()`, y compris à l'intérieur de `box-shadow` et `border`. Les valeurs codées en dur cassent le mode sombre.
- **Variables CSS sur `::part()`** : les variables CSS des composants sont l'API de stylisation prévue. `::part()` est la sortie de secours pour les cas où aucune variable n'existe.
- **Sélecteurs scopés** : les sélecteurs de balise nus sur des composants avec des attributs `theme` ou `expanse` remplacent toutes les variantes. La compétence exige `:not([theme])` ou `[theme~="value"]` pour le scoping.

</div>
  </AccordionDetails>
</Accordion>
