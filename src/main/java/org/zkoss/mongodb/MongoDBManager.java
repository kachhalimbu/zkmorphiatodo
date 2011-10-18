
package org.zkoss.mongodb;

import com.google.code.morphia.Morphia;
import com.mongodb.DB;
import com.mongodb.Mongo;

/**
 * @author Ashish
 *
 */
public class MongoDBManager {

	private static Mongo mongo = null;
	private static Morphia morphia = null;
	private MongoDBManager() {};
	
	public static synchronized DB getDB() throws Exception {
		if(mongo == null) {
			mongo = new Mongo();
		} 
		return mongo.getDB("test");
	}
	public static synchronized Mongo getMongo() throws Exception {
		if(mongo == null) {
			mongo = new Mongo();
		}
		return mongo;
	}
	public static synchronized Morphia getMorphia() throws Exception {
		if(morphia == null) {
			mongo = getMongo();
			morphia = new Morphia();
			morphia.mapPackage("org.zkoss.mongodb.model");
		}
		
		return morphia;
	}
}
