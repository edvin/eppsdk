#!/bin/bash
verisignGroupId=com.verisign.epp
verisignVersion=3.13.0.2
modules="contact domain host gen secdns"

for module in $modules; do (
	cd $module
	chmod +x build.sh
	./build.sh
	cd ..
	mvn install:install-file -Dpackaging=jar -DgroupId=$verisignGroupId -Dversion=$verisignVersion -DartifactId=$module -Dfile=lib/epp/epp-$module.jar
)
done
