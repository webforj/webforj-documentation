---
title: Project Setup
sidebar_position: 1
---

In this tutorial, the app will be structured into **four steps**, each introducing new features as the project progresses. By following along, you’ll gain a clear understanding of how the app evolves and how each feature is implemented.

To get started, you can download the entire project or clone it from GitHub:
<!-- vale off -->
- Download ZIP: [webforj-demo-application.zip](https://github.com/webforj/webforj-demo-application/archive/refs/heads/main.zip)
- GitHub Repository: Clone the project [directly from GitHub](https://github.com/webforj/webforj-demo-application)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-demo-application.git
```

Both the ZIP file and GitHub repository contain the complete project structure with all four steps, so you can start at any point or follow along step by step.

## Project structure

The project is broken into four discrete directories, each representing a specific stage of the app’s development. These steps allow you to see how the app evolves from a basic setup to a fully functional customer management system.

Inside the project folder, you’ll find four subdirectories, each corresponding to a step in the tutorial:

```
webforj-demo-application
│   .gitignore
│   LICENSE
│   README.md
│   tree.txt
│
├───1-creating-a-basic-app  
├───2-working-with-data
├───3-scaling-with-routing-and-composites
└───4-validating-and-binding-data
```

### Running the app

To see the app in action at any stage:

1) Navigate to the directory for the desired step. This should be the top level directory for that step, containing the `pom.xml`

2) Use the Maven Jetty plugin to deploy the app locally by running:

```bash
mvn jetty:run
```

3) Open your browser and navigate to http://localhost:8080 to view the app.

Repeat this process for each step as you follow along with the tutorial, allowing you to explore the app’s features as they're added.