---
sidebar_position: 1
title: Turvallisuusarkkitehtuuri
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: df2f795c6b65edc60adb39b549cb780b
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Turvallisuusarkkitehtuuri <DocChip chip='since' label='25.10' />

webforJ:n turvallisuusjärjestelmä perustuu rajapintojen ja suunnittelumallien perusteelle, jotka mahdollistavat joustavan ja laajennettavan reitin suojaamisen. Tässä osiossa selitetään, kuinka perustavanlaatuinen turvallisuuskehys toimii ja kuinka voit rakentaa räätälöityjä turvallisuusratkaisuja toteuttamalla näitä rajapintoja.

:::tip[Integrointi Springin kanssa]
Suurimman osan sovelluksista tulisi käyttää [Spring Security -integraatiota](/docs/security/getting-started), koska se konfiguroi kaiken tämän automaattisesti puolestasi. Implementoi räätälöity turvallisuus vain, jos sinulla on erityisiä vaatimuksia tai et käytä Spring Bootia. Spring-integraatio perustuu tähän samaan perustavanlaatuiseen arkkitehtuuriin.
:::

Opit ydinarajapinnat, evaluatoriketjun mallin, kuinka navigointi poistetaan käytöstä ja arvioidaan, sekä erilaisten lähestymistapojen tallentamiseen liittyvän autentikointitilan.

:::info[Keskittyminen arkkitehtuuriin ja laajennuskohtiin]
Nämä oppaat selittävät perustavanlaatuisen arkkitehtuurin ja laajennuskohtia, toteuttamasi rajapinnat ja kuinka ne toimivat yhdessä. Koodiesimerkit näyttävät **yhden mahdollisen lähestymistavan**, eivätkä ne ole määrättyjä vaatimuksia. Toteutuksesi voi käyttää erilaisia tallennusmekanismeja (JWT, tietokanta, LDAP), erilaisia kytkentämalleja tai erilaisia autentikointivirtoja tarpeidesi mukaan.
:::

## Mitä opit {#what-youll-learn}

- **Perustavanlaatuinen arkkitehtuuri**: Ydinarajapinnat, jotka määrittävät turvallisuuskäyttäytymisen ja kuinka ne toimivat yhdessä
- **Navigoinnin poistaminen käytöstä**: Kuinka turvallisuusjärjestelmä keskeyttää navigointipyyntöjä ja arvioi käyttöoikeussäännöt
- **Evaluatoriketjun malli**: Kuinka turvallisuussäännöt arvioidaan prioriteettijärjestyksessä vastuuketjumallin avulla
- **Autentikoinnin tallennus**: Eri lähestymistavat käyttäjäautentikointitilan tallentamiseen (istunnot, JWT, tietokanta jne.)
- **Täydellinen toteutus**: Toimiva esimerkki, jossa kaikki komponentit on kytketty yhteen

## Kenelle tämä on tarkoitettu {#who-this-is-for}

Nämä oppaat on tarkoitettu kehittäjille, jotka haluavat:

- Rakentaa mukautettuja turvallisuusratkaisuja ei-Spring-sovelluksille
- Ymmärtää perustavanlaatuinen arkkitehtuuri ongelmien ratkaisemiseksi
- Toteuttaa mukautettuja autentikointivirtoja tai valtuutuksen logiikkaa
- Luoda turvallisuusevaluattoreita, joilla on alan erityisiä logiikoita
- Integroitu olemassa oleviin autentikointijärjestelmiin (LDAP, OAuth, mukautetut taustat)

## Esivaatimukset {#prerequisites}

Ennen kuin sukellat näihin oppaisiin, sinun tulisi:

- Suorittaa [Aloitusopas](/docs/security/getting-started) ymmärtääksesi turvallisuuskäsitteet
- Ymmärtää turvallisuusannotat Alueoppaasta](/docs/security/annotations)
- Tuntemus vastuuketjumallista
- Olla kokemusta Java-rajapinnoista ja perinnöstä

## Aiheet {#topics}

<DocCardList className="topics-section" />
