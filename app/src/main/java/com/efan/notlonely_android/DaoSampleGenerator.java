package com.efan.notlonely_android;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DaoSampleGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "com.efan.notlonely_android.entity");

        addUser(schema);
        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }

    private static void addUser(Schema schema) {
        Entity note = schema.addEntity("user");
        note.addIdProperty();
        note.addIntProperty("userid").notNull();
        note.addStringProperty("username").notNull();
        note.addStringProperty("password");
        note.addDateProperty("nickname");
        note.addStringProperty("sex");
        note.addDateProperty("introduction");
        note.addStringProperty("created_at");
        note.addDateProperty("updated_at");
        note.addDateProperty("url");
    }
}
