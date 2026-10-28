---
sidebar_position: 1
title: Actualizaciones Automatizadas
description: >-
  Migrate webforJ projects between versions automatically with OpenRewrite
  recipes that rename APIs, update dependencies, and flag manual fixes.
_i18n_hash: 8c889d8dea5c90aad2ef224d152e611e
---
# Usando OpenRewrite {#using-openrewrite}

OpenRewrite es un proyecto de código abierto diseñado para la refactorización automática del código fuente, lo que te permite migrar de manera eficiente y automática entre versiones de webforJ para proyectos que utilizan API obsoletas o eliminadas.

## ¿Cuándo usar OpenRewrite? {#when-to-use-openrewrite}

Puedes usar OpenRewrite al actualizar para alejarte de las API obsoletas, no solo en las versiones principales. Esto te ayuda a evitar métodos que se vuelven obsoletos recientemente, antes de que se eliminen en una versión posterior.

## Configuración {#setup}

Para actualizar tu proyecto con OpenRewrite, añade el plugin de Maven OpenRewrite a tu `pom.xml`, con [`webforj-rewrite`](https://github.com/webforj/webforj/tree/main/webforj-rewrite) como una dependencia:

```xml
<plugin>
  <groupId>org.openrewrite.maven</groupId>
  <artifactId>rewrite-maven-plugin</artifactId>
  <version>6.36.0</version>
  <configuration>
    <activeRecipes>
      <recipe>RECIPE_NAME</recipe>
    </activeRecipes>
  </configuration>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-rewrite</artifactId>
      <version>TARGET_VERSION</version>
    </dependency>
  </dependencies>
</plugin>
```

Reemplaza `TARGET_VERSION` con la versión de webforJ a la que estás actualizando y `RECIPE_NAME` con una de las recetas de la sección [Recetas disponibles](#available-recipes) de este artículo.

## Previsualizando cambios (opcional) {#previewing-changes}

Si prefieres ver qué cambios realizará OpenRewrite con una receta de webforJ, ejecutar el siguiente comando generará un diff en `target/rewrite/rewrite.patch` sin modificar archivos. Revisa el patch para ver exactamente qué cambiará la receta.

```bash
mvn rewrite:dryRun
```

## Ejecutando OpenRewrite {#running-openrewrite}

Cuando estés listo para aplicar los cambios con OpenRewrite, ejecuta el siguiente comando:

```bash
mvn rewrite:run
```

La receta maneja la mayoría de la actualización automáticamente actualizando dependencias, renombrando métodos, cambiando tipos y ajustando tipos de retorno. En los pocos casos donde no existe un reemplazo 1:1, añade comentarios `TODO` en tu código con instrucciones específicas. Busca en tu proyecto estos `TODO` para encontrar lo que queda por hacer.

## Limpieza {#clean-up}

Después de ejecutar OpenRewrite con una receta de webforJ y resolver cualquier comentario `TODO`, elimina el plugin de tu `pom.xml`.

## Recetas disponibles {#available-recipes}

| Versión | Proyectos estándar de webforJ | Proyectos de Spring Boot |
| ------- | ------- | ------- |
| v26.03 | `com.webforj.rewrite.v26.UpgradeWebforj_26_03` | `com.webforj.rewrite.v26.UpgradeWebforjSpring_26_01` |
| v26.01 | `com.webforj.rewrite.v26.UpgradeWebforj_26_01` | `com.webforj.rewrite.v26.UpgradeWebforjSpring_26_01` |
| v26 | `com.webforj.rewrite.v26.UpgradeWebforj` | `com.webforj.rewrite.v26.UpgradeWebforjSpring` |
