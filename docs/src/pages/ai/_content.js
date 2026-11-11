import { translate } from '@docusaurus/Translate';

// Content of the AI tooling page. Every command, prompt, skill name and
// code sample is copied from the AI tooling docs, the TestBed docs and the
// Claude Code blog post. The get* functions are called while rendering, so
// translate() sees the language of the page.

export function getClients() {
  return [
    {
      id: 'claude',
      label: 'Claude Code',
      lines: [
        { kind: 'cmd', text: 'claude plugin marketplace add webforj/webforj-ai' },
        { kind: 'cmd', text: 'claude plugin install webforj@webforj-ai' },
        {
          kind: 'note',
          text: translate({
            id: 'page.ai.terminal.verifyClaude',
            message: 'Verify inside Claude Code',
          }),
        },
        { kind: 'slash', text: '/plugin' },
        { kind: 'slash', text: '/mcp' },
        {
          kind: 'ok',
          text: translate({
            id: 'page.ai.terminal.okInstalled',
            message: 'webforj appears under Installed',
          }),
        },
        {
          kind: 'ok',
          text: translate({
            id: 'page.ai.terminal.okServers',
            message: 'plugin:webforj:webforj-mcp appears under connected servers',
          }),
        },
      ],
    },
    {
      id: 'copilot',
      label: 'Copilot CLI',
      lines: [
        { kind: 'cmd', text: 'copilot plugin marketplace add webforj/webforj-ai' },
        { kind: 'cmd', text: 'copilot plugin install webforj@webforj-ai' },
        {
          kind: 'note',
          text: translate({ id: 'page.ai.terminal.copilotVerify', message: 'Verify' }),
        },
        { kind: 'cmd', text: 'copilot plugin list' },
      ],
    },
    {
      id: 'gemini',
      label: 'Gemini CLI',
      lines: [
        { kind: 'cmd', text: 'gemini extensions install https://github.com/webforj/webforj-ai' },
        {
          kind: 'note',
          text: translate({ id: 'page.ai.terminal.geminiVerify', message: 'Verify' }),
        },
        { kind: 'cmd', text: 'gemini extensions list' },
      ],
    },
    {
      id: 'codex',
      label: 'Codex CLI',
      lines: [
        { kind: 'cmd', text: 'codex plugin marketplace add webforj/webforj-ai' },
        {
          kind: 'note',
          text: translate({
            id: 'page.ai.terminal.codexRun',
            message: 'Then run /plugins in a Codex session, select webforj and press Space',
          }),
        },
        {
          kind: 'note',
          text: translate({
            id: 'page.ai.terminal.codexSkills',
            message: 'Codex skills are invoked explicitly',
          }),
        },
        { kind: 'slash', text: '$webforj:webforj-styling-apps theme this app with a blue palette' },
      ],
    },
  ];
}

// The install commands of the plugin in Claude Code
export const INSTALL_COMMANDS = `claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai`;

export function getBreaks() {
  return [
    {
      failure: translate({
        id: 'page.ai.breaks.api.failure',
        message: 'It calls an API that does not exist, or used to.',
      }),
      piece: translate({ id: 'page.ai.breaks.api.piece', message: 'The real API' }),
      fix: translate({
        id: 'page.ai.breaks.api.fix',
        message:
          'The MCP server searches the webforJ knowledge base, so the agent reads signatures instead of recalling them.',
      }),
      to: '/docs/ai-tooling/mcp',
    },
    {
      failure: translate({
        id: 'page.ai.breaks.workflow.failure',
        message: 'It follows generic Java habits instead of the conventions of the framework.',
      }),
      piece: translate({ id: 'page.ai.breaks.workflow.piece', message: 'The right workflow' }),
      fix: translate({
        id: 'page.ai.breaks.workflow.fix',
        message:
          'Agent Skills tell the agent how to approach forms, security, styling, timers, and more.',
      }),
      to: '/docs/ai-tooling/agent-skills',
    },
    {
      failure: translate({
        id: 'page.ai.breaks.check.failure',
        message: 'It produces code that compiles and does nothing useful when a user clicks.',
      }),
      piece: translate({ id: 'page.ai.breaks.check.piece', message: 'A pass or fail' }),
      fix: translate({
        id: 'page.ai.breaks.check.fix',
        message:
          'The TestBed runs a view in a plain JUnit test, so the agent can prove its own work.',
      }),
      to: '/docs/testing/testbed/overview',
    },
  ];
}

// The three stages of the loop, in order
export function getStages() {
  return [
    {
      id: 'write',
      label: translate({ id: 'page.ai.stages.write.label', message: 'Write' }),
      short: translate({ id: 'page.ai.stages.write.short', message: 'The agent writes the view' }),
    },
    {
      id: 'check',
      label: translate({ id: 'page.ai.stages.check.label', message: 'Check' }),
      short: translate({
        id: 'page.ai.stages.check.short',
        message: 'It writes and runs the test',
      }),
    },
    {
      id: 'review',
      label: translate({ id: 'page.ai.stages.review.label', message: 'Review' }),
      short: translate({
        id: 'page.ai.stages.review.short',
        message: 'You review a result that passed',
      }),
    },
  ];
}

export const GREETING_VIEW = `@Route("greeting")
public class GreetingView extends Composite<Div> {
  private final TextField name = new TextField("Name");
  private final Paragraph message = new Paragraph();
  private final Button greet =
      new Button("Greet", ButtonTheme.PRIMARY, e -> greet());

  public GreetingView() {
    getBoundComponent().add(name, greet, message);
  }

  private void greet() {
    message.setText("Hello, " + name.getValue() + "!");
  }
}`;

export const GREETING_TEST = `@WebforjTest
class GreetingViewTest {

  @Test
  void shouldGreetTheNameTheUserTyped(WebforjTestbed context) {
    context.add(new GreetingView());

    var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();
    var greet = ComponentLocator.of(Button.class).byText("Greet").locate();

    UserInteraction.type(name, "Ada");
    UserInteraction.click(greet);

    verifyThat(ComponentLocator.of(Paragraph.class).locate())
        .hasText("Hello, Ada!");
  }
}`;

export const TESTBED_DEPENDENCY = `<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-testbed-junit</artifactId>
  <scope>test</scope>
</dependency>`;

export const CRAFTFORJ_CONF = `webforj.debug = true
webforj.devtools.craftforj.enabled = true`;

export const MCP_ONLY = 'claude mcp add webforj-mcp https://mcp.webforj.com/mcp -t http -s user';

export function getPluginClients() {
  return [
    {
      name: 'Claude Code',
      how: translate({ id: 'page.ai.plugin.clients.claude', message: 'Marketplace command' }),
    },
    {
      name: 'GitHub Copilot CLI',
      how: translate({ id: 'page.ai.plugin.clients.copilot', message: 'Marketplace command' }),
    },
    {
      name: 'VS Code + Copilot',
      how: translate({
        id: 'page.ai.plugin.clients.vscode',
        message: 'Chat: Install Plugin From Source',
      }),
    },
    {
      name: 'Gemini CLI',
      how: translate({ id: 'page.ai.plugin.clients.gemini', message: 'Extension install' }),
    },
    {
      name: 'OpenAI Codex CLI',
      how: translate({
        id: 'page.ai.plugin.clients.codex',
        message: 'Marketplace command, then /plugins',
      }),
    },
    {
      name: 'Cursor, Kiro, Goose, Junie, Antigravity',
      how: translate({ id: 'page.ai.plugin.clients.others', message: 'Manual configuration' }),
    },
  ];
}

export function getPluginPrompts() {
  return [
    [
      translate({
        id: 'page.ai.plugin.prompts.wrap.prompt',
        message: 'Wrap this Custom Element library as a webforJ component.',
      }),
      translate({ id: 'page.ai.plugin.prompts.wrap.target', message: 'creating-components skill' }),
    ],
    [
      translate({
        id: 'page.ai.plugin.prompts.style.prompt',
        message: 'Style this view with the DWC design tokens.',
      }),
      translate({ id: 'page.ai.plugin.prompts.style.target', message: 'styling-apps skill' }),
    ],
    [
      translate({
        id: 'page.ai.plugin.prompts.scaffold.prompt',
        message: 'Scaffold a new webforJ sidemenu project called CustomerPortal.',
      }),
      translate({
        id: 'page.ai.plugin.prompts.scaffold.target',
        message: 'MCP project scaffolder',
      }),
    ],
    [
      translate({
        id: 'page.ai.plugin.prompts.theme.prompt',
        message: 'Generate a theme from brand color #6366f1.',
      }),
      translate({ id: 'page.ai.plugin.prompts.theme.target', message: 'MCP theme generator' }),
    ],
    [
      translate({
        id: 'page.ai.plugin.prompts.find.prompt',
        message: 'Find the webforJ docs on @Route and routing.',
      }),
      translate({ id: 'page.ai.plugin.prompts.find.target', message: 'MCP knowledge search' }),
    ],
  ];
}

export function getMcpCapabilities() {
  return [
    {
      title: translate({
        id: 'page.ai.mcp.version.title',
        message: 'Target the right webforJ version',
      }),
      text: translate({
        id: 'page.ai.mcp.version.text',
        message:
          'Reads pom.xml when available and otherwise asks you. Every answer after that is scoped to your version.',
      }),
      prompt: null,
    },
    {
      title: translate({
        id: 'page.ai.mcp.lookup.title',
        message: 'Look things up in the knowledge base',
      }),
      text: translate({
        id: 'page.ai.mcp.lookup.text',
        message:
          'An API question, a guide, a code sample or the Kotlin DSL, answered from the real framework.',
      }),
      prompt: translate({
        id: 'page.ai.mcp.lookup.prompt',
        message: 'How do I set up routing with @Route in webforJ?',
      }),
    },
    {
      title: translate({ id: 'page.ai.mcp.scaffold.title', message: 'Scaffold a new project' }),
      text: translate({
        id: 'page.ai.mcp.scaffold.text',
        message:
          'The correct Maven archetype command from hello-world, blank, tabs or sidemenu, in the webforj or webforj-spring flavor.',
      }),
      prompt: translate({
        id: 'page.ai.mcp.scaffold.prompt',
        message: 'Create a webforJ project called CustomerPortal using the sidemenu archetype',
      }),
    },
    {
      title: translate({ id: 'page.ai.mcp.theme.title', message: 'Generate a DWC theme' }),
      text: translate({
        id: 'page.ai.mcp.theme.text',
        message:
          'Primary, success, warning, danger, info, default and gray palettes from one brand color, with automatic text contrast.',
      }),
      prompt: translate({
        id: 'page.ai.mcp.theme.prompt',
        message: 'Generate a webforJ theme from brand color #6366f1',
      }),
    },
    {
      title: translate({
        id: 'page.ai.mcp.style.title',
        message: 'Style DWC components correctly',
      }),
      text: translate({
        id: 'page.ai.mcp.style.text',
        message:
          'CSS custom properties, shadow parts, reflected attributes and slots, read before any CSS is written.',
      }),
      prompt: translate({
        id: 'page.ai.mcp.style.prompt',
        message: 'What CSS variables and parts does dwc-button expose?',
      }),
    },
    {
      title: translate({ id: 'page.ai.mcp.tokens.title', message: 'Validate design tokens' }),
      text: translate({
        id: 'page.ai.mcp.tokens.text',
        message:
          'Checks CSS, Java or Markdown against the real --dwc-* catalog and flags unknown names with suggested corrections.',
      }),
      prompt: translate({
        id: 'page.ai.mcp.tokens.prompt',
        message: 'Validate app.css for any unknown --dwc-* tokens',
      }),
    },
  ];
}

export function getSkills() {
  return [
    {
      name: 'webforj-adding-servlets',
      text: translate({
        id: 'page.ai.skills.servlets.text',
        message: 'REST endpoints, webhooks and custom servlets next to the webforJ UI.',
      }),
      prompt: translate({
        id: 'page.ai.skills.servlets.prompt',
        message: 'Add a REST endpoint at /api/orders.',
      }),
    },
    {
      name: 'webforj-building-forms',
      text: translate({
        id: 'page.ai.skills.forms.text',
        message: 'Binding, Jakarta validation, masked inputs and responsive form layouts.',
      }),
      prompt: translate({
        id: 'page.ai.skills.forms.prompt',
        message: 'Build a registration form bound to my User bean.',
      }),
    },
    {
      name: 'webforj-creating-components',
      text: translate({
        id: 'page.ai.skills.components.text',
        message:
          'Reusable Java components from Custom Elements, JavaScript libraries or compositions.',
      }),
      prompt: translate({
        id: 'page.ai.skills.components.prompt',
        message: 'Compose these webforJ components into a reusable card.',
      }),
    },
    {
      name: 'webforj-handling-timers-and-async',
      text: translate({
        id: 'page.ai.skills.timers.text',
        message:
          'Interval, Debouncer, Environment.runLater and PendingResult instead of raw timers and threads.',
      }),
      prompt: translate({
        id: 'page.ai.skills.timers.prompt',
        message: 'Refresh this dashboard every 30 seconds.',
      }),
    },
    {
      name: 'webforj-localizing-apps',
      text: translate({
        id: 'page.ai.skills.localizing.text',
        message: 'Message bundles, runtime language switching and browser locale detection.',
      }),
      prompt: translate({
        id: 'page.ai.skills.localizing.prompt',
        message: 'Add multi-language support with English and Spanish.',
      }),
    },
    {
      name: 'webforj-securing-apps',
      text: translate({
        id: 'page.ai.skills.securing.text',
        message: 'Login, logout, role-based access and secure-by-default routes.',
      }),
      prompt: translate({
        id: 'page.ai.skills.securing.prompt',
        message: 'Protect /admin so only users with the ADMIN role can see it.',
      }),
    },
    {
      name: 'webforj-styling-apps',
      text: translate({
        id: 'page.ai.skills.styling.text',
        message: 'Themes, palettes and component styling with every --dwc-* token validated.',
      }),
      prompt: translate({
        id: 'page.ai.skills.styling.prompt',
        message: 'Theme this app with a blue palette.',
      }),
    },
    {
      name: 'webforj-upgrading-versions',
      text: translate({
        id: 'page.ai.skills.upgrading.text',
        message: 'Major version upgrades with the official webforj-rewrite OpenRewrite recipe.',
      }),
      prompt: translate({
        id: 'page.ai.skills.upgrading.prompt',
        message: 'Upgrade this app from webforJ 25 to 26.',
      }),
    },
  ];
}

export function getCompare() {
  return [
    [
      translate({ id: 'page.ai.compare.livesIn.label', message: 'Lives in' }),
      translate({ id: 'page.ai.compare.livesIn.plugin', message: 'Your editor' }),
      translate({ id: 'page.ai.compare.livesIn.assistant', message: 'The running app' }),
    ],
    [
      translate({ id: 'page.ai.compare.reads.label', message: 'Reads' }),
      translate({ id: 'page.ai.compare.reads.plugin', message: 'Your source files' }),
      translate({
        id: 'page.ai.compare.reads.assistant',
        message: 'Your app, live, with its real values',
      }),
    ],
    [
      translate({ id: 'page.ai.compare.verifies.label', message: 'Verifies by' }),
      translate({ id: 'page.ai.compare.verifies.plugin', message: 'Your next build' }),
      translate({
        id: 'page.ai.compare.verifies.assistant',
        message: 'Compiling each edit before you see it',
      }),
    ],
    [
      translate({ id: 'page.ai.compare.suited.label', message: 'Suited to' }),
      translate({
        id: 'page.ai.compare.suited.plugin',
        message: 'Building something new from scratch',
      }),
      translate({
        id: 'page.ai.compare.suited.assistant',
        message: 'Understanding, fixing and prototyping against the app in front of you',
      }),
    ],
  ];
}

export function getDocs() {
  return [
    [
      translate({ id: 'page.ai.docs.plugin.title', message: 'webforJ AI plugin' }),
      translate({ id: 'page.ai.docs.plugin.text', message: 'Install for every client' }),
      '/docs/ai-tooling',
    ],
    [
      translate({ id: 'page.ai.docs.mcp.title', message: 'MCP server' }),
      translate({ id: 'page.ai.docs.mcp.text', message: 'Capabilities and prompts' }),
      '/docs/ai-tooling/mcp',
    ],
    [
      translate({ id: 'page.ai.docs.skills.title', message: 'Agent Skills' }),
      translate({ id: 'page.ai.docs.skills.text', message: 'Every skill and when it loads' }),
      '/docs/ai-tooling/agent-skills',
    ],
    [
      translate({ id: 'page.ai.docs.testbed.title', message: 'TestBed' }),
      translate({ id: 'page.ai.docs.testbed.text', message: 'Browserless tests in plain JUnit' }),
      '/docs/testing/testbed/overview',
    ],
    [
      translate({ id: 'page.ai.docs.craftforj.title', message: 'craftforJ Assistant' }),
      translate({
        id: 'page.ai.docs.craftforj.text',
        message: 'A coding agent in the running app',
      }),
      '/docs/ai-tooling/craftforj-assistant',
    ],
  ];
}
