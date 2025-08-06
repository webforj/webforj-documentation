---
title: Events and updates
sidebar_position: 5
_i18n_hash: 45321f5a931bc6efb56376b53662be32
---
<!-- vale off -->
# Tapahtumat ja päivitykset <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-tapahtumat antavat sinun reagoida tietomuutoksiin. Automatisoitujen käyttöliittymäpäivitysten lisäksi voit kuunnella muutoksia käynnistääksesi mukautettua logiikkaa.

## `Repository`-tapahtuma elinkaari {#repository-event-lifecycle}

Jokainen `commit()`-kutsu laukaisee <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>-tapahtuman. Tämä tapahtuma sisältää tietoa siitä, mitä on muuttunut:

```java
repository.onCommit(event -> {
    // Hanki kaikki vahvistetut entiteetit
    List<Customer> commits = event.getCommits();
    
    // Tarkista onko kyseessä yksittäisen entiteetin päivitys
    if (event.isSingleCommit()) {
        Customer updated = event.getFirstCommit();
        System.out.println("Päivitetty: " + updated.getName());
    }
});
```

Tapahtuma kertoo sinulle:
- `getCommits()` - Lista entiteeteistä, jotka on vahvistettu
- `isSingleCommit()` - Olisiko tämä kohdennettu yksittäisen entiteetin päivitys
- `getFirstCommit()` - Kätevyyden menetelmä ensimmäisen (tai ainoan) entiteetin saamiseksi

`commit()`-kutsussa ilman parametreja tapahtuma sisältää kaikki entiteetit, jotka ovat tällä hetkellä varastossa suodattamisen jälkeen.

## Päivitysstrategiat {#update-strategies}

Kaksi vahvistusallekirjoitusta palvelevat eri tarkoituksia:

```java
// Yksittäisen entiteetin päivitys - tehokas yksittäisille muutoksille
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// Massapäivitys - tehokas useille muutoksille
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

Yksittäiset vahvistukset ovat kirurgisia - ne kertovat liitetyille komponenteille tarkalleen, mikä rivi muuttui. [`Table`](../../components/table/overview) voi päivittää vain kyseisen rivin solut koskematta muihin.

Massavahvistukset päivittävät kaiken. Käytä niitä, kun:
- Useita entiteettejä on muuttunut
- Olet lisännyt tai poistanut kohteita
- Et ole varma, mitä on muuttunut

## Reaktiiviset käyttöliittymäkuviot {#reactive-ui-patterns}

`Repository`-tapahtumat antavat sinun pitää yhteenvedot näytöissä ajan tasalla tietojesi kanssa:

```java
// Automaattisesti päivittyvät otsikot
repository.onCommit(event -> {
    double total = sales.stream().mapToDouble(Sale::getAmount).sum();
    totalLabel.setText(String.format("Yhteensä: $%.2f", total));
    countLabel.setText("Myynnit: " + sales.size());
});

// Reaaliaikaiset tulosmäärät
repository.onCommit(e -> {
    long count = repository.findAll().count();
    resultsLabel.setText(count + " tuotetta löydetty");
});
```

Nämä kuuntelijat laukeavat jokaisella vahvistuksella, olipa kyseessä käyttäjän toimet, tietojen tuonti tai ohjelmalliset päivitykset. Tapahtuma antaa sinulle pääsyn vahvistettuihin entiteetteihin, mutta usein laskentasi tulee lähdejoukostasi mukana olemaan kaikki nykyiset tiedot.

## Muistinhallinta {#memory-management}

Tapahtumakuuntelijat pitävät viittauksia komponentteihisi. Jos et poista niitä, `Repository` pitää komponenttisi muistissa, vaikka ne eivät enää olisikaan näkyvissä:

```java
// Pidä viittaus, jotta voit poistaa sen myöhemmin
ListenerRegistration<RepositoryCommitEvent<Data>> registration = 
    repository.onCommit(event -> {
        updateDisplay(event.getCommits());
    });

// Siivoa kuuntelija komponentin tuhoutuessa
if (registration != null) {
    registration.remove();
}
```

`onCommit()`-menetelmä palauttaa `ListenerRegistration`-objektin. Tallenna tämä viittaus ja kutsu `remove()` kun komponenttisi tuhoutuu tai ei enää tarvitse päivityksiä. Tämä estää muistivuodot pitkäkestoisissa sovelluksissa.
