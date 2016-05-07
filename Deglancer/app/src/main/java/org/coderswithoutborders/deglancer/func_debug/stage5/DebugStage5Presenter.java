package org.coderswithoutborders.deglancer.func_debug.stage5;

import org.coderswithoutborders.deglancer.func_debug.stage3.IDebugStage3Presenter;
import org.coderswithoutborders.deglancer.func_debug.stage3.IDebugStage3View;
import org.coderswithoutborders.deglancer.interactor.IStageInteractor;
import org.coderswithoutborders.deglancer.model.Stage;

/**
 * Created by Renier on 2016/05/06.
 */
public class DebugStage5Presenter implements IDebugStage5Presenter {
    private IDebugStage5View mView;
    private IStageInteractor mStageInteractor;

    private Stage mCurrentStage;


    public DebugStage5Presenter(IStageInteractor stageInteractor) {
        this.mStageInteractor = stageInteractor;
    }

    @Override
    public void setView(IDebugStage5View view) {
        mView = view;
    }

    @Override
    public void clearView() {
        mView = null;
    }

    @Override
    public void onAttached() {
    }

    @Override
    public void onDetached() {

    }

    @Override
    public void previousStageClicked() {
        mStageInteractor.goToPreviousStage();

        if (mView != null) {
            mView.moveToStage4View();
            mView.finishActivity();
        }
    }
}
