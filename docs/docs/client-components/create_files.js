// This file is used to generate all the files in the client-components directory.
// To run this program and re-generate the files, navigate to the client-components
// directory and run the following command:
// node ./create_files.js
//
// Existing hand-curated `description:` frontmatter on a file is preserved across
// regenerations; only the body and other frontmatter fields are rewritten. New
// components (no existing file) get a generic description that should be
// hand-edited afterward.

const fs = require('fs');

const outputDirectory = './';

if (!fs.existsSync(outputDirectory)) {
  fs.mkdirSync(outputDirectory);
}

const jsonUrl = 'https://dwc.style/docs/dwc-components.json';

function readExistingDescription(filePath) {
  if (!fs.existsSync(filePath)) {
    return null;
  }
  const content = fs.readFileSync(filePath, 'utf8');
  const fm = content.match(/^---\r?\n([\s\S]*?)\r?\n---/);
  if (!fm) {
    return null;
  }
  const desc = fm[1].match(/^description:\s*(.+?)\s*$/m);
  return desc ? desc[1] : null;
}

async function fetchData() {
  try {
    const response = await fetch(jsonUrl);
    if (!response.ok) {
      throw new Error(`Failed to fetch data: ${response.statusText}`);
    }

    const jsonData = await response.json();

    jsonData.components.forEach((componentData) => {
      const { tag, encapsulation, docsTags, styles, props, dependencies } = componentData;
      const partItems = docsTags?.filter((docTag) => docTag.name === 'part');

      const formattedTagName = tag.replace('dwc-', '');
      const filePath = `${outputDirectory}${formattedTagName}.md`;

      const existingDescription = readExistingDescription(filePath);
      const description = existingDescription
        || `Style the ${tag} client component with CSS variables, shadow parts, and slots for theming.`;

      const markdownContent = `---
title: <${tag}>
sidebar_class_name: sidebar--item__hidden
slug: ${formattedTagName}
description: ${description}
---
${encapsulation !== 'none'
  ? `
<DocChip chip='${encapsulation}' />
    `
  : ''}
:::info CLIENT COMPONENT
This section outlines styling information for the **\`<${tag}>\`** component. This component is **client side only** - it can't be instantiated on its own via the API, but may make up part of API components.
:::

## Styling {#styling}

<TableBuilder name="${tag}" clientComponent />

`;

      fs.writeFileSync(filePath, markdownContent);
      const status = existingDescription ? 'preserved description' : 'new component';
      console.log(`Generated: ${filePath} (${status})`);
    });

    console.log('Markdown files have been generated.');
  } catch (error) {
    console.error('Error:', error.message);
  }
}

fetchData();
