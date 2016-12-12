package com.gu.projack.projack;

/**
 * Created by vishal on 10/12/2016.
 */
public class SqlProjects {

    private  SqlDbHandler helper;

    int _id;
    String _project_name, _project_desc,_project_type;
    String _project_start_date,_project_end_date;


    public SqlProjects() {
    }

    public SqlProjects(String _project_name, String _project_desc, String _project_type, String _project_start_date, String _project_end_date) {
        this._project_name = _project_name;
        this._project_desc = _project_desc;
        this._project_type = _project_type;
        this._project_start_date = _project_start_date;
        this._project_end_date = _project_end_date;
    }


    public SqlProjects(int _id, String _project_name, String _project_desc, String _project_type, String _project_start_date, String _project_end_date) {
        this._id = _id;
        this._project_name = _project_name;
        this._project_desc = _project_desc;
        this._project_type = _project_type;
        this._project_start_date = _project_start_date;
        this._project_end_date = _project_end_date;
    }

    public int get_project_id() {
        return _id;
    }

    public void set_project_id(int _project_id) {
        this._id = _project_id;
    }

    public String get_project_name() {
        return _project_name;
    }

    public void set_project_name(String _project_name) {
        this._project_name = _project_name;
    }

    public String get_project_desc() {
        return _project_desc;
    }

    public void set_project_desc(String _project_desc) {
        this._project_desc = _project_desc;
    }

    public String get_project_type() {
        return _project_type;
    }

    public void set_project_type(String _project_type) {
        this._project_type = _project_type;
    }

    public String get_project_start_date() {
        return _project_start_date;
    }

    public void set_project_start_date(String _project_start_date) {
        this._project_start_date = _project_start_date;
    }

    public String get_project_end_date() {
        return _project_end_date;
    }

    public void set_project_end_date(String _project_end_date) {
        this._project_end_date = _project_end_date;
    }
}
