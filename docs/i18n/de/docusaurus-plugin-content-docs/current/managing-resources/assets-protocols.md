---
sidebar_position: 2
title: Assets Protocols
_i18n_hash: a7482285684e797c3cfc30d025a95482
---
webforJ unterstützt benutzerdefinierte Asset-Protokolle, die einen strukturierten und effizienten Ressourcen Zugriff ermöglichen. Diese Protokolle abstrahieren die Komplexität der statischen und dynamischen Ressourcenbeschaffung und gewährleisten, dass Assets nahtlos innerhalb der App abgerufen und integriert werden.

## Das Webserver-Protokoll {#the-webserver-protocol}

Das **`ws://`** Protokoll ermöglicht es Ihnen, Assets abzurufen, die im statischen Ordner einer webforJ-App gehostet werden. Alle Dateien, die sich im App-Klassenpfad `src/main/resources/static` befinden, sind direkt über den Browser zugänglich. Wenn Sie beispielsweise eine Datei namens **css/app.css** im **resources/static** Ordner haben, kann sie unter **`/static/css/app.css`** aufgerufen werden.  

Das **ws://** Protokoll beseitigt die Notwendigkeit, das `static` Präfix in Ihren Ressourcen-URLs hartkodiert einzufügen, was gegen Änderungen der Präfixe in Abhängigkeit vom Bereitstellungskontext schützt. Wenn die Web-App unter einem anderen Kontext als dem Root-Kontext bereitgestellt wird, wie z. B. **/mycontext**, wäre die URL für **css/app.css**: **`/mycontext/static/css/app.css`**  

:::tip Konfiguration des statischen Präfix
Sie können das `static` Präfix mithilfe der [webforj-Konfiguration](../configuration/properties#configuration-options) Option `webforj.assetsDir` steuern. Diese Einstellung gibt den Routen-Namen an, der verwendet wird, um statische Dateien bereitzustellen, während **der physische Ordner weiterhin `static` genannt wird**. Dies ist besonders nützlich, wenn die standardmäßige statische Route mit einer Route in Ihrer App in Konflikt steht, da es Ihnen ermöglicht, den Routen-Namen zu ändern, ohne den Ordner umzubenennen.
:::

Sie können die <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> Dienstprogrammkklasse verwenden, um eine gegebene Webserver-URL aufzulösen. Dies kann nützlich sein, wenn Sie eine benutzerdefinierte Komponente haben, die dieses Protokoll unterstützen muss.

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## Das Kontext-Protokoll {#the-context-protocol}

Das Kontext-Protokoll ordnet Ressourcen innerhalb des Klassenpfads der App unter `src/main/resources` zu. Einige Ressourcen-API-Methoden und Annotationen unterstützen dieses Protokoll, um den Inhalt einer Datei im Ressourcenordner zu lesen und dessen Inhalt an den Browser zu senden. Beispielsweise ermöglicht die Annotation `InlineJavaScript` das Lesen des Inhalts einer JavaScript-Datei aus dem Ressourcenordner und das Inline-Integrieren auf der Client-Seite.

Zum Beispiel:

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## Das Icons-Protokoll {#the-icons-protocol}

Das **`icons://`** Protokoll bietet einen dynamischen Ansatz für das Icon-Management, was es effizient und flexibel für die Generierung und Bereitstellung von Icons in [installierbaren Apps](../configuration/installable-apps) macht. Dieses Protokoll ermöglicht es, Icons basierend auf dem angeforderten Dateinamen und den Parametern dynamisch zu generieren, wodurch die Notwendigkeit vorgefertigter Icons in vielen Fällen entfällt.

```java
Img icon = new Img("icons://icon-192x192.png")
```

### Basis-Icon {#base-icon}

Wenn ein Icon mit dem `icons://` Protokoll angefordert wird, wird ein Basisbild dynamisch aus dem angeforderten Icon-Dateinamen abgeleitet. Dies gewährleistet Konsistenz im Icon-Design und ermöglicht ein automatisches Zurückfallen auf ein Standardbild, wenn kein Basis-Icon bereitgestellt wird.

- **Beispiel 1:** Anfrage: `/icons/icon-192x192.png` → Basis-Icon: `resources/icons/icon.png`
- **Beispiel 2:** Anfrage: `/icons/icon-different-192x192.png` → Basis-Icon: `resources/icons/icon-different.png`

Wenn für das angeforderte Icon kein Basisbild existiert, wird das StandardwebforJ-Logo als Fallback verwendet.

:::tip `webforj.iconsDir`
Standardmäßig dient webforJ Icons aus dem `resources/icons/` Verzeichnis. Sie können den Endpunkt-Namen ändern, indem Sie die `webforj.iconsDir` Eigenschaft in der webforJ-Konfigurationsdatei festlegen. Dies ändert nur die URL des Endpunkts, der zum Zugriff auf die Icons verwendet wird, nicht den tatsächlichen Ordnernamen. Der Standardendpunkt ist `icons/`. 
:::

### Überschreiben von Icons {#overriding-icons}

Sie können bestimmte Icon-Größen überschreiben, indem Sie vorgefertigte Bilder im `resources/icons/` Verzeichnis ablegen. Dies bietet eine größere Kontrolle über das Erscheinungsbild von Icons, wenn bestimmte Größen oder Stile angepasst werden müssen.

- **Beispiel:** Wenn `resources/icons/icon-192x192.png` existiert, wird es direkt bereitgestellt, anstatt dynamisch generiert zu werden.

### Anpassen des Icon-Erscheinungsbilds {#customizing-icon-appearance}

Das `icons://` Protokoll unterstützt zusätzliche Parameter, die es Ihnen ermöglichen, das Erscheinungsbild von dynamisch generierten Icons anzupassen. Dazu gehören Optionen für Polsterung und Hintergrundfarbe.

- **Polsterung**: Der `padding` Parameter kann verwendet werden, um die Polsterung um das Icon zu steuern. Akzeptierte Werte reichen von `0`, was keine Polsterung bedeutet, bis `1`, was maximalen Abstand bedeutet.
  - **Beispiel:** `/icons/icon-192x192.png?padding=0.3`
  
- **Hintergrundfarbe**: Der `background` Parameter ermöglicht es Ihnen, die Hintergrundfarbe des Icons festzulegen. Er unterstützt die folgenden Werte:
  - **`transparent`**: Keine Hintergrundfarbe.
  <!-- vale off -->
  - **Hexadezimale Farbwerte**: Benutzerdefinierte Hintergrundfarben (z. B. `FFFFFF` für Weiß).
  <!-- vale on -->
  - **`auto`**: Versucht, die Hintergrundfarbe des Icons automatisch zu erkennen.

  Zum Beispiel: 
  
  - **Beispiel 1:** `/icons/icon-192x192.png?background=000000`
  - **Beispiel 2:** `/icons/icon-192x192.png?background=auto`
