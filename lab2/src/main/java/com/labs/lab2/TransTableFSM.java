package com.labs.lab2;
import java.util.ArrayList;

public class TransTableFSM extends FSM {

    private ArrayList <Holder> transitions;

    public TransTableFSM(){
        this.transitions = new ArrayList<Holder>();
        this.transitions = buildTransitionTable();
    }
    public State nextState(Event event) {
        for (Holder el: transitions )
            if (el.equals(currentState, event))
                return el.getEndState();
        return State.ERROR;
    }
    private void addTransition(State startState, Event triggerEvent, State endState){
        Holder newHolder = new Holder(startState, triggerEvent, endState);
        this.transitions.add(newHolder);
    }
    private ArrayList <Holder> buildTransitionTable(){
        addTransition(State.q0, Event.PLUS, State.q1);
        addTransition(State.q1, Event.PLUS, State.q2);
        addTransition(State.q1, Event.DIGIT, State.q4);
        addTransition(State.q1, Event.NON_DIGIT, State.q3);
        addTransition(State.q2, Event.PLUS, State.q2);
        addTransition(State.q2, Event.NON_DIGIT, State.q3);
        addTransition(State.q2, Event.DIGIT, State.q4);
        addTransition(State.q3, Event.DIGIT, State.q4);
        addTransition(State.q4, Event.DIGIT, State.q4);
        return transitions;
    }
}
