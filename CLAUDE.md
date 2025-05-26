# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is the official documentation website for the webforJ framework. It combines:
- A Docusaurus-based documentation site (in `/docs`)
- Java-based interactive demo applications (in `/src/main/java/com/webforj/samples`)

## Common Development Commands

### Documentation Development
```bash
# From the docs/ directory
npm install          # Install dependencies (first time setup)
npm start            # Start Docusaurus dev server (http://localhost:3000)
npm run build        # Build static documentation site
npm run serve        # Serve the built documentation
```

### Demo Application Development
```bash
# From root directory
mvn clean install    # Build the entire project
mvn jetty:run        # Run demo server on http://localhost:8080
mvn verify           # Run all tests including integration tests
mvn test             # Run unit tests only
```

### Combined Development (Recommended)
```bash
# Terminal 1: Run the Java demos
mvn jetty:run

# Terminal 2: Run the documentation site
cd docs && npm start
```

## Architecture & Structure

### Documentation (`/docs`)
- **Content**: MDX/Markdown files in `/docs/docs/`
- **Blog**: Release notes and articles in `/docs/blog/`
- **Components**: React components in `/docs/src/components/`
- **Styling**: SCSS files in `/docs/src/css/`
- **Configuration**: `docusaurus.config.js` and `sidebars.js`

### Demo Applications (`/src/main/java/com/webforj/samples`)
- **Views**: Individual component demos in `/views/` subdirectories
- **Components**: Reusable demo components in `/components/`
- **Resources**: CSS, config files in `/src/main/resources/`
- **Application.java**: Main application entry point with routing

### Key Integration Points
1. **URL Mapping**: `/src/webapp/WEB-INF/urlrewrite.xml` handles routing between docs and demos
2. **Static Assets**: Documentation builds are copied to `/target/classes/static/`
3. **Hot Reload**: Jetty configured with 1-second scan interval for Java changes

## Code Standards

### Java Code
- Java 17+ required
- Follow Google Java Style Guide
- Use meaningful variable and method names
- Add Javadoc for public methods
- Demo views should extend appropriate base classes

### Documentation
- Use MDX for interactive content
- Follow Vale prose linting rules (`.vale.ini`)
- Link to Javadoc when referencing API: https://javadoc.io/doc/com.webforj
- Use custom components from `/docs/src/components/DocsTools/`

## Component Demo Pattern

When creating new component demos:
1. Create a view class in `/src/main/java/com/webforj/samples/views/{component}/`
2. Extend appropriate base class (e.g., `DemoPanel`)
3. Register route in `Application.java`
4. Add corresponding CSS in `/src/main/resources/css/{component}/`
5. Create documentation page in `/docs/docs/components/{component}.md`

## Testing

### Documentation
- Broken link checking during build
- Vale prose linting for content quality

### Java Demos
- Unit tests with JUnit 5
- Integration tests with Maven Failsafe
- Run with `mvn verify`

## Deployment Profiles

- **dev** (default): Uses `webforj-dev.conf`, includes development tools
- **prod**: Uses `webforj-prod.conf`, optimized for production

Switch profiles with: `mvn jetty:run -Pprod`

## Important URLs

- **Live Documentation**: https://docs.webforj.com
- **GitHub Repo**: https://github.com/webforj/webforj-documentation
- **Main webforJ Repo**: https://github.com/webforj/webforj
- **Javadoc**: https://javadoc.io/doc/com.webforj