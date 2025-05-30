#!/bin/bash

# Script to download and extract webforJ JavaDocs for MCP server
# This is an alternative to running the full Maven build

echo "📥 Downloading webforJ JavaDocs..."

# Get the directory where the script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"

# Change to project root
cd "$PROJECT_ROOT" || {
    echo "❌ Error: Could not change to project root directory"
    exit 1
}

echo "📍 Working in: $PROJECT_ROOT"

# Create directories
mkdir -p target/javadocs
mkdir -p target/javadocs-extracted

# Counter for successful downloads
DOWNLOADED=0

# Function to download a JavaDoc artifact
download_javadoc() {
    local artifact=$1
    local name=$2
    
    echo "⬇️  Downloading $name JavaDocs..."
    if mvn -q dependency:copy -Dartifact=$artifact -DoutputDirectory=target/javadocs 2>/dev/null; then
        echo "✅ $name downloaded successfully"
        ((DOWNLOADED++))
        return 0
    else
        echo "⚠️  $name JavaDocs not available (this is normal for some modules)"
        return 1
    fi
}

# Try to download each JavaDoc JAR
download_javadoc "com.webforj:webforj-foundation:25.01:jar:javadoc" "webforj-foundation"
download_javadoc "com.webforj:webforj-data:25.01:jar:javadoc" "webforj-data"
download_javadoc "com.webforj:webforj-components:25.01:jar:javadoc" "webforj-components"

# Try additional modules that might have JavaDocs
download_javadoc "com.webforj:webforj:25.01:jar:javadoc" "webforj-core"

# Try earlier versions if 25.01 doesn't have JavaDocs
if [ $DOWNLOADED -eq 0 ]; then
    echo ""
    echo "🔍 Version 25.01 JavaDocs not found, trying version 24.22..."
    download_javadoc "com.webforj:webforj-foundation:24.22:jar:javadoc" "webforj-foundation (24.22)"
    download_javadoc "com.webforj:webforj-data:24.22:jar:javadoc" "webforj-data (24.22)"
    download_javadoc "com.webforj:webforj-components:24.22:jar:javadoc" "webforj-components (24.22)"
fi

if [ $DOWNLOADED -eq 0 ]; then
    echo ""
    echo "❌ No JavaDoc JARs were downloaded. Possible reasons:"
    echo "   - JavaDocs might not be published for these versions"
    echo "   - Network connectivity issues"
    echo "   - Maven repository temporarily unavailable"
    echo ""
    echo "💡 Alternative: You can generate JavaDocs locally if you have the webforJ source code:"
    echo "   git clone https://github.com/webforj/webforj.git"
    echo "   cd webforj"
    echo "   mvn javadoc:aggregate"
    echo ""
    echo "The MCP server will still work but without enhanced API information."
    echo ""
    echo "📝 Note: The MCP server can still provide:"
    echo "   - Documentation content"
    echo "   - Demo code samples"
    echo "   - Component descriptions"
    echo "   - Basic API patterns from documentation"
    exit 0
fi

# Extract JavaDocs
echo ""
echo "📦 Extracting JavaDocs..."
cd target/javadocs-extracted || {
    echo "❌ Error: Could not change to extraction directory"
    exit 1
}

# Count extracted files
EXTRACTED=0

# Extract each JAR
for jar in ../javadocs/*-javadoc.jar; do
    if [ -f "$jar" ]; then
        JAR_NAME=$(basename "$jar")
        echo "   Extracting $JAR_NAME..."
        if jar xf "$jar" 2>/dev/null; then
            ((EXTRACTED++))
        else
            echo "   ⚠️  Warning: Could not extract $JAR_NAME"
        fi
    fi
done

cd ../..

# Summary
echo ""
if [ $EXTRACTED -gt 0 ]; then
    echo "✅ JavaDocs extracted successfully!"
    echo "📊 Summary: $DOWNLOADED JARs downloaded, $EXTRACTED JARs extracted"
    echo "📁 Location: $PROJECT_ROOT/target/javadocs-extracted/"
    echo ""
    echo "You can now start the MCP server with enhanced API information."
else
    echo "⚠️  Warning: JavaDocs were downloaded but could not be extracted"
    echo "The MCP server will still work but without enhanced API information."
fi

# Check if we have at least some HTML files
if [ -d "target/javadocs-extracted" ]; then
    HTML_COUNT=$(find target/javadocs-extracted -name "*.html" 2>/dev/null | wc -l | tr -d ' ')
    if [ "$HTML_COUNT" -gt 0 ]; then
        echo "📄 Found $HTML_COUNT HTML documentation files"
    fi
fi