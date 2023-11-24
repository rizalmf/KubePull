package com.kubepull.service;

import java.util.List;
import java.util.Map;

public interface KubeServiceInterface {

    /**
     *
     * @param ctx contextname
     * @param namespace
     * @param kind
     * @param name
     * @return
     * @throws Exception
     */
    public Map<String, Object> get(String ctx, String namespace, String kind, String name) throws Exception;
}
