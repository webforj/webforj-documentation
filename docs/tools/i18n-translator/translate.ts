import OpenAI from 'openai';
import { ProxyAgent } from 'proxy-agent';
import { options } from './config';
import pLimit from 'p-limit';

export async function translate(content: string, targetLanguage: string, isUIString: boolean = false) {
  const client = new OpenAI({
    baseURL: options.baseUrl,
    apiKey: options.apiKey,
    organization: options.organization,
    httpAgent: new ProxyAgent(),
  });

  const systemPrompt = isUIString 
    ? `You are a UI translator. Translate the following short UI text to ${targetLanguage}. 
       Keep the translation concise and appropriate for UI elements like buttons, menu items, and labels.
       Do not translate technical terms or brand names like: webforJ, DWC, BASIS, startforJ, HueCraft, Blog, JavaDocs.
       Return ONLY the translated text, nothing else.`
    : `You are translating technical documentation to ${targetLanguage}. 

⚠️ CRITICAL FAILURE PREVENTION ⚠️
If you translate ANY of the following, the build will FAIL:

🚫 NEVER TRANSLATE THESE EXACT WORDS IN JSX ATTRIBUTES:
• chip='since' → MUST stay chip='since' (NEVER chip='desde')
• chip='shadow' → MUST stay chip='shadow' (NEVER chip='sombra') 
• chip='name' → MUST stay chip='name' (NEVER chip='nombre')
• chip='experimental' → MUST stay chip='experimental'
• chip='foundation' → MUST stay chip='foundation'

🚫 NEVER TRANSLATE:
• Anchor IDs: {#heading-id} and (#anchor-name) - keep EXACT
• Admonitions: :::tip :::warning :::important :::note :::info
• Image paths: /img/path/file.png - keep EXACT
• Component names: <DocChip>, <JavadocLink>, <ComponentDemo>, <Head>, <Tabs>, <TabItem>
• All JSX attributes and their values
• Import statements: import Component from 'path' - keep EXACT
• URLs, file paths, module paths
• Brand names: webforJ, DWC, BASIS, startforJ, HueCraft
• DO NOT add markdown code blocks around JSX components
• DO NOT wrap HTML/JSX tags in triple-backtick code blocks

✅ ONLY TRANSLATE:
• Heading text: ## Configuration {#config} → ## Configuración {#config}
• Body paragraphs and descriptions
• Image alt text: ![Docker setup](/img/x.png) → ![Configuración Docker](/img/x.png)
• Text inside JSX tags: <JavadocLink>docs</JavadocLink> → <JavadocLink>documentos</JavadocLink>

🔥 CRITICAL EXAMPLES - STUDY THESE:
❌ WRONG: <DocChip chip='desde' label='24.10' />
✅ CORRECT: <DocChip chip='since' label='24.10' />

❌ WRONG: <DocChip chip='sombra' />  
✅ CORRECT: <DocChip chip='shadow' />

❌ WRONG: :::importante
✅ CORRECT: :::important

❌ WRONG: {#configuracion}
✅ CORRECT: {#configuration}

❌ WRONG: [link](#configuracion)  
✅ CORRECT: [link](#configuration)

❌ WRONG: Missing import statement
✅ CORRECT: import RadiusBox from '@site/src/components/DWCTheme/RadiusBox/RadiusBox';

❌ WRONG: importar ShadowBox desde '@site/...'
✅ CORRECT: import ShadowBox from '@site/src/components/DWCTheme/ShadowBox/ShadowBox';

❌ WRONG: Adding triple-backtick markdown code blocks around JSX components
❌ WRONG: Wrapping HTML tags like <Head> in html/jsx code blocks  
❌ WRONG: Adding language specifiers like html, jsx around components

✅ CORRECT: Keep JSX components exactly as they appear without any markdown formatting
✅ CORRECT: <Head> stays as <Head>, not wrapped in any code blocks
✅ CORRECT: <DocChip> stays as <DocChip>, not wrapped in any code blocks

🎯 YOUR TASK: Translate ONLY the readable text content. Keep ALL technical syntax, attributes, IDs, component names, import statements, and JSX components exactly as they appear without adding any markdown code block formatting.

Return only the translated content.`;

  const chatCompletion = await client.chat.completions.create({
    messages: [
      {
        role: 'system',
        content: systemPrompt,
      },
      { role: 'user', content: content },
    ],
    model: options.model,
  });

  return {
    translatedText: chatCompletion.choices[0].message.content ?? '',
    usage: chatCompletion.usage,
  };
}

export interface TranslationTask {
  content: string;
  locale: string;
  isUIString: boolean;
  key?: string; // for JSON translations
  file?: string; // for markdown translations
}

export interface TranslationResult {
  translatedText: string;
  usage?: any;
  key?: string;
  file?: string;
}

export async function translateBatch(
  tasks: TranslationTask[],
  onProgress?: (completed: number, total: number) => void
): Promise<TranslationResult[]> {
  const batchSize = parseInt(options.batchSize, 10) || 200;
  const limit = pLimit(batchSize);
  
  const results: TranslationResult[] = [];
  const promises = tasks.map((task, index) => 
    limit(async () => {
      try {
        const { translatedText, usage } = await translate(
          task.content,
          task.locale,
          task.isUIString
        );
        
        const result: TranslationResult = {
          translatedText,
          usage,
          key: task.key,
          file: task.file,
        };
        
        results[index] = result;
        
        if (onProgress) {
          const completed = results.filter(r => r !== undefined).length;
          onProgress(completed, tasks.length);
        }
        
        return result;
      } catch (error) {
        console.error(`Translation failed for ${task.file || task.key}: ${error}`);
        throw error;
      }
    })
  );
  
  await Promise.all(promises);
  return results;
}