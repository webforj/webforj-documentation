# 🌍 webforJ Documentation Translation Tools

## Overview

This tool automates the translation of webforJ documentation using OpenAI's GPT-4 API. It intelligently translates both Markdown documentation files and UI strings while preserving technical accuracy.

**Key Features:**
- 🤖 **AI-Powered**: Uses OpenAI GPT-4 for high-quality, context-aware translations
- 🎯 **Smart Caching**: Tracks file changes using MD5 hashes to avoid re-translating unchanged content
- 🛡️ **Technical Term Protection**: Automatically preserves technical terms from Vale's vocabulary
- 📦 **Batch Processing**: Efficiently handles large documentation sets with configurable batch sizes
- 🔍 **Intelligent Detection**: Automatically identifies untranslated English content
- 🎨 **Format Preservation**: Maintains Markdown structure, code blocks, and interpolation syntax

## 🚀 Quick Start Guide

**TLDR**
1. **Extract new strings before translating**: `npm run write-translations`
2. **Generate stable heading IDs**: `npx docusaurus write-heading-ids` (prevents broken links when translating)
3. **Run translation for all locales**: `npm run translate`
4. **Vale whitelist is automatically loaded** - terms in `.github/.styles/config/vocabularies/webforj/accept.txt` won't be translated
5. **All strings in code.json are processed** - Translates the code that's been manually marked in the JSX components used throughout the site
6. **Environment setup**: Relies on an OpenAI API key to function correctly. This is set up in the docs repo as a secret, but needs a variable in .env if running locally.

## 📁 File Structure

```
tools/i18n-translator/
├── README.md         # This documentation file
├── config.ts         # Configuration and whitelist management
├── index.ts          # Main entry point and orchestration
├── translate.ts      # Translation logic and API calls
└── utils.ts          # Utility functions for file handling
```

## 🔧 Tool Descriptions

### config.ts
**Purpose**: Manages configuration settings and translation whitelist
- **Key Features**:
  - Dynamically loads Vale's accept.txt file for consistent terminology
  - Combines Vale terms with additional technical terms
  - Configures OpenAI API settings
  - Sets batch size for translation requests (default: 250)
  - Sets which openAI model is used for translation
- **Important**: Whitelist prevents translation of technical terms, brand names, and code-specific terminology

### index.ts
**Purpose**: Main orchestration file that coordinates the translation process
- **Key Features**:
  - Translates Markdown documentation files
  - Processes JSON translation files (navbar, footer, code.json)
  - Implements smart caching with MD5 hashes to skip unchanged files (Markdown files only)
  - Handles batch processing for efficiency
- **Special Functions**:
  - `translateDocs()`: Processes all Markdown files with frontmatter hash tracking
  - `translateCodeJSON()`: Handles the main code.json file with intelligent English detection
  - `translateJSON()`: Processes specific JSON files with prefix filtering

### translate.ts
**Purpose**: Handles the actual translation logic and OpenAI API interactions
- **Key Features**:
  - Batch translation support for efficiency
  - Progress tracking for long-running operations
  - Handles both documentation and UI string translations
  - Uses GPT-4 for high-quality translations
- **Translation Modes**:
  - **Documentation mode**: Preserves Markdown formatting, code blocks, and frontmatter
  - **UI String mode**: Focused, concise translations for interface elements

### utils.ts
**Purpose**: Provides utility functions for configuration loading
- **Key Features**:
  - Loads Docusaurus configuration from various file formats
  - Supports TypeScript, JavaScript, and module configurations
  - Uses jiti for transpilation of configuration files

## 🔗 Heading IDs for Stable Links

**Why this matters for translations:**
When Markdown headings are translated, their auto-generated IDs change, breaking existing links. The `write-heading-ids` command adds explicit IDs to preserve link stability across languages.

**Usage:**
```bash
npx docusaurus write-heading-ids
```

**What it does:**
- Adds explicit `{#heading-id}` to all Markdown headings
- Preserves existing links when headings are translated
- Ensures consistent anchor links across all locales

**Example:**
```markdown
<!-- Before -->
## Installation Guide

<!-- After running write-heading-ids -->
## Installation Guide {#installation-guide}
```

Run this command before translating to ensure all internal links remain functional!

## 📝 Usage Instructions

### Prerequisites
1. Create a `.env` file in the docs directory OR ensure GitHub secret present with following info:
   ```bash
   OPENAI_API_KEY=your-openai-api-key-here
   ```

2. Ensure all translation strings are extracted:
   ```bash
   npm run write-translations
   ```

### Running Translations

**Translate all configured locales:**
```bash
npm run translate
```

**Force retranslation of all files (ignore cache):**
```bash
npm run translate -- --force
```

Use the `--force` flag when you need to retranslate all files regardless of whether they've changed. This bypasses the hash-based caching system and is useful when:
- Updating translations after prompt improvements
- Fixing translation quality issues across all files
- Migrating to a new translation model
- Testing translation changes on the entire documentation set

**What happens during translation:**
1. Loads locale configuration from `docusaurus.config.js`
2. For each non-English locale:
   - Translates all Markdown documentation files
   - Translates UI strings in JSON files
   - Skips already-translated content (unless `--force` is used)
   - Preserves technical terms from the whitelist

### Translation Detection

The tool uses intelligent detection to identify untranslated content:
- **Markdown files**: Uses MD5 hash comparison with `_i18n_hash` in frontmatter
- **JSON strings**: Detects English patterns like:
  - Common English words (the, and, or, in, on, etc.)
  - Title case patterns (Show Code, Hide Code)
  - Specific UI patterns (Show, Hide, Navigate to)

## 🛠️ Configuration

### Environment Variables
- `OPENAI_API_KEY`: Required for translation API access
- `OPENAI_ORGANIZATION`: Optional organization ID

### Batch Size
Configured in `config.ts`:
```typescript
.option('--batchSize [number]', 'Number of concurrent translation requests', '250')
```

### Whitelist Management
The whitelist is automatically loaded from:
- **Primary source**: `.github/.styles/config/vocabularies/webforj/accept.txt`
- **Additional terms**: Defined in `config.ts` for terms not in Vale

## 🔍 Important Notes

1. **Never translate**:
   - Code blocks
   - Technical terms in the whitelist
   - URLs and file paths
   - Component/class names

2. **Preserve formatting**:
   - Markdown structure
   - HTML tags
   - Interpolation placeholders ({variable})
   - Frontmatter metadata

3. **Translation quality**:
   - Uses GPT-4 for accuracy
   - Maintains context awareness
   - Preserves technical accuracy

## 🐛 Troubleshooting

**Issue**: "Skip translation json config because you need to run `docusaurus write-translations` first"
**Solution**: Run `npm run write-translations -- --locale [locale]` before translating

**Issue**: Translation tool misses some strings
**Solution**: Check that strings match the English detection patterns in `translateCodeJSON()`

**Issue**: Technical terms are being translated
**Solution**: Add them to `.github/.styles/config/vocabularies/webforj/accept.txt` or the whitelist in the `config.ts` file.

## 📊 Translation Progress Tracking

The tool provides progress indicators:
- File-by-file progress for Markdown translations
- Token usage reporting for cost monitoring
- Skip notifications for already-translated content

## 🔄 Workflow Integration

1. **Before releasing new features**:
   ```bash
   npm run write-translations  # Extract new strings
   npm run translate          # Translate all locales
   ```

2. **After adding new components**:
   - Ensure translation keys use `translate()` or `<Translate>` components
   - Run extraction and translation commands
   - Verify translations in each locale

3. **Maintaining quality**:
   - Review translated content for technical accuracy
   - Update Vale accept.txt for new technical terms
   - Monitor token usage for cost management

4. **Using GitHub Actions**:
   - Navigate to **Actions** → **Update Translations** → **Run workflow**
   - Select target language (or "all" for all locales)
   - Check **Force retranslation** to bypass cache and retranslate everything
   - Click **Run workflow** to start the translation process