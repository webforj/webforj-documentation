// Content of the MCP Apps page. Every fact comes from the MCP Apps docs in
// docs/integrations/mcp-apps/, and every snippet is copied from the file
// named in the comment above it.
//
// Text goes through translate() or <Translate>, so the translation workflow
// picks it up into i18n/<locale>/code.json. The get* functions are called
// while rendering, so they see the page's language. Code snippets and API
// names are never translated.

import React from 'react';
import Translate, { translate } from '@docusaurus/Translate';

export function getVideo() {
  return {
    video: 'mcp-apps/webforj-mcp-app',
    width: 1920,
    height: 1080,
    label: translate({
      id: 'page.mcpApps.hero.videoLabel',
      message:
        'A routed webforJ view opened inside an AI conversation, where the person and the AI work with the same live UI.',
    }),
  };
}

// From overview.md, opening-apps.md, actions-updates.md and
// host-interaction.md: the round trip between the host and the app.
// `to` says which way the call goes.
export function getFlow() {
  return [
    {
      key: 'call',
      to: 'app',
      who: translate({ id: 'page.mcpApps.flow.step1Who', message: 'The AI' }),
      text: (
        <Translate id="page.mcpApps.flow.step1Text" values={{ tool: <code>inventory</code> }}>
          {'The host calls the {tool} tool, with input such as a warehouse code.'}
        </Translate>
      ),
    },
    {
      key: 'render',
      to: 'host',
      who: 'webforJ',
      text: (
        <Translate id="page.mcpApps.flow.step2Text" values={{ route: <code>/inventory</code> }}>
          {'webforJ renders the routed view {route}, and the view opens in the conversation.'}
        </Translate>
      ),
    },
    {
      key: 'action',
      to: 'app',
      who: translate({ id: 'page.mcpApps.flow.step3Who', message: 'The AI' }),
      text: (
        <Translate
          id="page.mcpApps.flow.step3Text"
          values={{ tool: <code>inventory_refresh_stock</code> }}>
          {'The AI calls actions such as {tool} on the open view.'}
        </Translate>
      ),
    },
    {
      key: 'message',
      to: 'host',
      who: translate({ id: 'page.mcpApps.flow.step4Who', message: 'The view' }),
      text: (
        <Translate id="page.mcpApps.flow.step4Text">
          {'The view sends messages and model context back to the conversation.'}
        </Translate>
      ),
    },
  ];
}

// docs/integrations/mcp-apps/opening-apps.md, "Apply input after the view opens".
// An excerpt: the annotations and the class line, without the imports and
// the class body. Each line is as the docs have it, "// ..." marks the
// lines left out.
export const MCP_APP = `@Route("/inventory")
@McpApp(
    name = "inventory",
    description = "Shows the current inventory for a warehouse.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {
  // ...
}`;

// From opening-apps.md, "Request a display mode". API names, not translated.
export const DISPLAY_MODES = ['INLINE', 'PIP', 'FULLSCREEN'];

// docs/integrations/mcp-apps/actions-updates.md, "Publish an action"
export const ACTION = `@McpAppAction(description = "Refreshes stock levels for the open warehouse.")
Map<String, Object> refreshStock() {
  warehouse.setText(warehouse.getText() + " - refreshed");
  return Map.of(
      "warehouse", warehouse.getText(),
      "refreshed", true);
}`;

// docs/integrations/mcp-apps/host-interaction.md, "Continue the conversation from the view"
export const SEND_MESSAGE = `Paragraph warehouse = new Paragraph("Warehouse: BER");
Button review = new Button("Review stock");

review.addClickListener(event -> McpHost.ifPresent(host ->
    host.sendMessage("Review the current stock for " + warehouse.getText())));`;

// From host-interaction.md: the other requests and events.
export function getHostMore() {
  return [
    {
      key: 'context',
      title: translate({
        id: 'page.mcpApps.host.contextTitle',
        message: 'Keep the model informed',
      }),
      text: (
        <Translate
          id="page.mcpApps.host.contextText"
          values={{ call: <code>updateModelContext</code> }}>
          {
            '{call} replaces the context the view contributes to the model. Later responses can use it, and no visible message is added.'
          }
        </Translate>
      ),
    },
    {
      key: 'events',
      title: translate({ id: 'page.mcpApps.host.eventsTitle', message: 'Follow the conversation' }),
      text: (
        <Translate
          id="page.mcpApps.host.eventsText"
          values={{
            cancelled: <code>onToolCancelled</code>,
            changed: <code>onHostContextChanged</code>,
          }}>
          {
            'Listeners such as {cancelled} and {changed} let the view react when a tool call is cancelled or the conversation context changes.'
          }
        </Translate>
      ),
    },
    {
      key: 'leave',
      title: translate({ id: 'page.mcpApps.host.leaveTitle', message: 'Leave the frame' }),
      text: (
        <Translate
          id="page.mcpApps.host.leaveText"
          values={{ link: <code>openLink</code>, mode: <code>requestDisplayMode</code> }}>
          {
            '{link} continues on an external page, and {mode} asks for a different presentation. The host decides whether it can satisfy either request.'
          }
        </Translate>
      ),
    },
  ];
}

// From spring.md and without-spring.md: the two ways to publish.
export function getRunWays() {
  return [
    {
      key: 'spring',
      tag: 'Spring Boot',
      title: translate({
        id: 'page.mcpApps.run.springTitle',
        message: 'Spring Boot with Spring AI',
      }),
      text: (
        <Translate id="page.mcpApps.run.springText" values={{ path: <code>/mcp</code> }}>
          {
            'The primary way to publish an MCP App. The integration discovers marked routes and adds them to the Spring AI MCP server, which serves Streamable HTTP at {path} by default. It publishes the tool and a UI resource that opens the route, and installs the servlet filters and session settings needed to embed webforJ.'
          }
        </Translate>
      ),
      to: '/docs/integrations/mcp-apps/spring',
      link: translate({ id: 'page.mcpApps.run.springLink', message: 'Spring Boot setup' }),
    },
    {
      key: 'standard',
      tag: translate({ id: 'page.mcpApps.run.standardTag', message: 'Standard servlet' }),
      title: translate({
        id: 'page.mcpApps.run.standardTitle',
        message: 'A servlet app without Spring',
      }),
      text: (
        <Translate
          id="page.mcpApps.run.standardText"
          values={{ path: <code>/mcp</code>, install: <code>install</code> }}>
          {
            'An initializer scans the package of the routed views, contributes their tools and UI resources to an MCP server, and mounts Streamable HTTP at {path}. One {install} call adds the embed support the host needs.'
          }
        </Translate>
      ),
      to: '/docs/integrations/mcp-apps/without-spring',
      link: translate({ id: 'page.mcpApps.run.standardLink', message: 'Standard setup' }),
    },
  ];
}

// From testing.md, "Verify the app"
export function getBaseline() {
  return [
    <Translate key="connects" id="page.mcpApps.test.baseline1">
      {'The client connects to the MCP endpoint.'}
    </Translate>,
    <Translate
      key="visible"
      id="page.mcpApps.test.baseline2"
      values={{ tool: <code>inventory</code> }}>
      {'The {tool} tool is visible.'}
    </Translate>,
    <Translate
      key="renders"
      id="page.mcpApps.test.baseline3"
      values={{
        tool: <code>inventory</code>,
        // the H1 text of the sample view, as the code renders it
        heading: <strong>Inventory</strong>,
      }}>
      {'Invoking {tool} renders the {heading} heading.'}
    </Translate>,
    <Translate key="interactive" id="page.mcpApps.test.baseline4">
      {'The rendered UI is interactive.'}
    </Translate>,
  ];
}

// From spring.md and testing.md: the path to a first view in a host.
export function getStartSteps() {
  return [
    {
      key: 'module',
      title: translate({ id: 'page.mcpApps.start.step1Title', message: 'Add the MCP Apps module' }),
      text: (
        <Translate
          id="page.mcpApps.start.step1Text"
          values={{ module: <code>webforj-mcp-apps</code> }}>
          {
            'Add the {module} module to a webforJ app. A Spring Boot app also adds the Spring AI MCP server starter.'
          }
        </Translate>
      ),
    },
    {
      key: 'mark',
      title: translate({ id: 'page.mcpApps.start.step2Title', message: 'Mark a routed view' }),
      text: (
        <Translate id="page.mcpApps.start.step2Text" values={{ annotation: <code>@McpApp</code> }}>
          {'Add {annotation} with a description that tells the AI when the tool is useful.'}
        </Translate>
      ),
    },
    {
      key: 'connect',
      title: translate({
        id: 'page.mcpApps.start.step3Title',
        message: 'Start the app and connect a host',
      }),
      text: (
        <Translate id="page.mcpApps.start.step3Text" values={{ path: <code>/mcp</code> }}>
          {'Start the server and add its {path} endpoint to an MCP Apps-capable host.'}
        </Translate>
      ),
    },
    {
      key: 'ask',
      title: translate({ id: 'page.mcpApps.start.step4Title', message: 'Ask the host to open it' }),
      text: (
        <Translate id="page.mcpApps.start.step4Text">
          {'Prompt the host with "Open the inventory app." and the view appears.'}
        </Translate>
      ),
    },
  ];
}

export function getDocs() {
  return [
    {
      to: '/docs/integrations/mcp-apps/overview',
      label: translate({ id: 'page.mcpApps.docs.overviewLabel', message: 'Overview' }),
      text: translate({
        id: 'page.mcpApps.docs.overviewText',
        message: 'What MCP Apps are and where to begin',
      }),
    },
    {
      to: '/docs/integrations/mcp-apps/spring',
      label: translate({ id: 'page.mcpApps.docs.springLabel', message: 'Spring Boot setup' }),
      text: translate({
        id: 'page.mcpApps.docs.springText',
        message: 'Dependencies, the endpoint and a routed app',
      }),
    },
    {
      to: '/docs/integrations/mcp-apps/testing',
      label: translate({ id: 'page.mcpApps.docs.testingLabel', message: 'Testing' }),
      text: translate({
        id: 'page.mcpApps.docs.testingText',
        message: 'Remote clients, a local inspector and the baseline',
      }),
    },
    {
      to: '/docs/integrations/mcp-apps/opening-apps',
      label: translate({ id: 'page.mcpApps.docs.openingLabel', message: 'Opening input' }),
      text: translate({
        id: 'page.mcpApps.docs.openingText',
        message: 'Input schemas, tool names and display modes',
      }),
    },
    {
      to: '/docs/integrations/mcp-apps/actions-updates',
      label: translate({ id: 'page.mcpApps.docs.actionsLabel', message: 'Actions and updates' }),
      text: translate({
        id: 'page.mcpApps.docs.actionsText',
        message: 'Tools that work with the open view',
      }),
    },
    {
      to: '/docs/integrations/mcp-apps/host-interaction',
      label: translate({ id: 'page.mcpApps.docs.hostLabel', message: 'Host interaction' }),
      text: translate({
        id: 'page.mcpApps.docs.hostText',
        message: 'Messages, model context and host events',
      }),
    },
    {
      to: '/docs/integrations/mcp-apps/configuration',
      label: translate({ id: 'page.mcpApps.docs.configLabel', message: 'Configuration' }),
      text: translate({
        id: 'page.mcpApps.docs.configText',
        message: 'The app origin, client origins and browser domains',
      }),
    },
    {
      to: '/docs/integrations/mcp-apps/without-spring',
      label: translate({ id: 'page.mcpApps.docs.standardLabel', message: 'Standard setup' }),
      text: translate({
        id: 'page.mcpApps.docs.standardText',
        message: 'An MCP server in a servlet app without Spring',
      }),
    },
  ];
}
