javac -cp ./ -d ./ DatabaseInterface.java
javac -cp ./ -d ./ RentalObject.java
jar cfmv DatabaseInterface.jar Manifest.txt DatabaseInterface.class RentalObject.class
java -jar DatabaseInterface.jar