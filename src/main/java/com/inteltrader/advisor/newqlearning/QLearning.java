package com.inteltrader.advisor.newqlearning;

import com.inteltrader.advisor.Advice;
import com.inteltrader.advisor.Advisor;
import com.inteltrader.advisor.qlearningadvisor.Holdings;
import com.inteltrader.advisor.qlearningadvisor.State;
import com.inteltrader.advisor.tawrapper.InstrumentWrapper;
import com.inteltrader.advisor.tawrapper.TAWrapper;
import com.inteltrader.dao.IStatesDao;
import com.inteltrader.entity.Instrument;
import com.inteltrader.entity.Price;
import com.inteltrader.entity.States;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: vvarm1
 * Date: 6/28/13
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class QLearning implements Advisor {
    @Autowired
    private IStatesDao statesDao;
    private States states;
    private InstrumentWrapper wrapper;
    private Trainer trainer;

    public QLearning(double alpha,double gamma,int max_elements) {
        trainer=new Trainer(alpha,gamma,max_elements);
    }
    public void initAdvisor(Instrument instrument, String... token) throws IOException, InstantiationException {
        initWrapper(instrument,token);
        initStates();
    }
    public void initWrapper(Instrument instrument, String... token) throws IOException,InstantiationException {
        if (wrapper!=null){
            System.out.println(wrapper);
            throw new InstantiationException();
        }
        wrapper = TAWrapper.WrapMaker(instrument, token);
    }

    private void initStates() {
        states = statesDao.retrieveStates(wrapper.getInstrument().getSymbolName());
        if (states == null) {
           states=new States();
            states.setStateSet(trainer.initTrain());
            int index=wrapper.getInstrument().getPriceList().size()-1;
            states.setPresentState(wrapper.getStateBuilder(index).build());
            states.setPresentAdvice(null);
            statesDao.createState(states);
        }
    }
    //gamma values and alpha values should be updated during iterations
    //number of iterations to stabilise? some parameter of error

    public class Trainer {
        private double ALPHA, GAMMA,ALPHA_VAL;
        private int MAX_ELE_START;

        public Trainer(double ALPHA, double GAMMA, int MAX_ELE_START) {
            this.ALPHA = ALPHA;
            this.ALPHA_VAL=ALPHA;
            this.GAMMA = GAMMA;
            this.MAX_ELE_START = MAX_ELE_START;
        }

        public Trainer() {
            ALPHA=0.5;
            ALPHA_VAL=ALPHA;
            GAMMA=0.5;
            MAX_ELE_START=40;
        }

        //changeTo use methods in wrapper super only. no local instance
        public Set<State> initTrain(){
            Holdings holdings;
            State presentState=null;
            Advice presentAdvice=null;
            Set<State> stateSet=new HashSet<State>();
            int index=wrapper.getInstrument().getPriceList().size()-1;
            int iter=0;
            double pnl=0;
            boolean bool=false;
            do{
                ALPHA=ALPHA_VAL/(1+iter);
                holdings=new Holdings();
                holdings.setCurrentPrice(wrapper.getInstrument().getPriceList().get(MAX_ELE_START-1).getClosePrice());
                iter++;
                for (int i=MAX_ELE_START;i<=index;i++){
                    State state=wrapper.getStateBuilder(i).build();
                    Iterator<State> stateIterator=stateSet.iterator();
                    boolean doesntContain=true;
                    while (stateIterator.hasNext()){
                        State s=stateIterator.next();
                        if (s.equals(state)){
                            state=s;
                            doesntContain=false;
                            break;
                        }
                    }
                    if (doesntContain){
                        stateSet.add(state);
                    }
                    if (presentState!=null){
                        updateReward(presentState,Advice.BUY,state,i);
                        updateReward(presentState,Advice.SELL,state,i);
                        updateReward(presentState,Advice.HOLD,state,i);
                    }
                    presentState=state;
                    //changeTo
                    presentAdvice=presentState.getGreedyAdvice();
                    holdings.setCurrentPrice(wrapper.getInstrument().getPriceList().get(i).getClosePrice());
                    holdings.updateHoldings(presentAdvice);
                }
                bool=holdings.calcPnl()==pnl;
                pnl=holdings.calcPnl();
                System.out.println("iter :" + iter + " pnl :"+pnl +"Learning rate :"+ALPHA);
            }while ((iter<=100)&&(!bool));
           //System.out.println("Holdings :" + holdings);

            return stateSet;

        }

        private void updateReward(State state, Advice advice, State nextState, int index) {
            double reward=0;
            double sample=0;
            sample=calcRewards(index,advice)+GAMMA*nextState.getRewardForAdvice(nextState.getGreedyAdvice());
            reward=state.getRewardForAdvice(advice) + ALPHA*(sample-state.getRewardForAdvice(advice));
            state.updateReward(advice,reward);
        }

        private double calcRewards(int index, Advice advice) {
            double ret=wrapper.getInstrument().getPriceList().get(index).getClosePrice()-wrapper.getInstrument().getPriceList().get(index-1).getClosePrice();
            switch (advice){
                case BUY:
                    return ret;
                case SELL:
                    return -ret;
                case HOLD:
                    return 0;
            }
            return 0;  //To change body of created methods use File | Settings | File Templates.
        }


    }

    public InstrumentWrapper getWrapper() {

        return wrapper;
    }

    @Override
    public Advice getAdvice() {
        return states.getPresentAdvice();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Advice updatePriceAndGetAdvice(Price price) throws IOException {
       return null;
    }



    @Override
    public States getStates() {
        return states;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
