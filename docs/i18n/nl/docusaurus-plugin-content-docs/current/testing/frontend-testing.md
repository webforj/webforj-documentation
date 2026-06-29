---
title: Frontend testing
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Write tests for the frontend sources of a webforJ app with the Bun test
  runner.
_i18n_hash: 31f4ee87c13ac75eb209408feef510fb
---
# Frontend testen <DocChip chip='since' label='26.01' /> {#frontend-testing}

Wanneer een project frontendbronnen maakt met de [frontend bundler](/docs/managing-resources/bundler/overview), worden deze getest met de [Bun](https://bun.sh/) test runner. Een component geschreven in TypeScript of een stuk clientlogica wordt op dezelfde manier gedekt als de backend.

## Waar tests zich bevinden {#where-tests-live}

Frontend tests bevinden zich onder `src/main/frontend`, naast de bronnen die ze dekken. Een bestand genaamd `*.test.ts` of `*.spec.ts` is een test.

```ts title="src/main/frontend/math/math.ts"
export const add = (a: number, b: number): number => a + b;
```

```ts title="src/main/frontend/math/math.test.ts"
import { expect, test } from 'bun:test';
import { add } from './math';

test('add telt twee nummers op', () => {
  expect(add(2, 3)).toBe(5);
});
```

Een test importeert zijn tools van `bun:test`, de eigen [test API](https://bun.sh/docs/cli/test) van de runner, inclusief `expect`, `test`, en `describe`.

## Tests uitvoeren {#running-the-tests}

Frontend tests worden uitgevoerd als onderdeel van de build, dus `mvn test` voert ze uit samen met de Java-tests, zonder extra aanroepen. Zie [Build en tests](/docs/managing-resources/bundler/build-and-tests#frontend-tests) voor de teststap in de build, wanneer deze wordt overgeslagen en hoe een mislukte test een release stopt.
