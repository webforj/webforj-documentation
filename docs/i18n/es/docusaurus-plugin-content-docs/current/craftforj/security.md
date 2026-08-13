---
title: Security
sidebar_position: 9
description: >-
  What craftforJ can reach in your project, how it restricts access, and how to
  confirm it's disabled in production.
_i18n_hash: 5ffbc5b5c6e6cfcf64143712a21944d5
---
craftforJ lee y escribe el origen del proyecto al que está adjunto. Esta página describe los límites alrededor de eso y cómo confirmar que craftforJ está desactivado en las compilaciones que despliegas.

## Dos configuraciones requeridas {#two-required-settings}

craftforJ requiere que ambas de las siguientes estén habilitadas:

- `webforj.debug`
- `webforj.devtools.craftforj.enabled`

Ninguna de las dos hace algo por sí sola. Una aplicación que llega a producción con el modo de depuración activado no expone craftforJ, y una aplicación que lleva la propiedad craftforJ en un archivo de configuración compartido no la expone fuera del modo de depuración.

Los proyectos creados con [startforJ](https://docs.webforj.com/startforj) o desde un [arquetipo](/docs/building-ui/archetypes/overview) de webforJ tienen ambas habilitadas, por lo que craftforJ funciona desde la primera ejecución. Antes de desplegar, revisa la [lista de verificación de producción](#in-production) a continuación.

## Acceso local por defecto {#local-access-by-default}

Solo un navegador en la máquina que ejecuta la aplicación puede acceder a craftforJ. Todo lo demás es rechazado, y esto se aplica sin ninguna configuración de tu parte. Para acceder a craftforJ desde otra máquina, nombra esa máquina en [`hosts-allowed`](/docs/craftforj/configuration#access). Las direcciones se comparan literalmente, por lo que un cliente no puede hacerse pasar por otra cosa.

:::warning El comodín elimina la restricción por completo
Configurar `hosts-allowed = "*"` significa que cualquier persona que pueda acceder al puerto de tu aplicación puede leer y escribir las fuentes de tu proyecto. Existe para entornos sellados, como un contenedor que solo tú puedes alcanzar. No lo uses en ningún otro lugar.
:::

## No se agrega ningún endpoint HTTP {#no-added-http-surface}

craftforJ no agrega ningún endpoint HTTP, servlet o filtro a tu aplicación. Funciona a través de la conexión que tu aplicación ya tiene, por lo que tu aplicación responde exactamente al mismo conjunto de solicitudes con craftforJ habilitado que lo haría sin él.

## Las solicitudes provienen de tu página {#requests-come-from-your-page}

craftforJ actúa solo sobre las solicitudes que provienen de la página que tu servidor realmente sirvió. Un script que se cuela en la página desde otro lugar, como una dependencia comprometida o algo pegado en una consola, no puede activar craftforJ.

## Claves API {#api-keys}

Tu clave se almacena en la máquina que ejecuta tu aplicación. El [asistente AI](/docs/craftforj/ai) se ejecuta en el navegador, por lo que craftforJ debe proporcionarle la clave para trabajar, y la mantiene en memoria mientras la página esté abierta. Nada se escribe en el almacenamiento del navegador, y cerrar la página no deja nada atrás.

El asistente luego se comunica con tu proveedor desde el navegador en lugar de a través de tu servidor. No hay ningún relay, ningún proxy, ninguna telemetría y ninguna tercera parte entre medio.

Lo que llega a tu proveedor es la conversación en sí, que incluye las partes de tu aplicación que el asistente examinó y cualquier captura de pantalla que tomó. Considera eso antes de apuntar un modelo alojado a una aplicación que funciona con datos reales. Un modelo que se ejecuta localmente mantiene todo en tu máquina.

## Lo que craftforJ puede cambiar {#what-craftforj-can-change}

Con cada función habilitada, craftforJ puede:

- Leer cualquier archivo fuente bajo la raíz de tu proyecto
- Escribir archivos fuente de Java, incluyendo anotaciones de acceso a rutas
- Escribir la hoja de estilo de tu aplicación
- Cambiar y eliminar componentes en la aplicación en ejecución
- Navegar por la aplicación en ejecución

Cada una de estas puede ser [desactivada](/docs/craftforj/configuration#feature-flags) independientemente, y cada escritura en el disco pasa por un diff que tú apruebas.

## En producción {#in-production}

Deja craftforJ desactivado. Está apagado a menos que lo hayas activado, así que en la mayoría de los casos no hay nada que hacer. Para confirmar:

1. `webforj.devtools.craftforj.enabled` está sin configurar o es `false` en la configuración que realmente despliegas.
2. `webforj.debug` está sin configurar o es `false` en esa misma configuración.
3. Ninguna de las propiedades está configurada por una variable de entorno o por un perfil que se aplica solo en producción.
4. Carga la aplicación desplegada y confirma que no hay ningún disparador de craftforJ en la página.

Para ver el panorama más amplio, consulta [Fortalecimiento de producción](/docs/security/application-security/production-hardening).

## Reportar un problema de seguridad {#reporting-a-security-issue}

Si encuentras un problema de seguridad en craftforJ, repórtalo a través de la [política de seguridad de webforJ](https://github.com/webforj/webforj/security) en lugar de en un problema público.
