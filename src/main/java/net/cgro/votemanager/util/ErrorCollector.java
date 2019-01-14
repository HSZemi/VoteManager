package net.cgro.votemanager.util;

import java.util.ArrayList;
import java.util.List;

public class ErrorCollector {
    private List<String> errors = new ArrayList<>();

    public ErrorCollector(){
    }

    public void throwIfHasErrors() throws WahlMergeException {
        if (!errors.isEmpty()) {
            throw new WahlMergeException(String.join("\n", errors));
        }
    }

    public void addError(String string){
        errors.add(string);
    }
}
