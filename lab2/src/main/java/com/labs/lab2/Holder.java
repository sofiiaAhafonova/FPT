package com.labs.lab2;

public class Holder{
    private State startState;
    private Event triggerEvent;
    private State endState;

    public Holder(State startState, Event triggerEvent, State endState) {
        this.startState = startState;
        this.triggerEvent = triggerEvent;
        this.endState = endState;
    }
    public State getEndState(){
        return this.endState;
    }

    public boolean equals(State startState, Event triggerEvent){
        return startState == this.startState && triggerEvent == this.triggerEvent;
    }

}
