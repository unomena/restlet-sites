#!/usr/bin/env sh

TITLESIZE="##"
TITLEPAGE="#"
TITLENOTE="###"

if [ "$#" -ne 1 ];
then
	echo "Saisissez le chemin du répertoire contenant les fichiers html à transformer en markdown"
	echo "Attention, ce script est récursif et supprimera les fichiers html"
	exit 1
else
	echo "Attention, script récursif et supprimera les fichiers html"
	echo "Vérifiez le chemin du dossier"
	read -r -p "Continuer [y/N] ? " response
	default=N
	if [ -z "$response" ]; then
		response=$default
	fi

	case "$response" in
		Y*|y*) break ;;
		N*|n*) exit 1 ;;
	esac	
fi

if [ -d "$1" ];
then
	cd $1
	#Find html files and convert them with pandoc
	FILES=`find -name '*.html'`
else
	FILES=$1
fi

for FILE in $FILES
do
	MARKFILE=`echo $FILE | sed -e "s/.html/.md/"`
	pandoc $FILE -f html -t markdown -s -o $MARKFILE
	rm $FILE
done

if [ -d "$1" ]
then
	#Find converted markdown files and perform some modifications
	MDFILES=`find -name '*.md'`
else
	MDFILES=`echo $FILES | sed -e "s/.html/.md/"`
fi

for MDFILE in $MDFILES
do
	#remove freemarker call for header
	sed -i '/\\<\\#global title=/d' $MDFILE
	sed -i '/content\\>/d' $MDFILE
	sed -i '1{/^ *$/d}' $MDFILE
	
	#remove name used in conversion for image : 
	#from ![image](image) to ![](image)
	sed -i -E 's/!\[([a-zA-Z0-9]+[,]*[ ]*[: ]*)*\]/!\[\]/g' $MDFILE
	
	#Replace titles by links
	sed -i -E 's/[#]+[ ](([0-9]+)[. ]([a-zA-Z0-9]+[,]*[ ]*[: ]*)*)/'"$TITLESIZE"' <a name="part\2">\1/g' $MDFILE
	sed -i -E 's/<a name="part(.*)*/&<\/a>/g' $MDFILE
	#Remove multiple <\a>
	sed -i -E 's/(<\/a>)+/<\/a>/g' $MDFILE
	#Replace title for conclusion and notes
	sed -i -E 's/[#]+[ ]Conclusion/'"$TITLESIZE"' <a name="conclusion">Conclusion<\/a>/' $MDFILE
	sed -i -E 's/[#]+[ ]Notes/'"$TITLENOTE"' <a name="notes">Notes<\/a>/' $MDFILE
	#Replace title for table of contents
	sed -i -E 's/[#]+[ ](Table(.*)*)/'"$TITLESIZE"' <a name="toc">\1<\/a>/' $MDFILE
	sed -i -E 's/<a name="part([1-9])">/<a name="part0\1">/g' $MDFILE

	#Change title size
	sed -i -E 's/[#]+( <a name=")/'"$TITLESIZE"'\1/g' $MDFILE

	#Change page's title size
	sed	-i -E '1 s/[#]+/'"$TITLEPAGE"'/' $MDFILE

	#Change Note title size
	sed -i -E 's/[#]+( <a name="notes">Notes<\/a>)/'"$TITLENOTE"'\1/' $MDFILE

	#Remove images from paragraphs
	sed -i -E 's/\\*$//g' $MDFILE
done
