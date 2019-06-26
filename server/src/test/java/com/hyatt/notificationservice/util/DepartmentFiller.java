package com.hyatt.notificationservice.util;

import com.hyatt.notificationservice.model.Department;
import com.google.common.collect.Lists;

import java.util.List;

public class DepartmentFiller {

    private DepartmentFiller() {
        // no-args constructor
    }

    public static final String DEPARTMENT_NAME_1 = "department_Name_1";
    public static final String DEPARTMENT_NAME_2 = "department_Name_2";
    public static final String DEPARTMENT_NAME_3 = "department_Name_3";

    public static final String DEPARTMENT_ID_1 = "department_id_1";
    public static final String DEPARTMENT_ID_2 = "department_id_2";
    public static final String DEPARTMENT_ID_3 = "department_id_3";

    public static final Department DEPARTMENT_WRONG_NAME = new Department(DEPARTMENT_ID_1, null);
    public static final Department DEPARTMENT_WRONG_ID = new Department(null, DEPARTMENT_NAME_1);
    public static final Department DEPARTMENT_1 = new Department(DEPARTMENT_ID_1, DEPARTMENT_NAME_1);
    public static final Department DEPARTMENT_2 = new Department(DEPARTMENT_ID_2, DEPARTMENT_NAME_2);
    public static final Department DEPARTMENT_3 = new Department(DEPARTMENT_ID_3, DEPARTMENT_NAME_3);

    public static final List<Department> DEPARTMENTS = Lists.newArrayList(DEPARTMENT_1, DEPARTMENT_2, DEPARTMENT_3);
}
