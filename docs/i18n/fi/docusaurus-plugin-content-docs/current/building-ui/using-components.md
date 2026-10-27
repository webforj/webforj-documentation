---
sidebar_position: 3
title: Komponenttien käyttö
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
_i18n_hash: df0f3d5a956eda1abd755f646899a7cc
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Komponentit ovat webforJ-sovellusten rakennuspalikoita. Olipa kyseessä sisäänrakennetut komponentit kuten `Button` ja `TextField`, tai tiimisi tarjoamat mukautetut komponentit, vuorovaikutus niiden kanssa seuraa samaa johdonmukaista mallia: määrität ominaisuuksia, hallitset tilaa ja koostet komponentteja asetteluiksi.

Tämä opas keskittyy arkipäivän toimintoihin: ei niinkään komponenttien sisäisiin toimintatapoihin, vaan siihen, miten niitä käytetään käytännössä.

## Komponentin ominaisuudet {#component-properties}

Jokainen komponentti altistaa ominaisuuksia, jotka hallitsevat sen sisältöä, ulkoasua ja käyttäytymistä. Useimmilla näistä on omat, tyypitetyt Java-menetelmät (`setText()`, `setTheme()`, `setExpanse()`, jne.), jotka ovat pääasiallinen tapa, jolla määrität komponentteja webforJ:ssa. Alla olevat osiot käsittelevät ominaisuuksia ja menetelmiä, jotka soveltuvat laajasti komponenttityypeille.

### Teksti sisältö {#text-content}

`setText()`-menetelmä asettaa komponentin näkyvän tekstin kirjaimiksi, kuten `Button`-painikkeen titteliksi tai `Label`-tekstiksi. Syöttökomponenteille, kuten `TextField`, käytä `setValue()`-menetelmää asettaaksesi kentän nykyinen arvo.

```java
Button button = new Button();
button.setText("Klikkaa minua");

Label label = new Label();
label.setText("Tila: valmis");

TextField field = new TextField();
field.setValue("Alkuarvo");
```

`setText()`-menetelmällä kirjoitettu merkintä näkyy kirjaimina, eikä sitä koskaan suoritetta, mikä estää käyttäjäsyötteestä tai ulkoisista tiedoista tulevien tekstien tulkinnan eläväksi merkinnäksi.

```java
// Näytetään kirjaimina "<b>Tila: valmis</b>"
component.setText("<b>Tila: valmis</b>");
```

:::note Käyttäen `<html>`-tagia
WebforJ:n aikaisemmat versiot käsittelivät `<html>`-tagin sisällä olevaa arvoa, joka siirrettiin `setText()`-menetelmään, HTML:nä. Tämä käyttäytyminen on poistunut käytöstä ja se poistetaan webforJ:sta versiossa 27.00.

Ensimmäisen kerran, kun `<html>`-tagin sisällä oleva arvo saavuttaa `setText()`:n, lokiin kirjataan varoitus, joka nimeää komponentin ja kutsupaikan, jotta kutsu voidaan siirtää `setHtml()`-metodiin.

Ota webforJ 27.00 oletus käyttöön etukäteen asettamalla `webforj.legacyHtmlInText` arvoksi `false`. Spring-sovelluksessa sama arvo asetetaan `webforj.legacy-html-in-text`.
```java
// webforj.legacyHtmlInText = true (oletus)
component.setText("<html><b>Tila: valmis</b></html>"); // renderöi lihavoituna

// webforj.legacyHtmlInText = false
component.setText("<html><b>Tila: valmis</b></html>"); // näyttää merkit <b>Tila: valmis</b>
```
:::

### HTML:n renderöinti {#rendering-html}

Jotkut komponentit tukevat myös `setHtml()`-menetelmää, tapauksissa, joissa tarvitset sisäisten HTML-merkintöjen renderöintiä sisällössä:

```java
Div container = new Div();
container.setHtml("<strong>Lihavoitu teksti</strong> ja <em>kursivoitu teksti</em>");
```

:::danger Ristiin-sivustohäirintä (XSS)
Ennaltaehkäisevänä toimenpiteenä [ristiin-sivustohäirintä (XSS) hyökkäyksiltä](/docs/security/application-security/common-threats#cross-site-scripting-xss), käytä `setHtml()`-menetelmää vain sisällön kanssa, jota hallitset suoraan.
:::

### HTML-ominaisuudet {#html-attributes}

Suurin osa konfiguraatiosta webforJ:ssa tehdään tyypitetyillä Java-menetelmillä sen sijaan, että käytettäisiin raakaa HTML-ominaisuutta. Kuitenkin, `setAttribute()` on hyödyllinen kulkuväylä saavutettavuusominaisuuksille, joilla ei ole omistettua API:a:

```java
Button button = new Button("Lähetä");
button.setAttribute("aria-label", "Lähetä lomake");
button.setAttribute("aria-describedby", "lomake-vihje");
```

:::note Tarkista komponentin tuki
Kaikki komponentit eivät tue satunnaisia ominaisuuksia. Tämä riippuu taustalla olevan komponentti-implementoinnin tuesta.
:::

### Komponenttien tunnukset {#component-ids}

Voit määrittää tunnuksen komponentin HTML-elementille käyttämällä `setAttribute()`-menetelmää:

```java
Button submitButton = new Button("Lähetä");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("Sähköposti");
emailField.setAttribute("id", "email-input");
```

DOM-tunnuksia käytetään yleisesti testivalitsimina ja CSS- kohdistamisena tyylitiedostoissasi.

:::tip Suosi luokkia monikomponenttien kohdistamiseen
Eriävät CSS-luokat, tunnusten tulee olla ainutlaatuisia sovelluksessasi. Jos tarvitset kohdistaa useita komponentteja, käytä `addClassName()`-menetelmää sen sijaan.
:::

:::info Kehyksen hallinnoimat tunnukset
webforJ määrittää myös automaattisia tunnuksia komponentteihin sisäisesti. Palvelinpuolen tunnusta (johon pääsee `getComponentId()` kautta) käytetään kehyksen seurannassa, kun taas asiakaspuolen tunnusta (johon pääsee `getClientComponentId()` kautta) käytetään asiakas-palvelin viestinnässä. Nämä ovat erillisiä DOM `id` -ominaisuudesta, jonka asetat `setAttribute()`-menetelmällä.
:::

### Tyylit {#styling}

Kolme menetelmää kattaa suurimman osan tyylitarpeista: `setStyle()` yksittäisten CSS-ominaisuusarvojen asettamiseen, sekä `addClassName()` ja `removeClassName()` CSS-luokkien lisäämiseen tai poistamiseen, joita on määritelty tyylitiedostoissasi. Käytä `setStyle()` pienille tai kertaluonteisille tyylitarkistuksille ja käytä CSS-luokkia laajempien tai uudelleenkäytettävien tyylien soveltamiseen.

```java
Div container = new Div();
container.setStyle("padding", "20px");

if (isHighPriority) {
    container.setStyle("border-left", "4px solid red");
}

Button button = new Button("Vaihda");
button.addClassName("primary", "large");

if (isLoading) {
    button.addClassName("loading");
}
```

## Komponentin tila {#component-state}

Sisällön ja ulkoasun lisäksi komponenteilla on tilaan liittyviä ominaisuuksia, jotka määrittävät, ovatko ne näkyviä ja vastaavatko ne käyttäjävuorovaikutukseen. Kaksi yleisimmin käytettyä ovat `setVisible()` ja `setEnabled()`.

`setVisible()` hallitsee, onko komponenttia lainkaan renderöity käyttöliittymässä. `setEnabled()` hallitsee, hyväksyykö se syötteen tai vuorovaikutuksen pysyessään näkyvänä. Useimmissa tapauksissa on suositeltavampaa estää komponentti kuin piilottaa se: estetty painike viestii yhä siitä, että toiminto on olemassa, mutta ei ole vielä saatavilla, mikä on vähemmän hämmentävää kuin sen ilmoittaminen ja siirtäminen.

```java
// Näytä lisäkenttä, kun valintaruutu on valittu
TextField advancedField = new TextField("Lisäasetukset");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Näytä lisäasetukset");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Ota painike käyttöön vain, kun vaaditun kentän arvo on voimassa
Button submitButton = new Button("Lähetä");
submitButton.setEnabled(false);

TextField nameField = new TextField("Nimi");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

:::warning Estetty ja piilotettu eivät ole turvallisia
`setVisible(false)` ja `setEnabled(false)` vaikuttavat vain käyttöliittymään. Ne eivät estä määrätietoista käyttäjää suorittamasta taustalla olevaa toimintoa selaimen tai muokatun pyynnön kautta, joten älä koskaan luota niihin suojellaksesi herkkiä toimintoja. Pakollinen pääsynhallinta on aina toteutettava palvelimella. Katso lisätietoja [Estetty ja piilotettu eivät ole turvallisia](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security).
:::

Seuraava kirjautumislomake esittää käytännössä `setEnabled()`:n. Kirjautumispainike pysyy estettynä, kunnes molemmat kentät sisältävät tietoja, mikä tekee käyttäjälle selväksi, että syöte on vaadittu ennen etenemistä:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
]}
height='450px'
/>

## Työskentely säiliöiden kanssa {#working-with-containers}

WebforJ:ssa asettelu toteutetaan säiliöiden kautta, jotka ovat komponentteja, jotka sisältävät muita komponentteja ja hallitsevat niiden järjestämistä. Et aseta lapsikomponentteja manuaalisesti; sen sijaan lisäät ne säiliöön ja määrität sen asetteluominaisuudet.

### Komponenttien lisääminen {#adding-components}

Kaikilla säiliöillä on `add()`-menetelmä. Voit siirtää komponentteja yksi kerrallaan tai kaikki kerralla:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Klikkaa minua"));

TextField nameField = new TextField("Nimi");
TextField emailField = new TextField("Sähköposti");
Button submitButton = new Button("Lähetä");

container.add(nameField, emailField, submitButton);
```

### Asettelu vaihtoehdot {#layout-options}

`FlexLayout` on pääasiallinen asettelusäiliö webforJ:ssa ja kattaa suurimman osan käyttötilanteista: rivit, sarakkeet, kohdistaminen, väli ja kelaus. Monimutkaisemmilla järjestelyillä, kuten CSS Grid tai mukautetulla kohdistamisella, voit käyttää CSS:ää suoraan `setStyle()` tai `addClassName()` -menetelmiä mille tahansa säiliökomponentille. Katso [FlexLayout](/docs/components/flex-layout) -dokumentaatio täydellistä asetteluvalikoimaa varten.

### Osioiden näyttäminen ja piilottaminen {#showing-hiding-sections}

Yleinen käyttötapa `setVisible()`-menetelmälle säiliöissä on paljastaa lisäkäyttöliittymä vain, kun se on relevanttia. Tämä pitää käyttöliittymän keskittyneenä ja vähentää visuaalista hälinää. Sen sijaan, että siirtyisit uuteen näkymään, voit näyttää osion nykyisestä asettelusta suoraan käyttäjäsyötteeseen.

Seuraava asetuspaneeli havainnollistaa tätä: perusilmoitusasetukset ovat aina näkyvissä, ja osio lisäasetuksista ilmestyy vain, kun käyttäjä pyytää sitä. Tallenna-painike aktivoituu heti, kun asetusta muutetaan:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
]}
height='450px'
/>

### Säiliöhallinta {#container-management}

Käytä `remove()` ja `removeAll()` poistaaksesi komponentteja säiliöstä ajonaikana:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Väliaikainen");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Tämä on hyödyllistä, kun tarvitset kokonaan vaihtaa sisältöä, esimerkiksi vaihtamalla latausindikaattorin ladattuihin tietoihin.

## Lomakevalidointi {#form-validation}

Useiden komponenttien yhdistäminen lähetyksen estämiseksi on yleinen malli webforJ-käyttöliittymissä. Perusidea on, että jokainen syöttökenttä rekisteröi kuuntelijan, ja aina kun arvo muuttuu, lomake arvioi uudelleen, täyttyvätkö kaikki kriteerit, ja päivittää lähetyspainikkeen sen mukaisesti.

Alla oleva esimerkki yhdistää tämän manuaalisesti, jotta voit nähdä, kuinka komponentin tila ja tapahtumakuuntelijat toimivat yhdessä. Tämä ei ole suositeltu lähestymistapa oikeille lomakkeille: manuaalinen kuuntelijalogiikka on vaikeaa ylläpitää lomakkeiden kasvaessa, eikä se yhdistä komponentteja taustalla olevaan tietomalliin.

:::tip Käytä datan sitomista lomakevalidointiin
Tuotantolomakkeissa käytä [datan sitomista](/docs/data-binding/overview). Se kattaa validoinnin, kaksisuuntaisen synkronoinnin komponenttien ja mallisi välillä, sekä arvon muuntamisen `BindingContext`in kautta. Manuaalista mallia on esitelty vain havainnollistamiseksi.
:::

Tässä yhteystiedotlomakkeessa nimen kenttä ei saa olla tyhjää, sähköpostiosoitteen on sisällettävä `@`-merkki, ja viestin on oltava vähintään 10 merkkiä pitkä:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
]}
height='500px'
/>

## Dynaamiset sisältöpäivitykset {#dynamic-content-updates}

Komponentit eivät välttämättä pysy kiinteässä tilassa niiden luomisen jälkeen. Voit päivittää tekstiä, vaihtaa CSS-luokkia ja vaihtaa käyttötilan mihin tahansa aikaan sovellustapahtumien seurauksena. Yleinen esimerkki on palautteen antaminen pitkään kestävän tehtävän aikana:

```java
Label statusLabel = new Label("Valmis");
Button startButton = new Button("Aloita prosessi");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("Käsitellään...");
    statusLabel.addClassName("processing");

    performTask(() -> {
        statusLabel.setText("Valmis");
        statusLabel.removeClassName("processing");
        statusLabel.addClassName("success");
        startButton.setEnabled(true);
    });
});
```

Painikkeen estäminen, kun tehtävä suoritetaan, estää kaksoislähetykset, ja merkin päivittäminen pitää käyttäjän ajan tasalla siitä, mitä tapahtuu.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

`ComponentLifecycleObserver`-rajapinta mahdollistaa komponentin elinkaaritapahtumien havainnoimisen komponentin itsensä ulkopuolelta. Tämä on hyödyllistä, kun sinun on reagoitava komponentin luomiseen tai tuhoamiseen ilman, että muokkaat sen toteutusta. Esimerkiksi saatat käyttää sitä ylläpitämään aktiivisten komponenttien rekisteriä tai vapauttamaan ulkoisia resursseja, kun komponentti poistetaan.

### Peruskäyttö {#basic-usage}

Kutsu `addLifecycleObserver()`-menetelmää mille tahansa komponentille rekisteröidäksesi palautekutsun. Palautekutsu vastaanottaa komponentin ja elinkaaritapahtuman:

```java
Button button = new Button("Tässä");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("Painike luotiin");
            break;
        case DESTROY:
            System.out.println("Painike tuhoutui");
            break;
    }
});
```

### Malli: Resurssirekisteri {#pattern-resource-registry}

DESTROY-tapahtuma on erityisen hyödyllinen pitää rekisteri automaattisesti synkronoituna. Sen sijaan, että poistat komponentteja manuaalisesti, kun niitä ei enää tarvita, annat komponentin ilmoittaa rekisterille itselleen:

```java
public class ResourceRegistry {
    private final Map<String, Component> activeComponents = new ConcurrentHashMap<>();

    public void track(Component component, String name) {
        activeComponents.put(name, component);

        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                activeComponents.remove(name);
            }
        });
    }
}
```

### Malli: Komponenttikoordinaatio {#pattern-component-coordination}

Koordinaattoriluokka, joka hallinnoi joukkoa liittyviä komponentteja, voi käyttää samaa lähestymistapaa pitää sisäisen luettelonsa tarkan:

```java
public class FormCoordinator {
    private final List<DwcComponent<?>> managedComponents = new ArrayList<>();

    public void manage(DwcComponent<?> component) {
        managedComponents.add(component);

        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                managedComponents.remove(comp);
            }
        });
    }

    public void disableAll() {
        managedComponents.forEach(c -> c.setEnabled(false));
    }
}
```

### Milloin käyttää {#when-to-use}

Käytä `ComponentLifecycleObserver`:
- Komponenttirekisterien rakentamiseen
- Lokalisointiin tai seurantaan
- Useiden komponenttien koordinoimiseen
- Ulkoisten resurssien puhdistamiseen

Koodin suorittamiseksi komponentin liittämisen jälkeen DOM:iin, katso `whenAttached()` [Komponenttien koostaminen](/docs/building-ui/composing-components) -oppaasta.

## Käyttäjätiedot {#user-data}

Komponentit voivat kantaa satunnaisia palvelinpuolen tietoja `setUserData()` ja `getUserData()` -menetelmien kautta. Molemmat menetelmät ottavat avaimen datan tunnistamiseksi. Tämä on hyödyllistä, kun sinun on yhdistettävä domaineja tai konteksteja komponenttiin ilman erillisen hakurakenteen hallintaa.

```java
Button button = new Button("Käsittele");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Koska käyttäjätiedot eivät koskaan siirry asiakkaalle, voit turvallisesti tallentaa arkaluonteisia tietoja tai suuria objekteja ilman verkon liikenteen vaikuttamista.
