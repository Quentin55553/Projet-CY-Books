# Comment installer un JDK sous Linux ?

> [!NOTE]
> ## Prérequis
> - Avoir les privilèges administrateur
> [!IMPORTANT]
> Le JDK installé doit obligatoirement être de la version 20 ou ultérieure.

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

## Étape 4 : Installer un JDK
Vous pouvez désormais installer le JDK sur votre système à l'aide de la commande suivante :

```bash
sudo apt install openjdk-21-jdk
```

> [!NOTE]
> Cette commande n'est qu'un exemple valable pour pouvoir lancer l'application.
> Vous pouvez installer n'importe quelle version du moment qu'elle est de la version 20 ou ultérieure en remplaçant '**21**' par la version désirée dans la commande.

Après avoir entré la commande ci-dessus, une confirmation vous sera demandée afin de poursuivre l'installation, entrez **Y** et appuyez sur **ENTRÉE**.

## Étape 5 : Vérifier l'installation

Pour vérifier que le JDK a bien été installé, vous pouvez vérifier la version installée sur votre système à l'aide de la commande suivante :

```bash
java -version
```
