# Hosted MCP Server Architecture

## Overview

To provide MCP services to customers, we need to bridge the stdio-based MCP protocol to a network-accessible service.

## Architecture Components

### 1. API Gateway Layer
```
┌─────────────────────────────────────────────────────────┐
│                    API Gateway (HTTPS)                   │
│  - Authentication (API keys)                             │
│  - Rate limiting                                         │
│  - Request routing                                       │
│  - SSL termination                                      │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
```

### 2. MCP Session Manager
```
┌─────────────────────────────────────────────────────────┐
│                  MCP Session Manager                     │
│  - Creates MCP process per session                       │
│  - Manages process lifecycle                             │
│  - Handles stdio ↔ HTTP translation                      │
│  - Session timeout management                            │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
```

### 3. MCP Server Instances
```
┌─────────────────────────────────────────────────────────┐
│                  MCP Server Processes                    │
│  - One process per active session                        │
│  - Isolated execution environment                        │
│  - Read-only access to documentation                     │
└─────────────────────────────────────────────────────────┘
```

## Implementation Options

### Option A: RESTful API

```typescript
// REST endpoints
POST   /api/v1/sessions          // Create new MCP session
POST   /api/v1/sessions/{id}/request  // Send MCP request
DELETE /api/v1/sessions/{id}     // Close session

// Example usage
const response = await fetch('https://mcp.webforj.com/api/v1/sessions', {
  method: 'POST',
  headers: {
    'Authorization': 'Bearer YOUR_API_KEY',
    'Content-Type': 'application/json'
  }
});

const { sessionId } = await response.json();

// Send MCP request
const mcpResponse = await fetch(`https://mcp.webforj.com/api/v1/sessions/${sessionId}/request`, {
  method: 'POST',
  headers: {
    'Authorization': 'Bearer YOUR_API_KEY',
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    jsonrpc: '2.0',
    method: 'resources/list',
    params: {},
    id: 1
  })
});
```

### Option B: WebSocket API

```typescript
// WebSocket connection
const ws = new WebSocket('wss://mcp.webforj.com/ws', {
  headers: {
    'Authorization': 'Bearer YOUR_API_KEY'
  }
});

ws.on('open', () => {
  // Send MCP initialization
  ws.send(JSON.stringify({
    jsonrpc: '2.0',
    method: 'initialize',
    params: {
      protocolVersion: '2024-11-05',
      capabilities: {},
      clientInfo: {
        name: 'webforj-client',
        version: '1.0.0'
      }
    },
    id: 1
  }));
});

ws.on('message', (data) => {
  const response = JSON.parse(data);
  // Handle MCP response
});
```

### Option C: GraphQL API

```graphql
type Query {
  listResources: [Resource!]!
  readResource(uri: String!): ResourceContent!
  searchComponents(query: String!): [Component!]!
}

type Mutation {
  createSession: Session!
  callTool(tool: String!, arguments: JSON!): ToolResult!
}
```

## Security Considerations

### 1. Authentication
- API key per customer/application
- Optional OAuth2 for user-specific access
- Rate limiting per API key

### 2. Resource Isolation
- Each session runs in isolated process
- No access to host filesystem beyond documentation
- Memory and CPU limits per session

### 3. Network Security
- HTTPS only
- CORS configuration for browser clients
- IP allowlisting option for enterprise

## Deployment Options

### 1. Containerized (Docker/Kubernetes)

```dockerfile
FROM node:20-alpine
WORKDIR /app

# Copy built MCP server
COPY dist/ ./dist/
COPY package*.json ./
COPY target/javadocs-extracted/ ./javadocs/

# Install production dependencies
RUN npm ci --production

# Security: Run as non-root user
USER node

# Health check endpoint
HEALTHCHECK CMD curl -f http://localhost:3000/health || exit 1

CMD ["node", "gateway-server.js"]
```

### 2. Serverless (AWS Lambda)

```typescript
// lambda-handler.ts
export const handler = async (event: APIGatewayEvent) => {
  const { httpMethod, path, body } = event;
  
  if (httpMethod === 'POST' && path === '/mcp-request') {
    // Spawn MCP process, send request, return response
    const mcp = spawn('node', ['index.js']);
    // ... handle stdio communication
  }
};
```

### 3. Edge Functions (Cloudflare Workers)

For lower latency globally distributed access.

## Pricing Model Options

1. **Per Request**: $0.001 per MCP request
2. **Per Session**: $0.10 per session hour
3. **Subscription**: $99/month unlimited access
4. **Enterprise**: Custom pricing with SLA

## Client Libraries

Provide SDKs for easy integration:

```typescript
// webforj-mcp-client.ts
import { MCPClient } from '@webforj/mcp-client';

const client = new MCPClient({
  apiKey: 'YOUR_API_KEY',
  endpoint: 'https://mcp.webforj.com'
});

// Use MCP through simple API
const components = await client.listComponents();
const buttonDocs = await client.getComponent('button');
const demos = await client.searchDemos('form validation');
```

## Monitoring & Analytics

- Request volume per customer
- Popular resources/components
- Error rates and latency
- Session duration metrics

## Example Gateway Implementation

```typescript
// gateway-server.ts
import express from 'express';
import { spawn } from 'child_process';
import { v4 as uuidv4 } from 'uuid';

interface Session {
  id: string;
  process: ChildProcess;
  lastActivity: Date;
  apiKey: string;
}

class MCPGateway {
  private sessions = new Map<string, Session>();
  private app = express();

  constructor() {
    this.app.use(express.json());
    this.setupRoutes();
    this.startCleanupTimer();
  }

  private setupRoutes() {
    // Create session
    this.app.post('/api/v1/sessions', this.authenticate, async (req, res) => {
      const sessionId = uuidv4();
      const mcpProcess = spawn('node', ['dist/index.js'], {
        env: { ...process.env, NODE_ENV: 'production' }
      });

      this.sessions.set(sessionId, {
        id: sessionId,
        process: mcpProcess,
        lastActivity: new Date(),
        apiKey: req.apiKey
      });

      res.json({ sessionId });
    });

    // Send MCP request
    this.app.post('/api/v1/sessions/:id/request', this.authenticate, async (req, res) => {
      const session = this.sessions.get(req.params.id);
      if (!session || session.apiKey !== req.apiKey) {
        return res.status(404).json({ error: 'Session not found' });
      }

      try {
        const response = await this.sendMCPRequest(session, req.body);
        session.lastActivity = new Date();
        res.json(response);
      } catch (error) {
        res.status(500).json({ error: error.message });
      }
    });
  }

  private authenticate = (req, res, next) => {
    const apiKey = req.headers.authorization?.replace('Bearer ', '');
    if (!apiKey || !this.isValidApiKey(apiKey)) {
      return res.status(401).json({ error: 'Invalid API key' });
    }
    req.apiKey = apiKey;
    next();
  };

  private async sendMCPRequest(session: Session, request: any): Promise<any> {
    return new Promise((resolve, reject) => {
      let response = '';
      
      const timeout = setTimeout(() => {
        reject(new Error('Request timeout'));
      }, 30000);

      session.process.stdout.once('data', (data) => {
        clearTimeout(timeout);
        try {
          resolve(JSON.parse(data.toString()));
        } catch (e) {
          reject(new Error('Invalid response'));
        }
      });

      session.process.stdin.write(JSON.stringify(request) + '\n');
    });
  }

  private startCleanupTimer() {
    setInterval(() => {
      const now = Date.now();
      for (const [id, session] of this.sessions) {
        if (now - session.lastActivity.getTime() > 300000) { // 5 minutes
          session.process.kill();
          this.sessions.delete(id);
        }
      }
    }, 60000); // Check every minute
  }

  start(port = 3000) {
    this.app.listen(port, () => {
      console.log(`MCP Gateway listening on port ${port}`);
    });
  }
}
```