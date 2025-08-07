---
sidebar_position: 4
title: Context Results
_i18n_hash: 15fc4551d1ed2f2b5e35785975e66946
---
Kun kirjoitat tietoja käyttöliittymästä malliin, `write`-metodi `BindingContext`-luokassa aktivoi validoinnit. Validoinnin tulokset määrittävät, onko tieto hyväksyttävää.

## Validointitulosten käsittely {#processing-validation-results}

Voit käsitellä validointituloksia antaaksesi palautetta käyttäjälle. Jos validointi epäonnistuu, voit estää tietojen päivittämisen mallissa ja näyttää virheilmoituksia, jotka liittyvät kuhunkin epäonnistuneeseen validointiin.

```java
ValidationResult result = context.write(hero);
if (!result.isValid()) {
    displayErrors(result.getMessages());
} else {
    proceedWithUpdate();
}
```

<!-- vale off -->
## Konteksti-validointitila {#context-validation-state}
<!-- vale on -->

Aina kun konteksti validoi komponentteja, se laukaisee `BindingContextValidateEvent`-tapahtuman. Tämä tapahtuma toimittaa `ValidationResult`:n kaikille sitoumuksille, jotka ovat muuttuneet samanaikaisesti. Voit käyttää näitä tuloksia laukaistaksesi toimintoja ja vastataksesi asianmukaisesti, esimerkiksi mahdollistamalla tai estämällä lähetysnapin käytön koko lomakkeen pätevyyden mukaan.

```java
BindingContext<User> context = new BindingContext<>(User.class);

// Kuuntele BindingContextValidateEvent:ia, joka laukaistaan jokaisen käyttäjän vuorovaikutuksen yhteydessä.
context.addValidateListener(event -> {
    submit.setEnabled(event.isValid());
});
```

## Automaattinen fokusoimisen rikkominen {#auto-focus-violation}

Kun käsitellään lomakkeita, jotka vaativat validointia useissa kentissä, automaattinen siirtyminen ensimmäiseen virheelliseen kenttään voi merkittävästi parantaa käyttäjäkokemusta. Tämä ominaisuus auttaa käyttäjiä tunnistamaan ja korjaamaan virheitä heti, mikä tekee lomakkeen täyttämisestä sujuvampaa.

`BindingContext` yksinkertaistaa prosessia, jolla asetetaan automaattinen fokus ensimmäiseen komponenttiin, jolla on validointivirhe. Käyttämällä `setAutoFocusFirstViolation`-metodia voit ottaa tämän ominaisuuden käyttöön vähällä koodilla, varmistaen, että käyttöliittymästä tulee intuitiivisempi ja reagoivampi syöttövirheisiin.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoFocusFirstViolation(true);
```

:::info Fokukseen liittyvä
Tämä ominaisuus toimii vain niille komponenteille, jotka toteuttavat `FocusAcceptorAware`-huolen. 
:::
