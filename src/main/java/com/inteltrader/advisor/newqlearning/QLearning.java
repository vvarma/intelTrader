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

    public void initWrapper(Instrument instrument, String... token) throws IOException {
        wrapper = TAWrapper.WrapMaker(instrument, token);
    }

    public void initStates() {
        states = statesDao.retrieveStates(wrapper.getInstrument().getSymbolName());
        if (states == null) {


        }
    }
    //gamma values and alpha values should be updated during iterations
    //number of iterations to stabilise? some parameter of error

    public class Trainer {
        private double ALPHA, GAMMA;
        private int MAX_ELE_START;

        public Trainer(double ALPHA, double GAMMA, int MAX_ELE_START) {
            this.ALPHA = ALPHA;
            this.GAMMA = GAMMA;
            this.MAX_ELE_START = MAX_ELE_START;
        }

        public Trainer() {
            ALPHA=0.5;
            GAMMA=0.5;
            MAX_ELE_START=40;
        }

        //changeTo use methods in wrapper super only. no local instance
        public void initTrain(){
            State presentState=null;
            Advice presentAdvice=null;
            Set<State> stateSet=new HashSet<State>();
            int index=wrapper.getInstrument().getPriceList().size()-1;
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
                   updateReward(presentState,presentAdvice,state,i);
                }
                presentState=state;
                //changeTo
                presentAdvice=presentState.getNonGreedyAdvice();
            }

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

    @Override
    public Advice getAdvice() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Advice updatePriceAndGetAdvice(Price price) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public States getStates() {
        return states;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
