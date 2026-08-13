---
title: Tailwind
sidebar_position: 60
sidebar_class_name: new-content
description: >-
  Turn on the webforj-tailwind extension, apply utility classes from a view, and
  understand how it generates and scans its own stylesheet.
_i18n_hash: f588624ebd738977bb8be4e9887141d1
---
[Tailwind CSS](https://tailwindcss.com/) on hyödyke-perusteinen CSS-kehys, jonka luokkien nimet vastaavat pieniä joukkoja CSS-deklaraatioita. Se on ainoa kuratoitu laajennus, joka toimitetaan mukana, koska suurin osa projekteista ei käytä sitä. Aktivoit sen id:llä, samalla tavalla kuin kytket päälle minkä tahansa laajennuksen, katso [Enabled by id](/docs/managing-resources/bundler/extensions/overview#enabled-by-id). Kun se on päällä, se tekee jotain, mitä muut eivät tee: se generoi oman sisääntulonsa.

## How it works {#how-it-works}

Sen sijaan, että koottaisiin tiedosto, jonka olet kirjoittanut, Tailwind-laajennus skannaa sovelluksesi lähteitä niiden käyttämien hyödyke-luokkien nimien löytämiseksi, generoi tyylisivun, joka sisältää vain nuo hyödykkeet, ja lataa sen joka näkymälle. Näkymä soveltaa sitten hyödykkeitä luokkien niminä ilman mitään tuontia:

```java title="TailwindView.java"
@Route("/tailwind")
public class TailwindView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public TailwindView() {
    Div card = new Div("Styled by compiled Tailwind utilities");
    card.addClassName("flex", "items-center", "gap-4", "p-8", "rounded-lg",
        "bg-blue-500", "text-white");
    self.add(card);
  }
}
```

Generoitu tyylisivu tuo Tailwindin teeman ja hyödykkeet, mutta ei sen perusasetusta. Perusasetuksen, Tailwindin esilennon, tyyli kaikille paljaalle elementille sivulla, mikä ohittaa sen tyylin, jonka webforJ jo soveltaa komponentteihinsa. Jättämällä sen pois, hyödyklasseja, jotka lisäät, toimivat ilman, että häiritset komponentteja, joita et ole muuttanut.

Koska hyödykkeet tulevat niiden luokkien nimistä, joita näkymäsi käyttävät, [frontend watch](/docs/configuration/deploy-reload/frontend-watch) seuraa sovelluksesi lähteitä sekä `src/main/frontend`. Lisää tai poista hyödykelas näkymässä ja tallenna, niin tyylisivu regeneroituu ja korjataan sivulle paikoilleen, samoin kuin muokkaamalla kirjoittamaasi tyylisivua.

## Where utility classes reach {#where-utility-classes-reach}

:::warning Hyödyklassi tyylittää elementin, ei komponentin sisäosaa
webforJ-komponentit renderoidaan varjosovelluksen (shadow DOM) avulla, joka pitää sisäisen rakenteensa yksityisenä. Komponenttiin lisätty hyödyklassi tyylittää vain sen ulkokuoren, sen välistä, kokoa ja paikkaa asettelussa, eikä koskaan saavuta sen sisällä piirrettäviä elementtejä. Hyödykkeet soveltuvat kuten niiden luokkien nimet lukevat asettelu-kontaineriin tai tavalliseen `Div`:iin, jonka rakennat, missä ei ole raja, mutta eivät rakennettujen komponenttien sisäosiin.

Tyylittääksesi komponentin sisäosat, käytä sen exponoimaa tyylitystä sen sijaan: [shadow parts](/docs/styling/shadow-parts) `::part()`-kautta ja komponentin [CSS mukautettuja ominaisuuksia](/docs/styling/css-variables), jotka molemmat löytyvät kunkin komponentin tyyliviitteestä. Pidä hyödykkeet asettelua varten ja omia elementtejäs varten, ja käytä komponentin omaa tyylitystä komponentin tarkentamiseksi.
:::

Tyylisivu sisältää hyödykaluokkien nimet, jotka skannaus löytää lähteistäsi, ja vain ne. Luokka, jonka kirjoitat selaimen tarkastajaan kokeillaksesi ideaa, ei sovellu, koska sitä ei koskaan koottu. Laita luokka näkymään ja tallenna, niin watch regeneroi tyylisivun sen kanssa.

Kun sama hyödykkeiden ryhmä toistuu monilla näkymillä, nimeä se: määrittele CSS-luokka kerran ja lisää se sen sijaan. Muutama inline-hyödyke pysyy luettavana, mutta pitkä merkkijono, joka toistuu käsin, voi hämärtyä muokatessasi.

## Options {#options}

Tailwind-laajennus ei ota vastaan asetuksia `bun.config.ts` -tiedostosta. Se generoi ja skannaa oman tyylisivunsa, ja Tailwind itsessään on konfiguroitu tuon tyylisivun kautta sen sijaan, että se olisi konfiguroitu laajennuksen kautta.
