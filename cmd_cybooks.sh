#!/bin/bash

Couleur_OFF='\033[0m'    # Réinitialisation des couleurs
Rouge='\033[0;31m'       # Rouge
Vert='\033[0;32m'        # Vert
Jaune='\033[0;33m'       # Jaune
Violet='\033[0;35m'      # Violet
Orange='\e[38;5;208m'    # Orange
Noir='\033[0;30m'        # Noir
Bleu='\033[0;34m'        # Bleu
Cyan='\033[0;36m'        # Cyan
Blanc='\033[0;37m'       # Blanc

MEF_OFF='\e[0m'          # Réinitialisation de la mise en forme du texte
Gras='\e[1m'             # Texte en gras
Italique='\e[3m'         # Texte en italique
Souligne='\e[4m'         # Texte souligné


function compile() {
	# Checks whether mvn command is installed
	if ! command -v mvn &> /dev/null; then
		echo "La commande 'mvn' n'est pas installée sur votre système."
		# Asks the user if he wants to install the Maven tool, stores the answer in the 'install_mvn' variable
		read -p "Souhaitez-vous l'installer maintenant ? (O/n) " install_mvn
		
		# Converts the string to lower case to avoid unintended behavior
		install_mvn=$(echo "$install_mvn" | tr '[:upper:]' '[:lower:]')
		
		# If the user wants to proceed with the installation
		if [ "$install_mvn" = "o" ] || [ "$install_mvn" = "oui" ]; then
			# Install Maven
			sudo apt install maven
		fi
	fi
	
	echo -e "Compilation des scripts..."

	# Cleans the whole output folder and then compiles the project with Maven
	mvn -q clean compile
	
	if [ $? -eq 0 ]; then
		echo -e "${Vert}Compilation du projet réussie !${Couleur_OFF}"
	else
		echo -e "${Rouge}Échec de la compilation du projet${Couleur_OFF}"
		# Stops the script execution with a positive exit code (error)
		exit 1
	fi
}


function main() {
	# Executes the compile function
	compile
	
	# Lancer l'interface graphique
	# mvn clean javafx:run
	
	exit 0
}


# Amorçage du script
main

