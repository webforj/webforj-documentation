# Hosted MCP Server Setup Guide

## Quick Start

### 1. Install Dependencies

```bash
cd mcp-server
npm install
```

### 2. Build the Server

```bash
npm run build
```

### 3. Start the Gateway Server

```bash
npm run gateway
# or for development with auto-reload:
npm run gateway:dev
```

The gateway server will start on port 3001 by default.

### 4. Test with the Web Client

Open `examples/gateway-client.html` in a browser and test the API.

## API Documentation

### Authentication

All API requests require an API key in one of these formats:
- Header: `X-API-Key: your-api-key`
- Header: `Authorization: Bearer your-api-key`

### Endpoints

#### POST /api/v1/sessions
Create a new MCP session.

**Response:**
```json
{
  "sessionId": "uuid",
  "expiresIn": 300
}
```

#### POST /api/v1/sessions/{sessionId}/request
Send an MCP request to an active session.

**Request Body:**
```json
{
  "jsonrpc": "2.0",
  "method": "resources/list",
  "params": {},
  "id": 1
}
```

**Response:** MCP protocol response

#### DELETE /api/v1/sessions/{sessionId}
Close an active session.

#### GET /api/v1/resources
List available resources (creates temporary session).

## Client SDK Examples

### JavaScript/TypeScript

```typescript
class WebforJMCPClient {
  constructor(private apiKey: string, private endpoint = 'https://mcp.webforj.com/api/v1') {}

  async createSession(): Promise<string> {
    const response = await fetch(`${this.endpoint}/sessions`, {
      method: 'POST',
      headers: {
        'X-API-Key': this.apiKey,
        'Content-Type': 'application/json'
      }
    });
    const { sessionId } = await response.json();
    return sessionId;
  }

  async request(sessionId: string, method: string, params: any = {}): Promise<any> {
    const response = await fetch(`${this.endpoint}/sessions/${sessionId}/request`, {
      method: 'POST',
      headers: {
        'X-API-Key': this.apiKey,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        jsonrpc: '2.0',
        method,
        params,
        id: Date.now()
      })
    });
    return response.json();
  }
}

// Usage
const client = new WebforJMCPClient('your-api-key');
const sessionId = await client.createSession();
const resources = await client.request(sessionId, 'resources/list');
```

### Python

```python
import requests
import json

class WebforJMCPClient:
    def __init__(self, api_key, endpoint='https://mcp.webforj.com/api/v1'):
        self.api_key = api_key
        self.endpoint = endpoint
        self.session_id = None
    
    def create_session(self):
        response = requests.post(
            f'{self.endpoint}/sessions',
            headers={'X-API-Key': self.api_key}
        )
        data = response.json()
        self.session_id = data['sessionId']
        return self.session_id
    
    def request(self, method, params=None):
        if not self.session_id:
            raise Exception('No active session')
        
        response = requests.post(
            f'{self.endpoint}/sessions/{self.session_id}/request',
            headers={
                'X-API-Key': self.api_key,
                'Content-Type': 'application/json'
            },
            json={
                'jsonrpc': '2.0',
                'method': method,
                'params': params or {},
                'id': 1
            }
        )
        return response.json()

# Usage
client = WebforJMCPClient('your-api-key')
client.create_session()
resources = client.request('resources/list')
```

### Java

```java
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebforJMCPClient {
    private final String apiKey;
    private final String endpoint;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private String sessionId;
    
    public WebforJMCPClient(String apiKey) {
        this.apiKey = apiKey;
        this.endpoint = "https://mcp.webforj.com/api/v1";
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    
    public String createSession() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(endpoint + "/sessions"))
            .header("X-API-Key", apiKey)
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();
            
        HttpResponse<String> response = httpClient.send(request, 
            HttpResponse.BodyHandlers.ofString());
            
        var data = objectMapper.readTree(response.body());
        sessionId = data.get("sessionId").asText();
        return sessionId;
    }
    
    public JsonNode request(String method, Map<String, Object> params) throws Exception {
        var requestBody = Map.of(
            "jsonrpc", "2.0",
            "method", method,
            "params", params != null ? params : Map.of(),
            "id", System.currentTimeMillis()
        );
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(endpoint + "/sessions/" + sessionId + "/request"))
            .header("X-API-Key", apiKey)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(
                objectMapper.writeValueAsString(requestBody)))
            .build();
            
        HttpResponse<String> response = httpClient.send(request, 
            HttpResponse.BodyHandlers.ofString());
            
        return objectMapper.readTree(response.body());
    }
}
```

## Deployment Options

### Docker

```dockerfile
FROM node:20-alpine
WORKDIR /app

# Copy package files
COPY package*.json ./
RUN npm ci --production

# Copy built application
COPY dist/ ./dist/
COPY target/javadocs-extracted/ ./javadocs/

# Security
USER node
EXPOSE 3001

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD node -e "require('http').get('http://localhost:3001/health', (r) => process.exit(r.statusCode === 200 ? 0 : 1))"

CMD ["node", "dist/gateway.js"]
```

Build and run:
```bash
docker build -t webforj-mcp-gateway .
docker run -p 3001:3001 -e API_KEYS=key1,key2,key3 webforj-mcp-gateway
```

### Kubernetes

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: webforj-mcp-gateway
spec:
  replicas: 3
  selector:
    matchLabels:
      app: webforj-mcp-gateway
  template:
    metadata:
      labels:
        app: webforj-mcp-gateway
    spec:
      containers:
      - name: gateway
        image: webforj-mcp-gateway:latest
        ports:
        - containerPort: 3001
        env:
        - name: NODE_ENV
          value: production
        - name: API_KEYS
          valueFrom:
            secretKeyRef:
              name: mcp-secrets
              key: api-keys
        resources:
          requests:
            memory: "256Mi"
            cpu: "250m"
          limits:
            memory: "512Mi"
            cpu: "500m"
        livenessProbe:
          httpGet:
            path: /health
            port: 3001
          initialDelaySeconds: 30
          periodSeconds: 10
---
apiVersion: v1
kind: Service
metadata:
  name: webforj-mcp-gateway
spec:
  selector:
    app: webforj-mcp-gateway
  ports:
  - port: 80
    targetPort: 3001
  type: LoadBalancer
```

### AWS Lambda

Use AWS API Gateway + Lambda for serverless deployment:

```typescript
// lambda-handler.ts
import { APIGatewayProxyHandler } from 'aws-lambda';
import { spawn } from 'child_process';

export const handler: APIGatewayProxyHandler = async (event) => {
  if (event.path === '/api/v1/resources' && event.httpMethod === 'GET') {
    // Create temporary MCP process
    const mcp = spawn('./mcp-server', [], {
      env: { NODE_ENV: 'production' }
    });
    
    // Handle request/response
    // ...
    
    return {
      statusCode: 200,
      body: JSON.stringify(response)
    };
  }
};
```

## Security Best Practices

1. **API Key Management**
   - Store API keys in environment variables
   - Rotate keys regularly
   - Use different keys for different environments

2. **Rate Limiting**
   - Default: 100 requests per 15 minutes per IP
   - Customize in `gateway.ts` based on your needs

3. **Session Management**
   - Sessions expire after 5 minutes of inactivity
   - Limit concurrent sessions per API key

4. **Process Isolation**
   - Each session runs in a separate process
   - No filesystem access beyond documentation
   - CPU and memory limits enforced

5. **HTTPS Only**
   - Always use TLS in production
   - Consider certificate pinning for mobile apps

## Monitoring

### Metrics to Track

- Active sessions count
- Request rate per API key
- Response times
- Error rates
- Resource usage per session

### Example Prometheus Metrics

```typescript
// Add to gateway.ts
import { register, Counter, Gauge, Histogram } from 'prom-client';

const sessionsGauge = new Gauge({
  name: 'mcp_active_sessions',
  help: 'Number of active MCP sessions'
});

const requestsCounter = new Counter({
  name: 'mcp_requests_total',
  help: 'Total MCP requests',
  labelNames: ['method', 'status']
});

const requestDuration = new Histogram({
  name: 'mcp_request_duration_seconds',
  help: 'MCP request duration',
  labelNames: ['method']
});

// Metrics endpoint
app.get('/metrics', (req, res) => {
  res.set('Content-Type', register.contentType);
  res.end(register.metrics());
});
```

## Troubleshooting

### Common Issues

1. **Session timeouts**
   - Increase timeout in `sendRequest()` method
   - Check if MCP server is building index on first request

2. **High memory usage**
   - Implement session pooling
   - Add memory limits per process

3. **Slow responses**
   - Pre-warm MCP processes
   - Cache common requests

### Debug Mode

Set environment variable for verbose logging:
```bash
DEBUG=mcp:* npm run gateway
```