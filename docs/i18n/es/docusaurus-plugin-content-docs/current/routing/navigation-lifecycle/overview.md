---
sidebar_position: 1
title: Navigation Lifecycle
hide_giscus_comments: true
_i18n_hash: 14d81d1a9ff86af17370e0a7eb50608b
---
import DocCardList from '@theme/DocCardList';

Navegar a través de diferentes vistas en una aplicación web implica varias etapas, que ofrecen oportunidades para realizar acciones antes, durante o después de una transición. El ciclo de vida de la navegación proporciona un sistema basado en eventos que permite a los desarrolladores gestionar aspectos clave de la navegación, como la validación de datos, el control de acceso, la actualización de la interfaz de usuario y la gestión de la limpieza.

Este sistema flexible permite a los desarrolladores gestionar transiciones de manera explícita al engancharse en puntos críticos del proceso de navegación. Ya sea que necesites bloquear la navegación, recuperar datos cuando un componente se muestra, o gestionar cambios no guardados, tienes control total sobre el flujo de navegación a través de sus eventos de ciclo de vida y observadores.

## Vista general de los eventos del ciclo de vida {#lifecycle-events-overview}

El proceso de navegación está gobernado por una serie de eventos que se activan durante las transiciones de ruta. Estos eventos te permiten reaccionar en puntos específicos del ciclo de vida:

1. **WillEnter**: Se activa antes de navegar a una ruta y antes de que su componente esté adjunto al DOM. Esto es ideal para tareas como verificaciones de autenticación o para bloquear la navegación si es necesario.
2. **DidEnter**: Se activa después de que la navegación se complete y el componente esté adjunto al DOM. Este evento es adecuado para acciones como recuperar datos, ejecutar animaciones o establecer el enfoque en elementos de la interfaz de usuario.
3. **WillLeave**: Se activa antes de navegar fuera de la ruta actual y antes de que su componente se elimine del DOM. Es útil para gestionar datos no guardados, solicitar confirmación al usuario o manejar tareas de limpieza.
4. **DidLeave**: Se activa después de cambiar a una ruta diferente y después de que el componente se haya eliminado del DOM. Este evento es ideal para liberar recursos o restablecer la interfaz de usuario para su uso futuro.
5. **Activate** (desde `25.03`): Se activa cuando los componentes en caché son reactivados en lugar de ser recreados. Esto ocurre al navegar a la misma ruta con diferentes parámetros o al regresar a una ruta visitada anteriormente. El evento se activa para todos los componentes en caché en la jerarquía de rutas que permanecen en la ruta actual, permitiendo que tanto los diseños padre como los componentes hijos actualicen sus datos o la interfaz de usuario según nuevos parámetros mientras mantienen el estado del componente.

Estos eventos proporcionan un control granular sobre el ciclo de vida de la navegación, permitiendo a los desarrolladores coordinar la validación de datos, las actualizaciones de la interfaz de usuario y la gestión de recursos durante las transiciones de ruta.

## Temas {#topics}

<DocCardList className="topics-section" />
