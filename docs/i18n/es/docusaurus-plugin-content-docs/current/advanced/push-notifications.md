---
sidebar_position: 39
title: Notificaciones push
description: >-
  Use the Push class, PushSender, and PushMessage to subscribe browsers and send
  notifications from the server, even when the app isn't open.
_i18n_hash: be5e6f5947bf38824dde0289d6415971
---
<DocChip chip='since' label='26.02' />
<JavadocLink type="push" location="com/webforj/push/Push" top='true'/>

Las notificaciones push pueden llegar a los usuarios incluso cuando la aplicación no está abierta. El navegador se suscribe una vez, la aplicación almacena la suscripción y el servidor la usa para enviar notificaciones cuando se produce un evento. <JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink> gestiona la suscripción y la cancelación en el navegador. En el servidor, <JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> envía un <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> a una suscripción almacenada.

<!-- INTRO_END -->

<VideoPlayer video="push-notifications/push" width={1920} height={1072} title="Push notifications" />

## Configuración y requisitos previos {#setup-and-prerequisites}

Las notificaciones push se proporcionan mediante un módulo independiente. Añádelo a tu aplicación:

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

- Una implementación de servlet, como Jetty, Spring Boot o un archivo WAR.
- Un par de claves, que se genera más adelante, y que la implementación usa para firmar las notificaciones.
- Un origen seguro. Los navegadores rechazan las suscripciones servidas mediante cualquier protocolo que no sea `https`, excepto desde `localhost` durante el desarrollo.

:::info Orígenes seguros
<!-- vale off -->
Para obtener más información sobre los contextos seguros y por qué son importantes, consulta la [documentación de MDN sobre contextos seguros](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

### Generar las claves {#generating-the-keys}

Los servicios push solo aceptan notificaciones firmadas por la implementación a la que se suscribió el navegador. Ejecuta el [complemento de compilación](/docs/configuration/build-plugin) una vez por cada implementación para generar su par de claves:

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

El comando muestra tres líneas de configuración. Pégalas en `application.properties` sin las comillas o cópialas tal como aparecen en `webforj.conf`. Reemplaza el asunto por la dirección de contacto de la implementación. Debe ser una dirección `mailto:` o `https://` que los servicios push puedan usar para ponerse en contacto con el operador.

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| Propiedad | Explicación |
|----------|-------------|
| `webforj.push.public-key` | La parte pública del par de claves que la implementación usa para firmar las notificaciones |
| `webforj.push.private-key` | La parte privada del par de claves. Como cualquier otro secreto, mantenla fuera del control de versiones |
| `webforj.push.subject` | La dirección de contacto de la implementación. Debe ser una dirección `mailto:` o `https://` mediante la que los servicios push puedan ponerse en contacto con el operador |

La aplicación lee estas propiedades al iniciarse. Si la configuración incluye solo algunas de ellas, el inicio falla e indica qué propiedades faltan.

:::warning Rotación de claves
Cada navegador se suscribe a un par de claves. Si las claves cambian, el servicio push rechaza las suscripciones existentes. La siguiente llamada a `subscribe()` en cada navegador reemplaza su suscripción.
:::

## Cómo funciona {#how-it-works}

El proceso consta de tres pasos:

1. **Suscribirse.** Desde una vista, `Push.getCurrent().subscribe()` solicita el permiso del usuario y devuelve un `PushSubscription` que identifica la dirección del navegador.
2. **Almacenar.** La aplicación guarda la suscripción junto con sus datos y la asocia al usuario correspondiente.
3. **Enviar.** Más adelante, desde cualquier hilo, `PushSender.send(subscription, message)` pasa el mensaje al servicio push del proveedor del navegador. El servicio muestra la notificación tanto si la aplicación está abierta como si no.

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("Order shipped").setUrl("/orders/42").build());
```

En las siguientes secciones se explica qué muestra el navegador y cómo gestionar los errores en cada paso.

## Instancia {#instance}

Obtén la instancia push del entorno actual:

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

## Suscribir el navegador {#subscribing-the-browser}

Llama a `subscribe()` como respuesta a una acción del usuario, por ejemplo, al hacer clic en un botón «Activar notificaciones». El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> devuelto se completa con el <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink> del navegador. Si el navegador no puede suscribirse, el resultado se completa de forma excepcional con un <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink>.

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

Si el navegador ya está suscrito, volver a llamar a `subscribe()` devuelve la suscripción existente. Por lo tanto, puedes llamarlo sin riesgo en cada visita.

:::info Permisos del navegador
La primera llamada a `subscribe()` solicita permiso al usuario. El navegador muestra esta solicitud; no forma parte de la interfaz de la aplicación. Como los navegadores solo muestran la solicitud en respuesta a una acción del usuario, llama a `subscribe()` desde un listener de clics y no desde el constructor de la vista.

Si el usuario bloquea la solicitud, la aplicación no puede volver a solicitar permiso para ese origen.
:::

### Almacenar suscripciones {#storing-subscriptions}

Una suscripción representa la dirección de un navegador y debe almacenarse en el servidor. Guárdala junto con los datos de la aplicación y usa su endpoint como clave. Incluye la información que la aplicación necesite para seleccionar los navegadores adecuados más adelante, como el usuario asociado. Cada suscripción contiene tres valores de texto:

| Valor | Significado |
|-------|---------|
| `getEndpoint()` | La URL de entrega asignada por el servicio push del proveedor del navegador |
| `getP256dh()` | La clave pública del navegador |
| `getAuth()` | El secreto de autenticación del navegador |

Un usuario que se suscribe desde dos navegadores tiene dos suscripciones. Elimina una suscripción cuando se cancele desde su navegador o cuando un envío indique que ha caducado. Consulta [Estado de error](#failure-status).

### Restaurar una suscripción {#restoring-a-subscription}

`getSubscription()` devuelve la suscripción actual del navegador o un resultado vacío si no existe ninguna. Úsalo para sincronizar la copia del servidor, por ejemplo, después de restablecer el almacenamiento de la aplicación:

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

Mediante <JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink>, `getPermission()` indica si el usuario ha concedido o denegado el permiso, o si todavía no ha respondido a la solicitud de notificaciones. Usa este resultado para ocultar el botón «Activar notificaciones» cuando hacer clic en él no tendría ningún efecto.

### Cancelar la suscripción {#unsubscribing}

`unsubscribe()` cancela la suscripción del navegador. Se completa con la suscripción eliminada para que la aplicación pueda borrar su copia almacenada, o con un resultado vacío si el navegador no tenía ninguna suscripción.

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## Enviar notificaciones {#sending-notifications}

<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> envía un <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> a una suscripción almacenada. Firma el mensaje con las claves de la implementación y lo pasa al servicio push del proveedor del navegador. Ese servicio activa el navegador y muestra la notificación. Como la operación nunca bloquea el hilo que la invoca, puedes llamarla desde un listener de clics, una tarea programada o un controlador de solicitudes.

Una vez configuradas las propiedades, el emisor está disponible como bean que puedes inyectar en vistas, servicios y tareas programadas. Para reemplazarlo, define tu propio bean `PushSender`.

```java
@Route("/orders")
public class OrdersView extends Composite<FlexLayout> {

  public OrdersView(PushSender sender, PushSubscriptions subscriptions) {
    // ...
  }
}
```

Sin Spring, `new PushSender()` lee las claves de la configuración de la aplicación. Crea el emisor en un hilo de la aplicación, ya sea en una vista o en `App.run()`, y luego úsalo desde cualquier hilo. Todos los emisores comparten un único grupo de conexiones con los servicios push, así que no tiene ningún coste crear uno donde lo necesites.

Para las notificaciones que deban enviarse más tarde o después de que el usuario se vaya, usa un temporizador en el servidor, como `TaskScheduler` de Spring. No uses un temporizador de página como `Interval`, porque deja de funcionar cuando se cierra la pestaña.

### Crear un mensaje {#composing-a-message}

Crea un mensaje con su título y, luego, configura las demás opciones en el builder:

```java
PushMessage message = PushMessage.create("Order shipped")
    .setBody("Order #42 is on its way")
    .setIcon("icons://icon-192x192.png")
    .setUrl("/orders/42")
    .setActions(List.of(new PushAction("track", "Track", "/orders/42/tracking")))
    .build();

PendingResult<Void> sent = sender.send(subscription, message);
sent.thenAccept(v -> status.setText("Sent"));
sent.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  status.setText(error.getStatus() + ": " + error.getMessage());

  return null;
});
```

`send()` devuelve el control inmediatamente. El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> se completa cuando el servicio push acepta el mensaje o se completa de forma excepcional si el servicio no lo acepta. Si se llama a `send()` en un hilo de la aplicación, por ejemplo, desde un listener, sus callbacks se ejecutan en ese hilo y pueden actualizar componentes. Si la sesión que llamó a `send()` termina antes de que llegue la respuesta, los callbacks no se ejecutan, pero la notificación se entrega igualmente.

El envío espera hasta 30 segundos a que responda el servicio push antes de fallar con `UNREACHABLE`. Usa `setTimeout(Duration)` para cambiar el tiempo de espera de cada emisor.

| Opción | Efecto |
|--------|--------|
| `setBody` | Define el texto que se muestra debajo del título |
| `setIcon` | Define la imagen que se muestra con la notificación. Acepta URL absolutas y los protocolos `icons://` y `ws://`. Consulta [Recursos](/docs/managing-resources/assets-protocols). No acepta el protocolo `context://`, porque los servicios push limitan los mensajes a 4 KB |
| `setUrl` | Define la página que se abre cuando el usuario hace clic en la notificación. Las URL relativas se resuelven en relación con la raíz de la aplicación. Si no se establece ninguna URL, se abre la raíz de la aplicación |
| `setActions` | Define los botones que se muestran en la notificación, cada uno con su propia URL. Consulta [Compatibilidad con navegadores](#browser-support) |
| `setTag` | Define una etiqueta identificativa. Si una notificación mostrada tiene la misma etiqueta, la nueva la reemplaza |
| `setSilent` | Muestra la notificación sin sonido ni vibración |
| `setTimeToLive` | Define durante cuánto tiempo el servicio push conserva el mensaje para un dispositivo desconectado, hasta un máximo de cuatro semanas |
| `setUrgency` | Usa <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink> para permitir que el dispositivo retrase los mensajes de baja urgencia y ahorre batería |
| `setTopic` | Reemplaza un mensaje que todavía esté en espera en el servicio push si ambos mensajes tienen el mismo tema. Los temas pueden contener como máximo 32 caracteres seguros para URL |

Si una pestaña ya muestra la página, al hacer clic en la notificación se activa la aplicación. De lo contrario, la página se abre en una pestaña nueva. Al hacer clic en un botón de notificación, su URL se abre de la misma manera.

:::info Una notificación por mensaje
Cada mensaje muestra una notificación. Como los navegadores no activan una página para un mensaje que no muestra nada, las notificaciones push no se pueden usar para actualizaciones de datos silenciosas.
:::

## Estado de error {#failure-status}

Cuando `subscribe()` o `send()` falla, su `PendingResult` informa de un `WebforjPushException`. <JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink> identifica el motivo:

| Estado | Cuándo | Qué hacer |
|--------|------|------------|
| `PERMISSION_DENIED` | El usuario ha bloqueado las notificaciones de la aplicación | Explica dónde puede permitir el usuario las notificaciones en la configuración del navegador |
| `UNSUPPORTED` | El navegador no admite las notificaciones push, la página no está en un contexto seguro o la aplicación no está implementada como servlet | Oculta la función |
| `NOT_CONFIGURED` | Falta al menos una propiedad `webforj.push.*` o su configuración está incompleta | Genera las claves y configura las tres propiedades |
| `SUBSCRIPTION_EXPIRED` | El servicio push ya no reconoce la suscripción porque el usuario canceló la suscripción o reinstaló el navegador | Elimina la suscripción almacenada |
| `REJECTED` | El servicio push rechazó el mensaje; `getStatusCode()` contiene su respuesta | Verifica las claves y el tamaño del mensaje |
| `UNREACHABLE` | El servicio push no respondió antes de que se agotara el tiempo de espera | Vuelve a intentarlo más tarde |
| `UNKNOWN` | El endpoint almacenado no es una URL válida, o no se pudo codificar la suscripción o el mensaje | Verifica la suscripción almacenada |

Elimina las suscripciones caducadas durante cada envío:

```java
sender.send(subscription, message).exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
    subscriptions.delete(subscription);
  }

  return null;
});
```

:::tip La caducidad se informa un mensaje después
Los servicios push cancelan las suscripciones de forma diferida. Siguen aceptando el primer mensaje después de que el usuario cancele la suscripción, pero no llega a ninguna parte. El siguiente mensaje informa de `SUBSCRIPTION_EXPIRED`. Que se acepte un envío significa que el mensaje llegó al servicio push, no que el usuario lo haya visto.
:::

## Compatibilidad con navegadores {#browser-support}

Todos los principales navegadores de escritorio y móviles muestran notificaciones push después de suscribirse. Ten en cuenta estas limitaciones:

- En iPhone y iPad, las notificaciones push solo funcionan con aplicaciones web añadidas a la pantalla de inicio en iOS 16.4 o posterior. En una pestaña de Safari, `subscribe()` informa de `UNSUPPORTED`. Consulta [Aplicaciones instalables](/docs/configuration/installable-apps) para ver el manifiesto de aplicación requerido.
- Safari no muestra botones en las notificaciones. Muestra los mensajes con acciones sin sus botones, pero al hacer clic en la notificación se abre igualmente la URL del mensaje.
- Las WebViews de Android e iOS no muestran notificaciones.

Para consultar los detalles de cada navegador, consulta la [tabla de compatibilidad de showNotification](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility) de MDN.

## Ejemplo completo {#complete-example}

La siguiente vista suscribe y cancela la suscripción del navegador, almacena las suscripciones en memoria y envía un mensaje a todas las suscripciones almacenadas. Puede enviarlo inmediatamente o esperar ocho segundos usando `TaskScheduler` de Spring, lo que permite cerrar la pestaña antes de que llegue la notificación. La clase de la aplicación usa `@EnableScheduling` para habilitar el planificador.

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
  private final Paragraph status = new Paragraph("Checking subscription…");
  private final TextField message = new TextField("Message", "Order #42 is on its way");
  private final Button subscribe =
      new Button("Enable notifications", ButtonTheme.PRIMARY);
  private final Button unsubscribe = new Button("Disable notifications");
  private final Button sendNow = new Button("Send now");
  private final Button sendLater = new Button("Send in 8 seconds");

  public PushView(PushSubscriptions subscriptions, PushSender sender, TaskScheduler scheduler) {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    subscribe.onClick(ev -> Push.getCurrent().subscribe()
        .thenAccept(subscription -> {
          subscriptions.save(subscription);
          status.setText("Subscribed");
        })
        .exceptionally(throwable -> {
          WebforjPushException error = (WebforjPushException) throwable.getCause();
          status.setText(error.getStatus() == PushStatus.PERMISSION_DENIED
              ? "Notifications are blocked in this browser"
              : error.getMessage());

          return null;
        }));

    unsubscribe.onClick(ev -> Push.getCurrent().unsubscribe().thenAccept(removed -> {
      removed.ifPresent(subscriptions::delete);
      status.setText(removed.isPresent() ? "Unsubscribed" : "There was no subscription");
    }));

    sendNow.onClick(ev -> sendToAll(subscriptions, sender, message.getValue(), status::setText));

    sendLater.onClick(ev -> {
      String text = message.getValue();
      status.setText("Sending in 8 seconds, close the tab now");
      scheduler.schedule(() -> sendToAll(subscriptions, sender, text, outcome -> {
      }), Instant.now().plusSeconds(8));
    });

    Push.getCurrent().getSubscription().thenAccept(existing -> {
      existing.ifPresent(subscriptions::save);
      status.setText(existing.isPresent() ? "Subscribed" : "Not subscribed");
    });

    self.add(status, message, subscribe, unsubscribe, sendNow, sendLater);
  }

  private static void sendToAll(PushSubscriptions subscriptions, PushSender sender, String text,
      Consumer<String> report) {
    report.accept("Sending to " + subscriptions.findAll().size() + " subscriptions");

    for (PushSubscription subscription : subscriptions.findAll()) {
      PendingResult<Void> sent = sender.send(subscription, PushMessage.create("Orders")
          .setBody(text)
          .setIcon("icons://icon-192x192.png")
          .setUrl("/push")
          .setActions(List.of(new PushAction("home", "Open home", "/")))
          .build());
      sent.thenAccept(v -> report.accept("Delivered"));
      sent.exceptionally(throwable -> {
        WebforjPushException error = (WebforjPushException) throwable;
        if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
          subscriptions.delete(subscription);
          report.accept("A subscription expired and was removed");
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
