function parseSnippet() {
  const fileContent = 
  `// Some code...
  // snippet: 4-6, 10
      public class Example {
        public static void main(String[] args) {
          System.out.println("Line 5");
          System.out.println("Line 6");
          System.out.println("Line 7");
          System.out.println("Line 8");
              System.out.println("Line 9");
              System.out.println("Line 10");
              System.out.println("Line 11");
              System.out.println("Line 12");
              System.out.println("Line 13");
              System.out.println("Line 14");
              System.out.println("Line 15");
              System.out.println("Line 16");
          }
      }
  `;

  // Split file content into lines
  const lines = fileContent.split("\n");

  // Find the line containing "snippet:"
  let snippetLineIndex = lines.findIndex((line) => line.includes("snippet:"));
  if (snippetLineIndex === -1) {
    console.log("No snippet line found.");
    // Handle case where snippet isn't found
    return false;
  } else {
    // Extract the substring after "snippet:"
    // Example line: "    // snippet: 3-10, 45-78"
    const snippetLine = lines[snippetLineIndex];
    const snippetPrefix = "snippet:";

    // Find the snippet ranges substring
    const snippetStart = snippetLine.indexOf(snippetPrefix);
    if (snippetStart === -1) {
      console.log("Snippet prefix not found in the line.");
      return false;
    } else {
      // Extract just the portion after 'snippet:'
      const rangesStr = snippetLine
        .substring(snippetStart + snippetPrefix.length)
        .trim();
      // rangesStr might look like: "3-10, 45-78"

      // Split by commas to get each range
      const rangeParts = rangesStr.split(",").map((part) => part.trim());

      // Parse each range into start and end integers
      const ranges = rangeParts.map((rangeStr) => {
        const [startStr, endStr] = rangeStr.split("-").map((s) => s.trim());
        const start = parseInt(startStr, 10);
        const end = endStr ? parseInt(endStr, 10) : start; // Use start as end if endStr is undefined
        return { start, end };
      });

      var test = ""
      ranges.forEach(({ start, end }, index) => {
        // Extract lines from start to end (inclusive).
        // Adjusting for zero-based index: lines[start-1] is the first requested line.
        const snippetLines = lines.slice(start - 1, end); // end index is non-inclusive, so no need to do end-1
        const snippetText = snippetLines.join("\n");
        test += snippetText
        if(index !== ranges.length-1){
          test += ("\n");
        }
      });
    }
  }
  return test
}

console.log(parseSnippet());