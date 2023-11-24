package com.kubepull.service.implement;

import com.kubepull.service.KubeServiceInterface;
import org.yaml.snakeyaml.Yaml;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class KubeServiceRuntime implements KubeServiceInterface {

    public static Runtime rt;
    public KubeServiceRuntime() {
        if (rt == null) {
            rt = Runtime.getRuntime();
        }
    }

    public Map<String, Object> get(String ctx, String namespace, String kind, String name) throws Exception {
        String command = String.format("kubectl --context %s get %s -n %s %s -o yaml",
                ctx, kind, namespace, name);

        return exec(command);
    }

    /**
     *
     * @param command kubectl commandline
     * @return yaml map
     * @throws Exception throw to controller
     */
    private Map<String, Object> exec(String command) throws Exception {

        Process pr = rt.exec(command);
        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line;
        String ymlResponse = "";
        while ((line = in.readLine()) != null) {
            ymlResponse += (line+"\n"); // line break
        }
        pr.waitFor();
        in.close();

        Yaml yaml = new Yaml();

        return yaml.load(ymlResponse);
    }
}
