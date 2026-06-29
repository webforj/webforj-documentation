---
sidebar_position: 1
title: Managing Resources
description: Manage JavaScript, CSS, and other assets in webforJ apps with declarative annotations, runtime injection APIs, and custom URL protocols.
hide_table_of_contents: true
hide_giscus_comments: true
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

Applications rely on various types of resources, such as JavaScript, CSS, and images. This document provides a comprehensive technical exploration of webforJ’s resource-handling mechanisms, covering declarative annotations, programmatic API methods, and custom protocol utilization.  

webforJ adopts a modular approach to resource management, offering multiple mechanisms to address different app needs:  

- **Frontend bundler**: Bring npm packages, component frameworks, and stylesheet languages into the app through a compiled entry. This is the default path for frontend assets, and it does everything the annotations do.  
- **Declarative Annotations**: Embed JavaScript and CSS resources at the components or app level, with no build step.  
- **API-Based Dynamic Injection**: Inject resources at runtime to enable dynamic app behavior.  
- **Custom Protocols**: Provide standardized methodologies for resource access.  
- **File Streaming and Controlled Downloads**: Facilitate managed retrieval and transmission of resource files.  

## Topics {#topics}

<DocCardList className="topics-section" />