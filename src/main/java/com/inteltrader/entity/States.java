package com.inteltrader.entity;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.qlearningadvisor.State;
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

}
