# WebforJ Documentation MCP Server

This MCP (Model Context Protocol) server provides structured access to webforJ documentation, component metadata, and code samples. It's designed to help AI assistants understand and work with webforJ components.

**Important**: MCP servers communicate via stdio (standard input/output), not HTTP. There is no web interface or port to access - the server is designed to be used by MCP-compatible AI clients only.

## Features

- **Component Discovery**: Browse all webforJ components with metadata
- **Documentation Access**: Read documentation content with extracted code samples  
- **Demo Integration**: Access live demo URLs and source code
- **Search Capabilities**: Search components by name or description
- **Auto-indexing**: Rebuilds index when files change

## How MCP Works

MCP (Model Context Protocol) servers communicate via stdio (standard input/output), not HTTP. They're designed to be launched by AI assistants or MCP clients, which communicate with them through structured JSON messages over stdin/stdout.

**This means:**
- No web interface or HTTP port
- No direct browser access
- Communication only through MCP-compatible clients
- The server waits for commands on stdin and responds on stdout

## Installation

```bash
cd mcp-server
npm install
```

## Development

Start the MCP server in development mode:

```bash
npm run dev
```

Build TypeScript files:

```bash
npm run build
```

## Production Deployment

### Option 1: Standalone Server

Since the documentation is served by Jetty on port 8080, the MCP server runs as a separate process. Note: MCP servers communicate via stdio (standard input/output), not HTTP ports.

1. Create a production environment file:

```bash
cp .env.example .env.production
```

2. Edit `.env.production`:

```env
NODE_ENV=production
PROD_BASE_URL=https://docs.webforj.com
PROD_DEMO_URL=https://docs.webforj.com/webforj
```

3. Start the server:

```bash
./scripts/start-production.sh
```

### Option 2: Integration with Maven Build

Add to your Maven build process to rebuild the MCP index automatically:

```xml
<plugin>
  <groupId>org.codehaus.mojo</groupId>
  <artifactId>exec-maven-plugin</artifactId>
  <version>3.1.0</version>
  <executions>
    <execution>
      <id>build-mcp-index</id>
      <phase>prepare-package</phase>
      <goals>
        <goal>exec</goal>
      </goals>
      <configuration>
        <executable>mcp-server/scripts/build-with-maven.sh</executable>
      </configuration>
    </execution>
  </executions>
</plugin>
```

### Option 3: Systemd Service (Linux)

Create `/etc/systemd/system/webforj-mcp.service`:

```ini
[Unit]
Description=WebforJ MCP Server
After=network.target

[Service]
Type=simple
User=webforj
WorkingDirectory=/path/to/webforj-documentation/mcp-server
ExecStart=/usr/bin/node dist/index.js
Restart=always
Environment="NODE_ENV=production"

[Install]
WantedBy=multi-user.target
```

Enable and start:

```bash
sudo systemctl enable webforj-mcp
sudo systemctl start webforj-mcp
```

## Claude Desktop Configuration

To use this MCP server with Claude Desktop (the local Claude app), you have two options:

### Option A: Automatic Setup (Recommended)

Run the setup script from the mcp-server directory:

```bash
cd /path/to/webforj-documentation/mcp-server
npm install
npm run build
npm run setup:claude
```

This will automatically configure Claude Desktop for you. After running, restart Claude Desktop.

### Option B: Manual Setup

If you prefer to configure manually:

#### 1. Find your Claude Desktop configuration file

The configuration file location depends on your operating system:

- **macOS**: `~/Library/Application Support/Claude/claude_desktop_config.json`
- **Windows**: `%APPDATA%\Claude\claude_desktop_config.json`
- **Linux**: `~/.config/Claude/claude_desktop_config.json`

#### 2. Build the MCP server

First, ensure the MCP server is built:

```bash
cd /path/to/webforj-documentation/mcp-server
npm install
npm run build
```

#### 3. Edit the configuration file

Open the `claude_desktop_config.json` file and add the webforj-docs server:

```json
{
  "mcpServers": {
    "webforj-docs": {
      "command": "node",
      "args": ["/path/to/webforj-documentation/mcp-server/dist/index.js"],
      "env": {
        "NODE_ENV": "development"
      }
    }
  }
}
```

**Important**: Replace `/path/to/webforj-documentation` with the actual absolute path to your webforj-documentation repository.

#### 4. Example complete configuration

Here's what a complete configuration might look like:

```json
{
  "mcpServers": {
    "webforj-docs": {
      "command": "node",
      "args": ["/Users/yourusername/projects/webforj-documentation/mcp-server/dist/index.js"],
      "env": {
        "NODE_ENV": "development",
        "DOCS_ROOT": "../docs/docs",
        "DEMO_ROOT": "../src/main/java/com/webforj/samples"
      }
    }
  }
}
```

#### 5. Restart Claude Desktop

After saving the configuration:
1. Completely quit Claude Desktop (not just close the window)
2. Restart Claude Desktop
3. The MCP server will be automatically started when you open Claude

#### 6. Verify the connection

In a new Claude conversation, you can verify the MCP server is connected by asking:

- "What webforJ components are available?"
- "Show me the Button component documentation"
- "Search for components related to 'field'"

Claude will use the MCP server to access the documentation and provide accurate, up-to-date information about webforJ components.

#### Troubleshooting

If the MCP server doesn't seem to be working:

1. **Check logs**: Look for errors in Claude Desktop's logs
   - macOS: `~/Library/Logs/Claude/`
   - Windows: `%LOCALAPPDATA%\Claude\logs\`
   - Linux: `~/.local/share/Claude/logs/`

2. **Test the server manually**: Run the test script to ensure it works:
   ```bash
   cd /path/to/webforj-documentation/mcp-server
   node test-mcp.js
   ```

3. **Verify paths**: Ensure all paths in the configuration are absolute paths

4. **Check Node.js**: Ensure Node.js 18+ is installed and accessible from your PATH

## Available Resources

- `webforj://components` - List all components with metadata
- `webforj://component/{name}` - Get specific component details
- `webforj://demos` - List all interactive demos
- `webforj://docs` - List all documentation pages

## Available Tools

- `search_components` - Search components by query
- `get_component_code` - Get source code for a demo
- `rebuild_index` - Manually rebuild the index

## Architecture

```
┌─────────────────┐     ┌─────────────────┐
│                 │     │                 │
│  Jetty Server   │     │   MCP Server    │
│   (port 8080)   │     │  (port 3001)    │
│                 │     │                 │
│  - Docs (HTML)  │     │  - Component    │
│  - Java Demos   │     │    Metadata     │
│  - Static Assets│     │  - Search API   │
│                 │     │  - Code Index   │
└─────────────────┘     └─────────────────┘
         ↓                       ↓
    Web Browsers            AI Assistants
```

The MCP server reads the same source files but provides structured access optimized for AI consumption.

## Adding Custom Context

To add additional context files (tutorials, patterns, etc.) that aren't part of the main documentation:

1. Create the context directory:

```bash
mkdir -p mcp-context
```

2. Add a `manifest.json`:

```json
{
  "version": "1.0",
  "include": [
    {
      "path": "tutorials/**/*.md",
      "type": "tutorial"
    }
  ]
}
```

3. Add your content files and the MCP server will automatically include them.

## Troubleshooting

### Server won't start

- Check if the process is running: `ps aux | grep webforj-mcp`
- Verify Node.js version: `node --version` (requires 18+)
- Check logs: `npm run dev` for detailed output

### Index not updating

- Manually rebuild: Use the `rebuild_index` tool
- Check file permissions on documentation directories
- Verify paths in `config/default.json`

### Production issues

- Ensure `NODE_ENV=production` is set
- MCP uses stdio communication, not network ports
- Verify production URLs in configuration

## Contributing

1. Make changes in `src/`
2. Run `npm run build` to compile TypeScript
3. Test with `npm run dev`
4. Submit PR with tests if applicable