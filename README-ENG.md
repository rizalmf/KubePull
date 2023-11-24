# KubePull
Pull data from Kubernetes Cluster existing and write to your local machine

## Note
1. Please make sure command `kubectl` already installed in your local machine
2. JRE 11 already installed in your local machine

## Usage

### Pull target File
This app will read *file.yaml* on key ***kind, metadata.name, metadata.namespace*** and write existing file based on those specification
```shell
java -jar KubePull.jar -c <context-name> -f <yaml-file>
```

### In Case Multi Target FIle
```shell
java -jar KubePull.jar -c <context-name> -f <yaml-file1> -f <yaml-file2> -f <yaml-file3>
```

### Pull All Yaml File from Folder
```shell
java -jar KubePull.jar -c <context-name> -af
```

### Pull And Create New File
This Command will create new file in your local machine
```shell
# will create 'kind-metadata-name.yaml'
java -jar KubePull.jar -c <context-name> -n <namespace> <kind> <metadata-name>
```

## Suggestion
Make alias on specific kubernetes-context to help you boost your syntax
```shell
path-yaml-file> alias1 -f myFile.yml
```
### Windows (PowerShell)
```shell
# Microsoft.PowerShell_profile.ps1
Function functionContext1 { return java -jar "<path-to-file/KubePull.jar" -c <context> $args }
Set-Alias -Name alias1 -Value functionContext1
```
### Linux (Debian/Ubuntu)
```shell
# .bashrc
alias alias1='java -jar "<path-to-file/KubePull.jar" -c <context>'
```