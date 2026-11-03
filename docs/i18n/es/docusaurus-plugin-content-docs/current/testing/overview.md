---
sidebar_position: 1
title: Pruebas
description: >-
  Combine JUnit unit tests with Selenium or Playwright end-to-end tests to
  validate webforJ components, logic, and full user journeys.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 58ac266506a221d71e22c723189aa08f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Pruebas en webforJ

Las pruebas en las aplicaciones webforJ combinan pruebas unitarias, de frontend y de extremo a extremo (E2E), cada una con un propósito distinto para mantener la estabilidad y la fiabilidad de la aplicación.

## Pruebas unitarias {#unit-testing}

Las pruebas unitarias se centran en verificar componentes individuales o la lógica del backend de forma aislada. Al seguir las prácticas habituales de pruebas de Java, como usar [JUnit](https://junit.org/junit5/), los desarrolladores pueden validar la lógica específica de la aplicación y comprobar que cada «unidad» funciona según lo esperado.

Para las vistas y los componentes, [TestBed](./testbed/overview) permite hacer pruebas sin navegador en pruebas JUnit normales, sin necesidad de un navegador ni de un servidor. Una prueba añade una vista a la página, interactúa con ella como lo haría un usuario y comprueba el resultado desde Java.

## Pruebas de frontend {#frontend-testing}

Las pruebas de frontend abarcan el código fuente que un proyecto crea con el [empaquetador de frontend](/docs/managing-resources/bundler/overview). El ejecutor de pruebas de [Bun](https://bun.sh/) las ejecuta como parte de la misma compilación que ejecuta las pruebas de Java, de modo que un componente de TypeScript o una parte de la lógica del cliente se verifica igual que el backend. Consulta [Pruebas de frontend](./frontend-testing).

## Pruebas de extremo a extremo (E2E) {#end-to-end-e2e-testing}

Las pruebas de extremo a extremo validan la experiencia de los usuarios en las aplicaciones webforJ, que generan interfaces web dinámicas de una sola página. Estas pruebas simulan las interacciones de los usuarios y verifican las funciones de toda la aplicación.

Con herramientas como [**Selenium**](https://www.selenium.dev/) y [**Playwright**](https://playwright.dev/java/docs/intro), puedes:

- Automatizar las interacciones con el navegador simulando clics en botones y envíos de formularios.
- Verificar la representación y la interactividad coherentes de los componentes dinámicos de la interfaz de usuario.
- Confirmar que el comportamiento es coherente en distintos navegadores y dispositivos.

## Combinación de estrategias de prueba {#combining-testing-strategies}

Al combinar las pruebas unitarias y las pruebas E2E:

1. **Aislar problemas**: detectar y resolver pronto los errores de los componentes mediante pruebas unitarias.
2. **Confirmar la fiabilidad**: validar los recorridos completos de los usuarios y las integraciones del sistema mediante pruebas E2E.

## Temas {#topics}

<DocCardList className="topics-section" />
