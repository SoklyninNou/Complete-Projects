package demo;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap ngramMap;
    public HistoryTextHandler(NGramMap map) {
        ngramMap = map;
    }

    public String handle(NgordnetQuery query) {
        StringBuilder response = new StringBuilder();

        for (String word : query.words()) {
            response.append(word).append(": {");
            boolean firstEntry = true;

            for (int year = query.startYear(); year <= query.endYear(); year++) {
                Double weight = ngramMap.weightHistory(word).get(year);
                if (weight != null) {
                    if (!firstEntry) {
                        response.append(", ");
                    }
                    response.append(year).append("=").append(weight);
                    firstEntry = false;
                }
            }

            response.append("}\n");
        }

        return response.toString();
    }
}
