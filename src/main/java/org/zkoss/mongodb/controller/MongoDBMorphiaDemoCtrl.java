/**
 * 
 */
package org.zkoss.mongodb.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.mongodb.dao.TaskDAO;
import org.zkoss.mongodb.model.Task;
import org.zkoss.mongodb.MongoDBManager;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
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
public class MongoDBMorphiaDemoCtrl extends GenericForwardComposer {

	Listbox tasks;
	Textbox name;
	Intbox priority;
	Datebox date;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		TaskDAO taskDao = new TaskDAO(MongoDBManager.getMongo(), MongoDBManager.getMorphia());
		List<Task> taskList = taskDao.find().asList();
		tasks.setItemRenderer(new ListitemRenderer() {
			
			@Override
			public void render(Listitem item, Object data) throws Exception {
				Task task = (Task) data;
				item.setValue(task);
				new Listcell(task.getName()).setParent(item); 
				new Listcell("" + task.getPriority()).setParent(item); 
				new Listcell(new SimpleDateFormat("yyyy-MM-dd").format(task.getExecutionDate())).setParent(item);
			}
		});
		tasks.setModel(new ListModelList(taskList));
	}
	
	public void onSelect$tasks(SelectEvent evt) {
		Task task = (Task) tasks.getSelectedItem().getValue();
		name.setValue(task.getName());
		priority.setValue(task.getPriority());
		date.setValue(task.getExecutionDate());
	}
	public void onClick$add(Event evt) {
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
	
	public void onClick$update() {
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
	
	public void onClick$delete() {
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
