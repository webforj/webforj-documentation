import React from 'react';
import Admonition from '@theme/Admonition';
import Link from '@docusaurus/Link';
import AutoAwesomeIcon from '@mui/icons-material/AutoAwesome';

// Single source of truth for skill metadata. Keep in sync with the table
// rendered by docs/integrations/ai-tooling/agent-skills.md.
const SKILLS = {
  'webforj-adding-servlets': {
    summary: 'add REST endpoints, webhooks, and custom servlets',
    prompts: [
      'Add a REST endpoint at /api/orders.',
      'Wire up a webhook handler for Stripe.',
      'Mount Swagger UI at /api/docs.',
    ],
  },
  'webforj-building-forms': {
    summary: 'build forms with binding, validation, and input masks',
    prompts: [
      'Build a registration form bound to my User bean.',
      'Add a phone number input with format-as-you-type.',
      'Format this Table column as currency.',
    ],
  },
  'webforj-creating-components': {
    summary: 'wrap web components, JS libraries, or compositions as reusable webforJ components',
    prompts: [
      'Wrap this Custom Element library as webforJ components.',
      'Compose these webforJ components into a reusable card.',
      'Integrate this plain JavaScript library as a webforJ component.',
    ],
  },
  'webforj-handling-timers-and-async': {
    summary: 'schedule timers, debouncers, and async work safely on the UI thread',
    prompts: [
      'Refresh this dashboard every 30 seconds.',
      'Add a search-as-you-type debouncer.',
      'Run this CPU-heavy work in the background and update the progress bar.',
    ],
  },
  'webforj-localizing-apps': {
    summary: 'add multi-language support and translate component labels',
    prompts: [
      'Add multi-language support with English and Spanish.',
      "Detect the user's browser locale and apply it on startup.",
      'Move all hardcoded strings into a messages bundle.',
    ],
  },
  'webforj-securing-apps': {
    summary: 'protect routes with login, role-based access, and ownership checks',
    prompts: [
      'Protect /admin so only users with the ADMIN role can see it.',
      'Add a public landing page that anyone can visit.',
      'Only let a user edit a record they own.',
    ],
  },
  'webforj-styling-apps': {
    summary: 'theme and style webforJ apps with DWC design tokens',
    prompts: [
      'Theme this app with a blue palette.',
      'Style the dwc-button to match the brand guidelines.',
      'Make this layout tighter - adjust spacing and typography.',
    ],
  },
  'webforj-upgrading-versions': {
    summary: 'upgrade across webforJ major versions with OpenRewrite',
    prompts: [
      'Upgrade this app from webforJ 25 to 26.',
      'Run the rewrite recipe and resolve the TODOs.',
      'What removed APIs do I need to fix after upgrading?',
    ],
  },
};

const SETUP_PATH = '/docs/integrations/ai-tooling/agent-skills';

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
  const data = SKILLS[skill];
  if (!data) {
    if (typeof console !== 'undefined') {
      console.error(`AISkillTip: unknown skill "${skill}"`);
    }
    return null;
  }
  const prompts = customPrompts ?? data.prompts;
  const title = customTitle ?? 'AI skill available';

  return (
    <Admonition type="tip" icon={<AutoAwesomeIcon fontSize="small" />} title={title}>
      <p>
        The <code>{skill}</code> skill can {data.summary}. After{' '}
        <Link to={SETUP_PATH}>installing the webforJ AI plugin</Link>, ask your assistant:
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
