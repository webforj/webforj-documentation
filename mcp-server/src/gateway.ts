import express from 'express';
import { spawn, ChildProcess } from 'child_process';
import { v4 as uuidv4 } from 'uuid';
import cors from 'cors';
import rateLimit from 'express-rate-limit';

interface Session {
  id: string;
  process: ChildProcess;
  lastActivity: Date;
  buffer: string;
}

export class MCPGatewayServer {
  private app = express();
  private sessions = new Map<string, Session>();
  private apiKeys = new Set<string>([
    'demo-api-key', // For testing
    // Add real API keys here
  ]);

  constructor() {
    this.setupMiddleware();
    this.setupRoutes();
    this.startSessionCleanup();
  }

  private setupMiddleware() {
    this.app.use(cors());
    this.app.use(express.json());
    
    // Rate limiting
    const limiter = rateLimit({
      windowMs: 15 * 60 * 1000, // 15 minutes
      max: 100 // limit each IP to 100 requests per windowMs
    });
    this.app.use('/api/', limiter);
  }

  private setupRoutes() {
    // Health check
    this.app.get('/health', (req, res) => {
      res.json({ status: 'ok', sessions: this.sessions.size });
    });

    // Create session
    this.app.post('/api/v1/sessions', this.authenticate, async (req, res) => {
      try {
        const sessionId = uuidv4();
        const mcpProcess = spawn('node', ['dist/index.js'], {
          cwd: process.cwd(),
          env: { ...process.env, NODE_ENV: 'production' }
        });

        const session: Session = {
          id: sessionId,
          process: mcpProcess,
          lastActivity: new Date(),
          buffer: ''
        };

        // Collect stdout data
        mcpProcess.stdout.on('data', (data) => {
          session.buffer += data.toString();
        });

        mcpProcess.stderr.on('data', (data) => {
          console.error(`Session ${sessionId} error:`, data.toString());
        });

        mcpProcess.on('error', (error) => {
          console.error(`Session ${sessionId} process error:`, error);
          this.sessions.delete(sessionId);
        });

        mcpProcess.on('exit', (code) => {
          console.log(`Session ${sessionId} exited with code ${code}`);
          this.sessions.delete(sessionId);
        });

        this.sessions.set(sessionId, session);
        
        res.json({ 
          sessionId,
          expiresIn: 300 // 5 minutes
        });
      } catch (error) {
        res.status(500).json({ error: 'Failed to create session' });
      }
    });

    // Send MCP request
    this.app.post('/api/v1/sessions/:sessionId/request', this.authenticate, async (req, res) => {
      const { sessionId } = req.params;
      const session = this.sessions.get(sessionId);

      if (!session) {
        return res.status(404).json({ error: 'Session not found or expired' });
      }

      try {
        session.lastActivity = new Date();
        const response = await this.sendRequest(session, req.body);
        res.json(response);
      } catch (error) {
        res.status(500).json({ error: error.message });
      }
    });

    // Close session
    this.app.delete('/api/v1/sessions/:sessionId', this.authenticate, (req, res) => {
      const { sessionId } = req.params;
      const session = this.sessions.get(sessionId);

      if (session) {
        session.process.kill();
        this.sessions.delete(sessionId);
      }

      res.json({ message: 'Session closed' });
    });

    // List available resources (stateless endpoint)
    this.app.get('/api/v1/resources', this.authenticate, async (req, res) => {
      try {
        // Create temporary session for single request
        const tempSession = await this.createTempSession();
        
        // Initialize MCP
        await this.sendRequest(tempSession, {
          jsonrpc: '2.0',
          method: 'initialize',
          params: {
            protocolVersion: '2024-11-05',
            capabilities: {},
            clientInfo: {
              name: 'gateway-client',
              version: '1.0.0'
            }
          },
          id: 1
        });

        // List resources
        const response = await this.sendRequest(tempSession, {
          jsonrpc: '2.0',
          method: 'resources/list',
          params: {},
          id: 2
        });

        // Clean up
        tempSession.process.kill();
        
        res.json(response.result);
      } catch (error) {
        res.status(500).json({ error: error.message });
      }
    });
  }

  private authenticate = (req: any, res: any, next: any) => {
    const apiKey = req.headers['x-api-key'] || 
                   req.headers.authorization?.replace('Bearer ', '');
    
    if (!apiKey || !this.apiKeys.has(apiKey)) {
      return res.status(401).json({ error: 'Invalid or missing API key' });
    }
    
    req.apiKey = apiKey;
    next();
  };

  private async createTempSession(): Promise<Session> {
    const mcpProcess = spawn('node', ['dist/index.js'], {
      cwd: process.cwd(),
      env: { ...process.env, NODE_ENV: 'production' }
    });

    const session: Session = {
      id: 'temp-' + uuidv4(),
      process: mcpProcess,
      lastActivity: new Date(),
      buffer: ''
    };

    mcpProcess.stdout.on('data', (data) => {
      session.buffer += data.toString();
    });

    return session;
  }

  private async sendRequest(session: Session, request: any): Promise<any> {
    return new Promise((resolve, reject) => {
      const timeout = setTimeout(() => {
        reject(new Error('Request timeout'));
      }, 30000); // 30 second timeout

      // Clear existing buffer
      session.buffer = '';

      // Wait for response
      const checkBuffer = setInterval(() => {
        const lines = session.buffer.split('\n');
        
        for (const line of lines) {
          if (line.trim()) {
            try {
              const response = JSON.parse(line);
              if (response.id === request.id) {
                clearInterval(checkBuffer);
                clearTimeout(timeout);
                resolve(response);
                return;
              }
            } catch (e) {
              // Not valid JSON yet, keep waiting
            }
          }
        }
      }, 10);

      // Send request
      session.process.stdin.write(JSON.stringify(request) + '\n');
    });
  }

  private startSessionCleanup() {
    setInterval(() => {
      const now = Date.now();
      const timeout = 5 * 60 * 1000; // 5 minutes

      for (const [id, session] of this.sessions) {
        if (now - session.lastActivity.getTime() > timeout) {
          console.log(`Cleaning up expired session: ${id}`);
          session.process.kill();
          this.sessions.delete(id);
        }
      }
    }, 60000); // Check every minute
  }

  start(port = 3001) {
    this.app.listen(port, () => {
      console.log(`🚀 MCP Gateway Server running on port ${port}`);
      console.log(`📍 API endpoint: http://localhost:${port}/api/v1`);
      console.log(`🔑 Demo API key: demo-api-key`);
    });
  }
}

// Start server if run directly
if (import.meta.url === `file://${process.argv[1]}`) {
  const server = new MCPGatewayServer();
  server.start(parseInt(process.env.PORT || '3001'));
}