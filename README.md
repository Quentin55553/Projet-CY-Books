# CY-Books

<div>
  <img src="src/main/resources/org/openjfx/cybooks/img/Cy books_vert.png" style="width: 55%;">
</div>

## 📋 Projet

CY-Books est une application programmée en Java permettant aux bibliothécaires de gérer leur bibliothèque.

Pour cela, elle dispose de toutes les fonctionnalités les plus élémentaires telles que l'inscription des bibliothécaires et des usagers, la gestion des livres et du stock ainsi que celle des différents emprunts effectués par les usagers. Toutes ces informations sont stockées dans une base de données SQL locale.

Le bibliothécaire a par exemple également la possibilité de modifier les informations des usagers, d'effectuer des recherches multi-critères, d'afficher l'historique des emprunts ou la liste des retards de rendu d'un usager pour l'aider dans la gestion des livres et des emprunts.

L'application peut soit être lancée avec une interface en ligne de commande ou avec l'interface graphique qui lui est dédiée grâce à un programme écrit en Shell.

L'interface graphique de l'application à été produite à l'aide de JavaFX et la relation avec la base de données est permise grâce au driver JDBC (Java DataBase Connectivity).<br>
L'application utilise l'API de la Bibliothèque nationale de France (BNF) afin de récupérer la liste des livres possibles.

### 👀 Aperçu

<div align="center">
  Gif de présentation du projet
  <img src="img/cybooks_presentation.gif" />
</div>

## 🚀 Lancer l'application
> [Accéder au tutoriel](docs/run_app.md)
