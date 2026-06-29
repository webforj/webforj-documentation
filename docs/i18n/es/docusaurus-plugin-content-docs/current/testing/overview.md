---
sidebar_position: 1
title: Testing
description: >-
  Combine JUnit unit tests with Selenium or Playwright end-to-end tests to
  validate webforJ components, logic, and full user journeys.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 3c566f2e9edf3bf00e984a01e0b2f049
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Pruebas en webforJ

Las pruebas en aplicaciones webforJ combinan pruebas unitarias, de frontend y de extremo a extremo (E2E), cada una sirviendo a un propósito distinto para mantener la aplicación estable y confiable.

## Pruebas unitarias {#unit-testing}

Las pruebas unitarias se centran en verificar componentes individuales o la lógica del backend de forma aislada. Al seguir prácticas estándar de pruebas en Java, como el uso de [JUnit](https://junit.org/junit5/), los desarrolladores pueden validar de manera eficiente la lógica específica de la aplicación y asegurarse de que cada "unidad" funcione como se espera.

## Pruebas de frontend {#frontend-testing}

Las pruebas de frontend cubren las fuentes que un proyecto autoriza con el [paquete de frontend](/docs/managing-resources/bundler/overview). El ejecutor de pruebas [Bun](https://bun.sh/) las ejecuta como parte del mismo proceso de construcción que ejecuta las pruebas de Java, por lo que un componente TypeScript o una pieza de lógica del cliente se verifica de la misma manera que el backend. Consulta [Pruebas de frontend](./frontend-testing).

## Pruebas de extremo a extremo (E2E) {#end-to-end-e2e-testing}

Las pruebas de extremo a extremo son importantes para validar la experiencia del usuario en aplicaciones webforJ, que generan interfaces web dinámicas de una sola página. Estas pruebas simulan interacciones del usuario y verifican las características de toda la aplicación.

Usando herramientas como [**Selenium**](https://www.selenium.dev/) y [**Playwright**](https://playwright.dev/java/docs/intro), puedes:

- Automatizar interacciones en el navegador, como clics en botones y envíos de formularios.
- Verificar el renderizado consistente y la interactividad de los componentes de la UI dinámica.
- Asegurar la consistencia de comportamiento en diferentes navegadores y dispositivos.

## Combinando estrategias de pruebas {#combining-testing-strategies}

Al combinar pruebas unitarias y E2E:

1. **Aislar problemas**: Detectar y resolver errores a nivel de componente con pruebas unitarias.
2. **Asegurar confiabilidad**: Validar recorridos completos del usuario e integraciones del sistema con pruebas E2E.

## Temas {#topics}

<DocCardList className="topics-section" />
