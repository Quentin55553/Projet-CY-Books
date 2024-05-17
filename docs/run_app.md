# Comment lancer l'application sous Linux ?

> [!NOTE]
> ## Prérequis
> - Avoir Linux comme système d'exploitation (recommandé) [Installer une distribution Linux](https://www.linux.org/pages/download/)
> - [Installer Maven](install_maven.md)
> - [Installer un JDK](install_jdk.md)

> [!NOTE]
> ## À savoir
> L'application peut également être lancée sur Windows à l'aide d'un JDK de la version **20** ou ultérieure ainsi que de **Maven**.<br>
> Le script de lancement de l'application peut être exécuté grâce à l'outil d'émulation [Git Bash](https://gitforwindows.org/) ou via [WSL](https://learn.microsoft.com/fr-fr/windows/wsl/install).

## Étape 1 : Ouvrir le terminal Linux
Ouvrez l'explorateur de fichier et placez-vous à la racine du répertoire de l'application.<br>
Faites un **CLIC DROIT** avec la souris et cliquez sur `Ouvrir dans un terminal`.

## Étape 2 : Lancer l'application
### Exécuter le programme de lancement de l'application
Pour pouvoir compiler et exécuter l'application sur votre ordinnateur, exécutez le programme de lancement de l'application à l'aide de la commande suivante :

```bash
bash run_app.sh
```

### Choisir l'interface
Une fois la compilation du projet réussie le programme de lancement vous demandera avec quelle interface vous souhaitez utiliser l'application.<br>
Si vous voulez utilisez l'application avec l'interface graphique entrez **O**, sinon l'application se lancera avec l'interface en ligne de commande (rentrez **n**).<br>
Appuyez ensuite sur **ENTRÉE**.

### Vous voilà sur l'application ! 🎉
