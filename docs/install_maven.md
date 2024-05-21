# Comment installer Maven sous Linux ?

> [!NOTE]
> ## Prérequis
> - Avoir les privilèges administrateur

## Étape 1 : Ouvrir le terminal Linux
Sur votre ordinateur Linux, ouvrez le terminal (**Ctrl+Alt+T**).

## Étape 2 : Mettre à jour la liste des packages disponibles
Sur votre terminal, mettez à jour vos packages à l'aide de la commande suivante :

```bash
sudo apt-get update
```

## Étape 3 : Mettre à jour les packages installés sur votre système
Installez maintenant les mises à niveau disponibles de tous les packages actuellement installés sur le système à l'aide de la commande suivante :

```bash
sudo apt-get upgrade
```

## Étape 4 : Installer Maven
Vous pouvez désormais installer Maven sur votre système à l'aide de la commande suivante :

```bash
sudo apt install maven
```

Si une confirmation vous est demandée afin de poursuivre l'installation, entrez **O** et appuyez sur **ENTRÉE**.

## Étape 5 : Vérifier l'installation

Pour vérifier que Maven a bien été installé, vous pouvez vérifier la version installée sur votre système à l'aide de la commande suivante :

```bash
mvn -v
```
