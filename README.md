intelTrader
===========
the application is aimed to be an automatic trading system, which means that it should be capable of giving trade advice 
on selected stocks with a view of generating profits.

current branch: qlearning;

As of now the application has the following functionalities:
1.can download data from nse.com given date
2.creates an instrument object which is stored on the db using orm.
3.maintains a portfolio that contains the list of investments of the trader.
4.generates advices based on an adaptation of the Watkins Q Learning algorithm* 

Functionalities aimed for:
1.Stop Losses
2.Brokerage
3.Keep track of the whole market* and generating the best bet of the day.


