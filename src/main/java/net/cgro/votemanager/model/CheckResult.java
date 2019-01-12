/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.cgro.votemanager.model;

/**
 * @author fabian
 */

public class CheckResult {

    private String info;
    private ResultType type;

    public CheckResult(String info, ResultType type) {
        this.info = info;
        this.type = type;
    }

    @Override
    public String toString() {
        switch (type) {
            case RESULT_TYPE_OK:
                return "OK: " + info;
            case RESULT_TYPE_WARNING:
                return "WARNUNG: " + info;
            case RESULT_TYPE_ERROR:
                return "FEHLER: " + info;
        }
        return info;
    }

    public String getInfo() {
        return this.info;
    }

    public ResultType getType() {
        return this.type;
    }

}
