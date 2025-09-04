---
sidebar_position: 4
title: Context Results
_i18n_hash: f7eeb60ff21b1d5dff27b17cc82cdf50
---
Kun kirjoitat dataa käyttöliittymästä malliin, `BindingContext`-luokan `write`-metodi laukaisee validoinnit. Validointitulokset määrittävät, onko data hyväksyttävää.

## Validointitulosten käsittely {#processing-validation-results}

Voit käsitellä validointituloksia antaaksesi palautetta käyttäjälle. Jos validointi epäonnistuu, voit estää datan päivityksen mallissa ja näyttää virheilmoituksia, jotka liittyvät jokaiseen epäonnistuneeseen validointiin.

```java
ValidationResult result = context.write(hero);
if (!result.isValid()) {
    displayErrors(result.getMessages());
} else {
    proceedWithUpdate();
}
```

<!-- vale off -->
## Konteksti validointitila {#context-validation-state}
<!-- vale on -->

Aina kun konteksti validoi komponentteja, se laukaisee `BindingContextValidateEvent`-tapahtuman. Tämä tapahtuma toimittaa `ValidationResult`-tiedot kaikista sidoksista, jotka ovat muuttuneet samanaikaisesti. Voit käyttää näitä tuloksia toimintojen laukaisemiseen ja vastata asianmukaisesti, esimerkiksi mahdollistamalla tai estämällä lähetysnappulan aktivoinnin lomakkeen kokonaisvalidaation perusteella.

```java
BindingContext<User> context = new BindingContext<>(User.class);

// Kuuntele BindingContextValidateEvent-tapahtumaa, joka laukaistaan jokaisen käyttäjävuorovaikutuksen jälkeen.
context.addValidateListener(event -> {
    submit.setEnabled(event.isValid());
});
```

## Automaattinen fokus rikkomus {#auto-focus-violation}

Kun käsitellään lomakkeita, jotka vaativat validoitavaksi useita kenttiä, ensimmäisen virheellisen kentän automaattinen fokusoiminen voi merkittävästi parantaa käyttäjäkokemusta. Tämä ominaisuus auttaa käyttäjiä tunnistamaan ja korjaamaan virheitä välittömästi, nopeuttaen lomakkeen täyttämisprosessia.

`BindingContext` yksinkertaistaa prosessia automaattisen fokuksen asettamisessa ensimmäiselle komponentille, jossa on validointivirhe. Käyttämällä `setAutoFocusFirstViolation`-metodia, voit aktivoida tämän ominaisuuden minimoidulla koodilla, varmistaen, että käyttöliittymästä tulee intuitiivisempi ja reagoivampi syöttövirheisiin.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoFocusFirstViolation(true);
```

:::info Fokukselle herkkä
Tämä ominaisuus toimii vain komponenttien kohdalla, jotka toteuttavat `FocusAcceptorAware`-huolen.
:::
