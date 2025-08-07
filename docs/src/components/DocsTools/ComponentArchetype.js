/** @jsxImportSource @emotion/react */

import Tabs from "@theme/Tabs";
import TabItem from "@theme/TabItem";
import CodeBlock from "@theme/CodeBlock";
import { translate } from '@docusaurus/Translate';

export default function ComponentArchetype({ project, flavor = "webforj" }) {
  return (<>
    <p>{translate({
      id: 'component.archetype.intro',
      message: 'To create and scaffold a new {project} project, follow these steps:',
      description: 'Introduction text for archetype component'
    }, {project: <code>{project}</code>})}
    </p>
    <ol>
      <li><strong>{translate({
        id: 'component.archetype.step1.title',
        message: 'Navigate to the proper directory',
        description: 'Step 1 title'
      })}</strong>: {translate({
        id: 'component.archetype.step1.desc',
        message: 'Open a terminal and move to the folder where you want to create your new project.',
        description: 'Step 1 description'
      })}
      </li>
      <li><strong>{translate({
        id: 'component.archetype.step2.title',
        message: 'Run the archetype:generate command',
        description: 'Step 2 title'
      })}</strong>: {translate({
        id: 'component.archetype.step2.desc',
        message: 'Use the Maven command below, and customize the groupId, artifactId, and version as needed for your project.',
        description: 'Step 2 description'
      })}
      </li>
    </ol>

    <div class="videos-container">
      <video controls>
        <source src={`https://cdn.webforj.com/webforj-documentation/video/archetypes/${project}-archetype.mp4`} type="video/mp4" />
      </video>
    </div>

    <Tabs>
      <TabItem value="bash" label="Bash/Zsh" default>
        <CodeBlock language="bash">
          {`mvn -B archetype:generate \\
-DarchetypeGroupId=com.webforj \\
-DarchetypeArtifactId=webforj-archetype-${project} \\
-DarchetypeVersion=LATEST \\
-DgroupId=org.example \\
-DartifactId=my-app \\
-Dversion=1.0-SNAPSHOT ${project !== 'bbj-hello-world' ? `\\
-Dflavor=${flavor}` : ''} \\
-DappName=MyApp`}
        </CodeBlock>
      </TabItem>
      <TabItem value="powershell" label="PowerShell">
        <CodeBlock language="powershell">
          {`mvn -B archetype:generate \`
-DarchetypeGroupId="com.webforj" \`
-DarchetypeArtifactId="webforj-archetype-${project}" \`
-DarchetypeVersion="LATEST" \`
-DgroupId="org.example" \`
-DartifactId="my-app" \`
-Dversion="1.0-SNAPSHOT" ${project !== 'bbj-hello-world' ? `\`
-Dflavor="${flavor}` : ''}" \`
-DappName="MyApp"`}
        </CodeBlock>
      </TabItem>
      <TabItem value="cmd" label="Command Prompt">
        <CodeBlock>
          {`mvn -B archetype:generate ^
-DarchetypeGroupId="com.webforj" ^
-DarchetypeArtifactId="webforj-archetype-${project}" ^
-DarchetypeVersion="LATEST" ^
-DgroupId="org.example" ^
-DartifactId="my-app" ^
-Dversion="1.0-SNAPSHOT" ${project !== 'bbj-hello-world' ? `^
-Dflavor="${flavor}` : ''}" ^
-DappName="MyApp"`}
        </CodeBlock>
      </TabItem>
    </Tabs>
    <table>
      <thead>
        <th>{translate({
          id: 'component.archetype.table.argument',
          message: 'Argument',
          description: 'Argument column header'
        })}</th>
        <th>{translate({
          id: 'component.archetype.table.explanation',
          message: 'Explanation',
          description: 'Explanation column header'
        })}</th>
      </thead>
      <tbody>
        <tr>
          <td><code>archetypeGroupId</code></td>
          <td>{translate({
            id: 'component.archetype.table.archetypeGroupId.desc',
            message: 'The group ID of the archetype is com.webforj for webforJ archetypes.',
            description: 'archetypeGroupId description'
          })}</td>
        </tr>
        <tr>
          <td><code>archetypeArtifactId</code></td>
          <td>{translate({
            id: 'component.archetype.table.archetypeArtifactId.desc',
            message: 'Specifies the name of the archetype to use.',
            description: 'archetypeArtifactId description'
          })}</td>
        </tr>
        <tr>
          <td><code>archetypeVersion</code></td>
          <td>{translate({
            id: 'component.archetype.table.archetypeVersion.desc',
            message: 'Specifies the version of the archetype to use. This ensures that the generated project is compatible with a specific archetype version. Using LATEST selects the most recent version available.',
            description: 'archetypeVersion description'
          })}</td>
        </tr>
        <tr>
          <td><code>groupId</code></td>
          <td>{translate({
            id: 'component.archetype.table.groupId.desc',
            message: 'Represents the namespace for the generated project. Typically structured like a Java package, such as org.example and is used to uniquely identify your organization or project domain.',
            description: 'groupId description'
          })}</td>
        </tr>
        <tr>
          <td><code>artifactId</code></td>
          <td>{translate({
            id: 'component.archetype.table.artifactId.desc',
            message: 'Specifies the name of the generated project. This will be the name of the resulting artifact and the project folder.',
            description: 'artifactId description'
          })}</td>
        </tr>
        <tr>
          <td><code>version</code></td>
          <td>{translate({
            id: 'component.archetype.table.version.desc',
            message: 'Defines the version of the generated project. A common convention is MAJOR.MINOR-SNAPSHOT, like 1.0-SNAPSHOT, where SNAPSHOT denotes that the project is still in development.',
            description: 'version description'
          })}</td>
        </tr>
        {project !== 'bbj-hello-world' && (
        <tr>
          <td><code>flavor</code></td>
          <td>
            {translate({
              id: 'component.archetype.table.flavor.desc',
              message: 'Selects a project flavor:',
              description: 'flavor description intro'
            })}
            <ul>
            <li><code>webforj</code> - {translate({
              id: 'component.archetype.table.flavor.webforj',
              message: 'Standard webforJ app.',
              description: 'webforj flavor description'
            })}</li>
            <li><code>webforj-spring</code> - {translate({
              id: 'component.archetype.table.flavor.spring',
              message: 'webforJ app with Spring Boot support (requires webforJ 25.02 or higher).',
              description: 'webforj-spring flavor description'
            })}</li>
            </ul>
          </td>
        </tr>
        )}
        <tr>
          <td><code>appName</code></td>
          <td>{translate({
            id: 'component.archetype.table.appName.desc',
            message: 'An optional parameter that can be used in the generated project\'s POM file. Depending on the used webforJ archetype, it can be utilized as a default title for the application.',
            description: 'appName description'
          })}</td>
        </tr>
      </tbody>
    </table>
    <p>{translate({
      id: 'component.archetype.conclusion',
      message: 'After running the command, Maven will generate the project files necessary to run the project.',
      description: 'Conclusion text'
    })}
    </p>
  </>);
}
