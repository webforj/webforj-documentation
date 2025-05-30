# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is the official documentation website for the webforJ framework. It's a sophisticated system combining:
- **Docusaurus-based documentation site** (`/docs`) - Static site with MDX support
- **Java-based interactive demos** (`/src/main/java/com/webforj/samples`) - Live component examples
- **MCP server** (`/mcp-server`) - AI-powered documentation access and search

## Development Commands

### Quick Start (Recommended)
```bash
# Terminal 1: Run Java demos with hot reload
mvn jetty:run

# Terminal 2: Run documentation site with hot reload
cd docs && npm start
```

### Documentation Development
```bash
cd docs
npm install          # Install dependencies (first time only)
npm start           # Start dev server at http://localhost:3000
npm run build       # Build static site
npm run serve       # Serve built documentation
```

### Java Demo Development
```bash
mvn clean install    # Build entire project
mvn jetty:run       # Run demos at http://localhost:8080
mvn jetty:run -Pprod # Run with production profile
mvn verify          # Run all tests including integration
mvn test            # Run unit tests only
```

### MCP Server Development
```bash
cd mcp-server
npm install         # Install dependencies
npm run build       # Build TypeScript
npm start          # Start in stdio mode
npm run gateway    # Start HTTP gateway at http://localhost:3001
```

## Architecture

### Build Pipeline
Maven orchestrates both Java and Node.js builds:
1. **Frontend Maven Plugin** downloads Node.js 23.5.0 and npm 10.9.2
2. Runs `npm install` and `npm run build` in `/docs`
3. Copies built documentation to `/target/classes/static`
4. Downloads and extracts JavaDoc JARs for component API documentation

### Routing and Integration
- **Java Routes**: Automatic discovery via `@Routify` annotation
- **Demo Views**: Each component demo uses `@Route` annotation
- **Static Assets**: Documentation served from `/target/classes/static`
- **URL Rewriting**: Basic redirects in `/src/webapp/WEB-INF/urlrewrite.xml`

### Component Demo Structure
```
/src/main/java/com/webforj/samples/views/{component}/
├── {Component}View.java          # Basic demo
├── {Component}ThemesView.java    # Theme variations
├── {Component}ExpansesView.java  # Size variations
└── {Component}EventView.java     # Event handling demo

/src/main/resources/css/{component}/
└── {feature}.css                 # Corresponding styles
```

### MCP Server Integration
The MCP server provides AI-powered documentation access:
- **Indexes**: Documentation content, Java demos, and JavaDoc
- **Search**: Component discovery and code examples
- **Deployment**: Stdio mode for Claude, HTTP gateway for web access

## Code Standards

### Java
- **Version**: Java 17+ required
- **Framework**: webforJ 25.01
- **Style**: Google Java Style Guide
- **Demo Pattern**: Extend `DemoPanel` or appropriate base class
- **Annotations**: Use `@Route` for routing, `@Routify` for package scanning

### Documentation
- **Format**: MDX for interactive content
- **Linting**: Vale prose linting (`.vale.ini`)
- **Components**: Custom React components in `/docs/src/components/DocsTools/`
- **JavaDoc Links**: Use https://javadoc.io/doc/com.webforj

## Testing

### Documentation
- Broken link checking during build
- Vale prose linting for content quality

### Java
- JUnit 5 for unit tests
- Maven Failsafe for integration tests
- Test discovery: `*Test.java` (unit), `*IT.java` (integration)

## Configuration Files

### Key Files
- `pom.xml` - Maven configuration with profiles and plugins
- `docusaurus.config.js` - Documentation site configuration
- `sidebars.js` - Documentation navigation structure
- `webforj-{profile}.conf` - BBj runtime configuration

### Maven Profiles
- **dev** (default): Development mode with debugging enabled
- **prod**: Production optimizations

## Creating New Component Documentation

1. **Create Java demo** in `/src/main/java/com/webforj/samples/views/{component}/`
2. **Add route** with `@Route("{component}")` annotation
3. **Create CSS** in `/src/main/resources/css/{component}/`
4. **Write docs** in `/docs/docs/components/{component}.md`
5. **Update sidebar** in `/docs/sidebars.js`

## Important URLs

- **Live Site**: https://docs.webforj.com
- **GitHub**: https://github.com/webforj/webforj-documentation
- **JavaDoc**: https://javadoc.io/doc/com.webforj
- **Main Framework**: https://github.com/webforj/webforj