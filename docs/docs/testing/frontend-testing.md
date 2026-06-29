---
title: Frontend testing
sidebar_position: 5
sidebar_class_name: new-content
description: Write tests for the frontend sources of a webforJ app with the Bun test runner.
---

# Frontend testing <DocChip chip='since' label='26.01' /> {#frontend-testing}

When a project authors frontend sources with the [frontend bundler](/docs/managing-resources/bundler/overview), it tests them with the [Bun](https://bun.sh/) test runner. A component written in TypeScript or a piece of client logic is covered the same way the backend is.

## Where tests live {#where-tests-live}

Frontend tests live under `src/main/frontend`, next to the sources they cover. A file named `*.test.ts` or `*.spec.ts` is a test.

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

A test imports its tools from `bun:test`, the runner's own [test API](https://bun.sh/docs/cli/test), including `expect`, `test`, and `describe`.

## Running the tests {#running-the-tests}

Frontend tests run as part of the build, so `mvn test` runs them with the Java tests, nothing extra to invoke. See [Build and tests](/docs/managing-resources/bundler/build-and-tests#frontend-tests) for the test step in the build, when it's skipped, and how a failing test stops a release.
