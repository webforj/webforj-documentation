import React from 'react';
import Admonition from '@theme/Admonition';
import Link from '@docusaurus/Link';
import Translate, { translate } from '@docusaurus/Translate';
import AutoAwesomeIcon from '@mui/icons-material/AutoAwesome';

const SETUP_PATH = '/docs/integrations/ai-tooling/agent-skills';

// Single source of truth for skill metadata. Keep in sync with the table
// rendered by docs/integrations/ai-tooling/agent-skills.md.
function getSkills() {
  return {
    'webforj-adding-servlets': {
      summary: translate({
        id: 'aiSkillTip.skill.webforj-adding-servlets.summary',
        message: 'add REST endpoints, webhooks, and custom servlets',
        description: 'Summary for the webforj-adding-servlets skill',
      }),
      prompts: [
        translate({
          id: 'aiSkillTip.skill.webforj-adding-servlets.prompt.0',
          message: 'Add a REST endpoint at /api/orders.',
          description: 'Sample prompt for the webforj-adding-servlets skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-adding-servlets.prompt.1',
          message: 'Wire up a webhook handler for Stripe.',
          description: 'Sample prompt for the webforj-adding-servlets skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-adding-servlets.prompt.2',
          message: 'Mount Swagger UI at /api/docs.',
          description: 'Sample prompt for the webforj-adding-servlets skill',
        }),
      ],
    },
    'webforj-building-forms': {
      summary: translate({
        id: 'aiSkillTip.skill.webforj-building-forms.summary',
        message: 'build forms with binding, validation, and input masks',
        description: 'Summary for the webforj-building-forms skill',
      }),
      prompts: [
        translate({
          id: 'aiSkillTip.skill.webforj-building-forms.prompt.0',
          message: 'Build a registration form bound to my User bean.',
          description: 'Sample prompt for the webforj-building-forms skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-building-forms.prompt.1',
          message: 'Add a phone number input with format-as-you-type.',
          description: 'Sample prompt for the webforj-building-forms skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-building-forms.prompt.2',
          message: 'Format this Table column as currency.',
          description: 'Sample prompt for the webforj-building-forms skill',
        }),
      ],
    },
    'webforj-creating-components': {
      summary: translate({
        id: 'aiSkillTip.skill.webforj-creating-components.summary',
        message: 'wrap web components, JS libraries, or compositions as reusable webforJ components',
        description: 'Summary for the webforj-creating-components skill',
      }),
      prompts: [
        translate({
          id: 'aiSkillTip.skill.webforj-creating-components.prompt.0',
          message: 'Wrap this Custom Element library as webforJ components.',
          description: 'Sample prompt for the webforj-creating-components skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-creating-components.prompt.1',
          message: 'Compose these webforJ components into a reusable card.',
          description: 'Sample prompt for the webforj-creating-components skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-creating-components.prompt.2',
          message: 'Integrate this plain JavaScript library as a webforJ component.',
          description: 'Sample prompt for the webforj-creating-components skill',
        }),
      ],
    },
    'webforj-handling-timers-and-async': {
      summary: translate({
        id: 'aiSkillTip.skill.webforj-handling-timers-and-async.summary',
        message: 'schedule timers, debouncers, and async work safely on the UI thread',
        description: 'Summary for the webforj-handling-timers-and-async skill',
      }),
      prompts: [
        translate({
          id: 'aiSkillTip.skill.webforj-handling-timers-and-async.prompt.0',
          message: 'Refresh this dashboard every 30 seconds.',
          description: 'Sample prompt for the webforj-handling-timers-and-async skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-handling-timers-and-async.prompt.1',
          message: 'Add a search-as-you-type debouncer.',
          description: 'Sample prompt for the webforj-handling-timers-and-async skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-handling-timers-and-async.prompt.2',
          message: 'Run this CPU-heavy work in the background and update the progress bar.',
          description: 'Sample prompt for the webforj-handling-timers-and-async skill',
        }),
      ],
    },
    'webforj-localizing-apps': {
      summary: translate({
        id: 'aiSkillTip.skill.webforj-localizing-apps.summary',
        message: 'add multi-language support and translate component labels',
        description: 'Summary for the webforj-localizing-apps skill',
      }),
      prompts: [
        translate({
          id: 'aiSkillTip.skill.webforj-localizing-apps.prompt.0',
          message: 'Add multi-language support with English and Spanish.',
          description: 'Sample prompt for the webforj-localizing-apps skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-localizing-apps.prompt.1',
          message: "Detect the user's browser locale and apply it on startup.",
          description: 'Sample prompt for the webforj-localizing-apps skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-localizing-apps.prompt.2',
          message: 'Move all hardcoded strings into a messages bundle.',
          description: 'Sample prompt for the webforj-localizing-apps skill',
        }),
      ],
    },
    'webforj-securing-apps': {
      summary: translate({
        id: 'aiSkillTip.skill.webforj-securing-apps.summary',
        message: 'protect routes with login, role-based access, and ownership checks',
        description: 'Summary for the webforj-securing-apps skill',
      }),
      prompts: [
        translate({
          id: 'aiSkillTip.skill.webforj-securing-apps.prompt.0',
          message: 'Protect /admin so only users with the ADMIN role can see it.',
          description: 'Sample prompt for the webforj-securing-apps skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-securing-apps.prompt.1',
          message: 'Add a public landing page that anyone can visit.',
          description: 'Sample prompt for the webforj-securing-apps skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-securing-apps.prompt.2',
          message: 'Only let a user edit a record they own.',
          description: 'Sample prompt for the webforj-securing-apps skill',
        }),
      ],
    },
    'webforj-styling-apps': {
      summary: translate({
        id: 'aiSkillTip.skill.webforj-styling-apps.summary',
        message: 'theme and style webforJ apps with DWC design tokens',
        description: 'Summary for the webforj-styling-apps skill',
      }),
      prompts: [
        translate({
          id: 'aiSkillTip.skill.webforj-styling-apps.prompt.0',
          message: 'Theme this app with a blue palette.',
          description: 'Sample prompt for the webforj-styling-apps skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-styling-apps.prompt.1',
          message: 'Style the dwc-button to match the brand guidelines.',
          description: 'Sample prompt for the webforj-styling-apps skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-styling-apps.prompt.2',
          message: 'Make this layout tighter - adjust spacing and typography.',
          description: 'Sample prompt for the webforj-styling-apps skill',
        }),
      ],
    },
    'webforj-upgrading-versions': {
      summary: translate({
        id: 'aiSkillTip.skill.webforj-upgrading-versions.summary',
        message: 'upgrade across webforJ major versions with OpenRewrite',
        description: 'Summary for the webforj-upgrading-versions skill',
      }),
      prompts: [
        translate({
          id: 'aiSkillTip.skill.webforj-upgrading-versions.prompt.0',
          message: 'Upgrade this app from webforJ 25 to 26.',
          description: 'Sample prompt for the webforj-upgrading-versions skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-upgrading-versions.prompt.1',
          message: 'Run the rewrite recipe and resolve the TODOs.',
          description: 'Sample prompt for the webforj-upgrading-versions skill',
        }),
        translate({
          id: 'aiSkillTip.skill.webforj-upgrading-versions.prompt.2',
          message: 'What removed APIs do I need to fix after upgrading?',
          description: 'Sample prompt for the webforj-upgrading-versions skill',
        }),
      ],
    },
  };
}

/**
 * Callout that flags a section of the docs as covered by a webforJ Agent
 * Skill. Tells the user the skill exists, links to the AI plugin setup
 * page, and shows sample prompts they can hand to their assistant.
 *
 * Usage:
 *   <AISkillTip skill="webforj-styling-apps" />
 *
 * Optional overrides:
 *   <AISkillTip skill="webforj-styling-apps" prompts={["Theme it red."]} />
 *   <AISkillTip skill="webforj-styling-apps" title="Let your AI do this" />
 */
export default function AISkillTip({ skill, prompts: customPrompts, title: customTitle }) {
  const data = getSkills()[skill];
  if (!data) {
    if (typeof console !== 'undefined') {
      console.error(`AISkillTip: unknown skill "${skill}"`);
    }
    return null;
  }
  const prompts = customPrompts ?? data.prompts;
  const title = customTitle ?? translate({
    id: 'aiSkillTip.title',
    message: 'AI skill available',
    description: 'Default title for the AI skill callout admonition',
  });

  return (
    <Admonition type="tip" icon={<AutoAwesomeIcon fontSize="small" />} title={title}>
      <p>
        <Translate
          id="aiSkillTip.body"
          description="Body text of the AI skill callout; {skill} is the skill ID code, {summary} is what the skill can do, {setupLink} links to the setup page"
          values={{
            skill: <code>{skill}</code>,
            summary: data.summary,
            setupLink: (
              <Link to={SETUP_PATH}>
                <Translate
                  id="aiSkillTip.setupLinkText"
                  description="Link text inside the AI skill callout body"
                >
                  installing the webforJ AI plugin
                </Translate>
              </Link>
            ),
          }}
        >
          {'The {skill} skill can {summary}. After {setupLink}, ask your assistant:'}
        </Translate>
      </p>
      <ul>
        {prompts.map((p, i) => (
          <li key={i}>
            <em>"{p}"</em>
          </li>
        ))}
      </ul>
    </Admonition>
  );
}
