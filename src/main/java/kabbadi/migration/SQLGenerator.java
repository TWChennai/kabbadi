package kabbadi.migration;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SQLGenerator {

    private Collection<Map<String, String>> entries;

    public SQLGenerator(Collection<Map<String, String>> entries) {
        this.entries = entries;
    }


    public List<String> createInsertStatements() {
        List<String> insertQueries = new ArrayList<String>();
        for (Map<String, String> entry : entries) {
            String[] keys = new String[entry.size()];
            entry.keySet().toArray(keys);
            String[] values = new String[keys.length];
            for (int index = 0; index < keys.length; index++)
                values[index] = entry.get(keys[index]);

            insertQueries.add(createSingleInsertStatement(insertQueries.size(), keys, values));
        }
        return insertQueries;
    }

    private String createSingleInsertStatement(Integer invoiceId, String[] columns, String[] values) {
        for (int i = 0; i < values.length; i++)
            if (!values[i].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                if (values[i].isEmpty())
                    values[i] = "null";
                else
                    values[i] = "'" + values[i] + "'";
            }
        return String.format("INSERT INTO invoice (invoice_id, %s) VALUES (%d, %s);",

                StringUtils.join(columns, ", "),
                invoiceId,
                StringUtils.join(values, ", "));
    }
}
