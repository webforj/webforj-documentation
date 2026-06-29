---
title: Frontend testing
sidebar_position: 5
sidebar_class_name: new-content
description: >-
  Write tests for the frontend sources of a webforJ app with the Bun test
  runner.
_i18n_hash: 31f4ee87c13ac75eb209408feef510fb
---
# Frontend-testaus <DocChip chip='since' label='26.01' /> {#frontend-testing}

Kun projekti kirjoittaa frontend-lähteitä [frontend bundler](/docs/managing-resources/bundler/overview) -työkalulla, se testaa niitä [Bun](https://bun.sh/) testimoduulilla. TypeScriptillä kirjoitettu komponentti tai asiakaslogiikka testataan samalla tavalla kuin taustajärjestelmä.

## Missä testit sijaitsevat {#where-tests-live}

Frontend-testit sijaitsevat `src/main/frontend`-hakemistossa, lähellä lähteitä, joita ne kattavat. Tiedosto, jonka nimi päättyy `*.test.ts` tai `*.spec.ts`, on testi.

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

Testi tuo työkalut `bun:test`-moduulista, joka on ajurin oma [testi-API](https://bun.sh/docs/cli/test), mukaan lukien `expect`, `test` ja `describe`.

## Testien suorittaminen {#running-the-tests}

Frontend-testit suoritetaan osana rakennusprosessia, joten `mvn test` suorittaa ne Java-testejä yhdessä, eikä mitään lisätoimia tarvitse kutsua. Katso [Rakennus ja testit](/docs/managing-resources/bundler/build-and-tests#frontend-tests) testivaiheesta rakennuksessa, kun se ohitetaan, ja siitä, miten epäonnistunut testi pysäyttää julkaisun.
