#!/bin/bash

# Start webforJ MCP Server in production mode

# Set production environment
export NODE_ENV=production
export MCP_PORT=${MCP_PORT:-3001}

# Load production configuration if exists
if [ -f .env.production ]; then
  export $(cat .env.production | grep -v '^#' | xargs)
fi

# Ensure the server is built
if [ ! -d "dist" ]; then
  echo "Building MCP server..."
  npm run build
fi

# Start the server
echo "Starting webforJ MCP Server on port $MCP_PORT..."
exec node dist/index.js