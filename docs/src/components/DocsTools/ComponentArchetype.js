/** @jsxImportSource @emotion/react */

import Tabs from "@theme/Tabs";
import TabItem from "@theme/TabItem";
import CodeBlock from "@theme/CodeBlock";

export default function ComponentArchetype({project}){
  return(<>
  <p>To create and scaffold a new <code>{project}</code> project, follow these steps:
  </p>
  <ol>
    <li><strong>Navigate to the proper directory</strong>: Open a terminal and move to the folder where you want to create your new project.
    </li>
    <li><strong>Run the <code>archetype:generate</code> command</strong>: Use the Maven command below, and customize the <code>groupId</code>, <code>artifactId</code>, and <code>version</code> as needed for your project.
    </li>
  </ol>
  <Tabs>
    <TabItem value="bash" label="Bash/Zsh" default>
      <CodeBlock language="bash">
{`mvn -B archetype:generate \\
-DarchetypeGroupId=com.webforj \\
-DarchetypeArtifactId=webforj-archetype-${project} \\
-DarchetypeVersion=LATEST \\
-DgroupId=org.example \\
-DartifactId=my-app \\
-Dversion=1.0-SNAPSHOT \\
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
-Dversion="1.0-SNAPSHOT" \`
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
-Dversion="1.0-SNAPSHOT" ^
-DappName="MyApp"`}
      </CodeBlock>
    </TabItem>
  </Tabs>
  <table>
    <thead>
      <th>Argument</th>
      <th>Explanation</th>
    </thead>
    <tbody>
      <tr>
        <td><code>archetypeGroupId</code></td>
        <td>The group ID of the archetype is <code>com.webforj</code> for webforJ archetypes.</td>
      </tr>
      <tr>
        <td><code>archetypeArtifactId</code></td>
        <td>Specifies the name of the archetype to use.</td>
      </tr>
      <tr>
        <td><code>archetypeVersion</code></td>
        <td>Specifies the version of the archetype to use. This ensures that the generated project is compatible with a specific archetype version. Using LATEST selects the most recent version available.</td>
      </tr>
      <tr>
        <td><code>groupId</code></td>
        <td>Represents the namespace for the generated project. Typically structured like a Java package, such as <code>org.example</code> and is used to uniquely identify your organization or project domain.</td>
      </tr>
      <tr>
        <td><code>artifactId</code></td>
        <td>Specifies the name of the generated project. This will be the name of the resulting artifact and the project folder.</td>
      </tr>
      <tr>
        <td><code>version</code></td>
        <td>Defines the version of the generated project. A common convention is MAJOR.MINOR-SNAPSHOT, like <code>1.0-SNAPSHOT</code>, where SNAPSHOT denotes that the project is still in development.</td>
      </tr>
      <tr>
        <td><code>appName</code></td>
        <td>An optional parameter that can be used in the generated project's POM file. Depending on the used webforJ archetype, it can be utilized as a default title for the application. </td>
      </tr>
    </tbody>
  </table>
<p>After running the command, Maven will generate the project files necessary to run the project.
</p>
</>);
}