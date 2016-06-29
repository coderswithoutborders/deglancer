package org.coderswithoutborders.deglancer.pretest;

import org.coderswithoutborders.deglancer.model.Results;

/**
 * Created by Lapa on 2016/06/29.
 */
public interface IPreTestView {
    void finishActivity();
    void moveToQuestion(int questionNumber);
    void moveToMainView();
}
