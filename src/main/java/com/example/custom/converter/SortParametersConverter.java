package com.example.custom.converter;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class SortParametersConverter {

    public Map<String, Boolean> buildInterestSortParameters(Optional<Boolean> isId, Optional<Boolean> isIdDesc,
                                                             Optional<Boolean> isName, Optional<Boolean> isNameDesc) {
        HashMap<String, Boolean> parameters = new HashMap<>();
        if (isId.orElse(false)) {
            parameters.put("id", isIdDesc.orElse(false));
        }
        if (isName.orElse(false)) {
            parameters.put("name", isNameDesc.orElse(false));
        }
        return parameters;
    }
}
