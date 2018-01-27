# Monte-Carlo-Option-Profitability-

# Goal 
The goal of this project is to see the profitability of managing short option positions. 

# What it does
Takes user input and determines the profitability of a short option strategy using a monte carlo simulation. 

# How it works
Takes user input for: Call or put, Strike, Underlying Price, Risk Free Rate (usually 2-year bonds), Implied vol of underlying, Implied vol of option, Days till expiration date, Current day of week (0 = Monday 6 = Sunday), when to cut losses, when to take profit and the amount of simulations to run

Once it has all needed inputs it will run a monte carlo simulation

The monte carlo simulation will simulate the daily price of underlying using the implied vol of the underlying

It will price the options using the Black-Scholes model

After the entire simulation is finished it will print out the average profitability of the strategy and some other useful stats. 
