# Rhizome

The aim of this project is to design a completely decentralized platform for
assigning and distributing computing with agreements enforced with Bitcoin
contracts.

## Communication

Communication is carried out through a Distributed Hash Table. Authority will be based on Trust.
This first version uses the Kademlia hash table as it is easier to use and prove the concept with.

The next version(Alpha 0.2) will use the Chord Distributed Hash Table and allow for location swapping
in the manner of Oskar Sandberg.

## Computing

Jobs will be distributed as signed jar files.

Data files will be distributed as signed raw files.

## Agreements

Alpha 0.5 will implement OpenAssets to use with Bitcoin for secure agreements.

In the meantime, users will complete work units and submit data for the job creator
to validate using a validation function.

Agreements could be implemented either with OpenAssets or Counterparty contracts.
This mechanism has to be evaluated a bit more before a decision can be made.
