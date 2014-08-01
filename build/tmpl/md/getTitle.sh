#!/usr/bin/env sh

TITLE=`sed -n 1p $1`
NEWTITLE=`echo $TITLE | sed "s/[#+][ ]//"`
echo $NEWTITLE > $2
