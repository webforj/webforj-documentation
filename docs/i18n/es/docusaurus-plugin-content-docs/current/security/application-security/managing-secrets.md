---
sidebar_position: 4
title: Gestión de secretos
description: >-
  Mantén las contraseñas de bases de datos, las claves de API y otros secretos
  fuera del árbol de código fuente y los archivos de configuración de webforJ;
  resuélvelos en tiempo de ejecución.
_i18n_title_hash: 5b750c6625464d4376d77d8495173d45
_i18n_description_hash: 8d59287c4a268f383d320a083ca67267
_i18n_hash: 3c20f1e66f7fb00f96c26f0b00d46f07
---
La regla detrás de cada secreto, una contraseña de base de datos, una clave API, una clave de firma, es que su verdadero valor nunca vive en tu código, tu `webforj.conf` o tu repositorio. Resuélvelo en tiempo de ejecución en su lugar, para que la misma construcción funcione en cada entorno y un repositorio filtrado no revele nada.

## Leer secretos desde el entorno {#read-secrets-from-the-environment}

El enfoque más portátil es almacenar cada secreto como una variable de entorno en la máquina o contenedor que ejecuta la aplicación, y leerlo al iniciar en lugar de comprometerlo en cualquier lugar.

```bash
# establecer dónde se ejecuta la aplicación, nunca en un archivo rastreado
export DB_PASSWORD=…
```

Mantén estos valores fuera de `webforj.conf` y de cualquier otro archivo que comprometas, y asegúrate de que tu despliegue los establezca antes de que la aplicación comience.

## En Spring Boot {#on-spring-boot}

Si tu aplicación se ejecuta en Spring Boot, aprovecha su configuración externalizada en lugar de inventar la tuya. Puedes referenciar una variable de entorno desde `application.properties` con un marcador de posición `${...}`, y cargar un archivo de secretos que esté fuera del proyecto (y fuera del control de versiones) con `spring.config.import`.

```properties title="application.properties"
spring.datasource.password=${DB_PASSWORD}
spring.config.import=optional:file:./secrets.properties
```

Estas son características de Spring Boot, no de webforJ; consulta la documentación de configuración externalizada de Spring Boot para ver toda la gama de opciones.

:::tip Un secreto filtrado es un secreto quemado
Agrega archivos como `secrets.properties` a `.gitignore`, audita tu historial en busca de valores que se hayan filtrado y rota cualquier cosa que haya estado expuesta. Mantener secretos fuera de nuevos commits no deshace aquellos que ya se han enviado.
:::
