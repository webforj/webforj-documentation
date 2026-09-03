---
title: Configuración
sidebar_position: 8
description: >-
  Every craftforJ configuration property, its default, and what turning each
  feature off changes.
_i18n_hash: 025fb1766af8cbcb741f6f353bdf6523
---
craftforJ está configurado en `webforj.conf`. Los nombres de las propiedades son los mismos en [Spring](/docs/integrations/spring/overview), así que configúralos en `application.properties` si es ahí donde vive tu configuración.

## Propiedades requeridas {#required-properties}

| Propiedad | Tipo | Predeterminado | Descripción |
|-----------|------|----------------|-------------|
| **`webforj.debug`** | Booleano | `false` | Habilita el modo de depuración. craftforJ lo requiere |
| **`webforj.devtools.craftforj.enabled`** | Booleano | `false` | Habilita craftforJ |

Ambas propiedades deben estar habilitadas. Consulta [Seguridad](/docs/craftforj/security#two-required-settings) para saber por qué craftforJ requiere dos configuraciones en lugar de una.

## Acceso {#access}

| Propiedad | Tipo | Predeterminado | Descripción |
|-----------|------|----------------|-------------|
| **`webforj.devtools.craftforj.hosts-allowed`** | Lista o Cadena | solo loopback | Direcciones de cliente permitidas más allá de la máquina que ejecuta la aplicación |

Por defecto, solo un navegador en la misma máquina que la aplicación puede acceder a craftforJ. Para permitir otras máquinas, enumera sus direcciones. Una entrada que termine en `*` coincide con un prefijo, y un único `*` elimina completamente la restricción.

```ini title="webforj.conf"
webforj.devtools.craftforj.hosts-allowed = ["192.168.1.42", "10.0.0.*"]
```

:::warning Un carácter comodín permite a cualquiera que pueda acceder a tu aplicación
craftforJ lee y escribe tus fuentes de proyecto. Solo usa `*` en una red donde estés seguro de quién puede acceder al puerto, como en un contenedor que solo usas tú. Nunca lo uses en una red compartida.
:::

## Raíz del proyecto {#project-root}

| Propiedad | Tipo | Predeterminado | Descripción |
|-----------|------|----------------|-------------|
| **`webforj.devtools.craftforj.project-root`** | Cadena | detectado | El directorio donde viven tus fuentes |

craftforJ determina dónde está tu proyecto a partir de cómo se inició la aplicación. Diseños de proyecto inusuales y algunas configuraciones de contenedores desactivan esa detección. Si [Información de la aplicación](/docs/craftforj/app-info) informa la raíz del proyecto incorrecta, configúralo aquí.

## Flags de características {#feature-flags}

Cada uno de estos está habilitado por defecto. Desactivarlo restringe lo que craftforJ puede hacer.

| Propiedad | Desactivándolo se elimina |
|-----------|--------------------------|
| **`webforj.devtools.craftforj.source-changes`** | Escribir cambios de propiedades de vuelta a Java, y cambiar el acceso a rutas |
| **`webforj.devtools.craftforj.stylesheet-changes`** | Guardar temas y estilos en tu hoja de estilos |
| **`webforj.devtools.craftforj.ai.enabled`** | El asistente de IA |
| **`webforj.devtools.craftforj.ai.freeform-changes`** | El asistente escribiendo Java por su cuenta |

Desactivar una bandera desactiva la característica para todos los que usan esa aplicación. La configuración de craftforJ es por desarrollador y solo puede restringir más, por lo que un desarrollador no puede reactivar una capacidad que la aplicación desactivó.

:::info Las características que desactivas permanecen visibles
Cuando una bandera está desactivada, el control permanece en craftforJ y se marca como no soportado por la aplicación conectada.
:::

:::warning En producción
Deja `webforj.devtools.craftforj.enabled` sin configurar. Consulta [Seguridad](/docs/craftforj/security#in-production) para la lista completa de verificación.
:::
