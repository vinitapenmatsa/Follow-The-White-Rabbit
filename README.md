# Follow-The-White-Rabbit


[Instructions](#instructions)<br/>
[Performance](#performance)<br/>
[Implementation Details](#implementation-details)<br/>
[Screenshots](#screenshots)<br/>

# Instructions

Run directly using jar:
  1. Git clone the repository.
  2. mvn clean install - to generate target resources.
  3. run using : java -jar target/Follow-The-White-Rabbit-0.0.1-SNAPSHOT.jar.
  4. enter the required inputs Eg . difficulty level , min word length , max words per anangram.<br/>
  
  
Run the Docker file:<br/>
  Docker Image is attached , use the following commands to build and run image<br/>
  docker build -t {dockerimagename}<br/>
  docker run -i -t {dockerimagename}<br/>
  
# Performance

Easy Key - 1 second ,
Difficult key - 38 seconds ,
Hard Key - about 9 minutes ( screenshots attached below )

# Implementation Details

Solution can be divided in 3 parts / 3 levels of optimization.

 1.  Building a Minified Dictionary ( optimizing by dicarding all the imopossible words )<br/>
   
  - At this level , I've assumed that the following words are useless
      - words whose length > length of the given phrase
      - words whose length > min_word_length entered by the user
      - words which contain characters other than those present in the given phrase
      - words which contain characters that occur more than the number of times they've occurred in given phrase
      
  - with the above assumption we can easily narrow down to a few thousands of words.<br/>
  For example if min_word_length is 5 , we can narrow down to about 998 words vs 99175 words.<br/>
  - I've used Trie ( with hashmap ) to implement my dictionary. Ideally I would use DAWG ( directed acyclic word graphs ) if space is a concern because DAWGs reduce the number of nodes. Since we are aiming for lookup performance , I stuck to a trie , as it is easy to implement )
    
 2.  Creating a service that generates possible anagrams using the Minified Dictionary ( optimizing by using the dictionary to generate phrases )<br/>
    
 - At this level , instead of blindly going through all permutations using the letters of the phrase , we just use the          minified dictionary to generate possible phrases.<br/>
 - This approach improves the performance drastically </br>
   For example if min_word_length is 5 (998 words) and number of anangrams per phrase = 3 , we generate atmost 998 * 997 * 996 phrases vs 16! ( 16 is the no of letters 
   in 'poultry outwits ants') <br/>
    
 3.  Parallel processing using one thread for each letter in the phrase.
 
  -  The key is to return from all other threads if any one of them is successful at finding the key.<br/> 
  -  Performance again depends on host machine's specifications. Mine was 4-core , 4GB RAM and it took about 9 minutes to find the hard key.<br/>

# Screenshots

Easy secret Key
![image](https://user-images.githubusercontent.com/4529989/42385279-f1af5526-8159-11e8-9d98-71605b711ece.png)


Difficult secret Key
![image](https://user-images.githubusercontent.com/4529989/42385137-88f6052a-8159-11e8-9385-e641c5961b7c.png)

Hard secret key
![screen shot 2018-07-06 at 8 38 34 pm](https://user-images.githubusercontent.com/4529989/42386310-c9b97364-815c-11e8-86ac-9db90de51c8f.png)

