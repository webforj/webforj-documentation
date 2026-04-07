import React, { useState } from 'react';

/**
 * Wraps a set of MUI Accordion components so that only one can be open at a time.
 * Each direct child should be an <Accordion> element.
 */
export default function AccordionGroup({ children }) {
  const [expanded, setExpanded] = useState(false);

  const handleChange = (panel) => (_, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };

  return React.Children.map(children, (child, i) => {
    if (!child) return null;
    const panel = `panel${i}`;
    return React.cloneElement(child, {
      expanded: expanded === panel,
      onChange: handleChange(panel),
    });
  });
}
