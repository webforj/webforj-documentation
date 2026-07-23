import React from 'react';
import CodeBlock from '@theme/CodeBlock';
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import AccordionGroup from './AccordionGroup';
import TableBuilder from './TableBuilder';

const PREFIX_SUFFIX_EXAMPLE = `TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));`;

/**
 * Renders shared Field properties as an accordion section to be placed at the
 * bottom of individual field component articles. Content inside accordions is
 * intentionally excluded from site search indexing.
 *
 * @param {Object}  props
 * @param {string}  [props.tableName="Field"]  - TableBuilder name for the styling section.
 *                  Pass "Color-Chooser" for ColorField, or any other valid TableBuilder name.
 * @param {boolean} [props.includeStyling=true] - Set to false to omit the Styling accordion.
 */
export default function FieldCommonFeatures({ tableName = 'Field', includeStyling = true }) {
  return (
    <div>
      <h2 id="common-field-features">Common field features</h2>
      <p>
        Because all field components extend the shared <code>Field</code> class, the following
        properties and behaviors are available across all field types.
      </p>
      <AccordionGroup>
        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <b>Label</b>
          </AccordionSummary>
          <AccordionDetails>
            <p>
              A field label is a descriptive text or title associated with the field. Define it
              using the constructor or by calling the <code>setLabel()</code> method. Labels provide
              a brief explanation or prompt to help users understand the purpose or expected input
              for that particular field. Field labels are important for usability and play a crucial
              role in accessibility, as they allow screen readers and assistive technologies to
              provide accurate information and facilitate keyboard navigation.
            </p>
          </AccordionDetails>
        </Accordion>

        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <b>Helper text</b>
          </AccordionSummary>
          <AccordionDetails>
            <p>
              Each field can display helper text beneath the input using the{' '}
              <code>setHelperText()</code> method. This helper text offers additional context or
              explanations about the available inputs, ensuring users have the necessary information
              to make informed selections.
            </p>
          </AccordionDetails>
        </Accordion>

        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <b>Required</b>
          </AccordionSummary>
          <AccordionDetails>
            <p>
              You can call the <code>setRequired(true)</code> method to require users to provide a
              value before submitting a form. This property works in tandem with the field label,
              providing a visual indication that a field is necessary.
            </p>
            <p>
              Field components contain built-in visual validation to notify users when a required
              field is empty or when a value has been removed.
            </p>
          </AccordionDetails>
        </Accordion>

        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <b>Spellcheck</b>
          </AccordionSummary>
          <AccordionDetails>
            <p>
              By calling <code>setSpellCheck(true)</code>, you can allow the browser or user agent
              to verify the spelling of text entered by the user and highlight any errors.
            </p>
          </AccordionDetails>
        </Accordion>

        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <b>Prefix and suffix</b>
          </AccordionSummary>
          <AccordionDetails>
            <p>
              Slots provide flexible options for extending field components. You can add icons,
              labels, loading spinners, clear/reset buttons, avatar images, and other components
              nested within a field to further clarify its intended purpose.
            </p>
            <p>
              Fields have two slots: <code>prefix</code> and <code>suffix</code>. Use the{' '}
              <code>setPrefixComponent()</code> and <code>setSuffixComponent()</code> methods to
              insert components before and after the field value.
            </p>
            <CodeBlock language="java">{PREFIX_SUFFIX_EXAMPLE}</CodeBlock>
          </AccordionDetails>
        </Accordion>

        {includeStyling && (
          <Accordion>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
              <b>Styling</b>
            </AccordionSummary>
            <AccordionDetails>
              <p>
                Because field components are built from a shared web component, they share the
                following shadow parts and CSS custom property values.
              </p>
              <TableBuilder name={tableName} />
            </AccordionDetails>
          </Accordion>
        )}
      </AccordionGroup>
    </div>
  );
}
