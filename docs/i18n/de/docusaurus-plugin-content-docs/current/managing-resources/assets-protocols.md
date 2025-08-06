---
sidebar_position: 2
title: Assets Protocols
_i18n_hash: b37158687b15dc2b94a7543624eaa09f
---
webforJ unterstützt benutzerdefinierte Asset-Protokolle, die einen strukturierten und effizienten Zugriff auf Ressourcen ermöglichen. Diese Protokolle abstrahieren die Komplexität des Abrufs statischer und dynamischer Ressourcen und sorgen dafür, dass Assets nahtlos innerhalb der App abgerufen und integriert werden.

## Das Webserver-Protokoll {#the-webserver-protocol}

Das **`ws://`**-Protokoll ermöglicht es Ihnen, Assets abzurufen, die im statischen Ordner einer webforJ-App gehostet werden. Alle Dateien, die sich im Klassenpfad der App `src/main/resources/static` befinden, sind direkt über den Browser zugänglich. Wenn Sie beispielsweise eine Datei namens **css/app.css** im **resources/static**-Ordner haben, kann sie unter **`/static/css/app.css`** aufgerufen werden. 

Das **ws://**-Protokoll entfernt die Notwendigkeit, das `static`-Präfix in Ihren Ressourcen-URLs hart zu codieren, und schützt so vor geänderten Präfixen, je nach Bereitstellungskontext. Wenn die Webanwendung unter einem anderen Kontext als dem Root kontext bereitgestellt wird, wie zum Beispiel **/mycontext**, wäre die URL für **css/app.css**: **`/mycontext/static/css/app.css`**  

:::tip Konfigurieren des statischen Präfixes
Sie können das `static`-Präfix mithilfe der [webforj-Konfiguration](../configuration/properties#configuration-options) Option `webforj.assetsDir` steuern. Diese Einstellung gibt den Routen-Namen an, der zum Bereitstellen statischer Dateien verwendet wird, während **der physische Ordner weiterhin den Namen `static` trägt**. Dies ist besonders nützlich, wenn die Standard-Static-Route mit einer Route in Ihrer App in Konflikt steht, da es Ihnen ermöglicht, den Routen-Namen zu ändern, ohne den Ordner umzubenennen.
:::

Sie können die <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink>-Dienstprogrammkasse verwenden, um eine gegebene Webserver-URL aufzulösen. Dies kann nützlich sein, wenn Sie eine benutzerdefinierte Komponente haben, die dieses Protokoll unterstützen muss.

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## Das Kontextprotokoll {#the-context-protocol}

Das Kontextprotokoll entspricht Ressourcen innerhalb des Klassenpfads der App unter `src/main/resources`. Einige Methoden und Annotationen der Ressourcen-API unterstützen dieses Protokoll, um den Inhalt einer Datei im Ressourcenordner zu lesen und dessen Inhalt an den Browser zu senden. Zum Beispiel erlaubt die `InlineJavaScript`-Annotation das Lesen des Inhalts einer JavaScript-Datei aus dem Ressourcenordner und das Einfügen dieses Inhalts auf der Client-Seite.

Zum Beispiel:

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## Das Icons-Protokoll {#the-icons-protocol}

Das **`icons://`**-Protokoll bietet einen dynamischen Ansatz für das Icon-Management, wodurch es effizient und flexibel für die Generierung und Bereitstellung von Icons in [installierbaren Apps](../configuration/installable-apps) wird. Dieses Protokoll ermöglicht es Ihnen, Icons dynamisch basierend auf dem angeforderten Dateinamen und den Parametern zu generieren, wodurch die Notwendigkeit vorgefertigter Icons in vielen Fällen entfällt.

```java
Img icon = new Img("icons://icon-192x192.png")
```

### Basisicon {#base-icon}

Wenn ein Icon über das `icons://`-Protokoll angefordert wird, wird ein Basisbild dynamisch aus dem angeforderten Icon-Dateinamen abgeleitet. Dies stellt Konsistenz im Icon-Design sicher und ermöglicht einen automatischen Fallback auf ein Standardbild, wenn kein Basisicon bereitgestellt wird.

- **Beispiel 1:** Anfrage: `/icons/icon-192x192.png` → Basisicon: `resources/icons/icon.png`
- **Beispiel 2:** Anfrage: `/icons/icon-different-192x192.png` → Basisicon: `resources/icons/icon-different.png`

Wenn für das angeforderte Icon kein Basisbild existiert, wird das Standard-Logo von webforJ als Fallback verwendet.

:::tip `webforj.iconsDir`
Standardmäßig bedient webforJ Icons aus dem Verzeichnis `resources/icons/`. Sie können den Endpunktnamen ändern, indem Sie die `webforj.iconsDir`-Eigenschaft in der webforJ-Konfigurationsdatei festlegen. Dies ändert nur den URL-Endpunkt, der zum Zugreifen auf die Icons verwendet wird, nicht den tatsächlichen Ordnernamen. Der Standard-Endpunkt ist `icons/`. 
:::

### Überschreiben von Icons {#overriding-icons}

Sie können bestimmte Icon-Größen überschreiben, indem Sie vorgefertigte Bilder im Verzeichnis `resources/icons/` ablegen. Dies ermöglicht eine bessere Kontrolle über das Erscheinungsbild der Icons, wenn bestimmte Größen oder Stile angepasst werden müssen.

- **Beispiel:** Wenn `resources/icons/icon-192x192.png` existiert, wird es direkt bereitgestellt, anstatt dynamisch generiert zu werden.

### Anpassung des Icon-Aussehens {#customizing-icon-appearance}

Das `icons://`-Protokoll unterstützt zusätzliche Parameter, mit denen Sie das Aussehen der dynamisch generierten Icons anpassen können. Dazu gehören Optionen für Abstände und Hintergrundfarbe.

- **Abstand**: Der `padding`-Parameter kann verwendet werden, um den Abstand um das Icon zu steuern. Akzeptierte Werte reichen von `0`, was keinen Abstand bedeutet, bis `1`, was maximalen Abstand bedeutet.
  - **Beispiel:** `/icons/icon-192x192.png?padding=0.3`
  
- **Hintergrundfarbe**: Der `background`-Parameter ermöglicht es Ihnen, die Hintergrundfarbe des Icons festzulegen. Er unterstützt die folgenden Werte:
  - **`transparent`**: Keine Hintergrundfarbe.
  <!-- vale off -->
  - **Hexadezimale Farbcodes**: Benutzerdefinierte Hintergrundfarben (z.B. `FFFFFF` für weiß).
  <!-- vale on -->
  - **`auto`**: Versucht automatisch, die Hintergrundfarbe des Icons zu erkennen.

  Zum Beispiel: 
  - **Beispiel 1:** `/icons/icon-192x192.png?background=000000`
  - **Beispiel 2:** `/icons/icon-192x192.png?background=auto`
