package org.coderswithoutborders.deglancer.func_debug.view;

import org.coderswithoutborders.deglancer.model.Stage;

/**
 * Created by Renier on 2016/05/06.
 */
public interface IStatsView {
    void setValues(
            String totalUnlocksDay, String totalUnlocksHour,
            String totalSOTDay, String totalSOTHour,
            String totalSFTDay, String totalSFTHour,
            String avgSOTDay, String avgSOTHour,
            String avgSFTDay, String avgSFTHour);
}
