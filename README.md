# TheMiddleman
A Service for discovery/management of circular trading opportunities

## Idea
From personal experience buying and selling musical instruments, I'm never only selling or only buying stuff, I'm always looking for something and sometimes I'm willing to trade.
Trading is hard though, as it's not easy to find someone that has what you're looking for and wants what you're selling, and here is where The middle man will come into play.

Let's have this potential scenario:
Jack sells a saxophone, and starts a search for a cheap used car
Nick sells his old car, and starts a search for a new fancy TV
Hanna sells an amazing TV, and starts a search for vintage saxophone

Normal 1to1 communication Scenario:
Jack contacts Nick suggesting a trade between his saxophone and Nick's car, Nick rejects the trade as he's not interested in a saxophone. 
Nick contacts Hanna suggesting a trade between his car and Hanna's TV, Hanna rejects the trade. 
Hanna contacts Jack suggesting a trade between her TV and Jack's saxophone, Jack rejects the trade. 
If no one is willing to buy the item before selling theirs, no one gets what they want and no one is happy.

Scenario with "the middle man":
The middleman notices that connecting all the three users together, they could all get what they want, so sends them a notification saying:
"Hey guys, if Jack gives the saxophone to Hanna, Nick gives the car to Jack, and Hanna gives the TV to Nick, everybody is happy!"
The service would also show the respective "net" price differences to suggest the least number of monetary transactions.
Every user would receive a notification, and can decide wether he wants to go forward with the trading or not. If all users accept, they can start a conversation with each other to arrange for the deal to happen.

"The middle man" would not be "Yet Another Platform", but rather a pluggable platform-agnostic module that can be either embedded into a single platform, or used as a shared SaaS for cross-platform matching, it wouldn't matter if it's a vertical or horizontal platform.
Imagine Jack, interested in a car he saw in an Auto vertical like AutoScout24, while the owner of that same auto, Nick, noticed and is interested in a vintage saxophone sold by Jack on ebay.
If both ebay and autoscout integrate the shared "middle man" service, Jack and Nick become aware of each other's interest and can find a better compromise to close both deals!

Each user would be able to tweak his experience by configuring:
* the maximum amount of money he wants to "add" to the transaction (checked against the calculated net amount for him)
* the maximum number of people he's willing to trade with (number of hops in the algorithm)
Every trade opportunity that does not meet the preferences will be automatically rejected.

Also for each trading opportunity the user can:
* explicitly accept or ignore (reject) the trading opportunity
* see the "acceptance" status of the other parties
* initiate a conversation with the parties that have accepted

In the case of cross-platform matching, the configuration, as well as all the matched trading opportunities and conversations, would be accessible through any of the integrated platforms.
Using a hashed email address, using a shared public key, would make sure that the user is recognized across all integrated platforms (as long as he registered with the same email address) while still keeping the data anonymized in the shared service.

## Behind the scenes
From a tech perspective, the middle man steals concepts from Graph Theory and applies them via an actor model framework, where each user is an actor and a vertex of the graph and each "interest" is an Edge in the graph connecting it to another actor/vertex.
Everytime an item is viewed by a user (or another condition defined by the integrating platform), the graph around that user is updated with the new "interest", and the service starts looking for cycles in the graph.
The User actor sends a message to all connected "interests", with his identity, and the maximum number of "hops" to navigate.
Each receiving User/actor, forwards the message to all connected "interests" by adding himself so that the path is propagated. 
If the message comes back to the original actor, a cycle/trading opportunity is found.
Everytime a cycle is found, if it matches all the users' preferences (maximum net cost), the users at the vertices are notified about the trading opportunity.
Once the users see the notification and "accept" the trade, they can start the conversation to close the deal.

## Tech Stack
For this project I plan to use:
* Kotlin as base language
* Micronaut as full-stack framework
* Akka as actor-model framework
* Swagger for api documentation
* this project: https://github.com/gotenks82/alexslist will integrate with the middleman service
