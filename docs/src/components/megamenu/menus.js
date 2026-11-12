import { translate } from '@docusaurus/Translate';

// The content of the navbar's mega menus (rendered by MegaMenu.js). Every
// piece of text goes through translate(), so `npm run write-translations`
// picks it up into i18n/<locale>/code.json and the translation workflow fills
// in the other languages. Each menu's navbar label lives in
// config/navbar-menus.js and is translated through navbar.json.
//
// A menu is either
//   { layout: 'products', products: [...] }: the main list; hovering one
//     shows its preview beside the list. Each product has label, slug (shown
//     as a path above the preview), tone (its colour), tagline, description,
//     cta (link text), to or href, icon, and preview:
//       { type: 'drawing', name }       a flat animated drawing from
//                                       MenuArt.js, in the product's colour
//       { type: 'code', lines: [...] }  code (Java, Kotlin or shell); code is
//                                       never translated
//   { columns: [...] }: titled groups (title, items of label, description,
//     to or href, icon)
//
// "to" links a page on this site, "href" another site (opens in a new tab).
// Icons are named in icons.js. The phone menu lists the same links.
//
// Called while rendering, so translate() sees the page's language.
export function getMenu(name) {
  switch (name) {
    case "products":
      return {
        layout: "products",
        products: [
          {
            label: translate({ id: "component.megaMenu.products.webforJ.label", message: "webforJ" }),
            slug: "webforj",
            tone: "#4d8bff",
            tagline: translate({ id: "component.megaMenu.products.webforJ.tagline", message: "100% Java, all the way" }),
            description: translate({ id: "component.megaMenu.products.webforJ.description", message: "Build the whole web app in Java, UI included. No JavaScript to write and no REST layer between the UI and your backend." }),
            cta: translate({ id: "component.megaMenu.products.webforJ.cta", message: "Get started" }),
            to: "/intro",
            icon: "webforj",
            preview: {
              type: 'code',
              lines: [
                "@Route(\"/\")",
                "public class HelloView extends Composite<FlexLayout> {",
                "  private final FlexLayout self = getBoundComponent();",
                "  private final TextField name = new TextField(\"Name\");",
                "  private final Button greet =",
                "      new Button(\"Greet\", ButtonTheme.PRIMARY);",
                "",
                "  public HelloView() {",
                "    greet.onClick(e -> Toast.show(\"Hello \" + name.getText()));",
                "    self.add(name, greet);",
                "  }",
                "}",
              ],
            },
          },
          {
            label: translate({ id: "component.megaMenu.products.kotlinDSL.label", message: "Kotlin DSL" }),
            slug: "kotlin-dsl",
            tone: "#ff8a3d",
            tagline: translate({ id: "component.megaMenu.products.kotlinDSL.tagline", message: "The same UIs, in Kotlin" }),
            description: translate({ id: "component.megaMenu.products.kotlinDSL.summary", message: "Build webforJ UIs declaratively in Kotlin with type-safe builders and nested blocks, alongside your Java code. In preview." }),
            cta: translate({ id: "component.megaMenu.products.kotlinDSL.cta", message: "Explore the Kotlin DSL" }),
            to: "/kotlin-dsl",
            icon: "kotlin",
            preview: {
              type: 'code',
              lines: [
                "@Route(\"hello\")",
                "class HelloView : Composite<FlexLayout>() {",
                "  private val self = boundComponent",
                "",
                "  init {",
                "    self.apply {",
                "      val name = textField(\"Name\", placeholder = \"Your name\")",
                "      button(\"Greet\", ButtonTheme.PRIMARY) {",
                "        onClick { Toast.show(\"Hello ${name.text}\") }",
                "      }",
                "    }",
                "  }",
                "}",
              ],
            },
          },
          {
            label: translate({ id: "component.megaMenu.products.uIComponents.label", message: "UI Components" }),
            slug: "ui-components",
            tone: "#7c9cff",
            tagline: translate({ id: "component.megaMenu.products.uIComponents.tagline", message: "Ready-made, and built to compose" }),
            description: translate({ id: "component.megaMenu.products.uIComponents.description", message: "Layouts, data entry, navigation, feedback, and visualization, all in Java. Combine them with Composite into your own reusable components, exposing only the API you choose." }),
            cta: translate({ id: "component.megaMenu.products.uIComponents.cta", message: "Explore components" }),
            to: "/ui-components",
            icon: "components",
            preview: { type: "drawing", name: "ui-components" },
          },
          {
            label: translate({ id: "component.megaMenu.products.swingModernization.label", message: "Swing modernization" }),
            slug: "swing-modernization",
            tone: "#2cc3b6",
            tagline: translate({ id: "component.megaMenu.products.swingModernization.tagline", message: "Bring Swing apps to the web" }),
            description: translate({ id: "component.megaMenu.products.swingModernization.description", message: "Run existing Swing, JavaFX, or SWT apps in the browser through Webswing, then move them to webforJ one screen at a time." }),
            cta: translate({ id: "component.megaMenu.products.swingModernization.cta", message: "Start modernizing" }),
            to: "/modernization",
            icon: "webswing",
            preview: { type: "drawing", name: "swing-modernization" },
          },
          {
            label: translate({ id: "component.megaMenu.products.craftforJ.label", message: "craftforJ" }),
            slug: "craftforj",
            tone: "#a07bff",
            tagline: translate({ id: "component.megaMenu.products.craftforJ.tagline", message: "Edit your running app, visually" }),
            description: translate({ id: "component.megaMenu.products.craftforJ.description", message: "Inspect the component tree of a running app, change components live, and write the changes you keep back into your Java source." }),
            cta: translate({ id: "component.megaMenu.products.craftforJ.cta", message: "Discover craftforJ" }),
            to: "/craftforj-tool",
            icon: "craftforj",
            preview: { type: "drawing", name: "craftforj" },
          },
          {
            label: translate({ id: "component.megaMenu.products.aITooling.label", message: "AI tooling" }),
            slug: "ai-tooling",
            tone: "#3fb4f0",
            tagline: translate({ id: "component.megaMenu.products.aITooling.tagline", message: "Teach your AI assistant webforJ" }),
            description: translate({ id: "component.megaMenu.products.aITooling.description", message: "One plugin gives Claude Code, Copilot, Cursor, Gemini, and Codex live webforJ docs, project scaffolding, theme generation, and workflows that follow webforJ conventions." }),
            cta: translate({ id: "component.megaMenu.products.aITooling.cta", message: "Install the AI plugin" }),
            to: "/ai",
            icon: "ai",
            preview: {
              type: 'code',
              lines: [
                "# Claude Code",
                "claude plugin marketplace add webforj/webforj-ai",
                "claude plugin install webforj@webforj-ai",
                "",
                "# GitHub Copilot CLI",
                "copilot plugin marketplace add webforj/webforj-ai",
                "copilot plugin install webforj@webforj-ai",
              ],
            },
          },
          {
            label: translate({ id: "component.megaMenu.products.mCPApps.label", message: "MCP Apps" }),
            slug: "mcp-apps",
            tone: "#3ecf8e",
            tagline: translate({ id: "component.megaMenu.products.mCPApps.tagline", message: "Your webforJ views, inside AI apps" }),
            description: translate({ id: "component.megaMenu.products.mCPApps.summary", message: "Let an MCP-capable AI app open a routed webforJ view in its conversation, where the person and the AI work with the same live UI. In preview." }),
            cta: translate({ id: "component.megaMenu.products.mCPApps.cta", message: "Explore MCP Apps" }),
            to: "/mcp-apps",
            icon: "mcpApps",
            preview: { type: "drawing", name: "mcp-apps" },
          },
          {
            label: translate({ id: "component.megaMenu.products.figmaDesignKit.label", message: "Figma design kit" }),
            slug: "design-kit",
            tone: "#e06ad0",
            tagline: translate({ id: "component.megaMenu.products.figmaDesignKit.tagline", message: "Design with the real components" }),
            description: translate({ id: "component.megaMenu.products.figmaDesignKit.description", message: "The DWC Design Kit brings the webforJ components to Figma, so what you design matches what you build." }),
            cta: translate({ id: "component.megaMenu.products.figmaDesignKit.cta", message: "Open the design kit" }),
            to: "/design-kit",
            icon: "design",
            preview: { type: "drawing", name: "design-kit" },
          },
        ],
      };
    case "developers":
      return {
        columns: [
          {
            title: translate({ id: "component.megaMenu.developers.learn.title", message: "Learn" }),
            items: [
              {
                label: translate({ id: "component.megaMenu.developers.learn.gettingStarted.label", message: "Getting started" }),
                description: translate({ id: "component.megaMenu.developers.learn.gettingStarted.description", message: "Create and run your first app" }),
                to: "/docs/introduction/getting-started",
                icon: "start",
              },
              {
                label: translate({ id: "component.megaMenu.developers.learn.tutorial.label", message: "Tutorial" }),
                description: translate({ id: "component.megaMenu.developers.learn.tutorial.description", message: "Build a complete app, step by step" }),
                to: "/docs/introduction/tutorial/overview",
                icon: "tutorial",
              },
              {
                label: translate({ id: "component.megaMenu.developers.learn.appBasics.label", message: "App basics" }),
                description: translate({ id: "component.megaMenu.developers.learn.appBasics.description", message: "How a webforJ app fits together" }),
                to: "/docs/introduction/basics",
                icon: "basics",
              },
              {
                label: translate({ id: "component.megaMenu.developers.learn.components.label", message: "Components" }),
                description: translate({ id: "component.megaMenu.developers.learn.components.description", message: "Every component, with live demos" }),
                to: "/docs/components/overview",
                icon: "components",
              },
              {
                label: translate({ id: "component.megaMenu.developers.learn.cookbook.label", message: "Cookbook" }),
                description: translate({ id: "component.megaMenu.developers.learn.cookbook.description", message: "Short recipes for everyday tasks" }),
                to: "/cookbook",
                icon: "cookbook",
              },
              {
                label: translate({ id: "component.megaMenu.developers.learn.javaDocs.label", message: "JavaDocs" }),
                description: translate({ id: "component.megaMenu.developers.learn.javaDocs.description", message: "The complete Java API" }),
                href: "https://javadoc.io/doc/com.webforj",
                icon: "apiDocs",
              },
              {
                label: translate({ id: "component.megaMenu.developers.learn.clientComponents.label", message: "Client Components" }),
                description: translate({ id: "component.megaMenu.developers.learn.clientComponents.description", message: "Parts and properties for styling" }),
                to: "/docs/client-components/overview",
                icon: "web",
              },
            ],
          },
          {
            title: translate({ id: "component.megaMenu.developers.buildAndShip.title", message: "Build and ship" }),
            items: [
              {
                label: translate({ id: "component.megaMenu.developers.buildAndShip.startforJ.label", message: "startforJ" }),
                description: translate({ id: "component.megaMenu.developers.buildAndShip.startforJ.description", message: "Generate a new project in the browser" }),
                href: "https://docs.webforj.com/startforj/",
                icon: "rocket",
              },
              {
                label: translate({ id: "component.megaMenu.developers.buildAndShip.springBoot.label", message: "Spring Boot" }),
                description: translate({ id: "component.megaMenu.developers.buildAndShip.springBoot.description", message: "Dependency injection, Spring Data and more" }),
                to: "/docs/integrations/spring/overview",
                icon: "spring",
              },
              {
                label: translate({ id: "component.megaMenu.developers.buildAndShip.jBang.label", message: "JBang" }),
                description: translate({ id: "component.megaMenu.developers.buildAndShip.jBang.description", message: "Run a webforJ app from a single file" }),
                to: "/docs/integrations/jbang",
                icon: "script",
              },
              {
                label: translate({ id: "component.megaMenu.developers.buildAndShip.hotswap.label", message: "Hotswap" }),
                description: translate({ id: "component.megaMenu.developers.buildAndShip.hotswap.description", message: "Apply code changes without a restart" }),
                to: "/docs/configuration/deploy-reload/hotswap",
                icon: "reload",
              },
              {
                label: translate({ id: "component.megaMenu.developers.buildAndShip.browserlessTesting.label", message: "Browserless testing" }),
                description: translate({ id: "component.megaMenu.developers.buildAndShip.browserlessTesting.description", message: "Test views in JUnit, no browser needed" }),
                to: "/docs/testing/testbed/overview",
                icon: "testing",
              },
              {
                label: translate({ id: "component.megaMenu.developers.buildAndShip.bBjShip.label", message: "BBjShip" }),
                description: translate({ id: "component.megaMenu.developers.buildAndShip.bBjShip.description", message: "Deploy apps to BBjServices" }),
                to: "/docs/configuration/bbjship/overview",
                icon: "deploy",
              },
              {
                label: translate({ id: "component.megaMenu.developers.buildAndShip.automatedUpgrades.label", message: "Automated upgrades" }),
                description: translate({ id: "component.megaMenu.developers.buildAndShip.automatedUpgrades.description", message: "Move to a new release with OpenRewrite" }),
                to: "/docs/upgrading/automated-upgrades",
                icon: "upgrade",
              },
            ],
          },
        ],
      };
    case "resources":
      return {
        columns: [
          {
            title: translate({ id: "component.megaMenu.resources.readAndWatch.title", message: "Read and watch" }),
            items: [
              {
                label: translate({ id: "component.megaMenu.resources.readAndWatch.blog.label", message: "Blog" }),
                description: translate({ id: "component.megaMenu.resources.readAndWatch.blog.description", message: "Releases, tutorials and deep dives" }),
                to: "/blog",
                icon: "blog",
              },
              {
                label: translate({ id: "component.megaMenu.resources.readAndWatch.showcase.label", message: "Showcase" }),
                description: translate({ id: "component.megaMenu.resources.readAndWatch.showcase.description", message: "What you can build with webforJ" }),
                to: "/showcase",
                icon: "components",
              },
              {
                label: translate({ id: "component.megaMenu.resources.readAndWatch.successStories.label", message: "Success stories" }),
                description: translate({ id: "component.megaMenu.resources.readAndWatch.successStories.description", message: "Teams building with webforJ" }),
                href: "https://webforj.com/success-stories/",
                icon: "stories",
              },
              {
                label: translate({ id: "component.megaMenu.resources.readAndWatch.whitepapers.label", message: "Whitepapers" }),
                description: translate({ id: "component.megaMenu.resources.readAndWatch.whitepapers.description", message: "In-depth papers on webforJ" }),
                href: "https://webforj.com/whitepapers/",
                icon: "whitepapers",
              },
              {
                label: translate({ id: "component.megaMenu.resources.readAndWatch.webinars.label", message: "Webinars" }),
                description: translate({ id: "component.megaMenu.resources.readAndWatch.webinars.description", message: "Live sessions and recordings" }),
                href: "https://webforj.com/webinars/",
                icon: "webinars",
              },
            ],
          },
          {
            title: translate({ id: "component.megaMenu.resources.connect.title", message: "Connect" }),
            items: [
              {
                label: translate({ id: "component.megaMenu.resources.connect.events.label", message: "Events" }),
                description: translate({ id: "component.megaMenu.resources.connect.events.description", message: "Talks, conferences and meetups" }),
                href: "https://webforj.com/events",
                icon: "events",
              },
              {
                label: translate({ id: "component.megaMenu.resources.connect.youTube.label", message: "YouTube" }),
                description: translate({ id: "component.megaMenu.resources.connect.youTube.description", message: "Videos and walkthroughs" }),
                href: "https://www.youtube.com/@webforJ",
                icon: "youtube",
              },
              {
                label: translate({ id: "component.megaMenu.resources.connect.linkedIn.label", message: "LinkedIn" }),
                description: translate({ id: "component.megaMenu.resources.connect.linkedIn.description", message: "News from the team" }),
                href: "https://www.linkedin.com/company/webforj",
                icon: "linkedin",
              },
              {
                label: translate({ id: "component.megaMenu.resources.connect.gitHub.label", message: "GitHub" }),
                description: translate({ id: "component.megaMenu.resources.connect.gitHub.description", message: "Source code and releases" }),
                href: "https://github.com/webforj",
                icon: "github",
              },
            ],
          },
        ],
      };
    case "support":
      return {
        columns: [
          {
            title: translate({ id: "component.megaMenu.support.getHelp.title", message: "Get help" }),
            items: [
              {
                label: translate({ id: "component.megaMenu.support.getHelp.reportAnIssue.label", message: "Report an issue" }),
                description: translate({ id: "component.megaMenu.support.getHelp.reportAnIssue.description", message: "Bugs and feature requests" }),
                href: "https://github.com/webforj/webforj/issues/new/choose",
                icon: "bug",
              },
              {
                label: translate({ id: "component.megaMenu.support.getHelp.emailSupport.label", message: "Email support" }),
                description: translate({ id: "component.megaMenu.support.getHelp.emailSupport.description", message: "support@basis.cloud" }),
                href: "mailto:support@basis.cloud",
                icon: "support",
              },
              {
                label: translate({ id: "component.megaMenu.support.getHelp.contactUs.label", message: "Contact us" }),
                description: translate({ id: "component.megaMenu.support.getHelp.contactUs.description", message: "Book a call with the webforJ team" }),
                href: "https://webforj.com/schedule-discovery/",
                icon: "mail",
              },
            ],
          },
          {
            title: translate({ id: "component.megaMenu.support.workWithUs.title", message: "Work with us" }),
            items: [
              {
                label: translate({ id: "component.megaMenu.support.workWithUs.lTSPlusSupportPlans.label", message: "LTS+ support plans" }),
                description: translate({ id: "component.megaMenu.support.workWithUs.lTSPlusSupportPlans.description", message: "Long-term support for production" }),
                href: "https://webforj.com/pricing/",
                icon: "plans",
              },
              {
                label: translate({ id: "component.megaMenu.support.workWithUs.partners.label", message: "Partners" }),
                description: translate({ id: "component.megaMenu.support.workWithUs.partners.description", message: "Find a webforJ partner" }),
                href: "https://webforj.com/partners/",
                icon: "partners",
              },
              {
                label: translate({ id: "component.megaMenu.support.workWithUs.company.label", message: "Company" }),
                description: translate({ id: "component.megaMenu.support.workWithUs.company.description", message: "BASIS, the team behind webforJ" }),
                href: "https://webforj.com/company/",
                icon: "company",
              },
            ],
          },
        ],
      };
    default:
      return null;
  }
}
