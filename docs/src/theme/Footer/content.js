import { translate } from '@docusaurus/Translate';

// The footer's content (rendered by index.js). Every piece of text goes
// through translate(), so `npm run write-translations` picks it up into
// i18n/<locale>/code.json and the translation workflow fills in the other
// languages.
//
// pitch: the line beside the logo
// actions: the buttons beside it ({ label, href, primary })
// columns: titled link groups ({ title, items of label and to or href })
//
// "to" links a page on this site, "href" another site (opens in a new tab).
// The social icons under the pitch come from src/components/connect/links.js.
//
// Called while rendering, so translate() sees the page's language.
export function getFooter() {
  return {
    pitch: translate({ id: "component.footer.pitch", message: "Do it all in Java. The full-stack Java framework for business web apps." }),
    actions: [
      { label: translate({ id: "component.footer.action.startYourApp", message: "Start your app" }), href: "https://docs.webforj.com/startforj/", primary: true },
      { label: translate({ id: "component.footer.action.contactUs", message: "Contact us" }), href: "https://webforj.com/schedule-discovery/" },
    ],
    columns: [
      {
        title: translate({ id: "component.footer.products.title", message: "Products" }),
        items: [
          { label: translate({ id: "component.footer.products.webforJ", message: "webforJ" }), to: "/docs/introduction/getting-started" },
          { label: translate({ id: "component.footer.products.kotlinDSL", message: "Kotlin DSL" }), to: "/docs/integrations/kotlin-dsl/overview" },
          { label: translate({ id: "component.footer.products.uIComponents", message: "UI Components" }), to: "/docs/components/overview" },
          { label: translate({ id: "component.footer.products.swingModernization", message: "Swing modernization" }), to: "/docs/integrations/webswing/overview" },
          { label: translate({ id: "component.footer.products.craftforJ", message: "craftforJ" }), to: "/docs/craftforj" },
          { label: translate({ id: "component.footer.products.aITooling", message: "AI tooling" }), to: "/docs/ai-tooling" },
          { label: translate({ id: "component.footer.products.mCPApps", message: "MCP Apps" }), to: "/docs/integrations/mcp-apps/overview" },
          { label: translate({ id: "component.footer.products.figmaDesignKit", message: "Figma design kit" }), href: "https://www.figma.com/community/file/1682060886525639971/dwc-design-kit" },
        ],
      },
      {
        title: translate({ id: "component.footer.developers.title", message: "Developers" }),
        items: [
          { label: translate({ id: "component.footer.developers.gettingStarted", message: "Getting started" }), to: "/docs/introduction/getting-started" },
          { label: translate({ id: "component.footer.developers.tutorial", message: "Tutorial" }), to: "/docs/introduction/tutorial/overview" },
          { label: translate({ id: "component.footer.developers.cookbook", message: "Cookbook" }), to: "/cookbook" },
          { label: translate({ id: "component.footer.developers.javaDocs", message: "JavaDocs" }), href: "https://javadoc.io/doc/com.webforj" },
          { label: translate({ id: "component.footer.developers.startforJ", message: "startforJ" }), href: "https://docs.webforj.com/startforj/" },
          { label: translate({ id: "component.footer.developers.springBoot", message: "Spring Boot" }), to: "/docs/integrations/spring/overview" },
          { label: translate({ id: "component.footer.developers.hotswap", message: "Hotswap" }), to: "/docs/configuration/deploy-reload/hotswap" },
          { label: translate({ id: "component.footer.developers.browserlessTesting", message: "Browserless testing" }), to: "/docs/testing/testbed/overview" },
        ],
      },
      {
        title: translate({ id: "component.footer.resources.title", message: "Resources" }),
        items: [
          { label: translate({ id: "component.footer.resources.blog", message: "Blog" }), to: "/blog" },
          { label: translate({ id: "component.footer.resources.successStories", message: "Success stories" }), href: "https://webforj.com/success-stories/" },
          { label: translate({ id: "component.footer.resources.whitepapers", message: "Whitepapers" }), href: "https://webforj.com/whitepapers/" },
          { label: translate({ id: "component.footer.resources.webinars", message: "Webinars" }), href: "https://webforj.com/webinars/" },
          { label: translate({ id: "component.footer.resources.events", message: "Events" }), href: "https://webforj.com/events" },
        ],
      },
      {
        title: translate({ id: "component.footer.support.title", message: "Support" }),
        items: [
          { label: translate({ id: "component.footer.support.reportAnIssue", message: "Report an issue" }), href: "https://github.com/webforj/webforj/issues/new/choose" },
          { label: translate({ id: "component.footer.support.emailSupport", message: "Email support" }), href: "mailto:support@basis.cloud" },
          { label: translate({ id: "component.footer.support.contactUs", message: "Contact us" }), href: "https://webforj.com/schedule-discovery/" },
          { label: translate({ id: "component.footer.support.lTSPlusSupportPlans", message: "LTS+ support plans" }), href: "https://webforj.com/pricing/" },
          { label: translate({ id: "component.footer.support.partners", message: "Partners" }), href: "https://webforj.com/partners/" },
        ],
      },
      {
        title: translate({ id: "component.footer.company.title", message: "Company" }),
        items: [
          { label: translate({ id: "component.footer.company.aboutBASIS", message: "About BASIS" }), href: "https://webforj.com/company/" },
          { label: translate({ id: "component.footer.company.pricing", message: "Pricing" }), href: "https://webforj.com/pricing/" },
          { label: translate({ id: "component.footer.company.webforjCom", message: "webforj.com" }), href: "https://webforj.com/" },
          { label: translate({ id: "component.footer.company.legal", message: "Legal" }), href: "https://webforj.com/legal/" },
        ],
      },
    ],
  };
}
