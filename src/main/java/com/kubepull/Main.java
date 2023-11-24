package com.kubepull;

import com.kubepull.library.KubeFormatter;
import com.kubepull.service.KubeServiceInterface;
import com.kubepull.service.implement.KubeServiceRuntime;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import picocli.CommandLine;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class Main implements Runnable {
    private KubeServiceInterface kubeService;
    private KubeFormatter formatter;

    public Main() {
        kubeService = new KubeServiceRuntime();
        formatter = new KubeFormatter();
    }

    /**
     * kubectl context
     */
    @CommandLine.Option(names = {"-c", "--context"}, required = true)
    private String context;

    /**
     * kubectl namespace
     */
    @CommandLine.Option(names = {"-n", "--namespace"})
    private String namespace;

    /**
     * from file
     */
    @CommandLine.Option(names = {"-f"})
    private String[] listFile;

    /**
     * scan all yaml file from folder
     */
    @CommandLine.Option(names = {"-af"})
    private boolean isAllFile;

    /**
     * kind. if All will get all kind
     */
    @CommandLine.Parameters(index = "0", defaultValue= "")
    private String kind;

    /**
     * metadata name
     */
    @CommandLine.Parameters(index = "1", defaultValue= "")
    private String name;

    public static void main(String[] args) {
        CommandLine.run(new Main(), args);
    }

    @Override
    public void run() {

        try {
            if (listFile != null) {
                for (String file: listFile) {
                    updateFile(file, false);
                }
                return;
            }

            if (isAllFile) {
                updateBySpecificFolder();
                return;
            }

            if (namespace.isEmpty()) {
                System.out.println("Namespace is empty");
                return;
            }

            if (kind.isEmpty()) {
                System.out.println("Kind is empty");
                return;
            }

            if (name.isEmpty()) {
                System.out.println("Specific Name is empty");
                return;
            }

            Map<String, Object> data = kubeService.get(context, namespace, kind, name);
            if (data != null) {
                newFile(data);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void updateFile(String rawFilename, boolean withPath) throws Exception {
        String filename = rawFilename.replace(".\\\\", "");

        String path = System.getProperty("user.dir")+"/"+filename;
        if (withPath) {
            path = filename;
        }
        path = path.replaceAll("\\\\", "/");

        InputStream inputStream = new FileInputStream(path);
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        Map<String, Object> file = yaml.load(inputStream);

        String readKind = (String) file.get("kind");
        Map<String, Object> metadata = (Map<String, Object>) file.get("metadata");
        String readName = (String) metadata.get("name");
        String readNameSpace = (String) metadata.get("namespace");

        Map<String, Object> data = kubeService.get(context, readNameSpace, readKind.toLowerCase(), readName);
        Map<String, Object> result = formatter.format(readKind.toLowerCase(), data);

        updateToFile(path, yaml, result);

        System.out.println(path + " updated");
    }

    private void updateBySpecificFolder() throws Exception {
        String path = System.getProperty("user.dir");

        String[] extensions = {"yaml", "yml"};
        List<String> files = findFiles(Paths.get(path), extensions);
        if (files.isEmpty()) {
            System.out.println("No yaml file found");
            return;
        }

        for (String file: files) {
            updateFile(file, true);
        }
    }

    private void updateToFile(String path, Yaml yaml, Map<String, Object> data) throws Exception {
        File myFoo = new File(path);
        FileWriter fooWriter = new FileWriter(myFoo, false); // true to append
        // false to overwrite.
        String output = yaml.dump(data);
        fooWriter.write(output);
        fooWriter.close();
    }

    public static List<String> findFiles(Path path, String[] fileExtensions) throws IOException {

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be a directory!");
        }

        List<String> result;
        try (Stream<Path> walk = Files.walk(path, 1)) {
            result = walk
                    .filter(p -> !Files.isDirectory(p))
                    // convert path to string
                    .map(p -> p.toString())
                    .filter(f -> isEndWith(f, fileExtensions))
                    .collect(Collectors.toList());
        }
        return result;

    }

    private static boolean isEndWith(String file, String[] fileExtensions) {
        boolean result = false;
        for (String fileExtension : fileExtensions) {
            if (file.endsWith(fileExtension)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private void newFile(Map<String, Object> data) throws Exception {

        String readKind = (String) data.get("kind");
        Map<String, Object> metadata = (Map<String, Object>) data.get("metadata");
        String readName = (String) metadata.get("name");
        String newNameFile = readKind + "-" + readName + ".yaml";

        String path = System.getProperty("user.dir")+"/"+newNameFile;

        File myFile = new File(path);
        myFile.createNewFile();

        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);


        Map<String, Object> result = formatter.format(readKind.toLowerCase(), data);
        updateToFile(path, yaml, result);

        System.out.println(path + " Created");
    }
}