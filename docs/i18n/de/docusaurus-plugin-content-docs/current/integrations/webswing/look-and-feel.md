---
title: Erscheinungsbild von Swing
sidebar_position: 5
description: >-
  Verleihen Sie einer eingebetteten Swing-App mit dem DWC Look and Feel for
  Swing Farben, Schriftarten, Schaltflächen und Fokusrahmen von webforJ – im
  hellen oder dunklen Design.
_i18n_title_hash: 3c0ddc5fa92ce0ba34ad9e57d896d42a
_i18n_description_hash: 8f86f1732f1d00ba4e1e87a16ff232f1
_i18n_hash: f7227a823be8a2f867fdf399bc56a1b4
---
Wenn `WebswingConnector` eine Swing-App in eine webforJ-App einbettet, sehen Benutzer Swing-Bildschirme neben webforJ-Bildschirmen. Mit dem standardmäßigen Swing Look and Feel wirken die beiden Seiten wie unterschiedliche Apps. Das DWC Look and Feel für Swing verleiht der Swing-App die Schriftarten, Farben, Schaltflächen und Fokusumrandungen der DWC-Komponenten, die webforJ verwendet. Es umfasst eine helle und eine dunkle Palette.

Es handelt sich um ein standardmäßiges Swing Look and Feel in einer einzelnen JAR-Datei. Sie fügen die JAR-Datei zum Klassenpfad der Swing-App hinzu und wählen das Look and Feel beim Start aus. Der übrige Swing-Code bleibt unverändert.

:::info Bestmögliche Annäherung
Swing und DWC zeichnen ihre Steuerelemente unterschiedlich. Beide Seiten können dieselbe helle oder dunkle Palette verwenden, dennoch sind beim Wechsel zwischen einem Swing-Bildschirm und einem webforJ-Bildschirm kleine Unterschiede sichtbar.
:::

## Demo ausprobieren {#try-the-demo}

Order Desk ist eine Swing-App, in der das Theme installiert ist. [Laden Sie die Order-Desk-Demo herunter](https://dwc.style/docs/#/swing/downloads) und führen Sie sie anschließend mit Java 21 oder neuer aus:

```bash
java -jar dwc-swing-laf-demo.jar
```

Wählen Sie **View → DWC light** oder **View → DWC dark** und anschließend **View → Primary color**. Vergleichen Sie dann die ausgewählte Bestellung, den aktiven Tab und die Schaltfläche Save order.

Jedes Steuerelement in Order Desk ist eine standardmäßige Swing-Komponente. Wechseln Sie die Palette und die Primärfarbe unter dem Screenshot. Klicken Sie auf den Screenshot, um ihn zu vergrößern.

<!-- vale off -->
<ThemeGallery alt="The Order Desk Swing demo" items={[
  { label: "Default", color: "#134ddf", light: "/img/swing-laf/order-desk-light-default.webp", dark: "/img/swing-laf/order-desk-dark-default.webp" },
  { label: "Violet", color: "#7c3aed", light: "/img/swing-laf/order-desk-light-violet.webp", dark: "/img/swing-laf/order-desk-dark-violet.webp" },
  { label: "Teal", color: "#0d9488", light: "/img/swing-laf/order-desk-light-teal.webp", dark: "/img/swing-laf/order-desk-dark-teal.webp" },
  { label: "Orange", color: "#ea580c", light: "/img/swing-laf/order-desk-light-orange.webp", dark: "/img/swing-laf/order-desk-dark-orange.webp" }
]} />
<!-- vale on -->

## Theme installieren {#install-the-theme}

[Laden Sie die Theme-JAR-Datei herunter](https://dwc.style/docs/#/swing/downloads) und fügen Sie sie dem Klassenpfad der Swing-App hinzu. Sie benötigt Java 21 oder neuer und enthält beide Paletten sowie die Schriftart Inter.

Wählen Sie das Look and Feel im Swing Event Dispatch Thread aus, bevor die App ihr erstes Fenster erstellt:

```java
try {
  UIManager.setLookAndFeel(new DwcLookAndFeel());
} catch (UnsupportedLookAndFeelException e) {
  throw new IllegalStateException(e);
}
```

Für die dunkle Palette verwenden Sie stattdessen `DwcDarkLookAndFeel`. Beide Klassen befinden sich im Paket `com.basis.dwc.swing.laf`. Wenn die App ihr Look and Feel anhand des Klassennamens auswählt, verwenden Sie `com.basis.dwc.swing.laf.DwcLookAndFeel` oder `com.basis.dwc.swing.laf.DwcDarkLookAndFeel`.

Wenn die App Java-Module verwendet, fügen Sie die JAR-Datei dem Modulpfad hinzu und benötigen Sie `com.basis.dwc.swing.laf`.

## An das webforJ-Theme anpassen {#match-your-webforj-theme}

Das Theme verwendet dieselben Token-Namen wie [webforJ-Themes](/docs/styling/themes). Setzen Sie ein Token in der Swing-App auf den Wert, den Ihre webforJ-App verwendet.

Um die Primärfarbe Ihrer webforJ-App zu verwenden, legen Sie nach der Installation des Look and Feel den Primärwert fest:

```java
DwcLookAndFeel.setToken("--dwc-color-primary-seed", new Color(117, 67, 188));
```

Primäre Schaltflächen, Auswahlen und Fokusumrandungen übernehmen diese Farbe. Andere semantische Farben, etwa die Warnfarbe, behalten ihre jeweilige Palettenfarbe. Um zur Primärfarbe der Palette zurückzukehren, übergeben Sie `null` als Wert.

Das Theme verwendet standardmäßig Inter. webforJ verwendet die Systemschriftart über [`--dwc-font-family`](/docs/styling/typography). Um dieselbe Schriftart in der Swing-App zu verwenden, setzen Sie das Token auf `system-ui`:

```java
DwcLookAndFeel.setToken("--dwc-font-family", "system-ui");
```

Eine andere Schriftart kann die Breite von Beschriftungen und die Höhe von Feldern verändern. Packen Sie Fenster daher, nachdem Sie deren Inhalt erstellt haben.

## Einzelnes Steuerelement gestalten {#style-one-control}

Mit einer Client-Property lässt sich ein einzelnes Steuerelement ändern. In diesem Formular wird ein Feld beschriftet, als Pflichtfeld gekennzeichnet und die Schaltfläche Save als primär festgelegt:

```java
email.putClientProperty(DwcLookAndFeel.LABEL, "Delivery email");
email.putClientProperty(DwcLookAndFeel.REQUIRED, true);
save.putClientProperty(DwcLookAndFeel.THEME, ButtonTheme.PRIMARY);
```

Um eine Validierungsmeldung unter einem Feld anzuzeigen, setzen Sie den ungültigen Status und den Hilfetext:

```java
boolean missing = email.getText().isBlank();
email.putClientProperty(DwcLookAndFeel.INVALID, missing);
email.putClientProperty(DwcLookAndFeel.HELPER_TEXT,
    missing ? "Enter a delivery email." : "");
```

Die [Referenz zu Client-Properties](https://dwc.style/docs/#/swing/client-properties) führt die Schlüssel für alle Steuerelemente auf, darunter Tabellen, Bäume, Tabs, Schieberegler und Dialoge.

Der [vollständige Theme-Leitfaden](https://dwc.style/docs/#/swing/) behandelt Dialoge, Tastaturfokus und Animationen.
