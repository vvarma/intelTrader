package com.inteltrader.entity;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.qlearning.State;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 6/4/13
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Entity
@Table(name = "STATES")
public class States {
    @Id
    @Column(name = "SYMBOL_NAME")
    private String symbolNamme;
    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn(name = "states")
    private Set<State> stateSet;
    @OneToOne (cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "presentState")
    private State presentState;
    @Enumerated(EnumType.STRING)
    @Column(name = "presentAdvice")
    private Advice presentAdvice;

    public State getPresentState() {
        return presentState;
    }

    public void setPresentState(State presentState) {
        this.presentState = presentState;
    }

    public Advice getPresentAdvice() {
        return presentAdvice;
    }

    public void setPresentAdvice(Advice presentAdvice) {
        this.presentAdvice = presentAdvice;
    }

    public States() {
        stateSet=new HashSet<State>();
    }

    public String getSymbolNamme() {
        return symbolNamme;
    }

    public void setSymbolNamme(String symbolNamme) {
        this.symbolNamme = symbolNamme;
    }

    public Set<State> getStateSet() {
        return stateSet;
    }

    public void setStateSet(Set<State> stateSet) {
        this.stateSet = stateSet;
    }

    @Override
    public String toString() {
        return "States{" +
                "symbolNamme='" + symbolNamme + '\'' +
                ", stateSet=" + stateSet +
                ", presentState=" + presentState +
                ", presentAdvice=" + presentAdvice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof States)) return false;

        States states = (States) o;

        if (presentAdvice != states.presentAdvice) return false;
        if (presentState != null ? !presentState.equals(states.presentState) : states.presentState != null)
            return false;
        if (stateSet != null ? !stateSet.equals(states.stateSet) : states.stateSet != null) return false;
        if (!symbolNamme.equals(states.symbolNamme)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = symbolNamme.hashCode();
        result = 31 * result + (stateSet != null ? stateSet.hashCode() : 0);
        result = 31 * result + (presentState != null ? presentState.hashCode() : 0);
        result = 31 * result + (presentAdvice != null ? presentAdvice.hashCode() : 0);
        return result;
    }
}
