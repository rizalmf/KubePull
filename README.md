> If you prefer in english. please click [here](README-ENG.md)
# KubePull
Pull data dari Kubernetes Cluster existing dan write ke file local.

## Note
1. Pastikan command `kubectl` sudah terinstall pada local OS kalian
2. Membutuhkan JRE 11 keatas

## Penggunaan

### Pull target File
Aplikasi akan membaca yaml file local pada key ***kind, metadata.name, metadata.namespace*** dan update file local berdasarkan spesifikasi tersebut 
```shell
java -jar KubePull.jar -c <context-name> -f <yaml-file>
```

### Jika Pull Data Multi FIle
```shell
java -jar KubePull.jar -c <context-name> -f <yaml-file1> -f <yaml-file2> -f <yaml-file3>
```

### Pull Dalam Satu Folder
```shell
java -jar KubePull.jar -c <context-name> -af
```

### Pull Dan Buat File Baru
Perintah ini akan membuat file baru pada local
```shell
# akan menghasilkan file 'kind-metadata-name.yaml'
java -jar KubePull.jar -c <context-name> -n <namespace> <kind> <metadata-name>
```

## Saran
Buat alias untuk memudahkan saat menulis syntax
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