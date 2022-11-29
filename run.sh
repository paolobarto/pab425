javac -cp ./ -d ./ DatabaseInterface.java
javac -cp ./ -d ./ RentalObject.java
jar cfmv pab425.jar Manifest.txt DatabaseInterface.class RentalObject.class
java -jar pab425.jar