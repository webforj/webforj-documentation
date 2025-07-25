import React from 'react';
import Heading from '@theme-original/Heading';
import ComboButton from '@site/src/components/DocsTools/ComboButton';

const alignStyles = {
  display: "flex",
  alignItems: "center",
  flexWrap: "wrap",
  justifyContent: "space-between"
};

export default function HeadingWrapper(props) {
  return (
    <>
      <div style={props.as === "h1" ? alignStyles : undefined}>
      <Heading {...props} />
      {props.as === "h1" && <ComboButton />}
      </div>
    </>
  );
}