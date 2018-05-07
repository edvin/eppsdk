@echo off
SET verisignGroupId=com.verisign.epp
SET verisignVersion=3.13.0.2

SET modules=contact domain host gen

for /d %%m in (%modules%) do (
	cd %%m
	call build.bat
	cd ..
	mvn install:install-file -Dpackaging=jar -DgroupId=%verisignGroupId% -Dversion=%verisignVersion% -DartifactId=%%m -Dfile=lib\epp\epp-%%m.jar
)
