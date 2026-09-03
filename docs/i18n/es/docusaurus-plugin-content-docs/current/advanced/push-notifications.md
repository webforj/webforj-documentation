---
sidebar_position: 39
sidebar_class_name: new-content
title: Push Notifications
description: >-
  Use the Push class, PushSender, and PushMessage to subscribe browsers and send
  notifications from the server, even when the app isn't open.
_i18n_hash: 47adf06762f8af67111f20937368723c
---
<DocChip chip='since' label='26.02' />
<JavadocLink type="push" location="com/webforj/push/Push" top='true'/>

Las notificaciones push pueden llegar a los usuarios incluso cuando una aplicación no está abierta. El navegador se suscribe una vez, la aplicación almacena la suscripción y el servidor la utiliza para enviar notificaciones cuando ocurre un evento. <JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink> gestiona la suscripción y la cancelación en el navegador. En el servidor, <JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> envía un <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> a una suscripción almacenada.

<!-- INTRO_END -->

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/push-notifications/push.mp4" type="video/mp4"/>
  </video>
</div>

## Configuración y requisitos previos {#setup-and-prerequisites}

Las notificaciones push son proporcionadas por un módulo separado. Añádelo a tu aplicación:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-push</artifactId>
</dependency>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
dependencies {
  implementation 'com.webforj:webforj-push'
}
```

</TabItem>
</Tabs>

Las notificaciones push requieren:

- Un despliegue servlet, como Jetty, Spring Boot, o un archivo WAR.
- Un par de claves, que se genera a continuación, que el despliegue utiliza para firmar las notificaciones.
- Un origen seguro. Los navegadores rechazan suscripciones servidas sobre cualquier cosa que no sea `https`, excepto desde `localhost` durante el desarrollo.

:::info Orígenes seguros
<!-- vale off -->
Para más información sobre contextos seguros y por qué son importantes, consulta la [documentación de Contextos Seguros de MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

### Generando las claves {#generating-the-keys}

Los servicios push aceptan solo notificaciones firmadas por el despliegue al que se suscribió el navegador. Ejecuta el [plugin de construcción](/docs/configuration/build-plugin) una vez para cada despliegue para generar su par de claves:

<Tabs>
<TabItem value="maven" label="Maven">

```bash
mvn webforj:push-keys
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```bash
./gradlew webforjPushKeys
```

</TabItem>
</Tabs>

El comando genera tres líneas de configuración. Pégalas en `application.properties` sin las comillas, o cópialas como se imprimieron en `webforj.conf`. Reemplaza el sujeto con la dirección de contacto del despliegue. Debe ser una dirección `mailto:` o `https://` que los servicios push puedan usar para contactar al operador.

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| Propiedad | Explicación |
|----------|-------------|
| `webforj.push.public-key` | La mitad pública del par de claves utilizado por el despliegue para firmar notificaciones |
| `webforj.push.private-key` | La mitad privada del par de claves. Al igual que cualquier otro secreto, mantenlo fuera del control de versiones |
| `webforj.push.subject` | La dirección de contacto del despliegue. Debe ser una dirección `mailto:` o `https://` a través de la cual los servicios push puedan alcanzar al operador |

La aplicación lee estas propiedades al inicio. Si la configuración incluye solo algunas de ellas, el inicio falla y se informa cuáles propiedades faltan.

:::warning Rotación de claves
Cada navegador se suscribe a un par de claves. Si las claves cambian, el servicio push rechaza las suscripciones existentes. La próxima llamada a `subscribe()` en cada navegador reemplaza su suscripción.
:::

## Cómo funciona {#how-it-works}

El proceso tiene tres pasos:

1. **Suscribirse.** Desde una vista, `Push.getCurrent().subscribe()` solicita el permiso del usuario y devuelve una `PushSubscription` que identifica la dirección del navegador.
2. **Almacenar.** La aplicación guarda la suscripción con sus datos y la asocia con el usuario correspondiente.
3. **Enviar.** Más tarde, desde cualquier hilo, `PushSender.send(subscription, message)` pasa el mensaje al servicio push del proveedor del navegador. El servicio muestra la notificación ya sea que la aplicación esté abierta o no.

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("Pedido enviado").setUrl("/orders/42").build());
```

Las siguientes secciones explican lo que muestra el navegador y cómo manejar los fallos en cada paso.

## Instancia {#instance}

Recupera la instancia push para el entorno actual:

```java
import com.webforj.push.Push;

Push push = Push.getCurrent();

if (Push.isPresent()) {
  // ...
}

Push.ifPresent(p -> {
  // ...
});
```

## Suscribiendo el navegador {#subscribing-the-browser}

Llama a `subscribe()` en respuesta a una acción del usuario, como hacer clic en un botón de "Habilitar notificaciones". El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> devuelto se completa con la <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink> del navegador. Si el navegador no puede suscribirse, se completa excepcionalmente con un <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink>.

```java
PendingResult<PushSubscription> request = Push.getCurrent().subscribe();
request.thenAccept(subscription -> {
  subscriptions.save(subscription);
});
request.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable.getCause();
  PushStatus status = error.getStatus();
  String message = error.getMessage();

  return null;
});
```

Si el navegador ya está suscrito, llamar a `subscribe()` nuevamente devuelve la suscripción existente. Por lo tanto, puedes llamarlo de manera segura en cada visita.

:::info Permiso del navegador
La primera llamada a `subscribe()` solicita permiso al usuario. El navegador muestra este aviso, no es parte de la UI de la aplicación. Debido a que los navegadores muestran el aviso solo en respuesta a una acción del usuario, llama a `subscribe()` desde un listener de clic en lugar de desde el constructor de la vista.

Si el usuario bloquea el aviso, la aplicación no puede volver a solicitarlo para ese origen.
:::

### Almacenando suscripciones {#storing-subscriptions}

Una suscripción representa la dirección de un navegador y pertenece al servidor. Almacénala con los datos de la aplicación, utilizando su endpoint como clave. Incluye cualquier información que la aplicación necesite para seleccionar los navegadores apropiados más tarde, como el usuario asociado. Cada suscripción contiene tres valores de texto:

| Valor | Significado |
|-------|---------|
| `getEndpoint()` | La URL de entrega asignada por el servicio push del proveedor del navegador |
| `getP256dh()` | La clave pública del navegador |
| `getAuth()` | El secreto de autenticación del navegador |

Un usuario que se suscriba desde dos navegadores tendrá dos suscripciones. Elimina una suscripción cuando su navegador se desuscriba o cuando un envío informe que ha expirado. Consulta [Estado de fallo](#failure-status).

### Restaurando una suscripción {#restoring-a-subscription}

`getSubscription()` devuelve la suscripción actual del navegador, o un resultado vacío si no existe. Úsalo para sincronizar la copia del servidor, por ejemplo, después de que el almacenamiento de la aplicación haya sido restablecido:

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

A través de <JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink>, `getPermission()` informa si el usuario concedió, denegó, o aún no ha respondido al aviso de notificación. Usa este resultado para ocultar el botón "Habilitar notificaciones" cuando hacer clic en él no tendría efecto.

### Desuscribiéndose {#unsubscribing}

`unsubscribe()` cancela la suscripción del navegador. Se completa con la suscripción eliminada para que la aplicación pueda eliminar su copia almacenada, o con un resultado vacío si el navegador no tenía suscripción.

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## Enviando notificaciones {#sending-notifications}

<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> envía un <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> a una suscripción almacenada. Firma el mensaje con las claves del despliegue y lo pasa al servicio push del proveedor del navegador. Ese servicio activa el navegador y muestra la notificación. Dado que la operación nunca bloquea el hilo que llama, puedes invocarla desde un listener de clic, un trabajo programado, o un manejador de solicitud.

Después de que las propiedades están configuradas, el sender está disponible como un bean que puedes inyectar en vistas, servicios, y trabajos programados. Para reemplazarlo, define tu propio bean `PushSender`.

```java
@Route("/orders")
public class OrdersView extends Composite<FlexLayout> {

  public OrdersView(PushSender sender, PushSubscriptions subscriptions) {
    // ...
  }
}
```

Sin Spring, `new PushSender()` lee las claves desde la configuración de la aplicación. Crea el sender en un hilo de aplicación, ya sea en una vista o en `App.run()`, y luego úsalo desde cualquier hilo. Todos los senders comparten un solo grupo de conexiones a los servicios push, por lo que no hay costo en crear uno donde sea necesario.

Para notificaciones que deben enviarse más tarde o después de que el usuario se vaya, utiliza un temporizador en el servidor como `TaskScheduler` de Spring. No utilices un temporizador de página como `Interval`, porque se detiene cuando se cierra la pestaña.

### Componiendo un mensaje {#composing-a-message}

Crea un mensaje con su título, luego configura cada otra opción en el constructor:

```java
PushMessage message = PushMessage.create("Pedido enviado")
    .setBody("El Pedido #42 está en camino")
    .setIcon("icons://icon-192x192.png")
    .setUrl("/orders/42")
    .setActions(List.of(new PushAction("seguir", "Seguir", "/orders/42/tracking")))
    .build();

PendingResult<Void> sent = sender.send(subscription, message);
sent.thenAccept(v -> status.setText("Enviado"));
sent.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  status.setText(error.getStatus() + ": " + error.getMessage());

  return null;
});
```

`send()` devuelve inmediatamente. El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> se completa cuando el servicio push acepta el mensaje, o se completa excepcionalmente si el servicio no lo acepta. Si `send()` se llama en un hilo de aplicación, como desde un listener, sus callbacks se ejecutan en ese hilo y pueden actualizar componentes. Si la sesión que llamó a `send()` finaliza antes de que llegue la respuesta, los callbacks no se ejecutan, pero la notificación aún se entrega.

Un envío espera hasta 30 segundos por el servicio push antes de fallar con `UNREACHABLE`. Usa `setTimeout(Duration)` para cambiar el tiempo de espera para cada sender.

| Opción | Efecto |
|--------|--------|
| `setBody` | Establece el texto mostrado debajo del título |
| `setIcon` | Establece la imagen mostrada con la notificación. Acepta URLs absolutas y los protocolos `icons://` y `ws://`. Consulta [Recursos](/docs/managing-resources/assets-protocols). No acepta el protocolo `context://` porque los servicios push limitan un mensaje a 4 KB |
| `setUrl` | Establece la página que se abre cuando el usuario hace clic en la notificación. Las URLs relativas se resuelven en contra de la raíz de la aplicación. Si no se establece ninguna URL, se abre la raíz de la aplicación |
| `setActions` | Establece los botones mostrados en la notificación, con una URL separada para cada botón. Consulta [Compatibilidad del navegador](#browser-support) |
| `setTag` | Establece una etiqueta identificativa. Si una notificación mostrada tiene la misma etiqueta, la nueva notificación la reemplaza |
| `setSilent` | Muestra la notificación sin sonido ni vibración |
| `setTimeToLive` | Establece cuánto tiempo retiene el servicio push el mensaje para un dispositivo sin conexión, hasta cuatro semanas |
| `setUrgency` | Usa <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink> para permitir que el dispositivo retrase mensajes de baja urgencia y ahorre batería |
| `setTopic` | Reemplaza un mensaje que aún está esperando en el servicio push cuando ambos mensajes tienen el mismo tema. Los temas pueden contener como máximo 32 caracteres que son seguros en una URL |

Cuando una pestaña ya muestra la página, hacer clic en la notificación enfoca la aplicación. De lo contrario, la página se abre en una nueva pestaña. Hacer clic en un botón de notificación abre su URL de la misma manera.

:::info Una notificación por mensaje
Cada mensaje muestra una notificación. Dado que los navegadores no activan una página para un mensaje que no muestra nada, push no puede usarse para actualizaciones de datos silenciosas.
:::

## Estado de fallo {#failure-status}

Cuando `subscribe()` o `send()` falla, su `PendingResult` informa un `WebforjPushException`. <JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink> identifica la razón:

| Estado | Cuándo | Qué hacer |
|--------|------|------------|
| `PERMISSION_DENIED` | El usuario ha bloqueado las notificaciones para la aplicación | Explica dónde puede el usuario permitir notificaciones en la configuración del navegador |
| `UNSUPPORTED` | Push no es compatible con el navegador, la página no está en un contexto seguro, o la aplicación no está desplegada como servlet | Oculta la función |
| `NOT_CONFIGURED` | Al menos una propiedad `webforj.push.*` falta o está incompleta | Genera las claves y configura todas las tres propiedades |
| `SUBSCRIPTION_EXPIRED` | El servicio push ya no reconoce la suscripción porque el usuario se desuscribió o reinstaló el navegador | Elimina la suscripción almacenada |
| `REJECTED` | El servicio push rechazó el mensaje; `getStatusCode()` contiene su respuesta | Verifica las claves y el tamaño del mensaje |
| `UNREACHABLE` | El servicio push no respondió antes del tiempo de espera | Intenta de nuevo más tarde |
| `UNKNOWN` | La URL almacenada no es válida, o la suscripción o el mensaje no pudieron ser codificados | Verifica la suscripción almacenada |

Elimina las suscripciones expiradas durante cada envío:

```java
sender.send(subscription, message).exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
    subscriptions.delete(subscription);
  }

  return null;
});
```

:::tip La expiración llega un mensaje tarde
Los servicios push desregistran las suscripciones de manera perezosa. Aún aceptan el primer mensaje después de que un usuario se desuscribe, pero no va a ninguna parte. El siguiente mensaje informa `SUBSCRIPTION_EXPIRED`. Un envío aceptado significa que el mensaje llegó al servicio push, no que el usuario lo vio.
:::

## Compatibilidad del navegador {#browser-support}

Todos los principales navegadores de escritorio y móviles muestran notificaciones push después de suscribirse. Ten en cuenta estas limitaciones:

- En iPhone y iPad, push funciona solo para aplicaciones web añadidas a la Pantalla de Inicio en iOS 16.4 o posterior. En una pestaña de Safari, `subscribe()` informa `UNSUPPORTED`. Consulta [Aplicaciones Instalables](/docs/configuration/installable-apps) para el manifiesto de aplicación requerido.
- Safari no muestra botones de notificación. Muestra mensajes con acciones sin sus botones, pero hacer clic en la notificación aún abre la URL del mensaje.
- Las WebViews de Android e iOS no muestran notificaciones.

Para más detalles por navegador, consulta la tabla de compatibilidad de MDN [showNotification](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility).

## Ejemplo completo {#complete-example}

La siguiente vista suscribe y desuscribe el navegador, almacena suscripciones en memoria, y envía un mensaje a cada suscripción almacenada. Puede enviar inmediatamente o esperar ocho segundos utilizando `TaskScheduler` de Spring, permitiendo que la pestaña se cierre antes de que llegue la notificación. La clase de la aplicación usa `@EnableScheduling` para hacer disponible el programador.

```java title="PushSubscriptions.java"
package com.example;

import com.webforj.push.PushSubscription;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class PushSubscriptions {

  private final Map<String, PushSubscription> byEndpoint = new ConcurrentHashMap<>();

  public void save(PushSubscription subscription) {
    byEndpoint.put(subscription.getEndpoint(), subscription);
  }

  public void delete(PushSubscription subscription) {
    byEndpoint.remove(subscription.getEndpoint());
  }

  public Collection<PushSubscription> findAll() {
    return byEndpoint.values();
  }
}
```

<!-- vale off -->

<ExpandableCode title="PushView.java" language="java" startLine={40} endLine={73}>

```java
package com.example;

import com.webforj.PendingResult;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.push.Push;
import com.webforj.push.PushAction;
import com.webforj.push.PushMessage;
import com.webforj.push.PushSender;
import com.webforj.push.PushStatus;
import com.webforj.push.PushSubscription;
import com.webforj.push.exception.WebforjPushException;
import com.webforj.router.annotation.Route;
import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.scheduling.TaskScheduler;

@Route("/push")
public class PushView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph status = new Paragraph("Comprobando suscripción…");
  private final TextField message = new TextField("Mensaje", "El Pedido #42 está en camino");
  private final Button subscribe =
      new Button("Habilitar notificaciones", ButtonTheme.PRIMARY);
  private final Button unsubscribe = new Button("Deshabilitar notificaciones");
  private final Button sendNow = new Button("Enviar ahora");
  private final Button sendLater = new Button("Enviar en 8 segundos");

  public PushView(PushSubscriptions subscriptions, PushSender sender, TaskScheduler scheduler) {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    subscribe.onClick(ev -> Push.getCurrent().subscribe()
        .thenAccept(subscription -> {
          subscriptions.save(subscription);
          status.setText("Suscrito");
        })
        .exceptionally(throwable -> {
          WebforjPushException error = (WebforjPushException) throwable.getCause();
          status.setText(error.getStatus() == PushStatus.PERMISSION_DENIED
              ? "Las notificaciones están bloqueadas en este navegador"
              : error.getMessage());

          return null;
        }));

    unsubscribe.onClick(ev -> Push.getCurrent().unsubscribe().thenAccept(removed -> {
      removed.ifPresent(subscriptions::delete);
      status.setText(removed.isPresent() ? "Desuscrito" : "No había ninguna suscripción");
    }));

    sendNow.onClick(ev -> sendToAll(subscriptions, sender, message.getValue(), status::setText));

    sendLater.onClick(ev -> {
      String text = message.getValue();
      status.setText("Enviando en 8 segundos, cierra la pestaña ahora");
      scheduler.schedule(() -> sendToAll(subscriptions, sender, text, outcome -> {
      }), Instant.now().plusSeconds(8));
    });

    Push.getCurrent().getSubscription().thenAccept(existing -> {
      existing.ifPresent(subscriptions::save);
      status.setText(existing.isPresent() ? "Suscrito" : "No suscrito");
    });

    self.add(status, message, subscribe, unsubscribe, sendNow, sendLater);
  }

  private static void sendToAll(PushSubscriptions subscriptions, PushSender sender, String text,
      Consumer<String> report) {
    report.accept("Enviando a " + subscriptions.findAll().size() + " suscripciones");

    for (PushSubscription subscription : subscriptions.findAll()) {
      PendingResult<Void> sent = sender.send(subscription, PushMessage.create("Pedidos")
          .setBody(text)
          .setIcon("icons://icon-192x192.png")
          .setUrl("/push")
          .setActions(List.of(new PushAction("inicio", "Abrir inicio", "/")))
          .build());
      sent.thenAccept(v -> report.accept("Entregado"));
      sent.exceptionally(throwable -> {
        WebforjPushException error = (WebforjPushException) throwable;
        if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
          subscriptions.delete(subscription);
          report.accept("Una suscripción expiró y fue eliminada");
        } else {
          report.accept(error.getMessage());
        }

        return null;
      });
    }
  }
}
```

</ExpandableCode>

<!-- vale on -->
