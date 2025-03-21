import os
import re

# This is a script to generate a list of URLs from Java files in a directory.
# The script extracts class names from Java files and converts them to URLs.
# In order to run this script you need to have Python installed on your machine. 
# After that simply run the script using the command `python url-mapper.py`.

# Define the root directory for scanning
# Replace this with the path to your WebForJ documentation project
views_dir = r"path-to-your-webforj-documentation\src\main\java\com\webforj\samples\views"

# Markdown file output
output_file = "generated_urls.md"

# Base URLs
base_urls = [
    "https://docs.webforj.com/webforj/",
    "http://localhost:8080/webforj/"
]

def convert_to_url(class_name):
    """Convert a class name to a lowercase URL without symbols."""
    base_name = class_name.replace("View", "")  # Remove 'View' suffix
    return base_name.lower()  # Convert to lowercase without hyphens or symbols

def extract_class_names(file_path):
    """Extract the class name from a Java file."""
    with open(file_path, "r", encoding="utf-8") as file:
        for line in file:
            match = re.search(r"public\s+class\s+(\w+)View", line)
            if match:
                return match.group(1)  # Return only the base class name
    return None

def generate_markdown():
    """Generate a Markdown list of full URLs from the views folder."""
    urls = []
    
    for root, _, files in os.walk(views_dir):
        for file in files:
            if file.endswith(".java"):
                file_path = os.path.join(root, file)
                class_name = extract_class_names(file_path)
                
                if class_name:
                    urls.append(convert_to_url(class_name))

    # Write the URLs to a Markdown file
    with open(output_file, "w", encoding="utf-8") as md_file:
        md_file.write("# Generated URLs\n\n")
        for url in sorted(urls):
            for base_url in base_urls:
                md_file.write(f"- `{base_url}{url}`\n")
            md_file.write("\n")  # Extra spacing between groups

    print(f"✅ Markdown file generated: {output_file}")

# Run the script
generate_markdown()
