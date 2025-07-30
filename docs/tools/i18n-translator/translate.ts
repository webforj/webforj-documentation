import OpenAI from 'openai';
import { ProxyAgent } from 'proxy-agent';
import { options } from './config';

export async function translate(content: string, targetLanguage: string) {
  const client = new OpenAI({
    baseURL: options.baseUrl,
    apiKey: options.apiKey,
    organization: options.organization,
    httpAgent: new ProxyAgent(),
  });

  const chatCompletion = await client.chat.completions.create({
    messages: [
      {
        role: 'system',
        content: `You are a professional technical documentation translator specializing in software development content. 

Translate the following content to ${targetLanguage} while following these rules:
1. Preserve ALL code blocks but translate any comments within the code to ${targetLanguage}
2. Keep technical terms like class names, method names, and API endpoints unchanged
3. Do not translate brand names: webforJ, DWC, BASIS, startforJ, HueCraft
4. Preserve markdown formatting and component syntax (like <DocChip>, <JavadocLink>)
5. Keep import statements and package names unchanged
6. Maintain professional technical writing style
7. For component attributes, only translate the values, not the attribute names themselves
8. Keep URLs and file paths unchanged

Return only the translated content with no additional commentary.`,
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