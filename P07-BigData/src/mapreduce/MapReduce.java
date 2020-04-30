/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapreduce;

import com.mongodb.operation.MapReduceWithInlineResultsOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.swing.UIManager;
import org.bson.Document;

/**
 *
 * @author Lenovo
 */
public class MapReduce {
    
    public static ArrayList<Document> map(ArrayList<Document> friendList){
        ArrayList<Document> mapResult = new ArrayList<>();
        friendList.stream().map((personFriend) -> {
            ArrayList<Document> mapFriend = new ArrayList<>();
            String name = personFriend.getString("name");
            ArrayList friends = (ArrayList)personFriend.get("friends");
            for (int i=0; i<friends.size(); i++) {
                ArrayList key = new ArrayList();
                key.add(name);
                key.add(friends.get(i));
                Document kv = new Document();
                kv.append("key", key);
                kv.append("value", friends);
                Collections.sort(key);
                mapFriend.add(kv);
            }
             return mapFriend;
            }).forEachOrdered((mapFriend) ->{
                mapResult.addAll(mapFriend);
            });
        return mapResult;
    }
    
    public static ArrayList<String> interselection(ArrayList<String> listl,
            ArrayList<String> list2){
        ArrayList<String>result = new ArrayList<>(listl);
        result.retainAll(list2);
        
        return result;
    }
    
    public static Map<ArrayList, ArrayList> reduce(Map<ArrayList,
            ArrayList<ArrayList>> groupResult){
        Map<ArrayList, ArrayList> reduceResult = new HashMap<>();
        
        groupResult.entrySet().forEach((kv) ->{
            ArrayList key =(ArrayList) kv.getKey();
            ArrayList<ArrayList> value =(ArrayList) kv.getValue();
            
            ArrayList<String> result = value.get(0);
            for(int i=1; i<value.size(); i++){
             result = interselection(value.get(i), result);
            }
            reduceResult.put(key, result);
        });
        return reduceResult;
    }

    static Map<ArrayList, ArrayList<ArrayList>> group(ArrayList<Document> mapResult) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
