package com.hyatt.notificationservice.util;

import com.hyatt.notificationservice.model.Configuration;
import com.google.common.collect.Lists;

import java.util.List;

public class ConfigurationFiller {

    private ConfigurationFiller() {
        // no-args constructor
    }

    public static final String CONFIGURATION_NAME_1 = "configuration_Name_1";
    public static final String CONFIGURATION_NAME_2 = "configuration_Name_2";
    public static final String CONFIGURATION_NAME_3 = "configuration_Name_3";

    public static final String CONFIGURATION_VALUE_1 = "configuration_value_1";
    public static final String CONFIGURATION_VALUE_2 = "configuration_value_2";
    public static final String CONFIGURATION_VALUE_3 = "configuration_value_3";

    public static final Configuration CONFIGURATION_WRONG_NAME = new Configuration(null, CONFIGURATION_VALUE_1);
    public static final Configuration CONFIGURATION_WRONG_VALUE = new Configuration(CONFIGURATION_NAME_1, null);
    public static final Configuration CONFIGURATION_1 = new Configuration(CONFIGURATION_NAME_1, CONFIGURATION_VALUE_1);
    public static final Configuration CONFIGURATION_2 = new Configuration(CONFIGURATION_NAME_2, CONFIGURATION_VALUE_2);
    public static final Configuration CONFIGURATION_3 = new Configuration(CONFIGURATION_NAME_3, CONFIGURATION_VALUE_3);

    public static final List<Configuration> CONFIGURATIONS = Lists.newArrayList(CONFIGURATION_1, CONFIGURATION_2, CONFIGURATION_3);
}
