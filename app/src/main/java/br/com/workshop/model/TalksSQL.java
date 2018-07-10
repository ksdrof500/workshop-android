package br.com.workshop.model;

import android.provider.BaseColumns;

public final class TalksSQL {

    private TalksSQL(){}

    public static class TalksEntry implements BaseColumns {
        public static final String TALKS = "talks";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IMAGE = "image";
    }
}
