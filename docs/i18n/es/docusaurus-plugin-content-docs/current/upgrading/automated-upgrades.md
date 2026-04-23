---
sidebar_position: 2
title: Automated Upgrades
sidebar_class_name: new-content
_i18n_hash: 1681300490f592540c6d96fbdbfd8ee4
---
# Usando OpenRewrite {#using-openrewrite}

OpenRewrite es un proyecto de código abierto diseñado para la refactorización automatizada de código fuente, permitiéndote migrar de manera eficiente y automática entre versiones de webforJ para proyectos que utilizan APIs obsoletas o eliminadas.

## ¿Cuándo usar OpenRewrite? {#when-to-use-openrewrite}

Puedes usar OpenRewrite al actualizar para alejarte de APIs obsoletas, no solo en lanzamientos mayores. Esto te ayuda a alejarte de métodos recién obsoletos, antes de que sean eliminados en una versión posterior.

## Configuración {#setup}

Para actualizar tu proyecto con OpenRewrite, añade el plugin de Maven OpenRewrite a tu `pom.xml`, con [`webforj-rewrite`](https://github.com/webforj/webforj/tree/main/webforj-rewrite) como dependencia:

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

Reemplaza `TARGET_VERSION` con la versión de webforJ a la que estás actualizando y `RECIPE_NAME` con uno de los recetas de la sección [Recetas disponibles](#available-recipes) de este artículo.

## Previsualizando cambios (opcional) {#previewing-changes}

Si prefieres ver qué cambios realizará OpenRewrite con una receta de webforJ, ejecutar el siguiente comando genera un diff en `target/rewrite/rewrite.patch` sin modificar ningún archivo. Revisa el patch para ver exactamente qué cambiará la receta.

```bash
mvn rewrite:dryRun
```

## Ejecutando OpenRewrite {#running-openrewrite}

Cuando estés listo para aplicar cambios con OpenRewrite, ejecuta el siguiente comando:

```bash
mvn rewrite:run
```

La receta maneja la mayoría de la actualización automáticamente al actualizar dependencias, renombrar métodos, cambiar tipos y ajustar tipos de retorno. Para los pocos casos donde no existe un reemplazo 1:1, añade comentarios `TODO` en tu código con instrucciones específicas. Busca en tu proyecto estos `TODO`s para encontrar lo que queda por hacer.

## Limpieza {#clean-up}

Después de ejecutar OpenRewrite con una receta de webforJ y resolver cualquier comentario `TODO`, elimina el plugin de tu `pom.xml`.

## Recetas disponibles {#available-recipes}

| Versión | Proyectos estándar de webforJ | Proyectos de Spring Boot |
| ------- | ------- | ------- |
| v26 | `com.webforj.rewrite.v26.UpgradeWebforj` | `com.webforj.rewrite.v26.UpgradeWebforjSpring` |
