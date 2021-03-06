package org.coderswithoutborders.deglancer.stagehandlers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.coderswithoutborders.deglancer.R;
import org.coderswithoutborders.deglancer.interactor.IDatabaseInteractor;
import org.coderswithoutborders.deglancer.model.ScreenAction;
import org.coderswithoutborders.deglancer.model.Stage;
import org.coderswithoutborders.deglancer.model.TriState;
import org.coderswithoutborders.deglancer.utils.TimeUtils;
import org.coderswithoutborders.deglancer.utils.ToastUtils;

import timber.log.Timber;

/**
 * Created by Renier on 2016/04/27.
 */
public class Stage3Handler implements IStageHandler {
    private Context mContext;
    private IDatabaseInteractor mDatabaseInteractor;

    public Stage3Handler(Context context, IDatabaseInteractor databaseInteractor) {
        mContext = context;
        mDatabaseInteractor = databaseInteractor;
    }

    @Override
    public void handleScreenAction(ScreenAction action) {
        if (action.getEventType().equals(Intent.ACTION_SCREEN_ON)) {

            //get unlock count up to current hour for this stage day
            long unlockCount = mDatabaseInteractor.getUnlockCountForStageDay(action.getStage(), action.getDay());

            //get totalSOTTime up to current hour for this stage day
            long totalSOTTime = mDatabaseInteractor.getTotalSOTForStageDay(action.getStage(), action.getDay());

            //get average sft time up to current hour for this stage day
            // double avgSFTTime = mDatabaseInteractor.getAverageSFTForStage(action.getStage(), action.getDay(), action.getHour());
            // override avgSFTTimePreviousStage with last SFT
            long sinceLastLock = action.getDuration();
            double avgSFTTime = (double) sinceLastLock;

            Timber.d(String.format("We're in %d", action.getStage()));
            Stage stageToCompareTo = new Stage(action.getStage() - 1, action.getDay(), action.getHour());
            //get unlock count up to current hour in previous stage
            // TODO - Get the number of unlocks up to the current hour - done but test
            long unlockCountPreviousStage = mDatabaseInteractor.getUnlockCountForStageFromAverages(stageToCompareTo.getStage(), stageToCompareTo.getDay(), stageToCompareTo.getHour());

            //get totalSOTTime up to current hour in previous stage
            // TODO - Get the total SOT up to the current hour - done but test
            long totalSOTTimePreviousStage = mDatabaseInteractor.getTotalSOTForStageFromAverages(stageToCompareTo.getStage(), stageToCompareTo.getDay(), stageToCompareTo.getHour());

            //get average sft time up to current hour in previous stage
            // TODO - Get the average SFT up to current hour - done but test
            double avgSFTTimePreviousStage = mDatabaseInteractor.getAverageSFTForStageFromAverages(stageToCompareTo.getStage(), stageToCompareTo.getDay(), stageToCompareTo.getHour());


            double unlockPercentage = unlockCount / (unlockCountPreviousStage > 0 ? unlockCountPreviousStage : unlockCount);
            double sotPercentage = totalSOTTime / (totalSOTTimePreviousStage > 0 ? totalSOTTimePreviousStage : totalSOTTime);
            double sftPercentage = avgSFTTime / (avgSFTTimePreviousStage > 0 ? avgSFTTimePreviousStage : avgSFTTime);


            double unlockDiffPercentage = unlockCount >= unlockCountPreviousStage ? (unlockPercentage - 1) * 100 : (1 - unlockPercentage) * 100;
            double sotDiffPercentage = totalSOTTime >= totalSOTTimePreviousStage ? (sotPercentage - 1) * 100 : (1 - sotPercentage) * 100;
            double sftDiffPercentage = avgSFTTime >= avgSFTTimePreviousStage ? (sftPercentage - 1) * 100 : (1 - sftPercentage) * 100;

            TriState unlockState;
            TriState sotState;
            TriState sftState;

            if (unlockCount == unlockCountPreviousStage) {
                unlockState = new TriState(TriState.State.Same);
            } else if (unlockCount > unlockCountPreviousStage) {
                unlockState = new TriState(TriState.State.Worse);
            } else{
                unlockState = new TriState(TriState.State.Better);
            }

            if (totalSOTTime == totalSOTTimePreviousStage) {
                sotState = new TriState(TriState.State.Same);
            } else if (totalSOTTime > totalSOTTimePreviousStage) {
                sotState = new TriState(TriState.State.Worse);
            } else {
                sotState = new TriState(TriState.State.Better);
            }

            if (avgSFTTime == avgSFTTimePreviousStage) {
                sftState = new TriState(TriState.State.Same);
            } else if (avgSFTTime > avgSFTTimePreviousStage) {
                sftState = new TriState(TriState.State.Better);
            } else {
                sftState = new TriState(TriState.State.Worse);
            }

            ToastUtils.showToast(mContext, action.getDuration(), unlockCount, totalSOTTime, unlockState, sotState, sftState, unlockDiffPercentage, sotDiffPercentage, sftDiffPercentage);
        }
    }
}
