/** @jsxImportSource @emotion/react */
import React, { useEffect, useState } from 'react';
import Admonition from '@theme/Admonition';

export default function ParentLink({parent, url = "./"}){
  const defaultChildValue = "This class";
  const [child, setChild] = useState(defaultChildValue);

  useEffect(() => {
    const h1 = document.querySelector('h1');
    if (h1) {
      setChild(h1.textContent);
    }
  }, []);

  return (
    <div>
      <Admonition type="info" title="Inheritance">
        <p>{child !== defaultChildValue ? <code>{child}</code> : child} is a {parent} component, and inherits its features and behaviors. 
        For an overview of {parent} properties, events, and other important information, please refer to the <a href={url}>{parent} documentation</a>.</p>
      </Admonition>
    </div>
  )
}