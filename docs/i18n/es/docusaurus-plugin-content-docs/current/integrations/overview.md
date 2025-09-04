---
title: Integrations
hide_table_of_contents: true
sidebar_class_name: new-content
hide_giscus_comments: true
_i18n_hash: 987f1fb9ef8aa9e50ff4ec00320d2dd7
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ está diseñado como una capa de interfaz de usuario independiente del framework para aplicaciones Java. Se centra exclusivamente en construir interfaces de usuario ricas, basadas en componentes, dejando completamente en tus manos las decisiones de arquitectura del backend. Esta clara separación de preocupaciones permite que webforJ funcione con cualquier pila de tecnología Java, desde servlets tradicionales hasta microservicios modernos.

## Filosofía de arquitectura {#architecture-philosophy}

webforJ separa deliberadamente las preocupaciones de UI y backend. A diferencia de los frameworks de pila completa, que dictan toda la estructura de tu aplicación, webforJ proporciona solo lo que necesitas para construir interfaces de usuario sofisticadas. Tu elección de capa de persistencia, marco de inyección de dependencias, implementación de seguridad y arquitectura de servicios permanece completamente independiente de tu tecnología de UI.

Este enfoque reconoce que la mayoría de las organizaciones tienen patrones de backend establecidos, capas de servicio existentes y pilas de tecnología preferidas. webforJ mejora estas aplicaciones con un marco de UI moderno sin requerir cambios arquitectónicos o migraciones de tecnología. Tu lógica de dominio, patrones de acceso a datos e implementaciones de seguridad continúan funcionando exactamente como antes.

## Compatibilidad con frameworks de backend {#backend-framework-compatibility}

webforJ funciona con cualquier framework de backend Java o patrón de arquitectura que ya estés utilizando. Ya sea que estés construyendo sobre Jakarta EE, utilizando una arquitectura de microservicios o trabajando con un framework personalizado, webforJ proporciona la capa de UI sin interferir con el diseño de tu backend.

Para ciertos frameworks populares, webforJ ofrece integraciones específicas que reducen el código boilerplate y optimizan el desarrollo. Estas integraciones proporcionan comodidades como inyección de dependencia automática en componentes de UI, configuración simplificada y soporte de herramientas específicas del framework. Si no ves tu framework listado a continuación, no significa que webforJ no funcione con él; simplemente significa que configurarás la conexión utilizando los patrones estándar de tu framework en lugar de usar una integración preconstruida.

Las integraciones a continuación son totalmente opcionales. Existen para mejorar la experiencia del desarrollador al utilizar frameworks específicos, pero las características básicas de webforJ funcionan de manera idéntica, ya sea que uses una integración o no. Tu framework de backend continúa gestionando servicios, acceso a datos y lógica de negocio mientras que webforJ se encarga de la capa de presentación.

## Temas {#topics}

<DocCardList className="topics-section" />
