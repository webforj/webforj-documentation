import React, { useState, useEffect } from "react";
import Link from "@docusaurus/Link";
import controlMap from '@site/docs/components/_dwc_control_map.json';
import exclusions from '@site/static/exclusions.json';

/**
 * TableBuilder creates tables of data for webforJ components. It can create the following tables:
 * Shadow Parts, Slots, CSS Properties, Reflected Attributes, and Dependencies
 * It pulls this data from https://dwc.style/docs/dwc-components.json
 * Example usage:
 *   <TableBuilder name="Button" />
 *   <TableBuilder name="dwc-alert" tables={["parts", "dependencies"]};
 * @param {Object} props - Component props
 * @param {String} props.name - The name of the component
 * @param {Array<string>} [props.tables] - (optional) Names of the tables to render
 * @returns {React.ReactElement} - Rendered tables of data for the component
 */
export default function TableBuilder(props) {
  const [componentData, setComponentData] = useState(null);
  const shadowDomLink = '/docs/glossary#shadow-dom';
  // headerClasses are the correct classes to give headers in Docusaurus to make sure
  // they are styled correctly and show up in the correct location when you click a 
  // TOC entry. This value may need to change sometimes.
  const headerClasses = "anchor anchorWithStickyNavbar_LWe7";
  const componentExclusions = exclusions[props.name?.toLowerCase()];

  // If the name isn't in the controlMap, we just use the name as-is. 
  // This allows you to set the tag directly with something like name="dwc-alert"
  const tag = controlMap[props.name] ?? props.name;

  // If no tables are provided, try to generate all of them. Empty tables won't render anything.
  const tables = props.tables ? props.tables : ["parts", "slots", "properties", "reflects", "dependencies"];

  useEffect(() => {
    fetch("https://dwc.style/docs/dwc-components.json")
      .then((response) => response.json())
      .then((data) => {
        const selectedComponent = data.components.find(
          (component) => component.tag === tag
        );
        setComponentData(selectedComponent);
      });

  }, [tag]);

  // Shows while the fetch request happens 
  if (!componentData) {
    return <div>Loading...</div>;
  }

  // The part items aren't formatted well for the table; the first element is just "part" every time, 
  // followed by a string like "label - The button's label", so we are splitting the string along that dash
  const partItems = componentData.docsTags?.filter((docTag) => docTag.name === "part").map((part) => ({
    name: part.text.split(" - ")[0],
    desc: part.text.split(" - ")[1]
  }));
  const slotItems = componentData.slots?.map((slots) => ({
    name: slots.name,
    desc: slots.docs
  }));
  const styleItems = componentData.styles?.map((style) => ({
    name: style.name,
    desc: style.docs,
  }));
  // Only include Reflected Attributes if reflectToAttr is true
  const reflectItems = componentData.props?.filter(prop => prop.reflectToAttr).map((prop) => ({
    attr: prop.attr,
    desc: prop.docs,
    type: prop.type,
  }));
  const dependencies = componentData?.dependencies || [];


  const renderTable = (table) => {
    let items, headers, sectionHeading, sectionDescription;
    const tableExclusions = componentExclusions?.[table] || [];

    // Set information depending on which table we are making
    switch (table) {
      case "parts":
        items = partItems;
        headers = ["Part", "Description"];
        sectionHeading = "Shadow parts";
        sectionDescription = (
          <>
            These are the <Link to={shadowDomLink}>shadow DOM</Link> parts for 
            the <code>{props.name}</code> component, which are required for styling with CSS.
          </>
        );
        break;
      case "slots":
        items = slotItems;
        headers = ["Slot", "Description"];
        sectionHeading = "Slots";
        sectionDescription = (
          <>
          These are the slots available for use within the <code>{props.name}</code> component. 
          These slots act as placeholders within the component that control 
          where the children of a customized element are inserted within the shadow tree.
          </>
        )
        break;
      case "properties":
        items = styleItems;
        headers = ["Property", "Description"];
        sectionHeading = "CSS properties";
        sectionDescription = (
          <>
          These are the CSS properties that are used in the <code>{props.name}</code> component, 
          with a short description of their use.
          </>
        )
        break;
      case "reflects":
        items = reflectItems;
        headers = ["Attribute", "Description", "Type"];
        sectionHeading = "Reflected attributes";
        sectionDescription = (
          <>
          These are the reflected attributes for the <code>{props.name}</code> component.
          Reflected attributes appear as attributes in the rendered HTML element 
          for the component in the DOM. This means that styling can be applied using these attributes.
          </>
        )
        break;
      case "dependencies":
        items = dependencies;
        sectionHeading = "Dependencies"
        sectionDescription = (
        <>
        The <code>{props.name}</code> component relies on the following components. 
        See the related article for more detailed styling information.
        </>
        )
        break;
      default:
        return null;
    }

    // Don't render anything if there is nothing in the table
    // Or if there is only one element without a name (sometimes the case with slots)
    if (items.length == 0 || items.length == 1 && items[0].name == ""){
      // Theoretically, if everything in the table was excluded, it should also return null.
      // Exclusions are currently pretty minor, so we're not handling that case.
      return null;
    }

    // Now that we know there is content for this section, add to the mini TOC
    addTocEntry(sectionHeading);

    if (table == "dependencies"){
      return (
        <>
        <h3 class={headerClasses} id={sectionHeading}>{sectionHeading}</h3>
        <p>{sectionDescription}</p>
        <ul>
          {dependencies.map((dependency) => {
            const clientLink = '/docs/client-components/' + 
              dependency.replace("dwc-", "")
              .replace(/^[a-z]/, (char) => char.toUpperCase());
            return (
            <li key={dependency}>
              <Link to={clientLink}>{dependency}</Link>
            </li>
            );
          })}
        </ul>
        </>
      );
    }

    return (
      <>
      <h3 class={headerClasses} id={sectionHeading}>{sectionHeading}</h3>
      <p>{sectionDescription}</p>
      <table className="custom--table" key={table}>
        <thead>
          <tr key="header">
            {headers.map((header) => (
              <th key={header}>{header}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {items?.map((item) => {
            const [key, ...values] = Object.values(item);
            if (tableExclusions?.includes(key) || !key) return null;
            return (
            <tr key={key}>
              <td><code>{key}</code></td>
              {values.map((value, idx) => (
                <td key={`${key}-${idx}`}>{formatText(value)}</td>
              ))}
            </tr>
            );
          })}
        </tbody>
      </table>
      </>
    );
  };

  // Render all tables passed in props.tables
  // React.Fragment helps avoid the warning about duplicate keys
  return (
    <>
      {tables.map((table) => (
        <React.Fragment key={`table-${table}`}>
        {renderTable(table)}
        </React.Fragment>
      ))}
    </>
  );
}

/**
 * formatText formats some limited Markdown and HTML syntax that appears in the data.
 * It changes <br> elements to line breaks, and changes text wrapped in backticks
 * to text wrapped in <code> tags.
 * @param {string} text 
 * @returns React fragments of processed text
 */
function formatText(text) {
  if (!text) return null;
  const lines = text.split(/<br>/);

  return lines.map((line, lineIndex) => {
    const parts = line.split(/(`[^`]+`)/); 

    const processedLine = parts.map((part, index) =>
      part.startsWith('`') && part.endsWith('`') ? (
        <code key={index}>{part.slice(1, -1)}</code>
      ) : (
        part
      )
    );
    // Don't render empty lines, and avoid duplicate keys
    if (processedLine[0].length === 0){
      return null;
    }
    return (
      <React.Fragment key={`${processedLine[0]}-${lineIndex}`}>
        {processedLine}
        {lineIndex < lines.length - 1 && <br />}
      </React.Fragment>
    );
  });

}

/**
 * addTocEntry adds an entry in the mini TOC for the specified section.
 * "entry" should be the content and ID of the heading.
 * addTocEntry will only add to the TOC if it already exists.
 * @param {string} entry 
 * @returns 
 */
function addTocEntry(entry) {
    const tocContainer = document.querySelector('.table-of-contents');
    if (!tocContainer) return;

    const existingEntry = tocContainer.querySelector(`a[href$="#${entry}"]`);
    if (existingEntry) return;
    
    // Find the "Styling" portion of the TOC
    const stylingListItem = Array.from(tocContainer.querySelectorAll('li a'))
      .find(a => a.textContent.trim().includes('Styling'))
      ?.closest('li');

    if (!stylingListItem) return;
    
    // Get <ul> element it has one, if not, create one.
    const stylingSubList = stylingListItem.querySelector('ul') || stylingListItem.appendChild(document.createElement('ul'));

    const link = document.createElement('a');
    // Todo: convert entry to a better "id" type value
    link.href = '#' + entry;
    link.textContent = entry;
    link.className = 'table-of-contents__link toc-highlight';
    const listItem = document.createElement('li');
    listItem.appendChild(link);
    stylingSubList.appendChild(listItem);
}