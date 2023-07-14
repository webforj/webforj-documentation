import React, { useState, useEffect } from "react";


export default function TableBuilder(props) {


  const [component, setComponent] = useState(null);

  useEffect(() => {
    fetch("https://basishub.github.io/basis-next/bbj-components.json")
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
  const styleItems = component.styles?.map((style) => ({
    name: style.name,
    description: style.docs,
  }));

  return (
    <>
    {
      props.table == "parts" && (
        <>
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
        </>
      )
    }
      {/* <h4>Shadow Parts</h4> */}
    {
      props.table == "properties" && (
        <>
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
        </>
      )
    }
      {/* <h4>CSS Properties</h4> */}

    {
      props.table != "properties" && props.table != "parts" && (
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
        </>
      )
    }
    </>
  );
}
