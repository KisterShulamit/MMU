package com.hit.client;

import com.hit.view.CacheUnitView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CacheUnitClientObserver implements PropertyChangeListener {

    private final CacheUnitClient cacheUnitClient;

    public CacheUnitClientObserver() {
    	cacheUnitClient = new CacheUnitClient();
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        String res = null;
        CacheUnitView newUI = (CacheUnitView) evt.getSource();
        try {
            res = cacheUnitClient.send(evt.getNewValue().toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            res = "false";
        }
        newUI.updateUIData(res);
    }
}
