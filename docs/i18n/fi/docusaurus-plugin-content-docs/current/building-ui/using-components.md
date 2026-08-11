---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: 046749107d0e78ccfaab4017d4e374d1
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Komponentit ovat webforJ-sovellusten rakennuspalikoita. Käytitpä sisäänrakennettuja komponentteja, kuten `Button` ja `TextField`, tai työsi mukanaan tuomia räätälöityjä komponentteja, vuorovaikutus niiden kanssa noudattaa aina samaa johdonmukaista mallia: määrität ominaisuuksia, hallitset tilaa ja koostet komponentteja asetteluiksi.

Tämä opas keskittyy päivittäisiin toimintoihin: ei komponenttien sisäiseen toimintaan, vaan siihen, miten asioita tehdään niiden kanssa käytännössä.

## Component properties {#component-properties}

Jokainen komponentti altistaa ominaisuuksia, jotka säätelevät sen sisältöä, ulkoasua ja käyttäytymistä. Useimmilla näistä on omat, tyypitetyt Java-metodinsa (`setText()`, `setTheme()`, `setExpanse()` ja niin edelleen), jotka ovat ensisijainen tapa, jolla määrität komponentteja webforJ:ssa. Alla olevat osiot kattavat ominaisuudet ja metodit, jotka koskevat laajalti komponenttityyppejä.

### Text content {#text-content}

`setText()`-metodi asettaa komponentin näkyvän tekstin kirjaimellisina merkkeinä, kuten `Button`:n otsikkona tai `Label`:n sisältönä. Syötekomponenteille, kuten `TextField`, käytä sen sijaan `setValue()`-metodia kentän nykyisen arvon asettamiseen.

```java
Button button = new Button();
button.setText("Napsauta minua");

Label label = new Label();
label.setText("Tila: valmis");

TextField field = new TextField();
field.setValue("Alkuarvo");
```

`setText()`-metodilla kirjoitettu markup näkyy näinä merkkeinä eikä sitä koskaan suoriteta, mikä estää käyttäjän syötteestä tai ulkoisista tiedoista tulevaa tekstiä tulkitsemasta eläväksi markupiksi.

```java
// Näkyy kirjaimellisesti merkit "<b>Tila: valmis</b>"
component.setText("<b>Tila: valmis</b>");
```

:::note Käyttäen `<html>`-tagia
Aikaisemmat webforJ-version käsittelivät `<html>`-tunnisteeseen käärittyjä arvoja ja siirrettyjä `setText()`-metodiin HTML:nä. Tämä käyttäytyminen on poistettu käytöstä ja se poistetaan webforJ 27.00:ssa.

Ensimmäisen kerran, kun `<html>`-kääritty arvo saavuttaa `setText()`, lokitetaan varoitus, joka nimeää komponentin ja kutsun sijainnin, jotta kutsu voidaan siirtää `setHtml()`-metodiin.

Ota webforJ 27.00 oletus ennakkoon käyttöön asettamalla `webforj.legacyHtmlInText` arvoksi `false`. Spring-sovelluksessa sama arvo asetetaan `webforj.legacy-html-in-text` kautta.

```java
// webforj.legacyHtmlInText = true (oletus)
component.setText("<html><b>Tila: valmis</b></html>"); // renderöi lihavoituna

// webforj.legacyHtmlInText = false
component.setText("<html><b>Tila: valmis</b></html>"); // näyttää merkit <b>Tila: valmis</b>
```
:::

### Rendering HTML {#rendering-html}

Jotkut komponentit tukevat myös `setHtml()`-metodia, kun tarvitset inline HTML-markupin renderöimistä sisällössä:

```java
Div container = new Div();
container.setHtml("<strong>Liivateksti</strong> ja <em>kursiiviteksti</em>");
```

:::danger Ristiverkko-skriptingi (XSS)
Varotoimenpiteenä [ristiverkko-skriptingi (XSS) hyökkäyksiä](/docs/security/application-security/common-threats#cross-site-scripting-xss) vastaan, käytä vain `setHtml()`-metodia sisällöt kanssa, joita hallitset suoraan.
:::

### HTML attributes {#html-attributes}

Suuri osa konfiguraatiosta webforJ:ssa tehdään tyypitetyillä Java-metodeilla sen sijaan, että käytettäisiin raaka HTML-ominaisuuksia. Kuitenkin `setAttribute()`-metodi on hyödyllinen esteettömyysominaisuuksien välittämiseen, joille ei ole erillistä API:a:

```java
Button button = new Button("Lähetä");
button.setAttribute("aria-label", "Lähetä lomake");
button.setAttribute("aria-describedby", "lomake-vihje");
```

:::note Tarkista komponentin tuki
Kaikki komponentit eivät tue satunnaisia ominaisuuksia. Tämä riippuu taustalla olevan komponenttien toteutuksesta.
:::

### Component IDs {#component-ids}

Voit määrittää ID:n komponentin HTML-elementille käyttämällä `setAttribute()`-metodia:

```java
Button submitButton = new Button("Lähetä");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("Sähköposti");
emailField.setAttribute("id", "email-input");
```

DOM-ID:t ovat yleisesti käytössä testivalitsijana ja CSS-targettina tyylitiedostoissasi.

:::tip Suosi luokkia usean komponentin targetingiin
Toisin kuin CSS-luokat, ID:t tulisi olla ainutlaatuisia sovelluksessasi. Jos sinun on kohdistettava useita komponentteja, käytä sen sijaan `addClassName()`-metodia.
:::

:::info Frameworkin hallinnoimat ID:t
webforJ myös määrittää automaattisia tunnisteita komponentteille sisäisesti. Palvelinpuolen ID:tä (jota pääsee käsiksi `getComponentId()`-metodin kautta) käytetään kehyksen seurannassa, kun taas asiakaspuolen ID (jota pääsee käsiksi `getClientComponentId()`-metodin kautta) käytetään asiakas-palvelin-viestinnässä. Nämä eriytyvät DOM `id`-attribuuteista, jotka asetat `setAttribute()`-metodilla.
:::

### Styling {#styling}

Kolme metodia kattaa suurimman osan tyylitarpeista: `setStyle()` yksittäisiä CSS-ominaisuusarvoja varten, sekä `addClassName()` ja `removeClassName()` CSS-luokkien lisäämiseksi tai poistamiseksi, jotka on määritelty tyylitiedostoissasi. Käytä `setStyle()`-metodia pienille tai kertaluontoisille tyyliin liittyville säädöille, ja käytä CSS-luokkia laajempien tai uudelleenkäytettävien tyyliin liittyvien sovellusten kehittämiseen.

```java
Div container = new Div();
container.setStyle("padding", "20px");

if (isHighPriority) {
    container.setStyle("border-left", "4px solid red");
}

Button button = new Button("Vaihtoehto");
button.addClassName("primary", "large");

if (isLoading) {
    button.addClassName("loading");
}
```

## Component state {#component-state}

Sisällön ja ulkoasun lisäksi komponenteilla on tilaoiminaisuuksia, jotka määrittävät, ovatko ne näkyviä ja vastaavatko ne käyttäjän vuorovaikutukseen. Kaksi yleisimmistä käytettävistä ovat `setVisible()` ja `setEnabled()`.

`setVisible()` ohjaa, näkyykö komponentti lainkaan käyttöliittymässä. `setEnabled()` ohjaa, hyväksyykö se syötteen tai vuorovaikutuksen säilyttäen samalla näkyvyyden. Useimmissa tapauksissa aukeaminen on mieluisampaa kuin piilottaminen: piilotettu nappi ei enää viesti siitä, että toiminto on olemassa mutta ei ole vielä saatavilla, mikä on vähemmän hämmentävää kuin sen näyttäminen ja piilottaminen.

```java
// Näytä ylimääräinen kenttä kun valintaruutu on valittuna
TextField advancedField = new TextField("Lisäasetukset");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Näytä lisäasetukset");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Ota nappi käyttöön vain silloin, kun tarvittavalla kentällä on arvo
Button submitButton = new Button("Lähetä");
submitButton.setEnabled(false);

TextField nameField = new TextField("Nimi");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

:::warning Poistettu ja piilotettu eivät ole turvallisuutta
`setVisible(false)` ja `setEnabled(false)` vaikuttavat vain käyttöliittymään. Ne eivät estä määrätietoista käyttäjää kutsumasta taustalla olevaa toimintoa selaimen tai käsittelemän pyynnön kautta, joten älä koskaan luota niihin suojellaessasi arkaluontoisia toimintoja. Aina pakota pääsynhallinta palvelimella. Katso [Poistettu ja piilotettu eivät ole turvallisuus](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security) enemmän tietoja.
:::

Seuraava kirjautumislomake demonstroi `setEnabled()`-metodin käytön käytännössä. Kirjautumispainike pysyy poissa käytöstä, kunnes molemmat kentät ovat täynnä, mikä tekee käyttäjälle selväksi, että syötteet ovat pakollisia ennen kuin edetään:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/frontend/usingcomponents/conditionalstate.css',
]}
height='450px'
/>

## Working with containers {#working-with-containers}

webforJ:ssa asettelu tapahtuu säiliöiden avulla, jotka ovat komponentteja, jotka sisältävät muita komponentteja ja hallitsevat niiden järjestystä. Et sijoita lapsikomponentteja manuaalisesti; sen sijaan lisäät ne säiliöön ja määrität sen asetteluominaisuudet.

### Adding components {#adding-components}

Kaikki säiliöt tarjoavat `add()`-metodin. Voit siirtää komponentteja yksi kerrallaan tai kaikki kerralla:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Napsauta minua"));

TextField nameField = new TextField("Nimi");
TextField emailField = new TextField("Sähköposti");
Button submitButton = new Button("Lähetä");

container.add(nameField, emailField, submitButton);
```

### Layout options {#layout-options}

`FlexLayout` on webforJ:n ensisijainen asettelusaine, ja se kattaa suurimman osan käyttötilanteista: rivit, sarakkeet, tasaaminen, väli ja kääntyminen. Monimutkaisempia asetteluja, kuten CSS Grid tai mukautettu sijoittaminen, voit soveltaa CSS:ää suoraan `setStyle()` tai `addClassName()` kautta mihin tahansa säiliökomponenttiin. Katso [FlexLayout](/docs/components/flex-layout) -dokumentaatio täydelliseen asetteluvalikoimaan.

### Showing and hiding sections {#showing-hiding-sections}

Yksi yleinen `setVisible()`-käyttö säiliöissä on paljastaa lisäkäyttöliittymä vain silloin, kun se on merkityksellistä. Tämä pitää käyttöliittymän keskittyneenä ja vähentää visuaalista häiriötä. Sen sijaan, että navigoit uuteen näkymään, voit näyttää osan nykyisestä asettelusta suoraan käyttäjän syötteiden perusteella.

Seuraava asetuspaneeli demonstroi tätä: perusilmoitusasetukset ovat aina näkyvissä, ja lisävaihtoehtojen osio ilmestyy vain, kun käyttäjä pyytää sitä. Tallenna-painike aktivoituu heti, kun mitään asetusta on muutettu:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/frontend/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### Container management {#container-management}

Käytä `remove()` ja `removeAll()` poistaaksesi komponentteja säiliöstä ajon aikana:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Tilapäinen");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Tämä on hyödyllistä, kun tarvitaan vaihtaa sisältö kokonaan, kuten vaihtaa latausindikaattori ladattaviin tietoihin.

## Form validation {#form-validation}

Useiden komponenttien koordinoiminen lähetys toiminnan estämiseksi on yleinen malli webforJ-käyttöliittymissä. Perusidea on, että jokainen syötekenttä rekisteröi kuuntelijan, ja aina kun arvo muuttuu, lomake arvioi, täyttyvätkö kaikki kriteerit, ja päivittää lähetyspainikkeen sen mukaisesti.

Esimerkki, joka näyttää tämän manuaalisesti, jotta voit nähdä, miten komponentin tila ja tapahtumakuuntelijat toimivat yhdessä. Tämä ei ole suositeltu lähestymistapa todellisille lomakkeille: manuaalinen kuuntelulogiikka tulee vaikeaksi ylläpitää lomakkeiden kasvaessa, eikä se yhdistä komponenttejasi taustalla olevaan tietomalliin.

:::tip Käytä tietosidontaa lomakevalidointiin
Tuotantolomakkeille käytä [tietosidontaa](/docs/data-binding/overview). Se kattaa validoinnin, kahdensuuntaisen synkronoinnin komponenttien ja mallisi välillä sekä arvon muuntamisen `BindingContext`in kautta. Tässä esitetty manuaalinen malli on vain havainnollistamista varten.
:::

Tässä yhteydenottolomakkeessa nimen kentän ei saa olla tyhjää, sähköpostissa on oltava `@`-merkki, ja viestin on oltava vähintään 10 merkkiä pitkä:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/frontend/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## Dynamic content updates {#dynamic-content-updates}

Komponenttien ei tarvitse pysyä kiinteässä tilassa niiden luomisen jälkeen. Voit päivittää tekstiä, vaihtaa CSS-luokkia ja käännellä aktiivista tilaa mihin tahansa aikaan sovellustapahtumien seurauksena. Yksi yleinen esimerkki on palautteen antaminen pitkään kestävää tehtävää varten:

```java
Label statusLabel = new Label("Valmis");
Button startButton = new Button("Aloita prosessi");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("Käsittely...");
    statusLabel.addClassName("käsittelyssä");

    performTask(() -> {
        statusLabel.setText("Valmis");
        statusLabel.removeClassName("käsittelyssä");
        statusLabel.addClassName("onnistunut");
        startButton.setEnabled(true);
    });
});
```

Painikkeen poistaminen käytöstä tehtävän ajaksi estää kaksoislähetykset, ja etiketin päivittäminen pitää käyttäjän informoituna siitä, mitä tapahtuu.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

`ComponentLifecycleObserver`-rajapinta antaa sinun tarkkailla komponentin elinkaaritapahtumia komponentin ulkopuolelta. Tämä on hyödyllistä, kun sinun on reagoitava siihen, että komponentti luodaan tai poistetaan ilman, että se muuttaa sen toteutusta. Esimerkiksi voit käyttää sitä ylläpitämään aktiivisten komponenttien rekisteriä tai vapauttamaan ulkoisia resursseja, kun komponentti poistetaan.

### Basic usage {#basic-usage}

Kutsu `addLifecycleObserver()` mihin tahansa komponenttiin rekisteröidäksesi takaisinsoiton. Takaisinsoitto saa komponentin ja elinkaaritapahtuman:

```java
Button button = new Button("Käytä minua");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("Nappi luotiin");
            break;
        case DESTROY:
            System.out.println("Nappi poistettiin");
            break;
    }
});
```

### Pattern: Resource registry {#pattern-resource-registry}

DESTROY-tapahtuma on erityisen hyödyllinen automaattisesti rekisterin synkronoinnissa. Sen sijaan, että poistaisit komponentteja manuaalisesti, kun niitä ei enää tarvita, annat komponentin ilmoittaa rekisterille itsestään:

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

### Pattern: Component coordination {#pattern-component-coordination}

Koordinaattori-luokka, joka hallinnoi joukkoa toisiinsa liittyviä komponentteja, voi käyttää samaa lähestymistapaa pitääkseen sisäisen luettelonsa tarkkana:

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

### When to use {#when-to-use}

Käytä `ComponentLifecycleObserver`-rajapintaa:
- Komponenttirekisterien rakentamisessa
- Lokituksen tai valvonnan toteuttamisessa
- Useiden komponenttien koordinoimisessa
- Ulkoisten resurssien siivoamisessa

Koodin suorittamiseksi komponentin kiinnittämisen jälkeen DOM:iin katso `whenAttached()` [Komponenttien koostamisessa](/docs/building-ui/composing-components) -oppaassa.

## User data {#user-data}

Komponentit voivat kuljettaa arvaamatonta palvelinpuolen tietoa käyttäen `setUserData()` ja `getUserData()`-metodeja. Molemmat metodit ottavat avaimen tietojen tunnistamiseksi. Tämä on hyödyllistä, kun sinun on yhdistettävä aluesi objekti tai konteksti komponenttiin hallitsematta erillistä hakurakennetta.

```java
Button button = new Button("Käsittele");
button.setUserData("konteksti", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("konteksti");
    processTask(context.getUserId(), context.getTaskId());
});
```

Koska käyttäjätiedot eivät koskaan siirry asiakkaalle, voit turvallisesti tallentaa arkaluontoisia tietoja tai suuria objekteja vaikuttamatta verkon liikenteeseen.
