---
sidebar_position: 3
title: Using Components
sidebar_class_name: new-content
_i18n_hash: cf47b1c83e67cb4c4998c149a7696701
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Komponentit ovat webforJ-sovellusten rakennuspalikoita. Käytitpä sisäänrakennettuja komponentteja, kuten `Button` ja `TextField`, tai työsi mukautettavia komponentteja tiimiltäsi, tapasi käyttää niitä seuraa samaa johdonmukaista mallia: määritä ominaisuuksia, hallitse tilaa ja yhdistä komponentteja asetteluiksi.

Tämä opas keskittyy päivittäisiin toimiin: ei komponenttien sisäisiin toimintoihin, vaan siihen, miten asioita tehdään käytännössä.

## Component properties {#component-properties}

Jokainen komponentti tarjoaa ominaisuuksia, jotka kontrolloivat sen sisältöä, ulkoasua ja käyttäytymistä. Suurin osa näistä sisältää erityisiä, tyypitettyjä Java-menetelmiä (`setText()`, `setTheme()`, `setExpanse()`, ja niin edelleen), jotka ovat ensisijainen tapa määrittää komponentteja webforJ:ssä. Alla olevat osiot käsittelevät laajasti komponenttityyppeihin soveltuvia ominaisuuksia ja menetelmiä.

### Tekstisisältö {#text-content}

`setText()`-menetelmä asettaa komponentin näkyvän tekstin, kuten `Button`-painikkeen otsikon tai `Label`-komponentin sisällön. Syöttökomponenteissa, kuten `TextField`, käytä sen sijaan `setValue()`-menetelmää kentän nykyisen arvon asettamiseen.

```java
Button button = new Button();
button.setText("Klikkaa minua");

Label label = new Label();
label.setText("Tila: valmis");

TextField field = new TextField();
field.setValue("Alkuarvo");
```

Joissakin komponenteissa tuetaan myös `setHtml()`-menetelmää, kun tarvitset inline HTML-muotoilua sisällössä:

```java
Div container = new Div();
container.setHtml("<strong>Rohkea teksti</strong> ja <em>kursivoitu teksti</em>");
```

### HTML-ominaisuudet {#html-attributes}

Suurin osa konfiguraatiosta webforJ:ssä tapahtuu tyypitettyjen Java-menetelmien kautta, eikä raakamuotoisten HTML-ominaisuuksien avulla. Kuitenkin `setAttribute()` on hyödyllinen saavutettavuusominaisuuksien välittämiseen, joille ei ole erillistä API:a:

```java
Button button = new Button("Lähetä");
button.setAttribute("aria-label", "Lähetä lomake");
button.setAttribute("aria-describedby", "lomake-vihje");
```

:::note Tarkista komponentin tuki
Kaikki komponentit eivät tue satunnaisia ominaisuuksia. Tämä riippuu taustalla olevan komponentin toteutuksesta.
:::

### Komponentti-ID:t {#component-ids}

Voit määrittää ID:n komponentin HTML-elementille käyttämällä `setAttribute()`:

```java
Button submitButton = new Button("Lähetä");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("Sähköposti");
emailField.setAttribute("id", "email-input");
```

DOM ID:itä käytetään yleisesti testivalitsimissa ja CSS-tyyleissä.

:::tip Suosi luokkia monikomponenttiselle kohdistamiselle
Toisin kuin CSS-luokat, ID:t tulisi olla ainutlaatuisia sovelluksessasi. Jos sinun täytyy kohdistaa useisiin komponentteihin, käytä `addClassName()`-menetelmää sen sijaan.
:::

:::info Kehyksen hallinnoimat ID:t
webforJ määrittää myös automaattisia tunnisteita komponenteille sisäisesti. Palvelinpuolen ID (johon pääsee käsiksi `getComponentId()`-menetelmällä) käytetään kehyksen seurannassa, kun taas asiakaspuolen ID (johon pääsee käsiksi `getClientComponentId()`-menetelmällä) käytetään asiakas-palvelin -viestinnässä. Nämä eroavat DOM `id`-attribuuttista, jonka asetat `setAttribute()`-menetelmällä.
:::

### Muotoilu {#styling}

Kolme menetelmää kattaa suurimman osan muotoilutarpeista: `setStyle()` yksittäisille CSS-ominaisuuksille, ja `addClassName()` ja `removeClassName()` CSS-luokkien lisäämiseen tai poistamiseen, jotka on määritelty tyylitiedostoissasi. 
Käytä `setStyle()`-menetelmää pieniin tai kertaluonteisiin muotoilumuutoksiin ja käytä CSS-luokkia laajempien tai uudelleenkäytettävien muotoilujen tekemiseen.

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

:::note Vanha lähestymistapa
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) on vanha lähestymistapa, eikä sitä yleisesti suositella uusille projekteille. Useimmissa tapauksissa pidä tyylisi erillisissä CSS-tiedostoissa.
:::

## Komponentin tila {#component-state}

Sisällön ja ulkoasun lisäksi komponenteilla on tilan ominaisuuksia, jotka määrittävät, ovatko ne näkyviä ja vastaavatko ne käyttäjän vuorovaikutukseen. Kaksi yleisimmin käytettyä ovat `setVisible()` ja `setEnabled()`.

`setVisible()` ohjaa, onko komponentti renderoitu käyttöliittymässä lainkaan. `setEnabled()` ohjaa, hyväksyykö komponentti syötettä tai vuorovaikutusta ollessaan näkyvissä. Useimmissa tapauksissa on parempi poistaa käytöstä kuin piilottaa: poistettu käyttöönottopainike viestii silti, että toiminta on olemassa, mutta ei ole vielä saatavilla, mikä on vähemmän hämmentävää kuin sen näkyminen ja katoaminen.

```java
// Näytä lisäkenttä, kun valintaruutu on valittu
TextField advancedField = new TextField("Edistynyt asetukset");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Näytä kehittyneet asetukset");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Ota painike käyttöön vain, kun vaadittu kenttä on täytetty
Button submitButton = new Button("Lähetä");
submitButton.setEnabled(false);

TextField nameField = new TextField("Nimi");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

Seuraava kirjautumislomake havainnollistaa `setEnabled()` käytännössä. Kirjaudu-painike pysyy käyttökelvottomana, kunnes molemmissa kentissä on sisältöä, mikä tekee käyttäjälle selväksi, että syötteitä vaaditaan ennen etenemistä:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='400px'
/>

## Työskentely säiliöiden kanssa {#working-with-containers}

webforJ:ssä asettelu hoidetaan säiliöiden avulla, jotka ovat komponentteja, jotka pitävät muita komponentteja ja kontrolloivat, miten ne on järjestetty. Et aseta lapsikomponentteja manuaalisesti; sen sijaan lisäät ne säiliöön ja määrität sen asetteluominaisuudet.

### Komponenttien lisääminen {#adding-components}

Kaikki säiliöt tarjoavat `add()`-menetelmän. Voit välittää komponentteja yksi kerrallaan tai kaikki kerralla:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Klikkaa minua"));

TextField nameField = new TextField("Nimi");
TextField emailField = new TextField("Sähköposti");
Button submitButton = new Button("Lähetä");

container.add(nameField, emailField, submitButton);
```

### Asetteluvaihtoehdot {#layout-options}

`FlexLayout` on webforJ:n ensisijainen asettelu-säiliö ja kattaa suurimman osan käyttötilanteista: rivit, sarakkeet, tasaus, väli ja kääntäminen. Monimutkaisemmille järjestelyille, kuten CSS Grid tai mukautettu sijoittelu, voit soveltaa CSS:ää suoraan `setStyle()` tai `addClassName()` avulla mille tahansa säiliökomponentille. Katso [FlexLayout](/docs/components/flex-layout) dokumentaatio täydelliselle asetteluvaihtoehtojen valikoimalle.

### Osioiden näyttäminen ja piilottaminen {#showing-hiding-sections}

Yleinen käyttö `setVisible()`-menetelmälle säiliöissä on lisä-käyttöliittymän näyttäminen vain, kun se on relevanttia. Tämä pitää käyttöliittymän keskittyneenä ja vähentää visuaalista hälinää. Sen sijaan, että navigoisit uuteen näkymään, voit näyttää nykyisen asettelun osan suoraan käyttäjän syötteen perusteella.

Seuraava asetuspaneeli osoittaa tämän: perustason ilmoitusasetukset ovat aina näkyvissä, ja edistyksellisten vaihtoehtojen osio ilmestyy vain, kun käyttäjä pyytää sitä. Tallenna-painike aktivoituu heti, kun mikä tahansa asetus muuttuu:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/resources/static/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### Säiliöhallinta {#container-management}

Käytä `remove()` ja `removeAll()` ottamaan komponentteja ulos säiliöstä ajonaikana:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Tilapäinen");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Tämä on hyödyllistä, kun sinun täytyy korvata sisältö kokonaisuudessaan, kuten vaihtaa latausindikaattori ladattuihin tietoihin.

## Lomakevalidointi {#form-validation}

Useiden komponenttien koordinointi lähetys-toimintoa varten on yksi yleisimmistä kuvioista webforJ-käyttöliittymissä. Ydinidea on yksinkertainen: jokainen syöttökenttä rekisteröi kuuntelijan, ja aina kun mikä tahansa arvo muuttuu, lomake arvioi uudelleen, täyttyvätkö kaikki kriteerit, ja päivittää lähetyspainikkeen vastaavasti.

Tämä on suositeltavaa verrattuna siihen, että näyttäisit validointivirheitä vasta, kun käyttäjä napsauttaa lähetyspainiketta, koska se antaa jatkuvaa palautetta ja estää tarpeettomia lähetyksiä. Lähetyspainike toimii indikaattorina: käyttökelvoton tarkoittaa, että lomake ei ole valmis, käyttökelpoinen tarkoittaa, että se on.

Tässä yhteydenottolomakkeessa nimen kenttä ei saa olla tyhjää, sähköpostin tulee sisältää `@`-merkki, ja viestin pitää olla vähintään 10 merkkiä pitkä:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/resources/static/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## Dynaamiset sisältöpäivitykset {#dynamic-content-updates}

Komponenttien ei tarvitse pysyä kiinteässä tilassa niiden luomisen jälkeen. Voit päivittää tekstiä, vaihtaa CSS-luokkia ja vaihtaa käyttöönottotilaa mihin tahansa aikaan sovellustapahtumien reaktioina. Yleinen esimerkki on palautteen antaminen pitkään kestävän tehtävän aikana:

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

Painikkeen poistaminen käytöstä, kun tehtävä on käynnissä, estää kaksoislähetykset, ja etiketin päivittäminen pitää käyttäjän tietoisena siitä, mitä tapahtuu.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

`ComponentLifecycleObserver`-rajapinta antaa sinun tarkkailla komponenttien elinkaaritapahtumia komponentin ulkopuolelta. Tämä on hyödyllistä, kun sinun tarvitsee reagoida komponentin luomiseen tai tuhoamiseen ilman, että muokkaat sen toteutusta. Esimerkiksi voit käyttää sitä ylläpitääksesi aktiivisten komponenttien rekisteriä tai vapauttaaksesi ulkoisia resursseja, kun komponentti poistetaan.

### Peruskäyttö {#basic-usage}

Kutsu `addLifecycleObserver()`-menetelmää mille tahansa komponentille rekisteröidäksesi palautteen. Palautteen saa komponentti ja elinkaaritapahtuma:

```java
Button button = new Button("Tarkkaile minua");

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

### Kaavio: Resurssirekisteri {#pattern-resource-registry}

DESTROY-tapahtuma on erityisen hyödyllinen pitämään rekisterin automaattisesti synkronoituna. Sen sijaan, että poistaisit komponentteja manuaalisesti, kun niitä ei enää tarvita, annat komponentin ilmoittaa rekisterille itselleen:

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

### Kaavio: Komponenttikoordinointi {#pattern-component-coordination}

Koordinointiluokka, joka hallitsee joukkoa toisiaan liittyviä komponentteja, voi hyödyntää samaa lähestymistapaa pitääkseen sisäisen luettelonsa tarkkana:

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

Käytä `ComponentLifecycleObserver`-menetelmää:
- Rakentamalla komponenttirekistereitä
- Toteuttamalla lokitusta tai seurantaa
- Koordinoimaan useita komponentteja
- Siivoamaan ulkoisia resursseja

Jos tarvitset suorittaa koodia sen jälkeen, kun komponentti on liitetty DOM:iin, katso [`whenAttached()`](/docs/building-ui/composite-components) Composite Components -oppaasta.

## Käyttäjätiedot {#user-data}

Komponentit voivat kantaa satunnaista palvelinpuolen dataa `setUserData()` ja `getUserData()`-menetelmien avulla. Molemmat menetelmät hyväksyvät avaimen tiedon tunnistamiseksi. Tämä on hyödyllistä, kun tarvitset liittää domain-objekteja tai kontekstia komponenttiin ilman, että hallitset erillistä hakurakennetta.

```java
Button button = new Button("Prosessi");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Koska käyttäjätiedot eivät koskaan siirry asiakkaalle, voit turvallisesti tallentaa arkaluontoisia tietoja tai suuria objekteja vaikuttamatta verkkoliikenteeseen.
