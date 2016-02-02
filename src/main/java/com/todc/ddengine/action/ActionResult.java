package com.todc.ddengine.action;

/**
 * @author Tim O'Donnell (tim.odonnell@imperva.com)
 */
public class ActionResult {


    // ----------------------------------------------------- Instance Variables


    private Action alternate;
    private boolean successful = false;


    // --------------------------------------------------------------- Mutators


    public Action getAlternate() {
        return alternate;
    }

    public void setAlternate(Action alternate) {
        this.alternate = alternate;
    }

    public boolean isSuccessful() {
        return successful;
    }


    // ----------------------------------------------------------- Constructors


    public ActionResult() {}

    public ActionResult(boolean successful) {
        this.successful = successful;
    }


    // --------------------------------------------------------- Public Methods


    public boolean hasAlternate() {
        return alternate != null;
    }

}
