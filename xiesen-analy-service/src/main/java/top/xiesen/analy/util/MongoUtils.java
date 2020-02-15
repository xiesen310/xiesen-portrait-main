package top.xiesen.analy.util;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * @Description mongodb 工具类
 * @className top.xiesen.analy.util.MongoUtils
 * @Author 谢森
 * @Email xiesen310@163.com
 * @Date 2020/2/5 11:25
 */
public class MongoUtils {
    private static MongoClient mongoClient = new MongoClient("192.168.0.103", 27017);

    /**
     * 查询
     *
     * @param tableName    表名
     * @param database     数据库
     * @param yearBaseType 年代标签
     * @return
     */
    public static Document findOneBy(String tableName, String database, String yearBaseType) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
        MongoCollection mongoCollection = mongoDatabase.getCollection(tableName);
        Document doc = new Document();
        doc.put("info", yearBaseType);
        FindIterable<Document> itrer = mongoCollection.find(doc);
        MongoCursor<Document> mongocursor = itrer.iterator();
        if (mongocursor.hasNext()) {
            return mongocursor.next();
        } else {
            return null;
        }
    }


    /**
     * mongo 保存更新数据
     *
     * @param tableName 表名
     * @param database  数据库
     * @param doc       Document
     */
    public static void saveOrUpdateMongo(String tableName, String database, Document doc) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(tableName);
        if (!doc.containsKey("_id")) {
            ObjectId objectid = new ObjectId();
            doc.put("_id", objectid);
            mongoCollection.insertOne(doc);
            return;
        }
        Document matchDocument = new Document();
        String objectId = doc.get("_id").toString();
        matchDocument.put("_id", new ObjectId(objectId));
        FindIterable<Document> findIterable = mongoCollection.find(matchDocument);
        if (findIterable.iterator().hasNext()) {
            mongoCollection.updateOne(matchDocument, new Document("$set", doc));
            try {
                System.out.println("come into saveOrUpdateMongo ---- update---" + JSONObject.toJSONString(doc));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mongoCollection.insertOne(doc);
            try {
                System.out.println("come into saveOrUpdateMongo ---- insert---" + JSONObject.toJSONString(doc));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
