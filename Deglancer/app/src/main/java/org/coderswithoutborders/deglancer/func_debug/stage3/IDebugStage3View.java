package org.coderswithoutborders.deglancer.func_debug.stage3;

import org.coderswithoutborders.deglancer.model.Stage;

/**
 * Created by Renier on 2016/05/06.
 */
public interface IDebugStage3View {
    void finishActivity();
    void moveToStage4View();
    void moveToStage2View();
    void setStage(Stage stage);
    void setTitleStage(String stage);
}
