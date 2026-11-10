---
title: Integraciones
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
description: >-
  Combine webforJ with Spring Boot, Spring Data, Kotlin DSL, Webswing, JBang,
  and AI tooling while keeping your backend stack independent.
_i18n_hash: 2272fe4265260266e667c1b22703a3b6
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ es el framework Java full-stack para aplicaciones web empresariales y es independiente del backend. Desarrollas toda la aplicación en Java, desde las pantallas y el enrutamiento hasta los formularios, el enlace de datos y la seguridad de las rutas, mientras que la capa de persistencia, la inyección de dependencias, los servicios y la arquitectura del backend quedan a tu elección. Por eso, webforJ funciona con cualquier stack tecnológico de Java, desde los servlets tradicionales hasta Spring Boot y los microservicios.

## Filosofía de arquitectura {#architecture-philosophy}

webforJ proporciona todo lo que tu aplicación necesita para mostrar datos a los usuarios y permitirles interactuar con ellos: componentes, diseños, enrutamiento, formularios con enlace y validación, tablas respaldadas por repositorios, seguridad de rutas y traducción. No dicta cómo funciona el resto del sistema. La capa de persistencia, el framework de inyección de dependencias, el proveedor de identidad y la arquitectura de servicios siguen siendo independientes de webforJ.

Este enfoque reconoce que la mayoría de las organizaciones ya tienen patrones de backend establecidos, capas de servicios existentes y stacks tecnológicos preferidos. webforJ lleva estas aplicaciones a la web sin exigir cambios de arquitectura ni migraciones tecnológicas. La lógica de dominio, los patrones de acceso a datos y las implementaciones de seguridad siguen funcionando exactamente como antes.

## Compatibilidad con frameworks de backend {#backend-framework-compatibility}

webforJ funciona con cualquier framework de backend de Java o patrón de arquitectura que ya utilices. Tanto si desarrollas con Jakarta EE, usas una arquitectura de microservicios o trabajas con un framework personalizado, webforJ se conecta sin interferir con el diseño de tu backend.

Para algunos frameworks populares, webforJ ofrece integraciones específicas que reducen el código repetitivo y agilizan el desarrollo. Estas integraciones ofrecen ventajas como la inyección automática de dependencias en los componentes de interfaz de usuario, una configuración simplificada y compatibilidad con herramientas específicas del framework. Si no ves tu framework en la lista de abajo, eso no significa que webforJ no sea compatible con él. Significa que configurarás la conexión siguiendo los patrones estándar de tu framework, en lugar de usar una integración preconfigurada.

Las integraciones que se indican a continuación son totalmente opcionales. Su propósito es mejorar la experiencia de desarrollo al usar frameworks específicos, pero las funciones principales de webforJ funcionan igual tanto si usas una integración como si no. Tu framework de backend sigue gestionando los servicios, el acceso a los datos y la lógica de negocio, mientras que webforJ se encarga de las pantallas, la navegación y la interacción con el usuario.

## Temas {#topics}

<DocCardList className="topics-section" />
