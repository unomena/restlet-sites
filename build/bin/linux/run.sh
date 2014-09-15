#!/bin/bash

BASEDIR=${PWD}
WEB_VERSION=2.2.10
DIST_DIR=$BASEDIR/dist
PROD_DIR=$DIST_DIR/web-$WEB_VERSION

# update configuration file
sed -i -e '/www.uri/d' $PROD_DIR/config/restletCom.properties
echo "www.uri=file://$PROD_DIR/www/restlet-com" >> $PROD_DIR/config/restletCom.properties

# Defines the classpath
SRV_CP=$PROD_DIR
SRV_CP=$SRV_CP:$PROD_DIR/lib/com.restlet.frontend.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/org.restlet.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/org.restlet.ext.atom.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/org.restlet.ext.freemarker.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/org.restlet.ext.jackson.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/org.restlet.ext.net.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/org.restlet.ext.xml.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/org.restlet.ext.jetty.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/com.fasterxml.jackson.annotations.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/com.fasterxml.jackson.core.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/com.fasterxml.jackson.csv.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/com.fasterxml.jackson.databind.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/com.fasterxml.jackson.jaxb.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/com.fasterxml.jackson.smile.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/com.fasterxml.jackson.xml.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/com.fasterxml.jackson.yaml.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/org.eclipse.jetty.ajp.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/org.eclipse.jetty.continuation.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/org.eclipse.jetty.http.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/org.eclipse.jetty.io.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/org.eclipse.jetty.server.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/org.eclipse.jetty.util.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/org.freemarker.jar
SRV_CP=$SRV_CP:$PROD_DIR/lib/javax.servlet.jar

java -Djava.util.logging.config.file=$PROD_DIR/config/logging.properties -classpath $SRV_CP com.restlet.frontend.web.WebComponent