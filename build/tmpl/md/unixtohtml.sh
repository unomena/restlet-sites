find $1 -name '*.md' -exec sed -i 's#\.md##' {} \;
find $1 -name '*.md' -exec pandoc -f markdown -t html -s --template=$2 --base-header-level=4 -o '{}.html' '{}' \;
find $1 -name '*.md' -delete
find $1 -name '*.md.html' -exec rename s/\.md.html$/\.html/ {} \;
