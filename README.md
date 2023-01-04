# Cookiefactory-22-23-Team-i

Ce projet est un **fork** du travail qu'on a eu à réaliser pour valider notre module de conception logicielle.
On a fait évoluer une application Java chaque semaine en commençant par la conception UML jusqu'a la partie programmation en suivant la méthode AGILE

 

## doc
Contient le rapport final

## .github
   1. Contient sous workflows/maven.yml, une version d'un fichier d'actions qui est déclenché dès que vous poussez du code. 
Sur cette version initiale seule un createOrder Junit5 est déclenché pour vérifier que tout fonctionne.
       - Github Actions (See in .github/workflows) to simply make a maven+createOrder compilation
  2. Contient sous ISSUE_TEMPLATE, les modèles pour les issues user_story et bug. Vous pouvez le compléter à votre guise.

## src
 - pom.xml : 
       - Cucumber 7 et JUnit 5
       - Maven compatible
       - JDK 17



## User stories 
La liste des fonctionnalités livrées par user story.
### Passage Commande :
   - Choisir un magasin assossier a notre commande [#13](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/13)
   - Choisir un horaire de retrait pour note commande [#31](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/31)
   - Modifier, par l'ajouts de cookies, ma commande [#13](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/13)
   - Modifier, par le retrait de cookies, ma commande [#13](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/13)
   - Afficher le prix d'une commande [#10](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/10)
   - Annuler une commande [#11](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/11) / [#44](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/44)
   - Payer et Valide une commande [#13](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/13)
   - Verification de stock lors de l'ajout d'un cookies coté client [#17](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/17)
   - ajouter plusieurs cookies a la commande [#60](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/60)
   - Envoie de Facture [#13](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/13)
### Preparation Commande :
   - Commande en preparation auprès d'un cuisinier puis prete [#21](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/21) / [#46](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/46)
   - Gestion des paniers surprises avec ToGoodToGo [#51](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/51) 
### Retrait Commande :
   - Retrait d'une commande : [#57](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/57)
   - Ping apres passage de 5min ou 1 heure en plus sur l'horaire de retrait de commande : [#14](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/14)
   - To good to go [#51](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/51)
### Interaction User :
   #### Client :
   - creation de compte et inscription Loyalty Program [#28](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/28)
   - Application Loyalty Program [#40](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/40)
   - Pouvoir commander un Party Cookies [#39](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/39)
   - Voir sont historique de commande [#58](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/58)
   - Pouvoir être notifié à l'ajout d'un panier surprise à un jour et une heure donné [#51](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/51) 
   #### Manager :
   - Changer les horaires d'ouverture du magasin [#32](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/32)
### Interaction Admin :
   - Ajout/retrait d'une recette [#27](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/27)
   - Mettre a jour une liste d'ingrediant [#6](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/6)
   - Ajouter/ retirer un Magasin et modifier la taxe[#26](https://github.com/PNS-Conception/cookiefactory-22-23-i/issues/26)
   
   
