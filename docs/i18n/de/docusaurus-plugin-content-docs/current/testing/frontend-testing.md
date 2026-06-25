---
title: Frontend testing
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Write tests for the frontend sources of a webforJ app with the Bun test
  runner.
_i18n_hash: 31f4ee87c13ac75eb209408feef510fb
---
# Frontend-Tests <DocChip chip='since' label='26.01' /> {#frontend-testing}

Wenn ein Projekt Frontend-Quellen mit dem [Frontend-Bundler](/docs/managing-resources/bundler/overview) erstellt, testet es diese mit dem [Bun](https://bun.sh/) Test-Läufer. Eine in TypeScript geschriebene Komponente oder ein Stück Client-Logik wird auf die gleiche Weise wie das Backend abgedeckt.

## Wo die Tests sitzen {#where-tests-live}

Frontend-Tests sitzen unter `src/main/frontend`, neben den Quellen, die sie abdecken. Eine Datei, die mit `*.test.ts` oder `*.spec.ts` endet, ist ein Test.

```ts title="src/main/frontend/math/math.ts"
export const add = (a: number, b: number): number => a + b;
```

```ts title="src/main/frontend/math/math.test.ts"
import { expect, test } from 'bun:test';
import { add } from './math';

test('add summiert zwei Zahlen', () => {
  expect(add(2, 3)).toBe(5);
});
```

Ein Test importiert seine Werkzeuge von `bun:test`, der eigenen [Test-API](https://bun.sh/docs/cli/test) des Läufers, einschließlich `expect`, `test` und `describe`.

## Tests ausführen {#running-the-tests}

Frontend-Tests werden als Teil des Builds ausgeführt, sodass `mvn test` sie zusammen mit den Java-Tests ausführt, ohne dass etwas zusätzlich aufgerufen werden muss. Siehe [Build und Tests](/docs/managing-resources/bundler/build-and-tests#frontend-tests) für den Testschritt im Build, wann er übersprungen wird und wie ein fehlgeschlagener Test einen Release stoppt.
