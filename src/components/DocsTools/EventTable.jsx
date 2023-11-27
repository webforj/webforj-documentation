/** @jsxImportSource @emotion/react */

import React from 'react'
import { css } from '@emotion/react';

import JavadocLink from './JavadocLink';


export default function EventTable({ base, events }) {
  
  const baseEvents = {
    blur : {
      name: "BlurEvent",
      description: "An event that is triggered when a component loses focus.",
      link: "org/dwcj/component/event/BlurEvent"
    },
    focus : {
      name: "FocusEvent",
      description: "An event that is triggered when a component gains focus, opposite of a blur event.",
      link: "org/dwcj/component/event/FocusEvent"
    },
    mouseEnter : {
      name: "MouseEnterEvent",
      description: "An event that is triggered when the mouse cursor enters the boundaries of a component.",
      link: "org/dwcj/component/event/MouseEnterEvent"
    },
    mouseExit : {
      name: "MouseExitEvent",
      description: "An event that is triggered when the mouse cursor exits the boundaries of a component.",
      link: "org/dwcj/component/event/MouseExitEvent"
    },
    rightMouseDown : {
      name: "RightMouseDownEvent",
      description: "An event that is triggered when the user presses the right mouse button while the cursor is over a component.",
      link: "org/dwcj/component/event/RightMouseDownEvent"
    }
  };
  
  const otherEvents = {
    buttonClick : {
      name: "ButtonClickEvent",
      description: "An event that is triggered when a user interacts with a button by clicking or tapping on it.",
      link: "org/dwcj/component/button/event/ButtonClickEvent"
    },
    check : {
      name: "CheckEvent",
      description: "An event that is triggered when the state of a component changes and becomes checked. It occurs when the user interacts with a checkbox element by clicking or tapping on it, causing the checkbox to transition from an unchecked state to a checked state.",
      link: "org/dwcj/component/event/CheckEvent"
    },
    uncheck : {
      name: "UncheckEvent",
      description: "An event that is triggered when the state of a component changes and becomes unchecked.",
      link: "org/dwcj/component/event/UncheckEvent"
    }, 
    toggle : {
      name: "ToggleEvent",
      description: "A ToggleEvent fires a CheckBox or a similar UI element changes its state between \"on\" and \"off\" or \"active\" and \"inactive.\"",
      link: "org/dwcj/component/event/UncheckEvent"
    },
    dialogOpen : {
      name: "DialogOpenEvent",
      description: "An event that is fired when the Dialog is opened.",
      link: "org/dwcj/component/dialog/event/DialogOpenEvent"
    },
    dialogClose : {
      name: "DialogCloseEvent",
      description: "An event that is fired when the Dialog is closed.",
      link: "org/dwcj/component/dialog/event/DialogCloseEvent"
    },
    drawerOpen : {
      name: "DrawerOpenEvent",
      description: "An event that is fired when the Drawer is opened.",
      link: "org/dwcj/component/drawer/event/DrawerOpenEvent"
    },
    drawerClose : {
      name: "DrawerCloseEvent",
      description: "An event that is fired when the Drawer is closed.",
      link: "org/dwcj/component/drawer/event/DrawerCloseEvent"
    },
    keypress : {
      name: "KeypressEvent",
      description: "An event that is triggered when one of \"special keys\" is pressed while the component has focus. These keys have specific codes, allowing for conditional logic to be implemented based on the key pressed.",
      link: "org/dwcj/component/event/KeypressEvent"
    },
    modify : {
      name: "ModifyEvent",
      description: "An event that is triggered when an is changed or modified. It typically occurs any time a user changes an aspect of the component, such as each time a letter is input or removed from an input component.",
      link: "org/dwcj/component/event/ModifyEvent"
    },
    listSelect : {
      name: "ListSelectEvent",
      description: "An event which is fired when the user selects an item from a list.",
      link: "org/dwcj/component/list/event/ListSelectEvent"
    },
    listOpen : {
      name: "ListOpenEvent",
      description: "An event which is fired when a list dropdown is opened.",
      link: "org/dwcj/component/list/event/ListOpenEvent"
    },
    listClose : {
      name: "ListCloseEvent",
      description: "An event which is fired when a list dropdown is closed.",
      link: "org/dwcj/component/list/event/ListCloseEvent"
    },
    listClick : {
      name: "ListClickEvent",
      description: "This event is triggered when the user clicks an item from a List-based component.",
      link: "org/dwcj/component/list/event/ListClickEvent"
    },
  };

  const nameStyles = css`
    text-align: center;
  `

  return (
      <table>
        <thead>
          <tr>
            <th>Event</th>
            <th>Description</th>
          </tr>
        </thead>
        {
          base && (
            Object.entries(baseEvents).map(([key, value]) => (
              <tr key={key}>
                <td css={nameStyles}><JavadocLink type="engine" location={value.link} code="true">{value.name}</JavadocLink></td>
                <td>{value.description}</td>
              </tr>
            ))
          )
        }
        {
          events && (
            Object.entries(otherEvents)
            .filter(([key]) => events.includes(key))
            .map(([key, value]) => (
              
              <tr key={key}>
                <td css={nameStyles}><JavadocLink type="engine" location={value.link} code="true">{value.name}</JavadocLink></td>
                <td>{value.description}</td>
              </tr>
            ))
          )
        }
      </table>
  )
}
