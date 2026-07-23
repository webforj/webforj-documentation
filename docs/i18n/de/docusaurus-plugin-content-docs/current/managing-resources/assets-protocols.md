---
sidebar_position: 2
title: Assets Protocols
description: >-
  Reference app resources with the webforJ ws, context, and icons protocols to
  load static files, classpath content, and dynamic icons.
_i18n_hash: 3928ba255cb9887c80c20f904baf62b8
---
webforJ unterstützt benutzerdefinierte Asset-Protokolle, die einen strukturierten und effizienten Zugriff auf Ressourcen ermöglichen. Diese Protokolle abstrahieren die Komplexitäten der statischen und dynamischen Ressourcenabfrage und gewährleisten, dass Assets nahtlos innerhalb der App abgerufen und integriert werden.

## Das webserver-Protokoll {#the-webserver-protocol}

Das **`ws://`**-Protokoll ermöglicht es Ihnen, Assets abzurufen, die im statischen Ordner einer webforJ-App gehostet werden. Alle Dateien, die sich im Klassenpfad der App `src/main/resources/static` befinden, sind direkt über den Browser zugänglich. Wenn Sie beispielsweise eine Datei namens **css/app.css** im **resources/static**-Ordner haben, kann sie unter **`/static/css/app.css`** erreicht werden.

Das **ws://**-Protokoll entfernt die Notwendigkeit, den `static`-Präfix in Ihren Ressourcen-URLs hart zu codieren, was gegen geänderte Präfixe je nach Bereitstellungskontext schützt. Wenn die Web-App unter einem anderen Kontext als dem Wurzelverzeichnis bereitgestellt wird, wie z.B. **/mycontext**, wäre die URL für **css/app.css**: **`/mycontext/static/css/app.css`**

:::tip Konfigurieren des statischen Präfixes
Sie können den `static`-Präfix mithilfe der [webforj-Konfiguration](../configuration/properties#configuration-options) Option `webforj.assetsDir` steuern. Diese Einstellung gibt den Routenname an, der zum Bereitstellen statischer Dateien verwendet wird, während **der physikalische Ordner weiterhin `static` benannt bleibt**. Dies ist besonders nützlich, wenn die Standardroute für statische Inhalte mit einer Route in Ihrer App in Konflikt steht, da Sie den Routenname ändern können, ohne den Ordner umzubenennen.
:::

Sie können die <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink>-Hilfsklasse verwenden, um eine gegebene Webserver-URL zu lösen. Dies kann nützlich sein, wenn Sie eine benutzerdefinierte Komponente haben, die dieses Protokoll unterstützen muss.

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## Das Kontextprotokoll {#the-context-protocol}

Das Kontextprotokoll mappt auf Ressourcen innerhalb des Klassenpfads der App unter `src/main/resources`. Einige Ressourcen-API-Methoden und -Annotationen unterstützen dieses Protokoll, um den Inhalt einer Datei im Ressourcenordner zu lesen und seinen Inhalt an den Browser zu senden. Beispielsweise ermöglicht die Annotation `InlineJavaScript` das Lesen des Inhalts einer JavaScript-Datei aus dem Ressourcenordner und das Einfügen in die Client-Seite.

Beispielsweise:

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## Das Icons-Protokoll {#the-icons-protocol}

Das **`icons://`**-Protokoll bietet einen dynamischen Ansatz für das Icon-Management, der es effizient und flexibel macht, Icons in [installierbaren Apps](../configuration/installable-apps) zu generieren und bereitzustellen. Dieses Protokoll ermöglicht es Ihnen, Icons je nach angeforderter Dateinamen und Parametern dynamisch zu erzeugen, wodurch die Notwendigkeit vorab generierter Icons in vielen Fällen entfällt.

```java
Img icon = new Img("icons://icon-192x192.png")
```

### Basis-Icon {#base-icon}

Wenn ein Icon über das `icons://`-Protokoll angefordert wird, wird ein Basisbild dynamisch aus dem angeforderten Icon-Dateinamen abgeleitet. Dies gewährleistet Konsistenz im Icon-Design und ermöglicht ein automatisches Zurückfallen auf ein Standardbild, wenn kein Basis-Icon bereitgestellt wird.

- **Beispiel 1:** Anfrage: `/icons/icon-192x192.png` → Basis-Icon: `resources/icons/icon.png`
- **Beispiel 2:** Anfrage: `/icons/icon-different-192x192.png` → Basis-Icon: `resources/icons/icon-different.png`

Wenn für das angeforderte Icon kein Basisbild vorhanden ist, wird das Standard-logo von webforJ als Fallback verwendet.

:::tip `webforj.iconsDir`
Standardmäßig liefert webforJ Icons aus dem Verzeichnis `resources/icons/`. Sie können den Endpunktnamen ändern, indem Sie die `webforj.iconsDir`-Eigenschaft in der webforJ-Konfigurationsdatei festlegen. Dies ändert nur die URL-Endpunkte, die zum Zugreifen auf die Icons verwendet werden, nicht den tatsächlichen Ordnernamen. Der Standardendpunkt ist `icons/`.
:::

### Überschreiben von Icons {#overriding-icons}

Sie können bestimmte Icon-Größen überschreiben, indem Sie vorab generierte Bilder im Verzeichnis `resources/icons/` platzieren. Dies ermöglicht eine bessere Kontrolle über das Erscheinungsbild von Icons, wenn bestimmte Größen oder Stile angepasst werden müssen.

- **Beispiel:** Wenn `resources/icons/icon-192x192.png` vorhanden ist, wird es direkt bedient, anstatt dynamisch generiert zu werden.

### Anpassen des Icon-Erscheinungsbilds {#customizing-icon-appearance}

Das `icons://`-Protokoll unterstützt zusätzliche Parameter, die es Ihnen ermöglichen, das Erscheinungsbild der dynamisch generierten Icons anzupassen. Dazu gehören Optionen für Padding und Hintergrundfarbe.

- **Padding**: Der `padding`-Parameter kann verwendet werden, um das Padding um das Icon zu steuern. Akzeptierte Werte reichen von `0`, was kein Padding bedeutet, bis `1`, was maximales Padding bedeutet.
  - **Beispiel:** `/icons/icon-192x192.png?padding=0.3`

- **Hintergrundfarbe**: Der `background`-Parameter ermöglicht es Ihnen, die Hintergrundfarbe des Icons festzulegen. Folgende Werte werden unterstützt:
  - **`transparent`**: Keine Hintergrundfarbe.
  <!-- vale off -->
  - **Hexadezimale Farbwerte**: Benutzerdefinierte Hintergrundfarben (z.B. `FFFFFF` für weiß).
  <!-- vale on -->
  - **`auto`**: Versucht automatisch, die Hintergrundfarbe des Icons zu erkennen.

  Beispielsweise:

  - **Beispiel 1:** `/icons/icon-192x192.png?background=000000`
  - **Beispiel 2:** `/icons/icon-192x192.png?background=auto`
