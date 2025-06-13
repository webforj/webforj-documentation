// This file is used to generate all the files in the client-components directory.
// To run this program and re-generate the files, navigate to the client-components
// directory and run the following command:
// node ./create_files.js

const fs = require('fs');

// Define the output directory where markdown files will be generated
const outputDirectory = './';

// Ensure the output directory exists
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

<DocChip chip='${encapsulation}' />

<br />

:::info CLIENT COMPONENT
This section outlines styling information for the **\`<${tag}>\`** component. This component is **client-side only** - it can't be instantiated on its own via the API, but may make up part of API components.
:::

## Styling

<TableBuilder name="${tag}" clientComponent />

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
