import java.util.LinkedHashMap;
import java.util.Map;

public class create_ausbildung_file {

    static Map<String,String> ausbildung_map = new LinkedHashMap<>();

    public static void main(String[] args) {

        //Apply.add_collection();

        System.out.println(ausbildung_map.size());

        for ( Map.Entry<String,String> entry : ausbildung_map.entrySet() )
        {
            System.out.println(entry.getKey() +""+ entry.getValue());
        }

        System.out.println( Apply.Ausbildung_list.size());

    }
}
