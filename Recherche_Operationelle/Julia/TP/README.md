# Mise en context

Lors de ce TP il fallait, avec le langage de programmation Julia, déterminer le ou les sites de productions à construire pour être le plus rentable possible au niveau transport.
En effet, nous avons comme données 
  * un ensemble de **sites** où l'on peut construire une production.
  * un ensemble de **magasins** déjà construit qui s'approvisionne auprès des sites de productions
  * Et pour chaque route (ref excel), existant, le **coût** du transport

A ça s'ajoute certaines contraintes:
* Il ne peut y avoir de routes reliant un magasin/sites à un site qui n'a pas été construit
* Il faut construire au moins un site
* chaque magasin n'à le droit de s'approvisionner auprès d'un seul site de production
* L'utilisation de l'algorithme de dijkstra 
* L'utilisation de la fonction optimize de la librairie JuMP

