# Mise en context

Lors de ce TP il fallait avec le langage de programmation Julia déterminer le ou les magasins à construire pour être le plus rentable possible au niveau transport.
En effet, nous avons comme données 
  * un ensemble de **sites** où l'on peut construire un magasins
  * un ensemble de **magasins** déjà construit
  * Et pour chaque route (ref excel), existant, le **coût** du transport

A ça s'ajoute certaine contrainte:
* Il ne peut y avoir des route reliant un site qui n'a pas été construit
* Il faut construire au moins un site
* chaque magasin n'à le droit de s'approvisionner chez qu'un seul site de production

