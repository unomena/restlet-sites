# convert a single markdown file or a full directory

if [ -f "$1" ]; then
  # single file. By convention, we provide 3 arguments: source file, target file, template
  # editors may be enclined to create links between marked down files... 
  sed -i 's#\.md##' $1
  # generate html document
  pandoc -f markdown -t html -s --template=$3 --base-header-level=4 -o "$2" "$1"
  # remove mark down document
  rm "$1"
elif [ -d "$1" ]; then
  # Directory. By convention, we provide 2 arguments: source directory, template
  # editors may be enclined to create links between marked down files... 
  find $1 -name '*.md' -exec sed -i 's#\.md##' {} \;
  # generate html documents
  find $1 -name '*.md' -exec pandoc -f markdown -t html -s --template=$2 --base-header-level=4 -o '{}.html' '{}' \;
  # remove mark down documents
  find $1 -name '*.md' -delete
  # rename generated files
  find $1 -name '*.md.html' -exec rename s/\.md.html$/\.html/ {} \;
fi


