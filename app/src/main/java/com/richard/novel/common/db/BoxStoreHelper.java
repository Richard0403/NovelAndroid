package com.richard.novel.common.db;

import com.richard.novel.common.base.App;
import com.richard.novel.http.entity.MyObjectBox;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by XiaoU on 2018/10/10.
 */

public class BoxStoreHelper {
    private BoxStore boxStore;

    private static volatile BoxStoreHelper singHelper;

    private BoxStoreHelper() {
        boxStore = MyObjectBox.builder().androidContext(App.INSTANCE).build();
    }

    public static BoxStoreHelper getInstance() {
        if (singHelper == null) {
            synchronized (BoxStoreHelper.class) {
                if (singHelper == null) {
                    singHelper = new BoxStoreHelper();
                }
            }
        }
        return singHelper;
    }


    public <T> Box getBox(Class<T> type){
        Box<T> box = boxStore.boxFor(type);
        return box;
    }


    public <T> void put(Class<T> type, List<T> t){
        try {
            Box<T> box = boxStore.boxFor(type);
            box.put(t);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public <T> long count(Class<T> type){
        try {
            Box<T> box = boxStore.boxFor(type);
            return box.count();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public <T> void put(Class<T> type,T... t){
        try {
            Box<T> box = boxStore.boxFor(type);
            box.put(t);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public <T> void remove(T t, Class<T> type){
        Box<T> box = boxStore.boxFor(type);
        box.remove(t);
    }

    public <T> T get(long id, Class<T> type){
        try {
            Box<T> box = boxStore.boxFor(type);
            return box.get(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public <T> List<T> get(Class<T> type){
        Box<T> box = boxStore.boxFor(type);
        return box.getAll();
    }

    public <T> void removeAll( Class<T> type){
        Box<T> box = boxStore.boxFor(type);
        box.removeAll();
    }


}
