import { translate } from '@docusaurus/Translate';

// The craftforJ panels shown in the tour. Every sentence restates the
// craftforJ docs pages. Each view is one of the videos or screenshots from
// those pages, with its real pixel size so nothing shifts while it loads.

// Called while rendering, so translate() sees the page's language.
export default function getPanels() {
  return [
    {
      id: 'inspector',
      tab: translate({ id: 'page.craftforj.panels.inspector.tab', message: 'Inspector' }),
      title: translate({
        id: 'page.craftforj.panels.inspector.title',
        message: 'The component tree your Java built',
      }),
      text: translate({
        id: 'page.craftforj.panels.inspector.text',
        message:
          'The Inspector lists your components, not the markup the browser rendered. A Composite appears as the class you wrote, holding its children in the order webforJ holds them.',
      }),
      points: [
        translate({
          id: 'page.craftforj.panels.inspector.point1',
          message: 'Press Alt+Shift+C and click anything in the page to select it in the tree.',
        }),
        translate({
          id: 'page.craftforj.panels.inspector.point2',
          message: 'Change a property in the sidebar and the running app updates immediately.',
        }),
        translate({
          id: 'page.craftforj.panels.inspector.point3',
          message: 'Trace a component back to the Java line that created it.',
        }),
      ],
      doc: {
        label: translate({
          id: 'page.craftforj.panels.inspector.docLabel',
          message: 'Inspecting components',
        }),
        to: '/docs/craftforj/inspector',
      },
      views: [
        {
          label: translate({
            id: 'page.craftforj.panels.inspector.view1',
            message: 'Pick a component',
          }),
          video: 'https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4',
          width: 1920,
          height: 992,
          alt: translate({
            id: 'page.craftforj.panels.inspector.alt1',
            message:
              'Video: picking a component in the running app selects it in the craftforJ component tree',
          }),
        },
        {
          label: translate({
            id: 'page.craftforj.panels.inspector.view2',
            message: 'Edit a property',
          }),
          video: 'https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4',
          width: 1920,
          height: 1000,
          alt: translate({
            id: 'page.craftforj.panels.inspector.alt2',
            message: 'Video: changing a property in the craftforJ sidebar updates the running app',
          }),
        },
      ],
    },
    {
      id: 'source-changes',
      tab: translate({
        id: 'page.craftforj.panels.sourceChanges.tab',
        message: 'Applying changes',
      }),
      title: translate({
        id: 'page.craftforj.panels.sourceChanges.title',
        message: 'Review the diff, then write it to Java',
      }),
      text: translate({
        id: 'page.craftforj.panels.sourceChanges.text',
        message:
          'Every property you change is recorded as a pending change. Press Cmd/Ctrl+S to review them grouped by the file they land in, each with its old and new value and the diff of the file. Nothing is written until you apply.',
      }),
      points: [
        translate({
          id: 'page.craftforj.panels.sourceChanges.point1',
          message: 'Pending changes survive a page reload and a route change.',
        }),
        translate({
          id: 'page.craftforj.panels.sourceChanges.point2',
          message:
            'Write a change at the usage, for one screen, or at the definition, for every screen that uses it.',
        }),
        translate({
          id: 'page.craftforj.panels.sourceChanges.point3',
          message: 'Cmd/Ctrl+Z undoes a change, including one already written to your files.',
        }),
      ],
      doc: {
        label: translate({
          id: 'page.craftforj.panels.sourceChanges.docLabel',
          message: 'Applying changes',
        }),
        to: '/docs/craftforj/source-changes',
      },
      views: [
        {
          label: translate({
            id: 'page.craftforj.panels.sourceChanges.view1',
            message: 'Save to source',
          }),
          video: 'https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4',
          width: 1920,
          height: 992,
          alt: translate({
            id: 'page.craftforj.panels.sourceChanges.alt1',
            message:
              'Video: reviewing the changes made in craftforJ and saving them into the Java source',
          }),
        },
      ],
    },
    {
      id: 'routes',
      tab: translate({ id: 'page.craftforj.panels.routes.tab', message: 'Routes' }),
      title: translate({
        id: 'page.craftforj.panels.routes.title',
        message: 'The routing table, live',
      }),
      text: translate({
        id: 'page.craftforj.panels.routes.text',
        message:
          'The Routes tab shows the routing table in the hierarchy the router holds it in, with the active route marked. Routes registered dynamically appear alongside annotated ones.',
      }),
      points: [
        translate({
          id: 'page.craftforj.panels.routes.point1',
          message:
            'Navigate to any route from craftforJ. Your lifecycle observers run as they would for a user.',
        }),
        translate({
          id: 'page.craftforj.panels.routes.point2',
          message: 'Each route carries a badge for the security annotation declared on it.',
        }),
        translate({
          id: 'page.craftforj.panels.routes.point3',
          message:
            'Change an access rule and craftforJ writes the annotation into the route class.',
        }),
      ],
      doc: {
        label: translate({ id: 'page.craftforj.panels.routes.docLabel', message: 'Routes' }),
        to: '/docs/craftforj/routes',
      },
      views: [
        {
          label: translate({
            id: 'page.craftforj.panels.routes.view1',
            message: 'Navigate to a route',
          }),
          video: 'https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-navigate.mp4',
          width: 1920,
          height: 1000,
          alt: translate({
            id: 'page.craftforj.panels.routes.alt1',
            message: 'Video: navigating to a route from the craftforJ Routes tab',
          }),
        },
        {
          label: translate({
            id: 'page.craftforj.panels.routes.view2',
            message: 'Set an access rule',
          }),
          video: 'https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-security.mp4',
          width: 1920,
          height: 1000,
          alt: translate({
            id: 'page.craftforj.panels.routes.alt2',
            message:
              'Video: choosing a security annotation for a route in the craftforJ Routes tab',
          }),
        },
      ],
    },
    {
      id: 'theme',
      tab: translate({ id: 'page.craftforj.panels.theme.tab', message: 'Theme' }),
      title: translate({
        id: 'page.craftforj.panels.theme.title',
        message: 'Retheme the app while it runs',
      }),
      text: translate({
        id: 'page.craftforj.panels.theme.text',
        message:
          'The Theme tab works with the DWC design tokens your app already uses, so one change reaches every component that reads that token. Controls cover the palette, surfaces, edges and corners, typography and spacing.',
      }),
      points: [
        translate({
          id: 'page.craftforj.panels.theme.point1',
          message: 'Edit the light side, the dark side or both, and flip the app between them.',
        }),
        translate({
          id: 'page.craftforj.panels.theme.point2',
          message: 'Start from a preset such as App Default or Portico.',
        }),
        translate({
          id: 'page.craftforj.panels.theme.point3',
          message: 'Saving writes the theme into your stylesheet, where it ships with your app.',
        }),
      ],
      doc: {
        label: translate({ id: 'page.craftforj.panels.theme.docLabel', message: 'Theme' }),
        to: '/docs/craftforj/theme',
      },
      views: [
        {
          label: translate({ id: 'page.craftforj.panels.theme.view1', message: 'Theme controls' }),
          video: 'https://cdn.webforj.com/webforj-documentation/video/craftforJ/theme-knobs.mp4',
          width: 1920,
          height: 992,
          alt: translate({
            id: 'page.craftforj.panels.theme.alt1',
            message:
              'Video: changing the theme of the running app with the craftforJ Theme controls',
          }),
        },
      ],
    },
    {
      id: 'ai',
      tab: translate({ id: 'page.craftforj.panels.ai.tab', message: 'AI Assistant' }),
      title: translate({
        id: 'page.craftforj.panels.ai.title',
        message: 'An agent that compiles before you see it',
      }),
      text: translate({
        id: 'page.craftforj.panels.ai.text',
        message:
          'The assistant is a coding agent inside your running app. It writes Java, sends each staged edit to a real Java compiler and fixes its own mistakes from the diagnostics, so what reaches your review already compiles.',
      }),
      points: [
        translate({
          id: 'page.craftforj.panels.ai.point1',
          message:
            'Choose how much it does alone: ask before acting, apply edits automatically, or run autonomously.',
        }),
        translate({
          id: 'page.craftforj.panels.ai.point2',
          message: 'Use an API key for a supported provider, or a model running locally.',
        }),
        translate({
          id: 'page.craftforj.panels.ai.point3',
          message: 'It talks to your provider from the browser, with no relay and no proxy.',
        }),
      ],
      doc: {
        label: translate({ id: 'page.craftforj.panels.ai.docLabel', message: 'AI assistant' }),
        to: '/docs/craftforj/ai',
      },
      views: [
        {
          label: translate({ id: 'page.craftforj.panels.ai.view1', message: 'A conversation' }),
          video:
            'https://cdn.webforj.com/webforj-documentation/video/craftforJ/ai-conversation.mp4',
          width: 1920,
          height: 1000,
          alt: translate({
            id: 'page.craftforj.panels.ai.alt1',
            message:
              'Video: a conversation with the craftforJ AI assistant that changes the running app',
          }),
        },
      ],
    },
    {
      id: 'app-info',
      tab: translate({ id: 'page.craftforj.panels.appInfo.tab', message: 'App info' }),
      title: translate({
        id: 'page.craftforj.panels.appInfo.title',
        message: 'What your app is really running with',
      }),
      text: translate({
        id: 'page.craftforj.panels.appInfo.text',
        message:
          'App info reports the versions the app runs with, which is not always what your pom.xml says. It covers webforJ and BBj Services, the Java runtime, the operating system and the project root.',
      }),
      points: [
        translate({
          id: 'page.craftforj.panels.appInfo.point1',
          message: 'The project root is where craftforJ looks for your sources.',
        }),
        translate({
          id: 'page.craftforj.panels.appInfo.point2',
          message:
            'The Java runtime decides how thoroughly assistant edits are validated. Full validation needs a JDK.',
        }),
      ],
      doc: {
        label: translate({ id: 'page.craftforj.panels.appInfo.docLabel', message: 'App info' }),
        to: '/docs/craftforj/app-info',
      },
      views: [
        {
          label: translate({ id: 'page.craftforj.panels.appInfo.view1', message: 'App info' }),
          file: 'app-info/app-info-tab.png',
          width: 597,
          height: 497,
          alt: translate({
            id: 'page.craftforj.panels.appInfo.alt1',
            message:
              'The App Info panel listing the webforJ version, project root, Java runtime and operating system',
          }),
        },
      ],
    },
  ];
}
