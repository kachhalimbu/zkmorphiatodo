/**
 * 
 */
package org.zkoss.mongodb.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.mongodb.MongoDBManager;
import org.zkoss.mongodb.dao.TaskDAO;
import org.zkoss.mongodb.model.Task;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

/**
 * @author Ashish
 *
 */
public class MongoDBMorphiaDemoCtrl extends SelectorComposer {

	@Wire
	Listbox tasks;
	@Wire
	Textbox name;
	@Wire
	Intbox priority;
	@Wire
	Datebox date;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		TaskDAO taskDao = new TaskDAO(MongoDBManager.getMongo(), MongoDBManager.getMorphia());
		List<Task> taskList = taskDao.find().asList();
		tasks.setItemRenderer(new ListitemRenderer() {
			
			public void render(Listitem item, Object data, int arg2)
					throws Exception {
				// TODO Auto-generated method stub
				Task task = (Task) data;
				item.setValue(task);
				new Listcell(task.getName()).setParent(item); 
				new Listcell("" + task.getPriority()).setParent(item); 
				new Listcell(new SimpleDateFormat("yyyy-MM-dd").format(task.getExecutionDate())).setParent(item);
				
			}
		});
		tasks.setModel(new ListModelList(taskList));
	}
	
	@Listen("onSelect=listbox#tasks")
	public void selectTask(SelectEvent evt) {
		Task task = (Task) tasks.getSelectedItem().getValue();
		name.setValue(task.getName());
		priority.setValue(task.getPriority());
		date.setValue(task.getExecutionDate());
	}
	
	@Listen("onClick=button#add")
	public void addTask(Event evt) {
		Task newTask = new Task();
		newTask.setName(name.getValue());
		newTask.setPriority(priority.getValue());
		newTask.setExecutionDate(date.getValue());
		try {
			TaskDAO taskDao = new TaskDAO(MongoDBManager.getMongo(), MongoDBManager.getMorphia());
			taskDao.save(newTask);
			tasks.setModel(new ListModelList(taskDao.find().asList()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Listen("onClick=button#update")
	public void updateTask() {
		Task task = (Task) tasks.getSelectedItem().getValue();
		task.setName(name.getValue());
		task.setPriority(priority.getValue());
		task.setExecutionDate(date.getValue());
		try {
			TaskDAO taskDao = new TaskDAO(MongoDBManager.getMongo(), MongoDBManager.getMorphia());
			taskDao.save(task);
			tasks.setModel(new ListModelList(taskDao.find().asList()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=button#delete")
	public void deleteTask() {
		Task task = (Task) tasks.getSelectedItem().getValue();
		try {
			TaskDAO taskDao = new TaskDAO(MongoDBManager.getMongo(), MongoDBManager.getMorphia());
			taskDao.delete(task);
			tasks.setModel(new ListModelList(taskDao.find().asList()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
