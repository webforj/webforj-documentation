---
title: Configuración de craftforJ
sidebar_label: Configuración
sidebar_position: 8
description: >-
  Todas las propiedades de craftforJ para webforj.conf o application.properties,
  con su tipo y valor predeterminado, y qué cambia al desactivar cada función.
_i18n_title_hash: 3eb5573b52d619504004137849515e97
_i18n_sidebar_label_hash: 254f642527b45bc260048e30704edb39
_i18n_description_hash: d219fb062890ca792008bd020a275a94
_i18n_hash: f7e73354e4d9548e79122405c21ae275
---
craftforJ está configurado en `webforj.conf`. Los nombres de las propiedades son los mismos en [Spring](/docs/integrations/spring/overview), así que configúralos en `application.properties` si es ahí donde resides tu configuración.

## Propiedades requeridas {#required-properties}

| Propiedad | Tipo | Predeterminado | Descripción |
|-----------|------|----------------|-------------|
| **`webforj.debug`** | Booleano | `false` | Habilita el modo de depuración. craftforJ lo requiere |
| **`webforj.devtools.craftforj.enabled`** | Booleano | `false` | Habilita craftforJ |

Ambas propiedades deben estar habilitadas. Consulta [Seguridad](/docs/craftforj/security#two-required-settings) para entender por qué craftforJ requiere dos configuraciones en lugar de una.

## Acceso {#access}

| Propiedad | Tipo | Predeterminado | Descripción |
|-----------|------|----------------|-------------|
| **`webforj.devtools.craftforj.hosts-allowed`** | Lista o Cadena | solo loopback | Direcciones de cliente permitidas más allá de la máquina que ejecuta la aplicación |

Por defecto, solo un navegador en la misma máquina que la aplicación puede acceder a craftforJ. Para permitir que otras máquinas accedan, enumera sus direcciones. Una entrada que termina en `*` coincide con un prefijo, y un único `*` elimina la restricción por completo.

```ini title="webforj.conf"
webforj.devtools.craftforj.hosts-allowed = ["192.168.1.42", "10.0.0.*"]
```

:::warning Un comodín permite que cualquiera que pueda acceder a tu aplicación
craftforJ lee y escribe los fuentes de tu proyecto. Solo utiliza `*` en una red donde estés seguro de quién puede acceder al puerto, como un contenedor que solo usas tú. Nunca lo utilices en una red compartida.
:::

## Raíz del proyecto {#project-root}

| Propiedad | Tipo | Predeterminado | Descripción |
|-----------|------|----------------|-------------|
| **`webforj.devtools.project-root`** | Cadena | detectado | El directorio donde residen tus fuentes |

craftforJ determina dónde está tu proyecto a partir de cómo se inició la aplicación. Dispositivos de proyecto poco comunes y algunas configuraciones de contenedor pueden obstaculizar esa detección. Si [Información de la aplicación](/docs/craftforj/app-info) informa la raíz del proyecto incorrecta, configúralo aquí.

```ini title="webforj.conf"
webforj.devtools.project-root = "/home/me/projects/my-app"
```

En Spring, establece la misma clave en `application.properties`.

:::info Renombrado en 26.03
Esta configuración fue previamente `webforj.devtools.craftforj.project-root`. La clave antigua aún funciona como un respaldo, pero está en desuso y genera una advertencia cuando se utiliza. Si ambas claves están configuradas, `webforj.devtools.project-root` tiene prioridad.
:::

## Banderas de características {#feature-flags}

Cada una de estas está habilitada por defecto. Desactivar una restringe lo que craftforJ puede hacer.

| Propiedad | Desactivarlo elimina |
|-----------|----------------------|
| **`webforj.devtools.craftforj.source-changes`** | Escribir cambios de propiedad de vuelta en Java y cambiar el acceso a rutas |
| **`webforj.devtools.craftforj.stylesheet-changes`** | Guardar temas y estilos en tu hoja de estilos |
| **`webforj.devtools.craftforj.ai.enabled`** | El asistente de IA |
| **`webforj.devtools.craftforj.ai.freeform-changes`** | El asistente escribiendo Java por su cuenta |

Desactivar una bandera desactiva la característica para todos los que utilizan esa aplicación. Las configuraciones de craftforJ son por desarrollador y solo pueden restringirse aún más, por lo que un desarrollador no puede volver a activar una capacidad que la aplicación desactivó.

:::info Las características que desactivas siguen siendo visibles
Cuando una bandera está desactivada, el control permanece en craftforJ y se marca como no soportado por la aplicación conectada.
:::

:::warning En producción
Deja `webforj.devtools.craftforj.enabled` sin establecer. Consulta [Seguridad](/docs/craftforj/security#in-production) para la lista de verificación completa.
:::
