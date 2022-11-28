javac -cp ./ -d ./ DatabaseInterface.java
jar cfmv DatabaseInterface.jar Manifest.txt DatabaseInterface.class
java -jar DatabaseInterface.jar