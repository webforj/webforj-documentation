---
sidebar_position: 15
title: Browser Console
_i18n_hash: fd0e46761a5fd8b887a39b7a51e9b66b
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Selain sela seluruhan terdalam program dari browser adalah bagian integral dari proses pengembangan. 
Kelas utilitas <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> menyediakan fitur-fitur yang meningkatkan kemampuan pencatatan melalui jenis log dan penataan.

<!-- :::info
Sebelum `24.10`, metode `App.consoleLog()` dan `App.consoleError()` mengaktifkan perilaku ini, namun sejak saat itu telah ditandai untuk depresiasi.
::: -->

## Instance {#instance}

Dapatkan satu instance dari `BrowserConsole` menggunakan metode `App.console()`. Cetak objek `Object` yang diinginkan sebagai salah satu dari lima jenis log: log, info, warn, error, atau debug.

```java
import static com.webforj.App.console;
// Jenis
console().log("Pesan log");
console().info("Pesan info");
console().warn("Pesan peringatan");
console().error("Pesan kesalahan");
console().debug("Pesan debug");
```

## Styling {#styling}

Gunakan metode builder untuk mengatur penampilan pesan log. Setiap builder memiliki opsi untuk mengubah properti tertentu. Juga dimungkinkan untuk [mencampur beberapa gaya](#mixing-styles).
Setelah pesan konsol dicetak, styling apa pun yang diterapkan tidak akan dibawa ke pesan berikutnya kecuali *secara eksplisit* didefinisikan ulang.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Gunakan metode `setStyle` untuk mengubah properti dari log `BrowserConsole` yang tidak ditentukan oleh builder.
:::

### Background color {#background-color}

Atur warna latar belakang dengan metode `background()`, yang mengembalikan <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink>.
Gunakan metode yang dinamai dengan warna, seperti `blue()`, atau pilih nilai tertentu dengan `colored(String color)`.

```java
// Contoh Latar Belakang
console().background().blue().log("Latar belakang biru");
console().background().colored("#031f8f").log("Latar belakang biru kustom");
```

### Text color {#text-color}

Atur warna teks dengan metode `color()`, yang mengembalikan <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink>.
Gunakan metode yang dinamai dengan warna, seperti `red()`, atau pilih nilai tertentu dengan `colored(String color)`.

```java
// Contoh Warna
console().background().red().log("Teks merah");
console().color().colored("#becad2").log("Teks abu-abu kebiruan kustom");
```

### Font size {#font-size}

Atur ukuran font dengan metode `size()`, yang mengembalikan <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink>.
Gunakan metode yang dinamai dengan ukuran, seperti `small()`, atau pilih nilai tertentu dengan `from(String value)`.

```java
// Contoh Ukuran
console().size().small().log("Font kecil");
console().size().from("30px").log("Font 30px");
```
:::tip
Metode `from(String value)` dapat mengambil nilai ukuran font lainnya, seperti rem dan vw.
:::

### Font style {#font-style}

Atur gaya font dengan metode `style()`, yang mengembalikan <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink>.
Misalnya, gunakan metode `italic()` untuk membuat log konsol menjadi miring.

```java
// Contoh Gaya
console().style().italic().log("Font miring");
console().style().normal().log("Font normal");
```

### Text transformation {#text-transformation}

Kontrol kapitalisasi karakter dalam pesan dengan metode `transform()`, yang mengembalikan <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink>.
Misalnya, gunakan metode `capitalize()` untuk mengubah huruf pertama dari setiap kata menjadi huruf besar.

```java
// Contoh Transformasi
// Transformasi Kapitalisasi Teks
console().transform().capitalize().log("Transformasi kapitalisasi teks");
// TRANSFORMASI TEKS  BIG LETTER 
console().transform().uppercase().log("Transformasi teks huruf besar");
```

### Font weight {#font-weight}

Atur seberapa tebal teksnya dengan metode `weight()`, yang mengembalikan <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink>.
Misalnya, gunakan metode `lighter()` untuk membuat font lebih ringan dari normal.

```java
// Contoh Berat
console().weight().bold().log("Font tebal");
console().weight().lighter().log("Font lebih ringan");
```

## Mixing styles {#mixing-styles}
Dimungkinkan untuk mencampur dan mencocokkan metode untuk tampilan logging yang kustom.

```java
// Berbagai opsi untuk tampilan logging kustom
console()
    .weight().bolder()
    .size().larger()
    .color().gray()
    .style().italic()
    .transform().uppercase()
    .background().blue()
    .warn("Mencampur gaya");
```
