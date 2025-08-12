---
sidebar_position: 1
title: Testing
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 34ef676ce8690df9732e6bee7af56206
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Pruebas de webforJ

Las pruebas en las aplicaciones webforJ implican una combinación de pruebas unitarias y pruebas de extremo a extremo (E2E) para garantizar una aplicación estable y confiable. Cada tipo de prueba tiene un propósito distinto en el mantenimiento de la calidad de la aplicación.

## Pruebas unitarias {#unit-testing}

Las pruebas unitarias se centran en verificar componentes individuales o lógica de backend de manera aislada. Siguiendo las prácticas estándar de pruebas en Java, como usar [JUnit](https://junit.org/junit5/), los desarrolladores pueden validar de manera eficiente la lógica específica de la aplicación y asegurarse de que cada "unidad" funcione como se espera.

## Pruebas de extremo a extremo (E2E) {#end-to-end-e2e-testing}

Las pruebas de extremo a extremo son importantes para validar la experiencia del usuario en las aplicaciones webforJ, que generan interfaces web dinámicas de una sola página. Estas pruebas simulan interacciones del usuario y verifican las características de toda la aplicación.

Usando herramientas como [**Selenium**](https://www.selenium.dev/) y [**Playwright**](https://playwright.dev/java/docs/intro), puedes:

- Automatizar interacciones del navegador, como clics en botones y envíos de formularios.
- Verificar el renderizado y la interactividad consistentes de los componentes de la interfaz de usuario dinámica.
- Garantizar la consistencia del comportamiento en diferentes navegadores y dispositivos.

## Combinando estrategias de pruebas {#combining-testing-strategies}

Al combinar pruebas unitarias y E2E:

1. **Aislar problemas**: Detectar y resolver errores a nivel de componente de manera temprana con pruebas unitarias.
2. **Garantizar confiabilidad**: Validar recorridos completos de usuarios e integraciones del sistema con pruebas E2E.

## Temas {#topics}

<DocCardList className="topics-section" />
