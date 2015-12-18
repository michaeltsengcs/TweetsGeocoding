This application only works only on Mac, Linux or Unix. (Sorry Windows).
Strongly suggest use Mac first.

(1) Twitter API Credential Verification
    - https://dev.twitter.com/oauth

(2) NLP
    # set environment variable
    export TWITTER_NLP=your_path_to_TweetsGeocoding/twitter_nlp

    # re-generate capital classifier by run the shell
    ./twitter_nlp/python/cap/build.sh

(3) GeoNames
    - Two libraries need to be added to the project: (1) geonames-1.1.13.jar (2) jdom-1.0.jar. (can be found in http://www.geonames.org/source-code/)
(4) MongoDB (optional)
    - MongoDB Java library needs to be added to the project: mongodb-driver-3.0.4.jar, this can be found in: https://oss.sonatype.org/content/repositories/releases/org/mongodb/mongodb-driver/3.0.4/

2. Restore our data into your mongoDB
   mongorestore -d some_other_db -c some_or_other_collection dump/some_collection.bson

3. Reference
(1) NLP
    - https://github.com/aritter/twitter_nlp

(2) GeoNames
    - Java Client Overview: http://www.geonames.org/source-code/
    - Java API Doc: http://www.geonames.org/source-code/javadoc/
    - Web services desription: http://www.geonames.org/export/geonames-search.html
    - A specification for data in GeoNames: http://download.geonames.org/export/dump/readme.txt

(3) MongoDB (Optional)
    - MongoDB 3.2 Java Manual: https://docs.mongodb.org/getting-started/java/

(4) Data (Optional)
    - Exported from our MongoDB: <filename>







