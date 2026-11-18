---
title: Pruebas de frontend
sidebar_position: 5
description: >-
  Prueba el código TypeScript y la lógica del cliente de una app webforJ con el
  ejecutor de pruebas Bun: dónde guardar las pruebas junto al frontend y cómo
  ejecutarlas.
_i18n_title_hash: 68de7033809511e9c6c5947b06b149d6
_i18n_description_hash: 8ec21415ae4edbce4bd1b9ddc70295c3
_i18n_hash: 31f4ee87c13ac75eb209408feef510fb
---
# Pruebas de frontend <DocChip chip='since' label='26.01' /> {#frontend-testing}

Cuando un proyecto autoriza fuentes de frontend con el [frontend bundler](/docs/managing-resources/bundler/overview), las prueba con el [Bun](https://bun.sh/) test runner. Un componente escrito en TypeScript o una pieza de lógica del cliente se cubre de la misma manera que el backend.

## Dónde viven las pruebas {#where-tests-live}

Las pruebas de frontend viven en `src/main/frontend`, junto a las fuentes que cubren. Un archivo llamado `*.test.ts` o `*.spec.ts` es una prueba.

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

Una prueba importa sus herramientas de `bun:test`, la propia [API de pruebas](https://bun.sh/docs/cli/test) del runner, incluyendo `expect`, `test` y `describe`.

## Ejecutando las pruebas {#running-the-tests}

Las pruebas de frontend se ejecutan como parte de la construcción, por lo que `mvn test` las ejecuta junto con las pruebas de Java, sin necesidad de invocar nada extra. Consulta [Construcción y pruebas](/docs/managing-resources/bundler/build-and-tests#frontend-tests) para el paso de prueba en la construcción, cuándo se omite y cómo una prueba fallida detiene un lanzamiento.
