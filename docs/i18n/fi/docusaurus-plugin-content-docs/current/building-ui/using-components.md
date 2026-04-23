---
sidebar_position: 3
title: Using Components
sidebar_class_name: new-content
_i18n_hash: 3ffe2e3b31ea278e434f7319f8019637
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Komponentit ovat webforJ-sovellusten rakennuspalikoita. Käytitpä sitten sisäänrakennettuja komponentteja kuten `Button` ja `TextField` tai työtiimisi tarjoamia mukautettuja komponentteja, vuorovaikutuksesi niiden kanssa noudattaa samaa johdonmukaista mallia: määrität ominaisuuksia, hallitset tilaa ja koostet komponentteja asetteluiksi.

Tässä oppaassa keskitytään päivittäisiin toimiin: ei komponenttien sisäisiin toimintatapoihin, vaan siihen, kuinka asioita tehdään niiden kanssa käytännössä.

## Component properties {#component-properties}

Jokainen komponentti paljastaa ominaisuuksia, jotka kontrolloivat sen sisältöä, ulkonäköä ja käyttäytymistä. Suurin osa näistä omaa omat, tyypitetyt Java-menetelmät (`setText()`, `setTheme()`, `setExpanse()` jne.), mikä on ensisijainen tapa, jolla määrität komponentteja webforJ:ssä. Alla olevat osiot käsittelevät ominaisuuksia ja menetelmiä, jotka pätevät laajalti komponenttityypeille.

### Text content {#text-content}

`setText()`-menetelmä asettaa komponentin näkyvän tekstin, kuten kuvauksen `Button`-painikkeessa tai sisällön `Label`:issa. Syöttökomponenteissa kuten `TextField` kannattaa käyttää `setValue()`-menetelmää asettaaksesi kentän nykyisen arvon.

```java
Button button = new Button();
button.setText("Klikkaa minua");

Label label = new Label();
label.setText("Tila: valmis");

TextField field = new TextField();
field.setValue("Alkuperäinen arvo");
```

Jotkut komponentit tukevat myös `setHtml()`-menetelmää, kun tarvitaan inline HTML-merkintää sisällössä:

```java
Div container = new Div();
container.setHtml("<strong>Rohkeaa tekstiä</strong> ja <em>kursivoitua tekstiä</em>");
```

### HTML attributes {#html-attributes}

Suurin osa konfiguraatiosta webforJ:ssä tehdään tyypitetyillä Java-menetelmillä, ei raakoilla HTML-ominaisuuksilla. Kuitenkin `setAttribute()` on hyödyllinen, kun halutaan antaa saavutettavuusominaisuuksia, joille ei ole omistettu APIa:

```java
Button button = new Button("Lähetä");
button.setAttribute("aria-label", "Lähetä lomake");
button.setAttribute("aria-describedby", "lomake-vinkki");
```

:::note Tarkista komponenttituen
Kaikki komponentit eivät tue mielivaltaisia ominaisuuksia. Tämä riippuu taustalla olevasta komponenttiversiosta.
:::

### Component IDs {#component-ids}

Voit määrittää ID:n komponentin HTML-elementille käyttämällä `setAttribute()`:

```java
Button submitButton = new Button("Lähetä");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("Sähköposti");
emailField.setAttribute("id", "email-input");
```

DOM ID:itä käytetään yleisesti testivalitsijoina ja CSS- kohdistuksina tyylitiedostoissasi.

:::tip Suosi luokkia monen komponentin kohdistuksessa
Toisin kuin CSS-luokat, ID:t tulisi olla ainutlaatuisia sovelluksessasi. Jos sinun on kohdistettava useita komponentteja, käytä `addClassName()`-menetelmää sen sijaan.
:::

:::info Kehyksen hallinnoimat ID:t
webforJ myös määrittää automaattiset tunnisteet komponentteihin sisäisesti. Palvelinpuolen ID (johon pääsee `getComponentId()`-menetelmällä) käytetään kehyksen seurantaan, kun taas asiakaspuolen ID (johon pääsee `getClientComponentId()`-menetelmällä) on käytössä asiakaspalvelinviestintään. Nämä ovat erillisiä DOM- `id`-ominaisuudesta, jonka määrität `setAttribute()`-menetelmällä.
:::

### Styling {#styling}

Kolme menetelmää kattaa useimmat tyylitarpeet: `setStyle()` yksittäisiä CSS-ominaisuusarvoja varten, ja `addClassName()` sekä `removeClassName()` CSS-luokkien soveltamiseksi tai poistamiseksi, jotka on määritelty tyylitiedostoissasi. 
Käytä `setStyle()`-menetelmää pienille tai kertakäyttöisille tyylimuutoksille, ja käytä CSS-luokkia suurempien tai toistuvien tyylityylien soveltamiseksi.

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

:::note Perinteinen lähestymistapa
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) on vanha lähestymistapa, eikä sitä yleensä suositella uusille projekteille. Useimmissa tapauksissa pidä tyylisi erillisissä CSS-tiedostoissa.
:::

## Component state {#component-state}

Sisällön ja ulkonäön lisäksi komponenteilla on tilaominaisuuksia, jotka määrittävät, ovatko ne näkyvissä ja vastaavatko ne käyttäjävuorovaikutukseen. Kaksi yleisimmin käytettyä ovat `setVisible()` ja `setEnabled()`.

`setVisible()` ohjaa, näkyykö komponentti ylipäänsä käyttöliittymässä. `setEnabled()` ohjaa, hyväksyykö se syötteitä tai vuorovaikutuksia samalla kun se pysyy näkyvissä. Useimmissa tapauksissa hylkääminen on suositeltavaa piilottamisen sijaan: poistettu painike viestittää, että toiminto on olemassa mutta ei ole vielä käytettävissä, mikä on vähemmän hämmentävää kuin sen ilmestyminen ja katoaminen.

```java
// Paljasta lisäkenttä, kun valintaruutu on valittu
TextField advancedField = new TextField("Lisäasetukset");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Näytä lisäasetukset");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Ota painike käyttöön vain, kun vaadittu kenttä on täytetty
Button submitButton = new Button("Lähetä");
submitButton.setEnabled(false);

TextField nameField = new TextField("Nimi");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

Seuraava kirjautumislomake demonstroi `setEnabled()`-menetelmän käytön käytännössä. Kirjautumispainike pysyy pois päältä, kunnes molemmat kentät sisältävät sisältöä, mikä tekee käyttäjälle selväksi, että syötteet ovat pakollisia ennen jatkamista:

<ComponentDemo 
path='/webforj/conditionalstate?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/conditionalstate.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java'
height='400px'
/>

## Working with containers {#working-with-containers}

webforJ:ssä asettelua hallitsevat säilöt, jotka ovat komponentteja, jotka sisältävät muita komponentteja ja ohjaavat, kuinka ne järjestetään. Et sijoita lapsikomponentteja käsin; sen sijaan lisäät ne säilöön ja määrität sen asetteluominaisuudet.

### Adding components {#adding-components}

Kaikki säilöt tarjoavat `add()`-menetelmän. Voit välittää komponentteja yksi kerrallaan tai kaikki kerralla:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Klikkaa minua"));

TextField nameField = new TextField("Nimi");
TextField emailField = new TextField("Sähköposti");
Button submitButton = new Button("Lähetä");

container.add(nameField, emailField, submitButton);
```

### Layout options {#layout-options}

`FlexLayout` on pääasiallinen asettelu säilö webforJ:ssä ja kattaa valtaosan käyttötapauksista: rivit, sarakkeet, tasaus, väli ja kääntäminen. Monimutkaisempia järjestelyjä, kuten CSS-ruudukkoa tai mukautettua sijoittelua varten, voit soveltaa CSS:ää suoraan `setStyle()`- tai `addClassName()`-menetelmällä mihin tahansa säilökomponenttiin. Katso [FlexLayout](/docs/components/flex-layout) -dokumentaatio täydelle asettelu vaihtoehtojen kattavuudelle.

### Showing and hiding sections {#showing-hiding-sections}

Yksi yleinen käyttötapa `setVisible()`-menetelmälle säilöissä on paljastaa lisäkäyttöliittymä vain, kun se on relevanttia. Tämä pitää käyttöliittymän keskittyneenä ja vähentää visuaalista häiriötä. Sen sijaan, että siirtyisit uuteen näkymään, voit näyttää osion nykyisestä asetelmasta suoraan käyttäjän syötteen perusteella.

Seuraava asetuspaneeli demonstroi tätä: perusilmoitustiedot ovat aina näkyvissä, ja edistyneitä asetuksia koskeva osio ilmestyy vain, kun käyttäjä pyytää sitä. Tallenna-painike aktivoituu heti, kun mikään asetus muuttuu:

<ComponentDemo 
path='/webforj/progressivedisclosure?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/progressivedisclosure.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java'
height='450px'
/>

### Container management {#container-management}

Käytä `remove()` ja `removeAll()` poistaaksesi komponentteja säilöstä ajon aikana:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Tilapäinen");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Tämä on hyödyllistä, kun sinun on vaihdettava sisältö kokonaan, esimerkiksi vaihtamalla latausindikaattori ladattuun tietoon.

## Form validation {#form-validation}

Useiden komponenttien koordinointi lähettämistoiminnan rajaamiseksi on yksi yleisimmistä malleista webforJ-käyttöliittymissä. Perusidea on yksinkertainen: jokainen syöttökenttä rekisteröi kuuntelijan, ja aina kun jokin arvo muuttuu, lomake arvioi uudelleen, onko kaikki kriteerit täytetty ja päivittää lähetyspainikkeen vastaavasti.

Tämä on suositeltavaa verrattuna virheiden näyttämiseen vasta sen jälkeen, kun käyttäjä on napsauttanut lähetä, koska se antaa jatkuvaa palautetta ja estää tarpeettomia lähetyksiä. Lähetyspainike toimii indikaattorina: pois päältä tarkoittaa, että lomake ei ole valmis, päällä tarkoittaa, että se on valmis.

Tässä yhteystietolomakkeessa nimen kenttä ei saa olla tyhjää, sähköpostin on sisällettävä `@`-merkki ja viestin on oltava vähintään 10 merkkiä pitkä:

<ComponentDemo 
path='/webforj/formvalidation?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/formvalidation.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java'
height='500px'
/>

## Dynamic content updates {#dynamic-content-updates}

Komponentit eivät tarvitse pysyä kiinteässä tilassa niiden luomisen jälkeen. Voit päivittää tekstiä, vaihtaa CSS-luokkia ja vaihtaa käyttötilaa milloin tahansa sovellustapahtumien seurauksena. Yksi yleinen esimerkki on palautteen antaminen pitkäkestoisissa tehtävissä:

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

Painikkeen poistaminen käytöstä tehtävän aikana estää kaksoiselle lähetykselle, ja tarran päivittäminen pitää käyttäjän tietoisena siitä, mitä tapahtuu.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

`ComponentLifecycleObserver`-rajapinta antaa sinun tarkkailla komponentin elinkaaritapahtumia komponentin ulkopuolelta. Tämä on hyödyllistä, kun sinun on reagoitava komponentin luomiseen tai tuhoamiseen ilman, että muokkaat sen toteutusta. Esimerkiksi voit käyttää sitä ylläpitääksesi aktiivisten komponenttien rekisteriä tai vapauttaaksesi ulkoisia resursseja, kun komponentti poistetaan.

### Basic usage {#basic-usage}

Kutsu `addLifecycleObserver()` minkä tahansa komponentin kohdalla rekisteröidäksesi palautteen. Palautteen saa komponentti ja elinkaaritapahtuma:

```java
Button button = new Button("Katso minua");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("Painike luotiin");
            break;
        case DESTROY:
            System.out.println("Painike tuhottiin");
            break;
    }
});
```

### Pattern: Resource registry {#pattern-resource-registry}

DESTROY-tapahtuma on erityisen hyödyllinen rekisterin automaattisessa synkronoinnissa. Sen sijaan, että poistaisit komponentteja manuaalisesti, kun niitä ei enää tarvita, annat komponentin ilmoittaa rekisterille itsestään:

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

Koordinaattori, joka hallitsee sarjaa toisiinsa liittyviä komponentteja, voi käyttää samaa lähestymistapaa ylläpitääkseen sisäistä luetteloaan tarkkana:

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
- Rakentaaksesi komponenttirekistereitä
- Toteuttaaksesi lokitusta tai seurantaa
- Koordinoidaksesi useita komponentteja
- Siivotaksesi ulkoisia resursseja

Koodin suorittamiseen komponentti siirteessä DOM:iin, katso [`whenAttached()`](/docs/building-ui/composite-components) Composite Components -oppaan kohdasta.

## User data {#user-data}

Komponentit voivat kuljettaa mielivaltaisia palvelinpuolen tietoja `setUserData()` ja `getUserData()`-menetelmien avulla. Molemmat menetelmät ottavat avaimen määrittääkseen tiedot. Tämä on hyödyllistä, kun sinun on liitettävä aluetta tai kontekstiä komponenttiin ilman erillistä hakurakennetta.

```java
Button button = new Button("Käsittele");
button.setUserData("konteksti", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("konteksti");
    processTask(context.getUserId(), context.getTaskId());
});
```

Koska käyttäjätieto ei koskaan lähetetä asiakkaalle, voit turvallisesti tallentaa arkaluonteisia tietoja tai suuria objekteja ilman, että se vaikuttaa verkkoliikenteeseen.
