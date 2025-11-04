---
sidebar_position: 1
title: Security Architecture
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: df2f795c6b65edc60adb39b549cb780b
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Arquitectura de Seguridad <DocChip chip='since' label='25.10' />

El sistema de seguridad webforJ se basa en una fundación de interfaces y patrones de diseño que permiten una protección de rutas flexible y extensible. Esta sección explica cómo funciona el marco de seguridad fundamental y cómo construir soluciones de seguridad personalizadas implementando estas interfaces.

:::tip[Integración con Spring]
La mayoría de las aplicaciones deberían usar la [integración de Spring Security](/docs/security/getting-started), ya que configura automáticamente todo esto por ti. Solo implementa seguridad personalizada si tienes requisitos específicos o si no estás utilizando Spring Boot. La integración de Spring se basa en esta misma arquitectura de fundación.
:::

Aprenderás sobre las interfaces principales, el patrón de cadena de evaluadores, cómo se intercepta y evalúa la navegación, y diferentes enfoques para almacenar el estado de autenticación.

:::info[Enfoque en arquitectura y puntos de extensión]
Estas guías explican la arquitectura fundamental y los puntos de extensión, las interfaces que implementas y cómo trabajan juntas. Los ejemplos de código muestran **un posible enfoque**, no son requisitos prescriptivos. Tu implementación puede utilizar diferentes mecanismos de almacenamiento (JWT, base de datos, LDAP), diferentes patrones de conexión o diferentes flujos de autenticación según tus necesidades.
:::

## Lo que aprenderás {#what-youll-learn}

- **Arquitectura fundamental**: Las interfaces centrales que definen el comportamiento de seguridad y cómo trabajan juntas
- **Intercepción de navegación**: Cómo el sistema de seguridad intercepta solicitudes de navegación y evalúa las reglas de acceso
- **Patrón de cadena de evaluadores**: Cómo se evalúan las reglas de seguridad en orden de prioridad utilizando el patrón de cadena de responsabilidad
- **Almacenamiento de autenticación**: Diferentes enfoques para almacenar el estado de autenticación de usuarios (sesiones, JWT, base de datos, etc.)
- **Implementación completa**: Un ejemplo funcional que muestra todos los componentes conectados

## A quién va dirigido {#who-this-is-for}

Estas guías son para desarrolladores que desean:

- Construir implementaciones de seguridad personalizadas para aplicaciones que no son de Spring
- Comprender la arquitectura fundamental para solucionar problemas
- Implementar flujos de autenticación personalizados o lógica de autorización
- Crear evaluadores de seguridad con lógica específica de dominio
- Integrarse con sistemas de autenticación existentes (LDAP, OAuth, backend personalizados)

## Requisitos previos {#prerequisites}

Antes de sumergirte en estas guías, deberías:

- Completar la [guía de Introducción](/docs/security/getting-started) para entender los conceptos de seguridad
- Comprender las anotaciones de seguridad de la [guía de Anotaciones](/docs/security/annotations)
- Estar familiarizado con el patrón de diseño de cadena de responsabilidad
- Tener experiencia con interfaces Java y herencia

## Temas {#topics}

<DocCardList className="topics-section" />
