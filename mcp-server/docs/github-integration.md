# GitHub Repository Integration for MCP Server

This feature allows the MCP server to index Java code from public GitHub repositories, making external examples searchable and accessible through the documentation system.

## Configuration

Edit `mcp-server/config/github-repos.json` to specify which repositories to include:

```json
{
  "repositories": [
    {
      "owner": "webforj",
      "repo": "built-with-webforj",
      "branch": "main",
      "paths": ["webforj-howdy/src/main/java", "webforj-tictactoe/src/main/java"],
      "description": "Sample applications demonstrating webforJ framework capabilities"
    }
  ]
}
```

### Configuration Options:
- `owner`: GitHub organization or username
- `repo`: Repository name
- `branch`: Branch to scan (defaults to "main")
- `paths`: Optional array of paths to scan within the repository
- `description`: Description of what the repository demonstrates

## How It Works

1. During the MCP server build, the GitHub scanner:
   - Fetches Java files from configured repositories using the GitHub API
   - Caches files locally in `target/github-repos-cache/`
   - Extracts metadata (routes, descriptions, class names)
   - Indexes files for search and retrieval

2. The indexed demos are merged with local demos and become searchable through:
   - Component search
   - Advanced search
   - Demo code retrieval

## Authentication (Optional)

For higher GitHub API rate limits, set the `GITHUB_TOKEN` environment variable:

```bash
export GITHUB_TOKEN=your-github-personal-access-token
```

## Adding New Repositories

1. Edit `config/github-repos.json`
2. Add repository configuration
3. Rebuild the MCP server: `npm run build`
4. Restart the MCP server

## Benefits

- **Real-world examples**: Index complete applications beyond component demos
- **Pattern discovery**: Find usage patterns across multiple projects
- **Always up-to-date**: Pulls latest code from repositories
- **No maintenance**: External repos stay in sync automatically

## Example Use Cases

The default configuration includes:
- **webforj-howdy**: Demonstrates routing, Google Charts, responsive UI
- **webforj-tictactoe**: Shows namespaces, state sharing, game logic

These complement the component demos by showing complete application patterns.