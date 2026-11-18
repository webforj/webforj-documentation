---
title: SCSS ja Sass
sidebar_position: 30
description: >-
  Käännä SCSS tai Sass CSS:ksi webforj-scss-laajennuksella, sido luokka
  tyylitiedostoon ja määritä latauspolku bun.config.ts-tiedostossa.
_i18n_title_hash: 9bd51327ee5db5248d9c4d0c207c9650
_i18n_description_hash: 241a3d6fadb39e11156b12f9a5d54e15
_i18n_hash: b278152eaf94d853a152b4d605c71981
---
[Sass](https://sass-lang.com/) on tyyliarkkikieli, joka lisää muuttujia, sisäkkäisyyksiä ja funktioita CSS:ään, ja SCSS on sen CSS:ään perustuva syntaksi. SCSS-laajennus aktivoituu, kun '.scss' tai '.sass' lähde on läsnä, kääntää sen CSS:ksi ja lataa sen tyyleiksi. Luokka sitoutuu kääntäytyneeseen tyyliarkkiin samalla tavalla kuin se sitoutuu skriptiin.

## Esimerkki {#example}

```scss title="scss/box.scss"
@use 'tokens' as t;

.box {
  padding: 1rem;
  border: 2px solid t.$accent;
}
```

```java title="ScssView.java"
@Route("/scss")
@BundleEntry("scss/box.scss")
public class ScssView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public ScssView() {
    Div box = new Div().addClassName("box");
    self.add(box);
  }
}
```

## Vaihtoehdot {#options}

SCSS-laajennus välittää vaihtoehtonsa [Dart Sass -kääntäjälle](https://sass-lang.com/documentation/js-api/interfaces/options/). Yleisimmät vaihtoehdot ovat latauspolku, joka sallii sisäänkäynnin `@use` käyttää osaa, joka sijaitsee sen omasta kansiosta ulkopuolella:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-scss': { loadPaths: ['styles'] }
};
```

Tuolla latauspolulla `@use 'tokens'` ratkaisee osan `src/main/frontend/styles` alla. Sass-vaihtoehtojen viittaus luettelee loput.
