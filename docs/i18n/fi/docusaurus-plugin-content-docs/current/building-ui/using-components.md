---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: fb67c93e2165a651245a703c772d3bcb
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Komponentit ovat webforJ-sovellusten rakennuspalikoita. Käytit sitten sisäänrakennettuja komponentteja, kuten `Button` ja `TextField`, tai työsi mukautettujen komponenttien parissa, vuorovaikutus niiden kanssa seuraa aina samaa johdonmukaista mallia: määrität ominaisuuksia, hallitset tilaa ja kokoat komponentteja asetteluiksi.

Tässä oppaassa keskitytään päivittäisiin toimiin: ei komponenttien sisäisiin toimintoihin vaan siihen, miten niillä tehdään asioita käytännössä.

## Komponenttien ominaisuudet {#component-properties}

Jokainen komponentti tarjoaa ominaisuuksia, jotka hallitsevat sen sisältöä, ulkoasua ja käyttäytymistä. Suurin osa näistä ominaisuuksista on omistetut, tyypitetyt Java-metodit (`setText()`, `setTheme()`, `setExpanse()`, jne.), jotka ovat pääasiallinen tapa määrittää komponentteja webforJ:ssa. Tämä osio kattaa ominaisuudet ja metodit, jotka pätevät laajalti eri komponenttityypeille.

### Tekstisisältö {#text-content}

`setText()`-metodi asettaa komponentin näkyvän tekstin kirjaimellisesti, kuten `Button`:n kuvatekstin tai `Label`:n sisällön. Syöttökonteille, kuten `TextField`, käytä sen sijaan `setValue()` asettaaksesi kentän nykyinen arvo.

```java
Button button = new Button();
button.setText("Napsauta minua");

Label label = new Label();
label.setText("Tila: valmis");

TextField field = new TextField();
field.setValue("Alkuperäinen arvo");
```

`setText()`-metodilla kirjoitettu merkintä näkyy sellaisina merkkeinä eikä sitä koskaan suoriteta, mikä estää käyttäjän syötteestä tai ulkoisista tiedoista tulevan tekstin tulkitsemista eläväksi merkinnäksi.

```java
// Näytetään kirjaimellisesti "<b>Tila: valmis</b>"
component.setText("<b>Tila: valmis</b>");
```

:::note Käyttäen `<html>`-tagia
Aikaisemmat versiot webforJ:sta käsittelivät `setText()`-metodin kautta tulevia arvoja, jotka oli kääritty `<html>`:n sisään, HTML:nä. Tämä käyttäytyminen on vanhentunut ja se poistetaan webforJ:sta versiolla 27.00.

Ensimmäinen kerta, kun `<html>`-kääritty arvo saavuttaa `setText()`, lokitetaan varoitus, jossa mainitaan komponentti ja kutsupaikka, jotta kutsun voi siirtää `setHtml()`-metodiin.

Adoptoidaksesi webforJ 27.00 oletusasetuksen etukäteen, aseta `webforj.legacyHtmlInText` arvoksi `false`. Spring-sovelluksessa sama arvo asetetaan `webforj.legacy-html-in-text` avulla.

```java
// webforj.legacyHtmlInText = true (oletus)
component.setText("<html><b>Tila: valmis</b></html>"); // näyttää lihavoituna

// webforj.legacyHtmlInText = false
component.setText("<html><b>Tila: valmis</b></html>"); // näyttää merkit <b>Tila: valmis</b>
```
:::

### HTML:n renderöinti {#rendering-html}

Jotkut komponentit tukevat myös `setHtml()`-metodia tapauksissa, joissa sinun täytyy renderöidä sisällössä inline HTML-merkintöjä:

```java
Div container = new Div();
container.setHtml("<strong>Lihavoitu teksti</strong> ja <em>kursivoitu teksti</em>");
```

:::danger Ristisitoutuvuus (XSS)
Ennaltaehkäisynä [ristisitoutuvuus (XSS) hyökkäyksiltä](/docs/security/application-security/common-threats#cross-site-scripting-xss), käytä `setHtml()`-metodia vain sisällön kanssa, jota hallitset suoraan.
:::

### HTML-ominaisuudet {#html-attributes}

Suurin osa konfiguraatiosta webforJ:ssa tehdään tyypitetyillä Java-metodeilla sen sijaan, että käytettäisiin raakoja HTML-ominaisuuksia. Kuitenkin `setAttribute()` on hyödyllinen, kun annetaan esteettömyysominaisuuksia, joille ei ole omistettua API:a:

```java
Button button = new Button("Lähetä");
button.setAttribute("aria-label", "Lähetä lomake");
button.setAttribute("aria-describedby", "lomake-vihje");
```

:::note Tarkista komponentin tuki
Kaikki komponentit eivät tue mielivaltaisia attribuutteja. Tämä riippuu taustalla olevan komponentin toteutuksesta.
:::

### Komponentin ID:t {#component-ids}

Voit määrittää ID:n komponentin HTML-elementille käyttämällä `setAttribute()`:

```java
Button submitButton = new Button("Lähetä");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("Sähköposti");
emailField.setAttribute("id", "email-input");
```

DOM ID:itä käytetään yleisesti testivalitsimina ja CSS:n kohdistamiseen tyylitiedostoissasi.

:::tip Suosi luokkia monikomponenttien kohdistamiseen
Toisin kuin CSS-luokat, ID:t tulisi olla ainutlaatuisia sovelluksessasi. Jos tarvitset kohdistaa useita komponentteja, käytä `addClassName()`-metodia sen sijaan.
:::

:::info Kehyksen hallinnoimat ID:t
webforJ määrittää myös automaattisesti tunnisteet komponentteille sisäisesti. Palvelinpuolen ID:t (johon pääsee `getComponentId()`-metodin kautta) käytetään kehyksen seurantaan, kun taas asiakaspuolen ID:t (johon pääsee `getClientComponentId()`-metodin kautta) käytetään asiakas-palvelin-viestintään. Nämä ovat erillisiä DOM `id`-attribuutista, jonka asetat `setAttribute()`-metodin avulla.
:::

### Tyylittely {#styling}

Kolme metodia kattavat suurimman osan tyylitarpeista: `setStyle()` yksittäisiä CSS-ominaisuusarvoja varten, ja `addClassName()` ja `removeClassName()` CSS-luokkien soveltamiseen tai poistamiseen tyylitiedostoistasi. 
Käytä `setStyle()` pienille tai kertaluonteisille tyylimuutoksille, ja käytä CSS-luokkia suurempien tai uudelleenkäytettävien tyylien soveltamiseen.

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

Sisällön ja ulkoasun lisäksi komponenteilla on tilan ominaisuuksia, jotka määrittävät, ovatko ne näkyviä ja reagoivatko ne käyttäjän vuorovaikutukseen. Kaksi yleisimmistä käytettävistä ovat `setVisible()` ja `setEnabled()`.

`setVisible()` hallitsee, näkyykö komponentti käyttöliittymässä lainkaan. `setEnabled()` hallitsee, hyväksyykö se syötteen tai vuorovaikutuksen pysyessään näkyvänä. Useimmissa tapauksissa piilottaminen on parempi ratkaisu kuin piilottaminen: poistettu painike viestii silti, että toiminto on olemassa, mutta ei ole vielä saatavilla, mikä on vähemmän häiritsevää kuin sen ilmestyminen ja katoaminen.

```java
// Näytä lisäkenttä, kun valintaruutu on valittu
TextField advancedField = new TextField("Laajennettu asetus");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Näytä laajennetut asetukset");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Ota painike käyttöön vain, kun vaadittavassa kentässä on arvo
Button submitButton = new Button("Lähetä");
submitButton.setEnabled(false);

TextField nameField = new TextField("Nimi");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

:::warning Poistettu ja piilotettu eivät ole turvallisia
`setVisible(false)` ja `setEnabled(false)` vaikuttavat vain käyttöliittymään. Ne eivät estä määrätietoista käyttäjää kutsumasta taustalla olevaa toimintoa selaimen tai muokatun pyynnön kautta, joten älä koskaan luota niihin suojautumaan herkille toimille. Aina tee pääsyn valvonta palvelimella. Katso lisätietoja [Poistettu ja piilotettu eivät ole turvallisia](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security).
:::

Seuraava kirjautumislomake demonstroi `setEnabled()` käyttöä käytännössä. Kirjautumispainike pysyy pois päältä, kunnes molemmissa kentissä on sisältöä, mikä tekee käyttäjälle selväksi, että syötettä vaaditaan ennen etenemistä:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='450px'
/>

## Työskentely konttien kanssa {#working-with-containers}

webforJ:ssa asettelu hoidetaan konteilla, jotka ovat komponentteja, jotka sisältävät muita komponentteja ja hallitsevat niiden asettelua. Et sijoita lapsikomponentteja manuaalisesti; sen sijaan lisäät ne konttiin ja asetat tämän säilön asetteluominaisuudet.

### Komponenttien lisääminen {#adding-components}

Kaikki kontit tarjoavat `add()`-metodin. Voit siirtää komponentteja yksi kerrallaan tai kaikki kerralla:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Napsauta minua"));

TextField nameField = new TextField("Nimi");
TextField emailField = new TextField("Sähköposti");
Button submitButton = new Button("Lähetä");

container.add(nameField, emailField, submitButton);
```

### Asetteluvaihtoehdot {#layout-options}

`FlexLayout` on webforJ:n ensisijainen asettelukontti ja kattaa suurimman osan käyttötapauksista: rivit, sarakkeet, kohdistus, väli ja kääntö. Monimutkaisempia järjestelyjä, kuten CSS Grid tai mukautetut sijoittelut, voit soveltaa CSS:tä suoraan `setStyle()`- tai `addClassName()`-metodilla mihin tahansa säilytysosaan. Katso [FlexLayout](/docs/components/flex-layout) asiakirja täydestä asetteluvaihtoehtojen valikoimasta.

### Osien näyttäminen ja piilottaminen {#showing-hiding-sections}

Yleinen käyttötapa `setVisible()`-metodille konteissa on paljastaa lisäkäyttöliittymä vain silloin, kun se on merkityksellistä. Tämä pitää käyttöliittymän keskittyneenä ja vähentää visuaalista hälinää. Sen sijaan, että siirtyisit uuteen näkymään, voit näyttää osan nykyisestä asettelusta suoraan vastauksena käyttäjän syötteeseen.

Seuraava asetuspaneeli tekee tästä selkeän: perus ilmoitusasetukset ovat aina näkyvissä, ja edistyksellisten vaihtoehtojen osio näkyy vain, kun käyttäjä pyytää sitä. Tallenna-painike aktivoituu heti, kun mikä tahansa asetus muuttuu:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/resources/static/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### Kontinhallinta {#container-management}

Käytä `remove()` ja `removeAll()` ottaaksesi komponentteja pois konteista ajonaikana:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Aikaväli");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Tämä on hyödyllistä, kun sinun on vaihdettava sisältö kokonaan, kuten latausindikaattorin vaihtaminen ladattuun dataan.

## Lomakevalidointi {#form-validation}

Useiden komponenttien koordinointi lähetys-toimintoa varten on yleinen kaava webforJ:n käyttöliittymissä. Perusidea on, että jokainen syöttökenttä rekisteröi kuuntelijan, ja aina kun arvo muuttuu, lomake arvioi uudelleen, ovatko kaikki kriteerit täytetty, ja päivittää lähetyspainiketta asianmukaisesti.
 
Alla oleva esimerkki liittää tämän manuaalisesti, jotta voit nähdä, miten komponentin tila ja tapahtumakuuntelijat toimivat yhdessä. Tämä ei ole suositeltava lähestymistapa oikeille lomakkeille: manuaalinen kuuntelijalogiikka voi olla vaikeaa ylläpitää, kun lomakkeet kasvavat, eikä se yhdistä komponenttejasi taustalla olevaan datamalliin.
 
:::tip Käytä datan sitomista lomakevalidointiin
Tuotanto-lomakkeille käytä [datan sitomista](/docs/data-binding/overview). Se kattaa validoinnin, kaksisuuntaisen synkronoinnin komponenttien ja mallisi välillä sekä arvon muuntamisen `BindingContext`:in kautta. Manuaalinen malli, joka tässä on esitetty, on vain havainnollistamista varten.
:::
 
Tässä yhteystiedot-lomakkeessa nimen kenttä ei saa olla tyhjä, sähköpostin on sisällettävä `@`-merkki ja viestin on oltava vähintään 10 merkkiä pitkä:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/resources/static/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## Dynaamiset sisällön päivitykset {#dynamic-content-updates}

Komponenttien ei tarvitse pysyä kiinteässä tilassa sen jälkeen, kun ne on luotu. Voit päivittää tekstiä, vaihtaa CSS-luokkia ja kytkeä käytettävissä olevan tilan mihin tahansa sovellustapahtumaan. Yleinen esimerkki on palautteen antaminen pitkään kestävän tehtävän aikana:

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

Painikkeen poistaminen käytöstä tehtävän aikana estää kaksoisesityksen, ja merkkilaatikon päivittäminen pitää käyttäjän tietoisena siitä, mitä tapahtuu.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

`ComponentLifecycleObserver`-rajapinta antaa sinun tarkkailla komponentin elinkaaritapahtumia komponentin ulkopuolelta. Tämä on hyödyllistä, kun sinun tarvitsee reagoida siihen, että komponentti luodaan tai tuhotaan ilman, että sen toteutusta tarvitsee muuttaa. Esimerkiksi voit käyttää sitä ylläpitämään aktiivisten komponenttien rekisteriä tai vapauttamaan ulkopuolisia resursseja, kun komponentti poistetaan.

### Perus käyttö {#basic-usage}

Kutsu `addLifecycleObserver()` kaikille komponentille rekisteröidäksesi palautteen. Palautteen saa komponentti ja elinkaaritapahtuma:

```java
Button button = new Button("Katso minua");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("Painike luotiin");
            break;
        case DESTROY:
            System.out.println("Painike tuhotaan");
            break;
    }
});
```

### Malli: Resurssirekisteri {#pattern-resource-registry}

DESTROY-tapahtuma on erityisen hyödyllinen pitäessäsi rekisterin automaattisesti synkronoituina. Sen sijaan, että poistaisit komponentteja manuaalisesti, kun ne eivät enää ole tarpeen, sallitaan komponentin ilmoittaa rekisterille itsestään:

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

### Malli: Komponenttikoordinointi {#pattern-component-coordination}

Koordinaattoriluokka, joka hallitsee joukkoa liittyviä komponentteja, voi käyttää samaa lähestymistapaa pitääkseen sisäisen luettelonsa tarkkana:

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
- Komponenttirakennusten luomisessa
- Kirjaamisen tai valvonnan toteuttamisessa
- Useiden komponenttien koordinoimiseen
- Ulkoisten resurssien siivoamiseen

Suorittaaksesi koodia komponentin liittämisen jälkeen DOM:iin käytä `whenAttached()` [Komponenttien kokoamisen](/docs/building-ui/composing-components) oppaassa.

## Käyttäjätiedot {#user-data}

Komponentit voivat kantaa mielivaltaisia palvelinpuolen tietoja käyttämällä `setUserData()` ja `getUserData()`-metodeja. Molemmat menetelmät ottavat avaimen tiedon tunnistamiseksi. Tämä on hyödyllistä, kun sinun tarvitsee yhdistää alueobjekteja tai konteksteja komponenttiin hallitsematta erillistä hakustruktuuria.

```java
Button button = new Button("Käsittele");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Koska käyttäjätiedot eivät koskaan lähetetä asiakkaalle, voit turvallisesti tallentaa herkkiä tietoja tai suuria objekteja ilman, että se vaikuttaa verkkoliikenteeseen.
