# The Isagen Tool 
[![Build Status](https://build.overture.au.dk/jenkins/buildStatus/icon?job=overture-development)](https://build.overture.au.dk/jenkins/job/overture-development/)
[![License](http://img.shields.io/:license-gpl3-blue.svg?style=flat-square)](http://www.gnu.org/licenses/gpl-3.0.html)
[![Maven Central](https://img.shields.io/maven-central/v/org.overturetool/core.svg?label=Maven%20Central)](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.overturetool.core%22)



## General Information

A tool for automated translation of a VDM-SL specification into Isabelle/HOL.

## Set-up

### IntelliJ set-up
Isagen is built using IntelliJ Idea and this wil be the easiest IDE to get started - installing IntelliJ is highly recommended.

1. Clone this repository.
2. Checkout js/isagen.
3. Either:
    - Open IntelliJ Idea and go to File > New > Project from Existing Sources...  
    ***OR***
    - From the welcome screen select Open or Import > Navigate to the 'overture' directory you have cloned > Select open.
4. Right click the root folder 'overture' in the project window and click 'Add Framework Support...'. Check 'Maven' and click ok.
5. In the IntelliJ IDEA menu:
    - Go to Preferences > Build, Execution, Deployment > Build Tools > Maven and check that 'Bundled (Maven 3)' is in the 'Maven home directory field'.
    - Go to Preferences > Build, Execution, Deployment > Build Tools > Maven > Runner and check that a JRE is configured.
6. In the File menu go to Project Structure > Project and configure the project SDK to point toward your Java 1.8 installation.
7. Restart IntelliJ.
8. On the far-right of the IntelliJ window the 'Maven' menu allows you to run maven commands for your current project. Click the 'M' (Execute maven goal) button and run ***'mvn clean install -DskipTests'.*** This will install the Overture project with its dependencies.

IntelliJ will set up the project.

### Eclipse set-up
If you are using eclipse then see the [development wiki](https://github.com/overturetool/overture/wiki) for how to download and configure the overture project in eclipse.

## Development

- The main entry point for Isabelle code generation is:
    core/codegen/isagen/src/main/java/org/overturetool/cgisa/IsaCodeGenMain.java  
    
- To run Isagen from inside the IntelliJ go to Run > Edit Configurations select IsaCodeGenMain.  
By default, the run configuration is set to use the ***-folder*** option. More information on Isagen program arguments is in the [using Isagen](#using-isagen) section. You must replace the ***\[Folder to be translated here]*** with a path to a folder containing VDM-SL specifications you wish to be translated.  

- ***core/codegen/isagen/src/main/java/org/overturetool/cgisa*** contains the Java classes behind Isabelle code generation. These classes manipulate, or help to manipulate, the Intermediate Representation (IR) AST so that they can be translated by the template engine.

- ***core/codegen/isagen/src/main/resources/IsaTemplates/org/overture*** contains velocity templates which map manipulated IR AST nodes (and their properties) to Isabelle/HOL syntax.

- Upon building overture (or simply building Isagen in isolation), the directory ***'target'*** will be generated and contain the Isagen .jar with dependencies. This can be used as a typical .jar file to run Isagen from the CLI.

## Using Isagen
### Program arguments

- ***exp*** Translates the proceeding VDM-SL expression into Isabelle/HOL.
- ***folder*** Translates the proceeding folder path (containing VDM-SL specifications) into an Isabelle/HOL .thy file.
- ***print*** Translated code is printed to console rather than file.
- ***output*** Specify the directory to which the translated .thy file will be saved. By default this is ***'.'*** .
- ***genir*** Dave the manipulated IR nodes as text files to ***./generatedIRtext/***.
- ***nowarnings*** Disable translation warnings.
- ***vdmascomment*** Prefix original VDM-SL as an Isabelle comment before its translation.

