const fs = require('fs');

const outputDirectory = './';
if (!fs.existsSync(outputDirectory)) {
  fs.mkdirSync(outputDirectory);
}

// Sample JSON data (replace with your actual data)
const jsonUrl = 'https://dwc.style/docs/dwc-components.json';

async function fetchData() {
  try {
    const response = await fetch(jsonUrl);
    if (!response.ok) {
      throw new Error(`Failed to fetch data: ${response.statusText}`);
    }

const jsonData = await response.json();

// Define the output directory where markdown files will be generated

// Ensure the output directory exists

// Iterate through the components in the JSON data
jsonData.components.forEach((componentData) => {
  const {  tag, encapsulation, docsTags, styles, props, dependencies } = componentData;
  const partItems = docsTags?.filter((docTag) => docTag.name === "part");

  const formattedTagName = tag.replace("dwc-", "");
  // Create the markdown content for each component
  const markdownContent = `---
sidebar_position: 0
title: <${tag}>
sidebar_class_name: sidebar--item__hidden
slug: ${formattedTagName}
description: A user guide article for the ${formattedTagName}
// pagination_prev: null
// pagination_next: null
---

import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import DocChip from '@site/src/components/DocsTools/DocChip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="${(encapsulation[0].toUpperCase()) + encapsulation.substring(1)}" target="_blank" clickable={false} iconName='${encapsulation}' />

<br />
<br />

:::info CLIENT COMPONENT
This section outlines styling information for the **\`<${tag}>\`** component. This component is **client side only** - it cannot be instantiated on its own via the API, but may make up part of API components.
:::

${
  encapsulation == 'shadow' ?
  partItems != '' ? `### Shadow Parts
These are the various parts of the shadow DOM for the component, which will be required when styling via CSS is desired.
<TableBuilder tag='${tag}' table="parts"/>` : "" : ""
}

${
  styles != '' ? `### CSS Properties

  These are the various CSS properties that are used in the component, with a short description of their use.
  
  <TableBuilder tag='${tag}' table="properties"/>` : ""
}

${
  props != '' ? `### Reflected Attributes

  The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.
  
  <TableBuilder tag='${tag}' table="reflects"/>` : ""
}

${
  dependencies != '' ? `### Dependencies

  This component relies on the following components - see the related article for more detailed styling information:
  
  <TableBuilder tag='${tag}' table="dependencies"/>` : ""
}
`;

  // Write the markdown content to a file
  const filePath = `${outputDirectory}${formattedTagName}.md`;
  fs.writeFileSync(filePath, markdownContent);
  console.log(`Generated: ${filePath}`);
});

console.log('Markdown files have been generated.');
}
 catch (error) {
  console.error('Error:', error.message);
}
}

fetchData(); // Call the async function to start fetching data
