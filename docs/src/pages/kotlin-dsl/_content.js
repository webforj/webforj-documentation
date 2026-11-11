import { translate } from '@docusaurus/Translate';

// Data and code for the Kotlin DSL page. Every snippet is copied from the
// file named in the comment above it. Prose goes through translate(), so
// the get* functions are called while rendering.

// src/pages/intro/_code.js, HELLO_JAVA (stageforJ, JavaKotlinScene)
export const HELLO_JAVA = `@Route("/hello")
public class HelloView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private TextField name = new TextField("What is your name?");
  private Button greet = new Button("Say hello");

  public HelloView() {
    greet.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e ->
            Toast.show("Welcome, " + name.getValue() + "!",
                Theme.SUCCESS));
    self.add(name, greet);
  }
}`;

// src/pages/intro/_code.js, HELLO_KOTLIN (stageforJ, JavaKotlinScene)
export const HELLO_KOTLIN = `@Route("hello")
class HelloView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      val name = textField("What is your name?")
      button("Say hello") {
        theme = ButtonTheme.PRIMARY
        onClick {
          Toast.show("Welcome, \${name.text}!", Theme.SUCCESS)
        }
      }
    }
  }
}`;

// Class names from docs/integrations/kotlin-dsl/using-the-dsl.md, Naming conventions
export const NAMES = [
  { java: 'Button', kotlin: 'button()' },
  { java: 'TextField', kotlin: 'textField()' },
  { java: 'FlexLayout', kotlin: 'flexLayout()' },
  { java: 'Break', kotlin: '`break`()' },
];

// docs/integrations/kotlin-dsl/using-the-dsl.md, Creating components
export const CONFIG_BLOCK = `textField("Email") {
  placeholder = "you@example.com"   // this.placeholder
  required = true                   // this.required
  onModify { validate() }           // this.onModify(...)
}`;

// Facts from docs/integrations/kotlin-dsl/using-the-dsl.md (naming, nesting,
// scope safety, styling, events, common parameters) and overview.md (Java
// interoperability).
export function getConcepts() {
  return [
    {
      id: 'naming',
      title: translate({
        id: 'page.kotlinDsl.concepts.naming.title',
        message: 'Builders named after components',
      }),
      text: translate({
        id: 'page.kotlinDsl.concepts.naming.text',
        message:
          'Each component has a function named after its class in camelCase. Most take the options you set most often, such as a label or a placeholder, before the block.',
      }),
    },
    {
      id: 'nesting',
      title: translate({ id: 'page.kotlinDsl.concepts.nesting.title', message: 'Nested blocks' }),
      text: translate({
        id: 'page.kotlinDsl.concepts.nesting.text',
        message:
          'Components that hold children accept nested DSL calls inside their block, so the code has the shape of the screen.',
      }),
    },
    {
      id: 'events',
      title: translate({
        id: 'page.kotlinDsl.concepts.events.title',
        message: 'Events as lambdas',
      }),
      text: translate({
        id: 'page.kotlinDsl.concepts.events.text',
        message:
          'Listeners use on prefixed methods that take a lambda, such as onClick and onModify.',
      }),
    },
    {
      id: 'scope',
      title: translate({ id: 'page.kotlinDsl.concepts.scope.title', message: 'Scope safety' }),
      text: translate({
        id: 'page.kotlinDsl.concepts.scope.text',
        message:
          'Children only go into components that support them. Adding to an outer scope by accident does not compile.',
      }),
    },
    {
      id: 'styles',
      title: translate({
        id: 'page.kotlinDsl.concepts.styles.title',
        message: 'Styles and class names',
      }),
      text: translate({
        id: 'page.kotlinDsl.concepts.styles.text',
        message:
          'The styles property gives bracket access to CSS properties, the same as setStyle() in Java. Class names are added with +=.',
      }),
    },
    {
      id: 'java',
      title: translate({
        id: 'page.kotlinDsl.concepts.java.title',
        message: 'Next to your Java code',
      }),
      text: translate({
        id: 'page.kotlinDsl.concepts.java.text',
        message:
          'Kotlin compiles to JVM bytecode. Use Kotlin composites from Java classes and nest Java components in DSL blocks with add().',
      }),
    },
  ];
}

// docs/integrations/kotlin-dsl/extending-the-dsl.md, Basic DSL function.
// Excerpt: the import lines above the function are left out.
export const EXTEND_FUNCTION = `fun @WebforjDsl HasComponents.starRating(
  block: @WebforjDsl StarRating.() -> Unit = {}
): StarRating {
  return init(StarRating(), block)
}`;

// Steps from docs/integrations/kotlin-dsl/overview.md, Setup
export function getSteps() {
  return [
    {
      id: 'dependency',
      title: translate({
        id: 'page.kotlinDsl.start.steps.dependency.title',
        message: 'Add the dependencies',
      }),
      text: translate({
        id: 'page.kotlinDsl.start.steps.dependency.text',
        message:
          'Add the webforJ Kotlin DSL module and the Kotlin standard library to your pom.xml.',
      }),
    },
    {
      id: 'plugin',
      title: translate({
        id: 'page.kotlinDsl.start.steps.plugin.title',
        message: 'Add the Kotlin Maven plugin',
      }),
      text: translate({
        id: 'page.kotlinDsl.start.steps.plugin.text',
        message:
          'It compiles your Kotlin and Java sources in one build. No separate Kotlin installation is needed.',
      }),
    },
    {
      id: 'files',
      title: translate({
        id: 'page.kotlinDsl.start.steps.files.title',
        message: 'Put Kotlin next to your Java',
      }),
      text: translate({
        id: 'page.kotlinDsl.start.steps.files.text',
        message:
          'Kotlin files can go in their own source folder or in the same folder as your Java files.',
      }),
    },
    {
      id: 'view',
      title: translate({
        id: 'page.kotlinDsl.start.steps.view.title',
        message: 'Write a view and compile',
      }),
      text: translate({
        id: 'page.kotlinDsl.start.steps.view.text',
        message:
          'Write a view with the builders. Maven then compiles your Kotlin alongside your Java.',
      }),
    },
  ];
}
