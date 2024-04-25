import React, { useState, useEffect } from "react";

import Link from "@docusaurus/Link";

export default function TableBuilder(props) {


  const [component, setComponent] = useState(null);

  useEffect(() => {
    fetch("https://basishub.github.io/basis-next/docs/dwc-components.json")
      .then((response) => response.json())
      .then((data) => {
        const selectedComponent = data.components.find(
          (component) => component.tag === props.tag
        );
        setComponent(selectedComponent);
      });

  }, []);

  //Shows while the fetch request happens (likely unneeded as the iframes will refocus the window)
  if (!component) {
    return <div>Loading...</div>;
  }

  const partItems = component.docsTags?.filter((docTag) => docTag.name === "part");
  const slotItems = component.slots?.map((slots) => ({
    name: slots.name,
    desc: slots.docs
  }));
  const styleItems = component.styles?.map((style) => ({
    name: style.name,
    description: style.docs,
  }));
  const reflectItems = component.props?.map((prop) => ({
    display: prop.reflectToAttr,
    attr: prop.attr,
    desc: prop.docs,
    type: prop.type,

  }));
  const dependencies = component?.dependencies || [];



  return (
    <>
    {
      props.table == "parts" && (
        <table className="custom--table">
        <thead>
          <tr key="header">
            <th>Part</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          {partItems?.map((docTag) => {
            const [part, description] = docTag.text.split(" - ");
            return (
              !props.exclusions?.includes(part) && (
              <tr key={docTag.id}>
                <td><code>{part}</code></td>
                <td>{description}</td>
              </tr>
              )
            );
          })}
        </tbody>
      </table>
      )
    }
    {
      props.table == "slots" && (
        <table className="custom--table">
        <thead>
          <tr key="header">
            <th>Slot</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          {slotItems?.map((slot) => (
            !props.exclusions?.includes(slot.name) && slot.name != "" && (
            <tr key={slot.name}>
              <td><code>{slot.name}</code></td>
              <td>{slot.desc}</td>
            </tr>
            )
          ))}
        </tbody>
      </table>
      )
    }
      {/* <h4>Shadow Parts</h4> */}
    {
      props.table == "properties" && (
        <table className="custom--table">
        <thead>
          <tr key="header">
            <th>Name</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          {styleItems?.map((style) => (
            !props.exclusions?.includes(style.name) && (
            <tr key={style.name}>
              <td><code>{style.name}</code></td>
              <td>{style.description}</td>
            </tr>
            )
          ))}
        </tbody>
      </table>
      )
    }
    
    {
      props.table == "reflects" && (
        <table className="custom--table">
        <thead>
          <tr key="header">
            <th>Attribute</th>
            <th>Description</th>
            <th>Type</th>
          </tr>
        </thead>
        <tbody>
          {reflectItems?.map((prop) => (
            prop.attr != null && prop.display == true && !props.exclusions?.includes(prop.attr) && (
            <tr key={prop.attr}>
              <td><code>{prop.attr}</code></td>
              <td>{prop.desc}</td>
              <td>{prop.type}</td>
            </tr>
            )
          ))}
        </tbody>
      </table>
      )
    }
    
    {
      props.table == "dependencies" && (
          <ul>
            {dependencies.map((dependency, index) => (
              <li>
                <Link to={'/docs/client-components/' + dependency.replace("dwc-", "").charAt(0).toUpperCase() + dependency.slice(5)} key={index}>{dependency}</Link>
              </li>
            ))}
          </ul>
      )
    }





    {
      props.table != "properties" && props.table != "parts" && props.table != "slots" && props.table != "reflects" && props.table != "dependencies" && (
        <>
          <h4>Shadow Parts</h4>
          <table className="custom--table">
            <thead>
              <tr key="header">
                <th>Part</th>
                <th>Description</th>
              </tr>
            </thead>
            <tbody>
              {partItems?.map((docTag) => {
                const [part, description] = docTag.text.split(" - ");
                return (
                  <tr key={docTag.id}>
                    <td><code>{part}</code></td>
                    <td>{description}</td>
                  </tr>
                );
              })}
            </tbody>
          </table>
          <h4>CSS Properties</h4>
          <table className="custom--table">
            <thead>
              <tr key="header">
                <th>Name</th>
                <th>Description</th>
              </tr>
            </thead>
            <tbody>
              {styleItems?.map((style) => (
                <tr key={style.name}>
                  <td><code>{style.name}</code></td>
                  <td>{style.description}</td>
                </tr>
              ))}
            </tbody>
          </table>
          <h4>Reflected Attributes</h4>
          <table className="custom--table">
        <thead>
          <tr key="header">
            <th>Attribute</th>
            <th>Description</th>
            <th>Type</th>
          </tr>
        </thead>
        <tbody>
          {reflectItems?.map((prop) => (
            prop.attr != null && prop.display == false && !props.exclusions?.includes(prop.attr) && (
            <tr key={prop.attr}>
              <td><code>{prop.attr}</code></td>
              <td>{prop.desc}</td>
              <td>{prop.type}</td>
            </tr>
            )
          ))}
        </tbody>
      </table>
        </>
      )
    }
    </>
  );
}
