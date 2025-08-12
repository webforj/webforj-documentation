---
title: Events and updates
sidebar_position: 5
_i18n_hash: b2973e75abc879992ab1e235ba5d8b5e
---
<!-- vale off -->
# Tapahtumat ja päivitykset <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-tapahtumat antavat sinun reagoida tietomuutoksiin. Automaattisten käyttöliittymäpäivitysten ohella voit kuunnella muutoksia ja laukaista mukautettua logiikkaa.

## `Repository`-tapahtuman elinkaari {#repository-event-lifecycle}

Jokainen `commit()` kutsu laukaisee <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>. Tämä tapahtuma kuljettaa tietoa siitä, mitä on muuttunut:

```java
repository.onCommit(event -> {
    // Hanki kaikki kommitoidut entiteetit
    List<Customer> commits = event.getCommits();
    
    // Tarkista onko kyseessä yksittäisen entiteetin päivitys
    if (event.isSingleCommit()) {
        Customer updated = event.getFirstCommit();
        System.out.println("Päivitetty: " + updated.getName());
    }
});
```

Tapahtuma kertoo sinulle:
- `getCommits()` - Lista entiteeteistä, jotka on kommitoitu
- `isSingleCommit()` - Olisiko tämä ollut kohdennettu yksittäisen entiteetin päivitys
- `getFirstCommit()` - Kätevyyden menetelmä, jolla saat ensimmäisen (tai ainoan) entiteetin

Kutsuttaessa `commit()` ilman parametreja, tapahtuma sisältää kaikki entiteetit, jotka tällä hetkellä ovat repositoriolla suodattamisen jälkeen.

## Päivitysstrategiat {#update-strategies}

Kaksi kommitin allekirjoitusta palvelevat eri tarkoituksia:

```java
// Yksittäisen entiteetin päivitys - tehokas yksittäisille muutoksille
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// Kappaleittain päivitys - tehokas useille muutoksille
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

Yksittäiset kommitit ovat kirurgisia - ne kertovat kytkettyille komponenteille tarkalleen, mikä rivi on muuttunut. [`Table`](../../components/table/overview) voi päivittää vain kyseisen rivin solut koskematta muihin.

Kappaleittaiset kommitit päivittävät kaiken. Käytä niitä kun:
- Useita entiteettejä on muuttunut
- Olet lisännyt tai poistanut kohteita 
- Et ole varma, mitä on muuttunut

## Reaktiiviset käyttöliittymäkuviot {#reactive-ui-patterns}

`Repository`-tapahtumat antavat sinun ylläpitää yhteenvedonäyttöjä synkronoituna tietojesi kanssa:

```java
// Itse päivittävät tarrat
repository.onCommit(event -> {
    double total = sales.stream().mapToDouble(Sale::getAmount).sum();
    totalLabel.setText(String.format("Yhteensä: $%.2f", total));
    countLabel.setText("Myynnit: " + sales.size());
});

// Suorien tulosten laskenta
repository.onCommit(e -> {
    long count = repository.findAll().count();
    resultsLabel.setText(count + " tuotetta löytyi");
});
```

Nämä kuuntelijat laukaistaan jokaisella kommitilla, olipa se käyttäjän toimista, tietojen tuonnista tai ohjelmallisista päivityksistä. Tapahtuma antaa sinulle pääsyn kommitoituihin entiteetteihin, mutta usein lasket uudelleen lähdeyhdistelmästä ottaaksesi mukaan kaikki nykyiset tiedot.

## Muistin hallinta {#memory-management}

Tapahtumakuuntelijat pitävät viittauksia komponentteihisi. Jos et poista niitä, `Repository` pitää komponenttisi muistissa, vaikka niitä ei enää esitetä:

```java
// Pidä viittaus poistaaksesi myöhemmin
ListenerRegistration<RepositoryCommitEvent<Data>> registration = 
    repository.onCommit(event -> {
        updateDisplay(event.getCommits());
    });

// Siivoa kuuntelija, kun komponentti tuhotaan
if (registration != null) {
    registration.remove();
}
```

`onCommit()`-metodi palauttaa `ListenerRegistration`-objektin. Tallenna tämä viittaus ja kutsu `remove()`, kun komponenttisi tuhotaan tai ei enää tarvitse päivityksiä. Tämä estää muistivuotoja pitkäaikaisissa sovelluksissa.
