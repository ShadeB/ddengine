package com.todc.ddengine.action;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class NoOpAction extends Action {

    @Override
    public ActionResult perform() {
        return new ActionResult(true);
    }
}
