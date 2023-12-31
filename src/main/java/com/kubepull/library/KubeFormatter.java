package com.kubepull.library;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class KubeFormatter {

    private final String KIND_DEPLOYMENT = "deployment";
    private final String KIND_D = "d";
    private final String KIND_CONFIGMAP = "configmap";
    private final String KIND_CM = "cm";
    private final String KIND_SECRET = "secret";
    private final String KIND_SERVICE = "service";
    private final String KIND_SVC= "svc";
    private final String KIND_INGRESS = "ingress";
    private final String KIND_HPA = "hpa";
    private final String KIND_HPA2 = "horizontalpodautoscaler";
    private final String KIND_CRONJOB = "cronjob";
    private final String KIND_PERSISTENT_VOLUME = "persistentvolumeclaim";
    private final String KIND_PVC = "pvc";

    public Map<String, Object> format(String kind, Map<String, Object> raw) {

        Map<String, Object> resultMap = null;
        if (kind.equals(KIND_DEPLOYMENT) || kind.equals(KIND_D)) {
            resultMap = formatDeployment(raw);
        } else if (kind.equals(KIND_CONFIGMAP) || kind.equals(KIND_CM)) {
            resultMap = formatCM(raw);
        } else if (kind.equals(KIND_SECRET)) {
            resultMap = formatSecret(raw);
        } else if (kind.equals(KIND_SERVICE) || kind.equals(KIND_SVC)) {
            resultMap = formatService(raw);
        } else if (kind.equals(KIND_INGRESS)) {
            resultMap = formatIngress(raw);
        } else if (kind.equals(KIND_HPA) || kind.equals(KIND_HPA2)) {
            resultMap = formatHpa(raw);
        } else if (kind.equals(KIND_CRONJOB)) {
            resultMap = formatCM(raw);
        } else if (kind.equals(KIND_PERSISTENT_VOLUME) || kind.equals(KIND_PVC)) {
            resultMap = formatCM(raw);
        }

        return resultMap;
    }

    private Map<String, Object> formatDeployment(Map<String, Object> raw) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> metadata = new HashMap<>();

        Map<String, Object> metadataOld = (Map<String, Object>) raw.get("metadata");
        metadata.put("name", metadataOld.get("name"));
        metadata.put("namespace", metadataOld.get("namespace"));
        if (metadataOld.get("labels") != null) {
            metadata.put("labels", metadataOld.get("labels"));
        }

        result.put("apiVersion", raw.get("apiVersion"));
        result.put("kind", raw.get("kind"));
        result.put("metadata", metadata);
        result.put("spec", raw.get("spec"));

        return result;
    }

    private Map<String, Object> formatCM(Map<String, Object> raw) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> metadata = new HashMap<>();

        Map<String, Object> metadataOld = (Map<String, Object>) raw.get("metadata");
        metadata.put("name", metadataOld.get("name"));
        metadata.put("namespace", metadataOld.get("namespace"));
        if (metadataOld.get("labels") != null) {
            metadata.put("labels", metadataOld.get("labels"));
        }

        result.put("apiVersion", raw.get("apiVersion"));
        result.put("kind", raw.get("kind"));
        result.put("metadata", metadata);
        result.put("data", raw.get("data"));

        return result;
    }

    private Map<String, Object> formatSecret(Map<String, Object> raw) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> metadata = new HashMap<>();

        Map<String, Object> metadataOld = (Map<String, Object>) raw.get("metadata");
        metadata.put("name", metadataOld.get("name"));
        metadata.put("namespace", metadataOld.get("namespace"));
        if (metadataOld.get("labels") != null) {
            metadata.put("labels", metadataOld.get("labels"));
        }

        result.put("apiVersion", raw.get("apiVersion"));
        result.put("kind", raw.get("kind"));
        result.put("metadata", metadata);
        result.put("data", raw.get("data"));
        result.put("type", raw.get("type"));

        return result;
    }

    private Map<String, Object> formatService(Map<String, Object> raw) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> metadata = new HashMap<>();

        Map<String, Object> metadataOld = (Map<String, Object>) raw.get("metadata");
        metadata.put("name", metadataOld.get("name"));
        metadata.put("namespace", metadataOld.get("namespace"));
        if (metadataOld.get("labels") != null) {
            metadata.put("labels", metadataOld.get("labels"));
        }
//        if (metadataOld.get("annotations") != null) {
//            metadata.put("annotations", metadataOld.get("annotations"));
//        }

        result.put("apiVersion", raw.get("apiVersion"));
        result.put("kind", raw.get("kind"));
        result.put("metadata", metadata);
        result.put("spec", raw.get("spec"));
        result.put("status", raw.get("status"));

        return result;
    }

    private Map<String, Object> formatIngress(Map<String, Object> raw) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> metadata = new HashMap<>();

        Map<String, Object> metadataOld = (Map<String, Object>) raw.get("metadata");
        metadata.put("name", metadataOld.get("name"));
        metadata.put("namespace", metadataOld.get("namespace"));
        if (metadataOld.get("annotations") != null) {
            Map<String, Object> annotationsOld = (Map<String, Object>) raw.get("annotations");
            if (annotationsOld.get("kubectl.kubernetes.io/last-applied-configuration") != null) {
                annotationsOld.remove("kubectl.kubernetes.io/last-applied-configuration");
            }

            metadata.put("annotations", annotationsOld);
        }
        if (metadataOld.get("labels") != null) {
            metadata.put("labels", metadataOld.get("labels"));
        }

        result.put("apiVersion", raw.get("apiVersion"));
        result.put("kind", raw.get("kind"));
        result.put("metadata", metadata);
        result.put("spec", raw.get("spec"));

        return result;
    }

    private Map<String, Object> formatHpa(Map<String, Object> raw) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        Map<String, Object> metadata = new HashMap<>();

        Map<String, Object> metadataOld = (Map<String, Object>) raw.get("metadata");
        metadata.put("name", metadataOld.get("name"));
        metadata.put("namespace", metadataOld.get("namespace"));
        if (metadataOld.get("annotations") != null) {
            metadata.put("annotations", metadataOld.get("annotations"));
        }
        if (metadataOld.get("labels") != null) {
            metadata.put("labels", metadataOld.get("labels"));
        }

        result.put("apiVersion", raw.get("apiVersion"));
        result.put("kind", raw.get("kind"));
        result.put("metadata", metadata);
        result.put("spec", raw.get("spec"));

        return result;
    }
}
