---
title: Frontend testing
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Write tests for the frontend sources of a webforJ app with the Bun test
  runner.
_i18n_hash: 31f4ee87c13ac75eb209408feef510fb
---
# Tests frontend <DocChip chip='since' label='26.01' /> {#frontend-testing}

Lorsqu'un projet rédige des sources frontend avec le [regroupement frontend](/docs/managing-resources/bundler/overview), il les teste avec le [Bun](https://bun.sh/) test runner. Un composant écrit en TypeScript ou une partie de la logique client est couvert de la même manière que le backend.

## Où se trouvent les tests {#where-tests-live}

Les tests frontend se trouvent sous `src/main/frontend`, à côté des sources qu'ils couvrent. Un fichier nommé `*.test.ts` ou `*.spec.ts` est un test.

```ts title="src/main/frontend/math/math.ts"
export const add = (a: number, b: number): number => a + b;
```

```ts title="src/main/frontend/math/math.test.ts"
import { expect, test } from 'bun:test';
import { add } from './math';

test('add sums two numbers', () => {
  expect(add(2, 3)).toBe(5);
});
```

Un test importe ses outils de `bun:test`, l'API de test propre au runner, y compris `expect`, `test`, et `describe`.

## Exécution des tests {#running-the-tests}

Les tests frontend s'exécutent dans le cadre de la build, donc `mvn test` les exécute avec les tests Java, sans rien d'autre à invoquer. Voir [Build et tests](/docs/managing-resources/bundler/build-and-tests#frontend-tests) pour l'étape de test dans la build, quand elle est ignorée, et comment un test échouant stoppe une release.
