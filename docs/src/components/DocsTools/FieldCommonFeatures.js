import React from 'react';
import CodeBlock from '@theme/CodeBlock';
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Translate, { translate } from '@docusaurus/Translate';
import AccordionGroup from './AccordionGroup';
import TableBuilder from './TableBuilder';

const PREFIX_SUFFIX_EXAMPLE = `TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));`;

function FeatureAccordion({ summary, children }) {
  return (
    <Accordion>
      <AccordionSummary expandIcon={<ExpandMoreIcon />}>
        <strong>{summary}</strong>
      </AccordionSummary>
      <AccordionDetails>{children}</AccordionDetails>
    </Accordion>
  );
}

/**
 * Renders shared Field properties as an accordion section to be placed at the
 * bottom of individual field component articles. Content inside accordions is
 * intentionally excluded from site search indexing.
 *
 * @param {Object}  props
 * @param {string}  [props.tableName="Field"]  - TableBuilder name for the styling section.
 *                  Pass "ColorField" for ColorField, or any other valid TableBuilder name.
 * @param {boolean} [props.includeStyling=true] - Set to false to omit the Styling accordion.
 * @param {boolean} [props.includeSpellCheck=false] - Set to true for text-oriented fields.
 */
export default function FieldCommonFeatures({
  tableName = 'Field',
  includeStyling = true,
  includeSpellCheck = false
}) {
  return (
    <div>
      <p>
        <Translate
          id="fieldCommonFeatures.introduction"
          description="Introduction to the features shared by field components"
          values={{ fieldClass: <code>Field</code> }}
        >
          {'Because all field components extend the shared {fieldClass} class, the following properties and behaviors are available across all field types.'}
        </Translate>
      </p>
      <AccordionGroup>
        <FeatureAccordion
          summary={translate({
            id: 'fieldCommonFeatures.label.summary',
            message: 'Label',
            description: 'Accordion summary for the field label feature'
          })}
        >
          <p>
            <Translate
              id="fieldCommonFeatures.label.description"
              description="Description of the label feature shared by field components"
              values={{ setLabelMethod: <code>setLabel()</code> }}
            >
              {'A field label is descriptive text associated with the field. Define it using the constructor or by calling the {setLabelMethod} method. Labels help users understand the purpose or expected input for the field. They also support accessibility by giving screen readers and assistive technologies accurate information for navigation.'}
            </Translate>
          </p>
        </FeatureAccordion>

        <FeatureAccordion
          summary={translate({
            id: 'fieldCommonFeatures.helperText.summary',
            message: 'Helper text',
            description: 'Accordion summary for the field helper text feature'
          })}
        >
          <p>
            <Translate
              id="fieldCommonFeatures.helperText.description"
              description="Description of the helper text feature shared by field components"
              values={{ setHelperTextMethod: <code>setHelperText()</code> }}
            >
              {'Each field can display helper text beneath the input using the {setHelperTextMethod} method. Helper text provides additional context about the available inputs so users can make informed selections.'}
            </Translate>
          </p>
        </FeatureAccordion>

        <FeatureAccordion
          summary={translate({
            id: 'fieldCommonFeatures.required.summary',
            message: 'Required',
            description: 'Accordion summary for the required field feature'
          })}
        >
          <p>
            <Translate
              id="fieldCommonFeatures.required.description"
              description="Description of the required feature shared by field components"
              values={{ setRequiredMethod: <code>setRequired(true)</code> }}
            >
              {'Call {setRequiredMethod} to require users to provide a value before submitting a form. This property works with the field label to indicate visually that a field is necessary.'}
            </Translate>
          </p>
          <p>
            <Translate
              id="fieldCommonFeatures.required.validation"
              description="Description of built-in required field validation"
            >
              Field components include visual validation that notifies users when a required field
              is empty or its value has been removed.
            </Translate>
          </p>
        </FeatureAccordion>

        {includeSpellCheck && (
          <FeatureAccordion
            summary={translate({
              id: 'fieldCommonFeatures.spellcheck.summary',
              message: 'Spellcheck',
              description: 'Accordion summary for the field spellcheck feature'
            })}
          >
            <p>
              <Translate
                id="fieldCommonFeatures.spellcheck.description"
                description="Description of the spellcheck feature for text-oriented fields"
                values={{ setSpellCheckMethod: <code>setSpellCheck(true)</code> }}
              >
                {'Call {setSpellCheckMethod} to allow the browser or user agent to check the spelling of entered text and highlight errors.'}
              </Translate>
            </p>
          </FeatureAccordion>
        )}

        <FeatureAccordion
          summary={translate({
            id: 'fieldCommonFeatures.prefixSuffix.summary',
            message: 'Prefix and suffix',
            description: 'Accordion summary for the field prefix and suffix feature'
          })}
        >
          <p>
            <Translate
              id="fieldCommonFeatures.prefixSuffix.description"
              description="Description of components that can be placed in field slots"
            >
              Slots let you add icons, labels, loading spinners, clear or reset buttons, avatar
              images, and other components that clarify a field's purpose.
            </Translate>
          </p>
          <p>
            <Translate
              id="fieldCommonFeatures.prefixSuffix.usage"
              description="Instructions for using field prefix and suffix slots"
              values={{
                prefixSlot: <code>prefix</code>,
                suffixSlot: <code>suffix</code>,
                setPrefixMethod: <code>setPrefixComponent()</code>,
                setSuffixMethod: <code>setSuffixComponent()</code>
              }}
            >
              {'Fields provide the {prefixSlot} and {suffixSlot} slots. Use {setPrefixMethod} and {setSuffixMethod} to insert components before and after the field value.'}
            </Translate>
          </p>
          <CodeBlock language="java">{PREFIX_SUFFIX_EXAMPLE}</CodeBlock>
        </FeatureAccordion>

        {includeStyling && (
          <FeatureAccordion
            summary={translate({
              id: 'fieldCommonFeatures.styling.summary',
              message: 'Styling',
              description: 'Accordion summary for shared field styling information'
            })}
          >
            <p>
              <Translate
                id="fieldCommonFeatures.styling.description"
                description="Description of styling information shared by field components"
              >
                Field components built from the shared web component use the following shadow parts
                and CSS custom properties.
              </Translate>
            </p>
            <TableBuilder name={tableName} />
          </FeatureAccordion>
        )}
      </AccordionGroup>
    </div>
  );
}
