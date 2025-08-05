---
title: Integrations
hide_table_of_contents: true
sidebar_class_name: new-content
hide_giscus_comments: true
_i18n_hash: 366829e324b872af8247a509f9c55783
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ está diseñado como una capa de interfaz de usuario independiente de frameworks para aplicaciones Java. Se enfoca exclusivamente en construir interfaces de usuario ricas y basadas en componentes, dejando las decisiones de arquitectura del backend completamente a tu cargo. Esta clara separación de preocupaciones permite que webforJ funcione con cualquier pila tecnológica de Java, desde servlets tradicionales hasta microservicios modernos.

## Filosofía de arquitectura {#architecture-philosophy}

webforJ separa deliberadamente las preocupaciones de la interfaz de usuario y del backend. A diferencia de los frameworks full-stack, que dictan toda la estructura de tu aplicación, webforJ proporciona solo lo que necesitas para construir interfaces de usuario sofisticadas. Tu elección de capa de persistencia, marco de inyección de dependencias, implementación de seguridad y arquitectura de servicios sigue siendo completamente independiente de tu tecnología de interfaz de usuario.

Este enfoque reconoce que la mayoría de las organizaciones ya tienen patrones de backend establecidos, capas de servicio existentes y pilas tecnológicas preferidas. webforJ mejora estas aplicaciones con un framework de interfaz de usuario moderno sin requerir cambios arquitectónicos o migraciones de tecnología. Tu lógica de dominio, patrones de acceso a datos e implementaciones de seguridad seguirán funcionando exactamente como antes.

## Compatibilidad con frameworks de backend {#backend-framework-compatibility}

webforJ funciona con cualquier framework de backend de Java o patrón de arquitectura que ya estés utilizando. Ya sea que estés construyendo sobre Jakarta EE, utilizando una arquitectura de microservicios o trabajando con un framework personalizado, webforJ proporciona la capa de interfaz de usuario sin interferir con tu diseño de backend.

Para ciertos frameworks populares, webforJ ofrece integraciones específicas que reducen el código de boilerplate y agilizan el desarrollo. Estas integraciones proporcionan comodidades como inyección de dependencias automática en componentes de interfaz de usuario, configuración simplificada y soporte de herramientas específicas del framework. Si no ves tu framework listado a continuación, no significa que webforJ no funcionará con él; simplemente significa que configurarás la conexión utilizando los patrones estándar de tu framework en lugar de utilizar una integración preconstruida.

Las integraciones a continuación son completamente opcionales. Existen para mejorar la experiencia del desarrollador al usar frameworks específicos, pero las características centrales de webforJ funcionan de manera idéntica ya sea que uses una integración o no. Tu framework de backend sigue gestionando servicios, acceso a datos y lógica empresarial mientras que webforJ se encarga de la capa de presentación.

## Temas {#topics}

<DocCardList className="topics-section" />
