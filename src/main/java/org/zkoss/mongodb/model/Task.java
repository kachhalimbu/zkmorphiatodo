package org.zkoss.mongodb.model;

import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.utils.IndexDirection;

/**
 * 
 * @author ashish
 *
 */
@Entity("tasks")
public class Task {
	@Id
	private ObjectId id;
	
	@Indexed(value = IndexDirection.ASC, name = "taskName", unique = true)
	private String name;
	
	@Indexed(value = IndexDirection.ASC, name = "taskPriority")
	private int priority;
	
	@Indexed(value = IndexDirection.ASC, name = "executionDate")
	private Date executionDate;
	
	public Date getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}
	public Task(){}
	public Task(String name, int priority, Date date){
		this.name = name;
		this.priority = priority;
		this.executionDate = date;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
