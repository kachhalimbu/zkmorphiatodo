/**
 * 
 */
package org.zkoss.mongodb.dao;

import org.bson.types.ObjectId;
import org.zkoss.mongodb.model.Task;

import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.Mongo;

/**
 * @author Ashish
 *
 */
public class TaskDAO extends BasicDAO<Task, ObjectId> {

	public TaskDAO(Mongo mongo, Morphia morphia) {
		super(mongo, morphia, "todo");
		// TODO Auto-generated constructor stub
	}
}
