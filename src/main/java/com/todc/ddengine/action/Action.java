package com.todc.ddengine.action;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public abstract class Action {


    public static final ActionResult SUCCESS = new ActionResult(true);
    public static final ActionResult FAILURE = new ActionResult(false);


    public abstract ActionResult perform();

    public ActionResult alternate(Action altAction) {
        ActionResult alternateResult = new ActionResult();
        alternateResult.setAlternate(altAction);
        return alternateResult;
    }

}
