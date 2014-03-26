# generate the full table of content model
tail -n +11 $1 > $1.tmp
pandoc -f markdown -t html -s --template=$3 -o $2 $1.tmp

# generate the right table of content page (especially remove unwanted entries, and add the "title" attribute)
grep -v "notoc" $1.tmp | grep -v "\[comment]" | sed -rn -e 's#(.*)\[([^\[]*)\]\(([^\)]*)\).*#\1[\2](\3 "\2")#p' > $1

