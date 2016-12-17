package com.gu.projack.projack;

/**
 * Created by vishal on 17/12/2016.
 */
public class SqlTasks {
    private SqlDbHandler helper;

    int _id, story_point,priority, project_id;
    String task_name, status, task_desc;

    public SqlTasks() {
    }

    public SqlTasks(int _id, String task_name,String task_desc, int story_point, int priority, String status, int project_id) {
        this._id = _id;
        this.story_point = story_point;
        this.task_name = task_name;
        this.task_desc = task_desc;
        this.priority = priority;
        this.status = status;
        this.project_id = project_id;
    }

    public SqlTasks(String task_name, String task_desc, int story_point, int priority, String status, int project_id) {
        this.story_point = story_point;
        this.task_name = task_name;
        this.task_desc = task_desc;
        this.priority = priority;
        this.status = status;
        this.project_id = project_id;
    }

    public SqlTasks(int _id, String task_name, String task_desc, int story_point, int priority) {
        this._id = _id;
        this.task_name = task_name;
        this.task_desc = task_desc;
        this.story_point = story_point;
        this.priority = priority;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public void setTask_desc(String task_desc) {
        this.task_desc = task_desc;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getStory_point() {
        return story_point;
    }

    public void setStory_point(int story_point) {
        this.story_point = story_point;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}



