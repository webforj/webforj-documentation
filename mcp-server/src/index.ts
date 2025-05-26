#!/usr/bin/env node
import { WebForJMCPServer } from './server.js';

async function main() {
  try {
    const server = new WebForJMCPServer();
    await server.start();
  } catch (error) {
    console.error('Failed to start MCP server:', error);
    process.exit(1);
  }
}

// Handle graceful shutdown
process.on('SIGINT', () => {
  console.error('\nShutting down MCP server...');
  process.exit(0);
});

process.on('SIGTERM', () => {
  console.error('\nShutting down MCP server...');
  process.exit(0);
});

main().catch(console.error);