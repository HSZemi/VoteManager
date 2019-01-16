package net.cgro.votemanager.util;

import spark.Filter;
import spark.Spark;

import java.util.HashMap;

public final class CorsFilter {

    private static final HashMap<String, String> corsHeaders = new HashMap<>();

    static {
        corsHeaders.put("Access-Control-Allow-Methods", "GET");
        corsHeaders.put("Access-Control-Allow-Origin", "*");
    }

    public static void apply() {
        Filter filter = (request, response) -> corsHeaders.forEach(response::header);
        Spark.after(filter);
    }
}
