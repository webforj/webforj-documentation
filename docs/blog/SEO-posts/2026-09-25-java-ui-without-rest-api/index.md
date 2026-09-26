---
title: "Your Spring Boot app doesn't need a REST API for the UI"
description: "When the frontend and backend are both Java, the REST layer between them is optional overhead — and naming what disappears makes the tradeoff concrete."
slug: java-ui-without-rest-api
date: 2026-09-25
authors: webforJ
tags: [spring, web development, full-stack]
image: ./cover.png
hide_table_of_contents: false

# --- Internal tracking (stripped at publish) ---
---

![cover](./cover.png)

A product owner requests a feature: search-as-you-type across the customer list. The backend is an afternoon. Add a Spring service, write the query, wire up the result. Done. Then the wiring to the frontend starts: add a REST endpoint, configure CORS, set up Axios in the React component, handle the loading state, handle error responses, connect the frontend build pipeline to the backend. The feature took an afternoon. The protocol between the feature and the user took another two days.

That overhead isn't part of the feature. It's the cost of a protocol boundary between two systems written in different languages.

<!-- truncate -->

## What a REST layer is for

A REST API between a frontend and backend is a formal contract: endpoints with documented shapes, request/response formats, error codes, versioning, CORS headers for cross-origin access. It's the right architecture when multiple consumers need that contract — a mobile app, a third-party integration, a partner system. The contract makes the backend independently deployable and independently testable.

When the only consumer of that contract is your own frontend, deployed alongside the backend, you're paying for the contract overhead without a counterparty who needs the contract.

The REST layer in a JS+Spring Boot full-stack app isn't wrong. It's the standard pattern and there are good reasons it became standard. But it's worth being clear about what it costs.

## The incidental complexity inventory

When a React frontend calls a Spring Boot backend, a specific set of problems appears — not because of the feature being built, but because of the protocol between the two layers:

**CORS configuration.** The browser enforces same-origin policy. A separate frontend dev server means setting up `@CrossOrigin` or a CORS filter on every endpoint, and debugging the difference between a CORS error and an actual server error.

**JSON serialization.** Spring returns POJOs; the frontend receives JSON. A change to the Java model requires a corresponding change to how the frontend interprets it. Adding a field means updating both sides.

**Axios or fetch().** Error handling for HTTP errors is separate from error handling for business logic. A 422 Unprocessable Entity means something different from a thrown exception, and the client has to distinguish them.

**State management.** The frontend now holds a local copy of data that lives on the server. Keeping those in sync — loading states, cache invalidation, optimistic updates — is a problem the backend doesn't have.

**Build pipeline integration.** React needs to be compiled and bundled. In a Spring Boot project that means either a separate build step, or a Maven/Gradle plugin that runs the frontend build, with its own configuration and its own failure modes.

Each item on this list is a real engineering problem. None of them are about the feature the product owner asked for.

## What disappears when both layers are Java

In a webforJ application, UI components are Java classes running in the same JVM as the Spring backend. A component can receive a Spring service via injection — the same injection mechanism any other Spring bean uses — and call its methods directly.

The full connection between the Spring integration and the component model is documented in the [Spring integration overview](/docs/integrations/spring/overview) and [composing components](/docs/building-ui/composing-components) guides. The structural consequence: when the component calls a service method, there's no HTTP request, no JSON serialization, no CORS negotiation, no client-side error state separate from server-side error state. A `RuntimeException` thrown in the service is a Java exception in the component, not an HTTP 500 that needs to be decoded.

The state management problem also changes shape. The UI component reads from the service on each render. There's no local cache to invalidate because there's no local copy — the component reads the data it needs when it needs it. The data lives in one place.

This is not a new pattern. GWT used the same model in the 2000s, with Java on both sides and an RPC mechanism in between. The architecture isn't novel; what's changed is that modern Java UI frameworks make it straightforward without requiring a separate RPC layer.

## When the REST API is still the right answer

A mobile app needs an API. A third-party system needs an API. A partner integration needs an API. Anything that isn't your own frontend, running on your own server, needs the formal contract that REST provides.

The argument here isn't that REST APIs are the wrong approach. It's that the REST layer is the wrong architectural unit when the only consumer is a UI that could be written in the same language as the backend and deployed on the same server.

If there's any chance the application will later need a public API — for a mobile client, for partner access, for user-facing integrations — build the REST layer now, deliberately, with the external consumer in mind. The full-stack Java pattern isn't a shortcut to a service you'll later need to expose. It's a different choice about who the consumers of your backend are.

## The actual tradeoff

The full-stack Java architecture trades one set of concerns for another. The protocol overhead disappears — no CORS, no fetch(), no JSON serialization between layers, no separate state management. What you take on instead: UI components are Java, which means the developers writing them need to know Java, and the rendering happens client-side, driven by Java. There's no static export of the frontend, no CDN-distributed bundle.

Whether that's a good trade depends on the team and the product. For applications built by Java teams, targeting internal users or customers on reliable connections, where the protocol overhead between frontend and backend layers is the engineering cost the team most wants to eliminate, it often is.

The product owner's feature is still an afternoon. But so is the wiring.
