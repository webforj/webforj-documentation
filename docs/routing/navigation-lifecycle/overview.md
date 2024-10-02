---
sidebar_position: 1
title: Navigation Lifecycle
---

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

Navigating through different views in a web app involves several stages, offering opportunities to perform actions before, during, or after a transition. The navigation lifecycle provides an event-driven system that allows developers to manage key aspects of navigation, such as validating data, controlling access, updating the UI, and handling cleanup.

This flexible system ensures smooth, consistent transitions by allowing developers to hook into critical points in the navigation process. Whether you need to block navigation, fetch data when a component is displayed, or manage unsaved changes, you have full control over the navigation flow through its lifecycle events and observers.

## Lifecycle events overview

The navigation process is governed by a series of events that are triggered during route transitions. These events allow you to react at specific points in the lifecycle:

1. **WillEnter**: Triggered before navigating to a route and before its component is attached to the DOM. This is ideal for tasks like authentication checks or blocking the navigation if necessary.
2. **DidEnter**: Triggered after the navigation is completed and the component is attached to the DOM. This event is suited for actions such as fetching data, running animations, or setting focus on UI elements.
3. **WillLeave**: Triggered before navigating away from the current route and before its component is removed from the DOM. It's useful for managing unsaved data, prompting the user for confirmation, or handling cleanup tasks.
4. **DidLeave**: Triggered after switching to a different route and after the component has been removed from the DOM. This event is ideal for clearing resources or resetting the UI for future use.

These events provide granular control over the navigation lifecycle, making it easier to manage complex transitions and ensure smooth interactions across routes.

# Topics

<DocCardList className="topics-section" />